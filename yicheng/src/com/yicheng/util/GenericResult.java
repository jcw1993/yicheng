package com.yicheng.util;

public class GenericResult<T> {
	private int resultCode;
	private String message;
	private T data;
	
	public GenericResult() {
		this.resultCode = ResultCode.NORMAL;
	}
	
	public GenericResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public GenericResult(int resultCode, T data) {
		this.resultCode = resultCode;
		this.data = data;
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
