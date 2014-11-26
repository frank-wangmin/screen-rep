package com.ysten.local.bss.panel.web.controller;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelItem;
import com.ysten.local.bss.panel.enums.*;
import com.ysten.local.bss.panel.service.IPanelItemService;
import com.ysten.local.bss.panel.service.IPanelService;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.JsonUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.Collator;
import java.util.*;

/**
 * Created by frank on 14-5-19.
 */
@Controller
public class PanelItemController {

    @Autowired
    private IPanelItemService panelItemService;

    @Autowired
    private IPanelService panelService;

    @Autowired
    private ILoggerWebService loggerWebService;

    @Autowired
    private SystemConfigService systemConfigService;

    private static String PIC_URL_PREFIX = null;

    private static String VIDEO_URL_PREFIX = null;

    private static String PIC_PATH = null;

    private static String VIDEO_PATH = null;

    private static Logger logger = LoggerFactory.getLogger(PanelItemController.class);

    @RequestMapping(value = "/to_rel_panel_item_list")
    public String toRelPanelItemList() {
        return "/panel/rel_panel_item_list";
    }

    @RequestMapping(value = "/to_panel_item_list")
    public String toPanelItemList() {
        return "/panel/panel_item_list";
    }

    @ResponseBody
    @RequestMapping(value = "/panel_item_list.json")
    public Pageable<PanelItem> getPanelItemList(PanelQueryCriteria panelQueryCriteria) {
        return panelItemService.findgetPanelItemList(panelQueryCriteria);
    }

