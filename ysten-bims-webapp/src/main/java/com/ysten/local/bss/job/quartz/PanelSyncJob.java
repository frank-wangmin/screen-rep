package com.ysten.local.bss.job.quartz;

import com.ysten.local.bss.panel.service.IPanelSyncService;
import com.ysten.local.bss.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yaoqy on 14-7-29.
 */
public class PanelSyncJob {

    private static final Logger logger = LoggerFactory.getLogger(PanelSyncJob.class);
    @Autowired
    private IPanelSyncService panelSyncService;

    public void executeSyncFromEpg() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("############### Panel Sync data from EPG begin ###########");
        }
        try {
            Long startTime = System.currentTimeMillis();
            this.panelSyncService.loadDatas();
            this.panelSyncService.syncEpgPanelDatas();
            logger.info("the interval of job :"+(System.currentTimeMillis()-startTime));
            if (logger.isInfoEnabled()) {
                logger.info("############### Panel Sync data from EPG end ########### ");
            }
        } catch (Exception e) {
            logger.error(" Panel Sync data from EPG failed and the error is " + LoggerUtils.printErrorStack(e));
        }
    }

    public void executeSyncFromCenter() throws Exception{
        if(logger.isInfoEnabled()) {
            logger.info("############### Panel Sync data from center begin ###########");
        }
        try {
            this.panelSyncService.syncCenterPanelDatas();
            if(logger.isInfoEnabled()) {
                logger.info("############### Panel Sync data from center end ########### ");
            }
        } catch (Exception e) {
            logger.error(" Panel Sync data from center failed and the error is "+ e);
        }

    }
}
