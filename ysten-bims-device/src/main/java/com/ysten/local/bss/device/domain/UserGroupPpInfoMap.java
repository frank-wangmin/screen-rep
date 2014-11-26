package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户组与产品包关系表
 * @author HanksXu
 *
 */
public class UserGroupPpInfoMap implements Serializable {
	
	private static final long serialVersionUID = -3870491371661511524L;
	
	private Long id;
    private Long userGroupId;
//	private ProductType productType;
	private String productId;//pp编码
	private Date createDate;
	private Date updateDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}
//	public ProductType getProductType() {
//		return productType;
//	}
//	public void setProductType(ProductType productType) {
//		this.productType = productType;
//	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	
}
