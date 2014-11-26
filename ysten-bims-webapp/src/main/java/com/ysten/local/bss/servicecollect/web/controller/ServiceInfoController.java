package com.ysten.local.bss.servicecollect.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.common.web.controller.ExportController;
import com.ysten.local.bss.device.domain.ServiceInfo;
import com.ysten.local.bss.device.service.IServiceInfoService;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.page.Pageable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ServiceInfoController extends ExportController {

    private static final String START = "start";
    private static final String LIMIT = "limit";

    @Autowired
    private IServiceInfoService serviceInfoService;
    @Autowired
    private ILoggerWebService loggerWebService;
    
    @RequestMapping(value = "/to_service_info_list")
    public String toServiceInfoList(ModelMap model) {
        return "/servicecollect/service_info_list";
    }

    @RequestMapping(value = "/find_service_info_list")
    public void findServiceInfoList(@RequestParam(value = "serviceName", defaultValue = "") String serviceName,
            @RequestParam(value = "serviceType", defaultValue = "") String serviceType,
            @RequestParam(value = "serviceCollectId", defaultValue = "") String serviceCollectId,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isBlank(serviceCollectId) && StringUtils.isNumeric(serviceCollectId)) {
            RenderUtils.renderJson("", response);
        } else {
            Pageable<ServiceInfo> pageable = this.serviceInfoService.findServiceInfoListBySimpleCondition(serviceName,
                    serviceType, Long.parseLong(serviceCollectId), Integer.valueOf(pageNo), Integer.parseInt(pageSize));
            RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
        }
    }
    @RequestMapping(value = "/service_name_exists")
    public void checkBootAnimationNameExists(@RequestParam(value = "id", defaultValue = "") String id,
                                             @RequestParam("name") String name,
                                             @RequestParam("serviceCollectId") String serviceCollectId ,HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (name != null && !"".equals(name)) {
            ServiceInfo serviceInfo = this.serviceInfoService.getServiceInfoByName(name);
            if (serviceInfo != null && !serviceInfo.getId().toString().equals(id) && serviceInfo.getServiceCollectId()==Long.valueOf(serviceCollectId)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }
    @RequestMapping(value = "/add_service_info")
    public void addServiceInfo(ServiceInfo serviceInfo, HttpServletRequest request, HttpServletResponse response) {
        this.serviceInfoService.saveServiceInfo(serviceInfo);
        RenderUtils.renderJson(JsonUtils.toJson(Constants.SUCCESS), response);
        this.loggerWebService.saveOperateLog(Constants.SERVICE_INFO_MANAGER_MAINTAIN, Constants.ADD, serviceInfo.getId()+"", "新增成功！新增服务信息："+JsonUtil.getJsonString4JavaPOJO(serviceInfo), request);
    }

    @RequestMapping(value = "/get_service_info_by_id")
    public void getServiceInfoById(@RequestParam(value = "id", defaultValue = "") String id,
            HttpServletRequest request, HttpServletResponse response) {
        ServiceInfo serviceInfo = this.serviceInfoService.getServiceInfoById(id);
        RenderUtils.renderJson(JsonUtils.toJson(serviceInfo), response);
    }

    @RequestMapping(value = "/update_service_info")
    public void updateServiceInfo(ServiceInfo serviceInfo, HttpServletRequest request, HttpServletResponse response) {
        this.serviceInfoService.updateServiceInfo(serviceInfo);
        RenderUtils.renderJson(JsonUtils.toJson(Constants.SUCCESS), response);
        this.loggerWebService.saveOperateLog(Constants.SERVICE_INFO_MANAGER_MAINTAIN, Constants.MODIFY, serviceInfo.getId()+"", "修改成功！服务信息："+JsonUtil.getJsonString4JavaPOJO(serviceInfo), request);
        }

}
