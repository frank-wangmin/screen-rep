package com.ysten.local.bss.talk.repository;

import java.util.List;

import com.ysten.local.bss.talk.domain.TalkSystem;
import com.ysten.utils.page.Pageable;

public interface ITalkSystemRepository {
	
	List<TalkSystem> getAllTalkSystem();
    /**
     * 通过编号查询
     * @param code
     * @return
     */
    List<TalkSystem> findTalkSystemByCode(String code);
    /**
     * 分页查询交互系统信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<TalkSystem> findAllTalkSystem(int pageNo, int pageSize);
    /**
     * 保存交互系统信息
     * @param talkSystem
     * @return
     */
    boolean saveTalkSystem(TalkSystem talkSystem);
    /**
     * 修改交互系统信息
     * @param talkSystem
     * @return
     */
    boolean updateTalkSystem(TalkSystem talkSystem);
    /**
     * 通过id查询交互系统信息
     * @param id
     * @return
     */
    TalkSystem getTalkSystemById(Long id);
    
}
