package com.ysten.local.bss.device.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ysten.local.bss.device.domain.ApkSoftwareCode;
import com.ysten.local.bss.device.domain.ApkUpgradeResultLog;
import com.ysten.local.bss.device.domain.AppUpgradeMap;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.DeviceActiveStatistics;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.DeviceGroupMap;
import com.ysten.local.bss.device.domain.DeviceIp;
import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.domain.DeviceType;
import com.ysten.local.bss.device.domain.DeviceUpgradeMap;
import com.ysten.local.bss.device.domain.DeviceUpgradeResultLog;
import com.ysten.local.bss.device.domain.DeviceVendor;
import com.ysten.local.bss.device.domain.UpgradeTask;
import com.ysten.local.bss.device.domain.UserActiveStatistics;
import com.ysten.local.bss.device.domain.UserUpgradeMap;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

/**
 * IDeviceRepository interface
 * 
 * @fileName IDeviceRepository.java
 */
public interface IDeviceRepository {

	/**
	 * 
	 * 功能描述：根据deviceId查找Device对象 参数：@param long 返回类型: Device
	 */
	Device getDeviceById(long deviceId);

	/**
	 * 功能描述：根据Code查询设备详细信息
	 * 
	 * @param deviceCode
	 * @return Device
	 */
	Device getDeviceByCode(String deviceCode);

	Device getDeviceByYstenId(String ystenId);

	Device getDeviceByYstenIdNR(String ystenId);

	/**
	 * 功能描述：根据mac查询设备详细信息
	 * 
	 * @param mac
	 * @return Device
	 */
	Device getDeviceByMac(String mac);

	/**
	 * 功能描述：根据sno查询设备信息
	 */
	Device getDeviceBySno(String sno);

	/**
	 * 更新设备信息
	 * 
	 * @param device
	 * @return
	 */
	boolean updateDevice(Device device);

	boolean updateDeviceByYstenId(Device device);

	/**
	 * 接口名称: 通过设备生产厂商id查询设备设备生产厂商信息
	 * 
	 * @return DeviceVendor
	 */
	DeviceVendor getDeviceVendorById(Long id);

	/**
	 * 接口名称: 通过设备类型id查询设备类型信息
	 * 
	 * @return DeviceType
	 */
	DeviceType getDeviceTypeById(Long id);

	DeviceType getDeviceTypeByTypeId(Long id);

	/**
	 * 查询昨天销户的用户-设备关系
	 * 
	 * @return
	 */
	// List<CustomerDeviceHistory> getAllCustomerDeviceHistoryCreatedLastDay();

	/**
	 * 根据sno和Expiredate>=当前时间，查询合约未到期的设备信息
	 * 
	 * @param sno
	 * @return
	 */
	Device getDeviceBySnoandExpiredate(String sno);

	/**
	 * 根据计算后ip获取ip是否存在库中
	 * 
	 * @param ipValue
	 * @return
	 */
	List<DeviceIp> getDeviceIpByIpValue(Long ipValue);

	/**
	 * 新增
	 * 
	 * @param customerDeviceHistory
	 * @return
	 */
	boolean saveCustomerDeviceHistory(
			CustomerDeviceHistory customerDeviceHistory);

	/**
	 * 查询sno为空的设备
	 * 
	 * @return
	 */
	Device getNoAuthDevice();

	/**
	 * 查询对应城市前一天到货的设备
	 * 
	 * @return
	 */
	long getAllDeviceCreatedLastDay(String cityId);

