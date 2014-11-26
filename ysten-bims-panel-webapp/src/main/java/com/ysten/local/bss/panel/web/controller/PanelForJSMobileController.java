package com.ysten.local.bss.panel.web.controller;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.service.IPanelDataStyleForJsService;
import com.ysten.local.bss.panel.service.IPanelPackageService;
import com.ysten.local.bss.panel.vo.AuthenticationResponseForJsMobile;
import com.ysten.local.bss.panel.vo.PanelResponseForJsMobile;
import com.ysten.utils.http.RenderUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by frank on 14-11-3.
 */
@Controller
@RequestMapping(value = "/js/panel/")
public class PanelForJSMobileController {

    public static final String HDC_SERVER_ROOT = "hdcServerRoot";
    public static final String HDC_API_VERSION = "hdcApiVersion";
    public static final String HDC_SVC_SSO_LOGIN_USER_INFO_CMTOKENID = "/hdc/svc/sso/loginUserInfo/cmtokenid/";
    public static final String USER_INFO_NODE = "userInfo";
    @Autowired
    private IPanelDataStyleForJsService panelDataStyleForJsService;

    @Autowired
    private IPanelPackageService panelPackageService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private SystemConfigService systemConfigService;

    public static final String LOCAL_ZIP_PATH = "localZipPath";

    private static final Logger LOGGER = LoggerFactory.getLogger(PanelForJSMobileController.class);

    /**
     * 自定义接口
     *
     * @param templateId 面板包ID，设备从boot接口获取。
     * @param response
     */
    @RequestMapping(value = "/getCustomerPanel")
    public void getCustomerPanel(@RequestParam(value = "panelPackageID", defaultValue = "") String templateId,
                                 @RequestParam(value = "dpi", defaultValue = "") String dpi, HttpServletResponse response) {
        LOGGER.debug("面板自定义接口: templateId={}", new Object[]{templateId});
        try {
            if (StringUtils.isEmpty(templateId) || StringUtils.isEmpty(dpi)) {
                RenderUtils.renderXML("templateId or dpi is null", response);
                return;
            }

            String sb = panelDataStyleForJsService.getCustomerPanel(templateId, dpi);
            response.addHeader("pragma", "no-cache");
            RenderUtils.renderXML(sb, response);
        } catch (Exception e) {
            LOGGER.error("getCustomerPanel exception , request parameter is templateId={} " + new Object[]{templateId});
            LOGGER.error("getCustomerPanel exception : " + e);
            RenderUtils.renderXML("getCustomerPanel error", response);
        }

    }


