package com.ysten.local.bss.gd.service;

import com.ysten.local.bss.device.api.domain.response.DefaultResponse;

public interface IHandleGdService {
    /**
     * 广东移动华为平台--终端第一次开机的时候，绑定用户和设备
     * @param userId
     * @param deviceId
     * @param password
     * @return
     */
    DefaultResponse bindOrRebindDevice(String userId, String deviceId, String password) throws Exception;
}
