package com.ysten.local.bss.panel.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.domain.PanelPackageNavigation;
import com.ysten.local.bss.panel.domain.PreviewItemData;
import com.ysten.local.bss.panel.enums.OnlineStatus;
import com.ysten.local.bss.panel.enums.PanelStatus;
import com.ysten.local.bss.panel.enums.PanelType;
import com.ysten.local.bss.panel.repository.IPanelPackageRepository;
import com.ysten.local.bss.panel.service.INavigationService;
import com.ysten.local.bss.panel.service.IPanelService;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.NewCollectionsUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IAreaWebService;
import com.ysten.local.bss.web.service.IInterfaceUrlWebService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;

/**
 * panel info manager controller Created by frank on 14-5-12.
 */
@Controller
public class PanelController {

    @Autowired
    private IPanelService panelWebService;

    @Autowired
    private ILoggerWebService loggerWebService;

    @Autowired
    private IInterfaceUrlWebService interfaceUrlWebService;

    @Autowired
    private IAreaWebService areaWebService;

    @Autowired
    private INavigationService navigationService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private IPanelPackageRepository panelPackageRepository;

    private static String PIC_URL_PREFIX = null;

    private static String PIC_PATH = null;
    
    private static Logger logger = LoggerFactory.getLogger(PanelController.class);

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

    @RequestMapping(value = "/to_panel_list")
    public String toPanelList() {
        return "/panel/panel_list";
    }

    @RequestMapping(value = "/to_panel_layout")
    public String toPanelLayout() {
        return "/panel/panel_layout";
    }

    @ResponseBody
    @RequestMapping(value = "/panel_list.json")
    public Pageable<Panel> getPanelList(PanelQueryCriteria panelQueryCriteria) {

        logger.info(panelQueryCriteria.toString());
        return this.panelWebService.getPanelList(panelQueryCriteria);
    }

    @ResponseBody
    @RequestMapping(value = "/panel_list_packageId.json")
    public Pageable<PanelPackageNavigation> getPanelListByPackageId(
            @RequestParam(value = "packageId", defaultValue = "") Long packageId,
            @RequestParam(value = "dpi", defaultValue = "") String dpi,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize) {
        Pageable<PanelPackageNavigation> pageable = panelWebService.getPanelListByPackageId(packageId,dpi,Integer.valueOf(pageNo),
                Integer.parseInt(pageSize));
        return pageable;
    }

