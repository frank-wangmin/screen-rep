package com.ysten.local.bss.logger.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.common.web.controller.ExportController;
import com.ysten.local.bss.enums.OnOff;
import com.ysten.local.bss.logger.domain.DeviceOperateLog;
import com.ysten.local.bss.logger.service.IDeviceOperateLogService;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.page.Pageable;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by frank on 14-7-8.
 */
@Controller
public class DeviceOperateLogController extends ExportController {

    public static final String DEVICE_OPERATE_LOG = "Device_Operate_Log_";
    private static Logger LOGGER = LoggerFactory.getLogger(LoggerController.class);
    @Autowired
    private IDeviceOperateLogService deviceOperateLogService;
    @Autowired
    private ILoggerWebService loggerWebService;
    @RequestMapping(value = "/to_device_operate_log_list.shtml")
    public String toDeviceOperateLog() {
        return "/logger/device_operate_log_list";
    }

    @ResponseBody
    @RequestMapping(value = "/device_operate_log_list.json")
    public Pageable<DeviceOperateLog> findDeviceOperateLog(@RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
                                                           @RequestParam(value = "ip", defaultValue = "") String ip,
                                                           @RequestParam(value = "status", defaultValue = "") String status,
                                                           @RequestParam(value = "result", defaultValue = "") String result,
                                                           @RequestParam(value = "start", defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                                                           @RequestParam(value = "limit", defaultValue = Constants.PAGE_NUMBER) String pageSize) {
        Pageable<DeviceOperateLog> page = deviceOperateLogService.findDeviceOperateLogPagination(deviceCode, ip, status,result,
                Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        return page;
    }

    @RequestMapping(value = "/device_operate_log_export.json")
    public void exportDeviceOperateLog(@RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
                                       @RequestParam(value = "ip", defaultValue = "") String ip,
                                       @RequestParam(value = "status", defaultValue = "") String status,
                                       @RequestParam(value = "result", defaultValue = "") String result,
                                       HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> heads = new ArrayList<String>();
            heads.add("终端编号");
            heads.add("IP");
            heads.add("状态");
            heads.add("创建日期");
            heads.add("响应结果");
            List<DeviceOperateLog> deviceOperateLogList = deviceOperateLogService.findDeviceOperateLog(deviceCode, ip, status,result);

            //转换status
            formatData(deviceOperateLogList);
            String fileName = DEVICE_OPERATE_LOG + DateUtils.getDate2String(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            this.export(heads, deviceOperateLogList, excludes, DeviceOperateLog.class, fileName, request, response);
            loggerWebService.saveOperateLog(Constants.OPERATE_LOG_INFO_MAINTAIN, "导出",
              		"", "导出成功!", request);
        } catch (Exception e) {
        	RenderUtils.renderText("导出异常!", response);
            LOGGER.error("Export device operate log data exception{}", e);
            loggerWebService.saveOperateLog(Constants.OPERATE_LOG_INFO_MAINTAIN, "导出",
              		"", "导出异常!" +e.getMessage(), request);
        }
    }

    private void formatData(List<DeviceOperateLog> deviceOperateLogList) {
        if (CollectionUtils.isEmpty(deviceOperateLogList)) return;
        for (DeviceOperateLog deviceOperateLog : deviceOperateLogList) {
            if (StringUtils.isBlank(deviceOperateLog.getStatus())) continue;
            if (StringUtils.equals(deviceOperateLog.getStatus(), OnOff.ON.getValue())) {
                deviceOperateLog.setStatus(OnOff.ON.getName());
            } else {
                deviceOperateLog.setStatus(OnOff.OFF.getName());
            }
        }
    }
}
