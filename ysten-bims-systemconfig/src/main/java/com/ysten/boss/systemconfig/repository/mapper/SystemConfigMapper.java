package com.ysten.boss.systemconfig.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.boss.systemconfig.domain.SystemConfig;

public interface SystemConfigMapper {
	
	/**
     * 查询系统配置表中的所有信息
     * @param id
     * @return
     */
	List<SystemConfig> findAllSystemConfig();
	
	/**
     * 根据关键字查询系统配置信息
     * @param configKey
     * @return
     */
	SystemConfig findSystemConfigByConfigKey(@Param("configKey")String configKey);
	
	/**
     * 修改
     * @param id
     * @param configValue
     * @return
     */
	int updateSystemConfig(@Param("configKey")String configKey ,@Param("configValue")String configValue);
	
	
	List<SystemConfig> findSystemConfigByNameAndKey(@Param("configKey")String configKey,@Param("name")String name,@Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);
	
	int findCountByNameAndKey(@Param("configKey")String configKey,@Param("name")String name);

}
