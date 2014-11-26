package com.ysten.local.bss.web.service;

import com.ysten.local.bss.device.domain.AnimationDeviceMap;
import com.ysten.local.bss.device.domain.AnimationUserMap;
import com.ysten.local.bss.device.domain.BootAnimation;
import com.ysten.utils.page.Pageable;

import java.util.List;


public interface IBootAnimationWebService {
    /**
     * 通过id查询
     * @return
     */
	BootAnimation getBootAnimationById(Long id);
    /**
     * 保存开机动画信息
     * @param sysNotice
     * @return
     */	 
    String saveBootAnimation(BootAnimation bootAnimation,String deviceGroupids,String deviceCodes,String userGroupIds,String userIds);
    String saveBootAnimation(BootAnimation bootAnimation);
    String saveBootAnimationMap(Long animationId,String areaIds,String deviceGroupIds,String ystenIds,String userGroupIds,String userIds);
    
    String checkAreaDevices(String areaIds,String[] ystenIds);
    
    boolean saveUserBootAnimation(BootAnimation bootAnimation,String userGroupIds,String userIds);
   
    /**
     * 修改开机动画信息
     * @param bootAnimation
     * @return
     */
    String updateBootAnimation(BootAnimation bootAnimation,String deviceGroupIds,String deviceCodes,String userGroupIds,String userIds);
    String updateBootAnimation(BootAnimation bootAnimation);
    
    boolean updateUserBootAnimation(BootAnimation bootAnimation,String userGroupIds,String userIds);
    /**
     * 删除开机动画信息
     * @param ids
     * @return
     */
    String deleteBootAnimation(List<Long> ids);
    boolean deleteBootAnimationaMap(List<Long> ids,String isUser);
    /**
     * 分页获取开机动画信息列表
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<BootAnimation> findBootAnimationList(String name,Integer pageNo,Integer pageSize);

    List<BootAnimation> findAllBootAnimationList();
    /**
     * 根据开机动画id和类型查询
     * @param bootAnimationId
     * @param type
     * @return
     */
    List<AnimationDeviceMap> findAnimationDeviceMapByAnimationIdAndType(Long bootAnimationId, String type);
    
    List<AnimationUserMap> findAnimationUserMapByAnimationIdAndType(Long bootAnimationId, String type);
    
    BootAnimation findBootAnimationByName(String name);
    
    BootAnimation findDefaultBootAnimation(int isDefault, String state);

    String saveUserBootAnimationMap(Long animationId,String areaIds,String userGroupIds,String userIds);

}
