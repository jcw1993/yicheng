package com.yicheng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yicheng.dao.ClothMaterialDao;
import com.yicheng.pojo.ClothMaterial;

@Repository
public class ClothMaterialDaoImpl extends HibernateDaoBase implements ClothMaterialDao {

	@Override
	public int create(ClothMaterial clothMaterial) {
		return (Integer) getHibernateTemplate().save(clothMaterial);
	}

	@Override
	public void update(ClothMaterial clothMaterial) {
		getHibernateTemplate().update(clothMaterial);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new ClothMaterial(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClothMaterial> getByCloth(int clothId) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("from ClothMaterial where clothId = " + clothId);
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

}
