package com.ysten.local.bss.web.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerCust;
import com.ysten.local.bss.device.domain.CustomerGroup;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceActiveStatisticsVo;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.UserActivateSum;
import com.ysten.local.bss.device.domain.UserActivateSum.SyncType;
import com.ysten.local.bss.device.domain.UserActiveStatisticsVo;
import com.ysten.utils.page.Pageable;

public interface IStatisticsWebService {
	Pageable<UserActivateSum> findUserActiveSum(String date,String province,SyncType sync,int pageNo, int pageSize );
	Pageable<UserActivateSum> getUserActiveSum(int pageNo, int pageSize);
	//List<CustomerCust> getCustomerCustByIp(String ip);
	List<Device> getByAreaAndType(int province, int E3, int E4);
	List<Customer> getBySource(String source);
	Long getCountByUidDid(Date end,List<Long> customerIds,List<Long> deviceIds);
    Long getCountByCreateDateAndUidDid(Date  start,Date  end,List<Long> customerIds,List<Long> deviceIds);
    Long getCountByCityAndDeviceType(Date  start,Date  end,int province,String source,String state,int deviceType,long cityId);
    Long getCountByRegionAndState(Date  start,Date  end,long city,int province,String source,String state);
    Long getCountByRegion(Date  start,Date  end,long city,int province,String source);
    Long getCountByAera(Date  start,Date  end,int province,String source);
    Long getCountByState(Date  start,Date  end,int province,String source,String state);
	List<CustomerCust> getCustomerCustListByIp(String ip);
	CustomerGroup getCustomerGroupByGroupId(String groupId);
    List<Customer> getCustomersByCustId(String custId);
    Device getDeviceById(long deviceId);
    List<DeviceCustomerAccountMap> getByCustomerId(Long customerId);
    List<DeviceActiveStatisticsVo> getActiveDeviceReport(String activeTime,Long province)throws ParseException;
    List<DeviceActiveStatisticsVo> getActiveDeviceChart(String activeTime,Long province)throws ParseException;
    List<UserActiveStatisticsVo> getActiveUserReport(String activeDate, Long province)throws ParseException;
	List<UserActivateSum> getAllUserActiveSum();
	File exportActiveDeviceByCity(String activeTime, Long province, String fileName) throws ParseException, IOException, RowsExceededException, WriteException;
	File exportActiveUserReport(String activeTime, Long province, String fileName) throws ParseException, IOException, RowsExceededException, WriteException;
}
