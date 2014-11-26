package com.ysten.local.bss.bsstv.repository.impl;

import com.ysten.local.bss.bsstv.domain.RecCategory;
import com.ysten.local.bss.bsstv.repository.ICategoryRepository;
import com.ysten.local.bss.bsstv.repository.mapper.RecCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yaoqy on 14-10-15.
 */
@Repository
public class CategoryRepository implements ICategoryRepository {
    @Autowired
    private RecCategoryMapper categoryMapper;
    @Override
    public List<RecCategory> findCategoryList(Long programId, String programName, Integer pageNo, Integer pageSize) {
        return categoryMapper.findCategoryList(programId,programName,pageNo,pageSize);
//        return null;
    }

    @Override
    public int getTotalCount(Long programId, String programName) {
        return categoryMapper.getTotalCount(programId,programName);
//        return 0;
    }
}
