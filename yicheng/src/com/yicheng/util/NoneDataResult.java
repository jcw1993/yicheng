package com.yicheng.util;

public class NoneDataResult {
	private int resultCode;
	private String message;
	
	public NoneDataResult() {
		this.resultCode = ResultCode.NORMAL;
	}
	
	public NoneDataResult(int resultCode) {
		this.resultCode = resultCode;
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
	
}
