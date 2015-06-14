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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yicheng.common.MaterialType;
import com.yicheng.common.Pagination;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothSize;
import com.yicheng.service.ClothColorService;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.service.ClothService;
import com.yicheng.service.ClothSizeService;
import com.yicheng.service.ContentService;
import com.yicheng.service.OrderClothService;
import com.yicheng.service.data.ClothDetailData;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.service.data.ClothOrderDetailData;
import com.yicheng.util.GenericResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.Utils;

@Controller
public class ManagerController {
	
	private static Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	@Autowired
	private ClothMaterialService clothMaterialService;
	
	@Autowired
	private OrderClothService orderClothService;
	
	@Autowired
	private ClothService clothService;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private ClothColorService clothColorService;
	
	@Autowired
	private ClothSizeService clothSizeService;
	
	@RequestMapping(value = "/Manager/ClothMaterialManage", method = RequestMethod.GET)
	public ModelAndView clothMaterialManage(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothResult = clothService.getNeedPricing();
		
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
			model.put("baseUrl", "ClothMaterialManage");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothes", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get all exception");
		}
		return new ModelAndView("manager/cloth_material_manage", "model", model);
	}
	
	@RequestMapping(value = "/Manager/SearchInMaterialManage", method = RequestMethod.GET)
	public ModelAndView searchInAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keyword = request.getParameter("keyword");
		if(StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = clothService.searchInAll(keyword);
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
				model.put("baseUrl", "SearchInMaterialManage");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothes", convertClothToDetailData(resultList));
			}else {
				logger.warn("cloth get all exception");
			}
			return new ModelAndView("manager/cloth_material_manage", "model", model);
		}else {	
			response.sendRedirect(request.getContextPath() + "/Error");
			return null;
		}
	}
	
	@RequestMapping(value = "/Manager/ClothPriceManage", method = RequestMethod.GET)
	public ModelAndView clothPriceManage(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothPricedResult = clothService.getPriced();
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
			model.put("baseUrl", "ClothPriceManage");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothPriced", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get priced exception");
		}
		return new ModelAndView("manager/cloth_price_manage", "model", model);
	}
	
	@RequestMapping(value = "/Manager/SearchInPriceManage", method = RequestMethod.GET)
	public ModelAndView searchInPriced(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keyword = request.getParameter("keyword");
		if(StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = clothService.searchInPriced(keyword);
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
				model.put("baseUrl", "SearchInPriceManage");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothPriced", convertClothToDetailData(resultList));
			}else {
				logger.warn("cloth get need pricing exception");
			}
			return new ModelAndView("manager/cloth_price_manage", "model", model);
		}else {	
			response.sendRedirect(request.getContextPath() + "/Error");
			return null;
		}
	}
	
	@RequestMapping(value = "/Manager/ClothCountManage", method = RequestMethod.GET)
	public ModelAndView clothCountManage(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothCountedResult = clothService.getCounted();
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
			model.put("baseUrl", "ClothCountManage");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothCounted", convertClothToDetailData(resultList));
		}else {
			logger.warn("cloth get counted exception");
		}
		return new ModelAndView("manager/cloth_count_manage", "model", model);
	}
	
	@RequestMapping(value = "/Manager/SearchInCountManage", method = RequestMethod.GET)
	public ModelAndView searchInCounted(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keyword = request.getParameter("keyword");
		if(StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = clothService.searchInCounted(keyword);
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
				model.put("baseUrl", "SearchInCountManage");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothCounted", convertClothToDetailData(resultList));
			}else {
				logger.warn("cloth get need counted exception");
			}
			return new ModelAndView("manager/cloth_count_manage", "model", model);
		}else {	
			response.sendRedirect(request.getContextPath() + "/Error");
			return null;
		}
	}
	
	@RequestMapping(value = "/Manager/ClothMaterialDetail", method = RequestMethod.GET)
	public ModelAndView clothMaterialDetail(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothMaterialDetail?clothId=" + clothId);
		return new ModelAndView("manager/cloth_material_detail", "model", model);
		
	}
	
	@RequestMapping(value = "/Manager/ClothPriceDetail", method = RequestMethod.GET)
	public ModelAndView clothPriceDetail(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothPriceDetail?clothId=" + clothId);
		return new ModelAndView("manager/cloth_price_detail", "model", model);
	}
	
	@RequestMapping(value = "/Manager/ClothCountDetail", method = RequestMethod.GET)
	public ModelAndView clothCountDetail(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfoWithCount(clothId, clothColorId);
		model.put("baseUrl", "ClothCountDetail?clothId=" + clothId);
		return new ModelAndView("manager/cloth_count_detail", "model", model);
	}
	
	private Map<String, Object> getClothMaterialInfo(int clothId, int clothColorId) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<Cloth> clothResult = clothService.getById(clothId);
		GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
		GenericResult<List<ClothMaterialDetailData>> leatherDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_LEATHER);
		GenericResult<List<ClothMaterialDetailData>> fabricDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_FABRIC);
		GenericResult<List<ClothMaterialDetailData>> supportDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_SUPPORT);
		
		model.put("clothColorId", clothColorId);
		
		if(clothResult.getResultCode() == ResultCode.NORMAL 
				&& clothColorResult.getResultCode() == ResultCode.NORMAL) {
			ClothDetailData cloth = new ClothDetailData(clothResult.getData(), clothColorResult.getData());
			if(null != cloth.getImageId() && cloth.getImageId() != 0) {
				GenericResult<String> contentResult = contentService.getContentCodeById(cloth.getImageId());
				if(contentResult.getResultCode() == ResultCode.NORMAL) {
					cloth.setImageContent(contentResult.getData());
				}
				
			}
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
		GenericResult<Cloth> clothResult = clothService.getById(clothId);
		GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
		
		GenericResult<List<ClothSize>> clothSizeResult = clothSizeService.getByClothColor(clothId, clothColorId);
		GenericResult<ClothOrderDetailData> clothOrderDetailResult = orderClothService.search(null, clothId);
		GenericResult<List<ClothMaterialDetailData>> leatherDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_LEATHER);
		GenericResult<List<ClothMaterialDetailData>> fabricDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_FABRIC);
		GenericResult<List<ClothMaterialDetailData>> supportDetailResult = clothMaterialService.getTypeDetailByCloth(clothId, clothColorId, MaterialType.MATERIAL_TYPE_SUPPORT);
		
		model.put("clothColorId", clothColorId);
		if(clothResult.getResultCode() == ResultCode.NORMAL 
				&& clothColorResult.getResultCode() == ResultCode.NORMAL) {
			ClothDetailData cloth = new ClothDetailData(clothResult.getData(), clothColorResult.getData());
			if(null != cloth.getImageId() && cloth.getImageId() != 0) {
				GenericResult<String> contentResult = contentService.getContentCodeById(cloth.getImageId());
				if(contentResult.getResultCode() == ResultCode.NORMAL) {
					cloth.setImageContent(contentResult.getData());
				}
				
			}
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
			GenericResult<List<ClothColor>> colorResult = clothColorService.getByCloth(cloth.getId());
			if(colorResult.getResultCode() == ResultCode.NORMAL) {
				resultList.add(new ClothDetailData(cloth, colorResult.getData()));
			}
		}
		return resultList;
	}
}
