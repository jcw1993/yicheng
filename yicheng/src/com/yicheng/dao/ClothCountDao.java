package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.ClothCount;

public interface ClothCountDao {
	
	public int create(ClothCount clothCount);
	
	public void update(ClothCount clothCount);
	
	public void delete(int id);
	
	public List<ClothCount> getByCloth(int clothId);

}
