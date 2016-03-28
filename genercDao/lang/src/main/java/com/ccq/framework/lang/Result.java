/**
 *  @author xiaoliu
 */

package com.ccq.framework.lang;

import java.io.Serializable;

public class Result implements Serializable{

	private static final long serialVersionUID = 8324599203636896151L;

	//状态标志
	private boolean success;
	//消息
	private String message;
	//错误码
	private String code;
	//object
	private Object object;
	//服务码
	private String serviceCode;
	
	
	
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 *  构造（返回标志，返回信息）
	 * 
	 * @param success
	 * @param message
	 */
	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	/**
	 * 构造（返回标志、消息、代码）
	 * 
	 * @param success
	 * @param message
	 * @param code
	 */
	public Result(boolean success, String message, String code) {
		super();
		this.success = success;
		this.message = message;
		this.code = code;
	}

	/**
	 * 构造（返回标志、消息、错误码、对象、服务码）
	 *  
	 * @param success
	 * @param message
	 * @param code
	 * @param object
	 * @param serviceCode
	 */
	public Result(boolean success, String message, String code, Object object, String serviceCode) {
		super();
		this.success = success;
		this.message = message;
		this.code = code;
		this.object = object;
		this.serviceCode = serviceCode;
	}
	
	/**
	 * 设置成功
	 * 
	 * @param message
	 */
	public void success(String message) {
		setSuccess(true);
		setMessage(message);
	}
	
	/**
	 * 设置成功
	 * 
	 * @param message
	 */
	public void success(String message,String code) {
		setSuccess(true);
		setMessage(message);
		setCode(code);
	}
	
	/**
	 * 设置成功
	 * 
	 * @param message
	 */
	public void success(String message,String code,Object obj) {
		setSuccess(true);
		setMessage(message);
		setCode(code);
		setObject(obj);
	}
	
	/**
	 * 错误 
	 * @param message
	 */
	public void error(String message) {
		setSuccess(false);
		setMessage(message);
	}
	
	public void error(String message,String code) {
		setSuccess(false);
		setMessage(message);
		setCode(code);
	}
	
	public void error(String message,String code,Object obj) {
		setSuccess(false);
		setCode(code);
		setObject(obj);
	}
	
	public boolean isSuccess() {
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
}
