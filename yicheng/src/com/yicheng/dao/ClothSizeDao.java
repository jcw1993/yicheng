package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.ClothSize;

public interface ClothSizeDao {
	
	public int create(ClothSize clothCount);
	
	public void update(ClothSize clothCount);
	
	public void delete(int id);
	
	public List<ClothSize> getByCloth(int clothId);

}
