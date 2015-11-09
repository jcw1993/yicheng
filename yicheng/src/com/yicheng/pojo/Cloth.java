package com.yicheng.pojo;

import java.util.Date;

import com.yicheng.dao.ClothDao;
import com.yicheng.util.DateUtil;

public class Cloth {
	private int id;
	private String type;
	private String name;
	
	private String client;
	private String remark;
	private String imagePath;
	
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	
	public Cloth(Cloth cloth) {
		id = cloth.getId();
		type = cloth.getType();
		name = cloth.getName();
		client = cloth.getClient();
		remark = cloth.getRemark();
		imagePath = cloth.getImagePath();
		createdTime = cloth.getCreatedTime();
	}
	
	public Cloth(ClothDao dao) {
		id = dao.getInt("id");
		type = dao.getStr("type");
		name = dao.getStr("name");
		client = dao.getStr("client");
		remark = dao.getStr("remark");
		imagePath = dao.getStr("image_path");
		createdTime = DateUtil.timestampToDate(dao.getTimestamp("created_time"));
	}

	public ClothDao toDao() {
		ClothDao dao = new ClothDao();
		if(id > 0) {
			dao.set("id", id);
		}
		dao.set("type", type);
		dao.set("name", name);
		dao.set("client", client);
		dao.set("remark", remark);
		dao.set("image_path", imagePath);
		dao.set("created_time", createdTime);
		return dao;
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
