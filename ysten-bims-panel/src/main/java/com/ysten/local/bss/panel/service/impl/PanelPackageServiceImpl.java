package com.ysten.local.bss.panel.service.impl;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.repository.*;
import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.device.service.IUserGroupService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.panel.domain.*;
import com.ysten.local.bss.panel.enums.IsOrNotDefault;
import com.ysten.local.bss.panel.enums.NavigationType;
import com.ysten.local.bss.panel.enums.OnlineStatus;
import com.ysten.local.bss.panel.repository.INavigationRepository;
import com.ysten.local.bss.panel.repository.IPanelPackageMapRepository;
import com.ysten.local.bss.panel.repository.IPanelPackageRepository;
import com.ysten.local.bss.panel.service.IPanelPackageService;
import com.ysten.local.bss.panel.service.IPanelService;
import com.ysten.local.bss.panel.vo.PanelPreview;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by frank on 14-5-19.
 */
@Service
public class PanelPackageServiceImpl implements IPanelPackageService {

    @Autowired
    private IPanelPackageRepository panelPackageRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IPanelPackageDeviceMapRepository panelPackageDeviceMapRepository;
    @Autowired
    private IPanelPackageUserMapRepository panelPackageUserMapRepository;
    @Autowired
    private IUserGroupRepository userGroupRepository;
    @Autowired
    private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
    @Autowired
    private IPanelPackageMapRepository panelPackageMapRepository;
    @Autowired
    private INavigationRepository navigationRepository;
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IPanelService panelService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IUserGroupService userGroupService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private IUserGroupMapRepository userGroupMapRepository;
    private String ISCENTER = "isCenter";

    @Override
    public PanelPackage getPanelPackageById(Long id) {
        return panelPackageRepository.getPanelPackageById(id);
    }

    @Override
    public void savePanelPackage(PanelPackage panelPackage) {
        panelPackageRepository.savePanelPackage(panelPackage);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        //delete panel package map
        if (!CollectionUtils.isEmpty(ids)) {
            for (Long id : ids) {
                this.panelPackageMapRepository.deletePanelPackageMapByPackagelId(id);
                this.panelPackageDeviceMapRepository.deletePanelPackageMapByPanelId(id, "", Constant.BSS_DEVICE_PANEL_PACKAGE_MAP);
                this.panelPackageDeviceMapRepository.deletePanelPackageMapByPanelId(id, "", Constant.BSS_USER_PANEL_PACKAGE_MAP);

                //delete packages
                PanelPackage panelPackage = panelPackageRepository.getPanelPackageById(id);
                panelPackageRepository.deletePanelPackageById(panelPackage);
            }
        }
    }

    @Override
    public void deleteMapByIds(List<Long> ids, String isUser) {
        for (Long id : ids) {
            if (StringUtils.isNotBlank(isUser)) {
                this.panelPackageDeviceMapRepository.deletePanelPackageMapByPanelId(id, "", Constant.BSS_USER_PANEL_PACKAGE_MAP);
            } else {
                this.panelPackageDeviceMapRepository.deletePanelPackageMapByPanelId(id, "", Constant.BSS_DEVICE_PANEL_PACKAGE_MAP);
            }

        }
    }

    @Override
    public void update(PanelPackage panelPackage) {
        panelPackageRepository.update(panelPackage);
    }

    @Override
    public Pageable<PanelPackage> getTargetList(PanelQueryCriteria panelQueryCriteria) {
        return panelPackageRepository.getTargetList(panelQueryCriteria);
    }

    @Override
    public List<PanelPackage> getAllCustomedTargetList() {
        return panelPackageRepository.getAllCustomedTargetList();
    }

