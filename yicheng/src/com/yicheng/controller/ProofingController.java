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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.pojo.Material;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.service.ClothService;
import com.yicheng.service.MaterialService;
import com.yicheng.util.GenericJsonResult;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataJsonResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.Utils;

@Controller
public class ProofingController {
	
	private static Logger logger = LoggerFactory.getLogger(ProofingController.class);
	
	@Autowired
	 private ClothService clothService;
	
	@Autowired
	private ClothMaterialService clothMaterialService;
	
	@Autowired
	private MaterialService materialService;
	
	@RequestMapping(value = "/Proofing/ClothMaterialManage", method = RequestMethod.GET)
	public ModelAndView clothMaterialList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothResult = clothService.getAll();
		
		Map<Cloth, List<ClothMaterial>> clothMaterialMap = new HashMap<Cloth, List<ClothMaterial>>();
		if(clothResult.getResultCode() == ResultCode.NORMAL) {
			for(Cloth cloth : clothResult.getData()) {
				GenericResult<List<ClothMaterial>> clothMaterialResult = clothMaterialService.getByCloth(cloth.getId());
				if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
					clothMaterialMap.put(cloth, clothMaterialResult.getData());
				}
			}
			model.put("clothMaterialMap", clothMaterialMap);
		}else {
			logger.warn("cloth get all exception");
		}
		return new ModelAndView("proofing/cloth_material_manage", "model", model);
	}
	
	@RequestMapping(value = "/Proofing/CreateCloth", method = RequestMethod.GET)
	public ModelAndView createClothGet(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("proofing/cloth_create", "model", null);
	}
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/CreateMaterial", method = RequestMethod.POST)
	public GenericJsonResult<Integer> createMaterialPost(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		int type = Utils.getRequestIntValue(request, "type", true);
		
		// TODO. 1 colorType = 0 for test
		Material material = new Material(name, 0, type);
		GenericResult<Integer> createResult = materialService.create(material);
		return new GenericJsonResult<Integer>(createResult);
	}
	
}