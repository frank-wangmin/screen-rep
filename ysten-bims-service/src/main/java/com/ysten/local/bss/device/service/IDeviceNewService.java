package com.ysten.local.bss.device.service;

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

public interface IDeviceNewService {
    /**
     * 开户接口
     * 用户在营业厅开户，营业厅受理开户请求，只保存用户信息。
     * 当设备第一次开机的时候，进行用户和设备的绑定
     * @param 
     *      request 开户接口参数
     * @return 
     *      IResponse 开户返回
     * @throws 
     *      Exception 开户接口异常
     */
    IResponse createUser(ICreateUserRequest request) throws Exception;
	/**
	 * 开户接口
	 * @param 
	 * 		request 开户接口参数
	 * @return 
	 * 		IResponse 开户返回
	 * @throws 
	 * 		Exception 开户接口异常
	 */
	IResponse bindDevice(IBindDeviceRequest request) throws Exception;
	/**
	 * 销户接口
	 * @param 
	 * 		request 销户接口参数
	 * @return
	 * 		IResponse 销户返回
	 * @throws 
	 * 		Exception 销户接口异常
	 */
	IResponse disableDevice(IDisableDeviceRequest request) throws Exception;
	/**
	 * 换机接口
	 * @param 
	 * 		request 换机接口参数
	 * @return
	 * 		IResponse 换机返回
	 * @throws 
	 * 		Exception 换机接口异常
	 */
	IResponse rebindDevice(IRebindDeviceRequest request) throws Exception;
	/**
	 * 终端编号合法性查询接口
	 * @param 
	 * 		request 终端编号合法性查询接口参数
	 * @return
	 * 		IResponse 终端编号合法性查询返回
	 * @throws 
	 * 		Exception 终端编号合法性查询异常
	 */
	IResponse verifyDevice(IVerifyDeviceRequest request) throws Exception;
	/**
	 * 终端到期时间修改接口
	 * @param 
	 * 		request 终端到期时间修改接口参数
	 * @return
	 * 		IResponse 终端到期时间修改接口返回
	 * @throws 
	 * 		Exception 终端到期时间修改接口异常
	 */
	IResponse updateServicesStop(IUpdateServicesStopRequest request) throws Exception;
	/**
	 * 集团业务办理接口
	 * @param 
	 * 		request 集团业务办理接口参数
	 * @return
	 * 		IResponse 集团业务办理接口返回
	 * @throws 
	 * 		Exception 集团业务办理接口异常
	 */
	IResponse bindMultipleDevice(IBindMultipleDeviceRequest request) throws Exception;
	/**
	 * 终端宽带速率变更接口
	 * @param 
	 * 		request 终端宽带速率变更接口参数
	 * @return
	 * 		IResponse 终端宽带速率变更接口返回
	 * @throws 
	 * 		Exception 终端宽带速率变更接口异常
	 */
	IResponse updateRate(IUpdateRateRequest request) throws Exception;
	/**
	 * 终端暂停启用接口
	 * @param 
	 * 		request 终端暂停启用接口参数
	 * @return
	 * 		IResponse 终端暂停启用接口返回
	 * @throws 
	 * 		Exception 终端暂停启用接口异常
	 */
	IResponse pauseOrRecoverDevice(IPauseOrRecoverDeviceRequest request) throws Exception;
}
