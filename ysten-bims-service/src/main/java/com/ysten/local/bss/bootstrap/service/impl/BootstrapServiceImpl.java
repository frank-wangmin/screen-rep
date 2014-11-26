package com.ysten.local.bss.bootstrap.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ysten.area.domain.Area;
import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.local.bss.device.bean.EShAAAStatus;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.exception.ServiceInfoNotFoundException;
import com.ysten.local.bss.bootstrap.service.IBootstrapService;
import com.ysten.local.bss.device.repository.IDeviceAbnormalLogRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.device.service.IServiceInfoService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.service.IPanelPackageService;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.YstenIdUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BootstrapServiceImpl implements IBootstrapService {
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IServiceInfoService serviceInfoService;
    @Autowired
    private IPanelPackageService panelPackageService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private IDeviceAbnormalLogRepository deviceAbnormalLogRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapServiceImpl.class);

    private static final String RESULT_CODE_SUCCESS = "111";
    private static final String BOOTSTRAP_INTERFACE = "bootsrap:interface:ystenId:";
    private static final String UNKNOWN_DISTRICT_DEVICE = "Unknown District Device:";
    private static final String DUP = "DUP:";

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static final String QUESTION_MARK = "?";
    private static final String EQUAL_SIGN = "=";
    private static final String UTF8 = "utf-8";

    @MethodCache(key = BOOTSTRAP_INTERFACE + "#{ystenId}")
    public String getBootstrapByYstenId(@KeyParam("ystenId") String ystenId, String deviceId, String mac, String isReturnYstenId) throws ServiceInfoNotFoundException, Exception {

        // 根据 用户 用户组 终端 终端组 查询PANEL包【优先级顺序依次】
        PanelPackage panelPackage = panelPackageService.getPanelPackageForBootstrapByYstenId(ystenId);
        String screenId = (panelPackage == null ? "" : panelPackage.getId().toString().trim());

        // 查询终端绑定引导初始化（优先级高）
        List<ServiceInfo> infos = this.serviceInfoService.getServiceInfoByYstenId(ystenId);
        if (CollectionUtils.isEmpty(infos)) {
            // 查询终端所属初始化设备分组（优先级低）
            List<DeviceGroup> groupList = deviceService.findGroupByYstenIdType(ystenId, EnumConstantsInterface.DeviceGroupType.BOOTSTRAP);
            if (!CollectionUtils.isEmpty(groupList)) {
                // 根据终端组查询相应的所属服务集合
                infos = serviceInfoService.getServiceInfoByDeviceGroupId(groupList.get(0).getId());
                if (CollectionUtils.isEmpty(infos)) {
                    // 如果服务集合为空，取默认
                    infos = serviceInfoService.getServiceInfoByDefaultService(EnumConstantsInterface.ServiceCollectType.DEFAULT);
                }
            } else {
                // 如果终端没有所属组，查询默认
                infos = serviceInfoService.getServiceInfoByDefaultService(EnumConstantsInterface.ServiceCollectType.DEFAULT);
            }
        }
        if (CollectionUtils.isEmpty(infos)) {
            throw new ServiceInfoNotFoundException("can not find service info");
        }

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.setMac(mac);
        bootstrap.setDeviceId(deviceId);
        //ystenId为空或者为1时返回ystenId
        if(null == isReturnYstenId || isReturnYstenId.equals("1")){
            bootstrap.setYstenId(ystenId);
        }
        bootstrap.setScreenId(screenId);
        bootstrap.setServiceInfos(infos);
        bootstrap.setResultcode(RESULT_CODE_SUCCESS);
        return Bootstrap.toXML(bootstrap);
    }

    public String getDefaultBootstrap(String ystenId, String deviceId, String mac) throws ServiceInfoNotFoundException,Exception {

        // 获取默认的PANEL
        PanelPackage panelPackage = panelPackageService.getDefaultPackage();
        String screenId = (panelPackage == null ? "" : panelPackage.getId().toString().trim());

        // 获取默认服务
        List<ServiceInfo> infos = serviceInfoService.getServiceInfoByDefaultService(EnumConstantsInterface.ServiceCollectType.DEFAULT);
        if (CollectionUtils.isEmpty(infos)) {
            throw new ServiceInfoNotFoundException("can not find service info");
        }

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.setMac(mac);
        bootstrap.setYstenId(ystenId);
        bootstrap.setDeviceId(deviceId);
        bootstrap.setScreenId(screenId);
        bootstrap.setServiceInfos(infos);
        bootstrap.setResultcode(RESULT_CODE_SUCCESS);
        return Bootstrap.toXML(bootstrap);
    }

    public String saveInputDeviceInCenter(String mac, String deviceId, String ystenId, String isReturnYstenId) throws Exception{
        String url ="";
        url = this.systemConfigService.getSystemConfigByConfigKey("bootstrapSaveDeviceUrl");
        if(StringUtils.isBlank(url)){
            throw new Exception("bootstrapSaveDevice url is blank");
        }
        String saveResult = this.saveInputDeviceByUrl(url, mac, deviceId, ystenId, isReturnYstenId);
        return saveResult;
    }

    @Override
    public String updateDeviceInCenter(String mac, String deviceId, String ystenId) throws Exception {
        String url ="";
        url = this.systemConfigService.getSystemConfigByConfigKey("bootstrapUpdateDeviceUrl");
        if(StringUtils.isBlank(url)){
            throw new Exception("bootstrapUpdateDeviceUrl url is blank");
        }
        String updateResult = this.updateInputDeviceByUrl(url, mac, deviceId, ystenId);
        return updateResult;
    }

    private String saveInputDeviceByUrl(String url, String mac, String deviceId, String ystenId, String isReturnYstenId) throws Exception {
        url = url + QUESTION_MARK + "mac" + EQUAL_SIGN + mac.trim() + "&deviceId" + EQUAL_SIGN + deviceId.trim()
                + "&ystenId" + EQUAL_SIGN + ystenId.trim() + "&isReturnYstenId" + EQUAL_SIGN + isReturnYstenId;
        LOGGER.info("保存设备信息 url: url={}", new Object[]{url});
        String rsp = getData(url);
        if(StringUtils.isBlank(rsp)){
            throw new Exception("get response failed url: "+ url);
        }

        String saveResult = gson.fromJson(rsp, String.class);
        LOGGER.info("save input device by url RESULT: "+ saveResult);
        return saveResult;
    }

    private String updateInputDeviceByUrl(String url, String mac, String deviceId, String ystenId) throws Exception {
        url = url + QUESTION_MARK + "mac" + EQUAL_SIGN + mac.trim() + "&deviceId" + EQUAL_SIGN + deviceId.trim()
                + "&ystenId" + EQUAL_SIGN + ystenId.trim();
        LOGGER.info("更新设备信息 url: url={}", new Object[]{url});
        String rsp = getData(url);
        if(StringUtils.isBlank(rsp)){
            throw new Exception("get response failed url: "+ url);
        }

        String saveResult = gson.fromJson(rsp, String.class);
        LOGGER.info("update device by url RESULT: "+ saveResult);
        return saveResult;
    }

    private String getData(String url)throws Exception {
        String param = "";
        String rsp = "";
        try{
            HttpUtils.HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
            int returnCode = wrapper.getHttpStatus();
            LOGGER.info("HTTP请求 返回码: returnCode={}", new Object[]{returnCode});
            if (returnCode == 200) {
                rsp = wrapper.getResponse();
            }
        }catch(Exception ex){
            LOGGER.error("发送请求失败 url={}", new Object[]{url});
            throw ex;
        }
        return rsp;
    }

    public String updateInputDevice(String mac, String deviceId, String ystenId) throws Exception {
        Device device = new Device();
        if(StringUtils.isBlank(ystenId) || ystenId.equals("00000000000000000000000000000000")){
            device = this.deviceService.getDeviceByMac(mac);
        } else{
            device = this.deviceService.getDeviceByYstenId(ystenId);
        }

        DeviceAbnormalLog deviceAbnormalLog = new DeviceAbnormalLog();
        deviceAbnormalLog.setDeviceCode(deviceId);
        deviceAbnormalLog.setMac(mac);
        deviceAbnormalLog.setYstenId(ystenId);
        deviceAbnormalLog.setCreateDate(new Date());
        deviceAbnormalLog.setServiceName("BootstrapService");
        deviceAbnormalLog.setStatus("0"); //未处理
        deviceAbnormalLog.setOperation("UPDATE");
        deviceAbnormalLog.setOriginalDeviceId(device.getId());

        //找到设备，更新广电号
        if (null != device) {
            if( StringUtils.isBlank(device.getCode()) ){
                if(StringUtils.isNotBlank(deviceId) && !deviceId.trim().equals("000000000000000")){
                    try{
                        //根据广电号获取设备
                        Device deviceinput = this.deviceService.getDeviceByDeviceCode(deviceId);

                        String description = "";
                        //该设备编号在bims中已有设备存在
                        if(null != deviceinput){
                            deviceAbnormalLog.setOriginalDataStatus("2"); //不更新广电号，更新描述信息
                            description = DUP + deviceId.trim();
                            deviceRepository.updateDeviceCode("", mac, ystenId, description);
                            deviceAbnormalLog.setMessage(description);
                        }else{
                            deviceAbnormalLog.setOriginalDataStatus("1"); //更新广电号
                            description = UNKNOWN_DISTRICT_DEVICE + deviceId.trim();
                            deviceRepository.updateDeviceCode(deviceId, mac, ystenId, description);
                            deviceAbnormalLog.setMessage(description);
                        }

                        deviceAbnormalLogRepository.saveDeviceAbnormalLog(deviceAbnormalLog);
                        LOGGER.debug("设备编号为 :" + deviceId + "mac地址为 ：" + mac + "ystenId为" + ystenId + "的设备, 更新成功!");
                        return "1"; //正常更新
                    }catch(Exception ex){
                        deviceAbnormalLog.setOriginalDataStatus("0");
                        deviceAbnormalLogRepository.saveDeviceAbnormalLog(deviceAbnormalLog);
                        LOGGER.error("设备编号为 :" + device.getCode() + "mac地址为 ：" + mac + "ystenId为" + ystenId + "的设备, 更新失败!");
                        return "0"; //更新失败
                    }
                }else{
                    return "3";//终端传入的设备广电号为空，不更新
                }
            }else{
                return "4";//设备广电号不为空，不更新
            }
        }else{
            LOGGER.info("mac地址为 ：" + mac + "ystenId为" + ystenId + "的设备不存在，不更新");
            return "5"; //设备不存在，不更新
        }
    }

    public String saveInputDevice(String mac, String deviceId, String ystenId, String isReturnYstenId) throws ParseException,Exception {
        Device device = new Device();
        if(StringUtils.isBlank(ystenId) || ystenId.equals("00000000000000000000000000000000")){
            device = this.deviceService.getDeviceByMac(mac);
        } else{
            device = this.deviceService.getDeviceByYstenId(ystenId);
        }

        String description = "";
        //根据ystenId和mac地址都找不到设备，则根据mac把设备信息存入数据库
        if (null == device) {
            Device deviceinput = new Device();

            //广电号在数据库中是唯一的，而广电号为000000000000000的请求有多个，对于000000000000000的广电号，不保存
            if(StringUtils.isNotBlank(deviceId) && !deviceId.trim().equals("000000000000000")){
                //根据广电号获取该设备
                device = this.deviceService.getDeviceByDeviceCode(deviceId);
                if(null == device){
                    deviceinput.setCode(deviceId.trim());
                    description = UNKNOWN_DISTRICT_DEVICE + deviceId.trim();
                    deviceinput.setDescription(description);
                }else{
                    description = DUP + deviceId.trim();
                    deviceinput.setDescription(description);
                }
            }else{
                description = UNKNOWN_DISTRICT_DEVICE + deviceId.trim();
                deviceinput.setDescription(UNKNOWN_DISTRICT_DEVICE + deviceId);
            }

            if(StringUtils.isNotBlank(mac)){
                deviceinput.setMac(mac.trim());
            }

            if(StringUtils.isNotBlank(ystenId)){
                deviceinput.setYstenId(ystenId.trim());
            }

            deviceinput.setIsReturnYstenId(null == isReturnYstenId? null : isReturnYstenId.trim());

            deviceinput.setState(Device.State.NONACTIVATED);
            deviceinput.setBindType(Device.BindType.UNBIND);
            deviceinput.setIsSync(Device.SyncType.WAITSYNC);
            deviceinput.setIsLock(LockType.UNLOCKED);

            DeviceVendor deviceVendor = new DeviceVendor();
            deviceVendor.setId(70L);
            deviceinput.setDeviceVendor(deviceVendor);
            DeviceType deviceType = new DeviceType();
            deviceType.setId(1L);
            deviceinput.setDeviceType(deviceType);
            SpDefine spDefine = new SpDefine();
            spDefine.setId(1);
            deviceinput.setSpDefine(spDefine);

            deviceinput.setPrepareOpen(EShAAAStatus.EPrepareOpen.NOT_OPEN);
            String isCenter = this.systemConfigService.getSystemConfigByConfigKey("isCenter");
//                    if (isCenter.equals("true")) {
//                        device.setDistributeState(DistributeState.UNDISTRIBUTE);
//                    } else {
//                        device.setDistributeState(DistributeState.DISTRIBUTE);
//                    }
            deviceinput.setCreateDate(new Date());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            deviceinput.setExpireDate(sdf.parse("2099-12-31 23:59:59"));

            String unKnowDistCode = "000000"; //未知区划码

            Area area = new Area();
            area.setId(9999L); //未知区域
            City city = new City();
            city.setDistCode(unKnowDistCode);
            city.setDistCode(unKnowDistCode);
            deviceinput.setArea(area);
            deviceinput.setCity(city);
            deviceinput.setDistrictCode(unKnowDistCode);

            DeviceAbnormalLog deviceAbnormalLog = new DeviceAbnormalLog();
            deviceAbnormalLog.setDeviceCode(deviceId);
            deviceAbnormalLog.setMac(mac);
            deviceAbnormalLog.setYstenId(ystenId);
            deviceAbnormalLog.setCreateDate(new Date());
            deviceAbnormalLog.setServiceName("BootstrapService");
            deviceAbnormalLog.setStatus("0"); //未处理
            deviceAbnormalLog.setOperation("ADD");
            deviceAbnormalLog.setMessage(description);

            try{
                deviceService.saveDevice(deviceinput);

                deviceAbnormalLog.setOriginalDeviceId(deviceinput.getId());
                deviceAbnormalLog.setOriginalDataStatus("1");
                deviceAbnormalLogRepository.saveDeviceAbnormalLog(deviceAbnormalLog);
                LOGGER.debug("设备编号为 :" + deviceId + "mac地址为 ：" + mac + "ystenId为" + ystenId + "的设备, 保存成功!");
                return "1"; //正常保存
            }catch(Exception ex){
                deviceAbnormalLog.setOriginalDataStatus("0");
                deviceAbnormalLogRepository.saveDeviceAbnormalLog(deviceAbnormalLog);
                LOGGER.error("设备编号为 :" + deviceId + "mac地址为 ：" + mac + "ystenId为" + ystenId + "的设备, 保存失败!");
                return "0"; //保存失败
//                throw new Exception("设备编号为 :" + deviceId + "mac地址为 ："+ mac + "的设备, 序列号或mac重复!",ex);
            }
        }else{
            LOGGER.info("设备编号为 :" + deviceId + "mac地址为 ：" + mac + "ystenId为" + ystenId + "的设备已存在，不再保存");
            return "2"; //设备存在，不用保存
        }
    }

}

//    private String getPanelPackageIdByUrl(String url, String ystenId) throws Exception {
//        LOGGER.debug("获取面板包id url: url={},ystenId={}", new Object[]{url, ystenId});
//        String rsp = getData(url + QUESTION_MARK + "ystenId" + EQUAL_SIGN + ystenId.trim());
//        if(StringUtils.isBlank(rsp)){
//            throw new Exception("get response failed url: "+ url + "ystenId +" +ystenId);
//        }
//        String screenId = gson.fromJson(rsp, String.class);
//        LOGGER.info("get panel package screenid: "+ screenId);
//        return screenId;
//    }
//


/*	public boolean save(final String sKey,final Object oValue) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
						connection.set(redisTemplate.getStringSerializer().serialize(sKey), SerializeUtil.serialize(oValue));
					return true;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Object getRedis(final String sKey) {
		try {
			return redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] key= redisTemplate.getStringSerializer().serialize(sKey);
					if (connection.exists(key)) {
						byte[] value = connection.get(key);
		                Object sValue = redisTemplate.getValueSerializer().deserialize(value);
						return sValue;
					}
					return null;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/