    @Override
    public List<PanelPreview> getPanelPreviewByPackageId(Long packageId, String dpi) {

        List<PanelPackageMap> panelPackageMapList = panelPackageMapRepository.findPanelPackageMapByPackageId(packageId);
        PanelPackage panelPackage = panelPackageRepository.getPanelPackageById(packageId);
        List<PanelPreview> panelPreviewList = new ArrayList<PanelPreview>();
        if (CollectionUtils.isEmpty(panelPackageMapList) || panelPackage == null) {
            return panelPreviewList;
        }
        for (PanelPackageMap panelPackageMap : panelPackageMapList) {
            Panel panel = panelService.getPanelById(panelPackageMap.getPanelId());

            //如果面板是初始或下线或分辨率不一致，不可以看到
            if (panel == null || (panel.getOnlineStatus() != null && OnlineStatus.ONLINE.getValue() != panel.getOnlineStatus()) || !StringUtils.equals(panel.getResolution(), dpi)) {
                continue;
            }
            PanelPreview panelPreview = new PanelPreview();
            List<PreviewItemData> previewItemDataList = panelService.getPreviewItemDataListByPanelId(panelPackageMap.getPanelId());
            if (!CollectionUtils.isEmpty(previewItemDataList)) {
                panelPreview.setPreviewItemDataList(previewItemDataList);
            }
            if (StringUtils.isNotBlank(panelPackageMap.getNavId())) {
                List<Long> navIds = StringUtil.splitToLong(panelPackageMap.getNavId());
                List<Navigation> navigationList = navigationRepository.findNavigationListByIds(navIds);

                if (!CollectionUtils.isEmpty(navigationList)) {
                    List<Navigation> headNavList = new ArrayList<Navigation>();
                    Navigation rootNav = new Navigation();
                    for (Navigation navigation : navigationList) {
                        if (navigation.getNavType().equals(NavigationType.HEAD_NAV.getValue())) {
                            headNavList.add(navigation);
                        }
                        if (navigation.getNavType().equals(NavigationType.ROOT_NAV.getValue())) {
                            rootNav = navigation;
                        }
                    }
                    panelPreview.setHeadNavList(headNavList);
                    panelPreview.setRootNavigation(rootNav);
                }
            }
            if (StringUtils.isNotBlank(panelPackageMap.getPanelLogo())) {
                panelPreview.setLogo(panelPackageMap.getPanelLogo());
            }
            if (StringUtils.isNotBlank(panelPackage.getDefaultBackground720p())) {
                panelPreview.setDefaultBackImag720p(panelPackage.getDefaultBackground720p());
            }
            if (StringUtils.isNotBlank(panelPackage.getDefaultBackground1080p())) {
                panelPreview.setDefaultBackImag1080p(panelPackage.getDefaultBackground1080p());
            }
            panelPreviewList.add(panelPreview);
        }
        return panelPreviewList;
    }

    @Override
    public List<PanelPackage> findAllPanelPackageList() {
        return panelPackageRepository.findAllPanelPackageList();
    }

    @Override
    public PanelPackage getDefaultPackage() {
        return panelPackageRepository.getDefaultPackage(IsOrNotDefault.DEFULT.getValue());
    }

