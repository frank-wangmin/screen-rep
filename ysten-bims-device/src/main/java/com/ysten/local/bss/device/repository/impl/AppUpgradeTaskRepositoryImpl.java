package com.ysten.local.bss.device.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.local.bss.device.domain.AppUpgradeMap;
import com.ysten.local.bss.device.domain.AppUpgradeTask;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.DeviceVendor;
import com.ysten.local.bss.device.domain.UserAppUpgradeMap;
import com.ysten.local.bss.device.repository.IAppUpgradeTaskRepository;
import com.ysten.local.bss.device.repository.mapper.AppSoftwarePackageMapper;
import com.ysten.local.bss.device.repository.mapper.AppUpgradeMapMapper;
import com.ysten.local.bss.device.repository.mapper.AppUpgradeTaskMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceVendorMapper;
import com.ysten.local.bss.device.repository.mapper.UserAppUpgradeMapMapper;
import com.ysten.local.bss.device.repository.mapper.UserGroupMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;

@Repository
public class AppUpgradeTaskRepositoryImpl implements IAppUpgradeTaskRepository {


    @Autowired
	private AppUpgradeTaskMapper appUpgradeTaskMapper;
	@Autowired
	private AppUpgradeMapMapper appUpgradeMapMapper;
	@Autowired
	private DeviceGroupMapper deviceGroupMapper;
	@Autowired
	private DeviceVendorMapper deviceVendorMapper;
	@Autowired
	private UserAppUpgradeMapMapper userAppUpgradeMapMapper;
	@Autowired
	private UserGroupMapper userGroupMapper;
	@Autowired
    private AppSoftwarePackageMapper appSoftwarePackageMapper;
	@Override
	public Pageable<AppUpgradeTask> findAppUpgradeTaskListByCondition(
			Long softwarePackageId, Long softCodeId,
			Integer pageNo, Integer pageSize) {
		List<AppUpgradeTask> lists = this.appUpgradeTaskMapper.getListByCondition(softwarePackageId, softCodeId, pageNo, pageSize);
		if(lists!=null && lists.size()>0){
			for(AppUpgradeTask sn : lists){
				List<AppUpgradeMap> deivceMapList = this.appUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "DEVICE");
				StringBuffer dsb = new StringBuffer();
				for(AppUpgradeMap dnm : deivceMapList){
					dsb.append(dnm.getYstenId()).append(",");
				}
				List<UserAppUpgradeMap> userMapList = this.userAppUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "USER");
				StringBuffer usb = new StringBuffer();
				for(UserAppUpgradeMap dnm : userMapList){
					usb.append(dnm.getUserId()).append(",");
				}
				List<AppUpgradeMap> groupMapList = this.appUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "GROUP");
				StringBuffer msb = new StringBuffer();
				for(AppUpgradeMap dnm : groupMapList){
					DeviceGroup deviceGroup = this.deviceGroupMapper.getById(dnm.getDeviceGroupId());
					msb.append(deviceGroup.getName()).append(",");
				}
				StringBuffer umsb = new StringBuffer();
				List<UserAppUpgradeMap> userGroupMapList = this.userAppUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "GROUP");
				for(UserAppUpgradeMap dnm : userGroupMapList){
					UserGroup userGroup = this.userGroupMapper.getById(dnm.getUserGroupId());
					umsb.append(userGroup.getName()).append(",");
				}
				StringBuffer vsb = new StringBuffer();
				List<Long> vendorIds = new ArrayList<Long>();
				if (StringUtils.isNotBlank(sn.getVendorIds())) {
					vendorIds = StringUtil.splitToLong(sn.getVendorIds());			
				 }
				for(int i=0;i<vendorIds.size();i++){
					DeviceVendor deviceVendor = this.deviceVendorMapper.getById(vendorIds.get(i));
					vsb.append(deviceVendor.getName()).append(",");
				}
				if(dsb.length()>0){
					sn.setDeviceCode(dsb.substring(0,dsb.length()-1).toString());
				}
				if(msb.length()>0){
					sn.setDeviceGroupIds(msb.substring(0,msb.length()-1).toString());
				}				
				if(usb.length()>0){
					sn.setUserIds(usb.substring(0,usb.length()-1).toString());
				}
				if(umsb.length()>0){
					sn.setUserGroupIds(umsb.substring(0,umsb.length()-1).toString());
				}
				if(vsb.length()>0){
					sn.setVendorIds(vsb.substring(0,vsb.length()-1).toString());
				}
			}
		}
		int total = this.appUpgradeTaskMapper.getCountByCondition(softwarePackageId, softCodeId);
		return new Pageable<AppUpgradeTask>().instanceByPageNo(lists, total, pageNo, pageSize);
	}

	@Override
	public boolean deleteAppUpgradeTaskByIds(List<Long> ids) {		
		this.appUpgradeMapMapper.deleteByUpgradeId(ids);
		this.userAppUpgradeMapMapper.deleteByUpgradeId(ids);
		return ids.size() == this.appUpgradeTaskMapper.delete(ids);		
	}
	@Override
	public AppUpgradeTask getAppUpgradeTaskById(long id) {
		List<AppUpgradeMap> groupMapList = this.appUpgradeMapMapper.findMapByUpgradeIdAndType(id, "GROUP");
		StringBuffer msb = new StringBuffer();
		for(AppUpgradeMap dnm : groupMapList){
			msb.append(dnm.getDeviceGroupId()).append(",");
		}
		AppUpgradeTask task = this.appUpgradeTaskMapper.getById(id);
		if(msb.length()>0){
			task.setDeviceGroupIds(msb.substring(0,msb.length()-1).toString());
		}
		return task;
	}

	@Override
	public boolean saveAppUpgradeTask(AppUpgradeTask appUpgradeTask) {
		return 1 == this.appUpgradeTaskMapper.save(appUpgradeTask);
	}
	
	@Override
	public boolean updateAppUpgradeTask(AppUpgradeTask appUpgradeTask) {
		return 1 == this.appUpgradeTaskMapper.updateById(appUpgradeTask);
	}
