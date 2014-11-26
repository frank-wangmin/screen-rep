package com.ysten.local.bss.talk.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.talk.domain.TalkSystem;

public interface TalkSystemMapper {
    
    List<TalkSystem> getAllTalkSystem();
    
    List<TalkSystem> findTalkSystemByCode(@Param("code") String code);
    /**
     * 获取所有交互系统信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<TalkSystem> findAllTalkSystem(@Param("pageNo")int pageNo,@Param("pageSize")int pageSize);
    /**
     * 获取总数
     * @return
     */
    int getCount();
    
    /**
     * 保存交互系统信息
     * @param talkSystem
     * @return
     */
    int save(TalkSystem talkSystem);
    /**
     * 修改交互系统信息
     * @param talkSystem
     * @return
     */
    int update(TalkSystem talkSystem);
    /**
     * 通过id查询交互系统信息
     * @param id
     * @return
     */
    TalkSystem getById(Long id);
}