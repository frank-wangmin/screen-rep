package com.ysten.local.bss.bean.gd;

public class Series {
	private String id = "";
	private String action = "";
	private String code = "";
	private String name = "";
	private String orderNumber = "";
	private String originalName = "";
	private String sortName = "";
	private String searchName = "";
	private String orgAirDate = "";
	private String licensingWindowStart = "";
	private String licensingWindowEnd = "";
	private String displayAsNew = "";
	private String displayAsLastChance = "";
	private String macrovision = "";
	private String price = "";
	private String volumnCount = "";
	private String status = "";
	private String description = "";
	private String type = "";
	private String keywords = "";
	private String tags = "";
	private String rMediaCode = "";
	private String result = "";
	private String errorDescription = "";

	public StringBuffer getSeries() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<Object ElementType=\"Series\" ID=\""+id+"\" Action=\""+action+"\" Code=\""+code+"\">");
		sb.append("<Property Name=\"Name\">"+name+"</Property>");
		sb.append("<Property Name=\"OrderNumber\">"+orderNumber+"</Property>");
		sb.append("<Property Name=\"OriginalName\">"+originalName+"</Property>");
		sb.append("<Property Name=\"SortName\">"+sortName+"</Property>");
		sb.append("<Property Name=\"SearchName\">"+searchName+"</Property>");
		sb.append("<Property Name=\"OrgAirDate\">"+orgAirDate+"</Property>");
		sb.append("<Property Name=\"LicensingWindowStart\">"+licensingWindowStart+"</Property>");
		sb.append("<Property Name=\"LicensingWindowEnd\">"+licensingWindowEnd+"</Property>");
		sb.append("<Property Name=\"DisplayAsNew\">"+displayAsNew+"</Property>");
		sb.append("<Property Name=\"DisplayAsLastChance\">"+displayAsLastChance+"</Property>");
		sb.append("<Property Name=\"Macrovision\">"+macrovision+"</Property>");
		sb.append("<Property Name=\"Price\">"+price+"</Property>");
		sb.append("<Property Name=\"VolumnCount\">"+volumnCount+"</Property>");
		sb.append("<Property Name=\"Status\">"+status+"</Property>");
		sb.append("<Property Name=\"Description\">"+description+"</Property>");
		sb.append("<Property Name=\"Type\">"+type+"</Property>");
		sb.append("<Property Name=\"Keywords\">"+keywords+"</Property>");
		sb.append("<Property Name=\"Tags\">"+tags+"</Property>");
		sb.append("<Property Name=\"RMediaCode\">"+rMediaCode+"</Property>");
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getOrgAirDate() {
		return orgAirDate;
	}

	public void setOrgAirDate(String orgAirDate) {
		this.orgAirDate = orgAirDate;
	}

	public String getLicensingWindowStart() {
		return licensingWindowStart;
	}

	public void setLicensingWindowStart(String licensingWindowStart) {
		this.licensingWindowStart = licensingWindowStart;
	}

	public String getLicensingWindowEnd() {
		return licensingWindowEnd;
	}

	public void setLicensingWindowEnd(String licensingWindowEnd) {
		this.licensingWindowEnd = licensingWindowEnd;
	}

	public String getDisplayAsNew() {
		return displayAsNew;
	}

	public void setDisplayAsNew(String displayAsNew) {
		this.displayAsNew = displayAsNew;
	}

	public String getDisplayAsLastChance() {
		return displayAsLastChance;
	}

	public void setDisplayAsLastChance(String displayAsLastChance) {
		this.displayAsLastChance = displayAsLastChance;
	}

	public String getMacrovision() {
		return macrovision;
	}

	public void setMacrovision(String macrovision) {
		this.macrovision = macrovision;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getVolumnCount() {
		return volumnCount;
	}

	public void setVolumnCount(String volumnCount) {
		this.volumnCount = volumnCount;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getrMediaCode() {
		return rMediaCode;
	}

	public void setrMediaCode(String rMediaCode) {
		this.rMediaCode = rMediaCode;
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
