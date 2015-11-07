package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.ClothSize;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface ClothSizeService {
	public GenericResult<Integer> create(ClothSize clothSize);
	
	public NoneDataResult update(ClothSize clothSize);
	
	public NoneDataResult save(ClothSize clothSize);
	
	public NoneDataResult delete(int id, int clothId);
	
	public NoneDataResult deleteByCloth(int clothId);
	
	public GenericResult<List<ClothSize>> getByCloth(int clothId);
	
	public GenericResult<List<ClothSize>> getByClothColor(int clothId, int clothColorId);
	
	public GenericResult<ClothSize> search(int clothId, int clothColorId, int sizeType);

}
