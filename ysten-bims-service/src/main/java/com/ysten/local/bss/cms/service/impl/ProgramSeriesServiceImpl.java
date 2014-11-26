package com.ysten.local.bss.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.cms.domain.ProgramSeries;
import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramContentType;
import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramSerialStatus;
import com.ysten.local.bss.cms.repository.IProgramSeriesRepository;
import com.ysten.local.bss.cms.service.IProgramSeriesService;

@Service
public class ProgramSeriesServiceImpl implements IProgramSeriesService{
	
	@Autowired
	private IProgramSeriesRepository programSerivesRepository;
	
	@Override
	public List<ProgramSeries> findProgramSeriesByIdsAndStatus(List<Long> programSeriesIds, EProgramSerialStatus status, EProgramContentType programContentType ) {
		return programSerivesRepository.findProgramSeriesByIds(programSeriesIds, status, programContentType);
	}
	
	@Override
	public List<ProgramSeries> findProgramSerialByCondition(EProgramSerialStatus status, EProgramContentType type, String cpCode, Integer offsetNumber, Integer pageNumber) {
		return programSerivesRepository.findProgramSerialByCondition(status, type, cpCode, offsetNumber, pageNumber);
	}
	
}
