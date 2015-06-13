package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.ClothSize;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface ClothSizeService {
	public GenericResult<Integer> create(ClothSize clothCount);
	
	public NoneDataResult update(ClothSize clothCount);
	
	public NoneDataResult delete(int id, int clothId);
	
	public GenericResult<List<ClothSize>> getByCloth(int clothId);

}
