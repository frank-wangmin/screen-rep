package com.ysten.local.bss.gd.service;

import com.ysten.local.bss.util.bean.FtpBean;


public interface IGdRecordService {

    /**
     * 获取热点ftp信息
     * @return
     */
    FtpBean getHotFtpResult();
    /**
     * 获取冷点ftp信息
     * @return
     */
    FtpBean getColdFtpResult();
    /**
     * 获取xml ftp信息
     * @return
     */
    FtpBean getXmlFtpResult();
    /**
     * 通过日志和epg模板id查询节目信息
     * @param logList
     * @param templateId
     * @return
     */
//    List<ProgramSerial> getProgramSerialList(List<ProgramSerialLog> logList, String templateId);
}
