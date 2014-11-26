package com.ysten.local.bss.web.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.repository.*;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.repository.IPanelPackageRepository;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.area.domain.Area;
import com.ysten.area.repository.IAreaRepository;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.domain.Customer.CustomerType;
import com.ysten.local.bss.device.domain.Customer.IdentityType;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.UserGroupType;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.local.bss.interfaceUrl.repository.IInterfaceUrlRepository;
import com.ysten.local.bss.notice.domain.UserNoticeMap;
import com.ysten.local.bss.notice.repository.ISysNoticeRepository;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.HttpUtils.HttpResponseWrapper;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.utils.AESAlgorithm;
import com.ysten.local.bss.web.service.ICustomerWebService;
import com.ysten.utils.date.DateUtil;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.springframework.util.CollectionUtils;

@Repository
public class CustomerWebServiceImpl implements ICustomerWebService {
    private Logger LOGGER = LoggerFactory.getLogger(CustomerWebServiceImpl.class);
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private ICustomerGroupRepository customerGroupRepository;
    @Autowired
    private ICustomerCustRepository customerCustRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private IAreaRepository areaRepository;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private IUserGroupRepository userGroupRepository;
    @Autowired
    private IUserGroupMapRepository userGroupMapRepository;
    @Autowired
    private ISysNoticeRepository sysNoticeRepository;
    @Autowired
    private IBootAnimationRepository bootAnimationRepository;
    @Autowired
    private IUpgradeTaskRepository upgradeTaskRepository;
    @Autowired
    private IAppUpgradeTaskRepository appUpgradeTaskRepository;
    @Autowired
    private IInterfaceUrlRepository interfaceUrlRepository;
    @Autowired
    private IBackgroundImageRepository backgroundImageRepository;
    @Autowired
    private IPanelPackageUserMapRepository panelPackageUserMapRepository;
    @Autowired
    private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
    @Autowired
    private IUserGroupPpInfoMapRepository userGroupPpInfoMapRepository;
    @Autowired
    private ICityRepository cityRepository;
    @Autowired
    private IServiceCollectRepository serviceCollectRepository;
    @Autowired
    private IPanelPackageRepository panelPackageRepository;

    private static final String UTF8 = "utf-8";
    private static final String SERVICESTOPTIME = "2099-12-31 23:59:59";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Pageable<Customer> findCustomersByCondition(Map<String, Object> map) {
        return this.customerRepository.findCustomersByCondition(map);
    }

