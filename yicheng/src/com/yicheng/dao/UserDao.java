package com.yicheng.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class UserDao extends Model<UserDao> {
	
	private static final long serialVersionUID = -6823343413461277249L;
	
	public static UserDao dao = new UserDao();
	
	public static List<UserDao> getAll() {
		return dao.find("select * from user");
	}

}
