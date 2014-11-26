package com.ysten.local.bss.facade.job;

import com.ysten.cache.Cache;
import com.ysten.cache.enums.RedisFacadeCommonRegular;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by frank on 14-8-14.
 */
public class ScanClearCacheTaskJob {

    private static final Logger logger = LoggerFactory.getLogger(ScanClearCacheTaskJob.class);

    private Cache<String, Serializable> cache;

    public void setCache(Cache<String, Serializable> cache) {
        this.cache = cache;
    }

    public void clearRedis() {
        logger.info("********************** start clear facade redis job *******************************");
        for (RedisFacadeCommonRegular redisFacadeCommonRegular : RedisFacadeCommonRegular.values()) {
            Set<String> keys = cache.findKeysByRegular(redisFacadeCommonRegular.getRegular() + "*");
            if (CollectionUtils.isEmpty(keys)) continue;
            if (redisFacadeCommonRegular == RedisFacadeCommonRegular.ANIMATION_FREFIX) {
                logger.info("********************** clear animation by job *******************************");
                cache.clearAnimation();
            }
            if (redisFacadeCommonRegular == RedisFacadeCommonRegular.BACKGROUND_FREFIX) {
                logger.info("********************** clear background image by job *******************************");
                cache.clearBackgroundImage();
            }
            if (redisFacadeCommonRegular == RedisFacadeCommonRegular.BOOTSTRAP_FREFIX) {
                logger.info("********************** clear bootsrap by job *******************************");
                cache.clearBootsrap();
            }
            if (redisFacadeCommonRegular == RedisFacadeCommonRegular.ALL_PANEL_FREFIX) {
                logger.info("********************** clear all panel by job *******************************");
                cache.clearAllPanel();
            }
            if (redisFacadeCommonRegular == RedisFacadeCommonRegular.PANEL_FREFIX) {
                logger.info("********************** clear panel by job *******************************");
                cache.clearPanelInterface();
            }
            for (String key : keys) {
                cache.remove(key);
            }
        }
    }
}
