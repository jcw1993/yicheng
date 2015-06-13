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
	public int create(ClothSize clothCount) {
		return (Integer) getHibernateTemplate().save(clothCount);
	}

	@Override
	public void update(ClothSize clothCount) {
		getHibernateTemplate().update(clothCount);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new ClothSize(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClothSize> getByCloth(int clothId) {
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
