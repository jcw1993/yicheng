package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.Cloth;

public interface ClothDao {
	
	public int create(Cloth cloth);
	
	public void update(Cloth cloth);
	
	public void delete(int id);
	
	public List<Cloth> getAll();
}
