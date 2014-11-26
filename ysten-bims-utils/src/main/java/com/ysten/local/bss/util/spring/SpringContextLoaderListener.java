package com.ysten.local.bss.util.spring;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;

/**
 * 不必专门为注入WebApplicationContext写一个servlet，继承ContextLoaderListener即可
 */
public class SpringContextLoaderListener {

    /**
     * @param event ServletContextEvent
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public static void contextInitialized(ServletContextEvent event) {
        WebApplicationContext context = (WebApplicationContext) event
                .getServletContext()
                .getAttribute(
                        WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        SpringBeanProxy.setApplicationContext(context);
    }
}
