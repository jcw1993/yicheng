package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.Material;

public class MaterialDao extends HibernateDaoBase {

	public int create(Material material) {
		return (Integer) getHibernateTemplate().save(material);
	}

	public void update(Material material) {
		getHibernateTemplate().update(material);
	}

	public void delete(int id) {
		getHibernateTemplate().delete(new Material(id));
	}

	@SuppressWarnings("unchecked")
	public List<Material> getAll() {
		return getHibernateTemplate().find("from Material");
	}

}
