package com.yicheng.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.yicheng.common.BaseController;
import com.yicheng.common.ClothSizeType;
import com.yicheng.common.MaterialType;
import com.yicheng.common.Pagination;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothMaterial;
import com.yicheng.pojo.ClothSize;
import com.yicheng.pojo.OrderCloth;
import com.yicheng.service.ServiceFactory;
import com.yicheng.service.data.ClothDetailData;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.service.data.ClothOrderDetailData;
import com.yicheng.service.data.ClothSizeListData;
import com.yicheng.util.GenericResult;
import com.yicheng.util.NoneDataJsonResult;
import com.yicheng.util.NoneDataResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.Utils;

public class BuyerController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(BuyerController.class);

	public void ClothCountToProcess() {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothToCountResult = ServiceFactory.getInstance().getClothService()
				.getNeedCount();
		if (clothToCountResult.getResultCode() == ResultCode.NORMAL) {
			int pageIndex = Utils.getRequestIntValue(getRequest(), "pageIndex",
					false);
			int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;

			List<Cloth> allList = clothToCountResult.getData();
			int itemCount = allList.size();
			List<Cloth> resultList = new ArrayList<Cloth>();
			for (int i = startIndex; i < startIndex + Pagination.ITEMS_PER_PAGE; i++) {
				if (i < itemCount) {
					resultList.add(allList.get(i));
				}
			}
			model.put("baseUrl", "ClothCountToProcess");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothToCount", convertClothToDetailData(resultList));
		} else {
			logger.warn("cloth get need count exception");
		}

		getRequest().setAttribute("model", model);
		renderJsp(getJsp("buyer/cloth_count_to_process"));
	}

	public void ClothCountProcessed() {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<List<Cloth>> clothCountedResult = ServiceFactory.getInstance().getClothService()
				.getCounted();
		if (clothCountedResult.getResultCode() == ResultCode.NORMAL) {
			int pageIndex = Utils.getRequestIntValue(getRequest(), "pageIndex",
					false);
			int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;

			List<Cloth> allList = clothCountedResult.getData();
			int itemCount = allList.size();
			List<Cloth> resultList = new ArrayList<Cloth>();
			for (int i = startIndex; i < startIndex + Pagination.ITEMS_PER_PAGE; i++) {
				if (i < itemCount) {
					resultList.add(allList.get(i));
				}
			}
			model.put("baseUrl", "ClothCountProcessed");
			model.put("pageIndex", pageIndex);
			model.put("itemCount", itemCount);
			model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
			model.put("clothCounted", convertClothToDetailData(resultList));
		} else {
			logger.warn("cloth get counted exception");
		}

		getRequest().setAttribute("model", model);
		renderJsp(getJsp("buyer/cloth_count_processed"));
	}

	public void SearchInToCount() throws IOException {
		String keyword = getPara("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = ServiceFactory.getInstance().getClothService()
					.searchInNeedCount(keyword);
			if (clothResult.getResultCode() == ResultCode.NORMAL) {
				int pageIndex = Utils.getRequestIntValue(getRequest(), "pageIndex",
						false);
				int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;

				List<Cloth> allList = clothResult.getData();
				int itemCount = allList.size();
				List<Cloth> resultList = new ArrayList<Cloth>();
				for (int i = startIndex; i < startIndex
						+ Pagination.ITEMS_PER_PAGE; i++) {
					if (i < itemCount) {
						resultList.add(allList.get(i));
					}
				}
				model.put("baseUrl", "SearchInToCount");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothToCount", convertClothToDetailData(resultList));
			} else {
				logger.warn("cloth get need pricing exception");
			}
			renderJsp(getJsp("buyer/cloth_count_to_process"));
		} else {
			getResponse().sendRedirect(getRequest().getContextPath() + "/Error");
			return ;
		}
	}

	public void SearchInCounted() throws IOException {
		String keyword = getPara("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			Map<String, Object> model = new HashMap<String, Object>();
			GenericResult<List<Cloth>> clothResult = ServiceFactory.getInstance().getClothService()
					.searchInCounted(keyword);
			if (clothResult.getResultCode() == ResultCode.NORMAL) {
				int pageIndex = Utils.getRequestIntValue(getRequest(), "pageIndex",
						false);
				int startIndex = pageIndex * Pagination.ITEMS_PER_PAGE;

				List<Cloth> allList = clothResult.getData();
				int itemCount = allList.size();
				List<Cloth> resultList = new ArrayList<Cloth>();
				for (int i = startIndex; i < startIndex
						+ Pagination.ITEMS_PER_PAGE; i++) {
					if (i < itemCount) {
						resultList.add(allList.get(i));
					}
				}
				model.put("baseUrl", "SearchInCounted");
				model.put("keyword", keyword);
				model.put("pageIndex", pageIndex);
				model.put("itemCount", itemCount);
				model.put("itemsPerPage", Pagination.ITEMS_PER_PAGE);
				model.put("clothCounted", convertClothToDetailData(resultList));
			} else {
				logger.warn("cloth get need pricing exception");
			}
			renderJsp(getJsp("buyer/cloth_count_processed"));
		} else {
			getResponse().sendRedirect(getRequest().getContextPath() + "/Error");
			return ;
		}
	}

	public void ClothCountDetail() {
		int clothId = getParaToInt("clothId");
		int clothColorId = Utils.getRequestIntValue(getRequest(), "clothColorId",
				false);
		if (clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService()
					.getByCloth(clothId);
			if (clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		getClothTotalPrice(clothId, model);
		model.put("baseUrl", "ClothCountDetail?clothId=" + clothId);
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("buyer/cloth_count_detail"));
	}

	public void ClothCountOperate() {
		int clothId = getParaToInt("clothId");
		int clothColorId = Utils.getRequestIntValue(getRequest(), "clothColorId",
				false);
		if (clothColorId == 0) {
			GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService()
					.getByCloth(clothId);
			if (clothColorResult.getResultCode() == ResultCode.NORMAL) {
				clothColorId = clothColorResult.getData().get(0).getId();
			}
		}
		Map<String, Object> model = getClothMaterialInfo(clothId, clothColorId);
		getClothTotalPrice(clothId, model);
		model.put("baseUrl", "ClothCountOperate?clothId=" + clothId);
		getRequest().setAttribute("model", model);
		renderJsp(getJsp("buyer/cloth_count_operate"));
	}

	@Before(POST.class)
	public void OrderClothSaveCount() {
		int clothId = getParaToInt("clothId");
		int clothColorId = getParaToInt("clothColorId");

		int xsCount = Utils.getRequestIntValue(getRequest(), "xsCount", false);
		int sCount = Utils.getRequestIntValue(getRequest(), "sCount", false);
		int mCount = Utils.getRequestIntValue(getRequest(), "mCount", false);
		int lCount = Utils.getRequestIntValue(getRequest(), "lCount", false);
		int xlCount = Utils.getRequestIntValue(getRequest(), "xlCount", false);
		int xxlCount = Utils.getRequestIntValue(getRequest(), "xxlCount", false);

		GenericResult<List<ClothSize>> clothSizeResult = ServiceFactory.getInstance().getClothSizeService()
				.getByClothColor(clothId, clothColorId);
		int originTotalCount = 0;
		if (clothSizeResult.getResultCode() == ResultCode.NORMAL) {
			ClothSizeListData data = new ClothSizeListData(clothId,
					clothColorId, clothSizeResult.getData());
			originTotalCount = data.getTotalCount();
		}

		ClothSize xsSize = new ClothSize(0, clothId, clothColorId,
				ClothSizeType.CLOTH_SIZE_XS, xsCount);
		ClothSize sSize = new ClothSize(0, clothId, clothColorId,
				ClothSizeType.CLOTH_SIZE_S, sCount);
		ClothSize mSize = new ClothSize(0, clothId, clothColorId,
				ClothSizeType.CLOTH_SIZE_M, mCount);
		ClothSize lSize = new ClothSize(0, clothId, clothColorId,
				ClothSizeType.CLOTH_SIZE_L, lCount);
		ClothSize xlSize = new ClothSize(0, clothId, clothColorId,
				ClothSizeType.CLOTH_SIZE_XL, xlCount);
		ClothSize xxlSize = new ClothSize(0, clothId, clothColorId,
				ClothSizeType.CLOTH_SIZE_XXL, xxlCount);

		int totalBuyCount = xsCount + sCount + mCount + lCount + xlCount
				+ xxlCount;

		ServiceFactory.getInstance().getClothSizeService().save(xsSize);
		ServiceFactory.getInstance().getClothSizeService().save(sSize);
		ServiceFactory.getInstance().getClothSizeService().save(mSize);
		ServiceFactory.getInstance().getClothSizeService().save(lSize);
		ServiceFactory.getInstance().getClothSizeService().save(xlSize);
		ServiceFactory.getInstance().getClothSizeService().save(xxlSize);

		GenericResult<OrderCloth> orderClothResult = ServiceFactory.getInstance().getOrderClothService().getFirstbyCloth(clothId);
		if (orderClothResult.getResultCode() == ResultCode.NORMAL) {
			OrderCloth orderCloth = orderClothResult.getData();
			int originBuyCount = orderCloth.getCount();
			originBuyCount = originBuyCount - originTotalCount + totalBuyCount;
			orderCloth.setCount(originBuyCount);
			NoneDataResult updateResult = ServiceFactory.getInstance().getOrderClothService().update(orderCloth);
			renderJson(new NoneDataJsonResult(updateResult));
		} else {
			OrderCloth orderCloth = new OrderCloth();
			orderCloth.setClothId(clothId);
			orderCloth.setCount(totalBuyCount);
			GenericResult<Integer> createResult = ServiceFactory.getInstance().getOrderClothService().create(orderCloth);
			renderJson(new NoneDataJsonResult(createResult));
		}
	}

	// TODO
	@Before(POST.class)
	public void ClothMaterialSaveCount() {
		int clothId = getParaToInt("clothId");
		int clothMaterialId = getParaToInt("clothMaterialId");
		int clothColorId = getParaToInt("clothColorId");
		int count = getParaToInt("count");
		String remark = getPara("remark");

		GenericResult<ClothMaterial> clothMaterialResult = ServiceFactory.getInstance().getClothMaterialService()
				.getById(clothId, clothColorId, clothMaterialId);
		if (clothMaterialResult.getResultCode() == ResultCode.NORMAL) {
			ClothMaterial clothMaterial = clothMaterialResult.getData();
			clothMaterial.setCount(count);
			clothMaterial.setRemark(remark);
			NoneDataResult updateResult = ServiceFactory.getInstance().getClothMaterialService()
					.update(clothMaterial);
			renderJson(new NoneDataJsonResult(updateResult));
		} else {
			renderJson(new NoneDataJsonResult(clothMaterialResult));
		}
	}

	private Map<String, Object> getClothMaterialInfo(int clothId,
			int clothColorId) {
		Map<String, Object> model = new HashMap<String, Object>();
		GenericResult<Cloth> clothResult = ServiceFactory.getInstance().getClothService().getById(clothId);
		GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService()
				.getByCloth(clothId);

		GenericResult<List<ClothSize>> clothSizeResult = ServiceFactory.getInstance().getClothSizeService()
				.getByClothColor(clothId, clothColorId);
		GenericResult<ClothOrderDetailData> clothOrderDetailResult = ServiceFactory.getInstance().getOrderClothService()
				.search(null, clothId);
		GenericResult<List<ClothMaterialDetailData>> leatherDetailResult = ServiceFactory.getInstance().getClothMaterialService()
				.getTypeDetailByCloth(clothId, clothColorId,
						MaterialType.MATERIAL_TYPE_LEATHER);
		GenericResult<List<ClothMaterialDetailData>> fabricDetailResult = ServiceFactory.getInstance().getClothMaterialService()
				.getTypeDetailByCloth(clothId, clothColorId,
						MaterialType.MATERIAL_TYPE_FABRIC);
		GenericResult<List<ClothMaterialDetailData>> supportDetailResult = ServiceFactory.getInstance().getClothMaterialService()
				.getTypeDetailByCloth(clothId, clothColorId,
						MaterialType.MATERIAL_TYPE_SUPPORT);

		model.put("clothColorId", clothColorId);
		if (clothResult.getResultCode() == ResultCode.NORMAL
				&& clothColorResult.getResultCode() == ResultCode.NORMAL) {
			ClothDetailData cloth = new ClothDetailData(clothResult.getData(),
					clothColorResult.getData());
			model.put("cloth", cloth);
			model.put("clothColors", clothColorResult.getData());
		}

		if (clothSizeResult.getResultCode() == ResultCode.NORMAL) {
			model.put("clothSizes", new ClothSizeListData(clothId,
					clothColorId, clothSizeResult.getData()));
		}

		double clothTotalPrice = 0.0;
		boolean hasCount = false;

		if (clothOrderDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("clothOrder", clothOrderDetailResult.getData());
			if (null != clothOrderDetailResult.getData().getCount()) {
				hasCount = true;
			}
		}
		if (leatherDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("leatherDetails", leatherDetailResult.getData());
			if (hasCount) {
				for (ClothMaterialDetailData data : leatherDetailResult
						.getData()) {
					if (null != data.getCount()) {
						clothTotalPrice += (data.getConsumption()
								* data.getPrice() * data.getCount());
					}
				}
			}
		}
		if (fabricDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("fabricDetails", fabricDetailResult.getData());
			if (hasCount) {
				for (ClothMaterialDetailData data : fabricDetailResult
						.getData()) {
					if (null != data.getCount()) {
						clothTotalPrice += (data.getConsumption()
								* data.getPrice() * data.getCount());
					}
				}
			}
		}
		if (supportDetailResult.getResultCode() == ResultCode.NORMAL) {
			model.put("supportDetails", supportDetailResult.getData());
			if (hasCount) {
				for (ClothMaterialDetailData data : supportDetailResult
						.getData()) {
					if (null != data.getCount()) {
						clothTotalPrice += (data.getConsumption()
								* data.getPrice() * data.getCount());
					}
				}
			}
		}

		if (hasCount) {
			model.put("clothTotalPrice", clothTotalPrice
					* clothOrderDetailResult.getData().getCount());
		}

		return model;
	}

	private List<ClothDetailData> convertClothToDetailData(List<Cloth> clothList) {
		if (null == clothList || clothList.isEmpty()) {
			return null;
		}
		List<ClothDetailData> resultList = new ArrayList<ClothDetailData>();
		for (Cloth cloth : clothList) {
			GenericResult<List<ClothColor>> colorResult = ServiceFactory.getInstance().getClothColorService()
					.getByCloth(cloth.getId());
			if (colorResult.getResultCode() == ResultCode.NORMAL) {
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

	private void getClothTotalPrice(int clothId, Map<String, Object> model) {
		GenericResult<Cloth> clothResult = ServiceFactory.getInstance().getClothService().getById(clothId);
		if (clothResult.getResultCode() == ResultCode.NORMAL) {
			GenericResult<List<ClothColor>> clothColorResult = ServiceFactory.getInstance().getClothColorService()
					.getByCloth(clothId);
			if (clothColorResult.getResultCode() == ResultCode.NORMAL) {
				int totalCount = 0;
				double totalPrice = 0;
				for (ClothColor clothColor : clothColorResult.getData()) {
					int clothColorId = clothColor.getId();
					GenericResult<List<ClothSize>> clothSizeResult = ServiceFactory.getInstance().getClothSizeService()
							.getByClothColor(clothId, clothColorId);
					GenericResult<List<ClothMaterialDetailData>> leatherDetailResult = ServiceFactory.getInstance().getClothMaterialService()
							.getTypeDetailByCloth(clothId, clothColorId,
									MaterialType.MATERIAL_TYPE_LEATHER);
					GenericResult<List<ClothMaterialDetailData>> fabricDetailResult = ServiceFactory.getInstance().getClothMaterialService()
							.getTypeDetailByCloth(clothId, clothColorId,
									MaterialType.MATERIAL_TYPE_FABRIC);
					GenericResult<List<ClothMaterialDetailData>> supportDetailResult = ServiceFactory.getInstance().getClothMaterialService()
							.getTypeDetailByCloth(clothId, clothColorId,
									MaterialType.MATERIAL_TYPE_SUPPORT);
					if (clothSizeResult.getResultCode() == ResultCode.NORMAL) {
						ClothSizeListData sizeData = new ClothSizeListData(
								clothId, clothColorId,
								clothSizeResult.getData());
						totalCount += sizeData.getTotalCount();

						if (leatherDetailResult.getResultCode() == ResultCode.NORMAL) {
							for (ClothMaterialDetailData materialData : leatherDetailResult
									.getData()) {
								if (null != materialData.getCount()) {
									totalPrice += totalCount
											* (materialData.getConsumption()
													* materialData.getPrice() * materialData
														.getCount());
								}
							}
						}
						if (fabricDetailResult.getResultCode() == ResultCode.NORMAL) {
							for (ClothMaterialDetailData materialData : fabricDetailResult
									.getData()) {
								if (null != materialData.getCount()) {
									totalPrice += totalCount
											* (materialData.getConsumption()
													* materialData.getPrice() * materialData
														.getCount());
								}
							}
						}
						if (supportDetailResult.getResultCode() == ResultCode.NORMAL) {
							for (ClothMaterialDetailData materialData : supportDetailResult
									.getData()) {
								if (null != materialData.getCount()) {
									totalPrice += totalCount
											* (materialData.getConsumption()
													* materialData.getPrice() * materialData
														.getCount());
								}
							}
						}
					}
				}
				model.put("clothTotalCount", totalCount);
				model.put("clothTotalPrice", totalPrice);
			}

		}
	}
}
