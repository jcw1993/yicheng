package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.ClothSize;

public interface ClothSizeDao {
	
	public int create(ClothSize clothSize);
	
	public void update(ClothSize clothSize);
	
	public void delete(int id);
	
	public List<ClothSize> getByCloth(int clothId);

}
