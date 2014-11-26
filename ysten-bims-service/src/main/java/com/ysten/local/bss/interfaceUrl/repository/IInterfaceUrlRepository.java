package com.ysten.local.bss.interfaceUrl.repository;

import java.util.List;

import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.utils.page.Pageable;

public interface IInterfaceUrlRepository {
	/**
	 * 根据省份Id和接口名称获取接口信息
	 * @param id
	 * @param receivedevice
	 * @return
	 */
	InterfaceUrl getByAreaAndName(Long areaId, InterfaceName interfaceName);
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
    InterfaceUrl getInterfaceUrlById(Long id);
    /**
     * 保存
     * @param interfaceUrl
     * @return
     */
    boolean saveInterfaceUrl(InterfaceUrl interfaceUrl);
    /**
     * 修改
     * @param interfaceUrl
     * @return
     */
    boolean updateInterfaceUrl(InterfaceUrl interfaceUrl);
    
	boolean deleteByIds(List<Long> idsList);
    /**
     * 分页获取终端接口地址信息列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<InterfaceUrl> findInterfaceUrlList(String interfaceName,Long areaId,Integer pageNo,Integer pageSize);
}
