//package com.ysten.local.bss.utils;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.StringReader;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class DataUtils {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);
//    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
//    
//    /**
//     * post以流方式接收
//     */
//    public static String asString(InputStream input, String charsetName) throws IOException {
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        convertInputStream(input, output);
//        return output.toString(charsetName);
//    }
//
//    public static int convertInputStream(InputStream in, OutputStream out) throws IOException {
//        try {
//            int byteCount = 0;
//            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//            int bytesRead = -1;
//            while ((bytesRead = in.read(buffer)) != -1) {
//                out.write(buffer, 0, bytesRead);
//                byteCount += bytesRead;
//                LOGGER.debug("file:bytesRead=" + bytesRead + ", byteCount=" + byteCount);
//            }
//            out.flush();
//            return byteCount;
//        } finally {
//            try {
//                in.close();
//            } catch (IOException ex) {
//                LOGGER.warn("Could not close InputStream", ex);
//            }
//            try {
//                out.close();
//            } catch (IOException ex) {
//                LOGGER.warn("Could not close OutputStream", ex);
//            }
//        }
//    }
//
//    /**
//     * post以流方式发送请求
//     * 
//     * @param url
//     *            发送目标
//     * @param paramContent
//     *            发送内容
//     * @return StringBuffer 请求响应
//     */
//    public static StringBuffer submitPost(String url, String paramContent) {
//        StringBuffer responseMessage = null;
//        java.net.URLConnection connection = null;
//        java.net.URL reqUrl = null;
//        OutputStreamWriter reqOut = null;
//        InputStream in = null;
//        BufferedReader br = null;
//        try {
//            LOGGER.info("post method end then url - :" + url + "\ncontent - :" + paramContent);
//            responseMessage = new StringBuffer();
//            reqUrl = new java.net.URL(url);
//            connection = reqUrl.openConnection();
//            connection.setDoOutput(true);
//            reqOut = new OutputStreamWriter(connection.getOutputStream());
//            reqOut.write(paramContent);
//            reqOut.flush();
//            int charCount = -1;
//            in = connection.getInputStream();
//
//            br = new BufferedReader(new InputStreamReader(in, "GBK"));
//            while ((charCount = br.read()) != -1) {
//                responseMessage.append((char) charCount);
//            }
//            LOGGER.info("post method end then response message - :" + responseMessage);
//        } catch (Exception ex) {
//            LOGGER.error("post data exception - :", ex);
//        } finally {
//            try {
//                in.close();
//                reqOut.close();
//            } catch (Exception e) {
//                LOGGER.error("close connection exception - :", e);
//            }
//        }
//        return responseMessage;
//    }
//
//    public static Element getRoot(String requestXml) throws DocumentException {
//        Document doc = null;
//        try {
//            SAXReader sax = new SAXReader();
//            StringReader reader = new StringReader(requestXml);
//            doc = sax.read(reader);
//        } catch (DocumentException e) {
//            LOGGER.info("error xml from AAA {},exception info {}", requestXml, e);
//            throw new DocumentException("不正确的xml格式,获取接口信息失败", e);
//        }
//        return doc.getRootElement();
//    }
//}
