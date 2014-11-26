package com.ysten.local.bss.device.api.domain.request.tt;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ysten.local.bss.device.api.domain.request.IRequest;


/**
 * for 开户验证请求 用户信息获取请求
 * @author chenxiang
 * @date 2014-6-23 下午12:01:51
 */
public class TtUserRequest implements IRequest{

	/**
	 * command 请求指令
	 */
	private String command;

	/**
	 * 请求数据
	 */
	private String xmlData;
	
	
//	//xml字段
//	private String mobileNumber;
//	private String stbId;
	
	//xml属性名
	private static String MOBILE_NUMBER="MobileNumber";
	private static String VERIFY_CODE="VerifyCode";
	private static String STBID="STBID";
	
	
	public static String VERIFY_REQ="VerifyReq";//开户验证请求命令
	public static String USER_INFO_REQ="UserInfoReq";//用户信息请求命令

	public TtUserRequest(String xmlData,String command) {
		this.command = command;
		this.xmlData = xmlData;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getXmlData() {
		return xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}
	
	
	public String getMobileNumber() {
		return parseReqXmlByAttribute(MOBILE_NUMBER);
	}

//	public void setMobileNumber(String mobileNumber) {
//		this.mobileNumber = mobileNumber;
//	}

	public String getStbId() {
		return parseReqXmlByAttribute(STBID);
	}

//	public void setStbId(String stbId) {
//		this.stbId = stbId;
//	}
	
	public String getVerifyCode() {
		return parseReqXmlByAttribute(VERIFY_CODE);
	}

	@Override
	public String toString() {
		return "xmlData=" + this.xmlData + ",command=" + this.command;
	}

	/**
	 * 根据 xml的属性名 获取值
	 * @author chenxiang
	 * @date 2014-6-23 下午1:18:02 
	 * @param attributeName 属性字段名称
	 * @return String
	 */
	public String parseReqXmlByAttribute(String attributeName) {
		Document doc;
		try {
			doc = DocumentHelper.parseText(xmlData);
			Element rootElement = doc.getRootElement();
			List<Element> elements = rootElement.element("body").elements(command);
			for (Element e : elements) {
				String field_value = e.attributeValue(attributeName);
				return field_value;
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		return null;

	}
}
