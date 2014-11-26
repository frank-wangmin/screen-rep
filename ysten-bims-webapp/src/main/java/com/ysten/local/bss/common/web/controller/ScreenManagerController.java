package com.ysten.local.bss.common.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.screen.domain.ScreenManager;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.local.bss.web.service.IScreenManagerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
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
import java.util.Date;
import java.util.List;

@Controller
public class ScreenManagerController {

    @Autowired
    private IScreenManagerWebService screenManagerWebService;
    @Autowired
    private ILoggerWebService loggerWebService;

    @RequestMapping(value = "/to_screen_manager_list")
    public String toCityList(ModelMap model) {
        return "/common/screen_manager_list";
    }

    @RequestMapping(value = "screen_manager_add")
    public void addCity(ScreenManager screenManager, @RequestParam(value = "areaId", defaultValue = "") Long areaId,
            HttpServletResponse response, HttpServletRequest request) {
        screenManager.setCreateDate(new Date());
        boolean bool = this.screenManagerWebService.saveScreenManager(screenManager, areaId);
        // 记录系统操作日志
        String logDescription = ((bool == true) ? "添加屏幕信息成功" : "添加屏幕信息失败") + "。屏幕信息：" + JsonUtils.toJson(screenManager);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        loggerWebService.saveOperateLog(Constants.SCREEN_MANAGER_MAINTAIN, Constants.ADD, screenManager.getId() + "",
                logDescription, request);

    }

    @RequestMapping(value = "screen_manager_to_update")
    public void toUpdateCity(@RequestParam("id") Long id, HttpServletResponse response) {
        if (id != null && !"".equals(id.toString())) {
            ScreenManager screenManager = this.screenManagerWebService.getScreenManagerById(id);
            RenderUtils.renderJson(JsonUtils.toJson(screenManager), response);
        }
    }

    @RequestMapping(value = "screen_manager_update")
    public void updateCity(ScreenManager screenManager, @RequestParam(value = "areaId", defaultValue = "") Long areaId,
            HttpServletResponse response, HttpServletRequest request) {
        boolean bool = this.screenManagerWebService.updateScreenManager(screenManager, areaId);
        // 记录系统操作日
        String logDescription = ((bool == true) ? "修改屏幕信息成功" : "修改屏幕信息失败") + "。屏幕信息：" + JsonUtils.toJson(screenManager);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        loggerWebService.saveOperateLog(Constants.SCREEN_MANAGER_MAINTAIN, Constants.MODIFY,
                screenManager.getId() + "", logDescription, request);

    }

    @RequestMapping(value = "screen_manager_list.json")
    public void findAllCity(@RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "start", defaultValue = "1") Integer start,
            @RequestParam(value = "limit", defaultValue = Constants.PAGE_NUMBER) Integer limit,
            HttpServletResponse response) {
        Pageable<ScreenManager> page = this.screenManagerWebService.findScreenManagerList(name, start, limit);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(page), response);
    }

    @RequestMapping(value = "/screen_manager_delete.json")
    public void deleteCity(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.screenManagerWebService.deleteScreenManager(idsList);
            RenderUtils.renderText(ControllerUtil.returnString(true), response);
        }
        // 记录系统操作日志
        String logDescription = (bool == true) ? "删除屏幕信息成功" : "删除失败";
        loggerWebService.saveOperateLog(Constants.SCREEN_MANAGER_MAINTAIN, Constants.DELETE, ids + "", logDescription,
                request);

    }
}
