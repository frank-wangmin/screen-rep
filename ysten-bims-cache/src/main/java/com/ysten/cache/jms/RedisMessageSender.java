package com.ysten.cache.jms;

import org.springframework.jms.core.JmsTemplate;

/**
 * Created by frank on 14-8-12.
 */
public class RedisMessageSender {

    public JmsTemplate jmsTemplate;

    /**
     * send message
     */
    public void sendMessage(String message){
        jmsTemplate.convertAndSend(message);
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
