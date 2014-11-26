package com.ysten.local.bss.bean.gd;

public class PhysicalChannel {
	private String id = "";
	private String action = "";
	private String code = "";
	private String channelID = "";
	private String channelCode = "";
	private String bitRateType = "";
	private String multiCastIP = "";
	private String multiCastPort = "";
	private String bitrateCount = "";
	private String result = "";
	private String errorDescription = "";
	
	public StringBuffer getPhysicalChannel() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Object ElementType=\"PhysicalChannel\" ID=\""+id+"\" Action=\""+action+"\" Code=\""+code+"\">");
		sb.append("<Property Name=\"ChannelID\">"+channelID+"</Property>");
		sb.append("<Property Name=\"ChannelCode\">"+channelCode+"</Property>");
		sb.append("<Property Name=\"BitRateType\">"+bitRateType+"</Property>");
		sb.append("<Property Name=\"MultiCastIP\">"+multiCastIP+"</Property>");
		sb.append("<Property Name=\"MultiCastPort\">"+multiCastPort+"</Property>");
		sb.append("<Property Name=\"BitrateCount\">"+bitrateCount+"</Property>");
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

	public String getBitRateType() {
		return bitRateType;
	}

	public void setBitRateType(String bitRateType) {
		this.bitRateType = bitRateType;
	}

	public String getMultiCastIP() {
		return multiCastIP;
	}

	public void setMultiCastIP(String multiCastIP) {
		this.multiCastIP = multiCastIP;
	}

	public String getMultiCastPort() {
		return multiCastPort;
	}

	public void setMultiCastPort(String multiCastPort) {
		this.multiCastPort = multiCastPort;
	}

	public String getBitrateCount() {
		return bitrateCount;
	}

	public void setBitrateCount(String bitrateCount) {
		this.bitrateCount = bitrateCount;
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
