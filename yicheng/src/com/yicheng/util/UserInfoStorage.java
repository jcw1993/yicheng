package com.yicheng.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class UserInfoStorage {
	private static Map<String, Object> userMap = new HashMap<String, Object>();
	
	public static Object getUser(String key) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == userMap) {
			userMap = new HashMap<String, Object>();
		}
		
		return userMap.get(key);
	}
	
	public static void putUser(String key, Object value) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == userMap) {
			userMap = new HashMap<String, Object>();
		}
		
		userMap.put(key, value);
	}
	
	public static void removeUser(String key) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == userMap) {
			userMap = new HashMap<String, Object>();
		}
		
		userMap.remove(key);
	}
}
