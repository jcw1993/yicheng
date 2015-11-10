package com.yicheng.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.yicheng.util.UserInfoStorage;

public class AuthFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	
	private static final String[] VALID_URL_SUFFIX = {"Login", "LoginPost", ".js", ".css", ".jpg", ".png"};
	
	private Gson gson = null;

	@Override
	public void destroy() {
		logger.info("AuthencationFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		String requestURI = httpServletRequest.getRequestURI();
		if(!isValidURI(requestURI)) {
			HttpSession session = httpServletRequest.getSession(true);
			String sessionId = session.getId();
			System.out.println("sessionId: " + sessionId);
			if(null == UserInfoStorage.getUser(sessionId)) {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/Login");
				return;
			}
		}
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("AuthencationFilter init");
		if(null == gson) {
			gson = new Gson();
		}
	}
	
	private boolean isValidURI(String uri) {
		if(StringUtils.isBlank(uri)) {
			return false;
		}
		
		for(String suffix : VALID_URL_SUFFIX) {
			if(uri.endsWith(suffix)) {
				return true;
			}
		}
		
		return false;
	}

}
