package com.yicheng.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yicheng.dao.OperationRecordDao;
import com.yicheng.pojo.OperationRecord;

@Repository
public class OperationRecordDaoImpl extends HibernateDaoBase implements OperationRecordDao {

	@Override
	public int create(OperationRecord operationRecord) {
		return (Integer) getHibernateTemplate().save(operationRecord);
	}

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(new OperationRecord(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationRecord> getAll() {
		return getHibernateTemplate().find("from OperationRecord");
	}

}
