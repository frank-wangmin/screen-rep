package com.ysten.boss.systemconfig.repository;

import java.util.List;

import com.ysten.boss.systemconfig.domain.SystemConfig;
import com.ysten.utils.page.Pageable;
/**
 * ISysDeployRepository interface
 * @fileName ISysDeployRepository.java
 */
public interface ISystemConfigRepository{
	
	/**
     * 根据关键字查询系统配置信息
     */
	SystemConfig getSystemConfigByConfigKey(String configKey);
	
	/**
     * 查询系统配置表中的所有信息
     */
	List<SystemConfig> findAllSystemConfig();
	
	/**
     * 修改
     */
	boolean updateSystemConfig(String configKey,String configValue);
	
	Pageable<SystemConfig> findSystemConfigByNameAndConfigKey(String configKey,String name,Integer pageNo, Integer pageSize);

	/**
	 * 返回通知时间，用于决定哪些设备即将在此时间内过期
	 * @return
	 */
	int getNoticePeriodForExpiringDevice();

}
