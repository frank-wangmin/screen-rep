package com.ysten.local.bss.logger.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.common.web.controller.ExportController;
import com.ysten.local.bss.device.domain.ApkUpgradeResultLog;
import com.ysten.local.bss.device.domain.DeviceUpgradeResultLog;
import com.ysten.local.bss.logger.domain.InterfaceLog;
import com.ysten.local.bss.logger.domain.OperateLog;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.system.service.SystemService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Controller
public class LoggerController extends ExportController {
    private static Logger LOGGER = LoggerFactory.getLogger(LoggerController.class);
    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/to_interface_log_list")
    public String toInterfaceLog(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/logger/interface_log_list";
    }

    @RequestMapping(value = "/to_device_upgrade_result_log_list")
    public String toDeviceUpgradeResultLog(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/logger/device_upgrade_result_log_list";
    }

    @RequestMapping(value = "/to_apk_upgrade_result_log_list")
    public String toApkUpgradeResultLog(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/logger/apk_upgrade_result_log_list";
    }

    @RequestMapping(value = "/to_operate_log_list")
    public String toOperateLogList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/logger/operate_log_list";
    }

    @RequestMapping(value = "/interface_log_list")
    public void findAllInterfaceLog(@RequestParam(value = "interfaceName", defaultValue = "") String interfaceName,
            @RequestParam(value = "caller", defaultValue = "") String caller,
            @RequestParam(value = "output", defaultValue = "") String output,
            @RequestParam(value = "input", defaultValue = "") String input,
            @RequestParam(value = "start", defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = "limit", defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Pageable<InterfaceLog> page = this.loggerWebService.findAllInterfaceLog(interfaceName, caller, input, output,
                Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        RenderUtils.renderJson(JsonUtils.toJson(page), response);
    }

    @RequestMapping(value = "/interface_log_export_by_query.json")
    public void exportAllInterfaceLog(@RequestParam(value = "interfaceName", defaultValue = "") String interfaceName,
            @RequestParam(value = "caller", defaultValue = "") String caller,
            @RequestParam(value = "output", defaultValue = "") String output,
            @RequestParam(value = "input", defaultValue = "") String input,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        try {
            List<String> heads = new ArrayList<String>();
            heads.add("接口名称");
            heads.add("访问系统");
            heads.add("访问时间");
            heads.add("输入参数");
            heads.add("响应结果");
            List<InterfaceLog> logs = this.loggerWebService.findExportInterfaceLog(interfaceName, caller, input, output);
            if (logs.size() > 20000) {
                RenderUtils.renderText("导出记录不能超过2万条!", response);
                this.loggerWebService.saveOperateLog(Constants.INTERFACE_LOG_INFO_MAINTAIN, "导出", "", "导出失败!导出记录超过2万条", request);
                return;
            }
            if (logs.size() == 0) {
                RenderUtils.renderText("没有要导出的数据!", response);
                this.loggerWebService.saveOperateLog(Constants.INTERFACE_LOG_INFO_MAINTAIN, "导出", "", "导出失败!没有查询出要导出的数据", request);
                return;
            }
            String fileName = "Interface_Log_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            this.export(heads, logs, excludes, InterfaceLog.class, fileName, request, response);
            loggerWebService.saveOperateLog(Constants.INTERFACE_LOG_INFO_MAINTAIN, "导出",
              		"", "导出成功!", request);
        } catch (Exception e) {
        	RenderUtils.renderText("导出异常!", response);
            LOGGER.error("Export interface log data by query exception{}", e);
            loggerWebService.saveOperateLog(Constants.INTERFACE_LOG_INFO_MAINTAIN, "导出",
              		"", "导出异常!"+e.getMessage(), request);
        }
    }
    @RequestMapping(value = "/interface_log_export_by_ids.json")
    public void exportInterfaceLogByIds(@RequestParam(Constants.IDS) String ids,
            HttpServletRequest request, HttpServletResponse response) {
    	try{
    		List<String> heads = new ArrayList<String>();
            heads.add("接口名称");
            heads.add("访问系统");
            heads.add("访问时间");
            heads.add("输入参数");
            heads.add("响应结果");
            List<InterfaceLog> logs = null;
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                logs = this.loggerWebService.findInterfaceLogByIds(idsList);
            }
            String fileName = "Interface_Log_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            this.export(heads, logs, excludes, InterfaceLog.class, fileName, request, response);
            loggerWebService.saveOperateLog(Constants.INTERFACE_LOG_INFO_MAINTAIN, "导出",
              		"", "导出成功!", request);
    	}catch (Exception e) {
    		RenderUtils.renderText("导出异常!", response);
            LOGGER.error("Export interface log data by ids exception{}", e);
            loggerWebService.saveOperateLog(Constants.INTERFACE_LOG_INFO_MAINTAIN, "导出",
              		"", "导出异常!"+e.getMessage(), request);
            }
    }
    @RequestMapping(value = "/operator_logger_list.json")
    public void loggerSearch(@RequestParam(value = "modelName", defaultValue = "") String modelName,
            @RequestParam(value = "operator", defaultValue = "") String operator,
            @RequestParam(value = "operatorId", defaultValue = "") String operatorId,
            @RequestParam(value = "beginDate", defaultValue = "") String beginDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "start", defaultValue = "1") Integer start,
            @RequestParam(value = "limit") Integer limit, ModelMap model, HttpServletResponse response) {
        Pageable<OperateLog> loggerPage = this.loggerWebService.findOperatorLogger(modelName, operator, operatorId,
                beginDate, endDate, start, limit);
        RenderUtils.renderJson(JsonUtils.toJson(loggerPage), response);
    }

    @RequestMapping(value = "/model_name_list.json")
    public void modelNameList(HttpServletResponse response) {
        //List<SysMenu> sysMenuList = this.systemService.findSysMenus();
    	List<String> list = loggerWebService.getModuleNameList();
        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "所有模块"));
        for (String name : list) {
        	tv.add(new TextValue(name, name));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/logger_operator_list.json")
    public void operatorList(HttpServletResponse response) {
        List<Operator> operatorList = this.systemService.findAllOperator();
        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "所有用户"));
        for (Operator operator : operatorList) {
        	tv.add(new TextValue(operator.getLoginName(), operator.getLoginName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/device_upgrade_result_log_list")
    public void findAllDeviceUpgradeResultLog(@RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
    		@RequestParam(value = "ystenId", defaultValue = "") String ystenId,
            @RequestParam(value = "start", defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
            @RequestParam(value = "limit", defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Pageable<DeviceUpgradeResultLog> page = this.loggerWebService.findDeviceUpgradeResultLogByCodeAndYstId(deviceCode, ystenId, pageNo,
                pageSize);
        RenderUtils.renderJson(JsonUtils.toJson(page), response);
    }
    @RequestMapping(value = "/device_upgrade_result_list_by_conditions.json")
    public void findDeviceUpgradeResultLogByCondition(@RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
    		@RequestParam(value = "ystenId", defaultValue = "") String ystenId,
    		@RequestParam(value = "state", defaultValue = "") String state,
    		@RequestParam(value = "softCode", defaultValue = "") String softCode,
    		@RequestParam(value = "distCode", defaultValue = "") String distCode,
    		@RequestParam(value = "deviceVersionSeq", defaultValue = "") String deviceVersionSeq,
    		@RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "start", defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
            @RequestParam(value = "limit", defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	try{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ystenIds", ystenId);
            map.put("deviceCodes", deviceCode);
            map.put("state", state);
            map.put("softCode", softCode);
            map.put("deviceVersionSeq", deviceVersionSeq);
            map.put("versionSeq", versionSeq);
            map.put("endDate", StringUtils.isNotBlank(endDate) ? sdf.parse(endDate + " 23:59:59") : null);
            map.put("startDate", StringUtils.isNotBlank(startDate) ? sdf.parse(startDate + " 00:00:00") : null);
            map.put("pageNo", pageNo);
            map.put("pageSize", pageSize);
            if(!distCode.equals("")){
            	map.put("distCode", distCode.substring(2, 6).equals("0000")?distCode.substring(0, 2):distCode);
            }else{
            	map.put("distCode", distCode);
            }
            Pageable<DeviceUpgradeResultLog> page = this.loggerWebService.findDeviceUpgradeResultLogByCondition(map);
            RenderUtils.renderJson(JsonUtils.toJson(page), response);
    	}catch (Exception e) {
            LOGGER.error("Height Query device upgrade result List data exception{}", e);
        }
    }
    @RequestMapping(value = "/device_upgrade_log_export_by_query.json")
    public void exportDeviceUpgradeLogByQuery(@RequestParam(value = "deviceCodeValue", defaultValue = "") String deviceCodeValue,
    		@RequestParam(value = "ystenValue", defaultValue = "") String ystenValue,
    		@RequestParam(value = "stateValue", defaultValue = "") String stateValue,
    		@RequestParam(value = "softCodevalue", defaultValue = "") String softCodevalue,
    		@RequestParam(value = "deviceCodeHvalue", defaultValue = "") String deviceCodeHvalue,
    		@RequestParam(value = "ystenIdHvalue", defaultValue = "") String ystenIdHvalue,
    		@RequestParam(value = "distCodeValue", defaultValue = "") String distCodeValue,
    		@RequestParam(value = "deviceVersionSeqValue", defaultValue = "") String deviceVersionSeqValue,
    		@RequestParam(value = "versionSeqValue", defaultValue = "") String versionSeqValue,
            @RequestParam(value = "startDateValue", defaultValue = "") String startDateValue,
            @RequestParam(value = "endDateValue", defaultValue = "") String endDateValue,
            HttpServletRequest request, HttpServletResponse response) {
    	try{
    		List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("区域");
            heads.add("软件信息号");
            heads.add("设备旧版本号");
            heads.add("设备新版本号");
            heads.add("状态");
            heads.add("持续时间");
            heads.add("创建时间");
            List<DeviceUpgradeResultLog> deviceLogs = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ystenValue", ystenValue);
            map.put("deviceCodeValue", deviceCodeValue);
            map.put("stateValue", stateValue);
            map.put("softCodevalue", softCodevalue);
            map.put("deviceVersionSeqValue", deviceVersionSeqValue);
            map.put("versionSeqValue", versionSeqValue);
            map.put("endDateValue", StringUtils.isNotBlank(endDateValue) ? sdf.parse(endDateValue + " 23:59:59") : null);
            map.put("startDateValue", StringUtils.isNotBlank(startDateValue) ? sdf.parse(startDateValue + " 00:00:00") : null);
            map.put("deviceCodeHvalue", deviceCodeHvalue);
            map.put("ystenIdHvalue", ystenIdHvalue);
            if(!distCodeValue.equals("")){
            	map.put("distCodeValue", distCodeValue.substring(2, 6).equals("0000")?distCodeValue.substring(0, 2):distCodeValue);
            }else{
            	map.put("distCodeValue", distCodeValue);
            }
            deviceLogs = this.loggerWebService.findExportDeviceUpgradeResultLog(map);
            if (deviceLogs.size() > 20000) {
                RenderUtils.renderText("导出记录不能超过2万条!", response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出失败!导出记录超过2万条", request);
                return;
            }
            if (deviceLogs.size() == 0) {
                RenderUtils.renderText("没有要导出的数据!", response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出失败!没有查询出要导出的数据", request);
                return;
            }
            String fileName = "Device_upgrade_result_log_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("status");
            this.export(heads, deviceLogs, excludes, DeviceUpgradeResultLog.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出成功!", request);
    	}catch (Exception e) {
            RenderUtils.renderText("导出数据异常!", response);
            LOGGER.error("Export device upgrade result List data by query exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(), request);
        }
    }
    @RequestMapping(value = "/device_upgrade_log_export_byIds.json")
    public void exportDeviceUpgradeLogByIds(@RequestParam(Constants.IDS) String ids,
            HttpServletRequest request, HttpServletResponse response) {
    	try{
    		List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("区域");
            heads.add("软件信息号");
            heads.add("设备旧版本号");
            heads.add("设备新版本号");
            heads.add("状态");
            heads.add("持续时间");
            heads.add("创建时间");
            List<DeviceUpgradeResultLog> deviceLogs = null;
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                deviceLogs = this.loggerWebService.findDeviceUpgradeResultLogByIds(idsList);
            }
            String fileName = "Device_upgrade_result_log_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("status");
            this.export(heads, deviceLogs, excludes, DeviceUpgradeResultLog.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出成功!", request);
    	}catch (Exception e) {
            RenderUtils.renderText("导出数据异常!", response);
            LOGGER.error("Export device upgrade result List data by ids exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(), request);
        }
    }
    @RequestMapping(value = "/apk_upgrade_result_log_list")
    public void findAllApkUpgradeResultLog(@RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
                                              @RequestParam(value = "ystenId", defaultValue = "") String ystenId,
                                              @RequestParam(value = "start", defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
                                              @RequestParam(value = "limit", defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
                                              HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Pageable<ApkUpgradeResultLog> page = this.loggerWebService.findApkUpgradeResultLogByCodeAndYstId(deviceCode, ystenId, pageNo,
                pageSize);
        RenderUtils.renderJson(JsonUtils.toJson(page), response);
    }
    @RequestMapping(value = "/apk_upgrade_result_list_by_conditions.json")
    public void findApkUpgradeResultLogByCondition(@RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
    		@RequestParam(value = "ystenId", defaultValue = "") String ystenId,
    		@RequestParam(value = "state", defaultValue = "") String state,
    		@RequestParam(value = "softCode", defaultValue = "") String softCode,
    		@RequestParam(value = "distCode", defaultValue = "") String distCode,
    		@RequestParam(value = "deviceVersionSeq", defaultValue = "") String deviceVersionSeq,
    		@RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "start", defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
            @RequestParam(value = "limit", defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	try{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ystenIds", ystenId);
            map.put("deviceCodes", deviceCode);
            map.put("state", state);
            map.put("softCode", softCode);
            map.put("deviceVersionSeq", deviceVersionSeq);
            map.put("versionSeq", versionSeq);
            map.put("endDate", StringUtils.isNotBlank(endDate) ? sdf.parse(endDate + " 23:59:59") : null);
            map.put("startDate", StringUtils.isNotBlank(startDate) ? sdf.parse(startDate + " 00:00:00") : null);
            map.put("pageNo", pageNo);
            map.put("pageSize", pageSize);
            if(!distCode.equals("")){
            	map.put("distCode", distCode.substring(2, 6).equals("0000")?distCode.substring(0, 2):distCode);
            }else{
            	map.put("distCode", distCode);
            }
            Pageable<ApkUpgradeResultLog> page = this.loggerWebService.findApkUpgradeResultLogByCondition(map);
            RenderUtils.renderJson(JsonUtils.toJson(page), response);
    	}catch (Exception e) {
            LOGGER.error("Height Query apk upgrade result List data exception{}", e);
        }
    }
    @RequestMapping(value = "/apk_upgrade_log_export_byIds.json")
    public void exportApkUpgradeLogByIds(@RequestParam(Constants.IDS) String ids,
            HttpServletRequest request, HttpServletResponse response) {
    	try{
    		List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("区域");
            heads.add("软件信息号");
            heads.add("设备旧版本号");
            heads.add("设备新版本号");
            heads.add("状态");
            heads.add("持续时间");
            heads.add("创建时间");
            List<ApkUpgradeResultLog> apkLogs = null;
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                apkLogs = this.loggerWebService.findApkUpgradeResultLogByIds(idsList);
            }
            String fileName = "Apk_upgrade_result_log_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("status");
            this.export(heads, apkLogs, excludes, ApkUpgradeResultLog.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出成功!", request);
    	}catch (Exception e) {
            RenderUtils.renderText("导出数据异常!", response);
            LOGGER.error("Export apk upgrade result List data by ids exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(), request);
        }
    }
    @RequestMapping(value = "/apk_upgrade_log_export_by_query.json")
    public void exportApkUpgradeLogByQuery(@RequestParam(value = "deviceCodeValue", defaultValue = "") String deviceCodeValue,
    		@RequestParam(value = "ystenValue", defaultValue = "") String ystenValue,
    		@RequestParam(value = "stateValue", defaultValue = "") String stateValue,
    		@RequestParam(value = "softCodevalue", defaultValue = "") String softCodevalue,
    		@RequestParam(value = "deviceCodeHvalue", defaultValue = "") String deviceCodeHvalue,
    		@RequestParam(value = "ystenIdHvalue", defaultValue = "") String ystenIdHvalue,
    		@RequestParam(value = "distCodeValue", defaultValue = "") String distCodeValue,
    		@RequestParam(value = "deviceVersionSeqValue", defaultValue = "") String deviceVersionSeqValue,
    		@RequestParam(value = "versionSeqValue", defaultValue = "") String versionSeqValue,
            @RequestParam(value = "startDateValue", defaultValue = "") String startDateValue,
            @RequestParam(value = "endDateValue", defaultValue = "") String endDateValue,
            HttpServletRequest request, HttpServletResponse response) {
    	try{
    		List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("区域");
            heads.add("软件信息号");
            heads.add("设备旧版本号");
            heads.add("设备新版本号");
            heads.add("状态");
            heads.add("持续时间");
            heads.add("创建时间");
            List<ApkUpgradeResultLog> deviceLogs = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ystenValue", ystenValue);
            map.put("deviceCodeValue", deviceCodeValue);
            map.put("stateValue", stateValue);
            map.put("softCodevalue", softCodevalue);
            map.put("endDateValue", StringUtils.isNotBlank(endDateValue) ? sdf.parse(endDateValue + " 23:59:59") : null);
            map.put("startDateValue", StringUtils.isNotBlank(startDateValue) ? sdf.parse(startDateValue + " 00:00:00") : null);
            map.put("deviceCodeHvalue", deviceCodeHvalue);
            map.put("ystenIdHvalue", ystenIdHvalue);
            map.put("deviceVersionSeqValue", deviceVersionSeqValue);
            map.put("versionSeqValue", versionSeqValue);
            if(!distCodeValue.equals("")){
            	map.put("distCodeValue", distCodeValue.substring(2, 6).equals("0000")?distCodeValue.substring(0, 2):distCodeValue);
            }else{
            	map.put("distCodeValue", distCodeValue);
            }
            deviceLogs = this.loggerWebService.findExportApkUpgradeResultLog(map);
            if (deviceLogs.size() > 20000) {
                RenderUtils.renderText("导出记录不能超过2万条!", response);
                this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出失败!导出记录超过2万条", request);
                return;
            }
            if (deviceLogs.size() == 0) {
                RenderUtils.renderText("没有要导出的数据!", response);
                this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出失败!没有查询出要导出的数据", request);
                return;
            }
            String fileName = "Apk_upgrade_result_log_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("status");
            this.export(heads, deviceLogs, excludes, ApkUpgradeResultLog.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出成功!", request);
    	}catch (Exception e) {
            RenderUtils.renderText("导出数据异常!", response);
            LOGGER.error("Export apk upgrade result List data by query exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_RESULT_LOG_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(), request);
        }
    }
}
