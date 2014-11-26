package com.ysten.local.bss.animation.web.controller;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.AnimationDeviceMap;
import com.ysten.local.bss.device.domain.AnimationUserMap;
import com.ysten.local.bss.device.domain.BootAnimation;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IBootAnimationWebService;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BootAnimationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BootAnimationController.class);
    @Autowired
    private IBootAnimationWebService bootAnimationWebService;
    @Autowired
    private ILoggerWebService loggerWebService;
    private int isDefault = 1;// 默认类型的开机动画标识
    private String state = "USEABLE";// 可用状态的开机动画标识
    @Autowired
    private SystemConfigService systemConfigService;

    @RequestMapping(value = "/to_boot_animation_list")
    public String toBootAnimationList(ModelMap model) {
        return "/animation/boot_animation_list";
    }

    @RequestMapping(value = "/to_user_boot_animation_list")
    public String toUserBootAnimationList(ModelMap model) {
        return "/animation/boot_animation_to_user_list";
    }

    @RequestMapping(value = "boot_animation_add")
    public void addBootAnimation(BootAnimation bootAnimation,
                                 HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "fileField", required = false) MultipartFile fileField) {
        try {
            String path = this.systemConfigService.getSystemConfigByConfigKey("picFiles");
            upload(fileField, path);
            bootAnimation.setCreateDate(new Date());
            bootAnimation.setUpdateDate(new Date());
            String str = this.bootAnimationWebService.saveBootAnimation(bootAnimation);
            RenderUtils.renderText(str, response);
            String logDescription = (StringUtils.isBlank(str)) ? "添加开机动画信息成功!" + ";动画：" + JsonUtils.toJson(bootAnimation) : "添加开机动画信息失败!" + str;
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, Constants.ADD,
                    bootAnimation.getId() + "", logDescription, request);
        } catch (Exception e) {
            e.printStackTrace();
            RenderUtils.renderText("添加开机动画失败!", response);
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, Constants.ADD, "", "新增开机动画信息异常;" + e.getMessage(), request);
        }
    }


    @RequestMapping(value = "get_animation_user")
    public void getBootAnimationUserMap(@RequestParam(Constants.ID) String id,
                                        @RequestParam(value = "type", defaultValue = "") String type,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("");
        if (StringUtils.isNotBlank(id)) {
            List<AnimationUserMap> mapList = bootAnimationWebService.findAnimationUserMapByAnimationIdAndType(Long.parseLong(id), type);
            for (AnimationUserMap map : mapList) {
                if (Constants.USER_NOTICT_MAP_USER.equals(type)) {
                    sb.append(map.getCode()).append(",");
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


    @RequestMapping(value = "get_animation_device")
    public void getBootAnimationDeviceMap(@RequestParam(Constants.ID) String id,
                                          @RequestParam(value = "type", defaultValue = "") String type,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("");
        if (StringUtils.isNotBlank(id)) {
            List<AnimationDeviceMap> mapList = bootAnimationWebService.findAnimationDeviceMapByAnimationIdAndType(Long.parseLong(id), type);
            for (AnimationDeviceMap map : mapList) {
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

    @RequestMapping(value = "animation_bind_user.json", method = RequestMethod.POST)
    public void bindUserBootAnimation(@RequestParam(Constants.IDS) String ids, @RequestParam(value = "areaIds", defaultValue = "") String areaIds,
                                      @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                      @RequestParam(value = "userIds3", required = false) MultipartFile userIdFile,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String userIds = FileUtils.getDeviceCodesFromUploadFile(userIdFile, path);

            String result = null;
            int i = 0;
            if (StringUtils.isNotBlank(ids)) {
                result = this.bootAnimationWebService.saveUserBootAnimationMap(Long.parseLong(ids), areaIds, userGroupIds, userIds);
            }
            String logDescription = null;
            if (StringUtils.isEmpty(result)) {
                logDescription = "绑定开机动画信息成功!";
            } else {
                logDescription = (i == 1 ? result : "绑定开机动画信息失败!" + "\n" + result);
            }
            RenderUtils.renderText(logDescription, response);
            logDescription += ";绑定的";
            if (!userGroupIds.equals("") && logDescription.indexOf("成功") > 0) {
                logDescription += "用户分组ID:【" + userGroupIds + "】;";
            }
            if (!userIds.equals("") && logDescription.indexOf("成功") > 0) {
                logDescription += "用户编码:【" + userGroupIds + "】";
            }
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, "绑定用户和分组", ids + "", logDescription,
                    request);
        } catch (Exception e) {
            e.printStackTrace();
            RenderUtils.renderText("绑定开机动画信息失败!", response);
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, "绑定用户和分组", ids + "", "绑定用户和分组异常;" + e.getMessage(),
                    request);
        }
    }

    @RequestMapping(value = "animation_bind.json", method = RequestMethod.POST)
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
                result = this.bootAnimationWebService.saveBootAnimationMap(Long.parseLong(ids), areaIds, deviceGroupIds,
                        deviceCodes, userGroupIds, userIds);
            }
            String logDescription = null;
            if (StringUtils.isEmpty(result)) {
                logDescription = "绑定开机动画信息成功!";
            } else {
                logDescription = (i == 1 ? result : "绑定开机动画信息失败!" + "\n" + result);
            }
            RenderUtils.renderText(logDescription, response);
            logDescription += ";绑定的";
            if (!deviceGroupIds.equals("") && logDescription.indexOf("成功") > 0) {
                logDescription += "设备分组ID:【" + deviceGroupIds + "】;";
            }
            if (!deviceCodes.equals("") && logDescription.indexOf("成功") > 0) {
                logDescription += "易视腾编号:【" + deviceCodes + "】";
            }
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, "绑定设备和分组", ids + "", logDescription,
                    request);
        } catch (Exception e) {
            e.printStackTrace();
            RenderUtils.renderText("绑定开机动画信息失败!", response);
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, "绑定设备和分组", ids + "", "绑定设备和分组异常;" + e.getMessage(),
                    request);
        }
    }

    @RequestMapping(value = "/animation_name_exists")
    public void checkBootAnimationNameExists(@RequestParam(value = "id", defaultValue = "") String id,
                                             @RequestParam("name") String name, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (name != null && !"".equals(name)) {
            BootAnimation bootAnimation = this.bootAnimationWebService.findBootAnimationByName(name);
            if (bootAnimation != null && !bootAnimation.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/default_already_exists")
    public void checkDefaultExists(@RequestParam(value = "id", defaultValue = "") String id,
                                   @RequestParam("isDefault") String isDefault, HttpServletResponse response) {
        String str = Constants.USABLE;
        if (isDefault != null && !"".equals(isDefault) && "1".equals(isDefault)) {
            BootAnimation bootAnimation = this.bootAnimationWebService.findDefaultBootAnimation(this.isDefault, state);
            if (bootAnimation != null && !bootAnimation.getId().toString().equals(id)) {
                str = Constants.IS_DEFAULT;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "user_boot_animation_add")
    public void addUserBootAnimation(BootAnimation bootAnimation,
                                     @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                     @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
                                     HttpServletResponse response) {
        try {
            bootAnimation.setCreateDate(new Date());
            boolean bool = this.bootAnimationWebService.saveUserBootAnimation(bootAnimation, userGroupIds, userIds);
            String logDescription = ((bool == true) ? "添加开机动画信息成功" : "添加开机动画信息失败") + "。消息信息："
                    + JsonUtils.toJson(bootAnimation);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, Constants.ADD,
                    bootAnimation.getId() + "", logDescription, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "boot_animation_to_update.json")
    public void toUpdateBootAnimation(@RequestParam("id") Long id, HttpServletResponse response) {
        if (id != null && !"".equals(id.toString())) {
            BootAnimation bootAnimation = this.bootAnimationWebService.getBootAnimationById(id);
            RenderUtils.renderJson(JsonUtils.toJson(bootAnimation), response);
        }
    }

    @RequestMapping(value = "boot_animation_update")
    public void updateBootAnimation(BootAnimation bootAnimation,
                                    HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "fileField", required = false) MultipartFile fileField) {
        try {
            String path = this.systemConfigService.getSystemConfigByConfigKey("picFiles");
            upload(fileField, path);

            bootAnimation.setUpdateDate(new Date());
            String str = this.bootAnimationWebService.updateBootAnimation(bootAnimation);
            String logDescription = (StringUtils.isBlank(str)) ? "修改开机动画信息成功!" : "修改开机动画信息失败!" + str;
            RenderUtils.renderText(logDescription, response);
            if (logDescription.indexOf("成功") > 0) {
                logDescription += ";动画：" + JsonUtils.toJson(bootAnimation);
            }
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, Constants.MODIFY,
                    bootAnimation.getId() + "", logDescription, request);
        } catch (Exception e) {
            e.printStackTrace();
            RenderUtils.renderText("修改开机动画失败!", response);
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, Constants.MODIFY,
                    bootAnimation.getId() + "", "修改开机动画异常;" + e.getMessage(), request);
        }
    }

    @RequestMapping(value = "user_boot_animation_update")
    public void updateUserBootAnimation(BootAnimation bootAnimation,
                                        @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                                        @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
                                        HttpServletResponse response) {
        try {
            boolean bool = this.bootAnimationWebService.updateUserBootAnimation(bootAnimation, userGroupIds, userIds);
            String logDescription = ((bool == true) ? "修改开机动画信息成功" : "修改开机动画信息失败") + "。消息信息："
                    + JsonUtils.toJson(bootAnimation);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, Constants.MODIFY,
                    bootAnimation.getId() + "", logDescription, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "boot_animation_list.json")
    public void findAllBootAnimation(@RequestParam(value = "name", defaultValue = "") String name,
                                     @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
                                     @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
                                     HttpServletResponse response) {
        Pageable<BootAnimation> bootAnimationList = this.bootAnimationWebService.findBootAnimationList(name, pageNo,
                pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(bootAnimationList), response);
    }

    @RequestMapping(value = "get_boot_animation_list.json")
    public void findAllBootAnimation(@RequestParam(value = "areaId", defaultValue = "") Long areaId,
                                     HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "请选择"));
        List<BootAnimation> list = this.bootAnimationWebService.findAllBootAnimationList();
        try {
            if (list != null && list.size() > 0) {
                for (BootAnimation bootAnimation : list) {
                    tv.add(new TextValue(bootAnimation.getId().toString(), bootAnimation.getName()));
                }
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            LOGGER.error("Get All boot animation Exception: " + LoggerUtils.printErrorStack(e));
        }
    }


    @RequestMapping(value = "/boot_animation_delete.json")
    public void deleteBootAnimation(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                    HttpServletResponse response) {
        List<Long> idsList = StringUtil.splitToLong(ids);
        String rsp = this.bootAnimationWebService.deleteBootAnimation(idsList);
        String logDescription = null;
        if (StringUtils.isBlank(rsp)) {
            logDescription = "删除开机动画成功!";
        } else {
            logDescription = "删除开机动画失败!<br/>" + rsp;
        }
        RenderUtils.renderText(logDescription, response);
        loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, Constants.DELETE, ids + "",
                logDescription, request);
    }

    @RequestMapping(value = "/boot_animation_map_delete")
    public void deleteUserGroupMap(@RequestParam(Constants.IDS) String ids,
                                   @RequestParam(value = "isUser", defaultValue = "") String isUser,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.bootAnimationWebService.deleteBootAnimationaMap(idsList, isUser);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, "解绑", ids,
                        (bool == true) ? "解绑开机动画成功!" : "解绑开机动画失败!", request);
            }
        } catch (Exception e) {
            LOGGER.error("Delete Unbind Boot Animation Map Error", e);
            RenderUtils.renderText("解绑开机动画失败!", response);
            this.loggerWebService.saveOperateLog(Constants.BOOT_ANIMATION_INFO_MAINTAIN, "解绑", ids,
                    "解绑开机动画异常;" + e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/get_animation_ids.json")
    public void getOperatorRoleIds(@RequestParam(value = "id", defaultValue = "") Long id,
                                   @RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response, ModelMap model) {
        List<AnimationDeviceMap> mapList = this.bootAnimationWebService.findAnimationDeviceMapByAnimationIdAndType(id,
                type);
        StringBuffer buffer = new StringBuffer();
        if (mapList != null && mapList.size() > 0) {
            for (AnimationDeviceMap map : mapList) {
                if ("GROUP".equals(type)) {
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

    @RequestMapping(value = "/get_user_animation_id.json")
    public void getUserAnimationsById(@RequestParam(value = "id", defaultValue = "") Long id,
                                      @RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response, ModelMap model) {
        List<AnimationUserMap> mapList = this.bootAnimationWebService
                .findAnimationUserMapByAnimationIdAndType(id, type);
        StringBuffer buffer = new StringBuffer();
        if (mapList != null && mapList.size() > 0) {
            for (AnimationUserMap map : mapList) {
                if ("GROUP".equals(type)) {
                    buffer.append(map.getUserGroupId()).append(",");
                } else {
                    buffer.append(map.getCode()).append(",");
                }
            }
        }
        if (buffer.length() > 0) {
            RenderUtils.renderText(buffer.substring(0, buffer.length() - 1).toString(), response);
        }
    }

    private void upload(MultipartFile fileField, String path) throws IOException {
        String fileName1 = "";
        if (fileField != null) {
            fileName1 = fileField.getOriginalFilename();
        }
        if (StringUtils.isNotEmpty(fileName1)) {

            FileUtils.saveFileFromInputStream(fileField.getInputStream(), path, fileName1);
        }
    }
}