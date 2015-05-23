package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.ClothMaterial;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface ClothMaterialService {
	public GenericResult<Integer> create(ClothMaterial clothMaterial);
	
	public NoneDataResult update(ClothMaterial clothMaterial);
	
	public NoneDataResult delete(int id);
	
	public GenericResult<List<ClothMaterial>> getByCloth(int clothId);
}
