package com.ysten.local.bss.util;

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
     * 将xml 字符串转换为Document对象
     * @param xml
     * @param encoding
     * @return
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
    
    /**
     * 
     * 根据节点名称取得节点值
     * @param xml
     * @param rootName
     * @param encoding
     * @return
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

}
