package com.ysten.local.bss.cms.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.cms.repository.IProgramSeriesExtRepository;
import com.ysten.local.bss.cms.repository.mapper.ProgramSeriesExtMapper;

@Repository
public class ProgramSeriesExtRepositoryImpl implements IProgramSeriesExtRepository{

    @Autowired
    private ProgramSeriesExtMapper ProgramSeriesExtMapper;
    
    public Float getScoreByProgramSeriesId(Long programSeriesId){
        return ProgramSeriesExtMapper.getScoreByProgramSeriesId(programSeriesId);
    }
    
}
