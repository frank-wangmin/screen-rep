package com.ysten.local.bss.facade.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelItem;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.domain.PanelPanelItemMap;
import com.ysten.local.bss.panel.domain.PreviewItem;
import com.ysten.local.bss.panel.domain.PreviewItemData;
import com.ysten.local.bss.panel.domain.PreviewTemplate;
import com.ysten.local.bss.panel.service.IPanelDistributeService;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.data.DataUtils;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.json.JsonUtils;

/**
 * Panel数据同步接口，省级用
 * Created by Joyce on 14-7-2.
 */
@Controller
@RequestMapping(value = "/center/")
public class PanelCenterSyncController {
    @Autowired
    private IPanelDistributeService panelDistributeService;

    private static Logger LOGGER = LoggerFactory.getLogger(PanelCenterSyncController.class);

    private transient List<Panel> panelList = new ArrayList<Panel>();
    private transient List<PanelPackage> panelPackageList = new ArrayList<PanelPackage>();
    private transient List<PreviewTemplate> previewTemplateList = new ArrayList<PreviewTemplate>();
    private transient List<PanelItem> panelItemList = new ArrayList<PanelItem>();
    private transient List<Navigation> navigationList = new ArrayList<Navigation>();
    private transient List<PanelPanelItemMap> panelItemMapList = new ArrayList<PanelPanelItemMap>();
    private transient List<PanelPackageMap> panelPackageMapList = new ArrayList<PanelPackageMap>();
    private transient List<PreviewItem> previewItemList = new ArrayList<PreviewItem>();
    private transient List<PreviewItemData> previewItemDataList = new ArrayList<PreviewItemData>();

    private boolean receivePanelPackage(String requestStr) throws Exception {
        boolean result = true;
        try {
            panelPackageList = JsonUtil.getList4Json(requestStr, PanelPackage.class, null);

        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = false;
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = false;
        }
        return result;
    }

    private boolean receivePreviewTemplate(String requestStr) throws Exception {
        boolean result;
        result = true;
        try {
            previewTemplateList = JsonUtil.getList4Json(requestStr, PreviewTemplate.class, null);

        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = false;
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = false;
        }
        return result;
    }

    private boolean receivePanel(String requestStr) throws Exception {
        boolean result = true;
        try {
            panelList = JsonUtil.getList4Json(requestStr, Panel.class, null);

        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = false;
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = false;
        }
        return result;
    }

