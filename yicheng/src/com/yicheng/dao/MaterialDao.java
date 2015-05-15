package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.Material;

public interface MaterialDao {
	
	public int create(Material material);
	
	public void update(Material material);
	
	public void delete(int id);
	
	public List<Material> getAll();
}
