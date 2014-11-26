package com.ysten.local.bss.logger.repository;

import com.ysten.local.bss.logger.domain.DeviceOperateLog;
import com.ysten.utils.page.Pageable;

import java.util.List;

/**
 * Created by frank on 14-7-8.
 */
public interface IDeviceOperateLogRepository {

    void deleteByPrimaryKey(Long id);

    void insertSelective(DeviceOperateLog record);

    DeviceOperateLog selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(DeviceOperateLog record);

    Pageable<DeviceOperateLog> findDeviceOperateLogPagination(String deviceCode, String ip, String status,String result, Integer pageNo, Integer pageSize);

    List<DeviceOperateLog> findDeviceOperateLog(String deviceCode, String ip, String status,String result);

}
