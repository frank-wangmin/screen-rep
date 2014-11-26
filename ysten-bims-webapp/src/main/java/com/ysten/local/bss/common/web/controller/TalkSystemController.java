package com.ysten.local.bss.common.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.talk.domain.TalkSystem;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.local.bss.web.service.ITalkSystemWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
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
public class TalkSystemController {

    @Autowired
    private ITalkSystemWebService talkSystemWebService;
    @Autowired
    private ILoggerWebService loggerWebService;

    @RequestMapping(value = "/to_talk_system_list")
    public String toTalkSystem(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/common/talk_system_list";
    }

    @RequestMapping(value = "/talk_system_list.json")
    public void loadSystemConfig(
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Pageable<TalkSystem> page = talkSystemWebService.findAllTalkSystem(Integer.valueOf(pageNo),
                Integer.valueOf(pageSize));
        RenderUtils.renderJson(JsonUtils.toJson(page), response);
    }

    @RequestMapping(value = "/talk_system_add")
    public void addSystemConfig(@RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "description", defaultValue = "") String description, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = this.talkSystemWebService.saveTalkSystem(name, code, description);
        // 记录系统操作日志
        String logDescription = ((bool == true) ? "交互系统信息添加成功" : "交互系统信息添加失败") + "。交互系统信息：" + "交互系统名称：" + name
                + ",交互系统代号：" + code;
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        loggerWebService.saveOperateLog(Constants.TALK_SYSTEM_MAINTAIN, Constants.ADD, "", logDescription, request);

    }

    @RequestMapping(value = "/talk_system_to_update")
    public void toUpdateDeviceVendor(@RequestParam(value = "id") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            TalkSystem talkSystem = this.talkSystemWebService.getTalkSystemById(Long.parseLong(id));
            RenderUtils.renderJson(JsonUtils.toJson(talkSystem), response);
        }
    }

    @RequestMapping(value = "/talk_system_update")
    public void updateSystemConfig(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "description", defaultValue = "") String description, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = this.talkSystemWebService.updateTalkSystem(id, name, code, description);
        // 记录系统操作日志
        String logDescription = ((bool == true) ? "交互系统信息修改成功" : "交互系统信息修改失败") + "。交互系统信息：" + "交互系统名称：" + name
                + ",交互系统代号：" + code;
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        loggerWebService.saveOperateLog(Constants.TALK_SYSTEM_MAINTAIN, Constants.MODIFY, id, logDescription, request);

    }

    @RequestMapping(value = "/source.json")
    public void getTalkSystem(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
            HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<TalkSystem> sourceList = this.talkSystemWebService.getAllTalkSystem();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("0", "所有来源"));
        }
        for (TalkSystem talkSystem : sourceList) {
            tv.add(new TextValue(talkSystem.getCode(), talkSystem.getName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
}
