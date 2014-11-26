package com.ysten.local.bss.cms.service;

import java.util.List;

import com.ysten.local.bss.cms.domain.ProgramSeries;
import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramContentType;
import com.ysten.local.bss.cms.domain.ProgramSeries.EProgramSerialStatus;

public interface IProgramSeriesService {
	/**
     * 通过id集合和状态查询节目集集合
     * @param programSeriesIds 节目集id集合
     * @return List<ProgramSerial>
     */
    List<ProgramSeries> findProgramSeriesByIdsAndStatus(List<Long> programSeriesIds, EProgramSerialStatus status,EProgramContentType programContentType); 
    
    /**
     * 在CMS库中，查询可以推送的数据
     * @param status 节目集状态
     * @param type 内容的类型
     * @param cpCode 内容提供商名称
     * @return
     */
    List<ProgramSeries> findProgramSerialByCondition(EProgramSerialStatus status, EProgramContentType type, String cpCode, Integer offsetNumber, Integer pageNumber);
}
