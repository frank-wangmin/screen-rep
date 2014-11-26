package com.ysten.local.bss.panel.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ysten.local.bss.panel.service.IPanelDataStyleService;
import com.ysten.utils.http.RenderUtils;

/**
 * @author cwang
 * @version 2014-5-20 下午8:53:23 返回给设备的面板数据格式。
 */
@Controller
@RequestMapping(value = "/panel/")
public class PanelDataStyleController {
	@Autowired
	private IPanelDataStyleService panelDataStyleService;
	private static final Logger LOGGER = LoggerFactory.getLogger(PanelDataStyleController.class);

	/**
	 * 自定义接口
	 * 
	 * @param templateId
	 *            面板包ID，设备从boot接口获取。
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getCustomerPanel")
	public void getCustomerPanel(@RequestParam(value = "templateId", defaultValue = "") String templateId,
			HttpServletRequest request, HttpServletResponse response) {
		double start = System.currentTimeMillis();
		LOGGER.debug("面板自定义接口: templateId={}", new Object[] { templateId });
		try {
			if (StringUtils.isEmpty(templateId)) {
				RenderUtils.renderXML("templateId is null", response);
				return;
			}
			String sb = panelDataStyleService.getCustomerPanel(templateId);
			response.addHeader("pragma", "no-cache");
			RenderUtils.renderXML(sb, response);
			double end = System.currentTimeMillis();
                LOGGER.debug("Totle time of getCustomerPanel() is : " + (end - start));
		} catch (Exception e) {
			LOGGER.error("getCustomerPanel exception , request parameter is templateId={} " + new Object[] { templateId });
			LOGGER.error("getCustomerPanel exception : " + e);
			RenderUtils.renderXML("getCustomerPanel error", response);
		}

	}

	/**
	 * 获得面板样式接口
	 * 
	 * @param templateId
	 * @param panelIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/2.0/style_{templateId}.xml")
	public void getStyle(@PathVariable(value = "templateId") String templateId, @RequestParam(value = "panelIds", defaultValue = "") String panelIds,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("get panel style: templateId="+templateId+",panleIds="+panelIds);
		try {
			String sb = panelDataStyleService.getPanelStyle(templateId, panelIds);
			response.addHeader("pragma", "no-cache");
			RenderUtils.renderXML(sb, response);
		} catch (Exception e) {
			LOGGER.error("getStyle exception , request parameters are templateId={},panelIds={}"  + new Object[] { templateId,panelIds });
			LOGGER.error("getStyle exception : " + e);
			RenderUtils.renderXML("getStyle error", response);
		}

	}

	/**
	 * 获取面板数据接口
	 * @param templateId
	 * @param panelIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/2.0/data_{templateId}.xml")
	public void getData(@PathVariable(value = "templateId") String templateId, @RequestParam(value = "panelIds", defaultValue = "") String panelIds,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("get panel data: templateId="+templateId+",panleIds="+panelIds);
		try {
			String sb = panelDataStyleService.getPanelData(templateId, panelIds);
			response.addHeader("pragma", "no-cache");
			RenderUtils.renderXML(sb, response);
		} catch (Exception e) {
			LOGGER.error("getData exception, request parameters are templateId={},panelIds={}" + new Object[] { templateId,panelIds });
			LOGGER.error("getData exception :{} " + e);
			RenderUtils.renderXML("getData error", response);
		}
	}
}