    @Override
    public PanelPackage getPanelPackageForBootstrapByYstenId(String ystenId) {
        if (!StringUtils.isEmpty(ystenId)) {
            //根据ystenId查询用户
            Customer customer = customerService.getCustomerByYstenId(ystenId);
            if (customer != null) {
                //根据用户查询面板包
                PanelPackageUserMap panelPackageUserMap = this.panelPackageUserMapRepository.getMapByUserCode(customer.getCode());
                if (panelPackageUserMap != null) {
                    PanelPackage panelPackage = panelPackageRepository.getPanelPackageById(panelPackageUserMap.getPanelPackageId());
                    if (panelPackage != null) {
                        return panelPackage;
                    }
                }

                //根据用户查询面板包为空，则根据用户分组查询面板包
                PanelPackage panelPackageByUserGroup = this.getPanelPackageByUserGroup(customer.getCode().trim());
                if (panelPackageByUserGroup != null) {
                    return panelPackageByUserGroup;
                }
            }

            //根据ystenId查询面板包
            PanelPackageDeviceMap panelPackageDeviceMap = panelPackageDeviceMapRepository.getPanelDeviceMapByYstenId(ystenId);
            if (panelPackageDeviceMap != null) {
                PanelPackage panelPackage = panelPackageRepository.getPanelPackageById(panelPackageDeviceMap.getPanelPackageId());
                if (panelPackage != null)
                    return panelPackage;
            }
            //ystenId查询不到面板包，根据终端所属组查询面板包
            List<DeviceGroup> groupList = deviceService.findGroupByYstenIdType(ystenId, EnumConstantsInterface.DeviceGroupType.PANEL);
            if (!CollectionUtils.isEmpty(groupList)) {
                for (DeviceGroup deviceGroup : groupList) {
                    PanelPackageDeviceMap panelPackageDeviceMapObj = panelPackageDeviceMapRepository.getPanelDeviceMapByGroupId(deviceGroup.getId());
                    if (panelPackageDeviceMapObj != null) {
                        PanelPackage packageObj = panelPackageRepository.getPanelPackageById(panelPackageDeviceMapObj.getPanelPackageId());
                        if (packageObj != null) return packageObj;
                    }
                }
            }
        }
        //查询默认包
        return panelPackageRepository.getDefaultPackage(IsOrNotDefault.DEFULT.getValue());
    }

    public Customer getCustomerByDeviceCode(String deviceCode) {
        //根据终端广电号CODE查询用户
        DeviceCustomerAccountMap dcm = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(deviceCode);
        if (dcm != null) {
            Customer customer = this.customerRepository.getCustomerByUserId(dcm.getUserId());
            if (null == customer || (null != customer.getServiceStop() && new Date().after(customer.getServiceStop()))) {
                return null;
            }
            return customer;
        }
        return null;
    }

    public Customer getCustomerByYstenId(String ystenId) {
        //根据ystenid查询用户
        Customer customer = customerService.getCustomerByYstenId(ystenId);
        if (null == customer || (null != customer.getServiceStop() && new Date().after(customer.getServiceStop()))) {
            return null;
        }
        return customer;
    }

    public PanelPackage getPanelPackageByUserGroup(String userCode) {
        PanelPackage panelPackage = null;
        List<UserGroupMap> userGroupMap = this.userGroupMapRepository.findByUserCode(userCode);
        if (!CollectionUtils.isEmpty(userGroupMap)) {
            List<UserGroup> groupList = this.userGroupRepository.findUserGroupListByIdsAndType(userGroupMap, EnumConstantsInterface.UserGroupType.PANEL);
            if (!CollectionUtils.isEmpty(groupList)) {
                PanelPackageUserMap panelPackageUserGroupMap = this.panelPackageUserMapRepository.getMapByUserGroupId(groupList.get(0).getId());
                if (panelPackageUserGroupMap != null) {
                    panelPackage = panelPackageRepository.getPanelPackageById(panelPackageUserGroupMap.getPanelPackageId());
                }
            }
        }
        return panelPackage;
    }

