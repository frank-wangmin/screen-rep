package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.Status;
import com.ysten.local.bss.util.bean.Required;
import com.ysten.utils.bean.IEnumDisplay;
/**
 * 
 * @author cwang
 * 应用升级软件号
 */
public class AppSoftwareCode implements Serializable {

    private static final long serialVersionUID = 338762038339090918L;
    //主键id
    @Required
    private Long id;
    //名称
    @Required
    private String name;
    //编号
    @Required
    private String code;
    //状态
    @Required
    private Status status;
    
    //创建时间
    @Required
    private Date createDate;
    //描述
    private String description;
    
    private Date lastModifyTime;
    @Required
    private String operUser;

    @Required
    private DistributeState distributeState = DistributeState.UNDISTRIBUTE;
    
    public DistributeState getDistributeState() {
		return distributeState;
	}

	public void setDistributeState(DistributeState distributeState) {
		this.distributeState = distributeState;
	}

	public Long getId() {
        return id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public enum State implements IEnumDisplay {
        USABLE("可用"), UNUSABLE("不可用");

        private String msg;

        private State(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

    }

}
