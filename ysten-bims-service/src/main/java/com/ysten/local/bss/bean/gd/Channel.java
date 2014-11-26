package com.ysten.local.bss.bean.gd;

public class Channel {
	private String id = "";
	private String action = "";
	private String code = "";
	private String channelNumber = "";
	private String name = "";
	private String callSign = "";
	private String timeShift = "";
	private String storageDuration = "";
	private String timeShiftDuration = "";
	private String description = "";
	private String country = "";
	private String state = "";
	private String city = "";
	private String zipCode = "";
	private String type = "";
	private String subType = "";
	private String language = "";
	private String status = "";
	private String startTime = "";
	private String endTime = "";
	private String macrovision = "";
	private String videoType = "";
	private String audioType = "";
	private String streamType = "";
	private String bilingual = "";
	private String URL = "";
	private String result = "";
	private String errorDescription = "";
	
	public StringBuffer getChannel() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Object ElementType=\"Channel\" ID=\""+id+"\" Action=\""+action+"\" Code=\""+code+"\">");
		sb.append("<Property Name=\"ChannelNumber\">"+channelNumber+"</Property>");
		sb.append("<Property Name=\"Name\">"+name+"</Property>");
		sb.append("<Property Name=\"CallSign\">"+callSign+"</Property>");
		sb.append("<Property Name=\"TimeShift\">"+timeShift+"</Property>");
		sb.append("<Property Name=\"StorageDuration\">"+storageDuration+"</Property>");
		sb.append("<Property Name=\"TimeShiftDuration\">"+timeShiftDuration+"</Property>");
		sb.append("<Property Name=\"Description\">"+description+"</Property>");
		sb.append("<Property Name=\"Country\">"+country+"</Property>");
		sb.append("<Property Name=\"State\">"+state+"</Property>");
		sb.append("<Property Name=\"City\">"+city+"</Property>");
		sb.append("<Property Name=\"ZipCode\">"+zipCode+"</Property>");
		sb.append("<Property Name=\"Type\">"+type+"</Property>");
		sb.append("<Property Name=\"SubType\">"+subType+"</Property>");
		sb.append("<Property Name=\"Language\">"+language+"</Property>");
		sb.append("<Property Name=\"Status\">"+status+"</Property>");
		sb.append("<Property Name=\"StartTime\">"+startTime+"</Property>");
		sb.append("<Property Name=\"EndTime\">"+endTime+"</Property>");
		sb.append("<Property Name=\"Macrovision\">"+macrovision+"</Property>");
		sb.append("<Property Name=\"VideoType\">"+videoType+"</Property>");
		sb.append("<Property Name=\"AudioType\">"+audioType+"</Property>");
		sb.append("<Property Name=\"StreamType\">"+streamType+"</Property>");
		sb.append("<Property Name=\"Bilingual\">"+bilingual+"</Property>");
		sb.append("<Property Name=\"URL\">"+URL+"</Property>");
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

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCallSign() {
		return callSign;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	public String getTimeShift() {
		return timeShift;
	}

	public void setTimeShift(String timeShift) {
		this.timeShift = timeShift;
	}

	public String getStorageDuration() {
		return storageDuration;
	}

	public void setStorageDuration(String storageDuration) {
		this.storageDuration = storageDuration;
	}

	public String getTimeShiftDuration() {
		return timeShiftDuration;
	}

	public void setTimeShiftDuration(String timeShiftDuration) {
		this.timeShiftDuration = timeShiftDuration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMacrovision() {
		return macrovision;
	}

	public void setMacrovision(String macrovision) {
		this.macrovision = macrovision;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getAudioType() {
		return audioType;
	}

	public void setAudioType(String audioType) {
		this.audioType = audioType;
	}

	public String getStreamType() {
		return streamType;
	}

	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}

	public String getBilingual() {
		return bilingual;
	}

	public void setBilingual(String bilingual) {
		this.bilingual = bilingual;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
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
