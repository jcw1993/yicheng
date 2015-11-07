package com.yicheng.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yicheng.common.Config;
import com.yicheng.common.MaterialType;
import com.yicheng.common.Pagination;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.pojo.Content;
import com.yicheng.pojo.Material;
import com.yicheng.pojo.OrderCloth;
import com.yicheng.service.ClothColorService;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.service.ClothService;
import com.yicheng.service.ClothSizeService;
import com.yicheng.service.ContentService;
import com.yicheng.service.MaterialService;
import com.yicheng.service.OrderClothService;
import com.yicheng.service.data.ClothDetailData;
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
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private OrderClothService orderClothService;
	
	@Autowired
	private ClothColorService clothColorService;
	
	@Autowired
	private ContentService contentServie;
	
	@Autowired
	private ClothSizeService clothSizeService;
	
	@RequestMapping(value = "/Proofing/ClothMaterialManage", method = RequestMethod.GET)
	public ModelAndView clothMaterialList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothResult = clothService.getAll();
		
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
		return new ModelAndView("proofing/cloth_material_manage", "model", model);
	}
	
	@RequestMapping(value = "/Proofing/SearchInAll", method = RequestMethod.GET)
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
				model.put("baseUrl", "SearchInAll");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothes", convertClothToDetailData(resultList));
			}else {
				logger.warn("cloth get all exception");
			}
			return new ModelAndView("proofing/cloth_material_manage", "model", model);
		}else {	
			response.sendRedirect(request.getContextPath() + "/Error");
			return null;
		}
	}

	@RequestMapping(value = "/Proofing/ClothMaterialDetail", method = RequestMethod.GET)
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
		return new ModelAndView("proofing/cloth_material_detail", "model", model);
	}
	
	@RequestMapping(value = "/Proofing/CreateCloth", method = RequestMethod.GET)
	public ModelAndView createClothGet(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("proofing/cloth_create", "model", null);
	}
	
	@RequestMapping(value = "/Proofing/CreateCloth", method = RequestMethod.POST)
	public void createClothPost(HttpServletRequest request, @RequestParam(value = "image", required = false) MultipartFile file,
			HttpServletResponse response) throws IOException, ParseException {
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String client = request.getParameter("client");
		String deliveryDate = request.getParameter("deliveryDate");
		String color = request.getParameter("color");
		String remark = request.getParameter("remark");
		
		type = type.trim();
		name = name.trim();
		color = color.trim();
		
		InputStream input = file.getInputStream();
		byte[] buffer = new byte[1024 * 1024];

		String originalFileName = file.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String saveName = uuid.toString() + Utils.getFileExtensionWithDot(originalFileName);
		
		File saveDir = new File(Config.UPLOAD_FOLDER);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File imageFile = new File(saveDir, saveName);
		if (!imageFile.exists()) {
			imageFile.createNewFile();
		}
		FileOutputStream output = new FileOutputStream(imageFile);

		int byteRead = 0;
		while ((byteRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, byteRead);
			output.flush();
		}
		output.close();
		input.close();
		
		// write into content
		Integer imageId = null;
		Content content = new Content(originalFileName, saveName, new Date());
		GenericResult<Integer> createContentResult = contentService.create(content);
		if(createContentResult.getResultCode() == ResultCode.NORMAL) {
			imageId = createContentResult.getData();
		}
		
		Cloth cloth = new Cloth(type, name, client, remark, imageId, new Date());
		GenericResult<Integer> createResult = clothService.create(cloth);
		if(createResult.getResultCode() == ResultCode.NORMAL) {
			int clothId = createResult.getData();
			if(StringUtils.isBlank(deliveryDate)) {
				deliveryDate = null;
			}else {
				deliveryDate.trim();
			}
			OrderCloth orderCloth = new OrderCloth(null, clothId, null == deliveryDate ? null : Utils.parseDate(deliveryDate, Config.DATE_FORMAT), null);
			orderClothService.create(orderCloth);
			ClothColor clothColor = new ClothColor(clothId, color);
			
			GenericResult<Integer> myResult = clothColorService.create(clothColor);
			response.sendRedirect(request.getContextPath() + "/Proofing/ClothMaterialCreate?clothId=" + clothId + "&clothColorId=" + myResult.getData());
		}else {
			// TODO redirect to error page
			response.sendRedirect(request.getContextPath() + "/Proofing/ClothMaterialManage");
		}
		
	}
	
	
	@RequestMapping(value = "/Proofing/DeleteCloth" ,method = RequestMethod.POST) 
	@ResponseBody
	public NoneDataJsonResult deleteCloth(HttpServletRequest request, HttpServletResponse response) {
		NoneDataJsonResult result = new NoneDataJsonResult();
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		try {

			NoneDataResult deleteResult = clothService.delete(clothId);
			if(deleteResult.getResultCode() != ResultCode.NORMAL) {
				return new NoneDataJsonResult(deleteResult);
			}
			NoneDataResult deleteMaterialResult = clothMaterialService.deleteByCloth(clothId);
			if(deleteMaterialResult.getResultCode() != ResultCode.NORMAL) {
				return new NoneDataJsonResult(deleteMaterialResult);
			}
			NoneDataResult deleteSizeResult = clothSizeService.deleteByCloth(clothId);
			if(deleteSizeResult.getResultCode() != ResultCode.NORMAL) {
				return new NoneDataJsonResult(deleteSizeResult);
			}
			NoneDataResult deleteColorResult = clothColorService.deleteByCloth(clothId);
			if(deleteColorResult.getResultCode() != ResultCode.NORMAL) {
				return new NoneDataJsonResult(deleteColorResult);
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
		}
		return result;
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
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", true);
		int materialId = Utils.getRequestIntValue(request, "materialId", true);
		String part = request.getParameter("part");
		String unitName = request.getParameter("unitName");
		String supplier = request.getParameter("supplier");
		double consumption = Utils.getRequestDoubleValue(request, "consumption", true);
		double estimatedPrice = Utils.getRequestDoubleValue(request, "estimatedPrice", false);
		String remark = request.getParameter("remark");
		String color = request.getParameter("color");
	
		ClothMaterial clothMaterial = new ClothMaterial(clothId, clothColorId, materialId, color, part, unitName, supplier, consumption, estimatedPrice, null,
				null, null, null, remark);
		GenericResult<Integer> createResult = clothMaterialService.create(clothMaterial);
		return new GenericJsonResult<Integer>(createResult);
	}
	
	@RequestMapping(value = "/Proofing/ClothMaterialOperate", method = RequestMethod.GET)
	public ModelAndView clothMaterialOperate(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", false);
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothMaterialOperate?clothId=" + clothId);
		return new ModelAndView("proofing/cloth_material_operate", "model", model);
	}
	
	@RequestMapping(value = "/Proofing/ClothMaterialCreate", method = RequestMethod.GET)
	public ModelAndView clothMaterialCreate(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", true);
		
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		return new ModelAndView("proofing/cloth_material_create", "model", model);
	}
	
	// Function to delete the clothes
	
	
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/DeleteClothMaterial", method = RequestMethod.POST)
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
		int clothColorId = Utils.getRequestIntValue(request, "clothColorId", true);
		
		double estimatedPrice = Utils.getRequestDoubleValue(request, "estimatedPrice", true);
		GenericResult<ClothMaterial> clothMaterialResult = clothMaterialService.getById(clothId, clothColorId, clothMaterialId);
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
		
		Material material = new Material(name,  type);
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
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/CreateNewColor", method = RequestMethod.POST)
	public NoneDataJsonResult createNewColor(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		String color = request.getParameter("color");
		if(StringUtils.isNotBlank(color)) {
			ClothColor clothColor = new ClothColor(clothId, color);
			GenericResult<Integer> creaetResult = clothColorService.create(clothColor);
			return new NoneDataJsonResult(creaetResult);
		}

		return new NoneDataJsonResult(ResultCode.E_INVALID_PARAMETER, "color can not null");
	}
	
	@ResponseBody
	@RequestMapping(value = "/Proofing/CreateNewVersion", method = RequestMethod.POST)
	public NoneDataJsonResult createNewVersion(HttpServletRequest request, HttpServletResponse response) {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);
		return new NoneDataJsonResult(clothService.copyCloth(clothId));
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
	
	private List<ClothDetailData> convertClothToDetailData(List<Cloth> clothList) {
		if(null == clothList || clothList.isEmpty()) {
			return null;
		}
		List<ClothDetailData> resultList = new ArrayList<ClothDetailData>();
		for(Cloth cloth : clothList) {
			GenericResult<List<ClothColor>> colorResult = clothColorService.getByCloth(cloth.getId());
			if(colorResult.getResultCode() == ResultCode.NORMAL) {
				ClothDetailData data = null;
				GenericResult<List<ClothMaterialDetailData>> clothMaterialResult = clothMaterialService.getTypeDetailByCloth(cloth.getId(), colorResult.getData().get(0).getId(), MaterialType.MATERIAL_TYPE_LEATHER);
				if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
					data = new ClothDetailData(cloth, colorResult.getData(), clothMaterialResult.getData());
				}else {
					data = new ClothDetailData(cloth, colorResult.getData());
				}
				
				if(null != cloth.getImageId() && cloth.getImageId() != 0) {
					GenericResult<String> contentResult = contentService.getContentCodeById(cloth.getImageId());
					if(contentResult.getResultCode() == ResultCode.NORMAL) {
						data.setImageContent(contentResult.getData());
					}
					
				}
				resultList.add(data);
			}

		}
		return resultList;
	}
	
}
