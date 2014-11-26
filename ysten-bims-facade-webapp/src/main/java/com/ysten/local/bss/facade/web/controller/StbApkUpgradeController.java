package com.ysten.local.bss.facade.web.controller;

import com.ysten.local.bss.device.domain.ApkSoftwarePackage;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.exception.*;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.service.IApkUpgradeService;
import com.ysten.local.bss.device.service.IUpgradeService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.UpgradeResultStatus;
import com.ysten.utils.http.RenderUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/stb")
public class StbApkUpgradeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StbApkUpgradeController.class);

    @Autowired
    private IApkUpgradeService apkUpgradeService;
    @Autowired
    private IDeviceRepository deviceRepository;

    /**
     * 获取终端升级包信息
     *
     * @param deviceCode
     *            设备编号
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getApkUpgradeInfo")
    public void getUpgradeInfo(
            @RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
            @RequestParam(value = "stbID", defaultValue = "") String stbId,
            @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
            @RequestParam(value = "versionSeq") Integer versionSeq, HttpServletRequest request,
                               HttpServletResponse response) {

        LOGGER.info("GET UPDATE INFO: deviceCode={},stbID={},softwareCode={},deviceVersionSeq={}", new Object[] { deviceCode,stbId,
                softCodeId, versionSeq });

        ApkSoftwarePackage apkSoftwarePackage = null;
        String dataXml = "";
        String dataXmlFailure = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                + "<Service id=\"getApkUpgradeInfor\">"
                + "<state>notupgrade</state>"
                + "</Service>";
        try {
            apkSoftwarePackage = this.apkUpgradeService.getApkUpgradeSoftwareByCondition(stbId, softCodeId, versionSeq);
            dataXml = ApkSoftwarePackage.toXML(apkSoftwarePackage);
            LOGGER.info("GET APK UPDATE INFO Success!: deviceCode={},softCodeId={},versionSeq={},response={}", new Object[] {
                    deviceCode, softCodeId, versionSeq, dataXml });
            RenderUtils.renderXML(dataXml, response);
        } catch (ParamIsEmptyException e) {
            LOGGER.error("ParamIsEmptyException"+ e.getMessage(), e);
            RenderUtils.renderXML(dataXmlFailure, response);
        } catch (DeviceNotFoundException e) {
            LOGGER.error("DeviceNotFoundException: " + e.getMessage(), e);
            RenderUtils.renderXML(dataXmlFailure, response);
        } catch (DeviceGroupNotFoundException e) {
            LOGGER.error("DeviceGroupNotFoundException"+ e.getMessage(), e);
            RenderUtils.renderXML(dataXmlFailure, response);
        } catch (PlatformNotFoundException e) {
            LOGGER.error("PlatformNotFoundException"+ e.getMessage(), e);
            RenderUtils.renderXML(dataXmlFailure, response);
        } catch (UpgradeDeviceInfoNotFoundException e) {
            LOGGER.error("UpgradeDeviceInfoNotFoundException"+ e.getMessage(), e);
            RenderUtils.renderXML(dataXmlFailure, response);
        } catch (Exception e) {
            LOGGER.error("System error: {}"+ e.getMessage(), e);
            RenderUtils.renderXML(dataXmlFailure, response);
        }
    }

    @RequestMapping(value = "/sendApkUpgradeResult")
    public void receiveUpgradeResult(@RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
                                     @RequestParam(value = "stbID", defaultValue = "") String ystenId,
                                     @RequestParam(value = "softCodeId", defaultValue = "") String softwareCode,
                                     @RequestParam(value = "deviceVersionSeq", defaultValue = "") Integer deviceVersionSeq,
                                     @RequestParam(value = "versionSeq", defaultValue = "") Integer versionSeq,
                                     @RequestParam(value = "status", defaultValue = "") String status,
                                     @RequestParam(value = "duration", defaultValue = "") Long duration, HttpServletRequest request,
                                     HttpServletResponse response) {

        LOGGER.info("ReceiveApkUpgradeResult: deviceCode={},ystenId={},softwareCode={},deviceVersionSeq={},versionSeq={},status={},duration={}",
                new Object[] { deviceCode, ystenId, softwareCode, deviceVersionSeq, versionSeq, status, duration });

        String dataXmlFailure = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                + "<Service id=\"sendApkUpgradeResultResponse\">"
                + "<result>false</result>"
                + "</Service>";

        String dataXmlSuccess = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                + "<Service id=\"sendApkUpgradeResultResponse\">"
                + "<result>true</result>"
                + "</Service>";

        try {
            if (!checkParametersForreceiveUpgradeResult(deviceCode, ystenId, softwareCode, deviceVersionSeq, versionSeq, status)) {
                RenderUtils.renderXML(dataXmlFailure, response);
                return;
            }

            //apk升级，不判断设备是否存在
//            Device device = this.deviceRepository.getDeviceByYstenIdNR(ystenId);
//            if (null == device) {
//                throw new DeviceNotFoundException("can not find device info!");
//            }

            this.apkUpgradeService.saveApkUpgradeTaskResultLog(deviceCode, ystenId, softwareCode, deviceVersionSeq, versionSeq, status, duration);

            LOGGER.info("ReceiveApkUpgradeResult: response={}", new Object[] {"true"});
            RenderUtils.renderXML(dataXmlSuccess, response);
        } catch (Exception e) {
            LOGGER.error("ReceiveApkUpgradeResult error:{}", e.getMessage(), e);
            RenderUtils.renderXML(dataXmlFailure, response);
        }

    }

    /**
     *
     * @param deviceCode
     * @param softwareCode
     * @param deviceVersionSeq
     * @param versionSeq
     * @param status
     * @return
     */
    private boolean checkParametersForreceiveUpgradeResult(String deviceCode, String ystenId, String softwareCode,
                                                           Integer deviceVersionSeq, Integer versionSeq, String status) {
        if (StringUtils.isBlank(ystenId) || StringUtils.isBlank(softwareCode) || StringUtils.isBlank(status)
                || null == deviceVersionSeq || null == versionSeq) {
            LOGGER.error("ReceiveApkUpgradeResult: There is null parameter: deviceCode={},ystenId={},softwareCode={},deviceVersionSeq={},"
                    + "versionSeq={},status={}", new Object[] { deviceCode, ystenId, softwareCode,deviceVersionSeq, versionSeq, status});
            return false;
        }
        if (!StringUtils.equals(UpgradeResultStatus.SUCCESS.getDisplayName(),status)
                && !StringUtils.equals(UpgradeResultStatus.FAILED.getDisplayName(),status)) {
            LOGGER.error("ReceiveApkUpgradeResult: status paramter is invalid: status={}",new Object[] {status});
            return false;
        }
        return true;
    }

}
