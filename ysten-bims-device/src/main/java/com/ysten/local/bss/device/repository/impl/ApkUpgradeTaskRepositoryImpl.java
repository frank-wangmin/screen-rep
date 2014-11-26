package com.ysten.local.bss.device.repository.impl;

import com.ysten.local.bss.device.domain.ApkUpgradeMap;
import com.ysten.local.bss.device.domain.ApkUpgradeTask;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.repository.IApkUpgradeTaskRepository;
import com.ysten.local.bss.device.repository.mapper.ApkUpgradeMapMapper;
import com.ysten.local.bss.device.repository.mapper.ApkUpgradeTaskMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapper;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApkUpgradeTaskRepositoryImpl implements IApkUpgradeTaskRepository {
    @Autowired
    private ApkUpgradeMapMapper apkUpgradeMapMapper;
    @Autowired
    private ApkUpgradeTaskMapper apkUpgradeTaskMapper;
	@Autowired
	private DeviceGroupMapper deviceGroupMapper;
    @Override
    public ApkUpgradeTask getById(long id) {
        ApkUpgradeTask apkUpgradeTask = this.apkUpgradeTaskMapper.getById(id);
        return apkUpgradeTask;
    }

	@Override
	public Pageable<ApkUpgradeTask> findApkUpgradeTaskListBySoftCodeAndPackage(
			String softwarePackageName, String softCodeName, Integer pageNo,
			Integer pageSize) {
		List<ApkUpgradeTask> datas = this.apkUpgradeTaskMapper.findApkUpgradeTaskListBySoftCodeAndPackage(softwarePackageName, softCodeName, pageNo, pageSize);
		if(datas!=null && datas.size()>0){
			for(ApkUpgradeTask sn : datas){
				List<ApkUpgradeMap> groupMapList = this.apkUpgradeMapMapper.findMapByUpgradeIdAndType(sn.getId(), "GROUP");
				StringBuffer msb = new StringBuffer();
				for(ApkUpgradeMap dnm : groupMapList){
					DeviceGroup deviceGroup = this.deviceGroupMapper.getById(dnm.getDeviceGroupId());
					msb.append(deviceGroup.getName()).append(",");
				}
				if(msb.length()>0){
					sn.setDeviceGroupIds(msb.substring(0,msb.length()-1).toString());
				}
			}
		}
		int total = this.apkUpgradeTaskMapper.getCountBySoftCodeAndPackage(softwarePackageName, softCodeName);
		return new Pageable<ApkUpgradeTask>().instanceByPageNo(datas, total, pageNo, pageSize);
	}

	@Override
	public boolean save(ApkUpgradeTask apkUpgradeTask) {
		return 1 == this.apkUpgradeTaskMapper.insert(apkUpgradeTask);
	}

	@Override
	public boolean updateById(ApkUpgradeTask apkUpgradeTask) {
		return 1 == this.apkUpgradeTaskMapper.updateByPrimaryKey(apkUpgradeTask);
	}

	@Override
	public ApkUpgradeTask selectByPrimaryKey(long id) {
		return this.apkUpgradeTaskMapper.selectByPrimaryKey(id);
	}

    @Override
    public List<ApkUpgradeTask> findAllUpgradeTask() {
        return this.apkUpgradeTaskMapper.findAllUpgradeTask();
    }

    @Override
    public List<ApkUpgradeTask> findUpgradeTaskByYstenIdOrGroupId(String ystenId, Long groupId) {
        return this.apkUpgradeTaskMapper.findUpgradeTaskByYstenIdOrGroupId(ystenId, groupId);
    }

    @Override
	public boolean deleteById(long id) {
		return 1 == this.apkUpgradeTaskMapper.deleteByPrimaryKey(id);
	}

}
