package com.ysten.local.bss.panel.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.panel.domain.PreviewItem;
import com.ysten.local.bss.panel.service.IPreviewItemService;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
@Controller
public class PreviewItemController {

    @Autowired
    private IPreviewItemService previewItemService;

    @Autowired
    private ILoggerWebService loggerWebService;

    private static Logger logger = LoggerFactory.getLogger(PreviewItemController.class);

    @RequestMapping(value = "/to_preview_item_list")
    public String toPreviewItemList() {
        return "/previewItem/preview_item_list";
    }

    @RequestMapping(value = "/preview_item_list.json")
    public void getPreviewItemList(
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<PreviewItem> pageable = this.previewItemService.getPreviewItemList(Integer.valueOf(pageNo),
                Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/previewItem_add.json", method = RequestMethod.POST)
    public void addPreviewItem(PreviewItem previewItem, HttpServletResponse response, HttpServletRequest request) {
        try {
            previewItemService.insert(previewItem);
            loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.TEMPLATE_CONFIG, previewItem.getId() + "",
                    "新增预览模块成功!", request);
            RenderUtils.renderText(Constants.SUCCESS, response);

        } catch (Exception e) {
            logger.error("新增预览模块失败 : {}", e);
            loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.TEMPLATE_CONFIG, previewItem.getId() + "",
                    "新增预览模块失败,"+ LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get_previewItem.json")
    public PreviewItem getPreviewItemById(@RequestParam(value = "id", defaultValue = "") String id) {
        if (!StringUtils.isBlank(id)) {
            return previewItemService.getPreviewItemById(Long.valueOf(id));
        }
        return new PreviewItem();
    }

    @RequestMapping(value = "/previewItem_update.json", method = RequestMethod.POST)
    public void updatePreviewItem(PreviewItem previewItem, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (previewItem.getId() != null) {
                previewItemService.updatePreviewItem(previewItem);
            }
            loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.TEMPLATE_CONFIG, previewItem.getId() + "",
                    "更新预览模块成功!", request);
            RenderUtils.renderText(Constants.SUCCESS, response);

        } catch (Exception e) {
            logger.error("更新预览模块失败 : {}", LoggerUtils.printErrorStack(e));
            loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.TEMPLATE_CONFIG, previewItem.getId() + "",
                    "更新预览模块失败!", request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/previewItem_delete.json")
    public void deletePreviewItem(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                previewItemService.deleteByIds(idsList);
                this.loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.TEMPLATE_CONFIG, ids,
                        "删除预览模块成功", request);
                RenderUtils.renderText(Constants.SUCCESS, response);
            }
        } catch (Exception e) {
            logger.error("删除预览模块错误 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.TEMPLATE_CONFIG, ids,
                    "删除预览模板失败,"+LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @RequestMapping(value = "/add_batch_previewItem.json", method = RequestMethod.POST)
    public void addBatchPreviewItem(@RequestBody PreviewItem[] previewItems, HttpServletRequest request,
            HttpServletResponse response) {

        try {
            if (previewItems != null && previewItems.length > 0) {
                previewItemService.insertBatchPreviewItem(previewItems);
            }
            this.loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.TEMPLATE_CONFIG, "",
                    "新增模块成功", request);
            RenderUtils.renderText(Constants.SUCCESS, response);
        } catch (Exception e) {
            logger.error("新增模块失败 : {}", LoggerUtils.printErrorStack(e));
            this.loggerWebService.saveOperateLog(Constants.PREVIEW_TEMPLATE_MAINTAIN, Constants.TEMPLATE_CONFIG, "",
                    "新增模块失败,"+LoggerUtils.printErrorStack(e), request);
            RenderUtils.renderText(Constants.FAILED, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get_previewlist_by_templateId.json")
    public List<PreviewItem> getPreviewItemListByTemplateId(
            @RequestParam(value = "templateId", defaultValue = "") Long templateId) {
        if (templateId != null) {
            return previewItemService.getPreviewItemListByTemplateId(templateId);
        }
        return null;
    }
}
