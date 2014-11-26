package com.ysten.local.bss.cms.repository.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.cms.domain.ProgramBitrate;
import com.ysten.local.bss.cms.repository.IProgramBitrateRepository;
import com.ysten.local.bss.cms.repository.mapper.ProgramBitrateMapper;

@Repository
public class ProgramBitrateRepositoryImpl implements IProgramBitrateRepository {

	@Autowired
	private ProgramBitrateMapper programBitrateMapper;

	@Override
	public List<ProgramBitrate> getByProgramIds(List<Long> programIds) {
		if(programIds == null || programIds.size() == 0){
			return Collections.<ProgramBitrate>emptyList();
		}else{
			List<ProgramBitrate> programBitrates=programBitrateMapper.getByProgramIds(programIds);
			return programBitrates==null ? Collections.<ProgramBitrate>emptyList() : programBitrates;
		}
	}

	@Override
	public ProgramBitrate getByGroupId(Long programId, Integer bitrateId) {
		return programBitrateMapper.getByGroupId(programId, bitrateId);
	}

	@Override
	public ProgramBitrate getById(Long id) {
		return programBitrateMapper.getById(id);
	}

	@Override
	public List<ProgramBitrate> getByProgramId(Long programId) {
		return programBitrateMapper.getByProgramId(programId);
	}

}
