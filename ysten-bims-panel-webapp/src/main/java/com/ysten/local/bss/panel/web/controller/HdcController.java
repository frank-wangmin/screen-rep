package com.ysten.local.bss.panel.web.controller;

import com.ysten.utils.http.RenderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * 模拟HDC认证
 * Created by frank on 14-11-13.
 */
@Controller
public class HdcController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PanelDataStyleController.class);

    @RequestMapping(value = "/{apiVersion}/hdc/svc/sso/loginUserInfo/cmtokenid/{cmtokenid}")
    public void getCustomerPanel(@PathVariable(value = "apiVersion") String apiVersion,
                                 @PathVariable(value = "cmtokenid") String cmtokenid,
                                 HttpServletResponse response) {
        try {
            LOGGER.info("apiVersion = " + apiVersion + ";" + "cmtokenid = " + cmtokenid);
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<loginUserInfoResponse xmlns:svc=\"http://js.chinamobile.com/hdc/svc/rest/xml\">\n" +
                    "<phoneNum>12345678901</phoneNum>\n" +
                    "<apppId>8888</apppId>\n" +
                    "<userInfo><![CDATA[0 邮箱|1 通行证伪码|2cookie|3 品牌|4 地市|5 登录类型|6 姓名|7 绑定\n" +
                    "手机号码|8 绑定手机类型|9 密码|10 用户等级|11 用户支付方式|12 当前积分\n" +
                    "|13 CRM 昵称|14 139 邮箱|15 飞信|16 用户状态|17 省份|18 通行证昵称|19\n" +
                    "第三方登录串号|20 第三方登录类型|21 登录渠道|22 CRM 用户标识|23 密码\n" +
                    "强度|24 当前登录名|25 区县|26 免登录初始时间|27 手机归宿标识]]></userInfo>\n" +
                    "</loginUserInfoResponse>";
            response.setHeader("statuscode","0");
            RenderUtils.renderXML(xml, response);
        } catch (Exception e) {
            e.printStackTrace();
            RenderUtils.renderXML("getCustomerPanel error", response);
        }

    }
}
