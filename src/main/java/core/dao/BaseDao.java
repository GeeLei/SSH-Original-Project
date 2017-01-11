package core.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.ParameterMode;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.zlg.util.New;

import com.huanke.sshshell.bean.Pager;
import com.huanke.sshshell.constant.BizStates;

import core.support.BaseParameter;
import core.support.QueryResult;

@Repository
public class BaseDao implements Dao {
    private static Map<String, Method> MAP_METHOD = new HashMap<String, Method>();
    protected final Logger log = Logger.getLogger(BaseDao.class);
    private SessionFactory sessionFactory;

    private void appendQL(StringBuffer sb, String[] propName, Object[] propValue) {
        // 遍历，取参数
        for (int i = 0; i < propName.length; i++) {
            String name = propName[i];
            Object value = propValue[i];
            // instanceof 判断左边的对象是否是右边对象的实例
            if (((value instanceof Object[]))
                    || ((value instanceof Collection))) {
                // 强转一下
                Object[] arraySerializable = (Object[]) value;
                if ((arraySerializable != null)
                        && (arraySerializable.length > 0)) {
                    sb.append(" and o." + name + " in (:"
                            + name.replace(".", "") + ")");
                }
            } else if (value == null) {
                sb.append(" and o." + name + " is null ");
            } else {
                sb.append(" and o." + name + "=:" + name.replace(".", ""));
            }
        }
    }

    protected void buildSorted(BaseParameter param, StringBuffer hql) {
        if ((param.getSortedConditions() != null)
                && (param.getSortedConditions().size() > 0)) {
            hql.append(" order by ");
            Map<String, String> sorted = param.getSortedConditions();
            for (String string : sorted.keySet()) {
                String col = string;
                hql.append(col + " " + sorted.get(col) + ",");
            }
            hql.deleteCharAt(hql.length() - 1);
        }
    }

    @Override
    public void clear() {
        this.getSession().clear();
    }

    @Override
    public <E> Long countAll(Class<E> entityClass) {
        StringBuffer sb = new StringBuffer("select count(*) from "
                + entityClass.getName() + " o where 1=1 ");
        String[] propName = { "isDelete" };
        Object[] propValue = { Byte.valueOf("0") };
        this.appendQL(sb, propName, propValue);
        Query query = this.getSession().createQuery(sb.toString());
        this.setParameter(query, propName, propValue);
        return (Long) query.uniqueResult();
    }

    @Override
    public <E> void delete(E entity) {
        if (entity != null) {
            try {
                BeanUtils.setProperty(entity, "isDelete", Byte.valueOf("1"));
                this.update(entity);
            } catch (Exception e) {
                this.log.error(e.getMessage(), e);
                return;
            }
        }
    }

