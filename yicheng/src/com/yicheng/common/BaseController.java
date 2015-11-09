package com.yicheng.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;

public class BaseController extends Controller{
	
	private static Gson gson = new Gson();

	protected String getJsp(String fileName) {
		return "/WEB-INF/views/" + fileName + ".jsp";
	}
	
	protected <T> T parseJson(Class<T> c) {
		String jsonStr = HttpKit.readIncommingRequestData(getRequest());
		return gson.fromJson(jsonStr, c);
	}
	
	protected <T> T parseJson2(Class<T> c) {
		StringBuilder sb;
        InputStream inputStream;
        sb = new StringBuilder();
        inputStream = null;
        String s;
        try
        {
            inputStream = getRequest().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            for(String line = null; (line = reader.readLine()) != null;)
                sb.append(line).append("\n");

            s = sb.toString();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }finally {
        	if(inputStream != null) {
        		try
                {
                    inputStream.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        System.out.println("jsonSTtr: " + s);    
     
        return gson.fromJson(s, c);
	}
	
}
