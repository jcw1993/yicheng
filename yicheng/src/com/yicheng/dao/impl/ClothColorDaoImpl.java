package com.yicheng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yicheng.dao.ClothColorDao;
import com.yicheng.pojo.ClothColor;

@Repository
public class ClothColorDaoImpl extends HibernateDaoBase implements ClothColorDao {

	@Override
	public int create(ClothColor clothColor) {
		return (Integer) getHibernateTemplate().save(clothColor);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new ClothColor(id));
	}

	@SuppressWarnings("unchecked")
	@Override
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
