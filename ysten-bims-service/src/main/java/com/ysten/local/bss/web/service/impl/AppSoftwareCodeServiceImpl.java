package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.AppSoftwareCode;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.repository.IAppSoftwareCodeRepository;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.local.bss.interfaceUrl.repository.IInterfaceUrlRepository;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.HttpUtils.HttpResponseWrapper;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.web.service.IAppSoftwareCodeService;
import com.ysten.utils.page.Pageable;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class AppSoftwareCodeServiceImpl implements IAppSoftwareCodeService {

    @Autowired
    private IAppSoftwareCodeRepository appSoftwareCodeRepository;
    @Autowired
    private IInterfaceUrlRepository interfaceUrlRepository;
    private static final String UTF8 = "utf-8";

    @Override
    public Pageable<AppSoftwareCode> findAppSoftwareCodesByCondition(EnumConstantsInterface.Status status, String name,
            Integer pageNo, Integer pageSize) {

        DeviceUpgradeCondition condition = new DeviceUpgradeCondition();
        condition.setStatus(status);
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        condition.setName(FilterSpaceUtils.filterStartEndSpace(name));
        return appSoftwareCodeRepository.findAppSoftwareCodesByCondition(condition);
    }

    @Override
    public AppSoftwareCode selectByPrimaryKey(Long softCodeId) {

        return appSoftwareCodeRepository.getAppSoftwareCodeBySoftCodeId(softCodeId);

    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return this.appSoftwareCodeRepository.deleteAppSoftwareCodeByIds(ids);
    }

    @Override
    public boolean insert(AppSoftwareCode appSoftwareCode) {
        return this.appSoftwareCodeRepository.saveAppSoftwareCode(appSoftwareCode);
    }

    @Override
    public boolean update(AppSoftwareCode appSoftwareCode) {
        return this.appSoftwareCodeRepository.updateAppSoftwareCode(appSoftwareCode);
    }

    @Override
    public List<AppSoftwareCode> getAll() {
        return this.appSoftwareCodeRepository.findAllAppSoftwareCode();
    }

    /**
     * insert or update app software code from center
     * 
     * @param appSoftwareCode
     * @return
     */
    @Override
    public boolean insertOrUpdateSynchronization(AppSoftwareCode appSoftwareCode) {

        AppSoftwareCode data = null;
        if (null == appSoftwareCode) {
            return false;
        }
        if (appSoftwareCode.getId() != null) {
            data = appSoftwareCodeRepository.getAppSoftwareCodeBySoftCodeId(appSoftwareCode.getId());
        }
        appSoftwareCode.setDistributeState(DistributeState.DISTRIBUTE);
        if (data == null) {
            return appSoftwareCodeRepository.saveAppSoftwareCode(appSoftwareCode);
        } else {
            return appSoftwareCodeRepository.updateAppSoftwareCode(appSoftwareCode);
        }
    }

    @Override
    public boolean batchInsertOrUpdateSynchronization(List<AppSoftwareCode> appSoftwareCodeList) {
        if (!CollectionUtils.isEmpty(appSoftwareCodeList)) {
            boolean result;
            for (AppSoftwareCode appSoftwareCode : appSoftwareCodeList) {
                result = this.insertOrUpdateSynchronization(appSoftwareCode);
                if (!result)
                    return result;
            }
        }
        return true;
    }

    @Override
    public AppSoftwareCode findSoftwareCodesByName(String name) {
        return appSoftwareCodeRepository.findSoftwareCodesByName(name);
    }

    @Override
    public AppSoftwareCode findBySoftwareCode(String code) {
        return appSoftwareCodeRepository.getBySoftwareCode(code);
    }

    @Override
    public boolean rendSoftCode(List<AppSoftwareCode> softwareCodes, Long area) throws Exception {
        boolean bool = false;
        // Long area =
        // Long.parseLong(this.systemConfigService.getSystemConfigByConfigKey("distributeAreaId"));
        // if(area == null){
        // throw new Exception("请先维护配置下发省份!");
        // }
        if (area == null) {
            throw new Exception("请选择下发省份!");
        }
        InterfaceUrl interfaceUrl = this.interfaceUrlRepository
                .getByAreaAndName(area, InterfaceName.RECEIVEAPPSOFTCODE);
        if (interfaceUrl == null) {
            throw new Exception("请先维护省份接口Url表!");
        }
        String url = interfaceUrl.getInterfaceUrl();
        bool = this.distributeSoftCodeToProvince(softwareCodes, url);
        return bool;
    }

    public boolean distributeSoftCodeToProvince(List<AppSoftwareCode> softwareCodes, String url) {
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
            for (AppSoftwareCode softwareCode : softwareCodes) {
                this.appSoftwareCodeRepository.updateDistributeStateById(softwareCode.getId(),
                        DistributeState.DISTRIBUTE.toString());
            }
        }
        return bool;
    }
}
