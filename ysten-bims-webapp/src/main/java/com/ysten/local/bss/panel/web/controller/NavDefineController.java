package com.ysten.local.bss.panel.web.controller;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.enums.NavigationType;
import com.ysten.local.bss.panel.enums.OnlineStatus;
import com.ysten.local.bss.panel.enums.PanelResolution;
import com.ysten.local.bss.panel.service.INavigationService;
import com.ysten.local.bss.panel.service.IPanelService;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.NewCollectionsUtils;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NavDefineController {

    private static Logger logger = LoggerFactory.getLogger(NavDefineController.class);
    @Autowired
    private INavigationService navigationService;
    @Autowired
    private IPanelService panelService;
    @Autowired
    private ILoggerWebService loggerWebService;
    private static int status = 0; // 导航状态标识：0|有效; 1|无效

    @Autowired
    private SystemConfigService systemConfigService;

    private static String PIC_URL_PREFIX = null;

    @RequestMapping(value = "/to_nav_define_list")
    public String toNavDefineList() {
        return "/panel/panel_nav_define_list";
    }

    // 获取列表
    @ResponseBody
    @RequestMapping(value = "/nav_define_list.json")
    public Pageable<Navigation> getNavList(PanelQueryCriteria panelQueryCriteria) {
        return navigationService.getNavigationListByCondition(panelQueryCriteria);
    }

    // 获取上部导航
    @ResponseBody
    @RequestMapping(value = "/heard_nav_list.json")
    public List<TextValue> getUpList(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
                                     @RequestParam(value = "panelId", defaultValue = "") Long panelId) {
        Panel panel = null;
        if (panelId != null) {
            panel = panelService.getPanelById(panelId);
        }
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Navigation> list = this.navigationService.findNavigationByNavType(NavigationType.HEAD_NAV.getValue());
        if (panel != null) {
            list = filterDpiNavigation(list, panel);
        }
        if (!StringUtils.isEmpty(par)) {
            tv.add(new TextValue(" ", "所有"));
        }
        for (Navigation nav : list) {
            tv.add(new TextValue(nav.getId() + "", nav.getNavName()));
        }
        return tv;
        // RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    /**
     * 根据面板的分辨率过滤导航
     *
     * @param list
     * @param panel
     * @return
     */
    private List<Navigation> filterDpiNavigation(List<Navigation> list, Panel panel) {
        List<Navigation> navigationList = NewCollectionsUtils.list();
        if (!CollectionUtils.isEmpty(list)) {
            for (Navigation navigation : list) {
                if (StringUtils.equals(panel.getResolution(), navigation.getResolution())) {
                    navigationList.add(navigation);
                }
            }
        }
        return navigationList;
    }

    @ResponseBody
    @RequestMapping(value = "/root_nav_list_720p.json")
    public List<TextValue> get720PDownList() {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Navigation> list = this.navigationService.findNavigationByNavTypeAndDpi(NavigationType.ROOT_NAV.getValue(), PanelResolution.RESOLUTION_720p.getValue());
        for (Navigation nav : list) {
            tv.add(new TextValue(nav.getId() + "", nav.getNavName()));
        }
        return tv;
    }

    @ResponseBody
    @RequestMapping(value = "/root_nav_list_1080p.json")
    public List<TextValue> get1080PDownList() {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Navigation> list = this.navigationService.findNavigationByNavTypeAndDpi(NavigationType.ROOT_NAV.getValue(), PanelResolution.RESOLUTION_1080p.getValue());
        for (Navigation nav : list) {
            tv.add(new TextValue(nav.getId() + "", nav.getNavName()));
        }
        return tv;
    }

    @ResponseBody
    @RequestMapping(value = "/head_nav_list_720p.json")
    public List<TextValue> get720PHeadList() {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Navigation> list = this.navigationService.findNavigationByNavTypeAndDpi(NavigationType.HEAD_NAV.getValue(), PanelResolution.RESOLUTION_720p.getValue());
        for (Navigation nav : list) {
            tv.add(new TextValue(nav.getId() + "", nav.getNavName()));
        }
        return tv;
    }

    @ResponseBody
    @RequestMapping(value = "/head_nav_list_1080p.json")
    public List<TextValue> get1080PHeadList() {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Navigation> list = this.navigationService.findNavigationByNavTypeAndDpi(NavigationType.HEAD_NAV.getValue(), PanelResolution.RESOLUTION_1080p.getValue());
        for (Navigation nav : list) {
            tv.add(new TextValue(nav.getId() + "", nav.getNavName()));
        }
        return tv;
    }

    // 获取下部导航
    @ResponseBody
    @RequestMapping(value = "/root_nav_list.json")
    public List<TextValue> getDownList(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
                                       @RequestParam(value = "panelId", defaultValue = "") Long panelId) {
//		List<TextValue> tv = new ArrayList<TextValue>();
//		List<Navigation> list = this.navigationService.findNavigationByNavType(NavigationType.ROOT_NAV.getValue());
        Panel panel = null;
        if (panelId != null) {
            panel = panelService.getPanelById(panelId);
        }
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Navigation> list = this.navigationService.findNavigationByNavType(NavigationType.ROOT_NAV.getValue());
        if (panel != null) {
            list = filterDpiNavigation(list, panel);
        }
        if (!StringUtils.isEmpty(par)) {
            tv.add(new TextValue(" ", "所有"));
        }
        for (Navigation nav : list) {
            tv.add(new TextValue(nav.getId() + "", nav.getNavName()));
        }
        return tv;
        // RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/nav_name_exists")
    public void checkNavNameExists(@RequestParam(value = "id", defaultValue = "") String id,
                                   @RequestParam("navName") String navName, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (navName != null && !"".equals(navName)) {
            Navigation nav = this.navigationService.findNavigationByName(navName);
            if (nav != null && !nav.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/nav_epgNavId_exists")
    public void checkEpgNavIdExists(@RequestParam(value = "id", defaultValue = "") String id,
                                    @RequestParam("epgNavId") String epgNavId, HttpServletResponse response) {
        String str = "";
        if (epgNavId != null && !"".equals(epgNavId)) {
            Navigation nav = this.navigationService.getNavigationByEpgNavId(Long.parseLong(epgNavId));
            if (nav != null && !nav.getId().toString().equals(id)) {
                str = "存在外部数据EPG导航编号为：" + epgNavId + ",确定将做更新操作";
            } else {
                str = "";
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/nav_define_add")
    public void addNavDefineInfo(Navigation nav, HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                 @RequestParam(value = "fileField2", required = false) MultipartFile fileField2,
                                 @RequestParam(value = "focusImgFile", required = false) MultipartFile focusImgFile,
                                 @RequestParam(value = "currentImageFile", required = false) MultipartFile currentImageFile
    ) {
        try {
            String path = this.systemConfigService.getSystemConfigByConfigKey("picFiles");
            FileUtils.uploadMultipartFiles(path, imageFile, fileField2, focusImgFile, currentImageFile);
            if(imageFile != null && StringUtils.isNotBlank(imageFile.getOriginalFilename())){
                nav.setImageUrl(getPicPrefixUrl() + imageFile.getOriginalFilename());
            }
            if(focusImgFile != null && StringUtils.isNotBlank(focusImgFile.getOriginalFilename())){
                nav.setFocusImg(getPicPrefixUrl() + focusImgFile.getOriginalFilename());
            }
            if(currentImageFile != null && StringUtils.isNotBlank(currentImageFile.getOriginalFilename())){
                nav.setCurrentPageImg(getPicPrefixUrl() + currentImageFile.getOriginalFilename());
            }
            nav.setCreateTime(new Date());
            nav.setUpdateTime(new Date());
            nav.setOnlineStatus(OnlineStatus.ONLINE.getValue());
            Operator op = ControllerUtil.getLoginOperator(request);
            nav.setOprUserId(op.getId());
            nav.setStatus(status);
            nav.setVersion(new Date().getTime()); //版本--时间戳
            boolean bool = this.navigationService.saveNavigation(nav);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            String info = "新增导航 标题：" + nav.getTitle() + ";导航类型：" + nav.getNavType() + ";动作类型:" + nav.getActionType()
                    + "动作地址:" + nav.getActionUrl() + ";序号:" + nav.getSortNum();
            String description = (bool == true ? "新增导航信息成功！" + info : "新增导航信息失败");
            this.loggerWebService.saveOperateLog(Constants.PANEL_NAV_MAINTAIN, Constants.ADD, nav.getId() + "",
                    description, request);
        } catch (Exception e) {
            logger.error("新增导航失败", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_NAV_MAINTAIN, Constants.ADD, nav.getId() + "",
                    "新增导航失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(ControllerUtil.returnString(false), response);
        }
    }

    private String getPicPrefixUrl(){
        if(StringUtils.isEmpty(PIC_URL_PREFIX)){
            PIC_URL_PREFIX = systemConfigService.getSystemConfigByConfigKey("picUrl");
        }
        return PIC_URL_PREFIX;
    }

    @RequestMapping(value = "/nav_define_to_update")
    public void getNavDefineInfo(@RequestParam(value = "navId", defaultValue = "") String navId,
                                 HttpServletResponse response) {
        if (navId != null && !"".equals(navId)) {
            Navigation nav = new Navigation();
            if (navId.indexOf(",") < 0) {
                nav = this.navigationService.getNavigationById(Long.valueOf(navId));
            } else {
                List<Navigation> navigationList = navigationService.findNavigationByNavIds(StringUtil.splitToLong(navId));
                StringBuilder sb = new StringBuilder("");
                for (Navigation navigation : navigationList) {
                    if (StringUtils.isNotBlank(sb.toString())) {
                        sb.append(",").append(navigation.getNavName());
                    } else {
                        sb.append(navigation.getNavName());
                    }
                }
                nav.setNavName(sb.toString());
            }
            RenderUtils.renderJson(JsonUtils.toJson(nav), response);
            logger.debug(JsonUtils.toJson(nav));
        }
    }

    @RequestMapping(value = "/nav_define_update")
    public void updateNavDefineInfo(Navigation nav, HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                    @RequestParam(value = "fileField2", required = false) MultipartFile fileField2,
                                    @RequestParam(value = "focusImgFile", required = false) MultipartFile focusImgFile,
                                    @RequestParam(value = "currentImageFile", required = false) MultipartFile currentImageFile
    ) {
        try {
            String path = this.systemConfigService.getSystemConfigByConfigKey("picFiles");
            FileUtils.uploadMultipartFiles(path, imageFile, fileField2, focusImgFile, currentImageFile);
            if(imageFile != null && StringUtils.isNotBlank(imageFile.getOriginalFilename())){
                nav.setImageUrl(getPicPrefixUrl() + imageFile.getOriginalFilename());
            }
            if(focusImgFile != null && StringUtils.isNotBlank(focusImgFile.getOriginalFilename())){
                nav.setFocusImg(getPicPrefixUrl() + focusImgFile.getOriginalFilename());
            }
            if(currentImageFile != null && StringUtils.isNotBlank(currentImageFile.getOriginalFilename())){
                nav.setCurrentPageImg(getPicPrefixUrl() + currentImageFile.getOriginalFilename());
            }
            nav.setUpdateTime(new Date());
            Operator op = ControllerUtil.getLoginOperator(request);
            nav.setOprUserId(op.getId());
            nav.setOnlineStatus(OnlineStatus.ONLINE.getValue());
            nav.setVersion(new Date().getTime()); //版本--时间戳
            boolean bool = this.navigationService.updateNavigation(nav);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            String info = "修改后导航 标题：" + nav.getTitle() + ";导航类型：" + nav.getNavType() + ";动作类型:" + nav.getActionType()
                    + "动作地址:" + nav.getActionUrl() + ";序号:" + nav.getSortNum();
            String description = (bool == true ? "修改导航信息成功！" + info : "修改导航信息失败");
            this.loggerWebService.saveOperateLog(Constants.PANEL_NAV_MAINTAIN, Constants.MODIFY, nav.getId() + "",
                    description, request);
        } catch (Exception e) {
            logger.error("修改导航信息失败", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_NAV_MAINTAIN, Constants.MODIFY, nav.getId() + "",
                    "修改导航信息失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(ControllerUtil.returnString(false), response);
        }
    }

    @RequestMapping(value = "/nav_define_delete.json")
    public void deleteNavDefineInfo(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                    HttpServletResponse response) {
        // boolean bool = false;
        // if (StringUtils.isNotBlank(ids)) {
        List<Long> idsList = StringUtil.splitToLong(ids);
        // bool = this.navigationService.deleteNavDefinesByIds(idsList);
        String rsp = this.navigationService.deleteNavDefinesByCondition(idsList);
        String logDescription = null;
        if (StringUtils.isBlank(rsp)) {
            logDescription = "删除导航信息成功!";
        } else {
            logDescription = "删除导航信息失败!" + rsp;
        }
        RenderUtils.renderText(logDescription, response);
        // String description = (bool == true?"删除导航信息成功！":"删除导航信息失败！");
        this.loggerWebService.saveOperateLog(Constants.PANEL_NAV_MAINTAIN, Constants.DELETE, ids, logDescription,
                request);
        // }
        // RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllUpNavList.json")
    public List<JSONObject> getAllUpNavList() {
        // Pageable<Navigation> pageable =
        // this.navigationService.getListByCondition(null,1, null, null,null);
        // List<Navigation> navigationList = pageable.getRows();
        List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
        for (int i = 0; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", "上部导航" + i);
            jsonObject.put("value", i);
            jsonObjectList.add(jsonObject);
        }
        /*
		 * for (Navigation navigation : navigationList) { JSONObject jsonObject
		 * = new JSONObject(); jsonObject.put("text", navigation.getTitle());
		 * jsonObject.put("value",navigation.getId());
		 * jsonObjectList.add(jsonObject); }
		 */

        return jsonObjectList;
    }

    @ResponseBody
    @RequestMapping(value = "/getAllDownNavList.json")
    public List<JSONObject> getAllDownNavList() {
        // Pageable<Navigation> pageable =
        // this.navigationService.getListByCondition(null,2, null, null,null);
        // List<Navigation> navigationList = pageable.getRows();
        List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
        for (int i = 0; i < 6; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", "下部导航" + i);
            jsonObject.put("value", i);
            jsonObjectList.add(jsonObject);
        }
        // for (Navigation navigation : navigationList) {
        // JSONObject jsonObject = new JSONObject();
        // jsonObject.put("text", navigation.getTitle());
        // jsonObject.put("value",navigation.getId());
        // jsonObjectList.add(jsonObject);
        // }
        return jsonObjectList;
    }

   /* private void upload(String path, MultipartFile... files) throws IOException {
        String fileName;
        for (MultipartFile multipartFile : files) {
            if (multipartFile != null) {
                fileName = multipartFile.getOriginalFilename();
                FileUtils.saveFileFromInputStream(multipartFile.getInputStream(), path, fileName);
            }
        }
    }*/
}
