package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.local.bss.device.domain.SpDefine;
import com.ysten.utils.page.Pageable;

public interface IConfigWebService {
	
    /**
     * 新增运营商不带内容提供商
     * @param spDefine
     * @return
     */
    boolean saveSpDefine(SpDefine spDefine);

    /**
     * 通过id获取运营商
     * @param id
     * @return
     */
    SpDefine getSpDefineById(Integer id);

    /**
     * 修改运营商
     * @param spDefine
     * @return
     */
    boolean updateSpDefine(SpDefine spDefine);

    /**
     * 查询所有sp
     * @return
     */
    List<SpDefine> findAllSp();

    /**
     * 通过名称获取sp
     * @param spName
     * @return
     */
    SpDefine getSpDefineByName(String spName);
    /**
     * 分页查询运营商信息
     * @param name
     * @param code
     * @param start
     * @param limit
     * @return
     */
    Pageable<SpDefine> findSpDefine(String name, String code, int start, int limit);
}
