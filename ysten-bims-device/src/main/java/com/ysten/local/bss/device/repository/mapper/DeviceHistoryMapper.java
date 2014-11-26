package com.ysten.local.bss.device.repository.mapper;

import org.apache.ibatis.annotations.Param;
import com.ysten.local.bss.device.domain.DeviceHistory;

/**
 * DeviceHistory接口
 * 
 * @fileName DeviceHistory.java
 */
public interface DeviceHistoryMapper {

    /**
     * 根据主键ID查询设备历史
     * 
     * @param id
     *            主键ID
     * @return 设备信息
     */
    DeviceHistory getDeviceById(Long id);

    /**
     * 根据ystenId查询设备历史
     * @param ystenId
     * @return
     */
    DeviceHistory getDeviceByYstenId(@Param("ystenId")String ystenId);

	/**
	 * 保存设备历史信息
	 * 
	 * @param deviceHistory
	 * @return
	 */
    
	int save(DeviceHistory deviceHistory);
	 /**
     * 更新设备历史信息
     * 
     * @param deviceHistory
     * @return
     */
    int update(DeviceHistory deviceHistory);
	
}