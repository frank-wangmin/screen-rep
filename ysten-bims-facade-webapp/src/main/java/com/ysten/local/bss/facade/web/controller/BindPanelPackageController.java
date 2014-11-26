package com.ysten.local.bss.facade.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ysten.local.bss.panel.service.IPanelPackageService;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.json.JsonUtils;

/**
 * 终端-面板包绑定接口,中心用。
 * 
 * @author mwl
 */
@Controller
@RequestMapping(value = "/center/")
public class BindPanelPackageController {
    private static Logger LOGGER = LoggerFactory.getLogger(BindPanelPackageController.class);
    @Autowired
    private IPanelPackageService panelPackageService;

    @RequestMapping(value = "/bindPanelPackage")
    public void BindPanelPackage(@RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
            @RequestParam(value = "packageId", defaultValue = "") String packageId,
//            @RequestParam(value = "order", defaultValue = "") String order,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result = "";
    	try {
			LOGGER.info("终端面板包绑定信息：deviceCode={},packageId={}", new Object[] { deviceCode ,packageId});
			//参数非空校验
			if(deviceCode.equals("") || deviceCode == null || packageId.equals("") || packageId == null){
				result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "panelPackage bind failed!packageId or deviceCode is null!"));
			}else{
				result = this.panelPackageService.bindPanelPackage(packageId, deviceCode);
			}
		} catch (Exception e) {
			LOGGER.error("bindPanelPackage error:{}", e);
			result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "panelPackage bind failed!"));
		}
		LOGGER.info("绑定结果信息：result={}", new Object[]{result});
		RenderUtils.renderJson(result, response);
    }
}
