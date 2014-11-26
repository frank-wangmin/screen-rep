package com.ysten.local.bss.device.repository.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.area.repository.mapper.AreaMapper;
import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;
import com.ysten.cache.annotation.MethodFlushBootsrap;
import com.ysten.local.bss.device.domain.ApkSoftwareCode;
import com.ysten.local.bss.device.domain.ApkUpgradeResultLog;
import com.ysten.local.bss.device.domain.AppUpgradeMap;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.Device.State;
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
import com.ysten.local.bss.device.redis.IDeviceCustomerAccountMapRedis;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.mapper.ApkSoftwareCodeMapper;
import com.ysten.local.bss.device.repository.mapper.ApkUpgradeResultLogMapper;
import com.ysten.local.bss.device.repository.mapper.AppUpgradeMapMapper;
import com.ysten.local.bss.device.repository.mapper.CityMapper;
import com.ysten.local.bss.device.repository.mapper.CustomerDeviceHistoryMapper;
import com.ysten.local.bss.device.repository.mapper.CustomerMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceIpMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceSoftwareCodeMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceSoftwarePackageMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceTypeMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceUpgradeMapMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceUpgradeResultLogMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceVendorMapper;
import com.ysten.local.bss.device.repository.mapper.SpDefineMapper;
import com.ysten.local.bss.device.repository.mapper.UpgradeTaskMapper;
import com.ysten.local.bss.device.repository.mapper.UserUpgradeMapMapper;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.date.DateUtil;
import com.ysten.utils.page.Pageable;

/**
 * 默认IDeviceRepository
 *
 * @author LI.T
 * @date 2011-4-22
 * @fileName DeviceRepositoryImpl.java
 */
@Repository
public class DeviceRepositoryImpl implements IDeviceRepository {

    private static final String BASE_DOMAIN = "ysten:local:bss:device:";
    private static final String DEVICE_ID = BASE_DOMAIN + "device:id:";
    private static final String DEVICE_CODE = BASE_DOMAIN + "device:code:";
    private static final String DEVICE_SNO = BASE_DOMAIN + "device:sno:";
    //    private static final String DEVICE_MAC = BASE_DOMAIN + "device:mac:";
    private static final String YSTEN_ID = BASE_DOMAIN + "device:ystenId:";
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private DeviceVendorMapper deviceVendorMapper;
    @Autowired
    private DeviceTypeMapper deviceTypeMapper;
    @Autowired
    private DeviceIpMapper deviceIpMapper;
    @Autowired
    private CustomerDeviceHistoryMapper customerDeviceHistoryMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private SpDefineMapper spDefineMapper;
    @Autowired
    IDeviceCustomerAccountMapRedis deviceCustomerAccountMapRedis;
    @Autowired
    private DeviceGroupMapper deviceGroupMapper;
    @Autowired
    private DeviceGroupMapMapper deviceGroupMapMapper;
    @Autowired
    private DeviceSoftwareCodeMapper deviceSoftwareCodeMapper;
    @Autowired
    private UpgradeTaskMapper upgradeTaskMapper;
    @Autowired
    private DeviceSoftwarePackageMapper deviceSoftwarePackageMapper;
    @Autowired
    private DeviceUpgradeResultLogMapper deviceUpgradeResultLogMapper;
    @Autowired
    private ApkUpgradeResultLogMapper apkUpgradeResultLogMapper;
    @Autowired
    private DeviceUpgradeMapMapper deviceUpgradeMapMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private UserUpgradeMapMapper userUpgradeMapMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AppUpgradeMapMapper appUpgradeMapMapper;
    @Autowired
    private ApkSoftwareCodeMapper apkSoftwareCodeMapper;

    @Override
    @MethodCache(key = DEVICE_CODE + "#{code}")
    public Device getDeviceByCode(@KeyParam("code") String deviceCode) {
        return deviceMapper.getDeviceByCode(deviceCode);
    }

    @Override
    @MethodCache(key = DEVICE_ID + "#{deviceId}")
    public Device getDeviceById(@KeyParam("deviceId") long deviceId) {
        return deviceMapper.getDeviceById(deviceId);
    }

    @Override
//    @MethodFlush(keys = {DEVICE_CODE + "#{device.code}", DEVICE_ID + "#{device.id}", DEVICE_MAC + "#{device.mac}",YSTEN_ID+"device.ystenId"
    @MethodFlush(keys = {DEVICE_CODE + "#{device.code}", DEVICE_ID + "#{device.id}", YSTEN_ID + "#{device.ystenId}", DEVICE_SNO + "#{device.sno}"
    })
    public boolean updateDevice(@KeyParam("device") Device device) {
        return (1 == this.deviceMapper.update(device));
    }

    @Override
//    @MethodFlush(keys = {DEVICE_CODE + "#{device.code}", DEVICE_ID + "#{device.id}", DEVICE_MAC + "#{device.mac}",YSTEN_ID+"device.ystenId"
    @MethodFlush(keys = {DEVICE_CODE + "#{device.code}", DEVICE_ID + "#{device.id}", YSTEN_ID + "#{device.ystenId}", DEVICE_SNO + "#{device.sno}"
    })
    public boolean updateDeviceByYstenId(Device device) {
        return (1 == this.deviceMapper.updateByYstenId(device));
    }

    @Override
    public DeviceType getDeviceTypeById(Long id) {
        return this.deviceTypeMapper.getById(id);
    }

    @Override
    public DeviceVendor getDeviceVendorById(Long id) {
        return this.deviceVendorMapper.getById(id);
    }

