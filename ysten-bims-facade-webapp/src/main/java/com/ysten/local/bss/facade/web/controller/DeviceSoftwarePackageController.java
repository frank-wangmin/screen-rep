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

import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.util.EntityUtils;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.web.service.IDeviceSoftwarePackageService;
import com.ysten.utils.data.DataUtils;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.json.JsonUtils;

/**
 * Created by frank on 14-5-6.
 */
@Controller
@RequestMapping("/province")
public class DeviceSoftwarePackageController {

    private static Logger logger = LoggerFactory.getLogger(DeviceSoftwarePackageController.class);

    @Autowired
    private IDeviceSoftwarePackageService deviceSoftwarePackageService;

    @RequestMapping(value = "/receiveSoftwarePackage")
    public void receiveSoftwarePackage(HttpServletResponse response, HttpServletRequest request) {

        String result = "";
        String requestStr = "";
        try {
            requestStr = DataUtils.asString(request.getInputStream(), Constant.UTF_ENCODE);
            if (logger.isDebugEnabled()) {
                logger.debug("synchronization deviceSoftwarePackage info:" + requestStr);
            }
            // 1.1.请求参数是否为空.
            if (StringUtils.isNotBlank(requestStr)) {
                @SuppressWarnings("unchecked")
                List<DeviceSoftwarePackage> list = JsonUtil.getList4Json(requestStr, DeviceSoftwarePackage.class, null);
                JsonResultBean jsonResultBean = null;
                // 2.非空验证.
                for (int i = 0; i < list.size(); i++) {
                    DeviceSoftwarePackage pack = list.get(i);
                    String message = EntityUtils.checkNull(pack);
                    if (message != null) {
                        jsonResultBean = new JsonResultBean(Constant.FALSE, message);
                        break;
                    }
                }
                if (jsonResultBean != null) {
                    result = JsonUtils.toJson(jsonResultBean);
                } else {
                    // 3.入库当有一条记录失败时，退回。请求方做数据回滚处理。
                    boolean sync = deviceSoftwarePackageService.batchInsertOrUpdateSynchronization(list);
                    if (sync) {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE,
                                "sync deviceSoftwarePackage success"));
                    } else {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE,
                                "sync deviceSoftwarePackage failed"));
                    }
                }
            } else {
                // 1.2请求参数为空
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE,
                        "deviceSoftwarePackage param has null!"));
            }

        } catch (Exception e) {
            logger.error("sync deviceSoftwarePackage failed", e);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync deviceSoftwarePackage failed"));
        }
        result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE, "sync deviceSoftwarePackage success"));
        RenderUtils.renderJson(result, response);
    }

    @SuppressWarnings("unused")
    private boolean checkParameterValid(Long id, Integer versionSeq, String versionName, Long softCodeId,
            String packageType, String packageLocation, String packageStatus, String mandatoryStatus, String md5,
            String createDate, Integer deviceVersionSeq, Long fullSoftwareId, String operUser,
            HttpServletResponse response) {

        String result;

        if (id == null) {
            logger.error("软件包主键id为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件包主键id为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (versionSeq == null) {
            logger.error("软件版本序号为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件版本序号为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(versionName)) {
            logger.error("软件版本名称为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件版本名称为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (softCodeId == null) {
            logger.error("软件号为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件号为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(packageType)) {
            logger.error("软件包类为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件包类为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }

        if (StringUtils.isBlank(packageLocation)) {
            logger.error("软件包绝对路径为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件包绝对路径为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(packageStatus)) {
            logger.error("软件包状态为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "软件包状态为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(mandatoryStatus)) {
            logger.error("是否强制升级为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "是否强制升级为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(md5)) {
            logger.error("md5为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "md5为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (StringUtils.isBlank(createDate)) {
            logger.error("创建时间为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "创建时间为空"));
            RenderUtils.renderJson(result, response);
            return true;
        }
        if (deviceVersionSeq == null) {
            logger.error("终端当前版本号为空");
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "终端当前版本号为空"));
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
