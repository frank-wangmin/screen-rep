package com.ysten.local.bss.cms.repository.mapper;

import org.apache.ibatis.annotations.Param;

public interface ProgramSeriesDynamicMapper {
    
    Float getScoreByProgramSeriesId(@Param("programSeriesId")Long programSeriesId);

}
