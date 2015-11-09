package com.yicheng.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.yicheng.dao.ClothColorDao;
import com.yicheng.pojo.ClothColor;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

public class ClothColorService {
	private static Logger logger = LoggerFactory.getLogger(ClothColorService.class);
	
	private static final String CLOTH_COLOR_CACHE_KEY = "cloth_color_cache_%d";
	
	public GenericResult<Integer> create(ClothColor clothColor) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			ClothColorDao clothColorDao = clothColor.toDao();
			clothColorDao.save();
			result.setData(clothColorDao.getInt("id"));
			CacheUtil.remove(String.format(CLOTH_COLOR_CACHE_KEY, clothColor.getClothId()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public NoneDataResult delete(int clothId, int clothColorId) {
		NoneDataResult result = new NoneDataResult();
		try{
			ClothColorDao.dao.deleteById(clothColorId);
			CacheUtil.remove(String.format(CLOTH_COLOR_CACHE_KEY, clothId));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	public NoneDataResult deleteByCloth(int clothId) {
		NoneDataResult result = new NoneDataResult();
		try {
			ClothColorDao.deleteByCloth(clothId);
		}catch(Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	public GenericResult<List<ClothColor>> getByCloth(int clothId) {
		GenericResult<List<ClothColor>> result = new GenericResult<List<ClothColor>>();
		@SuppressWarnings("unchecked")
		List<ClothColor> clothColorList = (List<ClothColor>) CacheUtil.get(String.format(CLOTH_COLOR_CACHE_KEY, clothId));
		if(null != clothColorList && !clothColorList.isEmpty()) {
			result.setData(clothColorList);
		}else {
			try {
				List<ClothColorDao> daos = ClothColorDao.getByCloth(clothId);
				if(null != daos && !daos.isEmpty()) {
					clothColorList = new ArrayList<ClothColor>();
					for(ClothColorDao dao : daos) {
						clothColorList.add(new ClothColor(dao));
					}
					result.setData(clothColorList);
					CacheUtil.put(String.format(CLOTH_COLOR_CACHE_KEY, clothId), clothColorList);
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

	public GenericResult<ClothColor> getById(int clothId, int clothColorId) {
		GenericResult<ClothColor> result = new GenericResult<ClothColor>();
		GenericResult<List<ClothColor>> allResult = getByCloth(clothId);
		if (allResult.getResultCode() == ResultCode.NORMAL) {
			for (ClothColor clothColor : allResult.getData()) {
				if (clothColor.getId() == clothColorId) {
					result.setData(clothColor);
					break;
				}
			}
		} else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

}
