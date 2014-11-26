package com.ysten.local.bss.upgrade.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.local.bss.web.service.IUpgradeTaskService;
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
public class UpgradeTaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeTaskController.class);
    @Autowired
    private IUpgradeTaskService upgradeTaskService;
    @Autowired
    private ILoggerWebService loggerWebService;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/upgrade_task_page")
    public String toUpgradeTaskPage(ModelMap model) {
        return "/upgrade/upgrade_task_list";
    }

    @RequestMapping(value = "/find_upgrade_task_list.json")
    public void getUpgradeTaskList(
            @RequestParam(value = "softwarePackageId", defaultValue = "") Long softwarePackageId,
            @RequestParam(value = "softCodeId", defaultValue = "") Long softCodeId,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Long softwarePackage = softwarePackageId != null && softwarePackageId == 0 ? null : softwarePackageId;
        Long softCode = softCodeId != null && softCodeId == 0 ? null : softCodeId;
        Pageable<UpgradeTask> pageable = this.upgradeTaskService.getListByCondition(softwarePackage, softCode,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "get_upgrade_task_list.json")
    public void findAllUpgradeTaskList(@RequestParam(value = "areaId", defaultValue = "") Long areaId,
                                     HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "请选择"));
        List<UpgradeTask> list = this.upgradeTaskService.findAllUpgradeTask();
        try {
            if (list != null && list.size() > 0) {
                for (UpgradeTask upgradeTask : list) {
                    tv.add(new TextValue(upgradeTask.getId().toString(), upgradeTask.getTaskName()));
                }
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            LOGGER.error("Get All Upgrade Task Exception: " + LoggerUtils.printErrorStack(e));
        }
    }
    
    @RequestMapping(value = "/find_upgrade_task_list_by_names.json")
    public void findUpgradeTaskList(
            @RequestParam(value = "softwarePackageName", defaultValue = "") String softwarePackageName,
            @RequestParam(value = "softCodeName", defaultValue = "") String softCodeName,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<UpgradeTask> pageable = this.upgradeTaskService.findUpgradeTaskListByCondition(softwarePackageName, softCodeName,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }


    @RequestMapping(value = "/upgrade_task_delete.json")
    public void deleteUpgradeTask(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        List<Long> idsList = StringUtil.splitToLong(ids);
        // bool = this.upgradeTaskService.deleteByIds(idsList);
        String rsp = this.upgradeTaskService.deleteUpgradeTaskByIds(idsList);
        String logDescription = null;
        if (StringUtils.isBlank(rsp)) {
            logDescription = "删除升级任务信息成功!";
        } else {
            logDescription = "删除升级任务信息失败!" + rsp;
        }
        RenderUtils.renderText(logDescription, response);
        // RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.UPGRADE_TASK_MAINTAIN, Constants.DELETE, ids, logDescription,
                request);
    }

    @RequestMapping(value = "/upgrade_task_to_update.json")
    public void getUpgradeTaskInfo(@RequestParam(value = "id", defaultValue = "") String id,
            HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            UpgradeTask upgradeTask = this.upgradeTaskService.getById(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(upgradeTask), response);
        }
    }

    @RequestMapping(value = "/upgrade_task_add.json")
    public void addUpgradeTask(
            @RequestParam(value = "taskName", defaultValue = "") String taskName,
            @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
            // @RequestParam(value = "deviceGroupIds", defaultValue = "") String
            // deviceGroupIds,
            // @RequestParam(value = "deviceCodes", defaultValue = "") String
            // deviceCodes,
            // @RequestParam(value = "userGroupIds", defaultValue = "") String
            // userGroupIds,
            // @RequestParam(value = "userIds", defaultValue = "") String
            // userIds,
            @RequestParam(value = "softwarePackageId", defaultValue = "") String softwarePackageId,
            @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "vendorIds", defaultValue = "") String vendorIds,
            @RequestParam(value = "maxNum", defaultValue = "") String maxNum,
            @RequestParam(value = "timeInterval", defaultValue = "") String timeInterval,
            @RequestParam(value = "isAll", defaultValue = "") String isAll,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate, HttpServletResponse response,
            HttpServletRequest request) throws ParseException {
        UpgradeTask upgradeTask = new UpgradeTask();
        // upgradeTask.setDeviceGroupIds(deviceGroupIds);
//        upgradeTask.setVersionSeq(Integer.parseInt(versionSeq));
//        upgradeTask.setVendorIds(vendorIds);
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
            DeviceSoftwareCode softCode = new DeviceSoftwareCode();
            softCode.setId(Long.parseLong(softCodeId));
            upgradeTask.setSoftCodeId(softCode);
        }
        if (StringUtils.isNotBlank(softwarePackageId)) {
            DeviceSoftwarePackage deviceSoftwarePackage = new DeviceSoftwarePackage();
            deviceSoftwarePackage.setId(Long.parseLong(softwarePackageId));
            upgradeTask.setSoftwarePackageId(deviceSoftwarePackage);
        }
        boolean bool = this.upgradeTaskService.save(upgradeTask);
        // if(!deviceGroupIds.isEmpty()){
        // this.upgradeTaskService.saveMap(upgradeTask, "GROUP", deviceGroupIds,
        // null);
        // }
        // if(!deviceCodes.isEmpty()){
        // result = this.upgradeTaskService.saveMap(upgradeTask, "DEVICE", null,
        // deviceCodes);
        // }
        // if(!userGroupIds.isEmpty()){
        // this.upgradeTaskService.saveUserMap(upgradeTask, "GROUP",
        // userGroupIds, null);
        // }
        // if(!userIds.isEmpty()){
        // result = this.upgradeTaskService.saveUserMap(upgradeTask, "USER",
        // null, userIds);
        // }

        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "新增升级任务信息成功！" : "新增升级任务信息失败！";
        description += "软件号：" + upgradeTask.getSoftCodeId() + ";软件包：" + upgradeTask.getSoftwarePackageId()
                + ";最大升级终端数：" + upgradeTask.getMaxNum() + ";最大间隔时间" + upgradeTask.getTimeInterval();
        this.loggerWebService.saveOperateLog(Constants.UPGRADE_TASK_MAINTAIN, Constants.ADD, upgradeTask.getId() + "",
                description, request);
    }

    @RequestMapping(value = "get_upgrade_user")
    public void getBootAnimationUserMap(@RequestParam(Constants.ID) String id,
                                        @RequestParam(value = "type", defaultValue = "") String type,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("");
        if (StringUtils.isNotBlank(id)) {
            List<UserUpgradeMap> mapList = upgradeTaskService.findUserMapByUpgradeIdAndType(Long.parseLong(id), type);
            for (UserUpgradeMap map : mapList) {
                if (Constants.USER_NOTICT_MAP_USER.equals(type)) {
                    sb.append(map.getUserId()).append(",");
                } else if (Constants.USER_NOTICT_MAP_GROUP.equals(type)) {
                    sb.append(map.getUserGroupId()).append(",");
                }
            }
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            RenderUtils.renderText(sb.substring(0, sb.length() - 1).toString(), response);
        } else {
            RenderUtils.renderText(sb.toString(), response);
        }
    }

    @RequestMapping(value = "get_upgrade_device")
    public void getBootAnimationDeviceMap(@RequestParam(Constants.ID) String id,
                                          @RequestParam(value = "type", defaultValue = "") String type,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("");
        if (StringUtils.isNotBlank(id)) {
            List<DeviceUpgradeMap> mapList = upgradeTaskService.findMapByUpgradeIdAndType(Long.parseLong(id), type);
            for (DeviceUpgradeMap map : mapList) {
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

    @RequestMapping(value = "upgradeTask_bind")
    public void bindBootAnimation(@RequestParam(Constants.IDS) String ids,@RequestParam(value = "areaIds", defaultValue = "") String areaIds,
            @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
//            @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes,
            @RequestParam(value = "deviceCodes", required = false) MultipartFile deviceCodeFile,
            @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
            @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
            HttpServletResponse response) {
        try {

            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String deviceCodes = FileUtils.getDeviceCodesFromUploadFile(deviceCodeFile,path);

            String result = null;
            int i =0;
            if (StringUtils.isNotBlank(ids)){
//            	 if(StringUtils.isBlank(deviceGroupIds) && StringUtils.isBlank(deviceCodes)&& StringUtils.isBlank(userGroupIds) && StringUtils.isBlank(userIds)) {
//                 	result = "至少填写一种类型绑定项做绑定，请确认！";
//                 	i++;
//                 }else{
                result = this.upgradeTaskService.saveUpgradeTaskMap(Long.parseLong(ids),  areaIds,deviceGroupIds, deviceCodes,
                        userGroupIds, userIds);
//                 }
            }
            String logDescription = null;
            if (StringUtils.isBlank(result)) {
                logDescription = "绑定升级任务信息成功!";
            } else {
                logDescription = (i==1?result:"绑定升级任务信息失败!"+"\n" + result);
            }
            RenderUtils.renderText(logDescription, response);
            loggerWebService.saveOperateLog(Constants.UPGRADE_TASK_MAINTAIN, "绑定设备和分组", ids + "", logDescription, request);
        } catch (Exception e) {
        	RenderUtils.renderText("绑定升级任务信息异常!", response);
        	RenderUtils.renderText("绑定异常!"+e.getMessage(), response);
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/upgrade_task_map_delete")
    public void deleteUserGroupMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.upgradeTaskService.deleteUpgradeTaskMap(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.UPGRADE_TASK_MAINTAIN, "解绑设备和分组", ids,
                        (bool == true) ? "解绑升级任务成功!" : "解绑升级任务失败!", request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText(ControllerUtil.returnString(false), response);
            LOGGER.error("Delete Unbind Upgrade Task Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.UPGRADE_TASK_MAINTAIN, "解绑设备和分组", ids,
                    "解绑升级任务异常!", request);
        }
    }

    @RequestMapping(value = "/upgrade_task_update.json")
    public void updateUpgradeTask(
            @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
            // @RequestParam(value = "deviceGroupIds", defaultValue = "") String
            // deviceGroupIds,
            // @RequestParam(value = "deviceCodes", defaultValue = "") String
            // deviceCodes,
            // @RequestParam(value = "userGroupIds", defaultValue = "") String
            // userGroupIds,
            // @RequestParam(value = "userIds", defaultValue = "") String
            // userIds,
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
        UpgradeTask upgradeTask = new UpgradeTask();
        upgradeTask.setId(Long.parseLong(id));
        upgradeTask.setTaskName(taskName);
        // upgradeTask.setDeviceGroupIds(deviceGroupIds);
//        upgradeTask.setVersionSeq(Integer.parseInt(versionSeq));
//        upgradeTask.setVendorIds(vendorIds);
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
            DeviceSoftwareCode softCode = new DeviceSoftwareCode();
            softCode.setId(Long.parseLong(softCodeId));
            upgradeTask.setSoftCodeId(softCode);
        }
        if (StringUtils.isNotBlank(softwarePackageId)) {
            DeviceSoftwarePackage deviceSoftwarePackage = new DeviceSoftwarePackage();
            deviceSoftwarePackage.setId(Long.parseLong(softwarePackageId));
            upgradeTask.setSoftwarePackageId(deviceSoftwarePackage);
        }
        boolean bool = this.upgradeTaskService.updateById(upgradeTask);
        // String result = null;
        // if(!deviceGroupIds.isEmpty()){
        // this.upgradeTaskService.saveMap(upgradeTask, "GROUP", deviceGroupIds,
        // null);
        // }
        // if(!deviceCodes.isEmpty()){
        // result = this.upgradeTaskService.saveMap(upgradeTask, "DEVICE", null,
        // deviceCodes);
        // }
        // if(!userGroupIds.isEmpty()){
        // this.upgradeTaskService.saveUserMap(upgradeTask, "GROUP",
        // userGroupIds, null);
        // }
        // if(!userIds.isEmpty()){
        // result = this.upgradeTaskService.saveUserMap(upgradeTask, "USER",
        // null, userIds);
        // }
        // String logDescription = null;
        // if(StringUtils.isBlank(result)){
        //
        // if(bool == true){
        // logDescription = "修改升级任务信息成功!";
        // }else{
        // logDescription = "修改升级任务信息失败!";
        // }
        // }else{
        // logDescription = "修改升级任务信息失败!"+result;
        // }
        // RenderUtils.renderText(logDescription, response);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "更新升级任务信息成功！" : "更新升级任务信息失败！";
        description += "软件号：" + upgradeTask.getSoftCodeId() + ";软件包：" + upgradeTask.getSoftwarePackageId()
                + ";最大升级终端数：" + upgradeTask.getMaxNum() + ";最大间隔时间" + upgradeTask.getTimeInterval();
        this.loggerWebService.saveOperateLog(Constants.UPGRADE_TASK_MAINTAIN, Constants.ADD, upgradeTask.getId() + "",
                description, request);
    }

    @RequestMapping(value = "/get_device_upgrade_ids.json")
    public void getDeviceUpgradeByIds(@RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response, ModelMap model) {
        List<DeviceUpgradeMap> mapList = this.upgradeTaskService.findMapByUpgradeIdAndType(id, type);
        StringBuffer buffer = new StringBuffer();
        if (mapList != null && mapList.size() > 0) {
            for (DeviceUpgradeMap map : mapList) {
                // DeviceGroup deviceGroup =
                // this.deviceWebService.getDeviceGroupById(map.getDeviceGroupId());
                if ("GROUP".equals(type)) {
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

    @RequestMapping(value = "/get_user_upgrade_ids.json")
    public void getUserUpgradeByIds(@RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response, ModelMap model) {
        List<UserUpgradeMap> mapList = this.upgradeTaskService.findUserMapByUpgradeIdAndType(id, type);
        StringBuffer buffer = new StringBuffer();
        if (mapList != null && mapList.size() > 0) {
            for (UserUpgradeMap map : mapList) {
                // UserGroup userGroup =
                // this.customerWebService.getUserGroupById(map.getUserGroupId());
                if ("GROUP".equals(type)) {
                    // buffer.append(userGroup.getName()).append(",");
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
