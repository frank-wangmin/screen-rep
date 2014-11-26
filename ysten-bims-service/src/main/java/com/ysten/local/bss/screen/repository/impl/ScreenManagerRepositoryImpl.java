package com.ysten.local.bss.screen.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.screen.domain.ScreenManager;
import com.ysten.local.bss.screen.repository.IScreenManagerRepository;
import com.ysten.local.bss.screen.repository.mapper.ScreenManagerMapper;
import com.ysten.utils.page.Pageable;

@Repository
public class ScreenManagerRepositoryImpl implements IScreenManagerRepository {

	@Autowired
	private ScreenManagerMapper screenManagerMapper;

	@Override
	public List<ScreenManager> getAllScreenManager(int areaId) {
		return this.screenManagerMapper.getAllScreenManager(areaId);
	}

	@Override
	public List<String> getAllArea() {
		return this.screenManagerMapper.getAllArea();
	}

    @Override
    public ScreenManager getScreenManagerById(Long id) {
        return this.screenManagerMapper.getById(id);
    }

    @Override
    public boolean saveScreenManager(ScreenManager screenManager) {
        return 1 == this.screenManagerMapper.save(screenManager);
    }

    @Override
    public boolean updateScreenManager(ScreenManager screenManager) {
        return 1 == this.screenManagerMapper.update(screenManager);
    }

    @Override
    public boolean deleteScreenManager(List<Long> ids) {
        return ids.size() == this.screenManagerMapper.delete(ids);
    }

    @Override
    public Pageable<ScreenManager> findScreenManagerList(String name, Integer pageNo, Integer pageSize) {
        List<ScreenManager> screenManagerList = this.screenManagerMapper.findAll(name,pageNo,pageSize);
        int count = this.screenManagerMapper.getCount(name);
        return new Pageable<ScreenManager>().instanceByPageNo(screenManagerList, count, pageNo, pageSize);
    }

    
	/**
	 * 查询出所有的screen manager信息
	 * @author chenxiang
	 * @date 2014-7-14 下午1:30:04 
	 */
	@Override
	public List<ScreenManager> findAllScreenManager() {
		return this.screenManagerMapper.findAllScreenManager();
	}
}
