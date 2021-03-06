package com.yicheng.service.data;

import java.io.Serializable;
import java.util.Date;

import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.pojo.Material;

public class ClothMaterialDetailData implements Serializable {

	private static final long serialVersionUID = 6533459074990301213L;

	private int id;
	
	private int clothId;
	
	private int clothColorId;
	private String clothColor;
	
	private int materialId;
	private int materialType;
	private String materialName;
	private String materialColor;
	
	private String part;
	private String unitName;
	private String supplier;
	
	private double consumption;
	private Integer count;
	private Integer orderCount;
	private Double estimatedPrice;
	private Double price;
	
	private Double materialTotalPrice;
	
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
	public int getClothColorId() {
		return clothColorId;
	}
	public void setClothColorId(int clothColorId) {
		this.clothColorId = clothColorId;
	}
	public String getClothColor() {
		return clothColor;
	}
	public void setClothColor(String clothColor) {
		this.clothColor = clothColor;
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
	public String getMaterialColor() {
		return materialColor;
	}
	public void setMaterialColor(String materialColor) {
		this.materialColor = materialColor;
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
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
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
	public Double getEstimatedPrice() {
		return estimatedPrice;
	}
	public void setEstimatedPrice(Double estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getMaterialTotalPrice() {
		if(null == materialTotalPrice) {
			calculateTotalPrice();
		}
		return materialTotalPrice;
	}
	public void setMaterialTotalPrice(Double materialTotalPrice) {
		this.materialTotalPrice = materialTotalPrice;
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
	
	public ClothMaterialDetailData(ClothMaterial clothMaterial, ClothColor clothColor, Material material) {
		if(null != clothMaterial) {
			this.id = clothMaterial.getId();
			this.clothId = clothMaterial.getId();
			this.materialId = clothMaterial.getMaterialId();
			this.materialColor = clothMaterial.getColor();
			this.part = clothMaterial.getPart();
			this.unitName = clothMaterial.getUnitName();
			this.supplier = clothMaterial.getSupplier();
			this.consumption = clothMaterial.getConsumption();
			this.count = clothMaterial.getCount();
			this.orderCount = clothMaterial.getOrderCount();
			this.estimatedPrice = clothMaterial.getEstimatedPrice();
			this.price = clothMaterial.getPrice();
			this.orderDate = clothMaterial.getOrderDate();
			this.remark = clothMaterial.getRemark();
		}
		
		if(null != clothColor) {
			this.clothColorId = clothColor.getId();
			this.clothColor = clothColor.getColor();
		}

		if(null != material) {
			this.materialName = material.getName();
			this.materialType = material.getType();
		}
	}
	
	public void calculateTotalPrice() {
		if(null == price || null == count) {
			return;
		}
		
		materialTotalPrice = price * consumption * count;
	}

}
