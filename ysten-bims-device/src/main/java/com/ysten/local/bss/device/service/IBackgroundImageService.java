package com.ysten.local.bss.device.service;

import java.util.List;

import com.ysten.local.bss.device.domain.BackgroundImage;
import com.ysten.local.bss.device.domain.BackgroundImageDeviceMap;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.exception.CustomerNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;

public interface IBackgroundImageService {

    /**
     * 根据deviceCode查询BackgroundImageDeviceMap
     *
     * @param deviceCode
     * @return
     */
    List<BackgroundImageDeviceMap> getMapByDeviceCode(String deviceCode);

    /**
     * 根据deviceGroupId查询BackgroundImageDeviceMap
     *
     * @param deviceGroupId
     * @return
     */
    List<BackgroundImageDeviceMap> getMapByDeviceGroupId(Long deviceGroupId);

    /**
     * 根据deviceCode查询BackgroundImageDeviceMap
     *
     * @param ystenId
     * @return
     */
    String getBackgroundImageByYstenId(String ystenId)throws DeviceNotFoundException,CustomerNotFoundException;

    /**
     * 根据deviceGroupId查询BackgroundImageDeviceMap
     *
     * @param deviceGroupId
     * @return
     */
    List<BackgroundImage> getBackgroundImageByDeviceGroupId(Long deviceGroupId);

    /**
     * 根据id查询背景图片
     *
     * @param id
     * @return
     */
    BackgroundImage getBackgroundImageById(Long id);

    /**
     * 获取默认背景图片
     *
     * @param isDefault
     * @return
     */
    List<BackgroundImage> getDefaultBackgroundImage(int isDefault);

    /**
     * 根据device id获取开机动画，如果获取不到，就获取默认开机动画
     *
     * @param device
     * @param isDefault
     * @return
     */
    List<BackgroundImage> getBackgroundImageByDeviceGroup(Device device, int isDefault);

}
