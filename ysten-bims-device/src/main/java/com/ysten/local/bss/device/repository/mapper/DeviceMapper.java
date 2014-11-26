package com.ysten.local.bss.device.repository.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerRelationDeviceVo;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.Device.State;
import com.ysten.local.bss.device.domain.DeviceActiveStatistics;
import com.ysten.local.bss.device.domain.UserActiveStatistics;

/**
 * DevieMapper接口
 *
 * @fileName DeviceMapper.java
 */
public interface DeviceMapper {

    String CODE = "code";
    String DEVICE_VENDOR_ID = "deviceVendorId";
    String DEVICE_TYPE_ID = "deviceTypeId";
    String AREA_ID = "areaId";
    String PLATFROM_NAME_ID = "platformNameId";
    String BIND_STATE = "bindState";
    String CUSTOMER_CODE = "customerCode";
    String STATE = "state";
    String SIZE = "size";
    String SP_DEFINE_ID = "spDefineId";

    /**
     * 根据主键ID查询设备
     *
     * @param id 主键ID
     * @return 设备信息
     */
    Device getDeviceById(Long id);

    /**
     * 根据设备号检索设备信息
     *
     * @param code 终端设备code
     * @return 设备信息
     */
    Device getDeviceByCode(String code);

    /**
     * 根据ystenId查询设备
     *
     * @param ystenId
     * @return
     */
    Device getDeviceByYstenId(@Param("ystenId") String ystenId);

    /**
     * 功能描述：根据mac查询设备详细信息
     *
     * @param mac
     * @return Device
     */
    Device getDeviceByMac(@Param("mac") String mac);

    /**
     * 功能描述：根据sno查询设备详细信息
     *
     * @param sno
     * @return Device
     */
    Device getDeviceBySno(@Param("sno") String sno);

    /**
     * 根据sno和Expiredate>=当前时间，查询合约未到期的设备信息
     *
     * @param sno
     * @return
     */
    Device getDeviceBySnoandExpiredate(@Param("sno") String sno);

    /**
     * 更新设备信息
     *
     * @param device
     * @return
     */
    int update(Device device);

    int updateByYstenId(Device device);

    Device getNoAuthDevice();

    /**
     * 查询对应城市前一天到货的设备
     *
     * @return
     */
    long getAllDeviceCreatedLastDay(Map<String, Object> map);

    /**
     * 查询设备信息
     *
     * @param code           设备编号（模糊查找）
     * @param deviceVendorId 设备供应商ID
     * @param deviceTypeId   设备型号ID
     * @param areaId         设备所在区域ID
     * @param state          设备状态
     * @param bindState      设备绑定状态
     * @param index          索引所在行号
     * @param size           获取的记录数
     * @return 符合条件的设备信息
     */
    List<Device> findByState(@Param("bindType") String bindType, @Param("isLock") String isLock, @Param(CODE) String code, @Param("mac") String mac, @Param("sno") String sno, @Param(DEVICE_VENDOR_ID) Long deviceVendorId,
                             @Param(DEVICE_TYPE_ID) Long deviceTypeId, @Param(AREA_ID) Long areaId, @Param(STATE) State state, @Param(BIND_STATE) BindType bindState, @Param(SP_DEFINE_ID) Long spDefineId,
                             @Param("productNo") String productNo, @Param("distributeState") Device.DistributeState distributeState, @Param("index") int index, @Param(SIZE) int size);

