package com.ysten.local.bss.panel.api.jms;

import com.ysten.cache.Cache;
import com.ysten.cache.enums.ClearRedisType;
import com.ysten.cache.enums.RedisPanelCommonRegular;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by frank on 14-8-13.
 */
public class JmsRedisCacheListener implements MessageListener {

    private Logger LOGGER = LoggerFactory.getLogger(JmsRedisCacheListener.class);

    private Cache<String, Serializable> cache;

    public void setCache(Cache<String, Serializable> cache) {
        this.cache = cache;
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message != null && message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                String type = tm.getText();
                LOGGER.info("*******************panel api receive message ,clear type is ******************** :" + type);
                if (StringUtils.isEmpty(type)) return;
                if (ClearRedisType.PANEL.toString().equals(type)) {
                    cache.put(RedisPanelCommonRegular.PANEL_FREFIX.getRegular() + new Date().getTime() + RandomUtils.nextLong(), type);
                }
                if (ClearRedisType.ALLPANEL.toString().equals(type)) {
                    cache.put(RedisPanelCommonRegular.ALL_PANEL_FREFIX.getRegular() + new Date().getTime() + RandomUtils.nextLong(), type);
                }
            }
        } catch (JMSException e) {
            LOGGER.error("clear panel api redis cache error : {} ", e);
        }
    }
}
