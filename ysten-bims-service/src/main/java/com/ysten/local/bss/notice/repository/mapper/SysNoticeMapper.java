package com.ysten.local.bss.notice.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.notice.domain.SysNotice;

/**
 * 
 * @author xuyesheng
 * @date 2014-04.05
 * 
 */
public interface SysNoticeMapper {
    /**
     * 删除
     * 
     * @param id
     * @return
     */
    int delete(@Param("ids")List<Long> ids);

    /**
     * 保存
     * 
     * @param sysNotice
     * @return
     */
    int save(SysNotice sysNotice);

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    SysNotice getById(Long id);

    /**
     * 更新
     * 
     * @param sysNotice
     * @return
     */
    int update(SysNotice sysNotice);
    
    /**
     * 查询总数
     * @param title
     * @param content
     * @return
     */
    int getCountByCondition(@Param("title")String title, @Param("content")String content);
    
    /**
     * 分页查询
     * @param title
     * @param content
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<SysNotice> findSysNotice(@Param("title")String title, 
            @Param("content")String content,
            @Param("pageNo")int pageNo,
            @Param("pageSize")int pageSize);

    List<SysNotice> findAllSysNoticeList();

    List<SysNotice> findSysNoticeByYstenIdOrGroupId(@Param("ystenId")String ystenId, @Param("groupId")Long groupId);

    List<SysNotice> findSysNoticeByCustomerCodeOrGroupId(@Param("customerCode")String customerCode, @Param("groupId")Long groupId);

    /**
     * get notices which belong to all device
     * @return
     */
    List<SysNotice> getBelongToAllNotice();

    /**
     * get notices by notice ids
     * @param noticeIds
     * @return
     */
    List<SysNotice> getNoticeListByIds(@Param("noticeIds")List<Long> noticeIds);

    /**
     *
     * @param groupIds
     * @param codes
     * @return list
     */
    List<SysNotice> findSysNoticeByDevice(@Param("noticeIds")List<Long> groupIds, @Param("codes")String codes);

    /**
     *
     * @param groupIds
     * @param codes
     * @return list
     */
    List<SysNotice> findSysNoticeByUser(@Param("noticeIds")List<Long> groupIds, @Param("codes")String codes);
    
    SysNotice findSysNoticeByTitle(@Param("title")String title);

    List<SysNotice> findSysNoticeByType(@Param("type") String type);

}