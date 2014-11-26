package com.ysten.local.bss.cms.repository;

import java.util.List;

import com.ysten.local.bss.cms.domain.CatgSeries;

public interface ICatgSeriesRepository {

	List<CatgSeries> findCatrSeriesByProgramSeriesId(Long programSeriesId);
	
}
