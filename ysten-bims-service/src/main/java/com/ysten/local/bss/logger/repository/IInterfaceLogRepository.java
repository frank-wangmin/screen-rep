package com.ysten.local.bss.logger.repository;

import com.ysten.local.bss.logger.domain.InterfaceLog;
import com.ysten.utils.page.Pageable;

import java.util.List;


public interface IInterfaceLogRepository {
	
    boolean saveInterfaceLog(InterfaceLog interfaceLog);

    /**
     * 批量保存日志
     * @param logs
     * @return
     */
    boolean saveInterfaceLog(List<InterfaceLog> logs);
    
    /**
     * 根据interfaceName和deviceId查询interfaceLog
     * @param interfaceName
     * @param deviceId
     * @return
     */
    InterfaceLog getByDeviceSno(String interfaceName, String deviceId);

    InterfaceLog getByUserId(String interfaceName, String userId);
    /**
     * 分页检索接口日志信息
     * 
     * @param interfaceName
     *            接口名称
     * @param caller
     *            访问系统
     * @param input
     *            输入参数
     * @param output
     *            输出结果
     * @param pageNo
     *            当前页号
     * @param pageSize
     *            每页大小
     * @return
     */
    Pageable<InterfaceLog> findAllInterfaceLog(String interfaceName,String caller,String input, String output, int pageNo, int pageSize);

	/**
	 * 获取系统所有操作日志
	 * @return
	 */
	List<InterfaceLog> findAllInterfaceLog();
	List<InterfaceLog> findExportInterfaceLog(String interfaceName,String caller,String input, String output);
	List<InterfaceLog> findInterfaceLogByIds(List<Long> ids);
}
