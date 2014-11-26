package com.ysten.local.bss.cms.domain;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONObject;

import com.ysten.local.bss.util.JsonUtil;
import com.ysten.utils.bean.IEnumDisplay;

/**
 * 节目集
 * 
 * @author qinxf
 * 
 */
public class ProgramSeries implements Serializable {
	
    public static final String CONTENT_TYPE_MIC_VIDEO = "micVideo";
    
	private static final long serialVersionUID = -871312757251548165L;
	/**
	 * 节目集标识
	 */
	private Long programSeriesId;
	/**
	 * 节目集名称
	 */
	private String programSeriesName;
	/**
	 * 拼音
	 */
	private String programPinyin;
	/**
	 * 节目集别名
	 */
	private String programSeriesAlias;
	/**
	 * 英文名称
	 */
	private String programSeriesEnName;
	/**
	 * 节目集小海报
	 */
	private String smallPosterAddr;
	/**
	 * 节目集默认海报
	 */
	private String poster;
	/**
	 * 节目集大海报
	 */
	private String bigPosterAddr;
	/**
	 * 节目集简介
	 */
	private String programSeriesDesc;
	/**
	 * 节目集类型
	 */
	private Integer programTypeId;
	/**
	 * 节目集小分类
	 */
	private String programClass;

	/**
	 * 节目集下节目数量
	 */
	private Integer programCount;
	/**
	 * 节目总数量
	 */
	private Integer programTotalCount;
	/**
	 * 节目集内容类型 video gudiVideo micVideo
	 */
	private String programContentType;
	/**
	 * 节目集标签
	 */
	private String tag;
	/**
	 * 清晰度ID
	 */
	private String definitionCode;
	/**
	 * 导演
	 */
	private String director;
	/**
	 * 主演
	 */
	private String leadingRole;
	/**
	 * 主演拼音
	 */
	private String leadingRolePinyin;
	/**
	 * 编剧
	 */
	private String scriptWriter;
	/**
	 * 年代
	 */
	private String years;
	/**
	 * 语言
	 */
	private Integer languageId;
	/**
	 * 赛事
	 */
	private String competition;
	/**
	 * 受众
	 */
	private String targetAudience;
	/**
	 * 首播时间
	 */
	private Date premiereDate;
	/**
	 * 发行时间
	 */
	private Date publishDate;
	/**
	 * 首播频道
	 */
	private String premiereChannel;
	/**
	 * 同步直播频道
	 */
	private String syncLiveChannel;
	/**
	 * 节目集下节目排序
	 */
	private String sortType;
	/**
	 * 节目集状态
	 */
	private String status;
	/**
	 * 状态更新时间
	 */
	private Date statusTime;
	/**
	 * 最后一次更新时间
	 */
	private Date lastModifyDate;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 是否3D
	 */
	private Integer is3d;
	/**
	 * 时长(分钟)
	 */
	private Integer timeLength;
	/**
	 * 是否会员片
	 */
	private Integer isCustomer;
	/**
	 * 推荐星级
	 */
	private Integer starRating;
	/**
	 * 蒙版描述
	 */
	private String maskDescription;
	/**
	 * 数据提供商
	 */
	private String dataProvider;
	/**
	 * 外部数据标识(cis)
	 */
	private String outSourceId;
	/**
	 * 区域
	 */
	private String zone;
	/**
	 * 厂商code
	 */
	private String cpCodes;
	/**
	 * 版权开始日期
	 */
	private Date copyrightStartDate;
	/**
	 * 版权结束日期
	 */
	private Date copyrightEndDate;
	/**
	 * 版权分级ID
	 */
	private Integer crClassId;
	/**
	 * 版权信息ID
	 */
	private Integer copyrightId;
	/**
	 * 价格标签
	 */
	private String ppvCode;
	/**
	 * 下线原因
	 */
	private String offReason;
	/**
	 * 重新上线原因
	 */
	private String reOnReason;
	/**
	 * 删除原因
	 */
	private String delReason;
	/**
	 * 回退原因
	 */
	private String backReason;
	/**
	 * 发布平台IDS
	 */
	private String publishPlatformIds;

	public ProgramSeries(){}
	