	/**
	 * 分页显示设备信息
	 * 
	 * @param code
	 *            设备编号（模糊检索）
	 * @param deviceVendorId
	 *            设备供应商ID
	 * @param deviceTypeId
	 *            设备型号ID
	 * @param areaId
	 *            设备区域ID
	 * @param state
	 *            设备状态
	 * @param bindState
	 *            设备绑定状态
	 * @param pageNo
	 *            当前页号
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	Pageable<Device> findDevicesByState(String bindType, String isLock,
			String ystenId, String code, String mac, String sno,
			Long deviceVendorId, Long deviceTypeId, Long areaId,
			Device.State state, BindType bindState, Long spDefineId,
			String productNo, Device.DistributeState distributeState,
			int pageNo, int pageSize);

	Pageable<Device> findDevicesBySnos(String snos, int pageNo, int pageSize);

	List<Device> findDevicesBySnos(String snos);

	List<Device> findDeviceYstenIdListByDeviceCodes(String codes);

	List<Device> ExportDevicesOfGroupId(String tableName, String character,
			Long deviceGroupId, String ystenId, String code, String mac,
			String sno, String softCode, String versionSeq);

	Pageable<Device> findDevicesByGroupId(String tableName, String character,
			Long deviceGroupId, String ystenId, String code, String mac,
			String sno, String softCode, String versionSeq, int pageNo,
			int pageSize);

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
	 * @return Pageable<DeviceVendor>
	 */
	Pageable<DeviceVendor> findAllDeviceVendors(String deviceVendorName,
			String deviceVendorCode, Integer pageNo, Integer pageSize);

	/**
	 * 
	 * 功能描述：获取设备生产厂商列表参数：@param 返回类型: List<DeviceVendor>
	 */
	List<DeviceVendor> findDeviceVendors(DeviceVendor.State state);

	/**
	 * 
	 * 功能描述：获取设备类型信息列表参数：@param 返回类型: List<DeviceType>
	 */
	List<DeviceType> findDeviceTypes();

	List<Device> getDeviceNotActivated();

	/**
	 * 通过设备厂商ID查询设备型号
	 * 
	 * @param vendorId
	 *            设备厂商ID
	 * @return
	 */
	List<DeviceType> findDeviceTypesByVendorId(String vendorId,
			DeviceType.State state);

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
	 * 分页查询设备类型信息
	 * 
	 * @param deviceTypeName
	 *            设备类型名称
	 * @param deviceTypeCode
	 *            设备类型编号
	 * @param deviceVendorId
	 *            设备厂商
	 * @param pageNo
	 *            当前页号
	 * @param pageSize
	 *            每页大小
	 * @return Pageable<DeviceType>
	 */
	Pageable<DeviceType> findAllDeviceTypes(String deviceTypeName,
			String deviceTypeCode, String deviceVendorId, Integer pageNo,
			Integer pageSize);

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
	DeviceType getDeviceTypeByNameOrCode(String deviceVendor,
			String deviceTypeName, String deviceTypeCode);

	/**
	 * 通过平台版本名称和平台版本编号获取平台版本信息
	 * 
	 * @param deviceVendorName
	 * @param deviceVendorCode
	 * @return
	 */
	DeviceVendor getDeviceVendorByNameOrCode(String deviceVendorName,
			String deviceVendorCode);

	/**
	 * 分页检索ip地址库信息
	 * 
	 * @param ipSeq
	 *            ip地址段
	 * @param pageNo
	 *            当前页号
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	Pageable<DeviceIp> findDeviceIpByIpSeg(String ipSeq, int pageNo,
			int pageSize);

	/**
	 * 保存设备ip地址库
	 * 
	 * @param deviceIp
	 * @return
	 */
	boolean saveDeviceIp(DeviceIp deviceIp);

	/**
	 * 修改设备ip地址库
	 * 
	 * @param deviceIp
	 * @return
	 */
	boolean updateDeviceIp(DeviceIp deviceIp);

	boolean updateDeviceSoftCodeVersion(String softCode, int versionSeq,
			String ystenId);

    boolean updateDeviceCode(String deviceCode, String mac, String ystenId, String description);

	/**
	 * 通过id查询
	 * 
	 * @param id
	 * @return
	 */
	DeviceIp getDeviceIpById(Long id);

	/**
	 * 删除ip地址库信息
	 * 
	 * @param ids
	 * @return
	 */
	boolean deleteDeviceIp(List<Long> ids);

	/**
	 * 返回符合条件的所有设备信息
	 * 
	 * @param map
	 * @return
	 */
	List<Device> findDevicesByState(Map<String, Object> map);

	/**
	 * 返回易视腾编号为空的设备
	 * 
	 * @return
	 */
	List<Device> findDevicesOfBlankYsteId();

