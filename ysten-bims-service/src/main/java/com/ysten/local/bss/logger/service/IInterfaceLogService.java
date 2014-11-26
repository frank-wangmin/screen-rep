package com.ysten.local.bss.logger.service;

import com.ysten.local.bss.logger.domain.InterfaceLog;

import java.util.List;

public interface IInterfaceLogService {
    /**
     * 保存接口访问日志
     * @param interfaceLog
     * @param output
     * @return
     */
    boolean saveInterfaceLog(InterfaceLog interfaceLog,String output);
    /**
     * 批量保存日志
     * @param logs
     * @return
     */
    boolean saveInterfaceLog(List<InterfaceLog> logs);
    /**
     * 获取接口访问日志bean
     * @param caller
     * @param input
     * @return
     */
    InterfaceLog getInterfaceLog(String caller,String input);
    /**
     * 获取接口访问日志bean
     * @param caller
     * @param interfaceName
     * @param input
     * @return
     */
    InterfaceLog getInterfaceLog(String caller,String interfaceName,String input);
    
    /**
     * 根据interfaceName和deviceId查询interfaceLog
     * @param deviceId
     * @return
     */
    public InterfaceLog getByDeviceSno(String deviceId);

    public InterfaceLog getByUserId(String userId);
}