    /**
     * 查询设备信息
     *
     * @param code           设备编号（模糊查找）
     * @param deviceVendorId 设备供应商ID
     * @param deviceTypeId   设备型号ID
     * @param areaId         设备所在区域ID
     * @param state          设备状态
     * @param bindState      设备绑定状态
     * @param index          索引所在行号
     * @param size           获取的记录数
     * @return 符合条件的设备信息
     */
    List<Device> findAllByState(@Param("bindType") String bindType, @Param("isLock") String isLock, @Param("ystenId") String ystenId, @Param(CODE) String code, @Param("mac") String mac, @Param("sno") String sno, @Param(DEVICE_VENDOR_ID) Long deviceVendorId,
                                @Param(DEVICE_TYPE_ID) Long deviceTypeId, @Param(AREA_ID) Long areaId, @Param(STATE) State state, @Param(BIND_STATE) BindType bindState, @Param(SP_DEFINE_ID) Long spDefineId,
                                @Param("productNo") String productNo, @Param("distributeState") Device.DistributeState distributeState, @Param("index") int index, @Param(SIZE) int size);


    /**
     * 查询设备信息的数量
     *
     * @param code           设备编号（模糊查找）
     * @param deviceVendorId 设备供应商ID
     * @param deviceTypeId   设备型号ID
     * @param areaId         设备所在区域ID
     * @param state          设备状态
     * @param bindState      设备绑定状态
     * @return 符合条件设备条数
     */
    int findCountByState(@Param("bindType") String bindType, @Param("isLock") String isLock, @Param(CODE) String code, @Param("ystenId") String ystenId, @Param("mac") String mac, @Param("sno") String sno, @Param(DEVICE_VENDOR_ID) Long deviceVendorId,
                         @Param(DEVICE_TYPE_ID) Long deviceTypeId, @Param(AREA_ID) Long areaId, @Param(STATE) State state, @Param(BIND_STATE) BindType bindState, @Param(SP_DEFINE_ID) Long spDefineId,
                         @Param("productNo") String productNo, @Param("distributeState") Device.DistributeState distributeState);

    List<Device> getByAreaAndType(@Param("province") int province, @Param("E3") int E3, @Param("E4") int E4);

    /**
     * 返回符合条件的设备信息
     *
     * @param map
     * @return 符合条件设备信息
     */
    List<Device> findDevicesByState(Map<String, Object> map);

    List<Device> findDevicesToExport(Map<String, Object> map);
    
    List<Device> QueryDevicesToExport(Map<String, Object> map);
    
    List<Device> findDeviceListByConditions(Map<String, Object> map);
    
    int getCountByConditions(Map<String, Object> map);

    List<Device> findCustomerCanBindDeviceList(Map<String, Object> paramters);

    List<Device> getDeviceNotActivated();

    int getCountCustomerCanBindDeviceList(Map<String, Object> paramters);

    /**
     * 保存设备信息
     *
     * @param device
     * @return
     */
    int save(Device device);

    List<Device> getDeviceByIds(@Param("ids") List<Long> ids);
    
    List<Device> findDeviceListByYstenIds(@Param("ids")String[] ids);

    List<Device> findDeviceListBySnos(@Param("snos")String[] snos);

    List<Device> findDeviceListByMacs(@Param("macs")String[] macs);
    /**
     * 返回易视腾编号为空的设备
     * @return
     */
    List<Device> findDevicesOfBlankYsteId();

    List<Device> findEpgDeviceByDeviceGroupIds(@Param("deviceGroupIdList") List<Long> deviceGroupIdList);

    /**
     * 根据区域Id获取所有终端批次号
     *
     * @param areaId
     * @return
     */
    List<String> getAllProductNoByArea(@Param("areaId") String areaId, @Param("state") DistributeState state);

    /**
     * 根据生产批次获取所有终端信息
     *
     * @param productNo
     * @return
     */
    List<Device> getByProductNo(@Param("productNo") String productNo, @Param("state") Device.DistributeState state);

    /**
     * 获取所有状态变更的设备信息
     *
     * @return
     */
    List<Device> getAllDeviceByStateChange(@Param("num") Integer num);

    Integer getTotalSyncingDevice();

/*    *//**
     * @param type
     * @param from
     * @param to
     * @return
     *//*
    boolean bindDeviceGroup(@Param("type") String type, @Param("from") String from, @Param("to") String to);*/

    int updateSyncById(@Param("id") Long id, @Param("isSync") String isSync);

