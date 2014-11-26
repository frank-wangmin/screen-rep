package com.ysten.local.bss.cms.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.cms.domain.CatgSeries;
import com.ysten.local.bss.cms.repository.ICatgSeriesRepository;
import com.ysten.local.bss.cms.repository.mapper.CatgSeriesMapper;

@Repository
public class CatgSeriesRepositoryImpl implements ICatgSeriesRepository {

	@Autowired
	private CatgSeriesMapper catgSeriesMapper;
	
	@Override
	public List<CatgSeries> findCatrSeriesByProgramSeriesId(Long programSeriesId) {
		return catgSeriesMapper.findCatrSeriesByProgramSeriesId(programSeriesId);
	}

}
