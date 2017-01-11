package core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.huanke.sshshell.bean.Pager;

import core.support.BaseParameter;
import core.support.QueryResult;

public interface Service {
    // E是指Element，是一个集合，元素

    /**
     * 持久化对象实体
     * 
     * @param <E>
     * 
     * @param entity
     *            对象实体
     * @return
     */
    public <E> Serializable persist(E entity);

    /**
     * 根据多个id参数删除对象
     * 
     * @param <E>
     * 
     * @param id
     *            多个id，以英文逗号隔开
     * @return 返回true或者false
     */
    public <E> boolean deleteByPK(Class<E> entityClass, Serializable... id);

    /**
     * 删除对象实体
     * 
     * @param <E>
     * 
     * @param entity
     *            对象实体
     */
    public <E> void delete(E entity);

    /**
     * 以HQL的方式，根据单个属性删除对象实体
     * 
     * @param <E>
     * 
     * @param propName
     *            属性名称
     * @param propValue
     *            属性值
     */
    public <E> void deleteByProperties(Class<E> entityClass, String propName,
            Object propValue);

    /**
     * 以HQL的方式，根据多个属性删除对象实体
     * 
     * @param <E>
     * 
     * @param propName
     *            属性名称
     * @param propValue
     *            属性值
     */
    public <E> void deleteByProperties(Class<E> entityClass, String[] propName,
            Object[] propValue);

    /**
     * 根据给定的Detached对象标识符更新对象实体
     * 
     * @param <E>
     * 
     * @param entity
     *            对象实体
     */
    public <E> void update(E entity);

    /**
     * 根据多个属性条件更新对象实体多个属性
     * 
     * @param <E>
     * 
     * @param conditionName
     *            WHERE子句条件的属性数组名称
     * @param conditionValue
     *            WHERE子句条件的属性数组值
     * @param propertyName
     *            UPDATE子句属性数组名称
     * @param propertyValue
     *            UPDATE子句属性数组值
     */
    public <E> void updateByProperties(Class<E> entityClass,
            String[] conditionName,
            Object[] conditionValue, String[] propertyName,
            Object[] propertyValue);

    /**
     * 根据单个属性条件更新对象实体多个属性
     * 
     * @param <E>
     * 
     * @param conditionName
     *            WHERE子句条件的属性数组名称
     * @param conditionValue
     *            WHERE子句条件的属性数组值
     * @param propertyName
     *            UPDATE子句属性名称
     * @param propertyValue
     *            UPDATE子句属性值
     */
    public <E> void updateByProperties(Class<E> entityClass,
            String[] conditionName,
            Object[] conditionValue, String propertyName, Object propertyValue);

    /**
     * 根据多个属性条件更新对象实体单个属性
     * 
     * @param <E>
     * 
     * @param conditionName
     *            WHERE子句条件的属性名称
     * @param conditionValue
     *            WHERE子句条件的属性值
     * @param propertyName
     *            UPDATE子句属性数组名称
     * @param propertyValue
     *            UPDATE子句属性数组值
     */
    public <E> void updateByProperties(Class<E> entityClass,
            String conditionName, Object conditionValue,
            String[] propertyName, Object[] propertyValue);

    /**
     * 根据单个属性条件更新对象实体单个属性
     * 
     * @param <E>
     * 
     * @param conditionName
     *            WHERE子句条件的属性名称
     * @param conditionValue
     *            WHERE子句条件的属性值
     * @param propertyName
     *            UPDATE子句属性名称
     * @param propertyValue
     *            UPDATE子句属性值
     */
    public <E> void updateByProperties(Class<E> entityClass,
            String conditionName, Object conditionValue,
            String propertyName, Object propertyValue);

    /**
     * 先删除再插入去更新对象实体
     * 
     * @param <E>
     * 
     * @param entity
     *            待更新的对象实体
     * @param oldId
     *            已存在的对象实体主键
     */
    public <E> void update(E entity, Serializable oldId);

    /**
     * 合并给定的对象实体状态到当前的持久化上下文
     * 
     * @param <E>
     * 
     * @param entity
     *            给定的对象实体
     * @return 返回对象实体
     */
    public <E> E merge(E entity);

    /**
     * 根据ID立即加载持久化对象实体
     * 
     * @param <E>
     * 
     * @param id
     *            ID值
     * @return 返回对象实体
     */
    public <E> E get(Class<E> entityClass, Serializable id);

    /**
     * 根据ID延迟加载持久化对象实体
     * 
     * @param <E>
     * 
     * @param id
     *            ID值
     * @return 返回对象实体
     */
    public <E> E load(Class<E> entityClass, Serializable id);

