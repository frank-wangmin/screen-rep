package com.ysten.local.bss.device.repository.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.icu.text.SimpleDateFormat;
import com.ysten.local.bss.util.bean.Constant;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.area.domain.Area;
import com.ysten.area.repository.mapper.AreaMapper;
import com.ysten.area.service.IAreaService;
import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.CustomerDeviceHistoryVo;
import com.ysten.local.bss.device.domain.CustomerRelationDeviceVo;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.DeviceType;
import com.ysten.local.bss.device.domain.DeviceVendor;
import com.ysten.local.bss.device.domain.UserActiveStatistics;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.mapper.CityMapper;
import com.ysten.local.bss.device.repository.mapper.CustomerDeviceHistoryMapper;
import com.ysten.local.bss.device.repository.mapper.CustomerMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceCustomerAccountMapMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceTypeMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceVendorMapper;
import com.ysten.local.bss.device.repository.mapper.UserGroupMapMapper;
import com.ysten.local.bss.device.repository.mapper.UserGroupMapper;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.utils.date.DateUtil;
import com.ysten.utils.page.Pageable;

/**
 * ICustomerRepository默认实现
 *
 * @author LI.T
 * @date 2011-5-9
 * @fileName CustomerRepositoryImpl.java
 */
@Repository
public class CustomerRepositoryImpl implements ICustomerRepository {

    private static final String BASE_DOMAIN = "ysten:local:bss:device:";
    private static final String CUSTOMER_CODE = BASE_DOMAIN + "customer:code:";
    private static final String CUSTOMER_ID = BASE_DOMAIN + "customer:id:";
    private static final String CUSTOMER_USERID = BASE_DOMAIN + "customer:user_id:";

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private DeviceCustomerAccountMapMapper deviceCustomerAccountMapMapper;
    @Autowired
    private CustomerDeviceHistoryMapper customerDeviceHistoryMapper;
    @Autowired
    private IAreaService areaService;
    @Autowired
    private ICityRepository cityRepository;
    @Autowired
    private DeviceTypeMapper deviceTypeMapper;
    @Autowired
    private UserGroupMapMapper userGroupMapMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private DeviceVendorMapper deviceVendorMapper;
    @Override
    @MethodCache(key = CUSTOMER_CODE + "#{code}")
    public Customer getCustomerByCode(@KeyParam("code") String customerCode) {
        return customerMapper.getByCode(customerCode);
    }

    @Override
    @MethodCache(key = CUSTOMER_USERID + "#{user_id}")
    public Customer getCustomerByUserId(@KeyParam("user_id") String userId) {
        return customerMapper.getByUserId(userId);

    }

    @Override
    public List<Customer> getCustomerByUserIdAndOuterCode(String userId, String OuterCode) {
        return customerMapper.getCustomerByUserIdAndOuterCode(userId, OuterCode);
    }

    @Override
    public List<Customer> getCustomerByUserIdAndAreaId(String userId, Long areaId) {
        return customerMapper.getCustomerByUserIdAndAreaId(userId, areaId);
    }

    @Override
    public void delCustomerByUserIdAndOuterCode(String userId, String OuterCode, Long id) {
        this.customerMapper.delCustomerByUserIdAndOuterCode(userId, OuterCode, id);
    }

    @Override
    @MethodFlush(keys = {
            CUSTOMER_CODE + "#{customer.code}",
            CUSTOMER_ID + "#{customer.id}"
    })
    public boolean updateCustomer(@KeyParam("customer") Customer customer) {
        return (1 == this.customerMapper.update(customer));
    }

    @Override
    public boolean updateCustomerAsProcessed(List<String> userIds) {
        return this.customerMapper.updateCustomerAsProcessed(userIds) > 0;
    }

    @Override
    public boolean saveCustomer(Customer customer) {
        int i = this.customerMapper.save(customer);
        return (i == 1);
    }

    @Override
    @MethodCache(key = CUSTOMER_ID + "#{id}")
    public Customer getCustomerById(@KeyParam("id") Long id) {
        return customerMapper.getById(id);
    }

    @Override
    public Customer getCustomerByLoginName(String loginName) {
        return customerMapper.getByLoginName(loginName);
    }

    @Override
    public Customer getCustomerByPhone(String phone) {
        return customerMapper.getByPhone(phone);
    }

