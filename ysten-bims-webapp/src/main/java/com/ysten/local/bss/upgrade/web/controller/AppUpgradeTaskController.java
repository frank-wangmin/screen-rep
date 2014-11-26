package com.ysten.local.bss.upgrade.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IAppUpgradeTaskService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AppUpgradeTaskController {

    // private Logger LOGGER =
    // LoggerFactory.getLogger(UpgradeTaskManagerController.class);

    @Autowired
    private IAppUpgradeTaskService appUpgradeTaskService;
    @Autowired
    private ILoggerWebService loggerWebService;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUpgradeTaskController.class);

    @RequestMapping(value = "/app_upgrade_task_page")
    public String toUpgradeTaskPage(ModelMap model) {
        return "/upgrade/app_upgrade_task_list";
    }

    @RequestMapping(value = "/find_app_upgrade_task_list.json")
    public void getUpgradeTaskList(
            @RequestParam(value = "softwarePackageId", defaultValue = "") Long softwarePackageId,
            @RequestParam(value = "softCodeId", defaultValue = "") Long softCodeId,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Long softwarePackage = softwarePackageId != null && softwarePackageId == 0 ? null : softwarePackageId;
        Long softCode = softCodeId != null && softCodeId == 0 ? null : softCodeId;
        Pageable<AppUpgradeTask> pageable = this.appUpgradeTaskService.getListByCondition(softwarePackage, softCode,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/find_app_upgrade_task_list_by_names.json")
    public void findUpgradeTaskList(
            @RequestParam(value = "softwarePackageName", defaultValue = "") String softwarePackageName,
            @RequestParam(value = "softCodeName", defaultValue = "") String softCodeName,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<AppUpgradeTask> pageable = this.appUpgradeTaskService.findAppUpgradeTaskListByCondition(softwarePackageName, softCodeName,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/app_upgrade_task_delete.json")
    public void deleteUpgradeTask(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                  HttpServletResponse response) {
        String result = "";
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            result = this.appUpgradeTaskService.deleteByIds(idsList);
        }
        RenderUtils.renderText(result, response);
        this.loggerWebService.saveOperateLog(Constants.APP_UPGRADE_TASK_MAINTAIN, Constants.DELETE, ids,
                (result.indexOf(Constants.SUCCESS) > 0) ? "删除应用升级任务信息成功" : "删除应用升级任务信息失败", request);
    }

    @RequestMapping(value = "/app_upgrade_task_to_update.json")
    public void getUpgradeTaskInfo(@RequestParam(value = "id", defaultValue = "") String id,
                                   HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            AppUpgradeTask upgradeTask = this.appUpgradeTaskService.getById(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(upgradeTask), response);
        }
    }

    @RequestMapping(value = "/app_upgrade_task_add.json")
    public void addUpgradeTask(@RequestParam(value = "id", defaultValue = "") String id,
                               @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
                               @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                               @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes,
                               @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                               @RequestParam(value = "userIds", defaultValue = "") String userIds,
                               @RequestParam(value = "softwarePackageId", defaultValue = "") String softwarePackageId,
                               @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
                               @RequestParam(value = "vendorIds", defaultValue = "") String vendorIds,
                               @RequestParam(value = "maxNum", defaultValue = "") String maxNum,
                               @RequestParam(value = "timeInterval", defaultValue = "") String timeInterval,
                               @RequestParam(value = "isAll", defaultValue = "") String isAll,
                               @RequestParam(value = "startDate", defaultValue = "") String startDate,
                               @RequestParam(value = "endDate", defaultValue = "") String endDate, HttpServletResponse response,
                               HttpServletRequest request) throws ParseException {
        AppUpgradeTask upgradeTask = new AppUpgradeTask();
        upgradeTask.setDeviceGroupIds(deviceGroupIds);
        upgradeTask.setVersionSeq(Integer.parseInt(versionSeq));
        upgradeTask.setVendorIds(vendorIds);
        upgradeTask.setMaxNum(Integer.parseInt(maxNum));
        upgradeTask.setTimeInterval(Integer.parseInt(timeInterval));
        upgradeTask.setIsAll(Integer.parseInt(isAll));
        upgradeTask.setCreateDate(new Date());
        Operator op = ControllerUtil.getLoginOperator(request);
        upgradeTask.setOperUser(op.getLoginName());
        if (StringUtils.isNotBlank(startDate)) {
            upgradeTask.setStartDate(format.parse(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            upgradeTask.setEndDate(format.parse(endDate));
        }
        if (StringUtils.isNotBlank(softCodeId)) {
            AppSoftwareCode softCode = new AppSoftwareCode();
            softCode.setId(Long.parseLong(softCodeId));
            upgradeTask.setSoftCodeId(softCode);
        }
        if (StringUtils.isNotBlank(softwarePackageId)) {
            AppSoftwarePackage appSoftwarePackage = new AppSoftwarePackage();
            appSoftwarePackage.setId(Long.parseLong(softwarePackageId));
            upgradeTask.setSoftwarePackageId(appSoftwarePackage);
        }
        String result = null;
        boolean bool = this.appUpgradeTaskService.save(upgradeTask);
        if (!deviceGroupIds.isEmpty()) {
            this.appUpgradeTaskService.saveMap(upgradeTask, "GROUP", deviceGroupIds, null);
        }
        if (!deviceCodes.isEmpty()) {
            result = this.appUpgradeTaskService.saveMap(upgradeTask, "DEVICE", null, deviceCodes);
        }
        if (!userGroupIds.isEmpty()) {
            this.appUpgradeTaskService.saveUserMap(upgradeTask, "GROUP", userGroupIds, null);
        }
        if (!userIds.isEmpty()) {
            result = this.appUpgradeTaskService.saveUserMap(upgradeTask, "USER", null, userIds);
        }
        String logDescription = null;
        if (StringUtils.isBlank(result)) {
            if (bool == true) {
                logDescription = "添加应用升级任务信息成功!";
            } else {
                logDescription = "添加应用升级任务信息失败!";
            }
        } else {
            logDescription = "添加应用升级任务信息失败!" + result;
        }
        RenderUtils.renderText(logDescription, response);
        // RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "新增应用升级任务信息成功！" : "新增应用升级任务信息失败！";
        description += "软件号：" + upgradeTask.getSoftCodeId() + ";软件包：" + upgradeTask.getSoftwarePackageId() + ";设备分组："
                + upgradeTask.getDeviceGroupIds() + ";最大升级终端数：" + upgradeTask.getMaxNum() + ";最大间隔时间"
                + upgradeTask.getTimeInterval();
        this.loggerWebService.saveOperateLog(Constants.APP_UPGRADE_TASK_MAINTAIN, Constants.ADD, upgradeTask.getId()
                + "", description, request);
    }

    @RequestMapping(value = "/app_upgrade_task_update_unbind.json")
    public void updateUpgradeTaskUnbind(@RequestParam(value = "id", defaultValue = "") String id,
                                        HttpServletResponse response, HttpServletRequest request) throws ParseException {
        AppUpgradeTask upgradeTask = new AppUpgradeTask();
        upgradeTask.setId(Long.parseLong(id));
        String result = this.appUpgradeTaskService.deleteDeviceAndUserMap(upgradeTask);
        String logDescription = null;
        if (StringUtils.isNotBlank(result)) {
            logDescription = "解绑应用升级任务信息成功!";

        } else {
            logDescription = "解绑应用升级任务信息失败!";
        }
        RenderUtils.renderText(logDescription, response);
        String description = (StringUtils.isNotBlank(result)) ? "解绑应用升级任务信息成功！" : "解绑应用升级任务信息失败！";
        this.loggerWebService.saveOperateLog(Constants.APP_UPGRADE_TASK_MAINTAIN, Constants.MODIFY, upgradeTask.getId()
                + "", description, request);
    }

    @RequestMapping(value = "/app_upgrade_task_update_bind.json")
    public void updateUpgradeTaskBind(@RequestParam(value = "id", defaultValue = "") String id,@RequestParam(value = "areaIds", defaultValue = "") String areaIds,
//            @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes,
                                      @RequestParam(value = "deviceCodes", required = false) MultipartFile deviceCodeFile,
                                      @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                                      @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                      @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletResponse response,
                                      HttpServletRequest request) throws ParseException {

        try {
            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String deviceCodes = FileUtils.getDeviceCodesFromUploadFile(deviceCodeFile, path);

            AppUpgradeTask upgradeTask = new AppUpgradeTask();
            upgradeTask.setId(Long.parseLong(id));
            String result = this.appUpgradeTaskService.saveDeviceAndUserMap(upgradeTask, areaIds, deviceGroupIds, deviceCodes,
                    userGroupIds, userIds);
            String logDescription = null;
            if (result == null) {
                logDescription = "绑定应用升级任务信息成功!";

            } else {
                logDescription = "绑定应用升级任务信息失败!" +"\n"+ result;
            }
            RenderUtils.renderText(logDescription, response);
            // RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            String description = (result.indexOf("未能") < 0) ? "绑定应用升级任务信息成功！" : "绑定应用升级任务信息失败！";
            this.loggerWebService.saveOperateLog(Constants.APP_UPGRADE_TASK_MAINTAIN, Constants.MODIFY, upgradeTask.getId()
                    + "", description, request);
        } catch (IOException e) {
            LOGGER.error("绑定应用升级任务信息失败! {}" + e);
            RenderUtils.renderText("绑定应用升级任务信息失败!", response);
        }
    }

    @RequestMapping(value = "/app_upgrade_task_update.json")
    public void updateUpgradeTask(@RequestParam(value = "id", defaultValue = "") String id,
                                  @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
                                  @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes,
                                  @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                                  @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                  @RequestParam(value = "userIds", defaultValue = "") String userIds,
                                  @RequestParam(value = "softwarePackageId", defaultValue = "") String softwarePackageId,
                                  @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
                                  @RequestParam(value = "vendorIds", defaultValue = "") String vendorIds,
                                  @RequestParam(value = "maxNum", defaultValue = "") String maxNum,
                                  @RequestParam(value = "timeInterval", defaultValue = "") String timeInterval,
                                  @RequestParam(value = "isAll", defaultValue = "") String isAll,
                                  @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                  @RequestParam(value = "endDate", defaultValue = "") String endDate, HttpServletResponse response,
                                  HttpServletRequest request) throws ParseException {
        AppUpgradeTask upgradeTask = new AppUpgradeTask();
        upgradeTask.setId(Long.parseLong(id));
        upgradeTask.setDeviceGroupIds(deviceGroupIds);
        upgradeTask.setVersionSeq(Integer.parseInt(versionSeq));
        upgradeTask.setVendorIds(vendorIds);
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
            AppSoftwareCode softCode = new AppSoftwareCode();
            softCode.setId(Long.parseLong(softCodeId));
            upgradeTask.setSoftCodeId(softCode);
        }
        if (StringUtils.isNotBlank(softwarePackageId)) {
            AppSoftwarePackage appSoftwarePackage = new AppSoftwarePackage();
            appSoftwarePackage.setId(Long.parseLong(softwarePackageId));
            upgradeTask.setSoftwarePackageId(appSoftwarePackage);
        }
//        String result = null;
        boolean bool = this.appUpgradeTaskService.updateById(upgradeTask);
        /*
         * if(!deviceGroupIds.isEmpty()){
         * this.appUpgradeTaskService.saveMap(upgradeTask, "GROUP",
         * deviceGroupIds, null); } if(!deviceCodes.isEmpty()){ result =
         * this.appUpgradeTaskService.saveMap(upgradeTask, "DEVICE", null,
         * deviceCodes); } if(!userGroupIds.isEmpty()){
         * this.appUpgradeTaskService.saveUserMap(upgradeTask, "GROUP",
         * userGroupIds, null); } if(!userIds.isEmpty()){ result =
         * this.appUpgradeTaskService.saveUserMap(upgradeTask, "USER", null,
         * userIds); }
         */
        String logDescription = null;
        // if(StringUtils.isBlank(result)){
        if (bool == true) {
            logDescription = "修改应用升级任务信息成功!";
        } else {
            logDescription = "修改应用升级任务信息失败!";
        }
        // }else{
        // logDescription = "修改应用升级任务信息失败!"+result;
        // }
        RenderUtils.renderText(logDescription, response);
        // RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "更新应用升级任务信息成功！" : "更新应用升级任务信息失败！";
        description += "软件号：" + upgradeTask.getSoftCodeId() + ";软件包：" + upgradeTask.getSoftwarePackageId() + ";设备分组："
                + upgradeTask.getDeviceGroupIds() + ";最大升级终端数：" + upgradeTask.getMaxNum() + ";最大间隔时间"
                + upgradeTask.getTimeInterval();
        this.loggerWebService.saveOperateLog(Constants.APP_UPGRADE_TASK_MAINTAIN, Constants.ADD, upgradeTask.getId()
                + "", description, request);
    }

    @RequestMapping(value = "/get_app_upgrade_ids.json")
    public void getAppUpgradeByIds(@RequestParam(value = "id", defaultValue = "") Long id,
                                   @RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response, ModelMap model) {
        List<AppUpgradeMap> mapList = this.appUpgradeTaskService.findMapByUpgradeIdAndType(id, type);
        StringBuffer buffer = new StringBuffer();
        if (mapList != null && mapList.size() > 0) {
            for (AppUpgradeMap map : mapList) {
                // DeviceGroup deviceGroup =
                // this.deviceWebService.getDeviceGroupById(map.getDeviceGroupId());
                if (Constants.DEVICE_NOTICT_MAP_GROUP.equals(type)) {
                    // buffer.append(deviceGroup.getName()).append(",");
                    buffer.append(map.getDeviceGroupId()).append(",");
                } else {
                    buffer.append(map.getYstenId()).append(",");
                }
            }
        }
        if (buffer.length() > 0) {
            RenderUtils.renderText(buffer.substring(0, buffer.length() - 1).toString(), response);
        }
    }

    @RequestMapping(value = "/get_user_app_upgrade_ids.json")
    public void getUserUpgradeByIds(@RequestParam(value = "id", defaultValue = "") Long id,
                                    @RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response, ModelMap model) {
        List<UserAppUpgradeMap> mapList = this.appUpgradeTaskService.findUserMapByUpgradeIdAndType(id, type);
        StringBuffer buffer = new StringBuffer();
        if (mapList != null && mapList.size() > 0) {
            for (UserAppUpgradeMap map : mapList) {
                if (Constants.USER_NOTICT_MAP_GROUP.equals(type)) {
                    buffer.append(map.getUserGroupId()).append(",");
                } else {
                    buffer.append(map.getUserId()).append(",");
                }
            }
        }
        if (buffer.length() > 0) {
            RenderUtils.renderText(buffer.substring(0, buffer.length() - 1).toString(), response);
        }
    }
}
