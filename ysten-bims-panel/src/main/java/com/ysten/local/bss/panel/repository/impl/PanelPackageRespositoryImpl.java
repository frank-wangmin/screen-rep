package com.ysten.local.bss.panel.repository.impl;

import com.ysten.cache.annotation.*;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.PanelPackageDeviceMap;
import com.ysten.local.bss.device.domain.PanelPackageUserMap;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.repository.IDeviceGroupRepository;
import com.ysten.local.bss.device.repository.IPanelPackageDeviceMapRepository;
import com.ysten.local.bss.device.repository.IPanelPackageUserMapRepository;
import com.ysten.local.bss.device.repository.IUserGroupRepository;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.repository.IPanelPackageRepository;
import com.ysten.local.bss.panel.repository.mapper.PanelPackageMapper;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by frank on 14-5-9.
 */
@Repository
public class PanelPackageRespositoryImpl implements IPanelPackageRepository {

    @Autowired
    private PanelPackageMapper panelPackageMapper;
    @Autowired
    private IPanelPackageDeviceMapRepository panelPackageDeviceMapRepository;
    @Autowired
    private IPanelPackageUserMapRepository panelPackageUserMapRepository;
    @Autowired
    private IDeviceGroupRepository deviceGroupRepository;
    @Autowired
    private IUserGroupRepository userGroupRepository;

    private static final String PANEL_PACKAGE_ID = Constant.PANEL_PACKAGE + "panel_package:id:";
//    private static final String PANEL_PACKAGE_EPG_ID = Constant.PANEL_PACKAGE + "epg_panel_package:id:";

    @Override
    @MethodCache(key = PANEL_PACKAGE_ID + "#{id}")
    public PanelPackage getPanelPackageById(@KeyParam("id") Long id) {
        return panelPackageMapper.getPanelPackageById(id);
    }

    @Override
    public PanelPackage getPanelPackageByYstenIdOrGroupId(String ystenId, Long groupId) {
        return panelPackageMapper.getPackageByYstenIdOrGroupId(ystenId, groupId);
    }

    @Override
    public PanelPackage getPackageByCustomerCodeOrGroupId(String customerCode, Long groupId) {
        return panelPackageMapper.getPackageByCustomerCodeOrGroupId(customerCode, groupId);
    }

    @Override
    public void savePanelPackage(PanelPackage panelPackage) {
        panelPackageMapper.save(panelPackage);
    }

    @Override
    @MethodFlush(keys = {PANEL_PACKAGE_ID + "#{panelPackage.id}"})
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deletePanelPackageById(@KeyParam("panelPackage") PanelPackage panelPackage) {
        panelPackageMapper.deleteById(panelPackage.getId());
    }


    @Override
    @MethodFlush(keys = {PANEL_PACKAGE_ID + "#{panelPackage.id}"})
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void update(@KeyParam("panelPackage") PanelPackage panelPackage) {
        panelPackageMapper.update(panelPackage);
    }

    @Override
    public void updateEpgPanelPackage(PanelPackage panelPackage) {
        panelPackageMapper.update(panelPackage);
    }


    @Override
    public Pageable<PanelPackage> getTargetList(PanelQueryCriteria panelQueryCriteria) {
        List<PanelPackage> panelPackages = panelPackageMapper.getTargetList(panelQueryCriteria);
        if (panelPackages != null && panelPackages.size() > 0) {
            for (PanelPackage panelPackage : panelPackages) {
               /* List<PanelPackageDeviceMap> deivceMapList = this.panelPackageDeviceMapRepository.findPanelPackageDeviceMapByPanelIdAndType(panelPackage.getId(), "DEVICE");
                StringBuffer dsb = new StringBuffer();
                for (PanelPackageDeviceMap dnm : deivceMapList) {
                    dsb.append(dnm.getYstenId()).append(",");
                }*/
                List<PanelPackageDeviceMap> groupMapList = this.panelPackageDeviceMapRepository.findPanelPackageDeviceMapByPanelIdAndType(panelPackage.getId(), "GROUP");
                StringBuffer msb = new StringBuffer();
                for (PanelPackageDeviceMap dnm : groupMapList) {
                    DeviceGroup deviceGroup = this.deviceGroupRepository.selectByPrimaryKey(dnm.getDeviceGroupId());
                    msb.append(deviceGroup.getName()).append(",");
                }
             /*   List<PanelPackageUserMap> userMapList = this.panelPackageUserMapRepository.findPanelPackageUserMapByPanelIdAndType(panelPackage.getId(), "USER");
                StringBuffer usb = new StringBuffer();
                for (PanelPackageUserMap dnm : userMapList) {
                    usb.append(dnm.getCode()).append(",");
                }*/
                List<PanelPackageUserMap> userGroupMapList = this.panelPackageUserMapRepository.findPanelPackageUserMapByPanelIdAndType(panelPackage.getId(), "GROUP");
                StringBuffer umsb = new StringBuffer();
                for (PanelPackageUserMap dnm : userGroupMapList) {
                    UserGroup userGroup = this.userGroupRepository.getById(dnm.getUserGroupId());
                    umsb.append(userGroup.getName()).append(",");
                }
              /*  if (dsb.length() > 0) {
                    panelPackage.setDeviceCodes(dsb.substring(0, dsb.length() - 1).toString());
                }*/
                if (msb.length() > 0) {
                    panelPackage.setDeviceGroupIds(msb.substring(0, msb.length() - 1).toString());
                }
               /* if (usb.length() > 0) {
                    panelPackage.setUserIds(usb.substring(0, usb.length() - 1).toString());
                }*/
                if (umsb.length() > 0) {
                    panelPackage.setUserGroupIds(umsb.substring(0, umsb.length() - 1).toString());
                }
            }
        }
        Integer total = panelPackageMapper.getTargetCount(panelQueryCriteria);
        return new Pageable<PanelPackage>().instanceByPageNo(panelPackages, total, panelQueryCriteria.getStart(), panelQueryCriteria.getLimit());
    }

    @Override
    public List<PanelPackage> getAllCustomedTargetList() {
        return panelPackageMapper.getAllCustomedTargetList();
    }

    @Override
    public List<PanelPackage> findAllEpgList() {
        return panelPackageMapper.findAllEpgList();
    }

    @Override
    public List<PanelPackage> findIdAndEpgIdList() {
        return panelPackageMapper.findIdAndEpgIdList();
    }

    @Override
    public List<PanelPackage> findAllPanelPackageList() {
        return panelPackageMapper.findAllPanelPackageList();
    }


    @Override
    public PanelPackage getDefaultPackage(Integer isDefault) {
        return panelPackageMapper.getDefaultPackage(isDefault);
    }

    @Override
    public PanelPackage getPackageByEpgPackageId(Long epgPackageId) {
        return panelPackageMapper.getPackageByEpgPackageId(epgPackageId);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
//    @MethodFlushAllPanel
    public void deleteByEpgPackageId() {
        panelPackageMapper.deleteByEpgPackageId();
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void batchSavePanelPackage(List<PanelPackage> list) {
        panelPackageMapper.batchSavePanelPackage(list);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
//    @MethodFlushAllPanel
    public void batchUpdatePanelPackage(List<PanelPackage> list) {
        panelPackageMapper.batchUpdate(list);
    }

	@Override
	public List<PanelPackage> findPanelPackageListOfArea(String distCode) {
		return this.panelPackageMapper.findPanelPackageListOfArea(distCode);
	}


}
