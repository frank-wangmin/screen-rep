package com.ysten.local.bss.web.service;

import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IDeviceWebService {
    /**
     * 分页检索设备信息
     *
     * @param code           设备编号（模糊检索）
     * @param deviceVendorId 设备供应商ID
     * @param deviceTypeId   设备型号ID
     * @param areaId         设备销售区域ID
     * @param state          设备状态
     * @param bindState      设备绑定状态
     * @param pageNo         当前页号
     * @param pageSize       每页大小
     * @return
     */
    Pageable<Device> findDevicesByState(String bindType, String isLock, String ystenId, String code, String mac, String sno,
                                        Long deviceVendorId, Long deviceTypeId, Long areaId, Device.State state, BindType bindState,
                                        Long spDefineId, String productNo, Device.DistributeState distributeState, int pageNo, int pageSize);


    Pageable<Device> findDevicesByGroupId(String tableName,String character,String deviceGroupId, String ystenId, String code, String mac, String sno,String softCode, String versionSeq,int pageNo, int pageSize);

    Pageable<Device> findDevicesBySnos(String snos, int pageNo, int pageSize);
    
    List<Device> ExportDevicesOfGroupId(String tableName,String character,String deviceGroupId, String ystenId, String code, String mac, String sno, String softCode, String versionSeq);

    List<Device> findDeviceYstenIdListBySnos(String snos);
    
    List<Device> findDeviceYstenIdListByDeviceCodes(String deviceCodes);

    /**
     * 分页查询所有设备厂商信息
     *
     * @param deviceVendorName 设备厂商名称
     * @param deviceVendorCode 设备厂商编号
     * @param pageNo           当前页号
     * @param pageSize         每页大小
     * @return Pageable<DeviceVendor>
     */
    Pageable<DeviceVendor> findAllDeviceVendors(String deviceVendorName, String deviceVendorCode, Integer pageNo, Integer pageSize);


    /**
     * 功能描述：根据deviceId查找Device对象 参数：@param long 返回类型: Device
     */
    Device getDeviceById(long deviceId);

    /**
     * 修改设备信息
     *
     * @param device
     * @return
     */
    boolean updateDevice(Device device);

    /**
     * 功能描述：获取设备生产厂商列表 参数：@param 返回类型: List<DeviceVendor>
     */
    List<DeviceVendor> findDeviceVendors(DeviceVendor.State state);

    /**
     * 功能描述：获取设备类型信息列表 参数：@param 返回类型: List<DeviceType>
     */
    List<DeviceType> findDeviceTypes();

    /**
     * 接口名称: 通过设备类型id查询设备类型信息
     *
     * @return DeviceType
     */
    DeviceType getDeviceTypeById(Long id);

    /**
     * 接口名称: 通过设备生产厂商id查询设备设备生产厂商信息
     *
     * @return DeviceVendor
     */
    DeviceVendor getDeviceVendorById(Long id);

    /**
     * 通过设备厂商ID查询设备型号
     *
     * @param vendorId 设备厂商ID
     * @return
     */
    List<DeviceType> findDeviceTypesByVendorId(String vendorId, DeviceType.State state);
    
    DeviceType getDeviceTypeByTypeId(Long id);

    /**
     * 新增设备厂商信息
     *
     * @param deviceVendor
     * @return
     */
    boolean saveDeviceVendor(DeviceVendor deviceVendor);

    /**
     * 修改设备厂商信息
     *
     * @param deviceVendor
     * @return boolean
     */
    boolean updateDeviceVendor(DeviceVendor deviceVendor);

    /**
     * 分页查询所有设备类型信息
     *
     * @param deviceTypeName 设备类型名称
     * @param deviceTypeCode 设备类型编号
     * @param deviceVendorId 设备厂商
     * @param pageNo         当前页号
     * @param pageSize       每页大小
     * @return Pageable<DeviceType>
     */
    Pageable<DeviceType> findAllDeviceTypes(String deviceTypeName, String deviceTypeCode, String deviceVendorId,
                                            Integer pageNo, Integer pageSize);

    /**
     * 新增设备类型信息
     *
     * @param deviceType
     * @return
     */
    boolean saveDeviceType(DeviceType deviceType);

    /**
     * 修改设备类型信息
     *
     * @param deviceType
     * @return
     */
    boolean updateDeviceType(DeviceType deviceType);

    /**
     * 通过设备型号名称和设备型号编号获取平台版本信息
     *
     * @param deviceVendor
     * @param deviceTypeName
     * @param deviceTypeCode
     * @return
     */
    DeviceType getDeviceTypeByNameOrCode(String deviceVendor, String deviceTypeName, String deviceTypeCode);

    /**
     * 通过平台版本名称和平台版本编号获取平台版本信息
     *
     * @param deviceVendorName
     * @param deviceVendorCode
     * @return
     */
    DeviceVendor getDeviceVendorByNameOrCode(String deviceVendorName, String deviceVendorCode);

    /**
     * 通过设备id查询设备信息
     *
     * @param id
     * @return
     */
    Device getDeviceById(Long id);

    /**
     * 分页检索ip地址库信息
     *
     * @param ipSeg    ip地址段
     * @param pageNo   当前页号
     * @param pageSize 每页大小
     * @return
     */
    Pageable<DeviceIp> findDeviceIpByIpSeg(String ipSeg, int pageNo, int pageSize);

    /**
     * 保存ip地址库
     *
     * @param deviceIp
     * @return
     */
    boolean saveDeviceIp(DeviceIp deviceIp);

    /**
     * 修改ip地址库
     *
     * @param deviceIp
     * @return
     */
    boolean updateDeviceIp(DeviceIp deviceIp);

    /**
     * 通过ip查询
     *
     * @param id
     * @return
     */
    DeviceIp getDeviceIpById(Long id);

    /**
     * 批量删除ip地址库信息
     *
     * @param ids
     * @return
     */
    boolean deleteDeviceIp(List<Long> ids);

    /**
     * 检查ip在数据库中是否存在
     *
     * @param ipSeg
     * @return
     */
    boolean checkDeviceIpExists(String ipSeg);

    /**
     * 是否锁定设备
     *
     * @param ids
     * @param par
     * @return
     */
    boolean lockDevice(List<Long> ids, String par);

    /**
     * 修改设备信息
     *
     * @param deviceId
     * @param deviceState
     * @param mac
     * @param sno
     * @return
     */
    String updateDevice(String deviceId, Device.State deviceState, String mac, String deviceVendorId, String deviceTypeId, String expireDate, String area, String city, String spCode, String sno) throws ParseException;

    /**
     * 返回符合条件的所有设备信息
     *
     * @param map
     * @return
     */
    List<Device> findDevicesByState(Map<String, Object> map);

    /**
     * 导入设备信息文档
     *
     * @param targetFile
     * @return
     */
    Map<String, Object> importDeviceFile(File targetFile) throws Exception;
    
    Map<String, Object> createDeviceYstenId(List<Device> deviceList) throws Exception;
    
    List<Device>  findDevicesOfBlankYsteId();

    List<Device> getDeviceByIds(List<Long> ids);

    List<Device> findDevicesToExport(Map<String, Object> map);
    
    List<Device> QueryDevicesToExport(Map<String, Object> map);
    
    Pageable<Device> findDeviceListByConditions(Map<String, Object> map);

    /**
     * 下发终端
     *
     * @param productNo
     * @return
     */
    boolean distributeDevice(String productNo) throws Exception;

    /**
     * @param productNo
     * @return
     */
    boolean pickupDevice(String productNo) throws Exception;

    /**
     * 根据类型获取分组信息
     *
     * @return
     */
    Pageable<DeviceGroup> findDeviceGroupByType(String name, String type, String pageNo, String pageSize);

    /**
     * 根据类型名称获取分组信息
     *
     * @return
     */
    Pageable<DeviceGroup> findDeviceGroupByTypeName(String name, String type, String pageNo, String pageSize);

    /**
     * 根据类型名称区域获取分组信息
     *
     * @return
     */
    Pageable<DeviceGroup> findDeviceGroupByTypeNameDist(String name, String type, Long areaId, String pageNo, String pageSize);

    /**
     * 新增设备分组信息
     *
     * @return
     */
    boolean saveDeviceGroup(DeviceGroup deviceGroup);

    /**
     * 根据主键Id获取设备分组信息
     *
     * @param groupId
     * @return
     */
    DeviceGroup getDeviceGroupById(Long groupId);

    /**
     * 更新设备分组信息
     *
     * @param deviceGroup
     * @return
     */
    boolean updateDeviceGroup(DeviceGroup deviceGroup);

    /**
     * 删除设备分组信息
     *
     * @param idsList
     * @return
     */
    boolean deleteDeviceGroup(List<Long> idsList);

    /**
     * 删除设备信息
     *
     * @param deviceIds
     * @return
     */
    String deleteDevice(List<Long> deviceIds);

    /**
     * @param idsList
     * @return
     */
    boolean deleteDeviceGroupBusiness(List<Long> idsList);

    /**
     * 删除设备分组与设备关系  根据设备分组ID
     *
     * @param idsList
     * @return
     */
    boolean deleteDeviceGroupMap(List<Long> idsList);

    boolean deleteDeviceByGroupId(Long deviceGroupId,List<String> idsList);

    /**
     * 删除设备分组与设备关系  根据设备CODE
     *
     * @param idsList
     * @return
     */
    boolean deleteDeviceGroupMapByCode(List<Long> idsList);

    /**
     * 删除设备分组信息   by 设备分组的关系
     *
     * @param idsList
     * @return
     */
    String deleteDeviceGroupByCondition(List<Long> idsList);

    /**
     * find device group by and group type
     *
     * @param isDynamic
     * @param type
     * @return
     */
    List<DeviceGroup> findDeviceGroupListByType(EnumConstantsInterface.DeviceGroupType type, String isDynamic);

    /**
     * find device group by districtCode,type and bootId
     *
     * @param id
     * @param districtCode
     * @param tableName
     * @param character
     * @param type
     * @return
     */
    List<DeviceGroup> findDeviceGroupByDistrictCode(EnumConstantsInterface.DeviceGroupType type, String tableName,String character,String districtCode, String id);

    List<Long> findDeviceGroupsById(String noticeId,String character,String tableName);
    
    List<Long> findAreaByBusiness(Long Id, String character,String tableName);

    /**
     * 根据省份获取所有未分发的批次号
     *
     * @return
     */
    List<String> getAllProductNoByArea(String area, DistributeState state);

    /**
     * 同步所有状态改变的设备信息到中心
     *
     * @return
     */
    boolean syncDevice() throws Exception;

    /**
     * 同步状态变更的设备信息到中心
     */
    void synchronizeDevice() throws Exception;

    List<Device> findNeedSyncDevices(Integer num);

    String bindDeviceGroupMap(String id, String ystenIds);
    
    String addDeviceGroupMap(String id, String ystenIds);
    
    String groupBindDeviceMap(String id, String ystenIds)throws Exception;

    int getCountByIsSync();
    /**
     * 批量插入设备与设备组关系--DeviceGroupMap
     * @param list
     */
    void BulkSaveDeviceGroupMap(List<DeviceGroupMap> list);

    boolean rendSoftCode(List<Device> devices) throws Exception;

    /**
     * check the input SQL
     *
     * @param sql
     * @return
     */
    String checkInputSql(String sql);

    /**
     * find the maps by deviceGroupId
     *
     * @param deviceGroupId
     * @return list
     */
    List<DeviceGroupMap> findDeviceGroupMapByGroupId(String deviceGroupId);

    /**
     * find the maps by deviceCode
     *
     * @param deviceCode
     * @return list
     */
    Map<String, Object> findDeviceGroupMapByDeviceCode(String deviceCode);
    
    Map<String, Object> findDeviceGroupByYstenIdAndArea(String ystenId);

    Map<String, Object> findDeviceRelateBusinessByYstenIdOrGroupId(String ystenId, Long groupId);

    boolean deleteDeviceBusiness(List<Long> idsList);

    void deleteDeviceByBusiness(String  ystenIds,Long bussinessId, String character,String tableName,String type,String device);

    String validatorSno(String snos);
    
    String validatorDeviceCode(String deviceCodes);

    String saveDeviceBusinessBind(String ids, String bootId, String animationId, String panelId, String noticeIds, String backgroundIds, String upgradeIds, String apkUpgradeIds);

    String saveGroupBusinessBind(String ids, String bootId, String animationId, String panelId, String noticeIds, String backgroundIds, String upgradeIds, String apkUpgradeIds);

}
