package com.yicheng.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static int getRequestIntValue(HttpServletRequest request, String parameterName, boolean throwException) {
		int result = 0;
		String parameterValue = request.getParameter(parameterName);
		if(StringUtils.isBlank(parameterValue)) {
			if(throwException) {
				throw new IllegalArgumentException();
			}else {
				return result;
			}
		}
		
		try {
			result = Integer.parseInt(parameterValue);
		}catch(NumberFormatException e) {
			logger.error(e.getMessage());
			if(throwException) {
				throw e;
			}
		}
		
		return result;
	}

	public static double getRequestDoubleValue(HttpServletRequest request, String parameterName, boolean throwException) {
		double result = 0.0;
		String parameterValue = request.getParameter(parameterName);
		if(StringUtils.isBlank(parameterValue)) {
			if(throwException) {
				throw new IllegalArgumentException();
			}else {
				return result;
			}
		}
		
		try {
			result = Double.parseDouble(parameterValue);
		}catch(NumberFormatException e) {
			logger.error(e.getMessage());
			if(throwException) {
				throw e;
			}
		}
		
		return result;
	}
	
	public static Date parseDate(String dateStr, String format) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(dateStr);
	}
	
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) {
			return null;
		}
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static void addSaeStorage(HttpServletRequest request) {
		
	}
	 
/*	public static void tmpfsToStorage(){
		
	}
	
	public static void storageToTmpfs(){
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("gbk");
        PrintWriter out = response.getWriter();
        // 浣跨敤SaeUserInfo鎷垮埌鏀硅姹傚彲鍐欑殑璺緞
        String realPath = SaeUserInfo.getSaeTmpPath() + "/";
        try {
            // 浣跨敤common-upload涓婁紶鏂囦欢鑷宠繖涓矾寰勪腑
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart)
                return;
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(1024 * 1024);
            List<FileItem> items = null;
            items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File fullFile = new File(item.getName());
                    File uploadFile = new File(realPath, fullFile.getName());
                    item.write(uploadFile);
                    // 涓婁紶瀹屾瘯鍚�浣跨敤SaeStorage寰�torage閲岄潰鍐�
                    SaeStorage ss = new SaeStorage();
                    // 浣跨敤upload鏂规硶涓婁紶鍒板煙涓篿mage涓�
                    ss.upload("file", realPath + fullFile.getName(),
                            fullFile.getName());

                    out.print("upload file:" + realPath + fullFile.getName()
                            + "</br>");
                }
            }
            out.print("upload end...");
        } catch (Exception e) {
            out.print("ERROR:" + e.getMessage() + "</br>");
        } finally {
            out.flush();
            out.close();
        }
    }*/
}