    @RequestMapping(value = "/getPanelData")
    public void getPanelData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = "";
        String type = request.getParameter(Constant.DISTRIBUTE_TYPE);
        LOGGER.info("type ==" + type);
        if (StringUtils.isNotBlank(type)) {
            result = panelDistributeService.findPanelDatas(type);
        } else {
            LOGGER.info("The request param is null.");
        }
        if (StringUtils.isBlank(result)) {
            result = Constant.FAILED;
        }
        RenderUtils.renderJson(result, response);
    }

    @RequestMapping(value = "/receivePanelDatas")
    public void receivePanelData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result;
        String requestStr;
        try {
            requestStr = DataUtils.asString(request.getInputStream(), Constant.UTF_ENCODE);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("ysten-bims-facade,sync panel data param:" + requestStr);
            }
            String type = request.getParameter(Constant.DISTRIBUTE_TYPE);
            if (StringUtils.isNotBlank(requestStr) && StringUtils.isNotBlank(type)) {
                //parse the inputStream to object according to the type
                result = parsetInputStream(type, requestStr);
            } else {
                result = JsonUtils
                        .toJson(new JsonResultBean(Constant.FALSE, "The param is null when sync the province panel data."));
            }
            if (StringUtils.isBlank(result)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE, "parse panel data success"));
            }
            RenderUtils.renderJson(result, response);
        } catch (SQLException el) {
            LOGGER.error("insert panel data failed with error is " + el);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "insert panel data exception!"));
            RenderUtils.renderJson(result, response);
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync province panel data exception!"));
            RenderUtils.renderJson(result, response);
        }
    }

    private String parsetInputStream(String type, String requestStr) throws Exception {
        String result = "";
      /*  if (Constant.DISTRIBUTE_TYPE_PANEL_PACKAGE.equals(type)) {
            if (!receivePanelPackage(requestStr)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "distribute panel data failed"));
                return result;
            }
        } else*/
      /*  if (Constant.DISTRIBUTE_TYPE_PREVIEW_TEMPLATE.equals(type)) {
            if (!receivePreviewTemplate(requestStr)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "distribute panel data failed"));
                return result;
            }
        } else */
        if (Constant.DISTRIBUTE_TYPE_PANEL.equals(type)) {
            if (!receivePanel(requestStr)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync province panel data failed"));
                return result;
            }
        } else if(Constant.DISTRIBUTE_TYPE_PANEL_ITEM.equals(type)) {
            if (!receivePanelItem(requestStr)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync province panel item data failed"));
                return result;
            }
        }
       /* } else if (Constant.DISTRIBUTE_TYPE_NAVIGATION.equals(type)) {
            if (!receiveNav(requestStr)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "distribute panel data failed"));
                return result;
            }
            }*/
         else if (Constant.DISTRIBUTE_TYPE_PANEL_ITEM_MAP.equals(type)) {
            if (!receivePanelItemMap(requestStr)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync province panelItemMap data failed"));
                return result;
            }
        }
    /*else if (Constant.DISTRIBUTE_TYPE_PANEL_PACKAGE_MAP.equals(type)) {
            if (!receivePanelPackageMap(requestStr)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "distribute panel data failed"));
                return result;
            }
        } else if (Constant.DISTRIBUTE_TYPE_PREVIEW_ITEM.equals(type)) {
            if (!receivePreviewItem(requestStr)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "distribute panel data failed"));
                return result;
            }
        }
        */
        else if (Constant.DISTRIBUTE_TYPE_PREVIEW_ITEM_DATA.equals(type)) {
            if (!receivePreviewItemData(requestStr)) {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync province PreviewItemData data failed"));
                return result;
            }
            else {
                //insert data to db
                LOGGER.info("################# insert the data begin ##################");
                long startTime = System.currentTimeMillis();
                LOGGER.info("before save data start time =" + startTime);
                panelDistributeService.savePanelData(panelPackageList, previewTemplateList, panelList, panelItemList, navigationList, panelItemMapList, panelPackageMapList, previewItemList, previewItemDataList);
                LOGGER.info("after save data start time =" + System.currentTimeMillis());
                LOGGER.info("interval of save data start time =" + (System.currentTimeMillis() - startTime));
                result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE, "insert panel data to db success"));
                LOGGER.info("######### insert the data end #######");
            }
        }
        return result;
    }

    private boolean receivePanelItem(String requestStr) throws Exception {
        boolean result = true;
        try {
            panelItemList = JsonUtil.getList4Json(requestStr, PanelItem.class, null);
        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = false;
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = false;
        }
        return result;
    }

    private boolean receiveNav(String requestStr) throws Exception {
        boolean result = true;
        try {
            navigationList = JsonUtil.getList4Json(requestStr, Navigation.class, null);
        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = false;
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = false;
        }
        return result;
    }

    private boolean receivePanelItemMap(String requestStr) throws Exception {
        boolean result = true;
        try {
            panelItemMapList = JsonUtil.getList4Json(requestStr, PanelPanelItemMap.class, null);

        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = false;
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = false;
        }
        return result;
    }

    private boolean receivePanelPackageMap(String requestStr) throws Exception {
        boolean result = true;
        try {
            panelPackageMapList = JsonUtil.getList4Json(requestStr, PanelPackageMap.class, null);

        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = false;
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = false;
        }
        return result;
    }


    private boolean receivePreviewItem(String requestStr) throws Exception {
        boolean result = true;
        try {
            previewItemList = JsonUtil.getList4Json(requestStr, PreviewItem.class, null);

        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = false;
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = false;
        }
        return result;
    }

    private boolean receivePreviewItemData(String requestStr) throws Exception {
        boolean result = true;
        try {
            previewItemDataList = JsonUtil.getList4Json(requestStr, PreviewItemData.class, null);

        } catch (JSONException jsonException) {
            LOGGER.error("accept request.previewItemData exception{}", jsonException);
            result = false;
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = false;
        }
        return result;
    }
}