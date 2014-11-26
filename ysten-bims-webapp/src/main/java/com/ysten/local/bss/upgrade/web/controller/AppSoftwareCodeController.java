package com.ysten.local.bss.upgrade.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.AppSoftwareCode;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IAppSoftwareCodeService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AppSoftwareCodeController {

    // private Logger LOGGER =
    // LoggerFactory.getLogger(AppSoftwareCodeManagerController.class);

    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private IAppSoftwareCodeService appSoftwareCodeService;

    @RequestMapping(value = "/app_software_code_page")
    public String toDeviceSoftwareCodePage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/upgrade/app_software_code_list";
    }

    // 获取软件列表
    @RequestMapping("/find_app_software_code_list")
    public void findSoftwareCodeList(@RequestParam(value = Constants.PARAM_NAME, defaultValue = "") String name,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response, EnumConstantsInterface.Status status,
            ModelMap model) {

        Pageable<AppSoftwareCode> data = this.appSoftwareCodeService.findAppSoftwareCodesByCondition(status, name,
                Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(data), response);
    }

    @RequestMapping(value = "app_soft_code_to_update.json")
    public void getSoftCodeInfo(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            AppSoftwareCode appSoftwareCode = this.appSoftwareCodeService.selectByPrimaryKey(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(appSoftwareCode), response);
        }
    }

    @RequestMapping(value = "/app_soft_code_delete.json")
    public void deleteSoftCode(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.appSoftwareCodeService.deleteByIds(idsList);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.APP_SOFT_CODE_MAINTAIN, Constants.DELETE, ids,
                (bool == true) ? "删除应用软件号信息成功" : "删除应用软件号信息失败", request);
    }

    @RequestMapping(value = "app_soft_code_add.json")
    public void addSoftCode(AppSoftwareCode appSoftwareCode, HttpServletResponse response, HttpServletRequest request) {
        appSoftwareCode.setCreateDate(new Date());
        appSoftwareCode.setDistributeState(DistributeState.UNDISTRIBUTE);
        Operator op = ControllerUtil.getLoginOperator(request);
        appSoftwareCode.setOperUser(op.getLoginName());
        boolean bool = this.appSoftwareCodeService.insert(appSoftwareCode);
        String info = (bool == true) ? "新增应用软件号成功！" : "新增应用软件号失败！";
        info += "软件号名称：" + appSoftwareCode.getName() + ";编码:" + appSoftwareCode.getCode() + ";创建时间:"
                + appSoftwareCode.getCreateDate() + ";状态：" + appSoftwareCode.getStatus().getDisplayName();
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.APP_SOFT_CODE_MAINTAIN, Constants.ADD, "新增软件信息编号："
                + appSoftwareCode.getCode(), info, request);
    }

    @RequestMapping(value = "/app_softCode_name_exists")
    public void checkAppSoftwareCodeNameExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("name") String name, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (name != null && !"".equals(name)) {
            AppSoftwareCode appSoftwareCode = this.appSoftwareCodeService.findSoftwareCodesByName(name);
            if (appSoftwareCode != null && !appSoftwareCode.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/app_softCode_code_exists")
    public void checkAppSoftwareCodeExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("code") String code, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (code != null && !"".equals(code)) {
            AppSoftwareCode appSoftwareCode = this.appSoftwareCodeService.findBySoftwareCode(code);
            if (appSoftwareCode != null && !appSoftwareCode.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "app_soft_code_update.json")
    public void updateSoftCode(AppSoftwareCode appSoftwareCode, HttpServletResponse response, HttpServletRequest request) {
        boolean bool = false;
        if (appSoftwareCode != null) {
            Operator op = ControllerUtil.getLoginOperator(request);
            appSoftwareCode.setOperUser(op.getLoginName());
            appSoftwareCode.setLastModifyTime(new Date());
            appSoftwareCode.setDistributeState(DistributeState.UNDISTRIBUTE);
            bool = this.appSoftwareCodeService.update(appSoftwareCode);
        }
        String description = (bool == true) ? "更新应用软件号信息成功！" : "更新应用软件号信息失败！";
        description += "软件号名称：" + appSoftwareCode.getName() + ";编码:" + appSoftwareCode.getCode() + ";状态："
                + appSoftwareCode.getStatus().getDisplayName();
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.APP_SOFT_CODE_MAINTAIN, Constants.MODIFY,
                appSoftwareCode.getId() + "", description, request);
    }

    @RequestMapping(value = "/app_soft_code.json")
    public void getList(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<AppSoftwareCode> list = this.appSoftwareCodeService.getAll();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("", "请选择"));
        }
        for (AppSoftwareCode appSoftwareCode : list) {
            tv.add(new TextValue(appSoftwareCode.getId() + "", appSoftwareCode.getName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "app_soft_code_rend.json")
    public void rendSoftCodeInfo(@RequestParam(Constants.IDS) String ids,
            @RequestParam(value = "areaId", defaultValue = "") String areaId, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        List<AppSoftwareCode> softwareCodes = new ArrayList<AppSoftwareCode>();
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                for (int i = 0; i < idsList.size(); i++) {
                    AppSoftwareCode epgSoftwareCode = this.appSoftwareCodeService.selectByPrimaryKey(idsList.get(i));
                    softwareCodes.add(epgSoftwareCode);
                }
                bool = this.appSoftwareCodeService.rendSoftCode(softwareCodes, Long.parseLong(areaId));
            }
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_CODE_MAINTAIN, Constants.DISTRIBUTE_SOFT_CODE,
                    ids, (bool == true) ? "下发软件号成功！" : "下发软件号失败！", request);
        } catch (Exception e) {
            e.printStackTrace();
            this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_CODE_MAINTAIN, Constants.DISTRIBUTE_SOFT_CODE,
                    ids, "下发软件号异常！", request);
        }
    }

}
