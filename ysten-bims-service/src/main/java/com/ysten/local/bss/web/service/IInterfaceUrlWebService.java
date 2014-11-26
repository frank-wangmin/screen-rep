package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.utils.page.Pageable;


public interface IInterfaceUrlWebService {
    /**
     * 通过id查询
     * @return
     */
	InterfaceUrl getInterfaceUrlById(Long id);
    /**
     * 保存终端接口地址信息
     * @param interfaceUrl
     * @return
     */
	boolean  saveInterfaceUrl(InterfaceUrl interfaceUrl);
    /**
     * 修改终端接口地址信息
     * @param interfaceUrl
     * @return
     */
    boolean updateInterfaceUrl(Long id,String interfaceName,String interfaceUrl,Long areaId);
    
	boolean deleteInterfaceUrl(List<Long> interfaceUrlIds);
    /**
     * 分页获取终端接口地址信息列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<InterfaceUrl> findInterfaceUrlList(String interfaceName,Long areaId,Integer pageNo,Integer pageSize);

    /**
     * 根据省份Id和接口名称获取接口信息
     * @param areaId
     * @param interfaceName
     * @return
     */
    InterfaceUrl getByAreaAndName(Long areaId, InterfaceUrl.InterfaceName interfaceName);

}