    @Override
    public void save(Customer customer) {
        customer.setCode(NumberGenerator.getNextCode());
        this.customerRepository.saveCustomer(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return this.customerRepository.getCustomerById(id);
    }

    @Override
    public String update(Customer customer) {
        Customer dbCustomer = this.customerRepository.getCustomerById(customer.getId());
        dbCustomer.setState(customer.getState());
        dbCustomer.setPassword(customer.getPassword());
        dbCustomer.setUserId(customer.getUserId());
        dbCustomer.setArea(customer.getArea());
        dbCustomer.setCustomerType(customer.getCustomerType());
        dbCustomer.setSource(customer.getSource());
        dbCustomer.setNickName(customer.getNickName());
        dbCustomer.setSex(customer.getSex());
        dbCustomer.setPhone(customer.getPhone());
        dbCustomer.setIdentityType(customer.getIdentityType());
        dbCustomer.setHobby(customer.getHobby());
        dbCustomer.setServiceStop(customer.getServiceStop());
        dbCustomer.setLoginName(customer.getLoginName());
        dbCustomer.setRegion(customer.getRegion());
        dbCustomer.setMail(customer.getMail());
        dbCustomer.setRealName(customer.getRealName());
        dbCustomer.setAge(customer.getAge());
        dbCustomer.setProfession(customer.getProfession());
        dbCustomer.setIdentityCode(customer.getIdentityCode());
        dbCustomer.setZipCode(customer.getZipCode());
        dbCustomer.setRate(customer.getRate());
        dbCustomer.setDeviceCode(customer.getDeviceCode());
        dbCustomer.setAddress(customer.getAddress());
        dbCustomer.setIsLock(customer.getIsLock());
        dbCustomer.setUpdateTime(new Date());
        dbCustomer.setIsSync(SyncType.WAITSYNC);

        City city = cityRepository.getCityById(dbCustomer.getRegion().getId());
        if(city.getLeaderId().intValue()==dbCustomer.getArea().getId().intValue()){
            this.customerRepository.updateCustomer(dbCustomer);
            return "修改用户信息成功！";
        }else{
            return "修改用户信息失败！";
        }

    }

//    @Override
//    public String remoteCustomerDevice(List<Long> customerDeviceMapIds) {
//        List<Device> devices = new ArrayList<Device>();
//        List<CustomerDeviceHistory> historys = new ArrayList<CustomerDeviceHistory>();
//        for (Long customerDeviceMapId : customerDeviceMapIds) {
//            DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository
//                    .getDeviceCustomerAccountMapById(customerDeviceMapId);
//            if (map != null) {
//                Device device = this.deviceRepository.getDeviceByCode(map.getDeviceCode());
//                if (device != null) {
//                    device.setState(Device.State.NONACTIVATED);
//                    device.setBindType(BindType.UNBIND);
//                    device.setStateChangeDate(new Date());
//                    device.setIsSync(SyncType.WAITSYNC);
//                    devices.add(device);
//                    CustomerDeviceHistory history = new CustomerDeviceHistory();
//                    history.setCreateDate(new Date());
//                    history.setOldDeviceCode(map.getDeviceCode());
//                    history.setOldYstenId(map.getYstenId());
//                    history.setOldDeviceId(map.getDeviceId());
//                    history.setCustomerCode(map.getCustomerCode());
//                    history.setCustomerId(map.getCustomerId());
//                    historys.add(history);
//                } else {
//                    return "设备不存在！";
//                }
//            } else {
//                return "用户已解绑！";
//            }
//        }
//
//        for (int i = 0; i < devices.size(); i++) {
//            List<DeviceCustomerAccountMap> list = deviceCustomerAccountMapRepository
//                    .getDeviceCustomerAccountMapByDeviceId(devices.get(i).getId());
//            for (int j = 0; j < list.size(); j++) {
//                DeviceCustomerAccountMap map = list.get(j);
//                deviceCustomerAccountMapRepository.delete(map);
//            }
//            this.deviceRepository.updateDevice(devices.get(i));
//            this.deviceRepository.saveCustomerDeviceHistory(historys.get(i));
//        }
//        return Constants.SUCCESS;
//    }

    @Override
    public String bindDevice(String customerId, List<Long> deviceIds) throws ParseException {
        Customer customer = this.customerRepository.getCustomerById(Long.parseLong(customerId));
        List<Device> devices = new ArrayList<Device>();
        for (Long deviceId : deviceIds) {
            Device device = deviceRepository.getDeviceById(deviceId);
            if (device == null) {
                return "设备不存在！";
            }
            List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository
                    .getDeviceCustomerAccountMapByDeviceId(device.getId());
            if (mapList != null && mapList.size() > 0) {
                return "设备已绑定！";
            }
            devices.add(device);
        }
        return bindDeviceAndMap(customer, devices);
    }

    public String addCustomer(Customer customer) throws Exception {
        customer.setCode(NumberGenerator.getNextCode());
        customer.setCreateDate(new Date());
        customer.setStartDate(new Date());
        customer.setActivateDate(new Date());
        customer.setIsSync(SyncType.WAITSYNC);
        customer.setIsLock(LockType.UNLOCKED);
        if (StringUtils.isBlank(customer.getServiceStop().toString())) {
            customer.setServiceStop(sdf.parse(SERVICESTOPTIME));
            customer.setEndDate(sdf.parse(SERVICESTOPTIME));
        } else {
            customer.setEndDate(customer.getServiceStop());
        }
        customer.setStateChangeDate(new Date());
        if (StringUtils.isBlank(customer.getDeviceCode())) {
            return "添加失败，用户未选择设备！";
        } else {
            String[] deviceCodes = customer.getDeviceCode().split(",");
            if (deviceCodes.length > 1) {
                customer.setDeviceCode(null);
            }
            List<Device> devices = new ArrayList<Device>();
            for (String deviceCode : deviceCodes) {
                Device device = deviceRepository.getDeviceByCode(deviceCode);
                if (device == null) {
                    return "设备不存在！";
                }else{
                	if(device.getArea() != null && customer.getArea() != null && !customer.getArea().getId().equals(device.getArea().getId())){
                		return "设备与用户不是同一区域！";
                	}
                }
                List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository
                        .getDeviceCustomerAccountMapByDeviceId(device.getId());
                if (mapList != null && mapList.size() > 0) {
                    return "设备已绑定！";
                }
                devices.add(device);
            }
            this.customerRepository.saveCustomer(customer);
            String message = bindDeviceAndMap(customer, devices);
            if (!Constants.SUCCESS.equals(message)) {
                throw new Exception("设备修改未成功！");
            }
            return message;
        }
    }

    private String bindDeviceAndMap(Customer customer, List<Device> devices) throws ParseException {
        if (devices.size() == 0) {
            return "未找到可以绑定的设备！";
        } else {
            for (Device device : devices) {
                device.setState(Device.State.ACTIVATED);
                device.setBindType(BindType.BIND);
                device.setActivateDate(new Date());
                device.setStateChangeDate(new Date());
                device.setIsSync(SyncType.WAITSYNC);
                device.setExpireDate(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", "2099-12-31 23:59:59"));
                this.deviceRepository.updateDevice(device);

                DeviceCustomerAccountMap map = new DeviceCustomerAccountMap();
                map.setYstenId(device.getYstenId());
                map.setCustomerId(customer.getId());
                map.setCustomerCode(customer.getCode());
                map.setDeviceCode(device.getCode());
                map.setDeviceId(Long.parseLong(device.getId() + ""));
                map.setCreateDate(new Date());
                map.setDeviceSno(device.getSno());
                map.setUserId(customer.getUserId());
                this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);
            }
            return Constants.SUCCESS;
        }
    }

    @Override
    public String delete(List<Long> customerIds) {
        int i = 0;
        String str = "";
        for (Long customerId : customerIds) {
            Customer customer = this.customerRepository.getCustomerById(customerId);
            Device map = this.deviceRepository.getDeviceByCustomerId(customerId);
            List<UserGroupMap> userGroupMap = this.userGroupMapRepository.findByUserCode(customer.getCode());
            List<UserNoticeMap> noticeMap = this.sysNoticeRepository.findSysNoticeMapByUserCode(customer.getCode());
            AnimationUserMap animationMap = this.bootAnimationRepository.findMapByUserCode(customer.getCode(), "USER");
//            UserUpgradeMap upgradeMap = this.upgradeTaskRepository.getUserUpgradeMapByUserId(customer.getUserId());
//            List<UserAppUpgradeMap> appUpgtade = this.appUpgradeTaskRepository.getUserUpgradeMapByUserId(customer
//                    .getUserId());
            List<BackgroundImageUserMap> backgroundMap = this.backgroundImageRepository.findMapByUserCode(customer.getCode());
            PanelPackageUserMap panelMap = this.panelPackageUserMapRepository.getMapByUserCode(customer.getCode());
            if (map != null) {
                str += "用户编号为:" + customer.getCode() + "的用户已绑定设备，无法删除！";
            }
            if (userGroupMap != null && userGroupMap.size() > 0) {
                str += "用户编号为:" + customer.getUserId() + "的用户已绑定用户分组，无法删除！";
            }
            if (animationMap != null) {
                str += "用户编号为:" + customer.getCode() + "的用户已绑定动画，无法删除！";
            }
            if (noticeMap != null && noticeMap.size() > 0) {
                str += "用户外部编号为:" + customer.getUserId() + "的用户已绑定通知，无法删除！";
            }
//            if (upgradeMap != null) {
//                str += "用户外部编号为:" + customer.getUserId() + "的用户已绑定终端升级任务，无法删除！";
//            }
//            if (appUpgtade != null && appUpgtade.size() > 0) {
//                str += "用户外部编号为:" + customer.getUserId() + "的用户已绑定应用升级任务，无法删除！";
//            }
            if (backgroundMap != null && backgroundMap.size() > 0) {
                str += "用户编号为:" + customer.getCode() + "的用户已绑定背景轮换，无法删除！";
            }
            if (panelMap != null) {
                str += "用户编号为:" + customer.getCode() + "的用户已绑定PANEL面板组，无法删除！";
            } 
//            else if (map.size() == 0 && appUpgtade.size() == 0 && backgroundMap.size() == 0 && panelMap == null
//                    && userGroupMap.size() == 0 && animationMap == null && noticeMap.size() == 0 && upgradeMap == null) {
//                this.customerRepository.delete(customerId);
//                i++;
//            }
            else if (map == null  && backgroundMap.size() == 0 && panelMap == null
                    && userGroupMap.size() == 0 && animationMap == null && noticeMap.size() == 0 ) {
                this.customerRepository.delete(customerId);
                i++;
            }
        }
        if (i == customerIds.size()) {
            return Constants.SUCCESS;
        } else {
            return str;
        }

    }

//    @Override
//    public Pageable<CustomerDeviceHistoryVo> findCustomerDeviceHistorys(Map<String, String> map) {
//        return this.customerRepository.findCustomerDeviceHistorys(map);
//    }

    @Override
    public Pageable<CustomerGroup> findCustomerGroups(Map<String, String> map) {
        return this.customerGroupRepository.findCustomerGroups(map);
    }

    @Override
    public Pageable<CustomerCust> findCustomerCusts(Map<String, String> map) {
        return this.customerCustRepository.findCustomerCusts(map);
    }

    @Override
    public Pageable<Customer> findCanBindCustomerList(Customer customer, int pageNo, int pageSize) {
        return this.customerRepository.findCanBindCustomerList(customer, pageNo, pageSize);
    }

    @Override
    public Pageable<CustomerRelationDeviceVo> findCustomerRelationByCondition(Map<String, Object> map) {
        return this.customerRepository.findCustomerRelationByCondition(map);
    }

    @Override
    public Pageable<Device> findCustomerCanBindDeviceList(Device device, int pageNo, int pageSize) {
        return this.deviceRepository.findCustomerCanBindDeviceList(device, pageNo, pageSize);
    }

    @Override
    public String getCustomerPassword(String id) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Customer customer = this.customerRepository.getCustomerById(Long.parseLong(id));
        if (StringUtils.isNotBlank(customer.getPassword())) {
            return AESAlgorithm.getContentFromToken(customer.getPassword(), "1r39df456j8wet45",
                    "AES/ECB/ZeroBytePadding");
        } else {
            return "密码为空";
        }
    }

//    @Override
//    public List<CustomerDeviceHistoryVo> findCustomerDeviceHistoryByState(Map<String, Object> map) {
//        return this.customerRepository.findCustomerDeviceHistoryByState(map);
//    }

    @Override
    public List<Customer> findAllCustomersByCondition(Customer customer) {
        return this.customerRepository.findAllCustomersByCondition(customer);
    }

    @Override
    public List<Customer> findAllCanBindCustomerList(Customer customer) {
        return this.customerRepository.findAllCanBindCustomerList(customer);
    }

    @Override
    public List<CustomerRelationDeviceVo> findAllCustomerRelation(CustomerRelationDeviceVo vo) {
        return this.customerRepository.findAllCustomerRelation(vo);
    }