//
//	@Override
//	public UpgradeTask getByVersionSeq(int versionSeq) {
//		return this.upgradeTaskMapper.getByVersionSeq(versionSeq);
//	}

	@Override
	public void deleteUserUpgradeMapByUserGroupId(Long userGroupId) {
		this.userAppUpgradeMapMapper.deleteUserUpgradeMapByUserGroupId(userGroupId);
	}

	@Override
	public boolean saveUserAppMap(UserAppUpgradeMap userAppUpgradeMap) {
		return 1 == this.userAppUpgradeMapMapper.save(userAppUpgradeMap);
	}

	@Override
	public List<UserAppUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId,String type) {
		return this.userAppUpgradeMapMapper.findMapByUpgradeIdAndType(upgradeId, type);
	}

	@Override
	public void deleteAppUpgradeMapByUserId(String userId) {
		this.userAppUpgradeMapMapper.deleteAppUpgradeMapByUserId(userId);
	}

	@Override
	public List<UserAppUpgradeMap> getUserUpgradeMapByUserGroupId(Long userGroupId) {
		return this.userAppUpgradeMapMapper.getUserUpgradeMapByUserGroupId(userGroupId);
	}

	@Override
	public List<UserAppUpgradeMap> getUserUpgradeMapByUserId(String userId) {
		return this.userAppUpgradeMapMapper.getUserUpgradeMapByUserId(userId);
	}

    @Override
    public List<AppUpgradeMap> findMapByUpgradeId(Long upgradeId) {
        return this.appUpgradeMapMapper.findMapByUpgradeId(upgradeId);
    }

    @Override
    public List<UserAppUpgradeMap> findUserMapByUpgradeId(Long upgradeId) {
        return this.userAppUpgradeMapMapper.findUserMapByUpgradeId(upgradeId);
    }

    @Override
    public AppSoftwarePackage findLatestSoftwarePackageByIds(List<Long> upgradeIds, int versionSeq, int sdkVersion,long softwareCodeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("upgradeIds", upgradeIds);
        map.put("appVersionSeq", versionSeq);
        map.put("softwareCodeId", softwareCodeId);
        map.put("sdkVersion", sdkVersion);
//        return this.appUpgradeTaskMapper.findLatestSoftwarePackageByIds(map);
        return this.appSoftwarePackageMapper.findLatestSoftwarePackageByIds(map);
    }

	@Override
	public Pageable<AppUpgradeTask> findAppUpgradeTaskListByCondition(
			String softwarePackageName, String softCodeName, Integer pageNo,
			Integer pageSize) {
		List<AppUpgradeTask> lists = this.appUpgradeTaskMapper.findAppUpgradeTaskListByCondition(softwarePackageName, softCodeName, pageNo, pageSize);
		if(lists!=null && lists.size()>0){
			for(AppUpgradeTask sn : lists){
				List<AppUpgradeMap> deivceMapList = this.appUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "DEVICE");
				StringBuffer dsb = new StringBuffer();
				for(AppUpgradeMap dnm : deivceMapList){
					dsb.append(dnm.getYstenId()).append(",");
				}
				List<UserAppUpgradeMap> userMapList = this.userAppUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "USER");
				StringBuffer usb = new StringBuffer();
				for(UserAppUpgradeMap dnm : userMapList){
					usb.append(dnm.getUserId()).append(",");
				}
				List<AppUpgradeMap> groupMapList = this.appUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "GROUP");
				StringBuffer msb = new StringBuffer();
				for(AppUpgradeMap dnm : groupMapList){
					DeviceGroup deviceGroup = this.deviceGroupMapper.getById(dnm.getDeviceGroupId());
					msb.append(deviceGroup.getName()).append(",");
				}
				StringBuffer umsb = new StringBuffer();
				List<UserAppUpgradeMap> userGroupMapList = this.userAppUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "GROUP");
				for(UserAppUpgradeMap dnm : userGroupMapList){
					UserGroup userGroup = this.userGroupMapper.getById(dnm.getUserGroupId());
					umsb.append(userGroup.getName()).append(",");
				}
				StringBuffer vsb = new StringBuffer();
				List<Long> vendorIds = new ArrayList<Long>();
				if (StringUtils.isNotBlank(sn.getVendorIds())) {
					vendorIds = StringUtil.splitToLong(sn.getVendorIds());			
				 }
				for(int i=0;i<vendorIds.size();i++){
					DeviceVendor deviceVendor = this.deviceVendorMapper.getById(vendorIds.get(i));
					vsb.append(deviceVendor.getName()).append(",");
				}
				if(dsb.length()>0){
					sn.setDeviceCode(dsb.substring(0,dsb.length()-1).toString());
				}
				if(msb.length()>0){
					sn.setDeviceGroupIds(msb.substring(0,msb.length()-1).toString());
				}				
				if(usb.length()>0){
					sn.setUserIds(usb.substring(0,usb.length()-1).toString());
				}
				if(umsb.length()>0){
					sn.setUserGroupIds(umsb.substring(0,umsb.length()-1).toString());
				}
				if(vsb.length()>0){
					sn.setVendorIds(vsb.substring(0,vsb.length()-1).toString());
				}
			}
		}
		int total = this.appUpgradeTaskMapper.getCountBySoftCodeAndPackage(softwarePackageName, softCodeName);
		return new Pageable<AppUpgradeTask>().instanceByPageNo(lists, total, pageNo, pageSize);
	}

}
