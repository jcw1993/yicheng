package com.yicheng.pojo;

import java.io.Serializable;
import java.util.Date;

public class OperationRecord implements Serializable {

	private static final long serialVersionUID = 6306976123558935283L;
	
	private int id;
	private String title;
	private String description;
	private Date createdTime;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public OperationRecord() {}
}
