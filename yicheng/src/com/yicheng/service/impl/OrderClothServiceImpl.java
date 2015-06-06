package com.yicheng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.OrderClothDao;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.OrderCloth;
import com.yicheng.service.ClothService;
import com.yicheng.service.OrderClothService;
import com.yicheng.service.data.ClothOrderDetailData;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

@Service
public class OrderClothServiceImpl implements OrderClothService {
	
	private static Logger logger = LoggerFactory.getLogger(OrderClothServiceImpl.class);
	
	private static final String ORDER_CLOTH_CACHE_KEY = "order_cloth_cache_%s";
	
	@Autowired
	private OrderClothDao orderClothDao;
	
	@Autowired
	private ClothService clothService;
	
	@Override
	public GenericResult<Integer> create(OrderCloth orderCloth) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = orderClothDao.create(orderCloth);
			result.setData(outId);
			CacheUtil.remove(String.format(ORDER_CLOTH_CACHE_KEY, orderCloth.getOrderNumber()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult update(OrderCloth orderCloth) {
		NoneDataResult result = new NoneDataResult();
		try{
			orderClothDao.update(orderCloth);
			CacheUtil.remove(String.format(ORDER_CLOTH_CACHE_KEY, orderCloth.getOrderNumber()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult delete(int id, String orderNumber) {
		NoneDataResult result = new NoneDataResult();
		try{
			orderClothDao.delete(id);
			CacheUtil.remove(String.format(ORDER_CLOTH_CACHE_KEY, orderNumber));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<OrderCloth>> getByOrderNumber(String orderNumber) {
		GenericResult<List<OrderCloth>> result = new GenericResult<List<OrderCloth>>();
		@SuppressWarnings("unchecked")
		List<OrderCloth> orderClothList = (List<OrderCloth>) CacheUtil.get(String.format(ORDER_CLOTH_CACHE_KEY, orderNumber));
		if(null != orderClothList && !orderClothList.isEmpty()) {
			result.setData(orderClothList);
		}else {
			try {
				orderClothList = orderClothDao.getByOrderNumber(orderNumber);
				if(null != orderClothList && !orderClothList.isEmpty()) {
					result.setData(orderClothList);
					CacheUtil.put(String.format(ORDER_CLOTH_CACHE_KEY, orderNumber), orderClothList);
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
	public GenericResult<ClothOrderDetailData> search(String orderNumber, int clothId) {
		GenericResult<ClothOrderDetailData> result = new GenericResult<ClothOrderDetailData>();
		
		// orderNumber null for test
		if(null == orderNumber) {
			GenericResult<OrderCloth> orderClothResult = getFirstbyCloth(clothId);
			if(orderClothResult.getResultCode() == ResultCode.NORMAL) {
				result.setData(convertClothOrderToDetialData(orderClothResult.getData()));
			}else {
				GenericResult<Cloth> clothResult = clothService.getById(clothId);
				if(clothResult.getResultCode() == ResultCode.NORMAL) {
 					Cloth cloth = clothResult.getData();
					result.setData(new ClothOrderDetailData(null, cloth));
				}
			}
			return result;
		}
		
		@SuppressWarnings("unchecked")
		List<OrderCloth> orderClothList = (List<OrderCloth>) CacheUtil.get(String.format(ORDER_CLOTH_CACHE_KEY, orderNumber));
		if(null != orderClothList && !orderClothList.isEmpty()) {
			for(OrderCloth orderCloth : orderClothList) {
				if(orderCloth.getClothId() == clothId) {
					ClothOrderDetailData data = convertClothOrderToDetialData(orderCloth);
					result.setData(data);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no orderCLoth data, orderNumber: " + orderNumber + ",clothId: " + clothId);
			}
		}else {
			try {
				orderClothList = orderClothDao.getByOrderNumber(orderNumber);
				for(OrderCloth orderCloth : orderClothList) {
					if(orderCloth.getClothId() == clothId) {
						ClothOrderDetailData data = convertClothOrderToDetialData(orderCloth);
						result.setData(data);
						break;
					}
				}
				if(null == result.getData()) {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no orderCLoth data, orderNumber: " + orderNumber + ",clothId: " + clothId);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setMessage(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			}
		}
		return result;
	}
	
	private ClothOrderDetailData convertClothOrderToDetialData(OrderCloth orderCloth) {
		Cloth cloth = null;
		GenericResult<Cloth> clothResult = clothService.getById(orderCloth.getClothId());
		if(clothResult.getResultCode() == ResultCode.NORMAL) {
			cloth = clothResult.getData();
		}
		return new ClothOrderDetailData(orderCloth, cloth);
		
	}

	@Override
	public GenericResult<OrderCloth> getFirstbyCloth(int clothId) {
		GenericResult<OrderCloth> result = new GenericResult<OrderCloth>();
		try {
			OrderCloth orderCloth = orderClothDao.getFirstByCloth(clothId);
			if(null != orderCloth) {
				result.setData(orderCloth);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("ordercloth no data, clothId: " + clothId);
			}
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage("getFirstByCloth error, clothId: " + clothId);
		}
		return result;
	}

}
