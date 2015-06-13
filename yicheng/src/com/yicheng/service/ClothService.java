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
	
	public GenericResult<Cloth> getById(int clothId);
	
	public GenericResult<List<Cloth>> getNeedPricing();
	
	public GenericResult<List<Cloth>> getNeedCount();
	
	public GenericResult<List<Cloth>> getPriced();
	
	public GenericResult<List<Cloth>> getCounted();
	
	public GenericResult<List<Cloth>> searchInAll(String keyword);
	
	public GenericResult<List<Cloth>> searchInNeedPricing(String keyword);
	
	public GenericResult<List<Cloth>> searchInNeedCount(String keyword);
	
	public GenericResult<List<Cloth>> searchInPriced(String keyword);
	
	public GenericResult<List<Cloth>> searchInCounted(String keyword);

	public GenericResult<Integer> copyCloth(int clothId);
	
}
