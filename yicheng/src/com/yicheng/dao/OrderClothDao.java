package com.yicheng.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class OrderClothDao extends Model<OrderClothDao> {

	private static final long serialVersionUID = -6600248391988939692L;

	public static OrderClothDao dao = new OrderClothDao();
	
	public static List<OrderClothDao> getByOrderNumber(String orderNumber) {
		return dao.find("select * from order_cloth where order_number = ?", orderNumber);
	}

	public static OrderClothDao getFirstByCloth(int clothId) {
		return dao.findFirst("select * from order_cloth where cloth_id = ?", clothId);
	}

}
