package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.ClothCount;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface ClothCountService {
	public GenericResult<Integer> create(ClothCount clothCount);
	
	public NoneDataResult update(ClothCount clothCount);
	
	public NoneDataResult delete(int id, int clothId);
	
	public GenericResult<List<ClothCount>> getByCloth(int clothId);

}
