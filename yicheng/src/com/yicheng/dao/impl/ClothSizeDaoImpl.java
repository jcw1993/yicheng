package com.yicheng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yicheng.dao.ClothSizeDao;
import com.yicheng.pojo.ClothSize;

@Repository
public class ClothSizeDaoImpl extends HibernateDaoBase implements ClothSizeDao {

	@Override
	public int create(ClothSize clothSize) {
		return (Integer) getHibernateTemplate().save(clothSize);
	}

	@Override
	public void update(ClothSize clothSize) {
		getHibernateTemplate().update(clothSize);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new ClothSize(id));
	}
	
	@Override
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
	@Override
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
