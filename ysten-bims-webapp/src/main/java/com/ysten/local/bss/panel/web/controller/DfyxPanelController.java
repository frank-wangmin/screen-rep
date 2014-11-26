package com.ysten.local.bss.panel.web.controller;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.PanelImgBox;
import com.ysten.local.bss.device.domain.PanelNavBox;
import com.ysten.local.bss.device.domain.PanelTextBox;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.IPanelWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class DfyxPanelController {

    @Autowired
    private IPanelWebService panelWebService;
    @Autowired
    private SystemConfigService systemConfigService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DfyxPanelController.class);

    @RequestMapping(value = "/to_nav_box_list")
    public String toNavBoxList(ModelMap model) {
        return "/panel/panel_nav_box_list";
    }

    @RequestMapping(value = "/to_img_box_list")
    public String toImgBoxList(ModelMap model) {
        return "/panel/panel_img_box_list";
    }

    @RequestMapping(value = "/to_text_box_list")
    public String toTextBoxList(ModelMap model) {
        return "/panel/panel_text_box_list";
    }

    @RequestMapping(value = "/nav_box_list.json")
    public void getPanelNavBoxs(@RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<PanelNavBox> pageable = this.panelWebService.findAllNavBoxByTitle(title, Integer.valueOf(pageNo),
                Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/text_box_list.json")
    public void getPanelTextBoxs(@RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "progromId", defaultValue = "") String progromId,
            @RequestParam(value = "isNew", defaultValue = "") String isNew,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Long progrom = "".equals(progromId) ? 0 : Long.valueOf(progromId);
        int isNewId = "".equals(isNew) ? 0 : Integer.parseInt(isNew);
        Pageable<PanelTextBox> pageable = this.panelWebService.findAllTextBoxByCondition(title, progrom, isNewId,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/img_box_list.json")
    public void getPanelImgBoxs(@RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "progromId", defaultValue = "") String progromId,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Long progrom = "".equals(progromId) ? 0 : Long.valueOf(progromId);
        Pageable<PanelImgBox> pageable = this.panelWebService.findAllImgBoxByCondition(title, progrom,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "nav_to_update.json")
    public void getNavBoxsInfo(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            PanelNavBox navBox = this.panelWebService.getNavBoxById(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(navBox), response);
        }
    }

    @RequestMapping(value = "text_to_update.json")
    public void getTextBoxsInfo(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            PanelTextBox textBox = this.panelWebService.getTextBoxById(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(textBox), response);
        }
    }

    @RequestMapping(value = "img_to_update.json")
    public void getImgBoxsInfo(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            PanelImgBox imgBox = this.panelWebService.getImgBoxById(Long.valueOf(id));
            RenderUtils.renderJson(JsonUtils.toJson(imgBox), response);
        }
    }

    @RequestMapping(value = "text_add.json")
    public void addTextBox(@RequestParam(value = "textBoxId", defaultValue = "") String textBoxId,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "progromId", defaultValue = "") String progromId,
            @RequestParam(value = "isNew", defaultValue = "") String isNew, HttpServletResponse response,
            HttpServletRequest request) {
        PanelTextBox panelTextBox = new PanelTextBox();
        if (!textBoxId.isEmpty()) {
            panelTextBox.setTextBoxId(Long.valueOf(textBoxId));
        }
        if (!isNew.isEmpty()) {
            panelTextBox.setIsNew(Integer.parseInt(isNew));
        }
        if (!progromId.isEmpty()) {
            panelTextBox.setProgromId(Long.valueOf(progromId));
        }
        panelTextBox.setTitle(title);
        boolean bool = this.panelWebService.saveTextBox(panelTextBox);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    @RequestMapping(value = "text_update.json")
    public void updateTextBox(PanelTextBox panelTextBox, HttpServletResponse response, HttpServletRequest request) {
        boolean bool = false;
        if (panelTextBox != null) {
            bool = this.panelWebService.updateTextBox(panelTextBox);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    @RequestMapping(value = "/text_delete.json")
    public void deleteTextBox(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.panelWebService.deleteTextBox(idsList);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    @RequestMapping(value = "nav_add.json")
    public void addNavBox(@RequestParam(value = "navBoxId", defaultValue = "") String navBoxId,
            @RequestParam(value = "title", defaultValue = "") String title, HttpServletResponse response,
            HttpServletRequest request) {
        PanelNavBox panelNavBox = new PanelNavBox();
        if (!navBoxId.isEmpty()) {
            panelNavBox.setNavBoxId(Long.valueOf(navBoxId));
        }
        panelNavBox.setTitle(title);
        boolean bool = this.panelWebService.saveNavBox(panelNavBox);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    @RequestMapping(value = "nav_update.json")
    public void updateNavBox(PanelNavBox panelNavBox, HttpServletResponse response, HttpServletRequest request) {
        boolean bool = false;
        if (panelNavBox != null) {
            bool = this.panelWebService.updateNavBox(panelNavBox);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    @RequestMapping(value = "/nav_delete.json")
    public void deleteNavBox(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.panelWebService.deleteNavBox(idsList);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    @RequestMapping(value = "img_add.json")
    public void addImgBox(@RequestParam(value = "imgBoxId", defaultValue = "") String imgBoxId,
            @RequestParam(value = "itemId", defaultValue = "") String itemId,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "imgUrl", defaultValue = "") String imgUrl,
            @RequestParam(value = "actionUrl", defaultValue = "") String actionUrl,
            @RequestParam(value = "progromId", defaultValue = "") String progromId, HttpServletResponse response,
            HttpServletRequest request) {
        PanelImgBox panelImgBox = new PanelImgBox();
        if (!imgBoxId.isEmpty()) {
            panelImgBox.setImgBoxId(Long.valueOf(imgBoxId));
        }
        if (!itemId.isEmpty()) {
            panelImgBox.setItemId(Long.valueOf(itemId));
        }
        if (!progromId.isEmpty()) {
            panelImgBox.setProgromId(Long.valueOf(progromId));
        }
        panelImgBox.setTitle(title);
        panelImgBox.setActionUrl(actionUrl);
        panelImgBox.setImgUrl(imgUrl);
        boolean bool = this.panelWebService.saveImgBox(panelImgBox);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    @RequestMapping(value = "img_update.json")
    public void updateImgBox(PanelImgBox panelImgBox, HttpServletResponse response, HttpServletRequest request) {
        boolean bool = false;
        if (panelImgBox != null) {
            bool = this.panelWebService.updateImgBox(panelImgBox);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    @RequestMapping(value = "/img_delete.json")
    public void deleteImgBox(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.panelWebService.deleteImgBox(idsList);
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }

    // 上传NavBoxs
    // public void sync(@RequestParam(Constants.IDS) String
    // ids,HttpServletRequest request,HttpServletResponse response){
    // if (StringUtils.isNotBlank(ids)) {
    // List<Long> idsList = StringUtil.splitToLong(ids);
    // List<PanelNavBox> panelNavBoxList = new ArrayList<PanelNavBox>();
    // for(Long id:idsList){
    // panelNavBoxList.add(this.panelWebService.getNavBoxById(id));
    // }
    // String xml = this.panelWebService.navBoxsXml(panelNavBoxList);
    //
    // }
    // }
    // 读取BOXS 上传文件到ftp
    @RequestMapping(value = "/upload_boxs.json")
    public void sync(HttpServletRequest request, HttpServletResponse response) {
        String xml_nav = this.panelWebService.navBoxsXml(this.panelWebService.findAllNavBox());
        LOGGER.debug("xml_nav====>" + xml_nav);
        String xml_text = this.panelWebService.textBoxsXml(this.panelWebService.findAllTextBox());
        LOGGER.debug("xml_text====>" + xml_text);
        String xml_img = this.panelWebService.imgBoxsXml(this.panelWebService.findAllImgBox());
        LOGGER.debug("xml_img====>" + xml_img);
        String xml = Constants.XML_HEAD + Constants.XML_BODY + xml_nav + xml_text + xml_img + Constants.XML_ROOT;
        LOGGER.debug("xml====>" + xml);
        FileWriter writer;
        boolean bool = false;
        String path = systemConfigService.getSystemConfigByConfigKey("upload_file");
        LOGGER.debug("path===============" + path);
        // String path = "E:/data/panel.xml";
        try {
            writer = new FileWriter(path);
            writer.write(xml);
            writer.flush();
            writer.close();
            bool = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    }
}
