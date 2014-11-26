package com.ysten.local.bss.upgrade.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.ApkSoftwareCode;
import com.ysten.local.bss.device.domain.ApkSoftwarePackage;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.MandatoryStatus;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.PackageStatus;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.PackageType;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IApkSoftwarePackageService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
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
public class ApkSoftwarePackageController {
    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private IApkSoftwarePackageService apkSoftwarePackageService;

    @RequestMapping(value = "/to_apk_software_package_list")
    public String toEpgDeviceSoftwarePage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/upgrade/apk_software_package_list";
    }

    @RequestMapping(value = "/apk_software_package_list.json")
    public void findSoftwareList(@RequestParam(value = "versionName", defaultValue = "") String versionName,
            @RequestParam(value = "softCodeName", defaultValue = "") String softCodeName,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
            HttpServletRequest request, HttpServletResponse response, EnumConstantsInterface.PackageType packageType,
            ModelMap model) {
        Pageable<ApkSoftwarePackage> data = this.apkSoftwarePackageService.findListByNameAndCode(FilterSpaceUtils.filterStartEndSpace(versionName), FilterSpaceUtils.filterStartEndSpace(softCodeName), null, null, pageNo, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(data), response);
    }
    
 // 获取软件包列表
    @RequestMapping(value = "/find_apk_software_package_list.json")
    public void findFullSoftwareList(@RequestParam(value = Constants.PARAM_NAME, defaultValue = "") String name,
            @RequestParam(value = "softCodeId", defaultValue = "") Long softCodeId,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
            HttpServletRequest request, HttpServletResponse response, EnumConstantsInterface.PackageType packageType) {
        Pageable<ApkSoftwarePackage> data = this.apkSoftwarePackageService.findListByNameAndCode(name, null, packageType.toString(), softCodeId, pageNo, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(data), response);
    }
    @RequestMapping(value = "/apk_soft_package_add.json")
    public void addSoftPackage(@RequestParam(value = "packageType", defaultValue = "") String packageType,
            @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
            @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "versionName", defaultValue = "") String versionName,
            @RequestParam(value = "packageStatus", defaultValue = "") String packageStatus,
            @RequestParam(value = "mandatoryStatus", defaultValue = "") String mandatoryStatus,
            @RequestParam(value = "deviceVersionSeq", defaultValue = "") String deviceVersionSeq,
            @RequestParam(value = "md5", defaultValue = "") String md5,
            @RequestParam(value = "packageLocation", defaultValue = "") String packageLocation,
            @RequestParam(value = "fullSoftwareId", defaultValue = "") String fullSoftwareId,
            HttpServletResponse response, HttpServletRequest request) throws Exception {
    	ApkSoftwarePackage deviceSoftwarePackage = new ApkSoftwarePackage();
        deviceSoftwarePackage.setPackageType(PackageType.valueOf(packageType));
        deviceSoftwarePackage.setVersionSeq(Integer.parseInt(versionSeq));
        deviceSoftwarePackage.setVersionName(versionName);
        deviceSoftwarePackage.setPackageStatus(PackageStatus.valueOf(packageStatus));
        deviceSoftwarePackage.setMandatoryStatus(MandatoryStatus.valueOf(mandatoryStatus));
        if(StringUtils.isNotBlank(deviceVersionSeq)){
            deviceSoftwarePackage.setDeviceVersionSeq(Integer.parseInt(deviceVersionSeq.trim()));
        }
        deviceSoftwarePackage.setMd5(md5);
        deviceSoftwarePackage.setPackageLocation(packageLocation);
        Operator op = ControllerUtil.getLoginOperator(request);
        deviceSoftwarePackage.setOperUser(op.getLoginName());
        if (!fullSoftwareId.isEmpty() && fullSoftwareId != null) {
            deviceSoftwarePackage.setFullSoftwareId(Long.parseLong(fullSoftwareId));
        }
        if (StringUtils.isNotBlank(softCodeId)) {
        	ApkSoftwareCode softCode = new ApkSoftwareCode();
            softCode.setId(Long.parseLong(softCodeId));
            deviceSoftwarePackage.setSoftCodeId(softCode);
        }
        deviceSoftwarePackage.setCreateDate(new Date());
        boolean bool = this.apkSoftwarePackageService.insert(deviceSoftwarePackage);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "新增APK软件包成功！" : "新增APK软件包失败！";
        description += "软件版本名称为：" + deviceSoftwarePackage.getVersionName() + ";软件号为："
                + deviceSoftwarePackage.getSoftCodeId() + ";软件包类型为：" + deviceSoftwarePackage.getPackageType()
                + ";软件包状态为：" + deviceSoftwarePackage.getPackageStatus();
        this.loggerWebService.saveOperateLog(Constants.APK_SOFT_WARE_PACKAGE_MAINTAIN, Constants.ADD, "版本编号："
                + versionSeq, description, request);
    }
    
    @RequestMapping(value = "/apk_soft_package_to_update.json")
    public void getDeviceSoftwareInfo(@RequestParam(value = "id", defaultValue = "") String id,
            HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
        	ApkSoftwarePackage apkSoftwarePackage = this.apkSoftwarePackageService.selectByPrimaryKey(Long
                    .valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(apkSoftwarePackage), response);
        }
    }
    @RequestMapping(value = "/apk_soft_package_update.json")
    public void updateSoftPackage(@RequestParam(value = "packageType", defaultValue = "") String packageType,
            @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
            @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "versionName", defaultValue = "") String versionName,
            @RequestParam(value = "packageStatus", defaultValue = "") String packageStatus,
            @RequestParam(value = "mandatoryStatus", defaultValue = "") String mandatoryStatus,
            @RequestParam(value = "deviceVersionSeq", defaultValue = "") String deviceVersionSeq,
            @RequestParam(value = "md5", defaultValue = "") String md5,
            @RequestParam(value = "packageLocation", defaultValue = "") String packageLocation,
            @RequestParam(value = "fullSoftwareId", defaultValue = "") String fullSoftwareId,
            @RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response,
            HttpServletRequest request) throws Exception {
        boolean bool = false;
        ApkSoftwarePackage deviceSoftwarePackage = new ApkSoftwarePackage();
        deviceSoftwarePackage.setId(Long.parseLong(id));
        deviceSoftwarePackage.setPackageType(PackageType.valueOf(packageType));
        deviceSoftwarePackage.setVersionSeq(Integer.parseInt(versionSeq));
        deviceSoftwarePackage.setVersionName(versionName);
        deviceSoftwarePackage.setPackageStatus(PackageStatus.valueOf(packageStatus));
        deviceSoftwarePackage.setMandatoryStatus(MandatoryStatus.valueOf(mandatoryStatus));
        if(StringUtils.isNotBlank(deviceVersionSeq)){
            deviceSoftwarePackage.setDeviceVersionSeq(Integer.parseInt(deviceVersionSeq.trim()));
        }
        deviceSoftwarePackage.setMd5(md5);
        deviceSoftwarePackage.setPackageLocation(packageLocation);
        if (StringUtils.isNotBlank(fullSoftwareId)) {
            deviceSoftwarePackage.setFullSoftwareId(Long.parseLong(fullSoftwareId));
        }
        Operator op = ControllerUtil.getLoginOperator(request);
        deviceSoftwarePackage.setOperUser(op.getLoginName());
        if (deviceSoftwarePackage != null) {
            if (StringUtils.isNotBlank(softCodeId)) {
            	ApkSoftwareCode softCode = new ApkSoftwareCode();
                softCode.setId(Long.parseLong(softCodeId));
                deviceSoftwarePackage.setSoftCodeId(softCode);
            }
            deviceSoftwarePackage.setLastModifyTime(new Date());
            bool = this.apkSoftwarePackageService.updateById(deviceSoftwarePackage);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "修改APK软件包成功！" : "修改APK软件包失败！";
        description += "软件版本名称为：" + deviceSoftwarePackage.getVersionName() + ";软件版本编号为："
                + deviceSoftwarePackage.getVersionSeq() + ";软件号为：" + deviceSoftwarePackage.getSoftCodeId() + ";软件包类型为："
                + deviceSoftwarePackage.getPackageType() + ";软件包状态为：" + deviceSoftwarePackage.getPackageStatus();
        this.loggerWebService.saveOperateLog(Constants.APK_SOFT_WARE_PACKAGE_MAINTAIN, Constants.MODIFY,
                deviceSoftwarePackage.getId() + "", description, request);
    }
    
    @RequestMapping(value = "/apk_soft_package_by_softCode.json")
    public void getListBySoftCode(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
            @RequestParam(value = "softwareCodeId", defaultValue = "") String softwareCodeId,
            HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if(par != null && softwareCodeId.equals("")){
        	tv.add(new TextValue("", "请选择"));
        }
        if(!softwareCodeId.equals("")){
        	List<ApkSoftwarePackage> list = this.apkSoftwarePackageService.findSoftwarePackageBySoftCode(Long
                    .parseLong(softwareCodeId));
            for (ApkSoftwarePackage apkSoftwarePackage : list) {
                tv.add(new TextValue(apkSoftwarePackage.getId() + "", apkSoftwarePackage.getVersionName()));
            }
        }
        
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
    
    @RequestMapping(value = "/apk_software_package_name_exists")
    public void checkAppSoftwareCodeNameExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("versionName") String versionName, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (versionName != null && !"".equals(versionName)) {
            ApkSoftwarePackage apkSoftwarePackage = this.apkSoftwarePackageService.getApkSoftwarePackByName(versionName);
            if (apkSoftwarePackage != null && !apkSoftwarePackage.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }
}
