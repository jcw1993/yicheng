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

import com.yicheng.common.MaterialType;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.service.ClothService;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataJsonResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.Utils;

@Controller
public class BuyerController {
	
	private static Logger logger = LoggerFactory.getLogger(BuyerController.class);
	
	@Autowired
	private ClothService clothService;
	
	@Autowired
	private ClothMaterialService clothMaterialService;
	
	@RequestMapping(value = "/Buyer/ClothCountManage", method = RequestMethod.GET)
	public ModelAndView clothMaterialList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothToCountResult = clothMaterialService.getNeedCount();
		if(clothToCountResult.getResultCode() == ResultCode.NORMAL) {
			model.put("clothToCount", clothToCountResult.getData());
		}else {
			logger.warn("cloth get need count exception");
		}
		
		GenericResult<List<Cloth>> clothCountedResult = clothMaterialService.getCounted();
		if(clothCountedResult.getResultCode() == ResultCode.NORMAL) {
			model.put("clothCounted", clothCountedResult.getData());
		}else {
			logger.warn("cloth get counted exception");
		}
		return new ModelAndView("buyer/cloth_count_manage", "model", model);
	}
	
	@RequestMapping(value = "/Buyer/ClothCountDetail", method = RequestMethod.GET)
	public ModelAndView clothMaterialDetail(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		Map<String, Object> model = getClothMaterialInfo(clothId);
		return new ModelAndView("pricing/cloth_count_detail", "model", model);
	}
	
	@RequestMapping(value = "/Buyer/ClothCountOperate", method = RequestMethod.GET)
	public ModelAndView clothMaterialOperate(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		Map<String, Object> model = getClothMaterialInfo(clothId);
		return new ModelAndView("pricing/cloth_count_operate", "model", model);
	}
	
	// TODO
	@ResponseBody
	@RequestMapping(value = "/Buyer/ClothMaterialSaveCount", method = RequestMethod.POST)
	public NoneDataJsonResult saveClothMaterialCount(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothMaterialId = Utils.getRequestIntValue(request, "clothMaterialId", true);
		int count = Utils.getRequestIntValue(request, "count", true);
		
		GenericResult<ClothMaterial> clothMaterialResult = clothMaterialService.getById(clothId, clothMaterialId);
		if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
			ClothMaterial clothMaterial = clothMaterialResult.getData();
			clothMaterial.setCount(count);
			NoneDataResult updateResult = clothMaterialService.update(clothMaterial);
			return new NoneDataJsonResult(updateResult);
		}else {
			return new NoneDataJsonResult(clothMaterialResult);
		}
	}
	
	private Map<String, Object> getClothMaterialInfo(int clothId) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<Cloth> clothResult = clothService.getById(clothId);
		GenericResult<List<ClothMaterialDetailData>> leatherDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, MaterialType.MATERIAL_TYPE_LEATHER);
		GenericResult<List<ClothMaterialDetailData>> fabricDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, MaterialType.MATERIAL_TYPE_FABRIC);
		GenericResult<List<ClothMaterialDetailData>> supportDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, MaterialType.MATERIAL_TYPE_LEATHER);
		
		if(clothResult.getResultCode() == ResultCode.NORMAL) {
			model.put("cloth", clothResult.getData());
		}
		if(leatherDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("leatherDetails", leatherDetailResult.getData());
		}
		if(fabricDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("fabricDetails", fabricDetailResult.getData());
		}
		if(supportDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("supportDetails", supportDetailResult.getData());
		}
		return model;
	}
}
