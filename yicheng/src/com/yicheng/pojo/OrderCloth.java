package com.yicheng.pojo;

import java.util.Date;

import com.yicheng.dao.OrderClothDao;
import com.yicheng.util.DateUtil;

public class OrderCloth {
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
	
	public OrderCloth(int id, String orderNumber, int clothId, Date deliveryDate, Integer count) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.clothId = clothId;
		this.deliveryDate = deliveryDate;
		this.count = count;
	}
	
	public OrderCloth(OrderCloth orderCloth) {
		id = orderCloth.getId();
		orderNumber = orderCloth.getOrderNumber();
		clothId = orderCloth.getClothId();
		deliveryDate = orderCloth.getDeliveryDate();
		count = orderCloth.getCount();
		
	}
	
	public OrderCloth(OrderClothDao dao) {
		id = dao.getInt("id");
		orderNumber = dao.getStr("order_number");
		clothId = dao.getInt("cloth_id");
		deliveryDate = DateUtil.timestampToDate(dao.getTimestamp("delivery_date"));
		count = dao.getInt("count");
		
	}
	
	public OrderClothDao toDao() {
		OrderClothDao dao = new OrderClothDao();
		if(id > 0) {
			dao.set("id", id);
		}
		dao.set("order_number", orderNumber);
		dao.set("cloth_id", clothId);
		dao.set("delivery_date", deliveryDate);
		dao.set("count", count);
		return dao;
	}
	
}
