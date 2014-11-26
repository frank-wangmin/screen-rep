package com.ysten.local.bss.device.domain;

/**
 * 
 * 类名称：DeviceIp 类描述： 设备ip信息 修改备注：
 * 
 * @version
 */
public class DeviceIp implements java.io.Serializable {

    private static final long serialVersionUID = 8245997925843425637L;
    private Long id;
    private String ipSeg;
    private int maskLength;
    private Long startIpValue;
    private Long endIpValue;

    public DeviceIp() {

    }

    public DeviceIp(Long id, String ipSeg, int maskLength, Long startIpValue, Long endIpValue) {
        this.id = id;
        this.ipSeg = ipSeg;
        this.maskLength = maskLength;
        this.startIpValue = startIpValue;
        this.endIpValue = endIpValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpSeg() {
        return ipSeg;
    }

    public void setIpSeg(String ipSeg) {
        this.ipSeg = ipSeg;
    }

    public int getMaskLength() {
        return maskLength;
    }

    public void setMaskLength(int maskLength) {
        this.maskLength = maskLength;
    }

    public Long getStartIpValue() {
        return startIpValue;
    }

    public void setStartIpValue(Long startIpValue) {
        this.startIpValue = startIpValue;
    }

    public Long getEndIpValue() {
        return endIpValue;
    }

    public void setEndIpValue(Long endIpValue) {
        this.endIpValue = endIpValue;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
