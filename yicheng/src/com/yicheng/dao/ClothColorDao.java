package com.yicheng.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class ClothColorDao extends Model<ClothColorDao> {
	
	private static final long serialVersionUID = 3590018956784292831L;

	public static ClothColorDao dao = new ClothColorDao();

	public static void deleteByCloth(int clothId) {
		Db.update("delete from cloth_color where cloth_id = ?", clothId);
	}
	
	public static List<ClothColorDao> getByCloth(int clothId) {
		return dao.find("select * from cloth_color where cloth_id = " + clothId + " order by id asc");
	}
	

}
