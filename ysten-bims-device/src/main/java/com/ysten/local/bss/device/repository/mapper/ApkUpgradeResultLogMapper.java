package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.domain.ApkUpgradeResultLog;
import com.ysten.local.bss.device.domain.DeviceUpgradeResultLog;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApkUpgradeResultLogMapper {
    int deleteByPrimaryKey(Long id);
    int insert(ApkUpgradeResultLog record);
    ApkUpgradeResultLog selectByPrimaryKey(Long id);
    int updateByPrimaryKey(ApkUpgradeResultLog record);

    int insertApkUpgradeResultLog(ApkUpgradeResultLog apkUpgradeResultLog);

    int getCountByCodeAndYstId(@Param("deviceCode") String deviceCode,@Param("ystenId") String ystenId);

    List<ApkUpgradeResultLog> findApkUpgradeResultLogByCodeAndYstId(@Param("deviceCode") String deviceCode,@Param("ystenId") String ystenId,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<ApkUpgradeResultLog> findApkUpgradeResultLogByCondition(Map<String, Object> map);
    
    int getCountByCondition(Map<String, Object> map);
    
    List<ApkUpgradeResultLog> findApkUpgradeResultLogByIds(@Param("ids") List<Long> ids);
    
    List<ApkUpgradeResultLog> findExportApkUpgradeResultLog(Map<String, Object> map);
}