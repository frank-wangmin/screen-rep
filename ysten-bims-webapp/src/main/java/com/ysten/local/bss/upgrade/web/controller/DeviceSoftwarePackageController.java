package com.ysten.local.bss.upgrade.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.MandatoryStatus;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.PackageStatus;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.PackageType;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IDeviceSoftwarePackageService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonResult;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;

@Controller
public class DeviceSoftwarePackageController {

    private Logger LOGGER = LoggerFactory.getLogger(DeviceSoftwarePackageController.class);

    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private IDeviceSoftwarePackageService deviceSoftwarePackageService;

    @RequestMapping(value = "/device_software_package_page")
    public String toEpgDeviceSoftwarePage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/upgrade/device_software_package_list";
    }

    @RequestMapping("/find_package_status_list.json")
    public void findPackageStatusList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<TextValue> tv = new ArrayList<TextValue>();
        EnumConstantsInterface.PackageStatus[] status = EnumConstantsInterface.PackageStatus.values();
        for (int i = 0; i < status.length; i++) {
            tv.add(new TextValue(status[i].name(), status[i].getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping("/find_package_type_list.json")
    public void findPackageTypeList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<TextValue> tv = new ArrayList<TextValue>();
        EnumConstantsInterface.PackageType[] packageType = EnumConstantsInterface.PackageType.values();
        for (int i = 0; i < packageType.length; i++) {
            tv.add(new TextValue(packageType[i].name(), packageType[i].getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping("/find_mandatory_status_list.json")
    public void findMandatoryStatusList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<TextValue> tv = new ArrayList<TextValue>();
        EnumConstantsInterface.MandatoryStatus[] mandatoryStatus = EnumConstantsInterface.MandatoryStatus.values();
        for (int i = 0; i < mandatoryStatus.length; i++) {
            tv.add(new TextValue(mandatoryStatus[i].name(), mandatoryStatus[i].getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    // 获取软件包列表
    @RequestMapping(value = "/find_device_software_package_list")
    public void findEpgDeviceSoftwareList(@RequestParam(value = Constants.PARAM_NAME, defaultValue = "") String name,
            @RequestParam(value = "softCodeId", defaultValue = "") Long softCodeId,
            @RequestParam(value = Constants.PAGE, defaultValue = "1") Integer page,
            @RequestParam(value = Constants.PARAM_ROWS, defaultValue = "15") Integer pageSize,
            HttpServletRequest request, HttpServletResponse response, EnumConstantsInterface.PackageType packageType,
            ModelMap model) {

        LOGGER.info(
                "find_device_software_package_list params: name={},softCodeId={}.packageType={},pageNo={}.pageSize={}",
                new Object[] { name, softCodeId, packageType, page, pageSize });
        Pageable<DeviceSoftwarePackage> data = this.deviceSoftwarePackageService.findEpgDeviceSoftwaresByCondition(
                softCodeId, packageType, name, page, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(data), response);
    }

    // 获取软件包列表
    @RequestMapping(value = "/get_device_software_package_list.json")
    public void getEpgDeviceSoftwareList(@RequestParam(value = "versionName", defaultValue = "") String versionName,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<DeviceSoftwarePackage> pageable = this.deviceSoftwarePackageService.getListByCondition(versionName,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    /**
     * 保存或修改软件信息
     * 
     * @param request
     * @param response
     * @param epgDeviceSoftware
     */
    @RequestMapping("/save_device_software_package_info")
    public void saveEpgDeviceSoftwareInfo(HttpServletRequest request, HttpServletResponse response,
            DeviceSoftwarePackage epgDeviceSoftware) {

        LOGGER.info("save_device_software_package_info params: {}", new Object[] { epgDeviceSoftware.toString() });
        JsonResult jsonResult = null;
        try {
            jsonResult = deviceSoftwarePackageService.insertOrUpdate(epgDeviceSoftware);
        } catch (Exception e) {
            LOGGER.error("save_device_software_package_info ：exception:{}",
                    new Object[] { ExceptionUtils.getFullStackTrace(e) });
            jsonResult = new JsonResult(false, "操作失败！");
        }

        RenderUtils.renderJson(JsonUtils.toJson(jsonResult), response);
    }

    @RequestMapping(value = "/soft_package_delete.json")
    public void deleteSoftPackage(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.deviceSoftwarePackageService.deleteByIds(idsList);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_WARE_PACKAGE_MAINTAIN, Constants.DELETE, ids,
                (bool == true) ? "删除设备软件包信息成功" : "删除设备软件包信息失败", request);
    }

    @RequestMapping(value = "/soft_package_add.json")
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
        DeviceSoftwarePackage deviceSoftwarePackage = new DeviceSoftwarePackage();
        deviceSoftwarePackage.setPackageType(PackageType.valueOf(packageType));
        deviceSoftwarePackage.setVersionSeq(Integer.parseInt(versionSeq));
        deviceSoftwarePackage.setVersionName(versionName);
        deviceSoftwarePackage.setPackageStatus(PackageStatus.valueOf(packageStatus));
        deviceSoftwarePackage.setMandatoryStatus(MandatoryStatus.valueOf(mandatoryStatus));
        if(StringUtils.isNotBlank(deviceVersionSeq)){
            deviceSoftwarePackage.setDeviceVersionSeq(Integer.parseInt(deviceVersionSeq.trim()));
        }
        deviceSoftwarePackage.setMd5(md5);
        deviceSoftwarePackage.setDistributeState(DistributeState.UNDISTRIBUTE);
        deviceSoftwarePackage.setPackageLocation(packageLocation);
        Operator op = ControllerUtil.getLoginOperator(request);
        deviceSoftwarePackage.setOperUser(op.getLoginName());
        /*
         * deleted by joyce on 2014-6-12 MessageDigest md =
         * MessageDigest.getInstance("MD5"); md.update(versionName.getBytes());
         * byte b[] = md.digest(); int i;
         * 
         * StringBuffer buf = new StringBuffer(""); for (int offset = 0; offset
         * < b.length; offset++) { i = b[offset]; if(i<0) i+= 256; if(i<16)
         * buf.append("0"); buf.append(Integer.toHexString(i)); }
         * System.out.println("result: " + buf.toString());//32位的加密
         * System.out.println("result: " +
         * buf.toString().substring(8,24));//16位的加密
         * deviceSoftwarePackage.setMd5(buf.toString());
         */
        if (!fullSoftwareId.isEmpty() && fullSoftwareId != null) {
            deviceSoftwarePackage.setFullSoftwareId(Long.parseLong(fullSoftwareId));
        }
        if (StringUtils.isNotBlank(softCodeId)) {
            DeviceSoftwareCode softCode = new DeviceSoftwareCode();
            softCode.setId(Long.parseLong(softCodeId));
            deviceSoftwarePackage.setSoftCodeId(softCode);
        }
        deviceSoftwarePackage.setCreateDate(new Date());
        boolean bool = this.deviceSoftwarePackageService.insert(deviceSoftwarePackage);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "新增软件包成功！" : "新增软件包失败！";
        description += "软件版本名称为：" + deviceSoftwarePackage.getVersionName() + ";软件号为："
                + deviceSoftwarePackage.getSoftCodeId() + ";软件包类型为：" + deviceSoftwarePackage.getPackageType()
                + ";软件包状态为：" + deviceSoftwarePackage.getPackageStatus();
        this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_WARE_PACKAGE_MAINTAIN, Constants.ADD, "版本编号："
                + versionSeq, description, request);
    }

    @RequestMapping(value = "/device_software_package_name_exists")
    public void checkAppSoftwareCodeNameExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("versionName") String versionName, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (versionName != null && !"".equals(versionName)) {
            DeviceSoftwarePackage deviceSoftwarePackage = this.deviceSoftwarePackageService
                    .getSoftwarePackageByName(versionName);
            if (deviceSoftwarePackage != null && !deviceSoftwarePackage.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/soft_package_update.json")
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
        DeviceSoftwarePackage deviceSoftwarePackage = new DeviceSoftwarePackage();
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
        deviceSoftwarePackage.setDistributeState(DistributeState.UNDISTRIBUTE);
        deviceSoftwarePackage.setPackageLocation(packageLocation);
        /*
         * deleted by joyce on 2014-6-12 MessageDigest md =
         * MessageDigest.getInstance("MD5"); md.update(versionName.getBytes());
         * byte b[] = md.digest(); int i; StringBuffer buf = new
         * StringBuffer(""); for (int offset = 0; offset < b.length; offset++) {
         * i = b[offset]; if(i<0) i+= 256; if(i<16) buf.append("0");
         * buf.append(Integer.toHexString(i)); } System.out.println("result: " +
         * buf.toString());//32位的加密 System.out.println("result: " +
         * buf.toString().substring(8,24));//16位的加密
         * deviceSoftwarePackage.setMd5(buf.toString());
         */
        if (StringUtils.isNotBlank(fullSoftwareId)) {
            deviceSoftwarePackage.setFullSoftwareId(Long.parseLong(fullSoftwareId));
        }
        Operator op = ControllerUtil.getLoginOperator(request);
        deviceSoftwarePackage.setOperUser(op.getLoginName());
        if (deviceSoftwarePackage != null) {
            if (StringUtils.isNotBlank(softCodeId)) {
                DeviceSoftwareCode softCode = new DeviceSoftwareCode();
                softCode.setId(Long.parseLong(softCodeId));
                deviceSoftwarePackage.setSoftCodeId(softCode);
            }
            deviceSoftwarePackage.setLastModifyTime(new Date());
            bool = this.deviceSoftwarePackageService.updateById(deviceSoftwarePackage);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        String description = (bool == true) ? "修改软件包成功！" : "修改软件包失败！";
        description += "软件版本名称为：" + deviceSoftwarePackage.getVersionName() + ";软件版本编号为："
                + deviceSoftwarePackage.getVersionSeq() + ";软件号为：" + deviceSoftwarePackage.getSoftCodeId() + ";软件包类型为："
                + deviceSoftwarePackage.getPackageType() + ";软件包状态为：" + deviceSoftwarePackage.getPackageStatus();
        this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_WARE_PACKAGE_MAINTAIN, Constants.MODIFY,
                deviceSoftwarePackage.getId() + "", description, request);
    }

    @RequestMapping(value = "/soft_package_to_update.json")
    public void getDeviceSoftwareInfo(@RequestParam(value = "id", defaultValue = "") String id,
            HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            DeviceSoftwarePackage epgDeviceSoftware = this.deviceSoftwarePackageService.selectByPrimaryKey(Long
                    .valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(epgDeviceSoftware), response);
        }
    }

    @RequestMapping("/delete_device_software_package_info")
    public void deleteEpgDeviceSoftwareInfos(@RequestParam(value = Constants.IDS) String ids,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        JsonResult jsonResult = null;
        try {
            jsonResult = deviceSoftwarePackageService.deleteByIds(ids);
        } catch (Exception e) {
            LOGGER.error("delete_device_software_package_info exception:{}", ExceptionUtils.getFullStackTrace(e));
            jsonResult = new JsonResult(false, "删除失败！");
        }
        RenderUtils.renderJson(JsonUtils.toJson(jsonResult), response);
    }
    @RequestMapping(value = "/check_softPackage_isDistribute")
    public void checkDefaultExists(@RequestParam(value = "id", defaultValue = "") String id,
                                   HttpServletResponse response) {
        String str = Constant.SUCCESS;
        if (id != null && !"".equals(id)) {
            DeviceSoftwarePackage deviceSoftware = this.deviceSoftwarePackageService.selectByPrimaryKey(Long.parseLong(id));
            if (deviceSoftware != null) {
                str = deviceSoftware.getDistributeState()==DistributeState.UNDISTRIBUTE ? "被引用的软件版本名称为"+deviceSoftware.getVersionName()+"的全量软件包未下发，请先下发成功后再操作":str;
            }else{
                str = "被引用的全量软件包ID为"+id+"不存在！";
            }
        }
        RenderUtils.renderText(str, response);
    }
    @RequestMapping(value = "soft_package_rend.json")
    public void rendDeviceSoftwareInfo(@RequestParam(Constants.IDS) String ids,
            @RequestParam(value = "areaId", defaultValue = "") String areaId, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        List<DeviceSoftwarePackage> softwarePackages = new ArrayList<DeviceSoftwarePackage>();
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                for (int i = 0; i < idsList.size(); i++) {
                    DeviceSoftwarePackage softwarePackage = this.deviceSoftwarePackageService
                            .selectByPrimaryKey(idsList.get(i));
                    softwarePackages.add(softwarePackage);
                }
                bool = this.deviceSoftwarePackageService.rendSoftwarePackage(softwarePackages, Long.parseLong(areaId));
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

    @RequestMapping(value = "/soft_package.json")
    public void getList(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<DeviceSoftwarePackage> list = this.deviceSoftwarePackageService.getAll();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue(" ", "请选择"));
        }
        for (DeviceSoftwarePackage deviceSoftwarePackage : list) {
            tv.add(new TextValue(deviceSoftwarePackage.getId() + "", deviceSoftwarePackage.getVersionName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/soft_package_by_softCode.json")
    public void getListBySoftCode(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
            @RequestParam(value = "softwareCodeId", defaultValue = "") String softwareCodeId,
            HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if(par != null && softwareCodeId.equals("")){
        	tv.add(new TextValue("", "请选择"));
        }
        if(!softwareCodeId.equals("")){
        	List<DeviceSoftwarePackage> list = this.deviceSoftwarePackageService.getSoftwarePackageBySoftCode(Long
                    .parseLong(softwareCodeId));
            for (DeviceSoftwarePackage deviceSoftwarePackage : list) {
                tv.add(new TextValue(deviceSoftwarePackage.getId() + "", deviceSoftwarePackage.getVersionName()));
            }
        }
        
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

}
