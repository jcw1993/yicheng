package com.yicheng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.ClothCountDao;
import com.yicheng.pojo.ClothCount;
import com.yicheng.service.ClothCountService;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

@Service
public class ClothCountServiceImpl implements ClothCountService {

	private static Logger logger = LoggerFactory.getLogger(ClothCountService.class);
	
	private static final String CLOTH_COUNT_CACHE_KEY = "cloth_count_cache_%d";
	
	@Autowired
	private ClothCountDao clothCountDao;

	@Override
	public GenericResult<Integer> create(ClothCount clothCount) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = clothCountDao.create(clothCount);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult update(ClothCount clothCount) {
		NoneDataResult result = new NoneDataResult();
		try{
			clothCountDao.update(clothCount);
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
			clothCountDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<ClothCount>> getByCloth(int clothId) {
		GenericResult<List<ClothCount>> result = new GenericResult<List<ClothCount>>();
		@SuppressWarnings("unchecked")
		List<ClothCount> clothCountList = (List<ClothCount>) CacheUtil.get(String.format(CLOTH_COUNT_CACHE_KEY, clothId));
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
