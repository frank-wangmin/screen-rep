package com.ysten.local.bss.device.remote.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.remote.domain.RemoteUserActivateSum;
import com.ysten.local.bss.device.remote.repository.IActivateSumRemoteRepository;
import com.ysten.local.bss.device.remote.repository.mapper.RemoteUserActivateSumMapper;

@Repository
public class ActivateSumRemoteRepositoryImpl implements IActivateSumRemoteRepository{

	@Autowired
	private RemoteUserActivateSumMapper remoteUserActivateSumMapper;
	
	@Override
	public boolean save(RemoteUserActivateSum remoteUserActivateSum) {
		return 1 == remoteUserActivateSumMapper.save(remoteUserActivateSum);
	}

	@Override
	public boolean saveList(List<RemoteUserActivateSum> remoteUserActivateSumList) {
		return remoteUserActivateSumList.size() == this.remoteUserActivateSumMapper.saveList(remoteUserActivateSumList);
	}

}
