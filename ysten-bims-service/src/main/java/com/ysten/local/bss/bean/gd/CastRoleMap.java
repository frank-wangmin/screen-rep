package com.ysten.local.bss.bean.gd;

public class CastRoleMap {
	private String id = "";
	private String action = "";
	private String code = "";
	private String castRole = "";
	private String castID = "";
	private String castCode = "";
	private String result = "";
	private String errorDescription = "";
	
	public StringBuffer getCastRoleMap() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Object ElementType=\"CastRoleMap\" ID=\""+id+"\" Action=\""+action+"\" Code=\""+code+"\">");
		sb.append("<Property Name=\"CastRole\">"+castRole+"</Property>");
		sb.append("<Property Name=\"CastID\">"+castID+"</Property>");
		sb.append("<Property Name=\"CastCode\">"+castCode+"</Property>");
		sb.append("<Property Name=\"Result\">"+result+"</Property>");
		sb.append("<Property Name=\"ErrorDescription\">"+errorDescription+"</Property>");
		sb.append("</Object>");
		return sb;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCastRole() {
		return castRole;
	}

	public void setCastRole(String castRole) {
		this.castRole = castRole;
	}

	public String getCastID() {
		return castID;
	}

	public void setCastID(String castID) {
		this.castID = castID;
	}

	public String getCastCode() {
		return castCode;
	}

	public void setCastCode(String castCode) {
		this.castCode = castCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
}
