package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.domain.BackgroundImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BackgroundImageMapper {

    /**
     * 根据device code查询背景图片
     *
     * @param deviceCode
     * @return
     */
    List<BackgroundImage> findBackgroundImageByDeviceCode(@Param("deviceCode") String deviceCode);

    List<BackgroundImage> findBackgroundImageByYstenId(@Param("ystenId") String ystenId);

    List<BackgroundImage> findAllBackgroundImage();
    
    List<BackgroundImage> findBackgroundImageByYstenIdOrGroupId(@Param("ystenId") String ystenId, @Param("groupId") Long groupId);

    List<BackgroundImage> findBackgroundImageByCustomerCodeOrGroupId(@Param("customerCode") String customerCode, @Param("groupId") Long groupId);
    
    /**
     * 根据device group id查询背景图片
     *
     * @param deviceGroupId
     * @return
     */
    List<BackgroundImage> findBackgroundImageByDeviceGroupId(@Param("deviceGroupId") Long deviceGroupId);
    
    List<BackgroundImage> findBackgroundImageByDeviceGroupIdAndState(@Param("deviceGroupId") Long deviceGroupId,@Param("state") String state);

    /**
     * 根据background image id查询背景图片
     *
     * @param id
     * @return
     */
    BackgroundImage findBackgroundImageById(@Param("id") Long id);
    
    /**
     * 根据background image id,state查询背景图片  
     *
     * @param id,state
     * @return
     */
    BackgroundImage getBackgroundImageByIdAndUseable(@Param("id") Long id,@Param("state") String state);

    /**
     * 查询默认背景图片
     *
     * @param isDefault
     * @return
     */
    List<BackgroundImage> findDefaultBackgroundImage(@Param("isDefault") int isDefault);

	/**
	 * 获取分页背景信息
	 * @param url
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<BackgroundImage> findBackGroundList(@Param("name")String name, @Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);

	/**
	 * 获取背景信息总的记录数
	 * @param url
	 * @return
	 */
	int getCountByCondition(@Param("name")String name);

	/**
	 * 保存背景信息
	 * @param backgroundImage
	 * @return
	 */
	int save(BackgroundImage backgroundImage);

	/**
	 * 保存背景信息
	 * @param backgroundImage
	 * @return
	 */
	int update(BackgroundImage backgroundImage);

	/**
	 * 根据Ids删除背景信息
	 * @param idsList
	 * @return
	 */
	int deleteByIds(@Param("ids")List<Long> ids);
	
	int deleteById(@Param("id")Long id);
}
