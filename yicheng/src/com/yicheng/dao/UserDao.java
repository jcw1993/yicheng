package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.User;

public interface UserDao {
	public List<User> getAll();
	
	public int create(User user);
	
	public void update(User user);
	
	public void delete(int id);
}
