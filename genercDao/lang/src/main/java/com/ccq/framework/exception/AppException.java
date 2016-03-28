package com.ccq.framework.exception;

public class AppException extends RuntimeException{

	private static final long serialVersionUID = -3069574834102486493L;
	private boolean success;
	private String message;
	private String code;
	
	public AppException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	public AppException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public AppException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	public AppException(String message) {
		super();
		this.message = message;
	}
	public AppException(boolean success) {
		super();
		this.success = success;
	}
	public AppException(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public AppException(boolean success, String message, String code) {
		super();
		this.success = success;
		this.message = message;
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "AppException [success=" + success + ", message=" + message + "]";
	}
}
