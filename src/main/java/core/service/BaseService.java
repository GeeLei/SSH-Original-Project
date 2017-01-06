package core.service;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huanke.sshshell.bean.Pager;

import core.dao.BaseDao;
import core.dao.Dao;
import core.support.BaseParameter;
import core.support.QueryResult;

@Service
@Transactional
public class BaseService implements Service {

    protected final Logger log = Logger.getLogger(BaseService.class);


    public Dao dao = new BaseDao();

    // @Resource
    // public void setDao(BaseDao baseDao) {
    // this.dao = baseDao;
    // }

    public <E> Serializable persist(E entity) {
        return this.dao.persist(entity);
    }

    public <E> boolean deleteByPK(Class<E> entityClass, Serializable... id) {
        return this.dao.deleteByPK(entityClass, id);
    }

    public <E> void delete(E entity) {
        this.dao.delete(entity);
    }

    public <E> void deleteByProperties(Class<E> entityClass, String[] propName,
            Object[] propValue) {
        this.dao.deleteByProperties(entityClass, propName, propValue);
    }

    public <E> void deleteByProperties(Class<E> entityClass, String propName,
            Object propValue) {
        this.dao.deleteByProperties(entityClass, propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> E get(Class<E> entityClass, Serializable id) {
        return this.dao.get(entityClass, id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> E getByProerties(Class<E> entityClass, String[] propName,
            Object[] propValue) {
        return this.dao.getByProerties(entityClass, propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> E getByProerties(Class<E> entityClass, String propName,
            Object propValue) {
        return this.dao.getByProerties(entityClass, propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> E getByProerties(Class<E> entityClass, String[] propName,
            Object[] propValue,
            Map<String, String> sortedCondition) {
        return this.dao.getByProerties(entityClass, propName, propValue,
                sortedCondition);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> E getByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Map<String, String> sortedCondition) {
        return this.dao.getByProerties(entityClass, propName, propValue,
                sortedCondition);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> E load(Class<E> entityClass, Serializable id) {
        return this.dao.load(entityClass, id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue) {
        return this.dao.queryByProerties(entityClass, propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue) {
        return this.dao.queryByProerties(entityClass, propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Map<String, String> sortedCondition) {
        return this.dao.queryByProerties(entityClass, propName, propValue,
                sortedCondition);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Map<String, String> sortedCondition) {
        return this.dao.queryByProerties(entityClass, propName, propValue,
                sortedCondition);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Map<String, String> sortedCondition, Integer top) {
        return this.dao.queryByProerties(entityClass, propName, propValue,
                sortedCondition,
                top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Integer top) {
        return this.dao.queryByProerties(entityClass, propName, propValue, top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Map<String, String> sortedCondition, Integer top) {
        return this.dao.queryByProerties(entityClass, propName, propValue,
                sortedCondition,
                top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Integer top) {
        return this.dao.queryByProerties(entityClass, propName, propValue, top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> queryByProertiesByPage(Class<E> entityClass,
            String[] propName,
            Object[] propValue, Map<String, String> sortedCondition,
            Integer firstIndex, Integer count) {
        return this.dao.queryByProertiesByPage(entityClass, propName,
                propValue,
                sortedCondition, firstIndex, count);
    }

    public <E> E merge(E entity) {
        return this.dao.merge(entity);
    }

    public <E> void update(E entity) {
        this.dao.update(entity);
    }

    public <E> void updateByProperties(Class<E> entityClass,
            String[] conditionName,
            Object[] conditionValue, String[] propertyName,
            Object[] propertyValue) {
        this.dao.updateByProperties(entityClass, conditionName,
                conditionValue,
                propertyName, propertyValue);
    }

    public <E> void updateByProperties(Class<E> entityClass,
            String conditionName, Object conditionValue,
            String[] propertyName, Object[] propertyValue) {
        this.dao.updateByProperties(entityClass, conditionName, conditionValue,
                propertyName, propertyValue);
    }

    public <E> void updateByProperties(Class<E> entityClass,
            String[] conditionName,
            Object[] conditionValue, String propertyName, Object propertyValue) {
        this.dao.updateByProperties(entityClass, conditionName, conditionValue,
                propertyName, propertyValue);
    }

    public <E> void updateByProperties(Class<E> entityClass,
            String conditionName, Object conditionValue,
            String propertyName, Object propertyValue) {
        this.dao.updateByProperties(entityClass, conditionName, conditionValue,
                propertyName, propertyValue);
    }

    public <E> void update(E entity, Serializable oldId) {
        this.dao.update(entity, oldId);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> doQueryAll(Class<E> entityClass) {
        return this.dao.doQueryAll(entityClass);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> doQueryAll(Class<E> entityClass,Map<String, String> sortedCondition, Integer top) {
        return this.dao.doQueryAll(entityClass,sortedCondition, top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> doQueryAll(Class<E> entityClass,Integer top) {
        return this.dao.doQueryAll(entityClass,top);
    }

    public <E> void evict(E entity) {
        this.dao.evict(entity);
    }

    public void clear() {
        this.dao.clear();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> Long countAll(Class<E> entityClass) {
        return this.dao.countAll(entityClass);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> Long doCount(Class<E> entityClass,BaseParameter parameter) {
        return this.dao.doCount(entityClass,parameter);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> List<E> doQuery(Class<E> entityClass,BaseParameter parameter) {
        return this.dao.doQuery(entityClass,parameter);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> QueryResult<E> doPaginationQuery(Class<E> entityClass,BaseParameter parameter) {
        return this.dao.doPaginationQuery(entityClass,parameter);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> QueryResult<E> doPaginationQuery(Class<E> entityClass,BaseParameter parameter,
            boolean bool) {
        return this.dao.doPaginationQuery(entityClass,parameter, bool);
    }

    /**
     * 邹猛 getMaxId(这里用一句话描述这个方法的作用) 获取订单号种子id最大值
     * @param <E>
     * 
     * @return Long
     * @exception
     * @since 1.0.0
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public <E> Long getMaxId(Class<E> entityClass) {
        return this.dao.getMaxId(entityClass);
    }

    /**
     * 邹猛 doQueryByInMethods(这里用一句话描述这个方法的作用) 用in方法的查询
     * @param <E>
     * 
     * @param param
     * @return List<E>
     * @exception
     * @since 1.0.0
     */
    public <E> List<E> doQueryByInMethods(Class<E> entityClass,BaseParameter param) {
        return this.dao.doQueryByInMethods(entityClass,param);
    }

    public <E> Pager<E> queryByProertiesPage(Class<E> entityClass,String[] propName, Object[] propValue,
            Map<String, String> sortedCondition, int pageNo, int pageSize) {
        return this.dao.queryByProertiesPage(entityClass,propName, propValue,
                sortedCondition, pageNo, pageSize);
    }

    public void deleteByEntities(String[] propName, Object[] propValue,
            String entities) {
        this.dao.deleteByEntities(propName, propValue, entities);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String value() {
        // TODO Auto-generated method stub
        return null;
    }

}