    /**
     * 登录认证请求
     *
     * @param userToken
     * @param deviceCode
     * @param mac
     * @param stbID
     * @param userID
     * @return
     */
    @RequestMapping(value = "/AuthenticationURL")
    @ResponseBody
    public AuthenticationResponseForJsMobile getAuthenticationURL(@RequestParam(value = "userToken", defaultValue = "") String userToken,
                                                                  @RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
                                                                  @RequestParam(value = "mac", defaultValue = "") String mac,
                                                                  @RequestParam(value = "stbID", defaultValue = "") String stbID,
                                                                  @RequestParam(value = "userID", defaultValue = "") String userID) {

        AuthenticationResponseForJsMobile authenticationResponseForJsMobile = new AuthenticationResponseForJsMobile();
        if (StringUtils.isBlank(userToken) || StringUtils.isBlank(mac) || StringUtils.isBlank(stbID) || StringUtils.isBlank(userID)) {
            authenticationResponseForJsMobile.setResultCode("100004");
            authenticationResponseForJsMobile.setResultDesc("参数不正确");
            return authenticationResponseForJsMobile;
        }

        try {
            Device device = deviceService.getDeviceBySno(stbID);
            if (device == null) {
                authenticationResponseForJsMobile.setResultCode("100002");
                authenticationResponseForJsMobile.setResultDesc("设备不存在");
                return authenticationResponseForJsMobile;
            }
            if (!StringUtils.equals(device.getMac(), mac)) {
                authenticationResponseForJsMobile.setResultCode("100001");
                authenticationResponseForJsMobile.setResultDesc("MAC不存在");
                return authenticationResponseForJsMobile;
            }
            //向hdc发起认证流程
            String serverRoot = systemConfigService.getSystemConfigByConfigKey(HDC_SERVER_ROOT);
            String hdcApiVersion = systemConfigService.getSystemConfigByConfigKey(HDC_API_VERSION);
            String hdcUrl = serverRoot + hdcApiVersion + HDC_SVC_SSO_LOGIN_USER_INFO_CMTOKENID + userToken;
            LOGGER.info("hdc_authentication_url : " + hdcUrl);
//        String xml = HttpUtils.get(hdcUrl);
            String[] resultArr = getHDCAuthentication(hdcUrl);
            LOGGER.info("HDC认证状态statusCode : " + resultArr[0]);
            LOGGER.info("HDC认证结果：" + resultArr[1]);
            if (!StringUtils.equals(resultArr[0], "0")) {
                authenticationResponseForJsMobile.setResultCode("110001");
                authenticationResponseForJsMobile.setResultDesc("用户认证失败");
                return authenticationResponseForJsMobile;
            }
            PanelPackage panelPackage = panelPackageService.getPanelPackageForBootstrapByYstenId(stbID);
            if(panelPackage != null){
                authenticationResponseForJsMobile.setPanelPackageID(panelPackage.getId());
            }
            //TODO 根据用户或终端，查询绑定的面板包
//            String userInfo = XmlUtils.parserXMLToNode(xml, USER_INFO_NODE, "UTF-8");
        } catch (IOException e) {
            authenticationResponseForJsMobile.setResultCode("100005");
            authenticationResponseForJsMobile.setResultDesc("系统内部错误");
            return authenticationResponseForJsMobile;
        }
        authenticationResponseForJsMobile.setResultCode("000000");
        authenticationResponseForJsMobile.setResultDesc("认证成功");
//        authenticationResponseForJsMobile.setPanelPackageID(814L);
        return authenticationResponseForJsMobile;

    }

    /**
     * 发起HDC认证
     * @param url
     * @return
     * @throws IOException
     */
    private String[] getHDCAuthentication(String url) throws IOException {
        StringBuffer sb = new StringBuffer();
        URL urlClient = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) urlClient.openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
        String statusCode = httpConnection.getHeaderField("statuscode");
        int charCount = -1;
        while ((charCount = br.read()) != -1) {
            sb.append((char) charCount);
        }
        String[] arr = new String[2];
        arr[0] = statusCode;
        arr[1] = sb.toString();
        return arr;
    }

    @RequestMapping(value = "/GetPanelInfo")
    @ResponseBody
    public PanelResponseForJsMobile getPanelInfo(@RequestParam(value = "panelPackageID", defaultValue = "") Long panelPackageId,
                                                 @RequestParam(value = "dpi", defaultValue = "") String dpi,
                                                 @RequestParam(value = "launcherVersion", defaultValue = "") Long launcherVersion) {
        PanelResponseForJsMobile panelResponseForJsMobile = new PanelResponseForJsMobile();
        if (panelPackageId == null || StringUtils.isEmpty(dpi)) {
            panelResponseForJsMobile.setResultCode("112005");
            panelResponseForJsMobile.setResultDesc("参数不正确");
            return panelResponseForJsMobile;
        }
        try {
            panelResponseForJsMobile = panelDataStyleForJsService.getPanelInfo(panelPackageId, dpi, launcherVersion);
        } catch (Exception e) {
            LOGGER.error("getPanelInfo exception , request parameter is templateId={} " + new Object[]{panelPackageId});
            LOGGER.error("getCustomerPanel exception : " + e);
            e.printStackTrace();
            panelResponseForJsMobile.setResultCode("112004");
            panelResponseForJsMobile.setResultDesc("内部错误");
            return panelResponseForJsMobile;
        }
        return panelResponseForJsMobile;
    }
}
