package com.yicheng.controller;

import com.yicheng.common.BaseController;

public class HomeController extends BaseController {
	
	public void Home() {
		renderJsp(getJsp("home"));
	}
	
	public void Error() {
		renderJsp(getJsp("error"));
	}
}


