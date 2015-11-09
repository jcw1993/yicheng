package com.yicheng.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.yicheng.dao.OrderClothDao;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.OrderCloth;
import com.yicheng.service.data.ClothOrderDetailData;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

public class OrderClothService {
	
	private static Logger logger = LoggerFactory.getLogger(OrderClothService.class);
	
	private static final String ORDER_CLOTH_CACHE_KEY = "order_cloth_cache_%s";
	
	public GenericResult<Integer> create(OrderCloth orderCloth) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			OrderClothDao dao = orderCloth.toDao();
			dao.save();
			result.setData(dao.getInt("id"));
			CacheUtil.remove(String.format(ORDER_CLOTH_CACHE_KEY, orderCloth.getOrderNumber()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public NoneDataResult update(OrderCloth orderCloth) {
		NoneDataResult result = new NoneDataResult();
		try{
			orderCloth.toDao().update();
			CacheUtil.remove(String.format(ORDER_CLOTH_CACHE_KEY, orderCloth.getOrderNumber()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	public NoneDataResult delete(int id, String orderNumber) {
		NoneDataResult result = new NoneDataResult();
		try{
			OrderClothDao.dao.deleteById(id);
			CacheUtil.remove(String.format(ORDER_CLOTH_CACHE_KEY, orderNumber));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public GenericResult<List<OrderCloth>> getByOrderNumber(String orderNumber) {
		GenericResult<List<OrderCloth>> result = new GenericResult<List<OrderCloth>>();
		@SuppressWarnings("unchecked")
		List<OrderCloth> orderClothList = (List<OrderCloth>) CacheUtil.get(String.format(ORDER_CLOTH_CACHE_KEY, orderNumber));
		if(null != orderClothList && !orderClothList.isEmpty()) {
			result.setData(orderClothList);
		}else {
			try {
				List<OrderClothDao> daos = OrderClothDao.getByOrderNumber(orderNumber);
				if(null != daos && !daos.isEmpty()) {
					orderClothList = new ArrayList<OrderCloth>();
					for(OrderClothDao dao : daos) {
						orderClothList.add(new OrderCloth(dao));
					}
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

	public GenericResult<ClothOrderDetailData> search(String orderNumber, int clothId) {
		GenericResult<ClothOrderDetailData> result = new GenericResult<ClothOrderDetailData>();
		
		// orderNumber null for test
		if(null == orderNumber) {
			GenericResult<OrderCloth> orderClothResult = getFirstbyCloth(clothId);
			if(orderClothResult.getResultCode() == ResultCode.NORMAL) {
				result.setData(convertClothOrderToDetialData(orderClothResult.getData()));
			}else {
				GenericResult<Cloth> clothResult = ServiceFactory.getInstance().getClothService().getById(clothId);
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
				List<OrderClothDao> daos = OrderClothDao.getByOrderNumber(orderNumber);
				for(OrderClothDao dao : daos) {
					if(dao.getInt("cloth_id") == clothId) {
						ClothOrderDetailData data = convertClothOrderToDetialData(new OrderCloth(dao));
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
		GenericResult<Cloth> clothResult = ServiceFactory.getInstance().getClothService().getById(orderCloth.getClothId());
		if(clothResult.getResultCode() == ResultCode.NORMAL) {
			cloth = clothResult.getData();
		}
		return new ClothOrderDetailData(orderCloth, cloth);
		
	}

	public GenericResult<OrderCloth> getFirstbyCloth(int clothId) {
		GenericResult<OrderCloth> result = new GenericResult<OrderCloth>();
		try {
			OrderClothDao dao = OrderClothDao.getFirstByCloth(clothId);
			if(null != dao) {
				result.setData(new OrderCloth(dao));
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
