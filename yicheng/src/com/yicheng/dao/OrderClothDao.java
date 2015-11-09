package com.yicheng.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.yicheng.pojo.OrderCloth;

public class OrderClothDao extends HibernateDaoBase {

	public int create(OrderCloth orderCloth) {
		return (Integer) getHibernateTemplate().save(orderCloth);
	}

	public void update(OrderCloth orderCloth) {
		getHibernateTemplate().update(orderCloth);
	}

	public void delete(int id) {
		getHibernateTemplate().delete(new OrderCloth(id));
	}

	@SuppressWarnings("unchecked")
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

	public OrderCloth getFirstByCloth(int clothId) {
		Session session = super.getSession(true);
		Query query = null;
		try {
			query = session.createQuery("from OrderCloth where clothId = " + clothId);
			@SuppressWarnings("unchecked")
			List<OrderCloth> list =  query.list();
			if(!list.isEmpty()) {
				return list.get(0);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

}
