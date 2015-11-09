package com.yicheng.pojo;

import com.yicheng.dao.UserDao;

public class User {

	private int id;
	private String name;
	private String password;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public User() {}
	
	public User(int id, String name, String password, int type) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.type = type;
	}
	
	public User(UserDao dao) {
		id = dao.getInt("id");
		name = dao.getStr("name");
		password = dao.getStr("password");
		type = dao.getInt("type");
	}
	
	public UserDao toDao() {
		UserDao dao = new UserDao();
		if(id > 0) {
			dao.set("id", id);
		}
		dao.set("name", name);
		dao.set("password", password);
		dao.set("type", type);
		return dao;
	}
}
