package com.ysten.local.bss.device.bean;

import org.dom4j.Node;

public class DeviceInfoBean {
    
    //设备sn号
    private Node stbId;
    //设备类型
    private Node loginType;
    //请求时间
    private Node timestamp;
    //设备状态
    private Node status;
    //设备编号
    private Node loginId;
    //用户id
    private Node userId;
    //用户省份
    private Node provinceId;
    //旧设备编号
    private Node oldLoginId;
    //旧设备sn号
    private Node oldStbId;
    //预开通天数，当status值为5时，必填
    private Node presubTime;
    //地市代码
    private Node cityCode;
    //机顶盒MAC地址
    private Node mac;
    
    private String methodName;
    
    public Node getLoginId() {
        return loginId;
    }

    public void setLoginId(Node loginId) {
        this.loginId = loginId;
    }

    public Node getStbId() {
        return stbId;
    }

    public void setStbId(Node stbId) {
        this.stbId = stbId;
    }

    public Node getLoginType() {
        return loginType;
    }

    public void setLoginType(Node loginType) {
        this.loginType = loginType;
    }

    public Node getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Node timestamp) {
        this.timestamp = timestamp;
    }

    public Node getStatus() {
        return status;
    }

    public void setStatus(Node status) {
        this.status = status;
    }

    public Node getUserId() {
        return userId;
    }

    public void setUserId(Node userId) {
        this.userId = userId;
    }

    public Node getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Node provinceId) {
        this.provinceId = provinceId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Node getOldLoginId() {
        return oldLoginId;
    }

    public void setOldLoginId(Node oldLoginId) {
        this.oldLoginId = oldLoginId;
    }

    public Node getOldStbId() {
        return oldStbId;
    }

    public void setOldStbId(Node oldStbId) {
        this.oldStbId = oldStbId;
    }

    public Node getPresubTime() {
        return presubTime;
    }

    public void setPresubTime(Node presubTime) {
        this.presubTime = presubTime;
    }
    
    public Node getCityCode() {
        return cityCode;
    }
    public void setCityCode(Node cityCode) {
        this.cityCode = cityCode;
    }
    public Node getMac() {
        return mac;
    }
    public void setMac(Node mac) {
        this.mac = mac;
    }
    
}
