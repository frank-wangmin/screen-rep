package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.AppSoftwareCode;


public interface AppSoftwareCodeMapper {
    /**
     * 通过应用软件信息号查询
     * @param appSoftwareCode
     * @return
     */
    AppSoftwareCode getAppSoftwareCodeByCode(@Param("code")String code);
    
    AppSoftwareCode getById(long id);
    /**
     * 查询状态为可用的软件号【status='USABLE'】
     * @return
     */
    List<AppSoftwareCode> findAll();
    
    List<AppSoftwareCode> findAppSoftwareCodesByCondition(DeviceUpgradeCondition condition);
            
    int countAppSoftwareCodesByCondition(DeviceUpgradeCondition condition);
    
    int delete(@Param("ids")List<Long> ids);
    
    int save(AppSoftwareCode appSoftwareCode);
    
    int updateByPrimaryKey(AppSoftwareCode appSoftwareCode);

    AppSoftwareCode findSoftwareCodeByName(@Param("name")String name);
    
    int updateDistributeStateById(@Param("id")Long id,@Param("distributeState")String distributeState);
}
