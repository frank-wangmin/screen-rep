package com.ysten.local.bss.cms.repository.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.cms.domain.ProgramSeries;
import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramContentType;
import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramSerialStatus;

public interface ProgramSeriesMapper {

	public List<ProgramSeries> findProgramSeriesByStatus(@Param("status")String status);
	
	List<ProgramSeries> findProgramSeriesByPSId(@Param("programSeriesId")Long programSeriesId);
	
	/**
	 * 根据节目集ID、节目集状态、节目集内容类型查询节目集
	 * @param ids
	 * @param status
	 * @param programContentType
	 * @return
	 */
	List<ProgramSeries> findByIds(@Param("programSeriesIds") List<Long> programSeriesIds, @Param("status")EProgramSerialStatus status,@Param("programContentType")EProgramContentType programContentType);

    /**
     * 查询可以推送的数据
     * @param status 节目集状态
     * @param type 内容的类型
     * @param cpCode 内容提供商名称
     * @return
     */
	List<ProgramSeries> findProgramSerialByCondition(@Param("status")EProgramSerialStatus status, @Param("type")EProgramContentType type, @Param("cpCode")String cpCode, @Param("offsetNumber")Integer offsetNumber, @Param("pageNumber")Integer pageNumber);
	
	/**
	 * 根据节目集ID查询节目集
	 * @param programSeriesIds
	 * @return
	 */
	List<ProgramSeries> getProgramSeriesHasNoMcvdByIds(@Param("programSeriesIds")List<Long> programSeriesIds);
	
    /**
     * 根据节目集ID查询内容提供商是CMS的、去掉微视频的节目集
     * @param ProgramSeriesId
     * @return
     */
	ProgramSeries getProgramSeriesById(@Param("ProgramSeriesId")Long ProgramSeriesId);

	/**
	 * 条件查询产品内容信息
	 * @param contentName
	 * @param s_date
	 * @param e_date
	 * @param status
	 * @return
	 */
	public List<ProgramSeries> findProgramSeriesByConditions(@Param("contentName")String contentName, @Param("start")Date start, @Param("end")Date end,
			@Param("stateList")List<EProgramSerialStatus> status,@Param("cpCode")String cpCode);
	
	List<ProgramSeries> findProgramSeriesByPage(@Param("limit")Integer limit, @Param("skip")Integer skip, @Param("isFull")String isFull);
}