    @RequestMapping(value = "/panel_add.json", method = RequestMethod.POST)
    public void addPanel(Panel panel, HttpServletResponse response, HttpServletRequest request,
                         @RequestParam(value = "fileField", required = false) MultipartFile smallimgFile,
                         @RequestParam(value = "fileField1", required = false) MultipartFile panelIconFile,
                         @RequestParam(value = "fileField2", required = false) MultipartFile imageFile,
                         @RequestParam(value = "fileField3", required = false) MultipartFile bigimgFile) {
        try {
            FileUtils.uploadMultipartFiles(getPicPath(),smallimgFile,panelIconFile,imageFile,bigimgFile);
            if(smallimgFile != null && StringUtils.isNotBlank(smallimgFile.getOriginalFilename())){
                panel.setSmallimg(getPicUrlPrefix() + smallimgFile.getOriginalFilename());
            }
            if(panelIconFile != null && StringUtils.isNotBlank(panelIconFile.getOriginalFilename())){
                panel.setPanelIcon(getPicUrlPrefix() + panelIconFile.getOriginalFilename());
            }
            if(imageFile != null && StringUtils.isNotBlank(imageFile.getOriginalFilename())){
                panel.setImageUrl(getPicUrlPrefix() + imageFile.getOriginalFilename());
            }
            if(bigimgFile != null && StringUtils.isNotBlank(bigimgFile.getOriginalFilename())){
                panel.setBigimg(getPicUrlPrefix() + bigimgFile.getOriginalFilename());
            }
            panel.setCreateTime(DateUtils.getCurrentDate());
            panel.setUpdateTime(DateUtils.getCurrentDate());
            panel.setOnlineStatus(OnlineStatus.INIT.getValue());
           /* if(panel.getIsCustom() != null && panel.getIsCustom() == 0){
                panel.setDisplay("true");
                panel.setIsLock("true");
            }*/
            Operator op = ControllerUtil.getLoginOperator(request);
           /* if (op != null) {
                panel.setOprUserId(op.getId());
            }*/
            panelWebService.insert(panel,op !=null ? op.getId() : null);
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.ADD, panel.getId() + "", "新增面板成功!", request);
            RenderUtils.renderText(Constants.SUCCESS, response);

        } catch (Exception e) {
            logger.error("新增面板失败: {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.ADD, "", "新增面板失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }


    @RequestMapping(value = "/push_panel_to_center.json", method = RequestMethod.POST)
    public void pushPanelData(HttpServletResponse response, HttpServletRequest request) {
//        logger.info("areaId is : " + areaId);
        Long startTime = System.currentTimeMillis();
        boolean bool = false;
        String description = "";
//        Area area = new Area();
        try {
//            if (areaId != null) {
           /*     List<Long> areaIdList = new ArrayList<Long>();
                if (Constants.AREA_ALL.equals(areaId)) {
                    List<AreaBean> areaList = this.areaWebService.findAllArea();
                    for (AreaBean areaBean : areaList) {
                        if (!areaBean.getExpanded()) {
                            areaIdList.add(areaBean.getId());
                        }
                    }
                } else {
                    areaIdList.add(areaId);
                }*/
            /*    for (Long id : areaIdList) {
                    InterfaceUrl interfaceUrl = this.interfaceUrlWebService.getByAreaAndName(id, InterfaceUrl.InterfaceName.RECEIVEAPPSOFTCODE);
//                            InterfaceUrl.InterfaceName.PANELDISTRIBUTE);
                    if (interfaceUrl == null) {
                        area = areaWebService.getAreaById(id);
                        description = "请先维护省份" + area.getName() + "的接口Url表!";
                        return;
                    }
                    String url = interfaceUrl.getInterfaceUrl();

                    if (!bool) {
                        description = Constants.FAILED;
                        return;
                    }
                }*/
//            } else {
//                description = Constants.FAILED;
//            }
            bool = this.panelWebService.pushPanelData();
            if (StringUtils.isBlank(description)) {
                description = (bool == true) ? Constants.SUCCESS : Constants.FAILED;
            }
            RenderUtils.renderText(description, response);
            this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.DISTRIBUTE_PANEL, "",
                    "同步省级数据成功！", request);
            logger.info("interval :" + (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            logger.error("同步省级数据失败", LoggerUtils.printErrorStack(e));
            RenderUtils.renderText(StringUtils.isBlank(description) ? Constants.FAILED : description, response);
            this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.DISTRIBUTE_PANEL, "",
                    "同步省级数据失败," + LoggerUtils.printErrorStack(e), request);
        }
    }

    @RequestMapping(value = "/bindPanelPackage.json", method = RequestMethod.POST)
    public void bindPanelPackage(@RequestParam(value = "panelId") Long panelId,
                                 @RequestParam(value = "packageId") Long packageId, @RequestParam(value = "upNavIds") String upNavIds,
                                 @RequestParam(value = "downNavId") Long downNavId, @RequestParam(value = "sortNum") Integer sortNum,
                                 @RequestParam(value = "panelLogo") String panelLogo,
                                 @RequestParam(value = "isLock") String isLock,
                                 @RequestParam(value = "display") String display,
                                 HttpServletResponse response, HttpServletRequest request) {
        try {

            logger.info("panelId:" + panelId + ",packageId:" + packageId + ",upNavIds:" + upNavIds + ",downNavId:"
                    + downNavId + ",sortNum:" + sortNum + ",isLock:" + isLock + ",display:" + display);

            // 根据panelId 和 packageId查询是否已经绑定
            List<PanelPackageMap> panelPackageMapList = panelWebService.verifyIfExistBinded(panelId, packageId);
            if (!CollectionUtils.isEmpty(panelPackageMapList)) {
                logger.info("panel is already binded with package");
                RenderUtils.renderText(Constants.BINDED, response);
                this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, "绑定面板包", "panelId:" + panelId + "绑定" + "packageId:" + packageId,
                        "绑定面板包失败，面板已经绑定过此面板包", request);
                return;
            }

            PanelPackageMap panelPackageMap = new PanelPackageMap();
            Panel panel = panelWebService.getPanelById(panelId);
            if (panel == null) {
                RenderUtils.renderText(Constants.FAILED, response);
                logger.info("panel is null when binding package");
                this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, "绑定面板包", "panelId:" + panelId + "绑定" + "packageId:" + packageId,
                        "绑定面板包失败，面板不存在", request);
                return;
            }
            panelPackageMap.setPanelId(panelId);

            //用于同步播控数据需要
            if (panel.getEpgPanelId() != null) {
                panelPackageMap.setEpgPanelId(panel.getEpgPanelId());
            }
//            String epg_ids = "";
            String navIdStr;
            List<Navigation> navigationList = NewCollectionsUtils.list();
            if (StringUtils.isNotBlank(upNavIds)) {
                /*List<Long> upNavIdList = StringUtil.splitToLong(upNavIds);
                for (Long navId : upNavIdList) {
                    Navigation navigation = navigationService.getNavigationById(navId);
                    if (navigation != null) navigationList.add(navigation);
                }*/
                navIdStr = upNavIds + "," + downNavId;
            } else {
                navIdStr = downNavId + "";
            }
           /* Navigation navigation = navigationService.getNavigationById(downNavId);
            if (navigation != null) navigationList.add(navigation);
            for (Navigation navigationTemp : navigationList) {
                if (navigationTemp.getEpgNavId() != null) {
                    epg_ids += navigationTemp.getEpgNavId() + ",";
                }
            }
            if (StringUtils.isNotBlank(epg_ids)) {
                panelPackageMap.setEpgNavId(StringUtils.substring(epg_ids, 0, epg_ids.length() - 1));
            }*/

            Operator op = ControllerUtil.getLoginOperator(request);
            panelPackageMap.setOprUserId(op.getId());
            panelPackageMap.setPackageId(packageId);
            panelPackageMap.setPanelLogo(panelLogo);
            panelPackageMap.setNavId(navIdStr);
            panelPackageMap.setSortNum(sortNum);
            panelPackageMap.setIsLock(isLock);
            panelPackageMap.setDisplay(display);
            panelPackageMap.setCreateTime(DateUtils.getCurrentDate());
            panelPackageMap.setUpdateTime(DateUtils.getCurrentDate());
            panelWebService.insertPanelPackageMap(panelPackageMap);//done
            
            //面板包和面板关系表变更时，更新面板包的version版本号
            PanelPackage panelPackage =this.panelPackageRepository.getPanelPackageById(packageId);
            panelPackage.setVersion(new Date().getTime()); //版本--时间戳
            this.panelPackageRepository.update(panelPackage);

            this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, "绑定面板包", "panelId:" + panelId + "绑定" + "packageId:" + packageId,
                    "绑定面板包成功", request);
            RenderUtils.renderText(Constants.SUCCESS, response);
        } catch (Exception e) {
            logger.error("绑定面板包失败 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, "绑定面板包", "panelId:" + panelId + "绑定" + "packageId:" + packageId,
                    "绑定面板包失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/unbind_panel_package.json", method = RequestMethod.POST)
    public void unBindPanelPackage(@RequestParam(value = "packageId", defaultValue = "") Long packageId,
                                   @RequestParam(value = "panelIds", defaultValue = "") String panelIds, HttpServletResponse response,
                                   HttpServletRequest request) {

        try {
            if (packageId != null && StringUtils.isNotBlank(panelIds)) {
                List<Long> panelIDs = StringUtil.splitToLong(panelIds);
                panelWebService.unBindPanelPackage(packageId, panelIDs);
                RenderUtils.renderText(Constants.SUCCESS, response);
                this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "解绑面板包", panelIds,
                        "解绑成功", request);
            } else {
                RenderUtils.renderText(Constants.FAILED, response);
                this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "解绑面板包", panelIds,
                        "解绑失败", request);
            }
        } catch (Exception e) {
            logger.error("解绑面板包失败", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_PACKAGE_MAINTAIN, "解绑面板包", panelIds,
                    "解绑失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/get_panel_byId.json")
    public Panel getPanelById(@RequestParam(value = "id", defaultValue = "") String id) {
        if (StringUtils.isNotBlank(id)) {
            return panelWebService.getPanelById(Long.valueOf(id));
        }
        return new Panel();
    }

    @RequestMapping(value = "/panel_name_exists")
    public void checkPanelNameExists(@RequestParam(value = "id", defaultValue = "") String id,
                                     @RequestParam("panelName") String panelName, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (StringUtils.isNotEmpty(panelName)) {
            List<Panel> panelList = this.panelWebService.getPanelByName(panelName);
            if (panelList != null && panelList.size() > 0) {
                if (panelList.get(0) != null && !panelList.get(0).getId().toString().equals(id)) {
                    str = Constants.UN_USABLE;
                } else {
                    str = Constants.USABLE;
                }
            } else {
                str = Constants.USABLE;
            }

        }
        RenderUtils.renderText(str, response);
    }

    // 获取上线的面板
    @ResponseBody
    @RequestMapping(value = "/get_online_panel_list.json")
    public List<TextValue> getOnlinePanels(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
                                           @RequestParam(value = "dpi", defaultValue = "") String dpi,
                                           HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Panel> list = this.panelWebService.getAllOnlinePanels(OnlineStatus.ONLINE.getValue(),dpi);
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue(" ", "所有"));
        }
        for (Panel panel : list) {
            tv.add(new TextValue(panel.getId() + "", panel.getPanelName()));
        }
        return tv;
