package com.yicheng.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.dao.UserDao;
import com.yicheng.pojo.User;
import com.yicheng.service.UserService;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;

@Service
public class UserServiceImpl implements UserService {
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final String ALL_USER_CACHE_KEY = "all_user_cache";
	
	@Autowired
	private UserDao userDao;
	
	public UserServiceImpl() {}

	@Override
	public GenericResult<List<User>> getAll() {
		GenericResult<List<User>> result = new GenericResult<List<User>>();
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) CacheUtil.get(ALL_USER_CACHE_KEY);
		if(null != userList && !userList.isEmpty()) {
			result.setData(userList);
		}else {
			try {
				userList = userDao.getAll();
				if(null != userList && !userList.isEmpty()) {
					result.setData(userList);
					CacheUtil.put(ALL_USER_CACHE_KEY, userList);
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
	public GenericResult<User> getById(int id) {
		GenericResult<User> result = new GenericResult<User>();
		GenericResult<List<User>> allResult = getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(User User : allResult.getData()) {
				if(User.getId() == id) {
					result.setData(User);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no User data, User id: " + id);
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		
		return result;
	}

	@Override
	public GenericResult<User> search(String name,
			String password, int userType) {
		GenericResult<User> result = new GenericResult<User>();
		if(StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			return result;
		}
		
		GenericResult<List<User>> allResult = getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(User user : allResult.getData()) {
				if(user.getName().equals(name) && user.getPassword().equals(password)
						&& user.getType() == userType) {
					result.setData(user);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no User data, User name: " + name);
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		
		return result;
	}

	@Override
	public GenericResult<Integer> create(User user) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = userDao.create(user);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoneDataResult update(User user) {
		NoneDataResult result = new NoneDataResult();
		try{
			userDao.update(user);
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
			userDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

}
