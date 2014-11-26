package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.device.domain.PanelImgBox;
import com.ysten.local.bss.device.domain.PanelNavBox;
import com.ysten.local.bss.device.domain.PanelTextBox;
import com.ysten.local.bss.device.repository.IXPanelRepository;
import com.ysten.local.bss.web.service.IPanelWebService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class PanelWebServiceImpl implements IPanelWebService {

    @Autowired
    private IXPanelRepository panelRepository;

    @Override
    public Pageable<PanelTextBox> findAllTextBoxByCondition(String title, Long progromId, int isNew, Integer pageNo,
            Integer pageSize) {
        return this.panelRepository.findAllTextBoxByCondition(title, progromId, isNew, pageNo, pageSize);
    }

    @Override
    public Pageable<PanelImgBox> findAllImgBoxByCondition(String title, Long progromId, Integer pageNo, Integer pageSize) {
        return this.panelRepository.findAllImgBoxByCondition(title, progromId, pageNo, pageSize);
    }

    @Override
    public Pageable<PanelNavBox> findAllNavBoxByTitle(String title, Integer pageNo, Integer pageSize) {
        return this.panelRepository.findAllNavBoxByTitle(title, pageNo, pageSize);
    }

    @Override
    public PanelTextBox getTextBoxById(Long id) {
        return this.panelRepository.getTextBoxById(id);
    }

    @Override
    public boolean saveTextBox(PanelTextBox panelTextBox) {
        return this.panelRepository.saveTextBox(panelTextBox);
    }

    @Override
    public boolean updateTextBox(PanelTextBox panelTextBox) {
        return this.panelRepository.updateTextBox(panelTextBox);
    }

    @Override
    public boolean deleteTextBox(List<Long> ids) {
        return this.panelRepository.deleteTextBox(ids);
    }

    @Override
    public PanelImgBox getImgBoxById(Long id) {
        return this.panelRepository.getImgBoxById(id);
    }

    @Override
    public boolean saveImgBox(PanelImgBox panelImgBox) {
        return this.panelRepository.saveImgBox(panelImgBox);
    }

    @Override
    public boolean updateImgBox(PanelImgBox panelImgBox) {
        return this.panelRepository.updateImgBox(panelImgBox);
    }

    @Override
    public boolean deleteImgBox(List<Long> ids) {
        return this.panelRepository.deleteImgBox(ids);
    }

    @Override
    public PanelNavBox getNavBoxById(Long id) {
        return this.panelRepository.getNavBoxById(id);
    }

    @Override
    public boolean saveNavBox(PanelNavBox panelNavBox) {
        return this.panelRepository.saveNavBox(panelNavBox);
    }

    @Override
    public boolean updateNavBox(PanelNavBox panelNavBox) {
        return this.panelRepository.updateNavBox(panelNavBox);
    }

    @Override
    public boolean deleteNavBox(List<Long> ids) {
        return this.panelRepository.deleteNavBox(ids);
    }

    // 拼NavBoxs
    @Override
    public String navBoxsXml(List<PanelNavBox> panelNavBoxList) {
        Iterator<PanelNavBox> interPanelNavBox = panelNavBoxList.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("<navboxs>");
        while (interPanelNavBox.hasNext()) {
            PanelNavBox navBox = interPanelNavBox.next();
            sb.append("<navbox id=\"").append(navBox.getNavBoxId()).append("\">");
            sb.append("<title>").append(navBox.getTitle()).append("</title></navbox>");
        }
        sb.append("</navboxs>");
        return sb.toString();
    }

    // 拼textboxs
    @Override
    public String textBoxsXml(List<PanelTextBox> panelTextBoxList) {
        Iterator<PanelTextBox> interPanelTextBox = panelTextBoxList.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("<textboxs>");
        while (interPanelTextBox.hasNext()) {
            PanelTextBox textBox = interPanelTextBox.next();
            sb.append("<textbox id=\"").append(textBox.getTextBoxId()).append("\">");
            sb.append("<title>").append(textBox.getTitle()).append("</title>");
            boolean isNew = false;
            if (textBox.getIsNew() == 1) {
                isNew = true;
            }
            sb.append("<isNew>").append(isNew).append("</isNew>");
            sb.append("<progromId>").append(textBox.getProgromId()).append("</progromId></textbox>");
        }
        sb.append("</textboxs>");
        return sb.toString();
    }

    // 拼imgboxs
    @Override
    public String imgBoxsXml(List<PanelImgBox> panelImgBoxList) {
        Iterator<PanelImgBox> interPanelImgBoxList = panelImgBoxList.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("<imgboxs>");
        while (interPanelImgBoxList.hasNext()) {
            PanelImgBox imgBox = interPanelImgBoxList.next();
            sb.append("<imgbox id=\"").append(imgBox.getImgBoxId()).append("\">");
            if (!"".equals(imgBox.getItemId() + "") && imgBox.getItemId() != null) {
                // if(StringUtils.isNotBlank(imgBox.getItemId().toString())){
                List<PanelImgBox> imgBoxs = this.panelRepository.getByImgBoxId(imgBox.getItemId());
                for (PanelImgBox imgBoxNew : imgBoxs) {
                    sb.append("<item id=\"").append(imgBoxNew.getItemId()).append("\">");
                    sb.append("<imgurl>").append(imgBoxNew.getImgUrl()).append("</imgurl>");
                    sb.append("<actionUrl>").append(imgBoxNew.getActionUrl()).append("</actionUrl>");
                    sb.append("<progromId>").append(imgBoxNew.getProgromId()).append("</progromId></item>");
                }
            } else {
                sb.append("<title>").append(imgBox.getTitle()).append("</title>");
                sb.append("<imgurl>").append(imgBox.getImgUrl()).append("</imgurl>");
                sb.append("<progromId>").append(imgBox.getProgromId()).append("</progromId>");
            }
            sb.append("</imgbox>");
        }
        sb.append("</imgboxs>");
        return sb.toString();
    }

    @Override
    public List<PanelTextBox> findAllTextBox() {
        return this.panelRepository.findAllTextBox();
    }

    @Override
    public List<PanelNavBox> findAllNavBox() {
        return this.panelRepository.findAllNavBox();
    }

    @Override
    public List<PanelImgBox> findAllImgBox() {
        return this.panelRepository.findAllImgBox();
    }
}
