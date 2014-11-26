package com.ysten.local.bss.device.repository.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.UserActivateSum;
import com.ysten.local.bss.device.domain.UserActivateSum.SyncType;
import com.ysten.local.bss.device.repository.IActivateSumRepository;
import com.ysten.local.bss.device.repository.mapper.UserActivateSumMapper;
import com.ysten.utils.page.Pageable;

@Repository
public class ActivateSumRepositoryImpl implements IActivateSumRepository{

	@Autowired
	private UserActivateSumMapper userActivateSumMapper;
//	@Autowired
//	private DeviceCustomerAccountMapMapper deviceCustomerAccountMapMapper;
	@Override
	public boolean saveUserActivateSum(UserActivateSum userActivateSum) {
		return 1 == userActivateSumMapper.save(userActivateSum);
	}

	@Override
	public boolean saveUserActivateSumList(List<UserActivateSum> userActivateSumList) {
		return userActivateSumList.size() == this.userActivateSumMapper.saveList(userActivateSumList);
	}

	@Override
	public List<UserActivateSum> findUserActivateSumBySync(String sync) {
		return this.userActivateSumMapper.getBySync(sync);
	}

	@Override
	public boolean updateUserActivateSumSyncState(int syncState, Date syncDate, List<UserActivateSum> userActivateSumList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", userActivateSumList);
		map.put("sync", syncState);
		map.put("syncDate", syncDate);
		
		return userActivateSumList.size() == this.userActivateSumMapper.updateSyncStateList(map);
	}

	@Override
	public boolean updateUserActivateSumSyncState(UserActivateSum userActivateSum) {
		return 1 == this.userActivateSumMapper.updateSyncState(userActivateSum);
	}

	@Override
	public Pageable<UserActivateSum> getUserActivateSumList(String date,String province,SyncType sync,int pageNo, int pageSize) {
		List<UserActivateSum> usr = userActivateSumMapper.getList(date,province,sync,pageNo,pageSize);
		int total = this.userActivateSumMapper.getCount(date,province,sync,pageNo,pageSize);
        return new Pageable<UserActivateSum>().instanceByPageNo(usr, total, pageNo, pageSize);
	}
	@Override
	public Pageable<UserActivateSum> getAll(int pageNo, int pageSize) {
		List<UserActivateSum> usr = userActivateSumMapper.getAll(pageNo, pageSize);
        int total = this.userActivateSumMapper.getCounts();
        return new Pageable<UserActivateSum>().instanceByPageNo(usr, total, pageNo, pageSize);		
	}

	@Override
	public List<UserActivateSum> findAllUserActiveSum() {
		return userActivateSumMapper.getAll(null, null);
	}

//	@Override
//	public Long getCountByUidDid(Date end, List<Long> customerIds,
//			List<Long> deviceIds) {
//		return this.deviceCustomerAccountMapMapper.getCountByUidDid(end, customerIds, deviceIds);
//	}
//	@Override
//	public Long getCountByCreateDateAndUidDid(Date start, Date end,
//			List<Long> customerIds, List<Long> deviceIds) {
//		return this.deviceCustomerAccountMapMapper.getCountByCreateDateAndUidDid(start, end, customerIds, deviceIds);
//	}
//	@Override
//	public Long getCountByCityAndDeviceType(Date start, Date end, int province,
//			String source, String state, int deviceType, long cityId) {
//		return this.deviceCustomerAccountMapMapper.getCountByCityAndDeviceType(start, end, province, source, state, deviceType, cityId);
//	}
//	@Override
//	public Long getCountByRegion(Date start, Date end, long city, int province,
//			String source) {
//		return this.deviceCustomerAccountMapMapper.getCountByRegion(start, end, city, province, source);
//	}
//	@Override
//	public Long getCountByAera(Date  start,Date  end,int province,String source){
//		return this.deviceCustomerAccountMapMapper.getCountByAera(start,end,province,source);
//	}
//	@Override
//	public List<DeviceCustomerAccountMap> getByCustomerId(Long customerId) {
//		return this.deviceCustomerAccountMapMapper.getByCustomerId(customerId);
//	}
}