    int updateSoftCodeVersion(@Param("softCode") String softCode, @Param("versionSeq") int versionSeq,  @Param("ystenId") String ystenId);

    int updateDeviceCode(@Param("deviceCode") String deviceCode, @Param("mac") String mac,  @Param("ystenId") String ystenId,  @Param("description") String description);

    Device getDeviceByCodeSnoAndMac(@Param("device") String device);

    int getCountByIsSync();

    List<DeviceActiveStatistics> getActiveDeviceReport(@Param("activeTime") String activeTime, @Param("province") Long province, @Param("customerList") List<Customer> customerList);

    List<DeviceActiveStatistics> getActiveDeviceChart(@Param("activeTime") String activeTime, @Param("province") Long province, @Param("customerList") List<Customer> customerList);

    List<UserActiveStatistics> getActiveUserReport(@Param("activeDate") String activeDate, @Param("province") Long province);

    List<Device> checkInputSql(@Param("sql") String sql);

    List<Device> findDuoToDeviceByPreOpening();

    int deleteDevice(long id);

    int findCountDevicesBySnos(@Param("snos") String snos);

    List<Device> findDevicesBySnos(@Param("snos") String snos, @Param("index") int index, @Param(SIZE) int size);
    
    List<Device> findDeviceYstenIdListByDeviceCodes(@Param("codes") String codes);

    List<Device> findDevicesByBackImageId(@Param("tableName") String tableName,@Param("character") String character,@Param("deviceGroupId")Long deviceGroupId,@Param("ystenId") String ystenId,@Param(CODE) String code, @Param("mac") String mac, @Param("sno") String sno, @Param("index") int index, @Param(SIZE) int size);

    List<Device> findDevicesByGroupId(@Param("tableName") String tableName,@Param("character") String character,@Param("deviceGroupId")Long deviceGroupId,@Param("ystenId") String ystenId,@Param(CODE) String code, @Param("mac") String mac, @Param("sno") String sno, @Param("softCode")String softCode, @Param("versionSeq") String versionSeq,@Param("index") int index, @Param(SIZE) int size);
    
    List<Device> ExportDevicesOfGroupId(@Param("tableName") String tableName,@Param("character") String character,@Param("deviceGroupId")Long deviceGroupId,@Param("ystenId") String ystenId,@Param(CODE) String code, @Param("mac") String mac, @Param("sno") String sno, @Param("softCode")String softCode, @Param("versionSeq") String versionSeq);

    int findCountDevicesByGroupId(@Param("tableName") String tableName,@Param("character") String character,@Param("deviceGroupId")Long deviceGroupId,@Param("ystenId") String ystenId,@Param(CODE) String code, @Param("mac") String mac, @Param("sno") String sno, @Param("softCode")String softCode, @Param("versionSeq") String versionSeq);

	List<Device> getAllDeviceByIsSync(@Param("num")Integer num);
	
	List<Long> findAreaByBusiness(@Param("Id")Long Id,@Param("character") String character,@Param("tableName") String tableName);
	
	List<Device> findExpirePrepareOpenDevice();
	
	Device getDeviceByCustomerId(@Param("customerId")Long customerId);
	
    List<CustomerRelationDeviceVo> findCustomerDeviceRelationByCondition(Map<String, Object> params);
    
    int getCountCustomerDeviceRelationByCondition(Map<String, Object> params);
    
    List<CustomerRelationDeviceVo> findRelationCustomerByDeviceIds(@Param("deviceIds") List<Long> deviceIds);
    
    List<CustomerRelationDeviceVo> exportRelationListByConditions(Map<String, Object> map);

    int deleteDeviceByBusiness(@Param("ystenList")List<String>ystenList,@Param("Id")Long Id, @Param("character")String character,@Param("tableName")String tableName,@Param("type")String type,@Param("device")String device);
    
    /**
     * 查找过期时间在指定日期之间的设备Yesten ID
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Device> findExpiringDevices(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}