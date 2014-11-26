package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.UserGroupMap;

/**
 * UserGroupMapMapper接口
 *
 * @fileName UserGroupMapMapper.java
 */
public interface UserGroupMapMapper {
    /**
     * 保存终端分组关系
     *
     * @param deviceGroupMap
     * @return
     */
    int save(UserGroupMap userGroupMap);

    void bulkSaveUserGroupMap(@Param("list") List<UserGroupMap> list);

    int deleteMapByGroupId(@Param("groupId") Long groupId);

    int deleteMapByUserCode(@Param("code") String code);

    int deleteMapByUserCodeAndGroupId(@Param("code") String code, @Param("groupId") Long groupId);

    UserGroupMap getByUserCodeAndGroupId(@Param("groupId") Long groupId, @Param("code") String code);

    List<UserGroupMap> getMapByGroupId(@Param("groupId") Long groupId);

    List<UserGroupMap> findByUserCode(@Param("code") String code);

    void deleteMapByCodeAndGroup(@Param("groupId") Long groupId, @Param("code") String code);
    
    void deleteMapByUserCodesAndGroups(@Param("codes")String[] codes, @Param("groupIds")List<Long> groupIds);
}
