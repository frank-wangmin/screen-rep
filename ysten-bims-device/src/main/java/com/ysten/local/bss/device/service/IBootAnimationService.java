package com.ysten.local.bss.device.service;

import com.ysten.local.bss.device.domain.AnimationDeviceMap;
import com.ysten.local.bss.device.domain.BootAnimation;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.exception.CustomerNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;

public interface IBootAnimationService {

    /**
     * 根据deviceCode查询AnimationDeviceMap
     *
     * @param deviceCode
     * @return
     */
    AnimationDeviceMap getMapByDeviceCode(String deviceCode);

    /**
     * 根据deviceGroupId查询AnimationDeviceMap
     *
     * @param deviceGroupId
     * @return
     */
    AnimationDeviceMap getMapByDeviceGroupId(Long deviceGroupId);

    /**
     * 根据deviceCode查询BootAnimation
     *
     * @param deviceCode
     * @return
     * @throws CustomerNotFoundException 
     */
    String getBootAnimationByYstenId(String deviceCode) throws DeviceNotFoundException, CustomerNotFoundException ;

    /**
     * 根据deviceGroupId查询BootAnimation
     *
     * @param deviceGroupId
     * @return
     */
    BootAnimation getBootAnimationByDeviceGroupId(Long deviceGroupId);

    /**
     * 根据id查询开机动画信息
     *
     * @param id
     * @return
     */
    BootAnimation getBootAnimationById(Long id);

    /**
     * 获取默认开机动画
     *
     * @param isDefault
     * @return
     */
    BootAnimation getDefaultBootAnimation(int isDefault);

    /**
     * 根据device id获取开机动画，如果获取不到，就获取默认开机动画
     *
     * @param device
     * @param isDefault
     * @return
     */
    Object[] getBootAnimationByDeviceGroup(Device device);

}
