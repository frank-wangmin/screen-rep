package com.ysten.local.bss.cms.repository.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramContentType;
import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramSerialStatus;
import com.ysten.local.bss.cms.domain.ProgramSeries;
import com.ysten.local.bss.cms.repository.IProgramSeriesRepository;
import com.ysten.local.bss.cms.repository.mapper.ProgramSeriesMapper;

@Repository
public class ProgramSeriesRepository implements IProgramSeriesRepository {

	@Autowired
	private ProgramSeriesMapper programSeriesMapper;
	
	@Override
	public List<ProgramSeries> findProgramSeriesByStatus(String status) {
		return programSeriesMapper.findProgramSeriesByStatus(status);
	}

	@Override
	public List<ProgramSeries> findProgramSeriesByPSId(Long programSeriesId) {
		return programSeriesMapper.findProgramSeriesByPSId(programSeriesId);
	}

	@Override
	public List<ProgramSeries> findProgramSeriesByIds(List<Long> programSeriesIds, EProgramSerialStatus status, EProgramContentType programContentType) {
		List<ProgramSeries> programSeries=programSeriesMapper.findByIds(programSeriesIds, status ,programContentType);
		return programSeries==null ? Collections.<ProgramSeries>emptyList() : programSeries;
	}

	@Override
	public List<ProgramSeries> findProgramSerialByCondition(EProgramSerialStatus status, EProgramContentType type, String cpCode, Integer offsetNumber, Integer pageNumber) {
		List<ProgramSeries> programSeries=programSeriesMapper.findProgramSerialByCondition(status, type, cpCode, offsetNumber, pageNumber);
		return programSeries==null ? Collections.<ProgramSeries>emptyList() : programSeries;
	}

	@Override
	public List<ProgramSeries> getProgramSeriesHasNoMcvdByIds(List<Long> programSeriesIds) {
		if(programSeriesIds != null && programSeriesIds.size() > 0){
			List<ProgramSeries> programSeries=programSeriesMapper.getProgramSeriesHasNoMcvdByIds(programSeriesIds);
			return programSeries==null ? Collections.<ProgramSeries>emptyList() : programSeries;
		}else{
			return Collections.<ProgramSeries>emptyList();
		}
	}

	@Override
	public ProgramSeries getProgramSeriesById(Long ProgramSeriesId) {
		return programSeriesMapper.getProgramSeriesById(ProgramSeriesId);
	}

	@Override
	public List<ProgramSeries> findProgramSeriesByConditions(String contentName, Date s_date, Date e_date,
			List<EProgramSerialStatus> status,String cpCode) {
		List<ProgramSeries> psList = programSeriesMapper.findProgramSeriesByConditions(contentName,s_date,e_date,status,cpCode);
		return psList;
	}

    @Override
    public List<ProgramSeries> findProgramSeriesByPage(Integer limit, Integer skip, String isFull) {
        return programSeriesMapper.findProgramSeriesByPage(limit, skip, isFull);
    }
}
