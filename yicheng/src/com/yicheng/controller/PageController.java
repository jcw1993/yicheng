package com.yicheng.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yicheng.common.Pagination;
import com.yicheng.util.NoneDataJsonResult;
import com.yicheng.util.Utils;

@Controller
public class PageController {
	
	@ResponseBody
	@RequestMapping(value = { "/Proofing/ChangeItemsPerPage" }, method = RequestMethod.GET)
	public NoneDataJsonResult changeItemsPerPage(HttpServletRequest request, HttpServletResponse response) {
		int count = Utils.getRequestIntValue(request, "count", true);
		Pagination.ITEMS_PER_PAGE = count;
		return new NoneDataJsonResult();
	}
}
