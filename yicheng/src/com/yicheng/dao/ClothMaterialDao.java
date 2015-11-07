package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.ClothMaterial;

public interface ClothMaterialDao {
	
	public int create(ClothMaterial clothMaterial);
	
	public void update(ClothMaterial clothMaterial);
	
	public void delete(int id);
	
	public void deleteByCloth(int clothId);
	
	public List<ClothMaterial> getByCloth(int clothId);

}
