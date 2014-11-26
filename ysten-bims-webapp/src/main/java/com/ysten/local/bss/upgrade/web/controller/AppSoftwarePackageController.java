package com.ysten.local.bss.upgrade.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.AppSoftwareCode;
import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IAppSoftwarePackageService;
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
public class AppSoftwarePackageController {

    // private Logger LOGGER =
    // LoggerFactory.getLogger(AppSoftwarePackageController.class);

    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private IAppSoftwarePackageService appSoftwarePackageService;

    @RequestMapping(value = "/app_software_package_page")
    public String toEpgDeviceSoftwarePage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/upgrade/app_software_package_list";
    }

    // 获取软件包列表
    @RequestMapping(value = "/get_app_software_package_list.json")
    public void getAppSoftwareList(@RequestParam(value = "versionName", defaultValue = "") String versionName,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<AppSoftwarePackage> pageable = this.appSoftwarePackageService.getListByCondition(versionName,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/find_app_software_package_list.json")
    public void findSoftwareList(@RequestParam(value = Constants.PARAM_NAME, defaultValue = "") String name,
            @RequestParam(value = "softCodeId", defaultValue = "") Long softCodeId,
            @RequestParam(value = Constants.PAGE, defaultValue = "1") Integer page,
            @RequestParam(value = Constants.PARAM_ROWS, defaultValue = "15") Integer pageSize,
            HttpServletRequest request, HttpServletResponse response, EnumConstantsInterface.PackageType packageType,
            ModelMap model) {
        // LOGGER.info("find_app_software_package_list params: name={},softCodeId={}.packageType={},pageNo={}.pageSize={}",
        // new Object[]{name,softCodeId,packageType,page,pageSize});
        Pageable<AppSoftwarePackage> data = this.appSoftwarePackageService.findAppSoftwaresByCondition(softCodeId,
                packageType, name, page, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(data), response);
    }

    @RequestMapping(value = "/app_soft_package_delete.json")
    public void deleteSoftPackage(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.appSoftwarePackageService.deleteByIds(idsList);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.APP_SOFT_WARE_PACKAGE_MAINTAIN, Constants.DELETE, ids,
                (bool == true) ? "删除应用软件包信息成功" : "删除应用软件包信息失败", request);
    }

    @RequestMapping(value = "/app_soft_package_add.json")
    public void addSoftPackage(@RequestParam(value = "packageType", defaultValue = "") String packageType,
            @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
            @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "versionName", defaultValue = "") String versionName,
            @RequestParam(value = "packageStatus", defaultValue = "") String packageStatus,
            @RequestParam(value = "mandatoryStatus", defaultValue = "") String mandatoryStatus,
            @RequestParam(value = "appVersionSeq", defaultValue = "") String appVersionSeq,
            @RequestParam(value = "sdkVersion", defaultValue = "") String sdkVersion,
            @RequestParam(value = "md5", defaultValue = "") String md5,
            @RequestParam(value = "packageLocation", defaultValue = "") String packageLocation,
            @RequestParam(value = "fullSoftwareId", defaultValue = "") String fullSoftwareId,
            HttpServletResponse response, HttpServletRequest request) throws Exception {
        AppSoftwarePackage appSoftwarePackage = new AppSoftwarePackage();
        appSoftwarePackage.setPackageType(EnumConstantsInterface.PackageType.valueOf(packageType));
        appSoftwarePackage.setVersionSeq(Integer.parseInt(versionSeq));
        appSoftwarePackage.setVersionName(versionName);
        appSoftwarePackage.setPackageStatus(EnumConstantsInterface.PackageStatus.valueOf(packageStatus));
        appSoftwarePackage.setMandatoryStatus(EnumConstantsInterface.MandatoryStatus.valueOf(mandatoryStatus));
        appSoftwarePackage.setAppVersionSeq(Integer.parseInt(appVersionSeq));
        appSoftwarePackage.setSdkVersion(Integer.parseInt(sdkVersion));
        appSoftwarePackage.setMd5(md5);
        appSoftwarePackage.setDistributeState(DistributeState.UNDISTRIBUTE);
        appSoftwarePackage.setPackageLocation(packageLocation);
        Operator op = ControllerUtil.getLoginOperator(request);
        appSoftwarePackage.setOperUser(op.getLoginName());
        /*
         * deleted by joyce on 2014-6-12 MessageDigest md =
         * MessageDigest.getInstance("MD5"); md.update(versionName.getBytes());
         * byte b[] = md.digest(); int i; StringBuffer buf = new
         * StringBuffer(""); for (int offset = 0; offset < b.length; offset++) {
         * i = b[offset]; if(i<0) i+= 256; if(i<16) buf.append("0");
         * buf.append(Integer.toHexString(i)); } System.out.println("result: " +
         * buf.toString());//32位的加密 System.out.println("result: " +
         * buf.toString().substring(8,24));//16位的加密
         * appSoftwarePackage.setMd5(buf.toString());
         */
        if (!fullSoftwareId.isEmpty() && fullSoftwareId != null) {
            appSoftwarePackage.setFullSoftwareId(Long.parseLong(fullSoftwareId));
        }
        if (StringUtils.isNotBlank(softCodeId)) {
            AppSoftwareCode softCode = new AppSoftwareCode();
            softCode.setId(Long.parseLong(softCodeId));
            appSoftwarePackage.setSoftCodeId(softCode);
        }
        appSoftwarePackage.setCreateDate(new Date());
        boolean bool = this.appSoftwarePackageService.insert(appSoftwarePackage);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "新增应用软件包成功！" : "新增应用软件包失败！";
        description += "软件版本名称为：" + appSoftwarePackage.getVersionName() + ";软件号为：" + appSoftwarePackage.getSoftCodeId()
                + ";软件包类型为：" + appSoftwarePackage.getPackageType() + ";软件包状态为："
                + appSoftwarePackage.getPackageStatus().getDisplayName();
        this.loggerWebService.saveOperateLog(Constants.APP_SOFT_WARE_PACKAGE_MAINTAIN, Constants.ADD, "版本编号："
                + versionSeq, description, request);
    }

    @RequestMapping(value = "/app_software_package_name_exists")
    public void checkAppSoftwareCodeNameExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("versionName") String versionName, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (versionName != null && !"".equals(versionName)) {
            AppSoftwarePackage appSoftwarePackage = this.appSoftwarePackageService
                    .getAppSoftwarePackageByName(versionName);
            if (appSoftwarePackage != null && !appSoftwarePackage.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/app_soft_package_update.json")
    public void updateSoftPackage(@RequestParam(value = "packageType", defaultValue = "") String packageType,
            @RequestParam(value = "softCodeId", defaultValue = "") String softCodeId,
            @RequestParam(value = "versionSeq", defaultValue = "") String versionSeq,
            @RequestParam(value = "versionName", defaultValue = "") String versionName,
            @RequestParam(value = "packageStatus", defaultValue = "") String packageStatus,
            @RequestParam(value = "mandatoryStatus", defaultValue = "") String mandatoryStatus,
            @RequestParam(value = "appVersionSeq", defaultValue = "") String appVersionSeq,
            @RequestParam(value = "sdkVersion", defaultValue = "") String sdkVersion,
            @RequestParam(value = "md5", defaultValue = "") String md5,
            @RequestParam(value = "packageLocation", defaultValue = "") String packageLocation,
            @RequestParam(value = "fullSoftwareId", defaultValue = "") String fullSoftwareId,
            @RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response,
            HttpServletRequest request) throws Exception {
        boolean bool = false;
        AppSoftwarePackage appSoftwarePackage = new AppSoftwarePackage();
        appSoftwarePackage.setId(Long.parseLong(id));
        appSoftwarePackage.setPackageType(EnumConstantsInterface.PackageType.valueOf(packageType));
        appSoftwarePackage.setVersionSeq(Integer.parseInt(versionSeq));
        appSoftwarePackage.setVersionName(versionName);
        appSoftwarePackage.setPackageStatus(EnumConstantsInterface.PackageStatus.valueOf(packageStatus));
        appSoftwarePackage.setMandatoryStatus(EnumConstantsInterface.MandatoryStatus.valueOf(mandatoryStatus));
        appSoftwarePackage.setAppVersionSeq(Integer.parseInt(appVersionSeq));
        appSoftwarePackage.setSdkVersion(Integer.parseInt(sdkVersion));
        appSoftwarePackage.setMd5(md5);
        appSoftwarePackage.setDistributeState(DistributeState.UNDISTRIBUTE);
        appSoftwarePackage.setPackageLocation(packageLocation);
        /*
         * deleted by joyce on 2014-6-12 MessageDigest md =
         * MessageDigest.getInstance("MD5"); md.update(versionName.getBytes());
         * byte b[] = md.digest(); int i; StringBuffer buf = new
         * StringBuffer(""); for (int offset = 0; offset < b.length; offset++) {
         * i = b[offset]; if(i<0) i+= 256; if(i<16) buf.append("0");
         * buf.append(Integer.toHexString(i)); } System.out.println("result: " +
         * buf.toString());//32位的加密 System.out.println("result: " +
         * buf.toString().substring(8,24));//16位的加密
         * appSoftwarePackage.setMd5(buf.toString());
         */
        if (!fullSoftwareId.isEmpty() && fullSoftwareId != null) {
            appSoftwarePackage.setFullSoftwareId(Long.parseLong(fullSoftwareId));
        }
        Operator op = ControllerUtil.getLoginOperator(request);
        appSoftwarePackage.setOperUser(op.getLoginName());
        if (appSoftwarePackage != null) {
            if (StringUtils.isNotBlank(softCodeId)) {
                AppSoftwareCode softCode = new AppSoftwareCode();
                softCode.setId(Long.parseLong(softCodeId));
                appSoftwarePackage.setSoftCodeId(softCode);
            }
            appSoftwarePackage.setLastModifyTime(new Date());
            bool = this.appSoftwarePackageService.updateById(appSoftwarePackage);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "修改应用软件包成功！" : "修改应用软件包失败！";
        description += "软件版本名称为：" + appSoftwarePackage.getVersionName() + ";软件版本编号为："
                + appSoftwarePackage.getVersionSeq() + ";软件号为：" + appSoftwarePackage.getSoftCodeId() + ";软件包类型为："
                + appSoftwarePackage.getPackageType().getDisplayName() + ";软件包状态为："
                + appSoftwarePackage.getPackageStatus().getDisplayName();
        this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_WARE_PACKAGE_MAINTAIN, Constants.MODIFY,
                appSoftwarePackage.getId() + "", description, request);
    }

    @RequestMapping(value = "/app_soft_package_to_update.json")
    public void getSoftwareInfo(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            AppSoftwarePackage appSoftware = this.appSoftwarePackageService.getById(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(appSoftware), response);
        }
    }

    @RequestMapping(value = "/app_soft_package.json")
    public void getList(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<AppSoftwarePackage> list = this.appSoftwarePackageService.getAll();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue(" ", "所有"));
        }
        for (AppSoftwarePackage appSoftwarePackage : list) {
            tv.add(new TextValue(appSoftwarePackage.getId() + "", appSoftwarePackage.getVersionName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/app_soft_package_by_softCode.json")
    public void getListBySoftCode(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
            @RequestParam(value = "softwareCodeId", defaultValue = "") String softwareCodeId,
            HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if(par != null && softwareCodeId.equals("")){
        	tv.add(new TextValue("", "请选择"));
        }
        if(!softwareCodeId.equals("")){
	        List<AppSoftwarePackage> list = this.appSoftwarePackageService.getSoftwarePackageBySoftCode(Long
	                .parseLong(softwareCodeId));
	        for (AppSoftwarePackage softwarePackage : list) {
	            tv.add(new TextValue(softwarePackage.getId() + "", softwarePackage.getVersionName()));
	        }
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
    @RequestMapping(value = "/check_appsoftPackage_isDistribute")
    public void checkDefaultExists(@RequestParam(value = "id", defaultValue = "") String id,
                                   HttpServletResponse response) {
        String str = null;
        if (id != null && !"".equals(id)) {
            AppSoftwarePackage appSoftware = this.appSoftwarePackageService.getById(Long.parseLong(id));
            if (appSoftware != null) {
                str = appSoftware.getDistributeState()==DistributeState.UNDISTRIBUTE ? "被引用的软件版本名称为"+appSoftware.getVersionName()+"的全量应用软件包未下发，请先下发成功后再操作":str;
            }else{
                str = "被引用的全量应用软件包ID为"+id+"不存在！";
            }
        }
        RenderUtils.renderText(str, response);
    }
    @RequestMapping(value = "app_soft_package_rend.json")
    public void rendSoftPackageInfo(@RequestParam(Constants.IDS) String ids,
            @RequestParam(value = "areaId", defaultValue = "") String areaId, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        List<AppSoftwarePackage> softwarePackages = new ArrayList<AppSoftwarePackage>();
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                for (int i = 0; i < idsList.size(); i++) {
                    AppSoftwarePackage softwarePackage = this.appSoftwarePackageService.getById(idsList.get(i));
                    softwarePackages.add(softwarePackage);
                }
                bool = this.appSoftwarePackageService.rendSoftwarePackage(softwarePackages, Long.parseLong(areaId));
                String description = (bool == true) ? "下发成功" : "下发失败";
                this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_WARE_PACKAGE_MAINTAIN,
                        Constants.DISTRIBUTE_SOFT_WARE_PACKAGE, ids, description, request);
            }
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        } catch (Exception e) {
            e.printStackTrace();
            this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_WARE_PACKAGE_MAINTAIN,
                    Constants.DISTRIBUTE_SOFT_WARE_PACKAGE, ids, "下发软件包异常！", request);
        }
    }
}
