package com.ysten.local.bss.facade.web.controller;

import com.ysten.local.bss.device.domain.BackgroundImage;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.service.IBackgroundImageService;
import com.ysten.utils.http.RenderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/stb")
public class BackgroundImageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundImageController.class);

	@Autowired
	private IBackgroundImageService backgroundImageService;

	/**
	 * 背景轮换接口
	 * 
	 * @param deviceId
	 * @param request
	 * @param response
	 * @throws DeviceNotFoundException
	 */
	@RequestMapping("/backgroundImage")
	public void getBackgroundImage(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "ystenId") String ystenId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			LOGGER.debug("背景轮换信息: ystenId={},deviceId={}", new Object[] { ystenId, deviceId });
			String backgroundImageXml = this.backgroundImageService.getBackgroundImageByYstenId(ystenId);
			RenderUtils.renderXML(backgroundImageXml, response);
		} catch (Exception e) {
			LOGGER.error("getBackgroundImage exception, request parameters are ystenId={},deviceId={} " + new Object[] { ystenId, deviceId });
			LOGGER.error("getBackgroundImage exception : {}" + e);
			List<BackgroundImage> list=new ArrayList<BackgroundImage>();
			RenderUtils.renderXML(BackgroundImage.toXML(list), response);
		}
	}

}
