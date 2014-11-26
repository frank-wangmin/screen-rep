package com.ysten.local.bss.bean.gd;

public class Picture {
	private String id = "";
	private String action = "";
	private String code = "";
	private String fileURL = "";
	private String description = "";
	private String result = "";
	private String errorDescription = "";
	
	public StringBuffer getPicture() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Object ElementType=\"Picture\" ID=\""+id+"\" Action=\""+action+"\" Code=\""+code+"\">");
		sb.append("<Property Name=\"FileURL\">"+fileURL+"</Property>");
		sb.append("<Property Name=\"Description\">"+description+"</Property>");
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

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
