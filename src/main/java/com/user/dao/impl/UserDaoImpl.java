package com.user.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.user.dao.intf.UserDao;
import com.user.dto.UserDto;
import com.user.model.User;
import com.user.page.Page;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
	
	@Override
	public void add(User user) {
		getHibernateTemplate().save(user);
	}

	@Override
	public void delete(String idStr) {
		String[] ids = idStr.split(",");
		
        final String queryString = "delete User where id in (:ids) ";
        
        getHibernateTemplate().execute(new HibernateCallback<Object>() {
        	@Override
        	public Object doInHibernate(Session session) throws HibernateException {
        		@SuppressWarnings("rawtypes")
				Query query = session.createQuery(queryString);
        		query.setParameterList("ids", ids);	
        		query.executeUpdate();
        		return null;
        	}
		});
	}

	@Override
	public void update(User user) {
		System.out.println(user);
//		getHibernateTemplate().update(user);
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				session.update(user);
				return null;
			}
		});
	}

	@Override
	public List<User> query(Page<UserDto> page) {
		return getHibernateTemplate().execute(new HibernateCallback<List<User>>() {
			@Override
			public List<User> doInHibernate(Session session) throws HibernateException {
				StringBuilder hql = new StringBuilder("FROM User WHERE 1=1");
				UserDto userDto = page.getT();
				if(userDto != null){
					if(userDto.getCondition() != null && !userDto.getCondition().isEmpty()){
						if("id".equals(userDto.getConditionType())){
							hql.append(" AND id=:id");
						}else if("username".equals(userDto.getConditionType())){
							hql.append(" AND username=:username");
						}
					}
					if(userDto.getSex() != null && !userDto.getSex().isEmpty()){
						hql.append(" AND sex=:sex");
					}
					if(userDto.getMaxAge() != null && !userDto.getMaxAge().isEmpty()){
						hql.append(" AND age<=:maxAge");
					}
					if(userDto.getMinAge() != null && !userDto.getMinAge().isEmpty()){
						hql.append(" AND age>=:minAge");
					}
				}
				Query<User> query = session.createQuery(hql.toString(),User.class);
				query.setFirstResult(page.getStartIndex());
				query.setMaxResults(Page.MAXCOUNT);
				if(userDto != null){
					if(userDto.getCondition() != null && !userDto.getCondition().isEmpty()){
						if("id".equals(userDto.getConditionType())){
							query.setParameter("id", userDto.getCondition());
						}else if("username".equals(userDto.getConditionType())){
							query.setParameter("username", userDto.getCondition());
						}
					}
					if(userDto.getSex() != null && !userDto.getSex().isEmpty()){
						query.setParameter("sex", Integer.parseInt(userDto.getSex()));
					}
					if(userDto.getMaxAge() != null && !userDto.getMaxAge().isEmpty()){
						query.setParameter("maxAge", Integer.parseInt(userDto.getMaxAge()));
					}
					if(userDto.getMinAge() != null && !userDto.getMinAge().isEmpty()){
						query.setParameter("minAge", Integer.parseInt(userDto.getMinAge()));
					}
				}
				return query.list();
			}
		});
	}

	@Override
	public int count(UserDto userDto) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				StringBuilder hql = new StringBuilder("SELECT count(*) FROM User WHERE 1=1");
				if(userDto != null){
					if(userDto.getCondition() != null && !userDto.getCondition().isEmpty()){
						if("id".equals(userDto.getConditionType())){
							hql.append(" AND id=:id");
						}else if("username".equals(userDto.getConditionType())){
							hql.append(" AND username=:username");
						}
					}
					if(userDto.getSex() != null && !userDto.getSex().isEmpty()){
						hql.append(" AND sex=:sex");
					}
					if(userDto.getMaxAge() != null && !userDto.getMaxAge().isEmpty()){
						hql.append(" AND age<=:maxAge");
					}
					if(userDto.getMinAge() != null && !userDto.getMinAge().isEmpty()){
						hql.append(" AND age>=:minAge");
					}
				}
				@SuppressWarnings("rawtypes")
				Query query = session.createQuery(hql.toString());
				if(userDto != null){
					if(userDto.getCondition() != null && !userDto.getCondition().isEmpty()){
						if("id".equals(userDto.getConditionType())){
							query.setParameter("id", userDto.getCondition());
						}else if("username".equals(userDto.getConditionType())){
							query.setParameter("username", userDto.getCondition());
						}
					}
					if(userDto.getSex() != null && !userDto.getSex().isEmpty()){
						query.setParameter("sex", Integer.parseInt(userDto.getSex()));
					}
					if(userDto.getMaxAge() != null && !userDto.getMaxAge().isEmpty()){
						query.setParameter("maxAge", Integer.parseInt(userDto.getMaxAge()));
					}
					if(userDto.getMinAge() != null && !userDto.getMinAge().isEmpty()){
						query.setParameter("minAge", Integer.parseInt(userDto.getMinAge()));
					}
				}
				long count = (long)query.uniqueResult();
				return (int)count;
			}
		});
	}

	@Override
	public User queryById(String id) {
//		String hql1 = "FROM User WHERE id = ?";
//		List<?> user = getHibernateTemplate().find(hql1, new Object[]{id});
		return getHibernateTemplate().execute(new HibernateCallback<User>() {
			@Override
			public User doInHibernate(Session session) throws HibernateException {
				String hql = "FROM User WHERE id =:id";
				Query<User> query = session.createQuery(hql,User.class);
				query.setParameter("id", id);
				return query.uniqueResult();
			}
		});
	}
	
	@Override
	public boolean exist(User user){
		System.out.println(user);
		String hql = "FROM User WHERE username =:username AND password =:password";
		User loginer = getHibernateTemplate().execute(new HibernateCallback<User>() {
			@Override
			public User doInHibernate(Session session) throws HibernateException {
				Query<User> query = session.createQuery(hql, User.class);
				query.setParameter("username", user.getUsername());
				query.setParameter("password", user.getPassword());
				User user = query.uniqueResult();
				return user;
			}
		});
		return loginer != null ? true : false;
	}

	@Override
	public boolean checkName(String username) {
		User register = getHibernateTemplate().execute(new HibernateCallback<User>() {
			@Override
			public User doInHibernate(Session session) throws HibernateException {
				String hql = "FROM User WHERE username =:username";
				Query<User> query = session.createQuery(hql,User.class);
				query.setParameter("username", username);
				return query.uniqueResult();
			}
		});
		return register != null ? true : false;
	}

	@Override
	public int dayCount(String date) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				String hql = "SELECT count(*) FROM User WHERE id like '%" + date + "%'";
				@SuppressWarnings("rawtypes")
				Query query = session.createQuery(hql);
				long count = (long)query.uniqueResult();
				return (int)count;
			}
		});
	}
}
