package com.yicheng.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class ClothMaterialDao extends Model<ClothMaterialDao> {
	
	private static final long serialVersionUID = 4840028610179347138L;

	public static ClothMaterialDao dao = new ClothMaterialDao();

	public static void deleteByCloth(int clothId) {
		Db.update("delete from cloth_material where cloth_id = ?", clothId);
	}
	
	public static List<ClothMaterialDao> getByCloth(int clothId) {
		return dao.find("select * from cloth_material where cloth_id = ? order by id asc", clothId);
	}

}
