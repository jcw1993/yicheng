package com.yicheng.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.yicheng.pojo.Material;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.service.ClothService;
import com.yicheng.service.MaterialService;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.util.GenericJsonResult;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataJsonResult;
import com.yicheng.util.NoneDataResult;
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
		
		if(clothResult.getResultCode() == ResultCode.NORMAL) {
			model.put("clothes", clothResult.getData());
		}else {
			logger.warn("cloth get all exception");
		}
		return new ModelAndView("proofing/cloth_material_manage", "model", model);
	}
	
	@RequestMapping(value = "/Proofing/ClothMaterialDetail", method = RequestMethod.GET)
	public ModelAndView clothMaterialDetail(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		Map<String, Object> model = getClothMaterialInfo(clothId);
		return new ModelAndView("proofing/cloth_material_detail", "model", model);
	}
	
	@RequestMapping(value = "/Proofing/CreateCloth", method = RequestMethod.GET)
	public ModelAndView createClothGet(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("proofing/cloth_create", "model", null);
	}
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/CreateCloth", method = RequestMethod.POST)
	public GenericJsonResult<Integer> createClothPost(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		type = type.trim();
		name = name.trim();
		
		// set colorType = 0 for test
		int colorType = 0;
		
		Cloth cloth = new Cloth(type, name, colorType, new Date());
		GenericResult<Integer> createResult = clothService.create(cloth);
		return new GenericJsonResult<Integer>(createResult);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/GetAllMaterialByType", method = RequestMethod.GET)
	public GenericJsonResult<List<Material>> getAllMaterialName(HttpServletRequest request, HttpServletResponse response) {
		int type = Utils.getRequestIntValue(request, "type", false);
		GenericResult<List<Material>> materialResult = materialService.getByType(type);
		return new GenericJsonResult<List<Material>>(materialResult);
	}
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/CreateClothMaterial", method = RequestMethod.POST)
	public GenericJsonResult<Integer> createClothMaterialPost(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int materialId = Utils.getRequestIntValue(request, "materialId", true);
		String part = request.getParameter("part");
		String unitName = request.getParameter("unitName");
		double consumption = Utils.getRequestDoubleValue(request, "consumption", true);
		double estimatedPrice = Utils.getRequestDoubleValue(request, "estimatedPrice", false);
		String remark = request.getParameter("remark");
		
		ClothMaterial clothMaterial = new ClothMaterial(clothId, materialId, part, unitName, consumption, estimatedPrice, null,
				null, null, null, remark);
		GenericResult<Integer> createResult = clothMaterialService.create(clothMaterial);
		return new GenericJsonResult<Integer>(createResult);
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
	
	@RequestMapping(value = "/Proofing/ClothMaterialOperate", method = RequestMethod.GET)
	public ModelAndView clothMaterialOperate(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		Map<String, Object> model = getClothMaterialInfo(clothId);
		return new ModelAndView("proofing/cloth_material_operate", "model", model);
	}
	
	@RequestMapping(value = "/Proofing/ClothMaterialCreate", method = RequestMethod.GET)
	public ModelAndView clothMaterialCreate(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		Map<String, Object> model = getClothMaterialInfo(clothId);
		return new ModelAndView("proofing/cloth_material_create", "model", model);
	}
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/DeleteClothMaterial", method = RequestMethod.GET)
	 public NoneDataJsonResult deleteClothMaterial(HttpServletRequest request, HttpServletResponse response) {
		int clothMaterialId = Utils.getRequestIntValue(request, "clothMaterialId", true);
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		NoneDataResult result = clothMaterialService.delete(clothMaterialId, clothId);
		return new NoneDataJsonResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/SaveClothMaterialEstimatedPrice", method = RequestMethod.POST)
	 public NoneDataJsonResult saveClothMaterialEstimatedPrice(HttpServletRequest request, HttpServletResponse response) {
		int clothMaterialId = Utils.getRequestIntValue(request, "clothMaterialId", true);
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		double estimatedPrice = Utils.getRequestDoubleValue(request, "estimatedPrice", true);
		GenericResult<ClothMaterial> clothMaterialResult = clothMaterialService.getById(clothId, clothMaterialId);
		if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
			ClothMaterial clothMaterial = clothMaterialResult.getData();
			clothMaterial.setEstimatedPrice(estimatedPrice);
			return new NoneDataJsonResult(clothMaterialService.update(clothMaterial));
		}else {
			return new NoneDataJsonResult(clothMaterialResult);
		}
		
	}
	
	@RequestMapping(value = "/Proofing/MaterialManage", method = RequestMethod.GET)
	public ModelAndView materialManage(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Material>> materialResult = materialService.getAll();
		
		if(materialResult.getResultCode() == ResultCode.NORMAL) {
			List<Material> leatherList = new ArrayList<Material>();
			List<Material> fabricList = new ArrayList<Material>();
			List<Material> supportList = new ArrayList<Material>();
			
			for(Material material : materialResult.getData()) {
				if(material.getType() == MaterialType.MATERIAL_TYPE_LEATHER) {
					leatherList.add(material);
				}else if(material.getType() == MaterialType.MATERIAL_TYPE_FABRIC) {
					fabricList.add(material);
				}else if(material.getType() == MaterialType.MATERIAL_TYPE_SUPPORT) { 
					supportList.add(material);
				}
			}
			if(!leatherList.isEmpty()) {
				model.put("leathers", leatherList);
			}
			if(!fabricList.isEmpty()) {
				model.put("fabrics", fabricList);
			}
			if(!supportList.isEmpty()) {
				model.put("supports", supportList);
			}
		}else {
			logger.warn("material get all exception");
		}
		return new ModelAndView("proofing/material_manage", "model", model);
	}
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/CreateMaterial", method = RequestMethod.POST)
	public GenericJsonResult<Integer> createMaterial(HttpServletRequest request, HttpServletResponse response) {
		int type = Utils.getRequestIntValue(request, "type", true);
		String name = request.getParameter("name");
		name = name.trim();
		// color 0
		int colorType = 0;
		
		Material material = new Material(name, colorType, type);
		GenericResult<Integer> createResult = materialService.create(material);
		return new GenericJsonResult<Integer>(createResult);
	}
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/DeleteMaterial", method = RequestMethod.POST)
	public NoneDataJsonResult deleteMaterial(HttpServletRequest request, HttpServletResponse response) {
		int materialId = Utils.getRequestIntValue(request, "materialId", true);
		return new NoneDataJsonResult(materialService.delete(materialId));
	}
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/UpdateMaterial", method = RequestMethod.POST)
	public NoneDataJsonResult updateMaterial(HttpServletRequest request, HttpServletResponse response) {
		int materialId = Utils.getRequestIntValue(request, "materialId", true);
		String name = request.getParameter("name");
		name = name.trim();
		
		GenericResult<Material> materialResult = materialService.getById(materialId);
		if(materialResult.getResultCode() == ResultCode.NORMAL) {
			Material material = materialResult.getData();
			material.setName(name);
			return new NoneDataJsonResult(materialService.update(material));
		}else {
			return new NoneDataJsonResult(materialResult);
		}
	}
	
	private Map<String, Object> getClothMaterialInfo(int clothId) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<Cloth> clothResult = clothService.getById(clothId);
		GenericResult<List<ClothMaterialDetailData>> leatherDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, MaterialType.MATERIAL_TYPE_LEATHER);
		GenericResult<List<ClothMaterialDetailData>> fabricDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, MaterialType.MATERIAL_TYPE_FABRIC);
		GenericResult<List<ClothMaterialDetailData>> supportDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, MaterialType.MATERIAL_TYPE_SUPPORT);
		
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
