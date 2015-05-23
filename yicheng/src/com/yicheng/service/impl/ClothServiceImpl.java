package com.yicheng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.ClothDao;
import com.yicheng.pojo.Cloth;
import com.yicheng.service.ClothService;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

@Service
public class ClothServiceImpl implements ClothService {
	private static final String ALL_CLOTH_CACHE_KEY = "all_cloth_cache";
	
	private static Logger logger = LoggerFactory.getLogger(ClothServiceImpl.class);
	
	@Autowired
	private ClothDao clothDao;
	
	@Override
	public GenericResult<Integer> create(Cloth cloth) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = clothDao.create(cloth);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult update(Cloth cloth) {
		NoneDataResult result = new NoneDataResult();
		try{
			clothDao.update(cloth);
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
			clothDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> getAll() {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		@SuppressWarnings("unchecked")
		List<Cloth> clothList = (List<Cloth>) CacheUtil.get(ALL_CLOTH_CACHE_KEY);
		if(null != clothList && !clothList.isEmpty()) {
			result.setData(clothList);
		}else {
			try {
				clothList = clothDao.getAll();
				if(null != clothList && !clothList.isEmpty()) {
					result.setData(clothList);
					CacheUtil.put(ALL_CLOTH_CACHE_KEY, clothList);
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
