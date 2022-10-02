package com.Auth.JwtAuth.payload;

public class ApiResponse {
	private String message;
	private boolean success;
	public ApiResponse(String message2, boolean b) {
		this.message=message2;
		this.success = b;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
