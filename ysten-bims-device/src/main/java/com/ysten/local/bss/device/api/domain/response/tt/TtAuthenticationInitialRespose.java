package com.ysten.local.bss.device.api.domain.response.tt;

import com.ysten.local.bss.device.api.domain.response.IResponse;
/**
 * for 用户认证返回
 * @author chenxiang
 * @date 2014-7-7 下午12:04:05 
 */
public class TtAuthenticationInitialRespose implements IResponse {

	/**
	 * 返回结果code
	 */
	private int statusCode;
	/**
	 * 返回信息
	 */
	private String userToken;
	
	
	public static int REQUESTERROR_CODE=20101;//请求失败code
	public static int RESPOSEERROR_CODE=20201;//响应方错误code
	public static int SUCESS_CODE=0;//成功code
	
	
	
	public TtAuthenticationInitialRespose(int statusCode, String userToken) {
		this.statusCode = statusCode;
		this.userToken = userToken;
	}


	/**
	 * 创建用户认证返回xml
	 * @author chenxiang
	 * @date 2014-6-23 下午12:01:19 
	 * @return String
	 */
	public String createUserAuthRequestResponseXML() {
		StringBuffer xmlContent = new StringBuffer("<payLoad>");
		xmlContent.append("<StatusCode>").append(statusCode).append("</StatusCode>");
		xmlContent.append("<UserToken>").append(userToken).append("</UserToken>");
		xmlContent.append("</payLoad>");
		return xmlContent.toString();
	}


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	public String getUserToken() {
		return userToken;
	}


	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	
	

}
