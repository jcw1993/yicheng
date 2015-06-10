package com.yicheng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yicheng.dao.ClothDao;
import com.yicheng.pojo.Cloth;

@Repository
public class ClothDaoImpl extends HibernateDaoBase implements ClothDao {

	@Override
	public int create(Cloth cloth) {
		return (Integer) getHibernateTemplate().save(cloth);
	}

	@Override
	public void update(Cloth cloth) {
		getHibernateTemplate().update(cloth);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new Cloth(id));
	}

	@SuppressWarnings("unchecked")
	@Override
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
