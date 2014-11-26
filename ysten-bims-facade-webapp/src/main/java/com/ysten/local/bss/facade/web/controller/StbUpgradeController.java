package com.ysten.local.bss.facade.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ysten.local.bss.device.domain.DeviceUpgradeResultLog;
import com.ysten.local.bss.device.exception.DeviceGroupNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.exception.DeviceSoftwareCodeNotFoundException;
import com.ysten.local.bss.device.exception.ParamIsEmptyException;
import com.ysten.local.bss.device.exception.PlatformNotFoundException;
import com.ysten.local.bss.device.exception.UpgradeDeviceInfoNotFoundException;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.device.service.IUpgradeService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.UpgradeResultStatus;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonUtils;

import freemarker.template.Configuration;

@Controller
@RequestMapping("/stb")
public class StbUpgradeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StbUpgradeController.class);

    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IUpgradeService upgradeService;
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
    @RequestMapping(value = "/getUpgradeInfor/{deviceCode}/{ystenId}/{softCodeId}/{versionSeq}")
    public void getUpgradeInfo(
            @PathVariable(value = "deviceCode") String deviceCode,
            @PathVariable(value = "ystenId") String ystenId,
            @PathVariable(value = "softCodeId") String softwareCode,
            @PathVariable(value = "versionSeq") Integer deviceVersionSeq, HttpServletRequest request,
                               HttpServletResponse response) {

        LOGGER.info("GET UPDATE INFO: deviceCode={},ystenId={},softwareCode={},deviceVersionSeq={}", new Object[] { deviceCode,ystenId,
                softwareCode, deviceVersionSeq });

        DeviceSoftwarePackage deviceSoftwarePackage = null;
        String dataXml = "";
        String dataXmlFailure = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                + "<Service id=\"getUpgradeInfor\">"
                + "<state>notupgrade</state>"
                + "</Service>";
        try {
            deviceSoftwarePackage = this.upgradeService.getUpgradeSoftwareByCondition(ystenId, softwareCode, deviceVersionSeq);
            dataXml = DeviceSoftwarePackage.toXML(deviceSoftwarePackage);
            LOGGER.info("GET UPDATE INFO Success!: deviceCode={},softwareCode={},deviceVersionSeq={},response={}", new Object[] {
                    deviceCode, softwareCode, deviceVersionSeq, dataXml });
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
        } catch (DeviceSoftwareCodeNotFoundException e) {
            LOGGER.error("DeviceSoftwareCodeNotFoundException"+ e.getMessage(), e);
            RenderUtils.renderXML(dataXmlFailure, response);
        } catch (Exception e) {
            LOGGER.error("System error: {}"+ e.getMessage(), e);
            RenderUtils.renderXML(dataXmlFailure, response);
        }
    }

    @RequestMapping(value = "/upgradeQuery")
    public void getUpgradeHistoryLog(@RequestParam(value = "deviceCode") String deviceCode,
                                     @PathVariable(value = "ystenId") String ystenId,
                                     HttpServletRequest request,HttpServletResponse response) {
        List<DeviceUpgradeResultLog> list = this.deviceService.findDeviceUpgradeResultLogListByDeviceCodeAndYstenId(deviceCode,ystenId);
        RenderUtils.renderJson(JsonUtils.toJson(list), response);
    }

    @RequestMapping(value = "/sendUpgradeResult.xml")
    public void receiveUpgradeResult(@RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
                                     @RequestParam(value = "ystenId", defaultValue = "") String ystenId,
                                     @RequestParam(value = "softCodeId", defaultValue = "") String softwareCode,
                                     @RequestParam(value = "deviceVersionSeq", defaultValue = "") Integer deviceVersionSeq,
                                     @RequestParam(value = "versionSeq", defaultValue = "") Integer versionSeq,
                                     @RequestParam(value = "status", defaultValue = "") String status,
                                     @RequestParam(value = "duration", defaultValue = "") Long duration, HttpServletRequest request,
                                     HttpServletResponse response) {

        LOGGER.info("ReceiveUpgradeResult: deviceCode={},ystenId={},softwareCode={},deviceVersionSeq={},versionSeq={},status={},duration={}",
                new Object[] { deviceCode, ystenId, softwareCode, deviceVersionSeq, versionSeq, status, duration });

        String dataXmlFailure = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                + "<Service id=\"sendUpgradeResultResponse\">"
                + "<result>false</result>"
                + "</Service>";

        String dataXmlSuccess = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                + "<Service id=\"sendUpgradeResultResponse\">"
                + "<result>true</result>"
                + "</Service>";

        try {
            if (!checkParametersForreceiveUpgradeResult(deviceCode, ystenId, softwareCode, deviceVersionSeq, versionSeq, status)) {
                RenderUtils.renderXML(dataXmlFailure, response);
                return;
            }

            Device device = this.deviceRepository.getDeviceByYstenIdNR(ystenId);
            if (null == device) {
                throw new DeviceNotFoundException("can not find device info!");
            }else{
                if(StringUtils.isBlank(device.getSoftCode()) || null == device.getVersionSeq() ){
                    this.upgradeService.saveUpgradeTaskResultLog(deviceCode, ystenId, softwareCode, deviceVersionSeq, versionSeq, status, duration);
                }
                else {
                    if(!softwareCode.equalsIgnoreCase(device.getSoftCode().trim()) || versionSeq != device.getVersionSeq()){
                        this.upgradeService.saveUpgradeTaskResultLog(deviceCode, ystenId, softwareCode, deviceVersionSeq, versionSeq, status, duration);
                    }
                    else{
                        LOGGER.info("ReceiveUpgradeResult: Not Save Update Result: the device have got the same softwareCode and versionSeq:"
                                +"ystenId={}, softwareCode={},versionSeq={}", new Object[] {ystenId, softwareCode, versionSeq});
                    }
                }
            }
            LOGGER.info("ReceiveUpgradeResult: response={}", new Object[] {"true"});
            RenderUtils.renderXML(dataXmlSuccess, response);
        } catch (Exception e) {
            LOGGER.error("ReceiveUpgradeResult error:{}", e.getMessage(), e);
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
        if (StringUtils.isBlank(deviceCode) || StringUtils.isBlank(ystenId) || StringUtils.isBlank(softwareCode) || StringUtils.isBlank(status)
                || null == deviceVersionSeq || null == versionSeq) {
            LOGGER.error("ReceiveUpgradeResult: There is null parameter: deviceCode={},ystenId={},softwareCode={},deviceVersionSeq={},"
                    + "versionSeq={},status={}", new Object[] { deviceCode, ystenId, softwareCode,deviceVersionSeq, versionSeq, status});
            return false;
        }
        if (!UpgradeResultStatus.SUCCESS.getDisplayName().equals(status)
                && !UpgradeResultStatus.FAILED.getDisplayName().equals(status)) {
            LOGGER.error("ReceiveUpgradeResult: status paramter is invalid: status={}",new Object[] {status});
            return false;
        }
        return true;
    }

}
