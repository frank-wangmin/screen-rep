package com.ysten.local.bss.device.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.SpDefine;
import com.ysten.utils.page.Pageable;
 
public interface IConfigRepository {

    /**
     * 保存sp
     * @param spDefine
     * @return
     */
    boolean saveSpDefine(SpDefine spDefine);

    /**
     * 通过id获取sp
     * @param id
     * @return
     */
    SpDefine getSpDefineById(Integer id);

    /**
     * 修改sp
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
     * 通过name获取sp
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
    /**
     * 通过spId和状态查询
     * @param spId
     * @param state
     * @return
     */
    SpDefine getSpDefineListById(@Param("spId")Integer spId, @Param("state")String state);

}
