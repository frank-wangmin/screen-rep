package com.ysten.local.bss.device.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.DeviceUpgradeMap;
import com.ysten.local.bss.device.domain.DeviceVendor;
import com.ysten.local.bss.device.domain.UpgradeTask;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserUpgradeMap;
import com.ysten.local.bss.device.repository.IUpgradeTaskRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceUpgradeMapMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceVendorMapper;
import com.ysten.local.bss.device.repository.mapper.UpgradeTaskMapper;
import com.ysten.local.bss.device.repository.mapper.UserGroupMapper;
import com.ysten.local.bss.device.repository.mapper.UserUpgradeMapMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;

@Repository
public class UpgradeTaskRepositoryImpl implements IUpgradeTaskRepository {

	@Autowired
	private UpgradeTaskMapper upgradeTaskMapper;
	@Autowired
	private DeviceUpgradeMapMapper deviceUpgradeMapMapper;
	@Autowired
	private DeviceGroupMapper deviceGroupMapper;
	@Autowired
	private UserGroupMapper userGroupMapper;
	@Autowired
	private DeviceVendorMapper deviceVendorMapper;
	@Autowired
	private UserUpgradeMapMapper userUpgradeMapMapper;
	@Override
	public Pageable<UpgradeTask> getListByCondition(
			Long softwarePackageId, Long softCodeId,
			Integer pageNo, Integer pageSize) {
		List<UpgradeTask> lists = this.upgradeTaskMapper.getListByCondition(softwarePackageId, softCodeId, pageNo, pageSize);
		if(lists!=null && lists.size()>0){
			for(UpgradeTask sn : lists){
				/*List<DeviceUpgradeMap> deivceMapList = this.deviceUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "DEVICE");
				StringBuffer dsb = new StringBuffer();
				for(DeviceUpgradeMap dnm : deivceMapList){
					dsb.append(dnm.getYstenId()).append(",");
				}*/
			/*	List<UserUpgradeMap> userMapList = this.userUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "USER");
				StringBuffer usb = new StringBuffer();
				for(UserUpgradeMap unm : userMapList){
					usb.append(unm.getUserId()).append(",");
				}*/
				List<DeviceUpgradeMap> groupMapList = this.deviceUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "GROUP");
				StringBuffer msb = new StringBuffer();
				for(DeviceUpgradeMap dnm : groupMapList){
					DeviceGroup deviceGroup = this.deviceGroupMapper.getById(dnm.getDeviceGroupId());
					msb.append(deviceGroup.getName()).append(",");
				}
				/*List<UserUpgradeMap> userGroupMapList = this.userUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "GROUP");
				StringBuffer umsb = new StringBuffer();
				for(UserUpgradeMap unm : userGroupMapList){
					UserGroup userGroup = this.userGroupMapper.getById(unm.getUserGroupId());
					umsb.append(userGroup.getName()).append(",");
				}*/
				StringBuffer vsb = new StringBuffer();
				List<Long> vendorIds = new ArrayList<Long>();
				if (StringUtils.isNotBlank(sn.getVendorIds())) {
					vendorIds = StringUtil.splitToLong(sn.getVendorIds());			
				 }
				for(int i=0;i<vendorIds.size();i++){
					DeviceVendor deviceVendor = this.deviceVendorMapper.getById(vendorIds.get(i));
					vsb.append(deviceVendor.getName()).append(",");
				}
				/*if(dsb.length()>0){
					sn.setDeviceCode(dsb.substring(0,dsb.length()-1).toString());
				}*/
				if(msb.length()>0){
					sn.setDeviceGroupIds(msb.substring(0,msb.length()-1).toString());
				}
				/*if(usb.length()>0){
					sn.setUserIds(usb.substring(0,usb.length()-1).toString());
				}*/
				/*if(umsb.length()>0){
					sn.setUserGroupIds(umsb.substring(0,umsb.length()-1).toString());
				}*/
				if(vsb.length()>0){
					sn.setVendorIds(vsb.substring(0,vsb.length()-1).toString());
				}
			}
		}
		int total = this.upgradeTaskMapper.getCountByCondition(softwarePackageId, softCodeId);
		return new Pageable<UpgradeTask>().instanceByPageNo(lists, total, pageNo, pageSize);
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {		
		this.deviceUpgradeMapMapper.deleteByUpgradeId(ids);
		this.userUpgradeMapMapper.deleteByUpgradeId(ids);
		return ids.size() == this.upgradeTaskMapper.delete(ids);		
	}
	@Override
    public boolean deleteUpgradeTaskById(Long id) {
        return 1 == this.upgradeTaskMapper.deleteById(id);
    }
	@Override
	public UpgradeTask getByVersionSeq(int versionSeq) {
		return this.upgradeTaskMapper.getByVersionSeq(versionSeq);
	}

