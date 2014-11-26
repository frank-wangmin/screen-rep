package com.ysten.local.bss.job.quartz;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.DeviceType;
import com.ysten.local.bss.device.domain.UserActivateSum;
import com.ysten.local.bss.device.remote.domain.RemoteUserActivateSum;
import com.ysten.local.bss.device.service.IActivateSumService;
import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.local.bss.device.service.IDeviceCustomerAccountMapService;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ActivateSumJob {
	
	private static final Logger logger = LoggerFactory .getLogger(ActivateSumJob.class);
	
	private String province;
	private String telecom;
	private String vendor;
	private String lastDate;
	
	@Autowired
	private IDeviceCustomerAccountMapService deviceCustomerAccountMapService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IActivateSumService activateSumService;
	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private ICustomerService customerService;
	
	
	public void executeSum(){
		try {
			if(logger.isInfoEnabled()) {
				logger.info("------------------counting activated device last day-------- start");
			}
			initEnvironment();
			List<DeviceCustomerAccountMap> lastDayCreatedMap = this.deviceCustomerAccountMapService.findDeviceCustomerAccountMapCreatedLastDay();
			if(null != lastDayCreatedMap && lastDayCreatedMap.size() > 0) {
				List<UserActivateSum> userActivatLastDayList = getUserActivateLastDayList(lastDayCreatedMap);
				if(null != userActivatLastDayList && userActivatLastDayList.size() > 0) {
					saveUserActivateLastDayListLocalAndRemote(userActivatLastDayList);
				}
			}
			if(logger.isInfoEnabled()) {
				logger.info("------------------counting activated device last day-------- end");
			}
		} catch (Exception e) {
			logger.error("Activate sum job failed", e);
		}
	}
	
	public void executeSync() throws Exception{
		if(logger.isInfoEnabled()) {
			logger.info("------------------sync activate result with central-------- start");
		}
		try {
			List<UserActivateSum> notSyncList = this.activateSumService.getNotSync();
			if(notSyncList != null && notSyncList.size() > 0){
				if(logger.isInfoEnabled()) {
					logger.info("sync with central, record sie: " + notSyncList.size());
				}
				List<RemoteUserActivateSum> remoteUserActivateSumList = createRemoteUserActivateSumList(notSyncList);
				this.activateSumService.syncData(notSyncList, remoteUserActivateSumList);
			}
		} catch (Exception e) {
			logger.error("Sync activate sum data to remote failed", e);
		}
		if(logger.isInfoEnabled()) {
			logger.info("------------------sync activate result with central-------- end");
		}
	}
	
	/**
	 * 
	 */
	private void initEnvironment() {
		//省份
		this.province = this.systemConfigService.getSystemConfigByConfigKey("areaId");
		//运营商
		this.telecom = this.systemConfigService.getSystemConfigByConfigKey("telecom");
		//厂商
		this.vendor = this.systemConfigService.getSystemConfigByConfigKey("vendor");
		this.lastDate = DateUtils.getDateString(DateUtils.reduceDays(new Date(), 1));
	}
	
	/**
	 * 
	 * @param lastDayCreatedMap
	 * @return
	 */
	private List<UserActivateSum> getUserActivateLastDayList(List<DeviceCustomerAccountMap> lastDayCreatedMap) {
		DeviceCustomerAccountMap dcam = null;
		long cityId = 0l;
		String deviceTypeId = null;
		Device device = null;
		List<UserActivateSum> userActivateSumList = new ArrayList<UserActivateSum>();
		UserActivateSum uas = null;
		for(int index = 0; index < lastDayCreatedMap.size(); index++) {
			dcam = lastDayCreatedMap.get(index);
			device = this.deviceService.getDeviceByDeviceId(dcam.getDeviceId());
			if(device.getCity() == null){
				continue;
			}
			cityId = device.getCity().getId();
			DeviceType dType = device.getDeviceType();
			deviceTypeId = dType == null ? "" : dType.getCode();
			uas = getUserActivateSumByCityAndDeviceType(cityId + "", deviceTypeId, userActivateSumList);
			uas.setActivateDay(uas.getActivateDay() + 1);
		}
		for(int index = 0; index < userActivateSumList.size(); index++) {
			uas = userActivateSumList.get(index);
			setUserActivateTotalCount(uas);
			
			uas.setUserDay(customerService.getAllCustomerCreatedLastDay(uas.getCityId()));
			uas.setUserAll(customerService.getAllCustomer(uas.getCityId()));
			uas.setStbReturnDay(customerService.getAllCustomerCancelLastDay(uas.getCityId()));
			uas.setStbReceiveDay(deviceService.getAllDeviceCreatedLastDay(uas.getCityId()));
		}
		return userActivateSumList;
	}
	
	/**
	 * 
	 * @param cityId
	 * @param deviceTypeId
	 * @param userActivateSumList
	 * @return
	 */
	private UserActivateSum getUserActivateSumByCityAndDeviceType(String cityId, String deviceTypeId, List<UserActivateSum> userActivateSumList) {
		for(UserActivateSum uas : userActivateSumList) {
			if(cityId.equals(uas.getCityId()) && deviceTypeId.equals(uas.getTerminalId())) {
				return uas;
			}
		}
		UserActivateSum newUas = new UserActivateSum(cityId, deviceTypeId);
		newUas.setDate(this.lastDate);
		newUas.setProvinceId(this.province);
		newUas.setTelecomId(this.telecom);
		newUas.setVendorId(this.vendor);
		userActivateSumList.add(newUas);
		return newUas;
	}
	
	/**
	 * 
	 * @param uas
	 */
	private void setUserActivateTotalCount(UserActivateSum uas) {
		long activateAll = this.deviceCustomerAccountMapService.getTotalCountByCityAndDeviceType(uas.getCityId(), uas.getTerminalId());
		uas.setActivateAll(activateAll);
	}
	
	/**
	 * 
	 * @param userActivatLastDayList
	 * @return
	 * @throws Exception 
	 */
	private boolean saveUserActivateLastDayListLocalAndRemote(List<UserActivateSum> userActivatLastDayList) throws Exception {
		this.activateSumService.saveActivateSumList(userActivatLastDayList);
		return true;
	}
	
	/**
	 * 
	 * @param userActivatLastDayList
	 * @return
	 * @throws Exception 
	 */
	private List<RemoteUserActivateSum> createRemoteUserActivateSumList(List<UserActivateSum> userActivatLastDayList) throws Exception {
		List<RemoteUserActivateSum> remoteUserActivateSumList = new ArrayList<RemoteUserActivateSum>();
		RemoteUserActivateSum rusa = null;
		for(int index = 0; index < userActivatLastDayList.size(); index++) {
			rusa = (RemoteUserActivateSum)com.ysten.local.bss.util.EntityUtils.createTargetCopyFromSrc(userActivatLastDayList.get(index), new RemoteUserActivateSum(), new String[]{"id", "sync", "syncDate"});
			remoteUserActivateSumList.add(rusa);
		}
		return remoteUserActivateSumList;
	}
}
