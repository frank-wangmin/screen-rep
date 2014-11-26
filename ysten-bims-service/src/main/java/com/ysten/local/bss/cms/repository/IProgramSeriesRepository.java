package com.ysten.local.bss.cms.repository;

import java.util.Date;
import java.util.List;

import com.ysten.local.bss.cms.domain.ProgramSeries;
import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramContentType;
import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramSerialStatus;

public interface IProgramSeriesRepository {

	List<ProgramSeries> findProgramSeriesByStatus(String status);
	
	List<ProgramSeries> findProgramSeriesByPSId(Long programSeriesId);
	
	/**
	 * 通过节目集id和节目集状态查询节目集信息
	 * @param programSeriesIds
	 * @param status
	 * @return
	 */
	List<ProgramSeries> findProgramSeriesByIds(List<Long> programSeriesIds, EProgramSerialStatus status, EProgramContentType programContentType);
	
    /**
     * 查询可以推送的数据
     * @param status 节目集状态
     * @param type 内容的类型
     * @param cpCode 内容提供商名称
     * @return
     */
    List<ProgramSeries> findProgramSerialByCondition(EProgramSerialStatus status, EProgramContentType type, String cpCode, Integer offsetNumber, Integer pageIndex);
    
	/**
	 * 根据节目集ID查询节目集
	 * @param programSeriesIds
	 * @return
	 */
    List<ProgramSeries> getProgramSeriesHasNoMcvdByIds(List<Long> programSeriesIds);
    
    /**
     * 根据节目集ID查询内容提供商是CMS的、去掉微视频的节目集
     * @param ProgramSeriesId
     * @return
     */
    ProgramSeries getProgramSeriesById(Long ProgramSeriesId);

    /**
     * 条件查询cms中产品内容信息
     * @param contentName
     * @param s_date
     * @param e_date
     * @param statusList
     * @return
     */
	List<ProgramSeries> findProgramSeriesByConditions(String contentName, Date s_date, Date e_date,
			List<EProgramSerialStatus> statusList,String cpCode);
	
	List<ProgramSeries> findProgramSeriesByPage(Integer limit, Integer skip, String isFull);
}
