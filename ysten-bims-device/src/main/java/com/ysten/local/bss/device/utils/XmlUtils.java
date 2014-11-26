package com.ysten.local.bss.device.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);

	/**
	 * 
	 * @param xml
	 * @param rootName
	 * @param encoding
	 * @return
	 * @author HanksXu
	 */
	public static NodeList parserXML(String xml, String rootName,String encoding) {
		NodeList requestList = null;
		Document doc = parserXML2Doc(xml, encoding);
		if (doc != null) {
			NodeList root = doc.getElementsByTagName(rootName);
			if (root != null && root.getLength() > 0) {
				requestList = root.item(0).getChildNodes();
			}
		}
		return requestList;
	}
	
	public static String createRequestResponseXML(String resCode, String resDesc) {
		StringBuffer xmlContent = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><Root><ServCont><Response>");
		xmlContent.append("<Result>").append(resCode).append("</Result>");
		xmlContent.append("<Description>").append(resDesc).append("</Description>");
		xmlContent.append("</Response></ServCont></Root>");
		return xmlContent.toString();
	}

//	/**
//	 * 创建xml格式的字符串
//	 * 
//	 * @param type
//	 * @param operation
//	 * @param params
//	 * @return
//	 * @author michael
//	 * 
//	 */
//	public static String createRequestResponseXML(REQUEST_RESPONSE type,
//			OPERATION_COMMAND operation, Map<String, String> params) {
//
//		if (params != null && !params.isEmpty()) {
//			StringBuffer xmlContent = new StringBuffer(
//					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
//			xmlContent.append("<Root><ServCont>");
//			String endTag = "";
//			if (type == REQUEST_RESPONSE.RESPONSE) {
//				xmlContent.append("<Response>");
//				endTag = "</Response>";
//			} else {
//				if (operation == null) {
//					xmlContent.append("<Request>");
//					endTag = "</Request>";
//				} else {
//					switch (operation) {
//					case SN_CHANGE:
//						xmlContent.append("<SNChangeInfo>");
//						endTag = "</SNChangeInfo>";
//						break;
//					case CNTV_USER:
//						xmlContent.append("<CNTVUserInfo>");
//						endTag = "</CNTVUserInfo>";
//						break;
//					case ORDER:
//						xmlContent.append("<OrderInfo>");
//						endTag = "</OrderInfo>";
//						break;
//					default:
//
//						break;
//					}
//				}
//			}
//			Set<Entry<String, String>> sets = params.entrySet();
//			String key;
//			for (Entry<String, String> entry : sets) {
//				key = entry.getKey();
//				xmlContent.append("<").append(key).append(">")
//						.append(entry.getValue()).append("</").append(key)
//						.append(">");
//			}
//			xmlContent.append(endTag).append("</ServCont></Root>");
//			return xmlContent.toString();
//		}
//		return "";
//	}

	/**
	 * 根据节点名称取得节点值
	 * 
	 * @param xml
	 * @param rootName
	 * @param encoding
	 * @return
	 * @author michael
	 */
	public static String parserXMLToNode(String xml, String rootName,
			String encoding) {
		String content = "";
		Document doc = parserXML2Doc(xml, encoding);
		if (doc != null) {
			NodeList nodes = doc.getElementsByTagName(rootName);
			if (nodes != null && nodes.getLength() > 0) {
				Node node = nodes.item(0);
				content = node.getTextContent();
			}
		}
		return content;
	}

	/**
	 * 将xml 字符串转换为Document对象
	 * 
	 * @param xml
	 * @param encoding
	 * @return
	 * @author michael
	 */
	public static Document parserXML2Doc(String xml, String encoding) {
		Document doc = null;
		try {
			DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = domfac.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(xml.getBytes(encoding));
			doc = builder.parse(is, encoding);
		} catch (ParserConfigurationException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("parserXML2Doc occure exception, the message is "
						+ e.getMessage());
			}
		} catch (UnsupportedEncodingException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("parserXML2Doc occure exception, the message is "
						+ e.getMessage());
			}
		} catch (SAXException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("parserXML2Doc occure exception, the message is "
						+ e.getMessage());
			}
		} catch (IOException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("parserXML2Doc occure exception, the message is "
						+ e.getMessage());
			}
		}
		return doc;
	}
	
	public static String createXmlResponse(String status, String desription) {
        StringBuffer response = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><responseCode>");
        response.append(status).append("</responseCode><responseInfo>").append(desription)
                .append("</responseInfo></response>");
        return response.toString();
    }
}