//        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/panel_update.json", method = RequestMethod.POST)
    public void updatePanel(Panel panel, HttpServletResponse response, HttpServletRequest request,
                            @RequestParam(value = "fileField", required = false) MultipartFile smallimgFile,
                            @RequestParam(value = "fileField1", required = false) MultipartFile panelIconFile,
                            @RequestParam(value = "fileField2", required = false) MultipartFile imageFile,
                            @RequestParam(value = "fileField3", required = false) MultipartFile bigimgFile) {
        try {
            FileUtils.uploadMultipartFiles(getPicPath(),smallimgFile,panelIconFile,imageFile,bigimgFile);
            if(smallimgFile != null && StringUtils.isNotBlank(smallimgFile.getOriginalFilename())){
                panel.setSmallimg(getPicUrlPrefix() + smallimgFile.getOriginalFilename());
            }
            if(panelIconFile != null && StringUtils.isNotBlank(panelIconFile.getOriginalFilename())){
                panel.setPanelIcon(getPicUrlPrefix() + panelIconFile.getOriginalFilename());
            }
            if(imageFile != null && StringUtils.isNotBlank(imageFile.getOriginalFilename())){
                panel.setImageUrl(getPicUrlPrefix() + imageFile.getOriginalFilename());
            }
            if(bigimgFile != null && StringUtils.isNotBlank(bigimgFile.getOriginalFilename())){
                panel.setBigimg(getPicUrlPrefix() + bigimgFile.getOriginalFilename());
            }

//            panel.setUpdateTime(DateUtils.getCurrentDate());
            Operator op = ControllerUtil.getLoginOperator(request);
//            panel.setOprUserId(op.getId());
          /*  if(panel.getIsCustom() != null && panel.getIsCustom() == 0){
                panel.setDisplay("true");
                panel.setIsLock("true");
            }*/
            if (panel.getId() != null) {
                Panel oldPanel = panelWebService.getPanelById(panel.getId());
                panel.setOnlineStatus(oldPanel.getOnlineStatus());
                panelWebService.updatePanel(panel,op!=null ? op.getId() : null);
            }
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.MODIFY, panel.getId() + "",
                    "更新面板成功!", request);
            RenderUtils.renderText(Constants.SUCCESS, response);

        } catch (Exception e) {
            logger.error("更新面板失败 : {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.MODIFY, panel.getId() + "",
                    "更新面板失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/panel_delete.json")
    public void deletePanel(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                panelWebService.deleteByIds(idsList);
                RenderUtils.renderText(Constants.SUCCESS, response);
                this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.DELETE, ids,
                        "删除成功", request);
            }
        } catch (Exception e) {
            logger.error("删除面板错误 : {}", e);
            this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.DELETE, ids,
                    "删除失败", request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/panel_online.json")
    public void onlinePanel(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                Operator op = ControllerUtil.getLoginOperator(request);
                String result = panelWebService.onlinePanel(idsList,op!=null?op.getId():null);
                RenderUtils.renderText(result, response);
                String msg = StringUtils.equals(result, Constant.SUCCESS) ? "上线成功" : "上线失败，面板配置错误！";
                this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.PANEL_ONLINE, ids, msg, request);
            }
        } catch (Exception e) {
            logger.error("面板上线错误 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.PANEL_ONLINE, ids, "上线失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAIL, response);
        }
    }

    @RequestMapping(value = "/panel_downline.json")
    public void downlinePanel(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                Operator op = ControllerUtil.getLoginOperator(request);
                panelWebService.downLinePanel(idsList,op!=null?op.getId():null);
                RenderUtils.renderText(Constants.SUCCESS, response);
                this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.PANEL_ONLINE, ids, "下线成功", request);
            }
        } catch (Exception e) {
            logger.error("面板下线错误 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.PANEL_ONLINE, ids, "下线失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAIL, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getPreviewItemDataByPanelId.json")
    public List<PreviewItemData> getPreviewItemDataByPanelId(
            @RequestParam(value = "panelId", defaultValue = "") Long panelId) {
        if (panelId != null) {
            return panelWebService.getPreviewItemDataListByPanelId(panelId);
        }
        return null;
    }

    @RequestMapping(value = "/batch_update_preview_item_data.json", method = RequestMethod.POST)
    public void addBatchPreviewItem(@RequestBody PreviewItemData[] previewItemDatas, HttpServletRequest request,
                                    HttpServletResponse response) {

        try {
            if (previewItemDatas != null && previewItemDatas.length > 0) {
                Operator operator = ControllerUtil.getLoginOperator(request);
                if (operator != null) {
                    logger.debug("current loginUserName =" + operator.getLoginName());
                    logger.debug("current oprUserId =" + operator.getId());
                    panelWebService.submitPreviewItemDatas(previewItemDatas, operator.getId());
                }
            }
            RenderUtils.renderText(Constants.SUCCESS, response);
            this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.PANEL_CONFIG, "", "面板配置成功", request);
        } catch (Exception e) {
            logger.error("面板配置错误 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.PANEL_CONFIG, "", "面板配置失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/panel_online_status.json")
    public List<JSONObject> getOnlineStatus() {
        List<JSONObject> onlineStatusList = new ArrayList<JSONObject>();
        for (OnlineStatus onlineStatus : OnlineStatus.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", onlineStatus.getName());
            jsonObject.put("value", onlineStatus.getValue());
            onlineStatusList.add(jsonObject);
        }
        return onlineStatusList;
    }

    @ResponseBody
    @RequestMapping(value = "/panel_status.json")
    public List<JSONObject> getPanelStatus() {
        List<JSONObject> panelStatusList = new ArrayList<JSONObject>();
        for (PanelStatus panelStatus : PanelStatus.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", panelStatus.getName());
            jsonObject.put("value", panelStatus.getValue());
            panelStatusList.add(jsonObject);
        }
        return panelStatusList;
    }

    @ResponseBody
    @RequestMapping(value = "/panel_type.json")
    public List<JSONObject> getPanelType() {
        List<JSONObject> panelStatusList = new ArrayList<JSONObject>();
        for (PanelType panelType : PanelType.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", panelType.getName());
            jsonObject.put("value", panelType.getValue());
            panelStatusList.add(jsonObject);
        }
        return panelStatusList;
    }
}
