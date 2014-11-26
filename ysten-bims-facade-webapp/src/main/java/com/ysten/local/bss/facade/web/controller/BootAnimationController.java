package com.ysten.local.bss.facade.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ysten.core.AppErrorCodeException;
import com.ysten.local.bss.device.domain.BootAnimation;
import com.ysten.local.bss.device.service.IBootAnimationService;
import com.ysten.utils.http.RenderUtils;

@Controller
@RequestMapping("/stb")
public class BootAnimationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BootAnimationController.class);
	@Autowired
	private IBootAnimationService bootAnimationService;

	/**
	 * 开机动画接口
	 * 
	 * @param deviceCode
	 * @param request
	 * @param response
	 * @throws AppErrorCodeException
	 */
	@RequestMapping("/animation")
	public void getBootAnimation(@RequestParam(value = "deviceId") String deviceCode, @RequestParam(value = "ystenId") String ystenId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			LOGGER.debug("开机动画信息: deviceCode={},ystenId={}", new Object[] { deviceCode ,ystenId});
			String bootAnimationXml = this.bootAnimationService.getBootAnimationByYstenId(ystenId);
			RenderUtils.renderXML(bootAnimationXml, response);
		} catch (Exception e) {
			LOGGER.error("getBootAnimation error, request parameters are deviceCode={},ystenId={}", new Object[] { deviceCode ,ystenId});
			LOGGER.error("getBootAnimation error:{}", e);
			BootAnimation bootAnimation=new BootAnimation();
			RenderUtils.renderXML(BootAnimation.toXML(bootAnimation), response);
		} 
	}

}