	@Override
	public boolean save(UpgradeTask upgradeTask) {		
		return 1 == this.upgradeTaskMapper.save(upgradeTask);
	}

	@Override
	public boolean updateById(UpgradeTask upgradeTask) {		
		return 1 == this.upgradeTaskMapper.updateById(upgradeTask);
	}

	@Override
	public boolean saveDeviceUpgradeMap(DeviceUpgradeMap deviceUpgradeMap) {
		return 1 == this.deviceUpgradeMapMapper.save(deviceUpgradeMap);
	}

	@Override
	public boolean updateDeviceUpgradeMap(DeviceUpgradeMap deviceUpgradeMap) {
		return 1 == this.deviceUpgradeMapMapper.updateByUpgradeId(deviceUpgradeMap);
	}

	@Override
	public boolean deleteDeviceUpgradeMap(List<Long> upgradeIds) {
		this.deviceUpgradeMapMapper.deleteByUpgradeId(upgradeIds);
		return true;
	}

	@Override
	public UpgradeTask getById(long id) {
		List<DeviceUpgradeMap> groupMapList = this.deviceUpgradeMapMapper.findMapByUpgradeIdAndType(id, "GROUP");
		StringBuffer msb = new StringBuffer();
		for(DeviceUpgradeMap dnm : groupMapList){
			msb.append(dnm.getDeviceGroupId()).append(",");
		}
		UpgradeTask task = this.upgradeTaskMapper.getById(id);
		if(msb.length()>0){
			task.setDeviceGroupIds(msb.substring(0,msb.length()-1).toString());
		}
		return task;
	}

	@Override
	public void deleteUpgradeTaskMapByGroupId(Long id) {
		this.deviceUpgradeMapMapper.deleteUpgradeTaskMapByGroupId(id);
	}

	@Override
	public void deleteUserUpgradeMapByUserGroupId(Long userGroupId) {
		this.userUpgradeMapMapper.deleteUserUpgradeMapByUserGroupId(userGroupId);
	}

	@Override
	public boolean saveUserUpgradeMap(UserUpgradeMap userUpgradeMap) {
		return  1== this.userUpgradeMapMapper.save(userUpgradeMap);
	}

	@Override
	public boolean deleteUserUpgradeMap(List<Long> upgradeIds) {
		this.userUpgradeMapMapper.deleteByUpgradeId(upgradeIds);
		return true;
	}