	List<Device> getByAreaAndType(int province, int E3, int E4);

	/**
	 * 保存设备信息
	 * 
	 * @param device
	 */
	boolean saveDevice(Device device);

	Pageable<Device> findCustomerCanBindDeviceList(Device device, int pageNo,
			int pageSize);

	List<Device> getDeviceByIds(List<Long> ids);

	List<Device> findDeviceListByYstenIds(String[] ids);

    public List<Device> findDeviceListBySnos(String[] snos);

	List<Device> findDevicesToExport(Map<String, Object> map);

	List<Device> QueryDevicesToExport(Map<String, Object> map);

	List<Device> findEpgDeviceByDeviceGroupIds(List<Long> deviceGroupIdList);

	/**
	 * 根据终端信息获取终端分组
	 * 
	 * @param ystenId
	 * @return
	 */
	List<DeviceGroup> findDeviceGroupByYstenId(String ystenId,
			EnumConstantsInterface.DeviceGroupType type);

	/**
	 * 根据分组编号获取终端分组对象
	 * 
	 * @param deviceGroupId
	 * @return
	 */
	DeviceGroup findDeviceGroupByDeviceGroupId(long deviceGroupId);

	/**
	 * 根据软件编码获取软件对象
	 * 
	 * @param code
	 * @return
	 */
	DeviceSoftwareCode findDeviceSoftwareCodeByCode(String code);

	ApkSoftwareCode findApkSoftwareCodeByCode(String code);

	/**
	 * 
	 * @param deviceGroupId
	 * @param softCodeId
	 * @param deviceVersionSeq
	 * @return
	 */

	/**
	 * 
	 * @param deviceUpgradeResultLog
	 * @return
	 */
	Boolean insertDeviceUpgradeResultLog(
			DeviceUpgradeResultLog deviceUpgradeResultLog);

	Boolean insertApkUpgradeResultLog(ApkUpgradeResultLog apkUpgradeResultLog);

	/**
	 * 根据device code查询设备升级历史记录
	 * 
	 * @param deviceCode
	 * @return
	 */
	List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogListByDeviceCodeAndYstenId(
			String deviceCode, String ystenId);

	/**
	 * 
	 * @param deviceMac
	 * @return
	 */
	DeviceUpgradeMap findDeviceUpgradeMapByDeviceCode(String deviceMac);

	DeviceUpgradeMap findDeviceUpgradeMapByGroupId(long groupId);

	List<AppUpgradeMap> findAppUpgradeMapByDeviceCode(String deviceCode);

	DeviceSoftwarePackage findDeviceSoftwarePackagebyId(long id);

	UpgradeTask findUpgradeTaskById(long id);

	DeviceSoftwareCode findDeviceSoftwareCodeById(long id);

	UpgradeTask findLatestUpgradeTask(long deviceGroupId, String softwareCode,
			long deviceVersionSeq);

	/**
	 * find device group by device code and group type
	 * 
	 * @param deviceCode
	 * @param type
	 * @return
	 */
	List<DeviceGroup> findGroupByDeviceCodeType(String deviceCode,
			EnumConstantsInterface.DeviceGroupType type);

	/**
	 * 根据版本号取的版本记录
	 * 
	 * @param version
	 * @param codeId
	 * @return
	 */
	DeviceSoftwarePackage findDeviceSoftwarePackageByVersionAndSoftCodeId(
			String version, Long codeId);

	/**
	 * 根据区域Id获取所有终端批次号
	 * 
	 * @param areaId
	 * @return
	 */
	List<String> getAllProductNoByArea(String areaId, DistributeState state);

	/**
	 * 根据生产批次获取所有终端信息
	 * 
	 * @param productNo
	 * @return
	 */
	List<Device> getByProductNo(String productNo, Device.DistributeState state);

	/**
	 * 获取所有状态变更的设备信息
	 * 
	 * @return
	 */
	List<Device> getAllDeviceByStateChange(Integer num);

	/**
	 * 获取所有同步中的设备数
	 * 
	 * @return
	 */
	Integer getTotalSyncingDevice();