    @Override
    public Customer getMinCustomerByCustId(String custId) {
        return customerMapper.getMinCustomerByCustId(custId);
    }

    @Override
    public List<Customer> getNotProcessedCustomers() {
        return customerMapper.getNotProcessedCustomers();
    }

    @Override
    public Customer getCustomerByCustIdAndDeviceCode(String custId, String deviceCode) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("custId", custId);
        params.put("deviceCode", deviceCode);
        return customerMapper.getCustomerByCustIdAndDeviceCode(params);
    }

    @Override
    public Customer getLatestCreateCustomerByCustId(String custId) {
        return customerMapper.getLatestCreateCustomerByCustId(custId);
    }

    @Override
    public Customer getCustomerByDeviceId(Long deviceId) {
        List<DeviceCustomerAccountMap> map = this.deviceCustomerAccountMapMapper.getByDeviceId(deviceId);
        if (map != null && map.size() > 0) {
            return this.customerMapper.getById(map.get(0).getCustomerId());
        }
        return null;
    }

    @Override
    public boolean pauseAndRecoverDevice(@KeyParam("user_id") String userId, String state) {
        return (1 == this.customerMapper.pauseAndRecoverDevice(userId, state));
    }

    @Override
    public long getAllCustomerCreatedLastDay(String cityId) {
        Map<String, String> params = new HashMap<String, String>();
        String lastDay = DateUtil.convertDateToString("yyyy-MM-dd", reduceDays(new Date(), 1));
        params.put("start", lastDay + " 00:00:00");
        params.put("end", lastDay + " 23:59:59");
        params.put("region", cityId);
        params.put("source", "OTHERS");

        return customerMapper.getAllCustomerCreatedLastDay(params);
    }

    @Override
    public long getAllCustomer(String cityId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("region", cityId);
        params.put("source", "OTHERS");

        return customerMapper.getAllCustomer(params);
    }

    @Override
    public Long getLastInsertId() {
        return customerMapper.getLastInsertId();
    }

    @Override
    public long getAllCustomerCancelLastDay(String cityId) {
        Map<String, String> params = new HashMap<String, String>();
        String lastDay = DateUtil.convertDateToString("yyyy-MM-dd", reduceDays(new Date(), 1));
        params.put("start", lastDay + " 00:00:00");
        params.put("end", lastDay + " 23:59:59");
        params.put("region", cityId);
        params.put("source", "OTHERS");

        return customerMapper.getAllCustomerCancelLastDay(params);
    }

    private Date reduceDays(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - offset);// 让日期加位移量
        return calendar.getTime();
    }

    @Override
    public Pageable<Customer> findCustomersByCondition(Map<String, Object> params) {
        List<Customer> customers = this.customerMapper.findCustomersByCondition(params);
        if (customers != null && customers.size() > 0) {
            for (Customer _customer : customers) {
                List<UserGroupMap> map = this.userGroupMapMapper.findByUserCode(_customer.getCode());
                StringBuffer dsb = new StringBuffer();
                for (UserGroupMap m : map) {
                    UserGroup group = this.userGroupMapper.getById(m.getUserGroupId());
                    dsb.append(group.getName()).append(",");
                }
                if (dsb.length() > 0) {
                    _customer.setGroups(dsb.substring(0, dsb.length() - 1).toString());
                }
            }
        }
        Integer total = this.customerMapper.getCountByCondition(params);
        return new Pageable<Customer>().instanceByPageNo(customers, total, Integer.parseInt(params.get("pageNo").toString()), Integer.parseInt(params.get("pageSize").toString()));
    }

    public List<Customer> getCustomersByCustId(@Param("custId") String custId) {
        return this.customerMapper.getCustomersByCustId(custId);
    }

    @Override
    public List<Customer> getBySource(String source) {
        return this.customerMapper.getBySource(source);
    }

    @Override
    public Long getCountByRegionAndState(Date start, Date end, long city,
                                         int province, String source, String state) {
        return this.customerMapper.getCountByRegionAndState(start, end, city, province, source, state);
    }

    @Override
    public Long getCountByState(Date start, Date end, int province,
                                String source, String state) {
        return this.customerMapper.getCountByState(start, end, province, source, state);
    }

