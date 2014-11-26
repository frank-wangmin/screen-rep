package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.repository.IDeviceSoftwarePackageRepository;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.PackageType;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.local.bss.interfaceUrl.repository.IInterfaceUrlRepository;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.HttpUtils.HttpResponseWrapper;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.web.service.IDeviceSoftwarePackageService;
import com.ysten.utils.json.JsonResult;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class DeviceSoftwarePackageServiceImpl implements IDeviceSoftwarePackageService {

    @Autowired
    private IDeviceSoftwarePackageRepository deviceSoftwarePackageRepository;
    @Autowired
    private IInterfaceUrlRepository interfaceUrlRepository;
    private static final String UTF8 = "utf-8";

    @Override
    public JsonResult insertOrUpdate(DeviceSoftwarePackage deviceSoftwarePackage) {
        JsonResult jsonResult = null;
        DeviceSoftwarePackage data = null;
        if (null == deviceSoftwarePackage) {
            return new JsonResult(false, "数据对象为空！");
        }
        if (null != deviceSoftwarePackage.getId()) {
            data = this.deviceSoftwarePackageRepository.selectByPrimaryKey(deviceSoftwarePackage.getId());
            if (data == null) {
                jsonResult = new JsonResult(false, "刷新后，再进行修改操作！");
                return jsonResult;
            }
            deviceSoftwarePackage.setCreateDate(data.getCreateDate());
            jsonResult = new JsonResult(true, "修改成功！");
        } else {
            deviceSoftwarePackage.setCreateDate(new Date());
            jsonResult = new JsonResult(true, "新增成功！");
        }
        this.deviceSoftwarePackageRepository.insertOrUpdate(deviceSoftwarePackage);

        return jsonResult;
    }

    @Override
    public boolean insertOrUpdateSynchronization(DeviceSoftwarePackage deviceSoftwarePackage) {
        DeviceSoftwarePackage data = null;
        if (deviceSoftwarePackage.getId() != null) {
            data = deviceSoftwarePackageRepository.selectByPrimaryKey(deviceSoftwarePackage.getId());
        }
        deviceSoftwarePackage.setDistributeState(DistributeState.DISTRIBUTE);
        if (data == null) {
            return deviceSoftwarePackageRepository.insertSynchronization(deviceSoftwarePackage);
        } else {
            return deviceSoftwarePackageRepository.updateSoftwarePackage(deviceSoftwarePackage);
        }
    }

    @Override
    public boolean batchInsertOrUpdateSynchronization(List<DeviceSoftwarePackage> deviceSoftwarePackageList) {
        if (!CollectionUtils.isEmpty(deviceSoftwarePackageList)) {
            boolean result;
            for (DeviceSoftwarePackage deviceSoftwarePackage : deviceSoftwarePackageList) {
                if(deviceSoftwarePackage.getPackageType()==PackageType.INCREMENT){
                    if(deviceSoftwarePackage.getFullSoftwareId() == null || deviceSoftwarePackage.getFullSoftwareId().equals("")){
                        return false;
                    }
                    if(deviceSoftwarePackage.getFullSoftwareId() != null && !deviceSoftwarePackage.getFullSoftwareId().equals("")){
                        DeviceSoftwarePackage softackage = this.deviceSoftwarePackageRepository.selectByPrimaryKey(deviceSoftwarePackage.getFullSoftwareId());
                        if(softackage == null){
                            return false;
                        }
                    }
                }
                result = this.insertOrUpdateSynchronization(deviceSoftwarePackage);
                if (!result)
                    return result;
            }
        }
        return true;
    }
   
    @Override
    public JsonResult deleteByIds(String ids) {
        List<Long> idList = StringUtil.splitToLong(ids);
        if (StringUtils.isBlank(ids) || CollectionUtils.isEmpty(idList)) {
            return new JsonResult(false, "参数为空！");
        }
        if (isExist(idList)) {
            return new JsonResult(false, "其中有全量包被引用，不能删除！");
        }
        this.deleteEpgDeviceSoftwareByIds(idList);
        return new JsonResult(true, "删除成功！");
    }

    @Override
    public DeviceSoftwarePackage selectByPrimaryKey(Long softwareId) {

        return deviceSoftwarePackageRepository.selectByPrimaryKey(softwareId);
    }

    @Override
    public Pageable<DeviceSoftwarePackage> findEpgDeviceSoftwaresByCondition(Long softCodeId,
            EnumConstantsInterface.PackageType packageType, String name, Integer pageNo, Integer pageSize) {

        DeviceUpgradeCondition condition = new DeviceUpgradeCondition();
        condition.setSoftCodeId(softCodeId);
        condition.setPackageType(packageType);
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        condition.setName(name);
        return deviceSoftwarePackageRepository.findEpgDeviceSoftwaresByCondition(condition);
    }

    // 查看该全量包是否被引用
    private boolean isExist(List<Long> fullIds) {

        List<DeviceSoftwarePackage> list = deviceSoftwarePackageRepository.findEpgDeviceSoftwaresByFullIds(fullIds);

        return !CollectionUtils.isEmpty(list);
    }

    // 批量删除记录
    private boolean deleteEpgDeviceSoftwareByIds(List<Long> idList) {

        for (Long softwareId : idList) {
            this.deviceSoftwarePackageRepository.deleteByPrimaryKey(softwareId);
        }
        return true;
    }

    @Override
    public Pageable<DeviceSoftwarePackage> getListByCondition(String versionName, Integer pageNo, Integer pageSize) {
        return this.deviceSoftwarePackageRepository.getListByCondition(FilterSpaceUtils.filterStartEndSpace(versionName), pageNo, pageSize);
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return this.deviceSoftwarePackageRepository.deleteByIds(ids);
    }

    @Override
    public boolean insert(DeviceSoftwarePackage deviceSoftwarePackage) {
        return this.deviceSoftwarePackageRepository.insert(deviceSoftwarePackage);
    }

    @Override
    public boolean updateById(DeviceSoftwarePackage deviceSoftwarePackage) {
        return this.deviceSoftwarePackageRepository.updateById(deviceSoftwarePackage);
    }

    @Override
    public boolean rendSoftwarePackage(List<DeviceSoftwarePackage> deviceSoftwarePackages, Long area) throws Exception {
        boolean bool = false;
        // Long area =
        // Long.parseLong(this.systemConfigService.getSystemConfigByConfigKey("distributeAreaId"));
        // if (area == null) {
        // throw new Exception("请先维护配置下发省份!");
        // }
        if (area == null) {
            throw new Exception("请选择下发省份!");
        }
        InterfaceUrl interfaceUrl = this.interfaceUrlRepository.getByAreaAndName(area,
                InterfaceName.RECEIVESOFTWAREPACKAGE);
        if (interfaceUrl == null) {
            throw new Exception("请先维护省份接口Url表!");
        }
        String url = interfaceUrl.getInterfaceUrl();
        bool = this.distributeSoftwarePackageToProvince(deviceSoftwarePackages, url);
        return bool;
    }

    public boolean distributeSoftwarePackageToProvince(List<DeviceSoftwarePackage> deviceSoftwarePackages, String url) {
        boolean bool = false;
        String pagam = JsonUtil.getJsonString4List(deviceSoftwarePackages);
        HttpResponseWrapper wrapper = HttpUtils.doPost(url, pagam, UTF8);
        int returnCode = wrapper.getHttpStatus();
        if (returnCode == 200) {
            String rsp = wrapper.getResponse();
            JSONObject object = JSONObject.fromObject(rsp);
            String result = object.getString("result");
            if (StringUtils.equalsIgnoreCase(result, "true")) {
                bool = true;
            }
        }
        if (bool == true) {
            for (DeviceSoftwarePackage deviceSoftwarePackage : deviceSoftwarePackages) {
                this.deviceSoftwarePackageRepository.updateDistributeStateById(deviceSoftwarePackage.getId(),
                        DistributeState.DISTRIBUTE.toString());
            }
        }
        return bool;
    }

    @Override
    public List<DeviceSoftwarePackage> getAll() {
        return this.deviceSoftwarePackageRepository.getAll();
    }

    @Override
    public DeviceSoftwarePackage getSoftwarePackageByName(String versionName) {
        return this.deviceSoftwarePackageRepository.getSoftwarePackageByName(versionName);
    }

    @Override
    public List<DeviceSoftwarePackage> getSoftwarePackageBySoftCode(Long softwareCodeId) {
        return this.deviceSoftwarePackageRepository.getSoftwarePackageBySoftCode(softwareCodeId);
    }
}
