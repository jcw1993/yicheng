package com.yicheng.pojo;

import java.io.Serializable;
import java.util.Date;

public class ClothMaterial implements Serializable {

	private static final long serialVersionUID = 2750552457138856125L;
	
	private int id;
	private int clothId;
	private int materialId;
	private String part;
	private String unitName;
	private double consumption;
	private Double estimatedPrice;
	private Integer count;
	private Integer orderCount;
	private Double price;
	private Date orderDate;
	private String remark;
	
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

	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public double getConsumption() {
		return consumption;
	}

	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	
	public Double getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(Double estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ClothMaterial() {}

	public ClothMaterial(int id) {
		this.id = id;
	}
	
	public ClothMaterial(int id, int clothId, int materialId, String part, String unitName, double consumption, 
			Double estimatedPrice, Integer count, Integer orderCount, Double price, Date orderDate, String remark) {
		this.id = id;
		this.clothId = clothId;
		this.materialId = materialId;
		this.part = part;
		this.unitName = unitName;
		this.consumption = consumption;
		this.estimatedPrice = estimatedPrice;
		this.count = count;
		this.orderCount = orderCount;
		this.price = price;
		this.orderDate = orderDate;
		this.remark = remark;
	}
	
	public ClothMaterial(int clothId, int materialId, String part, String unitName, double consumption, 
			Double estimatedPrice, Integer count, Integer orderCount, Double price, Date orderDate, String remark) {
		this.clothId = clothId;
		this.materialId = materialId;
		this.part = part;
		this.unitName = unitName;
		this.consumption = consumption;
		this.estimatedPrice = estimatedPrice;
		this.count = count;
		this.orderCount = orderCount;
		this.price = price;
		this.orderDate = orderDate;
		this.remark = remark;
	}
}
