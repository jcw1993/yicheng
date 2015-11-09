package com.yicheng.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.yicheng.pojo.ClothSize;

public class ClothSizeDao extends HibernateDaoBase {

	public int create(ClothSize clothSize) {
		return (Integer) getHibernateTemplate().save(clothSize);
	}

	public void update(ClothSize clothSize) {
		getHibernateTemplate().update(clothSize);
	}

	public void delete(int id) {
		getHibernateTemplate().delete(new ClothSize(id));
	}
	
	public void deleteByCloth(int clothId) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("delete ClothSize where clothId = " + clothId);
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<ClothSize> getByCloth(int clothId) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("from ClothSize where clothId = " + clothId + "order by sizeType asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

}
