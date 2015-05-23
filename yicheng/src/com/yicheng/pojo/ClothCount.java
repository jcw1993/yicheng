package com.yicheng.pojo;

import java.io.Serializable;

public class ClothCount implements Serializable {

	private static final long serialVersionUID = 5897533045836037706L;
	
	private int id;
	private int orderId;
	private int clothId;
	private int sizeType;
	private int count;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getClothId() {
		return clothId;
	}
	public void setClothId(int clothId) {
		this.clothId = clothId;
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
	
	public ClothCount() {}

	public ClothCount(int id) {
		this.id = id;
	}
	
}
