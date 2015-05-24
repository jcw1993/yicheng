package com.yicheng.service;

import java.util.List;

import com.yicheng.pojo.User;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataResult;

public interface UserService {
	public GenericResult<List<User>> getAll();
	
	public GenericResult<User> getById(int id);
	
	public GenericResult<User> search(String name, String password, int userType);
	
	public GenericResult<Integer> create(User user);
	
	public NoneDataResult update(User user);
	
	public NoneDataResult delete(int id);
}
