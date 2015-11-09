package com.yicheng.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.upload.UploadFile;
import com.yicheng.common.BaseController;
import com.yicheng.common.Config;
import com.yicheng.common.MaterialType;
import com.yicheng.common.Pagination;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.pojo.Material;
import com.yicheng.pojo.OrderCloth;
import com.yicheng.service.ServiceFactory;
import com.yicheng.service.data.ClothDetailData;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.util.GenericJsonResult;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataJsonResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.QiniuUtil;
import com.yicheng.util.ResultCode;
import com.yicheng.util.Utils;

public class ProofingController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ProofingController.class);
	
	public void ClothMaterialManage() {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = ServiceFactory.getInstance().getClothService().getAll();
			
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
			renderJsp(getJsp("proofing/cloth_material_manage"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void SearchInAll() throws IOException {
		String keyword = getPara("keyword");
		if(StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = ServiceFactory.getInstance().getClothService().searchInAll(keyword);
			if(clothResult.getResultCode() == ResultCode.NORMAL) {
				int pageIndex = getParaToInt("pageIndex");
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
			getRequest().setAttribute("model", model);
			renderJsp(getJsp("proofing/cloth_material_manage"));
		}else {	
			getResponse().sendRedirect(getRequest().getContextPath() + "/Error");
		}
	}

	public void ClothMaterialDetail() {
		int clothId = getParaToInt("clothId");
		int clothColorId = getParaToInt("clothColorId");
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService().getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothMaterialDetail?clothId=" + clothId);
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("proofing/cloth_material_detail"));
	}
	
	public void CreateCloth() {
		renderJsp(getJsp("proofing/cloth_create"));
	}
	
	@Before(POST.class)
	public void CreateClothPost() throws IOException, ParseException {
		try {
			UploadFile uploadFile = getFile("image");
			File file = null;
			String type = getPara("type");
			String name = getPara("name");
			String client = getPara("client");
			String deliveryDate = getPara("deliveryDate");
			String color = getPara("color");
			String remark = getPara("remark");
			
			type = type.trim();
			name = name.trim();
			color = color.trim();
			
			Cloth cloth = null;
			if(null != uploadFile && null != (file = uploadFile.getFile())) {
				String originalFileName = uploadFile.getOriginalFileName();
				UUID uuid = UUID.randomUUID();
				String saveName = uuid.toString() + Utils.getFileExtensionWithDot(originalFileName);
				
				cloth = new Cloth();
				cloth.setType(type);
				cloth.setName(name);
				cloth.setClient(client);
				cloth.setRemark(remark);
				cloth.setImagePath(QiniuUtil.QINIU_BASE_URL + String.format(QiniuUtil.QINIU_PIC_KEY_FORMAT, saveName));
				cloth.setCreatedTime(new Date());
			}else {
				cloth = new Cloth();
				cloth.setType(type);
				cloth.setName(name);
				cloth.setClient(client);
				cloth.setRemark(remark);
				cloth.setCreatedTime(new Date());
			}
			
			GenericResult<Integer> createResult = ServiceFactory.getInstance().getClothService().create(cloth);
			if(createResult.getResultCode() == ResultCode.NORMAL) {
				if(null != file && file.length() > 0) {
					QiniuUtil.uploadImage(cloth.getImagePath().substring(QiniuUtil.QINIU_BASE_URL.length(), cloth.getImagePath().length()), uploadFile.getFile());
				}
				
				int clothId = createResult.getData();
				if(StringUtils.isBlank(deliveryDate)) {
					deliveryDate = null;
				}else {
					deliveryDate.trim();
				}
				
				OrderCloth orderCloth = new OrderCloth();
				orderCloth.setClothId(clothId);
				orderCloth.setDeliveryDate(null == deliveryDate ? null : Utils.parseDate(deliveryDate, Config.DATE_FORMAT));
				ServiceFactory.getInstance().getOrderClothService().create(orderCloth);
				ClothColor clothColor = new ClothColor();
				clothColor.setClothId(clothId);
				clothColor.setColor(color);
				
				GenericResult<Integer> myResult = ServiceFactory.getInstance().getClothColorService().create(clothColor);
				getResponse().sendRedirect(getRequest().getContextPath() + "/Proofing/ClothMaterialOperate?clothId=" + clothId + "&clothColorId=" + myResult.getData());
			}else {
				getResponse().sendRedirect(getRequest().getContextPath() + "/Proofing/ClothMaterialManage");
			}
		} catch (Error e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Before(POST.class)
	public void DeleteCloth() {
		NoneDataJsonResult result = new NoneDataJsonResult();
		int clothId = getParaToInt("clothId");
		try {

			NoneDataResult deleteResult = ServiceFactory.getInstance().getClothService().delete(clothId);
			if(deleteResult.getResultCode() != ResultCode.NORMAL) {
				renderJson(new NoneDataJsonResult(deleteResult));
				return;
			}
			NoneDataResult deleteMaterialResult = ServiceFactory.getInstance().getClothMaterialService().deleteByCloth(clothId);
			if(deleteMaterialResult.getResultCode() != ResultCode.NORMAL) {
				renderJson(new NoneDataJsonResult(deleteMaterialResult));
				return;
			}
			NoneDataResult deleteSizeResult = ServiceFactory.getInstance().getClothSizeService().deleteByCloth(clothId);
			if(deleteSizeResult.getResultCode() != ResultCode.NORMAL) {
				renderJson(new NoneDataJsonResult(deleteSizeResult));
				return;
			}
			NoneDataResult deleteColorResult = ServiceFactory.getInstance().getClothColorService().deleteByCloth(clothId);
			if(deleteColorResult.getResultCode() != ResultCode.NORMAL) {
				renderJson(new NoneDataJsonResult(deleteColorResult));
				return;
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
		}
		renderJson(result);
	}
		
		
	
	public void GetAllMaterialByType() {
		int type = getParaToInt("type");
		GenericResult<List<Material>> materialResult = ServiceFactory.getInstance().getMaterialService().getByType(type);
		renderJson(new GenericJsonResult<List<Material>>(materialResult));
	}
	
	@Before(POST.class)
	public void CreateClothMaterial() {
		int clothId = getParaToInt("clothId");
		int clothColorId = getParaToInt("clothColorId");
		int materialId = getParaToInt("materialId");
		String part = getPara("part");
		String unitName = getPara("unitName");
		String supplier = getPara("supplier");
		double consumption = Utils.getRequestDoubleValue(getRequest(), "consumption", true);
		double estimatedPrice = Utils.getRequestDoubleValue(getRequest(), "estimatedPrice", false);
		String remark = getPara("remark");
		String color = getPara("color");
	
		ClothMaterial clothMaterial = new ClothMaterial();
		clothMaterial.setClothId(clothId);
		clothMaterial.setClothColorId(clothColorId);
		clothMaterial.setMaterialId(materialId);
		clothMaterial.setColor(color);
		clothMaterial.setPart(part);
		clothMaterial.setUnitName(unitName);
		clothMaterial.setSupplier(supplier);
		clothMaterial.setConsumption(consumption);
		clothMaterial.setEstimatedPrice(estimatedPrice);
		clothMaterial.setRemark(remark);
		GenericResult<Integer> createResult = ServiceFactory.getInstance().getClothMaterialService().create(clothMaterial);
		renderJson(new GenericJsonResult<Integer>(createResult));
	}
	
	public void ClothMaterialOperate() {
		int clothId = getParaToInt("clothId");
		int clothColorId = getParaToInt("clothColorId");
		if(clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService().getByCloth(clothId);
			if(clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		model.put("baseUrl", "ClothMaterialOperate?clothId=" + clothId);
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("proofing/cloth_material_operate"));
	}
	
	public void ClothMaterialCreate() {
		int clothId = getParaToInt("clothId");
		int clothColorId = getParaToInt("clothColorId");
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("proofing/cloth_material_create"));
	}
	
	@Before(POST.class)
	 public void DeleteClothMaterial() {
		int clothMaterialId = getParaToInt("clothMaterialId");
		int clothId = getParaToInt("clothId");
		NoneDataResult result = ServiceFactory.getInstance().getClothMaterialService().delete(clothMaterialId, clothId);
		renderJson(new NoneDataJsonResult(result));
	}
	
	@Before(POST.class)
	 public void SaveClothMaterialEstimatedPrice() {
		int clothMaterialId = getParaToInt("clothMaterialId");
		int clothId = getParaToInt("clothId");
		int clothColorId = getParaToInt("clothColorId");
		
		double estimatedPrice = Utils.getRequestDoubleValue(getRequest(), "estimatedPrice", true);
		GenericResult<ClothMaterial> clothMaterialResult = ServiceFactory.getInstance().getClothMaterialService().getById(clothId, clothColorId, clothMaterialId);
		if(clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
			ClothMaterial clothMaterial = clothMaterialResult.getData();
			clothMaterial.setEstimatedPrice(estimatedPrice);
			renderJson(new NoneDataJsonResult(ServiceFactory.getInstance().getClothMaterialService().update(clothMaterial)));
		}else {
			renderJson(new NoneDataJsonResult(clothMaterialResult));
		}
		
	}
	
	public void MaterialManage() {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Material>> materialResult = ServiceFactory.getInstance().getMaterialService().getAll();
		
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
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("proofing/material_manage"));
	}
	
	@Before(POST.class)
	public void CreateMaterial() {
		int type = getParaToInt("type");
		String name = getPara("name");
		
		Material material = new Material();
		material.setName(name);
		material.setType(type);
		GenericResult<Integer> createResult = ServiceFactory.getInstance().getMaterialService().create(material);
		renderJson(new GenericJsonResult<Integer>(createResult));
	}

	@Before(POST.class)
	public void DeleteMaterial() {
		int materialId = getParaToInt("materialId");
		renderJson(new NoneDataJsonResult(ServiceFactory.getInstance().getMaterialService().delete(materialId)));
	}

	@Before(POST.class)
	public void UpdateMaterial() {
		int materialId = getParaToInt("materialId");
		String name = getPara("name");
		name = name.trim();
		
		GenericResult<Material> materialResult = ServiceFactory.getInstance().getMaterialService().getById(materialId);
		if(materialResult.getResultCode() == ResultCode.NORMAL) {
			Material material = materialResult.getData();
			material.setName(name);
			renderJson(new NoneDataJsonResult(ServiceFactory.getInstance().getMaterialService().update(material)));
		}else {
			renderJson(new NoneDataJsonResult(materialResult));
		}
	}

	@Before(POST.class)
	public void CreateNewColor() {
		int clothId = getParaToInt("clothId");
		String color = getPara("color");
		if(StringUtils.isNotBlank(color)) {
			ClothColor clothColor = new ClothColor();
			clothColor.setClothId(clothId);
			clothColor.setColor(color);
			GenericResult<Integer> creaetResult = ServiceFactory.getInstance().getClothColorService().create(clothColor);
			renderJson(new NoneDataJsonResult(creaetResult));
		}

		renderJson(new NoneDataJsonResult(ResultCode.E_INVALID_PARAMETER, "color can not null"));
	}

	@Before(POST.class)
	public void CreateNewVersion() {
		int clothId = getParaToInt("clothId");
		renderJson(new NoneDataJsonResult(ServiceFactory.getInstance().getClothService().copyCloth(clothId)));
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
