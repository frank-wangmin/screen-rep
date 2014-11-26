package com.ysten.local.bss.jsdx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.bean.OTTResponseBean;
import com.ysten.local.bss.device.bean.Product;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Customer.State;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.UserPackageInfoModified;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.IUserPackageModifiedRepository;
import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.local.bss.device.service.impl.DeviceServiceImpl;
import com.ysten.local.bss.jsdx.service.IJsdxDeviceService;

@Service
public class JsdxDeviceServiceImpl implements IJsdxDeviceService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);
	
	@Autowired
	private ICustomerService customerService;
	@Autowired
    private IDeviceRepository deviceRepository;
	@Autowired
    private IUserPackageModifiedRepository userPackageRepository;
	@Autowired
	private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;

	@Override
	public Map<String, Object> processRequest(Map<String, Object> requestMap) {
		String response = (String) requestMap.get(OTTResponseBean.KEY_STATUS_RESPONSE);
		if(StringUtils.isEmpty(response)) {
			return null;
		}
		OTTResponseBean ottResponseBean = getOTTResponseBean(response);
		if(!OTTResponseBean.OTT_RETURN_OK.equals(ottResponseBean.getResult())) {
			requestMap.put(OTTResponseBean.KEY_RESULT_CODE, OTTResponseBean.VIRIFIED_ERROR_CODE_OTT);
			requestMap.put(OTTResponseBean.KEY_RESULT_DESC, ottResponseBean.getResult());
			return null;
		}
		//create customer
		Customer customer = createCustomer(ottResponseBean);
		if(null == customer) {
			requestMap.put(OTTResponseBean.KEY_RESULT_CODE, OTTResponseBean.VIRIFIED_ERROR_CODE_CLIENT);
			requestMap.put(OTTResponseBean.KEY_RESULT_DESC, OTTResponseBean.VIRIFIED_ERROR_DESC_CLIENT);	
			return null;
		}
		Device device = this.deviceRepository.getDeviceByCode(ottResponseBean.getSn());
		if(null == device || Device.State.NOTUSE == device.getState()) {
			requestMap.put(OTTResponseBean.KEY_RESULT_CODE, OTTResponseBean.VIRIFIED_ERROR_CODE_SN);
			requestMap.put(OTTResponseBean.KEY_RESULT_DESC, OTTResponseBean.VIRIFIED_ERROR_DESC_SN);	
			return null;
		}
		//login device
		if(Device.State.ACTIVATED == device.getState() && Device.BindType.BIND == device.getBindType()) {
			return loginDevice(customer, device, ottResponseBean.getProducts(), requestMap);
		}
		//bind device
		if(Device.State.NONACTIVATED == device.getState() && Device.BindType.UNBIND == device.getBindType()) {
			return bindDevice(customer, device, ottResponseBean.getProducts(), requestMap);
		}
		return requestMap;
	}
	
	/**
	 * 
	 * @param customer
	 * @param device
	 * @param requestMap
	 * @return
	 */
	private Map<String, Object> bindDevice(Customer customer, Device device, List<Product> products, Map<String, Object> requestMap) {
		if(!this.customerService.deviceBindCustomer(customer, device)) {
			requestMap.put(OTTResponseBean.KEY_RESULT_CODE, OTTResponseBean.VIRIFIED_ERROR_CODE_DB);
			requestMap.put(OTTResponseBean.KEY_RESULT_DESC, OTTResponseBean.VIRIFIED_ERROR_DESC_DB);	
			return null;
		}
		if(!saveProducts(customer, products)) {
			requestMap.put(OTTResponseBean.KEY_RESULT_CODE, OTTResponseBean.VIRIFIED_ERROR_CODE_DB);
			requestMap.put(OTTResponseBean.KEY_RESULT_DESC, OTTResponseBean.VIRIFIED_ERROR_DESC_DB);	
			return null;
		}
		return requestMap;
	}
		
	/**
	 * 
	 * @param customer
	 * @param device
	 * @param requestMap
	 * @return
	 */
	private Map<String, Object> loginDevice(Customer customer, Device device, List<Product> products, Map<String, Object> requestMap) {
		List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
		if(null != maps && maps.size() > 0 && isBoundDevice(device.getCode(), maps)) {
			return requestMap;
		}
		if(!saveProducts(customer, products)) {
			requestMap.put(OTTResponseBean.KEY_RESULT_CODE, OTTResponseBean.VIRIFIED_ERROR_CODE_DB);
			requestMap.put(OTTResponseBean.KEY_RESULT_DESC, OTTResponseBean.VIRIFIED_ERROR_DESC_DB);	
			return null;
		}
		requestMap.put(OTTResponseBean.KEY_RESULT_CODE,	OTTResponseBean.VIRIFIED_ERROR_CODE_SN);
		requestMap.put(OTTResponseBean.KEY_RESULT_DESC,	OTTResponseBean.VIRIFIED_ERROR_DESC_SN);
		return null;
		
	}
	
	/**
	 * 
	 * @param deviceCode
	 * @param maps
	 * @return
	 */
	private boolean isBoundDevice(String deviceCode, List<DeviceCustomerAccountMap> maps) {
		if(StringUtils.isEmpty(deviceCode)) {
			return false;
		}
		for (int i = 0; i < maps.size(); i++) {
			if (deviceCode.equals(maps.get(i).getDeviceCode())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param ottResponseBean
	 * @return
	 * @author HanksXu
	 * @date 2013-10-18
	 */
	private Customer createCustomer(OTTResponseBean ottResponseBean) {
		Customer customer = this.customerService.getCustomerByUserId(ottResponseBean.getClientid());
		if(null != customer) {
			if(Customer.State.NORMAL != customer.getState()) {
				return null;
			}
			return customer;
		}
		//customer is not registered before.
		customer = new Customer();
		if(!StringUtils.isEmpty(ottResponseBean.getClientid())) {
			customer.setUserId(ottResponseBean.getClientid());
			customer.setLoginName(ottResponseBean.getClientid());
    	}
		customer.setCode(NumberGenerator.getNextCode());
		customer.setState(State.NORMAL);
		try {
			customer.setCreateDate(new Date(Long.valueOf(ottResponseBean.getTimestamp())));
		} catch (Exception e) {
			customer.setCreateDate(new Date());
		}
		customer.setSource("JIANGSUDX");
		if(!this.customerService.saveCustomer(customer)) {
			return null;
		}
		return customer;
	}

	/**
	 * 
	 * @param customer
	 * @param products
	 * @return
	 * @author HanksXu
	 * @date 2013-10-18
	 */
	private boolean saveProducts(Customer customer, List<Product> products) {
		if(null == products || products.size() <= 0) {
			return true;
		}
		List<UserPackageInfoModified> userPackageInfoList = getUserPackageInfo(customer, products);
		for (UserPackageInfoModified upi : userPackageInfoList) {
			if(null != this.userPackageRepository.getById(Long.valueOf(upi.getId()))) {
				if(!this.userPackageRepository.upadteUserPackageInfo(upi)) {
					return false;
				}
			} else if (!(1 == this.userPackageRepository.saveUserPackageInfo(upi))) {
				return false;
			}
		}
		return true;
	}
	
	
	 /**
     * get the product list info in the customer object.
     * 
     * @param customer
     * @param productList
     * @return
     * @author HanksXu
     */
    private List<UserPackageInfoModified> getUserPackageInfo(Customer customer, List<Product> products) {
        String customerCode = customer.getCode();
        List<UserPackageInfoModified> upiList = new ArrayList<UserPackageInfoModified>();
        Date currentTime = customer.getCreateDate();
        List<UserPackageInfoModified> orderedProduct = this.userPackageRepository.findByCustomerId(customer.getCode());
        List<String> productIdList = null; 
        if(null != orderedProduct && orderedProduct.size() > 0) {
        	productIdList = new ArrayList<String>();
        	for(int i = 0; i < orderedProduct.size(); i++) {
        		productIdList.add(orderedProduct.get(i).getProductId());
        	}
        }
        String productId = null;
        for (int index = 0; index < products.size(); index++) {
        	try {
        		productId = products.get(index).getProductId();
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				continue;
			}
        	if(null != productIdList && productIdList.contains(productId)) {
        		continue;
        	}
            UserPackageInfoModified upi = new UserPackageInfoModified();
            upi.setProductId(productId);
            upi.setStartDate(currentTime);
            upi.setCreateDate(currentTime);
            upi.setCustomerCode(customerCode);
            upiList.add(upi);
        }
        return upiList;
    }
	
    /**
     * 
     * @param jsonStr
     * @return
     * @author HanksXu
     * @date 2013-10-18
     */
	private OTTResponseBean getOTTResponseBean(String jsonStr) {
		JSONObject json = JSONObject.fromObject(jsonStr);
		if(null == json) {
			return null;
		}
		OTTResponseBean orb = (OTTResponseBean) JSONObject.toBean(json, OTTResponseBean.class);
		if(!"0".equals(orb.getResult())) {
			return orb;
		}
		int productCount = orb.getProductlist().size();
		if(productCount > 0) {
			List<Product> products = new ArrayList<Product>();
			for(int i = 0; i < productCount; i++){   
	            JSONObject jsonObject = orb.getProductlist().getJSONObject(i);   
	            Product p = (Product)JSONObject.toBean(jsonObject, Product.class);
	            if(null != p) {
	            	products.add(p); 
	            }
	        }  
			orb.setProducts(products);
		}
		return orb;
	}
    
	
}

