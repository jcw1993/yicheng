package com.yicheng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.MaterialDao;
import com.yicheng.pojo.Material;
import com.yicheng.service.MaterialService;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

@Service
public class MaterialServiceImpl implements MaterialService {
	
	private static Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);
	
	private static final String ALL_MATERIAL_CACHE = "all_material_cache";
	
	@Autowired
	private MaterialDao materialDao;

	@Override
	public GenericResult<Integer> create(Material material) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = materialDao.create(material);
			result.setData(outId);
			CacheUtil.remove(ALL_MATERIAL_CACHE);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult update(Material material) {
		NoneDataResult result = new NoneDataResult();
		try{
			materialDao.update(material);
			CacheUtil.remove(ALL_MATERIAL_CACHE);
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
			materialDao.delete(id);
			CacheUtil.remove(ALL_MATERIAL_CACHE);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Material>> getAll() {
		GenericResult<List<Material>> result = new GenericResult<List<Material>>();
		@SuppressWarnings("unchecked")
		List<Material> materialList = (List<Material>) CacheUtil.get(ALL_MATERIAL_CACHE);
		if(null != materialList && !materialList.isEmpty()) {
			result.setData(materialList);
		}else {
			try {
				materialList = materialDao.getAll();
				if(null != materialList && !materialList.isEmpty()) {
					result.setData(materialList);
					CacheUtil.put(ALL_MATERIAL_CACHE, materialList);
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

	@Override
	public GenericResult<Material> getById(int materialId) {
		GenericResult<Material> result = new GenericResult<Material>();
		GenericResult<List<Material>> allResult = getAll();
		
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(Material material : allResult.getData()) {
				if(material.getId() == materialId) {
					result.setData(material);
					return result;
				}
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		
		return result;
	}

}
