package com.ysten.local.bss.notice.repository.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.notice.domain.DeviceNoticeMap;

/**
 * 
 * @author xuyesheng
 * @date 2014-04.05
 * 
 */
public interface DeviceNoticeMapMapper {
    /**
     * 删除
     * 
     * @param id
     * @return
     */
    int deleteByNoticeIdAndType(@Param("noticeId")Long noticeId,@Param("type")String type,@Param("tableName")String tableName);

    /**
     * 保存
     * 
     * @param deviceNoticeMap
     * @return
     */
    int save(DeviceNoticeMap deviceNoticeMap);
    /**
     * 批量保存
     * @param list
     */
    
    void bulkSaveDeviceNoticeMap(List<DeviceNoticeMap> list);
    
    /**
     * 根据消息id查询
     * 
     * @param id
     * @return
     */
    List<DeviceNoticeMap> findDeviceNoticeMapByNoticeIdAndType(@Param("noticeId")Long noticeId,@Param("type")String type);

    /**
     * get notice ids by device code
     * @param ystenId
     * @return
     */
    List<Long> findNoticeIdsByDeviceCode(@Param("ystenId") String ystenId);

    /**
     * get notice ids by device group ids
     * @param deviceGroupIds
     * @return
     */
    List<Long> findNoticeIdsByDeviceGroupIds(@Param("deviceGroupIds") List<Long> deviceGroupIds);
    
    List<DeviceNoticeMap> findSysNoticeMapByGroupId(@Param("groupId")Long id);
    List<DeviceNoticeMap> findSysNoticeMapByYstenId(@Param("ystenId") String ystenId);
    
    DeviceNoticeMap findMapByNoticeIdAndYstenId(@Param("noticeId")Long noticeId,@Param("ystenId") String ystenId);
	/**
	 * 根据分组Id删除消息设备分组信息
	 * @param id
	 */
	void deleteSysNoticeMapByGroupId(@Param("groupId")Long id);
	
	void deleteSysNoticeMapByYstenId(@Param("ystenId") String ystenId);
	
	int deleteByNoticeId(@Param("ids")List<Long> ids);
	
	void deleteMapByYstenIdsAndNoticeId(@Param("noticeId")Long noticeId,@Param("ystenIds")List<String> ystenIds,@Param("tableName")String tableName,@Param("character")String character);
}