	public ProgramSeries(JSONObject jsonObject){
	    programSeriesId = JsonUtil.jsonToBeanField(jsonObject.getString("programSeriesId"), Long.class);
	    programSeriesName = JsonUtil.jsonToBeanField(jsonObject.getString("programSeriesName"), String.class);
	    programPinyin = JsonUtil.jsonToBeanField(jsonObject.getString("programPinyin"), String.class);
	    programSeriesAlias =JsonUtil.jsonToBeanField(jsonObject.getString("programSeriesAlias"), String.class); 
	    programSeriesEnName =JsonUtil.jsonToBeanField(jsonObject.getString("programSeriesEnName"), String.class); 
	    smallPosterAddr =JsonUtil.jsonToBeanField(jsonObject.getString("smallPosterAddr"), String.class); 
	    poster =JsonUtil.jsonToBeanField(jsonObject.getString("poster"), String.class); 
	    bigPosterAddr =JsonUtil.jsonToBeanField(jsonObject.getString("bigPosterAddr"), String.class); 
	    programSeriesDesc =JsonUtil.jsonToBeanField(jsonObject.getString("programSeriesDesc"), String.class); 
	    programTypeId =JsonUtil.jsonToBeanField(jsonObject.getString("programTypeId"), Integer.class); 
	    programClass =JsonUtil.jsonToBeanField(jsonObject.getString("programClass"), String.class);
	    programCount =JsonUtil.jsonToBeanField(jsonObject.getString("programCount"), Integer.class); 
	    programTotalCount =JsonUtil.jsonToBeanField(jsonObject.getString("programTotalCount"), Integer.class); 
	    programContentType =JsonUtil.jsonToBeanField(jsonObject.getString("programContentType"), String.class); 
	    tag =JsonUtil.jsonToBeanField(jsonObject.getString("tag"), String.class); 
	    definitionCode =JsonUtil.jsonToBeanField(jsonObject.getString("definitionCode"), String.class); 
	    director =JsonUtil.jsonToBeanField(jsonObject.getString("director"), String.class); 
	    leadingRole =JsonUtil.jsonToBeanField(jsonObject.getString("leadingRole"), String.class); 
	    leadingRolePinyin =JsonUtil.jsonToBeanField(jsonObject.getString("leadingRolePinyin"), String.class); 
	    scriptWriter =JsonUtil.jsonToBeanField(jsonObject.getString("scriptWriter"), String.class); 
	    years =JsonUtil.jsonToBeanField(jsonObject.getString("years"), String.class); 
	    languageId =JsonUtil.jsonToBeanField(jsonObject.getString("languageId"), Integer.class); 
	    competition =JsonUtil.jsonToBeanField(jsonObject.getString("competition"), String.class); 
	    targetAudience =JsonUtil.jsonToBeanField(jsonObject.getString("targetAudience"), String.class);  
	    premiereDate =JsonUtil.jsonToBeanField(jsonObject.getString("premiereDate"), Date.class); 
	    publishDate =JsonUtil.jsonToBeanField(jsonObject.getString("publishDate"), Date.class); 
	    premiereChannel =JsonUtil.jsonToBeanField(jsonObject.getString("premiereChannel"), String.class); 
	    syncLiveChannel =JsonUtil.jsonToBeanField(jsonObject.getString("syncLiveChannel"), String.class); 
	    sortType =JsonUtil.jsonToBeanField(jsonObject.getString("sortType"), String.class); 
	    status =JsonUtil.jsonToBeanField(jsonObject.getString("status"), String.class); 
	    statusTime =JsonUtil.jsonToBeanField(jsonObject.getString("statusTime"), Date.class); 
	    lastModifyDate =JsonUtil.jsonToBeanField(jsonObject.getString("lastModifyDate"), Date.class); 
	    createDate =JsonUtil.jsonToBeanField(jsonObject.getString("createDate"), Date.class); 
	    is3d =JsonUtil.jsonToBeanField(jsonObject.getString("is3d"), Integer.class); 
	    timeLength =JsonUtil.jsonToBeanField(jsonObject.getString("timeLength"), Integer.class); 
	    isCustomer =JsonUtil.jsonToBeanField(jsonObject.getString("isCustomer"), Integer.class); 
	    starRating =JsonUtil.jsonToBeanField(jsonObject.getString("starRating"), Integer.class); 
	    maskDescription =JsonUtil.jsonToBeanField(jsonObject.getString("maskDescription"), String.class); 
	    dataProvider =JsonUtil.jsonToBeanField(jsonObject.getString("dataProvider"), String.class); 
	    outSourceId =JsonUtil.jsonToBeanField(jsonObject.getString("outSourceId"), String.class); 
	    zone =JsonUtil.jsonToBeanField(jsonObject.getString("zone"), String.class); 
	    cpCodes =JsonUtil.jsonToBeanField(jsonObject.getString("cpCodes"), String.class); 
	    copyrightStartDate =JsonUtil.jsonToBeanField(jsonObject.getString("copyrightStartDate"), Date.class); 
	    copyrightEndDate =JsonUtil.jsonToBeanField(jsonObject.getString("copyrightEndDate"), Date.class); 
	    crClassId =JsonUtil.jsonToBeanField(jsonObject.getString("crClassId"), Integer.class); 
	    copyrightId =JsonUtil.jsonToBeanField(jsonObject.getString("copyrightId"), Integer.class); 
	    ppvCode =JsonUtil.jsonToBeanField(jsonObject.getString("ppvCode"), String.class); 
	    offReason =JsonUtil.jsonToBeanField(jsonObject.getString("offReason"), String.class); 
	    reOnReason =JsonUtil.jsonToBeanField(jsonObject.getString("reOnReason"), String.class); 
	    delReason =JsonUtil.jsonToBeanField(jsonObject.getString("delReason"), String.class); 
	    backReason =JsonUtil.jsonToBeanField(jsonObject.getString("backReason"), String.class); 
	    publishPlatformIds =JsonUtil.jsonToBeanField(jsonObject.getString("publishPlatformIds"), String.class); 
//	    finishPlatformIds =JsonUtil.jsonToBeanFiled(jsonObject.getString("finishPlatformIds"), String.class); 
//	    cpProducer =JsonUtil.jsonToBeanFiled(jsonObject.getString("cpProducer"), String.class); 
//	    extInfo =JsonUtil.jsonToBeanFiled(jsonObject.getString("extInfo"), String.class); 
//	    updateFlag =JsonUtil.jsonToBeanFiled(jsonObject.getString("updateFlag"), String.class); 
//	    cpId =JsonUtil.jsonToBeanFiled(jsonObject.getString("cpId"), String.class); 
//	    filmCpId =JsonUtil.jsonToBeanFiled(jsonObject.getString("filmCpId"), String.class); 
//	    subCaption =JsonUtil.jsonToBeanFiled(jsonObject.getString("subCaption"), String.class); 
//	    producer =JsonUtil.jsonToBeanFiled(jsonObject.getString("producer"), String.class); 
//	    character =JsonUtil.jsonToBeanFiled(jsonObject.getString("character"), String.class);
//	    subject =JsonUtil.jsonToBeanFiled(jsonObject.getString("subject"), String.class); 
//	    hours =JsonUtil.jsonToBeanFiled(jsonObject.getString("hours"), String.class);
//	    programNo =JsonUtil.jsonToBeanFiled(jsonObject.getString("programNo"), String.class);
//	    publisher =JsonUtil.jsonToBeanFiled(jsonObject.getString("publisher"), String.class);
//	    style =JsonUtil.jsonToBeanFiled(jsonObject.getString("style"), String.class); 
//	    content =JsonUtil.jsonToBeanFiled(jsonObject.getString("content"), String.class); 
//	    presenter =JsonUtil.jsonToBeanFiled(jsonObject.getString("presenter"), String.class); 
//	    guests =JsonUtil.jsonToBeanFiled(jsonObject.getString("guests"), String.class); 
//	    contentDate =JsonUtil.jsonToBeanFiled(jsonObject.getString("contentDate"), String.class); 
//	    defaultDefinition =JsonUtil.jsonToBeanFiled(jsonObject.getString("defaultDefinition"), String.class); 
//	    operateTime =JsonUtil.jsonToBeanFiled(jsonObject.getString("operateTime"), String.class); 
//	    operateType =JsonUtil.jsonToBeanFiled(jsonObject.getString("operateType"), String.class); 
//	    firstOnlineTime =JsonUtil.jsonToBeanFiled(jsonObject.getString("firstOnlineTime"), String.class); 
//	    playSort =JsonUtil.jsonToBeanFiled(jsonObject.getString("playSort"), String.class); 
//	    isCdn =JsonUtil.jsonToBeanFiled(jsonObject.getString("isCdn"), String.class); 
//	    specialInfo =JsonUtil.jsonToBeanFiled(jsonObject.getString("specialInfo"), String.class); 
//	    isCpDelete =JsonUtil.jsonToBeanFiled(jsonObject.getString("isCpDelete"), String.class); 
//	    cpModifyTime =JsonUtil.jsonToBeanFiled(jsonObject.getString("cpModifyTime"), String.class); 
	}
	
