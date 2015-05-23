package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.OperationRecord;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface OperationRecordService {
	public GenericResult<Integer> create(OperationRecord operationRecord);
	
	public NoneDataResult delete(int id);

	public GenericResult<List<OperationRecord>> getAll();
}
