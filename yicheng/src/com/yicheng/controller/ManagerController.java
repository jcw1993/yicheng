package com.yicheng.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yicheng.common.BaseController;
import com.yicheng.common.MaterialType;
import com.yicheng.common.Pagination;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothSize;
import com.yicheng.service.ServiceFactory;
import com.yicheng.service.data.ClothDetailData;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.service.data.ClothOrderDetailData;
import com.yicheng.util.GenericResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.Utils;

public class ManagerController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	public void ClothMaterialManage() {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothResult = ServiceFactory.getInstance().getClothService().getNeedPricing();
		
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
			model.put("baseUrl", "ClothMaterialManage");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothes", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get all exception");
		}
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("manager/cloth_material_manage"));
	}
	
	public void SearchInMaterialManage() throws IOException {
		String keyword = getPara("keyword");
		if(StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = ServiceFactory.getInstance().getClothService().searchInAll(keyword);
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
				model.put("baseUrl", "SearchInMaterialManage");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothes", convertClothToDetailData(resultList));
			}else {
				logger.warn("cloth get all exception");
			}
			getRequest().setAttribute("model", model);
			renderJsp(getJsp("manager/cloth_material_manage"));
		}else {	
			getResponse().sendRedirect(getRequest().getContextPath() + "/Error");
			return ;
		}
	}
	
	public void ClothPriceManage() {
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
			model.put("baseUrl", "ClothPriceManage");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothPriced", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get priced exception");
		}
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("manager/cloth_price_manage"));
	}
	
	public void SearchInPriceManage() throws IOException {
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
				model.put("baseUrl", "SearchInPriceManage");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothPriced", convertClothToDetailData(resultList));
			}else {
				logger.warn("cloth get need pricing exception");
			}
			getRequest().setAttribute("model", model);
			renderJsp(getJsp("manager/cloth_price_manage"));
		}else {	
			getResponse().sendRedirect(getRequest().getContextPath() + "/Error");
			return ;
		}
	}
	
	public void ClothCountManage() {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothCountedResult = ServiceFactory.getInstance().getClothService().getCounted();
		if(clothCountedResult.getResultCode() == ResultCode.NORMAL) {
			int pageIndex = Utils.getRequestIntValue(getRequest(), "pageIndex", false);
			int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;
			
			List<Cloth> allList = clothCountedResult.getData();
			int itemCount = allList.size();
			List<Cloth> resultList = new ArrayList<Cloth>();
			for(int i = startIndex; i < startIndex + Pagination.ITEMS_PER_PAGE; i++) {
				if(i < itemCount) {
					resultList.add(allList.get(i));
				}
			}
			model.put("baseUrl", "ClothCountManage");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothCounted", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get counted exception");
		}
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("manager/cloth_count_manage"));
	}
	
	public void SearchInCountManage() throws IOException {
		String keyword = getPara("keyword");
		if(StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = ServiceFactory.getInstance().getClothService().searchInCounted(keyword);
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
				model.put("baseUrl", "SearchInCountManage");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothCounted", convertClothToDetailData(resultList));
			}else {
				logger.warn("cloth get need counted exception");
			}
			getRequest().setAttribute("model", model);
			renderJsp(getJsp("manager/cloth_count_manage"));
		}else {	
			getResponse().sendRedirect(getRequest().getContextPath() + "/Error");
			return ;
		}
	}
	
	public void ClothMaterialDetail() {
		int clothId = getParaToInt("clothId");
		int clothColorId = Utils.getRequestIntValue(getRequest(), "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService().getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothMaterialDetail?clothId=" + clothId);
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("manager/cloth_material_detail"));
		
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
		renderJsp(getJsp("manager/cloth_price_detail"));
		
	}
	
	public void ClothCountDetail() {
		int clothId = getParaToInt("clothId");
		int clothColorId = Utils.getRequestIntValue(getRequest(), "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService().getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfoWithCount(clothId, clothColorId);
		model.put("baseUrl", "ClothCountDetail?clothId=" + clothId);
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("manager/cloth_count_detail"));
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
	
	private Map<String, Object> getClothMaterialInfoWithCount(int clothId, int clothColorId) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<Cloth> clothResult = ServiceFactory.getInstance().getClothService().getById(clothId);
		GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService().getByCloth(clothId);
		
		GenericResult<List<ClothSize>> clothSizeResult = ServiceFactory.getInstance().getClothSizeService().getByClothColor(clothId, clothColorId);
		GenericResult<ClothOrderDetailData> clothOrderDetailResult = ServiceFactory.getInstance().getOrderClothService().search(null, clothId);
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
		
		if(clothSizeResult.getResultCode() == ResultCode.NORMAL) {
			model.put("clothSizes", clothSizeResult.getData());
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
