package com.yicheng.pojo;

import java.io.Serializable;

public class ClothColor implements Serializable {
	
	private static final long serialVersionUID = -4075181254796437556L;
	
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
	
	public ClothColor(int id, int clothId, String color) {
		this.id = id;
		this.clothId = clothId;
		this.color = color;
	}
	
	public ClothColor(int clothId, String color) {
		this.clothId = clothId;
		this.color = color;
	}
	
	public ClothColor(ClothColor clothColor) {
		this.id = clothColor.getId();
		this.clothId = clothColor.getClothId();
		this.color = clothColor.getColor();
	}
	

}
