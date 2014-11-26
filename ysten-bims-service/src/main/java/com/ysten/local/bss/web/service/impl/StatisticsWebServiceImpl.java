package com.ysten.local.bss.web.service.impl;

import com.ysten.area.domain.Area;
import com.ysten.area.service.IAreaService;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.domain.Customer.State;
import com.ysten.local.bss.device.domain.UserActivateSum.SyncType;
import com.ysten.local.bss.device.repository.*;
import com.ysten.local.bss.web.service.IStatisticsWebService;
import com.ysten.utils.page.Pageable;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class StatisticsWebServiceImpl implements IStatisticsWebService {

    private static final String source = "OTHERS";
    @Autowired
    private IActivateSumRepository activateSumRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private ICustomerGroupRepository customerGroupRepository;
    @Autowired
    private ICustomerCustRepository customerCustRepository;
    @Autowired
    private IAreaService areaService;
    @Autowired
    private ICityRepository cityRepository;


    @Override
    public Pageable<UserActivateSum> findUserActiveSum(String date, String province, SyncType sync, int pageNo,
            int pageSize) {
        return this.activateSumRepository.getUserActivateSumList(date, province, sync, pageNo, pageSize);
    }

    @Override
    public Pageable<UserActivateSum> getUserActiveSum(int pageNo, int pageSize) {
        return this.activateSumRepository.getAll(pageNo, pageSize);
    }

    @Override
    public List<Device> getByAreaAndType(int province, int E3, int E4) {
        return this.deviceRepository.getByAreaAndType(province, E3, E4);
    }

    @Override
    public List<Customer> getBySource(String source) {
        return this.customerRepository.getBySource(source);
    }

    @Override
    public Long getCountByUidDid(Date end, List<Long> customerIds, List<Long> deviceIds) {
        return this.customerRepository.getCountByUidDid(end, customerIds, deviceIds);
    }

    @Override
    public Long getCountByCreateDateAndUidDid(Date start, Date end, List<Long> customerIds, List<Long> deviceIds) {
        return this.customerRepository.getCountByCreateDateAndUidDid(start, end, customerIds, deviceIds);
    }

    @Override
    public Long getCountByCityAndDeviceType(Date start, Date end, int province, String source, String state,
            int deviceType, long cityId) {
        return this.customerRepository.getCountByCityAndDeviceType(start, end, province, source, state, deviceType,
                cityId);
    }

    @Override
    public Long getCountByRegionAndState(Date start, Date end, long city, int province, String source, String state) {
        return this.customerRepository.getCountByRegionAndState(start, end, city, province, source, state);
    }

    @Override
    public Long getCountByRegion(Date start, Date end, long city, int province, String source) {
        return this.customerRepository.getCountByRegion(start, end, city, province, source);
    }

    @Override
    public Long getCountByAera(Date start, Date end, int province, String source) {
        return this.customerRepository.getCountByAera(start, end, province, source);
    }

    @Override
    public Long getCountByState(Date start, Date end, int province, String source, String state) {
        return this.customerRepository.getCountByState(start, end, province, source, state);
    }

    @Override
    public List<CustomerCust> getCustomerCustListByIp(String ip) {
        return this.customerCustRepository.getCustomerCustListByIp(ip);
    }

    @Override
    public CustomerGroup getCustomerGroupByGroupId(String groupId) {
        return this.customerGroupRepository.getCustomerGroupByGroupId(groupId);
    }

    @Override
    public List<Customer> getCustomersByCustId(String custId) {
        return this.customerRepository.getCustomersByCustId(custId);
    }

    @Override
    public Device getDeviceById(long deviceId) {
        return this.deviceRepository.getDeviceById(deviceId);
    }

    @Override
    public List<DeviceCustomerAccountMap> getByCustomerId(Long customerId) {
        return this.customerRepository.getByCustomerId(customerId);
    }

    @Override
    public List<DeviceActiveStatisticsVo> getActiveDeviceReport(String activeTime, Long province) throws ParseException {
        List<DeviceActiveStatisticsVo> vList = new ArrayList<DeviceActiveStatisticsVo>();
        List<Long> typeIds = new ArrayList<Long>();
        List<Customer> customerList = this.customerRepository.getBySource(source);
        List<DeviceActiveStatistics> list = deviceRepository.getActiveDeviceReport(activeTime, province, customerList);
        List<City> cities = cityRepository.findAllCityByLeaderId(province);
        if(cities != null && cities.size() > 0 && list != null && list.size() > 0){
            List<Long> cityIds = new ArrayList<Long>();
            Set<Long> typeSet = new HashSet<Long>();
            for(DeviceActiveStatistics das : list){
                typeSet.add(das.getDeviceTypeId());
            }
            Iterator<Long> it = typeSet.iterator();
            while(it.hasNext()){
                typeIds.add(it.next());
            }
            Integer citySize = cities.size();
            Integer typeSize = typeIds.size();
            DeviceActiveStatistics[][] dasArray = new DeviceActiveStatistics[citySize][2*typeSize];
            for(int i=0; i<citySize; i++){//数据初始化，初始值
                cityIds.add(cities.get(i).getId());
                for(int j=0; j<2*typeSize; j++){
                    DeviceActiveStatistics das = new DeviceActiveStatistics();
                    das.setCityId(cities.get(i).getId());
                    if(j - typeSize < 0){
                        das.setDeviceTypeId(typeIds.get(j));
                        das.setFlag("one");
                    }else{
                        das.setDeviceTypeId(typeIds.get(j-typeSize));
                        das.setFlag("all");
                    }
                    das.setRowTotal(0);
                    dasArray[i][j] = das;
                }
            }
            
            for(DeviceActiveStatistics das : list){//赋值
                Integer i = cityIds.indexOf(das.getCityId());
                Integer j = typeIds.indexOf(das.getDeviceTypeId());
                if(i > -1 && j > -1){
                    if("one".equals(das.getFlag())){
                        DeviceActiveStatistics inDas = dasArray[i][j];
                        inDas.setRowTotal(das.getRowTotal());
                        dasArray[i][j] = inDas;
                    }else{
                        DeviceActiveStatistics inDas = dasArray[i][j+typeSize];
                        inDas.setRowTotal(das.getRowTotal());
                        dasArray[i][j+typeSize] = inDas;
                    }
                }
            }
            
            for(int i=0; i<citySize; i++){//组装vo
                for(int j=0; j<2*typeSize; j++){
                    DeviceActiveStatistics das = dasArray[i][j];
                    DeviceActiveStatisticsVo vo = new DeviceActiveStatisticsVo();
                    City city = cities.get(i);
                    vo.setCityName(city == null ? das.getCityId() + "" : city.getName());
                    DeviceType deviceType = deviceRepository.getDeviceTypeById(das.getDeviceTypeId());
                    vo.setDeviceTypeName(deviceType == null ? das.getDeviceTypeId()+"" : deviceType.getName());
                    vo.setRowTotal(das.getRowTotal());
                    vo.setFlag(das.getFlag());
                    vList.add(vo);
                }
            }
            
            List<DeviceActiveStatisticsVo> statisticsVoList = new ArrayList<DeviceActiveStatisticsVo>();
            Integer oneTotal = 0;
            Integer allTotal = 0;
            for(int j=0; j<2*typeSize; j++){//组装统计vo
                Integer typeTotal = 0;
                DeviceType deviceType = null;
                String typeName = "";
                for(int i=0; i<citySize; i++){
                    DeviceActiveStatistics das = dasArray[i][j];
                    if(deviceType == null){
                        deviceType= deviceRepository.getDeviceTypeById(das.getDeviceTypeId());
                    }
                    typeName = (deviceType == null ? das.getDeviceTypeId()+"" : deviceType.getName());
                    typeTotal += das.getRowTotal();
                    if("one".equals(das.getFlag())){
                        oneTotal += das.getRowTotal();
                    }else{
                        allTotal += das.getRowTotal();
                    }
                }
                DeviceActiveStatisticsVo voTypeTotal = new DeviceActiveStatisticsVo();
                voTypeTotal.setRowTotal(typeTotal);
                voTypeTotal.setDeviceTypeName(typeName);
                if(j < typeSize){
                    voTypeTotal.setCityName("type-total");
                    voTypeTotal.setFlag("one");
                }else{
                    voTypeTotal.setCityName("type-total");
                    voTypeTotal.setFlag("all");
                }
                statisticsVoList.add(voTypeTotal);
            }
            DeviceActiveStatisticsVo oneTotalVo = new DeviceActiveStatisticsVo();
            oneTotalVo.setCityName("total");
            oneTotalVo.setFlag("one");
            oneTotalVo.setRowTotal(oneTotal);
            DeviceActiveStatisticsVo allTotalVo = new DeviceActiveStatisticsVo();
            allTotalVo.setCityName("total");
            allTotalVo.setFlag("all");
            allTotalVo.setRowTotal(allTotal);
            vList.addAll(statisticsVoList);
            vList.add(oneTotalVo);
            vList.add(allTotalVo);
        }
        return vList;
    }
    @Override
    public List<DeviceActiveStatisticsVo> getActiveDeviceChart(String activeTime, Long province) throws ParseException {
        List<DeviceActiveStatisticsVo> vList = new ArrayList<DeviceActiveStatisticsVo>();
        List<Customer> customerList = this.customerRepository.getBySource(source);
        List<DeviceActiveStatistics> list = deviceRepository.getActiveDeviceChart(activeTime, province, customerList);
        List<City> cities = cityRepository.findAllCityByLeaderId(province);
        if(cities != null && cities.size() > 0 && list != null && list.size() > 0){
            List<Long> cityIds = new ArrayList<Long>();
            Integer citySize = cities.size();
            DeviceActiveStatistics[][] dasArray = new DeviceActiveStatistics[citySize][2];
            for(int i=0; i<citySize; i++){//数据初始化，初始值
                cityIds.add(cities.get(i).getId());
                for(int j=0; j<2; j++){
                    DeviceActiveStatistics das = new DeviceActiveStatistics();
                    das.setCityId(cities.get(i).getId());
                    if(j < 1){
                        das.setFlag("one");
                    }else{
                        das.setFlag("all");
                    }
                    das.setRowTotal(0);
                    dasArray[i][j] = das;
                }
            }
          //赋值
            for(DeviceActiveStatistics das : list){
                Integer i = cityIds.indexOf(das.getCityId());
                if(i > -1 ){
                    if("one".equals(das.getFlag())){
                        DeviceActiveStatistics inDas = dasArray[i][0];
                        inDas.setRowTotal(das.getRowTotal());
                        dasArray[i][0] = inDas;
                    }else{
                        DeviceActiveStatistics inDas = dasArray[i][1];
                        inDas.setRowTotal(das.getRowTotal());
                        dasArray[i][1] = inDas;
                    }
                }
            }
          //组装
            for(int i=0; i<citySize; i++){
                for(int j=0; j<2; j++){
                    DeviceActiveStatistics das = dasArray[i][j];
                    DeviceActiveStatisticsVo vo = new DeviceActiveStatisticsVo();
                    City city = cities.get(i);
                    vo.setCityName(city == null ? das.getCityId() + "" : city.getName());
                    vo.setRowTotal(das.getRowTotal());
                    vo.setFlag(das.getFlag());
                    vList.add(vo);
                }
            }
            
//            List<DeviceActiveStatisticsVo> statisticsVoList = new ArrayList<DeviceActiveStatisticsVo>();
//            Integer oneTotal = 0;
//            Integer allTotal = 0;
//            for(int j=0; j<2; j++){//组装统计
//                Integer typeTotal = 0;
//                for(int i=0; i<citySize; i++){
//                    DeviceActiveStatistics das = dasArray[i][j];
//                    typeTotal += das.getRowTotal();
//                    if("one".equals(das.getFlag())){
//                        oneTotal += das.getRowTotal();
//                    }else{
//                        allTotal += das.getRowTotal();
//                    }
//                }
//                DeviceActiveStatisticsVo voTypeTotal = new DeviceActiveStatisticsVo();
//                voTypeTotal.setRowTotal(typeTotal);
//                if(j < 1){
//                    voTypeTotal.setCityName("type-total");
//                    voTypeTotal.setFlag("one");
//                }else{
//                    voTypeTotal.setCityName("type-total");
//                    voTypeTotal.setFlag("all");
//                }
//                statisticsVoList.add(voTypeTotal);
//            }
//            DeviceActiveStatisticsVo oneTotalVo = new DeviceActiveStatisticsVo();
//            oneTotalVo.setCityName("total");
//            oneTotalVo.setFlag("one");
//            oneTotalVo.setRowTotal(oneTotal);
//            DeviceActiveStatisticsVo allTotalVo = new DeviceActiveStatisticsVo();
//            allTotalVo.setCityName("total");
//            allTotalVo.setFlag("all");
//            allTotalVo.setRowTotal(allTotal);
//            vList.addAll(statisticsVoList);
//            vList.add(oneTotalVo);
//            vList.add(allTotalVo);
        }
        return vList;
    }
    @Override
    public List<UserActiveStatisticsVo> getActiveUserReport(String activeDate, Long province) throws ParseException {
        List<UserActiveStatisticsVo> voList = new ArrayList<UserActiveStatisticsVo>();
        List<City> cityList = cityRepository.findAllCityByLeaderId(province);
//        List<UserActiveStatistics> accountList = customerRepository.getAccountUserReport(activeDate, province);
//        List<UserActiveStatistics> activeList = deviceRepository.getActiveUserReport(activeDate, province);
        List<UserActiveStatistics> activeList = customerRepository.getActiveUserReport(activeDate, State.NORMAL.toString(), province);
        List<UserActiveStatistics> cancelList = customerRepository.getCancelUserReport(activeDate, State.CANCEL.toString(), province);
        for(City city : cityList){
            UserActiveStatisticsVo vo = new UserActiveStatisticsVo();
            vo.setCityName(city==null ? "" : city.getName());
            for(UserActiveStatistics cancel : cancelList){
                if(city.getId().equals(cancel.getCityId())){
                	if(cancel.getCancelUser() != 0){
                		vo.setCancelUser(cancel.getCancelUser());
                	}
                	if(cancel.getCancelUsers() != 0){
                		vo.setCancelUser(cancel.getCancelUsers());
                	}                	
                }
            }
            for(UserActiveStatistics active : activeList){
                if(city.getId().equals(active.getCityId())){
                	if(active.getActiveUser() != 0){
                		vo.setActiveUser(active.getActiveUser());
                	}                	
                	if(active.getActiveUsers() != 0){
                		vo.setActiveUsers(active.getActiveUsers());
                	}                        
                }
            }
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<UserActivateSum> getAllUserActiveSum() {
        return this.activateSumRepository.findAllUserActiveSum();
    }

    @Override
    public File exportActiveDeviceByCity(String activeTime, Long province, String fileName) throws ParseException, IOException, RowsExceededException, WriteException {
        List<DeviceActiveStatisticsVo> list = this.getActiveDeviceReport(activeTime, province);
        Area area = this.areaService.getAreaById(province);
        String areaname = (area == null ? province+"" : area.getName());
        WritableWorkbook book = Workbook.createWorkbook(new File(fileName));
        WritableSheet sheet = book.createSheet("Sheet1", 0);
        WritableCellFormat wc = new WritableCellFormat(); 
        wc.setAlignment(Alignment.CENTRE);
        if(list != null && list.size() > 0){
            int rows = 0;
            int cols = 0;
            int size = list.size();
            for(int i=0; i<size-1; i++){
                DeviceActiveStatisticsVo vo = list.get(i);
                DeviceActiveStatisticsVo nextVo = list.get(i+1);
                if(vo.getCityName().equals(nextVo.getCityName())){
                    cols ++;
                }else{
                    break;
                }
            }
            rows = (size-2)/(cols+1) + 4;
            cols = cols + 2;
            sheet.mergeCells(0, 0, cols-1, 0);
            sheet.mergeCells(0, 1, 0, 2);
            sheet.mergeCells(1, 1, (cols-1)/2, 1);
            sheet.mergeCells((cols-1)/2+1, 1, cols-1, 1);
            sheet.mergeCells(0, rows-2, 0, rows-1);
            sheet.mergeCells(1, rows-1, (cols-1)/2, rows-1);
            sheet.mergeCells((cols-1)/2+1, rows-1, cols-1, rows-1);            
            Label tLabel1 = new Label(0, 0, activeTime+" "+areaname+"设备激活数据", wc);
            sheet.addCell(tLabel1);
            Label tLabel2 = new Label(0, 1, " ");
            sheet.addCell(tLabel2);
            Label tLabel3 = new Label(1, 1, "当日激活设备", wc);
            sheet.addCell(tLabel3);
            Label tLabel4 = new Label((cols-1)/2+1, 1, "激活总设备", wc);
            sheet.addCell(tLabel4); 
            Label tLabel5 = new Label(0, rows-2, "总计", wc);
            sheet.addCell(tLabel5);
            for(int i=1; i<cols; i++){//设备类型
                Label label = new Label(i, 2, list.get(i-1).getDeviceTypeName(), wc);
                sheet.addCell(label);
            }
            for(int i=0; i<size-2; i=i+(cols-1)){//设备数据
                if(!"type-total".equals(list.get(i).getCityName())){
                    Label label = new Label(0, 3+(i/(cols-1)), list.get(i).getCityName(), wc);
                    sheet.addCell(label);
                }
                for(int j=i; j<i+(cols-1); j++){
                    jxl.write.Number numberLabel = new jxl.write.Number((j%(cols -1))+1, 3+(i/(cols-1)), list.get(j).getRowTotal(), wc); 
                    sheet.addCell(numberLabel);
                }
            }
            jxl.write.Number eLabel1 = new jxl.write.Number(1, rows-1, list.get(size-2).getRowTotal(), wc);//统计数据
            sheet.addCell(eLabel1);
            jxl.write.Number eLabel2 = new jxl.write.Number((cols-1)/2+1, rows-1, list.get(size-1).getRowTotal(), wc);
            sheet.addCell(eLabel2);            
        }else{
            sheet.mergeCells(0, 0, 4, 0);
            sheet.mergeCells(1, 1, 4, 1);
            Label tLabel1 = new Label(0, 0, activeTime+" "+areaname+"设备激活数据", wc);
            sheet.addCell(tLabel1);
            Label tLabel2 = new Label(0, 1, "总计", wc);
            sheet.addCell(tLabel2);
            Label tLabel3 = new Label(1, 1, "暂无数据！", wc);
            sheet.addCell(tLabel3);
        }
        book.write();
        book.close();
        File file = new File(fileName);
        return file;
    }

    @Override
    public File exportActiveUserReport(String activeTime, Long province, String fileName) throws ParseException, IOException, RowsExceededException, WriteException {
        List<UserActiveStatisticsVo> list = getActiveUserReport(activeTime, province);
        Area area = this.areaService.getAreaById(province);
        String areaname = (area == null ? province+"" : area.getName());
        WritableWorkbook book = Workbook.createWorkbook(new File(fileName));
        WritableSheet sheet = book.createSheet("Sheet1", 0);
        WritableCellFormat wc = new WritableCellFormat(); 
        wc.setAlignment(Alignment.CENTRE);
        sheet.mergeCells(0, 0, 4, 0);
        Label tLable = new Label(0, 0, activeTime+" "+areaname+"用户数据", wc);        
        sheet.addCell(tLable);
        if(list !=null && list.size() > 0){
            Label tLable1 = new Label(0, 1, "地市", wc);
            sheet.addCell(tLable1);   
            Label tLable2 = new Label(1, 1, "开户用户", wc);
            sheet.addCell(tLable2);
            Label tLable3 = new Label(2, 1, "总开户用户", wc);
            sheet.addCell(tLable3);
            Label tLable4 = new Label(3, 1, "激活用户", wc);
            sheet.addCell(tLable4);
            Label tLable5 = new Label(4, 1, "总激活用户", wc);
            sheet.addCell(tLable5); 
            Long account = 0L;
            Long accounts = 0L;
            Long active = 0L;
            Long actives = 0L;
            int size = list.size();
            for(int i=0; i<size; i++){
                UserActiveStatisticsVo vo = list.get(i);
                Label nameLable = new Label(0, i+2, vo.getCityName(), wc);
                sheet.addCell(nameLable);
                Long tAccount = vo.getAccount() == null ? 0 : vo.getAccount();
                Long tAccounts = vo.getAccounts() == null ? 0 : vo.getAccounts();
                Long tActive = vo.getActiveUser() == null ? 0 : vo.getActiveUser();
                Long tActives = vo.getActiveUsers() == null ? 0 : vo.getActiveUsers();
                account = account + tAccount;
                accounts = accounts + tAccounts;
                active = active + tActive;
                actives = actives + tActives;
                jxl.write.Number bodyLabel1 = new jxl.write.Number(1, i+2, tAccount, wc);
                sheet.addCell(bodyLabel1);     
                jxl.write.Number bodyLabel2 = new jxl.write.Number(2, i+2, tAccounts, wc);
                sheet.addCell(bodyLabel2);  
                jxl.write.Number bodyLabel3 = new jxl.write.Number(3, i+2, tActive, wc);
                sheet.addCell(bodyLabel3);  
                jxl.write.Number bodyLabel4 = new jxl.write.Number(4, i+2, tActives, wc);
                sheet.addCell(bodyLabel4);  
            }
            Label nameLable = new Label(0, size+1, "总计", wc);
            jxl.write.Number bodyLabel1 = new jxl.write.Number(1, size+1, account, wc);
            sheet.addCell(bodyLabel1);     
            jxl.write.Number bodyLabel2 = new jxl.write.Number(2, size+1, accounts, wc);
            sheet.addCell(bodyLabel2);  
            jxl.write.Number bodyLabel3 = new jxl.write.Number(3, size+1, active, wc);
            sheet.addCell(bodyLabel3);  
            jxl.write.Number bodyLabel4 = new jxl.write.Number(4, size+1, actives, wc);
            sheet.addCell(bodyLabel4);              
            sheet.addCell(nameLable);
        }else{
            sheet.mergeCells(1, 1, 4, 1);
            Label lable1 = new Label(0, 1, "总计", wc);        
            sheet.addCell(lable1);
            Label lable2 = new Label(1, 1, "暂无数据", wc);        
            sheet.addCell(lable2);            
        }
        book.write();
        book.close();
        File file = new File(fileName);
        return file;
    }
}
