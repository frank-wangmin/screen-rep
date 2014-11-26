package com.ysten.local.bss.cms.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.cms.domain.ProgramSeriesRelation;

public interface ProgramSeriesRelationMapper {

	/**
	 * 根据节目集ID查询节目-节目集关系
	 * @param programSeriesId
	 * @return
	 */
	List<ProgramSeriesRelation> getByProgramSeriesId(@Param("programSeriesId") Long programSeriesId);

	/**
	 * 根据节目集ID和节目ID查询节目-节目集关系
	 * @param ProgramSeriesId
	 * @param PrgramId
	 * @return
	 */
	ProgramSeriesRelation getCanUseBySeriesIdAndProgramId(@Param("programSeriesId")Long ProgramSeriesId, @Param("prgramId")Long prgramId);
	
	/**
	 * 根据节目ID查询节目-节目集关系
	 * @param programId
	 * @return
	 */
	List<ProgramSeriesRelation> getCanUseByProgramId(@Param("programId")Long programId);
	
}