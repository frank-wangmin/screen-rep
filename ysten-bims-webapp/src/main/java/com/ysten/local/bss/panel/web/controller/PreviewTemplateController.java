package com.ysten.local.bss.panel.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.panel.domain.PreviewTemplate;
import com.ysten.local.bss.panel.service.IPreviewTemplateService;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
@Controller
public class PreviewTemplateController {

    @Autowired
    private IPreviewTemplateService previewTemplateService;

    @Autowired
    private ILoggerWebService loggerWebService;

    private static Logger logger = LoggerFactory.getLogger(PreviewTemplateController.class);

    @RequestMapping(value = "/to_preview_template_list")
    public String toPreviewTemplateList() {
        return "/panel/preview_template_list";
    }

    @ResponseBody
    @RequestMapping(value = "/preview_template_list.json")
    public Pageable<PreviewTemplate> getPreviewTemplateList(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "Id", defaultValue = "") Long Id,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<PreviewTemplate> pageable = previewTemplateService.getTargetList(Id,name, Integer.valueOf(pageNo),
                Integer.parseInt(pageSize));
        return pageable;
    }

    @ResponseBody
    @RequestMapping(value = "/get_all_preview_templateList.json")
    public List<JSONObject> getAllPackageList() {
        List<PreviewTemplate> targetList = previewTemplateService.findAllCustomedTargetList();
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (PreviewTemplate previewTemplate : targetList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", previewTemplate.getName());
            jsonObject.put("value", previewTemplate.getId());
            jsonList.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "请选择");
        jsonObject.put("value", "");
        jsonList.add(jsonObject);
        return jsonList;
    }

    @ResponseBody
    @RequestMapping(value = "/find_all_outer_templateList.json")
    public List<JSONObject> getAllOuterPackageList() {
        List<PreviewTemplate> targetList = previewTemplateService.findAllOuterList();
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (PreviewTemplate previewTemplate : targetList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", previewTemplate.getName());
            jsonObject.put("value", previewTemplate.getId());
            jsonList.add(jsonObject);
        }
        return jsonList;
    }

    @RequestMapping(value = "/previewTemplate_add.json", method = RequestMethod.POST)
    public void addPreviewTemplate(PreviewTemplate previewTemplate, HttpServletResponse response,
            HttpServletRequest request) {
        try {
            Operator op = ControllerUtil.getLoginOperator(request);
            previewTemplate.setOprUserId(op!=null?op.getId():null);
            previewTemplateService.insert(previewTemplate);
            loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.ADD, previewTemplate.getId()
                    + "", "新增预览模板成功!", request);
            RenderUtils.renderText(Constants.SUCCESS, response);

        } catch (Exception e) {
            logger.error("新增预览模板失败 : {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.ADD, previewTemplate.getId()
                    + "", "新增预览模板失败,"+ LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get_previewTemplate.json")
    public PreviewTemplate getPreviewTemplateById(@RequestParam(value = "id", defaultValue = "") String id) {
        if (!StringUtils.isBlank(id)) {
            return previewTemplateService.getTargetById(Long.valueOf(id));
        }
        return new PreviewTemplate();
    }

    @RequestMapping(value = "/previewTemplate_update.json", method = RequestMethod.POST)
    public void updatePreviewTemplate(PreviewTemplate previewTemplate, HttpServletResponse response,
            HttpServletRequest request) {
        try {
            if (previewTemplate.getId() != null) {
                Operator op = ControllerUtil.getLoginOperator(request);
                previewTemplateService.update(previewTemplate,op!=null?op.getId():null);
            }
            loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.MODIFY,
                    previewTemplate.getId() + "", "更新预览模板成功!", request);
            RenderUtils.renderText(Constants.SUCCESS, response);

        } catch (Exception e) {
            logger.error("更新预览模板失败 : {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.MODIFY,
                    previewTemplate.getId() + "", "更新预览模板失败,"+LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/previewTemplate_delete.json")
    public void deletePreviewTemplate(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                previewTemplateService.deleteByIds(idsList);
                this.loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.DELETE, ids,
                        "删除预览模板成功", request);
                RenderUtils.renderText(Constants.SUCCESS, response);
            }
        } catch (Exception e) {
            logger.error("删除预览模板失败 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.DELETE, ids,
                    "删除预览模板失败,"+LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }
}
