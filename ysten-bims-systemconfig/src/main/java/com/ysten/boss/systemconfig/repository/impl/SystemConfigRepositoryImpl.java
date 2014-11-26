package com.ysten.boss.systemconfig.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.boss.systemconfig.domain.SystemConfig;
import com.ysten.boss.systemconfig.repository.ISystemConfigRepository;
import com.ysten.boss.systemconfig.repository.mapper.SystemConfigMapper;
import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;
import com.ysten.utils.page.Pageable;
@Service
public class SystemConfigRepositoryImpl implements ISystemConfigRepository{
	
	@Autowired
	private SystemConfigMapper systemConfigMapper;

    private static final String SYSTEM_CONFIG = "ysten:local:bss:systemconfig:configKey:";
	
	@Override
    @MethodCache(key=SYSTEM_CONFIG + "#{configKey}")
	public SystemConfig getSystemConfigByConfigKey(@KeyParam("configKey")String configKey) {
		return this.systemConfigMapper.findSystemConfigByConfigKey(configKey);
	}

	@Override
	public List<SystemConfig> findAllSystemConfig() {
		return this.systemConfigMapper.findAllSystemConfig();
	}

	@Override
    @MethodFlush(keys = {SYSTEM_CONFIG + "#{configKey}"})
	public boolean updateSystemConfig(@KeyParam("configKey")String configKey,String configValue) {
		return  this.systemConfigMapper.updateSystemConfig(configKey, configValue) > 0;
	}

	@Override
	public Pageable<SystemConfig> findSystemConfigByNameAndConfigKey(
			String configKey, String name, Integer pageNo, Integer pageSize) {
		List<SystemConfig> page = this.systemConfigMapper.findSystemConfigByNameAndKey(configKey, name, pageNo, pageSize);
		int total = this.systemConfigMapper.findCountByNameAndKey(configKey, name);
		return new Pageable<SystemConfig>().instanceByPageNo(page, total, pageNo, pageSize);
	}
	
	public int getNoticePeriodForExpiringDevice() {
		SystemConfig config = this.systemConfigMapper.findSystemConfigByConfigKey("notice_expire_days");
		try {
			return Integer.parseInt(config.getConfigValue());
		}
		catch (Exception e) {
			return 15;
		}
	}

}
