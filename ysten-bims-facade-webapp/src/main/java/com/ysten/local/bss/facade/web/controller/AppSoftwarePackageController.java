package com.ysten.local.bss.facade.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.local.bss.util.EntityUtils;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.web.service.IAppSoftwareCodeService;
import com.ysten.local.bss.web.service.IAppSoftwarePackageService;
import com.ysten.utils.data.DataUtils;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.json.JsonUtils;

/**
 * Created by frank on 14-5-12.
 */
@Controller
@RequestMapping("/province")
public class AppSoftwarePackageController {

    private static Logger logger = LoggerFactory.getLogger(AppSoftwarePackageController.class);

    @Autowired
    IAppSoftwarePackageService appSoftwarePackageService;

    @Autowired
    IAppSoftwareCodeService appSoftwareCodeService;

    @RequestMapping(value = "/receiveAppSoftPackage")
    public void receiveAppSoftwareCode(HttpServletResponse response, HttpServletRequest request) {
        String result = "";
        String requestStr = "";
        try {
            requestStr = DataUtils.asString(request.getInputStream(), Constant.UTF_ENCODE);
            if (logger.isDebugEnabled()) {
                logger.debug("synchronization AppSoftwarePackage code info:" + requestStr);
            }
            // 1.1.请求参数是否为空.
            if (StringUtils.isNotBlank(requestStr)) {
                @SuppressWarnings("unchecked")
                List<AppSoftwarePackage> list = JsonUtil.getList4Json(requestStr, AppSoftwarePackage.class, null);
                JsonResultBean jsonResultBean = null;
                // 2.非空验证.
                for (int i = 0; i < list.size(); i++) {
                    AppSoftwarePackage device = list.get(i);
                    String message = EntityUtils.checkNull(device);
                    if (message != null) {
                        jsonResultBean = new JsonResultBean(Constant.FALSE, message);
                        break;
                    }
                }
                if (jsonResultBean != null) {
                    result = JsonUtils.toJson(jsonResultBean);
                } else {
                    // 3.入库当有一条记录失败时，退回。请求方做数据回滚处理。
                    boolean sync = appSoftwarePackageService.batchInsertOrUpdateSynchronization(list);
                    if (sync) {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE,
                                "sync AppSoftwarePackage success"));
                    } else {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE,
                                "sync AppSoftwarePackage failed"));
                    }
                }
            } else {
                // 1.2请求参数为空
                result = JsonUtils
                        .toJson(new JsonResultBean(Constant.FALSE, "AppSoftwarePackage param has null!"));
            }
        } catch (Exception e) {
            logger.error("sync AppSoftwarePackage failed", e);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync AppSoftwarePackage failed"));
        }
        result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE, "sync AppSoftwarePackage success"));
        RenderUtils.renderJson(result, response);
    }

    @SuppressWarnings("unused")
    private boolean checkParameterValid(Long id, Long softCodeId, String versionName, Integer versionSeq,
            Integer appVersionSeq, Integer sdkVersion, String packageType, String packageLocation, String md5,
            String mandatoryStatus, String packageStatus, String createDate, Long fullSoftwareId, String operUser,
            HttpServletResponse response) {
        String result;
        if (id == null) {
            logger.error("主键id为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "主键id为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (softCodeId == null) {
            logger.error("App软件号id为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "App软件号id为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(versionName)) {
            logger.error("版本名称为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "版本名称为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (versionSeq == null) {
            logger.error("版本号为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "版本号为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (appVersionSeq == null) {
            logger.error("当前版本号为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "当前版本号为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (sdkVersion == null) {
            logger.error("SDK版本号为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "SDK版本号为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(packageType)) {
            logger.error("升级包类型为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "升级包类型为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }

        if (StringUtils.isBlank(packageLocation)) {
            logger.error("升级地址为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "升级地址为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(md5)) {
            logger.error("升级包md5值为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "升级包md5值为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(mandatoryStatus)) {
            logger.error("是否强制升级为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "是否强制升级为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(packageStatus)) {
            logger.error("升级包状态为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "升级包状态为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(createDate)) {
            logger.error("创建时间为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "创建时间为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (fullSoftwareId == null) {
            logger.error("全量包软件ID为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "全量包软件ID为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(operUser)) {
            logger.error("操作人为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "操作人为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        return false;
    }
}
