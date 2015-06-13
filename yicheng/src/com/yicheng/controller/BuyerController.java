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
import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.pojo.OrderCloth;
import com.yicheng.service.ClothColorService;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.service.ClothService;
import com.yicheng.service.OrderClothService;
import com.yicheng.service.data.ClothDetailData;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.service.data.ClothOrderDetailData;
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
	
	@Autowired
	private OrderClothService orderClothService;
	
	@Autowired
	private ClothColorService clothColorService;
	
	@RequestMapping(value = "/Buyer/ClothCountToProcess", method = RequestMethod.GET)
	public ModelAndView clothCountToProcess(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothToCountResult = clothMaterialService.getNeedCount();
		if(clothToCountResult.getResultCode() == ResultCode.NORMAL) {
			int pageIndex = Utils.getRequestIntValue(request, "pageIndex", false);
			int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;
			
			List<Cloth> allList = clothToCountResult.getData();
			int itemCount = allList.size();
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(int i = startIndex; i < startIndex + Pagination.ITEMS_PER_PAGE; i++) {
				if(i < itemCount) {
					resultList.add(allList.get(i));
				}
			}
			model.put("baseUrl", "ClothCountToProcess");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothToCount", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get need count exception");
		}
		
		return new ModelAndView("buyer/cloth_count_to_process", "model", model);
	}
	
	@RequestMapping(value = "/Buyer/ClothCountProcessed", method = RequestMethod.GET)
	public ModelAndView clothCountProcessed(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothCountedResult = clothMaterialService.getCounted();
		if(clothCountedResult.getResultCode() == ResultCode.NORMAL) {
			int pageIndex = Utils.getRequestIntValue(request, "pageIndex", false);
			int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;
			
			List<Cloth> allList = clothCountedResult.getData();
			int itemCount = allList.size();
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(int i = startIndex; i < startIndex + Pagination.ITEMS_PER_PAGE; i++) {
				if(i < itemCount) {
					resultList.add(allList.get(i));
				}
			}
			model.put("baseUrl", "ClothCountProcessed");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothCounted", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get counted exception");
		}
		
		return new ModelAndView("buyer/cloth_count_processed", "model", model);
	}
	
	@RequestMapping(value = "/Buyer/ClothCountDetail", method = RequestMethod.GET)
	public ModelAndView clothCountDetail(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothCountDetail?clothId=" + clothId);
		return new ModelAndView("buyer/cloth_count_detail", "model", model);
	}
	
	@RequestMapping(value = "/Buyer/ClothCountOperate", method = RequestMethod.GET)
	public ModelAndView clothCountOperate(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothCountOperate?clothId=" + clothId);
		return new ModelAndView("buyer/cloth_count_operate", "model", model);
	}
	
	@ResponseBody
	@RequestMapping(value = "/Buyer/OrderClothSaveCount", method = RequestMethod.POST)
	public NoneDataJsonResult saveClothCount(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int buyCount = Utils.getRequestIntValue(request, "buyCount", true);
		
		GenericResult<OrderCloth> orderClothResult = orderClothService.getFirstbyCloth(clothId);
		if(orderClothResult.getResultCode() == ResultCode.NORMAL) {
			OrderCloth orderCloth = orderClothResult.getData();
			orderCloth.setCount(buyCount);
			NoneDataResult updateResult = orderClothService.update(orderCloth);
			return new NoneDataJsonResult(updateResult);
		}else {
			OrderCloth orderCloth = new OrderCloth();
			orderCloth.setClothId(clothId);
			orderCloth.setCount(buyCount);
			GenericResult<Integer> createResult = orderClothService.create(orderCloth);
			return new NoneDataJsonResult(createResult);
		}
	}
	
	// TODO
	@ResponseBody
	@RequestMapping(value = "/Buyer/ClothMaterialSaveCount", method = RequestMethod.POST)
	public NoneDataJsonResult saveClothMaterialCount(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothMaterialId = Utils.getRequestIntValue(request, "clothMaterialId", true);
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", true);
		int count = Utils.getRequestIntValue(request, "count", true);
		String remark = request.getParameter("remark");
		
		GenericResult<ClothMaterial> clothMaterialResult = clothMaterialService.getById(clothId, clothColorId, clothMaterialId);
		if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
			ClothMaterial clothMaterial = clothMaterialResult.getData();
			clothMaterial.setCount(count);
			clothMaterial.setRemark(remark);
			NoneDataResult updateResult = clothMaterialService.update(clothMaterial);
			return new NoneDataJsonResult(updateResult);
		}else {
			return new NoneDataJsonResult(clothMaterialResult);
		}
	}
	
	private Map<String, Object> getClothMaterialInfo(int clothId, int clothColorId) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<Cloth> clothResult = clothService.getById(clothId);
		GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
		
		GenericResult<ClothOrderDetailData> clothOrderDetailResult = orderClothService.search(null, clothId);
		GenericResult<List<ClothMaterialDetailData>> leatherDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_LEATHER);
		GenericResult<List<ClothMaterialDetailData>> fabricDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_FABRIC);
		GenericResult<List<ClothMaterialDetailData>> supportDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_SUPPORT);
		
		model.put("clothColorId", clothColorId);
		if(clothResult.getResultCode() == ResultCode.NORMAL 
				&& clothColorResult.getResultCode() == ResultCode.NORMAL) {
			model.put("clothColors", clothColorResult.getData());
		}
		
		double clothTotalPrice = 0.0;
		boolean hasCount = false;
		
		if(clothOrderDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("clothOrder", clothOrderDetailResult.getData());
			if(null != clothOrderDetailResult.getData().getCount()) {
				hasCount = true;
			}
		}
		if(leatherDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("leatherDetails", leatherDetailResult.getData());
			if(hasCount) {
				for(ClothMaterialDetailData data : leatherDetailResult.getData()) {
					if(null != data.getCount()) {
						clothTotalPrice += (data.getConsumption() * data.getPrice() * data.getCount());
					}
				}
			}
		}
		if(fabricDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("fabricDetails", fabricDetailResult.getData());
			if(hasCount) {
				for(ClothMaterialDetailData data : fabricDetailResult.getData()) {
					if(null != data.getCount()) {
						clothTotalPrice += (data.getConsumption() * data.getPrice() * data.getCount());
					}
				}
			}
		} 
		if(supportDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("supportDetails", supportDetailResult.getData());
			if(hasCount) {
				for(ClothMaterialDetailData data : supportDetailResult.getData()) {
					if(null != data.getCount()) {
						clothTotalPrice += (data.getConsumption() * data.getPrice() * data.getCount());
					}
				}
			}
		}
		
		if(hasCount) {
			model.put("clothTotalPrice", clothTotalPrice * clothOrderDetailResult.getData().getCount());
		}
		
		return model;
	}
	
	private List<ClothDetailData> convertClothToDetailData(List<Cloth> clothList) {
		if(null == clothList || clothList.isEmpty()) {
			return null;
		}
		List<ClothDetailData> resultList = new ArrayList<ClothDetailData>();
		for(Cloth cloth : clothList) {
			GenericResult<List<ClothColor>> colorResult = clothColorService.getByCloth(cloth.getId());
			if(colorResult.getResultCode() == ResultCode.NORMAL) {
				resultList.add(new ClothDetailData(cloth, colorResult.getData()));
			}
		}
		return resultList;
	}
}
