package com.ysten.local.bss.panel.api.job;

import com.ysten.cache.Cache;
import com.ysten.cache.enums.RedisFacadeCommonRegular;
import com.ysten.cache.enums.RedisPanelCommonRegular;
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

    public void clearRedis(){
        logger.info("**********************start clear panel redis job *******************************");
        for (RedisPanelCommonRegular redisPanelCommonRegular : RedisPanelCommonRegular.values()) {
            Set<String> keys = cache.findKeysByRegular(redisPanelCommonRegular.getRegular() + "*");
            if(CollectionUtils.isEmpty(keys)) continue;
            if(redisPanelCommonRegular == RedisPanelCommonRegular.ALL_PANEL_FREFIX){
                logger.info("********************** clear all panel by job *******************************");
                cache.clearAllPanel();
            }
            if(redisPanelCommonRegular == RedisPanelCommonRegular.PANEL_FREFIX){
                logger.info("********************** clear panel by job *******************************");
                cache.clearPanelInterface();
            }
            for (String key : keys) {
                cache.remove(key);
            }
        }
    }
}
