package com.yicheng.pojo;

import java.io.Serializable;

public class Cloth implements Serializable {
	private static final long serialVersionUID = 186654531546632842L;

	private int id;
	private String type;
	private String name;
	private int colorType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getColorType() {
		return colorType;
	}
	public void setColorType(int colorType) {
		this.colorType = colorType;
	}
	
	public Cloth() {}
	
	public Cloth(int id) {
		this.id = id;
	}

}
