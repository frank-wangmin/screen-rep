package com.ysten.local.bss.device.service.impl;

import com.ysten.local.bss.device.api.domain.request.IBindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IBindMultipleDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.ICreateUserRequest;
import com.ysten.local.bss.device.api.domain.request.IDisableDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IPauseOrRecoverDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IRebindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateRateRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateServicesStopRequest;
import com.ysten.local.bss.device.api.domain.request.IVerifyDeviceRequest;
import com.ysten.local.bss.device.api.domain.response.IResponse;
import com.ysten.local.bss.device.domain.Customer;

public class DeviceEServiceImpl extends DeviceNewServiceImpl {

    /************************************************************** 终端编号合法性查询接口********************************************************/
    @Override
    public IResponse verifyDevice(IVerifyDeviceRequest request) throws Exception {
        return null;
    }

    /**************************************************************生成用户操作********************************************************/
    @Override
    public IResponse checkCreateUserParam(ICreateUserRequest request) throws Exception {
        return null;
    }

    @Override
    public IResponse doAfterCreateUser(ICreateUserRequest request, boolean isCreateSuccess) throws Exception {
        return null;
    }

    @Override
    public Customer initCustomerByRequest(ICreateUserRequest request) throws Exception {
        return null;
    }

    /**************************************************************开户操作********************************************************/
    @Override
    public IResponse checkBindDeviceParam(IBindDeviceRequest request) throws Exception {
        return null;
    }

    @Override
    public IResponse doAfterBind(IBindDeviceRequest request, boolean isBindSuccess) throws Exception {
        return null;
    }

    @Override
    public Customer initCustomerByRequest(IBindDeviceRequest request) throws Exception {
        return null;
    }

    /**************************************************************销户操作********************************************************/
    @Override
    public IResponse checkDisableDeviceParam(IDisableDeviceRequest request) throws Exception {
        return null;
    }

    @Override
    public IResponse doAfterDisable(IDisableDeviceRequest request, boolean isDisableSuccess) throws Exception {
        return null;
    }

    /**************************************************************换机操作********************************************************/
    @Override
    public IResponse checkRebindDeviceParam(IRebindDeviceRequest request) throws Exception {
        return null;
    }

    @Override
    public IResponse doAfterRebind(IRebindDeviceRequest request, boolean isRebindSuccess) throws Exception {
        return null;
    }

    /**************************************************************终端到期时间修改操作********************************************************/
    @Override
    public IResponse checkUpdateServicesStopParam(IUpdateServicesStopRequest request) throws Exception {
        return null;
    }

    @Override
    public IResponse doAfterUpdateServicesStop(IUpdateServicesStopRequest request, boolean isUpdateServicesStopSuccess)
            throws Exception {
        return null;
    }

    /**************************************************************集团业务办理接口********************************************************/
    @Override
    public IResponse checkBindMultipleDeviceParam(IBindMultipleDeviceRequest request) throws Exception {
        return null;
    }

    @Override
    public IResponse doAfterBindMultipleDevice(IBindMultipleDeviceRequest request, boolean isBindMultipleDeviceSuccess)
            throws Exception {
        return null;
    }

    @Override
    public Customer initMultipleCustomerByRequest(IBindMultipleDeviceRequest request) throws Exception {
        return null;
    }

    /**************************************************************终端速率变更接口********************************************************/
    @Override
    public IResponse checkUpdateRateParam(IUpdateRateRequest request) throws Exception {
        return null;
    }

    @Override
    public IResponse doAfterUpdateRate(IUpdateRateRequest request, boolean isUpdateRateSuccess) throws Exception {
        return null;
    }

    /**************************************************************终端暂停启用接口********************************************************/
    @Override
    public IResponse checkPauseOrRecoverDeviceParam(IPauseOrRecoverDeviceRequest request) throws Exception {
        return null;
    }

    @Override
    public IResponse doAfterPauseOrRecoverDevice(IPauseOrRecoverDeviceRequest request,
            boolean isPauseOrRecoverDeviceSuccess) throws Exception {
        return null;
    }

}
