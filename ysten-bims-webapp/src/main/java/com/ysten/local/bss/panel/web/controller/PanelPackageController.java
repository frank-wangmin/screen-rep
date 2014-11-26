package com.ysten.local.bss.panel.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.PanelPackageDeviceMap;
import com.ysten.local.bss.device.domain.PanelPackageUserMap;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.domain.PanelPackageNavigation;
import com.ysten.local.bss.panel.enums.IsOrNotDefault;
import com.ysten.local.bss.panel.enums.OnlineStatus;
import com.ysten.local.bss.panel.enums.PanelType;
import com.ysten.local.bss.panel.service.IPanelDataStyleForJsService;
import com.ysten.local.bss.panel.service.IPanelPackageMapService;
import com.ysten.local.bss.panel.service.IPanelPackageService;
import com.ysten.local.bss.panel.vo.PanelPreview;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by frank on 14-5-19.
 */
@Controller
public class PanelPackageController {

    private static Logger logger = LoggerFactory.getLogger(PanelPackageController.class);

    @Autowired
    private IPanelPackageService panelPackageWebService;

    @Autowired
    private IPanelPackageMapService panelPackageMapService;

    @Autowired
    private ILoggerWebService loggerWebService;

    @Autowired
    private IPanelDataStyleForJsService panelDataStyleForJsService;

    @Autowired
    private SystemConfigService systemConfigService;

    private static String PIC_URL_PREFIX = null;

    private static String PIC_PATH = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(PanelPackageController.class);
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private String getPicUrlPrefix(){
        if(StringUtils.isEmpty(PIC_URL_PREFIX)){
            PIC_URL_PREFIX = systemConfigService.getSystemConfigByConfigKey("picUrl");
        }
        return PIC_URL_PREFIX;
    }

    private String getPicPath(){
        if(StringUtils.isEmpty(PIC_PATH)){
            PIC_PATH = systemConfigService.getSystemConfigByConfigKey("picFiles");
        }
        return PIC_PATH;
    }

    @RequestMapping(value = "/to_panel_package_layout")
    public String toPanelPackageLayout() {
        return "/panel/panel_package_list";
//        return "/panel/panel_package_layout";
    }

    @RequestMapping(value = "/to_panel_package_list")
    public String toPanePackagelList() {
        return "/panel/panel_package_list";
    }

    @RequestMapping(value = "/to_panel_list_by_package")
    public String toPanelListByPackage() {
        return "/panel/panel_list_package";
    }

