package com.ysten.local.bss.cms.domain;

import java.io.Serializable;

/**
 * 节目码率媒体信息
 * 
 * @author qinxf
 * 
 */
public class ProgramBitrate implements Serializable {

	private static final long serialVersionUID = 1302852736873190508L;
	/**
	 * 节目码率媒体ID
	 */
	private Long programRateId;
	/**
	 * 码率ID
	 */
	private Integer bitrateId;
	/**
	 * 节目ID
	 */
	private Long programId;
	/**
	 * 源文件名称
	 */
	private String srcFileName;
	/**
	 * 源文件相对路径
	 */
	private String srcFilePath;
	/**
	 * 转码文件相对路径
	 */
	private String covertPath;
	/**
	 * 转码文件名称
	 */
	private String covertFileName;
	/**
	 * DRM文件相对路径
	 */
	private String drmPath;
	/**
	 * DRM文件名称
	 */
	private String drmFileName;
	/**
	 * MD5值
	 */
	private String md5;
	/**
	 * 转码状态
	 */
	private Integer convertStatus;
	/**
	 * DRM状态
	 */
	private Integer drmStatus;
	/**
	 * 是否需要DRM
	 */
	private Integer needDrm;
	/**
	 * UUID
	 */
	private String uuid;
	/**
	 * 码率媒体状态
	 */
	private Integer bitrateStatus;
	/**
	 * 文件格式
	 */
	private String fileType;
	/**
	 * 文件大小
	 */
	private Double fileSize;
	/**
	 * 第三方代码
	 */
	private String cpCode;
	/**
	 * 播放地址
	 */
	private String playUrl;
	/**
	 * 下载地址
	 */
	private String downUrl;
	/**
	 * 数据提供商
	 */
	private String dataProvider;
	/**
	 * 外部数据标识(Cis)
	 */
	private String outSourceId;
	/**
	 * 清晰度CODE
	 */
	private String definitionCode;
	/**
	 * 加密内容ID
	 */
	private Long contentId;

	public String getSrcFileName() {
		return srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public String getSrcFilePath() {
		return srcFilePath;
	}

	public void setSrcFilePath(String srcFilePath) {
		this.srcFilePath = srcFilePath;
	}

	public String getCovertFileName() {
		return covertFileName;
	}

	public void setCovertFileName(String covertFileName) {
		this.covertFileName = covertFileName;
	}

	public String getDrmFileName() {
		return drmFileName;
	}

	public void setDrmFileName(String drmFileName) {
		this.drmFileName = drmFileName;
	}

	public String getOutSourceId() {
		return outSourceId;
	}

	public void setOutSourceId(String outSourceId) {
		this.outSourceId = outSourceId;
	}

	public Long getProgramRateId() {
		return programRateId;
	}

	public void setProgramRateId(Long programRateId) {
		this.programRateId = programRateId;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public String getCovertPath() {
		return covertPath;
	}

	public void setCovertPath(String covertPath) {
		this.covertPath = covertPath;
	}

	public String getDrmPath() {
		return drmPath;
	}

	public void setDrmPath(String drmPath) {
		this.drmPath = drmPath;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Integer getConvertStatus() {
		return convertStatus;
	}

	public void setConvertStatus(Integer convertStatus) {
		this.convertStatus = convertStatus;
	}

	public Integer getDrmStatus() {
		return drmStatus;
	}

	public void setDrmStatus(Integer drmStatus) {
		this.drmStatus = drmStatus;
	}

	public Integer getNeedDrm() {
		return needDrm;
	}

	public void setNeedDrm(Integer needDrm) {
		this.needDrm = needDrm;
	}

	public String getUuid() {
		return uuid;
	}

	public String getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(String dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getBitrateStatus() {
		return bitrateStatus;
	}

	public void setBitrateStatus(Integer bitrateStatus) {
		this.bitrateStatus = bitrateStatus;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getBitrateId() {
		return bitrateId;
	}

	public void setBitrateId(Integer bitrateId) {
		this.bitrateId = bitrateId;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public String getCpCode() {
		return cpCode;
	}

	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getDefinitionCode() {
		return definitionCode;
	}

	public void setDefinitionCode(String definitionCode) {
		this.definitionCode = definitionCode;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

}