package com.ysten.local.bss.system.domain;

import java.io.Serializable;
import java.util.Date;

public class SysVersion implements Serializable {


    private static final long serialVersionUID = -1019511244006627118L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 版本ID
     */
    private String versionId;

    /**
     * 名称
     */
    private String name;

   /**
    * 版本内容
    */
    private String content;
    /**
     * 创建时间
     */
    private Date createDate;

    public SysVersion()
    {}

    public SysVersion(Long id, String versionId, String name, String content, Date createDate)
    {
    	this.id = id;
    	this.versionId = versionId;
    	this.name = name;
    	this.content = content;
    	this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}