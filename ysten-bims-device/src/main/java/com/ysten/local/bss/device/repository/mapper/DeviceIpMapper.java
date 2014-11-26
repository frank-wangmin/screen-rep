package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.DeviceIp;

/**
 * 
 * 类名：DeviceIpMapper
 * 
 * @version
 */
public interface DeviceIpMapper {
    /**
     * 通过id查询
     * 
     * @param ipValue
     * @return
     */
    DeviceIp getById(Long id);
    /**
     * 根据计算后ip获取ip在库中信息
     * 
     * @param ipValue
     * @return
     */
    List<DeviceIp> getDeviceIpByIpValue(Long ipValue);
    /**
     * 通过ip段查询
     * @param ipSeq
     * @return
     */
    List<DeviceIp> findAllDeviceIpByIpSeg(@Param("ipSeg") String ipSeg,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
    /**
     * 通过ip段查询总数
     * @param ipSeq
     * @return
     */
    int getCountByIpSeq(@Param("ipSeg") String ipSeg);
    /**
     * 新增
     * @param deviceIp
     * @return
     */
    int save(DeviceIp deviceIp);
    /**
     * 修改
     * @param deviceIp
     * @return
     */
    int update(DeviceIp deviceIp);
    
    int delete(@Param("ids")List<Long> ids);

}
