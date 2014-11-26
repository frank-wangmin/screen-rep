package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.local.bss.util.bean.Required;
import com.ysten.utils.bean.IEnumDisplay;

public class ProductPackageInfo implements java.io.Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1704661251998111629L;
//	@Required
    private String productId;
//	@Required
    private String productName;
//	@Required
    private ProductType productType;
//	@Required
    private Date createDate;
//	@Required
    private Date validDate;
//	@Required
    private Date expireDate;
//	@Required
    private String source;

    
    public ProductPackageInfo() {
		super();
	}
	public ProductPackageInfo(String productId,String productName,ProductType productType,Date createDate,Date validDate,Date expireDate,String source){
    	this.productId = productId;
    	this.productName = productName;
        this.productType = productType;
        this.createDate = createDate;
        this.validDate = validDate;
        this.expireDate = expireDate;
        this.source = source;
    }
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public enum ProductType implements IEnumDisplay{
		BASIC("基础包"), SUBJOIN("增值月包"), SINGLE("单片包"); // , SUBJECT("专题包")
        private String msg;
        private ProductType(String msg){
            this.msg = msg;
        }
        @Override
        public String getDisplayName() {
            return this.msg;
        }
    }

}