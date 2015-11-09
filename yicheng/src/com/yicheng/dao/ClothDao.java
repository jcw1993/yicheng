package com.yicheng.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.yicheng.pojo.Cloth;

public class ClothDao extends HibernateDaoBase {

	public int create(Cloth cloth) {
		return (Integer) getHibernateTemplate().save(cloth);
	}

	public void update(Cloth cloth) {
		getHibernateTemplate().update(cloth);
	}

	public void delete(int id) {
		getHibernateTemplate().delete(new Cloth(id));
	}

	@SuppressWarnings("unchecked")
	public List<Cloth> getAll() {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("from Cloth order by createdTime desc, id desc");
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
