package com.ysten.local.bss.facade.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ysten.local.bss.device.domain.AppSoftwareCode;
import com.ysten.local.bss.util.EntityUtils;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.web.service.IAppSoftwareCodeService;
import com.ysten.utils.data.DataUtils;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.json.JsonUtils;

/**
 * Created by frank on 14-5-12.
 */
@Controller
@RequestMapping("/province")
public class AppSoftwareCodeController {

    private static Logger logger = LoggerFactory.getLogger(AppSoftwareCodeController.class);

    @Autowired
    IAppSoftwareCodeService appSoftwareCodeService;

    @RequestMapping(value = "/receiveAppSoftCode")
    public void receiveAppSoftwareCode(HttpServletResponse response, HttpServletRequest request) {
        String result = "";
        String requestStr = "";
        try {
            requestStr = DataUtils.asString(request.getInputStream(), Constant.UTF_ENCODE);
            if (logger.isDebugEnabled()) {
                logger.debug("synchronization appSoftwareCode code info:" + requestStr);
            }

            // 1.1.请求参数是否为空.
            if (StringUtils.isNotBlank(requestStr)) {
                @SuppressWarnings("unchecked")
                List<AppSoftwareCode> list = JsonUtil.getList4Json(requestStr, AppSoftwareCode.class, null);
                JsonResultBean jsonResultBean = null;
                // 2.非空验证.
                for (int i = 0; i < list.size(); i++) {
                    AppSoftwareCode code = list.get(i);
                    String message = EntityUtils.checkNull(code);
                    if (message != null) {
                        jsonResultBean = new JsonResultBean(Constant.FALSE, message);
                        break;
                    }
                }
                if (jsonResultBean != null) {
                    result = JsonUtils.toJson(jsonResultBean);
                } else {
                    // 3.入库当有一条记录失败时，退回。请求方做数据回滚处理。
                    boolean sync = appSoftwareCodeService.batchInsertOrUpdateSynchronization(list);
                    if (sync) {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE,
                                "sync AppSoftwareCode success"));
                    } else {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE,
                                "sync AppSoftwareCode failed"));
                    }
                }
            } else {
                // 1.2请求参数为空
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "AppSoftwareCode param has null!"));
            }
        } catch (Exception e) {
            logger.error("sync AppSoftwareCode failed", e);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync AppSoftwareCode failed"));
        }
        result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE, "sync AppSoftwareCode success"));
        RenderUtils.renderJson(result, response);

    }

    @SuppressWarnings("unused")
    private boolean checkParameterValid(Long id, String name, String code, Date createDate, String status,
            String operUser, HttpServletResponse response) {
        String result;
        if (id == null) {
            logger.error("主键id为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "主键id为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(name)) {
            logger.error("应用软件名称为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "应用软件名称为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }

        if (StringUtils.isBlank(code)) {
            logger.error("应用软件编码为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "应用软件编码为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(status)) {
            logger.error("应用软件状态为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "应用软件状态为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (createDate == null) {
            logger.error("创建日期create_date为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "创建日期create_date为空"));
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
