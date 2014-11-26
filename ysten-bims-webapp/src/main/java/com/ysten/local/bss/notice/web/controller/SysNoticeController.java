package com.ysten.local.bss.notice.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.notice.domain.DeviceNoticeMap;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.notice.domain.UserNoticeMap;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.local.bss.web.service.ISysNoticeWebService;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SysNoticeController {

    @Autowired
    private ISysNoticeWebService sysNoticeWebService;
    @Autowired
    private ILoggerWebService loggerWebService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SysNoticeController.class);

    @RequestMapping(value = "/to_sys_notice_list")
    public String toSysNoticeList(ModelMap model) {
        return "/notice/sys_notice_list";
    }

    @RequestMapping(value = "/to_user_sys_notice_list")
    public String toUsrSysNoticeList(ModelMap model) {
        return "/notice/sys_notice_to_usr_list";
    }

    @RequestMapping(value = "sys_notice_add")
    public void addSysNotice(SysNotice sysNotice,
                             @RequestParam(value = "startDate3", defaultValue = "") String startDate,
                             @RequestParam(value = "endDate3", defaultValue = "") String endDate,
                             @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                             @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes,
                             @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                             @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            String result = this.sysNoticeWebService.saveSysNotice(sysNotice, startDate, endDate, deviceGroupIds,
                    deviceCodes, userGroupIds, userIds, request);
            String logDescription = null;
            if (StringUtils.isBlank(result)) {
                logDescription = "添加消息信息成功!";
            } else {
                logDescription = "添加消息信息失败!" + result;
            }
            RenderUtils.renderText(logDescription, response);
            // RenderUtils.renderText(ControllerUtil.returnString(bool),
            // response);
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, Constants.ADD, sysNotice.getId() + "",
                    logDescription + ";消息:" + JsonUtils.toJson(sysNotice), request);
        } catch (Exception e) {
        	RenderUtils.renderText("添加消息信息异常!", response);
            e.printStackTrace();
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, Constants.ADD, sysNotice.getId() + "",
                    "添加消息信息异常!"+e.getMessage() , request);
        }
    }

    @RequestMapping(value = "/notice_title_exists")
    public void checkSysNoticeNameExists(@RequestParam(value = "id", defaultValue = "") String id,
                                         @RequestParam("title") String title, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (title != null && !"".equals(title)) {
            SysNotice sysNotice = this.sysNoticeWebService.findSysNoticeByTitle(title);
            if (sysNotice != null && !sysNotice.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "user_sys_notice_add")
    public void addUserSysNotice(SysNotice sysNotice,
                                 @RequestParam(value = "startDate3", defaultValue = "") String startDate,
                                 @RequestParam(value = "endDate3", defaultValue = "") String endDate,
                                 @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                 @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            boolean bool = this.sysNoticeWebService.saveUserSysNotice(sysNotice, startDate, endDate, userGroupIds,
                    userIds, request);
            String logDescription = ((bool == true) ? "添加消息信息成功" : "添加消息信息失败") + "。消息信息：" + JsonUtils.toJson(sysNotice);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, Constants.ADD, sysNotice.getId() + "",
                    logDescription, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "sys_notice_to_update.json")
    public void toUpdateSysNotice(@RequestParam("id") Long id, HttpServletResponse response) {
        if (id != null && !"".equals(id.toString())) {
            SysNotice sysNotice = this.sysNoticeWebService.getSysNoticeById(id);
            RenderUtils.renderJson(JsonUtils.toJson(sysNotice), response);
        }
    }

    @RequestMapping(value = "sys_notice_update")
    public void updateSysNotice(SysNotice sysNotice,
                                @RequestParam(value = "startDate3", defaultValue = "") String startDate,
                                @RequestParam(value = "endDate3", defaultValue = "") String endDate,
                                @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                                @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes,
                                @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            String result = this.sysNoticeWebService.updateSysNotice(sysNotice, startDate, endDate, deviceGroupIds,
                    deviceCodes, userGroupIds, userIds);
            String logDescription = null;
            if (StringUtils.isBlank(result)) {
                logDescription = "修改消息信息成功!";
            } else {
                logDescription = "修改消息信息失败!" + result;
            }
            RenderUtils.renderText(logDescription, response);
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, Constants.MODIFY, sysNotice.getId()
                    + "", logDescription+";消息:"+JsonUtils.toJson(sysNotice), request);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "delete_sys_notic_relationship.json")
    public void deleteSysNoticeRelationship(SysNotice sysNotice,
                                            @RequestParam(value="user" ,defaultValue="") String user,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        String result = this.sysNoticeWebService.deleteSysNoticeRelationship(sysNotice.getId(),user);
        String logDescription = null;
        if (StringUtils.isNotBlank(result)) {
            logDescription = "为消息信息解除绑定成功!";
        } else {
            logDescription = "为消息信息解除绑定失败!";
        }
        RenderUtils.renderText(logDescription, response);
        loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, "解绑", sysNotice.getId() + "",
                logDescription, request);
    }

    @RequestMapping(value = "sys_notice_bind_device_relationship.json", method = RequestMethod.POST)
    public void updateSysNoticeDeviceRelationship(SysNotice sysNotice,
                                            @RequestParam(value = "areaIds", defaultValue = "") String areaIds,
                                            @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                                            @RequestParam(value = "deviceCodes3", required = false) MultipartFile deviceCodeFile,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        try {

            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String deviceCodes = FileUtils.getDeviceCodesFromUploadFile(deviceCodeFile, path);
            String result = "";
            result = this.sysNoticeWebService.updateSysNoticeDeviceRelationship(sysNotice, areaIds, deviceGroupIds, deviceCodes);
            String logDescription = null;
            if (StringUtils.isEmpty(result)) {
                logDescription = "绑定消息信息成功!";
            } else {
                logDescription = "绑定消息信息失败!" +"\n"+ result;
            }
            RenderUtils.renderText(logDescription, response);
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, "绑定设备和分组", sysNotice.getId()
                    + "", logDescription, request);
        } catch (Exception e) {
        	RenderUtils.renderText("绑定异常!", response);
            e.printStackTrace();
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, "绑定设备和分组", sysNotice.getId()
                    + "", "绑定异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "sys_notice_bind_user_relationship.json", method = RequestMethod.POST)
    public void updateSysNoticeUserRelationship(SysNotice sysNotice, @RequestParam(value = "areaIds", defaultValue = "") String areaIds,
                                            @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                            @RequestParam(value = "userIds3", required = false) MultipartFile userIdFile,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        try {

            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String userIds = FileUtils.getDeviceCodesFromUploadFile(userIdFile, path);
            String result = "";
                result = this.sysNoticeWebService.updateSysNoticeUserRelationship(sysNotice, areaIds, userGroupIds, userIds);
            String logDescription = null;
            if (StringUtils.isEmpty(result)) {
                logDescription = "绑定消息信息成功!";
            } else {
                logDescription = "绑定消息信息失败!" +"\n"+ result;
            }
            RenderUtils.renderText(logDescription, response);
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, "绑定用户和分组", sysNotice.getId()
                    + "", logDescription, request);
        } catch (Exception e) {
            e.printStackTrace();
            RenderUtils.renderText("绑定消息信息失败!", response);
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, "绑定用户和分组", sysNotice.getId()
                    + "", "绑定异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "user_sys_notice_update")
    public void updateUserSysNotice(SysNotice sysNotice,
                                    @RequestParam(value = "startDate3", defaultValue = "") String startDate,
                                    @RequestParam(value = "endDate3", defaultValue = "") String endDate,
                                    @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                    @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            boolean bool = this.sysNoticeWebService.updateUserSysNotice(sysNotice, startDate, endDate, userGroupIds,
                    userIds);
            String logDescription = ((bool == true) ? "修改消息信息成功" : "修改消息信息失败") + "。消息信息：" + JsonUtils.toJson(sysNotice);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, Constants.MODIFY, sysNotice.getId()
                    + "", logDescription, request);
        } catch (ParseException e) {
        	RenderUtils.renderText(ControllerUtil.returnString(false), response);
            e.printStackTrace();
            loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, Constants.MODIFY, sysNotice.getId()
                    + "", "修改异常!"+e.getMessage(), request);
        }
    }

    /**
     * 获取消息信息列表
     */
    @RequestMapping(value = "sys_notice_list.json")
    public void findAllSysNoticeList(@RequestParam(value = "name", defaultValue = "") String name,
                                     @RequestParam(value = "content", defaultValue = "") String content,
                                     @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
                                     @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
                                     HttpServletResponse response) {
        Pageable<SysNotice> sysNoticeList = this.sysNoticeWebService.findSysNoticeList(name, content, pageNo, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(sysNoticeList), response);
    }

    @RequestMapping(value = "get_sys_notice_list.json")
    public void findAllSysNoticeList(@RequestParam(value = "areaId", defaultValue = "") Long areaId,
                                     HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "请选择"));
        List<SysNotice> list = this.sysNoticeWebService.findAllSysNoticeList();
        try {
            if (list != null && list.size() > 0) {
                for (SysNotice sysNotice : list) {
                    tv.add(new TextValue(sysNotice.getId().toString(), sysNotice.getTitle()));
                }
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            LOGGER.error("Get All sys notice Exception: " + LoggerUtils.printErrorStack(e));
        }
    }

    @RequestMapping(value = "user_sys_notice_list.json")
    public void findUserSysNoticeList(@RequestParam(value = "name", defaultValue = "") String name,
                                      @RequestParam(value = "content", defaultValue = "") String content,
                                      @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
                                      @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
                                      HttpServletResponse response) {
        Pageable<SysNotice> sysNoticeList = this.sysNoticeWebService.findSysNoticeList(name, content, pageNo, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(sysNoticeList), response);
    }

    @RequestMapping(value = "/sys_notice_delete.json")
    public void deleteSysNotice(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.sysNoticeWebService.deleteSysNotice(idsList);
            RenderUtils.renderText(ControllerUtil.returnString(true), response);
        }
        String logDescription = (bool == true) ? "删除消息信息成功" : "删除消息信息失败";
        loggerWebService.saveOperateLog(Constants.SYS_NOTICE_INFO_MAINTAIN, Constants.DELETE, ids + "", logDescription,
                request);
    }


    @RequestMapping(value = "/get_notice_ids.json")
    public void getNoticeGroupIds(@RequestParam(value = "id", defaultValue = "") Long id,
                                  @RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response, ModelMap model) {
        List<DeviceNoticeMap> mapList = this.sysNoticeWebService.findDeviceNoticeMapByNoticeIdAndType(id, type);
        StringBuffer buffer = new StringBuffer("");
        if (mapList != null && mapList.size() > 0) {
            for (DeviceNoticeMap map : mapList) {
                if ("GROUP".equals(type)) {
                    buffer.append(map.getDeviceGroupId()).append(",");
                } else {
                    buffer.append(map.getYstenId()).append(",");
                }
            }
        }
        if (StringUtils.isBlank(buffer.toString())) {
            RenderUtils.renderText(buffer.toString(), response);
        } else {
            RenderUtils.renderText(buffer.substring(0, buffer.length() - 1).toString(), response);
        }

    }

    @RequestMapping(value = "/get_user_notice_ids.json")
    public void getNoticeByIds(@RequestParam(value = "id", defaultValue = "") Long id,
                               @RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response, ModelMap model) {
        List<UserNoticeMap> mapList = this.sysNoticeWebService.findUserNoticeMapByNoticeIdAndType(id, type);
        StringBuffer buffer = new StringBuffer();
        if (mapList != null && mapList.size() > 0) {
            for (UserNoticeMap map : mapList) {
                if ("GROUP".equals(type)) {
                    buffer.append(map.getUserGroupId()).append(",");
                } else {
                    buffer.append(map.getCode()).append(",");
                }
            }
        }
        if (StringUtils.isBlank(buffer.toString())) {
            RenderUtils.renderText(buffer.toString(), response);
        } else {
            RenderUtils.renderText(buffer.substring(0, buffer.length() - 1).toString(), response);
        }
    }
}