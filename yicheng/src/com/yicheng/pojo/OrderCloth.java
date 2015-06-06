package com.yicheng.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderCloth implements Serializable {

	private static final long serialVersionUID = -3293539450701976134L;
	
	private int id;
	private String orderNumber;
	private int clothId;
	private Date deliveryDate;
	private Integer count;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getClothId() {
		return clothId;
	}

	public void setClothId(int clothId) {
		this.clothId = clothId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public OrderCloth() {}

	public OrderCloth(int id) {
		this.id = id;
	}
	
}
