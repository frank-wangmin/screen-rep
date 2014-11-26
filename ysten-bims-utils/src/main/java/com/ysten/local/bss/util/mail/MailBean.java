package com.ysten.local.bss.util.mail;

import java.util.Properties;

public class MailBean {
    private String mailServerHost;              //发送邮件的服务器的IP和端口
    private String mailServerPort = "25";
    private String fromAddress;                 //邮件发送者的地址
    private String toAddress;                   //邮件接收者的地址
    private String[] toArrayAddress;            //邮件接收者的地址
    private String userName;                    //登陆邮件发送服务器的用户名和密码
    private String password;
    private boolean validate = false;           //是否需要身份验证
    private String title;                       //邮件主题
    private String content;                     //邮件的文本内容

	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getToArrayAddress() {
		return toArrayAddress;
	}

	public void setToArrayAddress(String[] toArrayAddress) {
		this.toArrayAddress = toArrayAddress;
	}
	
	
}