//    @Override
//    public Pageable<CustomerDeviceHistoryVo> findCustomerDeviceHistorys(Map<String, String> map) {
//        List<CustomerDeviceHistoryVo> customerDeviceHistoryVos = this.customerDeviceHistoryMapper.findCustomerDeviceHistorys(map);
//        Integer total = this.customerDeviceHistoryMapper.getTotalCountCustomerDeviceHistorys(map);
//        return new Pageable<CustomerDeviceHistoryVo>().instanceByPageNo(customerDeviceHistoryVos, total, Integer.parseInt(map.get("pageNo")), Integer.parseInt(map.get("pageSize")));
//    }

    @Override
    public void delete(Long customerId) {
        this.customerMapper.delete(customerId);
    }

    @Override
    public Pageable<Customer> findCanBindCustomerList(Customer customer, int pageNo, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("id", customer.getId());
        params.put("userId", customer.getUserId() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getUserId()) : "");
        params.put("code", customer.getCode() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getCode()) : "");
        params.put("phone", customer.getPhone() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getPhone()) : "");
        params.put("loginName", customer.getLoginName() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getLoginName()) : "");
        params.put("serviceStop", customer.getServiceStop());
        params.put("nickName", customer.getNickName() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getNickName()) : "");
        params.put("realName", customer.getRealName() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getRealName()) : "");
        params.put("source", customer.getSource());
        params.put("age", customer.getAge());
        params.put("rate", customer.getRate() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getRate()) : "");

        if (customer.getArea() == null) {
            params.put("area", null);
        } else {
            params.put("area", customer.getArea().getId());
        }
        if (customer.getRegion() == null) {
            params.put("region", null);
        } else {
            params.put("region", customer.getRegion().getId());
        }
        List<Customer> customers = this.customerMapper.findCanBindCustomersByCondition(params);
        Integer total = this.customerMapper.getCountByCanBindCustomerCondition(params);
        return new Pageable<Customer>().instanceByPageNo(customers, total, pageNo, pageSize);
    }

    @Override
    public Pageable<CustomerRelationDeviceVo> findCustomerRelationByCondition(Map<String, Object> map) {
        List<CustomerRelationDeviceVo> customerRelationDeviceVos = this.deviceMapper.findCustomerDeviceRelationByCondition(map);
        if (customerRelationDeviceVos != null && customerRelationDeviceVos.size() > 0) {
            for (CustomerRelationDeviceVo crv : customerRelationDeviceVos) {
                if (crv.getDeviceArea() != null) {
                    Area deviceArea = areaService.getAreaById(crv.getDeviceArea().getId());
                    crv.setDeviceArea(deviceArea);
                }
                if (crv.getDeviceCity() != null) {
                    City deviceCity = cityRepository.getCityById(crv.getDeviceCity().getId());
                    crv.setDeviceCity(deviceCity);
                }
                if (crv.getDeviceType() != null) {
                    DeviceType deviceType = deviceTypeMapper.getById(crv.getDeviceType().getId());
                    crv.setDeviceType(deviceType);
                }
                if (crv.getDeviceVendor() != null) {
                    DeviceVendor deviceVendor = deviceVendorMapper.getById(crv.getDeviceVendor().getId());
                    crv.setDeviceVendor(deviceVendor);
                }
                if (crv.getCustomerArea() != null) {
                    Area userArea = areaService.getAreaById(crv.getCustomerArea().getId());
                    crv.setCustomerArea(userArea);
                }
                if (crv.getCustomerCity() != null) {
                    City userCity = cityRepository.getCityById(crv.getCustomerCity().getId());
                    crv.setCustomerCity(userCity);
                }
            }
        }
        Integer total = this.deviceMapper.getCountCustomerDeviceRelationByCondition(map);
        return new Pageable<CustomerRelationDeviceVo>().instanceByPageNo(customerRelationDeviceVos, total, Integer.parseInt(map.get("pageNo").toString()), Integer.parseInt(map.get("pageSize").toString()));
    }

    @Override
    public List<Customer> getAllCustomerByCustId(String custId) {
        return this.customerMapper.getAllCustomerByCustId(custId);
    }

    @Override
    public Long getCountByUidDid(Date end, List<Long> customerIds,
                                 List<Long> deviceIds) {
        return this.deviceCustomerAccountMapMapper.getCountByUidDid(end, customerIds, deviceIds);
    }

    @Override
    public Long getCountByCreateDateAndUidDid(Date start, Date end,
                                              List<Long> customerIds, List<Long> deviceIds) {
        return this.deviceCustomerAccountMapMapper.getCountByCreateDateAndUidDid(start, end, customerIds, deviceIds);
    }

    @Override
    public Long getCountByCityAndDeviceType(Date start, Date end, int province,
                                            String source, String state, int deviceType, long cityId) {
        return this.deviceCustomerAccountMapMapper.getCountByCityAndDeviceType(start, end, province, source, state, deviceType, cityId);
    }

    @Override
    public Long getCountByRegion(Date start, Date end, long city, int province,
                                 String source) {
        return this.deviceCustomerAccountMapMapper.getCountByRegion(start, end, city, province, source);
    }

    @Override
    public Long getCountByAera(Date start, Date end, int province, String source) {
        return this.deviceCustomerAccountMapMapper.getCountByAera(start, end, province, source);
    }

    @Override
    public List<DeviceCustomerAccountMap> getByCustomerId(Long customerId) {
        return this.deviceCustomerAccountMapMapper.getByCustomerId(customerId);
    }

