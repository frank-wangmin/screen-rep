package com.ysten.local.bss.screen.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.screen.domain.ScreenManager;

public interface ScreenManagerMapper {

	/**
	 * 根据area查询出所有的screen manager信息
	 * @return
	 */
	List<ScreenManager> getAllScreenManager(int areaId);
	
	/**
	 * 查询全部的area id
	 * @return
	 */
	public List<String> getAllArea();
	/**
     * 通过id查询屏幕管理
     * @return
     */
    ScreenManager getById(Long id);
    /**
     * 保存屏幕管理信息
     * @param screenManager
     * @return
     */
    int save(ScreenManager screenManager);
    /**
     * 修改屏幕管理信息
     * @param screenManager
     * @return
     */
    int update(ScreenManager screenManager);
    /**
     * 删除屏幕管理信息
     * @param id
     * @return
     */
    int delete(@Param("ids")List<Long> ids);
    /**
     * 分页获取屏幕信息列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<ScreenManager> findAll(@Param("name")String name,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
    /**
     * 获取总数
     * @return
     */
    int getCount(@Param("name")String name);

	/**
	 *  查询出所有的screen manager信息
	 * @author chenxiang
	 * @date 2014-7-14 下午1:32:14 
	 * @return
	 */
	List<ScreenManager> findAllScreenManager();

}