package com.ysten.local.bss.device.domain;

import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.util.bean.Required;

import java.util.Date;


/**
 * 设备软件号
 * 
 * @author hxy
 * 
 */
public class DeviceSoftwareCode implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1278689342870379641L;

	/**
	 * 软件号
	 */
	@Required
	private Long id;

	/**
	 * 名称
	 */
	@Required
	private String name;

	/**
	 * 编码
	 */
	@Required
	private String code;

	/**
	 * 创建时间
	 */
	@Required
	private Date createDate;

	/**
	 * 状态
	 */
	private EnumConstantsInterface.Status status;

	/**
	 * 描述
	 */
	private String description;

    private Date lastModifyTime;
    
    private String operUser;
    @Required
    private DistributeState distributeState = DistributeState.UNDISTRIBUTE;
    
	public DistributeState getDistributeState() {
		return distributeState;
	}

	public void setDistributeState(DistributeState distributeState) {
		this.distributeState = distributeState;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public EnumConstantsInterface.Status getStatus() {
		return status;
	}

	public void setStatus(EnumConstantsInterface.Status status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	@Override
	public String toString(){
		return this.code;
	}
}