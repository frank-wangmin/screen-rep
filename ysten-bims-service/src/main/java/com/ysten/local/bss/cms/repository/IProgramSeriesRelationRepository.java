package com.ysten.local.bss.cms.repository;

import java.util.List;

import com.ysten.local.bss.cms.domain.ProgramSeriesRelation;

public interface IProgramSeriesRelationRepository {
	
	/**
	 * 根据节目集ID查询节目-节目集关系
	 * @param programSeriesId
	 * @return
	 */
	List<ProgramSeriesRelation> getCanUseByProgramSeriesId(Long programSeriesId);
	
	/**
	 * 根据节目集ID和节目ID查询节目-节目集关系
	 * @param ProgramSeriesId
	 * @param PrgramId
	 * @return
	 */
	ProgramSeriesRelation getCanUseBySeriesIdAndProgramId(Long programSeriesId, Long programId);
	
	/**
	 * 根据节目ID查询节目-节目集关系
	 * @param programId
	 * @return
	 */
	List<ProgramSeriesRelation> getCanUseByProgramId(Long programId);
}