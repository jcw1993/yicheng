package com.yicheng.pojo;

import com.yicheng.dao.MaterialDao;


public class Material {
	private int id;
	private String name;
	private int type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public Material() {}
	
	public Material(int id) {
		this.id = id;
	}
	
	public Material(MaterialDao dao) {
		id = dao.getInt("id");
		name = dao.getStr("name");
		type = dao.getInt("type");
	}

	public MaterialDao toDao() {
		MaterialDao dao = new MaterialDao();
		if(id > 0) {
			dao.set("id", id);
		}
		dao.set("name", name);
		dao.set("type", type);
		return dao;
	}
}