    @Override
    public Map<String, Object> importCustomerFile(File targetFile) throws Exception {
        Map<String, Object> map = readDevicesFromFile(targetFile);
        if (!(Boolean) map.get("isSuccess")) {
            return map;
        }
        String[][] data = (String[][]) map.get("data");
        String importFileSize = this.systemConfigService.getSystemConfigByConfigKey("importFileSize");
        if (data.length > Integer.parseInt(importFileSize)) {
            map.put("error", "Excel文档最大导入条数：" + importFileSize);
            map.put("isSuccess", false);
            return map;
        }
        List<Customer> customers = convertToList(data);
        for (Customer customer : customers) {
            // Customer _customer =
            // this.customerRepository.getCustomerByUserId(customer.getUserId());
            if (customer.getArea() == null) {
                this.appendErrorMsg(map, "用户外部编号为 :" + customer.getUserId() + "的用户， 区域值不正确!");
                continue;
            }
            if (customer.getRegion() == null) {
                this.appendErrorMsg(map, "用户外部编号为 :" + customer.getUserId() + "的用户， 地市值不正确!");
                continue;
            }
            // if(_customer == null){
            // this.customerRepository.saveCustomer(customer);
            // }else {
            // //customer.setId(_customer.getId());
            // //this.customerRepository.updateCustomer(_customer);
            // this.appendErrorMsg(map, "用户外部编号为 :" + _customer.getUserId() +
            // ", 外部编号重复!");
            // }
            try {
                this.customerRepository.saveCustomer(customer);
            } catch (Exception e) {
                this.appendErrorMsg(map, "用户外部编号为 :" + customer.getUserId() + ", 外部编号重复!");
            }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private void appendErrorMsg(Map<String, Object> map, String error) {
        List<String> errors = (List<String>) map.get("error");
        if (errors == null) {
            errors = new ArrayList<String>();
            map.put("error", errors);
            map.put("isSuccess", false);
        }
        errors.add(error);
    }

    private Map<String, Object> readDevicesFromFile(File targetFile) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isSuccess", true);
        // 创建输入流
        InputStream stream = new FileInputStream(targetFile);
        // 获取Excel文件对象
        Workbook rwb = Workbook.getWorkbook(stream);
        // 获取文件的指定工作表 默认的第一个
        Sheet sheet = rwb.getSheet(0);
        if (sheet.getRows() <= 1) {
            map.put("error", "空Excel文档，请确认!");
            map.put("isSuccess", false);
            return map;
        }
        String[][] data = new String[sheet.getRows() - 1][sheet.getColumns()];
        // 行数(表头的目录不需要，从1开始)
        for (int i = 1; i < sheet.getRows(); i++) {
            boolean isCheckTrue = true;
            for (int j = 0; j < sheet.getColumns(); j++) {
                data[i - 1][j] = sheet.getCell(j, i).getContents();
                if (j < 10 && StringUtils.isBlank(data[i - 1][j])) {
                    isCheckTrue = false;
                    break;
                }
                if (!isCheckTrue) {
                    @SuppressWarnings("unchecked")
                    List<String> errors = (List<String>) map.get("error");
                    if (errors == null) {
                        errors = new ArrayList<String>();
                        map.put("error", errors);
                        map.put("isSuccess", false);
                    }
                    errors.add("Row " + i + " error, 用户外部编号，登陆名，用户类型，用户状态，所属区域，所属地市，服务到期时间，用户来源,用户电话不能为空！");
                }
            }
        }
        map.put("data", data);
        return map;
    }

    private List<Customer> convertToList(String[][] data) throws ParseException {
        List<Customer> customers = null;
        Map<String, Object> areaCache = new HashMap<String, Object>();
        Map<String, Object> cityCache = new HashMap<String, Object>();
        for (String[] di : data) {
            Customer customer = new Customer();
            // 第一列：用户外部编号
            if (StringUtils.isNotEmpty(di[0])) {
                customer.setUserId(FilterSpaceUtils.replaceBlank(di[0]));
            }
            // 第二列：登陆名
            if (StringUtils.isNotEmpty(di[1])) {
                customer.setLoginName(FilterSpaceUtils.replaceBlank(di[1]));
            }
            // 第三列：用户类型
            if (StringUtils.isNotEmpty(di[2])) {
                if (CustomerType.GROUP.getDisplayName().equals(FilterSpaceUtils.replaceBlank(di[2]))) {
                    customer.setCustomerType(CustomerType.GROUP);
                } else {
                    customer.setCustomerType(CustomerType.PERSONAL);
                }
            }
            // 第四列：用户状态
            if (StringUtils.isNotEmpty(di[3])) {
                if (com.ysten.local.bss.device.domain.Customer.State.NORMAL.getDisplayName().equals(FilterSpaceUtils.replaceBlank(di[3]))) {
                    customer.setState(com.ysten.local.bss.device.domain.Customer.State.NORMAL);
                } else if (com.ysten.local.bss.device.domain.Customer.State.UNUSABLE.getDisplayName().equals(FilterSpaceUtils.replaceBlank(di[3]))) {
                    customer.setState(com.ysten.local.bss.device.domain.Customer.State.UNUSABLE);
                } else if (com.ysten.local.bss.device.domain.Customer.State.CANCEL.getDisplayName().equals(FilterSpaceUtils.replaceBlank(di[3]))) {
                    customer.setState(com.ysten.local.bss.device.domain.Customer.State.CANCEL);
                } else if (com.ysten.local.bss.device.domain.Customer.State.SUSPEND.getDisplayName().equals(FilterSpaceUtils.replaceBlank(di[3]))) {
                    customer.setState(com.ysten.local.bss.device.domain.Customer.State.SUSPEND);
                }
            }
            // 第五列：所属区域
            if (StringUtils.isNotEmpty(di[4])) {
                if (areaCache.get(di[4]) == null) {
                    Area area = this.areaRepository.getAreaByNameOrCode(FilterSpaceUtils.replaceBlank(di[4]), null);
                    areaCache.put(FilterSpaceUtils.replaceBlank(di[4]), area);
                }
                customer.setArea((Area) areaCache.get(FilterSpaceUtils.replaceBlank(di[4])));
            }
            // 第六列：所属地市
            if (StringUtils.isNotEmpty(di[5])) {
                if (cityCache.get(di[5]) == null) {
                    City city = this.cityRepository.getCityByName(FilterSpaceUtils.replaceBlank(di[5]));
                    cityCache.put(FilterSpaceUtils.replaceBlank(di[5]), city);
                }
                customer.setRegion((City) cityCache.get(FilterSpaceUtils.replaceBlank(di[5])));
                customer.setDistrictCode(customer.getRegion().getDistCode());
            }
            // 第七列：服务到期时间
            if (StringUtils.isNotEmpty(di[6])) {
                customer.setServiceStop(sdf.parse(di[6]));
            }
            // 第八列：用户来源
            if (StringUtils.isNotEmpty(di[7])) {
                customer.setSource(FilterSpaceUtils.replaceBlank(di[7]));
            }
            // 第九列：用户电话
            if (StringUtils.isNotEmpty(di[8])) {
                customer.setPhone(FilterSpaceUtils.replaceBlank(di[8]));
            }
            // 第十列
            if (StringUtils.isNotEmpty(di[9])) {
                String key = this.systemConfigService.getSystemConfigByConfigKey("crmAesKey");
                customer.setPassword(AESAlgorithm.getEncryptPassword(FilterSpaceUtils.replaceBlank(di[9]), key));
            }
            customer.setCreateDate(new Date());
            customer.setIdentityType(IdentityType.IDCARD);
            customer.setIsLock(LockType.UNLOCKED);
            customer.setIsSync(SyncType.WAITSYNC);
            customer.setCode(NumberGenerator.getNextCode());
            if (customers == null) {
                customers = new ArrayList<Customer>();
            }
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public List<Customer> getCustomerByIds(List<Long> ids) {
        return this.customerRepository.getCustomerByIds(ids);
    }

    @Override
    public List<Customer> findCustomersToExport(Map<String, Object> map) {
        return this.customerRepository.findCustomersToExport(map);
    }

    @Override
    public Customer getByUserId(String userId) {
        return this.customerRepository.getCustomerByUserId(userId);
    }

    @Override
    public Pageable<UserGroup> findUserGroupByNameAndType(String name, String type, Long areaId, Integer pageNo, Integer pageSize) {
        return this.userGroupRepository.findUserGroupByNameAndType(FilterSpaceUtils.filterStartEndSpace(name), type, areaId, pageNo, pageSize);
    }
    @Override
	public String checkPpVail(UserGroup userGroup){
		if(StringUtils.isBlank(userGroup.getProductId())){
			return null;
		}
		List<UserGroupPpInfoMap> um = this.userGroupPpInfoMapRepository.findMapByUserGroupIdAndProductId(null, userGroup.getProductId());
		if(um != null && um.size() >0){
			for(UserGroupPpInfoMap u:um){
				UserGroup ug = this.userGroupRepository.getById(u.getUserGroupId());
				if(ug != null && ug.getAreaId() != null && ug.getAreaId().equals(userGroup.getAreaId()) && !ug.getId().equals(userGroup.getId())){
					return "该区域下编号为： "+userGroup.getProductId()+"的产品包已经关联了分组名为:"+ug.getName()+"的用户分组!";
				}
			}
		}
		return null;
    }
    @Override
    public String saveUserGroup(UserGroup userGroup) {
//    	if(userGroup.getType().equals(UserGroupType.PRODUCTPACKAGE)){
//    		PpInfo pp = this.ppInfoWebService.findPpInfoByProductId(userGroup.getProductId());
//    		if(pp == null){
//    			return "未能找到编号为" + userGroup.getProductId() + "的产品包，请确认!";
//    		}
//    	}
    	if(userGroup.getType().equals(UserGroupType.PRODUCTPACKAGE) && userGroup.getProductId() != null && !userGroup.getProductId().equals("")){
//    		List<UserGroupPpInfoMap> um = this.userGroupPpInfoMapRepository.findMapByUserGroupIdAndProductId(null, userGroup.getProductId());
//    		if(um != null && um.size() >0){
//    			for(UserGroupPpInfoMap u:um){
//    				UserGroup ug = this.userGroupRepository.getById(u.getUserGroupId());
//    				if(ug != null && ug.getAreaId() != null && ug.getAreaId().equals(userGroup.getAreaId())){
//    					return "该区域下编号为： "+userGroup.getProductId()+"的产品包类型分组已经存在!";
//    				}
//    			}
//    		}
	    	String jy =	this.checkPpVail(userGroup);
	    	if(jy != null){
	    		return jy;
	    	}
    	}
        boolean bool = this.userGroupRepository.save(userGroup);
    	 if(userGroup.getType().equals(UserGroupType.PRODUCTPACKAGE) && userGroup.getProductId() != null && !userGroup.getProductId().equals("")){
//    		String[] ppIds = userGroup.getProductId().split(",");
//    		for(String ppId:ppIds){
//    			UserGroupPpInfoMap map = new UserGroupPpInfoMap();
//             	map.setProductId(ppId);
//             	map.setUserGroupId(userGroup.getId());
//             	map.setCreateDate(new Date());
//             	map.setUpdateDate(new Date());
//             	bool = this.userGroupPpInfoMapRepository.save(map);
//    		}
    		
    		UserGroupPpInfoMap map = new UserGroupPpInfoMap();
          	map.setProductId(userGroup.getProductId());
          	map.setUserGroupId(userGroup.getId());
          	map.setCreateDate(new Date());
          	map.setUpdateDate(new Date());
          	bool = this.userGroupPpInfoMapRepository.save(map);
         }
        return bool == true ? Constants.SUCCESS : Constants.FAILED;
    }

    @Override
    public UserGroup getUserGroupById(Long id) {
        return this.userGroupRepository.getById(id);
    }

    @Override
    public String updateUserGroupById(UserGroup userGroup) {
//    	if(userGroup.getType().equals(UserGroupType.PRODUCTPACKAGE)){
//    		PpInfo pp = this.ppInfoWebService.findPpInfoByProductId(userGroup.getProductId());
//    		if(pp == null){
//    			return "未能找到编号为" + userGroup.getProductId() + "的产品包，请确认!";
//    		}
//    	}
    	if(userGroup.getType().equals(UserGroupType.PRODUCTPACKAGE) && userGroup.getProductId() != null && !userGroup.getProductId().equals("")){
//    		List<UserGroupPpInfoMap> um = this.userGroupPpInfoMapRepository.findMapByUserGroupIdAndProductId(null, userGroup.getProductId());
//    		if(um != null && um.size() >0){
//    			for(UserGroupPpInfoMap u:um){
//    				UserGroup ug = this.userGroupRepository.getById(u.getUserGroupId());
//    				if(ug != null && ug.getAreaId() != null && ug.getAreaId().equals(userGroup.getAreaId())){
//    					return "该区域下编号为： "+userGroup.getProductId()+"的产品包类型分组已经存在!";
//    				}
//    			}
//    		}
	    	String jy =	this.checkPpVail(userGroup);
	    	if(jy != null){
	    		return jy;
	    	}
    	}
        boolean bool = this.userGroupRepository.updateByPrimaryKey(userGroup);
    	if(userGroup.getType().equals(UserGroupType.PRODUCTPACKAGE) && userGroup.getProductId() != null && !userGroup.getProductId().equals("")){
//    		String[] ppIds = userGroup.getProductId().split(",");
//    		this.userGroupPpInfoMapRepository.deleteMapByUserGroupId(userGroup.getId());
//    		for(String ppId:ppIds){
//    			UserGroupPpInfoMap map = new UserGroupPpInfoMap();
//             	map.setProductId(ppId);
//             	map.setUserGroupId(userGroup.getId());
//             	map.setCreateDate(new Date());
//             	map.setUpdateDate(new Date());
//             	bool = this.userGroupPpInfoMapRepository.save(map);
//    		}
    		UserGroupPpInfoMap map = new UserGroupPpInfoMap();
         	map.setProductId(userGroup.getProductId());
         	map.setUserGroupId(userGroup.getId());
         	map.setUpdateDate(new Date());
         	bool = this.userGroupPpInfoMapRepository.update(map);
    		
         }
        return bool == true ? Constants.SUCCESS : Constants.FAILED;
    }

    @Override
    public boolean deleteUserGroupBusiness(List<Long> idsList) {
        for (Long id : idsList) {
            UserGroup userGroup = this.userGroupRepository.getById(id);
            if (userGroup.getType().equals(UserGroupType.NOTICE)) {
                this.sysNoticeRepository.deleteByUserGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.ANIMATION)) {
                this.bootAnimationRepository.deleteAnimationUserMapByUserGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.UPGRADE)) {
                this.upgradeTaskRepository.deleteUserUpgradeMapByUserGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.APPUPGRADE)) {
                this.appUpgradeTaskRepository.deleteUserUpgradeMapByUserGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.BACKGROUND)) {
                this.backgroundImageRepository.deleteUserBackGroundImageMapByGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.PANEL)) {
                this.panelPackageUserMapRepository.deletePanelUserMapByUserGroupId(id);
            }
            if(userGroup.getType().equals(UserGroupType.PRODUCTPACKAGE)){
                this.panelPackageUserMapRepository.deletePanelUserMapByUserGroupId(id);
            }
        }
        return true;
    }

    @Override
    public boolean deleteUserBusiness(List<Long> idsList) {
        for (Long id : idsList) {
            Customer customer = this.customerRepository.getCustomerById(id);
            // this.userGroupRepository.deleteMapByUserId(customer.getUserId());
            this.sysNoticeRepository.deleteByUserCode(customer.getCode());
            this.bootAnimationRepository.deleteAnimationUserMapByCode(customer.getCode());
//            this.upgradeTaskRepository.deleteUserUpgradeMapByUserId(customer.getUserId());
//            this.appUpgradeTaskRepository.deleteAppUpgradeMapByUserId(customer.getUserId());
            this.backgroundImageRepository.deleteBackGroundImageMapByUserCode(customer.getCode());
            this.panelPackageUserMapRepository.deletePanelUserMapByUserCode(customer.getCode());
        }
        return true;
    }

    @Override
    public boolean deleteUserGroupByIds(List<Long> ids) {
        for (Long id : ids) {
            UserGroup userGroup = this.userGroupRepository.getById(id);
            this.userGroupMapRepository.deleteMapByGroupId(id);
            if (userGroup.getType().equals(UserGroupType.NOTICE)) {
                this.sysNoticeRepository.deleteByUserGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.ANIMATION)) {
                this.bootAnimationRepository.deleteAnimationUserMapByUserGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.UPGRADE)) {
                this.upgradeTaskRepository.deleteUserUpgradeMapByUserGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.APPUPGRADE)) {
                this.appUpgradeTaskRepository.deleteUserUpgradeMapByUserGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.BACKGROUND)) {
                this.backgroundImageRepository.deleteUserBackGroundImageMapByGroupId(id);
            }
            if (userGroup.getType().equals(UserGroupType.PANEL)) {
                this.panelPackageUserMapRepository.deletePanelUserMapByUserGroupId(id);
            }
        }
        return this.userGroupRepository.delete(ids);
    }

    @Override
    public boolean deleteUserGroupMap(List<Long> ids) {
        for (Long id : ids) {
            this.userGroupMapRepository.deleteMapByGroupId(id);
        }
        return true;
    }

    @Override
    public boolean deleteUserGroupMapByUserId(List<Long> ids) {
        for (Long id : ids) {
            Customer customer = this.customerRepository.getCustomerById(id);
            this.userGroupMapRepository.deleteMapByUserCode(customer.getUserId());
        }
        return true;
    }

    @Override
    public String deleteUserGroupByCondition(List<Long> idsList) {
        for (Long id : idsList) {
            UserGroup userGroup = this.userGroupRepository.getById(id);
            List<UserGroupMap> userGroupMap = this.userGroupMapRepository.getMapByGroupId(id);
            if (userGroupMap != null && userGroupMap.size() != 0) {
                return "用户分组:" + userGroup.getName() + "绑定了用户，不可删除，请先解绑！";
            }
            // this.userGroupRepository.deleteMapByGroupId(id);
            if (userGroup.getType().equals(UserGroupType.NOTICE)) {
                // this.sysNoticeRepository.deleteByUserGroupId(id);
                List<UserNoticeMap> noticeMap = this.sysNoticeRepository.getByUserGroupId(id);
                if (noticeMap != null && noticeMap.size() != 0) {
                    return "用户分组:" + userGroup.getName() + "绑定了通知，不可删除，请先解绑！";
                }
            }
            if (userGroup.getType().equals(UserGroupType.ANIMATION)) {
                // this.bootAnimationRepository.deleteAnimationUserMapByUserGroupId(id);
                AnimationUserMap animationMap = this.bootAnimationRepository.findMapByUserGroupId(id, "GROUP");
                if (animationMap != null) {
                    return "用户分组:" + userGroup.getName() + "绑定了开机动画，不可删除，请先解绑！";
                }
            }
            if (userGroup.getType().equals(UserGroupType.UPGRADE)) {
                // this.upgradeTaskRepository.deleteUserUpgradeMapByUserGroupId(id);
                UserUpgradeMap upgradeMap = this.upgradeTaskRepository.getUserUpgradeMapByUserGroupId(id);
                if (upgradeMap != null) {
                    return "用户分组:" + userGroup.getName() + "绑定了设备升级任务，不可删除，请先解绑！";
                }
            }
            if (userGroup.getType().equals(UserGroupType.APPUPGRADE)) {
                // this.appUpgradeTaskRepository.deleteUserUpgradeMapByUserGroupId(id);
                List<UserAppUpgradeMap> appUpgtade = this.appUpgradeTaskRepository.getUserUpgradeMapByUserGroupId(id);
                if (appUpgtade != null) {
                    return "用户分组:" + userGroup.getName() + "绑定了应用升级任务，不可删除，请先解绑！";
                }
            }
            if (userGroup.getType().equals(UserGroupType.BACKGROUND)) {
                // this.backgroundImageRepository.deleteUserBackGroundImageMapByGroupId(id);
                List<BackgroundImageUserMap> backgroundMap = this.backgroundImageRepository.findMapByUserGroupId(id);
                if (backgroundMap != null && backgroundMap.size() != 0) {
                    return "用户分组:" + userGroup.getName() + "绑定了背景轮换，不可删除，请先解绑！";
                }
            }
            if (userGroup.getType().equals(UserGroupType.PANEL)) {
                // this.panelDeviceRepository.deletePanelUserMapByUserGroupId(id);
                PanelPackageUserMap panelMap = this.panelPackageUserMapRepository.getMapByUserGroupId(id);
                if (panelMap != null) {
                    return "用户分组:" + userGroup.getName() + "绑定了PANEL面板组，不可删除，请先解绑！";
                }
            }

            //删除用户组与产品包的关系
            List<UserGroupPpInfoMap> ppMap = this.userGroupPpInfoMapRepository.findMapByUserGroupIdAndProductId(id, null);
            if (ppMap != null && ppMap.size() > 0) {
                this.userGroupPpInfoMapRepository.deleteMapByUserGroupId(id);
            }
            this.userGroupRepository.deleteByPrimaryKey(id);
        }
        // this.userGroupRepository.delete(idsList);
        return null;
    }

    @Override
    public List<UserGroup> findUserGroupListByType(UserGroupType type, String dynamicFlag) {
        List<UserGroup> userGroups = new ArrayList<UserGroup>();
    	if(type.equals(UserGroupType.PANEL)){
    		userGroups = this.userGroupRepository.findUserGroupListByType(UserGroupType.PRODUCTPACKAGE, dynamicFlag);
    	}
        List<UserGroup> userMap = this.userGroupRepository.findUserGroupListByType(type, dynamicFlag);
        userGroups.addAll(userMap);
        return userGroups;
    }

    @Override
    public List<UserGroup> getUserGroupList() {
        return this.userGroupRepository.getList();
    }

    @Override
    public int saveUserGroupMap(UserGroupMap userGroupMap) {
        return this.userGroupMapRepository.saveUserGroupMap(userGroupMap);
    }

    @Override
    public String bindUserGroup(List<Long> idsList, String userGroupId) {
        UserGroupMap userGroupMap = new UserGroupMap();
        String message = Constants.FAILED;
        String users = "";
        int s = 0;

        for (Long userId : idsList) {
            int i = 0;
            Customer customer = this.customerRepository.getCustomerById(userId);
            // this.userGroupMapRepository.deleteMapByUserId(customer.getUserId());
            if (!userGroupId.equals("")) {
                userGroupMap.setCode(customer.getUserId());
                userGroupMap.setUserGroupId(Long.parseLong(userGroupId));
                userGroupMap.setCreateDate(new Date());
                i = this.saveUserGroupMap(userGroupMap);
                if (i == 0) {
                    users = users + customer.getUserId() + ";";
                }
                s = s + i;
            }
        }

        if (s == idsList.size()) {
            message = Constants.SUCCESS;
        } else {
            message = users;
        }
        return message;
    }

    @Override
    public boolean syncCustomer() throws Exception {
        int syncCustomerNum = 0;
        boolean isAllSuccess = true;
        String num = this.systemConfigService.getSystemConfigByConfigKey("syncCustomerNum");
        if (!StringUtils.isBlank(num)) {
            syncCustomerNum = Integer.parseInt(num);
        } else {
            throw new Exception("配置信息有误，请确认配置文件中syncCustomerNum是否配置正确");
        }
        String num_space = this.systemConfigService.getSystemConfigByConfigKey("getSpaceNum");
        if (StringUtils.isBlank(num_space)) {
            throw new Exception("配置信息有误，请确认配置文件中getSpaceNum是否配置正确");
        }
        int counts = this.customerRepository.getCustomerCountByIsSync();// 获取总共要同步量
        // 根据配置参数每次抓取数据同步
        int count = 0;
        if (counts % Integer.parseInt(num_space) == 0) {
            count = counts / Integer.parseInt(num_space);
        } else {
            count = counts / Integer.parseInt(num_space) + 1;
        }
        for (int j = 0; j < count; j++) {
            List<Customer> customers = this.customerRepository.getAllCustomerByIsSync(Integer.parseInt(num_space));
            if (customers == null || customers.isEmpty()) {
                return true;
            }
            int index = 0;
            List<Customer> _customers = null;
            Area area = customers.get(0).getArea();
            InterfaceUrl interfaceUrl = this.interfaceUrlRepository.getByAreaAndName(area.getId(),
                    InterfaceName.SYNCUSER);
            if (interfaceUrl == null) {
                throw new Exception("请先维护省份接口Url表!");
            }
            String url = interfaceUrl.getInterfaceUrl();
            for (int i = 0; i < customers.size(); i++) {
                if (index < syncCustomerNum) {
                    if (_customers == null) {
                        _customers = new ArrayList<Customer>();
                    }
                    _customers.add(customers.get(i));
                    index++;
                }
                // 如果达到发送数量或者已经是最后一个了，就同步
                if (index == syncCustomerNum || i == customers.size() - 1) {
                    boolean isSyncSuccess = this.syncUsersToCenter(_customers, url);
                    // 只要有一个发送批次同步失败，那么就返回失败
                    if (!isSyncSuccess) {
                        isAllSuccess = false;
                        // for(Customer _customer : _customers){
                        // this.customerRepository.updateSyncById(_customer.getId(),
                        // SyncType.FAILED.toString());
                        // }
                        // 成功，更新状态
                    } else {
                        for (Customer _customer : _customers) {
                            this.customerRepository.updateSyncById(_customer.getId(), SyncType.SYNCED.toString());
                        }
                    }
                    _customers = null;
                    index = 0;
                }
            }
        }
        return isAllSuccess;
    }

    private boolean syncUsersToCenter(List<Customer> customerList, String url) {
        String param = JsonUtil.getJsonString4List(customerList);
        LOGGER.info("Sync Customer To Center Url: " + url);
        LOGGER.info("Sync Customer To Center Param: " + param);
        HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
        int returnCode = wrapper.getHttpStatus();
        if (returnCode == 200) {
            String rsp = wrapper.getResponse();
            JSONObject object = JSONObject.fromObject(rsp);
            String result = object.getString("result");
            if (StringUtils.equalsIgnoreCase(result, "true")) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int getCustomerCountByIsSync() {
        return this.customerRepository.getCustomerCountByIsSync();
    }

    @Override
    public String checkInputSql(String sql) {
        List<Customer> customerList = this.userGroupRepository.checkInputSql(sql);
        for (Customer customer : customerList) {
            if (customer == null) {
                return "请输入正确的查询语句!";
            }
            Customer customerExisted = this.customerRepository.getCustomerByUserId(customer.getUserId());
            if (customerExisted == null) {
                return "请输入正确的表名";
            }
        }
        return Constants.SUCCESS;
    }

    @Override
    public List<UserGroupMap> findAllUserGroupMapsByUserGroupId(String userGroupId) {
        if (StringUtils.isNotBlank(userGroupId)) {
            Long id = Long.parseLong(userGroupId);
            return this.userGroupMapRepository.findAllUserGroupMapsByUserGroupId(id);
        }
        return null;
    }


    @Override
    public Map<String, Object> findUserGroupsByUserCode(String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(code)) {
        	Customer user = this.customerRepository.getCustomerByCode(code);
        	Long areaId = (user.getArea() == null ? null : user.getArea().getId());
            List<UserGroup> userGroups = this.userGroupRepository.findUserGroupsByUserCode(code,areaId);
            if (userGroups != null && userGroups.size() > 0) {
                StringBuilder animationGroupIds = new StringBuilder("");
                StringBuilder noticeGroupIds = new StringBuilder("");
                StringBuilder panelGroupIds = new StringBuilder("");
                StringBuilder upgradeGroupIds = new StringBuilder("");
                StringBuilder backGroupIds = new StringBuilder("");
                StringBuilder appUpgradeGroupIds = new StringBuilder("");
                for (UserGroup userGroup : userGroups) {
                    if (UserGroupType.ANIMATION.equals(userGroup.getType())) {
                        if (StringUtils.isBlank(animationGroupIds.toString())) {
                            animationGroupIds.append(userGroup.getId());
                        } else {
                            animationGroupIds.append(",").append(userGroup.getId());
                        }
                    } else if (UserGroupType.NOTICE.equals(userGroup.getType())) {
                        if (StringUtils.isBlank(noticeGroupIds.toString())) {
                            noticeGroupIds.append(userGroup.getId());
                        } else {
                            noticeGroupIds.append(",").append(userGroup.getId());
                        }
                        // noticeGroupIds.append(userGroup.getId()).append(",");
                    } else if (UserGroupType.PANEL.equals(userGroup.getType())) {
                        if (StringUtils.isBlank(panelGroupIds.toString())) {
                            panelGroupIds.append(userGroup.getId());
                        } else {
                            panelGroupIds.append(",").append(userGroup.getId());
                        }
                        // panelGroupIds.append(userGroup.getId()).append(",");
                    } else if (UserGroupType.UPGRADE.equals(userGroup.getType())) {
                        if (StringUtils.isBlank(upgradeGroupIds.toString())) {
                            upgradeGroupIds.append(userGroup.getId());
                        } else {
                            upgradeGroupIds.append(",").append(userGroup.getId());
                        }
                        // upgradeGroupIds.append(userGroup.getId()).append(",");
                    } else if (UserGroupType.BACKGROUND.equals(userGroup.getType())) {
                        if (StringUtils.isBlank(backGroupIds.toString())) {
                            backGroupIds.append(userGroup.getId());
                        } else {
                            backGroupIds.append(",").append(userGroup.getId());
                        }
                        // backGroupIds.append(userGroup.getId()).append(",");
                    } else {
                        if (StringUtils.isBlank(appUpgradeGroupIds.toString())) {
                            appUpgradeGroupIds.append(userGroup.getId());
                        } else {
                            appUpgradeGroupIds.append(",").append(userGroup.getId());
                        }
                        // appUpgradeGroupIds.append(userGroup.getId()).append(",");
                    }
                }
                map.put("ANIMATION", animationGroupIds);
                map.put("NOTICE", noticeGroupIds);
                map.put("BACKGROUND", backGroupIds);
                map.put("APPUPGRADE", appUpgradeGroupIds);
                map.put("UPGRADE", upgradeGroupIds);
                map.put("PANEL", panelGroupIds);
                LOGGER.debug("{\"" + UserGroupType.ANIMATION + ":\"" + animationGroupIds.toString() + "\",\""
                        + UserGroupType.NOTICE + ":\"" + noticeGroupIds.toString() + "\" }");
                // return "{\"" + UserGroupType.ANIMATION + ":\"" +
                // animationGroupIds.toString() + "\",\""+ UserGroupType.NOTICE
                // +":\"" + noticeGroupIds.toString() + "\" }";
                return map;
            }
        }
        return null;
    }

    @Override
    public List<Long> findUserGroupById(String id, String character,
                                           String tableName) {
        if (StringUtils.isNotBlank(id)) {
            return this.userGroupRepository.findUserGroupById(
                    Long.parseLong(id), character, tableName);
        }
        return null;
    }

    @Override
    public List<Long> findAreaByBusiness(String id, String character,
                                         String tableName) {
        if (StringUtils.isNotBlank(id)) {
            return this.customerRepository.findAreaByBusiness(Long.parseLong(id), character,
                    tableName);
        }
        return null;
    }

    @Override
    public List<UserGroup> findUserGroupByArea(EnumConstantsInterface.UserGroupType type, String tableName, String character, String districtCode, String id) {
    	List<UserGroup> userGroups = new ArrayList<UserGroup>();
    	List<String> districtCodes = new ArrayList<String>();
        if (districtCode.indexOf(",") > 0) {
            districtCodes = StringUtil.split(districtCode);
        } else {
            districtCodes.add(districtCode);
        }
        if(type.equals(UserGroupType.PANEL)){
    		userGroups = this.userGroupRepository.findUserGroupByArea(UserGroupType.PRODUCTPACKAGE,tableName, character, districtCodes, Long.parseLong(id));
    	}
        List<UserGroup> _userGroups = this.userGroupRepository.findUserGroupByArea(type,
                tableName, character, districtCodes, Long.parseLong(id));
        userGroups.addAll(_userGroups);
        return userGroups;
    }

    @Override
    public String bindUserGroupMap(String id, String userIds) {
        String[] _userIds = new String[0];
        StringBuilder jy = new StringBuilder();
        StringBuilder custmers = new StringBuilder();
        String[] ids;
        if (id.indexOf(",") > 0) {
            ids = id.split(",");
        } else {
            ids = new String[] { id };
        }
        List<String> userList = new ArrayList<String>();
        if (StringUtils.isNotBlank(userIds)) {
            _userIds = userIds.split(",");
            for (int i = 0; i < _userIds.length; i++) {
                if (StringUtils.isEmpty(_userIds[i])) {
                    continue;
                }
                _userIds[i] = FilterSpaceUtils.replaceBlank(_userIds[i]);
                userList.add(_userIds[i]);
            }
        }
        double start = System.currentTimeMillis();
        List<Customer> customerList = this.customerRepository.findCustomersByUserCodes(_userIds);
        if (customerList.size() == 0) {
            jy.append("用户不合法：未能找到你所上传的所有用户编号的用户，请确认!");
            String space = "";
            return "{\"info\":\"" + jy.toString() + "\",\"userIds\":\"" + space + "\"}";
        }
        String[] userId = new String[customerList.size()];
        if (customerList.size() > 0 && customerList.size() != userList.size()) {
            for (int i = 0; i <userList.size(); i++) {
                boolean bool = false;
                for (Customer customer : customerList) {
                    if (customer.getCode().equals(userList.get(i))) {
                        bool = true;
                        break;
                    }
                }
                if (bool == false) {
                    if (jy.length() <= 0) {
						jy.append("未能找到用户编号为："+ userList.get(i));
					}else{
						jy.append(","+ userList.get(i));
					}
                }
            }
            if (jy.length() > 0){
				jy.append("的用户，请确认!  ");
			}
        }
        double end = System.currentTimeMillis();
        LOGGER.info("检验用户的存在：" + (end - start));
        double start1 = System.currentTimeMillis();
        for (String _id : ids) {
            UserGroup group = this.userGroupRepository.getById(Long.parseLong(_id));
            UserGroupType type = group.getType();
            String typeName = group.getType().getDisplayName();
            int n = 0;
            StringBuilder qy = new StringBuilder();
            for (Customer customer : customerList) {
                if (customer.getArea() != null&& !customer.getArea().getId().equals(group.getAreaId())) {
                    if(qy.length() <= 0){
						qy.append("用户编号：" + customer.getCode());
					}else{
						qy.append("," + customer.getCode());
					}
                } else {
                    userId[n] = customer.getCode();
                    n++;
                }
            }
            if(qy.length() > 0){
				jy.append(qy.toString()+" 的所属区域 与要绑定分组的所属区域不符!  ");
			}
            double end1 = System.currentTimeMillis();
            LOGGER.info("检验用户与用户分组区域：" + (end1 - start1));

            if (userId != null) {
                List<String> ysten = new ArrayList<String>();
                List<String> code = new ArrayList<String>();
                for (String yst : userId) {
                    if(yst!=null){
                        ysten.add(yst);
                    }
                }
                double start2 = System.currentTimeMillis();
                if (ysten.size() > 0) {
                    List<UserGroup> userGroupList = this.userGroupRepository.findUserGroupsByUserCodesAndType(userId,type.toString());
                    if (userGroupList != null && userGroupList.size() > 0) {
                    	StringBuilder lx = new StringBuilder();
                        for (UserGroup userGroup : userGroupList) {
                            if (userGroup.getId() != group.getId()) {
                            	if (userGroup.getId() != group.getId()) {
                                    if(lx.length() <= 0){
        								lx.append("用户编号：" + userGroup.getUserId());
        							}else{
        								lx.append("," +  userGroup.getUserId());
        							}
                                    code.add(userGroup.getUserId());
                                }
                            }
                            ysten.remove(userGroup.getUserId());
                        }
                        if(lx.length() > 0){
    						jy.append(lx.toString()+ " 绑定过类型为" + typeName + "的用户分组!  ");
    					}
                    }
                }
                double end2 = System.currentTimeMillis();
                LOGGER.info("检验用户绑的分组类型是否重复：" + (end2 - start2));
                if (code.size() > 0) {
					for (String u : code) {
						custmers.append(u).append(",");
					}
				}
                if (ysten.size() > 0) {
                    List<UserGroupMap> maps = new ArrayList<UserGroupMap>();
                    int index = 0;
                    for (String y : ysten) {
                        UserGroupMap groupMap = new UserGroupMap();
                        groupMap.setCode(y);
                        groupMap.setUserGroupId(Long.parseLong(_id));
                        groupMap.setCreateDate(new Date());
                        maps.add(groupMap);
                        index++;
                        if (index % Constants.BULK_NUM == 0) {
                            this.userGroupMapRepository.bulkSaveUserGroupMap(maps);
                            maps.clear();
                        }
                    }
                    if (maps.size() > 0) {
                        this.userGroupMapRepository.bulkSaveUserGroupMap(maps);
                        maps.clear();
                    }
                }

                double end3 = System.currentTimeMillis();
                LOGGER.info("保存绑定：" + (end3 - end2));
            }

        }
        String users = custmers.length()>0 ? custmers.substring(0, custmers.length() - 1).toString() : "";
        if (StringUtils.isNotBlank(jy.toString())) {
            System.out.println(jy.toString());
        }
        String info = jy.length()>0 ? jy.toString() : "关联的用户合法,且关联成功!";
		return  "{\"info\":\"" + info + "\",\"userIds\":\"" + users + "\"}";
//        StringBuilder sb = new StringBuilder();
//        sb.append(Constants.SUCCESS);
//        return sb.toString();
    }
    @Override
	public String addUserGroupMap(String id, String userIds) {
		UserGroup group = this.userGroupRepository.getById(Long.parseLong(id));
		String[] usrIds = new String[0];
		List<Long> groupIds = new ArrayList<Long>();
		if(StringUtils.isNotBlank(userIds)){
			usrIds = userIds.split(",");
			for (int i = 0; i < usrIds.length; i++) {
				if (StringUtils.isEmpty(usrIds[i])) {
					break;
				}
				usrIds[i] = FilterSpaceUtils.replaceBlank(usrIds[i]);
			}
		}
		if(group != null && group.getType() != null){
			List<UserGroup> userGroupList = this.userGroupRepository.findUserGroupsByUserCodesAndType(usrIds,group.getType().toString());
			for(UserGroup g:userGroupList){
				if(!g.getId().equals(id)){
					groupIds.add(g.getId());
				}
			}
			if(groupIds != null && groupIds.size()>0){
				this.userGroupMapRepository.deleteMapByUserCodesAndGroups(usrIds, groupIds);
			}
		}
		if (usrIds.length > 0) {
			List<UserGroupMap> maps = new ArrayList<UserGroupMap>();
			int index = 0;
			for (String y : usrIds) {
				UserGroupMap groupMap = new UserGroupMap();
                groupMap.setCode(y);
                groupMap.setUserGroupId(Long.parseLong(id));
                groupMap.setCreateDate(new Date());
                maps.add(groupMap);
                index++;
                if (index % Constants.BULK_NUM == 0) {
                    this.userGroupMapRepository.bulkSaveUserGroupMap(maps);
                    maps.clear();
                }
			}
			if (maps.size() > 0) {
				 this.userGroupMapRepository.bulkSaveUserGroupMap(maps);
	                maps.clear();
			}
		}
		return null;
	}
    @Override
    public List<Customer> findAllCustomerByIsSync(Integer num) {
        return this.customerRepository.getAllCustomerByIsSync(num);
    }

    @Override
    public UserGroup findUserGroupByNameAndType(String name, String type) {
        return this.userGroupRepository.findUserGroupByNameAndType(name, type);
    }

    @Override
    public Pageable<CustomerRelationDeviceVo> findCustomerRelationByDeviceSno(
            String snos, int pageNo, int pageSize) {
        String sno = "";
        if (StringUtils.isNotBlank(snos)) {
            String[] _snos = snos.split(",");
            for (int i = 0; i < _snos.length; i++) {
                if (i == _snos.length - 1) {
                    sno += "'" + FilterSpaceUtils.replaceBlank(_snos[i]) + "'";
                } else {
                    sno += "'" + FilterSpaceUtils.replaceBlank(_snos[i]) + "',";
                }
            }
        }
        return this.customerRepository.findCustomerRelationByDeviceSno(sno, pageNo, pageSize);
    }

	@Override
	public Pageable<Customer> findCustomersOfGroupByCondition(String tableName,String character,
			Customer customer, Integer pageNo, Integer pageSize) {
		return this.customerRepository.findCustomersOfGroupByCondition(tableName,character,customer, pageNo, pageSize);
	}

	@Override
	public Pageable<ProductPackageInfo> findPpList()throws Exception {
		String url = this.systemConfigService.getSystemConfigByConfigKey(Constants.SELECT_PP_INFO);
		if(StringUtils.isBlank(url)){
			throw new Exception("请配置产品包查询接口Url!");
		}
		String repStr = HttpUtils.get(url);
//		String repStr = "[{\"productId\":\"20140410001\",\"productName\":\"单片包\",\"productType\":\"SINGLE\"}]";
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("select pp infos:" + repStr);
        }
        if(StringUtils.isNotBlank(repStr)){
        	@SuppressWarnings("unchecked")
    		List<ProductPackageInfo> list = JsonUtil.getList4Json(repStr, ProductPackageInfo.class, null);
//            String message = "";
//            for (int i = 0; i < list.size(); i++) {
//            	ProductPackageInfo pp = list.get(i);
//                message = EntityUtils.checkNull(pp);
//                if (message != null) {
//                    break;
//                }
//            }
//            if (message != null) {
//            	throw new Exception(message);
//            }
            return new Pageable<ProductPackageInfo>().instanceByPageNo(list, list.size(), 0, list.size());
        }
        
		return null;
	}

	@Override
	public List<Customer> findExportCustomers(Map<String, Object> map) {
		return this.customerRepository.findExportCustomers(map);
	}

	@Override
	public List<CustomerRelationDeviceVo> findRelationCustomerByDeviceIds(
			List<Long> deviceIds) {
		return this.customerRepository.findRelationCustomerByDeviceIds(deviceIds);
	}

	@Override
	public List<CustomerRelationDeviceVo> exportRelationListByConditions(
			Map<String, Object> map) {
		return this.customerRepository.exportRelationListByConditions(map);
	}

    @Override
    public boolean deleteUserByGroupId(Long userGroupId,List<String> idsList){
        if(idsList.size()==0){
            this.userGroupMapRepository.deleteMapByGroupId(userGroupId);
        }else{
            this.userGroupMapRepository.deleteMapByUserCodesAndGroups((String[])idsList.toArray(),StringUtil.splitToLong(String.valueOf(userGroupId)));
        }
        return true;
    }

    @Override
    public Map<String, Object> findCustomerRelateBusinessByCodeOrGroupId(String code, Long groupId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if ( (StringUtils.isNotBlank(code) && null==groupId)||(null==code && null!=groupId) ) {
            StringBuilder backgroundImageId = new StringBuilder("");
            StringBuilder noticeId = new StringBuilder("");
            String bootAnimationId = "";
            String panelPackageId = "";

            List<SysNotice> sysNoticeList = this.sysNoticeRepository.findSysNoticeByCustomerCodeOrGroupId(code, groupId);
            if(!CollectionUtils.isEmpty(sysNoticeList)){
                for(SysNotice sysNotice : sysNoticeList){
                    noticeId.append(sysNotice.getId().toString());
                    noticeId.append(",");
                }
                noticeId = noticeId.deleteCharAt(noticeId.length()-1);
                map.put(EnumConstantsInterface.UserGroupType.NOTICE.toString(), noticeId);
            }

            List<BackgroundImage> backgroundImageList = this.backgroundImageRepository.findBackgroundImageByCustomerCodeOrGroupId(code, groupId);
            if( !CollectionUtils.isEmpty(backgroundImageList) ){
                for(BackgroundImage backgroundImage : backgroundImageList){
                    backgroundImageId.append(backgroundImage.getId().toString());
                    backgroundImageId.append(",");
                }
                backgroundImageId = backgroundImageId.deleteCharAt(backgroundImageId.length()-1);
                map.put(EnumConstantsInterface.UserGroupType.BACKGROUND.toString(), backgroundImageId);
            }

            BootAnimation bootAnimation = this.bootAnimationRepository.getBootAnimationByCustomerCodeOrGroupId(code, groupId);
            if( null != bootAnimation ){
                bootAnimationId = bootAnimation.getId().toString();
                map.put(EnumConstantsInterface.UserGroupType.ANIMATION.toString(), bootAnimationId);
            }

            PanelPackage panelPackage = this.panelPackageRepository.getPackageByCustomerCodeOrGroupId(code, groupId);
            if( null != panelPackage ){
                panelPackageId = panelPackage.getId().toString();
                map.put(EnumConstantsInterface.UserGroupType.PANEL.toString(), panelPackageId);
            }

            return map;
        }
        return null;
    }

    @Override
    public String saveCustomerBusinessBind(String ids, String animationId, String panelId, String noticeIds, String backgroundIds) {
        String message = "";
        List<Long> idsList = StringUtil.splitToLong(ids);
        if(!CollectionUtils.isEmpty(idsList)){
            this.deleteUserBusiness(idsList);
        }

        Customer customer = this.customerRepository.getCustomerById(idsList.get(0));
        if(null != customer){
            String customerCode = customer.getCode();

            if(StringUtils.isNotBlank(noticeIds)){
                List<Long> noticeIdList = StringUtil.splitToLong(noticeIds);
                for (Long noticeId : noticeIdList) {
                    this.saveNoticeUserMap(noticeId, "USER", null, customerCode);
                }
            }
            if(StringUtils.isNotBlank(backgroundIds)){
                List<Long> backgroundList = StringUtil.splitToLong(backgroundIds);
                for (Long backgroundId : backgroundList) {
                    this.saveBackGroundUserMap(backgroundId, "USER", null, customerCode);
                }
            }
            if(StringUtils.isNotBlank(animationId)){
                this.saveAnimationUserMap(Long.parseLong(animationId), "USER", null, customerCode);
            }
            if(StringUtils.isNotBlank(panelId)){
                this.savePanelPackageUserMap(Long.parseLong(panelId), "USER", null, customerCode);
            }
        }

        message = "用户绑定业务成功!";
        return message;
    }

    @Override
    public String saveGroupBusinessBind(String ids, String animationId, String panelId, String noticeIds, String backgroundIds) {
        String message = "";
        List<Long> idsList = StringUtil.splitToLong(ids);
        if(!CollectionUtils.isEmpty(idsList)){
            this.deleteUserGroupBusiness(idsList);
        }

        UserGroup userGroup = this.userGroupRepository.getById(idsList.get(0));
        if(null != userGroup){
            Long groupId = userGroup.getId();
            int type = userGroup.getType().ordinal();

            if(type == UserGroupType.PRODUCTPACKAGE.ordinal()){
                if(StringUtils.isNotBlank(panelId)){
                    this.savePanelPackageUserMap(Long.parseLong(panelId), "GROUP", groupId, null);
                }
            }
            if(type == UserGroupType.NOTICE.ordinal()){
                if(StringUtils.isNotBlank(noticeIds)){
                    List<Long> noticeIdList = StringUtil.splitToLong(noticeIds);
                    for (Long noticeId : noticeIdList) {
                        this.saveNoticeUserMap(noticeId, "GROUP", groupId, null);
                    }
                }
            }
            if(type == UserGroupType.BACKGROUND.ordinal()){
                if(StringUtils.isNotBlank(backgroundIds)){
                    List<Long> backgroundList = StringUtil.splitToLong(backgroundIds);
                    for (Long backgroundId : backgroundList) {
                        this.saveBackGroundUserMap(backgroundId, "GROUP", groupId, null);
                    }
                }
            }
            if(type == UserGroupType.ANIMATION.ordinal()){
                if(StringUtils.isNotBlank(animationId)){
                    this.saveAnimationUserMap(Long.parseLong(animationId), "GROUP", groupId, null);
                }
            }
            if(type == UserGroupType.PANEL.ordinal()){
                if(StringUtils.isNotBlank(panelId)){
                    this.savePanelPackageUserMap(Long.parseLong(panelId), "GROUP", groupId, null);
                }
            }
        }

        message = "分组绑定业务成功!";
        return message;
    }

    private boolean saveBackGroundUserMap(Long backgroundImageId, String type, Long userGroupId, String customerCode){
        BackgroundImageUserMap backgroundImageUserMap = new BackgroundImageUserMap();
        backgroundImageUserMap.setBackgroundImageId(backgroundImageId);
        backgroundImageUserMap.setType(type);
        backgroundImageUserMap.setCode(customerCode);
        backgroundImageUserMap.setUserGroupId(userGroupId);
        backgroundImageUserMap.setCreateDate(new Date());
        return this.backgroundImageRepository.saveBackgroundImageUserMap(backgroundImageUserMap);
    }

    private boolean savePanelPackageUserMap(Long panelPackageId, String type, Long userGroupId, String customerCode){
        PanelPackageUserMap panelPackageUserMap = new PanelPackageUserMap();
        panelPackageUserMap.setPanelPackageId(panelPackageId);
        panelPackageUserMap.setUserGroupId(userGroupId);
        panelPackageUserMap.setCreateDate(new Date());
        panelPackageUserMap.setCode(customerCode);
        panelPackageUserMap.setType(type);
        return this.panelPackageUserMapRepository.saveUserMap(panelPackageUserMap);
    }

    private boolean saveNoticeUserMap(Long sysNoticeId, String type, Long userGroupId, String customerCode) {
        UserNoticeMap userNoticeMap = new UserNoticeMap();
        userNoticeMap.setCreateDate(new Date());
        userNoticeMap.setType(type);
        userNoticeMap.setUserGroupId(userGroupId);
        userNoticeMap.setCode(customerCode);
        userNoticeMap.setNoticeId(sysNoticeId);
        return sysNoticeRepository.saveUserNoticeMap(userNoticeMap);
    }

    private boolean saveAnimationUserMap(Long bootAnimationId, String type, Long userGroupId, String customerCode) {
        AnimationUserMap animationUserMap = new AnimationUserMap();
        animationUserMap.setCreateDate(new Date());
        animationUserMap.setType(type);
        animationUserMap.setUserGroupId(userGroupId);
        animationUserMap.setBootAnimationId(bootAnimationId);
        animationUserMap.setCode(customerCode);
        return this.bootAnimationRepository.saveAnimationUserMap(animationUserMap);
    }

    @Override
	public Pageable<CustomerDeviceHistory> findCustomerDeviceHistoryByConditions(
			Map<String, Object> map) {
		return this.customerRepository.findCustomerDeviceHistoryByConditions(map);
	}

	@Override
	public List<CustomerDeviceHistory> findCustomerDeviceHistoryByIds(
			List<Long> ids) {
		return this.customerRepository.findCustomerDeviceHistoryByIds(ids);
	}

	@Override
	public List<CustomerDeviceHistory> findExportHistoryByConditions(
			Map<String, Object> map) {
		return this.customerRepository.findExportHistoryByConditions(map);
	}
}
