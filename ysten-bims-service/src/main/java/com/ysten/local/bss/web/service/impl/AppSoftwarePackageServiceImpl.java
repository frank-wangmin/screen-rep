package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.exception.DeviceGroupNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.exception.PlatformNotFoundException;
import com.ysten.local.bss.device.repository.*;
import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.local.bss.device.service.IDeviceCustomerAccountMapService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.PackageType;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.local.bss.interfaceUrl.repository.IInterfaceUrlRepository;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.HttpUtils.HttpResponseWrapper;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.web.service.IAppSoftwarePackageService;
import com.ysten.utils.page.Pageable;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AppSoftwarePackageServiceImpl implements IAppSoftwarePackageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppSoftwarePackageServiceImpl.class);

    private static final String STR = "get app upgrade info,can not find";

    @Autowired
    private IAppSoftwarePackageRepository appSoftwarePackageRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private IAppSoftwareCodeRepository appSoftwareCodeRepository;
    @Autowired
    private IAppUpgradeTaskRepository appUpgradeTaskRepository;
    @Autowired
    IAppUpgradeMapRepository appUpgradeMapRepository;

    @Autowired
    private IInterfaceUrlRepository interfaceUrlRepository;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDeviceCustomerAccountMapService deviceCustomerAccountMapService;
    private static final String UTF8 = "utf-8";

    @Override
    public Pageable<AppSoftwarePackage> getListByCondition(String versionName, Integer pageNo, Integer pageSize) {
        return this.appSoftwarePackageRepository.findAppSoftwarePackageListByCondition(FilterSpaceUtils.filterStartEndSpace(versionName), pageNo, pageSize);
    }

    @Override
    public Pageable<AppSoftwarePackage> findAppSoftwaresByCondition(Long softCodeId,
            EnumConstantsInterface.PackageType packageType, String name, Integer pageNo, Integer pageSize) {
        DeviceUpgradeCondition condition = new DeviceUpgradeCondition();
        condition.setSoftCodeId(softCodeId);
        condition.setPackageType(packageType);
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        condition.setName(FilterSpaceUtils.filterStartEndSpace(name));
        return this.appSoftwarePackageRepository.findAppSoftwaresByCondition(condition);
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return this.appSoftwarePackageRepository.deleteAppSoftwarePackageByIds(ids);
    }

    @Override
    public boolean insert(AppSoftwarePackage appSoftwarePackage) {
        return this.appSoftwarePackageRepository.saveAppSoftwarePackage(appSoftwarePackage);
    }

    @Override
    public boolean updateById(AppSoftwarePackage appSoftwarePackage) {
        return this.appSoftwarePackageRepository.updateAppSoftwarePackage(appSoftwarePackage);
    }

    @Override
    public List<AppSoftwarePackage> getAll() {
        return this.appSoftwarePackageRepository.findAllAppSoftwarePackage();
    }

    @Override
    public AppSoftwarePackage getById(Long id) {
        return this.appSoftwarePackageRepository.getAppSoftwarePackageById(id);
    }

    @Override
    public boolean insertOrUpdateSynchronization(AppSoftwarePackage appSoftwarePackage) {
        AppSoftwarePackage data = null;
        if (null == appSoftwarePackage) {
            return false;
        }
        if (appSoftwarePackage.getId() != null) {
            data = appSoftwarePackageRepository.getAppSoftwarePackageById(appSoftwarePackage.getId());
        }
        appSoftwarePackage.setDistributeState(DistributeState.DISTRIBUTE);
        if (data == null) {
            return appSoftwarePackageRepository.saveAppSoftwarePackage(appSoftwarePackage);
        } else {
            return appSoftwarePackageRepository.updateAppSoftwarePackage(appSoftwarePackage);
        }
    }

    @Override
    public boolean batchInsertOrUpdateSynchronization(List<AppSoftwarePackage> appSoftwarePackageList) {
        if (!CollectionUtils.isEmpty(appSoftwarePackageList)) {
            boolean result;
            for (AppSoftwarePackage appSoftwarePackage : appSoftwarePackageList) {
                if(appSoftwarePackage.getPackageType()==PackageType.INCREMENT){
                    if(appSoftwarePackage.getFullSoftwareId() == null || appSoftwarePackage.getFullSoftwareId().equals("")){
                            return false;
                    }
                    if(appSoftwarePackage.getFullSoftwareId() != null && !appSoftwarePackage.getFullSoftwareId().equals("")){
                        AppSoftwarePackage softPackage = this.appSoftwarePackageRepository.getAppSoftwarePackageById(appSoftwarePackage.getFullSoftwareId());
                        if(softPackage == null){
                            return false;
                        }
                    }
                }
                result = this.insertOrUpdateSynchronization(appSoftwarePackage);
                if (!result)
                    return result;
            }
        }
        return true;
    }
    
    @Override
    public AppSoftwarePackage getAppSoftwarePackageByName(String versionName) {
        return this.appSoftwarePackageRepository.getAppSoftwarePackageByName(versionName);
    }

    // @Override
    // public boolean rendSoftwarePackage(
    // List<DeviceSoftwarePackage> deviceSoftwarePackages) throws Exception {
    // boolean bool = false;
    // Long area =
    // Long.parseLong(this.systemConfigService.getSystemConfigByConfigKey("distributeAreaId"));
    // if(area == null){
    // throw new Exception("请先维护配置下发省份!");
    // }
    // InterfaceUrl interfaceUrl =
    // this.interfaceUrlRepository.getByAreaAndName(area,
    // InterfaceName.RECEIVESOFTWAREPACKAGE);
    // if(interfaceUrl == null){
    // throw new Exception("请先维护省份接口Url表!");
    // }
    // String url = interfaceUrl.getInterfaceUrl();
    // bool = this.distributeSoftwarePackageToProvince(deviceSoftwarePackages,
    // url);
    // return bool;
    // }
    // public boolean
    // distributeSoftwarePackageToProvince(List<DeviceSoftwarePackage>
    // deviceSoftwarePackages,String url){
    // String pagam = JsonUtil.getJsonString4List(deviceSoftwarePackages);
    // HttpResponseWrapper wrapper = HttpUtils.doPost(url, pagam, UTF8);
    // int returnCode = wrapper.getHttpStatus();
    // if(returnCode == 200){
    // String rsp = wrapper.getResponse();
    // JSONObject object = JSONObject.fromObject(rsp);
    // String result = object.getString("result");
    // if(StringUtils.equalsIgnoreCase(result, "true")){
    // return true;
    // }
    // return false;
    // }
    // return false;
    // }

    @Override
    public AppSoftwarePackage getAppUpgradeInfo(String deviceCode, String appSoftwareCode, int versionSeq,
            int sdkVersion) throws PlatformNotFoundException, DeviceNotFoundException, DeviceGroupNotFoundException {

        LOGGER.debug("get app upgrade info. deviceCode={}, platformId={}, versionSeq={}.", new Object[] { deviceCode,
                appSoftwareCode, versionSeq, sdkVersion });

        // 1、查询设备信息
        Device device = this.deviceRepository.getDeviceByCode(deviceCode);
        if (device == null || (null != device.getExpireDate() && new Date().after(device.getExpireDate()))) {
            LOGGER.warn(STR + " device : deviceCode = {}", deviceCode);
            throw new DeviceNotFoundException();
        }
//        if (!device.getState().equals(State.ACTIVATED)) {
//            LOGGER.warn(STR + " device : deviceCode = {}", deviceCode);
//            throw new DeviceNotFoundException();
//        }
        // 2、查询软件信息
        AppSoftwareCode asc = this.appSoftwareCodeRepository.getAppSoftwareCodeByCode(appSoftwareCode);
        if (asc == null) {
            LOGGER.warn(STR + " app software code : appSoftwareCode = {}", appSoftwareCode);
            throw new PlatformNotFoundException();
        }
        AppSoftwarePackage appSoftwarePackage = null;
//        List<Long> upgradeIds = null;
        // 根据终端广电号CODE查询用户
        Customer customer = this.getCustomerByDeviceCode(deviceCode);
        if (customer != null) {
            // 用户获取应用升级任务
            List<UserAppUpgradeMap> userAppUpgradeMap = this.appUpgradeTaskRepository
                    .getUserUpgradeMapByUserId(customer.getUserId());
            if (!CollectionUtils.isEmpty(userAppUpgradeMap)) {
                // appSoftwarePackage =
                // this.getLatestAppSoftwarePackage(userAppUpgradeMap.getUpgradeId(),
                // versionSeq, sdkVersion);
                appSoftwarePackage = this.getLatestAppSoftwarePackageByUser(userAppUpgradeMap, versionSeq, sdkVersion,
                        asc.getId());
                if (appSoftwarePackage != null) {
                    return appSoftwarePackage;
                }
            }
            // 用户分组获取升级任务
            List<UserGroup> groupList = this.customerService.findUserGroupListByUserIdAndType(customer.getCode().trim(),
                    EnumConstantsInterface.UserGroupType.APPUPGRADE);
            if (groupList != null && groupList.size() > 0) {
                List<UserAppUpgradeMap> _userAppUpgradeMap = this.appUpgradeTaskRepository
                        .getUserUpgradeMapByUserGroupId(groupList.get(0).getId());
                if (!CollectionUtils.isEmpty(_userAppUpgradeMap)) {
                    // appSoftwarePackage =
                    // this.getLatestAppSoftwarePackage(_userAppUpgradeMap.getUpgradeId(),
                    // versionSeq, sdkVersion);
                    appSoftwarePackage = this.getLatestAppSoftwarePackageByUser(_userAppUpgradeMap, versionSeq,
                            sdkVersion, asc.getId());
                    if (appSoftwarePackage != null) {
                        return appSoftwarePackage;
                    }
                }
            }
        }
        // 3、终端是否有升级任务绑定

        List<AppUpgradeMap> map = deviceRepository.findAppUpgradeMapByDeviceCode(device.getCode());
        if (!CollectionUtils.isEmpty(map)) {
            // appSoftwarePackage =
            // this.getLatestAppSoftwarePackage(map.getUpgradeId(), versionSeq,
            // sdkVersion);
            appSoftwarePackage = this.getLatestAppSoftwarePackageByDevice(map, versionSeq, sdkVersion, asc.getId());
            if (appSoftwarePackage != null) {
                return appSoftwarePackage;
            }
        }
        // 4、终端所在的分组是否有升级任务绑定
        // 4.1终端是否在分组中
        List<DeviceGroup> deviceGroupList = this.deviceRepository.findDeviceGroupByYstenId(device.getCode(),EnumConstantsInterface.DeviceGroupType.APPUPGRADE);
        if (null == deviceGroupList || deviceGroupList.size() <= 0) {
            throw new DeviceGroupNotFoundException("终端" + device.getCode() + "未关联任何分组");
        }
        DeviceGroup deviceGroup = null;
        for (int index = 0; index < deviceGroupList.size(); index++) {
            // 终端所在的升级组
            DeviceGroup dg = deviceGroupList.get(index);
            if (null != dg && dg.getType() == EnumConstantsInterface.DeviceGroupType.APPUPGRADE) {
                deviceGroup = deviceGroupList.get(index);
            }
        }
        // 4.2分组是否关联升级任务.
        if (null != deviceGroup) {
            List<AppUpgradeMap> umap = appUpgradeMapRepository.findpUpgradeMapByGroupIdAndType(deviceGroup.getId(),"GROUP");
            if (!CollectionUtils.isEmpty(umap)) {
                appSoftwarePackage = this
                        .getLatestAppSoftwarePackageByDevice(umap, versionSeq, sdkVersion, asc.getId());
            }
            // appSoftwarePackage =
            // this.getLatestAppSoftwarePackage(umap.getUpgradeId(), versionSeq,
            // sdkVersion);
            if (appSoftwarePackage != null) {
                return appSoftwarePackage;
            }
        }

        // //5、判断当前时间升级数量是否已经超过最大升级数
        // if(appUpgrade.getTimeInterval()>0 && appUpgrade.getMaxNum()>0){
        // if(!this.handUpgradeTimeAndMaxNum(appUpgrade)){
        // return null;
        // }
        // }
        return appSoftwarePackage;
    }

    public Customer getCustomerByDeviceCode(String deviceCode) {
        DeviceCustomerAccountMap dcm = this.deviceCustomerAccountMapService.getDeviceCustomerAccountMapByDeviceCode(deviceCode);
        if (dcm != null) {
            Customer customer = this.customerService.getCustomerByUserId(dcm.getUserId());
            if (null == customer || (null != customer.getServiceStop() && new Date().after(customer.getServiceStop()))) {
                return null;
            }
            return customer;
        }
        return null;
    }

    public AppSoftwarePackage getLatestAppSoftwarePackageByUser(List<UserAppUpgradeMap> userAppUpgradeMap,
            int versionSeq, int sdkVersion, long appSoftwareCode) {
        List<Long> upgradeIds = new ArrayList<Long>();
        for (UserAppUpgradeMap map : userAppUpgradeMap) {
            upgradeIds.add(map.getUpgradeId());
        }
        return this.appUpgradeTaskRepository.findLatestSoftwarePackageByIds(upgradeIds, versionSeq, sdkVersion,
                appSoftwareCode);
    }

    public AppSoftwarePackage getLatestAppSoftwarePackageByDevice(List<AppUpgradeMap> appUpgradeMap, int versionSeq,
            int sdkVersion, long appSoftwareCode) {
        List<Long> upgradeIds = new ArrayList<Long>();
        for (AppUpgradeMap map : appUpgradeMap) {
            upgradeIds.add(map.getUpgradeId());
        }
        return this.appUpgradeTaskRepository.findLatestSoftwarePackageByIds(upgradeIds, versionSeq, sdkVersion,
                appSoftwareCode);
    }

    public AppSoftwarePackage getLatestAppSoftwarePackage(Long upgradeId, int versionSeq, int sdkVersion) {
        AppUpgradeTask task = this.appUpgradeTaskRepository.getAppUpgradeTaskById(upgradeId);
        AppSoftwarePackage asp = task.getSoftwarePackageId();
        if (null != asp) {
            if (versionSeq < asp.getVersionSeq() && sdkVersion == asp.getSdkVersion()) {
                return asp;
            }
        }
        return null;
    }

