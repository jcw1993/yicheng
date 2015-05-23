package com.yicheng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.ClothMaterialDao;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

@Service
public class ClothMaterialServiceImpl implements ClothMaterialService {
	
	private static Logger logger = LoggerFactory.getLogger(ClothMaterialServiceImpl.class);
	
	private static final String CLOTH_MATERIAL_CACHE_KEY = "cloth_material_cache_%d";
	
	@Autowired
	private ClothMaterialDao clothMaterialDao;
	
	@Override
	public GenericResult<Integer> create(ClothMaterial clothMaterial) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = clothMaterialDao.create(clothMaterial);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult update(ClothMaterial clothMaterial) {
		NoneDataResult result = new NoneDataResult();
		try{
			clothMaterialDao.update(clothMaterial);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult delete(int id) {
		NoneDataResult result = new NoneDataResult();
		try{
			clothMaterialDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<ClothMaterial>> getByCloth(int clothId) {
		GenericResult<List<ClothMaterial>> result = new GenericResult<List<ClothMaterial>>();
		@SuppressWarnings("unchecked")
		List<ClothMaterial> clothMaterialList = (List<ClothMaterial>) CacheUtil.get(String.format(CLOTH_MATERIAL_CACHE_KEY, clothId));
		if(null != clothMaterialList && !clothMaterialList.isEmpty()) {
			result.setData(clothMaterialList);
		}else {
			try {
				clothMaterialList = clothMaterialDao.getByCloth(clothId);
				if(null != clothMaterialList && !clothMaterialList.isEmpty()) {
					result.setData(clothMaterialList);
					CacheUtil.put(String.format(CLOTH_MATERIAL_CACHE_KEY, clothId), clothMaterialList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setMessage(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			}
		}
		return result;
	}
}
