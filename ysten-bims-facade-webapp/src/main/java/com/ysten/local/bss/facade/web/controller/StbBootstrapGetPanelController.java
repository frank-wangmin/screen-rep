package com.ysten.local.bss.facade.web.controller;

import com.ysten.local.bss.bootstrap.service.IBootstrapGetPanelService;
import com.ysten.utils.http.RenderUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 获取终端引导初始化的panelpackageid
 */
@Controller
@RequestMapping("/stb")
public class StbBootstrapGetPanelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StbBootstrapGetPanelController.class);
    @Autowired
    private IBootstrapGetPanelService bootstrapGetPanelService;

    /**
     * 终端初始化引导接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/bootstrapGetPanel.json")
    public void deviceBootstrapGetPanel(
            @RequestParam(value = "ystenId", defaultValue = "") String ystenId,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (StringUtils.isBlank(ystenId) ||ystenId.equals("00000000000000000000000000000000")) {
                String screenId = bootstrapGetPanelService.getDefaultPanelPackage();
                RenderUtils.renderJson(screenId, response);
                return;
            }

            LOGGER.info("获取初始化引导信息的screenid:,ystenId={}", new Object[]{ystenId});
            String screenId = bootstrapGetPanelService.getPanelPackageByYstenId(ystenId);
            RenderUtils.renderJson(screenId, response);
        } catch (Exception e) {
            LOGGER.error("deviceBootstrapGetPanel error:{}", e);
            RenderUtils.renderJson("", response);
        }
    }
}