package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.ClothColor;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface ClothColorService {

	public GenericResult<Integer> create(ClothColor clothColor);
	
	public NoneDataResult delete(int clothId, int clothColorId);
	
	public GenericResult<List<ClothColor>> getByCloth(int clothId);
	
	public GenericResult<ClothColor> getById(int clothId, int clothColorId);

}
