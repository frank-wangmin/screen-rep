package com.ysten.local.bss.push.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.push.service.IMailService;
import com.ysten.utils.date.DateUtil;
import com.ysten.utils.page.Pageable;

@Service
public class MailServiceImpl implements IMailService {

    @Autowired
    private SystemConfigService systemConfigService;
    
    private static final Integer limit = 1000;
    private static final String PROGRAMPREFIXFILE = "p_failed_";
    private static final String PROGRAMSERIESPREFIXFILE = "ps_failed_";
    private static final String FILEMOVEFAILED = "文件搬迁失败";
    
    @Override
    public String shMailContent(String queryDate) throws ParseException{
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        sb.append("<html>");
        sb.append(" <head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
        sb.append(" <style type=\"text/css\">");
        sb.append("     table tr {text-align:center; height: 26px;} \n");
        sb.append("     table td {text-align:center; border:#C5CDDA 1px solid; padding-left:5px; padding-right:5px}");
        sb.append(" </style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append(" </body>");
        sb.append("</html>");
        return sb+"";
    }
    
    @Override
    public String c2MailContent(String queryDate, String title, String prefix, String webHttp) throws ParseException, IOException{
        Date date = null;
        int length = webHttp.length();
        File psFile = new File(prefix.substring(0, prefix.length()-14)+PROGRAMSERIESPREFIXFILE+webHttp.substring(length-14, length)+".txt");
        File pFile = new File(prefix.substring(0, prefix.length()-14)+PROGRAMPREFIXFILE+webHttp.substring(length-14, length)+".txt");
        if(StringUtils.isNotBlank(queryDate)){
            date = DateUtil.convertStringToDate("yyyyMMddHHmmss", queryDate);
        }
        String psUrl = ""; //c2FailedProgramSeriesDataWriteToFile(date, psFile, webHttp);
        String pUrl = ""; //c2FailedProgramDataWriteToFile(date, pFile, webHttp);
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        sb.append("<html>");
        sb.append(" <head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
        sb.append(" <style type=\"text/css\">");
        sb.append("     body {font-family:Microsoft YaHei; font-size:12px;} \n");
        sb.append("     table tr {text-align:center; height: 26px;} \n");
        sb.append("     table td {text-align:center; border:#C5CDDA 1px solid; padding-left:5px; padding-right:5px; min-width:40px;} \n");
        sb.append(" </style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<div style=\"text-align:left\">Hi,All:</div>");
        sb.append("<div style=\"text-align:left\">&nbsp;&nbsp;&nbsp;&nbsp;"+title+"</div>");
        sb.append(c2StatisticsData(date));
        sb.append("<br />");
        sb.append(" <table style=\"width:850px\">");
        sb.append("     <tr><td style=\"text-align:left;font-size:12px;\">节目失败数据：<a href=\""+pUrl+"\" style=\"font-size:12px\">"+pUrl+"</a>");
        sb.append("     <br />节目集失败数据：<a href=\""+psUrl+"\" style=\"font-size:12px\">"+psUrl+"</a></td></tr>");
        sb.append(" </table>");
        sb.append("<br />");
        sb.append("-------------------------------------------------------------------------<br />");
        sb.append("<font style=\"font-size:12px;\">互联网电视业务子平台</font><br />");
        sb.append("<font style=\"font-size:12px;\">无锡运营研发中心</font>");
//        sb.append(c2FailedData(date));
        sb.append(" </body>");
        sb.append("</html>");
        return sb+"";        
    }
    
    private String c2StatisticsData(Date queryDate) throws ParseException{
        StringBuffer sb = new StringBuffer();
        sb.append("<table style=\"width:850px; heigth:60; border-collapse:collapse; font-size:12px;\">");
        sb.append(" <tr>");
        sb.append("     <td>类型</td>");
        sb.append("     <td>总数</td>");
        sb.append("     <td>成功</td>");
        sb.append("     <td>成功率</td>");
        sb.append("     <td>注入时长</td>");
        if(queryDate == null){
            sb.append("     <td>待注入</td>");
            sb.append("     <td>待注入比例</td>");
            sb.append("     <td>注入中</td>");
            sb.append("     <td>注入中比例</td>");
        }
        sb.append("     <td>失败</td>");
        sb.append("     <td>失败率</td>");
        sb.append(" </tr>");
        return sb.append("</table>")+"";
    }
    
    
//    private String c2FailedData(Date date){
//        StringBuffer sb = new StringBuffer();
//        Long maxPSId = 0L;
//        Long maxProgramId = 0L;
//        List<ProgramSeriesRecord> programSeriesList = new ArrayList<ProgramSeriesRecord>(limit);
//        List<ProgramRecord> programList = new ArrayList<ProgramRecord>(limit);
//        sb.append("<table style=\"heigth:60; border-collapse:collapse\">");
//        sb.append(" <tr>");
//        sb.append("     <td width=\"85px\">节目集编号</td>");
//        sb.append("     <td>节目集名称</td>");
//        sb.append("     <td width=\"160px\">推送时间</td>");
//        sb.append("     <td width=\"150px\">失败原因</td>");
//        sb.append(" </tr>");
//        do{
//            programSeriesList = recordRepository.findSubPSRecordByPushStatusAndDate(EPushStatus.FAILED, limit, maxPSId, date);
//            if(programSeriesList != null && programSeriesList.size() > 0){
//                maxPSId = programSeriesList.get(programSeriesList.size()-1).getProgramSeriesId();
//                for(ProgramSeriesRecord series : programSeriesList){
//                    sb.append(" <tr>");
//                    sb.append("     <td>"+series.getProgramSeriesId()+"</td>");
//                    sb.append("     <td>"+series.getProgramSeriesName()+"</td>");
//                    sb.append("     <td>"+DateUtil.convertDateToString(series.getPushDate())+"</td>");
//                    sb.append("     <td>"+c2ParsingReason(series.getReason())+"</td>");
//                    sb.append(" </tr>");                    
//                }
//            }
//        }while(programSeriesList.size() > 0);
//        if(maxPSId == 0L){
//            sb.append(" <tr><td colspan=\"4\">暂无数据！</td></tr>");
//        }
//        sb.append("</table>");
//        sb.append("<br />");
//        sb.append("<table style=\"heigth:60; border-collapse:collapse\">");
//        sb.append(" <tr>");
//        sb.append("     <td width=\"85px\">节目编号</td>");
//        sb.append("     <td>节目名称</td>");
//        sb.append("     <td width=\"160px\">推送时间</td>");
//        sb.append("     <td width=\"150px\">失败原因</td>");
//        sb.append(" </tr>");
//        do{
//            programList = recordRepository.findSubProgramRecordByPushStatus(EPushStatus.FAILED, limit, maxProgramId, date);
//            if(programList != null && programList.size() > 0){
//                maxProgramId = programList.get(programList.size()-1).getProgramId();
//                for(ProgramRecord program : programList){
//                    sb.append(" <tr>");
//                    sb.append("     <td width=\"85px\">"+program.getProgramId()+"</td>");
//                    sb.append("     <td>"+program.getProgramName()+"</td>");
//                    sb.append("     <td width=\"120px\">"+DateUtil.convertDateToString(program.getPushDate())+"</td>");
//                    sb.append("     <td width=\"150px\">"+c2ParsingReason(program.getReason())+"</td>");
//                    sb.append(" </tr>");                    
//                }
//            }
//        }while(programList.size() > 0);
//        if(maxProgramId == 0L){
//            sb.append(" <tr><td colspan=\"4\">暂无数据！</td></tr>");
//        }
//        sb.append("</table>");        
//        return sb+"";
//    }
    
    
}
