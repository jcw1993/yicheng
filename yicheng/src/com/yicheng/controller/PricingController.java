package com.yicheng.controller;

import java.util.ArrayList;
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
import com.yicheng.common.Pagination;
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
public class PricingController {
	
	private static Logger logger = LoggerFactory.getLogger(PricingController.class);
	
	@Autowired
	private ClothService clothService;
	
	@Autowired
	private ClothMaterialService clothMaterialService;
	
	@RequestMapping(value = "/Pricing/ClothPriceToProcess", method = RequestMethod.GET)
	public ModelAndView clothPriceToProcess(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothToPriceResult = clothMaterialService.getNeedPricing();
		if(clothToPriceResult.getResultCode() == ResultCode.NORMAL) {
			int pageIndex = Utils.getRequestIntValue(request, "pageIndex", false);
			int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;
			
			List<Cloth> allList = clothToPriceResult.getData();
			int itemCount = allList.size();
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(int i = startIndex; i < startIndex + Pagination.ITEMS_PER_PAGE; i++) {
				if(i < itemCount) {
					resultList.add(allList.get(i));
				}
			}
			model.put("baseUrl", "ClothPriceToProcess");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothToPrice", resultList);
		}else {
			logger.warn("cloth get need pricing exception");
		}
		
		return new ModelAndView("pricing/cloth_price_to_process", "model", model);
	}
	
	@RequestMapping(value = "/Pricing/ClothPriceProcessed", method = RequestMethod.GET)
	public ModelAndView clothPriceProcessed(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothPricedResult = clothMaterialService.getPriced();
		if(clothPricedResult.getResultCode() == ResultCode.NORMAL) {
			int pageIndex = Utils.getRequestIntValue(request, "pageIndex", false);
			int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;
			
			List<Cloth> allList = clothPricedResult.getData();
			int itemCount = allList.size();
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(int i = startIndex; i < startIndex + Pagination.ITEMS_PER_PAGE; i++) {
				if(i < itemCount) {
					resultList.add(allList.get(i));
				}
			}
			model.put("baseUrl", "ClothPriceProcessed");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothPriced", resultList);
		}else {
			logger.warn("cloth get priced exception");
		}
		return new ModelAndView("pricing/cloth_price_processed", "model", model);
	}
	
	@RequestMapping(value = "/Pricing/ClothPriceDetail", method = RequestMethod.GET)
	public ModelAndView clothPriceDetail(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		Map<String, Object> model = getClothMaterialInfo(clothId);
		return new ModelAndView("pricing/cloth_price_detail", "model", model);
	}

	@RequestMapping(value = "/Pricing/ClothPriceOperate", method = RequestMethod.GET)
	public ModelAndView clothPriceOperate(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		Map<String, Object> model = getClothMaterialInfo(clothId);
		return new ModelAndView("pricing/cloth_price_operate", "model", model);
	}
	
	@ResponseBody
	@RequestMapping(value = "/Pricing/ClothMaterialSavePrice", method = RequestMethod.POST)
	public NoneDataJsonResult saveClothMaterialPrice(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothMaterialId = Utils.getRequestIntValue(request, "clothMaterialId", true);
		double price = Utils.getRequestDoubleValue(request, "price", true);
		String remark = request.getParameter("remark");
		
		GenericResult<ClothMaterial> clothMaterialResult = clothMaterialService.getById(clothId, clothMaterialId);
		if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
			ClothMaterial clothMaterial = clothMaterialResult.getData();
			clothMaterial.setPrice(price);
			clothMaterial.setRemark(remark);
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
