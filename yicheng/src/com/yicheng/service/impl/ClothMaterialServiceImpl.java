package com.yicheng.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.ClothMaterialDao;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.pojo.Material;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.service.ClothService;
import com.yicheng.service.MaterialService;
import com.yicheng.service.data.ClothMaterialDetailData;
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
	
	@Autowired
	private MaterialService materialService;
	@Autowired
	private ClothService clothService;
	
	@Override
	public GenericResult<Integer> create(ClothMaterial clothMaterial) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = clothMaterialDao.create(clothMaterial);
			result.setData(outId);
			CacheUtil.remove(String.format(CLOTH_MATERIAL_CACHE_KEY, clothMaterial.getClothId()));
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
			CacheUtil.remove(String.format(CLOTH_MATERIAL_CACHE_KEY, clothMaterial.getClothId()));
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
			clothMaterialDao.delete(id);
			CacheUtil.remove(String.format(CLOTH_MATERIAL_CACHE_KEY, clothId));
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

	@Override
	public GenericResult<List<ClothMaterialDetailData>> getDetailByCloth(int clothId) {
		GenericResult<List<ClothMaterialDetailData>> result = new GenericResult<List<ClothMaterialDetailData>>();
		
		GenericResult<List<ClothMaterial>> clothMaterialResult = getByCloth(clothId);
		if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
			List<ClothMaterialDetailData> dataList = new ArrayList<ClothMaterialDetailData>();
			for(ClothMaterial clothMaterial : clothMaterialResult.getData()) {
				int materialId = clothMaterial.getMaterialId();
				GenericResult<Material> materialResult = materialService.getById(materialId);
				if(materialResult.getResultCode() == ResultCode.NORMAL) {
					ClothMaterialDetailData data = new ClothMaterialDetailData(clothMaterial, materialResult.getData());
					dataList.add(data);
				}
			}
			if(!dataList.isEmpty()) {
				result.setData(dataList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no cloth material detail data, clothId: " + clothId);
			}
		}else {
			result.setResultCode(clothMaterialResult.getResultCode());
			result.setMessage(clothMaterialResult.getMessage());
		}

		return result;
	}

	@Override
	public GenericResult<List<Cloth>> getNeedPricing() {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		GenericResult<List<Cloth>> allResult = clothService.getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(Cloth cloth : allResult.getData()) {
				if(null != cloth && !isPriced(cloth.getId())) {
					resultList.add(cloth);
				}
			}
			
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("none cloth need to be priced");
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> getNeedCount() {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		GenericResult<List<Cloth>> allResult = clothService.getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(Cloth cloth : allResult.getData()) {
				if(null != cloth && !isCounted(cloth.getId())) {
					resultList.add(cloth);
				}
			}
			
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("none cloth need to be counted");
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}
	

	@Override
	public GenericResult<List<Cloth>> getPriced() {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		GenericResult<List<Cloth>> allResult = clothService.getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(Cloth cloth : allResult.getData()) {
				if(null != cloth && isPriced(cloth.getId())) {
					resultList.add(cloth);
				}
			}
			
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("none cloth need to be priced");
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> getCounted() {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		GenericResult<List<Cloth>> allResult = clothService.getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(Cloth cloth : allResult.getData()) {
				if(null != cloth && isCounted(cloth.getId())) {
					resultList.add(cloth);
				}
			}
			
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("none cloth need to be counted");
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * check if cloth has been priced
	 * true: priced, false:not priced
	 *
	 * 
	 * @param clothId
	 * @return
	 */
	private boolean isPriced(int clothId) {
		boolean result = true; 
		GenericResult<List<ClothMaterial>> allResult = getByCloth(clothId);
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(ClothMaterial clothMaterial : allResult.getData()) {
				if(null == clothMaterial.getPrice()) {
					result = false;
					break;
				}
			}

		}
		return result;
	}

	/**
	 * 
	 * check if cloth has count recorded
	 * true: priced, false:not priced
	 *
	 * 
	 * @param clothId
	 * @return
	 */
	private boolean isCounted(int clothId) {
		boolean result = true; 
		GenericResult<List<ClothMaterial>> allResult = getByCloth(clothId);
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(ClothMaterial clothMaterial : allResult.getData()) {
				if(null != clothMaterial.getPrice() && null == clothMaterial.getCount()) {
					result = false;
					break;
				}
			}

		}
		return result;
	}

}
