package com.yicheng.service.data;

import java.io.Serializable;
import java.util.Date;

import com.yicheng.pojo.ClothMaterial;
import com.yicheng.pojo.Material;

public class ClothMaterialDetailData implements Serializable {

	private static final long serialVersionUID = 6533459074990301213L;

	private int id;
	
	private int clothId;

	private int materialId;
	private int materialType;
	private String materialName;
	
	private String part;
	private String unitName;
	private double consumption;
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
	public int getMaterialType() {
		return materialType;
	}
	public void setMaterialType(int materialType) {
		this.materialType = materialType;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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
	
	public ClothMaterialDetailData() {}
	
	public ClothMaterialDetailData(ClothMaterial clothMaterial, Material material) {
		if(null != clothMaterial) {
			this.id = clothMaterial.getId();
			this.clothId = clothMaterial.getId();
			this.materialId = clothMaterial.getMaterialId();
			this.part = clothMaterial.getPart();
			this.unitName = clothMaterial.getUnitName();
			this.consumption = clothMaterial.getConsumption();
			this.count = clothMaterial.getCount();
			this.orderCount = clothMaterial.getOrderCount();
			this.price = clothMaterial.getPrice();
			this.orderDate = clothMaterial.getOrderDate();
			this.remark = clothMaterial.getRemark();
		}

		if(null != material) {
			this.materialName = material.getName();
			this.materialType = material.getType();
		}
	}

}
