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

import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.local.bss.util.EntityUtils;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.web.service.IDeviceSoftwareCodeService;
import com.ysten.utils.data.DataUtils;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.json.JsonUtils;

/**
 * 省级设备软件号，软件包接收接口 Created by frank on 14-5-5.
 */
@Controller
@RequestMapping("/province")
public class DeviceSoftwareCodeController {

    private static Logger logger = LoggerFactory.getLogger(DeviceSoftwareCodeController.class);

    @Autowired
    private IDeviceSoftwareCodeService deviceSoftwareCodeService;

    @RequestMapping(value = "/receiveSoftCode")
    public void receiveSoftwareCode(HttpServletResponse response, HttpServletRequest request) {

        String result = "";
        String requestStr = "";
        try {
            requestStr = DataUtils.asString(request.getInputStream(), Constant.UTF_ENCODE);
            if (logger.isDebugEnabled()) {
                logger.debug("synchronization software code info:" + requestStr);
            }
            // 1.1.请求参数是否为空.
            if (StringUtils.isNotBlank(requestStr)) {
                @SuppressWarnings("unchecked")
                List<DeviceSoftwareCode> list = JsonUtil.getList4Json(requestStr, DeviceSoftwareCode.class, null);
                JsonResultBean jsonResultBean = null;
                // 2.非空验证.
                for (int i = 0; i < list.size(); i++) {
                    DeviceSoftwareCode device = list.get(i);
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
                    boolean sync = deviceSoftwareCodeService.batchInsertOrUpdateSynchronization(list);
                    if (sync) {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE,
                                "sync deviceSoftwareCode success"));
                    } else {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE,
                                "sync deviceSoftwareCode failed"));
                    }
                }
            } else {
                // 1.2请求参数为空
                result = JsonUtils
                        .toJson(new JsonResultBean(Constant.FALSE, "deviceSoftwareCode param has null!"));
            }
        } catch (Exception e) {
            logger.error("sync deviceSoftwareCode failed", e);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync deviceSoftwareCode failed"));
        }
        RenderUtils.renderJson(result, response);
    }

    @SuppressWarnings("unused")
    private boolean checkParameterValid(Long id, String name, String code, String createDate,
            HttpServletResponse response) {
        String result;

        if (id == null) {
            logger.error("软件号主键id为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件号主键id为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(name)) {
            logger.error("软件号名称name为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件号名称name为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(code)) {
            logger.error("软件号编码code为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件号编码code为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(createDate)) {
            logger.error("创建日期create_date为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "创建日期create_date为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        return false;
    }

}
