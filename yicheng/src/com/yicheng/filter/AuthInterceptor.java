package com.yicheng.filter;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class AuthInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation invocation) {
		System.out.println("before interception");
		invocation.invoke();
		System.out.println("after interception");
	}

	
}
