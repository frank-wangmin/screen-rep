package com.ysten.local.bss.upgrade.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.ApkSoftwareCode;
import com.ysten.local.bss.device.domain.ApkSoftwarePackage;
import com.ysten.local.bss.device.domain.ApkUpgradeMap;
import com.ysten.local.bss.device.domain.ApkUpgradeTask;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IApkUpgradeTaskService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ApkUpgradeTaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApkUpgradeTaskController.class);
    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private IApkUpgradeTaskService apkUpgradeTaskService;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/to_apk_upgrade_task_list")
    public String toUpgradeTaskPage(ModelMap model) {
        return "/upgrade/apk_upgrade_task_list";
    }

    @RequestMapping(value = "/find_apk_upgrade_task_list.json")
    public void findApkUpgradeTaskList(
            @RequestParam(value = "softwarePackageName", defaultValue = "") String softwarePackageName,
            @RequestParam(value = "softCodeName", defaultValue = "") String softCodeName,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<ApkUpgradeTask> pageable = this.apkUpgradeTaskService.findApkUpgradeTaskListBySoftCodeAndPackage(softwarePackageName, softCodeName,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "get_apk_upgrade_task_list.json")
    public void findAllUpgradeTaskList(@RequestParam(value = "areaId", defaultValue = "") Long areaId,
                                       HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "请选择"));
        List<ApkUpgradeTask> list = this.apkUpgradeTaskService.findAllUpgradeTask();
        try {
            if (list != null && list.size() > 0) {
                for (ApkUpgradeTask upgradeTask : list) {
                    tv.add(new TextValue(upgradeTask.getId().toString(), upgradeTask.getTaskName()));
                }
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            LOGGER.error("Get All Apk Upgrade Task Exception: " + LoggerUtils.printErrorStack(e));
        }
    }

    @RequestMapping(value = "/apkUpgrade_task_to_update.json")
    public void getUpgradeTaskInfo(@RequestParam(value = "id", defaultValue = "") String id,
                                   HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            ApkUpgradeTask upgradeTask = this.apkUpgradeTaskService.getById(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(upgradeTask), response);
        }
    }

    @RequestMapping(value = "/apkUpgrade_task_add.json")
    public void addApkUpgradeTask(
            @RequestParam(value = "taskName", defaultValue = "") String taskName,
            @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
            @RequestParam(value = "softwarePackageId", defaultValue = "") String softwarePackageId,
            @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "vendorIds", defaultValue = "") String vendorIds,
            @RequestParam(value = "maxNum", defaultValue = "") String maxNum,
            @RequestParam(value = "timeInterval", defaultValue = "") String timeInterval,
            @RequestParam(value = "isAll", defaultValue = "") String isAll,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate, HttpServletResponse response,
            HttpServletRequest request) throws ParseException {
        ApkUpgradeTask upgradeTask = new ApkUpgradeTask();
        upgradeTask.setTaskName(taskName);
        upgradeTask.setMaxNum(Integer.parseInt(maxNum));
        upgradeTask.setTimeInterval(Integer.parseInt(timeInterval));
        upgradeTask.setIsAll(Integer.parseInt(isAll));
        upgradeTask.setCreateDate(new Date());
        upgradeTask.setLastModifyTime(new Date());
        Operator op = ControllerUtil.getLoginOperator(request);
        upgradeTask.setOperUser(op.getLoginName());
        if (StringUtils.isNotBlank(startDate)) {
            upgradeTask.setStartDate(format.parse(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            upgradeTask.setEndDate(format.parse(endDate));
        }
        if (StringUtils.isNotBlank(softCodeId)) {
            ApkSoftwareCode softCode = new ApkSoftwareCode();
            softCode.setId(Long.parseLong(softCodeId));
            upgradeTask.setSoftCodeId(softCode);
        }
        if (StringUtils.isNotBlank(softwarePackageId)) {
            ApkSoftwarePackage deviceSoftwarePackage = new ApkSoftwarePackage();
            deviceSoftwarePackage.setId(Long.parseLong(softwarePackageId));
            upgradeTask.setSoftwarePackageId(deviceSoftwarePackage);
        }
        boolean bool = this.apkUpgradeTaskService.save(upgradeTask);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "新增APK升级任务信息成功！" : "新增APK升级任务信息失败！";
        description += "升级任务名称:" + upgradeTask.getTaskName() + ";软件号：" + upgradeTask.getSoftCodeId() + ";软件包：" + upgradeTask.getSoftwarePackageId()
                + ";最大升级终端数：" + upgradeTask.getMaxNum() + ";最大间隔时间" + upgradeTask.getTimeInterval();
        this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_TASK_MAINTAIN, Constants.ADD, upgradeTask.getId() + "",
                description, request);
    }

    @RequestMapping(value = "/apkUpgrade_task_update.json")
    public void updateApkUpgradeTask(
            @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
            @RequestParam(value = "softwarePackageId", defaultValue = "") String softwarePackageId,
            @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "vendorIds", defaultValue = "") String vendorIds,
            @RequestParam(value = "maxNum", defaultValue = "") String maxNum,
            @RequestParam(value = "timeInterval", defaultValue = "") String timeInterval,
            @RequestParam(value = "isAll", defaultValue = "") String isAll,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "taskName", defaultValue = "") String taskName,
            @RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response,
            HttpServletRequest request) throws ParseException {
        ApkUpgradeTask upgradeTask = new ApkUpgradeTask();
        upgradeTask.setId(Long.parseLong(id));
        upgradeTask.setTaskName(taskName);
        upgradeTask.setMaxNum(Integer.parseInt(maxNum));
        upgradeTask.setTimeInterval(Integer.parseInt(timeInterval));
        upgradeTask.setIsAll(Integer.parseInt(isAll));
        upgradeTask.setLastModifyTime(new Date());
        Operator op = ControllerUtil.getLoginOperator(request);
        upgradeTask.setOperUser(op.getLoginName());
        if (StringUtils.isNotBlank(startDate)) {
            upgradeTask.setStartDate(format.parse(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            upgradeTask.setEndDate(format.parse(endDate));
        }
        if (StringUtils.isNotBlank(softCodeId)) {
            ApkSoftwareCode softCode = new ApkSoftwareCode();
            softCode.setId(Long.parseLong(softCodeId));
            upgradeTask.setSoftCodeId(softCode);
        }
        if (StringUtils.isNotBlank(softwarePackageId)) {
            ApkSoftwarePackage deviceSoftwarePackage = new ApkSoftwarePackage();
            deviceSoftwarePackage.setId(Long.parseLong(softwarePackageId));
            upgradeTask.setSoftwarePackageId(deviceSoftwarePackage);
        }
        boolean bool = this.apkUpgradeTaskService.updateById(upgradeTask);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "更新APK升级任务信息成功！" : "更新APK升级任务信息失败！";
        description += "升级任务名称:" + upgradeTask.getTaskName() + "软件号：" + upgradeTask.getSoftCodeId() + ";软件包：" + upgradeTask.getSoftwarePackageId()
                + ";最大升级终端数：" + upgradeTask.getMaxNum() + ";最大间隔时间" + upgradeTask.getTimeInterval();
        this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_TASK_MAINTAIN, Constants.ADD, upgradeTask.getId() + "",
                description, request);
    }

    @RequestMapping(value = "/apkUpgrade_task_delete.json")
    public void deleteUpgradeTask(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                  HttpServletResponse response) {
        List<Long> idsList = StringUtil.splitToLong(ids);
        String rsp = this.apkUpgradeTaskService.deleteUpgradeTaskByIds(idsList);
        String logDescription = null;
        if (StringUtils.isBlank(rsp)) {
            logDescription = "删除APK升级任务信息成功!";
        } else {
            logDescription = "删除APK升级任务信息失败!" + rsp;
        }
        RenderUtils.renderText(logDescription, response);
        this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_TASK_MAINTAIN, Constants.DELETE, ids, logDescription,
                request);
    }

    @RequestMapping(value = "get_app_upgrade_device")
    public void getBootAnimationDeviceMap(@RequestParam(Constants.ID) String id,
                                          @RequestParam(value = "type", defaultValue = "") String type,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("");
        if (StringUtils.isNotBlank(id)) {
            List<ApkUpgradeMap> mapList = apkUpgradeTaskService.findMapByUpgradeIdAndType(Long.parseLong(id), type);
            for (ApkUpgradeMap map : mapList) {
                if (Constants.DEVICE_NOTICT_MAP_DEVICE.equals(type)) {
                    sb.append(map.getYstenId()).append(",");
                } else if (Constants.DEVICE_NOTICT_MAP_GROUP.equals(type)) {
                    sb.append(map.getDeviceGroupId()).append(",");
                }
            }
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            RenderUtils.renderText(sb.substring(0, sb.length() - 1).toString(), response);
        } else {
            RenderUtils.renderText(sb.toString(), response);
        }
    }

    @RequestMapping(value = "apk_upgradeTask_bind")
    public void bindBootAnimation(@RequestParam(Constants.IDS) String ids, @RequestParam(value = "areaIds", defaultValue = "") String areaIds,
                                  @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                                  @RequestParam(value = "deviceCodes", required = false) MultipartFile deviceCodeFile,
                                  @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                  @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
                                  HttpServletResponse response) {
        try {

            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String deviceCodes = FileUtils.getDeviceCodesFromUploadFile(deviceCodeFile, path);

            String result = null;
            int i = 0;
            if (StringUtils.isNotBlank(ids)) {
                result = this.apkUpgradeTaskService.saveUpgradeTaskMap(Long.parseLong(ids), areaIds, deviceGroupIds, deviceCodes,
                        userGroupIds, userIds);
            }
            String logDescription = null;
            if (StringUtils.isBlank(result)) {
                logDescription = "绑定APK升级任务信息成功!";
            } else {
                logDescription = (i == 1 ? result : "绑定APK升级任务信息失败!" + "\n" + result);
            }
            RenderUtils.renderText(logDescription, response);
            loggerWebService.saveOperateLog(Constants.APK_UPGRADE_TASK_MAINTAIN, "绑定设备和分组", ids + "", logDescription, request);
        } catch (Exception e) {
            RenderUtils.renderText("绑定APK升级任务信息异常!", response);
            RenderUtils.renderText("绑定异常!" + e.getMessage(), response);
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/apk_upgrade_task_map_delete")
    public void deleteUserGroupMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.apkUpgradeTaskService.deleteUpgradeTaskMap(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_TASK_MAINTAIN, "解绑设备和分组", ids,
                        (bool == true) ? "解绑升级任务成功!" : "解绑升级任务失败!", request);
            }
        } catch (Exception e) {
            RenderUtils.renderText(ControllerUtil.returnString(false), response);
            LOGGER.error("Delete Unbind APk Upgrade Task Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.APK_UPGRADE_TASK_MAINTAIN, "解绑设备和分组", ids,
                    "解绑升级任务异常!", request);
        }
    }
}
