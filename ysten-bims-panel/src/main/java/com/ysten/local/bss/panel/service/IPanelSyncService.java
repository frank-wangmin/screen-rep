package com.ysten.local.bss.panel.service;

/**
 * @author cwang
 * @version 2014-5-30 上午11:34:51 同步EPG数据的服务层
 */
public interface IPanelSyncService {
	
    //0.add all the sysMethods to the method
//    void syncEpgPanelDatas(Long oprUserId)throws Exception;
    void syncEpgPanelDatas()throws Exception;

    void loadDatas();

    void syncCenterPanelDatas()throws Exception;

}
