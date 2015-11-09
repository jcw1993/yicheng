package com.yicheng.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.yicheng.pojo.ClothMaterial;

public class ClothMaterialDao extends HibernateDaoBase {

	public int create(ClothMaterial clothMaterial) {
		return (Integer) getHibernateTemplate().save(clothMaterial);
	}

	public void update(ClothMaterial clothMaterial) {
		getHibernateTemplate().update(clothMaterial);
	}

	public void delete(int id) {
		getHibernateTemplate().delete(new ClothMaterial(id));
	}

	public void deleteByCloth(int clothId) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("delete ClothMaterial where clothId = " + clothId);
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ClothMaterial> getByCloth(int clothId) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("from ClothMaterial where clothId = " + clothId + " order by id asc");
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
