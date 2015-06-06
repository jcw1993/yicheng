package com.yicheng.dao;

import java.util.List;

import com.yicheng.pojo.OrderCloth;

public interface OrderClothDao {
	
	public int create(OrderCloth orderCloth);
	
	public void update(OrderCloth orderCloth);
	
	public void delete(int id);
	
	public List<OrderCloth> getByOrderNumber(String orderNumber);
	
	public OrderCloth getFirstByCloth(int clothId);

}
