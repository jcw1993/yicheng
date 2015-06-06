package com.yicheng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yicheng.dao.OrderClothDao;
import com.yicheng.pojo.OrderCloth;

@Repository
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

	@Override
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