//    @Override
//    public List<CustomerDeviceHistoryVo> findCustomerDeviceHistoryByState(Map<String, Object> map) {
//        return this.customerDeviceHistoryMapper.findCustomerDeviceHistoryByState(map);
//    }

    @Override
    public List<Customer> findAllCustomersByCondition(Customer customer) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", customer.getId());
        params.put("userId", customer.getUserId());
        params.put("code", customer.getCode());
        params.put("phone", customer.getPhone());
        params.put("loginName", customer.getLoginName());
        params.put("serviceStop", customer.getServiceStop());
        params.put("nickName", customer.getNickName());
        params.put("realName", customer.getRealName());
        params.put("source", customer.getSource());
        params.put("rate", customer.getRate());

        if (customer.getArea() != null) {
            params.put("area", customer.getArea().getId());
        } else {
            params.put("area", null);
        }
        if (customer.getRegion() != null) {
            params.put("region", customer.getRegion().getId());
        } else {
            params.put("region", null);
        }
        return this.customerMapper.findCustomersByCondition(params);
    }

    @Override
    public List<Customer> findAllCanBindCustomerList(Customer customer) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", customer.getId());
        params.put("userId", customer.getUserId());
        params.put("code", customer.getCode());
        params.put("phone", customer.getPhone());
        params.put("loginName", customer.getLoginName());
        params.put("area", customer.getArea());
        params.put("serviceStop", customer.getServiceStop());
        params.put("nickName", customer.getNickName());
        params.put("realName", customer.getRealName());
        params.put("source", customer.getSource());
        params.put("age", customer.getAge());
        params.put("rate", customer.getRate());
        return this.customerMapper.findCanBindCustomersByCondition(params);
    }

    // @Override
    // public List<CustomerRelationDeviceVo> findAllCustomerRelation() {
    // Map<String, Object> params = new HashMap<String, Object>();
    // return
    // this.deviceCustomerAccountMapMapper.findCustomerRelationByCondition(params);
    // }
    @Override
    public List<CustomerRelationDeviceVo> findAllCustomerRelation(CustomerRelationDeviceVo vo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("customerCode", vo.getCustomerCode());
        params.put("customerUserId", vo.getCustomerUserId());
        params.put("deviceCode", vo.getDeviceCode());
        params.put("deviceSno", vo.getDeviceSno());
        List<CustomerRelationDeviceVo> customerRelationDeviceVos = this.deviceMapper.findCustomerDeviceRelationByCondition(params);
        if (customerRelationDeviceVos != null && customerRelationDeviceVos.size() > 0) {
            for (CustomerRelationDeviceVo crv : customerRelationDeviceVos) {
//				if(crv.getCustomerArea() != null){
//					Area custometArea = areaService.getAreaById(crv.getCustomerArea().getId());
//					crv.setCustomerArea(custometArea);
//				}
//				if(crv.getCustomerCity() != null){
//					City customerCity = cityRepository.getCityById(crv.getCustomerCity().getId());
//					crv.setCustomerCity(customerCity);
//				}
                if (crv.getDeviceArea() != null) {
                    Area deviceArea = areaService.getAreaById(crv.getDeviceArea().getId());
                    crv.setDeviceArea(deviceArea);
                }
                if (crv.getDeviceCity() != null) {
                    City deviceCity = cityRepository.getCityById(crv.getDeviceCity().getId());
                    crv.setDeviceCity(deviceCity);
                }
                if (crv.getDeviceType() != null) {
                    DeviceType deviceType = deviceTypeMapper.getById(crv.getDeviceType().getId());
                    crv.setDeviceType(deviceType);
                }
            }
        }
        return customerRelationDeviceVos;
    }

    @Override
    public List<Customer> getCustomerByIds(List<Long> ids) {
        return this.customerMapper.getCustomerByIds(ids);
    }

    @Override
    public List<Customer> findCustomersToExport(Map<String, Object> map) {
        return this.customerMapper.findCustomersToExport(map);
    }

    @Override
    public List<Customer> getAllCustomerByIsSync(Integer num) {
        return this.customerMapper.getAllCustomerByIsSync(num);
    }

    @Override
    public boolean updateSyncById(Long id, String isSync) {
        return 1 == this.customerMapper.updateSyncById(id, isSync);
    }

    @Override
    public int getCustomerCountByIsSync() {
        return this.customerMapper.getCountByIsSync();
    }

    @Override
    public List<Customer> getOpenAndCancelCustomer(String yesterday) throws Exception {
        Date createStartDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", yesterday + " 00:00:00");
        Date createEndDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", yesterday + " 23:59:59");
        Date cancelStartDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", yesterday + " 00:00:00");
        Date cancelEndDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", yesterday + " 23:59:59");
        return this.customerMapper.getOpenAndCancelCustomer(createStartDate, createEndDate, cancelStartDate, cancelEndDate);
    }

