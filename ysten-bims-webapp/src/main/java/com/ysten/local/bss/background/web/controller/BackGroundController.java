package com.ysten.local.bss.background.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.BackgroundImage;
import com.ysten.local.bss.device.repository.IBackgroundImageRepository;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IBackGroundService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;

@Controller
public class BackGroundController {
    private Logger LOGGER = LoggerFactory.getLogger(BackGroundController.class);
    @Autowired
    private IBackGroundService backGroundService;
    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private IBackgroundImageRepository backgroundImageRepository;
    @Autowired
    private SystemConfigService systemConfigService;
    @RequestMapping(value = "/to_background_image_list")
    public String toBackGroundList(ModelMap model) {
        return "/background/background_list";
    }

    @RequestMapping(value = "background_list.json")
    public void findAllBackGround(@RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
            HttpServletResponse response) {
        Pageable<BackgroundImage> backGroundList = this.backGroundService.findBackGroundList(name, pageNo, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(backGroundList), response);
    }

    @RequestMapping(value = "/backgroud_default_already_exists")
    public void checkDefaultExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("isDefault") String isDefault, HttpServletResponse response) {
        String str = Constants.USABLE;
        if (isDefault != null && !"".equals(isDefault) && "1".equals(isDefault)) {
            List<BackgroundImage> backgroundImage = this.backGroundService.findDefaultBackgroundImage(1);
            if (backgroundImage != null && backgroundImage.size() > 0
                    && !backgroundImage.get(0).getId().toString().equals(id)) {
                str = Constants.IS_DEFAULT;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "background_image_add")
    public void addBackGroundImage(BackgroundImage backgroundImage, HttpServletRequest request,
            HttpServletResponse response,@RequestParam(value = "fileField1", required = false) MultipartFile fileField1, @RequestParam(value = "fileField2", required = false) MultipartFile fileField2) {
        try {
        	String path = this.systemConfigService.getSystemConfigByConfigKey("picFiles");
        	upload(fileField1, fileField2,path);
        	
            backgroundImage.setCreateDate(new Date());
            backgroundImage.setUpdateDate(new Date());
            String rsp = this.backGroundService.saveBackGroundImage(backgroundImage);
            String logDescription = null;
            if (StringUtils.isBlank(rsp)) {
                logDescription = "添加背景轮换信息成功!";
            } else {
                logDescription = "添加背景轮换信息失败!<br/>" + rsp;
            }
            RenderUtils.renderText(logDescription, response);
            if(logDescription.indexOf("成功")>0){
            	logDescription += ";背景轮换:"+JsonUtils.toJson(backgroundImage);
            }
            loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, Constants.ADD, backgroundImage.getId()
                    + "", logDescription, request);
        } catch (Exception e) {
            LOGGER.error("Save BackGround Image Error", e);
            loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, Constants.ADD, "", "添加背景轮换异常;"+e.getMessage(), request);
        }
    }



    @RequestMapping(value = "backgroud_bind_user.json",method = RequestMethod.POST)
    public void bindUserBackgroud(@RequestParam(Constants.IDS) String ids,
                                  @RequestParam(value = "areaIds", defaultValue = "") String areaIds,
                              @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                              @RequestParam(value = "userIds3", required = false) MultipartFile userIdFile,
                              @RequestParam(value = "userGroupLoopTime", defaultValue = "") String userGroupLoopTime,
                              @RequestParam(value = "userLoopTime", defaultValue = "") String userLoopTime, HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String userIds = FileUtils.getDeviceCodesFromUploadFile(userIdFile, path);

            String result = null;
            if (StringUtils.isNotBlank(ids)) {
                Integer _userGroupLoopTime = StringUtils.isNotBlank(userGroupLoopTime) ? Integer
                        .parseInt(userGroupLoopTime) : 0;
                Integer _userLoopTime = StringUtils.isNotBlank(userLoopTime) ? Integer.parseInt(userLoopTime) : 0;
                result = this.backGroundService.saveUserBackGroundImageMap(Long.parseLong(ids), areaIds, userGroupIds, userIds, _userGroupLoopTime,
                        _userLoopTime);
            }
            String logDescription = null;
            if (StringUtils.isEmpty(result)) {
                logDescription = "绑定背景轮换信息成功!";
            } else {
                logDescription = "绑定背景轮换信息失败!"+"\n" + result;
            }
            RenderUtils.renderText(logDescription, response);
            loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, "绑定", ids + "", logDescription, request);
        } catch (Exception e) {
            e.printStackTrace();
            RenderUtils.renderText( "绑定背景轮换信息失败!", response);
        }
    }


    @RequestMapping(value = "backgroud_bind",method = RequestMethod.POST)
    public void bindBackgroud(@RequestParam(Constants.IDS) String ids,@RequestParam(value = "areaIds", defaultValue = "") String areaIds,
            @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
            @RequestParam(value = "deviceCodes1",required = false) MultipartFile deviceCodeFile,
            @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
            @RequestParam(value = "userIds", defaultValue = "") String userIds,
            @RequestParam(value = "groupLoopTime", defaultValue = "") String groupLoopTime,
            @RequestParam(value = "deviceLoopTime", defaultValue = "") String deviceLoopTime,
            @RequestParam(value = "userGroupLoopTime", defaultValue = "") String userGroupLoopTime,
            @RequestParam(value = "userLoopTime", defaultValue = "") String userLoopTime, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String deviceCodes = FileUtils.getDeviceCodesFromUploadFile(deviceCodeFile, path);

            String result = null;
            int i=0;
            if (StringUtils.isNotBlank(ids)) {
//              if(StringUtils.isBlank(deviceGroupIds) && StringUtils.isBlank(deviceCodes)&& StringUtils.isBlank(userGroupIds) && StringUtils.isBlank(userIds)){
//            	  result = "至少填写一种类型绑定项做绑定，请确认！";
//            	  i++;
//              }else{
            	  Integer deviceGroupLoopTime = StringUtils.isNotBlank(groupLoopTime) ? Integer.parseInt(groupLoopTime)
                          : 0;
                  Integer _deviceLoopTime = StringUtils.isNotBlank(deviceLoopTime) ? Integer.parseInt(deviceLoopTime) : 0;
                  Integer _userGroupLoopTime = StringUtils.isNotBlank(userGroupLoopTime) ? Integer
                          .parseInt(userGroupLoopTime) : 0;
                  Integer _userLoopTime = StringUtils.isNotBlank(userLoopTime) ? Integer.parseInt(userLoopTime) : 0;
                  result = this.backGroundService.saveBackGroundImageMap(Long.parseLong(ids), areaIds,deviceGroupIds,
                          deviceCodes, userGroupIds, userIds, deviceGroupLoopTime, _deviceLoopTime, _userGroupLoopTime,
                          _userLoopTime);
//              }
            }
            String logDescription = null;
            if (StringUtils.isEmpty(result)) {
                logDescription = "绑定背景轮换信息成功!";
            } else {
                logDescription = (i==1?result:"绑定背景轮换信息失败!"+"\n" + result);
            }
            RenderUtils.renderText(logDescription, response);
            logDescription += ";绑定的";
            if(!deviceGroupIds.equals("") && logDescription.indexOf("成功")>0){
            	logDescription += "设备分组ID【"+deviceGroupIds+"】;";
            }
            if(!deviceCodes.equals("")){
            	logDescription += "易视腾编号【"+deviceCodes+"】";
            }
            loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, "绑定设备和分组", ids + "", logDescription, request);
        } catch (Exception e) {
            e.printStackTrace();
            RenderUtils.renderText( "绑定背景轮换信息失败!", response);
            loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, "绑定设备和分组", ids + "", "绑定背景轮换异常!"+e.getMessage(), request);
            
        }
    }

    @RequestMapping(value = "background_image_update")
    public void updateBackGroundImage(BackgroundImage backgroundImage, HttpServletRequest request,
            HttpServletResponse response,@RequestParam(value = "fileField1", required = false) MultipartFile fileField1, @RequestParam(value = "fileField2", required = false) MultipartFile fileField2) {

        try {
        	String path = this.systemConfigService.getSystemConfigByConfigKey("picFiles");
        	upload(fileField1, fileField2,path);
            backgroundImage.setCreateDate(new Date());
            backgroundImage.setUpdateDate(new Date());
            String rsp = this.backGroundService.updateBackGroundImage(backgroundImage);
            String logDescription = null;
            if (StringUtils.isBlank(rsp)) {
                logDescription = "修改背景信息成功!";
            } else {
                logDescription = "修改背景信息失败!<br/>" + rsp;
            }
            RenderUtils.renderText(logDescription, response);
            if(logDescription.indexOf("成功")>0){
            	logDescription += "修改后的背景轮换:"+JsonUtils.toJson(backgroundImage);
            }
            loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, Constants.MODIFY,
                    backgroundImage.getId() + "", logDescription, request);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Update BackGround Image Error", e);
            RenderUtils.renderText(Constants.FAILED, response);
            loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, Constants.MODIFY,
                    backgroundImage.getId() + "", "背景轮换修改异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "background_image_to_update.json")
    public void toUpdateBackGroundImage(@RequestParam("id") Long id, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (id != 0L) {
                BackgroundImage backgroundImage = this.backGroundService.getById(id);
                RenderUtils.renderJson(JsonUtils.toJson(backgroundImage), response);
            }
        } catch (Exception e) {
            LOGGER.error("Get BackGround Image Error", e);
        }
    }

    @RequestMapping(value = "/background_image_delete.json")
    public void deleteBackGroundImage(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        List<Long> idsList = StringUtil.splitToLong(ids);
        String rsp = this.backGroundService.deleteBackGroundImages(idsList);
        String logDescription = null;
        if (StringUtils.isBlank(rsp)) {
            logDescription = "删除背景轮换成功!";
        } else {
            logDescription = "删除背景轮换失败!<br/>" + rsp;
        }
        RenderUtils.renderText(logDescription, response);
        loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, Constants.DELETE, ids + "", logDescription,
                request);
    }

    @RequestMapping(value = "/backgroud_map_delete")
    public void deleteUserGroupMap(@RequestParam(Constants.IDS) String ids,
                                   @RequestParam(value = "isUser", defaultValue = "") String isUser,
                                   HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.backGroundService.deleteBackGroundImageMap(idsList,isUser);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, "解绑", ids,
                        (bool == true) ? "解绑背景轮换成功!" : "解绑背景轮换失败!", request);
            }
        } catch (Exception e) {
            LOGGER.error("Delete Unbind Backgroud Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.BACKGROUD_INFO_MAINTAIN, "解绑", ids,
                    "解绑背景轮换异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/get_background_image_map.json")
    public void getBackGroundImageMap(@RequestParam("type") String type, @RequestParam("id") String id,
            HttpServletRequest request, HttpServletResponse response) {
        String json = this.backGroundService.getBackGroundImageMapByTypeAndId(type, id);
        if (StringUtils.isNotBlank(json)) {
            RenderUtils.renderText(json, response);
        }
    }

    @RequestMapping(value = "/get_user_background_image_map.json")
    public void getUserBackGroundImageMap(@RequestParam("type") String type, @RequestParam("id") Long id,
            HttpServletRequest request, HttpServletResponse response) {
        String json = this.backGroundService.getUserBackGroundImageMapByTypeAndId(type, id);
        if (StringUtils.isNotBlank(json)) {
            RenderUtils.renderText(json, response);
        }
    }

    @RequestMapping(value = "get_background_image_list.json")
    public void findAllBackgroundImage(@RequestParam(value = "areaId", defaultValue = "") Long areaId,
                                 HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "请选择"));
        List<BackgroundImage> list = this.backGroundService.findAllBackgroundImage();
        try {
            if (list != null && list.size() > 0) {
                for (BackgroundImage backgroundImage : list) {
                    tv.add(new TextValue(backgroundImage.getId().toString(), backgroundImage.getName()));
                }
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            LOGGER.error("Get All background image Exception: " + LoggerUtils.printErrorStack(e));
        }
    }

    private void upload(MultipartFile fileField1, MultipartFile fileField2, String path) throws IOException {
		String fileName1 = "";
		String fileName2 = "";
		if (fileField1 != null) {
			fileName1 = fileField1.getOriginalFilename();
		}
		if (fileField2 != null) {
			fileName2 = fileField2.getOriginalFilename();
		}
		if (StringUtils.isNotEmpty(fileName1)) {

			 FileUtils.saveFileFromInputStream(fileField1.getInputStream(), path, fileName1);
		}
		if (StringUtils.isNotEmpty(fileName2)) {
			FileUtils.saveFileFromInputStream(fileField2.getInputStream(), path, fileName2);
		}
	}
}
