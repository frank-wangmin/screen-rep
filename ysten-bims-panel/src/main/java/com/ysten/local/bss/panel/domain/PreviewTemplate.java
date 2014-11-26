package com.ysten.local.bss.panel.domain;

import com.ysten.local.bss.util.bean.Required;

/**
 * Created by frank on 14-5-16.
 */
public class PreviewTemplate extends BaseDomain {

    private static final long serialVersionUID = -7551994206618408673L;

    private Long id;

    private String name;

    // 描述
    private String description;

    // 模板类型
    private Integer type;

    private String districtCode;

    // 平台id
    private Long platformId;

    // 是否包含节目集属性
    private Integer containsPs;

    // epg同步过来的Id,适配用
    private Long templateId;

    // epg_template_id
    private Long epgTemplateId;

    private Integer templateType;

    // epg
    private Integer containPs;

    public Long getId() {
        return id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public Integer getContainsPs() {
        return containsPs;
    }

    public void setContainsPs(Integer containsPs) {
        this.containsPs = containsPs;
    }

    public Long getEpgTemplateId() {
        return epgTemplateId;
    }

    public void setEpgTemplateId(Long epgTemplateId) {
        this.epgTemplateId = epgTemplateId;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Integer getContainPs() {
        return containPs;
    }

    public void setContainPs(Integer containPs) {
        this.containPs = containPs;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
}
