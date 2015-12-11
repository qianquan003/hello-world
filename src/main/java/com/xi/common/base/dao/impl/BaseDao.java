package com.xi.common.base.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import com.xi.common.base.dao.IBaseDao;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xi.common.util.PaginationSupport;

public class BaseDao extends HibernateDaoSupport implements IBaseDao
{

	private static Logger logger = Logger.getLogger(BaseDao.class.getName());

	@Resource(name = "sessionFactory")
	public void setInjectionSessionFacotry(SessionFactory sessionFacotry)
	{
		super.setSessionFactory(sessionFacotry);
	}

	public Object load(Class clazz, Serializable id) throws DataAccessException
	{
		Object load = getHibernateTemplate().load(clazz, id);
		return load;
	}

	public Object get(Class clazz, Serializable id) throws DataAccessException
	{
		Object get = getHibernateTemplate().get(clazz, id);

		return get;
	}

	public Object getByLockModeUpgrade(Class clazz, Serializable id) throws DataAccessException
	{
		Object get = getHibernateTemplate().get(clazz, id, LockMode.UPGRADE);

		return get;
	}

	public List list(Class clazz) throws DataAccessException
	{
		return getHibernateTemplate().loadAll(clazz);

	}

	public boolean contains(Object t) throws DataAccessException
	{
		return getHibernateTemplate().contains(t);
	}

	public void delete(Object t, LockMode lockMode) throws DataAccessException
	{
		getHibernateTemplate().delete(t, lockMode);
	}

	public void delete(Object t) throws DataAccessException
	{
		getHibernateTemplate().delete(t);
	}

	public void deleteByPKId(Class clazz, Serializable pkid) throws DataAccessException
	{
		Object ob = get(clazz, pkid);
		delete(ob);
	}

	public void deleteAll(Collection entities) throws DataAccessException
	{
		getHibernateTemplate().deleteAll(entities);
	}

	public List find(String queryString, Object value) throws DataAccessException
	{
		List find = getHibernateTemplate().find(queryString, value);
		return find;
	}

	public List find(String queryString, Object[] values) throws DataAccessException
	{
		List find = getHibernateTemplate().find(queryString, values);
		return find;
	}

	public List find(String queryString) throws DataAccessException
	{
		return getHibernateTemplate().find(queryString);
	}

	public Serializable save(Object t) throws DataAccessException
	{
		return getHibernateTemplate().save(t);
	}

	
	public void saveOrUpdate(Object t) throws DataAccessException
	{
		getHibernateTemplate().saveOrUpdate(t);
	}

