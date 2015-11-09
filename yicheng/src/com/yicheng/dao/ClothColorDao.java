package com.yicheng.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.yicheng.pojo.ClothColor;

public class ClothColorDao extends HibernateDaoBase {

	public int create(ClothColor clothColor) {
		return (Integer) getHibernateTemplate().save(clothColor);
	}

	public void delete(int id) {
		getHibernateTemplate().delete(new ClothColor(id));
	}

	public void deleteByCloth(int clothId) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("delete ClothColor where clothId = " + clothId);
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ClothColor> getByCloth(int clothId) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("from ClothColor where clothId = " + clothId + " order by id asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally {
			session.close();
		}
		return null;
	}
	

}
