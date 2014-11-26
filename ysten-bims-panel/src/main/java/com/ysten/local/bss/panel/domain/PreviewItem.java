package com.ysten.local.bss.panel.domain;

import com.ysten.local.bss.util.bean.Required;

import java.io.Serializable;

/**
 * Created by frank on 14-5-16.
 */
public class PreviewItem implements Serializable {

    private static final long serialVersionUID = 7662572573746462384L;

    private Long id;

    private Long ioid;

    @Required
    private Long templateId;

    private Integer left;

    private Integer top;

    @Required
    private Integer width;

    @Required
    private Integer height;

    private Integer type;

    @Required
    private Integer sort;

    private String imageUrl;

    // epg_ioid;
    private Long epgIoid;

    // epg_template_id
    private Long epgTemplateId;

    //epg
    private Integer itemLeft;

    /**
     * epg 模块与Top距离
     */
    private Integer itemTop;

    /**
     * epg 模块宽度
     */
    private Integer itemWidth;

    /**
     * epg 模块高度
     */
    private Integer itemHeight;

    /**
     * epg 排序
     */
    private Integer sortNum;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setIoid(Long ioid) {
        this.ioid = ioid;
    }

    public Long getIoid() {
        return ioid;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getEpgIoid() {
        return epgIoid;
    }

    public void setEpgIoid(Long epgIoid) {
        this.epgIoid = epgIoid;
    }

    public Long getEpgTemplateId() {
        return epgTemplateId;
    }

    public void setEpgTemplateId(Long epgTemplateId) {
        this.epgTemplateId = epgTemplateId;
    }

    public Integer getItemLeft() {
        return itemLeft;
    }

    public void setItemLeft(Integer itemLeft) {
        this.itemLeft = itemLeft;
    }

    public Integer getItemTop() {
        return itemTop;
    }

    public void setItemTop(Integer itemTop) {
        this.itemTop = itemTop;
    }

    public Integer getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
    }

    public Integer getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}
