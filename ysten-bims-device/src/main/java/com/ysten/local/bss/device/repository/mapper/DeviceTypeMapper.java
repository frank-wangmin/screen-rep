package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.DeviceType;

/**
 * 类名称：DeviceTypeMapper 类描述： 设备类型
 *
 * @version
 */
public interface DeviceTypeMapper {

    /**
     * 根据主键id查询设备型号
     *
     * @param id
     *            主键ID
     * @return 设备型号
     */
    DeviceType getById(Long id);
    
    DeviceType getDeviceTypeById(Long id);
    /**
     * 添加新的设备型号信息
     * @param dType
     *              待添加的设备型号信息
     * @return
     *
     */
    int save(DeviceType deviceType);
    /**
     * 更新设备型号信息
     * @param dTyple
     *          待更新的设备型号信息
     * @return
     *
     */
    int update(DeviceType deviceTyple);
    /**
     * 列表查询设备类型
     * @return
     */
    List<DeviceType> findAllType();
    /**
     * 通过设备厂商ID查询设备型号
     *
     * @param vendorId
     *            设备厂商ID
     * @return
     */
    List<DeviceType> findDeviceTypeByVendorId(@Param("vendorId") String vendorId,@Param("state")DeviceType.State state);
    /**
     * 分页查询设备类型信息
     *
     * @param deviceTypeName
     *            设备类型名称
     * @param deviceTypeCode
     *            设备型号编号
     * @param pageNo
     *            当前页号
     * @param pageSize
     *            每页大小
     * @return List<DeviceType>
     */
    List<DeviceType> findAllDeviceTypes(@Param("deviceTypeName") String deviceTypeName,
                                        @Param("deviceTypeCode") String deviceTypeCode, @Param("deviceVendorId") String deviceVendorId,
                                        @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
    /**
     * 通过设备类型名称获取总数
     *
     * @param deviceTypeName
     *            设备类型名称
     * @param deviceTypeCode
     *            设备型号编号
     * @return
     */
    int getCountByName(@Param("deviceTypeName") String deviceTypeName,
                       @Param("deviceTypeCode") String deviceTypeCode,
                       @Param("deviceVendorId") String deviceVendorId);
    /**
     *通过设备型号名称和设备型号编号获取平台版本
     * @param deviceVendor
     * @param deviceTypeName
     * @param deviceTypeCode
     * @return
     */
    DeviceType getDeviceTypeByNameOrCode(@Param("deviceVendor")String deviceVendor,
                                         @Param("deviceTypeName")String deviceTypeName,
                                         @Param("deviceTypeCode")String deviceTypeCode);

    /**
     * find deviceTypes by deviceTypeId
     * @param deviceTypeId
     * @return list
     */
    List<DeviceType> findDeviceTypes(@Param("deviceTypeId")Long deviceTypeId);
}