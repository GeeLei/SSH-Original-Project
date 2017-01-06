package core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huanke.sshshell.bean.Pager;

import core.dao.Dao;
import core.support.BaseParameter;
import core.support.QueryResult;

@Transactional
public class BaseService<E> implements Service<E> {

    protected final Logger log = Logger.getLogger(BaseService.class);

    protected Dao<E> dao;

    public Serializable persist(E entity) {
        return this.dao.persist(entity);
    }

    public boolean deleteByPK(Serializable... id) {
        return this.dao.deleteByPK(id);
    }

    public void delete(E entity) {
        this.dao.delete(entity);
    }

    public void deleteByProperties(String[] propName, Object[] propValue) {
        this.dao.deleteByProperties(propName, propValue);
    }

    public void deleteByProperties(String propName, Object propValue) {
        this.dao.deleteByProperties(propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public E get(Serializable id) {
        return this.dao.get(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public E getByProerties(String[] propName, Object[] propValue) {
        return this.dao.getByProerties(propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public E getByProerties(String propName, Object propValue) {
        return this.dao.getByProerties(propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public E getByProerties(String[] propName, Object[] propValue,
            Map<String, String> sortedCondition) {
        return this.dao.getByProerties(propName, propValue, sortedCondition);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public E getByProerties(String propName, Object propValue,
            Map<String, String> sortedCondition) {
        return this.dao.getByProerties(propName, propValue, sortedCondition);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public E load(Serializable id) {
        return this.dao.load(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> queryByProerties(String[] propName, Object[] propValue) {
        return this.dao.queryByProerties(propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> queryByProerties(String propName, Object propValue) {
        return this.dao.queryByProerties(propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> queryByProerties(String[] propName, Object[] propValue,
            Map<String, String> sortedCondition) {
        return this.dao.queryByProerties(propName, propValue, sortedCondition);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> queryByProerties(String propName, Object propValue,
            Map<String, String> sortedCondition) {
        return this.dao.queryByProerties(propName, propValue, sortedCondition);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> queryByProerties(String[] propName, Object[] propValue,
            Map<String, String> sortedCondition, Integer top) {
        return this.dao.queryByProerties(propName, propValue, sortedCondition,
                top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> queryByProerties(String[] propName, Object[] propValue,
            Integer top) {
        return this.dao.queryByProerties(propName, propValue, top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> queryByProerties(String propName, Object propValue,
            Map<String, String> sortedCondition, Integer top) {
        return this.dao.queryByProerties(propName, propValue, sortedCondition,
                top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> queryByProerties(String propName, Object propValue,
            Integer top) {
        return this.dao.queryByProerties(propName, propValue, top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> queryByProertiesByPage(String[] propName,
            Object[] propValue, Map<String, String> sortedCondition,
            Integer firstIndex, Integer count) {
        return this.dao.queryByProertiesByPage(propName, propValue,
                sortedCondition, firstIndex, count);
    }

    public E merge(E entity) {
        return this.dao.merge(entity);
    }

    public void update(E entity) {
        this.dao.update(entity);
    }

    public void updateByProperties(String[] conditionName,
            Object[] conditionValue, String[] propertyName,
            Object[] propertyValue) {
        this.dao.updateByProperties(conditionName, conditionValue,
                propertyName, propertyValue);
    }

    public void updateByProperties(String conditionName, Object conditionValue,
            String[] propertyName, Object[] propertyValue) {
        this.dao.updateByProperties(conditionName, conditionValue,
                propertyName, propertyValue);
    }

    public void updateByProperties(String[] conditionName,
            Object[] conditionValue, String propertyName, Object propertyValue) {
        this.dao.updateByProperties(conditionName, conditionValue,
                propertyName, propertyValue);
    }

    public void updateByProperties(String conditionName, Object conditionValue,
            String propertyName, Object propertyValue) {
        this.dao.updateByProperties(conditionName, conditionValue,
                propertyName, propertyValue);
    }

    public void update(E entity, Serializable oldId) {
        this.dao.update(entity, oldId);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> doQueryAll() {
        return this.dao.doQueryAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top) {
        return this.dao.doQueryAll(sortedCondition, top);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> doQueryAll(Integer top) {
        return this.dao.doQueryAll(top);
    }

    public void evict(E entity) {
        this.dao.evict(entity);
    }

    public void clear() {
        this.dao.clear();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Long countAll() {
        return this.dao.countAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Long doCount(BaseParameter parameter) {
        return this.dao.doCount(parameter);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> doQuery(BaseParameter parameter) {
        return this.dao.doQuery(parameter);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public QueryResult<E> doPaginationQuery(BaseParameter parameter) {
        return this.dao.doPaginationQuery(parameter);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public QueryResult<E> doPaginationQuery(BaseParameter parameter,
            boolean bool) {
        return this.dao.doPaginationQuery(parameter, bool);
    }

    /**
     * 邹猛 getMaxId(这里用一句话描述这个方法的作用) 获取订单号种子id最大值
     * 
     * @return Long
     * @exception
     * @since 1.0.0
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Long getMaxId() {
        return this.dao.getMaxId();
    }

    /**
     * 邹猛 doQueryByInMethods(这里用一句话描述这个方法的作用) 用in方法的查询
     * 
     * @param param
     * @return List<E>
     * @exception
     * @since 1.0.0
     */
    public List<E> doQueryByInMethods(BaseParameter param) {
        return this.dao.doQueryByInMethods(param);
    }

    @Override
    public Pager<E> queryByProertiesPage(String[] propName, Object[] propValue,
            Map<String, String> sortedCondition, int pageNo, int pageSize) {
        return this.dao.queryByProertiesPage(propName, propValue,
                sortedCondition, pageNo, pageSize);
    }

    public void deleteByEntities(String[] propName, Object[] propValue,
            String entities) {
        this.dao.deleteByEntities(propName, propValue, entities);
    }
}
