package com.ysten.local.bss.job.quartz;

import com.ysten.local.bss.web.service.ICustomerWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerSyncJob {
	private static final Logger logger = LoggerFactory .getLogger(CustomerSyncJob.class);
	@Autowired
	private ICustomerWebService customerWebService;
	public void executeSync() throws Exception{
		if(logger.isInfoEnabled()) {
			logger.info("------------------Sync Customer Start---------------------");
		}
		try {
			this.customerWebService.syncCustomer();
		} catch (Exception e) {
			logger.error("Sync activate sum data to remote failed", e);
		}
		if(logger.isInfoEnabled()) {
			logger.info("------------------Sync Customer End-------------------- ");
		}
	}
}
