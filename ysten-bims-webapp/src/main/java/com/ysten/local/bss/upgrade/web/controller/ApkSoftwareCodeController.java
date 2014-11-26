package com.ysten.local.bss.upgrade.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.ApkSoftwareCode;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IApkSoftwareCodeService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ApkSoftwareCodeController {

    private Logger LOGGER = LoggerFactory.getLogger(ApkSoftwareCodeController.class);

    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private IApkSoftwareCodeService apkSoftwareCodeService;

    @RequestMapping(value = "/to_apk_software_code_list")
    public String toSoftwareCodePage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/upgrade/apk_software_code_list";
    }
    @RequestMapping(value = "/apk_software_package_upgrade_page")
    public String toApkSoftwareCodePage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/upgrade/apk_software_code_list";
    }
 // 获取软件列表
    @RequestMapping("/find_apk_software_code_list.json")
    public void findSoftwareCodeList(
    		@RequestParam(value = Constants.PARAM_NAME, defaultValue = "") String name,
    		@RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
            HttpServletResponse response) {
        Pageable<ApkSoftwareCode> data = this.apkSoftwareCodeService.findListByNameAndCode(FilterSpaceUtils.filterStartEndSpace(code), FilterSpaceUtils.filterStartEndSpace(name), pageNo, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(data), response);
    }
    @RequestMapping(value = "apk_soft_code_add.json")
    public void addApkSoftCode(ApkSoftwareCode apkSoftwareCode, HttpServletResponse response, HttpServletRequest request) {
    	apkSoftwareCode.setCreateDate(new Date());
    	apkSoftwareCode.setLastModifyTime(new Date());
        Operator op = ControllerUtil.getLoginOperator(request);
        apkSoftwareCode.setOperUser(op.getLoginName());
        boolean bool = this.apkSoftwareCodeService.saveApkSoftwareCode(apkSoftwareCode);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String info = (bool == true) ? "新增apk软件号成功！" : "新增apk软件号失败！";
        info += "软件号名称：" + apkSoftwareCode.getName() + ";编码:" + apkSoftwareCode.getCode() + ";创建时间:"
                + apkSoftwareCode.getCreateDate() + ";状态：" + apkSoftwareCode.getStatus().getDisplayName();
        this.loggerWebService.saveOperateLog(Constants.APK_SOFT_CODE_MAINTAIN, Constants.ADD, "新增apk软件信息编号："
                + apkSoftwareCode.getCode(), info, request);
    }
    @RequestMapping(value = "apk_soft_code_update.json")
    public void updateApkSoftCode(ApkSoftwareCode apkSoftwareCode, HttpServletResponse response, HttpServletRequest request) {
        boolean bool = false;
        if (apkSoftwareCode != null) {
            Operator op = ControllerUtil.getLoginOperator(request);
            apkSoftwareCode.setOperUser(op.getLoginName());
            apkSoftwareCode.setLastModifyTime(new Date());
            bool = this.apkSoftwareCodeService.updateApkSoftwareCode(apkSoftwareCode);
        }
        String description = (bool == true) ? "更新APK软件号信息成功！" : "更新APK软件号信息失败！";
        description += "软件号名称：" + apkSoftwareCode.getName() + ";编码:" + apkSoftwareCode.getCode() + ";状态："
                + apkSoftwareCode.getStatus().getDisplayName();
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.APK_SOFT_CODE_MAINTAIN, Constants.MODIFY,
        		apkSoftwareCode.getId() + "", description, request);
    }
    @RequestMapping(value = "apk_soft_code_to_update.json")
    public void getSoftCodeInfo(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
        	ApkSoftwareCode apkSoftwareCode = this.apkSoftwareCodeService.selectByPrimaryKey(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(apkSoftwareCode), response);
        }
    }
    @RequestMapping(value = "/apk_softCode_name_exists")
    public void checkAppSoftwareCodeNameExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("name") String name, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (name != null && !"".equals(name)) {
        	ApkSoftwareCode apkSoftwareCode = this.apkSoftwareCodeService.getSoftwareCodesByCodeAndName(null, name);
            if (apkSoftwareCode != null && !apkSoftwareCode.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/apk_softCode_code_exists")
    public void checkAppSoftwareCodeExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("code") String code, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (code != null && !"".equals(code)) {
        	ApkSoftwareCode apkSoftwareCode = this.apkSoftwareCodeService.getSoftwareCodesByCodeAndName(code, null);
            if (apkSoftwareCode != null && !apkSoftwareCode.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }
    
    @RequestMapping(value = "/apk_soft_code.json")
    public void getList(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<ApkSoftwareCode> list = this.apkSoftwareCodeService.findAllOfUseble();
        tv.add(new TextValue("", "请选择"));
        for (ApkSoftwareCode apkSoftwareCode : list) {
            tv.add(new TextValue(apkSoftwareCode.getId() + "", apkSoftwareCode.getName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
}
