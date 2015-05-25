package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.ClothMaterial;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface ClothMaterialService {
	public GenericResult<Integer> create(ClothMaterial clothMaterial);
	
	public NoneDataResult update(ClothMaterial clothMaterial);
	
	public NoneDataResult delete(int id, int clothId);

	public GenericResult<List<ClothMaterial>> getByCloth(int clothId);
	
	public GenericResult<List<ClothMaterial>> getNeedPricing(int clothId);
	
	public GenericResult<List<ClothMaterial>> getNeedCount(int clothId);
	
	public GenericResult<List<ClothMaterialDetailData>> getDetailByCloth(int clothId);
}
