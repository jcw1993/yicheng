package com.yicheng.dao;

public class DaoFactory {
	
	private static DaoFactory instance = null;
	
	private ClothDao clothDao;
	private ClothMaterialDao clothMaterialDao;
	private ClothColorDao clothColorDao;
	private ClothSizeDao clothSizeDao;
	private MaterialDao materialDao;
	private OrderClothDao orderClothDao;
	private UserDao userDao;
	
	private DaoFactory() {
		clothDao = new ClothDao();
		clothMaterialDao = new ClothMaterialDao();
		clothColorDao = new ClothColorDao();
		clothSizeDao = new ClothSizeDao();
		materialDao = new MaterialDao();
		orderClothDao = new OrderClothDao();
		userDao = new UserDao();
	}
	
	public static DaoFactory getInstance() {
		if(null == instance) {
			instance = new DaoFactory();
		}
		return instance;
	}

	public ClothDao getClothDao() {
		return clothDao;
	}

	public ClothMaterialDao getClothMaterialDao() {
		return clothMaterialDao;
	}

	public ClothColorDao getClothColorDao() {
		return clothColorDao;
	}

	public ClothSizeDao getClothSizeDao() {
		return clothSizeDao;
	}

	public MaterialDao getMaterialDao() {
		return materialDao;
	}

	public OrderClothDao getOrderClothDao() {
		return orderClothDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

}