    @RequestMapping(value = "/createZips.json")
    public void createZip(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                panelDataStyleForJsService.createAllZips(idsList);
                RenderUtils.renderText(Constants.SUCCESS, response);
                this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, Constants.CREATE_ZIP, ids,
                        "创建Zip文件成功", request);
            }
        } catch (Exception e) {
            logger.error("创建Zip文件错误 : {}", e);
            this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, Constants.CREATE_ZIP, ids,
                    "创建Zip文件失败", request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/get_panel_package_list.json")
    public void findPanelPackageList(@RequestParam(value = "areaId", defaultValue = "") Long areaId,
                                     HttpServletRequest request, HttpServletResponse response) {

        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "请选择"));
        List<PanelPackage> list = this.panelPackageWebService.findAllPanelPackageList();
        try {
            if (list != null && list.size() > 0) {
                for (PanelPackage panelPackage : list) {
                    tv.add(new TextValue(panelPackage.getId().toString(), panelPackage.getPackageName()));
                }
            }
            RenderUtils.renderJson(com.ysten.utils.json.JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            LOGGER.error("Get All Panel package Exception: " + LoggerUtils.printErrorStack(e));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/panel_package_list.json")
    public Pageable<PanelPackage> getPanelPackageList(PanelQueryCriteria panelQueryCriteria) {
        return panelPackageWebService.getTargetList(panelQueryCriteria);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllCustomedTargetList.json")
    public List<JSONObject> getAllCustomedTargetList() {
        List<PanelPackage> panelPackages = panelPackageWebService.getAllCustomedTargetList();
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (PanelPackage panelPackage : panelPackages) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", panelPackage.getPackageName());
            jsonObject.put("value", panelPackage.getId());
            jsonList.add(jsonObject);
        }
        return jsonList;
    }

    @ResponseBody
    @RequestMapping(value = "/panel_package_preview.json")
    public List<PanelPreview> getPanelPreviewByPackageId(
            @RequestParam(value = "packageId", defaultValue = "") Long packageId,
            @RequestParam(value = "dpi", defaultValue = "") String dpi
            ) {
        if (packageId != null) {
            return panelPackageWebService.getPanelPreviewByPackageId(packageId,dpi);
        }
        return null;
    }

    @RequestMapping(value = "/panel_package_add.json", method = RequestMethod.POST)
    public void addPanelPackage(PanelPackage panelPackage, HttpServletResponse response, HttpServletRequest request,
                                @RequestParam(value = "backgroundImg720p", required = false) MultipartFile backgroundImg720p,
                                @RequestParam(value = "backgroundImg1080p", required = false) MultipartFile backgroundImg1080p) {
        try {
            PanelPackage defaultPackage = null;
            if (panelPackage.getIsDefault() != null && panelPackage.getIsDefault() == IsOrNotDefault.DEFULT.getValue()) {
                defaultPackage = panelPackageWebService.getDefaultPackage();
            }
            if (defaultPackage != null) {
                logger.error("新增面板包失败");
                loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, Constants.ADD, "", "新增面板包失败，已存在默认的面板包!", request);
                RenderUtils.renderText(Constants.ISDEFAULT, response);
                return;
            }
            FileUtils.uploadMultipartFiles(getPicPath(),backgroundImg720p,backgroundImg1080p);
            if(backgroundImg720p != null && StringUtils.isNotBlank(backgroundImg720p.getOriginalFilename())){
                panelPackage.setDefaultBackground720p(getPicUrlPrefix() + backgroundImg720p.getOriginalFilename());
            }
            if(backgroundImg1080p != null && StringUtils.isNotBlank(backgroundImg1080p.getOriginalFilename())){
                panelPackage.setDefaultBackground1080p(getPicUrlPrefix() + backgroundImg1080p.getOriginalFilename());
            }
            panelPackage.setCreateTime(DateUtils.getCurrentDate());
            panelPackage.setCreateTime(DateUtils.getCurrentDate());
            panelPackage.setOnlineStatus(OnlineStatus.ONLINE.getValue());
            Operator op = ControllerUtil.getLoginOperator(request);
            panelPackage.setOprUserId(op.getId());
            panelPackage.setPackageType(PanelType.REAL_TIME.getValue());
            panelPackage.setVersion(new Date().getTime()); //版本--时间戳
            panelPackageWebService.savePanelPackage(panelPackage);
            loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, Constants.ADD, panelPackage.getId() + "", "新增面板包成功!", request);
            RenderUtils.renderText(Constants.SUCCESS, response);

        } catch (Exception e) {
            logger.error("新增面板包失败 : {}", e);
            loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, Constants.ADD, "", "新增面板包失败，" + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get_panel_package.json")
    public PanelPackage getPanelPackageById(@RequestParam(value = "id", defaultValue = "") String id) {
        if (!StringUtils.isBlank(id)) {
            return panelPackageWebService.getPanelPackageById(Long.valueOf(id));
        }
        return new PanelPackage();
    }

  /*  @RequestMapping(value = "/config_panel_package.json")
    public void configPanelPackage(
//            @RequestParam(value = "formData", defaultValue = "") String formData,

            @RequestParam(value = "packageId", defaultValue = "") Long packageId,
            @RequestParam(value = "panelId", defaultValue = "") Long panelId,
            @RequestParam(value = "panelId", defaultValue = "") Long rootNavId,
            @RequestParam(value = "panelId", defaultValue = "") String headNavIds,
            @RequestParam(value = "sort", defaultValue = "") Integer sort,
            @RequestParam(value = "isLock", defaultValue = "") String isLock,
            @RequestParam(value = "display", defaultValue = "") String display,
            @RequestParam(value = "panelLogo", defaultValue = "") String panelLogo,
            HttpServletResponse response) {

        try {
            if (packageId != null && panelId != null && sort != null) {
                PanelPackageMap panelPackageMap = panelPackageMapService.getMapByPackageIdAndPanelId(packageId, panelId);
                if (panelPackageMap != null) {
                    panelPackageMap.setSortNum(sort);
                    panelPackageMap.setIsLock(isLock);
                    panelPackageMap.setDisplay(display);
                    panelPackageMap.setPanelLogo(panelLogo);
                    panelPackageMap.setPanelId(panelId);
                    panelPackageMap.setNavId(headNavIds+String.valueOf(rootNavId));
                    panelPackageMapService.updateSort(panelPackageMap);
                }
            }
            RenderUtils.renderText(Constants.SUCCESS, response);
        } catch (Exception e) {
            logger.error("面板排序调整失败 : {}", e);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }*/


    @RequestMapping(value = "/delete_panel_by_panelPackageId.json")
    public void deleteDeviceByGroupId(@RequestParam("panelPackageId") Long panelPackageId,
                                      @RequestParam("panelIds") String panelIds,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            List<Long> idsList = new ArrayList<Long>();
            if (StringUtils.isNotBlank(panelIds)) {
                idsList = StringUtil.splitToLong(panelIds);
            }
            boolean bool = this.panelPackageMapService.deletePanelPackageMapByPackagelId(panelPackageId, idsList);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "面板包配置-移除面板", panelIds,
                    (bool == true) ? "移除面板成功!" : "移除面板失败!", request);
        } catch (Exception e) {
            this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "面板包配置-移除面板", panelIds,
                    "移除面板失败，" + LoggerUtils.printErrorStack(e), request);
            LOGGER.error("面板包配置-移除面板", LoggerUtils.printErrorStack(e));
        }
    }

    @RequestMapping(value = "/config_panel_package.json")
    public void configPanelPackage(
            @RequestParam(value = "formData", defaultValue = "") String formData,
            @RequestParam(value = "dpi", defaultValue = "") String dpi,
            @RequestParam(value = "packageId", defaultValue = "") Long packageId,
            HttpServletResponse response, HttpServletRequest request) {

        try {
            if (StringUtils.isNotEmpty(formData)) {
                JSONObject reagobj = JSONObject.fromObject(formData);
                String rowsValue = reagobj.getString("rows");
                if (StringUtils.isNotEmpty(rowsValue)) {
                    List<PanelPackageNavigation> list = gson.fromJson(rowsValue, new TypeToken<List<PanelPackageNavigation>>() {
                    }.getType());
                    for (int i = 0; i < list.size() - 1; i++) {
                       /* List<PanelPackageMap> panelPackageMapList = panelPackageMapService.verifyIfExistBinded(list.get(i).getId(), packageId);
                        if (!CollectionUtils.isEmpty(panelPackageMapList)) {
                            logger.info("panel is already binded with package");
                            RenderUtils.renderText("同一面板包不可重复配置同一面板", response);
                            return;
                        }*/
                        for (int j = list.size() - 1; j > i; j--) {
                            if (list.get(j).getId().intValue() == list.get(i).getId().intValue()) {
                                RenderUtils.renderText("同一面板包不可重复配置同一面板", response);
                                this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "配置面板包", String.valueOf(packageId),
                                        "面板包配置失败，同一面板包不可重复配置同一面板", request);
                                return;
                            }
                        }
                    }

                    Operator op = ControllerUtil.getLoginOperator(request);
                    panelPackageMapService.updatePanelPackageConfig(packageId, list, op != null ? op.getId() : null,dpi);
                    RenderUtils.renderText(Constants.SUCCESS, response);
                    this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "配置面板包", String.valueOf(packageId),
                            "面板包配置成功", request);
                } else {
                    RenderUtils.renderText("请先配置面板包", response);
                    this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "配置面板包", String.valueOf(packageId),
                            "面板包配置失败，未配置面板包", request);
                }
            }
        } catch (Exception e) {
            logger.error("面板包配置失败 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "配置面板包", String.valueOf(packageId),
                    "面板包配置失败，" + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/panel_package_update.json", method = RequestMethod.POST)
    public void updatePanelPackage(PanelPackage panelPackage, HttpServletResponse response, HttpServletRequest request,
                                   @RequestParam(value = "backgroundImg720p", required = false) MultipartFile backgroundImg720p,
                                   @RequestParam(value = "backgroundImg1080p", required = false) MultipartFile backgroundImg1080p) {
        try {
            PanelPackage defaultPackage = null;
            if (panelPackage.getIsDefault() != null && panelPackage.getIsDefault() == IsOrNotDefault.DEFULT.getValue()) {
                defaultPackage = panelPackageWebService.getDefaultPackage();
            }
            if (defaultPackage != null && !defaultPackage.getId().equals(panelPackage.getId())) {
                logger.info("更新面板包失败");
                RenderUtils.renderText(Constants.ISDEFAULT, response);
                return;
            }
            FileUtils.uploadMultipartFiles(getPicPath(),backgroundImg720p,backgroundImg1080p);
            if(backgroundImg720p != null && StringUtils.isNotBlank(backgroundImg720p.getOriginalFilename())){
                panelPackage.setDefaultBackground720p(getPicUrlPrefix() + backgroundImg720p.getOriginalFilename());
            }
            if(backgroundImg1080p != null && StringUtils.isNotBlank(backgroundImg1080p.getOriginalFilename())){
                panelPackage.setDefaultBackground1080p(getPicUrlPrefix() + backgroundImg1080p.getOriginalFilename());
            }
            PanelPackage oldPanelPackage = panelPackageWebService.getPanelPackageById(panelPackage.getId());
            if(oldPanelPackage != null){
                if(StringUtils.isNotBlank(oldPanelPackage.getZipUrl())){
                    panelPackage.setZipUrl(oldPanelPackage.getZipUrl());
                }
                if(StringUtils.isNotBlank(oldPanelPackage.getZipUrl1080p())){
                    panelPackage.setZipUrl1080p(oldPanelPackage.getZipUrl1080p());
                }
            }
            panelPackage.setUpdateTime(DateUtils.getCurrentDate());
            panelPackage.setOnlineStatus(OnlineStatus.ONLINE.getValue());
            Operator op = ControllerUtil.getLoginOperator(request);
            panelPackage.setOprUserId(op != null ? op.getId() : null);
            panelPackage.setPackageType(PanelType.REAL_TIME.getValue());
            panelPackage.setVersion(new Date().getTime()); //版本--时间戳
            if (panelPackage.getId() != null) {
                panelPackageWebService.update(panelPackage);
            }
            loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, Constants.MODIFY, panelPackage.getId() + "",
                    "更新面板包成功!", request);
            RenderUtils.renderText(Constants.SUCCESS, response);

        } catch (Exception e) {
            logger.error("更新面板包失败 : {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, Constants.MODIFY, panelPackage.getId() + "",
                    "更新面板包失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/panel_package_delete.json")
    public void deletePanelPackage(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                panelPackageWebService.deleteByIds(idsList);
                this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, Constants.DELETE, ids,
                        "删除面板包成功", request);
                RenderUtils.renderText(Constants.SUCCESS, response);
            }
        } catch (Exception e) {
            logger.error("删除面板包错误 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, Constants.DELETE, ids,
                    "删除面板包失败", request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/panel_package_map_delete")
    public void deletePanelPackageMap(@RequestParam(Constants.IDS) String ids,
                                      @RequestParam(value = "isUser", defaultValue = "") String isUser,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                panelPackageWebService.deleteMapByIds(idsList, isUser);
                this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "解绑面板包", ids, "解绑面板包关系成功", request);
                RenderUtils.renderText(Constants.SUCCESS, response);
            }
        } catch (Exception e) {
            logger.error("解绑面板包关系错误 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "解绑面板包", ids, "解绑面板包关系失败", request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/get_panel_device")
    public void getPanelDeviceMap(@RequestParam(Constants.ID) String id,
                                  @RequestParam(value = "type", defaultValue = "") String type,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("");
        if (StringUtils.isNotBlank(id)) {
            List<PanelPackageDeviceMap> mapList = this.panelPackageWebService.findPanelPackageDeviceMapByPanelIdAndType(Long.parseLong(id), type);
            for (PanelPackageDeviceMap map : mapList) {
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

    @RequestMapping(value = "/get_panel_user")
    public void getPanelUserMap(@RequestParam(Constants.ID) String id,
                                @RequestParam(value = "type", defaultValue = "") String type,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("");
        if (StringUtils.isNotBlank(id)) {
            List<PanelPackageUserMap> mapList = this.panelPackageWebService.findPanelPackageUserMapByPanelIdAndType(Long.parseLong(id), type);
            for (PanelPackageUserMap map : mapList) {
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


    @RequestMapping(value = "bind_user_map.json")
    public void bindUserMap(@RequestParam("id") Long id,
                            @RequestParam(value = "areaIds", defaultValue = "") String areaIds,
                            @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                            @RequestParam(value = "userIds3", required = false) MultipartFile userIdFile,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String userIds = FileUtils.getDeviceCodesFromUploadFile(userIdFile, path);

            String result = null;
            result = this.panelPackageWebService.bindUserMap(id, areaIds, userGroupIds, userIds);
            String logDescription = null;
            if (StringUtils.isEmpty(result)) {
                logDescription = "绑定用户和分组成功!";
            } else {
                logDescription = "绑定用户和分组失败!" + "\n" + result;
            }
            RenderUtils.renderText(logDescription, response);
            loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "绑定用户和分组", String.valueOf(id), logDescription,
                    request);
        } catch (Exception e) {
            loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "绑定用户和分组", String.valueOf(id), "绑定用户和分组失败," + LoggerUtils.printErrorStack(e),
                    request);
            logger.error("绑定用户和分组失败的异常为：" + LoggerUtils.printErrorStack(e));
            RenderUtils.renderText("绑定用户和分组失败!", response);
        }
    }

    @RequestMapping(value = "bind_map.json")
    public void bindMap(@RequestParam("id") Long id, @RequestParam(value = "areaIds", defaultValue = "") String areaIds,
                        @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                        @RequestParam(value = "deviceCodes3", defaultValue = "") MultipartFile stbIdFile,
                        @RequestParam(value = "userGroupIds", defaultValue = "") String userGroupIds,
                        @RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
                        HttpServletResponse response) {
        try {

            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String stbIds = FileUtils.getDeviceCodesFromUploadFile(stbIdFile, path);

            String result = null;
            int i = 0;
//        	if(StringUtils.isBlank(deviceGroupIds) && StringUtils.isBlank(deviceCodes)&& StringUtils.isBlank(userGroupIds) && StringUtils.isBlank(userIds)) {
//            	result = "至少填写一种类型绑定项做绑定，请确认！";
//            	i++;
//            }else{
            result = this.panelPackageWebService.bindMap(id, areaIds, deviceGroupIds, stbIds, userGroupIds, userIds);
//            }
            String logDescription = null;
            if (StringUtils.isEmpty(result)) {
                logDescription = "绑定设备和分组成功!";
            } else {
                logDescription = (i == 1 ? result : "绑定设备和分组失败!" + "\n" + result);
            }
            RenderUtils.renderText(logDescription, response);
            loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "绑定设备和分组", String.valueOf(id), "面板包绑定设备：" + stbIds + ",与设备分组：" + deviceGroupIds + "成功！",
                    request);
        } catch (Exception e) {
//            e.printStackTrace();
            loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "绑定设备和分组", String.valueOf(id), "绑定设备和分组失败," + LoggerUtils.printErrorStack(e),
                    request);
            logger.error("绑定设备和分组失败的异常为：" + LoggerUtils.printErrorStack(e));
            RenderUtils.renderText("绑定设备和分组失败!", response);
        }
    }

    @RequestMapping(value = "/get_panel_package_list_of_area.json")
    public void findPanelPackageListOfArea(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
                                           @RequestParam(value = "distCode", defaultValue = "") String distCode,
                                           HttpServletRequest request, HttpServletResponse response) {

        List<TextValue> tv = new ArrayList<TextValue>();
        if (String.valueOf(Constants.ZERO).equals(par)) {
            tv.add(new TextValue(String.valueOf(""), "请选择"));
        }
        if (String.valueOf(Constants.ONE).equals(par)) {
            tv.add(new TextValue(String.valueOf(""), "全部"));
        }
        List<PanelPackage> list = this.panelPackageWebService.findPanelPackageListOfArea(distCode);
        try {
            if (list != null && list.size() > 0) {
                for (PanelPackage panelPackage : list) {
                    tv.add(new TextValue(panelPackage.getId().toString(), panelPackage.getPackageName()));
                }
            }
            RenderUtils.renderJson(com.ysten.utils.json.JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            LOGGER.error("Get Panel package of Area Exception: " + LoggerUtils.printErrorStack(e));
        }
    }

}
