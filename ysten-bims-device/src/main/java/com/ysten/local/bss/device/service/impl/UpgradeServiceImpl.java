package com.ysten.local.bss.device.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ysten.area.repository.IAreaRepository;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.cache.Cache;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.IUpgradeTaskRepository;
import com.ysten.local.bss.device.utils.XmlUtils;
import com.ysten.local.bss.util.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.device.exception.DeviceGroupNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.exception.DeviceSoftwareCodeNotFoundException;
import com.ysten.local.bss.device.exception.ParamIsEmptyException;
import com.ysten.local.bss.device.exception.PlatformNotFoundException;
import com.ysten.local.bss.device.exception.UpgradeDeviceInfoNotFoundException;
import com.ysten.local.bss.device.service.IDeviceCustomerAccountMapService;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.device.service.IUpgradeService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.UpgradeResultStatus;
import com.ysten.local.bss.device.repository.IDeviceUpgradeMapRepository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;

@Service
public class UpgradeServiceImpl implements IUpgradeService {
	
	@Autowired
    private IDeviceService deviceService;
	@Autowired
    private IDeviceCustomerAccountMapService deviceCustomerAccountMapService;
    @Autowired
    private IUpgradeTaskRepository upgradeTaskRepository;
    @Autowired
    private IDeviceUpgradeMapRepository upgradeMapRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private Cache<String, Serializable> cache;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private ICityRepository cityRepository;
    @Autowired
    private IAreaRepository areaRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeServiceImpl.class);

    private static final String UPGRADE_DATE_REDIS_KEY = "upgrade:interface:upgradedate:taskid:";
    private static final String UPGRADE_MAX_NUM_REDIS_KEY = "upgrade:interface:upgrademaxnum:taskid:";
    private static final String SERVICE_BIMS_DEVICE_UPDATE_RESULT = "BIMS_DEVICE_UPDATE_RESULT";

    private static final String QUESTION_MARK = "?";
    private static final String EQUAL_SIGN = "=";
    private static final String UTF8 = "utf-8";

	/**
     * 
     * @param softwareCode
     * @param deviceVersionSeq
     * @return
     * @throws ParamIsEmptyException
     * @throws DeviceNotFoundException
     * @throws DeviceGroupNotFoundException
     * @throws PlatformNotFoundException
     * @throws UpgradeDeviceInfoNotFoundException
     * @throws DeviceSoftwareCodeNotFoundException
     */
    public DeviceSoftwarePackage getUpgradeSoftwareByCondition(String ystenId, String softwareCode,
            Integer deviceVersionSeq) throws ParamIsEmptyException, DeviceNotFoundException,
            DeviceGroupNotFoundException, PlatformNotFoundException, UpgradeDeviceInfoNotFoundException,
            DeviceSoftwareCodeNotFoundException{

        if (StringUtils.isBlank(ystenId) || StringUtils.isBlank(softwareCode)|| null == deviceVersionSeq) {
            throw new ParamIsEmptyException("parameter is invlaid. ystenId or softwareCode or deviceVersionSeq is blank!");
        }
        // 查询设备
        Device device = this.deviceService.getDeviceByYstenId(ystenId);
        if (null == device) {
            throw new DeviceNotFoundException("can not find device info!");
        }else{
            LOGGER.debug("设备软件号 终端版本号: ystenId={} softcode={} versionSeq={}", new Object[]{ystenId, device.getSoftCode(), device.getVersionSeq()});
            if(StringUtils.isBlank(device.getSoftCode()) && null == device.getVersionSeq()){
                //调用升级反馈接口，保存软件号和终端当前版本号
                String deviceCode ="";
                try{
                    deviceCode =(null == device.getCode() ? "":device.getCode() ) ;
                    this.saveSoftwareCodeAndVersionByUrl(ystenId, deviceCode, softwareCode, deviceVersionSeq);
                }catch(Exception ex){
                    LOGGER.error("Save device soft code and version error: ystenId={}, deviceCode={}, softwareCode={}, deviceVersionSeq={}",
                            new Object[]{ystenId, deviceCode, softwareCode, deviceVersionSeq}, ex.getCause(), ex);
                }
            }
        }
        // 验证软件号
        DeviceSoftwareCode dsc = this.deviceService.findDeviceSoftwareCodeByCode(softwareCode); //判断在sql语句里面做。
        if (null == dsc || EnumConstantsInterface.Status.DISABLED.equals(dsc.getStatus())) {
            throw new DeviceSoftwareCodeNotFoundException("device software code is not exist!");
        }
        LOGGER.debug("Get software code id:  softcode={} softcode id={}", new Object[]{softwareCode, dsc.getId()});
        DeviceSoftwarePackage deviceSoftwarePackage = null;

        //升级和用户没有关系，只和终端有关系。
//        Customer customer = this.getCustomerByDeviceCode(deviceCode);
//        // 根据用户查询是否已分配升级任务【优先级高于设备】
//        if (customer != null) {                                                            
//            // 根据userID查询升级任务关系                                                                                                               
//            deviceSoftwarePackage = this.findLatestSoftwarePackageByUser(customer.getUserId(), dsc.getId(),deviceVersionSeq);
//                // 用户没有绑定升级包 ， 获取用户绑定的用户组的升级包
//                deviceSoftwarePackage = this.getLatestSoftwarePackageByUserGroup(customer.getUserId(), dsc.getId(),deviceVersionSeq);
//            }
//        }
//        if (null == deviceSoftwarePackage) {
        
        // 查询设备分配的升级任务
        //用软件号SOFT_CODE_ID、终端版本号DEVICE_VERSION_SEQ查待升级终端表，查询结果按升级软件序号从大到小排序，按照先增量后全量排序
        List<DeviceSoftwarePackage> deviceSoftwarePackagelist = this.getSoftwarePackageListBySoftCodeIdAndVersionSeq(dsc.getId(), deviceVersionSeq);

        ComparatorDsp comparator=new ComparatorDsp();
        Collections.sort(deviceSoftwarePackagelist, comparator);

        if (!CollectionUtils.isEmpty(deviceSoftwarePackagelist)) {
            for (DeviceSoftwarePackage dsp: deviceSoftwarePackagelist) {
                //判断设备是否与升级任务绑定
                boolean isExistence = verifyExistence(dsp, ystenId);
                if (isExistence == true) {
                    deviceSoftwarePackage = dsp;
                    break;
                }
            }
        }

        if(null != deviceSoftwarePackage){
            long taskId = deviceSoftwarePackage.getUpgradeTaskId();
            UpgradeTask upgradeTask = this.upgradeTaskRepository.getById(taskId);
            boolean isupdate = this.handUpgradeTimeAndMaxNum(upgradeTask);
            if(isupdate == false){
                deviceSoftwarePackage = null;
            }
        }

        //获取升级包
//          deviceSoftwarePackage = getLatestSoftwarePackageByDevice(device.getYstenId(), dsc.getId(), deviceVersionSeq);
//            // 若终端未找到升级包，则查找终端分组获取升级包
//          if (null == deviceSoftwarePackage) {
//              deviceSoftwarePackage = getLatestSoftwarePackageByDeviceGroup(device.getYstenId(), dsc.getId(),deviceVersionSeq);
//          }

        return deviceSoftwarePackage;
    }

    private String saveSoftwareCodeAndVersionByUrl(String ystenId, String deviceCode, String softwareCode, Integer versionSeq) throws Exception {
        //获得升级反馈接口服务信息
        String url = this.systemConfigService.getSystemConfigByConfigKey("updateResultUrl");
        if(StringUtils.isBlank(url)){
            throw new Exception("bims device update result service url is blank");
        }

        url = url + "stb/sendUpgradeResult.xml"+ QUESTION_MARK + "deviceCode" + EQUAL_SIGN + deviceCode.trim()
                + "&ystenId" + EQUAL_SIGN + ystenId.trim()
                + "&softCodeId" + EQUAL_SIGN + softwareCode.trim()
                + "&deviceVersionSeq" + EQUAL_SIGN + versionSeq.toString()
                + "&versionSeq" + EQUAL_SIGN + versionSeq.toString()
                + "&status" + EQUAL_SIGN + "0000"
                + "&duration" + EQUAL_SIGN + "9999";

        LOGGER.debug("保存设备软件号，软件版本号 url: url={}", new Object[]{url});

        String rsp = getData(url);
        if(StringUtils.isBlank(rsp)){
            throw new Exception("get response failed url: "+ url);
        }

        String result = XmlUtils.parserXMLToNode(rsp, "result", "utf-8");
        LOGGER.info("保存设备软件号，软件版本号 RESULT: "+ result);
        return result;
    }

    private String getData(String url)throws Exception {
        String param = "";
        String rsp = "";
        try{
            HttpUtils.HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
            int returnCode = wrapper.getHttpStatus();
            LOGGER.debug("保存设备软件号，软件版本号 返回码: returnCode={}", new Object[]{returnCode});
            if (returnCode == 200) {
                rsp = wrapper.getResponse();
            }
        }catch(Exception ex){
            LOGGER.debug("发送请求失败 url={}", new Object[]{url});
            throw ex;
        }
        return rsp;
    }


    //处理升级最大数量和时间
    private boolean handUpgradeTimeAndMaxNum(UpgradeTask upgradeTask){
        boolean isUpgrade = false;
        String taskId = upgradeTask.getId().toString();
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
                if(time<=upgradeTask.getTimeInterval()*60000){
                    if(maxNum<upgradeTask.getMaxNum()){
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

    public boolean verifyExistence(DeviceSoftwarePackage dsp, String ystenId) {
        if ( null == dsp.getIsAll() || dsp.getIsAll() != 1){
            //判断设备是否绑定升级任务
            long upgradeTaskId = dsp.getUpgradeTaskId();
            DeviceUpgradeMap deviceUpgradeMap = null;
            deviceUpgradeMap = this.upgradeMapRepository.getDeviceUpgradeMapYstenId(ystenId, upgradeTaskId);
            //判断设备所在分组是否绑定升级任务
            if(null != deviceUpgradeMap){
                return true;
            }else{
                List<DeviceGroup> groupList = deviceService.findGroupByYstenIdType(ystenId, EnumConstantsInterface.DeviceGroupType.UPGRADE);
                if (!CollectionUtils.isEmpty(groupList)) {
                    // 根据终端组查询是否绑定升级任务
                    deviceUpgradeMap = this.upgradeMapRepository.getDeviceUpgradeMapByGroupId(groupList.get(0).getId(), upgradeTaskId);
                    if (null != deviceUpgradeMap) {
                        return true;
                    }else{
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }else if(dsp.getIsAll() == 1){//是全部升级，不做检验
            return true;
        }
        return false;
    }

    public boolean saveUpgradeTaskResultLog(String deviceCode, String ystenId, String softwareCode,
                                            Integer deviceVersionSeq, Integer versionSeq, String status, Long duration) {
        //如果升级成功，将该设备的设备软件号和版本号更新入库
        if(status.trim().equals("0000")){
            Device device = this.deviceService.getDeviceByYstenId(ystenId);
            if(device != null){
                device.setSoftCode(softwareCode);
                device.setVersionSeq(versionSeq);
                this.deviceService.updateDevice(device);
            }
        }

        //记录升级结果Log表
        DeviceUpgradeResultLog deviceUpgradeResultLog = packageDeviceUpgradeResult(deviceCode, ystenId, softwareCode,deviceVersionSeq, versionSeq, status, duration);
        Boolean result = this.deviceService.insertDeviceUpgradeResultLog(deviceUpgradeResultLog);

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
    private DeviceUpgradeResultLog packageDeviceUpgradeResult(String deviceCode, String ystenId, String softwareCode,
            Integer deviceVersionSeq, Integer versionSeq, String status, Long duration) {
        DeviceUpgradeResultLog deviceUpgradeResultLog = new DeviceUpgradeResultLog();
        deviceUpgradeResultLog.setDeviceCode(deviceCode);
        deviceUpgradeResultLog.setYstenId(ystenId);

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
            deviceUpgradeResultLog.setDistCode(distCode);
        }

        deviceUpgradeResultLog.setSoftCode(softwareCode);
        deviceUpgradeResultLog.setDeviceVersionSeq(deviceVersionSeq);
        deviceUpgradeResultLog.setVersionSeq(versionSeq);
        if (UpgradeResultStatus.SUCCESS.getDisplayName().equals(status)) {
            deviceUpgradeResultLog.setStatus(UpgradeResultStatus.SUCCESS);
        } else if (UpgradeResultStatus.FAILED.getDisplayName().equals(status)) {
            deviceUpgradeResultLog.setStatus(UpgradeResultStatus.FAILED);
        } else {
            deviceUpgradeResultLog.setStatus(null);
        }
        deviceUpgradeResultLog.setDuration(duration);
        deviceUpgradeResultLog.setCreateDate(new Date());
        return deviceUpgradeResultLog;
    }
    
    public Customer getCustomerByDeviceCode(String deviceCode) {
        return this.deviceCustomerAccountMapService.getCustomerByYstenId(deviceCode);
    }
    
//    private DeviceSoftwarePackage findLatestSoftwarePackageByUser(String userId, long softwareCodeId,
//            Integer deviceVersionSeq) {
//        return this.deviceService.findLatestSoftwarePackageByUser(userId, softwareCodeId, deviceVersionSeq);
//    }

    private DeviceSoftwarePackage getLatestSoftwarePackageByDevice(String ystenId, long softwareCodeId,
            Integer deviceVersionSeq) {
        return this.deviceService.findLatestSoftwarePackageByDevice(ystenId, softwareCodeId, deviceVersionSeq);
    }
    
    private DeviceSoftwarePackage getLatestSoftwarePackageByDeviceGroup(String ystenId, long softwareCodeId,
            Integer deviceVersionSeq) {
        return this.deviceService.findLatestSoftwarePackageByDeviceGroup(ystenId, softwareCodeId, deviceVersionSeq);
    }

    private List<DeviceSoftwarePackage> getSoftwarePackageListBySoftCodeIdAndVersionSeq(long softwareCodeId,
                                                                         Integer deviceVersionSeq) {
        return this.deviceService.getSoftwarePackageListBySoftCodeIdAndVersionSeq(softwareCodeId, deviceVersionSeq);
    }



//    private DeviceSoftwarePackage getLatestSoftwarePackageByUserGroup(String UserId, long softwareCodeId,
//            Integer deviceVersionSeq) {
//        return this.deviceService.findLatestSoftwarePackageByUserGroup(UserId, softwareCodeId, deviceVersionSeq);
//    }
	
}

class ComparatorDsp implements Comparator{

    public int compare(Object arg0, Object arg1) {
        DeviceSoftwarePackage dsp0=(DeviceSoftwarePackage)arg0;
        DeviceSoftwarePackage dsp1=(DeviceSoftwarePackage)arg1;

        //首先比较versionseq，按照有大到小倒序，如果相同，则比较增量全量,先增量后全量
        int flag=dsp1.getVersionSeq().compareTo(dsp0.getVersionSeq());
        if(flag==0){
            if(dsp1.getPackageType().ordinal() > dsp0.getPackageType().ordinal()){
                return -1;
            }else if(dsp1.getPackageType().ordinal() < dsp0.getPackageType().ordinal()){
                return 1;
            }else{
                return 0;
            }
        }else{
            return flag;
        }
    }

}