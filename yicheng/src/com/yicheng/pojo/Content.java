package com.yicheng.pojo;

import java.io.Serializable;
import java.util.Date;

public class Content implements Serializable {

	private static final long serialVersionUID = 4251159545561800010L;
	
	private int id;
	private String originFileName;
	private String destFileName;
	private Date createdTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOriginFileName() {
		return originFileName;
	}
	public void setOriginFileName(String orginFileName) {
		this.originFileName = orginFileName;
	}
	public String getDestFileName() {
		return destFileName;
	}
	public void setDestFileName(String destFileName) {
		this.destFileName = destFileName;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public Content() {}
	
	public Content(int id, String originFileName, String destFileName,  Date createdTime) {
		this.id = id;
		this.originFileName = originFileName;
		this.destFileName = destFileName;
		this.createdTime = createdTime;
	}
	
	public Content(String originFileName, String destFileName, Date createdTime) {
		this.originFileName = originFileName;
		this.destFileName = destFileName;
		this.createdTime = createdTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Content) {
			Content content = (Content) obj;
			return this.id == content.getId();
		}
		return false; 
	}
	
}
