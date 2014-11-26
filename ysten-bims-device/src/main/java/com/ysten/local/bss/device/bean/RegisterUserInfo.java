package com.ysten.local.bss.device.bean;

public class RegisterUserInfo{
	
	private String userId;
	private String oldUserId;
	private String password;
	private String cNTVUserId;
	private String productlist;
	private String timestamp;
	private String sN;
	private String iDNumber;
	private String phone;
	private String sex;
	private String address;
	private String postalCode;
	private String email;
	private String areacode;
	private String bandWidth;
	private String updateTime;
	private String terminalModel;
	private String replacement;
	private String dateCreated;
	private String dateActivated;
	private String dateSuspended;
	private String datecancelled;
	//whether replace.
	private String approve;
	private String status;
	
	public String getCNTVUserId() {
		return cNTVUserId;
	}

	public void setCNTVUserId(String cNTVUserId) {
		this.cNTVUserId = cNTVUserId;
	}
	
	public String getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	public String getIDNumber() {
		return iDNumber;
	}

	public void setIDNumber(String iDNumber) {
		this.iDNumber = iDNumber;
	}

	public RegisterUserInfo() {}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProductlist() {
		return productlist;
	}
	public void setProductlist(String productlist) {
		this.productlist = productlist;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getsN() {
		return sN;
	}
	public void setsN(String sN) {
		this.sN = sN;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getBandWidth() {
		return bandWidth;
	}
	public void setBandWidth(String bandWidth) {
		this.bandWidth = bandWidth;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getTerminalModel() {
		return terminalModel;
	}
	public void setTerminalModel(String terminalModel) {
		this.terminalModel = terminalModel;
	}
	public String getReplacement() {
		return replacement;
	}
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDateActivated() {
		return dateActivated;
	}
	public void setDateActivated(String dateActivated) {
		this.dateActivated = dateActivated;
	}
	public String getDateSuspended() {
		return dateSuspended;
	}
	public void setDateSuspended(String dateSuspended) {
		this.dateSuspended = dateSuspended;
	}
	public String getDatecancelled() {
		return datecancelled;
	}
	public void setDatecancelled(String datecancelled) {
		this.datecancelled = datecancelled;
	}
	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
