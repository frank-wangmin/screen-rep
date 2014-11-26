package com.ysten.local.bss.facade.web.controller;

import com.ysten.area.domain.Area;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.device.bean.EShAAAStatus;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.exception.ParamIsEmptyException;
import com.ysten.local.bss.bootstrap.service.IBootstrapService;
import com.ysten.local.bss.device.exception.ServiceInfoNotFoundException;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.utils.code.AESUtil;
import com.ysten.utils.code.DecryptException;
import com.ysten.utils.http.RenderUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ysten.local.bss.util.YstenIdUtils;
import com.ysten.local.bss.device.domain.Device.DistributeState;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/stb")
public class StbBootstrapController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StbBootstrapController.class);
    @Autowired
    private IBootstrapService bootstrapService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private IDeviceService deviceService;

    private static final String SYS_INTERVAL_ERROR = "900";
    private static final String PARAM_INVALID_ERROR = "901";
    private static final String PARAM_DECRYPT_ERROR = "902";
    private static final String DEVICE_NOTFOUND_ERROR = "903";
    private static final String SERVICEINFO_NOTFOUND_ERROR = "904";

    //加密传输秘钥
    private static final String KEY = "36b9c7e8695468dc";

    /**
     * 终端初始化引导接口
     *
     * @param deviceId
     * @param mac
     * @param request
     * @param response
     * @throws DecryptException
     */
    @RequestMapping(value = "/bootstrap.xml")
    public void deviceBootstrap(
            @RequestParam(value = "deviceId", defaultValue = "") String deviceId,
            @RequestParam(value = "mac", defaultValue = "") String mac,
            @RequestParam(value = "ystenId", defaultValue = "") String ystenId,
            @RequestParam(value = "k", defaultValue = "") String k,
            HttpServletRequest request, HttpServletResponse response) {

        mac = mac.trim();
        deviceId = deviceId.trim();
        ystenId = ystenId.trim();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.setMac(mac);
        bootstrap.setDeviceId(deviceId);
        String isReturnYstenId = "0";
        String bootstrapXml = "";
        try {
            LOGGER.info("获取初始化引导信息: deviceCode={},mac={},ystenId={},k={}", new Object[]{deviceId, mac, ystenId, k});
            // 入参检查
            //mac和ystenId至少一个不为空
            if (StringUtils.isBlank(mac) && StringUtils.isBlank(ystenId)) {
                throw new ParamIsEmptyException("parameter is invlaid. Both mac and ystenId are blank");
            }
            // 加密传输时做校验
            String bootstrapencrypt = this.systemConfigService.getSystemConfigByConfigKey("bootstrapencrypt");
            LOGGER.info("加密传输标示状态: bootstrapencrypt={}", new Object[]{bootstrapencrypt});
            if (StringUtils.isNotBlank(bootstrapencrypt) && "true".equalsIgnoreCase(bootstrapencrypt)) {
                String key = AESUtil.decrypt(k, KEY);
                String[] data = StringUtils.split(key, '/');
                if (null == data || data.length != 3 || !data[0].equals(deviceId) || !data[1].equals(ystenId) || !data[2].equals(mac)) {
                    throw new DecryptException("decrypted failed!");
                }
            }
            //ystenId为空或全0
            if (StringUtils.isBlank(ystenId) || ystenId.equals("00000000000000000000000000000000")) {
                Device device = this.deviceService.getDeviceByMac(mac);
                //根据mac地址可以找到设备
                if(null != device){
                    ystenId = device.getYstenId();
                    isReturnYstenId = device.getIsReturnYstenId();
                    if (StringUtils.isBlank(ystenId)){
                        throw new ParamIsEmptyException("ystenId is empty！mac is "+ mac);
                    }else{
                        if( StringUtils.isBlank(device.getCode()) ){
                            if(StringUtils.isNotBlank(deviceId) && !deviceId.equals("000000000000000")){
                                bootstrapService.updateDeviceInCenter(mac, deviceId, ystenId);
                                //根据传入广电号获取设备
                                Device deviceinput = this.deviceService.getDeviceByDeviceCode(deviceId);
                                if(null != deviceinput){
                                    //设备存在，返回该广电号对应的ystenId
                                    ystenId = deviceinput.getYstenId();
                                    isReturnYstenId = deviceinput.getIsReturnYstenId();
                                }
                            }
                        }
                        bootstrapXml = bootstrapService.getBootstrapByYstenId(ystenId, deviceId, mac, isReturnYstenId);
                    }
                } //根据mac地址找不到设备
                else {
                    //根据mac生成ystenId,并把设备信息存入数据库
                    ystenId = YstenIdUtils.createYstenId("0000", "00", "0000", mac.replaceAll(":", "").trim());
                    isReturnYstenId = "0";
                    bootstrapService.saveInputDeviceInCenter(mac, deviceId, ystenId, isReturnYstenId);
                    //保存ystenId，获取默认启动信息，不返回ystenId
                    bootstrapXml = bootstrapService.getDefaultBootstrap("", deviceId, mac);
                }
            }
            //ystenId不为空
            else{
                Device device = this.deviceService.getDeviceByYstenId(ystenId);
                if (null == device) {
                    isReturnYstenId = "0";
                    //找不到设备，则把设备信息存入数据库
                    bootstrapService.saveInputDeviceInCenter(mac, deviceId, ystenId, isReturnYstenId);
                    //获取默认启动信息
                    bootstrapXml = bootstrapService.getDefaultBootstrap("", deviceId, mac);
                }else{
                    isReturnYstenId = device.getIsReturnYstenId();
                    bootstrapXml = bootstrapService.getBootstrapByYstenId(ystenId, deviceId, mac, isReturnYstenId);
                }
            }

            LOGGER.info("getBootstrap Success: deviceCode={},mac={},ystenId={},k={}, bootstrapXml={}", new Object[]{deviceId, mac, ystenId, k, bootstrapXml});
            RenderUtils.renderXML(bootstrapXml, response);
        }catch(DecryptException dpte){
            LOGGER.error("getBootstrap error:{}", dpte.getMessage(), dpte);
            bootstrap.setResultcode(PARAM_DECRYPT_ERROR);
            RenderUtils.renderXML(Bootstrap.toXML(bootstrap), response);
        }catch(ParamIsEmptyException piee){
            LOGGER.error("getBootstrap error:{}", piee.getMessage(), piee);
            bootstrap.setResultcode(PARAM_INVALID_ERROR);
            RenderUtils.renderXML(Bootstrap.toXML(bootstrap), response);
        }catch(DeviceNotFoundException dnfe){
            LOGGER.error("getBootstrap error:{}", dnfe.getMessage(), dnfe);
            bootstrap.setResultcode(DEVICE_NOTFOUND_ERROR);
            RenderUtils.renderXML(Bootstrap.toXML(bootstrap), response);
        }catch(ServiceInfoNotFoundException snfe){
            LOGGER.error("getBootstrap error:{}", snfe.getMessage(), snfe);
            bootstrap.setResultcode(SERVICEINFO_NOTFOUND_ERROR);
            RenderUtils.renderXML(Bootstrap.toXML(bootstrap), response);
        }catch (Exception e) {
            LOGGER.error("getBootstrap error:{}", e.getMessage(), e);
            bootstrap.setResultcode(SYS_INTERVAL_ERROR);
            RenderUtils.renderXML(Bootstrap.toXML(bootstrap), response);
        }
    }

    @RequestMapping(value = "/bootstrapSaveDevice.json")
    public void saveDeviceInfo(
            @RequestParam(value = "deviceId", defaultValue = "") String deviceId,
            @RequestParam(value = "mac", defaultValue = "") String mac,
            @RequestParam(value = "ystenId", defaultValue = "") String ystenId,
            @RequestParam(value = "isReturnYstenId", defaultValue = "0") String isReturnYstenId,
            HttpServletRequest request, HttpServletResponse response) {
        String saveResult = "-1";
        try {
            saveResult = bootstrapService.saveInputDevice(mac, deviceId, ystenId, isReturnYstenId);

            LOGGER.info("保存设备信息: deviceCode={},mac={},ystenId={},saveResult={}", new Object[]{deviceId, mac, ystenId, saveResult});
            RenderUtils.renderJson(saveResult, response);
        }catch (Exception e) {
            saveResult = "-1";
            LOGGER.error("saveDeviceInfo error:{}", e.getMessage(), e);
            RenderUtils.renderJson(saveResult, response);
        }
    }

    @RequestMapping(value = "/bootstrapUpdateDevice.json")
    public void updateDeviceInfo(
            @RequestParam(value = "deviceId", defaultValue = "") String deviceId,
            @RequestParam(value = "mac", defaultValue = "") String mac,
            @RequestParam(value = "ystenId", defaultValue = "") String ystenId,
            HttpServletRequest request, HttpServletResponse response) {
        String updateResult = "-1";
        try {
            updateResult = bootstrapService.updateInputDevice(mac, deviceId, ystenId);
            LOGGER.info("修改设备广电号: deviceCode={},mac={},ystenId={},saveResult={}", new Object[]{deviceId, mac, ystenId, updateResult});
            RenderUtils.renderJson(updateResult, response);
        }catch (Exception e) {
            updateResult = "-1";
            LOGGER.error("updateDeviceInfo error:{}", e.getMessage(), e);
            RenderUtils.renderJson(updateResult, response);
        }
    }

}