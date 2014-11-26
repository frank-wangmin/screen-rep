package com.ysten.local.bss.customer.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.common.web.controller.ExportController;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.CustomerDeviceHistoryVo;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.web.service.ICustomerWebService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CustomerDeviceHistoryController extends ExportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDeviceHistoryController.class);
    @Autowired
    private ICustomerWebService customerWebService;
    @Autowired
    private ILoggerWebService loggerWebService;
    private static final String START = "start";
    private static final String LIMIT = "limit";

    @RequestMapping(value = "/to_device_customer_history_list")
    public String toCustomerDeviceHistory(ModelMap model) {
        return "/customer/customer_device_history_list";
    }

//    @RequestMapping(value = "/customer_device_history_list.json")
//    public void findCustomerDeviceHistoryList(
//            @RequestParam(value = "newYstenId", defaultValue = "") String newYstenId,
//            @RequestParam(value = "oldYstenId", defaultValue = "") String oldYstenId,
//            @RequestParam(value = "newDeviceCode", defaultValue = "") String newDeviceCode,
//            @RequestParam(value = "oldDeviceCode", defaultValue = "") String oldDeviceCode,
//            @RequestParam(value = "customerCode", defaultValue = "") String customerCode,
//            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
//            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
//            HttpServletRequest request, HttpServletResponse response) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("pageNo", FilterSpaceUtils.filterStartEndSpace(pageNo));
//        map.put("pageSize", pageSize);
//        map.put("newYstenId", FilterSpaceUtils.filterStartEndSpace(newYstenId));
//        map.put("oldYstenId", FilterSpaceUtils.filterStartEndSpace(oldYstenId));
//        map.put("newDeviceCode", FilterSpaceUtils.filterStartEndSpace(newDeviceCode));
//        map.put("oldDeviceCode", FilterSpaceUtils.filterStartEndSpace(oldDeviceCode));
//        map.put("customerCode", FilterSpaceUtils.filterStartEndSpace(customerCode));
//        Pageable<CustomerDeviceHistoryVo> pageable = null;
////        	this.customerWebService.findCustomerDeviceHistorys(map);
//        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
//    }
    @RequestMapping(value = "/customer_device_history_list.json")
    public void findCustomerDeviceHistoryList(
    	  @RequestParam(value = "bid", defaultValue = "") String bid,
    	  @RequestParam(value = "ystenId", defaultValue = "") String ystenId,
    	  @RequestParam(value = "deviceCode", defaultValue = "") String deviceCode,
    	  @RequestParam(value = "userId", defaultValue = "") String userId,
    	  @RequestParam(value = "customerCode", defaultValue = "") String customerCode,
    	  
          @RequestParam(value = "startDate", defaultValue = "") String startDate,
          @RequestParam(value = "endDate", defaultValue = "") String endDate,
          @RequestParam(value = Constants.AREA_ID, defaultValue = "") Long areaId,
          @RequestParam(value = "cityId", defaultValue = "") Long cityId,
          @RequestParam(value = "customerOuterCode", defaultValue = "") String customerOuterCode,
          @RequestParam(value = "loginName", defaultValue = "") String loginName,
          @RequestParam(value = "phone", defaultValue = "") String phone,
          @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
          @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
          HttpServletRequest request, HttpServletResponse response) {
    	try{
    		Map<String, Object> map = new HashMap<String, Object>();
    	      map.put("pageNo", Integer.parseInt(pageNo));
    	      map.put("pageSize", Integer.parseInt(pageSize));
    	      map.put("bid", bid);
    	      if(bid.equals("0")){
    	    	  map.put("ystenId", FilterSpaceUtils.filterStartEndSpace(ystenId));
        	      map.put("deviceCode", FilterSpaceUtils.filterStartEndSpace(deviceCode));
        	      map.put("userId", FilterSpaceUtils.filterStartEndSpace(userId));
        	      map.put("customerCode", FilterSpaceUtils.filterStartEndSpace(customerCode));
    	      }
    	      if(bid.equals("1")){
    	    	  map.put("ystenIds", getListByStringSplitDot(ystenId));
        	      map.put("deviceCodes", getListByStringSplitDot(deviceCode));
        	      map.put("userIds", getListByStringSplitDot(userId));
        	      map.put("customerCodes", getListByStringSplitDot(customerCode));
    	      }
    	      map.put("customerOuterCode", getListByStringSplitDot(customerOuterCode));
    	      map.put("loginName", FilterSpaceUtils.filterStartEndSpace(loginName));
    	      map.put("phone", FilterSpaceUtils.filterStartEndSpace(phone));
    	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	      Long area = areaId != null && areaId == 0 ? null : areaId;
    	      Long city = cityId != null && cityId == 0 ? null : cityId;
    	      map.put("area", area);
    	      map.put("city", city);
    	      map.put("deviceEndDate", StringUtils.isNotBlank(endDate) ? sdf.parse(endDate + " 23:59:59") : null);
    	      map.put("deviceStartDate", StringUtils.isNotBlank(startDate) ? sdf.parse(startDate + " 00:00:00") : null);
    	      Pageable<CustomerDeviceHistory> pageable = this.customerWebService.findCustomerDeviceHistoryByConditions(map);
    	      RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    	}catch (Exception e) {
            LOGGER.error("Query customer device history data exception{}", e);
        }
      
  }
    
    @RequestMapping(value = "/device_customer_history_export_byId.json")
    public void exportCustomerDeviceHistoryListByIds(
            @RequestParam(value = "ids", defaultValue = "") String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
        	List<CustomerDeviceHistory> datas = null;
        	if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                datas = this.customerWebService.findCustomerDeviceHistoryByIds(idsList);
        	}
            List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("设备序列号");
            heads.add("设备激活时间");
            heads.add("设备创建时间");
            heads.add("设备状态");
            heads.add("用户编号");
            heads.add("用户外部编号");
            heads.add("外部用户编号");
            heads.add("用户登录名");
            heads.add("用户电话");
            heads.add("用户激活时间");
            heads.add("用户创建时间");
            heads.add("所属区域");
            heads.add("所属地市");
            heads.add("关系创建时间");
            heads.add("描述");
            
            String fileName = "Customer_Device_History_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("deviceId");
            this.export(heads, datas, excludes, CustomerDeviceHistory.class, fileName, request, response);
            loggerWebService.saveOperateLog(Constants.CUSTOMER_DEVICE_HISTORY_INFO_MAINTAIN, "导出",
            		ids, "导出成功!" , request);
        } catch (Exception e) {
        	RenderUtils.renderText("导出异常!", response);
            LOGGER.error("Export customer device history data exception{}", e);
            loggerWebService.saveOperateLog(Constants.CUSTOMER_DEVICE_HISTORY_INFO_MAINTAIN, "导出",
            		ids, "导出异常!" , request);
        }
    }
    
    @RequestMapping(value = "/device_customer_history_export_by_query.json")
    public void exportCustomerDeviceHistoryListByQuery(
    		@RequestParam(value = "ystenValue", defaultValue = "") String ystenValue,
      	    @RequestParam(value = "deviceCodeValue", defaultValue = "") String deviceCodeValue,
      	    @RequestParam(value = "customerCodeValue", defaultValue = "") String customerCodeValue,
      	    @RequestParam(value = "userIdValue", defaultValue = "") String userIdValue,
      	  
            @RequestParam(value = "deviceStartDateValue", defaultValue = "") String deviceStartDateValue,
            @RequestParam(value = "deviceEndDateValue", defaultValue = "") String deviceEndDateValue,
            @RequestParam(value = "areaValue", defaultValue = "") String areaValue,
            @RequestParam(value = "cityValue", defaultValue = "") String cityValue,
            @RequestParam(value = "ystenHvalue", defaultValue = "") String ystenHvalue,
            @RequestParam(value = "deviceCodeHvalue", defaultValue = "") String deviceCodeHvalue,
            @RequestParam(value = "customerCodeHvalue", defaultValue = "") String customerCodeHvalue,
            @RequestParam(value = "userIdHvalue", defaultValue = "") String userIdHvalue,
            @RequestParam(value = "uOuterCodeValue", defaultValue = "") String uOuterCodeValue,
            @RequestParam(value = "loginNameValue", defaultValue = "") String loginNameValue,
            @RequestParam(value = "phoneValue", defaultValue = "") String phoneValue,
            HttpServletRequest request, HttpServletResponse response) {
        try {
        	Map<String, Object> map = new HashMap<String, Object>();
	  	      map.put("pageNo", null);
	  	      map.put("pageSize", null);
	  	      map.put("ystenIds", getListByStringSplitDot(ystenHvalue));
	      	  map.put("deviceCodes", getListByStringSplitDot(deviceCodeHvalue));
	      	  map.put("userIds", getListByStringSplitDot(userIdHvalue));
	      	  map.put("customerCodes", getListByStringSplitDot(customerCodeHvalue));
	  	      map.put("ystenId", FilterSpaceUtils.filterStartEndSpace(ystenValue));
	  	      map.put("deviceCode", FilterSpaceUtils.filterStartEndSpace(deviceCodeValue));
	  	      map.put("userId", FilterSpaceUtils.filterStartEndSpace(userIdValue));
	  	      map.put("customerCode", FilterSpaceUtils.filterStartEndSpace(customerCodeValue));
	  	      map.put("customerOuterCode", getListByStringSplitDot(uOuterCodeValue));
	  	      map.put("loginName", FilterSpaceUtils.filterStartEndSpace(loginNameValue));
	  	      map.put("phone", FilterSpaceUtils.filterStartEndSpace(phoneValue));
	  	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  	      Long area = areaValue.equals("") ? null : Long.parseLong(areaValue);
	  	      Long city = cityValue.equals("") ? null : Long.parseLong(cityValue);
	  	      map.put("area", area);
	  	      map.put("city", city);
	  	      map.put("deviceEndDate", StringUtils.isNotBlank(deviceEndDateValue) ? sdf.parse(deviceEndDateValue + " 23:59:59") : null);
	  	      map.put("deviceStartDate", StringUtils.isNotBlank(deviceStartDateValue) ? sdf.parse(deviceStartDateValue + " 00:00:00") : null);
	  	    List<CustomerDeviceHistory> datas = this.customerWebService.findExportHistoryByConditions(map);
		  	  if (datas.size() > 20000) {
	              RenderUtils.renderText("导出记录不能超过2万条!", response);
	              loggerWebService.saveOperateLog(Constants.CUSTOMER_DEVICE_HISTORY_INFO_MAINTAIN, "导出",
	              		"", "导出失败!要导出记录超过2万条!" , request);
	              return;
	          }
	          if (datas.size() == 0) {
	              RenderUtils.renderText("没有要导出的数据!", response);
	              loggerWebService.saveOperateLog(Constants.CUSTOMER_DEVICE_HISTORY_INFO_MAINTAIN, "导出",
		              		"", "导出失败!没有要导出的数据!" , request);
	              return;
	          }
            List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("设备序列号");
            heads.add("设备激活时间");
            heads.add("设备创建时间");
            heads.add("设备状态");
            heads.add("用户编号");
            heads.add("用户外部编号");
            heads.add("外部用户编号");
            heads.add("用户登录名");
            heads.add("用户电话");
            heads.add("用户激活时间");
            heads.add("用户创建时间");
            heads.add("所属区域");
            heads.add("所属地市");
            heads.add("关系创建时间");
            heads.add("描述");
            
            String fileName = "Customer_Device_History_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("deviceId");
            this.export(heads, datas, excludes, CustomerDeviceHistory.class, fileName, request, response);
        } catch (Exception e) {
        	RenderUtils.renderText("导出异常!", response);
            LOGGER.error("Export customer device history data exception{}", e);
            loggerWebService.saveOperateLog(Constants.CUSTOMER_DEVICE_HISTORY_INFO_MAINTAIN, "导出",
              		"", "导出异常!" +e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/customer_device_history_export.json") 
    public void exportCustomerDeviceHistoryList(
            @RequestParam(value = "newDeviceCode", defaultValue = "") String newDeviceCode,
            @RequestParam(value = "oldDeviceCode", defaultValue = "") String oldDeviceCode,
            @RequestParam(value = "customerCode", defaultValue = "") String customerCode, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            List<String> heads = new ArrayList<String>();
            heads.add("用户编号");
            heads.add("用户外部编号");
            heads.add("用户登录名");
            heads.add("用户状态");
            heads.add("用户电话");
            heads.add("用户创建时间");
            heads.add("用户所属地市");
            heads.add("用户激活时间");
            heads.add("旧设备编号");
            heads.add("旧易视腾编号");
            heads.add("旧设备序列号");
            heads.add("旧设备创建时间");
            heads.add("旧设备激活时间");
            heads.add("旧设备状态");
            heads.add("新设备编号");
            heads.add("新易视腾编号");
            heads.add("新设备序列号");
            heads.add("新设备创建时间");
            heads.add("新设备激活时间");
            heads.add("新设备状态");
            heads.add("创建时间");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("newDeviceCode", newDeviceCode);
            map.put("oldDeviceCode", oldDeviceCode);
            map.put("customerCode", customerCode);
            List<CustomerDeviceHistoryVo> devices = null;
//            	this.customerWebService.findCustomerDeviceHistoryByState(map);
            String fileName = "Customer_Device_History_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("userNo");
            excludes.add("oldDeviceBindType");
            excludes.add("oldDeviceNo");
            excludes.add("newDeviceBindType");
            excludes.add("newDeviceNo");
            this.export(heads, devices, excludes, CustomerDeviceHistoryVo.class, fileName, request, response);
        } catch (Exception e) {
            LOGGER.error("Export customer device history data exception{}", e);
        }
    }
    public List<String> getListByStringSplitDot(String codes){
		String[] _codes = new String[0];;
		if (StringUtils.isNotBlank(codes)) {
			_codes = codes.split(",");
            List<String> codeList = new ArrayList<String>();
            for (int i = 0; i < _codes.length; i++) {
                if (StringUtils.isEmpty(_codes[i])) {
                    continue;
                }
                _codes[i] = FilterSpaceUtils.replaceBlank(_codes[i]);
                codeList.add(_codes[i]);
            }
            if(codeList.size()>0 && codeList != null){
            	return codeList;
            }
		}
		return null;
	}
}
