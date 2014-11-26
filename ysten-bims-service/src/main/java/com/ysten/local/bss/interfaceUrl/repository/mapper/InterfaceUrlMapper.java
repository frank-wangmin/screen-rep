package com.ysten.local.bss.interfaceUrl.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
/**
 * 
 * @author XuSemon
 * @date 2014-5-6
 * 
 */
public interface InterfaceUrlMapper{
	
    List<InterfaceUrl> findAll(@Param("interfaceName")String interfaceName, @Param("areaId")Long areaId,@Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);
    
    InterfaceUrl getById(Long id);

    int delete(Long id);
    
	int deleteByIds(@Param("ids")List<Long> ids);

    int save(InterfaceUrl interfaceUrl);

    int getCount(@Param("interfaceName")String interfaceName, @Param("areaId")Long areaId);

    int update(InterfaceUrl interfaceUrl);
    
	InterfaceUrl getByAreaAndName(@Param("areaId")Long areaId, @Param("interfaceName")InterfaceName interfaceName);

}