	@Override
	public List<DeviceUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId,String type) {
		return this.deviceUpgradeMapMapper.findMapByUpgradeIdAndType(upgradeId, type);
	}

	@Override
	public List<UserUpgradeMap> findUserMapByUpgradeIdAndType(Long upgradeId,String type) {
		return this.userUpgradeMapMapper.findMapByUpgradeIdAndType(upgradeId, type);
	}

	@Override
	public void deleteUserUpgradeMapByUserId(String userId) {
		this.userUpgradeMapMapper.deleteUserUpgradeMapByUserId(userId);
	}

	@Override
	public void deleteUpgradeTaskMapByCode(String deviceCode) {
		this.deviceUpgradeMapMapper.deleteUpgradeTaskMapByCode(deviceCode);
	}

	@Override
	public DeviceUpgradeMap getByGroupId(Long deviceGroupId) {
		return this.deviceUpgradeMapMapper.getByGroupId(deviceGroupId);
	}

	@Override
	public UserUpgradeMap getUserUpgradeMapByUserGroupId(Long userGroupId) {
		return this.userUpgradeMapMapper.getUserUpgradeMapByUserGroupId(userGroupId);
	}

	@Override
	public UserUpgradeMap getUserUpgradeMapByUserId(String userId) {
		return this.userUpgradeMapMapper.getUserUpgradeMapByUserId(userId);
	}

    @Override
    public List<DeviceUpgradeMap> findMapByYstenId(String ystenId) {
        return this.deviceUpgradeMapMapper.findMapByYstenId(ystenId);
    }

	@Override
	public List<UpgradeTask> findUpgradeTasksByCondition(
			Long softwarePackageId, Long softCodeId) {
		return this.upgradeTaskMapper.getListByCondition(softwarePackageId, softCodeId, null, null);
	}

    @Override
    public List<UpgradeTask> findUpgradeTaskByYstenIdOrGroupId(String ystenId, Long groupId) {
        return this.upgradeTaskMapper.findUpgradeTaskByYstenIdOrGroupId(ystenId, groupId);
    }

    @Override
    public List<UpgradeTask> findAllUpgradeTask() {
        return this.upgradeTaskMapper.findAllUpgradeTask();
    }

    @Override
	public Pageable<UpgradeTask> findUpgradeTaskListByCondition(
			String softwarePackageName, String softCodeName, Integer pageNo,
			Integer pageSize) {
		List<UpgradeTask> lists = this.upgradeTaskMapper.findUpgradeTaskListByCondition(softwarePackageName, softCodeName, pageNo, pageSize);
		if(lists!=null && lists.size()>0){
			for(UpgradeTask sn : lists){
				List<DeviceUpgradeMap> deivceMapList = this.deviceUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "DEVICE");
				StringBuffer dsb = new StringBuffer();
				for(DeviceUpgradeMap dnm : deivceMapList){
					dsb.append(dnm.getYstenId()).append(",");
				}
				List<UserUpgradeMap> userMapList = this.userUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "USER");
				StringBuffer usb = new StringBuffer();
				for(UserUpgradeMap unm : userMapList){
					usb.append(unm.getUserId()).append(",");
				}
				List<DeviceUpgradeMap> groupMapList = this.deviceUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "GROUP");
				StringBuffer msb = new StringBuffer();
				for(DeviceUpgradeMap dnm : groupMapList){
					DeviceGroup deviceGroup = this.deviceGroupMapper.getById(dnm.getDeviceGroupId());
					msb.append(deviceGroup.getName()).append(",");
				}
				List<UserUpgradeMap> userGroupMapList = this.userUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "GROUP");
				StringBuffer umsb = new StringBuffer();
				for(UserUpgradeMap unm : userGroupMapList){
					UserGroup userGroup = this.userGroupMapper.getById(unm.getUserGroupId());
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
		int total = this.upgradeTaskMapper.getCountBySoftCodeAndPackage(softwarePackageName, softCodeName);
		return new Pageable<UpgradeTask>().instanceByPageNo(lists, total, pageNo, pageSize);
	}
    @Override
    public List<UserUpgradeMap> findUserMapBySoftCodeIdAndVersionSeq(Long softCodeId, int versionSeq){
        return this.upgradeTaskMapper.getUserMapBySoftCodeIdAndVersionSeq(softCodeId, versionSeq);
    }

	@Override
	public boolean deleteDeviceUpgradeMapByType(Long upgradeId, String type) {
		this.deviceUpgradeMapMapper.deleteByUpgradeIdAndType(upgradeId, type);
		return true;
	}

	@Override
	public DeviceUpgradeMap getByYstenIdAndUpgradeId(String ystenId,
			long upgradeId) {
		return this.deviceUpgradeMapMapper.getByYstenIdAndUpgradeId(ystenId, upgradeId);
	}

	@Override
	public void bulkSaveDeviceUpgradeMap(List<DeviceUpgradeMap> list) {
		this.deviceUpgradeMapMapper.bulkSaveDeviceUpgradeMap(list);
	}

	@Override
	public void deleteMapByYstenIdsAndUpgradeId(String[] ystenIds, long upgradeId) {
		this.deviceUpgradeMapMapper.deleteMapByYstenIdsAndUpgradeId(ystenIds, upgradeId);
	}
}
