package com.yicheng.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yicheng.common.UserType;
import com.yicheng.pojo.User;
import com.yicheng.service.UserService;
import com.yicheng.util.GenericJsonResult;
import com.yicheng.util.GenericResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.UserInfoStorage;
import com.yicheng.util.Utils;

@Controller
public class IdentityController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/Login" }, method = RequestMethod.GET)
	public ModelAndView loginView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("login", null);
	}

	@ResponseBody
	@RequestMapping(value = { "/Login" }, method = RequestMethod.POST)
	public GenericJsonResult<String> login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		GenericJsonResult<String> result = new GenericJsonResult<String>();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
//			response.sendRedirect(request.getContextPath() + "/Login");
			result.setResultCode(ResultCode.E_OTHER_ERROR);
			result.setMessage("username and password cannot be blank");;
			return result;
		}
		
		int userType = Utils.getRequestIntValue(request, "userType", true);

		name = name.trim();
		password = password.trim();
		GenericResult<User> userResult = userService.search(
				name, password, userType);
		if (userResult.getResultCode() == ResultCode.NORMAL) {
			User user = userResult.getData();
			String sessionId = request.getSession(true).getId();
			UserInfoStorage.putUser(sessionId, user);
			switch (userType) {
			case UserType.USER_TYPE_PROOFING:
				result.setData("Proofing/ClothMaterialManage");
				break;
			case UserType.USER_TYPE_PRICING:
				result.setData("Pricing/ClothPriceToProcess");
				break;
			case UserType.USER_TYPE_BUYER:
				result.setData("Buyer/ClothCountToProcess");
				break;
			case UserType.USER_TYPE_MANAGER:
				result.setData("Manager/ClothMaterialManage");
				break;
			default:
				break;
			}
		} else {
			result.setResultCode(userResult.getResultCode());
			result.setMessage(userResult.getMessage());
		}
		
		return result;

	}

	@RequestMapping(value = { "/Logout", "/logout" })
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserInfoStorage.removeUser(request.getSession(true).getId());
		response.sendRedirect(request.getContextPath() + "/Login");
		return;
	}
}
