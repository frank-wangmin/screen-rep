package com.ysten.local.bss.logger.repository.mapper;

import com.ysten.local.bss.logger.domain.InterfaceLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InterfaceLogMapper {
    int save(InterfaceLog interfaceLog);

    /**
     * 批量保存日志
     * @param logs
     * @return
     */
    int saveList(List<InterfaceLog> logs);
    
    /**
	 * 根据interfaceName和deviceId查询interfaceLog
	 * @param map
	 * @return
	 */
	InterfaceLog getByDeviceSno(Map<String, String> map);

    /**
     * 根据interfaceName和deviceId查询interfaceLog
     * @param map
     * @return
     */
    InterfaceLog getByUserName(Map<String, String> map);
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
    List<InterfaceLog> findAllInterfaceLog(@Param("interfaceName")String interfaceName,@Param("caller")String caller,
            @Param("input")String input,@Param("output")String output, @Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
    /**
     * 根据条件获取数量
     * @param interfaceName
     * @param caller
     * @param input
     * @param output
     * @return
     */
    int getCountByCondition(@Param("interfaceName")String interfaceName,@Param("caller")String caller,@Param("input")String input,@Param("output")String output);
    List<InterfaceLog> findInterfaceLogByIds(@Param("ids")List<Long> ids);
}