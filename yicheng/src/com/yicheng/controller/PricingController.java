package com.yicheng.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.yicheng.common.BaseController;
import com.yicheng.common.MaterialType;
import com.yicheng.common.Pagination;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.service.ServiceFactory;
import com.yicheng.service.data.ClothDetailData;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataJsonResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.Utils;

public class PricingController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(PricingController.class);
	
	public void ClothPriceToProcess() {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothToPriceResult = ServiceFactory.getInstance().getClothService().getNeedPricing();
		if(clothToPriceResult.getResultCode() == ResultCode.NORMAL) {
			int pageIndex = Utils.getRequestIntValue(getRequest(), "pageIndex", false);
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
			model.put("clothToPrice", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get need pricing exception");
		}
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("pricing/cloth_price_to_process"));
	}
	
	public void ClothPriceProcessed() {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothPricedResult = ServiceFactory.getInstance().getClothService().getPriced();
		if(clothPricedResult.getResultCode() == ResultCode.NORMAL) {
			int pageIndex = Utils.getRequestIntValue(getRequest(), "pageIndex", false);
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
			model.put("clothPriced", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get priced exception");
		}
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("pricing/cloth_price_processed"));
	}
	
	@RequestMapping(value = "/SearchInToPrice", method = RequestMethod.GET)
	public ModelAndView searchInToPrice(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keyword = request.getParameter("keyword");
		if(StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = ServiceFactory.getInstance().getClothService().searchInNeedPricing(keyword);
			if(clothResult.getResultCode() == ResultCode.NORMAL) {
				int pageIndex = Utils.getRequestIntValue(request, "pageIndex", false);
				int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;
				
				List<Cloth> allList = clothResult.getData();
				int itemCount = allList.size();
				List<Cloth> resultList = new ArrayList<Cloth>();
				for(int i = startIndex; i < startIndex + Pagination.ITEMS_PER_PAGE; i++) {
					if(i < itemCount) {
						resultList.add(allList.get(i));
					}
				}
				model.put("baseUrl", "SearchInToPrice");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothToPrice", convertClothToDetailData(resultList));
			}else {
				logger.warn("cloth get need pricing exception");
			}
			return new ModelAndView("pricing/cloth_price_to_process", "model", model);
		}else {	
			response.sendRedirect(request.getContextPath() + "/Error");
			return null;
		}
	}
	
	public void SearchInPriced() throws IOException {
		String keyword = getPara("keyword");
		if(StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = ServiceFactory.getInstance().getClothService().searchInPriced(keyword);
			if(clothResult.getResultCode() == ResultCode.NORMAL) {
				int pageIndex = Utils.getRequestIntValue(getRequest(), "pageIndex", false);
				int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;
				
				List<Cloth> allList = clothResult.getData();
				int itemCount = allList.size();
				List<Cloth> resultList = new ArrayList<Cloth>();
				for(int i = startIndex; i < startIndex + Pagination.ITEMS_PER_PAGE; i++) {
					if(i < itemCount) {
						resultList.add(allList.get(i));
					}
				}
				model.put("baseUrl", "SearchInPriced");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothPriced", convertClothToDetailData(resultList));
			}else {
				logger.warn("cloth get need pricing exception");
			}
			getRequest().setAttribute("model", model);
			renderJson(getJsp("pricing/cloth_price_processed"));
		}else {	
			getResponse().sendRedirect(getRequest().getContextPath() + "/Error");
			return ;
		}
	}
	
	public void ClothPriceDetail() {
		int clothId = getParaToInt("clothId");
		int clothColorId = Utils.getRequestIntValue(getRequest(), "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService().getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothPriceDetail?clothId=" + clothId);
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("pricing/cloth_price_detail"));
	}

	public void ClothPriceOperate() {
		int clothId = getParaToInt("clothId");
		int clothColorId = Utils.getRequestIntValue(getRequest(), "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService().getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothPriceOperate?clothId=" + clothId);
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("pricing/cloth_price_operate"));
	}
	
	@Before(POST.class)
	public void ClothMaterialSavePrice() {
		int clothId = getParaToInt("clothId");
		int clothColorId = getParaToInt("clothColorId");
		int clothMaterialId = getParaToInt("clothMaterialId");
		double price = Utils.getRequestDoubleValue(getRequest(), "price", true);
		String remark = getPara("remark");
		
		GenericResult<ClothMaterial> clothMaterialResult = ServiceFactory.getInstance().getClothMaterialService().getById(clothId, clothColorId, clothMaterialId);
		if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
			ClothMaterial clothMaterial = clothMaterialResult.getData();
			clothMaterial.setPrice(price);
			clothMaterial.setRemark(remark);
			NoneDataResult updateResult = ServiceFactory.getInstance().getClothMaterialService().update(clothMaterial);
			renderJson(new NoneDataJsonResult(updateResult));
		}else {
			renderJson(new NoneDataJsonResult(clothMaterialResult));
		}
	}
	
	private Map<String, Object> getClothMaterialInfo(int clothId, int clothColorId) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<Cloth> clothResult = ServiceFactory.getInstance().getClothService().getById(clothId);
		GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService().getByCloth(clothId);
		GenericResult<List<ClothMaterialDetailData>> leatherDetailResult = ServiceFactory.getInstance().getClothMaterialService().getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_LEATHER);
		GenericResult<List<ClothMaterialDetailData>> fabricDetailResult = ServiceFactory.getInstance().getClothMaterialService().getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_FABRIC);
		GenericResult<List<ClothMaterialDetailData>> supportDetailResult = ServiceFactory.getInstance().getClothMaterialService().getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_SUPPORT);
		
		model.put("clothColorId", clothColorId);
		
		if(clothResult.getResultCode() == ResultCode.NORMAL 
				&& clothColorResult.getResultCode() == ResultCode.NORMAL) {
			ClothDetailData cloth = new ClothDetailData(clothResult.getData(), clothColorResult.getData());
			model.put("cloth", cloth);
	 
			model.put("clothColors", clothColorResult.getData());
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
	
	private List<ClothDetailData> convertClothToDetailData(List<Cloth> clothList) {
		if(null == clothList || clothList.isEmpty()) {
			return null;
		}
		List<ClothDetailData> resultList = new ArrayList<ClothDetailData>();
		for(Cloth cloth : clothList) {
			GenericResult<List<ClothColor>> colorResult = ServiceFactory.getInstance().getClothColorService().getByCloth(cloth.getId());
			if(colorResult.getResultCode() == ResultCode.NORMAL) {
				ClothDetailData data = null;
				GenericResult<List<ClothMaterialDetailData>> clothMaterialResult = ServiceFactory.getInstance().getClothMaterialService().getTypeDetailByCloth(cloth.getId(), colorResult.getData().get(0).getId(), MaterialType.MATERIAL_TYPE_LEATHER);
				if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
					data = new ClothDetailData(cloth, colorResult.getData(), clothMaterialResult.getData());
				}else {
					data = new ClothDetailData(cloth, colorResult.getData());
				}
				resultList.add(data);
			}
		}
		return resultList;
	}
}
