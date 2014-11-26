package com.ysten.local.bss.screen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.screen.domain.ScreenManager;
import com.ysten.local.bss.screen.repository.IScreenManagerRepository;
import com.ysten.local.bss.screen.service.IScreenManagerService;

@Service
public class ScreenManagerServiceImpl implements IScreenManagerService {

	@Autowired
	private IScreenManagerRepository screenManagerRepository;

	@Override
	public List<ScreenManager> getAllScreenManager(int areaId) {
		return this.screenManagerRepository.getAllScreenManager(areaId);
	}

	@Override
	public List<String> getAllArea() {
		return this.screenManagerRepository.getAllArea();
	}

	/**
	 * 查询出所有的screen manager信息
	 * @author chenxiang
	 * @date 2014-7-14 下午1:28:16 
	 */
	@Override
	public List<ScreenManager> findAllScreenManager() {
		return this.screenManagerRepository.findAllScreenManager();
	}

}
