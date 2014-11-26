package com.ysten.local.bss.web.service;

import com.ysten.local.bss.notice.domain.DeviceNoticeMap;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.notice.domain.UserNoticeMap;
import com.ysten.utils.page.Pageable;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;


public interface ISysNoticeWebService {
    /**
     * 通过id查询
     * @return
     */
    SysNotice getSysNoticeById(Long id);
    /**
     * 保存消息信息
     * @param sysNotice
     * @return
     */
//    boolean saveSysNotice(SysNotice sysNotice,String startDate,String endDate,String deviceGroupIds,String deviceCodes,String userGroupIds,String userIds,HttpServletRequest request) throws ParseException;
    
    boolean saveUserSysNotice(SysNotice sysNotice,String startDate,String endDate,String userGroupIds,String userIds,HttpServletRequest request) throws ParseException;

    String saveSysNotice(SysNotice sysNotice,String startDate,String endDate,String deviceGroupIds,String deviceCodes,String userGroupIds,String userIds,HttpServletRequest request) throws ParseException;
    
   String deleteSysNoticeRelationship(Long noticeId,String user);
    
    String updateSysNoticeDeviceRelationship(SysNotice sysNotice,String areaIds,String deviceGroupIds,String ystenIds) throws ParseException;


    String updateSysNoticeUserRelationship(SysNotice sysNotice,String areaIds,String userGroupIds,String userIds) throws ParseException;

    boolean updateUserSysNotice(SysNotice sysNotice,String startDate,String endDate,String userGroupIds,String userIds) throws ParseException;

    /**
     * 修改地市信息
     * @param sysNotice
     * @return
     */
    String updateSysNotice(SysNotice sysNotice,String startDate,String endDate,String deviceGroupIds,String deviceCodes,String userGroupIds,String userIds) throws ParseException;
    /**
     * 删除消息信息
     * @param ids
     * @return
     */
    boolean deleteSysNotice(@Param("ids")List<Long> ids);
    /**
     * 分页获取消息信息列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<SysNotice> findSysNoticeList(String title,String content,Integer pageNo,Integer pageSize);
    /**
     * 根据消息id查询
     * 
     * @param noticeId
     * @param type
     * @return
     */
    List<DeviceNoticeMap> findDeviceNoticeMapByNoticeIdAndType(Long noticeId,String type);
    
    List<UserNoticeMap> findUserNoticeMapByNoticeIdAndType(Long noticeId,String type);

    List<SysNotice> findAllSysNoticeList();
    
    SysNotice findSysNoticeByTitle(String title);

}
