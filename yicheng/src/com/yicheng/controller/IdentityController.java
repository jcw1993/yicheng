package com.yicheng.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.ext.interceptor.POST;
import com.yicheng.common.BaseController;
import com.yicheng.common.UserType;
import com.yicheng.pojo.User;
import com.yicheng.service.ServiceFactory;
import com.yicheng.util.GenericJsonResult;
import com.yicheng.util.GenericResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.UserInfoStorage;

public class IdentityController extends BaseController {

//	@RequestMapping(value = { "/", "/Login" }, method = RequestMethod.GET)
	@ActionKey("/Login")
	public void Login() throws IOException {
		renderJsp(getJsp("login"));
	}

	@Before(POST.class)
	@ActionKey("/LoginPost")
	public void LoginPost()	throws IOException, ServletException {
		GenericJsonResult<String> result = new GenericJsonResult<String>();
		String name = getPara("name");
		String password = getPara("password");
		if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
//			response.sendRedirect(request.getContextPath() + "/Login");
			result.setResultCode(ResultCode.E_OTHER_ERROR);
			result.setMessage("username and password cannot be blank");;
			renderJson(result);
		}
		
		int userType = getParaToInt("userType");

		name = name.trim();
		password = password.trim();
		GenericResult<User> userResult = ServiceFactory.getInstance().getUserService().search(
				name, password, userType);
		if (userResult.getResultCode() == ResultCode.NORMAL) {
			User user = userResult.getData();
			String sessionId = getSession(true).getId();
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
		
		renderJson(result);

	}

	@ActionKey("/Logout")
	public void Logout() throws IOException {
		UserInfoStorage.removeUser(getSession(true).getId());
		getResponse().sendRedirect(getRequest().getContextPath() + "/Login");
		return;
	}
}
