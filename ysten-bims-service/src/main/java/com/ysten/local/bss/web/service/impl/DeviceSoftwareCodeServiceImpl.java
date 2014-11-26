package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.local.bss.device.repository.IDeviceSoftwareCodeRepository;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.local.bss.interfaceUrl.repository.IInterfaceUrlRepository;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.HttpUtils.HttpResponseWrapper;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.web.service.IDeviceSoftwareCodeService;
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
public class DeviceSoftwareCodeServiceImpl implements IDeviceSoftwareCodeService {

    @Autowired
    private IDeviceSoftwareCodeRepository deviceSoftwareCodeRepository;
    @Autowired
    private IInterfaceUrlRepository interfaceUrlRepository;
    private static final String UTF8 = "utf-8";

    @Override
    public JsonResult insertOrUpdate(DeviceSoftwareCode deviceSoftwareCode) {
        JsonResult jsonResult = null;
        DeviceSoftwareCode data = null;
        if (null == deviceSoftwareCode) {
            return new JsonResult(false, "数据对象为空！");
        }
        if (null != deviceSoftwareCode.getId()) {
            data = this.deviceSoftwareCodeRepository.selectByPrimaryKey(deviceSoftwareCode.getId());
            if (data == null || !deviceSoftwareCode.getId().equals(data.getId())) {
                jsonResult = new JsonResult(false, "刷新后，再进行修改操作！");
                return jsonResult;
            }
            deviceSoftwareCode.setCreateDate(data.getCreateDate());
            jsonResult = new JsonResult(true, "修改成功！");
        } else {
            deviceSoftwareCode.setCreateDate(new Date());
            jsonResult = new JsonResult(true, "新增成功！");
        }
        this.deviceSoftwareCodeRepository.insertOrUpdate(deviceSoftwareCode);

        return jsonResult;
    }

    @Override
    public boolean insertOrUpdateSynchronization(DeviceSoftwareCode deviceSoftwareCode) {
        DeviceSoftwareCode data = null;
        if (deviceSoftwareCode.getId() != null) {
            data = deviceSoftwareCodeRepository.selectByPrimaryKey(deviceSoftwareCode.getId());
        }
        deviceSoftwareCode.setDistributeState(DistributeState.DISTRIBUTE);
        if (data == null) {
            return deviceSoftwareCodeRepository.insertSynchronization(deviceSoftwareCode);
        } else {
            return deviceSoftwareCodeRepository.update(deviceSoftwareCode);
        }
    }

    @Override
    public boolean batchInsertOrUpdateSynchronization(List<DeviceSoftwareCode> deviceSoftwareCodeList) {
        if (!CollectionUtils.isEmpty(deviceSoftwareCodeList)) {
            boolean result;
            for (DeviceSoftwareCode deviceSoftwareCode : deviceSoftwareCodeList) {
                result = this.insertOrUpdateSynchronization(deviceSoftwareCode);
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
        // TODO 再次还得考虑判断设备软件包
        if (this.isExistDeviceDatasBySoftCodeIds(idList)) {
            return new JsonResult(false, "该设备软件已关联设备，不能删除！");
        }
        this.deleteEpgDeviceSoftCodeIds(idList);
        return new JsonResult(true, "删除成功！");
    }

    @Override
    public DeviceSoftwareCode selectByPrimaryKey(Long softCodeId) {

        return deviceSoftwareCodeRepository.selectByPrimaryKey(softCodeId);
    }

    @Override
    public Pageable<DeviceSoftwareCode> findEpgSoftwareCodesByCondition(EnumConstantsInterface.Status status,
            String name, Integer pageNo, Integer pageSize) {

        DeviceUpgradeCondition condition = new DeviceUpgradeCondition();
        condition.setStatus(status);
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        condition.setName(FilterSpaceUtils.filterStartEndSpace(name));
        return deviceSoftwareCodeRepository.findEpgSoftwareCodesByCondition(condition);
    }

    // 验证是否存在设备数据
    private boolean isExistDeviceDatasBySoftCodeIds(List<Long> idList) {

        // TODO frank
        // List<Device> list =
        // deviceRepository.findEpgDeviceBySoftCodeIds(idList);
        // return !CollectionUtils.isEmpty(list);
        return false;
    }

    // 批量删除记录
    private boolean deleteEpgDeviceSoftCodeIds(List<Long> idList) {

        for (Long softCodeId : idList) {
            this.deviceSoftwareCodeRepository.deleteByPrimaryKey(softCodeId);
        }
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return this.deviceSoftwareCodeRepository.deleteByIds(ids);
    }

    @Override
    public boolean insert(DeviceSoftwareCode deviceSoftwareCode) {
        return this.deviceSoftwareCodeRepository.insert(deviceSoftwareCode);
    }

    @Override
    public boolean update(DeviceSoftwareCode deviceSoftwareCode) {
        return this.deviceSoftwareCodeRepository.update(deviceSoftwareCode);
    }

    @Override
    public boolean rendSoftCode(List<DeviceSoftwareCode> softwareCodes, Long area) throws Exception {
        boolean bool = false;
        // Long area =
        // Long.parseLong(this.systemConfigService.getSystemConfigByConfigKey("distributeAreaId"));
        // if (area == null) {
        // throw new Exception("请先维护配置下发省份!");
        // }
        if (area == null) {
            throw new Exception("请选择下发省份!");
        }
        InterfaceUrl interfaceUrl = this.interfaceUrlRepository.getByAreaAndName(area, InterfaceName.RECEIVESOFTCODE);
        if (interfaceUrl == null) {
            throw new Exception("请先维护省份接口Url表!");
        }
        String url = interfaceUrl.getInterfaceUrl();
        bool = this.distributeSoftCodeToProvince(softwareCodes, url);
        return bool;
    }

    public boolean distributeSoftCodeToProvince(List<DeviceSoftwareCode> softwareCodes, String url) {
        boolean bool = false;
        String pagam = JsonUtil.getJsonString4List(softwareCodes);
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
            for (DeviceSoftwareCode softwareCode : softwareCodes) {
                this.deviceSoftwareCodeRepository.updateDistributeStateById(softwareCode.getId(),
                        DistributeState.DISTRIBUTE.toString());
            }
        }
        return bool;
    }

    @Override
    public List<DeviceSoftwareCode> getAll() {
        return this.deviceSoftwareCodeRepository.getAll();
    }

    @Override
    public DeviceSoftwareCode findSoftwareCodesByName(String name) {
        return this.deviceSoftwareCodeRepository.findSoftwareCodesByName(name);
    }

    @Override
    public DeviceSoftwareCode findBySoftwareCode(String code) {
        return this.deviceSoftwareCodeRepository.selectByCode(code);
    }
}
