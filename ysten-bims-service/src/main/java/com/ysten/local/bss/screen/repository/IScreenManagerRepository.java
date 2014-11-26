package com.ysten.local.bss.screen.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.screen.domain.ScreenManager;
import com.ysten.utils.page.Pageable;

public interface IScreenManagerRepository {

	/**
	 * 根据area查询出所有的screen manager信息
	 * 
	 * @return
	 */
	List<ScreenManager> getAllScreenManager(int areaId);
	
	/**
	 * 查询全部的area id
	 * @return
	 */
	List<String> getAllArea();
	/**
     * 通过id查询屏幕管理
     * @return
     */
    ScreenManager getScreenManagerById(Long id);
    /**
     * 保存屏幕管理信息
     * @param screenManager
     * @return
     */
    boolean saveScreenManager(ScreenManager screenManager);
    /**
     * 修改屏幕管理信息
     * @param screenManager
     * @return
     */
    boolean updateScreenManager(ScreenManager screenManager);
    /**
     * 删除屏幕管理信息
     * @param id
     * @return
     */
    boolean deleteScreenManager(@Param("ids")List<Long> ids);
    /**
     * 分页获取屏幕信息列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<ScreenManager> findScreenManagerList(String name,Integer pageNo,Integer pageSize);

	/**
	 * 查询出所有的screen manager信息
	 * @author chenxiang
	 * @date 2014-7-14 下午1:29:15 
	 * @return
	 */
	List<ScreenManager> findAllScreenManager();
}
