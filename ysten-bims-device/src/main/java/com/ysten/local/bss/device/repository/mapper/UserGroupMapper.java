package com.ysten.local.bss.device.repository.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.UserGroupType;

/**
 * UserGroupMapper接口
 * 
 * @fileName UserGroupMapper.java
 */
public interface UserGroupMapper {
    
    List<UserGroup> findUserGroupListByIdsAndType(@Param("list") List<UserGroupMap> list,@Param("type")UserGroupType type);

    /**
     * 按照主键ID获取分组信息
     * 
     * @param id
     *            主键ID
     * @return 分组信息
     */
    UserGroup getById(Long id);
    
	/**
	 * 插入记录
	 * @param userGroup
	 * @return
	 */
	int insert(UserGroup userGroup);

	/**
	 * 根据主键修改记录
	 * @param userGroup
	 * @return
	 */
	int updateByPrimaryKey(UserGroup userGroup);

	/**
	 * 根据主键删除记录
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(@Param("id")Long id);	
	
	int delete(@Param("ids")List<Long> ids);
	
	UserGroup findUserGroupsByNameAndType(@Param("type")String type,@Param("name")String name);
	
    List<UserGroup> findUserGroupByNameAndType(@Param("type")String type,@Param("name")String name, @Param("areaId")Long areaId, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize);

    /**
     * 获取分组信息总数
     * @param type
     * @return
     */
    int findUserGroupCountByNameAndType(@Param("type")String type,@Param("name")String name,@Param("areaId")Long areaId);

	/**
	 * 根据分组名称及分组类型唯一确定一条记录
	 * @param name
	 * @param type
	 * @return
	 */
    UserGroup findByNameAndType(@Param("name")String name, @Param("type")EnumConstantsInterface.UserGroupType type);
    /**
     * find user group by  group type
     * @param type
     * @return
     */
    List<UserGroup> findUserGroupListByType(@Param("type") EnumConstantsInterface.UserGroupType type,@Param("dynamicFlag") String dynamicFlag);
    
    List<UserGroup> getList();
    
    /**
     * get all the UserGroups bound with sysNotice
     * @param noticeId
     * @param type
     * @return list
     */
    List<UserGroup> findUserGroupByNoticeIdAndType(@Param("noticeId")Long noticeId,@Param("type")String type);

    /**
     * get the userGroups by upgradeId
     * @param type
     * @return list
     */
    List<UserGroup> findUserGroupByAppIdAndType(@Param("type")String type);

    /**
     * 查询已绑定业务的动态分组
     * @param tableName 业务与终端分组的映射表
     * @param type 分组的类型
     * @return
     */
    List<UserGroup> findDynamicGroupList(@Param("tableName") String tableName,@Param("type") String type);

    /**
     * Get all the userGroups by userId
     * @param userId
     * @return list
     */
    public List<UserGroup> findUserGroupsByUserCode(@Param("code") String code,@Param("areaId")Long areaId);

    /**
     * find device group by and group type
     * @param type
     * @return
     */
    List<UserGroup> findUserGroupByArea(@Param("type") EnumConstantsInterface.UserGroupType type,@Param("tableName")String tableName,@Param("character")String character,@Param("areaId")List<String> areaId,@Param("id")Long id);
    List<Long> findUserGroupById(@Param("Id")Long Id,@Param("character") String character,@Param("tableName") String tableName);

    List<UserGroup> findUserGroupsByUserCodesAndType(@Param("codes")String[] codes,@Param("type")String type);

    List<UserGroup> findUserGroupsByProductIdAndAreaId(@Param("productId")String productId, @Param("areaId")Long areaId);

}
