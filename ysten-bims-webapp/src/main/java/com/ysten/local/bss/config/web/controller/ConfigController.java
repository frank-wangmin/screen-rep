package com.ysten.local.bss.config.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.SpDefine;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IConfigWebService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
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
public class ConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private IConfigWebService configWebService;
    @Autowired
    private ILoggerWebService loggerWebService;

    @RequestMapping(value = "/to_sp_define_list")
    public String toSpDefineList(ModelMap model) {
        return "/config/sp_define_list";
    }

    @RequestMapping(value = "/sp_name_exist")
    public void spNameExist(@RequestParam("spName") String spName, HttpServletResponse response) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("sp_name_exist:{} param[spName]=" + spName);
        }
        try {
            SpDefine spDefine = this.configWebService.getSpDefineByName(spName);
            if (spDefine != null) {
                RenderUtils.renderText("exist", response);
            } else {
                RenderUtils.renderText("noexist", response);
            }
        } catch (Exception e) {
            LOGGER.error("sp name exist error:{}" + e);
        }
    }

    @RequestMapping(value = "sp_define_add")
    public void spAdd(SpDefine spDefine, HttpServletResponse response, HttpServletRequest request) {
        spDefine.setCreateDate(new Date());
        boolean bool = this.configWebService.saveSpDefine(spDefine);
        // 记录系统操作日志
        String logDescription = ((bool == true) ? "添加运营商成功" : "添加运营商失败") + "。运营商信息：" + JsonUtils.toJson(spDefine);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        loggerWebService.saveOperateLog(Constants.SP_INFO_MAINTAIN, Constants.ADD, spDefine.getId() + "",
                logDescription, request);

    }

    @RequestMapping(value = "sp_define_to_update")
    public void spToUpdate(@RequestParam("id") Integer id, HttpServletResponse response) {
        if (id != null && !"".equals(id.toString())) {
            SpDefine spDefine = this.configWebService.getSpDefineById(id);
            RenderUtils.renderJson(JsonUtils.toJson(spDefine), response);
        }
    }

    @RequestMapping(value = "sp_define_update")
    public void spUpdate(SpDefine spDefine, HttpServletResponse response, HttpServletRequest request) {
        boolean bool = this.configWebService.updateSpDefine(spDefine);
        // 记录系统操作日志
        String logDescription = ((bool == true) ? "修改运营商信息成功" : "修改运营商信息失败") + "。运营商信息：" + JsonUtils.toJson(spDefine);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        loggerWebService.saveOperateLog(Constants.SP_INFO_MAINTAIN, Constants.MODIFY, spDefine.getId() + "",
                logDescription, request);

    }

    @RequestMapping(value = "sp_define_list.json")
    public void cpSearch(SpDefine spDefine, @RequestParam(value = "start", defaultValue = "1") Integer start,
            @RequestParam(value = "limit", defaultValue = Constants.PAGE_NUMBER) Integer limit,
            HttpServletResponse response) {
        Pageable<SpDefine> spPage = this.configWebService.findSpDefine(spDefine.getName(), spDefine.getCode(), start,
                limit);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(spPage), response);
    }

    @RequestMapping(value = "/sp_state_list.json")
    public void spStateList(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        for (SpDefine.State state : SpDefine.State.values()) {
            tv.add(new TextValue(state.toString(), state.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/sp_define.json")
    public void getArea(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<SpDefine> spDefineList = this.configWebService.findAllSp();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("0", "所有运营商"));
        }
        for (SpDefine spDefine : spDefineList) {
            tv.add(new TextValue(spDefine.getId().toString(), spDefine.getName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

}
