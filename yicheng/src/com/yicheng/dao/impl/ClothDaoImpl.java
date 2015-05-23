package com.yicheng.dao.impl;

import java.util.List;

import com.yicheng.dao.ClothDao;
import com.yicheng.pojo.Cloth;

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
		return getHibernateTemplate().find("from CLoth");
	}

}
