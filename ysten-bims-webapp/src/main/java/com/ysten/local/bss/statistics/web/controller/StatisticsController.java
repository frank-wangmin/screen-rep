package com.ysten.local.bss.statistics.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.common.web.controller.ExportController;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.domain.UserActivateSum.SyncType;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.local.bss.web.service.IStatisticsWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.page.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

@Controller
public class StatisticsController extends ExportController {
    private static Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    private static final String START = "start";
    private static final String LIMIT = "limit";

    @Autowired
    private IStatisticsWebService statisticsWebService;
    @Autowired
    private ILoggerWebService loggerWebService;
    @RequestMapping(value = "to_device_active_statistics_list")
    public String getActiveDeviceReport(ModelMap model) {
        return "/statistics/device_active_statistics";
    }
    
    @RequestMapping(value = "to_device_active_statistics_chart")
    public String getActiveDeviceChart(ModelMap model) {
        return "/statistics/device_active_statistics_chart";
    }
    @RequestMapping(value = "to_user_activate_sum_list")
    public String getActiveDeviceSum(ModelMap model) {
        return "/statistics/user_activate_day_sum";
    }

    @RequestMapping(value = "to_user_activate_statistics_list")
    public String getActiveUserReport(ModelMap model) {
        return "/statistics/user_activate_statistics";
    }
    
    @RequestMapping(value = "to_user_activate_statistics_chart")
    public String getActiveUserChart(ModelMap model) {
        return "/statistics/user_activate_statistics_chart";
    }

    @RequestMapping(value = "to_group_cust_of_device_statistics_list")
    public String getGroupOfDeviceList(ModelMap model) {
        return "/statistics/group_cust_of_device_statistics";
    }

    @RequestMapping(value = "active_device_by_city_report")
    public void getActiveDeviceByProvince(
            @RequestParam(value="activeTime",defaultValue="")String activeTime, 
            @RequestParam(value="province",defaultValue="")Long province,
            HttpServletRequest request,HttpServletResponse response){
        try{
            List<DeviceActiveStatisticsVo> list = this.statisticsWebService.getActiveDeviceReport(activeTime,province);
            RenderUtils.renderJson(JsonUtils.toJson(list), response);
        } catch (Exception e) {
            LOGGER.error("StatisticsController.getActiveDeviceByProvince exception{}", e);
        }
    }  
    
    @RequestMapping(value = "active_device_by_city_chart")
    public void getActiveDeviceChartByProvince(
            @RequestParam(value="activeTime",defaultValue="")String activeTime, 
            @RequestParam(value="province",defaultValue="")Long province,
            HttpServletRequest request,HttpServletResponse response){
        try{
            List<DeviceActiveStatisticsVo> list = this.statisticsWebService.getActiveDeviceChart(activeTime,province);
            RenderUtils.renderJson(JsonUtils.toJson(list), response);
        } catch (Exception e) {
            LOGGER.error("StatisticsController.getActiveDeviceByProvince exception{}", e);
        }
    } 