//    private AppSoftwarePackage findLatestUpgradeTask(List<DeviceGroup> deviceGroupList, String softwareCode,
//            Integer deviceVersionSeq) {
//        UpgradeTask upgradeTask = null;
//        DeviceSoftwarePackage deviceSoftwarePackage = null;
//        AppSoftwarePackage latestDeviceSoftwarePackage = null;
//        return latestDeviceSoftwarePackage;
//    }

    /**
     * 处理升级最大数量和时间
     */
    // private boolean handUpgradeTimeAndMaxNum(AppSoftwarePackage appUpgrade){
    // boolean isUpgrade = false;
    // String upgradeDate =
    // redisTemplate.opsForValue().get(Constant.UPGRADE_DATE_REDIS_KEY);
    // String upgradeMaxNum =
    // redisTemplate.opsForValue().get(Constant.UPGRADE_MAX_NUM_REDIS_KEY);
    // if(StringUtils.isNotBlank(upgradeDate)){
    // //redis中升级数量
    // Long maxNum = Long.parseLong(upgradeMaxNum);
    // //超时时间 = 当前时间-redis中存的时间
    // Long time = new Date().getTime()-Long.parseLong(upgradeDate);
    // if(time<=appUpgrade.getTimeInterval()*3600000){
    // if(maxNum<appUpgrade.getMaxNum()){
    // redisTemplate.opsForValue().set(Constant.UPGRADE_MAX_NUM_REDIS_KEY,
    // String.valueOf(maxNum+1));
    // isUpgrade=true;
    // }
    // }else{
    // //初始化时间和数量
    // this.initUpgradeTimeAndMaxNum();
    // isUpgrade=true;
    // }
    // }else{
    // //为空 初始化时间和数量
    // this.initUpgradeTimeAndMaxNum();
    // isUpgrade=true;
    // }
    // return isUpgrade;
    // }

    ///**
    // * 在redis中初始化设备升级时间和最大数量
     //*/
    //private void initUpgradeTimeAndMaxNum() {
        // redisTemplate.opsForValue().set(Constant.UPGRADE_DATE_REDIS_KEY, new
        // Date().getTime()+"");
        // redisTemplate.opsForValue().set(Constant.UPGRADE_MAX_NUM_REDIS_KEY,
        // "1");
    //}

    @Override
    public List<AppSoftwarePackage> getSoftwarePackageBySoftCode(Long softwareCodeId) {
        return this.appSoftwarePackageRepository.findSoftwarePackageBySoftCode(softwareCodeId);
    }

    @Override
    public boolean rendSoftwarePackage(List<AppSoftwarePackage> appSoftwarePackages, Long area) throws Exception {
        boolean bool = false;
        // Long area =
        // Long.parseLong(this.systemConfigService.getSystemConfigByConfigKey("distributeAreaId"));
        // if(area == null){
        // throw new Exception("请先维护配置下发省份!");
        // }
        if (area == null) {
            throw new Exception("请选择下发省份!");
        }
        InterfaceUrl interfaceUrl = this.interfaceUrlRepository.getByAreaAndName(area,
                InterfaceName.RECEIVEAPPSOFTWAREPACKAGE);
        if (interfaceUrl == null) {
            throw new Exception("请先维护省份接口Url表!");
        }
        String url = interfaceUrl.getInterfaceUrl();
        bool = this.distributeSoftwarePackageToProvince(appSoftwarePackages, url);
        return bool;
    }

    public boolean distributeSoftwarePackageToProvince(List<AppSoftwarePackage> appSoftwarePackages, String url) {
        boolean bool = false;
        String pagam = JsonUtil.getJsonString4List(appSoftwarePackages);
        HttpResponseWrapper wrapper = HttpUtils.doPost(url, pagam, UTF8);
        int returnCode = wrapper.getHttpStatus();
        if (returnCode == 200) {
            String rsp = wrapper.getResponse();
            JSONObject object = JSONObject.fromObject(rsp);
            String result = object.getString("result");
            if (StringUtils.equalsIgnoreCase(result, "true")) {
                bool = true;
            }
        }
        if (bool == true) {
            for (AppSoftwarePackage appSoftwarePackage : appSoftwarePackages) {
                this.appSoftwarePackageRepository.updateDistributeStateById(appSoftwarePackage.getId(),
                        DistributeState.DISTRIBUTE.toString());
            }
        }
        return bool;
    }
}
