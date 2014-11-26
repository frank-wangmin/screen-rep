package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.DeviceVendor;

/**
 * DeviceVendorMapper接口
 *
 * @fileName DeviceVendorMapper.java
 */
public interface DeviceVendorMapper {

    /**
     * 添加设备供应商
     * @param dVendor
     *          设备供应商
     * @return
     *          影响的行数
     */
    int save(DeviceVendor dVendor);

    /**
     * 更新设备供应商
     * @param dVendor
     *          设备供应商
     * @return
     *          影响的行数
     */
    int update(DeviceVendor dVendor);
    /**
     * 下拉列表查询设备厂商
     */
    List<DeviceVendor> findAllVendor(@Param("state")DeviceVendor.State state);

    /**
     * 根据主键ID，获取设备供应商
     *
     * @param id
     * @return
     */
    DeviceVendor getById(Long id);
    /**
     * 分页查询设备厂商信息
     *
     * @param deviceVendorName
     *            设备厂商名称
     * @param deviceVendorCode
     *            设备厂商编号
     * @param pageNo
     *            当前页号
     * @param pageSize
     *            每页大小
     * @return List<DeviceVendor>
     */
    List<DeviceVendor> findAllDeviceVendors(@Param("deviceVendorName") String deviceVendorName,@Param("deviceVendorCode") String deviceVendorCode, @Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);
    /**
     * 通过设备厂商名称获取总数
     *
     * @param deviceVendorName
     *            设备厂商名称
     * @param deviceVendorCode
     *            设备厂商编号
     * @return
     */
    int getCountByName(@Param("deviceVendorName") String deviceVendorName,@Param("deviceVendorCode") String deviceVendorCode);
    /**
     *通过设备厂商名称和设备厂商编号获取平台版本
     * @param deviceVendorName
     * @param deviceVendorCode
     * @return
     */
    DeviceVendor getDeviceVendorByNameOrCode(@Param("deviceVendorName") String deviceVendorName,
                                             @Param("deviceVendorCode") String deviceVendorCode);

    /**
     * Find deviceVendors by vendorId
     * @param deviceVendorId
     * @return
     */
    List<DeviceVendor> findVendorsById(@Param("deviceVendorId") Long deviceVendorId);
}