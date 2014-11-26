package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.local.bss.device.domain.ApkUpgradeMap;
import com.ysten.local.bss.device.repository.IApkUpgradeMapRepository;
import com.ysten.local.bss.device.repository.mapper.ApkUpgradeMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApkUpgradeMapRepositoryImpl implements IApkUpgradeMapRepository {
    @Autowired
    private ApkUpgradeMapMapper apkUpgradeMapMapper;

    @Override
    public ApkUpgradeMap getApkUpgradeMapYstenId(String ystenId, long upgradeId) {
        ApkUpgradeMap apkUpgradeMap = null;
        apkUpgradeMap = this.apkUpgradeMapMapper.getByYstenIdAndUpgradeId(ystenId, upgradeId);
        return apkUpgradeMap;
    }

    @Override
    public ApkUpgradeMap getApkUpgradeMapByGroupId(long groupId, long upgradeId) {
        ApkUpgradeMap apkUpgradeMap = null;
        apkUpgradeMap = this.apkUpgradeMapMapper.getByGroupIdAndUpgradeId(groupId, upgradeId);
        return apkUpgradeMap;
    }

	@Override
	public List<ApkUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId,
			String type) {
		return this.apkUpgradeMapMapper.findMapByUpgradeIdAndType(upgradeId, type);
	}

	@Override
	public boolean deleteApkUpgradeMapByType(Long upgradeId, String type) {
		this.apkUpgradeMapMapper.deleteByUpgradeIdAndType(upgradeId, type);
		return true;
	}

	@Override
	public void bulkSaveApkUpgradeMap(List<ApkUpgradeMap> list) {
		this.apkUpgradeMapMapper.bulkSaveApkUpgradeMap(list);
	}

	@Override
	public void deleteMapByYstenIdsAndUpgradeId(String[] ystenIds,
			long upgradeId) {
		this.apkUpgradeMapMapper.deleteMapByYstenIdsAndUpgradeId(ystenIds, upgradeId);
	}

    @Override
    public void deleteApkUpgradeMapByYstenId(String ystenId) {
        this.apkUpgradeMapMapper.deleteApkUpgradeMapByYstenId(ystenId);
    }

    @Override
    public boolean saveApkUpgradeMap(ApkUpgradeMap apkUpgradeMap) {
        return 1 == this.apkUpgradeMapMapper.save(apkUpgradeMap);
    }

    @Override
    public void deleteApkUpgradeMapByGroupId(Long id) {
        this.apkUpgradeMapMapper.deleteUpgradeTaskMapByGroupId(id);
    }

    @Override
	public boolean deleteUpgradeTaskMap(List<Long> ids) {
//		int num = this.apkUpgradeMapMapper.deleteByUpgradeId(ids);
//		return num == ids.size();
		this.apkUpgradeMapMapper.deleteByUpgradeId(ids);
		return true;
	}
}
