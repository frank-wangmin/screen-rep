package com.ysten.local.bss.cms.domain;

import java.util.Date;

public class CatgItem {
	private Long catgItemId;
	private Long fid;
	private Long grade;
	private Long type;
	private String name;
	private String FDNCode;
	private String catgAlias;
	private Long parentCatgId;
	private int memberCatg;
	private String actionTypeId;
	private String catgTypeId;
	private String subStyleId;
	private int catgNumber;
	private String actionLink;
	private String innerPic;
	private String outPic;
	private String ppvCode;
	private String status;
	private Date createTime;
	private String createUser;
	private Date lastModifyDate;
	private String lastModifyUser;
	private String catgDesc;
	public Long getCatgItemId() {
		return catgItemId;
	}
	public void setCatgItemId(Long catgItemId) {
		this.catgItemId = catgItemId;
	}
	public Long getFid() {
		return fid;
	}
	public void setFid(Long fid) {
		this.fid = fid;
	}
	public Long getGrade() {
		return grade;
	}
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFDNCode() {
		return FDNCode;
	}
	public void setFDNCode(String fDNCode) {
		FDNCode = fDNCode;
	}
	public String getCatgAlias() {
		return catgAlias;
	}
	public void setCatgAlias(String catgAlias) {
		this.catgAlias = catgAlias;
	}
	public Long getParentCatgId() {
		return parentCatgId;
	}
	public void setParentCatgId(Long parentCatgId) {
		this.parentCatgId = parentCatgId;
	}
	public int getMemberCatg() {
		return memberCatg;
	}
	public void setMemberCatg(int memberCatg) {
		this.memberCatg = memberCatg;
	}
	public String getActionTypeId() {
		return actionTypeId;
	}
	public void setActionTypeId(String actionTypeId) {
		this.actionTypeId = actionTypeId;
	}
	public String getCatgTypeId() {
		return catgTypeId;
	}
	public void setCatgTypeId(String catgTypeId) {
		this.catgTypeId = catgTypeId;
	}
	public String getSubStyleId() {
		return subStyleId;
	}
	public void setSubStyleId(String subStyleId) {
		this.subStyleId = subStyleId;
	}
	public int getCatgNumber() {
		return catgNumber;
	}
	public void setCatgNumber(int catgNumber) {
		this.catgNumber = catgNumber;
	}
	public String getActionLink() {
		return actionLink;
	}
	public void setActionLink(String actionLink) {
		this.actionLink = actionLink;
	}
	public String getInnerPic() {
		return innerPic;
	}
	public void setInnerPic(String innerPic) {
		this.innerPic = innerPic;
	}
	public String getOutPic() {
		return outPic;
	}
	public void setOutPic(String outPic) {
		this.outPic = outPic;
	}
	public String getPpvCode() {
		return ppvCode;
	}
	public void setPpvCode(String ppvCode) {
		this.ppvCode = ppvCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getLastModifyUser() {
		return lastModifyUser;
	}
	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}
	public String getCatgDesc() {
		return catgDesc;
	}
	public void setCatgDesc(String catgDesc) {
		this.catgDesc = catgDesc;
	}
	
}
