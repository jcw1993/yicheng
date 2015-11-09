package com.yicheng.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuUtil {
	
	/**
	 * qiniu base url
	 */
	public static final String QINIU_BASE_URL = "http://7xo4v7.com1.z0.glb.clouddn.com/";
	
	public static final String QINIU_PIC_KEY_FORMAT = "cdn/pic/%s";
	
	/**
	 * auth related variables
	 */
	private static final String ACCESS_KEY = "jdehlDjbrvYqct_kSCPBPYY7mlcsPz_gsW10Mm1H";
	private static final String SECRET_KEY = "phttiiASC_BxIx2Lgy3um68uu9YKjfP-pyPgTYZV";
	private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	
	/**
	 * qiniu bucket
	 */
	public static final String DEFAULT_BUCKET = "yicheng";
	
	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(QiniuUtil.class);
	
	/**
	 * qiniu UploadManager
	 */
	private static UploadManager uploadManager = new UploadManager();
	
	/**
	 * qiniu operation manager
	 */
	@SuppressWarnings("unused")
	private static OperationManager operationManager = new OperationManager(auth);
	
	/**
	 * qiniu bucket manager
	 */
	@SuppressWarnings("unused")
	private static BucketManager bucketManager = new BucketManager(auth);

	
	private static final String MIME_TYPE_JPEG = "image/jpeg";
	
	public static String genPrivateUrl(String srcUrl) throws Exception {
		// default 24 hours expire
		return auth.privateDownloadUrl(srcUrl, 3600 * 24);
	}
	
	public static String uploadImage(String resourceKey, byte[] data) throws QiniuException {
		upload(data, resourceKey, getUpToken(DEFAULT_BUCKET, resourceKey), MIME_TYPE_JPEG);
		return QINIU_BASE_URL + resourceKey;
	}

	public static String uploadImage(String resourceKey, File file) throws QiniuException {
		upload(file, resourceKey, getUpToken(DEFAULT_BUCKET, resourceKey), MIME_TYPE_JPEG);
		return QINIU_BASE_URL + resourceKey;
	}
	
	public static String upload(File file, String key, String upToken, String mimeType) throws QiniuException {
		Response response = uploadManager.put(file, key, upToken, null, mimeType, false);
		if(response.isOK()) {
			return response.url();
		}else {
			logger.error(response.bodyString());
		}
		return null;
	}
	
	public static String upload(byte[] data, String key, String upToken, String mimeType) throws QiniuException {
		Response response = uploadManager.put(data, key, upToken, null, mimeType, false);
		if(response.isOK()) {
			return response.url();
		}else {
			logger.error(response.bodyString());
		}
		return null;
	}


	private static String getUpToken(String bucket, String key) {
		return auth.uploadToken(bucket, key);
	}

}
