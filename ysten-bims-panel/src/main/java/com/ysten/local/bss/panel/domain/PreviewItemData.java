package com.ysten.local.bss.panel.domain;

import com.ysten.local.bss.util.bean.Required;

import java.io.Serializable;
import java.util.List;

/**
 * @author cwang
 */
public class PreviewItemData extends BaseDomain {

    private static final long serialVersionUID = 3970927009386225033L;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.id
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    private Long id;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.template_id
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    @Required
    private Long templateId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.left
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    private Integer left;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.top
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    private Integer top;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.width
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    @Required
    private Integer width;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.height
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    @Required
    private Integer height;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.type
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    private Integer type;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.sort
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    @Required
    private Integer sort;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.content_type
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    private Integer contentType;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.content_id
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    private Long contentId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column bss_preview_item_data.content_item_id
     *
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    private Long contentItemId;

    private String imageUrl;

    // epg适配用
    private Long ioid;
    // epg
    private Long epgIoid;
    // epg
    private Long epgContentId;
    // epg
    private Long epgContentItemId;
    // epg
    private Long epgTemplateId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * epg 模块与left距离
     */
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
    // epg
    private Integer sortNum;

    private String districtCode;


    /* 冗余 */
    private PanelItem panelItem;
    /* 冗余 */

    public PanelItem getPanelItem() {
        return panelItem;
    }

    public void setPanelItem(PanelItem panelItem) {
        this.panelItem = panelItem;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.id
     *
     * @return the value of bss_preview_item_data.id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.id
     *
     * @param id the value for bss_preview_item_data.id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.template_id
     *
     * @return the value of bss_preview_item_data.template_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.template_id
     *
     * @param templateId the value for bss_preview_item_data.template_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.left
     *
     * @return the value of bss_preview_item_data.left
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Integer getLeft() {
        return left;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.left
     *
     * @param left the value for bss_preview_item_data.left
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setLeft(Integer left) {
        this.left = left;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.top
     *
     * @return the value of bss_preview_item_data.top
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Integer getTop() {
        return top;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.top
     *
     * @param top the value for bss_preview_item_data.top
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setTop(Integer top) {
        this.top = top;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.width
     *
     * @return the value of bss_preview_item_data.width
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.width
     *
     * @param width the value for bss_preview_item_data.width
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.height
     *
     * @return the value of bss_preview_item_data.height
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.height
     *
     * @param height the value for bss_preview_item_data.height
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.type
     *
     * @return the value of bss_preview_item_data.type
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.type
     *
     * @param type the value for bss_preview_item_data.type
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.sort
     *
     * @return the value of bss_preview_item_data.sort
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.sort
     *
     * @param sort the value for bss_preview_item_data.sort
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.content_type
     *
     * @return the value of bss_preview_item_data.content_type
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Integer getContentType() {
        return contentType;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.content_type
     *
     * @param contentType the value for bss_preview_item_data.content_type
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.content_id
     *
     * @return the value of bss_preview_item_data.content_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.content_id
     *
     * @param contentId the value for bss_preview_item_data.content_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.content_item_id
     *
     * @return the value of bss_preview_item_data.content_item_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Long getContentItemId() {
        return contentItemId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.content_item_id
     *
     * @param contentItemId the value for bss_preview_item_data.content_item_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setContentItemId(Long contentItemId) {
        this.contentItemId = contentItemId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.epg_ioid
     *
     * @return the value of bss_preview_item_data.epg_ioid
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Long getEpgIoid() {
        return epgIoid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.epg_ioid
     *
     * @param epgIoid the value for bss_preview_item_data.epg_ioid
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setEpgIoid(Long epgIoid) {
        this.epgIoid = epgIoid;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.epg_template_id
     *
     * @return the value of bss_preview_item_data.epg_template_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Long getEpgTemplateId() {
        return epgTemplateId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.epg_template_id
     *
     * @param epgTemplateId the value for bss_preview_item_data.epg_template_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setEpgTemplateId(Long epgTemplateId) {
        this.epgTemplateId = epgTemplateId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.epg_content_id
     *
     * @return the value of bss_preview_item_data.epg_content_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Long getEpgContentId() {
        return epgContentId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.epg_content_id
     *
     * @param epgContentId the value for bss_preview_item_data.epg_content_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setEpgContentId(Long epgContentId) {
        this.epgContentId = epgContentId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column bss_preview_item_data.epg_content_item_id
     *
     * @return the value of bss_preview_item_data.epg_content_item_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public Long getEpgContentItemId() {
        return epgContentItemId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column bss_preview_item_data.epg_content_item_id
     *
     * @param epgContentItemId the value for bss_preview_item_data.epg_content_item_id
     * @mbggenerated Fri May 23 11:11:43 CST 2014
     */
    public void setEpgContentItemId(Long epgContentItemId) {
        this.epgContentItemId = epgContentItemId;
    }

    public Long getIoid() {
        return ioid;
    }

    public void setIoid(Long ioid) {
        this.ioid = ioid;
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

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
}