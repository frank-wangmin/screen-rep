package com.ysten.local.bss.device.service;

import java.text.ParseException;
import java.util.List;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;

import com.ysten.local.bss.device.bean.DeviceInfoBean;
import com.ysten.local.bss.device.domain.ApkUpgradeResultLog;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.domain.DeviceUpgradeMap;
import com.ysten.local.bss.device.domain.DeviceUpgradeResultLog;
import com.ysten.local.bss.device.domain.UpgradeTask;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.page.Pageable;


/**
 * DeviceService
 * 
 * @fileName IDeviceService.java
 */
public interface IDeviceService {

    JsonResultBean deviceBindCustomer(JSONObject json)  throws ParseException ;
    
    /**
     * 绑定终端与用户（集团）
     * @param json
     * @return
     * @throws ParseException
     */
    JsonResultBean bindMultipleDevice(JSONObject json) throws ParseException;

    JsonResultBean deviceReBindCustomer(JSONObject json);
	/**
	 * 终端设备到期时间修改
	 */
	String[] deviceUpdateServiceStop(String phone,String sno,String servicestop) throws ParseException;
	/**
	 * 获取设备信息
	 * @param sno
	 * @return
	 */
	Device getDeviceBySno(String sno);	
	/**
	 * 获取设备信息
	 * @param deviceId
	 * @return
	 */
	Device getDeviceByDeviceId(long deviceId);	
	
    Device getDeviceByDeviceMac(String mac);
	
	/**
	 * 获取设备信息
	 * @param deviceCode
	 * @return
	 */
	Device getDeviceByDeviceCode(String deviceCode);	
	
	Device getDeviceByYstenId(String ystenId);
	
	/**
	 * 通过sno验证设备是否合法
	 * @param sno
	 * @return
	 */
	JsonResultBean verifyDeviceBySno(String sno);
	/**
	 * 宽带速率变更
	 * @param json
	 * @return
	 */
	String[] updateDeviceRate(JSONObject json);
	
	/**
	 * 设备状态变更
	 * @param
	 * @return
	 */
	boolean updateDevice(Device device);
	/**
	 *  判断合约是否到期（当前时间<=expire_date）,根据计算后ip获取ip是否存在库中
	 * @param
	 * @return
	 */
	String verifyDevice(String deviceId,String ip);

    /**
     * 机顶盒状态信息同步(上海)
     * @param bean
     */
    boolean syncWebtvStbForSh(DeviceInfoBean bean) throws ParseException;
    /**
     * 机顶盒换机(上海)
     * @param bean
     */
    boolean replaceWebtvStbForSh(DeviceInfoBean bean) throws ParseException;
    /**
     * 反向控制设备状态接口(上海)
     * @param deviceId
     * @return
     */
    boolean syncLoginIdStatusForSh(String deviceId) throws DeviceNotFoundException,DocumentException;
    /**
     * 终端停用销户接口
     * @param sno
     * @return
     */
    String[] disableDeviceBySno(String sno);
    
    /**
     * 终端启用暂停接口
     * @param sno
     * @return
     */
    boolean pauseOrRecoverDevice(String sno,String type);
    
    List<Device> getByDateType(String getByDateType);
    
    List<Device> getDeviceNotActivated();
    
    /**
     * 申请备案许可ID
     * @return
     */
    Device getLoginId(String sno, String mac);
    
    /**
     * 查询出设备所对应的地市
     * @param device
     * @return
     */
    City getCityFromDevice(Device device);
    
    /**
     * 查询对应城市前一天到货的设备
     * @return
     */
    Long getAllDeviceCreatedLastDay(String cityId);

    /**
     * 保存设备
     * @param device
     * @return
     */
    boolean saveDevice(Device device);

    /**
     * 省级接收终端接口
     * @param device
     * @return
     */
    boolean receiveDevice(Device device);

    boolean batchReceiveDevice(List<Device> deviceList);
    /**
	 * 江西终端本地认证
	 * @param deviceCode
	 * @param ip
	 * @return
	 */
	String verifyJxDevice(String deviceCode, String ip) throws Exception;
	
