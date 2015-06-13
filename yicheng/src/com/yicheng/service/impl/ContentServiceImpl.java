package com.yicheng.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.yicheng.common.Config;
import com.yicheng.dao.ContentDao;
import com.yicheng.pojo.Content;
import com.yicheng.service.ContentService;
import com.yicheng.util.CacheUtil;
import com.yicheng.util.GenericResult;
import com.yicheng.util.ResultCode;

@Service
public class ContentServiceImpl implements ContentService {
	
	private static Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);
	
	private static final String ALL_CONTENT_CACHE_KEY = "all_content_cache";
	
	@Autowired
	private ContentDao contentDao;

	@Override
	public GenericResult<Integer> create(Content content) {
		GenericResult<Integer> result = new GenericResult<Integer>();
		try {
			int outId = contentDao.create(content);
			result.setData(outId);
			CacheUtil.remove(ALL_CONTENT_CACHE_KEY);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GenericResult<List<Content>> getAll() {
		GenericResult<List<Content>> result = new GenericResult<List<Content>>();
		@SuppressWarnings("unchecked")
		List<Content> contentList = (List<Content>) CacheUtil.get(ALL_CONTENT_CACHE_KEY);
		if(null != contentList && !contentList.isEmpty()) {
			result.setData(contentList);
		}else {
			try {
				contentList = contentDao.getAll();
				if(null != contentList && !contentList.isEmpty()) {
					result.setData(contentList);
					CacheUtil.put(ALL_CONTENT_CACHE_KEY, contentList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setMessage(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			}
		}
		return result;
	}

	@Override
	public GenericResult<Content> getById(int contentId) {
		GenericResult<Content> result = new GenericResult<Content>();
		GenericResult<List<Content>> allResult = getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(Content content : allResult.getData()) {
				if(content.getId() == contentId) {
					result.setData(content);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no Content data, content id: " + contentId);
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		
		return result;
	}

	public GenericResult<String> getContentCodeById(Integer contentId) {
		GenericResult<String> result = new GenericResult<String>();
		if(null == contentId) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
		}
		GenericResult<Content> contentResult = getById(contentId);
		if(contentResult.getResultCode() == ResultCode.NORMAL) {
			String filePath = Config.UPLOAD_FOLDER + File.separator + contentResult.getData().getDestFileName();

			FileInputStream fin = null;
			try {
				File photoFile = new File(filePath);
				fin = new FileInputStream(photoFile);
				byte[] fileContent = new byte[(int) photoFile.length()];
				fin.read(fileContent);

				String photoBase64Code = Base64.encodeBase64String(fileContent);
				result.setData(photoBase64Code);
			} catch (Exception e) {
				result.setResultCode(ResultCode.E_OTHER_ERROR);
				result.setMessage(e.getMessage());
			} finally {
				try {
					if (null != fin) {
						fin.close();
					}
				} catch (IOException ioException) {
					result.setResultCode(ResultCode.E_OTHER_ERROR);
					result.setMessage(ioException.getMessage());
				}
			}
		}else {
			result.setResultCode(contentResult.getResultCode());
			result.setMessage(contentResult.getMessage());
		}
		
		return result;
	}
}
