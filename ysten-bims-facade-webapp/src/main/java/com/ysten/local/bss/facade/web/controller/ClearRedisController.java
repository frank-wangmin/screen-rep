package com.ysten.local.bss.facade.web.controller;

import com.ysten.cache.Cache;
import com.ysten.utils.http.RenderUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 清空Redis
 * Created by frank on 14-8-14.
 */
@Controller
@RequestMapping(value = "/clear")
public class ClearRedisController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClearRedisController.class);

    @Autowired
    private Cache<String, Serializable> cache;

    @RequestMapping(value = "/cache.json")
    public void clearCache(@RequestParam(value = "type", defaultValue = "") String type, HttpServletResponse response) {
        if (StringUtils.isBlank(type)) return;

        try {
            if (StringUtils.equals(type, "animation")) {
                cache.clearAnimation();
            }
            if (StringUtils.equals(type, "background")) {
                cache.clearBackgroundImage();
            }
            if (StringUtils.equals(type, "bootstrap")) {
                cache.clearBootsrap();
            }
            if (StringUtils.equals(type, "panel")) {
                cache.clearPanelInterface();
            }
            if (StringUtils.equals(type, "allPanel")) {
                cache.clearAllPanel();
            }
            LOGGER.info("手动清空Redis缓存，类型为：" + type);
            RenderUtils.renderJson("手动清空" + type + " Redis缓存成功", response);
        } catch (Exception e) {
            LOGGER.error("手动清空Redis缓存失败，类型为：" + type + ",错误信息 {}" + e);
            RenderUtils.renderJson("手动清空" + type + " Redis缓存失败", response);
        }
    }
}
