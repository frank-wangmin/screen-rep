package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.utils.bean.IEnumDisplay;

/**
 * 
 * 类名称：DeviceVendor 类描述： 设备厂商信息 修改备注：
 * 
 * @version
 */
public class DeviceVendor implements java.io.Serializable {

    private static final long serialVersionUID = -4695075790187532345L;
    private Long id;
    private String code;
    private String name;
    private State state;
    private String description;
    private Date createDate;

    public DeviceVendor() {
    }

    public DeviceVendor(Long id, String code, String name, State state, String description, Date createDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.state = state;
        this.description = description;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取设备厂商创建时间
     * 
     * @return
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置设备厂商创建时间
     * 
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取设备厂商的名称
     * 
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置设备厂商的名称
     * 
     * @param firmName
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取生产厂商Code
     * 
     * @return 厂商Code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置生产厂商Code
     * 
     * @param code
     *            厂商Code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取设备供应商状态
     * 
     * @return 设备供应商状态
     */
    public State getState() {
        return state;
    }

    /**
     * 设置设备供应商状态
     * 
     * @param state
     *            设备供应商状态
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * 获取设备供应商描述信息
     * 
     * @return 描述信息
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置设备供应商的描述信息
     * 
     * @param description
     *            描述信息
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString(){
    	return this.name;
    }

    /**
     * 设备供应商的状态
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName DeviceVendor.java
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