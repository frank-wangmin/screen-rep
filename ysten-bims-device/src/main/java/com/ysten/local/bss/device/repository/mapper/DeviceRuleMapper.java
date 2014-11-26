package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.domain.DeviceRule;

/**
 * DeviceMapper接口
 * 
 * @fileName DeviceRuleMapper.java
 */
public interface DeviceRuleMapper {

    /**
     * 根据主键ID查找终端规则信息
     * 
     * @param id
     *            主键ID
     * @return
     */
    DeviceRule getById(Long id);

    /**
     * 更新终端规则信息
     * 
     * @param dRule
     *            终端规则
     * @return 影响的行数
     */
    int update(DeviceRule dRule);
}