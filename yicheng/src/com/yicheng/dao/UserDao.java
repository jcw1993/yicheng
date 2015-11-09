package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.User;

public class UserDao extends HibernateDaoBase {

	@SuppressWarnings("unchecked")
	public List<User> getAll() {
		List<User> userList = getHibernateTemplate().find("from User");
		return userList;
	}

	public int create(User user) {
		return (Integer) getHibernateTemplate().save(user);
	}

	public void update(User user) {
		getHibernateTemplate().update(user);
	}

	public void delete(int id) {
		getHibernateTemplate().delete(new User(id));
	}

}
