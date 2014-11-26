package com.ysten.local.bss.device.repository;

import java.util.List;
import com.ysten.local.bss.device.domain.AnimationDeviceMap;
import com.ysten.local.bss.device.domain.AnimationUserMap;
import com.ysten.local.bss.device.domain.BootAnimation;
import com.ysten.utils.page.Pageable;

public interface IBootAnimationRepository {

    /**
     * 根据deviceCode查询AnimationDeviceMap
     *
     * @param ystenId
     * @param type
     * @return
     */
    AnimationDeviceMap findMapByYstenId(String ystenId, String type);

    /**
     * 根据deviceGroupId查询AnimationDeviceMap
     *
     * @param deviceGroupId
     * @param type
     * @return
     */
    AnimationDeviceMap findMapByDeviceGroupId(Long deviceGroupId, String type);

    /**
     * 根据deviceCode查询BootAnimation
     *
     * @param ystenId
     * @param type
     * @return
     */
    BootAnimation findBootAnimationByDeviceCode(String ystenId, String type);
    
    BootAnimation getBootAnimationByYstenIdOrGroupId(String ystenId, Long groupId);

    BootAnimation getBootAnimationByCustomerCodeOrGroupId(String customerCode, Long groupId);

    List<BootAnimation> findAllBootAnimation();

    /**
     * 根据deviceGroupId查询BootAnimation
     *
     * @param deviceGroupId
     * @param type
     * @return
     */
    BootAnimation findBootAnimationByDeviceGroupId(Long deviceGroupId, String type);
    
    BootAnimation getBootAnimationByDeviceGroupIdAndState(Long deviceGroupId, String state);

    /**
     * 根据升级id查询开机动画信息
     *
     * @param id
     * @param state
     * @return
     */
    BootAnimation findBootAnimationById(Long id, String state);

    /**
     * 获取默认开机动画
     *
     * @param isDefault
     * @param state
     * @return
     */
    BootAnimation findDefaultBootAnimation(int isDefault, String state);

    /**
     * 保存
     * 
     * @param bootAnimation
     * @return
     */
    boolean saveBootAnimation(BootAnimation bootAnimation);

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    BootAnimation getBootAnimationById(Long id);

    /**
     * 更新
     * 
     * @param bootAnimation
     * @return
     */
    boolean updateBootAnimation(BootAnimation bootAnimation);
    /**
     * 删除
     * @param ids
     * @return
     */
    boolean deleteBootAnimation(List<Long> ids);
    
    boolean deleteBootAnimationById(Long id);
    
    /**
     * 分页查询
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<BootAnimation> findBootAnimation(String name,Integer pageNo,Integer pageSize);
    /**
     * 删除
     * 
     * @param id
     * @return
     */
//    boolean deleteAnimationDeviceMapByAnimationIdAndType(Long bootAnimationId,String type);
//    boolean deleteAnimationUserMapByAnimationIdAndType(Long bootAnimationId,String type);

    void deleteAnimationMapByAnimationIdAndType(Long bootAnimationId,String type,String tableName);

    /**
     * 保存
     * 
     * @param animationDeviceMap
     * @return
     */
    boolean saveAnimationDeviceMap(AnimationDeviceMap animationDeviceMap);
    
    boolean saveAnimationUserMap(AnimationUserMap animationUserMap);
    
    AnimationUserMap findMapByUserGroupId(Long userGroupId, String type);
    
    AnimationUserMap findMapByUserCode(String code,String type);
    
    /**
     * 根据开机动画id和类型查询
     * 
     * @param id
     * @return
     */
    List<AnimationDeviceMap> findAnimationDeviceMapByAnimationIdAndType(Long bootAnimationId,String type);
    List<AnimationUserMap> findAnimationUserMapByAnimationIdAndType(Long bootAnimationId, String type);
	/**
	 * 根据分组Id删除开机动画设备分组信息
	 * @param id
	 */
	void deleteAnimationDeviceMapByGroupId(Long id);
	void deleteAnimationDeviceMapByCode(String deviceCode);
	void deleteAnimationUserMapByUserGroupId(Long userGroupId);
	void deleteAnimationUserMapByCode(String code);
	void deleteUserMapByAnimationId(List<Long> ids);
	void deleteDeviceMapByAnimationId(List<Long> ids);
	BootAnimation findBootAnimationByName(String name);

    BootAnimation getBootAnimationByDefault();
    
//	void deleteAnimationDeviceMapByGroupIds(List<Long> groupIds);

    void deleteAnimationMapByGroupIds(List<Long> groupIds,String tableName,String character);
	
	void deleteAnimationDeviceMapByYstenIds(List<String> ystenIds,String tableName,String character);
	
	void bulkSaveAnimationMap(List<AnimationDeviceMap> animationDeviceMap);

    void bulkSaveUserAnimationMap(List<AnimationUserMap> list);
}