	public Integer getProgramTypeId() {
		return programTypeId;
	}

	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}

	public String getTargetAudience() {
		return targetAudience;
	}

	public void setTargetAudience(String targetAudience) {
		this.targetAudience = targetAudience;
	}

	public void setProgramTypeId(Integer programTypeId) {
		this.programTypeId = programTypeId;
	}

	public Long getProgramSeriesId() {
		return programSeriesId;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getPublishPlatformIds() {
		return publishPlatformIds;
	}

	public void setPublishPlatformIds(String publishPlatformIds) {
		this.publishPlatformIds = publishPlatformIds;
	}

	public String getProgramContentType() {
		return programContentType;
	}

	public String getOffReason() {
		return offReason;
	}

	public void setOffReason(String offReason) {
		this.offReason = offReason;
	}

	public String getReOnReason() {
		return reOnReason;
	}

	public void setReOnReason(String reOnReason) {
		this.reOnReason = reOnReason;
	}

	public String getDelReason() {
		return delReason;
	}

	public void setDelReason(String delReason) {
		this.delReason = delReason;
	}

	public void setProgramContentType(String programContentType) {
		this.programContentType = programContentType;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setProgramSeriesId(Long programSeriesId) {
		this.programSeriesId = programSeriesId;
	}

	public String getProgramSeriesName() {
		return programSeriesName;
	}

	public void setProgramSeriesName(String programSeriesName) {
		this.programSeriesName = programSeriesName;
	}

	public String getProgramPinyin() {
		return programPinyin;
	}

	public void setProgramPinyin(String programPinyin) {
		this.programPinyin = programPinyin;
	}

	public String getProgramSeriesAlias() {
		return programSeriesAlias;
	}

	public void setProgramSeriesAlias(String programSeriesAlias) {
		this.programSeriesAlias = programSeriesAlias;
	}

	public String getProgramSeriesEnName() {
		return programSeriesEnName;
	}

	public void setProgramSeriesEnName(String programSeriesEnName) {
		this.programSeriesEnName = programSeriesEnName;
	}

	public String getSmallPosterAddr() {
		return smallPosterAddr;
	}

	public void setSmallPosterAddr(String smallPosterAddr) {
		this.smallPosterAddr = smallPosterAddr;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getBigPosterAddr() {
		return bigPosterAddr;
	}

	public void setBigPosterAddr(String bigPosterAddr) {
		this.bigPosterAddr = bigPosterAddr;
	}

	public String getProgramSeriesDesc() {
		return programSeriesDesc;
	}

	public void setProgramSeriesDesc(String programSeriesDesc) {
		this.programSeriesDesc = programSeriesDesc;
	}

	public String getProgramClass() {
		return programClass;
	}

	public void setProgramClass(String programClass) {
		this.programClass = programClass;
	}

	public Integer getProgramCount() {
		return programCount;
	}

	public void setProgramCount(Integer programCount) {
		this.programCount = programCount;
	}

	public Integer getProgramTotalCount() {
		return programTotalCount;
	}

	public void setProgramTotalCount(Integer programTotalCount) {
		this.programTotalCount = programTotalCount;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDefinitionCode() {
		return definitionCode;
	}

	public void setDefinitionCode(String definitionCode) {
		this.definitionCode = definitionCode;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getLeadingRole() {
		return leadingRole;
	}

	public void setLeadingRole(String leadingRole) {
		this.leadingRole = leadingRole;
	}

	public String getLeadingRolePinyin() {
		return leadingRolePinyin;
	}

	public void setLeadingRolePinyin(String leadingRolePinyin) {
		this.leadingRolePinyin = leadingRolePinyin;
	}

	public String getScriptWriter() {
		return scriptWriter;
	}

	public void setScriptWriter(String scriptWriter) {
		this.scriptWriter = scriptWriter;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Date getPublishDate() {
		if (publishDate == null) {
			return null;
		}
		return (Date)publishDate.clone();
	}

	public void setPublishDate(Date publishDate) {
		if (publishDate == null) {
			this.publishDate = null;
		} else {
			this.publishDate = (Date) publishDate.clone();
		}
	}

	public String getPremiereChannel() {
		return premiereChannel;
	}

	public void setPremiereChannel(String premiereChannel) {
		this.premiereChannel = premiereChannel;
	}

	public String getSyncLiveChannel() {
		return syncLiveChannel;
	}

	public void setSyncLiveChannel(String syncLiveChannel) {
		this.syncLiveChannel = syncLiveChannel;
	}

	public Date getStatusTime() {
		if (statusTime == null) {
			return null;
		}
		return (Date)statusTime.clone();
	}

	public void setStatusTime(Date statusTime) {
		if (statusTime == null) {
			this.statusTime = null;
		} else {
			this.statusTime = (Date) statusTime.clone();
		}
	}

	public Date getLastModifyDate() {
		if (lastModifyDate == null) {
			return null;
		}
		return (Date)lastModifyDate.clone();
	}

	public void setLastModifyDate(Date lastModifyDate) {
		if (lastModifyDate == null) {
			this.lastModifyDate = null;
		} else {
			this.lastModifyDate = (Date) lastModifyDate.clone();
		}
	}

	public Date getCreateDate() {
		if (createDate == null) {
			return null;
		}
		return (Date)createDate.clone();
	}

	public void setCreateDate(Date createDate) {
		if (createDate == null) {
			this.createDate = null;
		} else {
			this.createDate = (Date) createDate.clone();
		}
	}

	public Integer getIs3d() {
		return is3d;
	}

	public void setIs3d(Integer is3d) {
		this.is3d = is3d;
	}

	public Integer getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}

	public String getMaskDescription() {
		return maskDescription;
	}

	public void setMaskDescription(String maskDescription) {
		this.maskDescription = maskDescription;
	}

	public String getOutSourceId() {
		return outSourceId;
	}

	public void setOutSourceId(String outSourceId) {
		this.outSourceId = outSourceId;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCpCodes() {
		return cpCodes;
	}

	public void setCpCodes(String cpCodes) {
		this.cpCodes = cpCodes;
	}

	public Date getCopyrightStartDate() {
		if (copyrightStartDate == null) {
			return null;
		}
		return (Date)copyrightStartDate.clone();
	}

	public void setCopyrightStartDate(Date copyrightStartDate) {
		if (copyrightStartDate == null) {
			this.copyrightStartDate = null;
		} else {
			this.copyrightStartDate = (Date) copyrightStartDate.clone();
		}
	}

	public Date getCopyrightEndDate() {
		if (copyrightEndDate == null) {
			return null;
		}
		return (Date)copyrightEndDate.clone();
	}

	public void setCopyrightEndDate(Date copyrightEndDate) {
		if (copyrightEndDate == null) {
			this.copyrightEndDate = null;
		} else {
			this.copyrightEndDate = (Date) copyrightEndDate.clone();
		}
	}

	public Date getPremiereDate() {
		if (premiereDate == null) {
			return null;
		}
		return (Date)premiereDate.clone();
	}

	public void setPremiereDate(Date premiereDate) {
		if (premiereDate == null) {
			this.premiereDate = null;
		} else {
			this.premiereDate = (Date) premiereDate.clone();
		}
	}

	public Integer getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(Integer isCustomer) {
		this.isCustomer = isCustomer;
	}

	public Integer getStarRating() {
		return starRating;
	}

	public void setStarRating(Integer starRating) {
		this.starRating = starRating;
	}

	public Integer getCrClassId() {
		return crClassId;
	}

	public void setCrClassId(Integer crClassId) {
		this.crClassId = crClassId;
	}

	public Integer getCopyrightId() {
		return copyrightId;
	}

	public void setCopyrightId(Integer copyrightId) {
		this.copyrightId = copyrightId;
	}

	public String getPpvCode() {
		return ppvCode;
	}

	public String getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(String dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void setPpvCode(String ppvCode) {
		this.ppvCode = ppvCode;
	}
	public enum EProgramSerialStatus implements IEnumDisplay {
		HIDDEN("未启用"), EDIT("编辑"),  CHECKUP("待审核"), TOPREONLINE("待预发布"), 
		PREONLINE("已预发布"), ONLINE("已上线"),  PREOFFLINE("预下线"), 
		OFFLINE("已下线"), CHECKUPNOPASS("初审未通过"), CHECKUPFINALNOPASS("终审未通过"),  
		DELETED("已删除"), MERGED("已被合并");
	    private String message;

	    private EProgramSerialStatus(String message) {
	        this.message = message;
	    }

	    @Override
	    public String getDisplayName() {
	        return this.message;
	    }
	}
	public enum EProgramContentType {
	    video("GetMoiveDetail", "OpenMedia"), micVideo("GetMicVideosList", "OpenMedia"), guidVideo("GetMoiveDetail",
	            "OpenBxMedia"), ystVideo("GetMoiveDetail", "OpenYstMedia"), openUrl("OpenUrl", "OpenUrl");
	    private String actionType;
	    private String mediaType;

	    private EProgramContentType(String actionType, String mediaType) {
	        this.actionType = actionType;
	        this.mediaType = mediaType;
	    }

	    public String getActionType() {
	        return actionType;
	    }

	    public String getMediaType() {
	        return mediaType;
	    }
	}
	
}