	/**
	 * 
	 * @param deviceCode
	 * @return
	 */
	List<DeviceGroup> findDeviceGroupByDeviceCode(String deviceCode);

	/**
	 * 
	 * @param deviceGroupId
	 * @return
	 */
	DeviceGroup findDeviceGroupByDeviceGroupId(long deviceGroupId);
	
	/**
	 * 根据软件编码获取设备软件
	 * @param code
	 * @return
	 */
	DeviceSoftwareCode findDeviceSoftwareCodeByCode(String code);

	Boolean insertDeviceUpgradeResultLog(DeviceUpgradeResultLog deviceUpgradeResultLog);

    Boolean insertApkUpgradeResultLog(ApkUpgradeResultLog apkUpgradeResultLog);

    /**
     * 根据device code查询设备升级历史记录
     *
     * @param deviceCode
     * @return
     */
    List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogListByDeviceCodeAndYstenId(String deviceCode, String ystenId);
	
	DeviceUpgradeMap findDeviceUpgradeMapByDeviceCode(String deviceCode);
	
	DeviceUpgradeMap findDeviceUpgradeMapByGroupId(long groupId);

	DeviceSoftwarePackage findDeviceSoftwarePackagebyId(long id);
	
	UpgradeTask findUpgradeTaskById(long id);
	
	DeviceSoftwareCode findDeviceSoftwareCodeById(long id);
	
	UpgradeTask findLatestUpgradeTask(long deviceGroupId, String softwareCode, long deviceVersionSeq);

    List<DeviceGroup> findGroupByDeviceCodeType(String deviceCode,EnumConstantsInterface.DeviceGroupType type);
    
    List<DeviceGroup> findGroupByYstenIdType(String ystenId,EnumConstantsInterface.DeviceGroupType type);

    /**
     * find device groups which are not bound with service collect
     * @return
     */
    Pageable<DeviceGroup> finDeviceGroupNotBoundServiceCollect(String name, Integer pageNo,Integer pageSize);
    
    /**
     * find boot device groups which are not bound with service collect
     * @return list
     */
    List<DeviceGroup> finBootDeviceGroupNotBoundServiceCollect();
    
    JsonResultBean receiveDevice(String json)throws Exception;
    
    boolean batchUpdateDevice(List<Device> deviceList);
    
    DeviceSoftwarePackage findLatestSoftwarePackageByDevice(String deviceCode, long softwareCodeId,Integer deviceVersionSeq);
    
    DeviceSoftwarePackage findLatestSoftwarePackageByUser(String userId, long softwareCodeId,Integer deviceVersionSeq);
    
    DeviceSoftwarePackage findLatestSoftwarePackageByDeviceGroup(String deviceCode, long softwareCodeId,Integer deviceVersionSeq);
    
    DeviceSoftwarePackage findLatestSoftwarePackageByUserGroup(String userId, long softwareCodeId,Integer deviceVersionSeq);

    List<DeviceSoftwarePackage> getSoftwarePackageListBySoftCodeIdAndVersionSeq(long softwareCodeId, Integer deviceVersionSeq);

	/**
	 * 浙江终端本地认证
	 * @param deviceCode
	 * @param ip
	 * @return
	 */
	String verifyZjDevice(String deviceCode, String ip);

	/**
	 * 江西终端绑定
	 * @param json
	 * @return
	 */
	JsonResultBean deviceJxBindCustomer(JSONObject json)  throws ParseException;

	/**
	 * 浙江移动盒子绑定7天免费体验期
	 * @param deviceId
	 */
	JsonResultBean bindFreePeriod(String deviceId) throws Exception;
	
	/**
	 * 查找预开通状态的设备，并解注册到期的预开通设备和用户关系
	 * @return
	 */
	List<Device> terminationDuoToDeviceByPreOpening();
	
	public City getCityByCode(String code);
	
	public City getCityById(Long id);

    Device getDeviceByMac(String mac);
    
    List<Device> findExpirePrepareOpenDevice();
    
    void offlineExpirePrepareOpenDevice(List<Device> devices);
    
}
