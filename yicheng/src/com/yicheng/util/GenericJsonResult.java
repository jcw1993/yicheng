package com.yicheng.util;

public class GenericJsonResult<T> extends JsonBase {
	private int resultCode;
	private String message;
	private T data;
	
	public GenericJsonResult() {
		this.resultCode = ResultCode.NORMAL;
	}
	
	public GenericJsonResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public GenericJsonResult(int resultCode, T data) {
		this.resultCode = resultCode;
		this.data = data;
	}
	
	public GenericJsonResult(GenericResult<T> result) {
		this.resultCode = result.getResultCode();
		this.data = result.getData();
		this.message = result.getMessage();
	}
	
	public void convertFromGeneralResult(GenericResult<T> result) {
		this.resultCode = result.getResultCode();
		this.data = result.getData();
		this.message = result.getMessage();
	}
	
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
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
