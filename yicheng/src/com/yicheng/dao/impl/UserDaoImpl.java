package com.yicheng.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yicheng.dao.UserDao;
import com.yicheng.pojo.User;

@Repository
public class UserDaoImpl extends HibernateDaoBase implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		return getHibernateTemplate().find("from User");
	}

	@Override
	public int create(User user) {
		return (Integer) getHibernateTemplate().save(user);
	}

	@Override
	public void update(User user) {
		getHibernateTemplate().update(user);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new User(id));
	}

}
