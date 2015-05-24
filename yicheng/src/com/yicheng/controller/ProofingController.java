package com.yicheng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sun.media.jai.opimage.RescaleCRIF;
import com.yicheng.pojo.Cloth;
import com.yicheng.service.ClothService;
import com.yicheng.util.GenericResult;
import com.yicheng.util.ResultCode;

@Controller
public class ProofingController {
	
	private static Logger logger = LoggerFactory.getLogger(ProofingController.class);
	
	@Autowired
	 private ClothService clothService;
	
	@RequestMapping(value = "/Proofing/ClothMaterialManage", method = RequestMethod.GET)
	public ModelAndView clothMaterialList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothResult = clothService.getAll();
		if(clothResult.getResultCode() == ResultCode.NORMAL) {
			
		}else {
			logger.warn("cloth get all exception");
		}
		return new ModelAndView("proofing/cloth_material_manage", "model", model);
	}
	
	@RequestMapping(value = "/Proofing/CreateCloth", method = RequestMethod.GET)
	public ModelAndView createClothGet(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("proofing/cloth_create", "model", null);
	}
}
