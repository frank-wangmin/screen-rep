package com.ysten.local.bss.util.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzServletContextListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(QuartzServletContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("|---------------------contextDestroyed[s]--------------------|");
		try {
			StdSchedulerFactory.getDefaultScheduler().shutdown();
			Thread.sleep(1000);
		} catch (SchedulerException ex) {
			logger.debug(ex.toString());
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		logger.debug("|---------------------contextDestroyed[e]--------------------|");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

	}

}
