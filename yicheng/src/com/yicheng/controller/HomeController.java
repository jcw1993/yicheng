package com.yicheng.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@RequestMapping(value = { "/Error", "/error" }, method = RequestMethod.GET)
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("error", null);
	}
}


