package com.yicheng.service.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yicheng.common.Config;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothColor;

public class ClothDetailData implements Serializable {

	private static final long serialVersionUID = -6414161446766109730L;

	private int id;
	private String type;
	private String name;
	
	private String client;
	private String supplier;
	private String remark;
	// TODO maybe change to image type
	private Integer imageId;
	private String imageContent;
	
	private Date createdTime;
	
	private String color;
	
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

	public String getImageContent() {
		return imageContent;
	}

	public void setImageContent(String imageContent) {
		this.imageContent = imageContent;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public ClothDetailData() {}
	
	public ClothDetailData(Cloth cloth, List<ClothColor> clothColors) {
		if(null != cloth) {
			this.id = cloth.getId();
			this.type = cloth.getType();
			this.name = cloth.getName();
			this.client = cloth.getClient();
			this.supplier = cloth.getSupplier();
			this.remark = cloth.getRemark();
			this.imageId = cloth.getImageId();
			this.createdTime = cloth.getCreatedTime();
			
		}
		
		if(null != clothColors && !clothColors.isEmpty()) {
			this.color = "";
			int size = clothColors.size();
			for(int i = 0; i < size - 1; i++) {
				color += clothColors.get(i).getColor();
				color += Config.COLOR_SEPERATOR;
			}
			color += clothColors.get(size - 1).getColor();
		}
	}
	
}
