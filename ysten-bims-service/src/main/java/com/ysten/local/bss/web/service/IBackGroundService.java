package com.ysten.local.bss.web.service;

import com.ysten.local.bss.device.domain.BackgroundImage;
import com.ysten.local.bss.device.domain.BackgroundImageUserMap;
import com.ysten.utils.page.Pageable;

import java.util.List;

public interface IBackGroundService {

    /**
     * 获取分页背景信息
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<BackgroundImage> findBackGroundList(String name, Integer pageNo, Integer pageSize);

    /**
     * 保存背景信息
     *
     * @param backgroundImage
     * @return
     */
    String saveBackGroundImage(BackgroundImage backgroundImage);

    String saveUserBackGroundImageMap(Long backgroudImageId, String areaIds, String userGroupIds, String userIds, Integer userGroupLoopTime, Integer userLoopTime);

    String saveBackGroundImageMap(Long backgroudImageId, String areaIds,String deviceGroupIds, String deviceCodes, String userGroupIds, String userIds, Integer deviceGroupLoopTime, Integer deviceLoopTime, Integer userGroupLoopTime, Integer userLoopTime);

    /**
     * 修改背景信息
     *
     * @param backgroundImage
     * @return
     */
    String updateBackGroundImage(BackgroundImage backgroundImage);

    /**
     * 根据主键Id获取背景信息
     *
     * @param id
     * @return
     */
    BackgroundImage getById(Long id);

    List<BackgroundImage> findDefaultBackgroundImage(int isDefault);

    /**
     * 删除背景信息
     *
     * @param idsList
     * @return
     */
    boolean deleteBackGroundImage(List<Long> idsList);

    String deleteBackGroundImages(List<Long> idsList);

    boolean deleteBackGroundImageMap(List<Long> idsList,String isUser);

    /**
     * @param type
     * @param id
     * @return
     */
    String getBackGroundImageMapByTypeAndId(String type, String id);

    String getUserBackGroundImageMapByTypeAndId(String type, Long id);

    List<BackgroundImageUserMap> getBGUserImageMapByBGIdAndType(Long bgId, String type);

    List<BackgroundImage> findAllBackgroundImage();

}
