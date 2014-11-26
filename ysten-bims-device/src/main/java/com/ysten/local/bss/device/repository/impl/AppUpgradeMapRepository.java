
package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.repository.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.AppUpgradeMap;
import com.ysten.local.bss.device.repository.IAppUpgradeMapRepository;

/**  
 * @author cwang
 * @version 
 * 2014-5-11 下午11:30:54  
 * 
 */
@Repository
public class AppUpgradeMapRepository implements IAppUpgradeMapRepository {
	@Autowired
	private AppUpgradeMapMapper appUpgradeMapMapper;
	@Autowired
	private UserAppUpgradeMapMapper userAppUpgradeMapMapper;
    @Autowired
    private DeviceGroupMapper deviceGroupMapper;
    @Autowired
    private UserGroupMapper UserGroupMapper;
	@Override
	public List<AppUpgradeMap>  findpUpgradeMapByGroupIdAndType(Long groupId,String type) {
		return this.appUpgradeMapMapper.findByGroupIdAndType(groupId, type);
	}

	@Override
	public boolean save(AppUpgradeMap appUpgradeMap) {
		return 1 == this.appUpgradeMapMapper.save(appUpgradeMap);
	}

	@Override
	public boolean deleteByUpgradeId(List<Long> upgradeIds) {
		this.appUpgradeMapMapper.deleteByUpgradeId(upgradeIds);
		this.userAppUpgradeMapMapper.deleteByUpgradeId(upgradeIds);
		return true;
	}

	@Override
	public void deleteAppUpgradeMapByGroupId(Long id) {
		this.appUpgradeMapMapper.deleteAppUpgradeMapByGroupId(id);
	}

	@Override
	public List<AppUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId,String type) {
		return this.appUpgradeMapMapper.findMapByUpgradeIdAndType(upgradeId, type);
	}

	@Override
	public void deleteAppUpgradeMapByCode(String deviceCode) {
		this.appUpgradeMapMapper.deleteAppUpgradeMapByCode(deviceCode);
	}

    @Override
   public  List<DeviceGroup> findDeviceGroupByAppIdAndType(String type){
        return deviceGroupMapper.findDeviceGroupByAppIdAndType(type);
    }

    @Override
    public  List<UserGroup> findUserGroupByAppIdAndType(String type){
        return UserGroupMapper.findUserGroupByAppIdAndType(type);
    }

    @Override
    public boolean deleteAppUpgradeMapByUpgradeId(Long upgradeId) {
          return  appUpgradeMapMapper.deleteAppUpgradeMapByUpgradeId(upgradeId)>=0;
    }

    @Override
    public boolean deleteUserAppUpgradeMapByUpgradeId(Long upgradeId) {
         return  userAppUpgradeMapMapper.deleteUserAppUpgradeMapByUpgradeId(upgradeId)>=0;
    }

    @Override
    public List<AppUpgradeMap> findAppUpgradeMapByDeviceCode(String deviceCode) {
        return this.appUpgradeMapMapper.findAppUpgradeMapByYstenId(deviceCode);
    }

	@Override
	public AppUpgradeMap getByYstenIdAndUpgradeId(String ystenId, long upgradeId) {
		return this.appUpgradeMapMapper.getByYstenIdAndUpgradeId(ystenId, upgradeId);
	}

}

