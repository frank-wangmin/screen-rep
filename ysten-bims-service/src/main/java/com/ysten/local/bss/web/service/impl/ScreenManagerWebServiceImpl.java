package com.ysten.local.bss.web.service.impl;

import com.ysten.area.repository.IAreaRepository;
import com.ysten.local.bss.screen.domain.ScreenManager;
import com.ysten.local.bss.screen.repository.IScreenManagerRepository;
import com.ysten.local.bss.web.service.IScreenManagerWebService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenManagerWebServiceImpl implements IScreenManagerWebService {

    @Autowired
    private IScreenManagerRepository screenManagerRepository;
    @Autowired
    private IAreaRepository areaRepository;

    @Override
    public ScreenManager getScreenManagerById(Long id) {
        return this.screenManagerRepository.getScreenManagerById(id);
    }

    @Override
    public boolean saveScreenManager(ScreenManager screenManager, Long areaId) {
        screenManager.setArea(this.areaRepository.getAreaById(areaId));
        return this.screenManagerRepository.saveScreenManager(screenManager);
    }

    @Override
    public boolean updateScreenManager(ScreenManager screenManager, Long areaId) {
        if (areaId != null) {
            screenManager.setArea(this.areaRepository.getAreaById(areaId));
        }
        return this.screenManagerRepository.updateScreenManager(screenManager);
    }

    @Override
    public boolean deleteScreenManager(List<Long> ids) {
        return this.screenManagerRepository.deleteScreenManager(ids);
    }

    @Override
    public Pageable<ScreenManager> findScreenManagerList(String name, Integer pageNo, Integer pageSize) {
        return this.screenManagerRepository.findScreenManagerList(name, pageNo, pageSize);
    }

}
