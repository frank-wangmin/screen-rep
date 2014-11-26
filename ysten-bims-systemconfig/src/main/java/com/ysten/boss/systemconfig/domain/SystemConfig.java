package com.ysten.boss.systemconfig.domain;

import java.io.Serializable;

public class SystemConfig implements Serializable {

	private static final long serialVersionUID = 4253407349442373235L;

	/**
     * 主键
     */
    private Integer id;
    
    /**
     * 关键字
     */
    private String configKey;
    
    /**
     * 值
     */
    private String configValue;
    
    /**
     * 中文名
     */
    private String zhName;
    
	/**
     * 描述
     */
    private String depiction;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getZhName() {
		return zhName;
	}

	public void setZhName(String zhName) {
		this.zhName = zhName;
	}

	public String getDepiction() {
		return depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}

    public SystemConfig(){}
    
    public SystemConfig(Integer id,String configKey,String configValue,String zhName,String depiction)
    {
    	this.id = id;
    	this.configKey = configKey;
    	this.configValue = configValue;
    	this.zhName = zhName;
    	this.depiction = depiction;
    }
}
