package com.ysten.local.bss.bsstv.repository.impl;

import com.ysten.local.bss.bsstv.domain.RecProgramContentOperate;
import com.ysten.local.bss.bsstv.repository.IProgramContentRepository;
import com.ysten.local.bss.bsstv.repository.mapper.RecProgramContentOperateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yaoqy on 14-10-15.
 */
@Repository
public class ProgramContentRepository implements IProgramContentRepository {

    @Autowired
    private RecProgramContentOperateMapper programContentOperateMapper;
    @Override
    public List<RecProgramContentOperate> findProgramContentList(Long programId, String programName, Integer pageNo, Integer pageSize) {
         return programContentOperateMapper.findProgramContentList(programId, programName, pageNo, pageSize);
//        return  null;
    }

    @Override
    public int getTotalCount(Long programId, String programName) {
        return programContentOperateMapper.getTotalCount(programId,programName);
//        return 0;
    }
}
