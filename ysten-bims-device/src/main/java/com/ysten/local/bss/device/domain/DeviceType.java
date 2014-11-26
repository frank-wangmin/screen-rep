package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.utils.bean.IEnumDisplay;

/**
 * 
 * 类描述： 设备类型信息，与设备厂商级联 修改备注：
 * 
 * @version
 */
public class DeviceType implements java.io.Serializable {

    private static final long serialVersionUID = -6077988669155517492L;

    private Long id;
    private DeviceVendor deviceVendor;
    private String code;
    private String name;
    private State state;
    private Date createDate;
    private TerminalType terminalType;
    private String description;

    public DeviceType() {
    }

    public DeviceType(Long id, DeviceVendor deviceVendor, String code, String name, State state,
            TerminalType terminalType, Date createDate, String description) {
        this.id = id;
        this.deviceVendor = deviceVendor;
        this.code = code;
        this.name = name;
        this.state = state;
        this.createDate = createDate;
        this.terminalType = terminalType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public TerminalType getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(TerminalType terminalType) {
        this.terminalType = terminalType;
    }

    /**
     * 获取设备厂商
     * 
     * @return
     */
    public DeviceVendor getDeviceVendor() {
        return this.deviceVendor;
    }

    /**
     * 设置设备厂商
     * 
     * @param deviceVendor
     *            设备厂商
     */
    public void setDeviceVendor(DeviceVendor deviceVendor) {
        this.deviceVendor = deviceVendor;
    }

    /**
     * 获取设备类型
     * 
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置设备类型
     * 
     * @param deviceType
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取设备型号的Code
     * 
     * @return 型号的Code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置设备型号的Code
     * 
     * @param code
     *            设备型号Code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取设备状态
     * 
     * @return 设备状态
     */
    public State getState() {
        return state;
    }

    /**
     * 设置设备状态
     * 
     * @param state
     *            设备状态
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * 获取设备描述信息
     * 
     * @return 描述信息
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置设备描述信息
     * 
     * @param description
     *            设备描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString(){
    	return this.name;
    }

    /**
     * 设备型号状态
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName DeviceType.java
     */
    public enum State implements IEnumDisplay {
        USABLE("可用"), UNUSABLE("不可用");

        private String msg;

        private State(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

    }

}