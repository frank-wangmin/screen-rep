package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.local.bss.talk.domain.TalkSystem;
import com.ysten.utils.page.Pageable;

public interface ITalkSystemWebService {
    /**
     * 分页检索接口交互系统信息
     * 
     * @param pageNo
     *            当前页号
     * @param pageSize
     *            每页大小
     * @return
     */
    Pageable<TalkSystem> findAllTalkSystem(int pageNo, int pageSize);
    /**
     * 新增交互系统
     * @param code
     * @param name
     * @param description
     * @return
     */
    boolean saveTalkSystem(String code,String name,String description);
    /**
     * 修改交互系统
     * @param id
     * @param code
     * @param name
     * @param description
     * @return
     */
    boolean updateTalkSystem(String id,String name,String code,String description);
    /**
     * 通过id查询交互系统信息
     * @param id
     * @return
     */
    TalkSystem getTalkSystemById(Long id);
	List<TalkSystem> getAllTalkSystem();
}
