package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.cache.annotation.MethodFlushBootsrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ysten.local.bss.device.domain.PanelPackageDeviceMap;
import com.ysten.local.bss.device.repository.IPanelPackageDeviceMapRepository;
import com.ysten.local.bss.device.repository.mapper.PanelPackageDeviceMapMapper;

/**
 * 
 * @author
 *
 */
@Repository
public class PanelPackageDeviceMapRepositoryImpl implements IPanelPackageDeviceMapRepository{
	
	@Autowired
	private PanelPackageDeviceMapMapper panelPackageDeviceMapMapper;
	
	@Override
    @MethodFlushBootsrap
	public void deletePanelDeviceMapByDeviceGroupId(Long deviceGroupId) {
		this.panelPackageDeviceMapMapper.deletePanelDeviceMapByGroupId(deviceGroupId);
	}

	@Override
    @MethodFlushBootsrap
	public void deletePanelDeviceMapByCode(String deviceCode) {
		this.panelPackageDeviceMapMapper.deletePanelDeviceMapByCode(deviceCode);
	}

	@Override
    @MethodFlushBootsrap
	public boolean saveDeviceMap(PanelPackageDeviceMap panelPackageDeviceMap) {
		return 1 == this.panelPackageDeviceMapMapper.insert(panelPackageDeviceMap);
	}

	@Override
    @MethodFlushBootsrap
	public void deletePanelPackageMapByPanelId(Long panelPackageId,String type,String tableName) {
		this.panelPackageDeviceMapMapper.deletePanelPackageMapByPanelId(panelPackageId,type,tableName);
	}
	
	@Override
	public List<PanelPackageDeviceMap> findPanelPackageDeviceMapByPanelIdAndType(
			Long panelPackageId, String type) {
		return this.panelPackageDeviceMapMapper.findMapByPanelIdAndType(panelPackageId, type);
	}

	@Override
	public PanelPackageDeviceMap getPanelDeviceMapByGroupId(Long deviceGroupId) {
		return this.panelPackageDeviceMapMapper.getPanelDeviceMapByGroupId(deviceGroupId);
	}

    @Override
    public PanelPackageDeviceMap getPanelDeviceMapByYstenId(String ystenId){
        return panelPackageDeviceMapMapper.getPanelDeviceMapByYstenId(ystenId);
    }

	@Override
	public boolean updateDeviceMapByYstenId(
			PanelPackageDeviceMap panelPackageDeviceMap) {
		return 1 == this.panelPackageDeviceMapMapper.updateByYstenId(panelPackageDeviceMap);
	}

	@Override
	public void deletePanelDeviceMapByYstenIds(List<String> ystenIds,String tableName,String character) {
		this.panelPackageDeviceMapMapper.deletePanelDeviceMapByYstenIds(ystenIds,tableName,character);
	}

	@Override
	public void bulkSaveDevicePanelMap(List<PanelPackageDeviceMap> list) {
		this.panelPackageDeviceMapMapper.bulkSaveDevicePanelMap(list);
	}

	@Override
	public void deletePanelDeviceMapByGroupIds(List<Long> groupIds,String tableName,String character) {
		this.panelPackageDeviceMapMapper.deletePanelDeviceMapByGroupIds(groupIds,tableName,character);
	}
    
}
