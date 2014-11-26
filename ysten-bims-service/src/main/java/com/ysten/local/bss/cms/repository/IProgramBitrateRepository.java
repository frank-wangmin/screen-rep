package com.ysten.local.bss.cms.repository;

import java.util.List;

import com.ysten.local.bss.cms.domain.ProgramBitrate;

public interface IProgramBitrateRepository {
	
    /**
     * 根据节目ID列表取媒体数据
     * 
     * @param programIds
     * @return
     */
	List<ProgramBitrate> getByProgramIds(List<Long> programIds);
	
	/**
	 * 根据节目ID列表取媒体数据
	 * @param programId
	 * @return
	 */
	List<ProgramBitrate> getByProgramId(Long programId);
	
    /**
     * 通过节目ID和码率ID查询
     * @param programId
     * @param bitrateId
     * @return
     */
	ProgramBitrate getByGroupId(Long programId, Integer bitrateId);
	
	/**
	 * 通过主键查询节目媒体码率
	 * @param id
	 * @return
	 */
	ProgramBitrate getById(Long id);
	
}