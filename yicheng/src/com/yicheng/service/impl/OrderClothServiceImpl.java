package com.yicheng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.OrderClothDao;
import com.yicheng.pojo.OrderCloth;
import com.yicheng.service.OrderClothService;
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
	
	@Override
	public GenericResult<Integer> create(OrderCloth orderCloth) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = orderClothDao.create(orderCloth);
			result.setData(outId);
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
			orderClothDao.delete(id);
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

}
