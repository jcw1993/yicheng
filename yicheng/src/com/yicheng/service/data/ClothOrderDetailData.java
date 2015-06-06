package com.yicheng.service.data;

import java.io.Serializable;
import java.util.Date;

import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.OrderCloth;

public class ClothOrderDetailData implements Serializable {
	
	private static final long serialVersionUID = -7312010418088691530L;

	private int id;
	
	private int clothId;
	private String clothName;
	private String clothType;
	
	private String orderNumber;
	private Date deliveryDate;
	
	private Integer count;

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

	public String getClothName() {
		return clothName;
	}

	public void setClothName(String clothName) {
		this.clothName = clothName;
	}

	public String getClothType() {
		return clothType;
	}

	public void setClothType(String clothType) {
		this.clothType = clothType;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
	
	public ClothOrderDetailData() {}
	
	public ClothOrderDetailData(OrderCloth orderCloth, Cloth cloth) {
		if(null != orderCloth) {
			this.id = orderCloth.getId();
			this.orderNumber = orderCloth.getOrderNumber();
			this.deliveryDate = orderCloth.getDeliveryDate();
			this.count = orderCloth.getCount();
		}
		
		if(null != cloth) {
			this.clothId = cloth.getId();
			this.clothName = cloth.getName();
			this.clothType = cloth.getType();
		}
		
	}

}
