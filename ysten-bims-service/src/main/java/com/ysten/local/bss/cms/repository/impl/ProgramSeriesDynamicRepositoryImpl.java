package com.ysten.local.bss.cms.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.cms.repository.IProgramSeriesDynamicRepository;
import com.ysten.local.bss.cms.repository.mapper.ProgramSeriesDynamicMapper;

@Repository
public class ProgramSeriesDynamicRepositoryImpl implements IProgramSeriesDynamicRepository {

    @Autowired
    private ProgramSeriesDynamicMapper programSeriesDynamicMapper;
    
    @Override
    public Float getScoreByProgramSeriesId(Long programSeriesId) {
        return programSeriesDynamicMapper.getScoreByProgramSeriesId(programSeriesId);
    }

}
