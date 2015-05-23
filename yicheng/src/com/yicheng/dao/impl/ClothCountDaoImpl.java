package com.yicheng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.yicheng.dao.ClothCountDao;
import com.yicheng.pojo.ClothCount;

public class ClothCountDaoImpl extends HibernateDaoBase implements ClothCountDao {

	@Override
	public int create(ClothCount clothCount) {
		return (Integer) getHibernateTemplate().save(clothCount);
	}

	@Override
	public void update(ClothCount clothCount) {
		getHibernateTemplate().update(clothCount);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new ClothCount(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClothCount> getByCloth(int clothId) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("from ClothCount where clothId = " + clothId);
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

}
