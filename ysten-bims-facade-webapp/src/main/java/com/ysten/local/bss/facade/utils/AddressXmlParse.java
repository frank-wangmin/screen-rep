package com.ysten.local.bss.facade.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class AddressXmlParse implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressXmlParse.class);
    
    private static final long serialVersionUID = -279530416176112403L;

   
    
    private static AddressXmlParse addressXmlParse = new AddressXmlParse();
    /**
     * 所有服务器地址
     */
    private String allServiceAddress;
    /**
     * 升级服务器地址
     */
    private String updateServiceAddress;
    /**
     * 返回AddressXmlParse对象
     * @return AddressXmlParse对象
     */
    public static AddressXmlParse getInstance() {
        return addressXmlParse;
    }
    /**
     * 返回所有服务器地址
     * @return
     */
    public String getAllServiceAddress() {
        return allServiceAddress;
    }
    /**
     * 返回升级服务器地址
     * @return
     */
    public String getUpdateServiceAddress() {
        return updateServiceAddress;
    }
    /**
     * 构造方法
     */
    private AddressXmlParse() {
        init();
    }
    /**
     * 重新加载服务器地址的配置文件
     */
    public void reflush() {
        LOGGER.info("reload server address configuration file");
        init();
    }
    /**
     * 初始化方法
     */
    private void init() {
        String fileContent = loadFile();
        this.allServiceAddress = parseServiceAddresses(fileContent);
        this.updateServiceAddress = parseUpdateAddress(fileContent);
        LOGGER.info("AddressXmlParse initialization successful");
    }
    /**
     * 读取服务器地址配置文件
     * @return 服务器地址字符串
     */
    private String loadFile() {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        InputStreamReader in = null ;
        try {
            in = new InputStreamReader(getClass().getResourceAsStream("/address.xml"));
            reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
             in.close();
             reader.close();
             
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            LOGGER.info("can't found file address.xml ,please check the file ,{}", e);
        } catch (IOException e) {
            LOGGER.error("io exception", e);
        } finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("address file InputStreamReader close exception", e);
                }
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("address file reader close exception", e);
                }
            }
        }
        return "";
    }
    /**
     * 解析所有服务器地址
     * @param fileContent 服务器地址字符串
     * @return 所有服务器的地址
     */
    private String parseServiceAddresses(String fileContent) {
        String address = null;
        Pattern regex = Pattern.compile("<service id=\"online\">(.*?)</service>");
        Matcher matcher = regex.matcher(fileContent);
        while (matcher.find()) {
            address = matcher.group(1);
        }
        return address;
    }
    /**
     * 解析升级服务器地址
     * @param fileContent  服务器地址字符串
     * @return 升级服务地址
     */
    private String parseUpdateAddress(String fileContent) {
        String updateAddr = null;
        Pattern regex = Pattern.compile("<service id=\"update\">(.*?)</service>");
        Matcher matcher = regex.matcher(fileContent);
        while (matcher.find()) {
            updateAddr = matcher.group(1);
        }
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<Service id=\"updateAddress\">");
        sb.append(updateAddr);
        sb.append("</Service>");
        return sb.toString();
    }
}