    @Override
//    @MethodCache(key = DEVICE_MAC + "#{mac}")
    public Device getDeviceByMac(String mac) {
        return this.deviceMapper.getDeviceByMac(mac);
    }

    @Override
    @MethodCache(key = DEVICE_SNO + "#{sno}")
    public Device getDeviceBySno(@KeyParam("sno") String sno) {
        return this.deviceMapper.getDeviceBySno(sno);
    }

    @Override
    public List<DeviceIp> getDeviceIpByIpValue(Long ipValue) {
        return this.deviceIpMapper.getDeviceIpByIpValue(ipValue);
    }

    @Override
    public Device getDeviceBySnoandExpiredate(String sno) {
        return this.deviceMapper.getDeviceBySnoandExpiredate(sno);
    }

    @Override
    public boolean saveCustomerDeviceHistory(CustomerDeviceHistory customerDeviceHistory) {
        customerDeviceHistory.setYstenId(customerDeviceHistory.getDeviceCode());
//        customerDeviceHistory.setOldYstenId(customerDeviceHistory.getOldDeviceCode());
        return 1 == this.customerDeviceHistoryMapper.saveCustomerDeviceHistory(customerDeviceHistory);
    }

    @Override
    public Device getNoAuthDevice() {
        return deviceMapper.getNoAuthDevice();
    }

//    @Override
//    public List<CustomerDeviceHistory> getAllCustomerDeviceHistoryCreatedLastDay() {
//        Map<String, String> params = new HashMap<String, String>();
//        String lastDay = DateUtil.convertDateToString("yyyy-MM-dd", reduceDays(new Date(), 1));
//        params.put("start", lastDay + " 00:00:00");
//        params.put("end", lastDay + " 23:59:59");
//
//        return this.customerDeviceHistoryMapper.getByCreateDate(params);
//    }

