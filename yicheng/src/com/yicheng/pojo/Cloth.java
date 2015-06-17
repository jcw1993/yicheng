package com.yicheng.pojo;

import java.io.Serializable;
import java.util.Date;

public class Cloth implements Serializable {
	private static final long serialVersionUID = 186654531546632842L;

	private int id;
	private String type;
	private String name;
	
	private String client;
	private String supplier;
	private String remark;
	private Integer imageId;
	
	private Date createdTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getImageId() {
		return imageId;
	}
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Cloth() {}
	
	public Cloth(int id) {
		this.id = id;
	}

	public Cloth(int id, String type, String name, String client, String supplier, String remark, Integer imageId, Date createdTime) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.client = client;
		this.supplier = supplier;
		this.remark = remark;
		this.imageId = imageId;
		this.createdTime = createdTime;
	}
	
	public Cloth(String type, String name, String client, String supplier, String remark, Integer imageId, Date createdTime) {
		this.type = type;
		this.name = name;
		this.client = client;
		this.supplier = supplier;
		this.remark = remark;
		this.imageId = imageId;
		this.createdTime = createdTime;
	}	
	
	public Cloth(Cloth cloth) {
		this.id = cloth.getId();
		this.type = cloth.getType();
		this.name = cloth.getName();
		this.client = cloth.getClient();
		this.supplier = cloth.getSupplier();
		this.remark = cloth.getRemark();
		this.imageId = cloth.getImageId();
		this.createdTime = cloth.getCreatedTime();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Cloth) {
			Cloth cloth = (Cloth) obj;
			return this.id == cloth.getId();
		}
		return false; 
	}

}
