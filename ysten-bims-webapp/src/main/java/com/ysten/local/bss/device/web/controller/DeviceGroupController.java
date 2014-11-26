package com.ysten.local.bss.device.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.DeviceGroupMap;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.DeviceGroupType;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DeviceGroupController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceGroupController.class);
    @Autowired
    private IDeviceWebService deviceWebService;
    @Autowired
    private ILoggerWebService loggerWebService;

    @RequestMapping(value = "/to_device_group_list")
    public String toDeviceGroupList(ModelMap model) {
        return "/device/device_group_list";
    }

    // 设备分组类型
    @RequestMapping(value = "/device_group_type.json")
    public void getDeviceGroup(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
                               HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("", "请选择"));
        }
        for (DeviceGroupType dgt : DeviceGroupType.values()) {
            tv.add(new TextValue(dgt.name(), dgt.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }


    @RequestMapping(value = "/get_districtCodes_by_Id.json")
    public void getDistrictCodes(@RequestParam(value = Constants.ID, defaultValue = "") String id,
                                 @RequestParam(value = "tableName", defaultValue = "") String tableName,
                                 @RequestParam(value = "character", defaultValue = "") String character,
                                 HttpServletResponse response) {
        List<Long> groupList = this.deviceWebService.findDeviceGroupsById(id, character, tableName);
        List<Long> deviceList = this.deviceWebService.findAreaByBusiness(Long.parseLong(id), character, tableName);
        StringBuilder sb = new StringBuilder("");
        if (!CollectionUtils.isEmpty(groupList)) {
//            for (DeviceGroup group : groupList) {
//                sb.append(group.getAreaId()).append(",");
//            }
            for (Long group : groupList) {
                sb.append(group).append(",");
            }
        }
        if (!CollectionUtils.isEmpty(deviceList)) {
            for (Long device : deviceList) {
                boolean bool = true;
                if (!CollectionUtils.isEmpty(groupList)) {
                    for (int i = 0; i < groupList.size(); i++) {
                        if (groupList.get(i).equals(device)) {
                            bool = false;
                        }
                    }
                    if (bool) {
                        sb.append(device).append(",");
                    }
                } else {
                    sb.append(device).append(",");
                }
            }
        }
        if (StringUtils.isBlank(sb.toString())) {
            RenderUtils.renderText(sb.toString(), response);
        } else {
            RenderUtils.renderText(sb.toString().substring(0, sb.toString().length() - 1), response);
        }
//        RenderUtils.renderText(groupList.toString(), response);
    }


    // 消息设备分组
    @RequestMapping(value = "/device_group_list_by_district.json")
    public void getDeviceGroupByDistrictCode(@RequestParam(value = "type", defaultValue = "") String type,
                                             @RequestParam(value = "districtCode", defaultValue = "") String districtCode,
                                             @RequestParam(value = "tableName", defaultValue = "") String tableName,
                                             @RequestParam(value = "character", defaultValue = "") String character,
                                             @RequestParam(value = "id", defaultValue = "") String id,
                                             HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<DeviceGroup> list = this.deviceWebService.findDeviceGroupByDistrictCode(
                EnumConstantsInterface.DeviceGroupType.valueOf(type), tableName, character, districtCode, id);
        if (list != null && list.size() > 0) {
            for (DeviceGroup deviceGroup : list) {
                tv.add(new TextValue(deviceGroup.getId().toString(), deviceGroup.getAreaName() + ":" + deviceGroup.getName()));
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        }
    }

    // 设备分组类型
    @RequestMapping(value = "/device_group_list_by_type.json")
    public void getDeviceGroupByType(@RequestParam(value = "type", defaultValue = "") String type,
                                     @RequestParam(value = "isDynamic", defaultValue = "") String isDynamic,
                                     @RequestParam(value = "areaId", defaultValue = "") Long areaId,
                                     HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if (StringUtils.isNotBlank(isDynamic)) {
            tv.add(new TextValue("", "请选择"));
        }
        List<DeviceGroup> list = this.deviceWebService.findDeviceGroupListByType(
                EnumConstantsInterface.DeviceGroupType.valueOf(type), isDynamic);
        try {
            if (list != null && list.size() > 0) {
                for (DeviceGroup deviceGroup : list) {
                    if (deviceGroup.getAreaId() != null) {
                        if (deviceGroup.getAreaId().equals(areaId) && areaId != null) {
                            tv.add(new TextValue(deviceGroup.getId().toString(), deviceGroup.getName()));
                        }
                        if (areaId == null) {
                            tv.add(new TextValue(deviceGroup.getId().toString(), deviceGroup.getName()));
                        }
                    }
                }
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            LOGGER.error("Get device Group by type Exception: " + LoggerUtils.printErrorStack(e));
        }
    }

    @RequestMapping(value = "/device_group_list.json")
    public void findDeviceGroupList(@RequestParam(value = "type", defaultValue = "") String type,
                                    @RequestParam(value = "name", defaultValue = "") String name,
                                    @RequestParam(value = "districtCode", defaultValue = "") Long areaId,
                                    @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                                    @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
                                    HttpServletRequest request, HttpServletResponse response) {
        try {
            Long area = areaId != null && areaId == 0 ? null : areaId;
            Pageable<DeviceGroup> pageable = this.deviceWebService.findDeviceGroupByTypeNameDist(name, type, area, pageNo, pageSize);
            RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
        } catch (Exception e) {
            LOGGER.error("Find Device Group Error", e);
        }
    }

    @RequestMapping(value = "/get_device_group_relate_business.json")
    public void getDeviceRelateBusinessByGroupId(@RequestParam(value = "groupId", defaultValue = "") String groupId,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {

        Map<String, Object> json = deviceWebService.findDeviceRelateBusinessByYstenIdOrGroupId(null, Long.parseLong(groupId));

        RenderUtils.renderJson(EnumDisplayUtil.toJson(json), response);
    }

    @RequestMapping(value = "/device_group_bind_business.json")
    public void bindBusinesses(@RequestParam(Constants.IDS) String ids,
                               @RequestParam(value = "boot", defaultValue = "") String bootId,
                               @RequestParam(value = "animation", defaultValue = "") String animationId,
                               @RequestParam(value = "panel", defaultValue = "") String panelId,
                               @RequestParam(value = "notice", defaultValue = "") String noticeIds,
                               @RequestParam(value = "upgrade", defaultValue = "") String upgradeIds,
                               @RequestParam(value = "background", defaultValue = "") String backgroundIds,
                               @RequestParam(value = "apkUpgrade", defaultValue = "") String apkUpgradeIds,
                               HttpServletRequest request,HttpServletResponse response) {
        String message = "";
        if (StringUtils.isBlank(bootId) && StringUtils.isBlank(backgroundIds) && StringUtils.isBlank(animationId)
                && StringUtils.isBlank(panelId) && StringUtils.isBlank(noticeIds) && StringUtils.isBlank(upgradeIds) && StringUtils.isBlank(apkUpgradeIds)) {
            message = "至少选择一种类型的业务做绑定，请确认！";
        } else {
            message = this.deviceWebService.saveGroupBusinessBind(ids, bootId, animationId, panelId, noticeIds, backgroundIds, upgradeIds, apkUpgradeIds);
        }
        RenderUtils.renderText(message, response);
        String info = "终端ID：" + ids + "绑定了业务ID：" + bootId + "," + animationId + ","
                + panelId + "," + noticeIds + "," + backgroundIds ;
        String result = message +info;
        this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "关联业务", ids, result, request);
    }

    @RequestMapping(value = "/device_group_business_map_delete.json")
    public void deleteUserGroupBusinessMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                           HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.deviceWebService.deleteDeviceGroupBusiness(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "解绑业务", ids,
                        (bool == true) ? "解绑分组与业务关系成功!" : "解绑分组与业务关系失败!", request);
            }
        } catch (Exception e) {
            LOGGER.error("Delete Unbind Device Group Business Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "解绑业务", ids,
                    "解绑分组与业务关系异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/device_group_add")
    public void addDeviceGroup(DeviceGroup deviceGroup, HttpServletResponse response, HttpServletRequest request) {
        try {
            deviceGroup.setCreateDate(new Date());
            boolean bool = this.deviceWebService.saveDeviceGroup(deviceGroup);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, Constants.ADD,
                    deviceGroup.getId() + "", (bool == true) ? "新增设备分组信息成功!" +JsonUtil.getJsonString4JavaPOJO(deviceGroup): "新增IP地址信息失败 . "+JsonUtil.getJsonString4JavaPOJO(deviceGroup), request);
        } catch (Exception e) {
            LOGGER.error("Save Device Group Error", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, Constants.ADD,"", "新增设备分组信息异常!" +e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/device_group_to_update")
    public void findDeviceGroupById(@RequestParam(value = "id", defaultValue = "") String id,
                                    HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            DeviceGroup deviceGroup = this.deviceWebService.getDeviceGroupById(Long.parseLong(id));
            RenderUtils.renderJson(JsonUtils.toJson(deviceGroup), response);
        }
    }

    @RequestMapping(value = "/device_group_update")
    public void updateDeviceGroup(DeviceGroup deviceGroup, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (deviceGroup != null) {
                deviceGroup.setUpdateDate(new Date());
                boolean bool = this.deviceWebService.updateDeviceGroup(deviceGroup);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, Constants.MODIFY,
                        deviceGroup.getId().toString(), (bool == true) ? "修改设备分组信息成功!" +JsonUtil.getJsonString4JavaPOJO(deviceGroup): "修改设备分组信息失败. " +JsonUtil.getJsonString4JavaPOJO(deviceGroup), request);
            }
        } catch (Exception e) {
            LOGGER.error("Update Device Group Error", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, Constants.MODIFY,"", "修改设备分组信息异常!" +e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/device_group_delete")
    public void deleteDeviceGroup(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                  HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                // boolean bool =
                // this.deviceWebService.deleteDeviceGroup(idsList);
                String rsp = this.deviceWebService.deleteDeviceGroupByCondition(idsList);
                String logDescription = null;
                if (StringUtils.isBlank(rsp)) {
                    logDescription = "删除设备分组成功!";
                } else {
                    logDescription = "删除设备分组失败!<br/>" + rsp;
                }
                RenderUtils.renderText(logDescription, response);
                // RenderUtils.renderText(ControllerUtil.returnString(bool),
                // response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, Constants.DELETE, ids,
                        logDescription, request);
            }
        } catch (Exception e) {
            LOGGER.error("Delete Device Group Error", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, Constants.DELETE, ids,
                    "删除设备分组异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/check_Device_Input_Sql_validate")
    public void checkDeviceInputSql(@RequestParam(value = "sql", defaultValue = "") String sql,
                                    HttpServletResponse response) {
        String result = "";
        try {
            result = this.deviceWebService.checkInputSql(sql.replace(";", ""));
        } catch (Exception e) {
            String table = "Table";
            String column = "Unknown column";
            String notExisted = "doesn't exist";
            String whereClause = "in 'where clause'";
            String fieldList = "in 'field list'";
            String keyWords = "right syntax to use near";
            String param = "No value specified for parameter";
            if (StringUtils.isBlank(e.getMessage()) || e.getLocalizedMessage().indexOf(keyWords) >= 0
                    || e.getLocalizedMessage().indexOf(param) >= 0) {
                result = "请输入正确的查询语句!";
            } else if (e.getLocalizedMessage().indexOf(table) >= 0 && e.getLocalizedMessage().indexOf(notExisted) >= 0) {
                result = "你输入的表名"
                        + e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf(table) + table.length(),
                        e.getLocalizedMessage().indexOf(notExisted)) + "不存在！";
            } else if (e.getLocalizedMessage().indexOf(column) >= 0) {
                if (e.getLocalizedMessage().indexOf(whereClause) >= 0) {
                    result = "你输入的字段"
                            + e.getLocalizedMessage().substring(
                            e.getLocalizedMessage().indexOf(column) + column.length(),
                            e.getLocalizedMessage().indexOf(whereClause)) + "不存在！";
                } else if (e.getLocalizedMessage().indexOf(fieldList) >= 0) {
                    result = "你输入的字段"
                            + e.getLocalizedMessage().substring(
                            e.getLocalizedMessage().indexOf(column) + column.length(),
                            e.getLocalizedMessage().indexOf(fieldList)) + "不存在！";
                }
            } else {
                result = e.getLocalizedMessage();
            }
        } finally {
            RenderUtils.renderText(result, response);
        }
    }

    @RequestMapping(value = "/deviceGroup_device_map_delete")
    public void deleteDeviceGroupMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                     HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.deviceWebService.deleteDeviceGroupMap(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "解绑设备", ids,
                        (bool == true) ? "解绑设备成功!" : "解绑设备失败!", request);
            }
        } catch (Exception e) {
            LOGGER.error("Delete Unbind Group Device Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "解绑设备", ids,
                    "解绑设备异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/delete_device_by_deviceGroupId.json")
    public void deleteDeviceByGroupId(@RequestParam("deviceGroupId") Long deviceGroupId,
                                      @RequestParam("ystenIds") String ystenIds,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            List<String> idsList = new ArrayList<String>();
            if (StringUtils.isNotBlank(ystenIds)) {
                idsList = StringUtil.split(ystenIds);
            }
            boolean bool = this.deviceWebService.deleteDeviceByGroupId(deviceGroupId, idsList);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "移除设备", deviceGroupId+"",
                    (bool == true) ? "移除设备成功!"+" 移除的易视腾编号【"+ystenIds+"】" : "移除设备失败!"+" 移除的易视腾编号【"+ystenIds+"】", request);
        } catch (Exception e) {
            LOGGER.error("Delete Device By DeviceGroupId Error", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "移除设备", deviceGroupId+"",
                    "移除设备异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/deviceGroup_business_map_delete")
    public void deleteDeviceGroupBusinessMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                             HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.deviceWebService.deleteDeviceGroupBusiness(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "解绑业务", ids,
                        (bool == true) ? "解绑设备分组与业务关系成功!" : "解绑设备分组与业务关系失败!", request);
            }
        } catch (Exception e) {
            LOGGER.error("Delete Unbind Group Business Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "解绑业务", ids,
                    "解绑设备分组与业务关系异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "get_deviceCodes_by_deviceGroupId.json")
    public void getDeviceCodesByGroupId(@RequestParam(Constants.ID) String id, HttpServletRequest request,
                                        HttpServletResponse response) {
        List<DeviceGroupMap> mapList = deviceWebService.findDeviceGroupMapByGroupId(id);
        StringBuilder sb = new StringBuilder("");
        if (mapList != null && mapList.size() > 0) {
            for (DeviceGroupMap map : mapList) {
                if (StringUtils.isNotBlank(map.getYstenId())) {
                    sb.append(map.getYstenId()).append(",");
                }
            }
        }
        if (StringUtils.isBlank(sb.toString())) {
            RenderUtils.renderText(sb.toString(), response);
        } else {
            RenderUtils.renderText(sb.substring(0, sb.length() - 1).toString(), response);
        }

    }

    /*@RequestMapping(value = "/bind_devices.json")
    public void bindUserGroup(@RequestParam(Constants.IDS) String ids,
                              @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes, HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            StringBuilder sb = new StringBuilder();
            String description = null;
            if (StringUtils.isNotBlank(deviceCodes)) {
                description = this.deviceWebService.bindDeviceGroupMap(ids, deviceCodes, true);
            } else {
                description = "未填写要绑定的易视腾编号，请确认!";
            }
            this.appendDescriptions(sb, description);
            // if(StringUtils.isBlank(sb.toString())){
            // sb.append("关联设备成功!");
            // RenderUtils.renderText(sb.toString(), response);
            // }
            if (sb.substring(0, 7).equals("success")) {
//                RenderUtils.renderText("关联设备成功!<br/>" + sb.toString().substring(7, sb.toString().length()), response);
                RenderUtils.renderText("关联设备成功!<br/>", response);
            }
            if (sb.substring(0, 6).equals("failed")) {
                RenderUtils.renderText("关联设备失败!<br/>" + sb.toString().substring(6, sb.toString().length()), response);
            }
            if (!sb.substring(0, 6).equals("failed") && !sb.substring(0, 7).equals("success")) {
                RenderUtils.renderText(description, response);
            }
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "关联设备", ids, description,
                    request);
        } catch (Exception e) {
            LOGGER.error("Bind device exception{}", e);
            RenderUtils.renderText("关联设备异常", response);
            String description = "关联设备异常";
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "关联设备", ids, description,
                    request);
        }
    }*/

    @RequestMapping(value = "/bind_devices.json", method = RequestMethod.POST)
    public void bindUserGroup(@RequestParam(value = Constants.IDS) String id,
                              @RequestParam(value = "fileField", required = false) MultipartFile deviceCodeFile, HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String deviceCodes = FileUtils.getDeviceCodesFromUploadFile(deviceCodeFile, path);

            StringBuilder sb = new StringBuilder();
            String description = null;
            if (StringUtils.isNotBlank(deviceCodes)) {
                description = this.deviceWebService.groupBindDeviceMap(id, deviceCodes);
            } else {
                String info = "文件不合法：未填写要绑定的设备序列号，请确认!";
                String space = "";
                description = "{\"info\":\"" + info + "\",\"ystenIds\":\"" + space + "\"}";
            }
            this.appendDescriptions(sb, description);
            System.out.println(sb.toString());
            RenderUtils.renderJson(sb.toString(), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "关联设备", id, description,
                    request);
        } catch (Exception e) {
            LOGGER.error("Bind device exception{}", e);
            RenderUtils.renderText("关联设备异常", response);
            String description = "关联设备异常";
            this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "关联设备", id, description+e.getMessage(),
                    request);
        }
    }

    @RequestMapping(value = "/add_device_group_maps.json", method = RequestMethod.POST)
    public void addDeviceGroupMapDeleteSameOldMap(@RequestParam(value = Constants.ID) String id, @RequestParam(value = "ystenIds", defaultValue = "") String ystenIds, HttpServletRequest request,
                                                  HttpServletResponse response) {
        String description = "";
        if (StringUtils.isNotBlank(ystenIds) && StringUtils.isNotBlank(id)) {
            description = this.deviceWebService.addDeviceGroupMap(id, ystenIds);
        } else {
            description = "文件不合法：未填写要绑定的易视腾编号或设备分组，请确认!";
        }
        if (StringUtils.isBlank(description)) {
            RenderUtils.renderText("关联设备成功!", response);
        } else {
            RenderUtils.renderText(description, response);
        }
        this.loggerWebService.saveOperateLog(Constants.DEVICE_GROUP_INFO_MAINTAIN, "关联设备", id, StringUtils.isNotBlank(description) ? "关联设备失败!" + description : "关联设备成功!关联的设备易视腾编号【"+ystenIds+"】",
                request);
    }

    private void appendDescriptions(StringBuilder sb, String description) {
        if (StringUtils.isNotBlank(description)) {
            sb.append(description);
        }
    }
}
