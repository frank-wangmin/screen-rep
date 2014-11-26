package com.ysten.local.bss.device.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ysten.area.domain.Area;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.device.bean.DeviceInfoBean;
import com.ysten.local.bss.device.bean.EShAAAStatus.EPrepareOpen;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.domain.ApkUpgradeResultLog;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Customer.CustomerType;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.Device.State;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.DeviceGroupMap;
import com.ysten.local.bss.device.domain.DeviceIp;
import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.domain.DeviceUpgradeMap;
import com.ysten.local.bss.device.domain.DeviceUpgradeResultLog;
import com.ysten.local.bss.device.domain.LockType;
import com.ysten.local.bss.device.domain.SpDefine;
import com.ysten.local.bss.device.domain.UpgradeTask;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.domain.UserUpgradeMap;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.device.repository.IConfigRepository;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.device.repository.IDeviceGroupMapRepository;
import com.ysten.local.bss.device.repository.IDeviceGroupRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.IUserGroupMapRepository;
import com.ysten.local.bss.device.repository.IUserGroupRepository;
import com.ysten.local.bss.device.repository.mapper.CityMapper;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.device.service.IUserGroupService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.XmlRootUtils;
import com.ysten.local.bss.device.utils.XmlUtils;
import com.ysten.local.bss.util.JsonUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.code.MD5;
import com.ysten.utils.data.DataUtils;
import com.ysten.utils.date.DateUtil;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.page.Pageable;

/**
 * 类名称：DeviceServiceImp 类描述： 设备管理
 */
