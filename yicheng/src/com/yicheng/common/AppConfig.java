package com.yicheng.common;

import java.io.File;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.yicheng.controller.BuyerController;
import com.yicheng.controller.ExportController;
import com.yicheng.controller.HomeController;
import com.yicheng.controller.IdentityController;
import com.yicheng.controller.ManagerController;
import com.yicheng.controller.PageController;
import com.yicheng.controller.PricingController;
import com.yicheng.controller.ProofingController;
import com.yicheng.dao.ClothColorDao;
import com.yicheng.dao.ClothDao;
import com.yicheng.dao.ClothMaterialDao;
import com.yicheng.dao.ClothSizeDao;
import com.yicheng.dao.MaterialDao;
import com.yicheng.dao.OrderClothDao;
import com.yicheng.dao.UserDao;

public class AppConfig extends JFinalConfig {
	
	private static String CONFIG_BASE_DIR = "WebRoot/WEB-INF/config/" ;
	
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setUploadedFileSaveDirectory("/Users/jinchengwei/Desktop/cos_share_upload/");
	}

	public void configRoute(Routes me) {
		me.add("/Proofing", ProofingController.class);
		me.add("/Pricing", PricingController.class);
		me.add("/Buyer", BuyerController.class);
		me.add("/User", IdentityController.class);
		me.add("/Page", PageController.class);
		me.add("/Home", HomeController.class);
		me.add("/Exoprt", ExportController.class);
		me.add("/Manager", ManagerController.class);
	}

	public void configPlugin(Plugins me) {
		C3p0Plugin c3p0Plugin = new C3p0Plugin(new File(CONFIG_BASE_DIR + "c3p0.properties"));
		me.add(c3p0Plugin);
		ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
		me.add(activeRecordPlugin);
		activeRecordPlugin.addMapping("user", UserDao.class);
		activeRecordPlugin.addMapping("cloth", ClothDao.class);
		activeRecordPlugin.addMapping("material", MaterialDao.class);
		activeRecordPlugin.addMapping("cloth_material", ClothMaterialDao.class);
		activeRecordPlugin.addMapping("cloth_size", ClothSizeDao.class);
		activeRecordPlugin.addMapping("cloth_color", ClothColorDao.class);
		activeRecordPlugin.addMapping("order_Cloth", OrderClothDao.class);
		
//		me.add(new EhCachePlugin());
	}

	public void configInterceptor(Interceptors me) {
//		me.add(new AuthInterceptor());
	}

	public void configHandler(Handlers me) {
	}
}