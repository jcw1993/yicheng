package com.yicheng.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yicheng.dao.MaterialDao;
import com.yicheng.pojo.Material;

@Repository
public class MaterialDaoImpl extends HibernateDaoBase implements MaterialDao {

	@Override
	public int create(Material material) {
		return (Integer) getHibernateTemplate().save(material);
	}

	@Override
	public void update(Material material) {
		getHibernateTemplate().update(material);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new Material(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Material> getAll() {
		return getHibernateTemplate().find("from Material");
	}

}
