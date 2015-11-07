package com.yicheng.pojo;

import java.io.Serializable;
import java.util.Date;

public class Content implements Serializable {

	private static final long serialVersionUID = 4251159545561800010L;
	
	private int id;
	private String originFileName;
	private String destPath;
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
	public String getDestPath() {
		return destPath;
	}
	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public Content() {}
	
	public Content(int id, String originFileName, String destPath,  Date createdTime) {
		this.id = id;
		this.originFileName = originFileName;
		this.destPath = destPath;
		this.createdTime = createdTime;
	}
	
	public Content(String originFileName, String destPath, Date createdTime) {
		this.originFileName = originFileName;
		this.destPath = destPath;
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
