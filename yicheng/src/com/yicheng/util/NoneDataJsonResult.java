package com.yicheng.util;

public class NoneDataJsonResult extends JsonBase {
	private int resultCode;
	private String message;
	
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public NoneDataJsonResult() {
		this.resultCode = ResultCode.NORMAL;
	}
	
	public NoneDataJsonResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public NoneDataJsonResult(int resultCode, String message) {
		this.resultCode = resultCode;
		this.message = message;
	}
	
	public NoneDataJsonResult(NoneDataResult result) {
		this.resultCode = result.getResultCode();
		this.message = result.getMessage();
	}
	
	
	public <T> NoneDataJsonResult(GenericResult<T> result) {
		this.resultCode = result.getResultCode();
		this.message = result.getMessage();
	}

}
