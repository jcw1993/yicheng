package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.Content;

public interface ContentDao {
	
	public int create(Content content);
	
	public List<Content> getAll();

}
