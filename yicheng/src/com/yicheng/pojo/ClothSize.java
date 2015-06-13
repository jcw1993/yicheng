package com.yicheng.pojo;

import java.io.Serializable;

public class ClothSize implements Serializable {

	private static final long serialVersionUID = 5897533045836037706L;
	
	private int id;
	private int clothId;
	private int clothColorId;
	private int sizeType;
	private int count;
	
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
	public int getClothColorId() {
		return clothColorId;
	}
	public void setClothColorId(int clothColorId) {
		this.clothColorId = clothColorId;
	}
	public int getSizeType() {
		return sizeType;
	}
	public void setSizeType(int sizeType) {
		this.sizeType = sizeType;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public ClothSize() {}
	
	public ClothSize(int id) {
		this.id = id;
	}

	public ClothSize(int id, int clothId, int clothColorId, int sizeType, int count) {
		this.id = id;
		this.clothId = clothId;
		this.clothColorId = clothColorId;
		this.sizeType = sizeType;
		this.count = count;
	}
	
	public ClothSize(int clothId, int clothColorId, int sizeType, int count) {
		this.clothId = clothId;
		this.clothColorId = clothColorId;
		this.sizeType = sizeType;
		this.count = count;
	}
}
