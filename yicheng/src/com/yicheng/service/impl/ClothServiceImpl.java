package com.yicheng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.ClothDao;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.pojo.ClothSize;
import com.yicheng.pojo.OrderCloth;
import com.yicheng.service.ClothColorService;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.service.ClothService;
import com.yicheng.service.ClothSizeService;
import com.yicheng.service.OrderClothService;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.Utils;

@Service
public class ClothServiceImpl implements ClothService {
	private static final String ALL_CLOTH_CACHE_KEY = "all_cloth_cache";
	
	private static Logger logger = LoggerFactory.getLogger(ClothServiceImpl.class);
	
	@Autowired
	private ClothDao clothDao;
	
	@Autowired
	private ClothColorService clothColorService;
	@Autowired
	private ClothSizeService clothSizeService;
	@Autowired
	private OrderClothService orderClothService;
	@Autowired
	private ClothMaterialService clothMaterialServie;
	
	
	@Override
	public GenericResult<Integer> create(Cloth cloth) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = clothDao.create(cloth);
			result.setData(outId);
			CacheUtil.remove(ALL_CLOTH_CACHE_KEY);
		}catch(DataAccessException e) {
			e.printStackTrace();
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
			CacheUtil.remove(ALL_CLOTH_CACHE_KEY);
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
			CacheUtil.remove(ALL_CLOTH_CACHE_KEY);
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

	@Override
	public GenericResult<Cloth> getById(int clothId) {
		GenericResult<Cloth> result = new GenericResult<Cloth>();
		GenericResult<List<Cloth>> allResult = getAll();
		
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(Cloth cloth : allResult.getData()) {
				if(cloth.getId() == clothId) {
					result.setData(cloth);
					return result;
				}
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		
		return result;
	}
	
	@Override
	public GenericResult<List<Cloth>> getNeedPricing() {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		GenericResult<List<Cloth>> allResult = getAll();
		if (allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for (Cloth cloth : allResult.getData()) {
				if (null != cloth && !isEmpty(cloth.getId())
						&& !isPriced(cloth.getId())) {
					resultList.add(cloth);
				}
			}

			if (!resultList.isEmpty()) {
				result.setData(resultList);
			} else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("none cloth need to be priced");
			}
		} else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> getNeedCount() {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		GenericResult<List<Cloth>> allResult = getAll();
		if (allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for (Cloth cloth : allResult.getData()) {
				if (null != cloth && !isEmpty(cloth.getId())
						&& isPriced(cloth.getId()) 
						&& !(isClothMaterialCounted(cloth.getId()) && isClothSizeCounted(cloth.getId()))) {
					resultList.add(cloth);
				}
			}

			if (!resultList.isEmpty()) {
				result.setData(resultList);
			} else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("none cloth need to be counted");
			}
		} else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> getPriced() {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		GenericResult<List<Cloth>> allResult = getAll();
		if (allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for (Cloth cloth : allResult.getData()) {
				if (null != cloth && !isEmpty(cloth.getId())
						&& isPriced(cloth.getId())) {
					resultList.add(cloth);
				}
			}

			if (!resultList.isEmpty()) {
				result.setData(resultList);
			} else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("none cloth need to be priced");
			}
		} else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> getCounted() {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		GenericResult<List<Cloth>> allResult = getAll();
		if (allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for (Cloth cloth : allResult.getData()) {
				if (null != cloth && !isEmpty(cloth.getId())
						&& isPriced(cloth.getId()) && isClothMaterialCounted(cloth.getId())
						&& isClothSizeCounted(cloth.getId())) {
					resultList.add(cloth);
				}
			}

			if (!resultList.isEmpty()) {
				result.setData(resultList);
			} else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("none cloth need to be counted");
			}
		} else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Transactional
	@Override
	public GenericResult<Integer> copyCloth(int clothId) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		// Step1 copy from cloth info and create cloth, get inserted clothId
		if(clothId <= 0) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			result.setMessage("clothId must be greater than 0");
		}
		
		GenericResult<Cloth> clothResult = getById(clothId);
		int insertClothId = 0;
		if(clothResult.getResultCode() == ResultCode.NORMAL) {
			Cloth cloth = new Cloth(clothResult.getData());
			cloth.setId(0);
			cloth.setCreatedTime(new Date());
			GenericResult<Integer> createResult = create(cloth);
			if(createResult.getResultCode() == ResultCode.NORMAL) {
				insertClothId = createResult.getData();
			}else {
				result.setResultCode(createResult.getResultCode());
				result.setMessage(createResult.getMessage());
				return result;
			}
		}else {
			result.setResultCode(clothResult.getResultCode());
			result.setMessage(clothResult.getMessage());
			return result;
		}
		
		// Step2 copy cloth_color data
		List<Integer> originColorIdList = new ArrayList<Integer>();
		List<Integer> insertColorIdList = new ArrayList<Integer>();
		if(insertClothId > 0) {
			GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				// copy data from list to invoid reference error
				List<ClothColor> clothColorListCopy = Utils.copyFromList(clothColorResult.getData());
				
				for(ClothColor clothColor : clothColorListCopy) {
					int originColorId = clothColor.getId();
					originColorIdList.add(originColorId);
					ClothColor clothColorCopy = new ClothColor(clothColor);
					clothColorCopy.setId(0);
					clothColorCopy.setClothId(insertClothId);
					GenericResult<Integer> createColorResult = clothColorService.create(clothColorCopy);
					if(createColorResult.getResultCode() == ResultCode.NORMAL) {
						insertColorIdList.add(createColorResult.getData());
					}else {
						// 0 : fail
						insertColorIdList.add(0);
						logger.error("copy color error, origin clothColorId: " + originColorId 
								+ ", error message: " + createColorResult.getMessage());
					}
				}
			}
		}
		
		// Step3 copy order_cloth data
		if(insertClothId > 0) {
			GenericResult<OrderCloth> orderClothResult = orderClothService.getFirstbyCloth(clothId);
			if(orderClothResult.getResultCode() == ResultCode.NORMAL) {
				OrderCloth orderClothCopy = new OrderCloth(orderClothResult.getData());
				orderClothCopy.setId(0);
				orderClothCopy.setClothId(insertClothId);
				
				GenericResult<Integer> insertOrderResult = orderClothService.create(orderClothCopy);
				if(insertOrderResult.getResultCode() != ResultCode.NORMAL) {
					logger.error("copy orderCloth error, error messag: "
							+ insertOrderResult.getMessage());
				}
			}
		}
		
		int colorSize = originColorIdList.size();
		
		// Step4 copy cloth_size data
		if(insertClothId > 0 && !originColorIdList.isEmpty() && !insertColorIdList.isEmpty()) {
			for(int index = 0; index < colorSize; index++) {
				int originClothColorId = originColorIdList.get(index);
				GenericResult<List<ClothSize>> clothSizeResult = clothSizeService.getByClothColor(clothId, originClothColorId);
				if(clothSizeResult.getResultCode() == ResultCode.NORMAL) {
					List<ClothSize> clothSizeListCopy = Utils.copyFromList(clothSizeResult.getData());
					for(ClothSize clothSize : clothSizeListCopy) {
						ClothSize clothSizeCopy = new ClothSize(clothSize);
						clothSizeCopy.setId(0);
						clothSizeCopy.setClothId(insertClothId);
						clothSizeCopy.setClothColorId(insertColorIdList.get(index));
						GenericResult<Integer> createSizeResult = clothSizeService.create(clothSizeCopy);
						if(createSizeResult.getResultCode() != ResultCode.NORMAL) {
							logger.error("copy size error, origin clothSizeId: " + clothSize.getId() 
									+ ", error message: " + createSizeResult.getMessage());
						}
					}
				}
			}
		} 
		
		// Step5 copy cloth_material_data
		if(insertClothId > 0 && !originColorIdList.isEmpty() && ! insertColorIdList.isEmpty()) {
			for(int index = 0; index < colorSize; index++) {
				int originClothColorId = originColorIdList.get(index);
				GenericResult<List<ClothMaterial>> clothMaterialResult = clothMaterialServie.getByClothColor(clothId, originClothColorId);
				if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) { 
					List<ClothMaterial> clothMaterialListCopy = Utils.copyFromList(clothMaterialResult.getData());
					for(ClothMaterial clothMaterial : clothMaterialListCopy) {
						ClothMaterial clothMaterialCopy = new ClothMaterial(clothMaterial);
						clothMaterialCopy.setId(0);
						clothMaterialCopy.setClothId(insertClothId);
						clothMaterialCopy.setClothColorId(insertColorIdList.get(index));
						GenericResult<Integer> createClothMaterailResult = clothMaterialServie.create(clothMaterialCopy);
						if(createClothMaterailResult.getResultCode() != ResultCode.NORMAL) {
							logger.error("copy material error, origin clothMaterialId: " + clothMaterial.getId() 
									+ ", error message: " + createClothMaterailResult.getMessage());
						}
					}
				}
			}
		} 
		
		result.setData(insertClothId);
		return result;
	}
	
	/**
	 * 
	 * check if cloth has been priced true: priced, false:not priced
	 *
	 * 
	 * @param clothId
	 * @return
	 */
	private boolean isPriced(int clothId) {
		boolean result = true;
		GenericResult<List<ClothMaterial>> allResult = clothMaterialServie.getByCloth(clothId);
		if (allResult.getResultCode() == ResultCode.NORMAL) {
			for (ClothMaterial clothMaterial : allResult.getData()) {
				if (null == clothMaterial.getPrice()) {
					result = false;
					break;
				}
			}

		}
		return result;
	}

	/**
	 * 
	 * check if cloth has count recorded true: counted, false: not counted
	 *
	 * 
	 * @param clothId
	 * @return
	 */
	private boolean isClothMaterialCounted(int clothId) {
		boolean result = true;
		GenericResult<List<ClothMaterial>> allResult = clothMaterialServie.getByCloth(clothId);
		GenericResult<OrderCloth> orderClothResult = orderClothService
				.getFirstbyCloth(clothId);
		if ((orderClothResult.getResultCode() == ResultCode.NORMAL && null == orderClothResult
				.getData().getCount())
				|| (orderClothResult.getResultCode() != ResultCode.NORMAL)) {
			result = false;
		}
		if (allResult.getResultCode() == ResultCode.NORMAL) {
			for (ClothMaterial clothMaterial : allResult.getData()) {
				if (null == clothMaterial.getCount()) {
					result = false;
					break;
				}
			}

		}
		return result;
	}
	
	private boolean isClothSizeCounted(int clothId) {
		boolean result = true;
		GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
		if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
			for(ClothColor clothColor : clothColorResult.getData()) {
				int clothColorId = clothColor.getId();
				GenericResult<List<ClothSize>> clothSizeResult = clothSizeService.getByClothColor(clothId, clothColorId);
				if(clothSizeResult.getResultCode() == ResultCode.NORMAL) {
					if(clothSizeResult.getData().size() < 6) {
						result = false;
						break;
					}
				}else {
					result = false;
					break;
				}
			}
		}else {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * check if cloth has material related true: empty, false: not empty
	 * 
	 * @param clothId
	 * @return
	 */
	private boolean isEmpty(int clothId) {
		boolean result = true;
		GenericResult<List<ClothMaterial>> allResult = clothMaterialServie.getByCloth(clothId);
		if (allResult.getResultCode() == ResultCode.NORMAL
				&& !allResult.getData().isEmpty()) {
			result = false;
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> searchInAll(String keyword) {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		if(StringUtils.isBlank(keyword)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			result.setMessage("search keyword cannot be null");
		}
		
		GenericResult<List<Cloth>> allResult = getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(Cloth cloth : allResult.getData()) {
				if(cloth.getType().contains(keyword) || cloth.getName().contains(keyword)) {
					resultList.add(cloth);
				}
			}
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no data, keyword: " + keyword);
			}
 		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> searchInNeedPricing(String keyword) {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		if(StringUtils.isBlank(keyword)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			result.setMessage("search keyword cannot be null");
		}
		
		GenericResult<List<Cloth>> allResult = getNeedPricing();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(Cloth cloth : allResult.getData()) {
				if(cloth.getType().contains(keyword) || cloth.getName().contains(keyword)) {
					resultList.add(cloth);
				}
			}
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no data, keyword: " + keyword);
			}
 		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> searchInNeedCount(String keyword) {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		if(StringUtils.isBlank(keyword)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			result.setMessage("search keyword cannot be null");
		}
		
		GenericResult<List<Cloth>> allResult = getNeedCount();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(Cloth cloth : allResult.getData()) {
				if(cloth.getType().contains(keyword) || cloth.getName().contains(keyword)) {
					resultList.add(cloth);
				}
			}
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no data, keyword: " + keyword);
			}
 		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> searchInPriced(String keyword) {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		if(StringUtils.isBlank(keyword)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			result.setMessage("search keyword cannot be null");
		}
		
		GenericResult<List<Cloth>> allResult = getPriced();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(Cloth cloth : allResult.getData()) {
				if(cloth.getType().contains(keyword) || cloth.getName().contains(keyword)) {
					resultList.add(cloth);
				}
			}
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no data, keyword: " + keyword);
			}
 		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Cloth>> searchInCounted(String keyword) {
		GenericResult<List<Cloth>> result = new GenericResult<List<Cloth>>();
		if(StringUtils.isBlank(keyword)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			result.setMessage("search keyword cannot be null");
		}
		
		GenericResult<List<Cloth>> allResult = getCounted();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(Cloth cloth : allResult.getData()) {
				if(cloth.getType().contains(keyword) || cloth.getName().contains(keyword)) {
					resultList.add(cloth);
				}
			}
			if(!resultList.isEmpty()) {
				result.setData(resultList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no data, keyword: " + keyword);
			}
 		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}
}
