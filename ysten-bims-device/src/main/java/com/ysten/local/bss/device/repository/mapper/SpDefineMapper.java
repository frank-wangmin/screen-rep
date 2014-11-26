package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.SpDefine;

/**
 * 
 * @author xuyesheng
 * @date 2011-12.23
 * 
 */
public interface SpDefineMapper {

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 保存
     * 
     * @param spDefine
     * @return
     */
    int save(SpDefine spDefine);

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    SpDefine getById(Integer id);
    
    /**
     * 通过名称获取sp
     * @param spName
     * @return
     */
    SpDefine getByName(String spName);

    /**
     * 更新
     * 
     * @param spDefine
     * @return
     */
    int update(SpDefine spDefine);
    
    /**
     * 查找运营商
     * @param name
     * @param code
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<SpDefine> findSpDefine(@Param("name")String name, @Param("code")String code,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
    
    /**
     * 根据条件查找运营商数量
     * @param name
     * @param code
     * @return
     */
    int getCountByCondition(@Param("name")String name, @Param("code")String code);
    
    /**
     * 找出所有sp
     * @return
     */
    List<SpDefine> findAllSp();
    
    SpDefine getSpDefineListById(@Param("id")Integer id, @Param("state")String state);
    
}