    public <E> boolean deleteByPK(Class<E> entityClass, Serializable... id) {
        boolean result = false;
        if ((id != null) && (id.length > 0)) {
            for (Serializable element : id) {
                E entity = this.get(entityClass, element);
                if (entity != null) {
                    try {
                        BeanUtils.setProperty(entity, "isDelete",
                                Byte.valueOf("1"));
                        this.update(entity);
                    } catch (Exception e) {
                        this.log.error(e.getMessage(), e);
                        result = false;
                    }
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public <E> void deleteByProperties(Class<E> entityClass, String propName,
            Object propValue) {
        this.deleteByProperties(entityClass, new String[] { propName },
                new Object[] { propValue });
    }

    @Override
    public <E> void deleteByProperties(Class<E> entityClass,
            String[] conditionName, Object[] conditionValue) {
        String propertyName = "isDelete";
        Object propertyValue = Byte.valueOf("1");
        this.updateByProperties(entityClass, conditionName, conditionValue,
                propertyName,
                propertyValue);
    }

    public <E> Long doCount(Class<E> entityClass, BaseParameter param) {
        if (param == null) {
            return null;
        }
        Criteria criteria = this.getSession().createCriteria(entityClass);
        Map<String, Object> propMap = param.getQueryDynamicConditions();
        propMap.put("$eq_isdDelete", Byte.valueOf("0"));
        param.setQueryDynamicConditions(propMap);
        // this.processQuery(criteria, param);
        this.extendprocessQuery(entityClass, criteria, param);
        try {
            criteria.setProjection(Projections.rowCount());
            return Long.valueOf(((Number) criteria.uniqueResult()).longValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> QueryResult<E> doPaginationQuery(Class<E> entityClass,
            BaseParameter param) {
        return this.doPaginationQuery(entityClass, param, true);
    }

    public <E> QueryResult<E> doPaginationQuery(Class<E> entityClass,
            BaseParameter param, boolean bool) {
        if (param == null) {
            return null;
        }
        Criteria criteria = this.getSession().createCriteria(entityClass);
        Map<String, Object> propMap = param.getQueryDynamicConditions();
        propMap.put("$eq_isDelete", Byte.valueOf("0"));
        param.setQueryDynamicConditions(propMap);
        if (bool) {
            this.processQuery(criteria, param);
        } else {
            this.extendprocessQuery(entityClass, criteria, param);
        }
        try {
            QueryResult<E> qr = new QueryResult<E>();
            criteria.setProjection(Projections.rowCount());
            qr.setTotalCount(Long.valueOf(((Number) criteria.uniqueResult())
                    .longValue()));
            if (qr.getTotalCount().longValue() > 0L) {
                if ((param.getSortedConditions() != null)
                        && (param.getSortedConditions().size() > 0)) {
                    Map<String, String> map = param.getSortedConditions();
                    for (String string : param.getSortedConditions().keySet()) {
                        String pm = string;
                        if ("DESC".equalsIgnoreCase(map.get(pm))) {
                            criteria.addOrder(Order.desc(pm));
                        } else if ("ASC".equalsIgnoreCase(map.get(pm))) {
                            criteria.addOrder(Order.asc(pm));
                        }
                    }
                }
                criteria.setProjection(null);
                criteria.setMaxResults(param.getMaxResults().intValue());
                criteria.setFirstResult(param.getFirstResult().intValue());
                qr.setResultList(criteria.list());
            } else {
                qr.setResultList(new ArrayList<E>());
            }
            return qr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <E> List<E> doQuery(Class<E> entityClass, BaseParameter param) {
        if (param == null) {
            return null;
        }
        Criteria criteria = this.getSession().createCriteria(entityClass);
        Map<String, Object> propMap = param.getQueryDynamicConditions();
        propMap.put("$eq_isDelete", Byte.valueOf("0"));
        param.setQueryDynamicConditions(propMap);
        this.processQuery(criteria, param);
        try {
            if ((param.getSortedConditions() != null)
                    && (param.getSortedConditions().size() > 0)) {
                Map<String, String> map = param.getSortedConditions();
                for (String string : param.getSortedConditions().keySet()) {
                    String pm = string;
                    if ("DESC".equals(map.get(pm))) {
                        criteria.addOrder(Order.desc(pm));
                    } else if ("ASC".equals(map.get(pm))) {
                        criteria.addOrder(Order.asc(pm));
                    }
                }
            }
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> List<E> doQueryAll(Class<E> entityClass) {
        return this.doQueryAll(entityClass, null, null);
    }

    @Override
    public <E> List<E> doQueryAll(Class<E> entityClass, Integer top) {
        return this.doQueryAll(entityClass, null, top);
    }

    @Override
    public <E> List<E> doQueryAll(Class<E> entityClass,
            Map<String, String> sortedCondition, Integer top) {
        Criteria criteria = this.getSession().createCriteria(entityClass);
        BaseParameter param = new BaseParameter();
        Map<String, Object> propMap = new HashMap<String, Object>();
        propMap.put("$eq_isDelete", Byte.valueOf("0"));
        param.setQueryDynamicConditions(propMap);
        this.processQuery(criteria, param);
        if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
            for (String string : sortedCondition.keySet()) {
                String pm = string;
                if ("DESC".equalsIgnoreCase(sortedCondition.get(pm))) {
                    criteria.addOrder(Order.desc(pm));
                } else if ("ASC".equalsIgnoreCase(sortedCondition.get(pm))) {
                    criteria.addOrder(Order.asc(pm));
                }
            }
        }
        if (top != null) {
            criteria.setMaxResults(top.intValue());
            criteria.setFirstResult(0);
        }
        return criteria.list();
    }

    /**
     * 邹猛 doQueryByInMethods(这里用一句话描述这个方法的作用) 用in方法的查询
     * 
     * @param <E>
     * 
     * @param param
     * @return List<E>
     * @exception @since 1.0.0
     */
    @Override
    public <E> List<E> doQueryByInMethods(Class<E> entityClass,
            BaseParameter param) {
        if (param == null) {
            return null;
        }
        Criteria criteria = this.getSession().createCriteria(entityClass);
        Map<String, Object> propMap = param.getQueryDynamicConditions();
        propMap.put("$eq_isDelete", Byte.valueOf("0"));
        param.setQueryDynamicConditions(propMap);
        this.extendprocessQuery(entityClass, criteria, param);
        try {
            if ((param.getSortedConditions() != null)
                    && (param.getSortedConditions().size() > 0)) {
                Map<String, String> map = param.getSortedConditions();
                for (String string : param.getSortedConditions().keySet()) {
                    String pm = string;
                    if ("DESC".equalsIgnoreCase(map.get(pm))) {
                        criteria.addOrder(Order.desc(pm));
                    } else if ("ASC".equalsIgnoreCase(map.get(pm))) {
                        criteria.addOrder(Order.asc(pm));
                    }
                }
            }
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> void evict(E entity) {
        this.getSession().evict(entity);
    }

    private <E> void extendprocessQuery(Class<E> entityClass,
            Criteria criteria, BaseParameter param) {
        try {
            Map<String, Object> staticConditionMap = core.util.BeanUtils
                    .describeAvailableParameter(param);
            Map<String, Object> dynamicConditionMap = param
                    .getQueryDynamicConditions();
            String prop;
            if ((staticConditionMap != null) && (staticConditionMap.size() > 0)) {
                for (Map.Entry<String, Object> e : staticConditionMap
                        .entrySet()) {
                    Object value = e.getValue();
                    if ((value != null)
                            && ((!(value instanceof String)) || (!""
                                    .equals(value)))) {
                        prop = this.getPropName(e.getKey());
                        String methodName = this.getOpt(e.getKey());
                        Method m = this.getExtendMethod(methodName);
                        if ("like".equals(methodName)) {
                            criteria.add((Criterion) m.invoke(
                                    Restrictions.class, new Object[] { prop,
                                            value, MatchMode.ANYWHERE }));
                        } else if (("isNull".equals(methodName))
                                && ((value instanceof Boolean))) {
                            if (((Boolean) value).booleanValue()) {
                                criteria.add(Restrictions.isNull(prop));
                            } else {
                                criteria.add(Restrictions.isNotNull(prop));
                            }
                        } else if ((value != null)
                                && ((value instanceof Object[]))
                                && ("IN".equals(methodName.toUpperCase()))) {
                            Object[] obj = (Object[]) value;
                            criteria.add(Restrictions.in(prop, obj));
                        } else if ((value != null)
                                && ((value instanceof Object[]))
                                && ("BETWEEN".equals(methodName.toUpperCase()))) {
                            Object[] obj = (Object[]) value;
                            criteria.add(Restrictions.between(prop, obj[0],
                                    obj[1]));
                        } else {// 全查
                            criteria.add((Criterion) m.invoke(
                                    Restrictions.class, new Object[] { prop,
                                            value }));
                        }
                    }
                }
            }
            if ((dynamicConditionMap != null)
                    && (dynamicConditionMap.size() > 0)) {
                Object bean = entityClass.newInstance();
                Object map = new HashMap<Object, Object>();
                for (Map.Entry<String, Object> e : dynamicConditionMap
                        .entrySet()) {
                    ((Map) map).put(this.getPropName(e.getKey()), e.getValue());
                }
                org.apache.commons.beanutils.BeanUtils
                        .populate(bean, (Map) map);
                for (Map.Entry<String, Object> e : dynamicConditionMap
                        .entrySet()) {
                    String pn = e.getKey();
                    String prop1 = this.getPropName(pn);
                    String methodName = this.getOpt(pn);
                    // Method m = getMethod(methodName);
                    Method m = this.getExtendMethod(methodName);
                    // Object value = PropertyUtils.getNestedProperty(bean,
                    // prop1);
                    Object value = e.getValue();
                    if ((value != null)
                            && ((!(value instanceof String)) || (!""
                                    .equals(value)))) {
                        if ("like".equals(methodName)) {
                            criteria.add((Criterion) m.invoke(
                                    Restrictions.class, new Object[] { prop1,
                                            value, MatchMode.ANYWHERE }));
                        } else if (("isNull".equals(methodName))
                                && ((value instanceof Boolean))) {
                            if (((Boolean) value).booleanValue()) {
                                criteria.add(Restrictions.isNull(prop1));
                            } else {
                                criteria.add(Restrictions.isNotNull(prop1));
                            }
                        } else if ((value != null)
                                && ((value instanceof Object[]))
                                && ("IN".equals(methodName.toUpperCase()))) {
                            Object[] obj = (Object[]) value;
                            criteria.add(Restrictions.in(prop1, obj));
                        } else if ((value != null)
                                && ((value instanceof Object[]))
                                && ("BETWEEN".equals(methodName.toUpperCase()))) {
                            Object[] obj = (Object[]) value;
                            criteria.add(Restrictions.between(prop1, obj[0],
                                    obj[1]));
                        } else {
                            criteria.add((Criterion) m.invoke(
                                    Restrictions.class, new Object[] { prop1,
                                            value }));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <E> E get(Class<E> entityClass, Serializable id) {
        return (E) this.getSession().get(entityClass, id);
    }

    @Override
    public <E> E getByProerties(Class<E> entityClass, String propName,
            Object propValue) {
        return this.getByProerties(entityClass, new String[] { propName },
                new Object[] { propValue });
    }

    @Override
    public <E> E getByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Map<String, String> sortedCondition) {
        return this.getByProerties(entityClass, new String[] { propName },
                new Object[] { propValue }, sortedCondition);
    }

    @Override
    public <E> E getByProerties(Class<E> entityClass, String[] propName,
            Object[] propValue) {
        return this.getByProerties(entityClass, propName, propValue, null);
    }

    @Override
    public <E> E getByProerties(Class<E> entityClass, String[] propName,
            Object[] propValue, Map<String, String> sortedCondition) {
        if ((propName != null) && (propName.length > 0) && (propValue != null)
                && (propValue.length > 0)
                && (propValue.length == propName.length)) {
            // String的升级版，多用于sql语句的拼接
            StringBuffer sb = new StringBuffer("select o from "
                    + entityClass.getName() + " o where 1=1 ");
            String[] propNameNew = Arrays.copyOf(propName, propName.length + 1);
            propNameNew[propName.length] = "isDelete";
            propName = propNameNew;
            Object[] propValueNew = Arrays.copyOf(propValue,
                    propValue.length + 1);
            propValueNew[propValue.length] = Byte.valueOf("0");
            propValue = propValueNew;
            this.appendQL(sb, propName, propValue);
            if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
                sb.append(" order by ");
                for (Map.Entry<String, String> e : sortedCondition.entrySet()) {
                    sb.append(e.getKey() + " " + e.getValue() + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            Query query = this.getSession().createQuery(sb.toString());
            this.setParameter(query, propName, propValue);
            List<E> list = query.list();
            if ((list != null) && (list.size() > 0)) {
                return list.get(0);
            }
        }
        return null;
    }



    private Method getExtendMethod(String name) {
        if (!MAP_METHOD.containsKey(name)) {
            Class<Restrictions> clazz = Restrictions.class;
            Class[] paramType = { String.class, Object.class };
            Class[] likeParamType = { String.class, String.class,
                    MatchMode.class };
            Class[] isNullType = { String.class };
            try {
                Method method = null;
                if ("like".equals(name)) {
                    method = clazz.getMethod(name, likeParamType);
                } else if ("isNull".equals(name)) {
                    method = clazz.getMethod(name, isNullType);
                } else if (!"IN".equals(name.toUpperCase())
                        && !"BETWEEN".equals(name.toUpperCase())) {
                    method = clazz.getMethod(name, paramType);
                }
                MAP_METHOD.put(name, method);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return MAP_METHOD.get(name);
    }

    /**
     * 邹猛 getMaxId(这里用一句话描述这个方法的作用) 获取订单号种子id最大值
     * 
     * @return Long
     * @exception @since 1.0.0
     */
    @Override
    public <E> Long getMaxId(Class<E> entityClass) {
        StringBuffer sb = new StringBuffer("select max(id) from "
                + entityClass.getName() + " o where 1=1 ");
        String[] propName = { "isDelete" };
        Object[] propValue = { Byte.valueOf("0") };
        this.appendQL(sb, propName, propValue);
        Query query = this.getSession().createQuery(sb.toString());
        this.setParameter(query, propName, propValue);
        return (Long) query.uniqueResult();
    }

    private Method getMethod(String name) {
        if (!MAP_METHOD.containsKey(name)) {
            Class<Restrictions> clazz = Restrictions.class;
            Class[] paramType = { String.class, Object.class };
            Class[] likeParamType = { String.class, String.class,
                    MatchMode.class };
            Class[] isNullType = { String.class };
            try {
                Method method = null;
                if ("like".equals(name)) {
                    method = clazz.getMethod(name, likeParamType);
                } else if ("isNull".equals(name)) {
                    method = clazz.getMethod(name, isNullType);
                } else {
                    method = clazz.getMethod(name, paramType);
                }
                MAP_METHOD.put(name, method);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return MAP_METHOD.get(name);
    }

    @Override
    public List<Map<String, Object>> getObjectList(String tableName,
            String columns, String condition) {
        if ((tableName == null) || "".equals(tableName)) {
            return null;
        }
        if ((columns == null) || "".equals(columns)) {
            return null;
        }
        if ((condition == null) || "".equals(condition)) {
            return null;
        }
        StringBuffer sb = new StringBuffer(String.format(
                "select %s from %s o where %s", columns, tableName, condition));
        SQLQuery sqlQuery = this.getSession().createSQLQuery(sb.toString());
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return sqlQuery.list();
    }

    private String getOpt(String value) {
        return value.substring(0, value.indexOf('_', 1)).substring(1);// 向后截取
    }

    private String getPropName(String value) {
        return value.substring(value.indexOf('_', 1) + 1);
    }

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();// .openSession();//;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    protected Map<String, Object> handlerConditions(BaseParameter param)
            throws Exception {
        Map<String, Object> staticConditions = core.util.BeanUtils
                .describe(param);
        Map<String, Object> dynamicConditions = param
                .getQueryDynamicConditions();
        if (dynamicConditions.size() > 0) {
            for (String string : staticConditions.keySet()) {
                String key = string;
                Object value = staticConditions.get(key);
                if ((key.startsWith("$")) && (value != null)
                        && (!"".equals(value))) {
                    dynamicConditions.put(key, value);
                }
            }
            staticConditions = dynamicConditions;
        }
        return staticConditions;
    }

    @Override
    public <E> E load(Class<E> entityClass, Serializable id) {
        return (E) this.getSession().load(entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E merge(E entity) {
        return (E) this.getSession().merge(entity);
    }

    @Override
    public <E> Serializable persist(E entity) {
        if (entity != null) {
            try {
                BeanUtils.setProperty(entity, "isDelete", Byte.valueOf("0"));
            } catch (Exception e) {
            }
        }
        return this.getSession().save(entity);
    }

    private void processQuery(Criteria criteria, BaseParameter param) {
        try {
            Map<String, Object> staticConditionMap = core.util.BeanUtils
                    .describeAvailableParameter(param);
            Map<String, Object> dynamicConditionMap = param
                    .getQueryDynamicConditions();
            Disjunction disjunction = Restrictions.disjunction();// 分离
            String prop;
            if ((staticConditionMap != null) && (staticConditionMap.size() > 0)) {
                for (Map.Entry<String, Object> e : staticConditionMap
                        .entrySet()) {
                    Object value = e.getValue();
                    if ((value != null)
                            && ((!(value instanceof String)) || (!""
                                    .equals(value)))) {
                        prop = this.getPropName(e.getKey());
                        String methodName = this.getOpt(e.getKey());
                        Method m = this.getMethod(methodName);
                        if ("like".equals(methodName)) {
                            if (param.getFlag().equals("OR")) {
                                criteria.add(disjunction.add((Criterion) m
                                        .invoke(Restrictions.class,
                                                new Object[] { prop, value,
                                                        MatchMode.ANYWHERE })));
                            } else {
                                criteria.add((Criterion) m.invoke(
                                        Restrictions.class,
                                        new Object[] { prop, value,
                                                MatchMode.ANYWHERE }));
                            }
                        } else if (("isNull".equals(methodName))
                                && ((value instanceof Boolean))) {
                            if (((Boolean) value).booleanValue()) {
                                if (param.getFlag().equals("OR")) {
                                    criteria.add(disjunction.add(Restrictions
                                            .isNull(prop)));
                                } else {
                                    criteria.add(Restrictions.isNull(prop));
                                }
                            } else if (param.getFlag().equals("OR")) {
                                criteria.add(disjunction.add(Restrictions
                                        .isNotNull(prop)));
                            } else {
                                criteria.add(Restrictions.isNotNull(prop));
                            }
                        } else if (param.getFlag().equals("OR")) {
                            criteria.add(disjunction.add((Criterion) m.invoke(
                                    Restrictions.class, new Object[] { prop,
                                            value })));
                        } else {
                            criteria.add((Criterion) m.invoke(
                                    Restrictions.class, new Object[] { prop,
                                            value }));
                        }
                    }
                }
            }
            if ((dynamicConditionMap != null)
                    && (dynamicConditionMap.size() > 0)) {
                // Object bean = this.entityClass.newInstance();
                // Map map = new HashMap<Object, Object>();
                // for (Map.Entry<String, Object> e :
                // dynamicConditionMap.entrySet()) {
                // map.put(getPropName((String) e.getKey()), e.getValue());
                // }
                // org.apache.commons.beanutils.BeanUtils.populate(bean, (Map)
                // map);
                for (Map.Entry<String, Object> e : dynamicConditionMap
                        .entrySet()) {
                    String pn = e.getKey();
                    // System.out.println("***getKey :"+pn);
                    String prop1 = this.getPropName(pn);
                    // BeanUtils.getProperty(bean, prop1)
                    // System.out.println("***getPropName :"+prop1);
                    String methodName = this.getOpt(pn);
                    Method m = this.getMethod(methodName);// 原来getMethod
                    // System.out.println("***methodname :"+m+"----
                    // :"+methodName);
                    // Object value = PropertyUtils.getNestedProperty(bean,
                    // prop1);
                    Object value = e.getValue();
                    if ((value != null)
                            && ((!(value instanceof String)) || (!""
                                    .equals(value)))) {
                        if ("like".equals(methodName)) {
                            if (param.getFlag().equals("OR")) {
                                criteria.add(disjunction.add((Criterion) m
                                        .invoke(Restrictions.class,
                                                new Object[] { prop1, value,
                                                        MatchMode.ANYWHERE })));
                            } else {
                                criteria.add((Criterion) m.invoke(
                                        Restrictions.class, new Object[] {
                                                prop1, value,
                                                MatchMode.ANYWHERE }));
                            }
                        } else if (("isNull".equals(methodName))
                                && ((value instanceof Boolean))) {
                            if (((Boolean) value).booleanValue()) {
                                if (param.getFlag().equals("OR")) {
                                    criteria.add(disjunction.add(Restrictions
                                            .isNull(prop1)));
                                } else {
                                    criteria.add(Restrictions.isNull(prop1));
                                }
                            } else if (param.getFlag().equals("OR")) {
                                criteria.add(disjunction.add(Restrictions
                                        .isNotNull(prop1)));
                            } else {
                                criteria.add(Restrictions.isNotNull(prop1));
                            }
                        } else if (param.getFlag().equals("OR")) {
                            criteria.add(disjunction.add((Criterion) m.invoke(
                                    Restrictions.class, new Object[] { prop1,
                                            value })));
                        } else {
                            criteria.add((Criterion) m.invoke(
                                    Restrictions.class, new Object[] { prop1,
                                            value }));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue) {
        return this.queryByProerties(entityClass, new String[] { propName },
                new Object[] { propValue }, null, null);
    }

    @Override
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Integer top) {
        return this.queryByProerties(entityClass, new String[] { propName },
                new Object[] { propValue }, null, top);
    }

    @Override
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Map<String, String> sortedCondition) {
        return this.queryByProerties(entityClass, new String[] { propName },
                new Object[] { propValue }, sortedCondition, null);
    }

    @Override
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Map<String, String> sortedCondition, Integer top) {
        return this.queryByProerties(entityClass, new String[] { propName },
                new Object[] { propValue }, sortedCondition, top);
    }

    @Override
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName,
            Object[] propValue) {
        return this.queryByProerties(entityClass, propName, propValue, null,
                null);
    }

    @Override
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Integer top) {
        return this.queryByProerties(entityClass, propName, propValue, null,
                top);
    }

    @Override
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Map<String, String> sortedCondition) {
        return this.queryByProerties(entityClass, propName, propValue,
                sortedCondition, null);
    }

    @Override
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Map<String, String> sortedCondition, Integer top) {
        if ((propName != null) && (propValue != null)
                && (propValue.length == propName.length)) {
            StringBuffer sb = new StringBuffer("select o from "
                    + entityClass.getName() + " o where 1=1 ");
            String[] propNameNew = Arrays.copyOf(propName, propName.length + 1);
            propNameNew[propName.length] = "isDelete";
            propName = propNameNew;
            Object[] propValueNew = Arrays.copyOf(propValue,
                    propValue.length + 1);
            propValueNew[propValue.length] = Byte.valueOf("0");
            propValue = propValueNew;
            this.appendQL(sb, propName, propValue);
            if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
                sb.append(" order by ");
                for (Map.Entry<String, String> e : sortedCondition.entrySet()) {
                    sb.append(e.getKey() + " " + e.getValue() + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            Query query = this.getSession().createQuery(sb.toString());
            this.setParameter(query, propName, propValue);
            if (top != null) {
                query.setFirstResult(0);
                query.setMaxResults(top.intValue());
            }
            List<E> resList = query.list();
            return resList;
        }
        return null;
    }

    @Override
    public <E> List<E> queryByProertiesByPage(Class<E> entityClass,
            String[] propName,
            Object[] propValue, Map<String, String> sortedCondition,
            Integer firstIndex, Integer count) {
        if ((propName != null) && (propValue != null)
                && (propValue.length == propName.length)) {
            StringBuffer sb = new StringBuffer("select o from "
                    + entityClass.getName() + " o where 1=1 ");
            String[] propNameNew = Arrays.copyOf(propName, propName.length + 1);
            propNameNew[propName.length] = "isDelete";
            propName = propNameNew;
            Object[] propValueNew = Arrays.copyOf(propValue,
                    propValue.length + 1);
            propValueNew[propValue.length] = Byte.valueOf("0");
            propValue = propValueNew;
            this.appendQL(sb, propName, propValue);
            if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
                sb.append(" order by ");
                for (Map.Entry<String, String> e : sortedCondition.entrySet()) {
                    sb.append(e.getKey() + " " + e.getValue() + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            Query query = this.getSession().createQuery(sb.toString());
            this.setParameter(query, propName, propValue);
            if ((firstIndex != null) && (count != null)) {
                query.setFirstResult(firstIndex);
                query.setMaxResults(count);
            }
            List<E> resList = query.list();
            return resList;
        }
        return null;
    }

    @Override
    public <E> Pager<E> queryByProertiesPage(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Map<String, String> sortedCondition, int pageNo, int pageSize) {
        if (propName == null) {
            propName = new String[0];
        }
        if (propValue == null) {
            propValue = new Object[0];
        }

        if ((propValue.length == propName.length)) {
            Pager<E> pager = new Pager<E>();
            pager.setPageNo(pageNo);
            pager.setPageSize(pageSize);

            String[] propNameNew = Arrays.copyOf(propName, propName.length + 1);
            propNameNew[propName.length] = "isDelete";
            propName = propNameNew;
            Object[] propValueNew = Arrays.copyOf(propValue,
                    propValue.length + 1);
            propValueNew[propValue.length] = BizStates.ISDELETE.NO;
            propValue = propValueNew;
            StringBuffer sb = new StringBuffer("select o from "
                    + entityClass.getName() + " o where 1=1 ");
            this.appendQL(sb, propName, propValue);
            StringBuffer countSql = new StringBuffer("select count(*) from "
                    + entityClass.getName() + " o where 1=1 ");
            this.appendQL(countSql, propName, propValue);

            if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
                sb.append(" order by ");
                for (Map.Entry<String, String> e : sortedCondition.entrySet()) {
                    sb.append(e.getKey() + " " + e.getValue() + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            Query query = this.getSession().createQuery(sb.toString());
            Query countQuery = this.getSession().createQuery(
                    countSql.toString());
            this.setParameter(query, propName, propValue);
            this.setParameter(countQuery, propName, propValue);
            Long totalCount = Long.valueOf(((Number) countQuery.uniqueResult())
                    .longValue());
            pager.setRecordsTotal(totalCount.longValue());
            if (totalCount > 0) {
                query.setFirstResult((pageNo - 1) * pageSize);
                query.setMaxResults(pageSize);
                List<E> resList = query.list();
                pager.setData(resList);
            } else {
                pager.setData(Collections.EMPTY_LIST);
            }
            return pager;
        }
        throw new IllegalArgumentException("参数名个数为" + propName.length
                + "和参数的个数" + propValue.length + "不相等");
    }

    protected void setParameter(Map<String, Object> mapParameter, Query query) {
        for (String string : mapParameter.keySet()) {
            String parameterName = string;
            Object value = mapParameter.get(parameterName);
            query.setParameter(parameterName, value);
        }
    }

    private void setParameter(Query query, String[] propName, Object[] propValue) {
        for (int i = 0; i < propName.length; i++) {
            String name = propName[i];
            Object value = propValue[i];
            if (value != null) {
                if ((value instanceof Object[])) {
                    query.setParameterList(name.replace(".", ""),
                            (Object[]) value);
                } else if ((value instanceof Collection)) {
                    query.setParameterList(name.replace(".", ""),
                            (Collection) value);
                } else {
                    query.setParameter(name.replace(".", ""), value);
                }
            }
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSF(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }

    @Override
    public <E> void update(E entity) {
        Session s = this.getSession();// 解决opensessioninview的delete和update权限，by
        // 邹猛
        s.setFlushMode(FlushMode.AUTO);
        s.update(entity);
        s.flush();
    }

    @Override
    public <E> void update(E entity, Serializable oldId) {
        this.deleteByPK(entity.getClass(), new Serializable[] { oldId });
        this.persist(entity);
    }

    @Override
    public <E> void updateByProperties(Class<E> entityClass,
            String conditionName, Object conditionValue,
            String propertyName, Object propertyValue) {
        this.updateByProperties(entityClass, new String[] { conditionName },
                new Object[] { conditionValue }, new String[] { propertyName },
                new Object[] { propertyValue });
    }

    @Override
    public <E> void updateByProperties(Class<E> entityClass,
            String conditionName, Object conditionValue,
            String[] propertyName, Object[] propertyValue) {
        this.updateByProperties(entityClass, new String[] { conditionName },
                new Object[] { conditionValue }, propertyName, propertyValue);
    }

    @Override
    public <E> void updateByProperties(Class<E> entityClass,
            String[] conditionName, Object[] conditionValue,
            String propertyName, Object propertyValue) {
        this.updateByProperties(entityClass, conditionName, conditionValue,
                new String[] { propertyName }, new Object[] { propertyValue });
    }

    @Override
    public <E> void updateByProperties(Class<E> entityClass,
            String[] conditionName,
            Object[] conditionValue, String[] propertyName,
            Object[] propertyValue) {
        if ((propertyName != null) && (propertyName.length > 0)
                && (propertyValue != null) && (propertyValue.length > 0)
                && (propertyName.length == propertyValue.length)
                && (conditionValue != null) && (conditionValue.length > 0)) {
            StringBuffer sb = new StringBuffer();
            sb.append("update " + entityClass.getName() + " o set ");
            for (String element : propertyName) {
                sb.append(element + " = :p_" + element + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(" where 1=1 ");
            String[] conditionNameNew = Arrays.copyOf(conditionName,
                    conditionName.length + 1);
            conditionNameNew[conditionName.length] = "isDelete";
            conditionName = conditionNameNew;
            Object[] conditionValueNew = Arrays.copyOf(conditionValue,
                    conditionValue.length + 1);
            conditionValueNew[conditionValue.length] = Byte.valueOf("0");
            conditionValue = conditionValueNew;
            this.appendQL(sb, conditionName, conditionValue);
            Query query = this.getSession().createQuery(sb.toString());
            for (int i = 0; i < propertyName.length; i++) {
                query.setParameter("p_" + propertyName[i], propertyValue[i]);
            }
            this.setParameter(query, conditionName, conditionValue);
            query.executeUpdate();
        } else {
            throw new IllegalArgumentException(
                    "Method updateByProperties in BaseDao argument is illegal!");
        }
    }

    /**
     * zm 多model删除 deleteByEntities
     * 
     * @param propName
     * @param propValue
     *            void
     * @exception
     * @since 1.0.0
     */
    public void deleteByEntities(String[] propName, Object[] propValue,
            String entities) {
        if ((propName != null) && (propName.length > 0) && (propValue != null)
                && (propValue.length > 0)
                && (propValue.length == propName.length)) {
            StringBuffer sb = new StringBuffer("delete from " + entities
                    + " o where 1=1 ");
            appendQL(sb, propName, propValue);
            Query query = getSession().createQuery(sb.toString());
            setParameter(query, propName, propValue);
            query.executeUpdate();
        }
    }

    /**
     * zm 存储过程
     * 
     * @param types
     * @param procedureName
     * @param hql
     *            void
     * @exception
     * @since 1.0.0
     */
    public Long callProcedure(String types, String procedureName) {
        ProcedureCall pc = getSession()
                .createStoredProcedureCall(procedureName);
        pc.registerParameter("types", Byte.class, ParameterMode.IN).bindValue(
                Byte.valueOf(types));
        pc.registerParameter("ddh_return", Long.class, ParameterMode.OUT);
        Long result = (Long) pc.getOutputs().getOutputParameterValue(
                "ddh_return");
        return result;
    }

    // 统一查询方法(hql)
    public List executeQuery(String hql, Object[] parameters) {
        // TODO Auto-generated method stub
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof Collection<?>) {
                    query.setParameterList("inPara",
                            (Collection<?>) parameters[i]);
                } else {
                    query.setParameter(i, parameters[i]);
                }
            }
        }
        int pageCount = 0;
        List ls = query.list();
        try {
            pageCount = ls.size();
        } catch (Exception e) {
        }
        List<Object> rs = New.arraylist();
        rs.add(pageCount);
        rs.add(ls);
        return rs;
    }

    public void executeHql(String hql, Object[] parameters) {
        // TODO Auto-generated method stub
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof Collection<?>) {
                    query.setParameterList("inPara",
                            (Collection<?>) parameters[i]);
                } else {
                    query.setParameter(i, parameters[i]);
                }
            }
        }
        query.executeUpdate();
    }

    // sql原生查询
    public void executeSql(String sql, Object[] parameters) {
        // TODO Auto-generated method stub
        Query query = this.sessionFactory.getCurrentSession().createSQLQuery(
                sql);
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof Collection<?>) {
                    query.setParameterList("inPara",
                            (Collection<?>) parameters[i]);
                } else {
                    query.setParameter(i, parameters[i]);
                }
            }
        }
        query.executeUpdate();
    }

    // 查询+分页
    public List executeQueryByPage(String hql, Object[] parameters,
            int pageNum, int PagesSize) {
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof Collection<?>) {
                    query.setParameterList("inPara",
                            (Collection<?>) parameters[i]);
                } else {
                    query.setParameter(i, parameters[i]);
                }
            }
        }
        List<Object> rs = New.arraylist();
        rs.add(getCounts(hql, parameters));
        // 体现分页
        rs.add(query.setFirstResult((pageNum - 1) * PagesSize)
                .setMaxResults(PagesSize).list());
        return rs;
    }

    private Long getCounts(String sql, Object[] parameters) {
        sql = "select count(*) from" + sql.substring(sql.indexOf("from") + 4);
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof Collection<?>) {
                    query.setParameterList("inPara",
                            (Collection<?>) parameters[i]);
                } else {
                    query.setParameter(i, parameters[i]);
                }
            }
        }
        return (Long) query.uniqueResult();
    }

    /*
     * 获取对象属性
     */
    public <E> Object[] getFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue,
            Map<String, String> sortedCondition) {
        if ((propName != null) && (propName.length > 0) && (propValue != null)
                && (propValue.length > 0)
                && (propValue.length == propName.length)) {
            // String的升级版，多用于sql语句的拼接
            StringBuffer sb = new StringBuffer("select ");
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    sb.append("o." + fields[i] + " , ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append("from " + entityClass.getName() + " o where 1=1 ");
                String[] propNameNew = Arrays.copyOf(propName,
                        propName.length + 1);
                propNameNew[propName.length] = "isDelete";
                propName = propNameNew;
                Object[] propValueNew = Arrays.copyOf(propValue,
                        propValue.length + 1);
                propValueNew[propValue.length] = Byte.valueOf("0");
                propValue = propValueNew;
                this.appendQL(sb, propName, propValue);
                if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
                    sb.append(" order by ");
                    for (Map.Entry<String, String> e : sortedCondition
                            .entrySet()) {
                        sb.append(e.getKey() + " " + e.getValue() + ",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                Query query = this.getSession().createQuery(sb.toString());
                this.setParameter(query, propName, propValue);
                System.out.println("查询语句内容为：" + query.getQueryString());

                List list = query.list();
                if ((list != null) && (list.size() > 0)) {
                    return (Object[]) list.get(0);
                }
            }
        }
        return null;
    }

    /**
     * 根据属性名数组，排序条件获取指定多个属性
     */
    public <E> Object[] getFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue,
            Map<String, String> sortedCondition) {
        return this.getFieldsByProperties(entityClass, fields,
                new String[] { propName }, new Object[] { propValue },
                sortedCondition);
    }

    /**
     * 根据属性名数组，获取指定多个属性
     */
    public <E> Object[] getFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue) {
        return this.getFieldsByProperties(entityClass, fields,
                new String[] { propName }, new Object[] { propValue }, null);
    }

    /**
     * 根据属性名数组，获取指定多个属性
     */
    public <E> Object[] getFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue) {
        return this.getFieldsByProperties(entityClass, fields, propName,
                propValue, null);
    }
    /*
     * 获取对象属性列表
     */
    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue,
            Map<String, String> sortedCondition, Integer top) {
        if ((propName != null) && (propName.length > 0) && (propValue != null)
                && (propValue.length > 0)
                && (propValue.length == propName.length)) {
            // String的升级版，多用于sql语句的拼接
            StringBuffer sb = new StringBuffer("select ");
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    sb.append("o." + fields[i] + " , ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append("from " + entityClass.getName() + " o where 1=1 ");
                String[] propNameNew = Arrays.copyOf(propName,
                        propName.length + 1);
                propNameNew[propName.length] = "isDelete";
                propName = propNameNew;
                Object[] propValueNew = Arrays.copyOf(propValue,
                        propValue.length + 1);
                propValueNew[propValue.length] = Byte.valueOf("0");
                propValue = propValueNew;
                this.appendQL(sb, propName, propValue);
                if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
                    sb.append(" order by ");
                    for (Map.Entry<String, String> e : sortedCondition
                            .entrySet()) {
                        sb.append(e.getKey() + " " + e.getValue() + ",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                Query query = this.getSession().createQuery(sb.toString());
                this.setParameter(query, propName, propValue);
                if (top != null) {
                    query.setFirstResult(0);
                    query.setMaxResults(top.intValue());
                }
                List<Object[]> list = query.list();
                return list;

            }
        }
        return null;
    }

    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue) {
        return this.queryFieldsByProperties(entityClass, fields, propName,
                propValue, null, null);
    }

    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue,
            Map<String, String> sortedCondition) {
        return this.queryFieldsByProperties(entityClass, fields,
                new String[] { propName }, new Object[] { propValue },
                sortedCondition, null);
    }

    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue) {
        return this.queryFieldsByProperties(entityClass, fields,
                new String[] { propName }, new Object[] { propValue }, null,
                null);
    }

    /**
     * 根据属性名数组获取指定数量个属性列表
     * 
     * @param entityClass
     *            对象类型
     * @param fields
     *            查找元素数组
     * @param propName
     *            属性名数组
     * @param propValue
     *            属性值数组
     * @param top
     *            返回数量
     * 
     * @return
     */
    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue, Integer top) {
        return this.queryFieldsByProperties(entityClass, fields, propName,
                propValue, null, top);
    }

    /**
     * 根据属性名和排序条件获取指定数量个属性列表
     * 
     * @param entityClass
     *            对象类型
     * @param fields
     *            查找元素数组
     * @param propName
     *            属性名
     * @param propValue
     *            属性值
     * @param sortedCondition
     *            排序条件
     * @param top
     *            返回数量
     * @return
     */
    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue,
            Map<String, String> sortedCondition, Integer top) {
        return this.queryFieldsByProperties(entityClass, fields, propName,
                propValue, sortedCondition, top);
    }

    /**
     * 根据属性名获取指定数量个属性列表
     * 
     * @param entityClass
     *            对象类型
     * @param fields
     *            查找元素数组
     * @param propName
     *            属性名
     * @param propValue
     *            属性值
     * @param top
     *            返回数量
     * @return
     */
    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue, Integer top) {
        return this.queryFieldsByProperties(entityClass, fields, propName,
                propValue, null, top);
    }

    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue,
            Map<String, String> sortedCondition) {
        return this.queryFieldsByProperties(entityClass, fields, propName,
                propValue, sortedCondition, null);
    }
}