    public Date reduceDays(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - offset);// 让日期加位移量
        return calendar.getTime();
    }


    @Override
    public long getAllDeviceCreatedLastDay(String cityId) {
        Map<String, Object> params = new HashMap<String, Object>();
        String lastDay = DateUtil.convertDateToString("yyyy-MM-dd", reduceDays(new Date(), 1));
        params.put("start", lastDay + " 00:00:00");
        params.put("end", lastDay + " 23:59:59");
        params.put("city", cityId);
        params.put("state", "NONACTIVATED");

        return this.deviceMapper.getAllDeviceCreatedLastDay(params);
    }

    @Override
    public Pageable<Device> findDevicesByState(String bindType, String isLock, String ystenId, String code, String mac, String sno, Long deviceVendorId, Long deviceTypeId, Long areaId, State state,
                                               BindType bindState, Long spDefineId, String productNo, Device.DistributeState distributeState, int pageNo, int pageSize) {
        List<Device> datas = this.deviceMapper.findAllByState(bindType, isLock, ystenId, code, mac, sno, deviceVendorId, deviceTypeId, areaId, state, bindState, spDefineId, productNo, distributeState, pageNo, pageSize);
        if (datas != null && datas.size() > 0) {
            for (Device device : datas) {
                if (device.getArea() != null) {
                    device.setArea(this.areaMapper.getById(device.getArea().getId()));
                }
                if (device.getCity() != null) {
                    device.setCity(this.cityMapper.getById(device.getCity().getId()));
                }
                if (device.getDeviceType() != null) {
                    device.setDeviceType(this.deviceTypeMapper.getById(device.getDeviceType().getId()));
                }
                if (device.getDeviceVendor() != null) {
                    device.setDeviceVendor(this.deviceVendorMapper.getById(device.getDeviceVendor().getId()));
                }
                if (device.getSoftCode() != null) {
                    device.setSoftCode(device.getSoftCode());
                }
                if (device.getSpDefine() != null) {
                    device.setSpDefine(this.spDefineMapper.getById(device.getSpDefine().getId()));
                }
                if (device.getCustomerId() != null) {
                    device.setCustomerCode(this.customerMapper.getById(device.getCustomerId()).getCode());
                }
                if (StringUtils.isNotBlank(device.getYstenId())) {
                    List<DeviceGroup> groupList = this.deviceGroupMapper.findDeviceGroupByDeviceCode(device.getYstenId());
                    StringBuffer dsb = new StringBuffer();
                    for (DeviceGroup group : groupList) {
                        if (group != null) {
                            dsb.append(group.getName()).append(",");
                        }
                        if (dsb.length() > 0) {
                            device.setGroups(dsb.substring(0, dsb.length() - 1).toString());
                        }
                    }
                }
            }
        }
        int total = this.deviceMapper.findCountByState(bindType, isLock, code, ystenId, mac, sno, deviceVendorId, deviceTypeId, areaId, state, bindState, spDefineId, productNo, distributeState);
        return new Pageable<Device>().instanceByPageNo(datas, total, pageNo, pageSize);
    }

    @Override
    public Pageable<DeviceVendor> findAllDeviceVendors(String deviceVendorName, String deviceVendorCode, Integer pageNo, Integer pageSize) {
        List<DeviceVendor> deviceVendorList = this.deviceVendorMapper.findAllDeviceVendors(deviceVendorName, deviceVendorCode, pageNo, pageSize);
        int total = this.deviceVendorMapper.getCountByName(deviceVendorName, deviceVendorCode);
        return new Pageable<DeviceVendor>().instanceByPageNo(deviceVendorList, total, pageNo, pageSize);
    }

    @Override
    public List<DeviceVendor> findDeviceVendors(DeviceVendor.State state) {
        return this.deviceVendorMapper.findAllVendor(state);
    }

    @Override
    public List<DeviceType> findDeviceTypes() {
        return this.deviceTypeMapper.findAllType();
    }

    @Override
    public List<DeviceType> findDeviceTypesByVendorId(String vendorId, DeviceType.State state) {
        return this.deviceTypeMapper.findDeviceTypeByVendorId(vendorId, state);
    }

    @Override
    public boolean saveDeviceVendor(DeviceVendor deviceVendor) {
        return 1 == this.deviceVendorMapper.save(deviceVendor);
    }

    @Override
    public boolean updateDeviceVendor(DeviceVendor deviceVendor) {
        return 1 == this.deviceVendorMapper.update(deviceVendor);
    }

    @Override
    public Pageable<DeviceType> findAllDeviceTypes(String deviceTypeName, String deviceTypeCode, String deviceVendorId,
                                                   Integer pageNo, Integer pageSize) {
        List<DeviceType> deviceTypeList = this.deviceTypeMapper.findAllDeviceTypes(deviceTypeName, deviceTypeCode,
                deviceVendorId, pageNo, pageSize);
        int total = this.deviceTypeMapper.getCountByName(deviceTypeName, deviceTypeCode, deviceVendorId);
        return new Pageable<DeviceType>().instanceByPageNo(deviceTypeList, total, pageNo, pageSize);
    }

    @Override
    public boolean saveDeviceType(DeviceType deviceType) {
        return 1 == this.deviceTypeMapper.save(deviceType);
    }

    @Override
    public boolean updateDeviceType(DeviceType deviceType) {
        return 1 == this.deviceTypeMapper.update(deviceType);
    }

    @Override
    public DeviceType getDeviceTypeByNameOrCode(String deviceVendor, String deviceTypeName, String deviceTypeCode) {
        return this.deviceTypeMapper.getDeviceTypeByNameOrCode(deviceVendor, deviceTypeName, deviceTypeCode);
    }

    @Override
    public DeviceVendor getDeviceVendorByNameOrCode(String deviceVendorName, String deviceVendorCode) {
        return this.deviceVendorMapper.getDeviceVendorByNameOrCode(deviceVendorName, deviceVendorCode);
    }

    @Override
    public Pageable<DeviceIp> findDeviceIpByIpSeg(String ipSeq, int pageNo, int pageSize) {
        List<DeviceIp> deviceIpList = this.deviceIpMapper.findAllDeviceIpByIpSeg(ipSeq, pageNo, pageSize);
        int total = this.deviceIpMapper.getCountByIpSeq(ipSeq);
        return new Pageable<DeviceIp>().instanceByPageNo(deviceIpList, total, pageNo, pageSize);
    }

    @Override
    public boolean saveDeviceIp(DeviceIp deviceIp) {
        return 1 == this.deviceIpMapper.save(deviceIp);
    }

    @Override
    public boolean updateDeviceIp(DeviceIp deviceIp) {
        return 1 == this.deviceIpMapper.update(deviceIp);
    }

    @Override
    public boolean updateDeviceSoftCodeVersion(String softCode, int versionSeq, String ystenId) {
        return 1 == this.deviceMapper.updateSoftCodeVersion(softCode, versionSeq, ystenId);
    }

    @Override
    public boolean updateDeviceCode(String deviceCode, String mac, String ystenId, String description) {
        return 1 == this.deviceMapper.updateDeviceCode(deviceCode, mac, ystenId, description);
    }

    @Override
    public DeviceIp getDeviceIpById(Long id) {
        return this.deviceIpMapper.getById(id);
    }

    @Override
    public boolean deleteDeviceIp(List<Long> ids) {
        return ids.size() == this.deviceIpMapper.delete(ids);
    }

    @Override
    public List<Device> findDevicesByState(Map<String, Object> map) {
        return this.deviceMapper.findDevicesByState(map);
    }

    @Override
    public List<Device> getByAreaAndType(int province, int E3, int E4) {

        return this.deviceMapper.getByAreaAndType(province, E3, E4);
    }

    @Override
    public boolean saveDevice(Device device) {
        return 1 == this.deviceMapper.save(device);
    }

    @Override
    public Pageable<Device> findCustomerCanBindDeviceList(Device device, int pageNo, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("index", pageNo);
        params.put("size", pageSize);

        params.put("deviceCode", device.getCode());
        params.put("mac", device.getMac());
        params.put("sno", device.getSno());
        params.put("deviceVendorId", device.getDeviceVendor());
        params.put("deviceType", device.getDeviceType());

        if (device.getArea() != null) {
            params.put("areaId", device.getArea().getId());
        } else {
            params.put("areaId", null);
        }
        params.put("state", device.getState());
        List<Device> devices = this.deviceMapper.findCustomerCanBindDeviceList(params);
        Integer total = this.deviceMapper.getCountCustomerCanBindDeviceList(params);
        return new Pageable<Device>().instanceByPageNo(devices, total, pageNo, pageSize);
    }

    @Override
    public List<Device> getDeviceNotActivated() {
        return this.deviceMapper.getDeviceNotActivated();
    }

    @Override
    public List<Device> getDeviceByIds(List<Long> ids) {
        return this.deviceMapper.getDeviceByIds(ids);
    }

    @Override
    public List<Device> findDeviceListByYstenIds(String[] ids) {
        return this.deviceMapper.findDeviceListByYstenIds(ids);
    }

    @Override
    public List<Device> findDeviceListBySnos(String[] snos) {
        return this.deviceMapper.findDeviceListBySnos(snos);
    }

    @Override
    public List<Device> findDevicesToExport(Map<String, Object> map) {
        return this.deviceMapper.findDevicesToExport(map);
    }

    @Override
    public List<Device> findEpgDeviceByDeviceGroupIds(List<Long> deviceGroupIdList) {

        return deviceMapper.findEpgDeviceByDeviceGroupIds(deviceGroupIdList);
    }

    @Override
    public List<DeviceGroup> findDeviceGroupByYstenId(String ystenId, EnumConstantsInterface.DeviceGroupType type) {
        List<DeviceGroupMap> mapList = this.deviceGroupMapMapper.getByYstenId(ystenId);
        if (CollectionUtils.isEmpty(mapList)) {
            return null;
        }
        return this.deviceGroupMapper.findListByDeviceGroupMapList(mapList, type);
    }

    @Override
    public DeviceGroup findDeviceGroupByDeviceGroupId(long deviceGroupId) {
        return this.deviceGroupMapper.getById(deviceGroupId);
    }

    @Override
    public DeviceSoftwareCode findDeviceSoftwareCodeByCode(String code) {
        return this.deviceSoftwareCodeMapper.selectByCode(code);
    }

    @Override
    public ApkSoftwareCode findApkSoftwareCodeByCode(String code) {
        return this.apkSoftwareCodeMapper.selectByCode(code);
    }

    @Override
    public Boolean insertDeviceUpgradeResultLog(DeviceUpgradeResultLog deviceUpgradeResultLog) {
        return 1 == this.deviceUpgradeResultLogMapper.insertDeviceUpgradeResultLog(deviceUpgradeResultLog);
    }

    @Override
    public Boolean insertApkUpgradeResultLog(ApkUpgradeResultLog apkUpgradeResultLog) {
        return 1 == this.apkUpgradeResultLogMapper.insertApkUpgradeResultLog(apkUpgradeResultLog);
    }

    @Override
    public List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogListByDeviceCodeAndYstenId(String deviceCode, String ystenId) {
        return this.deviceUpgradeResultLogMapper.findListByDeviceCodeAndYstenId(deviceCode, ystenId);
    }

    @Override
    public DeviceSoftwarePackage findDeviceSoftwarePackagebyId(long id) {
        return this.deviceSoftwarePackageMapper.findById(id);
    }

    @Override
    public UpgradeTask findUpgradeTaskById(long id) {
        return this.upgradeTaskMapper.getById(id);
    }

    @Override
    public DeviceSoftwareCode findDeviceSoftwareCodeById(long id) {
        return this.deviceSoftwareCodeMapper.getById(id);
    }

    @Override
    public UpgradeTask findLatestUpgradeTask(long deviceGroupId, String softwareCode, long deviceVersionSeq) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceGroupId", deviceGroupId);
        map.put("softwareCode", softwareCode);
        map.put("deviceVersionSeq", deviceVersionSeq);
        return this.upgradeTaskMapper.findLatestUpgradeTask(map);
    }


    @Override
    public List<DeviceGroup> findGroupByDeviceCodeType(String deviceCode, EnumConstantsInterface.DeviceGroupType type) {
        return deviceGroupMapper.findGroupByDeviceCodeType(deviceCode, type);
    }


    @Override
    public DeviceUpgradeMap findDeviceUpgradeMapByDeviceCode(String deviceMac) {
        return this.deviceUpgradeMapMapper.getByDeviceCode(deviceMac);
    }

    @Override
    public DeviceUpgradeMap findDeviceUpgradeMapByGroupId(long groupId) {
        return this.deviceUpgradeMapMapper.getByGroupId(groupId);
    }

    @Override
    public List<AppUpgradeMap> findAppUpgradeMapByDeviceCode(String deviceCode) {
        return appUpgradeMapMapper.findAppUpgradeMapByYstenId(deviceCode);
    }

    @Override
    public DeviceSoftwarePackage findDeviceSoftwarePackageByVersionAndSoftCodeId(String version, Long codeId) {
        return this.deviceSoftwarePackageMapper.findDeviceSoftwarePackageByVersionAndSoftCodeId(version, codeId);
    }

    @Override
    public List<String> getAllProductNoByArea(String areaId, DistributeState state) {
        return this.deviceMapper.getAllProductNoByArea(areaId, state);
    }

    @Override
    public List<Device> getByProductNo(String productNo, Device.DistributeState state) {
        return this.deviceMapper.getByProductNo(productNo, state);
    }

    @Override
    public List<Device> getAllDeviceByStateChange(Integer num) {
        return this.deviceMapper.getAllDeviceByStateChange(num);
    }

    @Override
    public Integer getTotalSyncingDevice() {
        return this.deviceMapper.getTotalSyncingDevice();
    }

    /*  @Override
      public boolean bindDeviceGroup(String type, String from, String to) {
          return this.deviceMapper.bindDeviceGroup(type, from, to);
      }
  */
    @Override
    public void updateDeviceGroupMap(DeviceGroupMap dgm) {
        this.deviceGroupMapMapper.save(dgm);
    }

    @Override
    public DeviceGroupMap getByCodeAndGroup(String code, Long groupId) {
        return this.deviceGroupMapMapper.getByCodeAndGroup(code, groupId);
    }

    @Override
    public Pageable<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCodeAndYstId(String deviceCode, String ystenId, Integer pageNo, Integer pageSize) {
        List<DeviceUpgradeResultLog> deviceUpgradeResultLog = this.deviceUpgradeResultLogMapper.findDeviceUpgradeResultLogByCodeAndYstId(deviceCode, ystenId, pageNo, pageSize);
        if(deviceUpgradeResultLog != null && deviceUpgradeResultLog.size()>0){
        	for(DeviceUpgradeResultLog log:deviceUpgradeResultLog){
        		if(log.getDistCode() != null && !log.getDistCode().equals("")){
        			log.setDistCode(this.cityMapper.getByDistrictCode(log.getDistCode()).getName());
        		}
        	}
        }
        int total = this.deviceUpgradeResultLogMapper.getCountByCodeAndYstId(deviceCode, ystenId);
        return new Pageable<DeviceUpgradeResultLog>().instanceByPageNo(deviceUpgradeResultLog, total, pageNo, pageSize);
    }

    @Override
    public Pageable<ApkUpgradeResultLog> findApkUpgradeResultLogByCodeAndYstId(String deviceCode, String ystenId, Integer pageNo, Integer pageSize) {
        List<ApkUpgradeResultLog> apkUpgradeResultLog = this.apkUpgradeResultLogMapper.findApkUpgradeResultLogByCodeAndYstId(deviceCode, ystenId, pageNo, pageSize);
        if(apkUpgradeResultLog != null && apkUpgradeResultLog.size()>0){
        	for(ApkUpgradeResultLog log:apkUpgradeResultLog){
        		if(log.getDistCode() != null && !log.getDistCode().equals("")){
        			log.setDistCode(this.cityMapper.getByDistrictCode(log.getDistCode()).getName());
        		}
        	}
        }
        int total = this.apkUpgradeResultLogMapper.getCountByCodeAndYstId(deviceCode, ystenId);
        return new Pageable<ApkUpgradeResultLog>().instanceByPageNo(apkUpgradeResultLog, total, pageNo, pageSize);
    }

    @Override
    public DeviceSoftwarePackage getLatestSoftwarePackageByDevice(
            String ystenId, long softwareCodeId, Integer deviceVersionSeq) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ystenId", ystenId);
        map.put("softwareCodeId", softwareCodeId);
        map.put("deviceVersionSeq", deviceVersionSeq);
        return this.deviceSoftwarePackageMapper.findLatestSoftwarePackageByDevice(map);
    }

    @Override
    public List<DeviceSoftwarePackage> getSoftwarePackageListBySoftCodeIdAndVersionSeq(long softwareCodeId, Integer deviceVersionSeq) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("softwareCodeId", softwareCodeId);
        map.put("deviceVersionSeq", deviceVersionSeq);
        return this.deviceSoftwarePackageMapper.getSoftwarePackageListBySoftCodeIdAndVersionSeq(map);
    }

    @Override
    public DeviceSoftwarePackage getLatestSoftwarePackageByUser(String userId, long softwareCodeId,
                                                                Integer deviceVersionSeq) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("softwareCodeId", softwareCodeId);
        map.put("deviceVersionSeq", deviceVersionSeq);
        return this.deviceSoftwarePackageMapper.findLatestSoftwarePackageByUser(map);
    }

    @Override
    public DeviceSoftwarePackage getLatestSoftwarePackageByDeviceGroup(long softwareCodeId,
                                                                       Integer deviceVersionSeq, long upgradeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("softwareCodeId", softwareCodeId);
        map.put("deviceVersionSeq", deviceVersionSeq);
        map.put("upgradeId", upgradeId);
        return this.deviceSoftwarePackageMapper.findLatestSoftwarePackageByDeviceGroup(map);
    }

    @Override
    public DeviceSoftwarePackage getLatestSoftwarePackageByUserGroup(long softwareCodeId,
                                                                     Integer deviceVersionSeq, long upgradeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("softwareCodeId", softwareCodeId);
        map.put("deviceVersionSeq", deviceVersionSeq);
        map.put("upgradeId", upgradeId);
        return this.deviceSoftwarePackageMapper.findLatestSoftwarePackageByUserGroup(map);
    }

    @Override
    public void deleteDeviceGroupMapByGroupId(Long id) {
        this.deviceGroupMapMapper.deleteDeviceGroupMapByGroupId(id);
    }

    @Override
    public boolean updateSyncById(Long id, String isSync) {
        return 1 == this.deviceMapper.updateSyncById(id, isSync);
    }

    @Override
    public Device getDeviceByCodeSnoAndMac(String _device) {
        return deviceMapper.getDeviceByCodeSnoAndMac(_device);
    }

    @Override
    public int getCountByIsSync() {
        return this.deviceMapper.getCountByIsSync();
    }


    @Override
    public void deleteDeviceGroupMapByCode(String deviceCode) {
        this.deviceGroupMapMapper.deleteDeviceGroupMapByCode(deviceCode);
    }

    @Override
    public List<DeviceActiveStatistics> getActiveDeviceReport(String activeTime, Long province, List<Customer> customerList) {
        return deviceMapper.getActiveDeviceReport(activeTime, province, customerList);
    }

    @Override
    public List<UserActiveStatistics> getActiveUserReport(String activeDate, Long province) {
        return deviceMapper.getActiveUserReport(activeDate, province);
    }

    @Override
    public List<Long> getLeadIdsByUserAreaId(String userAreaID) {
        return cityMapper.getLeadIdsByUserAreaId(userAreaID);
    }

    @Override
    public UserUpgradeMap findMapByUserGroupId(Long userGroupId) {
        return userUpgradeMapMapper.getUserUpgradeMapByUserGroupId(userGroupId);
    }

    @Override
    public List<DeviceActiveStatistics> getActiveDeviceChart(String activeTime, Long province,
                                                             List<Customer> customerList) {
        return deviceMapper.getActiveDeviceChart(activeTime, province, customerList);
    }

    @Override
    @MethodCache(key = YSTEN_ID + "#{ystenId}")
    public Device getDeviceByYstenId(@KeyParam("ystenId") String ystenId) {
        return deviceMapper.getDeviceByYstenId(ystenId);
    }

    public Device getDeviceByYstenIdNR(String ystenId) {
        return deviceMapper.getDeviceByYstenId(ystenId);
    }

    @Override
    public List<Device> findDuoToDeviceByPreOpening() {
        return deviceMapper.findDuoToDeviceByPreOpening();
    }

 /*   @Override
    @MethodCache(key = DEVICE_ID + "#{deviceId}")
    public void deleteDeviceById(Long deviceId) {
        this.deviceMapper.deleteDevice(deviceId);
    }*/

    @Override
    @MethodFlush(keys = {DEVICE_CODE + "#{device.code}", DEVICE_ID + "#{device.id}", YSTEN_ID + "#{device.ystenId}"})
    public void deleteDevice(Device device) {
        this.deviceMapper.deleteDevice(device.getId());
    }


    @Override
    public Pageable<Device> findDevicesBySnos(String snos, int pageNo,
                                              int pageSize) {
        List<Device> datas = this.deviceMapper.findDevicesBySnos(snos, pageNo, pageSize);
        if (datas != null && datas.size() > 0) {
            for (Device device : datas) {
                if (device.getArea() != null) {
                    device.setArea(this.areaMapper.getById(device.getArea().getId()));
                }
                if (device.getCity() != null) {
                    device.setCity(this.cityMapper.getById(device.getCity().getId()));
                }
                if (device.getDeviceType() != null) {
                    device.setDeviceType(this.deviceTypeMapper.getById(device.getDeviceType().getId()));
                }
                if (device.getDeviceVendor() != null) {
                    device.setDeviceVendor(this.deviceVendorMapper.getById(device.getDeviceVendor().getId()));
                }
                if (device.getSoftCode() != null) {
                    device.setSoftCode(device.getSoftCode());
                }
                if (device.getSpDefine() != null) {
                    device.setSpDefine(this.spDefineMapper.getById(device.getSpDefine().getId()));
                }
                if (StringUtils.isNotBlank(device.getYstenId())) {
                    List<DeviceGroup> groupList = this.deviceGroupMapper.findDeviceGroupByDeviceCode(device.getYstenId());
                    StringBuffer dsb = new StringBuffer();
                    for (DeviceGroup group : groupList) {
                        if (group != null) {
                            dsb.append(group.getName()).append(",");
                        }
                        if (dsb.length() > 0) {
                            device.setGroups(dsb.substring(0, dsb.length() - 1).toString());
                        }
                    }
                }
            }
        }
        int total = this.deviceMapper.findCountDevicesBySnos(snos);
        return new Pageable<Device>().instanceByPageNo(datas, total, pageNo, pageSize);
    }

    @Override
    public Pageable<Device> findDevicesByGroupId(String tableName, String character, Long deviceGroupId, String ystenId, String code, String mac, String sno,  String softCode, String versionSeq,int pageNo, int pageSize) {

        List<Device> deviceList = new ArrayList<Device>();
        if (Constant.BSS_DEVICE_BACKGROUND_IMAGE_MAP.equals(tableName)) {
            deviceList = this.deviceMapper.findDevicesByBackImageId(tableName, character, deviceGroupId, ystenId, code, mac, sno, pageNo, pageSize);
        } else {
            deviceList = this.deviceMapper.findDevicesByGroupId(tableName, character, deviceGroupId, ystenId, code, mac, sno, softCode, versionSeq, pageNo, pageSize);
        }
        if (deviceList != null && deviceList.size() > 0) {
            for (Device device : deviceList) {
                if (device.getArea() != null) {
                    device.setArea(this.areaMapper.getById(device.getArea().getId()));
                }
                if (device.getCity() != null) {
                    device.setCity(this.cityMapper.getById(device.getCity().getId()));
                }
                // 这些数据没有在页面上显示，先注释掉
                /* if (device.getDeviceType() != null) {
                    device.setDeviceType(this.deviceTypeMapper.getById(device.getDeviceType().getId()));
                }
                if (device.getDeviceVendor() != null) {
                    device.setDeviceVendor(this.deviceVendorMapper.getById(device.getDeviceVendor().getId()));
                } */
                if (device.getSoftCode() != null) {
                    device.setSoftCode(device.getSoftCode());
                }
                // 这些数据没有在页面上显示，先注释掉
                /* if (device.getSpDefine() != null) {
                    device.setSpDefine(this.spDefineMapper.getById(device.getSpDefine().getId()));
                }
                if (StringUtils.isNotBlank(device.getYstenId())) {
                    List<DeviceGroup> groupList = this.deviceGroupMapper.findDeviceGroupByDeviceCode(device.getYstenId());
                    StringBuffer dsb = new StringBuffer();
                    for (DeviceGroup group : groupList) {
                        if (group != null) {
                            dsb.append(group.getName()).append(",");
                        }
                        if (dsb.length() > 0) {
                            device.setGroups(dsb.substring(0, dsb.length() - 1).toString());
                        }
                    }
                }*/
            }
        }
        int total = this.deviceMapper.findCountDevicesByGroupId(tableName, character, deviceGroupId, ystenId, code, mac, sno, softCode, versionSeq);
        return new Pageable<Device>().instanceByPageNo(deviceList, total, pageNo, pageSize);
    }


    /**
     * @author chenxiang
     * @date 2014-8-15 下午5:08:58
     */
    @Override
    public List<Device> getAllDeviceByIsSync(Integer num) {
        return this.deviceMapper.getAllDeviceByIsSync(num);
    }

    @Override
    public List<Device> findDevicesOfBlankYsteId() {
        return this.deviceMapper.findDevicesOfBlankYsteId();
    }

    @Override
    public List<Long> findAreaByBusiness(Long Id, String character,
                                         String tableName) {
        return this.deviceMapper.findAreaByBusiness(Id, character, tableName);
    }


    @Override
    public List<Device> findDevicesBySnos(String snos) {
        return this.deviceMapper.findDevicesBySnos(snos, 0, 0);
    }


    @Override
    public List<Device> findExpirePrepareOpenDevice() {
        return this.deviceMapper.findExpirePrepareOpenDevice();
    }

    @Override
    public List<Device> findDeviceYstenIdListByDeviceCodes(String codes) {
        return this.deviceMapper.findDeviceYstenIdListByDeviceCodes(codes);
    }

    @Override
    public void BulkSaveDeviceGroupMap(List<DeviceGroupMap> list) {
        this.deviceGroupMapMapper.BulkSaveDeviceGroupMap(list);
    }

    @Override
    public DeviceType getDeviceTypeByTypeId(Long id) {
        return this.deviceTypeMapper.getDeviceTypeById(id);
    }

    @Override
    public Device getDeviceByCustomerId(Long customerId) {
        return this.deviceMapper.getDeviceByCustomerId(customerId);
    }

    @Override
    @MethodFlushBootsrap
    public void deleteDeviceByBusiness(List<String>ystenList,Long bussinessId, String character,String tableName,String type,String device){
        deviceMapper.deleteDeviceByBusiness(ystenList,bussinessId,character,tableName,type,device);
    }

	@Override
	public Pageable<Device> findDeviceListByConditions(Map<String, Object> map) {
		List<Device> deviceList = this.deviceMapper.findDeviceListByConditions(map);
		if (deviceList != null && deviceList.size() > 0) {
            for (Device device : deviceList) {
                if (device.getArea() != null) {
                    device.setArea(this.areaMapper.getById(device.getArea().getId()));
                }
                if (device.getCity() != null) {
                    device.setCity(this.cityMapper.getById(device.getCity().getId()));
                }
                if (device.getDeviceType() != null) {
                    device.setDeviceType(this.deviceTypeMapper.getById(device.getDeviceType().getId()));
                }
                if (device.getDeviceVendor() != null) {
                    device.setDeviceVendor(this.deviceVendorMapper.getById(device.getDeviceVendor().getId()));
                }
                if (device.getSoftCode() != null) {
                    device.setSoftCode(device.getSoftCode());
                }
                if (device.getSpDefine() != null) {
                    device.setSpDefine(this.spDefineMapper.getById(device.getSpDefine().getId()));
                }
                if (device.getCustomerId() != null) {
                    device.setCustomerCode(this.customerMapper.getById(device.getCustomerId()).getCode());
                }
                if (StringUtils.isNotBlank(device.getYstenId())) {
                    List<DeviceGroup> groupList = this.deviceGroupMapper.findDeviceGroupByDeviceCode(device.getYstenId());
                    StringBuffer dsb = new StringBuffer();
                    for (DeviceGroup group : groupList) {
                        if (group != null) {
                            dsb.append(group.getName()).append(",");
                        }
                        if (dsb.length() > 0) {
                            device.setGroups(dsb.substring(0, dsb.length() - 1).toString());
                        }
                    }
                }
            }
        }
		int total = this.deviceMapper.getCountByConditions(map);
		return new Pageable<Device>().instanceByPageNo(deviceList, total, Integer.parseInt(map.get("pageNo").toString()), Integer.parseInt(map.get("pageSize").toString()));
	}

	@Override
	public List<Device> QueryDevicesToExport(Map<String, Object> map) {
		return this.deviceMapper.QueryDevicesToExport(map);
	}

	@Override
	public List<Device> ExportDevicesOfGroupId(String tableName,
			String character, Long deviceGroupId, String ystenId, String code,
			String mac, String sno, String softCode, String versionSeq) {
		return this.deviceMapper.ExportDevicesOfGroupId(tableName, character, deviceGroupId, ystenId, code, mac, sno, softCode, versionSeq);
	}
	
	@Override
	public List<Device> findExpiringDevices(Date startDate, Date endDate) {
		return this.deviceMapper.findExpiringDevices(startDate, endDate);
	}

	@Override
	public Pageable<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCondition(
			Map<String, Object> map) {
		List<DeviceUpgradeResultLog> deviceUpgradeResultLog = this.deviceUpgradeResultLogMapper.findDeviceUpgradeResultLogByCondition(map);
		if(deviceUpgradeResultLog != null && deviceUpgradeResultLog.size()>0){
        	for(DeviceUpgradeResultLog log:deviceUpgradeResultLog){
        		if(log.getDistCode() != null && !log.getDistCode().equals("")){
        			log.setDistCode(this.cityMapper.getByDistrictCode(log.getDistCode()).getName());
        		}
        	}
        }
		int total = this.deviceUpgradeResultLogMapper.getCountByCondition(map);
		return new Pageable<DeviceUpgradeResultLog>().instanceByPageNo(deviceUpgradeResultLog, total, Integer.parseInt(map.get("pageNo").toString()), Integer.parseInt(map.get("pageSize").toString()));
	}

	@Override
	public List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByIds(
			List<Long> ids) {
		List<DeviceUpgradeResultLog> deviceUpgradeResultLog = this.deviceUpgradeResultLogMapper.findDeviceUpgradeResultLogByIds(ids);
		if(deviceUpgradeResultLog != null && deviceUpgradeResultLog.size()>0){
        	for(DeviceUpgradeResultLog log:deviceUpgradeResultLog){
        		if(log.getDistCode() != null && !log.getDistCode().equals("")){
        			log.setDistCode(this.cityMapper.getByDistrictCode(log.getDistCode()).getName());
        		}
        		if(log.getStatus() != null && !log.getStatus().equals("")){
        			String status = "";
        			if(log.getStatus().getDisplayName().equals("0000")){
        				status = Constant.LOG_SUCCESS;
        			}
        			if(log.getStatus().getDisplayName().equals("9999")){
        				status = Constant.LOG_FAILED;
        			}
        			if(log.getStatus().getDisplayName().equals("1111")){
        				status = Constant.LOG_UNDFEFINED;
        			}
        			log.setResultStatus(status);
        		}
        	}
        }
		return deviceUpgradeResultLog;
	}

	@Override
	public List<DeviceUpgradeResultLog> findExportDeviceUpgradeResultLog(
			Map<String, Object> map) {
		List<DeviceUpgradeResultLog> deviceUpgradeResultLog = this.deviceUpgradeResultLogMapper.findExportDeviceUpgradeResultLog(map);
		if(deviceUpgradeResultLog != null && deviceUpgradeResultLog.size()>0){
        	for(DeviceUpgradeResultLog log:deviceUpgradeResultLog){
        		if(log.getDistCode() != null && !log.getDistCode().equals("")){
        			log.setDistCode(this.cityMapper.getByDistrictCode(log.getDistCode()).getName());
        		}
        		if(log.getStatus() != null && !log.getStatus().equals("")){
        			String status = "";
        			if(log.getStatus().getDisplayName().equals("0000")){
        				status = Constant.LOG_SUCCESS;
        			}
        			if(log.getStatus().getDisplayName().equals("9999")){
        				status = Constant.LOG_FAILED;
        			}
        			if(log.getStatus().getDisplayName().equals("1111")){
        				status = Constant.LOG_UNDFEFINED;
        			}
        			log.setResultStatus(status);
        		}
        	}
        }
		return deviceUpgradeResultLog;
	}

	@Override
	public Pageable<ApkUpgradeResultLog> findApkUpgradeResultLogByCondition(
			Map<String, Object> map) {
		List<ApkUpgradeResultLog> apkUpgradeResultLog = this.apkUpgradeResultLogMapper.findApkUpgradeResultLogByCondition(map);
		if(apkUpgradeResultLog != null && apkUpgradeResultLog.size()>0){
        	for(ApkUpgradeResultLog log:apkUpgradeResultLog){
        		if(log.getDistCode() != null && !log.getDistCode().equals("")){
        			log.setDistCode(this.cityMapper.getByDistrictCode(log.getDistCode()).getName());
        		}
        	}
        }
        int total = this.apkUpgradeResultLogMapper.getCountByCondition(map);
        return new Pageable<ApkUpgradeResultLog>().instanceByPageNo(apkUpgradeResultLog, total, Integer.parseInt(map.get("pageNo").toString()), Integer.parseInt(map.get("pageSize").toString()));
	}

	@Override
	public List<ApkUpgradeResultLog> findApkUpgradeResultLogByIds(List<Long> ids) {
		List<ApkUpgradeResultLog> apkUpgradeResultLog = this.apkUpgradeResultLogMapper.findApkUpgradeResultLogByIds(ids);
		if(apkUpgradeResultLog != null && apkUpgradeResultLog.size()>0){
        	for(ApkUpgradeResultLog log:apkUpgradeResultLog){
        		if(log.getDistCode() != null && !log.getDistCode().equals("")){
        			log.setDistCode(this.cityMapper.getByDistrictCode(log.getDistCode()).getName());
        		}
        		if(log.getStatus() != null && !log.getStatus().equals("")){
        			String status = "";
        			if(log.getStatus().getDisplayName().equals("0000")){
        				status = Constant.LOG_SUCCESS;
        			}
        			if(log.getStatus().getDisplayName().equals("9999")){
        				status = Constant.LOG_FAILED;
        			}
        			if(log.getStatus().getDisplayName().equals("1111")){
        				status = Constant.LOG_UNDFEFINED;
        			}
        			log.setResultStatus(status);
        		}
        	}
        }
		return apkUpgradeResultLog;
	}

	@Override
	public List<ApkUpgradeResultLog> findExportApkUpgradeResultLog(
			Map<String, Object> map) {
		List<ApkUpgradeResultLog> apkUpgradeResultLog = this.apkUpgradeResultLogMapper.findExportApkUpgradeResultLog(map);
		if(apkUpgradeResultLog != null && apkUpgradeResultLog.size()>0){
        	for(ApkUpgradeResultLog log:apkUpgradeResultLog){
        		if(log.getDistCode() != null && !log.getDistCode().equals("")){
        			log.setDistCode(this.cityMapper.getByDistrictCode(log.getDistCode()).getName());
        		}
        		if(log.getStatus() != null && !log.getStatus().equals("")){
        			String status = "";
        			if(log.getStatus().getDisplayName().equals("0000")){
        				status = Constant.LOG_SUCCESS;
        			}
        			if(log.getStatus().getDisplayName().equals("9999")){
        				status = Constant.LOG_FAILED;
        			}
        			if(log.getStatus().getDisplayName().equals("1111")){
        				status = Constant.LOG_UNDFEFINED;
        			}
        			log.setResultStatus(status);
        		}
        	}
        }
		return apkUpgradeResultLog;
	}
}
