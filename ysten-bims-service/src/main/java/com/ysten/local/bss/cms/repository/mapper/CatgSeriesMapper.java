package com.ysten.local.bss.cms.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ysten.local.bss.cms.domain.CatgSeries;

public interface CatgSeriesMapper {

	List<CatgSeries> findCatrSeriesByProgramSeriesId(@Param("programSeriesId")Long programSeriesId);
}
