package com.xi.common.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

import com.xi.common.util.PaginationSupport;

public interface IBaseDao {

	public Object load(Class clazz, Serializable id) throws DataAccessException;

	public Object get(Class clazz, Serializable id) throws DataAccessException;

	public Object getByLockModeUpgrade(Class clazz, Serializable id) throws DataAccessException;

	public boolean contains(Object t) throws DataAccessException;

	public Serializable save(Object t) throws DataAccessException;

	public void saveOrUpdate(Object t) throws DataAccessException;

	public void saveOrUpdateAll(Collection entities) throws DataAccessException;

	public void update(Object t, LockMode lockMode) throws DataAccessException;

	public void update(Object t) throws DataAccessException;

	public void batchUpdate(String querySql) throws DataAccessException;

	public void batchUpdate(String querySql, Object value) throws DataAccessException;

	public void batchUpdate(String querySql, Object[] values) throws DataAccessException;

	public void delete(Object t, LockMode lockMode) throws DataAccessException;

	public void delete(Object t) throws DataAccessException;

	public void deleteByPKId(Class clazz, Serializable pkid) throws DataAccessException;

	public void deleteAll(Collection entities) throws DataAccessException;

	public List find(String queryString, Object value) throws DataAccessException;

	public List find(String queryString, Object[] values) throws DataAccessException;

	public List find(String queryString) throws DataAccessException;

	public List list(Class claz) throws DataAccessException;

	public List findByNamedQuery(String queryName) throws DataAccessException;

	public List findByNamedQuery(String queryName, Object value) throws DataAccessException;

	public List findByNamedQuery(String queryName, Object[] values) throws DataAccessException;

	public PaginationSupport findPageByCriteria(final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex);

	public PaginationSupport findPageByQuery(final String hql, final int pageSize, final int startIndex);

	public PaginationSupport findPageByQuery(final String hql, final int pageSize, final int startIndex, final String orderByField, final String sortDirection);

	public PaginationSupport findPageByQuery(final String hql, final Object[] values, final int pageSize, final int startIndex, final String orderByField, final String sortDirection);

	public PaginationSupport findPageByQuery(final String hql, final Object[] values, final int pageSize, final int startIndex, final String orderByField, final String sortDirection,final Integer totalLimitSize);
	public PaginationSupport findPageBySQLQuery(final String sql, final int pageSize, final int startIndex);

	public List findBySQL(final String queryString, final Object[] values) throws DataAccessException;
	public List findBySQL(final String queryString) throws DataAccessException;

	public List findTopN(final String hql, final int topN);
	
	public List findByLock(final String hql);
	
	public Boolean executeBySQL(final String queryString) throws DataAccessException;
	
	public Integer calBySql(final String queryString) throws DataAccessException;

}
