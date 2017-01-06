package core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.huanke.sshshell.bean.Pager;

import core.support.BaseParameter;
import core.support.QueryResult;

public interface Dao {

    public Session getSession();

    /**
     * 彻底清除会话
     */
    public void clear();

    /**
     * 查询出对象实体的所有数目
     * 
     * @param <E>
     * 
     * @return 返回对象实体所有数目
     */
    public <E> Long countAll(Class<E> entityClass);

    /**
     * 删除对象实体
     * @param <E>
     * 
     * @param entity
     *            对象实体
     */
    public <E> void delete(E entity);

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
     * 根据各种查询条件返回对象实体数目
     * 
     * @param <E>
     * 
     * @param parameter
     *            各种查询条件
     * @return 返回对象实体数目
     */
    public <E> Long doCount(Class<E> entityClass, BaseParameter parameter);

    /**
     * 根据各种查询条件返回分页列表
     * 
     * @param <E>
     * 
     * @param parameter
     *            各种查询条件
     * @return 返回分页列表
     */
    public <E> QueryResult<E> doPaginationQuery(Class<E> entityClass,
            BaseParameter parameter);

    /**
     * 根据各种查询条件返回分页列表
     * 
     * @param <E>
     * 
     * @param parameter
     *            各种查询条件
     * @param bool
     *            默认为true；如果为false，增加属性是否为空等查询条件
     * @return 返回分页列表
     */
    public <E> QueryResult<E> doPaginationQuery(Class<E> entityClass,
            BaseParameter parameter, boolean bool);

    /**
     * 根据各种查询条件返回对象实体列表
     * 
     * @param <E>
     * 
     * @param parameter
     *            各种查询条件
     * @return 返回对象实体列表
     */
    public <E> List<E> doQuery(Class<E> entityClass, BaseParameter parameter);

    /**
     * 查询出所有的对象实体列表
     * 
     * @param <E>
     * 
     * @return 返回对象实体列表
     */
    public <E> List<E> doQueryAll(Class<E> entityClass);

    /**
     * 根据要返回的记录数目查询出对象实体列表
     * 
     * @param <E>
     * 
     * @param top
     *            要返回的记录数目
     * @return 返回对象实体列表
     */
    public <E> List<E> doQueryAll(Class<E> entityClass, Integer top);

    /**
     * 根据排序条件和要返回的记录数目查询出对象实体列表
     * 
     * @param <E>
     * 
     * @param sortedCondition
     *            排序条件
     * @param top
     *            要返回的记录数目
     * @return 返回对象实体列表
     */
    public <E> List<E> doQueryAll(Class<E> entityClass,
            Map<String, String> sortedCondition, Integer top);

    /**
     * 邹猛 doQueryByInMethods(这里用一句话描述这个方法的作用) 用in方法的查询
     * 
     * @param <E>
     * 
     * @param param
     * @return List<E>
     * @exception @since 1.0.0
     */
    public <E> List<E> doQueryByInMethods(Class<E> entityClass,
            BaseParameter param);

    /**
     * 从会话缓存中删除此对象实体
     * 
     * @param <E>
     * 
     * @param entity
     *            待删除的对象实体
     */
    public <E> void evict(E entity);

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
     * 邹猛 getMaxId(这里用一句话描述这个方法的作用) 获取订单号种子id最大值
     * 
     * @param <E>
     * 
     * @return Long
     * @exception @since 1.0.0
     */
    public <E> Long getMaxId(Class<E> entityClass);

    /**
     * 根据查询条件获取相应表的数据 KevinCui
     * 
     * @param tableName
     *            表名
     * @param columns
     *            列名（如果有多个，则中间用英文半角逗号分隔）
     * @param condition
     *            查询条件片断
     * @return 操作成功返回集合，否则返回Null。
     */
    public List<Map<String, Object>> getObjectList(String tableName,
            String columns, String condition);

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
     * 根据属性获取对象实体列表
     * 
     * @param <E>
     * 
     * @param propName
     *            属性名称
     * @param propValue
     *            属性值
     * @return 返回对象实体列表
     */
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue);

    /**
     * 根据属性和要返回的记录数目获取对象实体列表
     * 
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
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Integer top);

    /**
     * 根据属性和排序条件获取对象实体列表
     * 
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
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Map<String, String> sortedCondition);

    /**
     * 根据属性、排序条件和要返回的记录数目获取对象实体列表
     * 
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
    public <E> List<E> queryByProerties(Class<E> entityClass, String propName,
            Object propValue,
            Map<String, String> sortedCondition, Integer top);

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
     * 根据属性、排序条件和要返回的记录数目分页获取对象实体列表(使用hql方法)
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
    public <E> List<E> queryByProertiesByPage(Class<E> entityClass,
            String[] propName,
            Object[] propValue, Map<String, String> sortedCondition,
            Integer firstIndex, Integer count);

    public <E> Pager<E> queryByProertiesPage(Class<E> entityClass,
            String[] propName,
            Object[] propValue,
            Map<String, String> sortedCondition, int pageNo, int pageSize);

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
            String conditionName, Object conditionValue, String propertyName,
            Object propertyValue);

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
     * zm 多model删除 deleteByEntities
     * 
     * @param <E>
     * 
     * @param propName
     * @param propValue
     * @param hql
     *            void
     * @exception
     * @since 1.0.0
     */
    public <E> void deleteByEntities(String[] propName, Object[] propValue,
            String entities);

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
    public Long callProcedure(String types, String procedureName);

    // 查询+分页
    public List executeQueryByPage(String hql, Object[] parameters,
            int pageNum, int PagesSize);

    public List executeQuery(String hql, Object[] parameters);

    /**
     * 执行sql语句
     * 
     * @param hql
     */
    public void executeHql(String hql, Object[] parameters);

    // sql原生查询
    public void executeSql(String sql, Object[] parameters);
}
