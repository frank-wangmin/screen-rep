package com.ysten.local.bss.device.api.domain.request.tt;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ysten.local.bss.device.api.domain.request.IRequest;

/**
 * for 用户认证请求
 * @author chenxiang
 * @date 2014-7-7 上午11:54:24 
 */
public class TtAuthenticationInitialRequest implements IRequest {

	/**
	 * 请求数据
	 */
	private String xmlData;
	
	//xml属性名
	private static String STBID="stbId";

	public TtAuthenticationInitialRequest(String xmlData) {
		this.xmlData = xmlData;
	}


	public String getXmlData() {
		return xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}
	

	public String getStbId() {
		return parseReqXmlByAttribute(STBID);
	}

	

	@Override
	public String toString() {
		return "xmlData=" + this.xmlData ;
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
			String field_value=rootElement.elementTextTrim(attributeName);
			return field_value;
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		return null;

	}
}
