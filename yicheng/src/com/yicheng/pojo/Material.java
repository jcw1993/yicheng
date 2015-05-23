package com.yicheng.pojo;

import java.io.Serializable;

public class Material implements Serializable {

	private static final long serialVersionUID = 7745338079290673602L;
	
	private int id;
	private String name;
	private int colorType;
	private int type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public Material() {}
	
	public Material(int id) {
		this.id = id;
	}

}
