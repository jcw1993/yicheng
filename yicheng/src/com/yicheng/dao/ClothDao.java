package com.yicheng.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class ClothDao extends Model<ClothDao> {
	
	private static final long serialVersionUID = -6150273930435473505L;

	public static ClothDao dao = new ClothDao();

	public static List<ClothDao> getAll() {
		return dao.find("select * from cloth order by created_time desc, id desc");
	}

}
