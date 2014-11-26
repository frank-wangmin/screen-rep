package com.ysten.local.bss.device.service.impl;

import com.ysten.area.repository.IAreaRepository;
import com.ysten.cache.Cache;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.exception.*;
import com.ysten.local.bss.device.repository.*;
import com.ysten.local.bss.device.service.IApkUpgradeService;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.UpgradeResultStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ApkUpgradeServiceImpl implements IApkUpgradeService {

	@Autowired
    private IDeviceService deviceService;
    @Autowired
    private IApkUpgradeTaskRepository apkUpgradeTaskRepository;
    @Autowired
    private IApkSoftwareCodeRepository apkSoftwareCodeRepository;
    @Autowired
    private IApkSoftwarePackageRepository apkSoftwarePackageRepository;
    @Autowired
    private IApkUpgradeMapRepository apkUpgradeMapRepository;
    @Autowired
    private Cache<String, Serializable> cache;
    @Autowired
    private ICityRepository cityRepository;
    @Autowired
    private IAreaRepository areaRepository;
    @Autowired
    private IDeviceRepository deviceRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApkUpgradeServiceImpl.class);

    private static final String UPGRADE_DATE_REDIS_KEY = "apkupgrade:interface:apiupgradedate:taskid:";
    private static final String UPGRADE_MAX_NUM_REDIS_KEY = "apkupgrade:interface:apkupgrademaxnum:taskid:";

	/**
     *
     * @param softwareCode
     * @param deviceVersionSeq
     * @return
     * @throws com.ysten.local.bss.device.exception.ParamIsEmptyException
     * @throws com.ysten.local.bss.device.exception.DeviceNotFoundException
     * @throws com.ysten.local.bss.device.exception.DeviceGroupNotFoundException
     * @throws com.ysten.local.bss.device.exception.PlatformNotFoundException
     * @throws com.ysten.local.bss.device.exception.UpgradeDeviceInfoNotFoundException
     * @throws com.ysten.local.bss.device.exception.DeviceSoftwareCodeNotFoundException
     */
    public ApkSoftwarePackage getApkUpgradeSoftwareByCondition(String ystenId, String softwareCode,
                                                        Integer deviceVersionSeq) throws ParamIsEmptyException, DeviceNotFoundException,
            DeviceGroupNotFoundException, PlatformNotFoundException, UpgradeDeviceInfoNotFoundException,
            Exception{

        if (StringUtils.isBlank(ystenId) || StringUtils.isBlank(softwareCode)|| null == deviceVersionSeq) {
            throw new ParamIsEmptyException("parameter is invlaid. ystenId or softwareCode or deviceVersionSeq is blank!");
        }
        // 查询设备 APK升级，不判断设备是否存在
//        Device device = this.deviceService.getDeviceByYstenId(ystenId);
//        if (null == device) {
//            throw new DeviceNotFoundException("can not find device info!");
//        }

        // 验证软件号
        ApkSoftwareCode asc = this.apkSoftwareCodeRepository.findApkSoftwareCodeByCode(softwareCode);
        if (null == asc || EnumConstantsInterface.Status.DISABLED.equals(asc.getStatus())) {
            throw new Exception("Apk software code is not exist!");
        }
        LOGGER.debug("Get Apk software code id:  softcode={} softcode id={}", new Object[]{softwareCode, asc.getId()});
        ApkSoftwarePackage apkSoftwarePackage = null;
        
        // 查询设备分配的升级任务
        //用软件号SOFT_CODE_ID、终端版本号DEVICE_VERSION_SEQ查待升级终端表，查询结果按升级软件序号从大到小排序，按照先增量后全量排序
        List<ApkSoftwarePackage> apkSoftwarePackagelist =
                this.apkSoftwarePackageRepository.getSoftwarePackageListBySoftCodeIdAndVersionSeq(asc.getId(), deviceVersionSeq);

        ComparatorDsp comparator=new ComparatorDsp();
        Collections.sort(apkSoftwarePackagelist, comparator);

        if (!CollectionUtils.isEmpty(apkSoftwarePackagelist)) {
            for (ApkSoftwarePackage asp: apkSoftwarePackagelist) {
                //判断设备是否与升级任务绑定
                boolean isExistence = verifyExistence(asp, ystenId);
                if (isExistence == true) {
                    apkSoftwarePackage = asp;
                    break;
                }
            }
        }

        if(null != apkSoftwarePackage){
            long taskId = apkSoftwarePackage.getUpgradeTaskId();
            ApkUpgradeTask apkUpgradeTask = this.apkUpgradeTaskRepository.getById(taskId);
            boolean isupdate = this.handUpgradeTimeAndMaxNum(apkUpgradeTask);
            if(isupdate == false){
                apkSoftwarePackage = null;
            }
        }

        if(null != apkSoftwarePackage){
            apkSoftwarePackage.getSoftCodeId().setCode(softwareCode);
        }

        return apkSoftwarePackage;
    }

    //处理升级最大数量和时间
    private boolean handUpgradeTimeAndMaxNum(ApkUpgradeTask apkUpgradeTask){
        boolean isUpgrade = false;
        String taskId = apkUpgradeTask.getId().toString();
        try {
            Object upgradeDateObj = cache.get(this.UPGRADE_DATE_REDIS_KEY + taskId);
            Object upgradeMaxNumObj = cache.get(this.UPGRADE_MAX_NUM_REDIS_KEY + taskId);

            if(null != upgradeDateObj || null != upgradeMaxNumObj){
                //redis中升级数量
                String upgradeDate = upgradeDateObj.toString();
                String upgradeMaxNum = upgradeMaxNumObj.toString();
                Long maxNum = Long.parseLong(upgradeMaxNum);
                //超时时间 = 当前时间-redis中存的时间
                Long time = new Date().getTime()-Long.parseLong(upgradeDate);
                if(time<=apkUpgradeTask.getTimeInterval()*60000){
                    if(maxNum<apkUpgradeTask.getMaxNum()){
                        cache.put(this.UPGRADE_MAX_NUM_REDIS_KEY + taskId, String.valueOf(maxNum+1));
                        LOGGER.debug("cache object key={},object={}", this.UPGRADE_MAX_NUM_REDIS_KEY + taskId, String.valueOf(maxNum+1));
                        isUpgrade=true;
                    }
                }else{
                    //初始化时间和数量
                    initUpgradeTimeAndMaxNum(taskId);
                    isUpgrade=true;
                }
            }else{
                //为空 初始化时间和数量
                initUpgradeTimeAndMaxNum(taskId);
                isUpgrade=true;
            }
        }catch(Exception e){
            LOGGER.error("cache object error : {}" + e);
        }
        return isUpgrade;
    }

    /**
     * 在redis中初始化设备升级时间和最大数量
     */
    private void initUpgradeTimeAndMaxNum(String taskId){
        try{
            String timeNow = new Date().getTime() + "";
            cache.put(this.UPGRADE_DATE_REDIS_KEY + taskId, timeNow);
            LOGGER.debug("cache object key={},object={}", this.UPGRADE_DATE_REDIS_KEY + taskId, timeNow);
            cache.put(this.UPGRADE_MAX_NUM_REDIS_KEY + taskId, "1");
            LOGGER.debug("cache object key={},object={}", this.UPGRADE_MAX_NUM_REDIS_KEY + taskId, "1");
        }catch (Exception e) {
            LOGGER.error("cache object error : {}" + e);
        }
    }

    public boolean verifyExistence(ApkSoftwarePackage asp, String ystenId) {
        if ( null == asp.getIsAll() || asp.getIsAll() != 1){
            //判断设备是否绑定升级任务
            long upgradeTaskId = asp.getUpgradeTaskId();
            ApkUpgradeMap apkUpgradeMap = null;

            apkUpgradeMap = this.apkUpgradeMapRepository.getApkUpgradeMapYstenId(ystenId, upgradeTaskId);
            //判断设备所在分组是否绑定升级任务
            if(null != apkUpgradeMap){
                return true;
            }else{
                List<DeviceGroup> groupList = deviceService.findGroupByYstenIdType(ystenId, EnumConstantsInterface.DeviceGroupType.APKUPGRADE);
                if (!CollectionUtils.isEmpty(groupList)) {
                    // 根据终端组查询是否绑定升级任务
                    apkUpgradeMap = this.apkUpgradeMapRepository.getApkUpgradeMapByGroupId(groupList.get(0).getId(), upgradeTaskId);
                    if (null != apkUpgradeMap) {
                        return true;
                    }else{
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }else if(asp.getIsAll() == 1){//是全部升级，不做检验
            return true;
        }
        return false;
    }

    public boolean saveApkUpgradeTaskResultLog(String deviceCode, String ystenId, String softwareCode,
                                            Integer deviceVersionSeq, Integer versionSeq, String status, Long duration) {

        //记录升级结果Log表
        ApkUpgradeResultLog apkUpgradeResultLog = packageApkUpgradeResult(deviceCode, ystenId, softwareCode, deviceVersionSeq, versionSeq, status, duration);
        Boolean result = this.deviceService.insertApkUpgradeResultLog(apkUpgradeResultLog);

        return result;
    }
    
    /**
     * 
     * @param deviceCode
     * @param softwareCode
     * @param deviceVersionSeq
     * @param versionSeq
     * @param status
     * @param duration
     * @return
     */
    private ApkUpgradeResultLog packageApkUpgradeResult(String deviceCode, String ystenId, String softwareCode,
            Integer deviceVersionSeq, Integer versionSeq, String status, Long duration) {
        ApkUpgradeResultLog apkUpgradeResultLog = new ApkUpgradeResultLog();
        apkUpgradeResultLog.setDeviceCode(deviceCode);
        apkUpgradeResultLog.setYstenId(ystenId);

        Device device = this.deviceRepository.getDeviceByYstenId(ystenId);
        if(null != device){
            Long cityId = null;
            if(null != device.getCity()){
                cityId = device.getCity().getId();
            }
            String distCode = null;
            if(null != cityId){
                distCode = this.cityRepository.getCityById(cityId).getDistCode();
            }else{
                Long areaId = null;
                if(null != device.getArea()){
                    areaId = device.getArea().getId();
                    if(null != areaId){
                        distCode = this.areaRepository.getAreaById(areaId).getDistCode();
                    }
                }
            }
            apkUpgradeResultLog.setDistCode(distCode);
        }

        apkUpgradeResultLog.setSoftCode(softwareCode);
        apkUpgradeResultLog.setDeviceVersionSeq(deviceVersionSeq);
        apkUpgradeResultLog.setVersionSeq(versionSeq);
        if (UpgradeResultStatus.SUCCESS.getDisplayName().equals(status)) {
            apkUpgradeResultLog.setStatus(UpgradeResultStatus.SUCCESS);
        } else if (UpgradeResultStatus.FAILED.getDisplayName().equals(status)) {
            apkUpgradeResultLog.setStatus(UpgradeResultStatus.FAILED);
        } else {
            apkUpgradeResultLog.setStatus(null);
        }
        apkUpgradeResultLog.setDuration(duration);
        apkUpgradeResultLog.setCreateDate(new Date());
        return apkUpgradeResultLog;
    }

	
}

