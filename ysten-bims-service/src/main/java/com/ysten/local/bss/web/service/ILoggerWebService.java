package com.ysten.local.bss.web.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ysten.local.bss.device.domain.ApkUpgradeResultLog;
import com.ysten.local.bss.device.domain.DeviceUpgradeResultLog;
import com.ysten.local.bss.logger.domain.InterfaceLog;
import com.ysten.local.bss.logger.domain.OperateLog;
import com.ysten.utils.page.Pageable;

public interface ILoggerWebService {
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
     * 日志查询
     * @param modelName
     * @param operator
     * @param operatorDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<OperateLog> findOperatorLogger(String modelName, String operator,String operatorId, String beginDate, String endDate, int start, int limit);

    /**
     * 保存系统操作日志
     * @param moduleName
     * 				模块名称
     * @param operationType
     * 				操作类型
     * @param primaryKeyValue
     * 				操作主键id
     * @param description	
     *				描述 
     * @param request
     * @return
     */
    boolean saveOperateLog(String moduleName,String operationType,String primaryKeyValue,String description,HttpServletRequest request);
	/**
	 * 返回所有系统操作日志
	 * @return
	 */
	List<InterfaceLog> findAllInterfaceLog();
    /**
     * 分页检索接口终端升级日志信息
     * @param pageNo
     *            当前页号
     * @param pageSize
     *            每页大小
     * @return
     */
    Pageable<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCodeAndYstId(String deviceCode,String ystenId,Integer pageNo, Integer pageSize);

    Pageable<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCondition(Map<String, Object> map);

    Pageable<ApkUpgradeResultLog> findApkUpgradeResultLogByCodeAndYstId(String deviceCode,String ystenId,Integer pageNo, Integer pageSize);
    
    /**
     * 获取Module Name列表，这个列表是记录Log时各自定义的。不是从数据库从取的固定的名字，所以需要从bss_operate_log表中获取这个名字列表
     * @return
     */
    List<String> getModuleNameList();
    List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByIds(List<Long> ids);
    List<ApkUpgradeResultLog> findApkUpgradeResultLogByIds(List<Long> ids);
    List<DeviceUpgradeResultLog> findExportDeviceUpgradeResultLog(Map<String, Object> map);
    Pageable<ApkUpgradeResultLog> findApkUpgradeResultLogByCondition(Map<String, Object> map);
    List<ApkUpgradeResultLog> findExportApkUpgradeResultLog(Map<String, Object> map);
    List<InterfaceLog> findExportInterfaceLog(String interfaceName,String caller,String input, String output);
    List<InterfaceLog> findInterfaceLogByIds(List<Long> ids);
}