//    @Override
//    public List<CustomerDeviceHistory> findCustomerDeviceHistorysByCustomerIdAndDate(Long customerId, Date beginDate, Date endDate) {
//        return this.customerDeviceHistoryMapper.findCustomerDeviceHistorysByCustomerIdAndDate(customerId, beginDate, endDate);
//    }

    @Override
    public List<UserActiveStatistics> getAccountUserReport(String activeDate, Long province) {
        return customerMapper.getAccountUserReport(activeDate, province);
    }

    @Override
    public Customer getcustomerByNickName(String userId) {
        return customerMapper.getcustomerByNickName(userId);
    }

    @Override
    public List<Customer> findCustomersByUserCodes(String[] userIds) {
        return customerMapper.findCustomersByUserCodes(userIds);
    }

    @Override
    public List<Long> findAreaByBusiness(Long Id, String character, String tableName) {
        return customerMapper.findAreaByBusiness(Id, character, tableName);
    }


    @Override
    @MethodFlush(keys = {CUSTOMER_USERID + "#{customer.userId}", CUSTOMER_CODE + "#{customer.code}", CUSTOMER_ID + "#{customer.id}"})
    public boolean updateByUserId(@KeyParam("customer") Customer customer) {
        return (1 == this.customerMapper.updateByUserId(customer));
    }

    @Override
    public Pageable<CustomerRelationDeviceVo> findCustomerRelationByDeviceSno(
            String snos, int pageNo, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("snos", snos);
        List<CustomerRelationDeviceVo> customerRelationDeviceVos = this.deviceCustomerAccountMapMapper.findCustomerRelationByDeviceSno(params);
        if (customerRelationDeviceVos != null && customerRelationDeviceVos.size() > 0) {
            for (CustomerRelationDeviceVo crv : customerRelationDeviceVos) {
                if (crv.getCustomerArea() != null) {
                    Area custometArea = areaService.getAreaById(crv.getCustomerArea().getId());
                    crv.setCustomerArea(custometArea);
                }
                if (crv.getCustomerCity() != null) {
                    City customerCity = cityRepository.getCityById(crv.getCustomerCity().getId());
                    crv.setCustomerCity(customerCity);
                }
                if (crv.getDeviceArea() != null) {
                    Area deviceArea = areaService.getAreaById(crv.getDeviceArea().getId());
                    crv.setDeviceArea(deviceArea);
                }
                if (crv.getDeviceCity() != null) {
                    City deviceCity = cityRepository.getCityById(crv.getDeviceCity().getId());
                    crv.setDeviceCity(deviceCity);
                }
                if (crv.getDeviceType() != null) {
                    DeviceType deviceType = deviceTypeMapper.getById(crv.getDeviceType().getId());
                    crv.setDeviceType(deviceType);
                }
            }
        }
        Integer total = this.deviceCustomerAccountMapMapper.getCountCustomerRelationByDeviceSno(snos);
        return new Pageable<CustomerRelationDeviceVo>().instanceByPageNo(customerRelationDeviceVos, total, pageNo, pageSize);
    }

    @Override
    public Pageable<Customer> findCustomersOfGroupByCondition(String tableName, String character,
                                                              Customer customer, Integer pageNo, Integer pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("userId", customer.getUserId() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getUserId()) : "");
        params.put("code", customer.getCode() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getCode()) : "");
        params.put("phone", customer.getPhone() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getPhone()) : "");
        params.put("tableName", tableName);
        params.put("character", character);

        if (customer.getGroups() != null) {
            params.put("group", customer.getGroups());
        } else {
            params.put("group", null);
        }
        if (customer.getRegion() != null) {
            params.put("region", customer.getRegion().getId());
        } else {
            params.put("region", null);
        }
        List<Customer> customers = new ArrayList<Customer>();
        if (Constant.BSS_USER_BACKGROUND_IMAGE_MAP.equals(tableName)) {
            customers = this.customerMapper.findCustomersByBackImageId(params);
        } else {
            customers = this.customerMapper.findCustomersOfGroupByCondition(params);
        }
        if (customers != null && customers.size() > 0) {
            List<Long> cityIds = new ArrayList<Long>();
            List<Customer> userList = new ArrayList<Customer>();
            for (Customer user : customers) {
                if (user.getRegion() != null) {
                    cityIds.add(user.getRegion().getId());
                    userList.add(user);
//                	user.setRegion(this.cityMapper.getById(user.getRegion().getId()));
                }
            }
            if (cityIds != null && cityIds.size() > 0) {
                List<City> cityList = this.cityMapper.findCitysByIds(cityIds);
                for (Customer u : userList) {
                    if (cityList.size() > 0) {
                        for (City c : cityList) {
                            if (u.getRegion().getId().equals(c.getId())) {
                                u.setRegion(c);
                            }
                        }
                    }
                }
            }
        }
        Integer total = this.customerMapper.getUserCountOfGroupByCondition(params);
        return new Pageable<Customer>().instanceByPageNo(customers, total, pageNo, pageSize);
    }

    @Override
    public List<UserActiveStatistics> getActiveUserReport(String activeDate,
                                                          String statisticsType, Long province) {
        return this.customerMapper.getActiveUserReport(activeDate, statisticsType, province);
    }

    @Override
    public List<UserActiveStatistics> getCancelUserReport(String cancelledDate,
                                                          String statisticsType, Long province) {
        return this.customerMapper.getCancelUserReport(cancelledDate, statisticsType, province);
    }

	@Override
	public List<Customer> findExportCustomers(Map<String, Object> map) {
		return this.customerMapper.findExportCustomers(map);
	}

	@Override
	public List<CustomerRelationDeviceVo> findRelationCustomerByDeviceIds(
			List<Long> deviceIds) {
		List<CustomerRelationDeviceVo> customerRelationDeviceVos = this.deviceMapper.findRelationCustomerByDeviceIds(deviceIds);
		if (customerRelationDeviceVos != null && customerRelationDeviceVos.size() > 0) {
            for (CustomerRelationDeviceVo crv : customerRelationDeviceVos) {
            	if (crv.getDeviceArea() != null) {
                    Area deviceArea = areaService.getAreaById(crv.getDeviceArea().getId());
                    crv.setDeviceArea(deviceArea);
                }
                if (crv.getDeviceCity() != null) {
                    City deviceCity = cityRepository.getCityById(crv.getDeviceCity().getId());
                    crv.setDeviceCity(deviceCity);
                }
                if (crv.getDeviceType() != null) {
                    DeviceType deviceType = deviceTypeMapper.getById(crv.getDeviceType().getId());
                    crv.setDeviceType(deviceType);
                }
                if (crv.getCustomerArea() != null) {
                    Area userArea = areaService.getAreaById(crv.getCustomerArea().getId());
                    crv.setCustomerArea(userArea);
                }
                if (crv.getCustomerCity() != null) {
                    City userCity = cityRepository.getCityById(crv.getCustomerCity().getId());
                    crv.setCustomerCity(userCity);
                }
                if (crv.getDeviceVendor() != null) {
                    DeviceVendor deviceVendor = deviceVendorMapper.getById(crv.getDeviceVendor().getId());
                    crv.setDeviceVendor(deviceVendor);
                }
            }
		}
		return customerRelationDeviceVos;
	}

	@Override
	public List<CustomerRelationDeviceVo> exportRelationListByConditions(
			Map<String, Object> map) {
		List<CustomerRelationDeviceVo> customerRelationDeviceVos = this.deviceMapper.exportRelationListByConditions(map);
		if (customerRelationDeviceVos != null && customerRelationDeviceVos.size() > 0) {
            for (CustomerRelationDeviceVo crv : customerRelationDeviceVos) {
            	if (crv.getDeviceArea() != null) {
                    Area deviceArea = areaService.getAreaById(crv.getDeviceArea().getId());
                    crv.setDeviceArea(deviceArea);
                }
                if (crv.getDeviceCity() != null) {
                    City deviceCity = cityRepository.getCityById(crv.getDeviceCity().getId());
                    crv.setDeviceCity(deviceCity);
                }
                if (crv.getDeviceType() != null) {
                    DeviceType deviceType = deviceTypeMapper.getById(crv.getDeviceType().getId());
                    crv.setDeviceType(deviceType);
                }
                if (crv.getDeviceVendor() != null) {
                    DeviceVendor deviceVendor = deviceVendorMapper.getById(crv.getDeviceVendor().getId());
                    crv.setDeviceVendor(deviceVendor);
                }
                if (crv.getCustomerCity() != null) {
                    City userCity = cityRepository.getCityById(crv.getCustomerCity().getId());
                    crv.setCustomerCity(userCity);
                }
                if (crv.getDeviceVendor() != null) {
                    DeviceVendor deviceVendor = deviceVendorMapper.getById(crv.getDeviceVendor().getId());
                    crv.setDeviceVendor(deviceVendor);
                }
            }
		}
		return customerRelationDeviceVos;
	}

	@Override
	public Pageable<CustomerDeviceHistory> findCustomerDeviceHistoryByConditions(
			Map<String, Object> map) {
		List<CustomerDeviceHistory> customerDeviceHistory = this.customerDeviceHistoryMapper.findHistoryListByConditions(map);
		if (customerDeviceHistory != null && customerDeviceHistory.size() > 0) {
            for (CustomerDeviceHistory csh : customerDeviceHistory) {
                if (csh.getArea() != null) {
                	csh.setArea(this.areaMapper.getById(csh.getArea().getId()));
                }
                if (csh.getCity() != null) {
                	csh.setCity(this.cityMapper.getById(csh.getCity().getId()));
                }
            }
        }
		int total = this.customerDeviceHistoryMapper.getCountByConditions(map);
		return new Pageable<CustomerDeviceHistory>().instanceByPageNo(customerDeviceHistory, total, Integer.parseInt(map.get("pageNo").toString()), Integer.parseInt(map.get("pageSize").toString()));
	}

	@Override
	public List<CustomerDeviceHistory> findCustomerDeviceHistoryByIds(
			List<Long> ids) {
		return this.customerDeviceHistoryMapper.findHistoryListByIds(ids);
	}

	@Override
	public List<CustomerDeviceHistory> findExportHistoryByConditions(
			Map<String, Object> map) {
		List<CustomerDeviceHistory> customerDeviceHistory = this.customerDeviceHistoryMapper.findHistoryListByConditions(map);
		if (customerDeviceHistory != null && customerDeviceHistory.size() > 0) {
            for (CustomerDeviceHistory csh : customerDeviceHistory) {
                if (csh.getArea() != null) {
                	csh.setArea(this.areaMapper.getById(csh.getArea().getId()));
                }
                if (csh.getCity() != null) {
                	csh.setCity(this.cityMapper.getById(csh.getCity().getId()));
                }
            }
        }
		return customerDeviceHistory;
	}
}
