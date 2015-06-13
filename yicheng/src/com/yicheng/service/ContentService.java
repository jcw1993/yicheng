package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.Content;
import com.yicheng.util.GenericResult;

public interface ContentService {

	public GenericResult<Integer> create(Content content);
	
	public GenericResult<List<Content>> getAll();
	
	public GenericResult<Content> getById(int contentId);
	
	public GenericResult<String> getContentCodeById(Integer contentId);
}
