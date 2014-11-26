package com.ysten.local.bss.facade.jms;

import com.ysten.cache.Cache;
import com.ysten.cache.enums.ClearRedisType;
import com.ysten.cache.enums.RedisFacadeCommonRegular;
import com.ysten.cache.enums.RedisPanelCommonRegular;
import com.ysten.local.bss.util.DateUtils;
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
            if(message != null && message instanceof TextMessage){
                TextMessage tm = (TextMessage) message;
                String type = tm.getText();
                LOGGER.info("*******************facade receive message ,clear type is ******************** :" + type);
                if(StringUtils.isEmpty(type)) return;
                if(ClearRedisType.ANIMATION.toString().equals(type)){
                    cache.put(RedisFacadeCommonRegular.ANIMATION_FREFIX.getRegular() + new Date().getTime() + RandomUtils.nextLong(),type);
                }
                if(ClearRedisType.BACKGROUND.toString().equals(type)){
                    cache.put(RedisFacadeCommonRegular.BACKGROUND_FREFIX.getRegular() + new Date().getTime() + RandomUtils.nextLong(),type);
                }
                if(ClearRedisType.BOOTSTRAP.toString().equals(type)){
                    cache.put(RedisFacadeCommonRegular.BOOTSTRAP_FREFIX.getRegular() + new Date().getTime() + RandomUtils.nextLong(),type);
                }
                if (ClearRedisType.PANEL.toString().equals(type)) {
                    cache.put(RedisFacadeCommonRegular.PANEL_FREFIX.getRegular() + new Date().getTime() + RandomUtils.nextLong(), type);
                }
                if (ClearRedisType.ALLPANEL.toString().equals(type)) {
                    cache.put(RedisFacadeCommonRegular.ALL_PANEL_FREFIX.getRegular() + new Date().getTime() + RandomUtils.nextLong(), type);
                }
            }
        } catch (JMSException e) {
            LOGGER.error("clear facade redis cache error : {} ",e);
        }
    }
}
