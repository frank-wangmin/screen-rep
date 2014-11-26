package com.ysten.local.bss.device.web.controller;

import com.ibm.icu.text.SimpleDateFormat;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.common.web.controller.ExportController;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.DeviceType.State;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.util.bean.Required;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IDeviceWebService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
public class DeviceController extends ExportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);
    @Autowired
    private ILoggerWebService loggerWebService;
    private static final String TEMPLATE_FILE_NAME = "Device_Import_Template.xls";
    @Autowired
    private IDeviceWebService deviceWebService;

    @RequestMapping(value = "/to_device_list")
    public String toDeviceList(ModelMap model) {
        return "/device/device_list";
    }

    @RequestMapping(value = "/to_device_ip_list")
    public String toDeviceIpList(ModelMap model) {
        return "/device/device_ip_list";
    }

    @RequestMapping(value = "/to_device_vendor_list")
    public String toDeviceVendorList(ModelMap model) {
        return "/device/device_vendor_list";
    }

    @RequestMapping(value = "/to_device_type_list")
    public String toDeviceTypeList(ModelMap model) {
        return "/device/device_type_list";
    }

    @RequestMapping(value = "/to_upgrade_list")
    public String toUpgradeList(ModelMap model) {
        return "/device/upgrade_list";
    }

    @RequestMapping(value = "/to_software_list")
    public String toSoftwareList(ModelMap model) {
        return "/device/software_list";
    }

    @RequestMapping(value = "/get_device_groups.json")
    public void getUserGroupsByUserId(@RequestParam(value = "code", defaultValue = "") String code,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        Map<String, Object> json = deviceWebService.findDeviceGroupByYstenIdAndArea(code);
//        if (StringUtils.isNotBlank(json)) {
//            RenderUtils.renderText(json, response);
//        }
        RenderUtils.renderJson(EnumDisplayUtil.toJson(json), response);
    }

    @RequestMapping(value = "/get_device_relate_business.json")
    public void getDeviceRelateBusinessByYstenId(@RequestParam(value = "ystenId", defaultValue = "") String ystenId,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

        Map<String, Object> json = deviceWebService.findDeviceRelateBusinessByYstenIdOrGroupId(ystenId, null);

        RenderUtils.renderJson(EnumDisplayUtil.toJson(json), response);
    }


    // 设备状态
    @RequestMapping(value = "/device_state.json")
    public void getDeviceState(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
                               HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("0", "所有状态"));
        }
        for (Device.State state : Device.State.values()) {
            tv.add(new TextValue(state.toString(), state.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    // 设备型号终端类型
    @RequestMapping(value = "/device_terminal_type.json")
    public void getTerminalType(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        for (TerminalType terminalType : TerminalType.values()) {
            tv.add(new TextValue(terminalType.toString(), terminalType.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    // 设备厂商
    @RequestMapping(value = "/device_vendor.json")
    public void getDeviceVendor(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
                                HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<DeviceVendor> listDeviceVendor = this.deviceWebService.findDeviceVendors(DeviceVendor.State.USABLE);
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("0", "所有设备厂商"));
        }
        for (DeviceVendor deviceVendor : listDeviceVendor) {
            tv.add(new TextValue(deviceVendor.getId().toString(), deviceVendor.getName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    // 设备分发状态
    @RequestMapping(value = "/device_distribute_state.json")
    public void getDeviceDistributeState(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
                                         HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("0", "所有状态"));
        }
        for (DistributeState dt : Device.DistributeState.values()) {
            tv.add(new TextValue(dt.name(), dt.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    // 设备批次号
    @RequestMapping(value = "/device_product_no.json")
    public void getDeviceProductNo(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
                                   @RequestParam(value = "area", defaultValue = "") String area,
                                   @RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response) {
        try {
            List<TextValue> tv = new ArrayList<TextValue>();
            DistributeState state;
            if (StringUtils.equalsIgnoreCase(type, "pickup")) {
                state = DistributeState.DISTRIBUTE;
            } else if (StringUtils.equalsIgnoreCase(type, "distribute")) {
                state = DistributeState.UNDISTRIBUTE;
            } else {
                state = null;
            }
            List<String> productNos = this.deviceWebService.getAllProductNoByArea(area, state);
            if (par != null && !par.isEmpty()) {
                tv.add(new TextValue("0", "所有生产批次号"));
            }
            for (String productNo : productNos) {
                tv.add(new TextValue(productNo, productNo));
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/device_update", method = RequestMethod.POST)
    public void updateDevice(@RequestParam(value = "id", defaultValue = "") String deviceId,
                             @RequestParam(value = "mac", defaultValue = "") String mac,
                             @RequestParam(value = "deviceVendorId", defaultValue = "") String deviceVendorId,
                             @RequestParam(value = "deviceTypeId", defaultValue = "") String deviceTypeId,
                             @RequestParam(value = "spCode", defaultValue = "") String spCode,
                             @RequestParam(value = "expireDate", defaultValue = "") String expireDate,
                             @RequestParam(value = "area", defaultValue = "") String area,
                             @RequestParam(value = "city", defaultValue = "") String city,
                             @RequestParam(value = "deviceState", defaultValue = "") Device.State deviceState,
                             @RequestParam(value = "sno", defaultValue = "") String sno, HttpServletResponse response,
                             HttpServletRequest request) throws ParseException {

        String str = this.deviceWebService.updateDevice(deviceId, deviceState, mac, deviceVendorId, deviceTypeId,
                expireDate, area, city, spCode, sno);
        String description = ("success".equals(str)) ? "修改终端信息成功" : "修改终端信息失败";
        description += "修改后的终端MAC：" + mac + "\n" + "终端类型为：" + deviceTypeId + "\n" + "运营商编码为：" + spCode + "\n"
                + "到期时间为：" + expireDate + "\n" + "所属地市为：" + city + "\n" + "设备状态：" + deviceState.name();
        RenderUtils.renderText(str, response);
        this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.MODIFY, deviceId, description,
                request);
        // List<String> nameList = new ArrayList<String>();
        // nameList.add(deviceId);
        // boolean bool = false;
        // if("success".equals(str)){
        // bool = true;
        // }else {
        // bool = false;
        // }
        // String strDesc =
        // AuditLogUtils.returnLoggerDescription(Constants.DEVICE_UPDATE,nameList,bool);
        // this.auditLogService.saveAuditLog(new
        // AuditLog(Constants.UPDATE,deviceId,
        // Constants.DEVICE_MANAGER,new
        // Date(),ControllerUtil.getLoginOperator(request).getLoginName(),
        // request.getRemoteHost(),strDesc));
    }

    @RequestMapping(value = "/device_lock.json")
    public void lockDevice(@RequestParam(Constants.IDS) String ids, @RequestParam("par") String par,
                           HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            String operationType = ("LOCK".equals(par)) ? "加锁" : "解锁";
            boolean bool = this.deviceWebService.lockDevice(idsList, par);
            String description = (bool == true) ? operationType + "成功" : operationType + "失败";
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, operationType, ids, description,
                    request);
        }
    }

    @RequestMapping(value = "/device_distribute.json")
    public void distributeDevice(@RequestParam("productNo") String productNo, @RequestParam("par") String par,
                                 HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean bool = this.deviceWebService.distributeDevice(productNo);
            String description = (bool == true) ? "下发成功" : "下发失败";
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.DISTRIBUTE_DEVICE, "下发批次号为：" + productNo, description, request);
        } catch (Exception e) {
            LOGGER.error("Distribute device exception{}", e);
            RenderUtils.renderText(ControllerUtil.returnString(false), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.DISTRIBUTE_DEVICE, "下发批次号为：" + productNo,
                    "下发异常！", request);
        }
    }

    @RequestMapping(value = "/devices_rend.json")
    public void rendDevicesInfo(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                HttpServletResponse response) {
        boolean bool = false;
        List<Device> devices = new ArrayList<Device>();
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                for (int i = 0; i < idsList.size(); i++) {
                    Device device = this.deviceWebService.getDeviceById(idsList.get(i));
                    devices.add(device);
                }
                bool = this.deviceWebService.rendSoftCode(devices);
            }
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            String description = (bool == true) ? "下发成功" : "下发失败";
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.DISTRIBUTE_DEVICE, ids,
                    description, request);
        } catch (Exception e) {
            LOGGER.error("Distribute device exception{}", e);
            RenderUtils.renderText(ControllerUtil.returnString(false), response);
            e.printStackTrace();
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.DISTRIBUTE_DEVICE, ids,
                    "下发异常！", request);
        }
    }

    @RequestMapping(value = "/device_pickup.json")
    public void pickupDevice(@RequestParam("productNo") String productNo, @RequestParam("par") String par,
                             HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean bool = this.deviceWebService.pickupDevice(productNo);
            String description = (bool == true) ? "领用成功" : "领用失败";
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "终端领用", "领用批次号为：" + productNo, description, request);
        } catch (Exception e) {
            LOGGER.error("Distribute device exception{}", e);
            RenderUtils.renderText(ControllerUtil.returnString(false), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "终端领用", "领用批次号为：" + productNo, "领用异常！", request);
        }
    }

    @RequestMapping(value = "/device_sync")
    public void syncDevice(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean bool = this.deviceWebService.syncDevice();
            int faild = this.deviceWebService.getCountByIsSync();
            String description = (bool == true) ? "终端同步成功" : "终端同步失败，有" + faild + "条数据同步未成功！";
            RenderUtils.renderText(description, response);
            List<Device> devices = this.deviceWebService.findNeedSyncDevices(null);
            StringBuilder sb = new StringBuilder("");
            if (!CollectionUtils.isEmpty(devices)) {
                for (Device device : devices) {
                    sb.append(device.getId()).append(",");
                }
            }
            this.loggerWebService
                    .saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "终端同步", sb.substring(0, sb.length() - 1).toString(), description, request);
        } catch (Exception e) {
            LOGGER.error("Distribute device exception{}", e);
            RenderUtils.renderText(ControllerUtil.returnString(false), response);
        }
    }

    @RequestMapping(value = "/device_business_map_delete")
    public void deleteUserGroupBusinessMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                           HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.deviceWebService.deleteDeviceBusiness(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "解绑业务", ids,
                        (bool == true) ? "解绑终端与业务关系成功!" : "解绑终端与业务关系失败!", request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText(ControllerUtil.returnString(false), response);
            LOGGER.error("Delete Unbind Device Business Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "解绑业务", ids,
                    "解绑终端与业务关系异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/device_deviceGroup_map_delete")
    public void deleteDeviceGroupMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                     HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.deviceWebService.deleteDeviceGroupMapByCode(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "解绑设备分组", ids,
                        (bool == true) ? "解绑设备分组成功!" : "解绑设备分组失败!", request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText(ControllerUtil.returnString(false), response);
            LOGGER.error("Delete Unbind Group Device Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "解绑设备分组", ids,
                    "解绑设备分组异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/devices_bind_deviceGroup.json")
    public void bindUserGroup(@RequestParam(Constants.IDS) String ids, @RequestParam("boot") String boot,
                              @RequestParam("animation") String animation, @RequestParam("upgrade") String upgrade,
                              @RequestParam("appUpgrade") String appUpgrade, @RequestParam("panel") String panel,
                              @RequestParam("notice") String notice, @RequestParam("background") String background,
                              @RequestParam("common") String common, HttpServletRequest request, HttpServletResponse response) {
        String message = Constants.SUCCESS;
        if (StringUtils.isBlank(boot) && StringUtils.isBlank(background) && StringUtils.isBlank(animation) && StringUtils.isBlank(upgrade) && StringUtils.isBlank(panel) && StringUtils.isBlank(notice)) {
            message = "至少选择一种类型的设备分组做绑定，请确认！";
        } else {
            List<Long> idsList = StringUtil.splitToLong(ids);
            this.deviceWebService.deleteDeviceGroupMapByCode(idsList);
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(boot)) {
                message = this.bindByGroupType(ids, boot);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(animation)) {
                message = this.bindByGroupType(ids, animation);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(upgrade)) {
                message = this.bindByGroupType(ids, upgrade);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(appUpgrade)) {
                message = this.bindByGroupType(ids, appUpgrade);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(panel)) {
                message = this.bindByGroupType(ids, panel);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(notice)) {
                message = this.bindByGroupType(ids, notice);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(background)) {
                message = this.bindByGroupType(ids, background);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(common)) {
                message = this.bindByGroupType(ids, common);
            }
        }
        RenderUtils.renderText(message, response);
        String info = "终端用户ID：" + ids + "绑定了终端分组ID：" + boot + "," + animation + "," + upgrade + "," + appUpgrade + ","
                + panel + "," + notice + "," + background + "," + common;
        String result = message + info;
        this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "终端绑定分组", ids, result, request);
    }

    @RequestMapping(value = "/device_bind_business.json")
    public void bindBusinesses(@RequestParam(Constants.IDS) String ids,
                               @RequestParam("boot") String bootId,
                               @RequestParam("animation") String animationId,
                               @RequestParam("panel") String panelId,
                               @RequestParam("notice") String noticeIds,
                               @RequestParam("upgrade") String upgradeIds,
                               @RequestParam("background") String backgroundIds,
                               @RequestParam("apkUpgrade") String apkUpgradeIds,
                               HttpServletRequest request,HttpServletResponse response) {
        String message = "";
        if (StringUtils.isBlank(bootId) && StringUtils.isBlank(backgroundIds) && StringUtils.isBlank(animationId)
                 && StringUtils.isBlank(panelId) && StringUtils.isBlank(noticeIds) && StringUtils.isBlank(upgradeIds) && StringUtils.isBlank(apkUpgradeIds) ) {
            message = "至少选择一种类型的业务做绑定，请确认！";
        } else {
            message = this.deviceWebService.saveDeviceBusinessBind(ids, bootId, animationId, panelId, noticeIds, backgroundIds, upgradeIds, apkUpgradeIds);
        }
        RenderUtils.renderText(message, response);
        String info = "终端ID：" + ids + "绑定了业务ID：" + bootId + "," + animationId + ","
                + panelId + "," + noticeIds + "," + backgroundIds ;
        String result = message +info;
        this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "终端绑定业务", ids, result, request);
    }

    public String bindByGroupType(String ids, String deviceGroup) {
        String message = Constants.SUCCESS;
        if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(deviceGroup)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            String ystenIds = "";
            for (int i = 0; i < idsList.size(); i++) {
                Device list = this.deviceWebService.getDeviceById(idsList.get(i));
//                	 TODO：设备做业务校验：省设备要求为已领用，中心不做限制【以前的锁定 | 激活暂放开】
//                if(systemConfigService.getSystemConfigByConfigKey(Constants.ISCENTER).equals("false") && !list.getDistributeState().equals(DistributeState.PICKUP)){
//                     	return "终端绑定终端分组信息失败！易视腾编号为：" + list.getYstenId() + "的设备未领用，请先领用!";
//                }
                if (i != idsList.size() - 1) {
                    ystenIds += list.getYstenId() + ",";
                }
                ystenIds += list.getYstenId();
            }
            message = this.deviceWebService.bindDeviceGroupMap(deviceGroup, ystenIds);
            // System.out.println(message.substring(0,7));
            message = (message.substring(0, 7).equals("success")) ? "终端绑定终端分组信息成功！" : "终端绑定终端分组信息失败！" + message.substring(7, message.length() - 1);
            return message;
        }
        return message;
    }

    /*@RequestMapping(value = "/device_group_bind.json")
    public void bindDeviceGroup(@RequestParam("boot") String boot, @RequestParam("bootFrom") String bootFrom,
            @RequestParam("bootTo") String bootTo, @RequestParam("deviceCodes1") String deviceCodes1,
            @RequestParam("animation") String animation, @RequestParam("animationFrom") String animationFrom,
            @RequestParam("animationTo") String animationTo, @RequestParam("deviceCodes2") String deviceCodes2,
            @RequestParam("upgrade") String upgrade, @RequestParam("upgradeFrom") String upgradeFrom,
            @RequestParam("upgradeTo") String upgradeTo, @RequestParam("deviceCodes3") String deviceCodes3,
            @RequestParam("appUpgrade") String appUpgrade, @RequestParam("appUpgradeFrom") String appUpgradeFrom,
            @RequestParam("appUpgradeTo") String appUpgradeTo, @RequestParam("deviceCodes4") String deviceCodes4,
            @RequestParam("panel") String panel, @RequestParam("panelFrom") String panelFrom,
            @RequestParam("panelTo") String panelTo, @RequestParam("deviceCodes5") String deviceCodes5,
            @RequestParam("notice") String notice, @RequestParam("noticeFrom") String noticeFrom,
            @RequestParam("noticeTo") String noticeTo, @RequestParam("deviceCodes6") String deviceCodes6,
            @RequestParam("background") String background, @RequestParam("backgroundFrom") String backgroundFrom,
            @RequestParam("backgroundTo") String backgroundTo, @RequestParam("deviceCodes7") String deviceCodes7,
            @RequestParam("common") String common, @RequestParam("commonFrom") String commonFrom,
            @RequestParam("commonTo") String commonTo, @RequestParam("deviceCodes8") String deviceCodes8,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            StringBuilder sb = new StringBuilder();
            String description = null;
            if (StringUtils.isNotBlank(boot)) {
                if (StringUtils.isNotBlank(bootFrom) && StringUtils.isNotBlank(bootTo)) {
                    description = this.deviceWebService.bindDeviceGroup(boot, bootFrom, bootTo);
                }
                if (StringUtils.isNotBlank(deviceCodes1)) {
                    description = this.deviceWebService.bindDeviceGroupMap(boot, deviceCodes1);
                }
                this.appendDescription(sb, description);
            }
            if (StringUtils.isNotBlank(animation)) {
                if (StringUtils.isNotBlank(animationFrom) && StringUtils.isNotBlank(animationTo)) {
                    description = this.deviceWebService.bindDeviceGroup(animation, animationFrom, animationTo);
                }
                if (StringUtils.isNotBlank(deviceCodes2)) {
                    description = this.deviceWebService.bindDeviceGroupMap(animation, deviceCodes2);
                }
                this.appendDescription(sb, description);
            }
            if (StringUtils.isNotBlank(upgrade)) {
                if (StringUtils.isNotBlank(upgradeFrom) && StringUtils.isNotBlank(upgradeTo)) {
                    description = this.deviceWebService.bindDeviceGroup(upgrade, upgradeFrom, upgradeTo);
                }
                if (StringUtils.isNotBlank(deviceCodes3)) {
                    description = this.deviceWebService.bindDeviceGroupMap(upgrade, deviceCodes3);
                }
                this.appendDescription(sb, description);
            }
            if (StringUtils.isNotBlank(appUpgrade)) {
                if (StringUtils.isNotBlank(appUpgradeFrom) && StringUtils.isNotBlank(appUpgradeTo)) {
                    description = this.deviceWebService.bindDeviceGroup(appUpgrade, appUpgradeFrom, appUpgradeTo);
                }
                if (StringUtils.isNotBlank(deviceCodes4)) {
                    description = this.deviceWebService.bindDeviceGroupMap(appUpgrade, deviceCodes4);
                }
                this.appendDescription(sb, description);
            }
            if (StringUtils.isNotBlank(notice)) {
                if (StringUtils.isNotBlank(noticeFrom) && StringUtils.isNotBlank(noticeTo)) {
                    description = this.deviceWebService.bindDeviceGroup(notice, noticeFrom, noticeTo);
                }
                if (StringUtils.isNotBlank(deviceCodes6)) {
                    description = this.deviceWebService.bindDeviceGroupMap(notice, deviceCodes6);
                }
                this.appendDescription(sb, description);
            }
            if (StringUtils.isNotBlank(panel)) {
                if (StringUtils.isNotBlank(panelFrom) && StringUtils.isNotBlank(panelTo)) {
                    description = this.deviceWebService.bindDeviceGroup(panel, panelFrom, panelTo);
                }
                if (StringUtils.isNotBlank(deviceCodes5)) {
                    description = this.deviceWebService.bindDeviceGroupMap(panel, deviceCodes5);
                }
                this.appendDescription(sb, description);
            }
            if (StringUtils.isNotBlank(background)) {
                if (StringUtils.isNotBlank(backgroundFrom) && StringUtils.isNotBlank(backgroundTo)) {
                    description = this.deviceWebService.bindDeviceGroup(background, backgroundFrom, backgroundTo);
                }
                if (StringUtils.isNotBlank(deviceCodes7)) {
                    description = this.deviceWebService.bindDeviceGroupMap(background, deviceCodes7);
                }
                this.appendDescription(sb, description);
            }
            if (StringUtils.isNotBlank(common)) {
                if (StringUtils.isNotBlank(commonFrom) && StringUtils.isNotBlank(commonTo)) {
                    description = this.deviceWebService.bindDeviceGroup(common, commonFrom, commonTo);
                }
                if (StringUtils.isNotBlank(deviceCodes8)) {
                    description = this.deviceWebService.bindDeviceGroupMap(common, deviceCodes8);
                }
                this.appendDescription(sb, description);
            }
            if (StringUtils.isBlank(sb.toString())) {
                sb.append("终端分组绑定成功!");
                RenderUtils.renderText(sb.toString(), response);
            } else {
                RenderUtils.renderText("终端分组绑定失败!<br/>" + sb.toString(), response);
            }
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "DeviceBindGroup", "", description,
                    request);
        } catch (Exception e) {
            LOGGER.error("Bind deviceGroup exception{}", e);
            RenderUtils.renderText(ControllerUtil.returnString(false), response);
            String description = "终端分组绑定失败";
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "DeviceBindGroup", "", description,
                    request);
        }
    }
*/
//    private void appendDescription(StringBuilder sb, String description) {
//        if (StringUtils.isNotBlank(description)) {
//            sb.append(description);
//        }
//    }

    @RequestMapping(value = "/device_to_update")
    public void findDeviceInfoById(@RequestParam(value = "id", defaultValue = "") String id,
                                   HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            Device device = this.deviceWebService.getDeviceById(Long.parseLong(id));
            RenderUtils.renderJson(JsonUtils.toJson(device), response);
        }
    }

    @RequestMapping(value = "/dynamicCascade.json")
    public void dynamicCascade(@RequestParam(value = Constants.DEVICE_VENDOR) String deviceVendor,
                               @RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<DeviceType> deviceTypeList = new ArrayList<DeviceType>();
        if (deviceVendor != null && !"".equals(deviceVendor)) {
            deviceTypeList = this.deviceWebService.findDeviceTypesByVendorId(deviceVendor.trim(),
                    DeviceType.State.USABLE);
        }
        List<TextValue> tv = new ArrayList<TextValue>();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("0", "选择设备型号"));
        }
        if (par != null && !par.isEmpty() && !par.equals("0")) {
        	DeviceType type = this.deviceWebService.getDeviceTypeByTypeId(Long.parseLong(par));
        	if(type != null){
        		tv.add(new TextValue(type.getId().toString(), type.getName()));
        	}
        }
        if(deviceTypeList != null && deviceTypeList.size()>0){
        	for (DeviceType deviceType : deviceTypeList) {
                tv.add(new TextValue(deviceType.getId().toString(), deviceType.getName()));
            }
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }


    @RequestMapping(value = "/delete_device_by_bussiness.json")
    public void deviceDeviceByBusiness(@RequestParam(value = "delYstenIds", defaultValue = "") String delYstenIds,
                                        @RequestParam(value = "packageId", defaultValue = "") Long  packageId,
                                        @RequestParam(value = "tableName", defaultValue = "") String tableName,
                                        @RequestParam(value = "character", defaultValue = "") String character,
                                        @RequestParam(value = "device", defaultValue = "") String device,

                                        @RequestParam(value = "type", defaultValue = "") String type,
                                        HttpServletRequest request, HttpServletResponse response) {
       this.deviceWebService.deleteDeviceByBusiness(delYstenIds, packageId, character, tableName, type, device);
        RenderUtils.renderText(Constants.SUCCESS, response);
        String manager = "";
        String info = "业务解绑设备成功；解绑的";
        if(tableName.equals("bss_animation_device_map") || tableName.equals("bss_animation_user_map")){
        	manager = Constants.BOOT_ANIMATION_INFO_MAINTAIN;
        	info += tableName.equals("bss_animation_device_map")?"设备的易视腾编码有【":"用户的编码有【";
        }
        if(tableName.equals("bss_background_image_device_map") || tableName.equals("bss_background_image_user_map")){
        	manager = Constants.BACKGROUD_INFO_MAINTAIN;
        	info += tableName.equals("bss_background_image_device_map")?"设备的易视腾编码有【":"用户的编码有【";
        }
        if(tableName.equals("bss_device_notice_map") || tableName.equals("bss_user_notice_map")){
        	manager = Constants.SYS_NOTICE_INFO_MAINTAIN;
        	info += tableName.equals("bss_device_notice_map")?"设备的易视腾编码有【":"用户的编码有【";
        }
        if(tableName.equals("bss_device_upgrade_map")){
        	manager = Constants.UPGRADE_TASK_MAINTAIN;
        	info += "设备的易视腾编码有【";
        }
        if(tableName.equals("bss_panel_package_device_map") || tableName.equals("bss_panel_package_user_map")){
        	manager = Constants.PANEL_PACKAGE_MAINTAIN;
        	info += tableName.equals("bss_panel_package_device_map")?"设备的易视腾编码有【":"用户的编码有【";
        }
        if(tableName.equals("bss_service_collect_device_group_map")){
        	manager = Constants.SERVICE_COLLECT_MANAGER_MAINTAIN;
        	info += "设备的易视腾编码有【";
        }
        this.loggerWebService.saveOperateLog(manager, "移除", packageId.toString(), info+delYstenIds+"】", request);
    }

    @RequestMapping(value = "/device_list_by_groupId.json")
    public void findDeviceListByGroupId(@RequestParam(value = "deviceGroupId", defaultValue = "") String deviceGroupId,
                                        @RequestParam(value = Constants.YSTRN_ID, defaultValue = "") String ystenId,
                                        @RequestParam(value = Constants.DEVICE_CODE, defaultValue = "") String deviceCode,
                                        @RequestParam(value = "mac", defaultValue = "") String mac,
                                        @RequestParam(value = "sno", defaultValue = "") String sno,
                                        @RequestParam(value = "softCode", defaultValue = "") String softCode,
                                        @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
                                        @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                                        @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
                                        @RequestParam(value = "tableName", defaultValue = "") String tableName,
                                        @RequestParam(value = "character", defaultValue = "") String character,
                                        HttpServletRequest request, HttpServletResponse response) {
//    	Integer verSeq = StringUtils.isNotBlank(versionSeq) ? Integer.parseInt(versionSeq) : null; 
        Pageable<Device> pageable = this.deviceWebService.findDevicesByGroupId(tableName,character,deviceGroupId, ystenId, deviceCode, mac, sno, softCode, versionSeq,Integer.valueOf(pageNo),
                Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }
    
    @RequestMapping(value = "/export_device_list_by_groupId.json")
    public void ExportDeviceListOfGroupId(@RequestParam(value = "deviceGroupId", defaultValue = "") String deviceGroupId,
                                        @RequestParam(value = Constants.YSTRN_ID, defaultValue = "") String ystenId,
                                        @RequestParam(value = Constants.DEVICE_CODE, defaultValue = "") String deviceCode,
                                        @RequestParam(value = "mac", defaultValue = "") String mac,
                                        @RequestParam(value = "sno", defaultValue = "") String sno,
                                        @RequestParam(value = "softCode", defaultValue = "") String softCode,
                                        @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
                                        @RequestParam(value = "tableName", defaultValue = "") String tableName,
                                        @RequestParam(value = "character", defaultValue = "") String character,
                                        HttpServletRequest request, HttpServletResponse response) {
    	try{
    		List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("设备序列号");
            heads.add("MAC地址");
            heads.add("区域");
            heads.add("地市");
            heads.add("运营商");
            heads.add("创建时间");
            heads.add("激活时间");
            heads.add("状态变更时间");
            heads.add("到期时间");
            heads.add("设备状态");
            heads.add("绑定状态");
            heads.add("分发状态");
            heads.add("设备厂商");
            heads.add("设备型号");
            heads.add("是否锁定");
            heads.add("生产批次号");
            heads.add("软件版本号");
            heads.add("软件号");
            heads.add("是否同步");
            heads.add("描述");
//        	Integer verSeq = StringUtils.isNotBlank(versionSeq) ? Integer.parseInt(versionSeq) : null; 
            List<Device>  devices = this.deviceWebService.ExportDevicesOfGroupId(tableName,character,deviceGroupId, ystenId, deviceCode, mac, sno, softCode, versionSeq);
            if (devices.size() > 20000) {
                RenderUtils.renderText("导出记录不能超过2万条!", response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "导出", "", "导出失败!导出记录超过2万条", request);
                return;
            }
            if (devices.size() == 0) {
                RenderUtils.renderText("没有要导出的数据!", response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "导出", "", "导出失败!没有查询出要导出的数据", request);
                return;
            }
            String fileName = "Device_of_group_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("districtCode");
            excludes.add("ipAddress");
            excludes.add("groups");
            excludes.add("prepareOpen");
            excludes.add("customerId");
            excludes.add("customerCode");
            excludes.add("loopTime");
            excludes.add("isReturnYstenId");
            this.export(heads, devices, excludes, Device.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "导出", "导出ID为:"+deviceGroupId+"的设备分组下的设备", "导出成功!", request);
    	} catch (Exception e) {
    		RenderUtils.renderText("导出数据异常!", response);
            LOGGER.error("Export device data of group exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "导出", "导出ID为:"+deviceGroupId+"的设备分组下的设备", "导出异常!"+e.getMessage(), request);
        }
    	
    }

    @RequestMapping(value = "/device_list.json")
    public void findDeviceList(@RequestParam(value = Constants.YSTRN_ID, defaultValue = "") String ystenId,
                               @RequestParam(value = Constants.DEVICE_CODE, defaultValue = "") String deviceCode,
                               @RequestParam(value = "mac", defaultValue = "") String mac,
                               @RequestParam(value = "sno", defaultValue = "") String sno,
                               @RequestParam(value = Constants.DEVICE_VENDOR_ID, defaultValue = "") Long deviceVendorId,
                               @RequestParam(value = Constants.DEVICE_TYPE_ID, defaultValue = "") Long deviceTypeId,
                               @RequestParam(value = Constants.AREA_ID, defaultValue = "") Long areaId,
                               @RequestParam(value = "deviceState", defaultValue = "") String deviceState,
                               @RequestParam(value = "bindType", defaultValue = "") String bindType,
                               @RequestParam(value = "isLock", defaultValue = "") String isLock,
                               @RequestParam(value = "sp", defaultValue = "") Long sp,
                               @RequestParam(value = "productNo", defaultValue = "") String productNo,
                               @RequestParam(value = "deviceDistributeState", defaultValue = "") String deviceDistributeState,
                               @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                               @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
                               HttpServletRequest request, HttpServletResponse response) {
        Long deviceVendor = deviceVendorId != null && deviceVendorId == 0 ? null : deviceVendorId;
        Long deviceType = deviceTypeId != null && deviceTypeId == 0 ? null : deviceTypeId;
        Long area = areaId != null && areaId == 0 ? null : areaId;
        Long spDefineId = sp != null && sp == 0 ? null : sp;

        Device.State s = null;
        if (deviceState != null && !deviceState.isEmpty() && !"0".equals(deviceState)) {
            s = Device.State.valueOf(deviceState);
        }
        Device.DistributeState ds = null;
        if (StringUtils.isNotBlank(deviceDistributeState) && !StringUtils.equalsIgnoreCase("0", deviceDistributeState)) {
            ds = Device.DistributeState.valueOf(deviceDistributeState);
        }
        if (StringUtils.equalsIgnoreCase("0", productNo)) {
            productNo = null;
        }
        Pageable<Device> pageable = this.deviceWebService.findDevicesByState(bindType, isLock, ystenId, deviceCode, mac, sno,
                deviceVendor, deviceType, area, s, null, spDefineId, productNo, ds, Integer.valueOf(pageNo),
                Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }
    
    @RequestMapping(value = "/device_list_conditions.json")
    public void selectDeviceListByMayTerms(@RequestParam(value = Constants.YSTRN_ID, defaultValue = "") String ystenId,
            @RequestParam(value = Constants.DEVICE_CODE, defaultValue = "") String deviceCode,
            @RequestParam(value = "mac", defaultValue = "") String mac,
            @RequestParam(value = "sno", defaultValue = "") String sno,
            @RequestParam(value = Constants.DEVICE_VENDOR_ID, defaultValue = "") Long deviceVendorId,
            @RequestParam(value = Constants.DEVICE_TYPE_ID, defaultValue = "") Long deviceTypeId,
            @RequestParam(value = Constants.AREA_ID, defaultValue = "") Long areaId,
            @RequestParam(value = "deviceState", defaultValue = "") String deviceState,
            @RequestParam(value = "sp", defaultValue = "") Long sp,
            @RequestParam(value = "productNo", defaultValue = "") String productNo,
            @RequestParam(value = "softCode", defaultValue = "") String softCode,
            @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "startDateAvtive", defaultValue = "") String startDateAvtive,
            @RequestParam(value = "endDateAvtive", defaultValue = "") String endDateAvtive, 
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response){
    	try{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> map = new HashMap<String, Object>();
            Long deviceVendor = deviceVendorId != null && deviceVendorId == 0 ? null : deviceVendorId;
            Long deviceType = deviceTypeId != null && deviceTypeId == 0 ? null : deviceTypeId;
            Long area = areaId != null && areaId == 0 ? null : areaId;
            Long spDefineId = sp != null && sp == 0 ? null : sp;
//            Integer verSeq = StringUtils.isNotBlank(versionSeq) ? Integer.parseInt(versionSeq) : null; 

            Device.State s = null;
            if (deviceState != null && !deviceState.isEmpty() && !"0".equals(deviceState)) {
                s = Device.State.valueOf(deviceState);
            }
            if (StringUtils.equalsIgnoreCase("0", productNo)) {
                productNo = null;
            }
            
            map.put("ystenIds", ystenId);
            map.put("deviceCodes", deviceCode);
            map.put("mac", mac);
            map.put("snos", sno);
            map.put("deviceVendorId", deviceVendor);
            map.put("deviceTypeId", deviceType);
            map.put("areaId", area);
            if (!"0".equals(deviceState)) {
                map.put("state", s);
            }
            map.put("spId", spDefineId);
            map.put("productNo", productNo);
            map.put("softCode", softCode);
            map.put("versionSeq", versionSeq);
            map.put("endDate", StringUtils.isNotBlank(endDate) ? sdf.parse(endDate + " 23:59:59") : null);
            map.put("startDate", StringUtils.isNotBlank(startDate) ? sdf.parse(startDate + " 00:00:00") : null);
            map.put("startDateAvtive",
                    StringUtils.isNotBlank(startDateAvtive) ? sdf.parse(startDateAvtive + " 00:00:00") : null);
            map.put("endDateAvtive", StringUtils.isNotBlank(endDateAvtive) ? sdf.parse(endDateAvtive + " 23:59:59")
                    : null);
            map.put("pageNo", Integer.parseInt(pageNo));
            map.put("pageSize", Integer.parseInt(pageSize));
            Pageable<Device> pageData = this.deviceWebService.findDeviceListByConditions(map);
            RenderUtils.renderJson(EnumDisplayUtil.toJson(pageData), response);
    	}catch (Exception e) {
            LOGGER.error("Height Query deviceList data exception{}", e);
        }
    }

    @RequestMapping(value = "/device_export_by_query.json")
    public void exportDeviceListByQuery(
    @RequestParam(value = "ystenValue", defaultValue = "") String ystenValue,
    @RequestParam(value = "deviceCodeValue", defaultValue = "") String deviceCodeValue,
    @RequestParam(value = "snoValue", defaultValue = "") String snoValue,
    @RequestParam(value = "macValue", defaultValue = "") String macValue,
    @RequestParam(value = "areaValue", defaultValue = "") Long areaValue,
    @RequestParam(value = "spValue", defaultValue = "") Long spValue,
    @RequestParam(value = "deviceVendorValue", defaultValue = "") Long deviceVendorValue,
    @RequestParam(value = "deviceTypeValue", defaultValue = "") Long deviceTypeValue,
    @RequestParam(value = "areaHvalue", defaultValue = "") Long areaHvalue,
    @RequestParam(value = "productNoValue", defaultValue = "") String productNoValue,
    @RequestParam(value = "deviceStateValue", defaultValue = "") String deviceStateValue,
    @RequestParam(value = "macHvalue", defaultValue = "") String macHvalue,
    @RequestParam(value = "softCodeValue", defaultValue = "") String softCodeValue,
    @RequestParam(value = "versionSeqValue", defaultValue = "") String versionSeqValue,   
    @RequestParam(value = "snoHvalue", defaultValue = "") String snoHvalue,
    @RequestParam(value = "deviceCodeHvalue", defaultValue = "") String deviceCodeHvalue,
    @RequestParam(value = "ystenIdHvalue", defaultValue = "") String ystenIdHvalue,
    @RequestParam(value = "startDateValue", defaultValue = "") String startDateValue,
    @RequestParam(value = "endDateValue", defaultValue = "") String endDateValue,
    @RequestParam(value = "startDateAvtiveValue", defaultValue = "") String startDateAvtiveValue,
    @RequestParam(value = "endDateAvtiveValue", defaultValue = "") String endDateAvtiveValue,
    HttpServletRequest request,HttpServletResponse response) {
        try {
            List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("设备序列号");
            heads.add("MAC地址");
            heads.add("区域");
            heads.add("地市");
            heads.add("运营商");
            heads.add("创建时间");
            heads.add("激活时间");
            heads.add("状态变更时间");
            heads.add("到期时间");
            heads.add("设备状态");
            heads.add("绑定状态");
            heads.add("分发状态");
            heads.add("设备厂商");
            heads.add("设备型号");
//            heads.add("IP地址");
            heads.add("是否锁定");
            heads.add("生产批次号");
            heads.add("软件版本号");
            heads.add("软件号");
            heads.add("是否同步");
            heads.add("描述");
//            heads.add("设备分组");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> map = new HashMap<String, Object>();
            Long areaId = areaValue != null && areaValue == 0 ? null : areaValue;
            Long deviceVendor = deviceVendorValue != null && deviceVendorValue == 0 ? null : deviceVendorValue;
            Long deviceType = deviceTypeValue != null && deviceTypeValue == 0 ? null : deviceTypeValue;
            Long area = areaHvalue != null && areaHvalue == 0 ? null : areaHvalue;
            Long spDefineId = spValue != null && spValue == 0 ? null : spValue;
//            Integer verSeq = StringUtils.isNotBlank(versionSeqValue) ? Integer.parseInt(versionSeqValue) : null; 

            Device.State s = null;
            if (deviceStateValue != null && !deviceStateValue.isEmpty() && !"0".equals(deviceStateValue)) {
                s = Device.State.valueOf(deviceStateValue);
            }
            if (StringUtils.equalsIgnoreCase("0", productNoValue)) {
            	productNoValue = null;
            }
            map.put("ystenId", ystenValue);
            map.put("deviceCode", deviceCodeValue);
            map.put("macId", macValue);
            map.put("sno", snoValue);
            map.put("area", areaId);
            
            map.put("ystenIds", ystenIdHvalue);
            map.put("deviceCodes", deviceCodeHvalue);
            map.put("mac", macHvalue);
            map.put("snos", snoHvalue);
            map.put("deviceVendorId", deviceVendor);
            map.put("deviceTypeId", deviceType);
            map.put("areaId", area);
            if (!"0".equals(deviceStateValue)) {
                map.put("state", s);
            }
            map.put("spId", spDefineId);
            map.put("productNo", productNoValue);
            map.put("softCode", softCodeValue);
            map.put("versionSeq", versionSeqValue);
            map.put("endDate", StringUtils.isNotBlank(endDateValue) ? sdf.parse(endDateValue + " 23:59:59") : null);
            map.put("startDate", StringUtils.isNotBlank(startDateValue) ? sdf.parse(startDateValue + " 00:00:00") : null);
            map.put("startDateAvtive",
                    StringUtils.isNotBlank(startDateAvtiveValue) ? sdf.parse(startDateAvtiveValue + " 00:00:00") : null);
            map.put("endDateAvtive", StringUtils.isNotBlank(endDateAvtiveValue) ? sdf.parse(endDateAvtiveValue + " 23:59:59")
                    : null);
            List<Device> devices = this.deviceWebService.QueryDevicesToExport(map);
            if (devices.size() > 20000) {
                RenderUtils.renderText("导出记录不能超过2万条!", response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "导出", "", "导出失败!导出记录超过2万条", request);
                return;
            }
            if (devices.size() == 0) {
                RenderUtils.renderText("没有要导出的数据!", response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "导出", "", "导出失败!没有查询出要导出的数据", request);
                return;
            }
            String fileName = "Device_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("districtCode");
            excludes.add("ipAddress");
            excludes.add("groups");
            excludes.add("prepareOpen");
            excludes.add("customerId");
            excludes.add("customerCode");
            excludes.add("loopTime");
            excludes.add("isReturnYstenId");
            this.export(heads, devices, excludes, Device.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "导出", "", "导出成功!", request);
        } catch (Exception e) {
        	RenderUtils.renderText("导出数据异常!", response);
            LOGGER.error("Export device data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(), request);
        }
    }
    
    @RequestMapping(value = "/device_export.json")
    public void exportDeviceList(@RequestParam(value = Constants.DEVICE_CODE, defaultValue = "") String deviceCode,
                                 @RequestParam(value = "mac", defaultValue = "") String mac,
                                 @RequestParam(value = "sno", defaultValue = "") String sno,
                                 @RequestParam(value = Constants.DEVICE_VENDOR_ID, defaultValue = "") Long deviceVendorId,
                                 @RequestParam(value = Constants.DEVICE_TYPE_ID, defaultValue = "") Long deviceTypeId,
                                 @RequestParam(value = Constants.AREA_ID, defaultValue = "") Long areaId,
                                 @RequestParam(value = "deviceState", defaultValue = "") String deviceState,
                                 @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                 @RequestParam(value = "endDate", defaultValue = "") String endDate,
                                 @RequestParam(value = "startDateAvtive", defaultValue = "") String startDateAvtive,
                                 @RequestParam(value = "endDateAvtive", defaultValue = "") String endDateAvtive, HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("设备序列号");
            heads.add("MAC地址");
            heads.add("区域");
            heads.add("地市");
            heads.add("运营商");
            heads.add("创建时间");
            heads.add("激活时间");
            heads.add("状态变更时间");
            heads.add("到期时间");
            heads.add("设备状态");
            heads.add("绑定状态");
            heads.add("分发状态");
            heads.add("设备厂商");
            heads.add("设备型号");
//            heads.add("IP地址");
            heads.add("是否锁定");
            heads.add("生产批次号");
            heads.add("软件版本号");
            heads.add("软件号");
            heads.add("是否同步");
            heads.add("描述");
//            heads.add("设备分组");
//            heads.add("绑定的用户编号");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("deviceCode", deviceCode);
            map.put("mac", mac);
            map.put("sno", sno);
            map.put("deviceVendorId", deviceVendorId);
            map.put("deviceTypeId", deviceTypeId);
            map.put("areaId", areaId);
            if (!"0".equals(deviceState)) {
                map.put("state", deviceState);
            }
            map.put("endDate", StringUtils.isNotBlank(endDate) ? sdf.parse(endDate + " 23:59:59") : null);
            map.put("startDate", StringUtils.isNotBlank(startDate) ? sdf.parse(startDate + " 00:00:00") : null);
            map.put("startDateAvtive",
                    StringUtils.isNotBlank(startDateAvtive) ? sdf.parse(startDateAvtive + " 00:00:00") : null);
            map.put("endDateAvtive", StringUtils.isNotBlank(endDateAvtive) ? sdf.parse(endDateAvtive + " 23:59:59")
                    : null);
            List<Device> devices = this.deviceWebService.findDevicesToExport(map);
            if (devices.size() > 20000) {
                RenderUtils.renderText("导出记录不能超过2万条", response);
                return;
            }
            String fileName = "Device_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("districtCode");
            excludes.add("ipAddress");
            excludes.add("groups");
            excludes.add("prepareOpen");
            excludes.add("customerId");
            excludes.add("customerCode");
            excludes.add("loopTime");
            excludes.add("isReturnYstenId");
            this.export(heads, devices, excludes, Device.class, fileName, request, response);
        } catch (Exception e) {
            LOGGER.error("Export device data exception{}", e);
        }
    }

    @RequestMapping(value = "/device_export_byId.json")
    public void exportByIdDeviceList(@RequestParam(Constants.IDS) String ids,
                                     @RequestParam(value = Constants.DEVICE_CODE, defaultValue = "") String deviceCode,
                                     HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("设备序列号");
            heads.add("MAC地址");
            heads.add("区域");
            heads.add("地市");
            heads.add("运营商");
            heads.add("创建时间");
            heads.add("激活时间");
            heads.add("状态变更时间");
            heads.add("到期时间");
            heads.add("设备状态");
            heads.add("绑定状态");
            heads.add("分发状态");
            heads.add("设备厂商");
            heads.add("设备型号");
            heads.add("是否锁定");
            heads.add("生产批次号");
            heads.add("软件版本号");
            heads.add("软件号");
            heads.add("是否同步");
            heads.add("描述");
//            heads.add("设备分组");
            List<Device> devices = null;
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                devices = this.deviceWebService.getDeviceByIds(idsList);
            }
            String fileName = "Device_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("districtCode");
            excludes.add("ipAddress");
            excludes.add("groups");
            excludes.add("prepareOpen");
            excludes.add("customerId");
            excludes.add("customerCode");
            excludes.add("loopTime");
            excludes.add("isReturnYstenId");
            this.export(heads, devices, excludes, Device.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "导出", "", "导出成功!", request);
        } catch (Exception e) {
        	RenderUtils.renderText("导出数据异常!", response);
            LOGGER.error("Export device data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/device_export_template.json")
    public void exportDeviceTemplate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String path = request.getSession().getServletContext().getRealPath("template");
            String fileName = path + "/" + TEMPLATE_FILE_NAME;
            this.exportTemplate(fileName, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "下载导入模版", "", "下载成功!", request);
        } catch (Exception e) {
        	RenderUtils.renderText("下载异常!", response);
            LOGGER.error("Export device data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "下载导入模版", "", "下载异常!"+e.getMessage(), request);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/device_import.json")
    public String importDeviceList(@RequestParam(value = "fileField", required = false) MultipartFile fileField,
                                   HttpServletRequest request, HttpServletResponse response) {
        try {

            String path = request.getSession().getServletContext().getRealPath("upload");
            String fileName = fileField.getOriginalFilename();

            File targetFile = FileUtils.saveFileFromInputStream(fileField.getInputStream(), path, fileName);

            Map<String, Object> map = this.deviceWebService.importDeviceFile(targetFile);
            // targetFile.delete();
            if ((Boolean) map.get("isSuccess")) {
                String description = "导入成功.导入数据：" + map.get("data").toString();
                this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.IMPORT, "", "导入成功！文件名为:"+fileField.getName(),
                        request);
                LOGGER.info(description);
                return "/device/device_list";
            } else {
                List<String> errors = (List<String>) map.get("error");
                StringBuilder sb = new StringBuilder();
                for (String error : errors) {
                    sb.append(error + "\n");
                }
                this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.IMPORT, "",
                         "导入失败！文件名为："+fileField.getName()+";失败原因:"+sb.toString() , request);
                LOGGER.info(sb.toString());
                // RenderUtils.renderText(ControllerUtil.returnString(false),
                // response);
                request.setAttribute("errors", errors);
                return "/error_fileupload";
            }
        } catch (Exception e) {
            LOGGER.error("Import device data exception{}", e);
            request.getSession().setAttribute("errors", e.getMessage());
            String description = "导入失败.文件名：" + fileField.getName();
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.IMPORT, "", description+";失败原因:"+e.getMessage(),
                    request);
            return "/error_fileupload";
        }

    }

    @RequestMapping(value = "/set_device_ysteId.json")
    public void setYstenId(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Device> deviceList = this.deviceWebService.findDevicesOfBlankYsteId();
            StringBuilder sby = new StringBuilder();
            String message = "";
            if (!CollectionUtils.isEmpty(deviceList)) {
                for (Device device : deviceList) {
                    sby.append(device.getId() + ",");
                }
                Map<String, Object> map = this.deviceWebService.createDeviceYstenId(deviceList);

                if ((Boolean) map.get("isSuccess")) {
                    message = "生成易视腾编号成功!";
                } else {
                    List<String> errors = (List<String>) map.get("error");
                    StringBuilder sb = new StringBuilder();
                    for (String error : errors) {
                        sb.append(error + "\n");
                    }
                    message = "生成易视腾编号失败!" + sb;
                }
            }
            if(StringUtils.isEmpty(message)){
                message = "生成易视腾编号成功!";
            }
            RenderUtils.renderText(message, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "生成易视腾编号", sby.toString(), message,
                    request);
            LOGGER.info(message);
        } catch (Exception e) {
            LOGGER.error("set device ystenId exception{}", e);
            request.getSession().setAttribute("errors", e.getMessage());
            String description = "生成易视腾编号异常!";
            RenderUtils.renderText(description, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "生成易视腾编号", "", description+"；异常："+e.getMessage(),
                    request);
        }

    }

    @RequestMapping(value = "/device_ip_list.json")
    public void findDeviceIpList(@RequestParam(value = "ipSeq", defaultValue = "") String ipSeq,
                                 @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                                 @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
                                 HttpServletRequest request, HttpServletResponse response) {
        Pageable<DeviceIp> pageable = this.deviceWebService.findDeviceIpByIpSeg(ipSeq, Integer.valueOf(pageNo),
                Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/device_ip_to_update")
    public void findDeviceIpById(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            DeviceIp deviceIp = this.deviceWebService.getDeviceIpById(Long.parseLong(id));
            RenderUtils.renderJson(JsonUtils.toJson(deviceIp), response);
        }
    }

    @RequestMapping(value = "/device_ip_isexists")
    public void checkDeviceIpExists(@RequestParam(value = "ipSeg") String ipSeg, HttpServletResponse response) {
        if (ipSeg != null && !"".equals(ipSeg)) {
            boolean bool = this.deviceWebService.checkDeviceIpExists(ipSeg);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        }
    }

    @RequestMapping(value = "/device_ip_update")
    public void updateDeviceIp(DeviceIp deviceIp, HttpServletRequest request, HttpServletResponse response) {
        if (deviceIp != null) {
            boolean bool = this.deviceWebService.updateDeviceIp(deviceIp);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_IP_INFO_MAINTAIN, Constants.MODIFY, deviceIp.getId()
                    .toString(), (bool == true) ? "修改IP地址信息成功!Ip地址段：" + deviceIp.getIpSeg() + "\n" + "起始值："
                    + deviceIp.getStartIpValue().toString() + "\n" + "终止值：" + deviceIp.getEndIpValue().toString()
                    : "修改IP地址信息失败", request);
        }
    }

    @RequestMapping(value = "/device_ip_delete")
    public void deleteDeviceIp(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                               HttpServletResponse response) {
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            boolean bool = this.deviceWebService.deleteDeviceIp(idsList);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_IP_INFO_MAINTAIN, Constants.DELETE, ids,
                    (bool == true) ? "删除成功" : "删除失败", request);
        }
    }

    @RequestMapping(value = "/device_type_add")
    public void addDeviceType(@RequestParam(value = "deviceTypeName1", defaultValue = "") String deviceTypeName,
                              @RequestParam(value = "deviceTypeCode1", defaultValue = "") String deviceTypeCode,
                              @RequestParam(value = "deviceVendor1", defaultValue = "") Long deviceVendorId,
                              @RequestParam(value = "terminalType", defaultValue = "") TerminalType terminalType,
                              @RequestParam(value = "deviceTypeState", defaultValue = "") DeviceType.State state,
                              @RequestParam(value = Constants.DESCRIPTION, defaultValue = "") String description,
                              HttpServletRequest request, HttpServletResponse response) {
        DeviceType deviceType = new DeviceType();
        DeviceVendor deviceVendor = this.deviceWebService.getDeviceVendorById(deviceVendorId);
        deviceType.setName(deviceTypeName);
        deviceType.setCode(deviceTypeCode);
        deviceType.setDeviceVendor(deviceVendor);
        deviceType.setTerminalType(terminalType);
        deviceType.setCreateDate(new Date());
        deviceType.setState(state);
        deviceType.setDescription(description);
        boolean bool = this.deviceWebService.saveDeviceType(deviceType);
        String descriptions = (bool == true) ? "设备型号添加成功" : "设备型号添加失败";
        descriptions += "型号名称：" + deviceTypeName + "\n" + "型号编码：" + deviceTypeCode + "\n" + "设备厂商："
                + deviceVendor.getName() + "\n" + "终端类型：" + terminalType + "\n" + "状态：" + state.name() + "\n"
                + description;
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.DEVICE_TYPE_INFO_MAINTAIN, Constants.ADD, deviceType.getId()
                + "", descriptions, request);
    }

    @RequestMapping(value = "/device_type_list.json")
    public void findAllDeviceTypes(@RequestParam(value = "deviceTypeName", defaultValue = "") String deviceTypeName,
                                   @RequestParam(value = "deviceTypeCode", defaultValue = "") String deviceTypeCode,
                                   @RequestParam(value = Constants.DEVICE_VENDOR, defaultValue = "") String deviceVendorId,
                                   @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                                   @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
                                   HttpServletRequest request, HttpServletResponse response) {
        String deviceVendor = deviceVendorId != null && "0".equals(deviceVendorId) ? null : deviceVendorId;
        Pageable<DeviceType> page = this.deviceWebService.findAllDeviceTypes(deviceTypeName, deviceTypeCode,
                deviceVendor, Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(page), response);
    }

    @RequestMapping(value = "/device_type_to_update")
    public void toUpdateDeviceType(@RequestParam(value = Constants.ID, defaultValue = "") String id,
                                   HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (id != null && !"".equals(id)) {
            DeviceType deviceType = this.deviceWebService.getDeviceTypeById((Long.parseLong(id)));
            RenderUtils.renderJson(JsonUtils.toJson(deviceType), response);
        }
    }

    @RequestMapping(value = "/device_type_update")
    public void updateDeviceType(@RequestParam(value = "id", defaultValue = "") String id,
                                 @RequestParam(value = "deviceTypeName1", defaultValue = "") String deviceTypeName,
                                 @RequestParam(value = "deviceTypeCode1", defaultValue = "") String deviceTypeCode,
                                 @RequestParam(value = "deviceVendor1", defaultValue = "") Long deviceVendorId,
                                 @RequestParam(value = "terminalType", defaultValue = "") TerminalType terminalType,
                                 @RequestParam(value = "deviceTypeState", defaultValue = "") DeviceType.State state,
                                 @RequestParam(value = Constants.DESCRIPTION, defaultValue = "") String description,
                                 HttpServletRequest request, HttpServletResponse response) {
        if (id != null && !id.isEmpty()) {
            DeviceType deviceType = this.deviceWebService.getDeviceTypeById((Long.parseLong(id)));
            String str = "";
            if (deviceType != null) {
                deviceType.setName(deviceTypeName);
                if (deviceVendorId != null) {
                    DeviceVendor deviceVendor = new DeviceVendor();
                    deviceVendor.setId(deviceVendorId);
                    deviceType.setDeviceVendor(deviceVendor);
                    deviceType.setDescription(description);
                    deviceType.setTerminalType(terminalType);
                    deviceType.setState(state);
                    str += "\n" + "设备厂商：" + deviceVendor.getName();
                }
            }
            boolean bool = this.deviceWebService.updateDeviceType(deviceType);
            String descriptions = (bool == true) ? "设备型号修改成功" : "设备型号修改失败";
            descriptions += "型号名称：" + deviceTypeName + "\n" + "型号编码：" + deviceTypeCode + "\n" + "终端类型：" + terminalType
                    + str + "\n" + "状态：" + state.name() + "\n" + description;
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_TYPE_INFO_MAINTAIN, Constants.MODIFY,
                    deviceType.getId() + "", descriptions, request);
        }
    }

    @RequestMapping(value = "/device_vendor_add")
    public void addDeviceVendor(@RequestParam(value = "deviceVendorName1", defaultValue = "") String deviceVendorName,
                                @RequestParam(value = "deviceVendorCode1", defaultValue = "") String deviceVendorCode,
                                @RequestParam(value = "deviceVendorState", defaultValue = "") DeviceVendor.State state,
                                @RequestParam(value = Constants.DESCRIPTION, defaultValue = "") String description,
                                HttpServletResponse response, HttpServletRequest request) {
        DeviceVendor deviceVendor = new DeviceVendor();
        deviceVendor.setName(deviceVendorName);
        deviceVendor.setCode(deviceVendorCode);
        deviceVendor.setDescription(description);
        deviceVendor.setCreateDate(new Date());
        deviceVendor.setState(state);
        boolean bool = this.deviceWebService.saveDeviceVendor(deviceVendor);
        String descriptions = (bool == true) ? "设备厂商新增成功" : "设备厂商新增失败";
        descriptions += "厂商名称：" + deviceVendorName + "\n" + "厂商编码：" + deviceVendorCode + "\n" + "状态：" + state.name()
                + "\n" + description;
        LOGGER.debug("device controller: add device vendor result:{}.", bool);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.DEVICE_VENDOR_INFO_MAINTAIN, Constants.ADD, deviceVendor.getId()
                + "", descriptions, request);
        List<String> nameList = new ArrayList<String>();
        nameList.add(deviceVendorName);
    }

    @RequestMapping(value = "/device_vendor_list.json")
    public void findAllDeviceVendors(
            @RequestParam(value = "deviceVendorName", defaultValue = "") String deviceVendorName,
            @RequestParam(value = "deviceVendorCode", defaultValue = "") String deviceVendorCode,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<DeviceVendor> page = this.deviceWebService.findAllDeviceVendors(deviceVendorName, deviceVendorCode,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(page), response);
    }

    @RequestMapping(value = "/device_vendor_to_update")
    public void toUpdateDeviceVendor(@RequestParam(value = "id") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            DeviceVendor deviceVendor = this.deviceWebService.getDeviceVendorById((Long.parseLong(id)));
            RenderUtils.renderJson(JsonUtils.toJson(deviceVendor), response);
        }
    }

    @RequestMapping(value = "/device_vendor_update")
    public void updateDeviceVendor(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "deviceVendorName1", defaultValue = "") String deviceVendorName,
                                   @RequestParam(value = "deviceVendorCode1", defaultValue = "") String deviceVendorCode,
                                   @RequestParam(value = "deviceVendorState", defaultValue = "") DeviceVendor.State state,
                                   @RequestParam(value = Constants.DESCRIPTION, defaultValue = "") String description,
                                   HttpServletResponse response, HttpServletRequest request) {
        if (id != null && !"".equals(id)) {
            DeviceVendor deviceVendor = this.deviceWebService.getDeviceVendorById((Long.parseLong(id)));
            List<DeviceType> deviceType = this.deviceWebService.findDeviceTypesByVendorId(id, State.USABLE);
            LOGGER.debug("state ==" + state);
            if (deviceType.size() != 0 && state.toString() == "UNUSABLE") {
                RenderUtils.renderText("该设备厂商有所属的可用状态型号，不可置为不可用状态！", response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_VENDOR_INFO_MAINTAIN, Constants.MODIFY,
                        deviceVendor.getId() + "", "设备厂商修改失败!该设备厂商有所属的可用状态型号，不可置为不可用状态！", request);
                return;
            }
            if (deviceVendor != null) {
                deviceVendor.setName(deviceVendorName);
                deviceVendor.setDescription(description);
                deviceVendor.setState(state);
                boolean bool = this.deviceWebService.updateDeviceVendor(deviceVendor);
                String descriptions = (bool == true) ? "设备厂商修改成功" : "设备厂商修改失败";
                descriptions += "厂商名称：" + deviceVendorName + "\n" + "厂商编码：" + deviceVendorCode + "\n" + "状态："
                        + state.name() + "\n" + description;
                LOGGER.debug("device controller: update deviceVendor result:{}.", bool);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_VENDOR_INFO_MAINTAIN, Constants.MODIFY,
                        deviceVendor.getId() + "", descriptions, request);
            }
        }
    }

    @RequestMapping(value = "/device_vendor_name_exists")
    public void checkDeviceVendorNameExists(@RequestParam(value = "id", defaultValue = "") String id,
                                            @RequestParam("deviceVendorName") String deviceVendorName, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (deviceVendorName != null && !"".equals(deviceVendorName)) {
            DeviceVendor deviceVendor = this.deviceWebService
                    .getDeviceVendorByNameOrCode(deviceVendorName.trim(), null);
            if (deviceVendor != null && !deviceVendor.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/device_vendor_code_exists")
    public void checkDeviceVendorCodeExists(@RequestParam("deviceVendorCode") String deviceVendorCode,
                                            HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (deviceVendorCode != null && !"".equals(deviceVendorCode)) {
            DeviceVendor deviceVendor = this.deviceWebService.getDeviceVendorByNameOrCode(null, deviceVendorCode);
            if (deviceVendor != null) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/device_type_name_exists")
    public void checkDeviceTypeNameExists(@RequestParam(value = "id", defaultValue = "") String id,
                                          @RequestParam("deviceTypeName") String deviceTypeName,
                                          @RequestParam(Constants.DEVICE_VENDOR) String deviceVendor, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (deviceTypeName != null && !"".equals(deviceTypeName)) {
            DeviceType deviceType = this.deviceWebService.getDeviceTypeByNameOrCode(deviceVendor,
                    deviceTypeName.trim(), null);
            if (deviceType != null && !deviceType.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;

            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/device_type_code_exists")
    public void checkDeviceTypeCodeExists(@RequestParam("deviceTypeCode") String deviceTypeCode,
                                          @RequestParam(Constants.DEVICE_VENDOR) String deviceVendor, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (deviceTypeCode != null && !"".equals(deviceTypeCode)) {
            DeviceType deviceType = this.deviceWebService.getDeviceTypeByNameOrCode(deviceVendor, null, deviceTypeCode);
            if (deviceType != null) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/device_ip_add")
    public void addDeviceVendor(DeviceIp deviceIp, HttpServletResponse response, HttpServletRequest request) {
        boolean bool = this.deviceWebService.saveDeviceIp(deviceIp);
        LOGGER.debug("device controller: add device vendor result:{}.", bool);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.DEVICE_IP_INFO_MAINTAIN, Constants.ADD, deviceIp.getId() + "",
                (bool == true) ? "新增IP地址信息成功!Ip地址段：" + deviceIp.getIpSeg() + "\n" + "起始值："
                        + deviceIp.getStartIpValue().toString() + "\n" + "终止值：" + deviceIp.getEndIpValue().toString()
                        : "新增IP地址信息失败", request);
    }

    @RequestMapping(value = "/delete_devices.json")
    public void deleteDevices(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                              HttpServletResponse response) {
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            String message = this.deviceWebService.deleteDevice(idsList);
            RenderUtils.renderText(message, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.DELETE, ids,
                    (Constants.SUCCESS.equals(message)) ? "删除设备信息成功！" : "删除设备信息失败！", request);
        }
    }

    @RequestMapping(value = "/validator_code.json")
    public void validatorCode(@RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes,
                             HttpServletRequest request, HttpServletResponse response) {
        String description = this.deviceWebService.validatorDeviceCode(deviceCodes);
        if (description.substring(0, 7).equals("success")) {
            RenderUtils.renderText("设备编号合法!", response);
        } else {
            RenderUtils.renderText(description, response);
        }
    }
    
    @RequestMapping(value = "/validator_sno.json")
    public void validatorSno(@RequestParam(value = "snos", defaultValue = "") String snos,
                             HttpServletRequest request, HttpServletResponse response) {
        String description = this.deviceWebService.validatorSno(snos);
        if (description.substring(0, 7).equals("success")) {
            RenderUtils.renderText("设备序列号合法!", response);
        } else {
            RenderUtils.renderText(description, response);
        }
    }

    @RequestMapping(value = "/device_list_by_snos.json")
    public void findDeviceList(
            @RequestParam(value = "snos", defaultValue = "") String snos,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<Device> pageable = this.deviceWebService.findDevicesBySnos(snos, Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

//    @RequestMapping(value = "/bulk_find_ystenIds.json")
//    public void findDeviceYstenIdList(
//            @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes,
//            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Device> list = this.deviceWebService.findDeviceYstenIdListByDeviceCodes(deviceCodes);
//        StringBuilder sb = new StringBuilder();
//        if (list != null && list.size() > 0) {
//            for (Device device : list) {
//                if (!device.getYstenId().equals("")) {
//                    sb.append(device.getYstenId() + "," + "\n");
//                }
//            }
//        }
//        if (StringUtils.isNotBlank(sb.toString())) {
//            RenderUtils.renderText(sb.toString(), response);
//        } else {
//            RenderUtils.renderText("没有对应的易视腾编号", response);
//        }
//
//    }
    
    @RequestMapping(value = "/bulk_find_ystenIds.json")
    public void findDeviceYstenIdList(
            @RequestParam(value = "byDeviceCodes", required = false) MultipartFile byDeviceCodes,
            HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
    	try{
    		 String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
             String deviceCodes = FileUtils.getDeviceCodesFromUploadFile(byDeviceCodes, path);
             
            List<Device> list = this.deviceWebService.findDeviceYstenIdListByDeviceCodes(deviceCodes);
            List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            if (list.size() ==0) {
            	RenderUtils.renderText("没有对应的易视腾编号!", response);
            	this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "批量获取易视腾编号", "导入要查询的设备编号:"+deviceCodes, "没有对应的易视腾编号!",
                        request);
            }
            if (list.size() > 10000) {
            	RenderUtils.renderText("最大查询量不可超过10000!", response);
            	this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "批量获取易视腾编号", "导入要查询的设备编号:"+deviceCodes, "每次最多可查询1万条易视腾编号!",
                        request);
            }
            String fileName = "Query_Device_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("districtCode");
            excludes.add("sno");
            excludes.add("mac");
            excludes.add("area");
            excludes.add("city");
            excludes.add("spDefine");
            excludes.add("createDate");
            excludes.add("activateDate");
            excludes.add("stateChangeDate");
            excludes.add("expireDate");
            excludes.add("state");
            excludes.add("bindType");
            excludes.add("distributeState");
            excludes.add("deviceVendor");
            excludes.add("deviceType");
            excludes.add("ipAddress");
            excludes.add("isLock");
            excludes.add("productNo");
            excludes.add("versionSeq");
            excludes.add("softCode");
            excludes.add("isSync");
            excludes.add("description");
            excludes.add("groups");
            excludes.add("prepareOpen");
            excludes.add("customerId");
            excludes.add("customerCode");
            excludes.add("loopTime");
            excludes.add("isReturnYstenId");
            this.export(heads, list, excludes, Device.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "批量获取易视腾编号", "导入要查询的设备编号:"+deviceCodes, "批量查询易视腾编号成功!",
                    request);
    	}catch (Exception e) {
    		RenderUtils.renderText("查询异常!", response);
            LOGGER.error("Export device ystenId data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, "批量获取易视腾编号", "", "批量查询易视腾编号异常!"+e.getMessage(),
                    request);
        }
    }
    
    
}
