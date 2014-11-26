package com.ysten.local.bss.cms.repository.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.cms.domain.ProgramSeriesRelation;
import com.ysten.local.bss.cms.repository.IProgramSeriesRelationRepository;
import com.ysten.local.bss.cms.repository.mapper.ProgramSeriesRelationMapper;

@Repository
public class ProgramSeriesRelationRepositoryImpl implements IProgramSeriesRelationRepository {

	@Autowired
	private ProgramSeriesRelationMapper programSeriesRelationMapper;

	@Override
	public List<ProgramSeriesRelation> getCanUseByProgramSeriesId(Long programSeriesId) {
		List<ProgramSeriesRelation> programSeriesRels=programSeriesRelationMapper.getByProgramSeriesId(programSeriesId);
		return programSeriesRels==null ? Collections.<ProgramSeriesRelation>emptyList() : programSeriesRels;
	}

	@Override
	public ProgramSeriesRelation getCanUseBySeriesIdAndProgramId(Long programSeriesId, Long PrgramId) {
		return programSeriesRelationMapper.getCanUseBySeriesIdAndProgramId(programSeriesId, PrgramId);
	}

	@Override
	public List<ProgramSeriesRelation> getCanUseByProgramId(Long programId) {
		List<ProgramSeriesRelation> programSeriesRels = programSeriesRelationMapper.getCanUseByProgramId(programId);
		return programSeriesRels==null ? Collections.<ProgramSeriesRelation>emptyList() : programSeriesRels;
	}

}
