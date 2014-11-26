package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.local.bss.screen.domain.ScreenManager;
import com.ysten.utils.page.Pageable;

public interface IScreenManagerWebService {

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
    boolean saveScreenManager(ScreenManager screenManager,Long areaId);
    /**
     * 修改屏幕管理信息
     * @param screenManager
     * @return
     */
    boolean updateScreenManager(ScreenManager screenManager,Long areaId);
    /**
     * 删除屏幕管理信息
     * @param id
     * @return
     */
    boolean deleteScreenManager(List<Long> ids);
    /**
     * 分页获取屏幕信息列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<ScreenManager> findScreenManagerList(String name,Integer pageNo,Integer pageSize);
}