	/**
	 * 本方法暂时不推荐使用
	 * @deprecated
	 */
	public void saveOrUpdateAll(Collection entities) throws DataAccessException
	{
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public void update(Object t, LockMode lockMode) throws DataAccessException
	{
		getHibernateTemplate().update(t, lockMode);
	}

	public void update(Object t) throws DataAccessException
	{
		t = getHibernateTemplate().merge(t);

		getHibernateTemplate().update(t);
	}

	public void batchUpdate(String querySql) throws DataAccessException
	{
		getHibernateTemplate().bulkUpdate(querySql);
	}

	public void batchUpdate(String querySql, Object value) throws DataAccessException
	{
		getHibernateTemplate().bulkUpdate(querySql, value);
	}

	public void batchUpdate(String querySql, Object[] values) throws DataAccessException
	{
		getHibernateTemplate().bulkUpdate(querySql, values);
	}

	public List findByNamedQuery(String queryName) throws DataAccessException
	{
		return getHibernateTemplate().findByNamedQuery(queryName);
	}

	public List findByNamedQuery(String queryName, Object value) throws DataAccessException
	{
		return getHibernateTemplate().findByNamedQuery(queryName, value);
	}

	public List findByNamedQuery(String queryName, Object[] values) throws DataAccessException
	{
		return getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	public PaginationSupport findPageByCriteria(final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex)
	{
		return (PaginationSupport) getHibernateTemplate().executeWithNativeSession(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException
			{
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				int totalCount = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
				criteria.setProjection(null);
				List items = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
				PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
				return ps;
			}
		});
	}

	public PaginationSupport findPageByQuery(final String hsql, final int pageSize, final int startIndex)
	{
		return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hsql);
				int totalCount = query.list().size();
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				List items = query.list();
				PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
				return ps;

			}
		});
	}
	
	public PaginationSupport findPageByQuery(final String hsql, final int pageSize, final int startIndex,final Integer totalLimit)
	{
		return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hsql);
				int totalCount = query.list().size();
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				List items = query.list();
				if(null!=totalLimit&&totalLimit>0&&totalCount>totalLimit){
					totalCount = totalLimit;
				}
				PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
				return ps;

			}
		});
	}

	public PaginationSupport findPageByQuery(final String hql, final int pageSize, final int startIndex, final String orderByField, final String sortDirection)
	{
		return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback()
		{

			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				StringBuilder sb = new StringBuilder("");
				sb.append(hql);

				if(orderByField != null && !"".equals(orderByField.trim()))
				{
					sb.append(" ORDER BY ").append(orderByField);
				}

				if(sortDirection != null && !"".equals(sortDirection.trim()) && (sortDirection.equalsIgnoreCase("ASC") || sortDirection.equalsIgnoreCase("DESC")))
				{
					sb.append(" ").append(sortDirection);
				}

				Query query = session.createQuery(sb.toString());
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				List items = query.list();

				String queryString = "";
				String qs2 = sb.toString().toLowerCase();
				String a = "";
				if(qs2.indexOf(" group ") > 0 && qs2.indexOf(" by ") > qs2.indexOf(" group "))
				{
					a = sb.toString();
				}
				else
				{
					int j = qs2.toUpperCase().indexOf(" ORDER");
					a = sb.substring(0, j);
				}
				if(a.toUpperCase().indexOf("SELECT") != -1)
				{
					int i = query.getQueryString().toUpperCase().indexOf("FROM");
					queryString = "SELECT Count(*) " + a.substring(i, a.length());
				}
				else
				{
					queryString = "SELECT Count(*) " + a;

				}

				Query query2 = session.createQuery(queryString);

				// int totalCount = ((Long) query2.iterate().next()).intValue();

				int totalCount = 0;
				List countList = query2.list();
				if(countList != null && countList.size() > 0)
				{
					if(countList.size() == 1)
					{
						String qs = queryString.toLowerCase();
						if(qs.indexOf(" group ") > 0 && qs.indexOf(" by ") > qs.indexOf(" group "))
						{
							totalCount = countList.size();
						}
						else
						{
							totalCount = ((Long) countList.get(0)).intValue();
						}
					}
					else
					{
						totalCount = countList.size();
					}
				}
				PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
				return ps;

			}
		});
	}

	public PaginationSupport findPageByQuery(final String hql, final Object[] values, final int pageSize, final int startIndex, final String orderByField, final String sortDirection)
	{
		return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback()
		{

			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				StringBuilder sb = new StringBuilder("");
				sb.append(hql);

				if(orderByField != null && !"".equals(orderByField.trim()))
				{
					sb.append(" ORDER BY ").append(orderByField);
				}

				if(sortDirection != null && !"".equals(sortDirection.trim()) && (sortDirection.equalsIgnoreCase("ASC") || sortDirection.equalsIgnoreCase("DESC")))
				{
					sb.append(" ").append(sortDirection);
				}

				Query query = session.createQuery(sb.toString());
				for(int i = 0; i < values.length; i++)
				{
					query.setParameter(i, values[i]);
				}
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				List items = query.list();

				String queryString = "";
				String qs2 = sb.toString().toLowerCase();
				String a = "";
				if(qs2.indexOf(" group ") > 0 && qs2.indexOf(" by ") > qs2.indexOf(" group "))
				{
					a = sb.toString();
				}
				else
				{
					int j = qs2.toUpperCase().indexOf(" ORDER");
					a = sb.substring(0, j);
				}
				if(a.toUpperCase().indexOf("SELECT") != -1)
				{
					int i = query.getQueryString().toUpperCase().indexOf("FROM");
					queryString = "SELECT Count(*) " + a.substring(i, a.length());
				}
				else
				{
					queryString = "SELECT Count(*) " + a;

				}

				Query query2 = session.createQuery(queryString);
				for(int i = 0; i < values.length; i++)
				{
					query2.setParameter(i, values[i]);
				}

				// int totalCount = ((Long) query2.iterate().next()).intValue();

				int totalCount = 0;
				List countList = query2.list();
				if(countList != null && countList.size() > 0)
				{
					if(countList.size() == 1)
					{
						String qs = queryString.toLowerCase();
						if(qs.indexOf(" group ") > 0 && qs.indexOf(" by ") > qs.indexOf(" group "))
						{
							totalCount = countList.size();
						}
						else
						{
							totalCount = ((Long) countList.get(0)).intValue();
						}
					}
					else
					{
						totalCount = countList.size();
					}
				}

				PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
				return ps;

			}
		});
	}

	public PaginationSupport findPageBySQLQuery(final String sql, final int pageSize, final int startIndex)
	{
		return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback()
		{

			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(sql);
				int totalCount = query.list().size();
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				List items = query.list();
				PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
				return ps;

			}
		});
	}

	public List findBySQL(final String queryString, final Object[] values) throws DataAccessException
	{

		return (List) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryString);
				for(int i = 0; i < values.length; i++)
				{
					query.setParameter(i, values[i]);
				}
				List list = query.list();
				return list;
			};
		});

	}
	
	public List findBySQLRtMap(final String queryString,final Object ...param) throws DataAccessException
	{

		return (List) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryString);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
				for(int i = 0; i < param.length; i++)
				{
					query.setParameter(i, param[i]);
				}
				List list = query.list();
				return list;
			};
		});

	}

	public List findBySQL(final String queryString) throws DataAccessException
	{

		return (List) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryString);

				List list = query.list();
				return list;
			};
		});

	}

	public List findTopN(final String hql, final int topN)
	{
		return (List) getHibernateTemplate().executeFind(new HibernateCallback()
		{

			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(topN);
				List items = query.list();
				return items;
			}
		});
	}

	public List findTopN(final String hql, final int topN, final int start)
	{
		return (List) getHibernateTemplate().executeFind(new HibernateCallback()
		{

			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				query.setMaxResults(topN);
				query.setFirstResult(start);
				List items = query.list();
				return items;
			}
		});
	}

	public String list2Ids(List<Integer> idList)
	{
		StringBuffer sb = new StringBuffer();
		for(Integer id : idList)
		{
			sb.append(id);
			sb.append(',');
		}
		return " in (" + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")";
	}
	
	//????SELECT FOR UPDATE ?????
	public List findByLock(final String hql)
	{
		return (List) getHibernateTemplate().executeFind(new HibernateCallback()
		{

			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				String tableAlias = "";
				String[] arr = hql.split(" ");
				for(int i=0; i < arr.length;i++){
					if("from".equalsIgnoreCase(arr[i])){
						tableAlias = arr[i+1];
						break;
					}
				}
				Query query = session.createQuery(hql);
				query.setLockMode(tableAlias.toLowerCase(),LockMode.UPGRADE);
				List items = query.list();
				return items;
			}
		});
	}
	
	public PaginationSupport findPageByQuery(final String hql,final Object[] values, final int pageSize, final int startIndex, final String orderByField, final String sortDirection,final Integer totalLimitSize)
	{
		return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback()
		{

			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				StringBuilder sb = new StringBuilder("");
				sb.append(hql);

				if(orderByField != null && !"".equals(orderByField.trim()))
				{
					sb.append(" ORDER BY ").append(orderByField);
				}

				if(sortDirection != null && !"".equals(sortDirection.trim()) && (sortDirection.equalsIgnoreCase("ASC") || sortDirection.equalsIgnoreCase("DESC")))
				{
					sb.append(" ").append(sortDirection);
				}

				Query query = session.createQuery(sb.toString());
				for(int i = 0; i < values.length; i++)
				{
					query.setParameter(i, values[i]);
				}
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				List items = query.list();

				String queryString = "";
				String qs2 = sb.toString().toLowerCase();
				String a = "";
				if(qs2.indexOf(" group ") > 0 && qs2.indexOf(" by ") > qs2.indexOf(" group "))
				{
					a = sb.toString();
				}
				else
				{
					int j = qs2.toUpperCase().indexOf(" ORDER");
					a = sb.substring(0, j);
				}
				if(a.toUpperCase().indexOf("SELECT") != -1)
				{
					int i = query.getQueryString().toUpperCase().indexOf("FROM");
					queryString = "SELECT Count(*) " + a.substring(i, a.length());
				}
				else
				{
					queryString = "SELECT Count(*) " + a;

				}

				Query query2 = session.createQuery(queryString);
				for(int i = 0; i < values.length; i++)
				{
					query2.setParameter(i, values[i]);
				}

				// int totalCount = ((Long) query2.iterate().next()).intValue();

				int totalCount = 0;
				List countList = query2.list();
				if(countList != null && countList.size() > 0)
				{
					if(countList.size() == 1)
					{
						String qs = queryString.toLowerCase();
						if(qs.indexOf(" group ") > 0 && qs.indexOf(" by ") > qs.indexOf(" group "))
						{
							totalCount = countList.size();
						}
						else
						{
							totalCount = ((Long) countList.get(0)).intValue();
						}
					}
					else
					{
						totalCount = countList.size();
					}
				}
				if(null!=totalLimitSize&&totalLimitSize>0&&totalCount>totalLimitSize){
					totalCount = totalLimitSize;
				}
				PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
				return ps;

			}
		});
	}
	
	public Boolean executeBySQL(final String queryString) throws DataAccessException
	{

		return (Boolean) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryString);
				query.executeUpdate();
				return true;
			};
		});

	}
	
	public Integer calBySql(final String queryString) throws DataAccessException
	{

		return (Integer)getHibernateTemplate().execute(new HibernateCallback()
		{
			public Integer doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryString);
				return query.executeUpdate();
			};
		});

	}
	
	 /** 分页查询指定类的满足条件的持久化对象 */
    public List findByHql(String hql, int start, int limit) {
        final int pStart = start;
        final int pLimit = limit;
        final String hql1 = hql;
        return getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException{
                Query query = session.createQuery(hql1);
                query.setMaxResults(pLimit);
                query.setFirstResult(pStart);
                List result = query.list();
                if (!Hibernate.isInitialized(result))Hibernate.initialize(result);
                return result;
            }
        });    
    }

}
