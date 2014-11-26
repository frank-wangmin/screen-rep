package com.ysten.local.bss.device.api.domain.request;

import org.apache.commons.lang.StringUtils;

public class DefaultPauseOrRecoverDeviceRequest implements IPauseOrRecoverDeviceRequest {
	/**
	 * 交互系统代码
	 */
	private String systemCode;
	/**
	 * 终端序列号
	 */
	private String sno;
	/**
	 * 暂停:pause，恢复:recover
	 */
	private String action;
	/**
	 * 外部用户id
	 */
	private String userId;
	/**
	 * ONLY-只更新单个设备，ALL- 更新用户下的所有设备
	 */
	private String type;
	public DefaultPauseOrRecoverDeviceRequest(String systemCode, String sno, String action, String userId, String type) {
		this.systemCode = systemCode;
		this.sno = sno;
		this.action = action;
		this.userId = userId;
		if(StringUtils.isBlank(type)){
		    this.type = "ONLY";
		}else{
		    this.type = type;
		}
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString(){
        return "systemCode="+systemCode+",sno="+sno+",action="+action+",userId="+userId+",type="+type;
    }
}
