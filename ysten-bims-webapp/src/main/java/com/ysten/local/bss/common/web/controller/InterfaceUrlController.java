package com.ysten.local.bss.common.web.controller;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.customer.web.controller.CustomerController;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IInterfaceUrlWebService;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InterfaceUrlController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private IInterfaceUrlWebService interfaceUrlWebService;
    @Autowired
    private ILoggerWebService loggerWebService;

    @RequestMapping(value = "/to_interface_url_list")
    public String toSysNoticeList(ModelMap model) {
        return "/common/interface_url_list";
    }

    @RequestMapping(value = "/device_interface_name.json")
    public void getCustomerState(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        tv.add(new TextValue(String.valueOf(Constants.ZERO),Constants.PLEASE_SELECT));
        for (InterfaceName name : InterfaceName.values()) {
            tv.add(new TextValue(name.toString(), name.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "interface_url_add")
    public void addInterfaceUrl(@RequestParam(value = "interfaceName", defaultValue = "") String interfaceName,
                                @RequestParam(value = "interfaceUrl", defaultValue = "") String interfaceUrl,
                                @RequestParam(value = "areaId", defaultValue = "") Long areaId, HttpServletRequest request,
                                HttpServletResponse response) {
        try {

            if (StringUtils.isNotBlank(interfaceName) && areaId != null) {
                InterfaceName interfaceNameKey = getInterfaceName(interfaceName);
                if (interfaceName != null) {
                    InterfaceUrl existInterfaceUrl = interfaceUrlWebService.getByAreaAndName(areaId, interfaceNameKey);
                    if (existInterfaceUrl != null) {
                        LOGGER.info("区域和接口名称已经存在，新增终端接口配置信息失败");
                        RenderUtils.renderText(Constants.EXISTED, response);
                        return;
                    }
                }
            }

            InterfaceUrl ifu = new InterfaceUrl();
            Area area = new Area();
            area.setId(areaId);
            ifu.setArea(area);
            ifu.setInterfaceName(InterfaceName.valueOf(interfaceName));
            ifu.setInterfaceUrl(interfaceUrl);
            boolean bool = this.interfaceUrlWebService.saveInterfaceUrl(ifu);
            String logDescription = (bool ? "添加终端接口地址信息成功" : "添加终端接口配置信息失败") + "。消息信息："
                    + JsonUtils.toJson(interfaceUrl);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            loggerWebService.saveOperateLog(Constants.DEVICE_INTERFACE_URL_INFO_MAINTAIN, Constants.ADD, ifu.getId()
                    + "", logDescription, request);
        } catch (Exception e) {
            LOGGER.error("添加终端接口地址信息失败 ：{}", e);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "interface_url_to_update.json")
    public void toUpdateInterfaceUrl(@RequestParam("id") Long id, HttpServletResponse response) {
        if (id != null && !"".equals(id.toString())) {
            InterfaceUrl interfaceUrl = this.interfaceUrlWebService.getInterfaceUrlById(id);
            RenderUtils.renderJson(JsonUtils.toJson(interfaceUrl), response);
        }
    }

    @RequestMapping(value = "interface_url_update")
    public void updateInterfaceUrl(@RequestParam(value = "id", defaultValue = "") Long id,
                                   @RequestParam(value = "interfaceName", defaultValue = "") String interfaceName,
                                   @RequestParam(value = "interfaceUrl", defaultValue = "") String interfaceUrl,
                                   @RequestParam(value = "areaId", defaultValue = "") Long areaId, HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            if (id != null) {
                if (StringUtils.isNotBlank(interfaceName) && areaId != null) {
                    InterfaceName interfaceNameKey = getInterfaceName(interfaceName);
                    InterfaceUrl existInterfaceUrl = interfaceUrlWebService.getByAreaAndName(areaId, interfaceNameKey);
                    if (existInterfaceUrl != null && !existInterfaceUrl.getId().equals(id)) {
                        LOGGER.info("区域和接口名称已经存在，修改终端接口地址信息失败");
                        RenderUtils.renderText(Constants.EXISTED, response);
                        return;
                    }
                }

                boolean bool = this.interfaceUrlWebService.updateInterfaceUrl(id, interfaceName, interfaceUrl, areaId);
                String logDescription = ((bool == true) ? "修改终端接口地址信息成功" : "修改终端接口地址信息失败") + "。消息信息,id：" + id
                        + ",接口名称：" + interfaceName + "，接口地址：" + interfaceUrl + "，区域：" + areaId;
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                loggerWebService.saveOperateLog(Constants.DEVICE_INTERFACE_URL_INFO_MAINTAIN, Constants.MODIFY,
                        id + "", logDescription, request);
            }
        } catch (Exception e) {
            LOGGER.error("修改终端接口地址信息失败 : {}",e);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "delete_interface_url.json")
    public void deleteInterfaceUrl(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = interfaceUrlWebService.deleteInterfaceUrl(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.DEVICE_INTERFACE_URL_INFO_MAINTAIN, Constants.DELETE, ids,
                        (bool == true) ? "删除终端信息成功" : "删除终端信息失败", request);
            }
        } catch (Exception e) {
            LOGGER.error("Delete InterfaceUrl Error", e);
        }

    }

    @RequestMapping(value = "interface_url_list.json")
    public void findAllSysNoticeList(@RequestParam(value = "interfaceName", defaultValue = "") String interfaceName,
                                     @RequestParam(value = "areaId", defaultValue = "") Long areaId,
                                     @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
                                     @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
                                     HttpServletResponse response) {
        Pageable<InterfaceUrl> interfaceUrlList = this.interfaceUrlWebService.findInterfaceUrlList(interfaceName,
                areaId, pageNo, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(interfaceUrlList), response);
    }

    private InterfaceName getInterfaceName(String name) {
        for (InterfaceName interfaceName : InterfaceName.values()) {
            if (StringUtils.equals(name, interfaceName.toString())) return interfaceName;
        }
        return null;
    }
}