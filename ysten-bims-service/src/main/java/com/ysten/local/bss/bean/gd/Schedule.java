package com.ysten.local.bss.bean.gd;

public class Schedule {
	private String id = "";
	private String action = "";
	private String code = "";
	private String channelID = "";
	private String channelCode = "";
	private String programName = "";
	private String searchName = "";
	private String genre = "";
	private String sourceType = "";
	private String startDate = "";
	private String startTime = "";
	private String duration = "";
	private String status = "";
	private String description = "";
	private String objectType = "";
	private String objectCode = "";
	private String result = "";
	private String errorDescription = "";
	
	public StringBuffer getSchedule() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Object ElementType=\"Schedule\" ID=\""+id+"\" Action=\""+action+"\" Code=\""+code+"\">");
		sb.append("<Property Name=\"ChannelID\">"+channelID+"</Property>");
		sb.append("<Property Name=\"ChannelCode\">"+channelCode+"</Property>");
		sb.append("<Property Name=\"ProgramName\">"+programName+"</Property>");
		sb.append("<Property Name=\"SearchName\">"+searchName+"</Property>");
		sb.append("<Property Name=\"Genre\">"+genre+"</Property>");
		sb.append("<Property Name=\"SourceType\">"+sourceType+"</Property>");
		sb.append("<Property Name=\"StartDate\">"+startDate+"</Property>");
		sb.append("<Property Name=\"StartTime\">"+startTime+"</Property>");
		sb.append("<Property Name=\"Duration\">"+duration+"</Property>");
		sb.append("<Property Name=\"Status\">"+status+"</Property>");
		sb.append("<Property Name=\"Description\">"+description+"</Property>");
		sb.append("<Property Name=\"ObjectType\">"+objectType+"</Property>");
		sb.append("<Property Name=\"ObjectCode\">"+objectCode+"</Property>");
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

	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
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
