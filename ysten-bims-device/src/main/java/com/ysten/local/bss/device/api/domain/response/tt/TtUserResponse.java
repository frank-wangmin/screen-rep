package com.ysten.local.bss.device.api.domain.response.tt;

import com.ysten.local.bss.device.api.domain.response.IResponse;

/**
 * for 开户验证返回 用户信息获取返回
 * @author chenxiang
 * @date 2014-6-23 下午12:01:51 
 */
public class TtUserResponse implements IResponse {
	/**
	 * 返回结果code
	 */
	private String userInfoRsltCode;
	/**
	 * 返回信息
	 */
	private String desc;
	
	/**
	 * command 请求指令
	 */
	private String command;
	
	private String userId;//用户id
	
	private String password;//密码
	
	public static String FAIL_CODE="1";//失败code
	public static String SUCESS_CODE="0";//成功code
	
	public static String VERIFY_RES="VerifyRes";//开户验证返回命令
	public static String USER_INFO_RES="UserInfoRes";//用户信息返回命令
	
	
	public TtUserResponse(String userInfoRsltCode, String desc, String command) {
		this.userInfoRsltCode = userInfoRsltCode;
		this.desc = desc;
		this.command = command;
	}
	
	public TtUserResponse(String userInfoRsltCode, String desc, String command,String userId,String password) {
		this.userInfoRsltCode = userInfoRsltCode;
		this.desc = desc;
		this.command = command;
		this.userId = userId;
		this.password = password;
	}
	
	
	
	//toString
	@Override
	public String toString(){
		return "userInfoRsltCode=" + this.userInfoRsltCode+",desc="+this.desc;
	}
	
	
	/**
	 * 创建开户验证返回xml
	 * @author chenxiang
	 * @date 2014-6-23 下午12:01:19 
	 * @return String
	 */
	public String createUserAuthRequestResponseXML() {
		StringBuffer xmlContent = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><message version=\"1.0\"><header action=\"RESPONSE\" command=\""+command+"\"/> <body>");
		xmlContent.append("<"+command+"  Result=\"").append(userInfoRsltCode).append("\" Description=\"").append(desc).append("\" />");
		xmlContent.append("</body></message>");
		return xmlContent.toString();
	}
	
	
	/**
	 * 创建用户信息获取 返回xml
	 * @author chenxiang
	 * @date 2014-6-23 下午12:01:19 
	 * @return String
	 */
	public String createUserInfoRequestResponseXML() {
		StringBuffer xmlContent = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><message version=\"1.0\"><header action=\"RESPONSE\" command=\""+command+"\"/> <body>");
		xmlContent.append("<"+command+"  UserInfoRsltCode=\"").append(userInfoRsltCode).append("\" Description=\"").append(desc).append("\" UserID=\"").append(userId).append("\" Password=\"").append(password).append("\" />");
		xmlContent.append("</body></message>");
		return xmlContent.toString();
	}
	

	public String getUserInfoRsltCode() {
		return userInfoRsltCode;
	}
	public void setUserInfoRsltCode(String userInfoRsltCode) {
		this.userInfoRsltCode = userInfoRsltCode;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
