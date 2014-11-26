package com.ysten.local.bss.device.domain;

import java.util.Date;

public class UserPackageInfoModified implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1520067702948589806L;
	
	private Long id;
	private String customerCode;
	private String productName;
	private String productId;
	private String outterCode;
	private String productType;
	private Date createDate;
	private String contentName;
	private int buyNum;
	private Date startDate;
	private Date endDate;

	
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getOutterCode() {
		return outterCode;
	}
	public void setOutterCode(String outterCode) {
		this.outterCode = outterCode;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
