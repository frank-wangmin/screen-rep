package com.ysten.local.bss.bean.gd;

public class Progra {
	
	//接口中的唯一标识
	private String id = "";
	//操作类型  注册－REGIST 更新－UPDATE 删除－DELETE
	private String action = "";
	//全局唯一标识
	private String code = "";
	//节目名称
	private String name = "";
	//节目订购编号
	private String orderNumber = "";
	//原名
	private String originalName = "";
	//索引名称供界面排序
	private String sortName = "";
	//搜索名称供界面搜索
	private String searchName = "";
	//Program的默认类别（Genre）
	private String genre = "Genre";
	//演员列表(只供显示)
	private String actorDisplay = "";
	//作者列表(只供显示)
	private String writerDisplay = "";
	//国家地区
	private String originalCountry = "";
	//语言
	private String language = "";
	//上映年份(YYYY)
	private String releaseYear = "";
	//首播日期(YYYYMMDD)
	private String orgAirDate = "";
	//有效开始时间(YYYYMMDDHH24MiSS)
	private String licensingWindowStart = "";
	//有效结束时间(YYYYMMDDHH24MiSS)
	private String licensingWindowEnd = "";
	//新到天数
	private String displayAsNew = "";
	//剩余天数
	private String displayAsLastChance = "";
	//拷贝保护标志 0:无拷贝保护 1:有拷贝保护
	private String macrovision = "";
	//节目描述
	private String description = "";
	//列表定价
	private String priceTaxIn = "";
	//状态标志 0:失效 1:生效
	private String status = "";
	//1: VOD 5: Advertisement
	private String sourceType = "";
	//0: 普通VOD 1: 连续剧剧集
	private String seriesFlag = "";
	//节目内容类型
	private String type = "";
	//关键字 多个关键字之间使用分号分隔
	private String keywords = "";
	//关联标签
	private String tags = "";
	//关联内容唯一标识
	private String rmediaCode = "";
	//存储分发策略要求：
	private String storageType = "";
	//0: 成功
	private String result = "";
	//错误描述
	private String errorDescription = "";
	
	public StringBuffer getProgram() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<Object ElementType=\"Program\" ID=\""+id+"\" Action=\""+action+"\" Code=\""+code+"\">");
		sb.append("<Property Name=\"Name\">"+name+"</Property>");
		sb.append("<Property Name=\"OrderNumber\">"+orderNumber+"</Property>");
		sb.append("<Property Name=\"OriginalName\">"+originalName+"</Property>");
		sb.append("<Property Name=\"SortName\">"+sortName+"</Property>");
		sb.append("<Property Name=\"SearchName\">"+searchName+"</Property>");
		sb.append("<Property Name=\"Genre\">"+genre+"</Property>");
		sb.append("<Property Name=\"ActorDisplay\">"+actorDisplay+"</Property>");
		sb.append("<Property Name=\"WriterDisplay\">"+writerDisplay+"</Property>");
		sb.append("<Property Name=\"OriginalCountry\">"+originalCountry+"</Property>");
		sb.append("<Property Name=\"Language\">"+language+"</Property>");
		sb.append("<Property Name=\"ReleaseYear\">"+releaseYear+"</Property>");
		sb.append("<Property Name=\"OrgAirDate\">"+orgAirDate+"</Property>");
		sb.append("<Property Name=\"LicensingWindowStart\">"+licensingWindowStart+"</Property>");
		sb.append("<Property Name=\"LicensingWindowEnd\">"+licensingWindowEnd+"</Property>");
		sb.append("<Property Name=\"DisplayAsNew\">"+displayAsNew+"</Property>");
		sb.append("<Property Name=\"DisplayAsLastChance\">"+displayAsLastChance+"</Property>");
		sb.append("<Property Name=\"Macrovision\">"+macrovision+"</Property>");
		sb.append("<Property Name=\"Description\">"+description+"</Property>");
		sb.append("<Property Name=\"PriceTaxIn\">"+priceTaxIn+"</Property>");
		sb.append("<Property Name=\"Status\">"+status+"</Property>");
		sb.append("<Property Name=\"SourceType\">"+sourceType+"</Property>");
		sb.append("<Property Name=\"SeriesFlag\">"+seriesFlag+"</Property>");
		sb.append("<Property Name=\"Type\">"+type+"</Property>");
		sb.append("<Property Name=\"Keywords\">"+keywords+"</Property>");
		sb.append("<Property Name=\"Tags\">"+tags+"</Property>");
		sb.append("<Property Name=\"StorageType\">"+storageType+"</Property>");
		sb.append("<Property Name=\"RMediaCode\">"+rmediaCode+"</Property>");
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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getActorDisplay() {
		return actorDisplay;
	}

	public void setActorDisplay(String actorDisplay) {
		this.actorDisplay = actorDisplay;
	}

	public String getWriterDisplay() {
		return writerDisplay;
	}

	public void setWriterDisplay(String writerDisplay) {
		this.writerDisplay = writerDisplay;
	}

	public String getOriginalCountry() {
		return originalCountry;
	}

	public void setOriginalCountry(String originalCountry) {
		this.originalCountry = originalCountry;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriceTaxIn() {
		return priceTaxIn;
	}

	public void setPriceTaxIn(String priceTaxIn) {
		this.priceTaxIn = priceTaxIn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSeriesFlag() {
		return seriesFlag;
	}

	public void setSeriesFlag(String seriesFlag) {
		this.seriesFlag = seriesFlag;
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

	public String getRmediaCode() {
		return rmediaCode;
	}

	public void setRmediaCode(String rmediaCode) {
		this.rmediaCode = rmediaCode;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
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
