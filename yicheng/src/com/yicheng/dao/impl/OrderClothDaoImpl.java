package com.yicheng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.yicheng.dao.OrderClothDao;
import com.yicheng.pojo.OrderCloth;

public class OrderClothDaoImpl extends HibernateDaoBase implements OrderClothDao {

	@Override
	public int create(OrderCloth orderCloth) {
		return (Integer) getHibernateTemplate().save(orderCloth);
	}

	@Override
	public void update(OrderCloth orderCloth) {
		getHibernateTemplate().update(orderCloth);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new OrderCloth(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderCloth> getByOrderNumber(String orderNumber) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("from OrderCloth where orderNumber = " + orderNumber);
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

}
