package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.Material;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface MaterialService {
	public GenericResult<Integer> create(Material material);
	
	public NoneDataResult update(Material material);
	
	public NoneDataResult delete(int id);
	
	public GenericResult<List<Material>> getAll();
	
	public GenericResult<List<Material>> getByType(int type);
	
	public GenericResult<Material> getById(int materialId);
}
