package com.yicheng.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.yicheng.dao.ClothSizeDao;
import com.yicheng.pojo.ClothSize;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

public class ClothSizeService {

	private static Logger logger = LoggerFactory.getLogger(ClothSizeService.class);
	
	private static final String CLOTH_COUNT_CACHE_KEY = "cloth_count_cache_%d";
	
	public GenericResult<Integer> create(ClothSize clothSize) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			ClothSizeDao dao = clothSize.toDao();
			dao.save();
			result.setData(dao.getInt("id"));
			CacheUtil.remove(String.format(CLOTH_COUNT_CACHE_KEY, clothSize.getClothId()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public NoneDataResult update(ClothSize clothSize) {
		NoneDataResult result = new NoneDataResult();
		try{
			clothSize.toDao().update();
			CacheUtil.remove(String.format(CLOTH_COUNT_CACHE_KEY, clothSize.getClothId()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public NoneDataResult delete(int id, int clothId) {
		NoneDataResult result = new NoneDataResult();
		try{
			ClothSizeDao.dao.deleteById(id);
			CacheUtil.remove(String.format(CLOTH_COUNT_CACHE_KEY, clothId));
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
			ClothSizeDao.deleteByCloth(clothId);
		}catch(Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	public GenericResult<List<ClothSize>> getByCloth(int clothId) {
		GenericResult<List<ClothSize>> result = new GenericResult<List<ClothSize>>();
		@SuppressWarnings("unchecked")
		List<ClothSize> clothCountList = (List<ClothSize>) CacheUtil.get(String.format(CLOTH_COUNT_CACHE_KEY, clothId));
		if(null != clothCountList && !clothCountList.isEmpty()) {
			result.setData(clothCountList);
		}else {
			try {
				List<ClothSizeDao> daos = ClothSizeDao.getByCloth(clothId);
				if(null != daos && !daos.isEmpty()) {
					clothCountList = new ArrayList<ClothSize>();
					for(ClothSizeDao dao : daos) {
						clothCountList.add(new ClothSize(dao));
					}
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

	public GenericResult<List<ClothSize>> getByClothColor(int clothId,
			int clothColorId) {
		GenericResult<List<ClothSize>> result = new GenericResult<List<ClothSize>>();
 		GenericResult<List<ClothSize>> allResult = getByCloth(clothId);
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<ClothSize> resultList = new ArrayList<ClothSize>();
			for(ClothSize clothSize : allResult.getData()) {
				if(clothSize.getClothColorId() == clothColorId) {
					resultList.add(clothSize);
				}
			}
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	public GenericResult<ClothSize> search(int clothId, int clothColorId,
			int sizeType) {
		GenericResult<ClothSize> result = new GenericResult<ClothSize>();
		GenericResult<List<ClothSize>> allResult = getByClothColor(clothId, clothColorId);
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(ClothSize clothSize : allResult.getData()) {
				if(clothSize.getSizeType() == sizeType) {
					result.setData(clothSize);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	public NoneDataResult save(ClothSize clothSize) {
		GenericResult<ClothSize> clothSizeResult = search(clothSize.getClothId(), 
				clothSize.getClothColorId(), clothSize.getSizeType());
		if(clothSizeResult.getResultCode() == ResultCode.NORMAL) {
			clothSize.setId(clothSizeResult.getData().getId());
			return update(clothSize);
		}else {
			GenericResult<Integer> createResult = create(clothSize);
			return new NoneDataResult(createResult.getResultCode());
		}
	}
	
	
}
