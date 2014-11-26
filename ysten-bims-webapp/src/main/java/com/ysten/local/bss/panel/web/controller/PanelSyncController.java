package com.ysten.local.bss.panel.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.panel.domain.PanelItem;
import com.ysten.local.bss.panel.service.IPanelItemService;
import com.ysten.local.bss.panel.service.IPanelSyncService;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cwang
 * @version 2014-5-20 下午8:53:23
 * 
 */
@Controller
public class PanelSyncController {
    @Autowired
    private IPanelSyncService panelSyncService;
    @Autowired
    private ILoggerWebService loggerWebService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PanelSyncController.class);
    private static final String SUCCESS = "success";
    private static final String UNSUCCESS = "unsuccess";

    @Autowired
    private IPanelItemService panelItemService;

    @RequestMapping(value = "/syncEpgPanelDatas.json")
    public void syncEpgPanelDatas(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("synchronize EPG Panel Data!");
        try {
            Long startTime = System.currentTimeMillis();
            LOGGER.info("before :"+System.currentTimeMillis());
            panelSyncService.loadDatas();
            LOGGER.info("after get data interval :"+(System.currentTimeMillis() - startTime));
           /* Operator op = ControllerUtil.getLoginOperator(request);
            panelSyncService.syncEpgPanelDatas(op!=null ? op.getId():null);*/
            panelSyncService.syncEpgPanelDatas();
            RenderUtils.renderJson(JsonUtils.toJson(SUCCESS), response);
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.SYNS_EPG_PANEL_DATA, "", "同步播控数据成功",
                    request);
            LOGGER.info("interval :"+(System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            RenderUtils.renderJson(JsonUtils.toJson(UNSUCCESS), response);
            LOGGER.error("synchronize EPG Panel Exception: " + LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.SYNS_EPG_PANEL_DATA, "", "同步播控数据失败,"+LoggerUtils.printErrorStack(e),
                    request);
            return;
        }
    }


    @RequestMapping(value = "/syncCenterPanelDatas.json")
    public void syncCenterPanelDatas(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("synchronize Center Panel Data!");
        try {
            Long startTime = System.currentTimeMillis();
            LOGGER.info("before :"+System.currentTimeMillis());
            panelSyncService.syncCenterPanelDatas();
            RenderUtils.renderJson(JsonUtils.toJson(SUCCESS), response);
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.SYNS_EPG_PANEL_DATA, "", "同步中心数据成功",
                    request);
            LOGGER.info("interval :"+(System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            RenderUtils.renderJson(JsonUtils.toJson(UNSUCCESS), response);
            LOGGER.error("synchronize Center Panel Exception: " + e);
            loggerWebService.saveOperateLog(Constants.PANEL_INFO_MAINTAIN, Constants.SYNS_EPG_PANEL_DATA, "", "同步中心数据失败",
                    request);
            return;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/testPaneItem.json")
    public List<PanelItem> test(HttpServletRequest request, HttpServletResponse response){
        return panelItemService.findSublItemListByPanelItemParentId(19314L);
    }

}