    @ResponseBody
    @RequestMapping(value = "/panel_item_list_by_panelId.json")
    public Pageable<PanelItem> getPanelItemListByPanelId(
            @RequestParam(value = "panelId", defaultValue = "") Long panelId,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<PanelItem> pageable = panelItemService.getPanelItemListByPanelId(panelId, Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        return pageable;
    }

    private String getPicUrlPrefix(){
        if(StringUtils.isEmpty(PIC_URL_PREFIX)){
            PIC_URL_PREFIX = systemConfigService.getSystemConfigByConfigKey("picUrl");
        }
        return PIC_URL_PREFIX;
    }

    private String getVideoUrlPrefix(){
        if(StringUtils.isEmpty(VIDEO_URL_PREFIX)){
            VIDEO_URL_PREFIX = systemConfigService.getSystemConfigByConfigKey("videoUrl");
        }
        return VIDEO_URL_PREFIX;
    }

    private String getPicPath(){
        if(StringUtils.isEmpty(PIC_PATH)){
            PIC_PATH = systemConfigService.getSystemConfigByConfigKey("picFiles");
        }
        return PIC_PATH;
    }

    private String getVideoPath(){
        if(StringUtils.isEmpty(VIDEO_PATH)){
            VIDEO_PATH = systemConfigService.getSystemConfigByConfigKey("videoFiles");
        }
        return VIDEO_PATH;
    }

    @RequestMapping(value = "/panel_item_add.json", method = RequestMethod.POST)
    public void addPanelItem(PanelItem panelItem, HttpServletResponse response, HttpServletRequest request,
                             @RequestParam(value = "fileField2", required = false) MultipartFile imageFile,
                             @RequestParam(value = "fileField3", required = false) MultipartFile videoFile) {
        try {
            FileUtils.uploadMultipartFiles(getPicPath(),imageFile);
            FileUtils.uploadMultipartFiles(getVideoPath(),videoFile);
            if(imageFile != null && StringUtils.isNotBlank(imageFile.getOriginalFilename())){
                panelItem.setImageUrl(getPicUrlPrefix() + imageFile.getOriginalFilename());
            }
            if(videoFile != null && StringUtils.isNotBlank(videoFile.getOriginalFilename())){
                panelItem.setVideoUrl(getVideoUrlPrefix() + videoFile.getOriginalFilename());
            }
            panelItem.setCreateTime(DateUtils.getCurrentDate());
            panelItem.setUpdateTime(DateUtils.getCurrentDate());
            panelItem.setOnlineStatus(OnlineStatus.ONLINE.getValue());
            Operator op = ControllerUtil.getLoginOperator(request);
            panelItem.setOprUserId(op.getId());
            panelItem.setVersion(new Date().getTime()); //版本--时间戳
            String result = panelItemService.savePanelItemData(panelItem);
            String msg = Constant.BINED_PANEL_ITEM.equals(result) ? "不可重复关联面板项！" : "新增面板项成功!";

            loggerWebService.saveOperateLog(Constants.PANEL_ITEM_MAINTAIN,
                    Constants.ADD,
                    panelItem.getId() + "",
                    msg,
                    request);
            RenderUtils.renderText(Constant.BINED_PANEL_ITEM.equals(result) ? Constant.BINED_PANEL_ITEM : Constants.SUCCESS, response);

        } catch (Exception e) {
            logger.error("新增面板项失败 : {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PANEL_ITEM_MAINTAIN,
                    Constants.ADD,
                    panelItem.getId() + "",
                    "新增面板项失败," + LoggerUtils.printErrorStack(e),
                    request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/bind_panel_item.json", method = RequestMethod.POST)
    public void bindPanelItem(PanelItem panelItem, HttpServletResponse response, HttpServletRequest request,
                              @RequestParam(value = "fileField2", required = false) MultipartFile imageFile,
                              @RequestParam(value = "fileField3", required = false) MultipartFile videoFile) {
        try {
            FileUtils.uploadMultipartFiles(getPicPath(),imageFile);
            FileUtils.uploadMultipartFiles(getVideoPath(),videoFile);
            if(imageFile != null && StringUtils.isNotBlank(imageFile.getOriginalFilename())){
                panelItem.setImageUrl(getPicUrlPrefix() + imageFile.getOriginalFilename());
            }
            if(videoFile != null && StringUtils.isNotBlank(videoFile.getOriginalFilename())){
                panelItem.setVideoUrl(getVideoUrlPrefix() + videoFile.getOriginalFilename());
            }
            panelItem.setCreateTime(DateUtils.getCurrentDate());
            panelItem.setUpdateTime(DateUtils.getCurrentDate());
            Operator op = ControllerUtil.getLoginOperator(request);
            panelItem.setOnlineStatus(OnlineStatus.ONLINE.getValue());
            panelItem.setOprUserId(op.getId());
            panelItem.setVersion(new Date().getTime()); //版本--时间戳
            String result = panelItemService.savePanelItemData(panelItem);
            String msg = Constant.BINED_PANEL_ITEM.equals(result) ? "不可重复关联面板项！" : "绑定面板项成功!";
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN,
                    Constants.PANEL_CONFIG,
                    panelItem.getId() + "",
                    msg,
                    request);
            if (StringUtils.equals(result, Constant.SUCCESS)) {
                RenderUtils.renderJson(JsonUtils.toJson(panelItem), response);
            } else {
                RenderUtils.renderText(result, response);
            }
        } catch (Exception e) {
            logger.error("面板配置预览-绑定面板项失败 : {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN,
                    Constants.PANEL_CONFIG,
                    panelItem.getId() + "",
                    "绑定面板项失败，" + LoggerUtils.printErrorStack(e),
                    request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/update_binded_panel_item.json", method = RequestMethod.POST)
    public void updateBindedPanelItem(PanelItem panelItem, HttpServletResponse response, HttpServletRequest request,
                                      @RequestParam(value = "fileField2", required = false) MultipartFile imageFile,
                                      @RequestParam(value = "fileField3", required = false) MultipartFile videoFile) {
        try {
            FileUtils.uploadMultipartFiles(getPicPath(),imageFile);
            FileUtils.uploadMultipartFiles(getVideoPath(),videoFile);
            if(imageFile != null && StringUtils.isNotBlank(imageFile.getOriginalFilename())){
                panelItem.setImageUrl(getPicUrlPrefix() + imageFile.getOriginalFilename());
            }
            if(videoFile != null && StringUtils.isNotBlank(videoFile.getOriginalFilename())){
                panelItem.setVideoUrl(getVideoUrlPrefix() + videoFile.getOriginalFilename());
            }

            panelItem.setUpdateTime(DateUtils.getCurrentDate());
            Operator op = ControllerUtil.getLoginOperator(request);
//            panelItem.setOprUserId(op.getId());
            panelItem.setOnlineStatus(OnlineStatus.ONLINE.getValue());
//            panelItem.setId(panelItem.getPanelItemId());
            String result = panelItemService.updatePanelItemData(panelItem, op != null ? op.getId() : null);
            loggerWebService.saveOperateLog(Constants.PANEL_ITEM_MAINTAIN,
                    Constants.MODIFY,
                    panelItem.getId() + "",
                    (Constants.SUCCESS.equals(result)) ? "修改面板项成功!" : "修改面板项失败," + result,
                    request);

            if (StringUtils.equals(result, Constant.SUCCESS)) {
                RenderUtils.renderJson(JsonUtils.toJson(panelItem), response);
            } else {
                RenderUtils.renderText(result, response);
            }

        } catch (Exception e) {
            logger.error("修改面板项失败 : {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PANEL_ITEM_MAINTAIN,
                    Constants.MODIFY,
                    panelItem.getId() + "",
                    "修改面板项失败," + LoggerUtils.printErrorStack(e),
                    request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get_panel_item.json")
    public PanelItem getPanelItemById(@RequestParam(value = "id", defaultValue = "") String id) {
        return panelItemService.getPanelItemById(Long.valueOf(id));
    }

    @ResponseBody
    @RequestMapping(value = "/get_panel_item_detail.json")
    public PanelItem getPanelItemDetailById(@RequestParam(value = "id", defaultValue = "") Long id) {
        if (id != null) {
            PanelItem panelItem = panelItemService.getPanelItemDetail(id);
            return panelItem;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/get_panel_item_show.json")
    public PanelItem getPanelItemToShowById(@RequestParam(value = "id", defaultValue = "") Long id) {
        if (id != null) {
            return panelItemService.getPanelItemById(id);
        }
        return null;
    }

    @RequestMapping(value = "/panel_item_update.json", method = RequestMethod.POST)
    public void updatePanelItem(PanelItem panelItem, HttpServletResponse response, HttpServletRequest request,
                                @RequestParam(value = "fileField2", required = false) MultipartFile imageFile,
                                @RequestParam(value = "fileField3", required = false) MultipartFile videoFile) {
        try {
            FileUtils.uploadMultipartFiles(getPicPath(),imageFile);
            FileUtils.uploadMultipartFiles(getVideoPath(),videoFile);
            if(imageFile != null && StringUtils.isNotBlank(imageFile.getOriginalFilename())){
                panelItem.setImageUrl(getPicUrlPrefix() + imageFile.getOriginalFilename());
            }
            if(videoFile != null && StringUtils.isNotBlank(videoFile.getOriginalFilename())){
                panelItem.setVideoUrl(getVideoUrlPrefix() + videoFile.getOriginalFilename());
            }
            panelItem.setUpdateTime(DateUtils.getCurrentDate());
            Operator op = ControllerUtil.getLoginOperator(request);
            panelItem.setOnlineStatus(OnlineStatus.ONLINE.getValue());
//            panelItem.setOprUserId(op.getId());
            panelItem.setVersion(new Date().getTime()); //版本--时间戳
            String result = "";
            if (panelItem.getId() != null) {
                result = panelItemService.updatePanelItemData(panelItem, op != null ? op.getId() : null);
            }
            String msg = Constants.SUCCESS.equals(result) ? "更新面板项成功！" : "更新面板项失败!";
            loggerWebService.saveOperateLog(Constants.PANEL_ITEM_MAINTAIN,
                    Constants.MODIFY,
                    panelItem.getId() + "",
                    msg,
                    request);
            RenderUtils.renderText(result, response);
        } catch (Exception e) {
            logger.info("更新面板项失败 : {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PANEL_ITEM_MAINTAIN,
                    Constants.MODIFY,
                    panelItem.getId() + "",
                    "更新面板项失败," + LoggerUtils.printErrorStack(e),
                    request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/panel_item_delete.json")
    public void deletePanelItem(@RequestParam(Constants.IDS) String ids,
                                HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                panelItemService.deletePanelItemByIds(idsList);
                this.loggerWebService.saveOperateLog(Constants.PANEL_ITEM_MAINTAIN,
                        Constants.DELETE,
                        ids,
                        "删除面板项成功", request);
                RenderUtils.renderText(Constants.SUCCESS, response);
            }
        } catch (Exception e) {
            logger.error("删除面板项错误 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PANEL_ITEM_MAINTAIN,
                    Constants.DELETE,
                    ids,
                    "删除面板项失败," + LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/panel_item_content_type.json")
    public List<JSONObject> getPanelItemContentType() {
        List<JSONObject> contentTypeList = new ArrayList<JSONObject>();
        for (PanelItemContentType contentType : PanelItemContentType.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", contentType.getName());
            jsonObject.put("value", contentType.getValue());
            contentTypeList.add(jsonObject);
        }
        return contentTypeList;
    }

    @ResponseBody
    @RequestMapping(value = "/panel_item_type.json")
    public List<JSONObject> getPanelItemType() {
        List<JSONObject> contentTypeList = new ArrayList<JSONObject>();
        for (PanelItemType contentType : PanelItemType.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", contentType.getName());
            jsonObject.put("value", contentType.getValue());
            contentTypeList.add(jsonObject);
        }
        return contentTypeList;
    }

    @ResponseBody
    @RequestMapping(value = "/panel_item_action_type.json")
    public List<JSONObject> getPanelItemActionType() {
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        for (PanelItemActionType actionType : PanelItemActionType.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", actionType.getName());
            jsonObject.put("value", actionType.getValue());
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    @ResponseBody
    @RequestMapping(value = "/get_parent_item_list.json")
    public List<JSONObject> selectParentItemList(@RequestParam(value = "contentType", defaultValue = "") String contentType,
                                                 @RequestParam(value = "dpi", defaultValue = "") String dpi) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contentType", contentType);
        map.put("hasSubItem", YesOrNo.YES.getValue());
        map.put("dpi", dpi);
        List<PanelItem> panelItemList = panelItemService.selectParentItemList(map);
        return getJsonObjects(panelItemList);
    }

    @ResponseBody
    @RequestMapping(value = "/get_parent_item_list_update.json")
    public List<JSONObject> selectParentItemListForUpdate(@RequestParam(value = "contentType", defaultValue = "") String contentType,
                                                          @RequestParam(value = "refPanelItemId", defaultValue = "") Long refPanelItemId,
                                                          @RequestParam(value = "dpi", defaultValue = "") String dpi) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contentType", contentType);
        map.put("hasSubItem", YesOrNo.YES.getValue());
        map.put("dpi", dpi);
        List<PanelItem> panelItemList = panelItemService.selectParentItemList(map);
        if (refPanelItemId != null) {
            PanelItem parentItem = panelItemService.getPanelItemById(refPanelItemId);
            if (parentItem != null) {
                panelItemList.add(parentItem);
            }
        }
        return getJsonObjects(panelItemList);
    }

    @ResponseBody
    @RequestMapping(value = "/get_related_item_list.json")
    public List<JSONObject> selectRelatedItemList(@RequestParam(value = "contentType", defaultValue = "") String contentType,
                                                  @RequestParam(value = "dpi", defaultValue = "") String dpi) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contentType", contentType);
        map.put("hasSubItem", YesOrNo.YES.getValue());
        map.put("dpi", dpi);
        List<PanelItem> panelItemList = panelItemService.selectRelatedItemList(map);
        return getJsonObjects(panelItemList);
    }

    @ResponseBody
    @RequestMapping(value = "/get_related_item_list_update.json")
    public List<JSONObject> selectRelatedItemListForUpdate(@RequestParam(value = "contentType", defaultValue = "") String contentType,
                                                           @RequestParam(value = "refPanelItemId", defaultValue = "") Long refPanelItemId,
                                                           @RequestParam(value = "dpi", defaultValue = "") String dpi) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contentType", contentType);
        map.put("hasSubItem", YesOrNo.YES.getValue());
        map.put("dpi", dpi);
        List<PanelItem> panelItemList = panelItemService.selectRelatedItemList(map);
        if (refPanelItemId != null) {
            PanelItem refItem = panelItemService.getPanelItemById(refPanelItemId);
            if (refItem != null) {
                panelItemList.add(refItem);
            }
        }
        return getJsonObjects(panelItemList);
    }

    /*@ResponseBody
    @RequestMapping(value = "/get_not_ref_panel_item_list.json")
    public List<JSONObject> getNotRefPanelItemList() {
        List<PanelItem> panelItemList = panelItemService.getNotRefPanelItemList();
        return getJsonObjects(panelItemList);
    }*/

  /*  @ResponseBody
    @RequestMapping(value = "/get_ref_panel_item_list_exclude_self.json")
    public List<JSONObject> getPanelItemListOfRefExcludeSelf(@RequestParam(value = "editPanelItemId", defaultValue = "") Long editPanelItemId) {
        List<PanelItem> panelItemList = panelItemService.getNotRefPanelItemListExcludeSelf(editPanelItemId);
        return getJsonObjects(panelItemList);
    }*/

    @ResponseBody
    @RequestMapping(value = "/get_all_panel_item.json")
    public List<JSONObject> getAllPanelItem() {
        List<PanelItem> panelItemList = panelItemService.getAllTargetList();
        // 按拼音排序，以方便选择
        final Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(panelItemList, new Comparator<PanelItem>() {

            @Override
            public int compare(PanelItem o1, PanelItem o2) {
                // TODO Auto-generated method stub
                return comparator.compare(o1.getName(), o2.getName());
            }

        });

        return getJsonObjectsWithoutDefault(panelItemList);
    }

    @ResponseBody
    @RequestMapping(value = "/find_dpi_panel_item_list.json")
    public List<JSONObject> getPanelItemListByPanelId(@RequestParam(value = "panelId", defaultValue = "") Long panelId) {
        Panel panel = panelService.getPanelById(panelId);
        List<PanelItem> panelItemList = panelItemService.findPanelItemListByDpi(panel.getResolution());
        logger.info("*******************dpi*******************" + panel.getResolution());
        logger.info("*******************size*******************" + panelItemList.size());
        // 按拼音排序，以方便选择
        /*final Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(panelItemList, new Comparator<PanelItem>() {

            @Override
            public int compare(PanelItem o1, PanelItem o2) {
                // TODO Auto-generated method stub
                return comparator.compare(o1.getName(), o2.getName());
            }
        });*/
        return getJsonObjectsWithoutDefault(panelItemList);
    }

    private JSONObject getDefaultSelect() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "请选择");
        jsonObject.put("value", "");
        return jsonObject;
    }

    private List<JSONObject> getJsonObjects(List<PanelItem> panelItemList) {
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        jsonObjects.add(getDefaultSelect());
        if (!CollectionUtils.isEmpty(panelItemList)) {
            for (PanelItem panelItem : panelItemList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("text", panelItem.getName());
                jsonObject.put("value", panelItem.getId());
                jsonObjects.add(jsonObject);
            }
        }
        return jsonObjects;
    }

    private List<JSONObject> getJsonObjectsWithoutDefault(List<PanelItem> panelItemList) {
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        if (!CollectionUtils.isEmpty(panelItemList)) {
            for (PanelItem panelItem : panelItemList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("text", panelItem.getName());
                jsonObject.put("value", panelItem.getId());
                jsonObjects.add(jsonObject);
            }
        }
        return jsonObjects;
    }

    @RequestMapping(value = "/panel_item_name_exists")
    public void checkPanelItemNameExists(@RequestParam(value = "id", defaultValue = "") String id,
                                         @RequestParam("name") String name, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (name != null && !"".equals(name)) {
            PanelItem panelItem = this.panelItemService.getPanelItemByName(name);
            if (panelItem != null && !panelItem.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    /*private void upload(MultipartFile fileField2, MultipartFile fileField3, String path, String videoPath) throws IOException {
        String fileName2 = "";
        String fileName3 = "";
        if (fileField2 != null) {
            fileName2 = fileField2.getOriginalFilename();
        }
        if (fileField3 != null) {
            fileName3 = fileField3.getOriginalFilename();
        }
        if (StringUtils.isNotEmpty(fileName2)) {
            FileUtils.saveFileFromInputStream(fileField2.getInputStream(), path, fileName2);
        }
        if (StringUtils.isNotEmpty(fileName3)) {

            FileUtils.saveFileFromInputStream(fileField3.getInputStream(), videoPath, fileName3);
        }
    }*/
}
