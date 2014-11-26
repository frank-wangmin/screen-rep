package com.ysten.local.bss.screen.service;

import java.util.List;

import com.ysten.local.bss.screen.domain.ScreenManager;

public interface IScreenManagerService {

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
	 * 查询出所有的screen manager信息
	 * @author chenxiang
	 * @date 2014-7-14 下午1:27:47 
	 * @return
	 */
	List<ScreenManager> findAllScreenManager();
}
