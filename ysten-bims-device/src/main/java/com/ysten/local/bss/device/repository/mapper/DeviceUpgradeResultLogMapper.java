package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.domain.DeviceUpgradeResultLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DeviceUpgradeResultLogMapper {
	
	 int insertDeviceUpgradeResultLog(DeviceUpgradeResultLog deviceUpgradeResultLog);

     List<DeviceUpgradeResultLog> findListByDeviceCodeAndYstenId(@Param("deviceCode") String deviceCode,@Param("ystenId") String ystenId);
     
     List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCodeAndYstId(@Param("deviceCode") String deviceCode,@Param("ystenId") String ystenId,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
     
     int getCountByCodeAndYstId(@Param("deviceCode") String deviceCode,@Param("ystenId") String ystenId);
     
     List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCondition(Map<String, Object> map);
     
     int getCountByCondition(Map<String, Object> map);
     
     List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByIds(@Param("ids") List<Long> ids);
     List<DeviceUpgradeResultLog> findExportDeviceUpgradeResultLog(Map<String, Object> map);
}
