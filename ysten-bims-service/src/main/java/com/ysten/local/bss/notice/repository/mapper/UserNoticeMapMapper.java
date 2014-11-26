package com.ysten.local.bss.notice.repository.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.notice.domain.UserNoticeMap;

/**
 * 
 * @author 
 * @date 
 * 
 */
public interface UserNoticeMapMapper {
    /**
     * 删除
     * 
     * @param id
     * @return
     */
    int deleteByNoticeIdAndType(@Param("noticeId")Long noticeId,@Param("type")String type);
    int deleteByUserGroupId(@Param("userGroupId")Long userGroupId);
    /**
     * 保存
     * 
     * @param userNoticeMap
     * @return
     */
    int save(UserNoticeMap userNoticeMap);
    
    /**
     * 根据消息id查询
     * 
     * @param id
     * @return
     */
    List<UserNoticeMap> findUserNoticeMapByNoticeIdAndType(@Param("noticeId")Long noticeId,@Param("type")String type);
   
    List<UserNoticeMap> getByUserGroupId(@Param("userGroupId")Long userGroupId);
    
    List<UserNoticeMap> findSysNoticeMapByUserCode(@Param("code")String code);
    
    List<UserNoticeMap>  findSysNoticeMapByGroupId(@Param("groupId")String groupId);
    
    List<Long> findNoticeIdsByUserCode(@Param("code")String code);
    
    int deleteByUserCode(@Param("code")String code);
    
    /**
     * get notice ids by user group ids
     * @param userGroupIds
     * @return
     */
    List<Long> findNoticeIdsByUserGroupIds(@Param("userGroupIds") List<Long> userGroupIds);
    
    int deleteByNoticeId(@Param("ids")List<Long> ids);

    void bulkSaveUserNoticeMap(@Param("list")List<UserNoticeMap> list);
}