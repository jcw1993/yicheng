package com.yicheng.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class ClothSizeDao extends Model<ClothSizeDao> {
	
	private static final long serialVersionUID = 101765688616158506L;
	
	public static ClothSizeDao dao = new ClothSizeDao();
	
	public static void deleteByCloth(int clothId) {
		Db.update("delete from cloth_size where cloth_id = ?", clothId);
	}

	public static List<ClothSizeDao> getByCloth(int clothId) {
		return dao.find("from cloth_size where cloth_id = ? order by sizeType asc", clothId);
	}

}