@Service
public class DeviceServiceImpl implements IDeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);
    private String CHONGQING_YD_GROUP = "1";
    private String ONE = "1";
    private String TWO = "2";
    private String THREE = "3";
    private static final String SP_NAME = "江西移动";
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private IConfigRepository configRepository;
    @Autowired
    private IDeviceGroupRepository deviceGroupRepository;
    @Autowired
    private IUserGroupMapRepository userGroupMapRepository;

    @Autowired
    private IDeviceGroupMapRepository deviceGroupMapRepository;
    @Autowired
    IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
    @Autowired
    private IUserGroupService userGroupService;
    @Autowired
    private ICityRepository cityRepository;
    @Autowired
    private IUserGroupRepository userGroupRepository;
    @Autowired
    private CityMapper cityMapper;
    
    private static final String WEBTVSTBSYNC_REGISTER = "1";//注册
    private static final String WEBTVSTBSYNC_REMOVE_REGISTER = "2";//解注册
    private static final String WEBTVSTBSYNC_REMOVE = "3";//清除
    private static final String WEBTVSTBSYNC_ADD = "4";//加装


    // @Autowired
    // private IDeviceRedis deviceRedis;
    // @Autowired
    // private IDeviceCustomerAccountMapRedis deviceCustomerAccountMapRedis;
    // @Autowired
    // private ICustomerRedis customerRedis;

    @Override
    public Device getDeviceBySno(String sno) {
        return this.deviceRepository.getDeviceBySno(sno);
    }

    @Override
    public JsonResultBean verifyDeviceBySno(String sno) {
        boolean bool = false;
        String message = "";
        Device device = this.deviceRepository.getDeviceBySno(sno);
        if (device != null) {
            Customer customer = this.customerRepository.getCustomerByDeviceId(device.getId());
            if (customer == null) {
                bool = true;
                message = "verify device success! device sno:" + sno;
            } else {
                message = "verify device failed," + customer.getPhone() + " is use this sno " + DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", customer.getCreateDate());
            }
        } else {
            message = "verify device failed, device sno does not exists!";
        }
        return new JsonResultBean(bool, message);
    }

    @Override
    public String[] updateDeviceRate(JSONObject json) {
        String message = "";
        boolean flag = false;
        Device device = this.deviceRepository.getDeviceBySno(json.getString("sno"));
        if (device != null) {
            Customer customer = this.customerRepository.getCustomerByDeviceId(device.getId());
            if (customer != null) {
                customer.setRate(json.getString("rate"));
                this.customerRepository.updateCustomer(customer);
                flag = true;
                message = "update rate success!";
            } else {
                message = "update rate failed, because device bind customer is not exists.";
            }
        } else {
            message = "update rate failed, because deviceOfSno is not exists.";
        }
        return new String[]{flag + "", message};
    }

    @Override
    public String verifyDevice(String deviceCode, String ip) {
        String result = ONE;
        Device device = this.deviceRepository.getDeviceByCode(deviceCode);
        if (device != null && LockType.UNLOCKED.equals(device.getIsLock())) {
            String isCheckDevice = this.systemConfigService.getSystemConfigByConfigKey("isCheckDevice");
            if (StringUtils.isNotBlank(isCheckDevice) && "true".equals(isCheckDevice)) {
                result = this.getAuthDeviceResult(device);
            }
            if (ONE.equals(result)) {
                String isCheckIp = this.systemConfigService.getSystemConfigByConfigKey("isCheckIp");
                if (StringUtils.isNotBlank(isCheckIp) && "true".equals(isCheckIp)) {
                    result = this.getAuthIpResult(ip);
                }
            }
        }
        return result;
    }

    private String getAuthDeviceResult(Device device) {
        if (device != null) {
            List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
            if (mapList != null && mapList.size() > 0) {
                if (mapList == null || device.getState().name().isEmpty() || !Device.State.ACTIVATED.equals(device.getState()) || new Date().after(device.getExpireDate())) {
                    return TWO;
                }
                Customer customer = this.customerRepository.getCustomerByUserId(mapList.get(0).getUserId());
                if (customer == null || customer.getState().name().isEmpty() || !Customer.State.NORMAL.equals(customer.getState())) {
                    return TWO;
                }
            } else {
                return TWO;
            }
        }
        return ONE;
    }

    private String getAuthIpResult(String ip) {
        if (StringUtils.isNotBlank(ip)) {
            String[] str = ip.split("\\.");
            long ipValue = 0L;
            try {
                ipValue = Integer.parseInt(str[0]);
                for (int i = 1; i < 4; i++) {
                    ipValue = ipValue * 256 + (Integer.parseInt(str[i]));
                }
            } catch (java.lang.NumberFormatException nfe) {
                return THREE;
            } catch (java.lang.ArrayIndexOutOfBoundsException aioobe) {
                return THREE;
            }
            List<DeviceIp> list = this.deviceRepository.getDeviceIpByIpValue(ipValue);
            if (list == null || list.size() <= 0) {
                return THREE;
            } else {
                return ONE;
            }
        } else {
            return THREE;
        }
    }

    private Customer createCustomer(Customer customer, JSONObject json, String deviceCode) throws ParseException {
        customer.setRealName(json.getString("userName"));
        customer.setLoginName(customer.getRealName());
        customer.setNickName(customer.getRealName());
        customer.setPhone(json.getString("phone"));
        if (StringUtils.isNotBlank(json.getString("region"))) {
            City city = this.cityRepository.getCityByCityCode(json.getString("region"));
            customer.setRegion(city);
        }
        customer.setCounty(json.getString("county"));
        customer.setAddress(json.getString("address"));
        customer.setRate(json.getString("rate"));
        customer.setCreateDate(new Date());
        customer.setActivateDate(new Date());
        if (StringUtils.isNotBlank(json.getString("endDate"))) {
            customer.setServiceStop(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", json.getString("endDate")));
        }
        customer.setPassword(MD5.encrypt("666666"));
        customer.setCode(NumberGenerator.getNextCode());
        customer.setDeviceCode(deviceCode);
        String areaId = systemConfigService.getSystemConfigByConfigKey("areaId");
        customer.setSource(json.getString("systemCode"));
        Area area = new Area();
        area.setId(Long.parseLong(areaId));
        customer.setArea(area);
        // 重庆移动-集团
        if (json.containsKey("isGroup")) {
            String isGroup = json.getString("isGroup");
            if (StringUtils.isNotBlank(isGroup) && CHONGQING_YD_GROUP.equals(isGroup)) {
                customer.setCustomerType(CustomerType.GROUP);
            }
        }
        return customer;
    }

    private DeviceCustomerAccountMap createDeviceCustomerAccountMap(DeviceCustomerAccountMap map, Customer customer, Device device) {
        map.setCustomerId(customer.getId());
        map.setCustomerCode(customer.getCode());
        map.setDeviceCode(device.getCode());
        map.setDeviceId(Long.parseLong(device.getId() + ""));
        map.setCreateDate(new Date());
        map.setDeviceSno(device.getSno());
        map.setUserId(customer.getUserId());
        return map;
    }

    private CustomerDeviceHistory createCustomerDeviceHistory(CustomerDeviceHistory history, Device newDevice, Device oldDevice, DeviceCustomerAccountMap map) {
        history.setCreateDate(new Date());
        if (newDevice != null) {
            history.setDeviceCode(newDevice.getCode());
            history.setDeviceId(newDevice.getId());
        }
        if (map != null) {
            history.setCustomerCode(map.getCustomerCode());
//            history.setCustomerId(map.getCustomerId());
        }
        if (oldDevice != null) {
//            history.setOldDeviceCode(oldDevice.getCode());
//            history.setOldDeviceId(oldDevice.getId());
        }
        return history;
    }

    /**
     * 把设备和用户绑定
     *
     * @throws ParseException
     */
    @Override
    public JsonResultBean deviceBindCustomer(JSONObject json) throws ParseException {
        String sno = json.getString("sno");
        Device device = this.deviceRepository.getDeviceBySno(sno);
        if (device == null) {
            return new JsonResultBean(false, "binding failed,device sno does not exists,sno:" + sno);
        }
        List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
        if ((mapList == null || mapList.size() == 0)) {
            Customer dbCustomer = this.customerRepository.getCustomerByPhone(json.getString("phone"));
            Customer gCustomer = new Customer();
            String sDate = json.getString("startDate");
            String eDate = json.getString("endDate");
            if (StringUtils.isNotBlank(sDate) && sDate.length() == 8) {
                sDate = sDate + "000000";
            }
            if (StringUtils.isNotBlank(eDate) && eDate.length() == 8) {
                eDate = eDate + "235959";
            }
            String mask = "yyyyMMddHHmmss";
            if(sDate.contains("-")){
                mask = "yyyy-MM-dd HH:mm:ss";
            }   
            Date startDate = DateUtil.convertStringToDate(mask, sDate);
            Date endDate = DateUtil.convertStringToDate(mask, eDate);
            if (startDate.getTime() > endDate.getTime() || startDate.getTime() == endDate.getTime()) {
                return new JsonResultBean(false, "binding failed, startDate should be before endDate.");
            }

            if (dbCustomer == null) {
                Customer customer = new Customer();
                customer.setStartDate(startDate);
                customer.setEndDate(endDate);
                if (json.containsKey("userId") && !"".equals(json.getString("userId"))) {
                    Customer tCustomer = this.customerRepository.getCustomerByUserId(json.getString("userId"));
                    if (tCustomer != null) {
                        return new JsonResultBean(false, "user and device bind failes! userId is exists");
                    }
                    customer.setUserId(json.getString("userId"));
                } else {
                    customer.setUserId(json.getString("phone"));
                }
                customer = this.createCustomer(customer, json, device.getCode());
                gCustomer = customer;
                this.customerRepository.saveCustomer(customer);
                // } else if
                // (dbCustomer.getState().equals(Customer.State.CANCEL)) {
            } else {
                dbCustomer = this.createCustomer(dbCustomer, json, device.getCode());
                dbCustomer.setState(Customer.State.NORMAL);
                dbCustomer.setStartDate(startDate);
                dbCustomer.setEndDate(endDate);
                gCustomer = dbCustomer;
                this.customerRepository.updateCustomer(dbCustomer);
            }
            // else {
            // return new JsonResultBean(false,
            // "binding failed,this custmer is bind device");
            // }

            DeviceCustomerAccountMap map = createDeviceCustomerAccountMap(new DeviceCustomerAccountMap(), gCustomer, device);
            this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);

            device.setState(Device.State.ACTIVATED);
            device.setBindType(BindType.BIND);
            device.setActivateDate(new Date());
            device.setStateChangeDate(new Date());
            device.setIsSync(SyncType.WAITSYNC);
            device.setExpireDate(endDate);
            // 绑定用户的时候，会根据用户所在city更新设备所在city
            device.setCity(gCustomer.getRegion());
            this.deviceRepository.updateDevice(device);
            return new JsonResultBean(true, "user and device bind success!device sno:" + sno);
        } else {
            return new JsonResultBean(false, "bind failed,device has been binding,deivce sno:" + sno);
        }
    }

    @Override
    public String verifyZjDevice(String deviceCode, String ip) {
        Device device = this.deviceRepository.getDeviceByCode(deviceCode);
        //非子平台管理的盒子直接返回校验成功
        if (device == null) {
            return ONE;
        }
        //如果是未绑定锁定的设备，有可能为7天免费体验期盒子，首先判断7天免费体验期是否到期
        if (device.getBindType().equals(BindType.UNBIND) && device.getIsLock().equals(LockType.LOCK)) {
            if (device.getExpireDate() == null || device.getExpireDate().before(new Date())) {
                return TWO;
            } else {
                return ONE;
            }
        }else {
          //然后判断设备是否绑定用户，绑定则校验IP
            if(device.getBindType().equals(BindType.BIND) && device.getIsLock().equals(LockType.UNLOCKED)){
                String isCheckDevice = this.systemConfigService.getSystemConfigByConfigKey("isCheckDevice");
                if (StringUtils.isNotBlank(isCheckDevice) && "true".equals(isCheckDevice)) {
                    List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
                    if (maps == null || maps.isEmpty()) {
                        return TWO;
                    }
                }
                String isCheckIp = this.systemConfigService.getSystemConfigByConfigKey("isCheckIp");
                if (StringUtils.isNotBlank(isCheckIp) && "true".equals(isCheckIp)) {
                    return this.getAuthIpResult(ip);
                }
            }
            //如果是未绑定未锁定的盒子或者绑定锁定的盒子，直接通过
            return ONE;
        }
        
    }

    @Override
    public JsonResultBean deviceJxBindCustomer(JSONObject json)
            throws ParseException {
        String sno = json.getString("sno");
        Device device = this.deviceRepository.getDeviceBySno(sno);
        if (device == null) {
            return new JsonResultBean(false, "binding failed,device sno does not exists,sno:" + sno);
        }
        List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
        if ((mapList == null || mapList.size() == 0)) {
//			Customer dbCustomer = this.customerRepository.getCustomerByPhone(json.getString("phone"));
            Customer dbCustomer = this.customerRepository.getCustomerByUserId(json.getString("userId"));
//			Customer gCustomer = new Customer();

            String sDate = json.getString("startDate");
            String eDate = json.getString("endDate");
            if (StringUtils.isNotBlank(sDate) && sDate.length() == 8) {
                sDate = sDate + "000000";
            }
            if (StringUtils.isNotBlank(eDate) && eDate.length() == 8) {
                eDate = eDate + "235959";
            }
            String mask = "yyyyMMddHHmmss";
            if(sDate.contains("-")){
                mask = "yyyy-MM-dd HH:mm:ss";
            }            
            Date startDate = DateUtil.convertStringToDate(mask, sDate);
            Date endDate = DateUtil.convertStringToDate(mask, eDate);
            if (startDate.getTime() > endDate.getTime() || startDate.getTime() == endDate.getTime()) {
                return new JsonResultBean(false, "binding failed, startDate should be before endDate.");
            }

            if (dbCustomer == null) {
                dbCustomer = new Customer();

//				Customer customer = new Customer();
                dbCustomer.setStartDate(startDate);
                dbCustomer.setEndDate(endDate);
                dbCustomer.setUserId(json.getString("userId"));
//				if (json.containsKey("userId") && !"".equals(json.getString("userId"))) {
//					Customer tCustomer = this.customerRepository.getCustomerByUserId(json.getString("userId"));
//					if (tCustomer != null) {
//						return new JsonResultBean(false, "user and device bind failes! userId is exists");
//					}
//					customer.setUserId(json.getString("userId"));
//				} else {
//					customer.setUserId(json.getString("phone"));
//				}
                dbCustomer = this.createCustomer(dbCustomer, json, device.getCode());
                this.customerRepository.saveCustomer(dbCustomer);
            } else if (dbCustomer.getState().equals(Customer.State.CANCEL)) {
                dbCustomer.setState(Customer.State.NORMAL);
                dbCustomer.setServiceStop(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", eDate));
                dbCustomer.setEndDate(endDate);
                this.customerRepository.updateCustomer(dbCustomer);
            }
//			} else if (dbCustomer.getState().equals(Customer.State.CANCEL)) {
//			} else{
//				dbCustomer = this.createCustomer(dbCustomer, json, device.getCode());
//				dbCustomer.setState(Customer.State.NORMAL);
//				dbCustomer.setStartDate(startDate);
//				dbCustomer.setEndDate(endDate);
//				gCustomer = dbCustomer;
//				this.customerRepository.updateCustomer(dbCustomer);
//			} 
//			else {
//				return new JsonResultBean(false, "binding failed,this custmer is bind device");
//			}

            DeviceCustomerAccountMap map = createDeviceCustomerAccountMap(new DeviceCustomerAccountMap(), dbCustomer, device);
            this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);

            device.setState(Device.State.ACTIVATED);
            device.setBindType(BindType.BIND);
            device.setActivateDate(new Date());
            device.setStateChangeDate(new Date());
            device.setExpireDate(endDate);
            //绑定用户的时候，会根据用户所在city更新设备所在city
            device.setCity(dbCustomer.getRegion());
            this.deviceRepository.updateDevice(device);
            return new JsonResultBean(true, "user and device bind success!device sno:" + sno);
        } else {
            return new JsonResultBean(false, "bind failed,device has been binding,deivce sno:" + sno);
        }
    }

    /**
     * 重新绑定用户和设备
     */
    @Override
    public JsonResultBean deviceReBindCustomer(JSONObject json) {
        String oldSno = json.getString("oldSno");
        String newSno = json.getString("newSno");
        if (StringUtils.isBlank(oldSno) || StringUtils.isBlank(newSno) || oldSno.equals(newSno)) {
            return new JsonResultBean(false, "user rebind device failed: oldSno or newSno is illegal");
        }

        // 查询两个oldSno newSno对应的设备并判断这两个设备的状态
        Device oldDevice = deviceRepository.getDeviceBySno(oldSno);
        Device newDevice = deviceRepository.getDeviceBySno(newSno);
        if (oldDevice == null || newDevice == null) {
            return new JsonResultBean(false, "user rebind device failed: oldSno or newSno of device is not exist");
        }
        List<DeviceCustomerAccountMap> newMapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(newDevice.getId());
        if (newMapList != null && newMapList.size() > 0) {
            return new JsonResultBean(false, "user rebind device failed: newSno device is bind customer");
        }

        // 查询oldSno设备对应的DeviceCustomerAccountMap对象把其中的deviceId修改成新的newSno对应的设备的Id
        List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(oldDevice.getId());
        if (mapList != null && mapList.size() > 0) {
            mapList.get(0).setDeviceId(newDevice.getId());
            mapList.get(0).setDeviceCode(newDevice.getCode());
            mapList.get(0).setDeviceSno(newDevice.getSno());
            mapList.get(0).setCreateDate(new Date());
            this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(mapList.get(0));

            oldDevice.setStateChangeDate(new Date());
            oldDevice.setIsSync(SyncType.WAITSYNC);
            oldDevice.setBindType(BindType.UNBIND);
            oldDevice.setState(Device.State.NONACTIVATED);
            this.deviceRepository.updateDevice(oldDevice);

            newDevice.setActivateDate(new Date());
            newDevice.setState(Device.State.ACTIVATED);
            newDevice.setStateChangeDate(new Date());
            newDevice.setBindType(BindType.BIND);
            newDevice.setExpireDate(oldDevice.getExpireDate());
            this.deviceRepository.updateDevice(newDevice);

            CustomerDeviceHistory history = this.createCustomerDeviceHistory(new CustomerDeviceHistory(), newDevice, oldDevice, mapList.get(0));
            this.deviceRepository.saveCustomerDeviceHistory(history);
            return new JsonResultBean(true, "user rebind device success,oldSno:" + oldSno + ",newSno:" + newSno);
        } else {
            return new JsonResultBean(false, "user rebind device failed: there is no relationship between user and device!");
        }
    }

    @Override
    public JsonResultBean receiveDevice(String json) throws Exception {
        @SuppressWarnings("unchecked")
        List<Device> list = JsonUtils.getList4Json(json, Device.class, null);
        for (int i = 0; i < list.size(); i++) {
            Device device = list.get(i);
            device.setDistributeState(DistributeState.DISTRIBUTE);
            this.deviceRepository.saveDevice(device);
        }
        return new JsonResultBean(true, "receive device success!");
    }

    @Override
    public boolean batchUpdateDevice(List<Device> deviceList) {
        if (!CollectionUtils.isEmpty(deviceList)) {
            boolean result;
            for (Device device : deviceList) {
            	Device _device = this.deviceRepository.getDeviceByYstenId(device.getYstenId());
            	if(_device == null){
            		result = this.deviceRepository.saveDevice(device);
            	}else{
            		result = this.deviceRepository.updateDeviceByYstenId(device)? true : false;
            	}
                if (!result) return result;
            }
        }
        return true;
    }

    /**
     * 修改设备到期时间
     *
     * @throws ParseException
     */
    @Override
    public String[] deviceUpdateServiceStop(String phone, String sno, String servicestop) throws ParseException {
        Device device = deviceRepository.getDeviceBySno(sno);
        boolean flag = false;
        String message = "";
        if (device != null) {
            if (StringUtils.isNotBlank(servicestop) && servicestop.length() == 8) {
                servicestop = servicestop + "235959";
            }
            Date eDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", servicestop);
            device.setExpireDate(eDate);

            DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());

            Customer customer = this.customerRepository.getCustomerByCode(map.getCustomerCode());
            if (customer != null) {
                Date sDate = customer.getStartDate();
                if (eDate.getTime() > sDate.getTime()) {
                    customer.setServiceStop(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", servicestop));
                    this.customerRepository.updateCustomer(customer);
                    flag = true;
                    deviceRepository.updateDevice(device);
                    message = "update deivce services stop date success!";
                } else {
                    message = "update deivce services stop date failed, because service stop time is before service start time.";
                }
            } else {
                message = "update deivce services stop date failed, because sno of device not bind customer.";
            }

        } else {
            message = "update deivce services stop date failed, because sno is not exists.";
        }
        return new String[]{flag + "", message};
    }

    @Override
    public boolean syncWebtvStbForSh(DeviceInfoBean bean) throws ParseException {
        String status = bean.getStatus().getText();
        String loginID = bean.getLoginId().getText();
        String stbID = bean.getStbId().getText();
        String userid = bean.getUserId().getText();
        Long provinceID = Long.parseLong(bean.getProvinceId().getText());
        City city = null;
        if(bean.getCityCode()!=null){
            String cityCode=bean.getCityCode().getText().trim();
            city=cityMapper.queryByCode(cityCode);
        }
        String mac = null;
        if(bean.getMac()!=null){
            mac=bean.getMac().getText().trim();
        }
        boolean flag = false;
        if (WEBTVSTBSYNC_REGISTER.equals(status)) {//注册
            flag = syncWebtvStbForRegister(loginID, stbID, mac, userid, provinceID, city, "regedit");
        } else if (WEBTVSTBSYNC_REMOVE_REGISTER.equals(status)) {//解注册
            flag = syncWebtvStbForRemoveRegister(loginID, stbID, userid, provinceID);
        } else if (WEBTVSTBSYNC_REMOVE.equals(status)) {//清除
            DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCodeAndUserId(loginID, userid);
            if(map!=null){
                this.deviceCustomerAccountMapRepository.delete(map);
            }
            Device device = this.deviceRepository.getDeviceBySno(stbID);
            if(device!=null&&loginID.equals(device.getCode())){
                device.setState(Device.State.NONACTIVATED);
                device.setBindType(BindType.UNBIND);
                device.setPrepareOpen(EPrepareOpen.NOT_OPEN);
                device.setCode(null);
                this.deviceRepository.updateDevice(device);
            }else{
                return false;
            }
            return true;
        } else if (WEBTVSTBSYNC_ADD.equals(status)) {//加装
            flag = syncWebtvStbForAdd(loginID, stbID, mac, userid, provinceID, city);
        } else {//预开通
            String presubTime = bean.getPresubTime().getText();
            flag = syncWebtvStbForPrepareOpen(loginID, stbID, mac, userid, provinceID, city, presubTime);
        }
        return flag;
        /**
         try {
         Device device = this.deviceRepository.getDeviceByCode(bean.getLoginId().getText());
         if (device != null && bean.getStbId().getText().equals(device.getSno())) {
         Date expireDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", "2099-12-31 23:59:59");
         String source = systemConfigService.getSystemConfigByConfigKey("source");
         String areaId = systemConfigService.getSystemConfigByConfigKey("areaId");
         if ("1".equals(bean.getStatus().getText())) {
         if (Device.State.NONACTIVATED.equals(device.getState())) {
         Customer customer = this.customerRepository.getCustomerByUserId(bean.getUserId().getText());
         if (customer == null) {
         // 首次注册
         device.setActivateDate(new Date());
         device.setExpireDate(expireDate);
         device.setState(Device.State.ACTIVATED);
         device.setBindType(BindType.BIND);
         device.setStateChangeDate(new Date());
         device.setIsSync(SyncType.WAITSYNC);
         this.deviceRepository.updateDevice(device);

         Customer c = new Customer();
         c.setCode(NumberGenerator.getNextCode());
         c.setLoginName(bean.getUserId().getText());
         c.setProvince(bean.getProvinceId().getText());
         c.setNickName(bean.getUserId().getText());
         c.setCreateDate(new Date());
         c.setPassword(MD5.encrypt("666666"));
         c.setState(Customer.State.NORMAL);
         c.setActivateDate(new Date());
         c.setUserId(bean.getUserId().getText());
         c.setRealName(bean.getUserId().getText());
         c.setSource(source);
         Area area = new Area();
         area.setId(Long.parseLong(areaId));
         c.setArea(area);
         c.setServiceStop(sdf.parse("2099-12-31 23:59:59"));
         // c.setRegion(bean.getProvinceId().getText());
         this.customerRepository.saveCustomer(c);

         Account account = new Account();
         account.setCreateDate(new Date());
         account.setBalance(new Long(0));
         account.setCustomerId(c.getId() + "");
         account.setState(State.NORMAL);
         account.setPayPassword(MD5.encrypt("666666"));
         account.setCoustomerCode(c.getCode());
         account.setCode(c.getCode());
         this.accountRepository.saveAccount(account);

         DeviceCustomerAccountMap map = new DeviceCustomerAccountMap();
         map.setAccountCode(account.getCode());
         map.setAccountId(account.getId());
         map.setCreateDate(new Date());
         map.setCustomerCode(c.getCode());
         map.setCustomerId(c.getId());
         map.setDeviceId(device.getId());

         map.setDeviceCode(device.getCode());
         map.setDeviceSno(device.getSno());
         map.setUserId(c.getUserId());
         this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);
         return true;
         } else {
         // 解注册之后注册
         device.setBindType(BindType.BIND);
         device.setState(Device.State.ACTIVATED);
         device.setStateChangeDate(new Date());
         this.deviceRepository.updateDevice(device);

         Account account = this.accountRepository.getAccountByCustomerId(customer.getId() + "");
         DeviceCustomerAccountMap map = new DeviceCustomerAccountMap();
         map.setAccountCode(account.getCode());
         map.setAccountId(account.getId());
         map.setCreateDate(new Date());
         map.setCustomerCode(customer.getCode());
         map.setCustomerId(customer.getId());
         map.setDeviceId(device.getId());

         map.setDeviceCode(device.getCode());
         map.setDeviceSno(device.getSno());
         map.setUserId(customer.getUserId());
         this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);
         return true;
         }
         }
         } else if ("2".equals(bean.getStatus().getText())) {
         device.setBindType(BindType.UNBIND);
         device.setState(Device.State.NONACTIVATED);
         device.setStateChangeDate(new Date());
         this.deviceRepository.updateDevice(device);

         List<DeviceCustomerAccountMap> list = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
         if (list != null && list.size() > 0) {
         for (DeviceCustomerAccountMap map : list) {
         if (map.getUserId().equals(bean.getUserId().getText())) {
         CustomerDeviceHistory history = new CustomerDeviceHistory();
         history.setDeviceCode(device.getCode());
         history.setDeviceId(device.getId());
         history.setCreateDate(new Date());
         history.setCustomerCode(map.getCustomerCode());
         history.setCustomerId(map.getCustomerId());
         this.deviceRepository.saveCustomerDeviceHistory(history);
         this.deviceCustomerAccountMapRepository.deleteDeviceCustomerAccountMap(device.getId());
         return true;
         }
         }
         }
         } else if ("3".equals(bean.getStatus().getText())) {
         return true;
         } else if ("4".equals(bean.getStatus().getText())) {
         Customer customer = this.customerRepository.getCustomerByUserId(bean.getUserId().getText());
         if (customer != null && Device.State.NONACTIVATED.equals(device.getState())) {
         device.setActivateDate(new Date());
         device.setExpireDate(expireDate);
         device.setState(Device.State.ACTIVATED);
         device.setBindType(BindType.BIND);
         device.setStateChangeDate(new Date());
         this.deviceRepository.updateDevice(device);

         Account account = this.accountRepository.getAccountByCustomerId(customer.getId() + "");
         DeviceCustomerAccountMap map = new DeviceCustomerAccountMap();
         map.setAccountCode(account.getCode());
         map.setAccountId(account.getId());
         map.setCreateDate(new Date());
         map.setCustomerCode(customer.getCode());
         map.setCustomerId(customer.getId());
         map.setDeviceId(device.getId());

         map.setDeviceCode(device.getCode());
         map.setDeviceSno(device.getSno());
         map.setUserId(customer.getUserId());
         this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);
         return true;
         }
         }
         }
         return false;
         } catch (Exception e) {
         return false;
         }
         **/
    }

    private boolean syncWebtvStbForRegister(String deviceCode, String sno, String mac, String userid, Long provinceID, City city, String title) throws ParseException {
        boolean isSaveCustomer = false;
        boolean isSaveDevice = false;
        boolean isPrepareOpen = false;
        String realUserId = "";
        Area area = new Area();
        area.setId(provinceID);
        Date expireDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", "2099-12-31 23:59:59");
        
        Customer customer = this.customerRepository.getCustomerByUserId(userid);
        if (customer == null) {
            customer = this.customerRepository.getCustomerByUserId(userid + sno);
            if(customer == null){
                String source = systemConfigService.getSystemConfigByConfigKey("source");
                customer = createCustomer(customer, userid, city, source, area, expireDate);
                isSaveCustomer = true;
            }
        }
        
        Device device = this.deviceRepository.getDeviceBySno(sno);
        if(device==null){
            device = this.deviceRepository.getDeviceByCode(deviceCode);
            if(device!=null){
                System.out.println(title+" false, device code [param:"+deviceCode+",real:"+device.getCode()+"] sno[param:"+sno+",real:"+device.getSno()+"] not match.");
                return false;
            }
            device = initDevice(device, deviceCode, sno, mac, area, city, DateUtil.convertDateToString(expireDate));
            device.setStateChangeDate(new Date());
            device.setCreateDate(new Date());
            device.setState(Device.State.ACTIVATED);
            device.setBindType(BindType.BIND);
            device.setPrepareOpen(EPrepareOpen.FORMAL_OPEN);
            isSaveDevice = true;
        }else{
            if(!deviceCode.equals(device.getCode())){
                System.out.println(title+" false, device code [param:"+deviceCode+",real:"+device.getCode()+"] sno[param:"+sno+",real:"+device.getSno()+"] not match.");
                return false;
            }
        }
        
        if(EPrepareOpen.PREPARE_OPEN.equals(device.getPrepareOpen())){
            isPrepareOpen = true;
            realUserId=userid;
            userid=userid+sno;
        }
        DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCodeAndUserId(deviceCode, userid);
        customer.setArea(area);
        customer.setRegion(city);
        if(map==null){
            DeviceCustomerAccountMap checkMap = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(deviceCode);
            if(checkMap!=null){//查看设备有没有被其他用户使用
                System.out.println(title+" false, device[code:"+deviceCode+"] is use by other customer[userid:"+checkMap.getUserId()+"]");
                return false;
            }
            this.saveOrUpdateDevice(device, area, city, isSaveDevice);
            if(isSaveCustomer){
                this.customerRepository.saveCustomer(customer);
            }else{
                this.customerRepository.updateCustomer(customer);
            }
            DeviceCustomerAccountMap prepareOpenOrRegeditMap = createDeviceCustomerAccountMap(new DeviceCustomerAccountMap(), customer, device);
            this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(prepareOpenOrRegeditMap);
            return true;
        }else{
            this.saveOrUpdateDevice(device, area, city, isSaveDevice);
            if(isPrepareOpen){
                map.setUserId(realUserId);
                customer.setUserId(realUserId);
            }
            this.customerRepository.updateCustomer(customer);
            this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(map);
            return true;
        }
        
        /**
        if (device == null || !deviceCode.equals(device.getCode()) ||
                device.getArea() == null || !provinceID.equals(device.getArea().getId()) ||
                !State.ACTIVATED.equals(device.getState()) || !BindType.BIND.equals(device.getBindType()) ||
                !EPrepareOpen.PREPARE_OPEN.equals(device.getPrepareOpen())) {
            System.out.println("-----webtvStbSync register: device is illegal, null/BIND/not propare open/ACTIVEED");
            return false;
        }
        customer.setUserId(userid);
        customerRepository.updateCustomer(customer);
        
        DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(deviceCode);
        map.setUserId(userid);
        this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(map);
        
        Date expireDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", "2099-12-31 23:59:59");
        device.setExpireDate(expireDate);
        device.setPrepareOpen(EPrepareOpen.FORMAL_OPEN);
        device.setStateChangeDate(new Date());
        this.deviceRepository.updateDevice(device);
        return true;**/
    }
    
    private void saveOrUpdateDevice(Device device, Area area, City city, boolean isSaveDevice){
        if(isSaveDevice){
            this.deviceRepository.saveDevice(device);
        }else{
            device.setArea(area);
            device.setCity(city);
            device.setState(Device.State.ACTIVATED);
            device.setBindType(BindType.BIND);
            device.setPrepareOpen(EPrepareOpen.FORMAL_OPEN);
            this.deviceRepository.updateDevice(device);
        }
    }

    private boolean syncWebtvStbForRemoveRegister(String deviceCode, String sno, String userid, Long provinceID) {
        Customer customer = this.customerRepository.getCustomerByUserId(userid);
        if(customer==null){
            System.out.println("remote regedit false, customer[userid:"+userid+"] is null.");
            return false;
        }
        Device device = this.deviceRepository.getDeviceBySno(sno);
        if(device==null){
            System.out.println("remote regedit false, device[sno:"+sno+"] is null.");
            return false;
        }else{
            if(!deviceCode.equals(device.getCode())){
                System.out.println("remote regedit false, device code[param:"+deviceCode+",real:"+device.getCode()+"],sno[param:"+sno+",real:"+device.getSno()+"] not match.");
                return false;
            }
        }
        DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCodeAndUserId(deviceCode, userid);
        if(map==null){
            System.out.println("remote regedit false, device customer account map[deviceCode:"+deviceCode+", useId:"+userid+"] is null.");
            return false;
        }
        
        device.setBindType(BindType.UNBIND);
        device.setState(Device.State.NONACTIVATED);
        device.setPrepareOpen(EPrepareOpen.NOT_OPEN);
        device.setStateChangeDate(new Date());
        this.deviceRepository.updateDevice(device);
        CustomerDeviceHistory history = new CustomerDeviceHistory();
        history.setDeviceCode(device.getCode());
        history.setDeviceId(device.getId());
        history.setCreateDate(new Date());
        history.setCustomerCode(map.getCustomerCode());
//        history.setCustomerId(map.getCustomerId());
        this.deviceRepository.saveCustomerDeviceHistory(history);
        this.deviceCustomerAccountMapRepository.delete(map);
        return true;
    }

    private boolean syncWebtvStbForAdd(String deviceCode, String sno, String mac, String userid, Long provinceID, City city) throws ParseException {
        Customer customer = this.customerRepository.getCustomerByUserId(userid);
        if(customer==null){
            System.out.println("add device false, customer is null or this is prepare open customer.");
            return false;
        }else{
            if(customer.getArea()==null||!provinceID.equals(customer.getArea().getId())){
                System.out.println("add device false, customer area is not match.");
                return false;
            }
            if(city!=null&&customer.getRegion()!=null&&!city.getId().equals(customer.getRegion().getId())){
                System.out.println("add device false, customer city is not match.");
                return false;
            }
        }
        return syncWebtvStbForRegister(deviceCode, sno, mac, userid, provinceID, city, "add device");
    }

    /**
     * AAA创建用户
     * @param customer
     * @param userid
     * @param sno
     * @param source
     * @param area
     * @param expireDate
     * @return
     */
    private Customer createCustomer(Customer customer, String userid, City city, String source, Area area, Date expireDate){
        if(customer == null){
            customer = new Customer();
        }
        customer.setRegion(city);
        customer.setCode(NumberGenerator.getNextCode());
        customer.setLoginName(userid);
        customer.setNickName(userid);
        customer.setCreateDate(new Date());
        customer.setPassword(MD5.encrypt("666666"));
        customer.setState(Customer.State.NORMAL);
        customer.setActivateDate(new Date());
        customer.setUserId(userid);
        customer.setRealName(userid);
        customer.setSource(source);
        customer.setArea(area);
        customer.setServiceStop(expireDate);        
        return customer; 
    }
    
    /**
     * 初始化设备信息
     * @param device
     * @param deviceCode
     * @param area
     * @param deviceExpireDate
     * @return
     * @throws ParseException
     */
    private Device initDevice(Device device, String deviceCode, String sno, String mac, Area area, City city, String deviceExpireDate) throws ParseException{
        if(device == null){
            device = new Device();
        }
        device.setCity(city);
        device.setCode(deviceCode);
        device.setSno(sno);
        device.setMac(mac);
        device.setActivateDate(new Date());
        device.setState(Device.State.NONACTIVATED);
        device.setBindType(BindType.UNBIND);
        device.setPrepareOpen(EPrepareOpen.NOT_OPEN);
        device.setStateChangeDate(new Date());
        device.setExpireDate(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", deviceExpireDate));
        device.setArea(area);
        device.setIsSync(SyncType.WAITSYNC);
        return device;
    }
    
    private boolean syncWebtvStbForPrepareOpen(String deviceCode, String sno, String mac, String userid, Long provinceID, City city, String presubTime) throws ParseException {
        Area area = new Area();
        area.setId(provinceID);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(presubTime));
        String deviceExpireDate = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", calendar.getTime());        
        String source = systemConfigService.getSystemConfigByConfigKey("source");
        DeviceCustomerAccountMap checkMap = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(deviceCode);
        if(checkMap!=null && !(userid+sno).equals(checkMap.getUserId())){
            System.out.println("prepare open false, device[code:"+deviceCode+"] is bind other user[userId:"+checkMap.getUserId()+"].");
            return false;
        }
        Device device = this.deviceRepository.getDeviceBySno(sno);
        if(device!=null && !sno.equals(device.getSno())){
            System.out.println("prepare open false, device code[param:"+deviceCode+",real:"+device.getCode()+"] and sno[param:"+sno+",real:"+device.getSno()+"] not match");
            return false;
        }
        Customer customer = this.customerRepository.getCustomerByUserId(userid + sno);
        if(customer!=null && device!=null && checkMap!=null){//重复预开通
            device.setState(Device.State.ACTIVATED);
            device.setBindType(BindType.BIND);
            device.setPrepareOpen(EPrepareOpen.PREPARE_OPEN);
            device.setArea(area);
            device.setMac(mac);
            device.setCity(city);
            this.deviceRepository.updateDevice(device);
            customer.setArea(area);
            customer.setRegion(city);
            this.customerRepository.updateCustomer(customer);
            return true;
        }
        if(checkMap==null){
            if(customer == null) {
                customer = createCustomer(customer, userid, city, source, area, DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", "2099-12-31 23:59:59"));
                customer.setUserId(userid+sno);
                this.customerRepository.saveCustomer(customer);
            }else{
                customer.setArea(area);
                customer.setRegion(city);
                this.customerRepository.updateCustomer(customer);
            }
            if(device==null){
                device = initDevice(device, deviceCode, sno, mac, area, city, deviceExpireDate);
                device.setSno(sno);
                device.setCreateDate(new Date());
                device.setActivateDate(new Date());
                device.setStateChangeDate(new Date());
                device.setState(Device.State.ACTIVATED);
                device.setBindType(BindType.BIND);
                device.setPrepareOpen(EPrepareOpen.PREPARE_OPEN);
                this.deviceRepository.saveDevice(device);
            }else{
                device = initDevice(device, deviceCode, sno, mac, area, city, deviceExpireDate);
                device.setActivateDate(new Date());
                device.setStateChangeDate(new Date());
                device.setState(Device.State.ACTIVATED);
                device.setBindType(BindType.BIND);
                device.setPrepareOpen(EPrepareOpen.PREPARE_OPEN);
                this.deviceRepository.updateDevice(device);
            }
            DeviceCustomerAccountMap map = createDeviceCustomerAccountMap(new DeviceCustomerAccountMap(), customer, device);
            this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);
            return true;
        }
        System.out.println("prepare open false, In fact this is not going to happen.");
        return false;
    }

    @Override
    public boolean syncLoginIdStatusForSh(String deviceId) throws DeviceNotFoundException, DocumentException {
        String status = "";
        boolean bool = false;
        Device device = this.deviceRepository.getDeviceByCode(deviceId);
        if (device == null) {
            LOGGER.warn("device aaa auth can not find device:deviceCode={}", deviceId);
            throw new DeviceNotFoundException();
        }
        if (Device.State.ACTIVATED.equals(device.getState())) {
            status = "2";
        } else {
            status = "1";
        }
        String url = this.systemConfigService.getSystemConfigByConfigKey("sendDataToShAAA");
        if (StringUtils.isNotBlank(url)) {
            LOGGER.debug(url);
            StringBuffer xmlResult = DataUtils.submitPost(url, this.append("01", deviceId, status));
            LOGGER.debug("xmlResult====" + xmlResult);
            Element root = XmlRootUtils.getRoot(xmlResult.toString());
            if (root != null) {
                String returnCode = root.selectSingleNode("./returnCode").getText();
                if (StringUtils.isNotBlank(returnCode) && "000000".equals(returnCode)) {
                    bool = true;
                }
            }
        }
        return bool;
    }

    private String append(String cntvId, String loginId, String status) {
        StringBuffer xml = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<message module=\"").append(cntvId).append("\" method=\"loginIdStatusSync\" version=\"1.0\">");
        xml.append("<loginID>").append(loginId).append("</loginID>");
        xml.append("<stauts>").append(status).append("</stauts>");
        xml.append("<timestamp>").append(sdf.format(new Date())).append("</timestamp>");
        xml.append("</message>");
        return xml.toString();
    }

    public String[] disableDeviceBySno(String sno) {
        String message = "";
        String flag = "";
        Device device = this.deviceRepository.getDeviceBySno(sno);
        if (device != null) {
            // 终端停用
            device.setState(Device.State.NONACTIVATED);
            device.setBindType(BindType.UNBIND);
            device.setStateChangeDate(new Date());
            device.setIsSync(SyncType.WAITSYNC);
            this.deviceRepository.updateDevice(device);
            // 根据终端号 查找用户信息
            Customer customer = this.customerRepository.getCustomerByDeviceId(device.getId());
            if (customer != null && !Customer.State.UNUSABLE.equals(customer.getState()) && !Customer.State.CANCEL.equals(customer.getState())) {
                // 销户
                customer.setState(Customer.State.CANCEL);
                customer.setCancelledDate(new Date());
                this.customerRepository.updateCustomer(customer);

            	List<DeviceCustomerAccountMap>  list=deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
            	for(int i=0;i<list.size();i++){
            		DeviceCustomerAccountMap map=list.get(i);
            		deviceCustomerAccountMapRepository.delete(map);
            	}
            	
                CustomerDeviceHistory history = this.createCustomerDeviceHistory(new CustomerDeviceHistory(), null, device, null);
                this.deviceRepository.saveCustomerDeviceHistory(history);
                flag = "true";
                message = "cancellation success.";
            } else {
                message = "cancellation fail, because customer is not exists.";
            }
        } else {
            message = "cancellation fail, because sno is not exists.";
        }
        return new String[]{flag, message};
    }

    @Override
    public Device getDeviceByDeviceId(long deviceId) {
        Device device = deviceRepository.getDeviceById(deviceId);
        device.setDeviceType(deviceRepository.getDeviceTypeById(device.getDeviceType().getId()));
        return device;
    }

    @Override
    public Device getDeviceByDeviceMac(String mac) {
        return deviceRepository.getDeviceByMac(mac);
    }

    @Override
    public boolean updateDevice(Device device) {
        return deviceRepository.updateDevice(device);
    }

    @Override
    public Device getDeviceByDeviceCode(String deviceCode) {
        return deviceRepository.getDeviceByCode(deviceCode);
    }

    @Override
    public boolean replaceWebtvStbForSh(DeviceInfoBean bean) throws ParseException {
        String code = bean.getLoginId() == null ? null : bean.getLoginId().getText();
        String oldCode = bean.getOldLoginId() == null ? null : bean.getOldLoginId().getText();
        String sno = bean.getStbId().getText();
        String oldSno = bean.getOldStbId().getText();
        String userid = bean.getUserId().getText();
        Long provinceID = Long.parseLong(bean.getProvinceId().getText());

        Device device = deviceRepository.getDeviceBySno(sno);
        Device oldDevice = deviceRepository.getDeviceBySno(oldSno);
        if (!validNewDeviceLegalForShReplaceWebtvStb(code, device)) {
            return false;
        }
        if (!validOldDeviceLegalForShReplaceWebtvStb(userid, oldCode, provinceID, oldDevice)) {
            return false;
        }
        boolean isSaveDevice = false;
        if(device == null){
            isSaveDevice=true;
        }else{
            code = device.getCode();
        }
        DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCodeAndUserId(oldDevice.getCode(), userid);
        if(map==null){
            System.out.println("replace web tv false, old device has no customer-device-relation");
            return false;
        }
        
        Area area = new Area();
        area.setId(provinceID);
        device = initDevice(device, code, sno, null, area, null, "2099-12-31 23:59:59");
        device.setStateChangeDate(new Date());
        device.setCreateDate(new Date());
        device.setState(Device.State.ACTIVATED);
        device.setBindType(BindType.BIND);
        device.setPrepareOpen(oldDevice.getPrepareOpen());
        if(isSaveDevice){
            this.deviceRepository.saveDevice(device);
        }else{
            this.deviceRepository.updateDevice(device);
        }

        oldDevice.setBindType(BindType.UNBIND);
        oldDevice.setState(Device.State.NONACTIVATED);
        oldDevice.setPrepareOpen(EPrepareOpen.NOT_OPEN);
        oldDevice.setStateChangeDate(new Date());
        oldDevice.setIsSync(SyncType.WAITSYNC);
        this.deviceRepository.updateDevice(oldDevice);
        
        map.setDeviceId(device.getId());
        map.setDeviceCode(device.getCode());
        map.setDeviceSno(device.getSno());
        map.setUserId(bean.getUserId().getText());
        this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(map);

        CustomerDeviceHistory history = new CustomerDeviceHistory();
        history.setCreateDate(new Date());
        history.setCustomerCode(map.getCustomerCode());
//        history.setCustomerId(map.getCustomerId());
//        history.setOldDeviceCode(oldDevice.getCode());
//        history.setOldDeviceId(oldDevice.getId());
        this.deviceRepository.saveCustomerDeviceHistory(history);
        return true;
        /**
         String userId = bean.getUserId().getText();
         String oldDeviceCode = bean.getOldLoginId().getText();
         String oldSno = bean.getOldStbId().getText();
         Device oldDevice = deviceRepository.getDeviceBySno(oldSno);
         String newDeviceCode = bean.getLoginId().getText();
         String newSno = bean.getStbId().getText();
         Device newDevice = this.deviceRepository.getDeviceBySno(newSno);
         DeviceCustomerAccountMap checkMap = new DeviceCustomerAccountMap();
         boolean isOldDeviceLegal = false;
         boolean isNewDeviceLegal = false;

         // 检查旧设备stbId和loginId是否对应
         if (oldDevice != null) {
         if (oldDeviceCode != null && !oldDeviceCode.equals("")) {
         if (oldDeviceCode.equals(oldDevice.getCode()))
         isOldDeviceLegal = true;
         } else {
         isOldDeviceLegal = true;
         }
         }

         // 检查旧设备stbId和userId是否对应
         if (isOldDeviceLegal == true) {
         isOldDeviceLegal = false;
         List<DeviceCustomerAccountMap> list = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(oldDevice.getId());
         if (list != null && list.size() > 0) {
         for (DeviceCustomerAccountMap map : list) {
         if (userId.equals(map.getUserId())) {
         checkMap = map;
         isOldDeviceLegal = true;
         break;
         }
         }
         }
         } else {
         isOldDeviceLegal = false;
         }

         if (isOldDeviceLegal == true) {
         if (newDevice == null) {
         // 旧机顶盒解注册
         oldDevice.setBindType(BindType.UNBIND);
         oldDevice.setState(Device.State.NONACTIVATED);
         oldDevice.setStateChangeDate(new Date());
         oldDevice.setIsSync(SyncType.WAITSYNC);
         this.deviceRepository.updateDevice(oldDevice);

         CustomerDeviceHistory history = new CustomerDeviceHistory();
         history.setCreateDate(new Date());
         history.setCustomerCode(checkMap.getCustomerCode());
         history.setCustomerId(checkMap.getCustomerId());
         history.setOldDeviceCode(oldDevice.getCode());
         history.setOldDeviceId(oldDevice.getId());
         this.deviceRepository.saveCustomerDeviceHistory(history);
         this.deviceCustomerAccountMapRepository.deleteDeviceCustomerAccountMap(oldDevice.getId());
         return true;
         } else {
         if (Device.State.NONACTIVATED.equals(newDevice.getState())) {
         // 检查新设备stbId和loginId是否对应
         if (newDeviceCode != null && !newDeviceCode.equals("")) {
         if (newDeviceCode.equals(newDevice.getCode()))
         isNewDeviceLegal = true;
         } else {
         isNewDeviceLegal = true;
         }

         if (isNewDeviceLegal) {
         // 换机
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date expireDate = null;
         try {
         expireDate = sdf.parse("2099-12-31 23:59:59");
         } catch (ParseException e1) {
         return false;
         }

         oldDevice.setBindType(BindType.UNBIND);
         oldDevice.setState(Device.State.NONACTIVATED);
         oldDevice.setStateChangeDate(new Date());
         this.deviceRepository.updateDevice(oldDevice);

         newDevice.setActivateDate(new Date());
         newDevice.setStateChangeDate(new Date());
         newDevice.setExpireDate(expireDate);
         newDevice.setState(Device.State.ACTIVATED);
         newDevice.setBindType(BindType.BIND);
         this.deviceRepository.updateDevice(newDevice);

         checkMap.setDeviceId(newDevice.getId());
         checkMap.setDeviceCode(newDevice.getCode());
         checkMap.setDeviceSno(newDevice.getSno());
         checkMap.setUserId(bean.getUserId().getText());
         this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(checkMap);

         CustomerDeviceHistory history = new CustomerDeviceHistory();
         history.setCreateDate(new Date());
         history.setCustomerCode(checkMap.getCustomerCode());
         history.setCustomerId(checkMap.getCustomerId());
         history.setDeviceCode(newDevice.getCode());
         history.setDeviceId(newDevice.getId());
         history.setOldDeviceCode(oldDevice.getCode());
         history.setOldDeviceId(oldDevice.getId());
         this.deviceRepository.saveCustomerDeviceHistory(history);
         return true;
         } else {
         return false;
         }
         }
         }
         }
         return false;
         **/
    }

    private boolean validOldDeviceLegalForShReplaceWebtvStb(String userid, String code, Long provinceID, Device device) {
        if (device != null && 
                device.getState().equals(State.ACTIVATED) &&
                device.getBindType().equals(BindType.BIND) && 
                device.getArea() != null &&
                device.getArea().getId().equals(provinceID)) {
            if (code == null) {
                return true;
            } else {
                if (code.equals(device.getCode())) {
                    return true;
                }else{
                    System.out.println("replace web tv false, old device code[param:"+code+", real:"+device.getCode()+"] not match.");
                    return false;
                }
            }
        }else{
            System.out.println("replace web tv false, old device code[param:"+code+", real:"+device.getCode()+"] is null or not bind.");
            return false;
        }
    }

    private boolean validNewDeviceLegalForShReplaceWebtvStb(String code, Device device) {
        boolean flag = false;
        if(device == null){
            return true;
        }else{
            if (State.NONACTIVATED.equals(device.getState()) &&
                    BindType.UNBIND.equals(device.getBindType()) && 
                    EPrepareOpen.NOT_OPEN.equals(device.getPrepareOpen())) {
                if (code == null) {
                    flag = true;
                } else {
                    if (code.equals(device.getCode())) {
                        flag = true;
                    } else {
                        System.out.println("replace web tv false, new device code[param:"+code+", real:"+device.getCode()+"] not match.");
                        return false;
                    }
                }
            }
        }
        if (flag) {
            List<DeviceCustomerAccountMap> list = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
            if (list == null || list.size() == 0) {
                return true;
            }
            System.out.println("replace web tv false, new device code[real:"+device.getCode()+"] is bind customer.");
        }
        return false;
    }

    public List<Device> getByDateType(String getByDateType) {
        return this.getByDateType(getByDateType);
    }

    @Override
    public Device getLoginId(String sno, String mac) {
        Device snoDevice = deviceRepository.getDeviceBySno(sno);
        if (snoDevice == null) {
            Device device = deviceRepository.getNoAuthDevice();
            if (device == null) {
                return null;
            } else {
                device.setSno(sno);
                device.setMac(mac);
                deviceRepository.updateDevice(device);
                return device;
            }
        } else {
            return null;
        }
    }

    @Override
    public City getCityFromDevice(Device device) {
        City city = device.getCity();
        if (city != null) {
            city = cityRepository.getCityById(city.getId());
        }
        return city;
    }

    @Override
    public JsonResultBean bindMultipleDevice(JSONObject json) throws ParseException {
        // 1. 设备验证：设备是否存在、设备有无绑定用户
        JSONArray jsonArray = json.getJSONArray("snoList");
        if (jsonArray.size() == 0) {
            return new JsonResultBean(false, "binding failed,input parameters in the sno does not exist.");
        }
        List<Device> deviceList = new ArrayList<Device>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String sno = jsonArray.getJSONObject(i).getString("sno");
            Device device = this.deviceRepository.getDeviceBySno(sno);
            if (device == null) {
                return new JsonResultBean(false, "binding failed,device sno does not exists,sno:" + sno);
            } else {
                List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
                if (mapList != null && mapList.size() > 0) {
                    return new JsonResultBean(false, "bind failed, device has been binding, deivce sno:" + sno);
                }
            }
            deviceList.add(device);
        }

        // 1.1对于个人，一个用户只能绑定一个设备
        if (deviceList != null && deviceList.size() > 1) {
            if ("0".equals(json.getString("isGroup"))) {
                return new JsonResultBean(false, "bind failed, because isGroup is 0, a user can only bind a sno.");
            }
        }

        // 2.设备合法，检查用户是否合法，userId和phone是否对应,及是否创建（更新）用户
        Customer dbCustomer = this.customerRepository.getCustomerByPhone(json.getString("phone"));
        String sDate = json.getString("startDate");
        String eDate = json.getString("endDate");
        if (StringUtils.isNotBlank(sDate) && sDate.length() == 8) {
            sDate = sDate + "000000";
        }
        if (StringUtils.isNotBlank(eDate) && eDate.length() == 8) {
            eDate = eDate + "235959";
        }
        //时间FORMAT支持yyyyMMdd yyyyMMddHHmmss  yyyy-MM-dd HH:mm:ss格式
        String mask = "yyyyMMddHHmmss";
        if(sDate.contains("-")){
            mask = "yyyy-MM-dd HH:mm:ss";
        }
        Date startDate = DateUtil.convertStringToDate(mask, sDate);
        Date endDate = DateUtil.convertStringToDate(mask, eDate);
        if (startDate.getTime() > endDate.getTime() || startDate.getTime() == endDate.getTime()) {
            return new JsonResultBean(false, "binding failed, startDate should be before endDate.");
        }
        Customer globalCustomer = new Customer();
        String isGroup = json.getString("isGroup");
        if (dbCustomer == null) {
            Customer customer = this.createCustomer(new Customer(), json, null);
            if (!customer.setCustomerType(isGroup)) {
                return new JsonResultBean(false, "bind failed, isGroup is illegal.");
            }

            customer.setUserId(json.getString("userId"));
            globalCustomer = customer;
            this.customerRepository.saveCustomer(customer);
        } else {
            if (!dbCustomer.getUserId().equals(json.getString("userId"))) {
                return new JsonResultBean(false, "bind failed, useId and phone number do not match");
            }
            if (!dbCustomer.setCustomerType(isGroup)) {
                return new JsonResultBean(false, "bind failed, isGroup is illegal.");
            }

            dbCustomer.setState(Customer.State.NORMAL);
            dbCustomer.setUserId(json.getString("userId"));
            globalCustomer = dbCustomer;
            this.customerRepository.updateCustomer(dbCustomer);
        }
        for (Device device : deviceList) {
            DeviceCustomerAccountMap map = this.createDeviceCustomerAccountMap(new DeviceCustomerAccountMap(), globalCustomer, device);
            this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);

            device.setState(Device.State.ACTIVATED);
            device.setBindType(BindType.BIND);
            device.setActivateDate(new Date());
            device.setStateChangeDate(new Date());
            device.setIsSync(SyncType.WAITSYNC);
            device.setExpireDate(endDate);
            this.deviceRepository.updateDevice(device);
        }
        return new JsonResultBean(true, "user and device bind success!device sno:" + jsonArray.toString());
    }

    @Override
    public Long getAllDeviceCreatedLastDay(String cityId) {
        return this.deviceRepository.getAllDeviceCreatedLastDay(cityId);
    }

    @Override
    public List<Device> getDeviceNotActivated() {
        return this.deviceRepository.getDeviceNotActivated();
    }

    @Override
    public boolean pauseOrRecoverDevice(String sno, String type) {
        Device device = this.deviceRepository.getDeviceBySno(sno);
        if (device != null) {
            if (Device.State.ACTIVATED.equals(device.getState()) && "pause".equals(type)) {
                device.setStateChangeDate(new Date());
                device.setIsSync(SyncType.WAITSYNC);
                device.setState(Device.State.PAUSED);
            }
            if (Device.State.PAUSED.equals(device.getState()) && "recover".equals(type)) {
                device.setStateChangeDate(new Date());
                device.setState(Device.State.ACTIVATED);
            }
            return this.deviceRepository.updateDevice(device);
        }
        return false;
    }

    @Override
    public boolean saveDevice(Device device) {
        return deviceRepository.saveDevice(device);
    }

    @Override
    public boolean receiveDevice(Device device) {
        Device dbDevice = this.getDeviceByDeviceCode(device.getCode());
        if (null == dbDevice) {
            device.setDistributeState(DistributeState.DISTRIBUTE);
            return this.saveDevice(device);
        } else {
            device.setDistributeState(DistributeState.DISTRIBUTE);
            return this.updateDevice(device);
        }
    }

    @Override
    public boolean batchReceiveDevice(List<Device> deviceList) {
        if (!CollectionUtils.isEmpty(deviceList)) {
            boolean result;
            for (Device device : deviceList) {
                result = this.receiveDevice(device);
                if (!result) return result;
            }
        }
        return true;
    }

    @Override
    public String verifyJxDevice(String deviceCode, String ip) throws Exception {
        if (StringUtils.isEmpty(deviceCode)) {
            return XmlUtils.createXmlResponse(TWO, "Device code not given");
        }
        if (StringUtils.isEmpty(ip)) {
            return XmlUtils.createXmlResponse(TWO, "Device IP not given");
        }
        if (!this.getAuthIpResult(ip).equals(ONE)) {
            return XmlUtils.createXmlResponse(THREE, "Invalid IP");
        }
        Device device = this.deviceRepository.getDeviceByCode(deviceCode);
        if (device == null) {
            return XmlUtils.createXmlResponse(TWO, "Device not found!");
        }
        SpDefine sp = this.configRepository.getSpDefineById(device.getSpDefine().getId());
        if (sp == null) {
            return XmlUtils.createXmlResponse(TWO, "Sp not found!");
        }
        if (!sp.getName().equals(SP_NAME)) {
            return XmlUtils.createXmlResponse(ONE, "verified ok");
        }
        if (device.getState() == null) {
            return XmlUtils.createXmlResponse(TWO, "Device state can not be empty!");
        }
        boolean isBind = isDeviceBind(device.getId());
        String _upgradeDate = this.systemConfigService.getSystemConfigByConfigKey("upgrade_date");
        Date upgradeDate = new SimpleDateFormat("yyyy-MM-dd").parse(_upgradeDate);
        // 如果是未激活的设备，判断导入日期是否在升级日期之前，之前的可以上线
        if (Device.State.NONACTIVATED.equals(device.getState())) {
            if (!isBind) {
                if (device.getCreateDate().before(upgradeDate)) {
                    return XmlUtils.createXmlResponse(ONE, "verified ok");
                } else {
                    return XmlUtils.createXmlResponse(TWO, "The import date of the nonactived device that can be on line should be before upgrade date!");
                }
            }
            // 如果是激活的设备
        } else if (Device.State.ACTIVATED.equals(device.getState())) {
            if (isBind) {
                // 如果有绑定用户，且还未到期，则验证通过
                if (device.getExpireDate() == null) {
                    return XmlUtils.createXmlResponse(TWO, "Device expire data can not be null!");
                } else {
                    // String state = null;
                    if (device.getExpireDate().after(new Date(System.currentTimeMillis()))) {
                        return XmlUtils.createXmlResponse(ONE, "verified ok");
                    } else {
                        device.setState(Device.State.NOTUSE);
                        this.deviceRepository.updateDevice(device);
                        return XmlUtils.createXmlResponse(TWO, "Contract expired!");
                    }
                }
            } else {
                // 如果未绑定用户，且终端导入日期小于升级日期的可以绑定
                if (device.getCreateDate() == null) {
                    return XmlUtils.createXmlResponse(TWO, "Device create data can not be null!");
                } else {
                    if (device.getCreateDate().before(upgradeDate)) {
                        return XmlUtils.createXmlResponse(ONE, "verified ok");
                    } else {
                        return XmlUtils.createXmlResponse(TWO, "Device can be online before " + _upgradeDate);
                    }
                }
            }
        } else {
            return XmlUtils.createXmlResponse(TWO, "Device is disabled!");
        }
        return XmlUtils.createXmlResponse(ONE, "verified ok");
    }

    private boolean isDeviceBind(Long deviceId) {
        List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(deviceId);
        if (mapList != null && mapList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<DeviceGroup> findDeviceGroupByDeviceCode(String deviceCode) {
        return this.deviceRepository.findDeviceGroupByYstenId(deviceCode, null);
    }

    @Override
    public DeviceGroup findDeviceGroupByDeviceGroupId(long deviceGroupId) {
        return this.deviceRepository.findDeviceGroupByDeviceGroupId(deviceGroupId);
    }

    @Override
    public DeviceSoftwareCode findDeviceSoftwareCodeByCode(String code) {
        return this.deviceRepository.findDeviceSoftwareCodeByCode(code);
    }

    @Override
    public Boolean insertDeviceUpgradeResultLog(DeviceUpgradeResultLog deviceUpgradeResultLog) {
        return this.deviceRepository.insertDeviceUpgradeResultLog(deviceUpgradeResultLog);
    }

    @Override
    public Boolean insertApkUpgradeResultLog(ApkUpgradeResultLog apkUpgradeResultLog) {
        return this.deviceRepository.insertApkUpgradeResultLog(apkUpgradeResultLog);
    }

    @Override
    public List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogListByDeviceCodeAndYstenId(String deviceCode, String ystenId) {
        return this.deviceRepository.findDeviceUpgradeResultLogListByDeviceCodeAndYstenId(deviceCode, ystenId);
    }

    @Override
    public DeviceUpgradeMap findDeviceUpgradeMapByDeviceCode(String deviceCode) {
        return this.deviceRepository.findDeviceUpgradeMapByDeviceCode(deviceCode);
    }

    @Override
    public DeviceUpgradeMap findDeviceUpgradeMapByGroupId(long groupId) {
        return this.deviceRepository.findDeviceUpgradeMapByGroupId(groupId);
    }

    @Override
    public DeviceSoftwarePackage findDeviceSoftwarePackagebyId(long id) {
        return this.deviceRepository.findDeviceSoftwarePackagebyId(id);
    }

    @Override
    public UpgradeTask findUpgradeTaskById(long id) {
        return this.deviceRepository.findUpgradeTaskById(id);
    }

    @Override
    public DeviceSoftwareCode findDeviceSoftwareCodeById(long id) {
        return this.deviceRepository.findDeviceSoftwareCodeById(id);
    }

    @Override
    public UpgradeTask findLatestUpgradeTask(long deviceGroupId, String softwareCode, long deviceVersionSeq) {
        return this.deviceRepository.findLatestUpgradeTask(deviceGroupId, softwareCode, deviceVersionSeq);
    }


    @Override
    public List<DeviceGroup> findGroupByDeviceCodeType(String deviceCode, EnumConstantsInterface.DeviceGroupType type) {
        List<DeviceGroupMap> deviceGroupMapList = deviceGroupMapRepository.getByYstenId(deviceCode);
        if (!CollectionUtils.isEmpty(deviceGroupMapList)) {
            return deviceGroupRepository.findDeviceGroupByMapListAndType(deviceGroupMapList, type);
        }
        return null;
//        return deviceRepository.findGroupByDeviceCodeType(deviceCode,type);
    }
    
    @Override
    public List<DeviceGroup> findGroupByYstenIdType(String ystenId, EnumConstantsInterface.DeviceGroupType type) {
        List<DeviceGroupMap> deviceGroupMapList = deviceGroupMapRepository.getByYstenId(ystenId);
        if (!CollectionUtils.isEmpty(deviceGroupMapList)) {
            return deviceGroupRepository.findDeviceGroupByMapListAndType(deviceGroupMapList, type);
        }
        return null;
//        return deviceRepository.findGroupByDeviceCodeType(deviceCode,type);
    }

    @Override
    public Pageable<DeviceGroup> finDeviceGroupNotBoundServiceCollect(String name, Integer pageNo, Integer pageSize) {
        return deviceGroupRepository.finDeviceGroupNotBoundServiceCollect(name, pageNo, pageSize);
    }


    @Override
    public List<DeviceGroup> finBootDeviceGroupNotBoundServiceCollect() {
        return deviceGroupRepository.finBootDeviceGroupNotBoundServiceCollect();
    }

    @Override
    public DeviceSoftwarePackage findLatestSoftwarePackageByDevice(
            String ystenId, long softwareCodeId, Integer deviceVersionSeq) {
        return this.deviceRepository.getLatestSoftwarePackageByDevice(ystenId, softwareCodeId, deviceVersionSeq);
    }



    @Override
    public DeviceSoftwarePackage findLatestSoftwarePackageByUser(String userId, long softwareCodeId,
                                                                 Integer deviceVersionSeq) {
        return this.deviceRepository.getLatestSoftwarePackageByUser(userId, softwareCodeId, deviceVersionSeq);
    }

    @Override
    public DeviceSoftwarePackage findLatestSoftwarePackageByDeviceGroup(
            String ystenId, long softwareCodeId, Integer deviceVersionSeq) {
        //updated by joyce on 2014-6-27 begin
//            List<DeviceGroup> groupList = this.deviceRepository.findDeviceGroupByYstenId(device.getCode(),EnumConstantsInterface.DeviceGroupType.BACKGROUND);
        List<DeviceGroup> groupList  = this.deviceRepository.findDeviceGroupByYstenId(ystenId, EnumConstantsInterface.DeviceGroupType.UPGRADE);
        //取消设备的动态分组
//        List<DeviceGroup> dynamicGroupList = deviceGroupService.findDynamicGroupList(ystenId, EnumConstantsInterface.DeviceGroupType.UPGRADE.toString(), Constant.BSS_DEVICE_UPGRADE_IMAGE_MAP);
//        if (dynamicGroupList != null && dynamicGroupList.size() > 0) {
//            groupList.addAll(dynamicGroupList);
//        } else {
        
//        }
        //updated by joyce on 2014-6-27 end
  
        //根据设备组查询与升级的关系
        if (!org.apache.commons.collections.CollectionUtils.isEmpty(groupList)) {
            DeviceUpgradeMap deviceUpgradeMap = this.deviceRepository.findDeviceUpgradeMapByGroupId(groupList.get(0).getId());
            if(null == deviceUpgradeMap || null == deviceUpgradeMap.getUpgradeId()){
                return null;
            }
            return this.deviceRepository.getLatestSoftwarePackageByDeviceGroup(softwareCodeId, deviceVersionSeq, deviceUpgradeMap.getUpgradeId());
        }
        return null;
    }

    @Override
    public DeviceSoftwarePackage findLatestSoftwarePackageByUserGroup(String userId, long softwareCodeId,
                                                                      Integer deviceVersionSeq) {
        //updated by joyce on 2014-6-27 begin
        //3.用户分组是否绑定升级
        List<UserGroupMap> userGroupMap = this.userGroupMapRepository.findByUserCode(userId);
        if (!org.apache.commons.collections.CollectionUtils.isEmpty(userGroupMap)) {
            List<UserGroup> groupList = new ArrayList<UserGroup>();
            List<UserGroup> dynamicGroupList = userGroupService.findDynamicGroupList(userId, EnumConstantsInterface.UserGroupType.UPGRADE.toString(), Constant.BSS_USER_UPGRADE_IMAGE_MAP);
            if (dynamicGroupList != null && dynamicGroupList.size() > 0) {
                groupList.addAll(dynamicGroupList);
            } else {
                groupList = this.userGroupRepository.findUserGroupListByIdsAndType(userGroupMap, EnumConstantsInterface.UserGroupType.UPGRADE);
            }

            //根据用户组查询与升级的关系
            if (!org.apache.commons.collections.CollectionUtils.isEmpty(groupList)) {
                UserUpgradeMap userUpgradeMap = this.deviceRepository.findMapByUserGroupId(groupList.get(0).getId());
                return this.deviceRepository.getLatestSoftwarePackageByUserGroup(softwareCodeId, deviceVersionSeq, userUpgradeMap.getUpgradeId());
            }
        }
        //updated by joyce on 2014-6-27 end
        return null;
    }

    @Override
    public List<DeviceSoftwarePackage> getSoftwarePackageListBySoftCodeIdAndVersionSeq(long softwareCodeId, Integer deviceVersionSeq) {
        return this.deviceRepository.getSoftwarePackageListBySoftCodeIdAndVersionSeq(softwareCodeId, deviceVersionSeq);
    }

    @Override
    public JsonResultBean bindFreePeriod(String deviceId) throws Exception {
        if (StringUtils.isBlank(deviceId)) {
            return new JsonResultBean(false, "Device id is null!");
        }
        //浙江移动传过来的deviceId为32位串码
        Device device = this.deviceRepository.getDeviceByMac(deviceId);
        if (device == null) {
            return new JsonResultBean(false, "Device not found!");
        }
        if (device.getBindType().equals(BindType.BIND)) {
            return new JsonResultBean(false, "Device has bound!");
        }
        //7天免费体验期盒子lock状态更新为锁定，到期时间更新为7天后。
        //二次认证的时候，对lock状态的未绑定的盒子，验证到期时间
        device.setIsLock(LockType.LOCK);
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 7);
        device.setExpireDate(now.getTime());
        boolean isSuccess = this.deviceRepository.updateDevice(device);
        if (isSuccess) {
            return new JsonResultBean(true, "Device bind free period success!");
        }
        return new JsonResultBean(true, "Device bind free period failed!");
    }

    @Override
    public Device getDeviceByYstenId(String ystenId) {

        return deviceRepository.getDeviceByYstenId(ystenId);
    }

    @Override
    public List<Device> terminationDuoToDeviceByPreOpening() {
        List<Device> devices = this.deviceRepository.findDuoToDeviceByPreOpening();
        //对到期的预开通设备进行解绑操作
        if (devices != null && devices.size() > 0) {
            for (Device device : devices) {
                DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
                this.syncWebtvStbForRemoveRegister(device.getCode(), device.getSno(), map.getUserId(), device.getArea().getId());
            }
        }
        return devices;
    }

    @Override
    public City getCityByCode(String code) {
        return this.cityRepository.getCityByCityCode(code);
    }
    
    @Override
    public City getCityById(Long id) {
       return this.cityRepository.getCityById(id);
    }

    @Override
    public Device getDeviceByMac(String mac) {
        return deviceRepository.getDeviceByMac(mac);
    }

    @Override
    public List<Device> findExpirePrepareOpenDevice() {
        return deviceRepository.findExpirePrepareOpenDevice();
    }

    @Override
    public void offlineExpirePrepareOpenDevice(List<Device> devices) {
        if(devices==null){
            return;
        }
        for(Device device : devices){
            DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
            device.setBindType(BindType.UNBIND);
            device.setState(Device.State.NONACTIVATED);
            device.setPrepareOpen(EPrepareOpen.NOT_OPEN);
            device.setStateChangeDate(new Date());
            if(map==null){
                this.deviceRepository.updateDevice(device);
                continue;
            }
            this.deviceRepository.updateDevice(device);
            CustomerDeviceHistory history = new CustomerDeviceHistory();
            history.setDeviceCode(device.getCode());
            history.setDeviceId(device.getId());
            history.setCreateDate(new Date());
            history.setCustomerCode(map.getCustomerCode());
//            history.setCustomerId(map.getCustomerId());
            this.deviceRepository.saveCustomerDeviceHistory(history);
            this.deviceCustomerAccountMapRepository.delete(map);
        }
    }
}
