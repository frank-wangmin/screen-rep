package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.local.bss.device.utils.EnumConstantsInterface.UserGroupType;

/**
 * 类名称：UserGroup 类描述：用户组信息
 */
public class UserGroup implements java.io.Serializable {

    private static final long serialVersionUID = 6389856626502070998L;
    private Long id;
    private String name;
    //分组的类型 按业务功能分为升级用户组、PANEL用户组、开机动画用户组、通知用户组等
    private UserGroupType type;
    //是否是动态分组标识
    private Long dynamicFlag;
    //sql表达式
    private String sqlExpression;
    private String description;
    private Date createDate;
    private Date updateDate;
    private Long areaId;
    private String areaName;
    //	//存放UserGroupPpInfoMap中的产品包类型
//	private ProductType productType;
    //存放UserGroupPpInfoMap中的产品包编码
    private String productId;
    private String userId;

    public UserGroup() {
    }

    public UserGroup(Long id, String name, Date createDate, String description, UserGroupType type, Long dynamicFlag, String sqlExpression) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.description = description;
        this.type = type;
        this.dynamicFlag = dynamicFlag;
        this.sqlExpression = sqlExpression;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserGroupType getType() {
        return type;
    }

    public void setType(UserGroupType type) {
        this.type = type;
    }

    public Long getDynamicFlag() {
        return dynamicFlag;
    }

    public void setDynamicFlag(Long dynamicFlag) {
        this.dynamicFlag = dynamicFlag;
    }

    public String getSqlExpression() {
        return sqlExpression;
    }

    public void setSqlExpression(String sqlExpression) {
        this.sqlExpression = sqlExpression;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    //	public ProductType getProductType() {
//		return productType;
//	}
//
//	public void setProductType(ProductType productType) {
//		this.productType = productType;
//	}

}