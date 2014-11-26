package com.ysten.local.bss.gd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.gd.service.IGdRecordService;
import com.ysten.local.bss.util.bean.FtpBean;

@Service
public class GdRecordServiceImpl implements IGdRecordService {

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public FtpBean getHotFtpResult() {
        String server = systemConfigService.getSystemConfigByConfigKey("hotServer");
        return new FtpBean(server);
    }

    @Override
    public FtpBean getColdFtpResult() {
        String server = systemConfigService.getSystemConfigByConfigKey("coldServer");
        return new FtpBean(server);
    }
    
    @Override
    public FtpBean getXmlFtpResult() {
        String server = systemConfigService.getSystemConfigByConfigKey("xmlServer");
        return new FtpBean(server);
    }
    
//    @Override
//    public List<ProgramSerial> getProgramSerialList(List<ProgramSerialLog> logList, String templateId) {
//        List<ProgramSerial> psList = new ArrayList<ProgramSerial>();
//        if (logList != null && logList.size() > 0) {
//            for (ProgramSerialLog psl : logList) {
//                ProgramSerial ps = this.catgItemProgramSerialService.getProgramSerialById(psl.getProgramSerial().getProgramSeriesId());
//                if (ps != null && !EProgramContentType.micVideo.equals(ps.getProgramContentType()) && "CMS".equals(ps.getCpName())) {
//                    ps.setProgramSeriesLogId(psl.getId());
//                    ps.setProgramSerialLogStatus(psl.getState());
//                    psList.add(ps);
//                }
//                this.systemConfigService.updateSystemConfig("maxProgramSerialLogId", psl.getId()+"");
//            }
//        }
//        return psList;
//    }
}