package com.ysten.local.bss.device.repository;

import java.util.List;
import com.ysten.local.bss.device.domain.BackgroundImage;
import com.ysten.local.bss.device.domain.BackgroundImageDeviceMap;
import com.ysten.local.bss.device.domain.BackgroundImageUserMap;
import com.ysten.utils.page.Pageable;

public interface IBackgroundImageRepository {

    /**
     * 根据device code查询背景图片--设备映射
     *
     * @param deviceCode
     * @return
     */
    List<BackgroundImageDeviceMap> findMapByYstenId(String deviceCode);

    /**
     * 根据device group id查询背景图片--设备映射
     *
     * @param deviceGroupId
     * @return
     */
    List<BackgroundImageDeviceMap> findMapByDeviceGroupId(Long deviceGroupId);

    /**
     * 根据device code查询背景图片
     *
     * @param deviceCode
     * @return
     */
    List<BackgroundImage> findBackgroundImageByDeviceCode(String deviceCode);
    
    List<BackgroundImage> findBackgroundImageByYstenId(String ystenId);

    List<BackgroundImage> findAllBackgroundImage();

    List<BackgroundImage> findBackgroundImageByYstenIdOrGroupId(String ystenId, Long groupId);

    List<BackgroundImage> findBackgroundImageByCustomerCodeOrGroupId(String customerCode, Long groupId);

    /**
     * 根据device group id查询背景图片
     *
     * @param deviceGroupId
     * @return
     */
    List<BackgroundImage> findBackgroundImageByDeviceGroupId(Long deviceGroupId);
    
    List<BackgroundImage> findBackgroundImageByDeviceGroupIdAndState(Long deviceGroupId,String state);

    /**
     * 根据background image id查询背景图片
     *
     * @param id
     * @return
     */
//    BackgroundImage findBackgroundImageById(Long id);
    
    
    /**
     * 根据background image id,state查询背景图片  
     * @param id
     * @param state
     * @return
     */
    BackgroundImage getBackgroundImageByIdAndUseable(Long id,String state);

    /**
     * 查询默认背景图片
     *
     * @param isDefault
     * @return
     */
    List<BackgroundImage> findDefaultBackgroundImage(int isDefault);


	/**
	 * 获取分页背景信息
	 * @param url
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pageable<BackgroundImage> findBackGroundList(String name, Integer pageNo, Integer pageSize);

	/**
	 * 保存背景信息
	 * @param backgroundImage
	 * @return
	 */
	boolean saveBackGroundImage(BackgroundImage backgroundImage);

	/**
	 * 更新背景信息
	 * @param backgroundImage
	 * @return
	 */
	boolean updateBackGroundImage(BackgroundImage backgroundImage);

	/**
	 * 根据主键id获取背景信息
	 * @param id
	 * @return
	 */
	BackgroundImage getById(Long id);

	/**
	 * 根据Ids删除背景信息
	 * @param idsList
	 * @return
	 */
//	boolean deleteByIds(List<Long> idsList);
	boolean deleteById(Long id);

	/**
	 * 根据背景Id和设备code唯一确定一条终端背景关系
	 * @param id
	 * @param deviceCode
	 * @return
	 */
	BackgroundImageDeviceMap findMapByBGIdAndCode(Long id, String deviceCode);
	
	BackgroundImageUserMap findUserMapByBGIdAndCode(Long id, String userId);

	/**
	 * 根据背景Id和设备分组Id唯一确定一条背景关系
	 * @param id
	 * @param deviceGroupId
	 * @return
	 */
	BackgroundImageDeviceMap findMapByBGIdAndGroupId(Long id, Long deviceGroupId);

	/**
	 * 保存终端背景关系
	 * @param map
	 */
	boolean saveBackgroundImageDeviceMap(BackgroundImageDeviceMap map);
	
	void bulkSaveBackgroundMap(List<BackgroundImageDeviceMap> maps);

    void bulkSaveUserBackgroundMap(List<BackgroundImageUserMap> list);

	boolean saveBackgroundImageUserMap(BackgroundImageUserMap map);
	/**
	 * 根据背景id和类型，获取终端背景关系信息
	 * @param parseLong
	 * @param type
	 * @return
	 */
	List<BackgroundImageDeviceMap> getBGImageMapByBGIdAndType(Long bgId, String type);
	
	List<BackgroundImageDeviceMap> getBGImageDeviceMapByBGId(Long bgId);
	
	List<BackgroundImageUserMap> getBGUserImageMapByBGIdAndType(Long bgId, String type);
	
	List<BackgroundImageUserMap> getBGUserImageMapByBGId(Long bgId);
	
	List<BackgroundImageUserMap> findMapByUserGroupId(Long userGroupId);
	
	List<BackgroundImageUserMap> findMapByUserCode(String code);
	/**
	 * 根据背景Id删除背景设备分组信息
	 * @param id
	 */
	boolean deleteBackGroundImageMapByBGId(Long id);
	
	boolean deleteUserBackGroundImageMapByBGId(Long id);

	/**
	 * 根据设备分组Id删除背景设备分组信息
	 * @param id
	 */
	void deleteBackGroundImageMapByGroupId(Long id);
	
	void deleteUserBackGroundImageMapByGroupId(Long id);
	
	void deleteBackGroundImageMapByUserCode(String code);
	
	void deleteBackGroundImageMapByDeviceCode(String deviceCode);
	
	void deleteByBGIdAndType(Long groupId,String type,String tableName);
	
	void deleteBackGroundMapByYstenIdsAndBgId(Long bgId,List<String> ystenIds,String tableName,String character );
}
