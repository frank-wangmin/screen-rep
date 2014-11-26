package com.ysten.local.bss.panel.vo;

import java.io.Serializable;

/**
 * 面板模块查询条件
 * Created by frank on 14-9-1.
 */
public class PanelQueryCriteria implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7415044482197257232L;
    private Long Id;

    private String name;

    private String title;

    private Long epgId;

    private String dataScope;

    private Integer start;

    private Integer limit;

    private Integer navType;

    private String actionType;

    public Integer getNavType() {
        return navType;
    }

    public void setNavType(Integer navType) {
        this.navType = navType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getEpgId() {
        return epgId;
    }

    public void setEpgId(Long epgId) {
        this.epgId = epgId;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "Id:"+this.getId()+"name:" + this.getName() + ";title:" + this.getTitle()+";epgId:" + this.getEpgId() + ";dataScope:" + this.getDataScope() +
                "pageNo:" + this.getStart() + ";pageSize:" + this.getLimit();
    }
}