	/*
	*//**
	 * 绑定终端分组
	 * 
	 * @param type
	 * @param from
	 * @param to
	 * @return
	 */
	/*
	 * boolean bindDeviceGroup(String type, String from, String to);
	 */

	/**
	 * 绑定终端分组关系
	 * 
	 * @param dgm
	 */
	void updateDeviceGroupMap(DeviceGroupMap dgm);

	/**
	 * 批量插入设备与设备组关系--DeviceGroupMap
	 * 
	 * @param list
	 */
	void BulkSaveDeviceGroupMap(List<DeviceGroupMap> list);

	/**
	 * 根据Code和GroupId确认唯一一条终端分组绑定记录
	 * 
	 * @param code
	 * @param groupId
	 * @return
	 */
	DeviceGroupMap getByCodeAndGroup(String code, Long groupId);

	/**
	 * 查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pageable<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCodeAndYstId(
			String deviceCode, String ystenId, Integer pageNo, Integer pageSize);
	
	Pageable<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCondition(Map<String, Object> map);

    Pageable<ApkUpgradeResultLog> findApkUpgradeResultLogByCodeAndYstId(
            String deviceCode, String ystenId, Integer pageNo, Integer pageSize);

	/**
	 * 根据分组id删除终端分组信息
	 * 
	 * @param id
	 */
	void deleteDeviceGroupMapByGroupId(Long id);

	// void deleteDeviceById(Long id);

	void deleteDevice(Device device);

	void deleteDeviceGroupMapByCode(String deviceCode);

	public DeviceSoftwarePackage getLatestSoftwarePackageByDevice(
			String deviceCode, long softwareCodeId, Integer deviceVersionSeq);

	public DeviceSoftwarePackage getLatestSoftwarePackageByUser(String userId,
			long softwareCodeId, Integer deviceVersionSeq);

	public DeviceSoftwarePackage getLatestSoftwarePackageByDeviceGroup(
			long softwareCodeId, Integer deviceVersionSeq, long upgradeId);

	public DeviceSoftwarePackage getLatestSoftwarePackageByUserGroup(
			long softwareCodeId, Integer deviceVersionSeq, long upgradeId);

	public List<DeviceSoftwarePackage> getSoftwarePackageListBySoftCodeIdAndVersionSeq(
			long softwareCodeId, Integer deviceVersionSeq);

	boolean updateSyncById(Long id, String isSync);

	Device getDeviceByCodeSnoAndMac(String device);

	int getCountByIsSync();

	List<DeviceActiveStatistics> getActiveDeviceReport(String activeTime,
			Long province, List<Customer> customerList);

	List<DeviceActiveStatistics> getActiveDeviceChart(String activeTime,
			Long province, List<Customer> customerList);

	List<UserActiveStatistics> getActiveUserReport(String activeDate,
			Long province);

	List<Device> findDuoToDeviceByPreOpening();

	List<Long> getLeadIdsByUserAreaId(String userAreaID);

	UserUpgradeMap findMapByUserGroupId(Long userGroupId);

	List<Device> getAllDeviceByIsSync(Integer num);

	List<Long> findAreaByBusiness(Long Id, String character, String tableName);

	List<Device> findExpirePrepareOpenDevice();

	Device getDeviceByCustomerId(Long customerId);

	Pageable<Device> findDeviceListByConditions(Map<String, Object> map);

	void deleteDeviceByBusiness(List<String> ystenList, Long bussinessId,
			String character, String tableName, String type, String device);

	/**
	 * 查找过期时间在指定日期之间的设备Yesten ID
	 * 
	 * @return
	 */
	public List<Device> findExpiringDevices(Date startDate, Date endDate);
	List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByIds(List<Long> ids);
	List<ApkUpgradeResultLog> findApkUpgradeResultLogByIds(List<Long> ids);
	List<DeviceUpgradeResultLog> findExportDeviceUpgradeResultLog(Map<String, Object> map);
	Pageable<ApkUpgradeResultLog> findApkUpgradeResultLogByCondition(Map<String, Object> map);
	List<ApkUpgradeResultLog> findExportApkUpgradeResultLog(Map<String, Object> map);
}
