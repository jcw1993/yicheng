package com.yicheng.pojo;

import com.yicheng.dao.ClothColorDao;


public class ClothColor {
	
	private int id;
	private int clothId;
	private String color;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClothId() {
		return clothId;
	}
	public void setClothId(int clothId) {
		this.clothId = clothId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public ClothColor() {}
	
	public ClothColor(int id) {
		this.id = id;
	}

	public ClothColor(ClothColor clothColor) {
		id = clothColor.getId();
		clothId = clothColor.getClothId();
		color = clothColor.getColor();
	}
	
	public ClothColor(ClothColorDao dao) {
		id = dao.getInt("id");
		clothId = dao.getInt("cloth_id");
		color = dao.getStr("color");
	}
	
	public ClothColorDao toDao() {
		ClothColorDao dao = new ClothColorDao();
		if(id > 0) {
			dao.set("id", id);
		}
		dao.set("cloth_id", clothId);
		dao.set("color", color);
		return dao;
	}

}
