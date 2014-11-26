//package com.ysten.local.bss.utils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map.Entry;
//
//import javax.xml.soap.MessageFactory;
//import javax.xml.soap.SOAPBody;
//import javax.xml.soap.SOAPConnection;
//import javax.xml.soap.SOAPConnectionFactory;
//import javax.xml.soap.SOAPElement;
//import javax.xml.soap.SOAPEnvelope;
//import javax.xml.soap.SOAPException;
//import javax.xml.soap.SOAPHeader;
//import javax.xml.soap.SOAPMessage;
//import javax.xml.soap.SOAPPart;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//public class SoapUtils {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SoapUtils.class);
//    
//    private static String ENCODING = "UTF-8";
//
//    /**
//     * 发送消息
//     * 
//     * @param url
//     * @param message
//     * @return
//     */
//    public static SOAPMessage sendSOAPMessage(String url, SOAPMessage message) {
//        SOAPMessage reply = null;
//        SOAPConnectionFactory soapConnFactory = null;
//        SOAPConnection connection = null;
//
//        try {
//            // 创建连接
//            soapConnFactory = SOAPConnectionFactory.newInstance();
//            connection = soapConnFactory.createConnection();
//
//            reply = connection.call(message, new URL(url));
//
//        } catch (Exception e) {
//            LOGGER.error("send soap message exception - :", e);
//        } finally {
//            try {
//                connection.close();
//            } catch (SOAPException e) {
//                LOGGER.error("close soap connection SOAPException - :", e);
//            }
//        }
//
//        return reply;
//    }
//
//    /**
//     * 创建SOAPMessage的xml串
//     * 
//     * @param root
//     *            根节点
//     * @param nodes
//     *            节点集合
//     * @return string
//     * 
//     */
//    @SuppressWarnings({ "rawtypes" })
//    public static SOAPMessage createSOAPMessage(String SOAPAction, String method, HashMap<String, String> parameters) {
//        SOAPMessage message = null;
//        try {
//            // 创建消息对象
//            MessageFactory messageFactory = MessageFactory.newInstance();
//            message = messageFactory.createMessage();
//            message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, ENCODING);
//            // 创建soap消息主体==========================================
//            SOAPPart soapPart = message.getSOAPPart();// 创建soap部分
//            SOAPEnvelope envelope = soapPart.getEnvelope();
//            SOAPBody body = envelope.getBody();
//
//            SOAPHeader header = envelope.getHeader();
//            header.setAttribute("SOAPAction", SOAPAction);
//
//            // 根据要传给mule的参数，创建消息body内容。
//            SOAPElement bodyElement = body.addChildElement(envelope.createName(method));
//
//            Iterator it = parameters.entrySet().iterator();
//            while (it.hasNext()) {
//                Entry entry = (java.util.Map.Entry) it.next();
//                bodyElement.addChildElement(entry.getKey().toString()).addTextNode(entry.getValue().toString());
//            }
//            // 保存 message
//            message.saveChanges();
//
//        } catch (UnsupportedOperationException e) {
//            LOGGER.error("send SOAPMessage UnsupportedOperationException - :", e);
//        } catch (SOAPException e) {
//            LOGGER.error("send SOAPMessage SOAPException - :", e);
//        }
//        return message;
//    }
//
//    /**
//     * InputStream 转成string
//     * 
//     * @param in
//     * @return
//     * @throws IOException
//     */
//    public static String inputStreamToString(InputStream in) throws IOException {
//        StringBuffer out = new StringBuffer();
//        byte[] b = new byte[4096];
//        for (int n; (n = in.read(b)) != -1;) {
//            out.append(new String(b, 0, n));
//        }
//        return out.toString();
//    }
//
//    /**
//     * 获取返回的SOAPMessage返回值
//     * 
//     * @param message
//     * @return
//     */
//    public static ArrayList<String> parseSOAPMessage(SOAPMessage message) {
//        ArrayList<String> list = new ArrayList<String>();
//        try {
//            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
//            SOAPBody body = envelope.getBody();
//
//            NodeList nodeList = body.getChildNodes();
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (i != 0) {
//                    list.add(node.getTextContent());
//                }
//            }
//        } catch (SOAPException e) {
//            LOGGER.error("parse SOAPMessage to file exception - :", e);
//        }
//        return list;
//    }
//}
