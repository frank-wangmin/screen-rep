package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import com.ysten.local.bss.device.domain.BootAnimation;

import org.apache.ibatis.annotations.Param;


public interface BootAnimationMapper {

    /**
     * 根据device code查询开机动画信息
     *
     * @param ystenId
     * @param type
     * @return
     */
    BootAnimation findBootAnimationByDeviceCode(@Param("ystenId") String ystenId, @Param("type") String type);
    
    BootAnimation getBootAnimationByYstenIdOrGroupId(@Param("ystenId") String ystenId, @Param("groupId") Long groupId);

    BootAnimation getBootAnimationByCustomerCodeOrGroupId(@Param("customerCode") String customerCode, @Param("groupId") Long groupId);

    /**
     * 根据device group id查询开机动画信息
     *
     * @param deviceGroupId
     * @param type
     * @return
     */
    BootAnimation findBootAnimationByDeviceGroupId(@Param("deviceGroupId") Long deviceGroupId, @Param("type") String type);
    
    BootAnimation getBootAnimationByDeviceGroupIdAndState(@Param("deviceGroupId") Long deviceGroupId, @Param("state") String state);

    /**
     * 根据升级id查询开机动画信息
     *
     * @param id
     * @return
     */
    BootAnimation findBootAnimationById(@Param("id") Long id, @Param("state") String state);

    /**
     * 获取默认开机动画
     *
     * @param isDefault
     * @return
     */
    BootAnimation findDefaultBootAnimation(@Param("isDefault") int isDefault, @Param("state") String state);

    List<BootAnimation> findAllBootAnimation();
    /**
     * 删除
     * 
     * @param id
     * @return
     */
    int delete(@Param("ids")List<Long> ids);
    
    int deleteById(@Param("id")Long id);

    /**
     * 保存
     * 
     * @param bootAnimation
     * @return
     */
    int save(BootAnimation bootAnimation);

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    BootAnimation getById(Long id);

    /**
     * 更新
     * 
     * @param bootAnimation
     * @return
     */
    int update(BootAnimation bootAnimation);
    
    /**
     * 查询总数
     * @param name
     * @return
     */
    int getCountByCondition(@Param("name")String name);
    
    BootAnimation findBootAnimationByName(@Param("name")String name);

    BootAnimation getBootAnimationByDefault();
    
    /**
     * 分页查询
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<BootAnimation> findBootAnimation(@Param("name")String name,@Param("pageNo")int pageNo,@Param("pageSize")int pageSize);
}
