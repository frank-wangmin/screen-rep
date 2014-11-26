package com.ysten.local.bss.cms.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.cms.domain.ProgramBitrate;

public interface ProgramBitrateMapper {

    /**
     * 根据节目ID列表取媒体数据
     * 
     * @param programIds
     * @return
     */
    List<ProgramBitrate> getByProgramIds(@Param("programIds") List<Long> programIds);
    
    /**
     * 根据节目ID和码率ID查询节目媒体
     * @param programId
     * @param bitrateId
     * @return
     */
    ProgramBitrate getByGroupId(@Param("programId")Long programId, @Param("bitrateId")Integer bitrateId);
    
    /**
     * 通过主键查询节目媒体码率
     * @param id
     * @return
     */
    ProgramBitrate getById(@Param("id")Long id);
    
    /**
     * 根据节目ID列表取媒体数据
     * @param programId
     * @return
     */
    List<ProgramBitrate> getByProgramId(@Param("programId")Long programId);
    
}