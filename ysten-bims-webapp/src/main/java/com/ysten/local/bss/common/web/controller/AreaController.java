package com.ysten.local.bss.common.web.controller;

import com.ysten.area.domain.Area;
import com.ysten.area.service.IAreaService;
import com.ysten.local.bss.area.domian.AreaBean;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.web.service.IAreaWebService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AreaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);
    private static final String DESCRIPTION = "description";

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IAreaWebService areaWebService;
    @Autowired
    private ILoggerWebService loggerWebService;

    @RequestMapping(value = "/area_state.json")
    public void getCustomerState(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        for (Area.State state : Area.State.values()) {
            tv.add(new TextValue(state.toString(), state.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/area_id.json")
    public void getById(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        Area area = this.areaService.getAreaById(Long.parseLong(id));
        RenderUtils.renderJson(JsonUtils.toJson(area), response);
    }

    @RequestMapping(value = "/area_list")
    public String areaList(ModelMap model) {
        return "/common/area_list";
    }

    @RequestMapping(value = "/area_list.json")
    public void toAddArea(HttpServletResponse response, ModelMap model) {
        List<AreaBean> listArea = this.areaWebService.findAllArea();
        RenderUtils.renderJson(EnumDisplayUtil.toJson(listArea), response);

    }

    /**
     * 判断当前节点放到父节点下是否可行
     *
     * @param areaId
     * @param parentId
     * @param response
     * @param model
     */
    @RequestMapping(value = "/area_son_list.json")
    public void getSonLinst(@RequestParam(value = "areaId", defaultValue = "") String areaId,
                            @RequestParam(value = "parentId", defaultValue = "") String parentId, HttpServletResponse response,
                            ModelMap model) {
        List<Area> listArea = this.areaService.findAllArea();
        List<Long> ids = new ArrayList<Long>();
        ids.add(Long.parseLong(areaId));
        for (int i = 0; i < listArea.size(); i++) {
            Area area = listArea.get(i);
            for (int j = 0; j < ids.size(); j++) {
                if (area.getParentId().equals(ids.get(j))) {
                    ids.add(area.getId());
                }
            }
        }
        if (ids.contains(Long.parseLong(parentId))) {
            RenderUtils.renderText("false", response);
        } else {
            RenderUtils.renderText("true", response);
        }
    }

    @RequestMapping(value = "/area_tree.json")
    public void areaTree(HttpServletResponse response, ModelMap model) {
        List<Area> listArea = this.areaService.findAllArea();
        RenderUtils.renderJson(EnumDisplayUtil.toJson(listArea), response);
    }

    @RequestMapping(value = "/area_add")
    public void addArea(@RequestParam(value = "code", defaultValue = "") String code,
                        @RequestParam(value = "name", defaultValue = "") String name,
                        @RequestParam(value = DESCRIPTION, defaultValue = "") String description,
                        @RequestParam(value = "state", defaultValue = "") Area.State state,
                        @RequestParam(value = "parentName", defaultValue = "") String parentName,
                        @RequestParam(value = "parentId", defaultValue = "") Long parentId, HttpServletRequest request,
                        HttpServletResponse response, ModelMap model) {
        try {
            Area area = new Area();
            area.setParentId(parentId);
            area.setParentName(parentName);
            area.setCode(code);
            area.setMemo(description);
            area.setName(name);
            area.setState(state);
            if (this.areaService.getAreaByNameOrCode("", code) == null) {
                boolean bool = this.areaService.saveArea(area);
                LOGGER.info("system controller: add area result:{}.", bool);
                List<String> nameList = new ArrayList<String>();
                nameList.add(area.getName());
                // 记录系统操作日志
                String logDescription = ((bool == true) ? "添加区域信息成功" : "添加区域信息失败") + "。区域信息：" + JsonUtils.toJson(area);
                loggerWebService.saveOperateLog(Constants.AREA_INFO_MAINTAIN, Constants.ADD, area.getId() + "",
                        logDescription, request);
                RenderUtils.renderText(this.returnString(bool, false), response);
            } else {
                RenderUtils.renderText(this.returnString(false, true), response);
                // 记录系统操作日志
                String logDescription = "添加区域信息失败";
                loggerWebService.saveOperateLog(Constants.AREA_INFO_MAINTAIN, Constants.ADD, area.getId() + "",
                        logDescription, request);
            }
        } catch (Exception e) {
            LOGGER.error("add area exception, {}.", e);
            // 记录系统操作日志
            String logDescription = "添加区域信息失败";
            loggerWebService.saveOperateLog(Constants.AREA_INFO_MAINTAIN, Constants.ADD, "", logDescription + "异常信息："
                    + e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/to_update_area.json")
    public void toUpdateArea(@RequestParam(value = "id", defaultValue = "") Long id, HttpServletResponse response,
                             ModelMap model) {
        if (id != null) {
            Area area = this.areaService.getAreaById(id);
            RenderUtils.renderJson(JsonUtils.toJson(area), response);
        }
    }

    @RequestMapping(value = "/area_update")
    public void updateArea(@RequestParam(value = "id", defaultValue = "") Long areaId,
                           @RequestParam(value = "code", defaultValue = "") String code,
                           @RequestParam(value = "name", defaultValue = "") String name,
                           @RequestParam(value = "parentName", defaultValue = "") String parentName,
                           @RequestParam(value = DESCRIPTION, defaultValue = "") String description,
                           @RequestParam(value = "state", defaultValue = "") Area.State state,
                           @RequestParam(value = "parentId", defaultValue = "") Long parentId, HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) {
        if (areaId != null) {
            Area area = this.areaService.getAreaById(areaId);
            String checkcode = area.getCode();
            area.setParentName(parentName);
            area.setCode(code);
            area.setName(name);
            area.setParentId(parentId);
            area.setState(state);
            area.setMemo(description);
            if (checkcode.equals(code)) {
                boolean bool = this.areaService.updateArea(area);
                LOGGER.debug("device controller: update area result:{}.", bool);
                List<String> nameList = new ArrayList<String>();
                nameList.add(area.getName());
                // 记录系统操作日志
                String logDescription = ((bool == true) ? "修改区域信息成功" : "修改区域信息失败") + "。区域信息：" + JsonUtils.toJson(area);
                RenderUtils.renderText(this.returnString(bool, false), response);
                loggerWebService.saveOperateLog(Constants.AREA_INFO_MAINTAIN, Constants.MODIFY, areaId + "",
                        logDescription, request);

            } else if (this.areaService.getAreaByNameOrCode("", code) == null) {
                boolean bool = this.areaService.updateArea(area);
                LOGGER.debug("device controller: update area result:{}.", bool);
                List<String> nameList = new ArrayList<String>();
                nameList.add(area.getName());
                // 记录系统操作日志
                String logDescription = ((bool == true) ? "修改区域信息成功" : "修改区域信息失败") + "。区域信息：" + JsonUtils.toJson(area);
                RenderUtils.renderText(this.returnString(bool, false), response);
                loggerWebService.saveOperateLog(Constants.AREA_INFO_MAINTAIN, Constants.MODIFY, areaId + "",
                        logDescription, request);

            } else {
                // 记录系统操作日志
                String logDescription = "修改区域信息失败";
                RenderUtils.renderText(this.returnString(false, true), response);
                loggerWebService.saveOperateLog(Constants.AREA_INFO_MAINTAIN, Constants.MODIFY, areaId + "",
                        logDescription, request);

            }
        }
    }

//    @RequestMapping(value = "/area_delete")
//    public void deleteArea(@RequestParam(value = "id", defaultValue = "") Long areaId, HttpServletRequest request,
//            HttpServletResponse response, ModelMap model) {
//        try {
//            boolean isSuccess = this.areaWebService.deleteById(areaId);
//            // 记录系统操作日志
//            String logDescription = (isSuccess == true) ? "删除区域信息成功" : "删除区域信息失败";
//            RenderUtils.renderText(this.returnString(isSuccess, false), response);
//            loggerWebService.saveOperateLog(Constants.AREA_INFO_MAINTAIN, Constants.DELETE, areaId + "",
//                    logDescription, request);
//
//        } catch (Exception e) {
//            RenderUtils.renderText(this.returnString(false, false), response);
//        }
//    }

    @RequestMapping(value = "/area_update_pid")
    public void updateArea(@RequestParam(value = "id", defaultValue = "") Long areaId,
                           @RequestParam(value = "parentId", defaultValue = "") Long parentId,
                           @RequestParam(value = "parentName", defaultValue = "") String parentName, HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) {
        try {
            Area area = this.areaService.getAreaById(areaId);
            area.setParentId(parentId);
            area.setParentName(parentName);
            this.areaService.updateArea(area);
            RenderUtils.renderText(this.returnString(true, false), response);
        } catch (Exception e) {
            RenderUtils.renderText(this.returnString(false, false), response);
        }
    }

    // 区域
    @RequestMapping(value = "/area.json")
    public void getArea(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Area> listArea = this.areaService.findAllAreaByState(Area.State.USABLE);
        if (String.valueOf(Constants.ZERO).equals(par)) {
            tv.add(new TextValue(String.valueOf(Constants.ZERO), Constants.ALL_AREA));
        }
        if (String.valueOf(Constants.ONE).equals(par)) {
            tv.add(new TextValue(String.valueOf(Constants.ZERO), Constants.PLEASE_SELECT));
        }
        for (Area area : listArea) {
            if (area.getId() != 1) {
                tv.add(new TextValue(area.getId().toString(), area.getName()));
            }
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
    @RequestMapping(value = "/area_distCode.json")
    public void getAreaOfDistCode(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Area> listArea = this.areaService.findAllAreaByState(Area.State.USABLE);
        if (String.valueOf(Constants.ZERO).equals(par)) {
            tv.add(new TextValue(String.valueOf(Constants.DIST_CODE_CHINA), Constants.CHINA));
        }
        if (String.valueOf(Constants.ONE).equals(par)) {
            tv.add(new TextValue(String.valueOf(Constants.ZERO), Constants.PLEASE_SELECT));
        }
        for (Area area : listArea) {
        	if (area.getId() != 1) {
                tv.add(new TextValue(area.getDistCode(), area.getName()));
        	}
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    // 区域
    @RequestMapping(value = "/get_all_districtCode.json")
    public void getAllDistrictCodes(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Area> listArea = this.areaService.findAllAreaByState(Area.State.USABLE);
        for (Area area : listArea) {
             if (area.getId() == 1) {
                 area.setName("BIMS(中心)");
             }
            tv.add(new TextValue(area.getDistCode(), area.getName()));
        }
        tv.add(new TextValue(Constants.EPG_DISTRICT_CODE, Constants.EPG_NAME));
        tv.add(new TextValue("","全部"));
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @ResponseBody
    @RequestMapping(value = "/get_name_by_districtCode.json")
    public Area getNameByDistrictCode(@RequestParam(value = "districtCode", defaultValue = "") String districtCode, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<Area> listArea = this.areaService.findAllAreaByState(Area.State.USABLE);
        Area area = new Area();
        if (StringUtils.isNotEmpty(districtCode)) {
            if (Constants.EPG_DISTRICT_CODE.equals(districtCode)) {
                area.setDistCode(Constants.EPG_DISTRICT_CODE);
                area.setName(Constants.EPG_NAME);
            }
            for (Area area1 : listArea) {
                if (StringUtils.isNotEmpty(area1.getDistCode()) && area1.getDistCode().trim().equals(districtCode.trim())) {
                    if (area1.getId() == 1) {
                        area1.setName("BIMS(中心)");
                    }
                    area = area1;
                }
            }
        }
        return area;
    }

    private String returnString(boolean bool, boolean repeat) {
        String result = "";
        if (bool) {
            result = Constants.SUCCESS;
        } else if (repeat) {
            result = Constants.REPEAT;
        } else {
            result = Constants.FAILED;
        }
        return result;
    }

    // private String parseObjectToString(Object obj){
    // return obj==null?"":obj.toString();
    // }
}