    /**
     * 根据属性数组获取单个对象实体
     * 
     * @param <E>
     * 
     * @param propName
     *            属性数组名称
     * @param propValue
     *            属性数组值
     * @return 返回对象实体
     */
    public <E> E getByProerties(Class<E> entityClass, String[] propName,
            Object[] propValue);

    /**
     * 根据属性数组和排序条件获取单个对象实体
     * 
     * @param <E>
     * 
     * @param propName
     *            属性数组名称
     * @param propValue
     *            属性数组值
     * @param sortedCondition
     *            排序条件
     * @return 返回对象实体
     */
    public <E> E getByProerties(Class<E> entityClass, String[] propName,
            Object[] propValue,
            Map<String, String> sortedCondition);

    /**
     * 根据属性获取单个对象实体
     * 
     * @param <E>
     * 
     * @param propName
     *            属性名称
     * @param propValue
     *            属性值
     * @return 返回对象实体
     */
    public <E> E getByProerties(Class<E> entityClass, String propName,
            Object propValue);

    /**
     * 根据属性和排序条件获取单个对象实体
     * 
     * @param <E>
     * 
     * @param propName
     *            属性名称
     * @param propValue
     *            属性值
     * @param sortedCondition
     *            排序条件
     * @return 返回对象实体
     */
    public <E> E getByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Map<String, String> sortedCondition);

    /**
     * 根据属性、排序条件和要返回的记录数目获取对象实体列表
     * 
     * @param <E>
     * 
     * @param propName
     *            属性数组名称
     * @param propValue
     *            属性数组值
     * @param sortedCondition
     *            排序条件
     * @param top
     *            要返回的记录数目
     * @return 返回对象实体列表
     */
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Map<String, String> sortedCondition, Integer top);

    /**
     * 根据属性和排序条件获取对象实体列表
     * 
     * @param <E>
     * 
     * @param propName
     *            属性数组名称
     * @param propValue
     *            属性数组值
     * @param sortedCondition
     *            排序条件
     * @return 返回对象实体列表
     */
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Map<String, String> sortedCondition);

    /**
     * 根据属性和要返回的记录数目获取对象实体列表
     * 
     * @param <E>
     * 
     * @param propName
     *            属性数组名称
     * @param propValue
     *            属性数组值
     * @param top
     *            要返回的记录数目
     * @return 返回对象实体列表
     */
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue,
            Integer top);

    /**
     * 根据属性获取对象实体列表
     * 
     * @param <E>
     * 
     * @param propName
     *            属性数组名称
     * @param propValue
     *            属性数组值
     * @return
     */
    public <E> List<E> queryByProerties(Class<E> entityClass,
            String[] propName, Object[] propValue);

    /**
     * 根据属性、排序条件和要返回的记录数目获取对象实体列表
     * @param <E>
     * 
     * @param propName
     *            属性名称
     * @param propValue
     *            属性值
     * @param sortedCondition
     *            排序条件
     * @param top
     *            要返回的记录数目
     * @return 返回对象实体列表
     */
    public <E> List<E> queryByProerties(Class<E> entityClass,String propName, Object propValue,
            Map<String, String> sortedCondition, Integer top);

    /**
     * 根据属性和排序条件获取对象实体列表
     * @param <E>
     * 
     * @param propName
     *            属性名称
     * @param propValue
     *            属性值
     * @param sortedCondition
     *            排序条件
     * @return 返回对象实体列表
     */
    public <E> List<E> queryByProerties(Class<E> entityClass,String propName, Object propValue,
            Map<String, String> sortedCondition);

    /**
     * 根据属性和要返回的记录数目获取对象实体列表
     * @param <E>
     * 
     * @param propName
     *            属性名称
     * @param propValue
     *            属性值
     * @param top
     *            要返回的记录数目
     * @return 返回对象实体列表
     */
    public <E> List<E> queryByProerties(Class<E> entityClass,String propName, Object propValue,
            Integer top);

    /**
     * 根据属性获取对象实体列表
     * @param <E>
     * 
     * @param propName
     *            属性名称
     * @param propValue
     *            属性值
     * @return 返回对象实体列表
     */
    public <E> List<E> queryByProerties(Class<E> entityClass,String propName, Object propValue);

    /**
     * 彻底清除会话
     */
    public void clear();

    /**
     * 从会话缓存中删除此对象实体
     * @param <E>
     * 
     * @param entity
     *            待删除的对象实体
     */
    public <E> void evict(E entity);

    /**
     * 查询出对象实体的所有数目
     * @param <E>
     * 
     * @return 返回对象实体所有数目
     */
    public <E> Long countAll(Class<E> entityClass);

    /**
     * 查询出所有的对象实体列表
     * @param <E>
     * 
     * @return 返回对象实体列表
     */
    public <E> List<E> doQueryAll(Class<E> entityClass);

    /**
     * 根据排序条件和要返回的记录数目查询出对象实体列表
     * @param <E>
     * 
     * @param sortedCondition
     *            排序条件
     * @param top
     *            要返回的记录数目
     * @return 返回对象实体列表
     */
    public <E> List<E> doQueryAll(Class<E> entityClass,Map<String, String> sortedCondition, Integer top);

    /**
     * 根据要返回的记录数目查询出对象实体列表
     * @param <E>
     * 
     * @param top
     *            要返回的记录数目
     * @return 返回对象实体列表
     */
    public <E> List<E> doQueryAll(Class<E> entityClass,Integer top);

    /**
     * 根据各种查询条件返回对象实体数目
     * @param <E>
     * 
     * @param parameter
     *            各种查询条件
     * @return 返回对象实体数目
     */
    public <E> Long doCount(Class<E> entityClass,BaseParameter parameter);

    /**
     * 根据各种查询条件返回对象实体列表
     * @param <E>
     * 
     * @param parameter
     *            各种查询条件
     * @return 返回对象实体列表
     */
    public <E> List<E> doQuery(Class<E> entityClass,BaseParameter parameter);

    /**
     * 根据各种查询条件返回分页列表
     * @param <E>
     * 
     * @param parameter
     *            各种查询条件
     * @return 返回分页列表
     */
    public <E> QueryResult<E> doPaginationQuery(Class<E> entityClass,BaseParameter parameter);

    /**
     * 根据各种查询条件返回分页列表
     * @param <E>
     * 
     * @param parameter
     *            各种查询条件
     * @param bool
     *            默认为true；如果为false，增加属性是否为空等查询条件
     * @return 返回分页列表
     */
    public <E> QueryResult<E> doPaginationQuery(Class<E> entityClass,BaseParameter parameter,
            boolean bool);

    /**
     * 邹猛 getMaxId(这里用一句话描述这个方法的作用) 获取订单号种子id最大值
     * @param <E>
     * 
     * @return Long
     * @exception
     * @since 1.0.0
     */
    public <E> Long getMaxId(Class<E> entityClass);

    /**
     * 邹猛 doQueryByInMethods(这里用一句话描述这个方法的作用) 用in方法的查询
     * @param <E>
     * 
     * @param param
     * @return List<E>
     * @exception
     * @since 1.0.0
     */
    public <E> List<E> doQueryByInMethods(Class<E> entityClass,BaseParameter param);

    public <E> Pager<E> queryByProertiesPage(Class<E> entityClass,String[] propName, Object[] propValue,
            Map<String, String> sortedCondition, int pageNo, int pageSize);

    public <E> void deleteByEntities(String[] propName, Object[] propValue,
            String entities);

    /**
     * 根据属性名数组，排序条件获取指定多个属性
     */
    public <E> Object[] getFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue,
            Map<String, String> sortedCondition);

    /**
     * 根据属性名数组，排序条件获取指定多个属性
     */
    public <E> Object[] getFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue,
            Map<String, String> sortedCondition);

    /**
     * 根据属性名数组，获取指定多个属性
     */
    public <E> Object[] getFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue);

    /**
     * 根据属性名数组，获取指定多个属性
     */
    public <E> Object[] getFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue);

    /**
     * 根据属性数组和排序条件获取多个属性
     * 
     * @param entityClass
     * @param fields
     * @param propName
     * @param propValue
     * @param sortedCondition
     * @return
     */
    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue,
            Map<String, String> sortedCondition);

    /**
     * 根据属性数组获取多个属性
     * 
     * @param entityClass
     * @param fields
     * @param propName
     * @param propValue
     * @return
     */
    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String[] propName, Object[] propValue);

    /**
     * 根据属性和排序条件获取多个属性
     * 
     * @param entityClass
     * @param fields
     * @param propName
     * @param propValue
     * @param sortedCondition
     * @return
     */
    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue,
            Map<String, String> sortedCondition);

    /**
     * 根据属性获取多个属性
     * 
     * @param entityClass
     * @param fields
     * @param propName
     * @param propValue
     * @return
     */
    public <E> List<Object[]> queryFieldsByProperties(Class<E> entityClass,
            String[] fields, String propName, Object propValue);
}
