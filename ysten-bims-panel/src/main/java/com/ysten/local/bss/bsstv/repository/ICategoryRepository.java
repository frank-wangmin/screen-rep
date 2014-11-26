package com.ysten.local.bss.bsstv.repository;

import com.ysten.local.bss.bsstv.domain.RecCategory;

import java.util.List;

/**
 * Created by yaoqy on 14-10-15.
 */
public interface ICategoryRepository {

    List<RecCategory> findCategoryList(Long programId,String programName,Integer pageNo,Integer pageSize);
    int getTotalCount(Long programId,String programName);
}
