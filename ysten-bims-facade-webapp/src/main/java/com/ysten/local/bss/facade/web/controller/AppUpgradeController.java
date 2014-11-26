package com.ysten.local.bss.facade.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.local.bss.device.exception.DeviceGroupNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.exception.PlatformNotFoundException;
import com.ysten.local.bss.facade.utils.ProfileUtils;
import com.ysten.local.bss.logger.domain.InterfaceLog;
import com.ysten.local.bss.logger.service.IInterfaceLogService;
import com.ysten.local.bss.web.service.IAppSoftwarePackageService;
import com.ysten.utils.http.RenderUtils;

@Controller
@RequestMapping("/app")
public class AppUpgradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUpgradeController.class);
    private static final String STR = "get app upgrade info,can not find";
    private InterfaceLog interfaceLog = null;
    @Autowired
    private IInterfaceLogService interfaceLogService;

    @Autowired
    private IAppSoftwarePackageService appUpgradeService;

    /**
     * 应用升级接口
     * 
     * @param deviceCode
     *            设备编号
     * @param appSoftwareCode
     *            应用软件信息号
     * @param versionSeq
     *            版本号
     * @param request
     * @param response
     */
    @RequestMapping("/getAppUpgradeInfor/{deviceCode}/{appSoftwareCode}/{versionSeq}/{sdkVersion}")
    public void getAppUpgradeInfo(@PathVariable String deviceCode, @PathVariable String appSoftwareCode,
            @PathVariable int versionSeq, @PathVariable int sdkVersion, HttpServletRequest request,
            HttpServletResponse response) {
        LOGGER.info("应用升级信息: deviceCode={},appSoftwareCode={},versionSeq={},sdkVersion={}", new Object[] { deviceCode,
                appSoftwareCode, versionSeq, sdkVersion });
        String str = "";
        AppSoftwarePackage appUpgrade = null;
        this.interfaceLog = this.interfaceLogService.getInterfaceLog(request.getServerName(), "deviceCode="
                + deviceCode + "appSoftwareCode=" + appSoftwareCode + "versionSeq=" + versionSeq + "sdkVersion="
                + sdkVersion);
        interfaceLog.setInterfaceName(Constants.GET_APP_UPGRADE_INFO);
        try {
            appUpgrade = this.appUpgradeService.getAppUpgradeInfo(deviceCode, appSoftwareCode, versionSeq, sdkVersion);
        } catch (PlatformNotFoundException e1) {
            LOGGER.error(STR + " software code exception : {}", e1);
            str = "software code can not find,appSoftwareCode：" + appSoftwareCode;
            this.interfaceLogService.saveInterfaceLog(interfaceLog, str);
        } catch (DeviceNotFoundException e) {
            LOGGER.error(STR + " device exception : {}", e);
            str = "device code can not find,deviceCode：" + deviceCode;
            this.interfaceLogService.saveInterfaceLog(interfaceLog, str);
        } catch (DeviceGroupNotFoundException e) {
            LOGGER.error(STR + " device group not found exception : {}", e);
            str = "device code can not find,deviceCode：" + deviceCode;
            this.interfaceLogService.saveInterfaceLog(interfaceLog, str);
        }
        RenderUtils.renderXML(ProfileUtils.getResultXml(appUpgrade, appSoftwareCode, str), response);
        this.interfaceLogService.saveInterfaceLog(interfaceLog,
                ProfileUtils.getResultXml(appUpgrade, appSoftwareCode, str));
    }
}
