package com.yicheng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.ClothSizeDao;
import com.yicheng.pojo.ClothSize;
import com.yicheng.service.ClothSizeService;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

@Service
public class ClothSizeServiceImpl implements ClothSizeService {

	private static Logger logger = LoggerFactory.getLogger(ClothSizeService.class);
	
	private static final String CLOTH_COUNT_CACHE_KEY = "cloth_count_cache_%d";
	
	@Autowired
	private ClothSizeDao clothCountDao;

	@Override
	public GenericResult<Integer> create(ClothSize clothCount) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = clothCountDao.create(clothCount);
			result.setData(outId);
			CacheUtil.remove(String.format(CLOTH_COUNT_CACHE_KEY, clothCount.getClothId()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult update(ClothSize clothCount) {
		NoneDataResult result = new NoneDataResult();
		try{
			clothCountDao.update(clothCount);
			CacheUtil.remove(String.format(CLOTH_COUNT_CACHE_KEY, clothCount.getClothId()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult delete(int id, int clothId) {
		NoneDataResult result = new NoneDataResult();
		try{
			clothCountDao.delete(id);
			CacheUtil.remove(String.format(CLOTH_COUNT_CACHE_KEY, clothId));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<ClothSize>> getByCloth(int clothId) {
		GenericResult<List<ClothSize>> result = new GenericResult<List<ClothSize>>();
		@SuppressWarnings("unchecked")
		List<ClothSize> clothCountList = (List<ClothSize>) CacheUtil.get(String.format(CLOTH_COUNT_CACHE_KEY, clothId));
		if(null != clothCountList && !clothCountList.isEmpty()) {
			result.setData(clothCountList);
		}else {
			try {
				clothCountList = clothCountDao.getByCloth(clothId);
				if(null != clothCountList && !clothCountList.isEmpty()) {
					result.setData(clothCountList);
					CacheUtil.put(String.format(CLOTH_COUNT_CACHE_KEY, clothId), clothCountList);
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
