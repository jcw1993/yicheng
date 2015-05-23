package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.Cloth;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface ClothService {
	
	public GenericResult<Integer> create(Cloth cloth);
	
	public NoneDataResult update(Cloth cloth);
	
	public NoneDataResult delete(int id);
	
	public GenericResult<List<Cloth>> getAll();

}
