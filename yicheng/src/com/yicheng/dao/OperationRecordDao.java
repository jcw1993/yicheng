package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.OperationRecord;

public interface OperationRecordDao {
	
	public int create(OperationRecord operationRecord);
	
	public void delete(int id);

	public List<OperationRecord> getAll();
}