    public PanelPackage getPanelPackageByUserDynamicGroup(String code) {
        PanelPackage panelPackage = null;
        List<UserGroup> groupList = userGroupService.findDynamicGroupList(code, EnumConstantsInterface.UserGroupType.PANEL.toString(), Constant.BSS_USER_PANEL_PACKAGE_MAP);
        // 动态分组是否存在
        if (CollectionUtils.isEmpty(groupList)) {
            // 动态分组不存在，取设备所在的静态分组
            List<UserGroupMap> userGroupMap = this.userGroupMapRepository.findByUserCode(code);
            if (!CollectionUtils.isEmpty(userGroupMap)) {
                //TODO 暂定： 若绑定该设备的用户所属类型为产品包和面板分组俩个分组中，取所属在产品包类型分组所绑定的面板包
                List<UserGroup> groupListProduct = this.userGroupRepository.findUserGroupListByIdsAndType(userGroupMap, EnumConstantsInterface.UserGroupType.PRODUCTPACKAGE);
                groupList = this.userGroupRepository.findUserGroupListByIdsAndType(userGroupMap, EnumConstantsInterface.UserGroupType.PRODUCTPACKAGE);
                if (CollectionUtils.isEmpty(groupList)) {
                    groupList = this.userGroupRepository.findUserGroupListByIdsAndType(userGroupMap, EnumConstantsInterface.UserGroupType.PANEL);
                }
                groupList.addAll(groupListProduct);
            }
        }
        // 根据用户组查询与panel的关系
        PanelPackageUserMap panelPackageUserMap = null;
        if (!CollectionUtils.isEmpty(groupList)) {
            panelPackageUserMap = this.panelPackageUserMapRepository.getMapByUserGroupId(groupList.get(0).getId());
        }
        if (null != panelPackageUserMap) {
            panelPackage = panelPackageRepository.getPanelPackageById(panelPackageUserMap.getPanelPackageId());
        }
        return panelPackage;
    }


