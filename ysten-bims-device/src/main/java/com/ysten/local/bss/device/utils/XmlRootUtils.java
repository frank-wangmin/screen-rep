package com.ysten.local.bss.device.utils;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRootUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlRootUtils.class);
    
    public static Element getRoot(String requestXml) throws DocumentException {
        Document doc = null;
        try {
            SAXReader sax = new SAXReader();
            StringReader reader = new StringReader(requestXml);
            doc = sax.read(reader);
        } catch (DocumentException e) {
            LOGGER.info("error xml from AAA {},exception info {}", requestXml, e);
            throw new DocumentException("不正确的xml格式,获取接口信息失败", e);
        }
        return doc.getRootElement();
    }
}
