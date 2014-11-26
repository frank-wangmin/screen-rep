package com.ysten.local.bss.bean.gd;

public class Movie {
	
	//接口中的唯一标识
	private String id = "";
	//操作类型  注册－REGIST 更新－UPDATE 删除－DELETE
	private String action = "";
	//全局唯一标识
	private String code = "";
	private String type = "";
	private String fileURL = "";
	private String sourceDRMType = "";
	private String destDRMType = "";
	private String audioType = "";
	private String screenFormat = "";
	private String closedCaptioning = "";
	private String OCSURL = "";
	private String duration = "";
	private String fileSize = "";
	private String bitRateType = "";
	private String videoType = "";
	private String audioFormat = "";
	private String resolution = "";
	private String videoProfile = "";
	private String systemLayer = "";
	private String serviceType = "";
	//0: 成功
	private String result = "";
	//错误描述
	private String errorDescription = "";
	
	public StringBuffer getMovie() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<Object ElementType=\"Movie\" ID=\""+id+"\" Action=\""+action+"\" Code=\""+code+"\">");
		sb.append("<Property Name=\"Type\">"+type+"</Property>");
		sb.append("<Property Name=\"FileURL\">"+fileURL+"</Property>");
		sb.append("<Property Name=\"SourceDRMType\">"+sourceDRMType+"</Property>");
		sb.append("<Property Name=\"DestDRMType\">"+destDRMType+"</Property>");
		sb.append("<Property Name=\"AudioType\">"+audioType+"</Property>");
		sb.append("<Property Name=\"ScreenFormat\">"+screenFormat+"</Property>");
		sb.append("<Property Name=\"ClosedCaptioning\">"+closedCaptioning+"</Property>");
		sb.append("<Property Name=\"OCSURL\">"+OCSURL+"</Property>");
		sb.append("<Property Name=\"Duration\">"+duration+"</Property>");
		sb.append("<Property Name=\"FileSize\">"+fileSize+"</Property>");
		sb.append("<Property Name=\"BitRateType\">"+bitRateType+"</Property>");
		sb.append("<Property Name=\"VideoType\">"+videoType+"</Property>");
		sb.append("<Property Name=\"AudioFormat\">"+audioFormat+"</Property>");
		sb.append("<Property Name=\"Resolution\">"+resolution+"</Property>");
		sb.append("<Property Name=\"VideoProfile\">"+videoProfile+"</Property>");
		sb.append("<Property Name=\"SystemLayer\">"+systemLayer+"</Property>");
		sb.append("<Property Name=\"ServiceType\">"+serviceType+"</Property>");
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getSourceDRMType() {
		return sourceDRMType;
	}

	public void setSourceDRMType(String sourceDRMType) {
		this.sourceDRMType = sourceDRMType;
	}

	public String getDestDRMType() {
		return destDRMType;
	}

	public void setDestDRMType(String destDRMType) {
		this.destDRMType = destDRMType;
	}

	public String getAudioType() {
		return audioType;
	}

	public void setAudioType(String audioType) {
		this.audioType = audioType;
	}

	public String getScreenFormat() {
		return screenFormat;
	}

	public void setScreenFormat(String screenFormat) {
		this.screenFormat = screenFormat;
	}

	public String getClosedCaptioning() {
		return closedCaptioning;
	}

	public void setClosedCaptioning(String closedCaptioning) {
		this.closedCaptioning = closedCaptioning;
	}

	public String getOCSURL() {
		return OCSURL;
	}

	public void setOCSURL(String oCSURL) {
		OCSURL = oCSURL;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getBitRateType() {
		return bitRateType;
	}

	public void setBitRateType(String bitRateType) {
		this.bitRateType = bitRateType;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getAudioFormat() {
		return audioFormat;
	}

	public void setAudioFormat(String audioFormat) {
		this.audioFormat = audioFormat;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getVideoProfile() {
		return videoProfile;
	}

	public void setVideoProfile(String videoProfile) {
		this.videoProfile = videoProfile;
	}

	public String getSystemLayer() {
		return systemLayer;
	}

	public void setSystemLayer(String systemLayer) {
		this.systemLayer = systemLayer;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
