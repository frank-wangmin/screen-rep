package com.ysten.local.bss.panel.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by frank on 14-5-9.
 */
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = -7830051843104833941L;

    private Long id;

    //操作人
    private Long oprUserId;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getOprUserId() {
        return oprUserId;
    }

    public void setOprUserId(Long oprUserId) {
        this.oprUserId = oprUserId;
    }
}
