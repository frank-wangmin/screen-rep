package com.ysten.local.bss.device.service;

import com.ysten.local.bss.device.api.domain.request.tt.TtAuthenticationInitialRequest;
import com.ysten.local.bss.device.api.domain.request.tt.TtUserRequest;
import com.ysten.local.bss.device.api.domain.response.IResponse;

public interface ITtGroupService extends IDeviceNewService {
	/**
	 * 铁通定时上传当日开销户数据
	 * @return
	 * @throws 
	 * 		Exception 上传开销户数据异常
	 */
	public boolean downloadAndSyncOpenAndCancelCustomerData() throws Exception; 
	/**
	 * 接收铁通省级分公司同步的数据
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	public IResponse acceptDataFromProvince(String jsonData) throws Exception;
	/**
	 * 定时同步用户、设备、订单数据到铁通集团中心
	 * @throws Exception
	 */
	public boolean syncDataToCenter() throws Exception;
    /**
     * 铁通中心每日定点发送开户及订单数据到视频基地
     */
    public boolean syncDataToAAA() throws Exception;
    
    /**
     * 开户验证接口
     * @author chenxiang
     * 
     * @date 2014-6-23 上午11:12:27 
     * @param request 请求数据
     * @return IResponse
     * @throws Exception
     */
    IResponse userAuth(TtUserRequest request) throws Exception;
    
    
    /**
     * 用户信息获取接口
     * @author chenxiang
     * @date 2014-6-23 下午3:01:53 
     * @param request 请求数据
     * @return IResponse
     * @throws Exception
     */
    IResponse requestUserInfo(TtUserRequest request) throws Exception;
    
	/**
	 * 开户认证接口
	 * @author chenxiang
	 * @date 2014-7-7 下午1:07:46 
	 * @param request
	 * @return TtUserResponse
	 */
    IResponse authenticationInitial(TtAuthenticationInitialRequest request) throws Exception;
}
