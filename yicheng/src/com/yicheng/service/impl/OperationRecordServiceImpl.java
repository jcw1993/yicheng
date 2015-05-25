package com.yicheng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.OperationRecordDao;
import com.yicheng.pojo.OperationRecord;
import com.yicheng.service.OperationRecordService;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

@Service
public class OperationRecordServiceImpl implements OperationRecordService {
	
	private static Logger logger = LoggerFactory.getLogger(OperationRecordServiceImpl.class);
	
	private static final String ALL_OPERATION_RECORD_CACHE = "all_operation_record_cache";
	
	@Autowired
	private OperationRecordDao operationRecordDao;
	
	@Override
	public GenericResult<Integer> create(OperationRecord operationRecord) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = operationRecordDao.create(operationRecord);
			result.setData(outId);
			CacheUtil.remove(ALL_OPERATION_RECORD_CACHE);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult delete(int id) {
		NoneDataResult result = new NoneDataResult();
		try{
			operationRecordDao.delete(id);
			CacheUtil.remove(ALL_OPERATION_RECORD_CACHE);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<OperationRecord>> getAll() {
		GenericResult<List<OperationRecord>> result = new GenericResult<List<OperationRecord>>();
		@SuppressWarnings("unchecked")
		List<OperationRecord> operationRecordList = (List<OperationRecord>) CacheUtil.get(ALL_OPERATION_RECORD_CACHE);
		if(null != operationRecordList && !operationRecordList.isEmpty()) {
			result.setData(operationRecordList);
		}else {
			try {
				operationRecordList = operationRecordDao.getAll();
				if(null != operationRecordList && !operationRecordList.isEmpty()) {
					result.setData(operationRecordList);
					CacheUtil.put(ALL_OPERATION_RECORD_CACHE, operationRecordList);
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
