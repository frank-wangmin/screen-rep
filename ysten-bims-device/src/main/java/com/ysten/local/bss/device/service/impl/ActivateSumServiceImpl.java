package com.ysten.local.bss.device.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.device.domain.UserActivateSum;
import com.ysten.local.bss.device.domain.UserActivateSum.SyncType;
import com.ysten.local.bss.device.remote.domain.RemoteUserActivateSum;
import com.ysten.local.bss.device.remote.repository.IActivateSumRemoteRepository;
import com.ysten.local.bss.device.repository.IActivateSumRepository;
import com.ysten.local.bss.device.service.IActivateSumService;

@Service
public class ActivateSumServiceImpl implements IActivateSumService {

	@Autowired
	private IActivateSumRemoteRepository activateSumRemoteRepository;
	@Autowired
	private IActivateSumRepository activateSumRepository;

	@Override
	public boolean saveActivateSumRemote(RemoteUserActivateSum remoteUserActivateSum) {
		return this.activateSumRemoteRepository.save(remoteUserActivateSum);
	}
	
	@Override
	public boolean saveActivateSumRemoteList(List<RemoteUserActivateSum> remoteUserActivateSumList) {
		return this.activateSumRemoteRepository.saveList(remoteUserActivateSumList);
	}

	@Override
	public boolean saveActivateSumList(List<UserActivateSum> userActivateSumList) {
		return this.activateSumRepository.saveUserActivateSumList(userActivateSumList);
	}

	@Override
	public boolean saveActivateSum(UserActivateSum userActivateSum) {
		return this.activateSumRepository.saveUserActivateSum(userActivateSum);
	}

	@Override
	public boolean updateSyncState(List<UserActivateSum> userActivateSumList) {
		return this.activateSumRepository.updateUserActivateSumSyncState(1, new Date(), userActivateSumList);
	}
	
	@Override
	public boolean updateSyncState(UserActivateSum userActivateSum) {
		Date now = new Date();
		userActivateSum.setSync(SyncType.SYNC);
		userActivateSum.setSyncDate(now);
		return this.activateSumRepository.updateUserActivateSumSyncState(userActivateSum);
	}

	@Override
	public List<UserActivateSum> getNotSync() {
		return this.activateSumRepository.findUserActivateSumBySync(SyncType.NOSYNC.getDisplayName());
	}

	@Override
	public void syncData(List<UserActivateSum> notSyncList, List<RemoteUserActivateSum> remoteUserActivateSumList) {
		if(this.saveActivateSumRemoteList(remoteUserActivateSumList)){
			this.updateSyncState(notSyncList);
		}
	}

}
