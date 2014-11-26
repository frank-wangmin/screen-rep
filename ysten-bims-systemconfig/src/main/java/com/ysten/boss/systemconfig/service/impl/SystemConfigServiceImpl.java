package com.ysten.boss.systemconfig.service.impl;

import com.ysten.boss.systemconfig.domain.SystemConfig;
import com.ysten.boss.systemconfig.repository.ISystemConfigRepository;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.core.DataNotFoundException;
import com.ysten.utils.page.Pageable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private ISystemConfigRepository systemConfigRepository;

    @Override
    public String getSystemConfigByConfigKey(String configKey) {
        if(StringUtils.isNotBlank(configKey)){
            SystemConfig systemConfig = this.systemConfigRepository.getSystemConfigByConfigKey(configKey.trim());
            if(systemConfig != null){
                return systemConfig.getConfigValue();
            }
        }
        throw new DataNotFoundException("没有找到"+configKey+"对应的系统配置信息");
    }

    @Override
    public Pageable<SystemConfig> findAllSystemConfig() {
        List<SystemConfig> list = this.systemConfigRepository.findAllSystemConfig();
        return new Pageable<SystemConfig>().instanceByStartRow(list, list.size(), 0, list.size());
    }

    @Override
    public List<SystemConfig> findAllSystemConfigList() {
        return this.systemConfigRepository.findAllSystemConfig();
    }

    @Override
    public Pageable<SystemConfig> findSystemConfigByConfigKey(String configKey) {
        List<SystemConfig> list = new ArrayList<SystemConfig>();
        if(configKey != null){
            SystemConfig systemConfig = this.systemConfigRepository.getSystemConfigByConfigKey(configKey.trim());
            if(systemConfig != null){
                list.add(systemConfig);
            }
        }
        return new Pageable<SystemConfig>().instanceByStartRow(list, list.size(), 0, list.size());
    }

    @Override
    public boolean updateSystemConfig(String configKey, String configValue) {
        if(StringUtils.isBlank(configKey) || StringUtils.isBlank(configValue)){
            return false;
        }
        return this.systemConfigRepository.updateSystemConfig(configKey.trim(), configValue.trim());
    }

    @Override
    public SystemConfig getSystemConfigInfoByConfigKey(String configKey) {
        return this.systemConfigRepository.getSystemConfigByConfigKey(configKey);
    }

	@Override
	public Pageable<SystemConfig> findSystemConfigByNameAndConfigKey(
			String configKey, String name,Integer pageNo, Integer pageSize) {
		return this.systemConfigRepository.findSystemConfigByNameAndConfigKey(configKey, name, pageNo, pageSize);
	}
}
