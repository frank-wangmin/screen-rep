package com.ysten.boss.systemconfig.service;

import com.ysten.boss.systemconfig.domain.SystemConfig;
import com.ysten.utils.page.Pageable;

import java.util.List;

public interface SystemConfigService {
	
	/**
     * 根据关键字查询系统配置信息
     */
	String getSystemConfigByConfigKey(String configKey);
	
	/**
     * 加载系统配置表中的所有信息
     */
	Pageable<SystemConfig> findAllSystemConfig();

    List<SystemConfig> findAllSystemConfigList();
	
	/**
     * 根据关键字查询系统配置信息
     */
	Pageable<SystemConfig> findSystemConfigByConfigKey(String configKey);
	
	Pageable<SystemConfig> findSystemConfigByNameAndConfigKey(String configKey,String name,Integer pageNo, Integer pageSize);
	
	SystemConfig getSystemConfigInfoByConfigKey(String configKey);
	
	/**
     * 修改
     */
	boolean updateSystemConfig(String configKey, String configValue);
	
	/**
     * 加载缓存
     */
//	boolean loadSystemConfig();
	
}
