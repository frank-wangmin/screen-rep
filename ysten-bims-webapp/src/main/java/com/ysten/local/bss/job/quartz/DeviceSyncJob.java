package com.ysten.local.bss.job.quartz;

import com.ysten.local.bss.web.service.IDeviceWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceSyncJob {
	private static final Logger logger = LoggerFactory .getLogger(DeviceSyncJob.class);
	@Autowired
	private IDeviceWebService deviceWebService;
	public void executeSync() throws Exception{
		if(logger.isInfoEnabled()) {
			logger.info("------------------Sync Device Start---------------------");
		}
		try {
			this.deviceWebService.syncDevice();
		} catch (Exception e) {
			logger.error("Sync activate sum data to remote failed", e);
		}
		if(logger.isInfoEnabled()) {
			logger.info("------------------Sync Device End-------------------- ");
		}
	}
}
