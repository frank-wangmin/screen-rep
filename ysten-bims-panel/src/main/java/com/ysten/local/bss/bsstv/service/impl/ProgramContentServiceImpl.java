package com.ysten.local.bss.bsstv.service.impl;


import com.ysten.local.bss.bsstv.domain.RecCategory;
import com.ysten.local.bss.bsstv.domain.RecProgramContentOperate;
import com.ysten.local.bss.bsstv.repository.ICategoryRepository;
import com.ysten.local.bss.bsstv.repository.IProgramContentRepository;
import com.ysten.local.bss.bsstv.service.IProgramContentService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yaoqy on 14-10-15.
 */
@Service
public class ProgramContentServiceImpl implements IProgramContentService {
    @Autowired
    private IProgramContentRepository programContentRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Override
    public Pageable<RecProgramContentOperate> getProgramContentList(Long programId, String programName, Integer pageNo, Integer pageSize) {
      List<RecProgramContentOperate> list =  programContentRepository.findProgramContentList(programId,programName,pageNo,pageSize);
      int totalCount =  programContentRepository.getTotalCount(programId,programName);

        return new Pageable<RecProgramContentOperate>().instanceByPageNo(list, totalCount, pageNo, pageSize);
    }

    @Override
    public Pageable<RecCategory> findCategoryList(Long programId, String programName, Integer pageNo, Integer pageSize) {
        List<RecCategory> list =  categoryRepository.findCategoryList(programId, programName, pageNo, pageSize);
        int totalCount =  categoryRepository.getTotalCount(programId,programName);

        return new Pageable<RecCategory>().instanceByPageNo(list, totalCount, pageNo, pageSize);
    }
}
