package com.yicheng.service;

public class ServiceFactory {

	private static ServiceFactory instance = null;
	
	private ClothService clothService;
	private ClothMaterialService clothMaterialService;
	private ClothColorService clothColorService;
	private ClothSizeService clothSizeService;
	private MaterialService  materialService;
	private UserService userService;
	private OrderClothService orderClothService;
	
	private ServiceFactory() {
		clothService = new ClothService();
		clothMaterialService = new ClothMaterialService();
		clothColorService = new ClothColorService();
		clothSizeService  = new ClothSizeService();
		materialService = new MaterialService();
		userService = new UserService();
		orderClothService = new OrderClothService();
	}

	public static ServiceFactory getInstance() {
		if(null == instance) {
			instance = new ServiceFactory();
		}
		return instance;
	}

	public ClothService getClothService() {
		return clothService;
	}

	public ClothMaterialService getClothMaterialService() {
		return clothMaterialService;
	}

	public ClothColorService getClothColorService() {
		return clothColorService;
	}

	public ClothSizeService getClothSizeService() {
		return clothSizeService;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public UserService getUserService() {
		return userService;
	}

	public OrderClothService getOrderClothService() {
		return orderClothService;
	}
	
}
