package com.ysten.local.bss.bsstv.service;

import com.ysten.local.bss.bsstv.domain.RecCategory;
import com.ysten.local.bss.bsstv.domain.RecProgramContentOperate;
import com.ysten.utils.page.Pageable;

/**
 * Created by yaoqy on 14-10-15.
 */
public interface IProgramContentService {

    Pageable<RecProgramContentOperate> getProgramContentList(Long programId,String programName,Integer pageNo,Integer pageSize);

    Pageable<RecCategory> findCategoryList(Long programId, String programName, Integer pageNo, Integer pageSize);
}
