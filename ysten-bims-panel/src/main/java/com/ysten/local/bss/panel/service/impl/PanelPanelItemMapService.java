
package com.ysten.local.bss.panel.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysten.local.bss.panel.domain.PanelPanelItemMap;
import com.ysten.local.bss.panel.repository.IPanelItemRepository;
import com.ysten.local.bss.panel.repository.IPanelPanelItemMapRepository;
import com.ysten.local.bss.panel.repository.IPanelRepository;
import com.ysten.local.bss.panel.service.IPanelPanelItemMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cwang
 * @version 2014-5-23 下午4:14:52
 */
@Service
public class PanelPanelItemMapService implements IPanelPanelItemMapService {

    @Autowired
    IPanelPanelItemMapRepository panelPanelItemMapRepository;
    @Autowired
    IPanelItemRepository panelItemRepository;
    @Autowired
    IPanelRepository panelRepository;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return panelPanelItemMapRepository.deletePanelPanelItemMap(id);
    }

    @Override
    public int insert(PanelPanelItemMap record) {
        return panelPanelItemMapRepository.savePanelPanelItemMap(record);
    }

    @Override
    public PanelPanelItemMap selectByPrimaryKey(Long id) {
        return panelPanelItemMapRepository.selectByPrimaryKey(id);
    }

/*    @Override
    public int updateByPrimaryKey(PanelPanelItemMap record) {
        return panelPanelItemMapRepository.updateByPrimaryKey(record);
    }*/

	@Override
	public List<Long> findListByEpgPanelItem(List<Long> panelIdList) {
		List<Long> itemIdList = new ArrayList<Long>();
		List<PanelPanelItemMap> map = this.panelPanelItemMapRepository.findListByEpgPanelItem(panelIdList);
		if(map != null && map.size() >0){
			for(PanelPanelItemMap panelMap:map){
				if(panelMap.getEpgPanelitemId() != null && panelMap.getEpgPanelitemId().equals("")){
					itemIdList.add(panelMap.getEpgPanelitemId());
				}
			}
		}
		return itemIdList;
	}
}