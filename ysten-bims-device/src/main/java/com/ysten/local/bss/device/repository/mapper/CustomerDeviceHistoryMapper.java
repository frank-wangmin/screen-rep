package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.CustomerDeviceHistory;

/**
 * @author 
 */
public interface CustomerDeviceHistoryMapper {
    /**
     * 通过id查询用户设备历史记录
     *
     * @param id
     * @return
     */

    CustomerDeviceHistory getCustomerDeviceHistoryById(Long id);

    /**
     * 根据用户获取当前用户更换过的设备信息
     *
     * @param 
     * @return
     */
    List<CustomerDeviceHistory> findByUidAndCcodeAndOuterCode(@Param("customerCode")String customerCode,@Param("userId")String userId,@Param("customerOuterCode")String customerOuterCode);

    /**
     * 根据设备Id 取得所以的更换设备历史记录
     *
     * @return
     */
    List<CustomerDeviceHistory> findByDeviceCode(@Param("customerCode")String deviceCode);

    /**
     * 新增
     *
     * @param customerDeviceHistory
     * @return
     */
    int saveCustomerDeviceHistory(CustomerDeviceHistory customerDeviceHistory);
    
    List<CustomerDeviceHistory> findHistoryListByConditions(Map<String, Object> map);
    
    List<CustomerDeviceHistory> findHistoryListByIds(@Param("ids")List<Long> ids);
    
    int getCountByConditions(Map<String, Object> map);

}