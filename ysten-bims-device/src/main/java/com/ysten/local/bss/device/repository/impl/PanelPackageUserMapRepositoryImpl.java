package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.cache.annotation.MethodFlushBootsrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ysten.local.bss.device.domain.PanelPackageUserMap;
import com.ysten.local.bss.device.repository.IPanelPackageUserMapRepository;
import com.ysten.local.bss.device.repository.mapper.PanelPackageUserMapMapper;

/**
 * 
 * @author
 *
 */
@Repository
public class PanelPackageUserMapRepositoryImpl implements IPanelPackageUserMapRepository{

    @Autowired
	private PanelPackageUserMapMapper panelPackageUserMapMapper;
	

	@Override
    @MethodFlushBootsrap
	public void deletePanelUserMapByUserGroupId(Long userGroupId) {
		this.panelPackageUserMapMapper.deleteMapByUserGroupId(userGroupId);
	}

	@Override
    @MethodFlushBootsrap
	public void deletePanelUserMapByUserCode(String code) {
		this.panelPackageUserMapMapper.deleteMapByUserCode(code);
	}


    @Override
    @MethodFlushBootsrap
    public void bulkSaveUserPanelMap(List<PanelPackageUserMap> list) {
        this.panelPackageUserMapMapper.bulkSaveUserPanelMap(list);
    }

	@Override
    @MethodFlushBootsrap
	public boolean saveUserMap(PanelPackageUserMap panelPackageUserMap) {
		return 1 == this.panelPackageUserMapMapper.insert(panelPackageUserMap);
	}

	/*@Override
    @MethodFlushBootsrap
	public void deletePanelUserMapByPanelId(Long panelPackageId) {
		this.panelPackageUserMapMapper.deleteByPanelId(panelPackageId);
	}*/

	@Override
	public List<PanelPackageUserMap> findPanelPackageUserMapByPanelIdAndType(
			Long panelPackageId, String type) {
		return this.panelPackageUserMapMapper.findMapByPanelIdAndType(panelPackageId, type);
	}


    @Override
    public PanelPackageUserMap getMapByUserGroupId(Long userGroupId) {
        return this.panelPackageUserMapMapper.getMapByUserGroupId(userGroupId);
    }

    @Override
    public PanelPackageUserMap getMapByUserCode(String code) {
        return this.panelPackageUserMapMapper.getMapByUserCode(code);
    }
}
