//package com.ysten.local.bss.device.service.impl;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.google.gson.GsonBuilder;
//import com.ibm.icu.text.SimpleDateFormat;
//import com.ysten.local.bss.bean.tt.TtSyncData;
//import com.ysten.local.bss.device.api.domain.request.DefaultDisableDeviceRequest;
//import com.ysten.local.bss.device.api.domain.request.IBindDeviceRequest;
//import com.ysten.local.bss.device.api.domain.request.IBindMultipleDeviceRequest;
//import com.ysten.local.bss.device.api.domain.request.IDisableDeviceRequest;
//import com.ysten.local.bss.device.api.domain.request.IRebindDeviceRequest;
//import com.ysten.local.bss.device.api.domain.request.tt.TtAuthenticationInitialRequest;
//import com.ysten.local.bss.device.api.domain.request.tt.TtBindDeviceRequest;
//import com.ysten.local.bss.device.api.domain.request.tt.TtBindMultipleDeviceRequest;
//import com.ysten.local.bss.device.api.domain.request.tt.TtRebindDeviceRequest;
//import com.ysten.local.bss.device.api.domain.request.tt.TtUserInfo;
//import com.ysten.local.bss.device.api.domain.request.tt.TtUserRequest;
//import com.ysten.local.bss.device.api.domain.response.DefaultResponse;
//import com.ysten.local.bss.device.api.domain.response.IResponse;
//import com.ysten.local.bss.device.api.domain.response.tt.TtAuthenticationInitialRespose;
//import com.ysten.local.bss.device.api.domain.response.tt.TtUserResponse;
//import com.ysten.local.bss.device.domain.Customer;
//import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
//import com.ysten.local.bss.device.domain.Device;
//import com.ysten.local.bss.device.domain.Device.BindType;
//import com.ysten.local.bss.device.domain.Device.SyncType;
//import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
//import com.ysten.local.bss.device.domain.LockType;
//import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
//import com.ysten.local.bss.device.service.ITtGroupService;
//import com.ysten.local.bss.talk.service.ITalkSystemService;
//import com.ysten.local.bss.util.DateUtils;
//import com.ysten.local.bss.util.HttpUtils;
//import com.ysten.local.bss.util.HttpUtils.HttpResponseWrapper;
//import com.ysten.local.bss.util.bean.Constant;
//import com.ysten.local.bss.util.bean.FtpBean;
//import com.ysten.local.bss.utils.FtpUtils;
//import com.ysten.utils.FileUtil;
//import com.ysten.utils.date.DateUtil;
//
//@Service
//public class TtGroupServiceImpl extends DefaultDeviceNewServiceImpl implements ITtGroupService {
//    private Logger LOGGER = LoggerFactory.getLogger(TtGroupServiceImpl.class);
//    
//    @Autowired
//	private ITalkSystemService talkSystemService;
////    @Autowired
////    private IOrderRepository orderRepository;
////    @Autowired
////    private IPpInfoRepository ppInfoRepository;
//	@Autowired
//	private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
//	
//
//    // 重写开户接口的检查参数操作，增加添加订购产品包代码
//    @Override
//    public IResponse checkBindDeviceParam(IBindDeviceRequest request) throws Exception {
//        if (!(request instanceof TtBindDeviceRequest)) {
//            return new DefaultResponse(false, "device bind failed, because param wrong");
//        }
//        TtBindDeviceRequest _request = (TtBindDeviceRequest) request;
//        IResponse response = super.checkBindDeviceParam(_request);
//        if (response != null) {
//            return response;
//        }
//        if (StringUtils.isBlank(_request.getServiceId())) {
//            return new DefaultResponse(false, "device bind failed because serviceId is null!");
//        }
//        if (StringUtils.isBlank(_request.getServiceName())) {
//            return new DefaultResponse(false, "device bind failed because serviceName is null!");
//        }
//        if (StringUtils.isBlank(_request.getOrderDate())) {
//            return new DefaultResponse(false, "device bind failed because orderDate is null!");
//        }
//        if (StringUtils.isBlank(_request.getOrderType())) {
//            return new DefaultResponse(false, "device bind failed because orderType is null!");
//        }
//        if (StringUtils.isBlank(_request.getFree())) {
//            return new DefaultResponse(false, "device bind failed because free is null!");
//        }
//        if (StringUtils.isBlank(_request.getValidDate())) {
//            return new DefaultResponse(false, "device bind failed because validDate is null!");
//        }
//        if (StringUtils.isBlank(_request.getExpireDate())) {
//            return new DefaultResponse(false, "device bind failed because expireDate is null!");
//        }
//        if (_request.getValidDate().length() != 14) {
//            return new DefaultResponse(false, "device bind failed because validDate format is error!");
//        }
//        if (_request.getExpireDate().length() != 14) {
//            return new DefaultResponse(false, "device bind failed because expireDate format is error!");
//        }
//        Date _validDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", _request.getValidDate());
//        Date _expireDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", _request.getExpireDate());
//        if (_validDate.after(_expireDate)) {
//            return new DefaultResponse(false, "device bind failed because expireDate should be before validDate!");
//        }
//        return null;
//    }
//
//    // 重写开户接口的后续操作，增加添加订购产品包代码
//    @Override
//    public IResponse doAfterBind(IBindDeviceRequest request, boolean isBindSuccess) throws Exception {
//        TtBindDeviceRequest _request = (TtBindDeviceRequest) request;
//        return new DefaultResponse("user and device bind success!device sno:" + _request.getSno());
//    }
//
//    // 重写集团用户开户接口的操作，增加添加订购产品包代码
//    @Override
//    public IResponse checkBindMultipleDeviceParam(IBindMultipleDeviceRequest request) throws Exception {
//        if (!(request instanceof TtBindMultipleDeviceRequest)) {
//            return new DefaultResponse(false, "device bind failed, because param wrong");
//        }
//        TtBindMultipleDeviceRequest _request = (TtBindMultipleDeviceRequest) request;
//        IResponse response = super.checkBindMultipleDeviceParam(_request);
//        if (response != null) {
//            return response;
//        }
//        if (StringUtils.isBlank(_request.getServiceId())) {
//            return new DefaultResponse(false, "bind multiple device failed because serviceId is null!");
//        }
//        if (StringUtils.isBlank(_request.getServiceName())) {
//            return new DefaultResponse(false, "bind multiple device failed because serviceName is null!");
//        }
//        if (StringUtils.isBlank(_request.getOrderDate())) {
//            return new DefaultResponse(false, "bind multiple device failed because orderDate is null!");
//        }
//        if (StringUtils.isBlank(_request.getOrderType())) {
//            return new DefaultResponse(false, "bind multiple device failed because orderType is null!");
//        }
//        if (StringUtils.isBlank(_request.getFree())) {
//            return new DefaultResponse(false, "bind multiple device failed because free is null!");
//        }
//        if (StringUtils.isBlank(_request.getValidDate())) {
//            return new DefaultResponse(false, "bind multiple device failed because validDate is null!");
//        }
//        if (StringUtils.isBlank(_request.getExpireDate())) {
//            return new DefaultResponse(false, "bind multiple device failed because expireDate is null!");
//        }
//        if (_request.getValidDate().length() != 14) {
//            return new DefaultResponse(false, "bind multiple device failed because validDate format is error!");
//        }
//        if (_request.getExpireDate().length() != 14) {
//            return new DefaultResponse(false, "bind multiple device failed because expireDate format is error!");
//        }
//        Date _validDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", _request.getValidDate());
//        Date _expireDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", _request.getExpireDate());
//        if (_validDate.after(_expireDate)) {
//            return new DefaultResponse(false,
//                    "bind multiple device failed because expireDate should be before validDate!");
//        }
//        return null;
//    }
//
//    // 重写集团用户开户接口的后续操作，增加添加订购产品包代码
//    @Override
//    public IResponse doAfterBindMultipleDevice(IBindMultipleDeviceRequest request, boolean isBindMultipleDeviceSuccess)
//            throws Exception {
//        return new DefaultResponse("bind multiple device success!");
//    }
//
//    @Override
//    public boolean downloadAndSyncOpenAndCancelCustomerData() throws Exception {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -1);
//        // String yesterday = new
//        // SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        List<Customer> customers = this.customerRepository.getOpenAndCancelCustomer(yesterday);
//        if (customers == null || customers.isEmpty()) {
//            return true;
//        }
//        List<String> records = this.getFormatterRecordsByCustomers(customers, yesterday);
//        return this.sendRecordsToFtp(records);
//    }
//
//    private List<String> getFormatterRecordsByCustomers(List<Customer> customers, String yesterday) throws Exception {
//        List<String> records = null;
//        Date beginDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", yesterday + " 00:00:00");
//        Date endDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", yesterday + " 23:59:59");
//        for (Customer customer : customers) {
//            // 开户数据
//            if (customer.getState().equals(Customer.State.NORMAL)) {
//                List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
//                if (maps == null || maps.isEmpty()) {
//                    continue;
//                }
//                if (records == null) {
//                    records = new ArrayList<String>();
//                }
//                records.add(constructRecordByCustomerAndSno(customer, maps.get(0).getDeviceSno()));
//                // 销户数据
//            } else if (customer.getState().equals(Customer.State.CANCEL)) {
//                List<CustomerDeviceHistory> historys = this.customerRepository
//                        .findCustomerDeviceHistorysByCustomerIdAndDate(customer.getId(), beginDate, endDate);
//                if (historys == null || historys.isEmpty()) {
//                    continue;
//                }
//                for (CustomerDeviceHistory history : historys) {
//                    // history的deviceId为空，代表是销户数据
//                    if (history.getDeviceId() == null) {
//                        Device device = this.deviceRepository.getDeviceByCode(history.getOldDeviceCode());
//                        if (records == null) {
//                            records = new ArrayList<String>();
//                        }
//                        records.add(constructRecordByCustomerAndSno(customer, device.getSno()));
//                    }
//                }
//            }
//        }
//        return records;
//    }
//
//    private String constructRecordByCustomerAndSno(Customer customer, String sno) {
//        
//        StringBuffer sb = new StringBuffer();
//        sb.append(sno)
//                .append("|")
//                .append("null|")
//                .append("1|")
//                .append(customer.getLoginName() == null ? "null" : customer.getLoginName())
//                .append("|")
//                .append(customer.getPhone())
//                .append("|")
//                .append(DateUtil.convertDateToString("yyyyMMddHHmmss", customer.getCreateDate()))
//                .append("|")
//                .append(customer.getServiceStop() == null ? "null" : customer.getServiceStop())
//                .append("|")
//                .append(customer.getRegion() == null ? "null" : customer.getRegion().getName())
//                .append("|")
//                .append(customer.getCounty() == null ? "null" : customer.getCounty())
//                .append("|")
//                .append(customer.getAddress() == null ? "null" : customer.getAddress())
//                .append("|")
//                .append(customer.getRate() == null ? "null" : customer.getRate())
//                .append("|")
//                .append(customer.getUserId() == null ? "null" : customer.getUserId())
//                .append("|")
//                .append(customer.getCustomerType().equals(Customer.CustomerType.PERSONAL) ? "0" : "1");
//        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug(sb.toString());
//        }
//        return sb.toString();
//    }
//
//    @SuppressWarnings("null")
//    private boolean sendRecordsToFtp(List<String> records) throws Exception {
//        String ttStatisUploadFtpIp = null;
//        String ttStatisUploadFtpPort = null;
//        String ttStatisUploadFtpUsername = null;
//        String ttStatisUploadFtpPassword = null;
//        String ttStatisUploadFtpPath = null;
//        String ttStatisUploadLocalPath = null;
//        String fileName = null;
//        // 首先将数据保存文件到本地
//        if (ttStatisUploadLocalPath.endsWith("/")) {
//            fileName = ttStatisUploadLocalPath + DateUtils.getDate2String(DateUtils.getCurrentDate(), "yyyy-MM-dd")
//                    + ".txt";
//        } else {
//            fileName = ttStatisUploadLocalPath + "/"
//                    + DateUtils.getDate2String(DateUtils.getCurrentDate(), "yyyy-MM-dd") + ".txt";
//        }
//        LOGGER.info("TieTong save local statistic data path: " + fileName);
//        File file = new File(fileName);
//        if (!file.exists() && !file.createNewFile()) {
//            return false;
//        }
//        for (String record : records) {
//            FileUtil.saveFile(file, record, "UTF-8", true);
//        }
//        // 其次，将文件上传到ftp
//        FtpUtils ftpUtils = null;
//        try {
//            ftpUtils = new FtpUtils(ttStatisUploadFtpIp, Integer.parseInt(ttStatisUploadFtpPort),
//                    ttStatisUploadFtpUsername, ttStatisUploadFtpPassword);
//            ftpUtils.upload(ttStatisUploadFtpPath, file.getName(), file);
//        } finally {
//            if (ftpUtils != null) {
//                ftpUtils.disConnect();
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public IResponse acceptDataFromProvince(String jsonData) throws Exception {
//        TtSyncData ttSyncData = new GsonBuilder().create().fromJson(jsonData, TtSyncData.class);
//        Customer customer = ttSyncData.getCustomer();
//        List<DeviceCustomerAccountMap> maps = ttSyncData.getMaps();
//        List<Device> devices = ttSyncData.getDevices();
//        this.saveCustomer(customer);
//        this.saveDeviceCustomerMaps(customer, maps, devices);
//        return new DefaultResponse("Accept Data Success!");
//    }
//
//    private void saveCustomer(Customer customer) {
//        Customer _customer = this.customerRepository.getCustomerByCode(customer.getCode());
//        if (_customer == null) {
//            this.customerRepository.saveCustomer(customer);
//        } else {
//            customer.setId(_customer.getId());
//            this.customerRepository.updateCustomer(customer);
//        }
//    }
//
//    private void saveDeviceCustomerMaps(Customer customer, List<DeviceCustomerAccountMap> maps, List<Device> devices) {
//        List<DeviceCustomerAccountMap> _maps = this.deviceCustomerAccountMapRepository.findDeviceCustomerAccountMapByCustomerCode(customer.getCode());
//        // 首先根据customer信息删除该用户绑定的设备信息
//        if(_maps != null && !_maps.isEmpty()){
//            for (DeviceCustomerAccountMap _map : _maps) {
//                Device device = this.deviceRepository.getDeviceByCode(_map.getDeviceCode());
//                device.setState(Device.State.NONACTIVATED);
//                device.setBindType(BindType.UNBIND);
//                device.setIsLock(LockType.LOCK);
//                device.setIsSync(SyncType.WAITSYNC);
//                this.deviceRepository.updateDevice(device);
//                
//            	List<DeviceCustomerAccountMap>  list=deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(_map.getDeviceId());
//            	for(int i=0;i<list.size();i++){
//            		DeviceCustomerAccountMap map=list.get(i);
//            		deviceCustomerAccountMapRepository.delete(map);
//            	}
//            }
//        }
//        if(maps == null || maps.isEmpty()){
//            return;
//        }
//        Map<String, Device> devicesMap = this.convertDeviceListToMap(devices);
//        // 重新插入设备绑定信息,先插入设备信息，更新map关系中的设备id，最后插入map关系
//        for (DeviceCustomerAccountMap map : maps) {
//            Device device = devicesMap.get(map.getDeviceCode());
//            if (device == null) {
//                continue;
//            }
//            Device _device = this.deviceRepository.getDeviceByCode(device.getCode());
//            if (_device == null) {
//                this.deviceRepository.saveDevice(device);
//            } else {
//                device.setId(_device.getId());
//                this.deviceRepository.updateDevice(device);
//            }
//            map.setDeviceId(device.getId());
//            map.setCustomerId(customer.getId());
//            this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);
//        }
//    }
//
//    private Map<String, Device> convertDeviceListToMap(List<Device> devices) {
//        if(devices == null || devices.isEmpty()){
//            return null;
//        }
//		Map<String, Device> map = null;
//		for(Device device : devices){
//			if(map == null){
//				map = new HashMap<String, Device>();
//			}
//			map.put(device.getCode(), device);
//		}
//		return map;
//	}
//
//    @Override
//    public boolean syncDataToCenter() throws Exception {
//        String _ttSyncToCenterNum = this.systemConfigService.getSystemConfigByConfigKey("ttSyncToCenterNum");
//        if (StringUtils.isBlank(_ttSyncToCenterNum)) {
//            throw new Exception("请先维护配置表中的ttSyncToCenterNum字段");
//        }
//        Integer ttSyncToCenterNum = null;
//        try {
//            ttSyncToCenterNum = Integer.parseInt(_ttSyncToCenterNum);
//        } catch (Exception e) {
//            throw new Exception("请正确配置配置表中的ttSyncToCenterNum字段，必须为数字");
//        }
//        // 获取待推送的用户数据
//        List<Customer> customers = this.customerRepository.getAllCustomerByIsSync(ttSyncToCenterNum);
//        for(Customer customer : customers){
//            TtSyncData ttSyncData = this.getNeedSyncProvinceData(customer);
//            if (ttSyncData == null) {
//                customer.setIsSync(SyncType.SYNCED);
//                this.customerRepository.updateCustomer(customer);
//                continue;
//            }
//            String ttSyncToCenterUrl = this.systemConfigService.getSystemConfigByConfigKey("ttSyncToCenterUrl");
//            String jsonData = new GsonBuilder().create().toJson(ttSyncData);
//            LOGGER.info("Sync To Center Url: " + ttSyncToCenterUrl);
//            LOGGER.info("Sync To Center Data: " + jsonData);
//            HttpResponseWrapper wrapper = HttpUtils.doPost(ttSyncToCenterUrl, jsonData, "UTF-8");
//            LOGGER.info("Sync To Center Return :" + wrapper.getResponse());
//            if (wrapper.getHttpStatus() == 200) {
//                String jsonRsp = wrapper.getResponse();
//                DefaultResponse rsp = new GsonBuilder().create().fromJson(jsonRsp, DefaultResponse.class);
//                if (StringUtils.equalsIgnoreCase(rsp.getResult(), "true")) {
//                    customer.setIsSync(SyncType.SYNCED);
//                }else {
//                    customer.setIsSync(SyncType.FAILED);
//                }
//                this.customerRepository.updateCustomer(customer);
//            }
//        }
//        return true;
//    }
//
//    private TtSyncData getNeedSyncProvinceData(Customer customer) {
//        TtSyncData ttSyncData = null;
//        List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
//        ttSyncData = new TtSyncData(customer, maps);
//        if(maps == null || maps.isEmpty()){
//            ttSyncData.setDevices(null);
//        }else{
//            for (DeviceCustomerAccountMap map : maps) {
//                Device device = this.deviceRepository.getDeviceById(map.getDeviceId());
//                List<Device> devices = ttSyncData.getDevices();
//                if (devices == null) {
//                    devices = new ArrayList<Device>();
//                    ttSyncData.setDevices(devices);
//                }
//                devices.add(device);
//            }
//        }
//        return ttSyncData;
//    }
//
//    @Override
//    public boolean syncDataToAAA() throws Exception{
////        String yesterday = DateUtils.getDateString(DateUtils.reduceDays(new Date(), 1));
////        Date beginDate = DateUtils.getDate(yesterday+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
////        Date endDate = DateUtils.getDate(yesterday+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
//////        List<TtUserInfo> userInfos = this.getTtUserInfos(yesterday, beginDate, endDate);
////        List<TtOrderInfo> orderInfos = this.getTtOrderInfos(beginDate, endDate);
////        String ttLocalUserInfoPath = this.systemConfigService.getSystemConfigByConfigKey("ttLocalUserInfoPath");
////        if(StringUtils.isBlank(ttLocalUserInfoPath)){
////            throw new Exception("请先维护配置表中的ttLocalUserInfoPath字段");
////        }
////        String ttFtpServer = this.systemConfigService.getSystemConfigByConfigKey("ttFtpServer");
////        FtpBean ftpBean = new FtpBean(ttFtpServer);
////        String ttFtpIp = ftpBean.getService();
////        String ttFtpPort = ftpBean.getPort();
////        String ttFtpUserName = ftpBean.getUserName();
////        String ttFtpPassWord = ftpBean.getPassword();
////        String ttFtpPath = this.systemConfigService.getSystemConfigByConfigKey("ttFtpPath");
////        if(StringUtils.isBlank(ttFtpPath)){
////            throw new Exception("请先维护配置表中的ttFtpPath字段");
////        }
////        if(!ttLocalUserInfoPath.endsWith("/")){
////            ttLocalUserInfoPath = ttLocalUserInfoPath + "/";
////        }
////        if(!ttFtpPath.endsWith("/")){
////            ttFtpPath = ttFtpPath + "/";
////        }
//////        this.pushTtUserInfos(userInfos, ttLocalUserInfoPath, ttFtpIp, ttFtpPort, ttFtpUserName, ttFtpPassWord, ttFtpPath);
////        this.pushTtOrderInfos(orderInfos, ttLocalUserInfoPath, ttFtpIp, ttFtpPort, ttFtpUserName, ttFtpPassWord, ttFtpPath);
//        return true;
//    }
//    
//    
//    private void pushTtUserInfos(List<TtUserInfo> userInfos, String ttLocalUserInfoPath, String ttFtpIp, 
//            String ttFtpPort, String ttFtpUserName, String ttFtpPassWord, String ttFtpPath) throws Exception{
//        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        String fileName = "01.UserInfo."+date+".0001.txt";
//        File file = new File(ttLocalUserInfoPath+fileName);
//        OutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//            for(TtUserInfo userInfo : userInfos){
//                os.write((userInfo.toString()+"\r\n").getBytes());
//            }
//        } finally{
//            if(os!=null){
//                os.close();
//            }
//        }
//        FtpUtils ftpUtils = null;
//        try {
//            ftpUtils = new FtpUtils(ttFtpIp, Integer.parseInt(ttFtpPort), ttFtpUserName, ttFtpPassWord);
//            ftpUtils.upload(ttFtpPath, fileName, file);
//        } finally{
//            if(ftpUtils != null){
//                ftpUtils.disConnect();
//            }
//        }
//    }
//
////    private void pushTtOrderInfos(List<TtOrderInfo> orderInfos, String ttLocalUserInfoPath, String ttFtpIp, 
////            String ttFtpPort, String ttFtpUserName, String ttFtpPassWord, String ttFtpPath) throws Exception{
////        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
////        String fileName = "01.OrderInfo."+date+".0001.txt";
////        File file = new File(ttLocalUserInfoPath+fileName);
////        OutputStream os = null;
////        try {
////            os = new FileOutputStream(file);
////            for(TtOrderInfo orderInfo : orderInfos){
////                os.write((orderInfo.toString()+"\r\n").getBytes());
////            }
////        } finally{
////            if(os!=null){
////                os.close();
////            }
////        }
////        FtpUtils ftpUtils = null;
////        try {
////            ftpUtils = new FtpUtils(ttFtpIp, Integer.parseInt(ttFtpPort), ttFtpUserName, ttFtpPassWord);
////            ftpUtils.upload(ttFtpPath, fileName, file);
////        } finally{
////            if(ftpUtils != null){
////                ftpUtils.disConnect();
////            }
////        }
////    }
//
//    public static void main(String[] args) {
//        String ysterday = DateUtils.getDateString(DateUtils.reduceDays(new Date(), 1));
//        Date beginDate = DateUtils.getDate(ysterday+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
//        Date endDate = DateUtils.getDate(ysterday+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
//       /* System.out.println(beginDate);
//        System.out.println(endDate);*/
//    }
//    
//    
//    /**************************************************************铁通换机操作********************************************************/
//	/**
//	 * 换机操作参数检查
//	 */
//	@Override
//	public IResponse checkRebindDeviceParam(IRebindDeviceRequest request) throws Exception {
//		if(!(request instanceof TtRebindDeviceRequest)){
//			return new DefaultResponse(false, "device rebind failed, because param wrong");
//		}
//		TtRebindDeviceRequest _request = (TtRebindDeviceRequest)request;
//		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
//		if(!isExist){
//			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
//		}
////		if(StringUtils.isBlank(_request.getPhone())){
////			return new DefaultResponse(false, "device rebind failed because phone is null!");
////		}
//		
//		if(StringUtils.isBlank(_request.getUserId())){
//			return new DefaultResponse(false, "device rebind failed because userid is null!");
//		}
//		if(StringUtils.isBlank(_request.getOldSno())){
//			return new DefaultResponse(false, "device rebind failed because old sno is null!");
//		}
//		if(StringUtils.isBlank(_request.getNewSno())){
//			return new DefaultResponse(false, "device rebind failed because new sno is null!");
//		}
//		Customer customer = this.customerRepository.getCustomerByUserId(_request.getUserId());
//		if(customer == null){
//			return new DefaultResponse(false, "device rebind failed because userid is not exist!");
//		}
//		Device oldDevice = this.deviceRepository.getDeviceBySno(_request.getOldSno());
//		if(oldDevice == null){
//			return new DefaultResponse(false, "device rebind failed because old sno is not exist!");
//		}
//		Customer _customer = this.customerRepository.getCustomerByDeviceId(oldDevice.getId());
//		if(_customer == null){
//			return new DefaultResponse(false, "user rebind device failed: there is no relationship between user and old device!");
//		}
//		if(!customer.getId().equals(_customer.getId())){
//			return new DefaultResponse(false, "device rebind failed because old sno and userid can not be matched!");
//		}
//		Device newDevice = this.deviceRepository.getDeviceBySno(_request.getNewSno());
//		if(newDevice == null){
//			return new DefaultResponse(false, "device rebind failed because new sno is not exist!");
//		}
//		
//		if(!newDevice.getState().equals(Device.State.NONACTIVATED)){
//			return new DefaultResponse(false, "device rebind failed because newSno device state must be non activated!");
//		}
//		
//		List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(newDevice.getId());
//		if(maps != null && !maps.isEmpty()){
//			return new DefaultResponse(false, "device rebind failed, newSno device is bind customer!");
//		}
//		return null;
//	}
//	
//    
//    /**************************************************************铁通销户操作********************************************************/
//	@Override
//	public IResponse disableDevice(IDisableDeviceRequest request) throws Exception {
//		IResponse response = this.checkDisableDeviceParam(request);
//		if(response != null){
//			return response;
//		}
//		boolean isDisableSuccess = this.doDisableDeviceAndCustomer(request);
//		return this.doAfterDisable(request, isDisableSuccess);
//	}
//	
//	/**
//	 * 铁通销户 验证
//	 * @author chenxiang
//	 * @date 2014-6-24 下午2:16:06 
//	 */
//	@Override
//	public IResponse checkDisableDeviceParam(IDisableDeviceRequest request) throws Exception {
//		if(!(request instanceof DefaultDisableDeviceRequest)){
//			return new DefaultResponse(false, "device disable failed, because param wrong");
//		}
//		DefaultDisableDeviceRequest _request = (DefaultDisableDeviceRequest)request;
//		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
//		if(!isExist){
//			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
//		}
//		if(StringUtils.isBlank(_request.getSno())){
//			return new DefaultResponse(false, "device disable failed because sno is null!");
//		}
//		
//		Device device = this.deviceRepository.getDeviceByCodeSnoAndMac(request.getSno());
//		if(null == device){
//			return new DefaultResponse(false, "device disable failed because sno not find device!");
//		}
//		
//		return null;
//	}
//	
//	
//	/**
//	 * 铁通销户操作
//	 * @author chenxiang
//	 * @date 2014-6-24 下午2:41:51 
//	 */
//	protected boolean doDisableDeviceAndCustomer(IDisableDeviceRequest request) {
//		Device device = this.deviceRepository.getDeviceByCodeSnoAndMac(request.getSno());
//		
//		DeviceCustomerAccountMap deviceCustomerAccountMap = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
//		if(null == deviceCustomerAccountMap){
//			return false;
//		}
//		
//		Customer customer = this.customerRepository.getCustomerById(deviceCustomerAccountMap.getCustomerId());
//		
//		//添加是否同步状态，以便铁通数据同步
//		customer.setIsSync(SyncType.WAITSYNC);
// 		customer.setState(Customer.State.CANCEL);
// 		customer.setCancelledDate(new Date());
//		
//		// 终端停用
// 	    device.setState(Device.State.NONACTIVATED);
//	    device.setBindType(BindType.UNBIND);
//	    device.setIsLock(LockType.LOCK);
//	    device.setStateChangeDate(new Date());
//	    this.deviceRepository.updateDevice(device);
//    	List<DeviceCustomerAccountMap>  list=deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
//    	for(int i=0;i<list.size();i++){
//    		DeviceCustomerAccountMap map=list.get(i);
//    		deviceCustomerAccountMapRepository.delete(map);
//    	}
//	    //新增用户历史记录
//        CustomerDeviceHistory history = this.createCustomerDeviceHistory(null, device, customer);
//        this.deviceRepository.saveCustomerDeviceHistory(history);
//		
//        // 销户
//        this.customerRepository.updateCustomer(customer);
//        
//		return true;
//	}
//    
//    
//	
//	/**
//	 * 开户验证接口
//	 * @author chenxiang
//	 * @date 2014-6-23 上午11:13:40 
//	 * 
//	 * @param request 请求数据
//	 * @return IResponse
//	 * @throws Exception
//	 */
//	@Override
//	public IResponse userAuth(TtUserRequest request) throws Exception {
//		if(!(request instanceof TtUserRequest)){
//            return new TtUserResponse(TtUserResponse.FAIL_CODE, "user auth failed, because param wrong",TtUserResponse.VERIFY_RES);
//		}
//		
//		//验证stbid是否为空
//		if (StringUtils.isBlank(request.getStbId())) {
//            return new TtUserResponse(TtUserResponse.FAIL_CODE, "user auth failed because stbid is null!",TtUserResponse.VERIFY_RES);
//        }
//		
////		//验证MobileNumber是否为空
////		if (StringUtils.isBlank(request.getMobileNumber())) {
////            return new TtUserResponse(TtUserResponse.FAIL_CODE, "user auth failed because MobileNumber is null!,",TtUserResponse.VERIFY_RES);
////        }
//		
////		//根据手机号 查询用户 验证手机号是否有效
////		Customer customer = this.customerRepository.getCustomerByPhone(request.getMobileNumber());
////		if(null == customer){
////            return new TtUserResponse(TtUserResponse.FAIL_CODE, "user auth failed because MobileNumber is not exists!",TtUserResponse.VERIFY_RES);
////		}
//		
//		//根据设备code stbid验证是否有该设备
//		Device device = this.deviceRepository.getDeviceByCode(request.getStbId());
//		if(null == device){
//            return new TtUserResponse(TtUserResponse.FAIL_CODE, "user auth failed because stbid is not exists!",TtUserResponse.VERIFY_RES);
//		}
//		
//		//验证根据stbid是否绑定的用户
//		DeviceCustomerAccountMap deviceCustomerAccountMap = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(request.getStbId());
//		if(null == deviceCustomerAccountMap){
//            return new TtUserResponse(TtUserResponse.FAIL_CODE, "user auth failed because this stbid is not bind",TtUserResponse.VERIFY_RES);
//		}
////		if(deviceCustomerAccountMap.getUserId() != customer.getUserId()){
////            return new TtUserResponse(TtUserAuthResponse.FAIL_CODE, "user auth failed because this this stbid is not bind to this MobileNumber",TtUserResponse.VERIFY_RES);
////		}
//		
//        return new TtUserResponse(TtUserResponse.SUCESS_CODE, "user auth success! MobileNumber:" +request.getMobileNumber() + " stbid:"+request.getStbId(),TtUserResponse.VERIFY_RES);
//	}
//
//
//	  /**
//     * 用户信息获取接口
//     * @author chenxiang
//     * @date 2014-6-23 下午3:01:53 
//     * @param request 请求数据
//     * @return IResponse
//     * @throws Exception
//     */
//	@Override
//	public IResponse requestUserInfo(TtUserRequest request) throws Exception {
//		if(!(request instanceof TtUserRequest)){
//            return new TtUserResponse(TtUserResponse.FAIL_CODE, "request userinfo failed, because param wrong",TtUserResponse.USER_INFO_RES);
//		}
//		
//		//验证stbid是否为空
//		if (StringUtils.isBlank(request.getStbId())) {
//            return new TtUserResponse(TtUserResponse.FAIL_CODE, "request userinfo failed, because stbid is null!",TtUserResponse.USER_INFO_RES);
//        }
//		
//		//根据设备code stbid验证是否有该设备
//		Device device = this.deviceRepository.getDeviceByCode(request.getStbId());
//		if(null == device){
//            return new TtUserResponse(TtUserResponse.FAIL_CODE, "request userinfo failed, because stbid is not exists!",TtUserResponse.USER_INFO_RES);
//		}
//		
//		//验证根据stbid是否绑定的用户
//		DeviceCustomerAccountMap deviceCustomerAccountMap = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(request.getStbId());
//		if(null == deviceCustomerAccountMap){
//            return new TtUserResponse(TtUserResponse.FAIL_CODE, "request userinfo failed, because this stbid is not bind to this user",TtUserResponse.USER_INFO_RES);
//		}
//		
//		//根据deviceCustomerAccountMap中的userid查询用户信息
//		Customer customer = this.customerRepository.getCustomerByUserId(deviceCustomerAccountMap.getUserId());
//		if(null == customer){
//            return new TtUserResponse(TtUserResponse.FAIL_CODE, "request userinfo failed, because this stbid not find userinfo",TtUserResponse.USER_INFO_RES);
//		}
//		
//        return new TtUserResponse(TtUserResponse.SUCESS_CODE, "user auth success! stbid:"+request.getStbId(),TtUserResponse.USER_INFO_RES,customer.getUserId(),customer.getPassword());
//	}
//
//	/**
//	 * 开户认证接口
//	 * @author chenxiang
//	 * @date 2014-7-7 下午1:08:45 
//	 */
//	@Override
//	public IResponse authenticationInitial(TtAuthenticationInitialRequest request) throws Exception {
//		if(!(request instanceof TtAuthenticationInitialRequest)){
//            return new TtAuthenticationInitialRespose(TtAuthenticationInitialRespose.REQUESTERROR_CODE, "authenticationInitial failed, because param wrong");
//		}
//		
//		//验证stbid是否为空
//		if (StringUtils.isBlank(request.getStbId())) {
//            return new TtAuthenticationInitialRespose(TtAuthenticationInitialRespose.REQUESTERROR_CODE, "authenticationInitial failed because stbid is null!");
//        }
//		
//		
//		//根据设备code stbid验证是否有该设备
//		Device device = this.deviceRepository.getDeviceByCode(request.getStbId());
//		if(null == device){
//            return new TtAuthenticationInitialRespose(TtAuthenticationInitialRespose.RESPOSEERROR_CODE, "authenticationInitial failed because stbid is not exists!");
//		}
//		
//		//验证根据stbid是否绑定的用户
//		DeviceCustomerAccountMap deviceCustomerAccountMap = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(request.getStbId());
//		if(null == deviceCustomerAccountMap){
//            return new TtAuthenticationInitialRespose(TtAuthenticationInitialRespose.RESPOSEERROR_CODE, "authenticationInitial failed because this stbid is not bind");
//		}
//		
//        return new TtAuthenticationInitialRespose(TtAuthenticationInitialRespose.SUCESS_CODE, "authenticationInitial success! stbid :"+request.getStbId());
//	}
//
//}