    @Override
    public String bindUserMap(Long id, String areaIds, String userGroupIds, String userIds) {
        StringBuilder jy = new StringBuilder("");
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        this.panelPackageDeviceMapRepository.deletePanelPackageMapByPanelId(id, Constant.BUSINESS_USER_MAP_TYPE_GROUP, Constant.BSS_USER_PANEL_PACKAGE_MAP);
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                int index = 0;
                List<PanelPackageUserMap> maps = new ArrayList<PanelPackageUserMap>();
                this.panelPackageDeviceMapRepository.deletePanelDeviceMapByGroupIds(groupIds, Constant.BSS_USER_PANEL_PACKAGE_MAP, Constant.BSS_USER_PANEL_PACKAGE_MAP_GROUP_ID);

                for (Long deviceGroupId : groupIds) {
                    PanelPackageUserMap map = new PanelPackageUserMap();
                    map.setCreateDate(new Date());
                    map.setType(Constant.BUSINESS_USER_MAP_TYPE_GROUP);
                    map.setUserGroupId(deviceGroupId);
                    map.setPanelPackageId(id);
                    map.setCode(null);
                    maps.add(map);
                    index++;
                    if (index % Constant.BULK_NUM == 0) {
                        this.panelPackageUserMapRepository.bulkSaveUserPanelMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.panelPackageUserMapRepository.bulkSaveUserPanelMap(maps);
                    maps.clear();
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            String[] _userIds = userIds.split(",");
            List<String> userIdsList = new ArrayList<String>();
            for (String userId : _userIds) {
                if (StringUtils.isEmpty(userId)) {
                    continue;
                }
                userId = FilterSpaceUtils.replaceBlank(userId);
                userIdsList.add(userId);
            }
            /*List<Customer> customerList = this.customerRepository.findCustomersByUserCodes(_userIds);
            if (customerList.size() == 0) {
                jy.append("未能找到你所上传的所有用户编号的用户，请确认!");
                return jy.toString();
            }
            if (customerList.size() > 0 && customerList.size() != userIdsList.size()) {
                for (int i = 0; i < userIdsList.size(); i++) {
                    boolean bool = false;
                    for (Customer customer : customerList) {
                        if (customer.getCode().equals(userIdsList.get(i))) {
                            bool = true;
                            break;
                        }
                    }
                    if (bool == false) {
                        jy.append("未能找到用户编号为：" + userIdsList.get(i) + "的用户，请确认!" + "\n");
                    }
                }
            }
            List<String> userIdList = new ArrayList<String>();
            for (Customer customer : customerList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(customer.getArea().getId())) {
                        bool = true;
                        userIdList.add(customer.getCode());
                        break;
                    }
                }
                if (bool == false) {
                    jy.append("请确定用户编号为：" + customer.getUserId() + "的用户所在的区域!" + "\n");
                }
            }*/
            if (!CollectionUtils.isEmpty(userIdsList)) {
//                this.panelPackageDeviceMapRepository.deletePanelPackageMapByPanelId(id, Constant.BUSINESS_USER_MAP_TYPE_USER, Constant.BSS_USER_PANEL_PACKAGE_MAP);
                int index = 0;
                List<PanelPackageUserMap> maps = new ArrayList<PanelPackageUserMap>();
                this.panelPackageDeviceMapRepository.deletePanelDeviceMapByYstenIds(userIdsList, Constant.BSS_USER_PANEL_PACKAGE_MAP, Constant.BSS_USER_PANEL_PACKAGE_MAP_USER_ID);
                for (String y : userIdsList) {
                    PanelPackageUserMap map = new PanelPackageUserMap();
                    map.setCreateDate(new Date());
                    map.setType(Constant.BUSINESS_USER_MAP_TYPE_USER);
                    map.setUserGroupId(null);
                    map.setPanelPackageId(id);
                    map.setCode(y);
                    maps.add(map);
                    index++;
                    if (index % Constant.BULK_NUM == 0) {
                        this.panelPackageUserMapRepository.bulkSaveUserPanelMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.panelPackageUserMapRepository.bulkSaveUserPanelMap(maps);
                    maps.clear();
                }
            }
        }

        if (StringUtils.isNotEmpty(jy.toString())) {
            return jy.toString();
        }
        return null;
    }


    @Override
    public String bindMap(Long id, String areaIds, String deviceGroupIds, String deviceCodes, String userGroupIds, String userIds) {
        StringBuilder jy = new StringBuilder("");
        String[] _deviceCodes;
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        this.panelPackageDeviceMapRepository.deletePanelPackageMapByPanelId(id, Constant.BUSINESS_DEVICE_MAP_TYPE_GROUP, Constant.BSS_DEVICE_PANEL_PACKAGE_MAP);
        if (StringUtils.isNotBlank(deviceGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(deviceGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                int index = 0;
                List<PanelPackageDeviceMap> maps = new ArrayList<PanelPackageDeviceMap>();
                this.panelPackageDeviceMapRepository.deletePanelDeviceMapByGroupIds(groupIds, Constant.BSS_DEVICE_PANEL_PACKAGE_MAP, Constant.BSS_DEVICE_PANEL_PACKAGE_MAP_GROUP_ID);
                for (Long deviceGroupId : groupIds) {
                    PanelPackageDeviceMap map = new PanelPackageDeviceMap();
                    map.setCreateDate(new Date());
                    map.setType(Constant.MAP_GROUP);
                    map.setDeviceGroupId(deviceGroupId);
                    map.setPanelPackageId(id);
                    map.setYstenId(null);
                    maps.add(map);
                    index++;
                    if (index % Constant.BULK_NUM == 0) {
                        this.panelPackageDeviceMapRepository.bulkSaveDevicePanelMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.panelPackageDeviceMapRepository.bulkSaveDevicePanelMap(maps);
                    maps.clear();
                }
            }
        }
        if (StringUtils.isNotBlank(deviceCodes)) {
            _deviceCodes = deviceCodes.split(",");
            List<String> ystenIdList = new ArrayList<String>();
            for (String s : _deviceCodes) {
                if (StringUtils.isEmpty(s)) {
                    continue;
                }
                s = FilterSpaceUtils.replaceBlank(s);
                ystenIdList.add(s);
            }
            /*List<Device> deviceList = this.deviceRepository.findDeviceListByYstenIds(_deviceCodes);
            if (deviceList.size() == 0) {
                jy.append("未能找到你所上传的所有设备序列号的设备，请确认!");
                return jy.toString();
            }
            if (deviceList.size() > 0 && deviceList.size() != ystenIdList.size()) {
                for (int i = 0; i < ystenIdList.size(); i++) {
                    boolean bool = false;
                    for (Device device : deviceList) {
                        if (device.getYstenId().equals(ystenIdList.get(i))) {
                            bool = true;
                            break;
                        }
                    }
                    if (bool == false) {
                        jy.append("未能找到设备序列号为：" + ystenIdList.get(i) + "的设备，请确认!" + "\n");
                    }
                }
            }
            List<String> ystIdList = new ArrayList<String>();
            for (Device device : deviceList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(device.getArea().getId())) {
                        bool = true;
                        ystIdList.add(device.getYstenId());
                        break;
                    }
                }
                if (bool == false) {
                    jy.append("请确定设备序列号为：" + device.getYstenId() + "的所在区域!" + "\n");
                }
            }*/
            if (!CollectionUtils.isEmpty(ystenIdList)) {
                int index = 0;
                List<PanelPackageDeviceMap> maps = new ArrayList<PanelPackageDeviceMap>();
                this.panelPackageDeviceMapRepository.deletePanelDeviceMapByYstenIds(ystenIdList, Constant.BSS_DEVICE_PANEL_PACKAGE_MAP, Constant.BSS_DEVICE_PANEL_PACKAGE_MAP_YSTEN_ID);
                for (String y : ystenIdList) {
                    PanelPackageDeviceMap map = new PanelPackageDeviceMap();
                    map.setCreateDate(new Date());
                    map.setType(Constant.MAP_DEVICE);
                    map.setDeviceGroupId(null);
                    map.setPanelPackageId(id);
                    map.setYstenId(y);
                    maps.add(map);
                    index++;
                    if (index % Constant.BULK_NUM == 0) {
                        this.panelPackageDeviceMapRepository.bulkSaveDevicePanelMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.panelPackageDeviceMapRepository.bulkSaveDevicePanelMap(maps);
                    maps.clear();
                }
            }
        }

        if (StringUtils.isNotEmpty(jy.toString())) {
            return jy.toString();
        }
        return null;
    }

    private boolean savePanelDeviceMap(Long id, String type, Long deviceGroupId, String deviceCode) {
        PanelPackageDeviceMap map = new PanelPackageDeviceMap();
        map.setCreateDate(new Date());
        map.setType(type);
        map.setDeviceGroupId(deviceGroupId);
        map.setPanelPackageId(id);
        map.setPanelPackageId(id);
        map.setYstenId(deviceCode);
        return this.panelPackageDeviceMapRepository.saveDeviceMap(map);
    }

    private boolean savePanelUserMap(Long id, String type, Long userGroupId, String userId) {
        PanelPackageUserMap map = new PanelPackageUserMap();
        map.setCreateDate(new Date());
        map.setType(type);
        map.setUserGroupId(userGroupId);
        map.setPanelPackageId(id);
        map.setCode(userId);
        return this.panelPackageUserMapRepository.saveUserMap(map);
    }

    @Override
    public List<PanelPackageDeviceMap> findPanelPackageDeviceMapByPanelIdAndType(
            Long panelPackageId, String type) {
        return this.panelPackageDeviceMapRepository.findPanelPackageDeviceMapByPanelIdAndType(panelPackageId, type);
    }

    @Override
    public List<PanelPackageUserMap> findPanelPackageUserMapByPanelIdAndType(
            Long panelPackageId, String type) {
        return this.panelPackageUserMapRepository.findPanelPackageUserMapByPanelIdAndType(panelPackageId, type);
    }

    @Override
    public String bindPanelPackage(String packageId, String deviceCode) throws DeviceNotFoundException {
        // 1.查询设备是否合法
        Device device = this.deviceService.getDeviceByDeviceCode(deviceCode);
        if (device == null) {
            return JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "panelPackage bind failed!can not find device info."));
        }
        // 2.查询面板包是否合法
        PanelPackage panelPackage = this.panelPackageRepository.getPanelPackageById(Long.parseLong(packageId));
        if (panelPackage == null) {
            return JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "panelPackage bind failed!can not find panelPackage info."));
        }
        String ystenId = device.getYstenId();
        //3.设备-面板包关系是否存在：存在-更新；不存在-新增
        PanelPackageDeviceMap map = this.panelPackageDeviceMapRepository.getPanelDeviceMapByYstenId(ystenId);
        if (map == null) {
            if (this.savePanelDeviceMap(Long.parseLong(packageId), Constant.DEVICE, null, ystenId)) {
                return JsonUtils.toJson(new JsonResultBean(Constant.SUCCESS, "panelPackage bind success!"));
            }
        } else {
            map.setPanelPackageId(Long.parseLong(packageId));
            map.setCreateDate(new Date());
            if (this.panelPackageDeviceMapRepository.updateDeviceMapByYstenId(map)) {
                return JsonUtils.toJson(new JsonResultBean(Constant.SUCCESS, "panelPackage bind success!"));
            }
        }
        return JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "panelPackage bind failed!"));
    }

    @Override
    public String checkAreaDevices(String areaIds, String[] ystenIds) {
//		 boolean bool = false;
        //int sum = 0;
        StringBuilder sb = new StringBuilder("");
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
//         String[] ystenIdList = ystenIds.split(",");
        for (int i = 0; i < ystenIds.length; i++) {
            if (StringUtils.isEmpty(ystenIds[i])) {
                continue;
            }
            Device device = this.deviceRepository.getDeviceByYstenId(ystenIds[i]);
            int m = 0;
            boolean bool = false;
            for (Long areaId : areaIdList) {
                if (areaId.equals(device.getArea().getId())) {
                    bool = true;
                    //sum++;
                    continue;
                }
                m++;
                if (m == areaIdList.size() && bool == false) {
                    sb.append("请确定设备序列号为：" + ystenIds[i] + "的所在区域!" + "\n");
                }
            }
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            return sb.toString();
        }
        return null;
    }

    @Override
    public List<PanelPackage> findPanelPackageListOfArea(String distCode) {
        return this.panelPackageRepository.findPanelPackageListOfArea(distCode);
    }

    @Override
    public PanelPackage getBindedPanelPackageByStdId(String stdId) {
        if (!StringUtils.isEmpty(stdId)) {

            //根据ystenId查询面板包
            PanelPackageDeviceMap panelPackageDeviceMap = panelPackageDeviceMapRepository.getPanelDeviceMapByYstenId(stdId);
            if (panelPackageDeviceMap != null) {
                PanelPackage panelPackage = panelPackageRepository.getPanelPackageById(panelPackageDeviceMap.getPanelPackageId());
                if (panelPackage != null)
                    return panelPackage;
            }
            //ystenId查询不到面板包，根据终端所属组查询面板包
           /* List<DeviceGroup> groupList = deviceService.findGroupByYstenIdType(stdId, EnumConstantsInterface.DeviceGroupType.PANEL);
            if (!org.springframework.util.CollectionUtils.isEmpty(groupList)) {
                for (DeviceGroup deviceGroup : groupList) {
                    PanelPackageDeviceMap panelPackageDeviceMapObj = panelPackageDeviceMapRepository.getPanelDeviceMapByGroupId(deviceGroup.getId());
                    if (panelPackageDeviceMapObj != null) {
                        PanelPackage packageObj = panelPackageRepository.getPanelPackageById(panelPackageDeviceMapObj.getPanelPackageId());
                        if (packageObj != null) return packageObj;
                    }
                }
            }*/
        }
        //查询默认包
        return panelPackageRepository.getDefaultPackage(IsOrNotDefault.DEFULT.getValue());
    }
}
