package com.yicheng.pojo;

import java.util.Date;

import com.yicheng.dao.ClothMaterialDao;
import com.yicheng.util.DateUtil;

public class ClothMaterial {

	private int id;
	private int clothId;
	private int clothColorId;
	private int materialId;
	private String color;
	private String part;
	private String unitName;
	private String supplier;
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
	public int getClothColorId() {
		return clothColorId;
	}
	public void setClothColorId(int clothColorId) {
		this.clothColorId = clothColorId;
	}
	public int getMaterialId() {
		return materialId;
	}
	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
	
	public ClothMaterial(int id, int clothId, int clothColorId, int materialId, String color, String part, String unitName, String supplier, double consumption, 
			Double estimatedPrice, Integer count, Integer orderCount, Double price, Date orderDate, String remark) {
		this.id = id;
		this.clothId = clothId;
		this.clothColorId = clothColorId;
		this.materialId = materialId;
		this.color = color;
		this.part = part;
		this.unitName = unitName;
		this.supplier = supplier;
		this.consumption = consumption;
		this.estimatedPrice = estimatedPrice;
		this.count = count;
		this.orderCount = orderCount;
		this.price = price;
		this.orderDate = orderDate;
		this.remark = remark;
	}
	
	public ClothMaterial(ClothMaterial clothMaterial) {
		id = clothMaterial.getId();
		clothId = clothMaterial.getClothId();
		clothColorId = clothMaterial.getClothColorId();
		materialId = clothMaterial.getMaterialId();
		color = clothMaterial.getColor();
		part = clothMaterial.getPart();
		unitName = clothMaterial.getUnitName();
		supplier = clothMaterial.getSupplier();
		consumption = clothMaterial.getConsumption();
		estimatedPrice = clothMaterial.getEstimatedPrice();
		count = clothMaterial.getCount();
		orderCount = clothMaterial.getOrderCount();
		price = clothMaterial.getPrice();
		orderDate = clothMaterial.getOrderDate();
		remark = clothMaterial.getRemark();
	}
	
	public ClothMaterial(ClothMaterialDao dao) {
		id = dao.getInt("id");
		clothId = dao.getInt("cloth_id");
		clothColorId = dao.getInt("cloth_color_id");
		materialId = dao.getInt("material_id");
		color = dao.getStr("color");
		part = dao.getStr("part");
		unitName = dao.getStr("unit_name");
		supplier = dao.getStr("supplier");
		consumption = dao.getDouble("consumption");
		estimatedPrice = dao.getDouble("estimated_price");
		count = dao.getInt("count");
		orderCount = dao.getInt("order_count");
		price = dao.getDouble("price");
		orderDate = DateUtil.timestampToDate(dao.getTimestamp("order_date"));
		remark = dao.getStr("remark");
	}
	
	public ClothMaterialDao toDao() {
		ClothMaterialDao dao = new ClothMaterialDao();
		if(id > 0) {
			dao.set("id", id);
		}
		dao.set("cloth_id", clothId);
		dao.set("cloth_color_id", clothColorId);
		dao.set("material_id", materialId);
		dao.set("color", color);
		dao.set("part", part);
		dao.set("unit_name", unitName);
		dao.set("supplier", supplier);
		dao.set("consumption", consumption);
		dao.set("estimated_price", estimatedPrice);
		dao.set("count", count);
		dao.set("order_count", orderCount);
		dao.set("price", price);
		dao.set("order_date", orderDate);
		dao.set("remark", remark);
		return dao;
	}
}
