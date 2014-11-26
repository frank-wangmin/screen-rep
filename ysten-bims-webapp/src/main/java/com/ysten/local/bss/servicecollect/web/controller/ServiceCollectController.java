package com.ysten.local.bss.servicecollect.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.common.web.controller.ExportController;
import com.ysten.local.bss.device.domain.BackgroundImage;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.ServiceCollect;
import com.ysten.local.bss.device.domain.ServiceCollectDeviceGroupMap;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.device.service.IServiceCollectService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.ServiceCollectType;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ServiceCollectController extends ExportController {

    private static final String START = "start";
    private static final String LIMIT = "limit";

    @Autowired
    private IServiceCollectService serviceCollectService;
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private ILoggerWebService loggerWebService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCollectController.class);

    @RequestMapping(value = "/to_service_collect_main")
    public String serviceCollect(ModelMap model) {
        return "/servicecollect/service_collect_main";
    }

    @RequestMapping(value = "/to_service_collect_list")
    public String toServiceCollectList(ModelMap model) {
        return "/servicecollect/service_collect_list";
    }

    @RequestMapping(value = "/is_default_already_exists")
    public void checkDefaultExists(@RequestParam(value = "id", defaultValue = "") String id,
                                   @RequestParam("isDefault") String isDefault, HttpServletResponse response) {
        String str = Constants.USABLE;
        if (isDefault != null && !"".equals(isDefault) && "DEFAULT".equals(isDefault)) {
            List<ServiceCollect> serviceCollect = this.serviceCollectService.getServiceCollectByType(ServiceCollectType.DEFAULT);
            if (serviceCollect != null && !serviceCollect.get(0).getId().toString().equals(id)) {
                str = Constants.IS_DEFAULT;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/service_description_exists")
    public void checkBootAnimationNameExists(@RequestParam(value = "id", defaultValue = "") String id,
                                             @RequestParam("description") String description, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (description != null && !"".equals(description)) {
            ServiceCollect serviceCollect = this.serviceCollectService.getServiceCollectByDescription(description);
            if (serviceCollect != null && !serviceCollect.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/find_service_collect_list")
    public void findServiceCollectList(@RequestParam(value = "serviceType", defaultValue = "") String serviceType,
                                       @RequestParam(value = "id", defaultValue = "") String id,
                                       @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                                       @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
                                       HttpServletRequest request, HttpServletResponse response) {
        Pageable<ServiceCollect> pageable = this.serviceCollectService.findServiceCollectList(serviceType, id,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/get_service_collect_list.json")
    public void findServiceCollectList(@RequestParam(value = "areaId", defaultValue = "") Long areaId,
                                       HttpServletRequest request, HttpServletResponse response) {

        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "请选择"));
        List<ServiceCollect> list = this.serviceCollectService.findAllServiceCollectList();
        try {
            if (list != null && list.size() > 0) {
                for (ServiceCollect serviceCollect : list) {
                    tv.add(new TextValue(serviceCollect.getId().toString(), serviceCollect.getDescription()));
                }
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        } catch (Exception e) {
            LOGGER.error("Get All Service Collect Exception: " + LoggerUtils.printErrorStack(e));
        }
    }

    @RequestMapping(value = "/service_collect_type.json")
    public void getServiceCollectType(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue("", "全部"));
        for (EnumConstantsInterface.ServiceCollectType state : EnumConstantsInterface.ServiceCollectType.values()) {
            tv.add(new TextValue(state.toString(), state.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/service_collect_sub_type.json")
    public void getServiceCollectSubType(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        for (EnumConstantsInterface.ServiceCollectType state : EnumConstantsInterface.ServiceCollectType.values()) {
            tv.add(new TextValue(state.toString(), state.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    // updated by joyce on 2014-6-6
    @RequestMapping(value = "/add_service_collect.json")
    public void addServiceCollect(@RequestParam(value = "serviceType", defaultValue = "") String serviceType,
                                  @RequestParam(value = "description", defaultValue = "") String description,
                                  @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                                  @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes, HttpServletRequest request,
                                  HttpServletResponse response) {
        List<Long> deviceGroupIdList = StringUtil.splitToLong(deviceGroupIds);
        String result = serviceCollectService.saveServiceCollect(serviceType, description, deviceGroupIdList,
                deviceCodes);
        String logDescription = null;
        if (StringUtils.isBlank(result)) {
            logDescription = "添加服务集信息成功!";
        } else {
            logDescription = "添加服务集信息失败!" + result;
        }
        RenderUtils.renderText(logDescription, response);
        ServiceCollect serviceCollect = this.serviceCollectService.getServiceCollectByDescription(description);
        if(logDescription.indexOf("成功")>0){
        	logDescription += ";服务集:"+JsonUtils.toJson(serviceCollect);
        }
        this.loggerWebService.saveOperateLog(Constants.SERVICE_COLLECT_MANAGER_MAINTAIN, Constants.ADD, serviceCollect.getId() + "", logDescription, request);
    }

    // updated by joyce on 2014-6-7
    @RequestMapping(value = "/update_service_collect.json")
    public void updateServiceCollect(@RequestParam(value = "id", defaultValue = "") String id,
                                     @RequestParam(value = "serviceType", defaultValue = "") String serviceType,
                                     @RequestParam(value = "description", defaultValue = "") String description,
                                     @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
                                     @RequestParam(value = "deviceCodes", defaultValue = "") String deviceCodes, HttpServletRequest request,
                                     HttpServletResponse response) {
        List<Long> deviceGroupIdList = StringUtil.splitToLong(deviceGroupIds);
        String result = serviceCollectService.updateSerivceCollect(id, serviceType, description, deviceGroupIdList,
                deviceCodes);
        String logDescription = null;
        if (StringUtils.isBlank(result)) {
            logDescription = "修改服务集信息成功!";
        } else {
            logDescription = "修改服务集信息失败!" + result;
        }
        RenderUtils.renderText(logDescription, response);
        this.loggerWebService.saveOperateLog(Constants.SERVICE_COLLECT_MANAGER_MAINTAIN, Constants.MODIFY, id, logDescription, request);
    }

    @RequestMapping(value = "/get_service_collect_by_id.json")
    public void getServiceCollectById(@RequestParam(value = "id", defaultValue = "") String id,
                                      HttpServletRequest request, HttpServletResponse response) {
        ServiceCollect serviceCollect = serviceCollectService.getServiceCollectById(id);
        RenderUtils.renderJson(JsonUtils.toJson(serviceCollect), response);
    }

    // added by joyce to get deviceGroupIds on 2014-6-6
    @RequestMapping(value = "/get_device_group_by_id.json")
    public void getDeviceGroupsById(@RequestParam(value = "id", defaultValue = "") String id,
                                    HttpServletRequest request, HttpServletResponse response) {
        List<DeviceGroup> groupList = serviceCollectService.getDeviceGroupsById(id);
        StringBuffer buffer = new StringBuffer("");
        if (groupList != null && groupList.size() > 0) {
            for (DeviceGroup map : groupList) {
                if (StringUtils.isBlank(buffer.toString())) {
                    buffer.append(map.getId());
                } else {
                    buffer.append(",").append(map.getId());
                }
            }
        }
        RenderUtils.renderText(buffer.toString(), response);
    }

    // added by joyce to get deviceCodes on 2014-6-6
    @RequestMapping(value = "/get_device_code_by_id.json")
    public void getDeviceCodesById(@RequestParam(value = "id", defaultValue = "") String id,
                                   HttpServletRequest request, HttpServletResponse response) {
        List<ServiceCollectDeviceGroupMap> deviceCodes = serviceCollectService.getDeviceCodesById(id);
        StringBuffer buffer = new StringBuffer("");
        if (deviceCodes != null && deviceCodes.size() > 0) {
            for (ServiceCollectDeviceGroupMap map : deviceCodes) {
                if (StringUtils.isBlank(map.getYstenId())) {
                } else {
                    if (StringUtils.isBlank(buffer.toString())) {
                        buffer.append(map.getYstenId());
                    } else {
                        buffer.append(",").append(map.getYstenId());
                    }
                }
            }
        }
        RenderUtils.renderText(buffer.toString(), response);
    }

    @RequestMapping(value = "/can_bind_group_device.json")
    public void findCanBindGroupDevice(@RequestParam(value = "name", defaultValue = "") String name,
                                       @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                                       @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
                                       HttpServletRequest request, HttpServletResponse response) {
        Pageable<DeviceGroup> pageable = deviceService.finDeviceGroupNotBoundServiceCollect(name,
                Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    // added by joyce on 2014-6-5
    @RequestMapping(value = "/can_bind_boot_group_device.json")
    public void findCanBindBootGroupDevice(HttpServletRequest request, HttpServletResponse response) {
        List<DeviceGroup> groupList = deviceService.finBootDeviceGroupNotBoundServiceCollect();
        List<TextValue> tv = new ArrayList<TextValue>();
        for (DeviceGroup group : groupList) {
            tv.add(new TextValue(String.valueOf(group.getId()), group.getName()));
        }
        RenderUtils.renderJson(EnumDisplayUtil.toJson(tv), response);
    }

    @RequestMapping(value = "/add_service_collect_device_group_map.json", method = RequestMethod.POST)
    public void addServiceCollectDeviceGroupMap(
    		@RequestParam(value = "areaIds", defaultValue = "") String areaIds,
            @RequestParam(value = "serviceCollectId", defaultValue = "") String serviceCollectId,
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "deviceGroupIds", defaultValue = "") String deviceGroupIds,
            @RequestParam(value = "deviceCodes3", defaultValue = "") MultipartFile deviceCodeFile,
            @RequestParam(value = "districtCode", defaultValue = "") String districtCode,
            HttpServletRequest request,
            HttpServletResponse response) {
        String result = "";
        try {

            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String deviceCodes = FileUtils.getDeviceCodesFromUploadFile(deviceCodeFile, path);
            List<Long> deviceGroupIdList = null;
            if(StringUtils.isNotBlank(deviceGroupIds)){
            	deviceGroupIdList = StringUtil.splitToLong(deviceGroupIds);
            }
                result = this.serviceCollectService.saveServiceCollectDeviceGroupMap(Long.parseLong(serviceCollectId),areaIds,
                        deviceGroupIdList, deviceCodes, description);
            String logDescription = null;
            if (StringUtils.isEmpty(result)) {
                logDescription =  "绑定服务集信息成功!"+"\n" ;
            } else {
//            	if (result.indexOf("区域") > 0){
//            		logDescription = result;
//            	}else{
            		logDescription = "绑定服务集信息失败!"+"\n" + result;
//            	}
            }
            RenderUtils.renderText(logDescription, response);
            this.loggerWebService.saveOperateLog(Constants.SERVICE_COLLECT_MANAGER_MAINTAIN, "绑定设备和分组", serviceCollectId, logDescription, request);
        } catch (Exception e) {
        	RenderUtils.renderText("绑定服务集信息异常!"+e.getMessage(), response);
            e.printStackTrace();
            this.loggerWebService.saveOperateLog(Constants.SERVICE_COLLECT_MANAGER_MAINTAIN, "绑定设备和分组", serviceCollectId, "绑定异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/delete_service_collect_device_group_map.json")
    public void deleteServiceCollectDeviceGroupMap(
            @RequestParam(value = "serviceCollectId", defaultValue = "") String serviceCollectId,
            HttpServletRequest request, HttpServletResponse response) {
        this.serviceCollectService.deleteServiceCollectDeviceGroupMap(Long.parseLong(serviceCollectId));
        RenderUtils.renderJson(JsonUtils.toJson(Constants.SUCCESS), response);
        this.loggerWebService.saveOperateLog(Constants.SERVICE_COLLECT_MANAGER_MAINTAIN, "解绑设备和分组", serviceCollectId, "解绑成功！", request);
    }
}
