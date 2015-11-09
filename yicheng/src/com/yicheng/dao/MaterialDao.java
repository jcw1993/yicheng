package com.yicheng.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class MaterialDao extends Model<MaterialDao> {

	private static final long serialVersionUID = 4271129470826297917L;

	public static MaterialDao dao = new MaterialDao();

	public static List<MaterialDao> getAll() {
		return dao.find("select * from material");
	}

}
