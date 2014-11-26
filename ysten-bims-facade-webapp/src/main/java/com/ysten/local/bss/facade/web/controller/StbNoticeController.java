package com.ysten.local.bss.facade.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.notice.service.ISysNoticeService;
import com.ysten.utils.Yid;
import com.ysten.utils.http.RenderUtils;

/**
 * Created by frank on 14-5-7.
 */
@Controller
@RequestMapping("/notice")
public class StbNoticeController {
    private static final String DATA_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><notices><notice><key>SYS_NOTICE</key><data><![CDATA[{\"noticeList\":";

    private static final String DATA_SUFFIX = "}]]></data></notice></notices>";
    private static final Logger logger = LoggerFactory.getLogger(StbNoticeController.class);

    @Autowired
    private ISysNoticeService sysNoticeService;


    @RequestMapping(value = "/list.xml")
    public void getNoticeXml(@RequestParam(value = "groupId", defaultValue = "") String groupId,
                             @RequestHeader(value = "YID", defaultValue = "") String yid,
                             @RequestParam(value = "deviceId", defaultValue = "") String deviceId, @RequestParam(value = "ystenId", defaultValue = "") String ystenId, HttpServletRequest request,
                             HttpServletResponse response) {
        List<SysNotice> list = new ArrayList<SysNotice>();
        String responseXml = "";
        try {
            logger.debug("终端消息信息: groupId={},yid={},deviceCode={},ystenId={}", new Object[]{groupId, yid, deviceId, ystenId});
            list = sysNoticeService.getNoticeByYstenId(ystenId);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            if (list != null && list.size() > 0) {
                responseXml = DATA_PREFIX + gson.toJson(list) + DATA_SUFFIX;
            } else {
                responseXml = DATA_PREFIX + "\"\"" + DATA_SUFFIX;
            }

            RenderUtils.renderXML(responseXml, response);
        } catch (Exception e) {
            responseXml = DATA_PREFIX + "\"\"" + DATA_SUFFIX;
            logger.error("getNoticeXml exception, request parameters are groupId={},yid={},deviceCode={},ystenId={} " + new Object[]{groupId, yid, deviceId, ystenId});
            logger.error("getNoticeXml exception : " + e);
            RenderUtils.renderXML(responseXml, response);
        }
    }

    @RequestMapping(value = "/conf.xml")
    public void conf(@RequestParam(value = "groupId", defaultValue = "") String groupId,
                     @RequestHeader(value = "YID", defaultValue = "") String yid, HttpServletResponse response)
            throws IOException {

        Yid y = Yid.instanceOf(yid);

        logger.debug("yid=" + yid + ", y=" + y);

        String responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><service><url></url><interval>600</interval></service>";

        RenderUtils.renderXML(responseXml, response);
    }
}
