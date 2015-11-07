package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.ClothColor;

public interface ClothColorDao {
	
	public int create(ClothColor clothColor);
	
	public void delete(int id);
	
	public void deleteByCloth(int clothId);
	
	public List<ClothColor> getByCloth(int clothId);

}
