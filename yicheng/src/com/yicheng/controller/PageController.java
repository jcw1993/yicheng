package com.yicheng.controller;

import com.jfinal.core.ActionKey;
import com.yicheng.common.BaseController;
import com.yicheng.common.Pagination;
import com.yicheng.util.NoneDataJsonResult;

public class PageController extends BaseController {
	
	@ActionKey("/Proofing/ChangeItemsPerPage")
	public void changeItemsPerPage() {
		int count = getParaToInt("count");
		Pagination.ITEMS_PER_PAGE = count;
		renderJson(new NoneDataJsonResult());
	}
}
