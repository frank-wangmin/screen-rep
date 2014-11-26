package com.ysten.local.bss.common.web.controller;

import com.ysten.boss.systemconfig.domain.SystemConfig;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.HttpStatusCodeException;
import com.ysten.utils.http.HttpUtil;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResult;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private ILoggerWebService loggerWebService;

    @RequestMapping(value = "/to_system_config")
    public String toSystemConfit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/common/system_config_list";
    }

    @RequestMapping(value = "/system_config_list")
    public void findSystemConfig(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Pageable<SystemConfig> page = systemConfigService.findAllSystemConfig();
        RenderUtils.renderJson(JsonUtils.toJson(page), response);
    }

    @RequestMapping(value = "/system_config_query_bykey")
    public void querySystemConfig(@RequestParam(value = "configKey", defaultValue = "") String configKey,
    		@RequestParam(value = "name", defaultValue = "") String name,
    		@RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Pageable<SystemConfig> page = systemConfigService.findSystemConfigByNameAndConfigKey(configKey, name, pageNo, pageSize);
        RenderUtils.renderJson(JsonUtils.toJson(page), response);
    }

    @RequestMapping(value = "/system_config_update")
    public void updateSystemConfig(@RequestParam(value = "configKey", defaultValue = "") String configKey,
            @RequestParam(value = "configValue", defaultValue = "") String configValue, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) {
        boolean flag = systemConfigService.updateSystemConfig(configKey, configValue);
        SystemConfig systemConfig = this.systemConfigService.getSystemConfigInfoByConfigKey(configKey);
        if (flag) {
            RenderUtils.renderJson(JsonUtils.toJson(new JsonResult(JsonResult.STATUS_SUCCESS, "数据已经更新")), response);
            // 记录系统操作日志
            loggerWebService.saveOperateLog(Constants.SYSTEM_PARAM_CONFIG, Constants.MODIFY, systemConfig.getId()+"", "系统参数更新成功."
                    + "configKey：" + configKey + ",configValue:" + configValue, request);
        } else {
            RenderUtils.renderJson(JsonUtils.toJson(new JsonResult(JsonResult.STATUS_FAILED, "修改数据失败")), response);
            // 记录系统操作日志
            loggerWebService.saveOperateLog(Constants.SYSTEM_PARAM_CONFIG, Constants.MODIFY, systemConfig.getId()+"", "系统参数修改失败."
                    + "configKey：" + configKey + ",configValue:" + configValue, request);
        }
    }

    @RequestMapping(value = "/system_config_reload")
    public void loadSystemConfigCache(@RequestParam(value = "configKey", defaultValue = "") String configKey,
            @RequestParam(value = "configValue", defaultValue = "") String configValue, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) {
        String updateConfig = this.systemConfigService.getSystemConfigByConfigKey("updateSystemConfig");
        SystemConfig systemConfig = this.systemConfigService.getSystemConfigInfoByConfigKey(configKey);
        try {
            String result = HttpUtil.get(updateConfig + "?key=" + configKey + "&value=" + configValue, "UTF-8");
            if (result != null) {
                RenderUtils.renderText(ControllerUtil.returnString(true), response);
                // 记录系统操作日志
                loggerWebService.saveOperateLog(Constants.SYSTEM_PARAM_CONFIG, Constants.UPDATE_SYSTEM_PARAM_CACHE, systemConfig.getId()+"",
                        "更新系统参数缓存成功." + "configKey：" + configKey + ",configValue:" + configValue, request);
            } else {
                RenderUtils.renderText(ControllerUtil.returnString(false), response);
                // 记录系统操作日志
                loggerWebService.saveOperateLog(Constants.SYSTEM_PARAM_CONFIG, Constants.UPDATE_SYSTEM_PARAM_CACHE,  systemConfig.getId()+"",
                        "更新系统参数缓存失败." + "configKey：" + configKey + ",configValue:" + configValue, request);
            }
        } catch (HttpStatusCodeException e) {
            // 记录系统操作日志
            loggerWebService.saveOperateLog(Constants.SYSTEM_PARAM_CONFIG, Constants.UPDATE_SYSTEM_PARAM_CACHE, systemConfig.getId()+"",
                    "更新系统参数缓存失败." + "异常信息：" + e.getMessage(), request);
        } catch (IOException e) {
            // 记录系统操作日志
            loggerWebService.saveOperateLog(Constants.SYSTEM_PARAM_CONFIG, Constants.UPDATE_SYSTEM_PARAM_CACHE, systemConfig.getId()+"",
                    "更新系统参数缓存失败." + "异常信息：" + e.getMessage(), request);
        }
    }

    /**
     * load enum constants
     * 
     * @param response
     * @Author frank
     */
    @RequestMapping(value = "/load_enum_constants")
    public void loadEnumConstants(HttpServletResponse response) {
        Map<String, List<TextValue>> map = new HashMap<String, List<TextValue>>();
        List<TextValue> deviceGroupTypeList = new ArrayList<TextValue>();
        List<TextValue> deviceStatusList = new ArrayList<TextValue>();
        List<TextValue> bindTypeList = new ArrayList<TextValue>();
        List<TextValue> statusList = new ArrayList<TextValue>();
        List<TextValue> packageTypeList = new ArrayList<TextValue>();
        List<TextValue> packageStatusList = new ArrayList<TextValue>();
        List<TextValue> mandatoryStatusList = new ArrayList<TextValue>();
        List<TextValue> serviceCollectTypeList = new ArrayList<TextValue>();

        deviceGroupTypeList.add(new TextValue("", "全部"));
        for (EnumConstantsInterface.DeviceGroupType groupType : EnumConstantsInterface.DeviceGroupType.values()) {
            deviceGroupTypeList.add(new TextValue(groupType.name(), groupType.getDisplayName()));
        }

        for (EnumConstantsInterface.DeviceStatus deviceStatus : EnumConstantsInterface.DeviceStatus.values()) {
            deviceStatusList.add(new TextValue(deviceStatus.name(), deviceStatus.getDisplayName()));
        }

        for (EnumConstantsInterface.BindType bindType : EnumConstantsInterface.BindType.values()) {
            bindTypeList.add(new TextValue(bindType.name(), bindType.getDisplayName()));
        }

        for (EnumConstantsInterface.Status status : EnumConstantsInterface.Status.values()) {
            statusList.add(new TextValue(status.name(), status.getDisplayName()));
        }

        for (EnumConstantsInterface.PackageType packageType : EnumConstantsInterface.PackageType.values()) {
            packageTypeList.add(new TextValue(packageType.name(), packageType.getDisplayName()));
        }

        for (EnumConstantsInterface.PackageStatus packageStatus : EnumConstantsInterface.PackageStatus.values()) {
            packageStatusList.add(new TextValue(packageStatus.name(), packageStatus.getDisplayName()));
        }

        for (EnumConstantsInterface.MandatoryStatus mandatoryStatus : EnumConstantsInterface.MandatoryStatus.values()) {
            mandatoryStatusList.add(new TextValue(mandatoryStatus.name(), mandatoryStatus.getDisplayName()));
        }

        for (EnumConstantsInterface.ServiceCollectType serviceCollectType : EnumConstantsInterface.ServiceCollectType
                .values()) {
            serviceCollectTypeList.add(new TextValue(serviceCollectType.name(), serviceCollectType.getDisplayName()));
        }

        map.put("DeviceStatus", deviceStatusList);
        map.put("BindType", bindTypeList);
        map.put("Status", statusList);
        map.put("PackageType", packageTypeList);
        map.put("PackageStatus", packageStatusList);
        map.put("MandatoryStatus", mandatoryStatusList);
        map.put("ServiceCollectType", serviceCollectTypeList);
        map.put("DeviceGroupType", deviceGroupTypeList);
        RenderUtils.renderJson(JsonUtils.toJson(map), response);
    }

    @RequestMapping(value = "/get_sysconfig_by_key.json")
    @ResponseBody
    public SystemConfig getSystemConfigByKey(String key){
        if(StringUtils.isNotBlank(key)){
            SystemConfig systemConfig = systemConfigService.getSystemConfigInfoByConfigKey(key);
            return systemConfig;
        }
        return null;
    }

}
