package com.ysten.local.bss.upgrade.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.local.bss.device.domain.UpgradeTask;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IDeviceSoftwareCodeService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.local.bss.web.service.IUpgradeTaskService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonResult;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import java.util.Date;
import java.util.List;

@Controller
public class DeviceSoftwareCodeController {

    private Logger LOGGER = LoggerFactory.getLogger(DeviceSoftwareCodeController.class);

    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private IDeviceSoftwareCodeService deviceSoftwareCodeService;
    @Autowired
    private IUpgradeTaskService upgradeTaskService;

    @RequestMapping(value = "/device_software_code_page")
    public String toDeviceSoftwareCodePage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/upgrade/device_software_code_list";
    }

    @RequestMapping("/find_software_code_status_list.json")
    public void findSofrwareCodeStatusList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<TextValue> tv = new ArrayList<TextValue>();
        EnumConstantsInterface.Status[] status = EnumConstantsInterface.Status.values();
        for (int i = 0; i < status.length; i++) {
            tv.add(new TextValue(status[i].name(), status[i].getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    // 获取软件列表
    @RequestMapping("/find_software_code_list")
    public void findSoftwareCodeList(@RequestParam(value = Constants.PARAM_NAME, defaultValue = "") String name,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response, EnumConstantsInterface.Status status,
            ModelMap model) {

        Pageable<DeviceSoftwareCode> data = this.deviceSoftwareCodeService.findEpgSoftwareCodesByCondition(status,
                name, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(data), response);
    }

    @RequestMapping(value = "/soft_code_delete.json")
    public void deleteSoftCode(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.deviceSoftwareCodeService.deleteByIds(idsList);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_CODE_MAINTAIN, Constants.DELETE, ids,
                (bool == true) ? "删除设备软件号信息成功" : "删除设备软件号信息失败", request);
    }

    @RequestMapping(value = "/device_softCode_name_exists")
    public void checkDeviceSoftwareCodeNameExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("name") String name, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (name != null && !"".equals(name)) {
            DeviceSoftwareCode deviceSoftCode = this.deviceSoftwareCodeService.findSoftwareCodesByName(name);
            if (deviceSoftCode != null && !deviceSoftCode.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/device_softCode_code_exists")
    public void checkDeviceSoftwareCodeExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("code") String code, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (code != null && !"".equals(code)) {
            DeviceSoftwareCode deviceSoftCode = this.deviceSoftwareCodeService.findBySoftwareCode(code);
            if (deviceSoftCode != null && !deviceSoftCode.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "soft_code_add.json")
    public void addSoftCode(DeviceSoftwareCode epgSoftwareCode, HttpServletResponse response, HttpServletRequest request) {
        epgSoftwareCode.setCreateDate(new Date());
        epgSoftwareCode.setDistributeState(DistributeState.UNDISTRIBUTE);
        Operator op = ControllerUtil.getLoginOperator(request);
        epgSoftwareCode.setOperUser(op.getLoginName());
        boolean bool = this.deviceSoftwareCodeService.insert(epgSoftwareCode);
        String info = (bool == true) ? "新增软件号成功！" : "新增软件号失败！";
        info += "软件号名称：" + epgSoftwareCode.getName() + ";编码:" + epgSoftwareCode.getCode() + ";创建时间:"
                + epgSoftwareCode.getCreateDate() + ";状态：" + epgSoftwareCode.getStatus();
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_CODE_MAINTAIN, Constants.ADD, "新增软件信息编号："
                + epgSoftwareCode.getCode(), info, request);
    }

    @RequestMapping(value = "soft_code_to_update.json")
    public void getSoftCodeInfo(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            DeviceSoftwareCode epgSoftwareCode = this.deviceSoftwareCodeService.selectByPrimaryKey(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(epgSoftwareCode), response);
        }
    }

    @RequestMapping(value = "soft_code_update.json")
    public void updateSoftCode(DeviceSoftwareCode epgSoftwareCode, HttpServletResponse response,
            HttpServletRequest request) {
        boolean bool = false;
        if (epgSoftwareCode != null) {
        	List<UpgradeTask> upgradeTasks = this.upgradeTaskService.findUpgradeTasksByCondition(null, epgSoftwareCode.getId());
        	if(upgradeTasks.size() >0 && upgradeTasks != null && epgSoftwareCode.getStatus().equals(EnumConstantsInterface.Status.DISABLED)){
        		RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        	}else{
        		 Operator op = ControllerUtil.getLoginOperator(request);
                 epgSoftwareCode.setOperUser(op.getLoginName());
                 epgSoftwareCode.setLastModifyTime(new Date());
                 epgSoftwareCode.setDistributeState(DistributeState.UNDISTRIBUTE);
                 bool = this.deviceSoftwareCodeService.update(epgSoftwareCode);
        	}
        }
        String description = (bool == true) ? "更新软件号信息成功！" : "更新软件号信息失败！";
        description += "软件号名称：" + epgSoftwareCode.getName() + ";编码:" + epgSoftwareCode.getCode() + ";状态："
                + epgSoftwareCode.getStatus();
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        this.loggerWebService.saveOperateLog(Constants.DEVICE_SOFT_CODE_MAINTAIN, Constants.MODIFY,
                epgSoftwareCode.getId() + "", description, request);
    }

    /**
     * 保存或修改软件信息
     * 
     * @param request
     * @param response
     * @param epgSoftwareCode
     */
    @RequestMapping("/save_software_code_info")
    public void saveSoftwareCodeInfo(HttpServletRequest request, HttpServletResponse response,
            DeviceSoftwareCode epgSoftwareCode) {

        LOGGER.info("save_software_code_info params: {}", new Object[] { epgSoftwareCode.toString() });
        JsonResult jsonResult = null;
        try {
            jsonResult = deviceSoftwareCodeService.insertOrUpdate(epgSoftwareCode);
        } catch (Exception e) {
            LOGGER.error("save_software_code_info ：exception:{}", new Object[] { ExceptionUtils.getFullStackTrace(e) });
            jsonResult = new JsonResult(false, "操作失败！");
        }

        RenderUtils.renderJson(JsonUtils.toJson(jsonResult), response);
    }

    @RequestMapping("/delete_software_code_info.json")
    public void deleteSoftwareCodeInfos(@RequestParam(value = Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) {

        JsonResult jsonResult = null;
        try {
            jsonResult = deviceSoftwareCodeService.deleteByIds(ids);
        } catch (Exception e) {
            LOGGER.error("deleteSoftwareCodeInfos exception:{}", ExceptionUtils.getFullStackTrace(e));
            jsonResult = new JsonResult(false, "删除失败！");
        }
        RenderUtils.renderJson(JsonUtils.toJson(jsonResult), response);
    }

    @RequestMapping(value = "soft_code_rend.json")
    public void rendSoftCodeInfo(@RequestParam(Constants.IDS) String ids,
            @RequestParam(value = "areaId", defaultValue = "") String areaId, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        List<DeviceSoftwareCode> softwareCodes = new ArrayList<DeviceSoftwareCode>();
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                for (int i = 0; i < idsList.size(); i++) {
                    DeviceSoftwareCode epgSoftwareCode = this.deviceSoftwareCodeService.selectByPrimaryKey(idsList
                            .get(i));
                    softwareCodes.add(epgSoftwareCode);
                }
                bool = this.deviceSoftwareCodeService.rendSoftCode(softwareCodes, Long.parseLong(areaId));
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

    @RequestMapping(value = "/soft_code.json")
    public void getList(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<DeviceSoftwareCode> list = this.deviceSoftwareCodeService.getAll();
//        if (par != null && !par.isEmpty()) {
//            tv.add(new TextValue(" ", "所有"));
//        }
        tv.add(new TextValue("", "请选择"));
        for (DeviceSoftwareCode deviceSoftwareCode : list) {
            tv.add(new TextValue(deviceSoftwareCode.getId() + "", deviceSoftwareCode.getName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
}