    @RequestMapping(value = "/active_device_by_city_export.json")
    public void exportActiveDeviceByProvince( 
            @RequestParam(value="activeTime",defaultValue="")String activeTime, 
            @RequestParam(value="province",defaultValue="")Long province,
            HttpServletRequest request,HttpServletResponse response) {
        try {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String fileName = "Active_Device_Statistic_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            fileName = path + "\\" + fileName + ".xls";
            File file = statisticsWebService.exportActiveDeviceByCity(activeTime, province, fileName);
            this.exportTemplate(file, response);
            file.delete();
            this.loggerWebService.saveOperateLog(Constants.DEVICE_ACTIVE_INFO_STATISTICS_MAINTAIN, "导出", "", "导出成功!", request);
        } catch (Exception e) {
        	RenderUtils.renderText("导出异常!", response);
            LOGGER.info("Export product order data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.DEVICE_ACTIVE_INFO_STATISTICS_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/active_usr_by_city_report")
    public void getActiveUserByProvince(
            @RequestParam(value="activeDate",defaultValue="")String activeDate, 
            @RequestParam(value="province",defaultValue="")Long province,
            HttpServletRequest request,HttpServletResponse response){
        try{
            List<UserActiveStatisticsVo> list = this.statisticsWebService.getActiveUserReport(activeDate, province);
            RenderUtils.renderJson(JsonUtils.toJson(list), response);
        } catch (Exception e) {
            LOGGER.error("StatisticsController getActiveUserByProvince exception{}", e);
        }
    }

    @RequestMapping(value = "/active_usr_by_city_export.json")
    public void exportActiveUserByProvince(
            @RequestParam(value="activeDate",defaultValue="")String activeDate, 
            @RequestParam(value="province",defaultValue="")Long province,
            HttpServletRequest request,HttpServletResponse response){
        try{
            String path = request.getSession().getServletContext().getRealPath("upload");
            String fileName = "Active_User_Statistic_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            fileName = path + "\\" + fileName + ".xls";
            File file = statisticsWebService.exportActiveUserReport(activeDate, province, fileName);
            this.exportTemplate(file, response);
            file.delete();            
            this.loggerWebService.saveOperateLog(Constants.USER_ACTIVE_INFO_STATISTICS_MAINTAIN, "导出", "", "导出成功!", request);
       } catch (Exception e) {
    	    RenderUtils.renderText("导出异常!", response);
            e.printStackTrace();
            LOGGER.error("StatisticsController exportActiveUserByProvince exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.USER_ACTIVE_INFO_STATISTICS_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "usr_active_sum_list.json")
    public void getActiveUserSumList(
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<UserActivateSum> pageable = this.statisticsWebService.getUserActiveSum(Integer.valueOf(pageNo),
                Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "usr_active_sum_export.json")
    public void getActiveUserSumList(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> heads = new ArrayList<String>();
            heads.add("统计日期");
            heads.add("省份");
            heads.add("地市");
            heads.add("运营商");
            heads.add("厂商");
            heads.add("终端类型");
            heads.add("是否同步");
            heads.add("同步时间");
            heads.add("当日激活终端数");
            heads.add("总激活数");
            heads.add("新增开户数");
            heads.add("总开户数");
            heads.add("退订用户数");
            heads.add("到货终端数");
            List<UserActivateSum> uass = this.statisticsWebService.getAllUserActiveSum();
            String fileName = "User_Active_Sum_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            this.export(heads, uass, excludes, UserActivateSum.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.USER_ACTIVATE_DAY_SUM, "导出", "", "导出成功!", request);
        } catch (Exception e) {
        	RenderUtils.renderText("导出异常!", response);
            LOGGER.error("Export product order data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.USER_ACTIVATE_DAY_SUM, "导出", "", "导出异常!", request);
        }
    }

    @RequestMapping(value = "usr_active_sum.json")
    public void getActiveUserSumList(@RequestParam(value = "date", defaultValue = "") String date,
            @RequestParam(value = "province", defaultValue = "") String province,
            @RequestParam(value = "sync", defaultValue = "") SyncType sync,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        if ("0".equals(province)) {
            province = null;
        }
        // if(sync.getDisplayName().isEmpty()){
        // sync = null;
        // }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!date.isEmpty()) {
            date = sdf.format(sdf.parse(date));
        }

        Pageable<UserActivateSum> pageable = this.statisticsWebService.findUserActiveSum(date, province, sync,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "group_cust_device_list.json")
    public void getGroupOfDeviceList(@RequestParam(value = "groupIp", defaultValue = "") String groupIp,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        List<CustomerCust> custList = this.statisticsWebService.getCustomerCustListByIp(groupIp);// 根据输入的集团IP查询客户
        List<CustomerGroup> custGroup = new ArrayList<CustomerGroup>();
        List<Customer> customer = new ArrayList<Customer>();
        List<DeviceCustomerAccountMap> deviceCustmerAccount = new ArrayList<DeviceCustomerAccountMap>();
        List<Device> device = new ArrayList<Device>();
        for (CustomerCust cust : custList) {
            custGroup.add(this.statisticsWebService.getCustomerGroupByGroupId(cust.getGroupId()));// 根据查询到的客户的集团号
                                                                                                  // 找到集团
            customer.addAll((this.statisticsWebService.getCustomersByCustId(cust.getCustId()))); // 根据客户ID找到用户
        }
        for (Customer customerNew : customer) {
            deviceCustmerAccount.addAll(this.statisticsWebService.getByCustomerId(customerNew.getId()));// 根据用户找到绑定关系
        }
        for (DeviceCustomerAccountMap deviceCustAccount : deviceCustmerAccount) {
            device.add(this.statisticsWebService.getDeviceById(deviceCustAccount.getDeviceId()));// 根据绑定关系
                                                                                                 // 找到设备
        }
        List<Object> data = new ArrayList<Object>();
        data.addAll(custList);
        data.addAll(custGroup);
        data.addAll(device);
        int total = device.size();
        Pageable<Object> pageable = new Pageable<Object>();
        pageable.instanceByPageNo(data, total, Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

}
