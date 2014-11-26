package com.ysten.local.bss.device.service;

import com.ysten.local.bss.device.api.domain.response.IResponse;
import com.ysten.local.bss.device.exception.ParamIsEmptyException;
import com.ysten.local.bss.device.exception.ParamIsInvalidException;

public interface ISyncDataToCenterService {
	
	
	/**
	 * 根据用户状态定时同步数据到中心
	 * @author chenxiang
	 * @date 2014-8-13 下午2:29:54 
	 * @return
	 * @throws Exception
	 */
	public boolean syncDataToCenter() throws Exception;

	/**
	 * 中心接受需要同步的数据
	 * @author chenxiang
	 * @date 2014-8-13 下午3:20:40 
	 * @param jsonData
	 * @return
	 */
	public IResponse acceptData(String jsonData);

	/**
	 *  根据设备状态定时同步数据到中心
	 * @author chenxiang
	 * @date 2014-8-15 下午4:58:57 
	 */
	public boolean syncDataToCenterByDevice() throws Exception;

	/**
	 * 接受根据设备状态同步的数据到中心
	 * @author chenxiang
	 * @date 2014-8-15 下午5:31:46 
	 * @param jsonData
	 * @return
	 */
	public void saveAcceptDataByDevice(String jsonData) throws Exception;

}
