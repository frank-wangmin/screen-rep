package com.ysten.local.bss.customer.web.controller;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.common.web.controller.ExportController;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ICustomerWebService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CustomerController extends ExportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private ICustomerWebService customerWebService;
    @Autowired
    private ILoggerWebService loggerWebService;
    private static final String TEMPLATE_FILE_NAME = "Customer_Import_Template.xls";
    private static final String START = "start";
    private static final String LIMIT = "limit";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/to_customer_list")
    public String customerList(ModelMap model) {
        return "/customer/customer_list";
    }

    @RequestMapping(value = "/to_device_bind_customer_list")
    public String bindCustomerList(ModelMap model) {
        return "/customer/bind_customer_list";
    }

    @RequestMapping(value = "/to_device_customer_map_list")
    public String remoteCustomerList(ModelMap model) {
        return "/customer/relation_customer_list";
    }

    // 用户状态
    @RequestMapping(value = "/customer_state.json")
    public void getCustomerState(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
            HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("0", "所有状态"));
        }
        for (Customer.State state : Customer.State.values()) {
            tv.add(new TextValue(state.toString(), state.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/get_customer_relate_business.json")
    public void getCustomerRelateBusinessByCode(@RequestParam(value = "customerCode", defaultValue = "") String customerCode,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {

        Map<String, Object> json = customerWebService.findCustomerRelateBusinessByCodeOrGroupId(customerCode, null);

        RenderUtils.renderJson(EnumDisplayUtil.toJson(json), response);
    }

    @RequestMapping(value = "/customer_list.json")
    public void findCustomerList(Customer customer, @RequestParam(value = "bid", defaultValue = "") String bid,
    		@RequestParam(value = "startDateExport", defaultValue = "") String startDateExport,
            @RequestParam(value = "endDateExport", defaultValue = "") String endDateExport,
            @RequestParam(value = "startDateAvtive", defaultValue = "") String startDateAvtive,
            @RequestParam(value = "endDateAvtive", defaultValue = "") String endDateAvtive,
    		@RequestParam(value = "cArea", defaultValue = "") String cArea,
            @RequestParam(value = "cRegion", defaultValue = "") String cRegion,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isNumeric(cArea) && StringUtils.isNotBlank(cArea)) {
                Area area = new Area();
                area.setId(Long.parseLong(cArea));
                customer.setArea(area);
            }
            if (StringUtils.isNumeric(cRegion) && StringUtils.isNotBlank(cRegion)) {
                City region = new City();
                region.setId(Long.parseLong(cRegion));
                customer.setRegion(region);
            }
            Map<String, Object> params = new HashMap<String, Object>(); 
            params.put("pageNo", Integer.valueOf(pageNo));
            params.put("pageSize", Integer.parseInt(pageSize));
            params.put("id", customer.getId());
            if(bid.equals("1")){
            	params.put("userIds", customer.getUserId() != null ? getListByStringSplitDot(customer.getUserId()) : "");
                params.put("codes", customer.getCode() != null ? getListByStringSplitDot(customer.getCode()) : "");
            }
            if(bid.equals("0")){
            	params.put("userId", customer.getUserId() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getUserId()) : "");
                params.put("code", customer.getCode() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getCode()) : "");
            }
            params.put("phone", customer.getPhone() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getPhone()) : "");
            params.put("loginName", customer.getLoginName() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getLoginName()) : "");
            params.put("serviceStop", customer.getServiceStop());
            params.put("state", customer.getState());
            params.put("nickName", customer.getNickName() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getNickName()) : "");
            params.put("realName", customer.getRealName() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getRealName()) : "");
            params.put("source", customer.getSource());
            params.put("rate", customer.getRate() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getRate()) : "");

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
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("endDate", StringUtils.isNotBlank(endDateExport) ? sdf.parse(endDateExport + " 23:59:59") : null);
            params.put("startDate", StringUtils.isNotBlank(startDateExport) ? sdf.parse(startDateExport + " 00:00:00")
                    : null);
            params.put("startDateAvtive",
                    StringUtils.isNotBlank(startDateAvtive) ? sdf.parse(startDateAvtive + " 00:00:00") : null);
            params.put("endDateAvtive", StringUtils.isNotBlank(endDateAvtive) ? sdf.parse(endDateAvtive + " 23:59:59")
                    : null);
            
            Pageable<Customer> pageable = this.customerWebService.findCustomersByCondition(params);
            RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
        } catch (Exception e) {
            LOGGER.error("Find customer data exception{}", e);
        }
    }
    @RequestMapping(value = "/customer_list_by_groupId.json")
     public void findCustomerListOfGroup(Customer customer,
                                         @RequestParam(value = "userGroupId", defaultValue = "") String userGroupId,
    		 @RequestParam(value = "cRegion", defaultValue = "") String cRegion,
             @RequestParam(value = "tableName", defaultValue = "") String tableName,
             @RequestParam(value = "character", defaultValue = "") String character,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        try {
        	if (StringUtils.isNumeric(cRegion) && StringUtils.isNotBlank(cRegion)) {
                City region = new City();
                region.setId(Long.parseLong(cRegion));
                customer.setRegion(region);
            }
        	customer.setGroups(userGroupId);
            Pageable<Customer> pageable = this.customerWebService.findCustomersOfGroupByCondition(tableName,character,customer,
                    Integer.valueOf(pageNo), Integer.parseInt(pageSize));
            RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
        } catch (Exception e) {
            LOGGER.error("Find customer data exception{}", e);
        }
    }
    @RequestMapping(value = "/customer_list_export_by_conditions.json")
    public void exportCustomerListByConditions(Customer customer,
    		@RequestParam(value = "uUserId", defaultValue = "") String uUserId,
    		@RequestParam(value = "uCode", defaultValue = "") String uCode,
    		@RequestParam(value = "uPhone", defaultValue = "") String uPhone,
    		@RequestParam(value = "uAreaId", defaultValue = "") String uAreaId,
            @RequestParam(value = "startDateExport", defaultValue = "") String startDateExport,
            @RequestParam(value = "endDateExport", defaultValue = "") String endDateExport,
            @RequestParam(value = "startDateAvtive", defaultValue = "") String startDateAvtive,
            @RequestParam(value = "endDateAvtive", defaultValue = "") String endDateAvtive,
            @RequestParam(value = "cArea", defaultValue = "") String cArea,
            @RequestParam(value = "cRegion", defaultValue = "") String cRegion, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNumeric(cArea) && StringUtils.isNotBlank(cArea)) {
                Area area = new Area();
                area.setId(Long.parseLong(cArea));
                customer.setArea(area);
            }
            if (StringUtils.isNumeric(cRegion) && StringUtils.isNotBlank(cRegion)) {
                City region = new City();
                region.setId(Long.parseLong(cRegion));
                customer.setRegion(region);
            }
            Map<String, Object> params = new HashMap<String, Object>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("userId", customer.getUserId() != null ? getListByStringSplitDot(customer.getUserId()) : "");
            params.put("code", customer.getCode() != null ? getListByStringSplitDot(customer.getCode()) : "");
            params.put("phone", customer.getPhone() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getPhone()) : "");
            params.put("loginName", customer.getLoginName() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getLoginName()) : "");
            params.put("state", customer.getState());
            params.put("nickName", customer.getNickName() != null ? FilterSpaceUtils.filterStartEndSpace(customer.getNickName()) : "");

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
            
            params.put("endDate", StringUtils.isNotBlank(endDateExport) ? sdf.parse(endDateExport + " 23:59:59") : null);
            params.put("startDate", StringUtils.isNotBlank(startDateExport) ? sdf.parse(startDateExport + " 00:00:00")
                    : null);
            params.put("startDateAvtive",
                    StringUtils.isNotBlank(startDateAvtive) ? sdf.parse(startDateAvtive + " 00:00:00") : null);
            params.put("endDateAvtive", StringUtils.isNotBlank(endDateAvtive) ? sdf.parse(endDateAvtive + " 23:59:59")
                    : null);
            if (StringUtils.isNotBlank(uAreaId) && !uAreaId.equals('0')) {
                params.put("areaId", uAreaId);
            } else {
                params.put("areaId", null);
            }
            params.put("uUserId", uUserId);
            params.put("uCode", uCode);
            params.put("uPhone", uPhone);
            List<Customer> customers = this.customerWebService.findExportCustomers(params);
            if (customers.size() > 20000) {
                RenderUtils.renderText("导出记录不能超过2万条!", response);
                this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "导出", "", "导出失败!查询导出记录不能超过2万条!!",
                        request);
                return;
            }
            if (customers.size() == 0) {
                RenderUtils.renderText("没有要导出的数据!", response);
                this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "导出", "", "导出失败!没有要导出记录!",
                        request);
                return;
            }
            List<String> heads = new ArrayList<String>();
            heads.add("用户编号");
            heads.add("外部用户编号");
            heads.add("用户外部编号");
            heads.add("登录名");
            heads.add("真实姓名");
            heads.add("用户昵称");
            heads.add("用户类型");
            heads.add("用户状态");
            heads.add("是否锁定");
            heads.add("客户编号");
            heads.add("所属区域");
            heads.add("所属地市");
            heads.add("创建时间");
            heads.add("激活时间");
            heads.add("服务到期时间");
            heads.add("用户销户时间");
            heads.add("更新时间");
            heads.add("状态变更时间");
            heads.add("证件类型");
            heads.add("证件编号");
            heads.add("年龄");
            heads.add("性别");
            heads.add("用户来源");
            heads.add("用户电话");
            heads.add("宽带速率");
            heads.add("电子邮件");
            heads.add("邮政编码");
            heads.add("常用地址");
//            heads.add("是否同步");
//            heads.add("用户分组");
            String fileName = "Customer_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("password");
            excludes.add("districtCode");
            excludes.add("productList");
            excludes.add("county");
            excludes.add("terminalModel");
            excludes.add("suspendedDate");
            excludes.add("profession");
            excludes.add("hobby");
            excludes.add("deviceCode");
            excludes.add("outterDeviceCode");
            excludes.add("province");
            excludes.add("verificationCode");
            excludes.add("startDate");
            excludes.add("endDate");
            excludes.add("isSync");
            excludes.add("groups");
            excludes.add("loopTime");
            this.export(heads, customers, excludes, Customer.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "导出", "", "导出成功!",
                    request);
        } catch (Exception e) {
        	RenderUtils.renderText("导出异常!", response);
            LOGGER.error("Export customer data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(),
                    request);
        }
    }
    @RequestMapping(value = "/customer_list_export.json")
    public void exportCustomerList(Customer customer,
            @RequestParam(value = "startDateExport", defaultValue = "") String startDateExport,
            @RequestParam(value = "endDateExport", defaultValue = "") String endDateExport,
            @RequestParam(value = "startDateAvtive", defaultValue = "") String startDateAvtive,
            @RequestParam(value = "endDateAvtive", defaultValue = "") String endDateAvtive,
            @RequestParam(value = "cArea", defaultValue = "") String cArea,
            @RequestParam(value = "cRegion", defaultValue = "") String cRegion, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNumeric(cArea) && StringUtils.isNotBlank(cArea)) {
                Area area = new Area();
                area.setId(Long.parseLong(cArea));
                customer.setArea(area);
            }
            if (StringUtils.isNumeric(cRegion) && StringUtils.isNotBlank(cRegion)) {
                City region = new City();
                region.setId(Long.parseLong(cRegion));
                customer.setRegion(region);
            }
            Map<String, Object> params = new HashMap<String, Object>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

            params.put("endDate", StringUtils.isNotBlank(endDateExport) ? sdf.parse(endDateExport + " 23:59:59") : null);
            params.put("startDate", StringUtils.isNotBlank(startDateExport) ? sdf.parse(startDateExport + " 00:00:00")
                    : null);
            params.put("startDateAvtive",
                    StringUtils.isNotBlank(startDateAvtive) ? sdf.parse(startDateAvtive + " 00:00:00") : null);
            params.put("endDateAvtive", StringUtils.isNotBlank(endDateAvtive) ? sdf.parse(endDateAvtive + " 23:59:59")
                    : null);
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
            List<Customer> customers = this.customerWebService.findCustomersToExport(params);
            if (customers.size() > 20000) {
                RenderUtils.renderText("导出记录不能超过2万条", response);
                return;
            }
            List<String> heads = new ArrayList<String>();
            heads.add("用户编号");
            heads.add("外部用户编号");
            heads.add("用户外部编号");
            heads.add("登录名");
            heads.add("真实姓名");
            heads.add("用户昵称");
            heads.add("用户类型");
            heads.add("用户状态");
            heads.add("是否锁定");
            heads.add("客户编号");
            heads.add("所属区域");
            heads.add("所属地市");
            heads.add("创建时间");
            heads.add("激活时间");
            heads.add("服务到期时间");
            heads.add("用户销户时间");
            heads.add("更新时间");
            heads.add("状态变更时间");
            heads.add("证件类型");
            heads.add("证件编号");
            heads.add("年龄");
            heads.add("性别");
            heads.add("用户来源");
            heads.add("用户电话");
            heads.add("宽带速率");
            heads.add("电子邮件");
            heads.add("邮政编码");
            heads.add("常用地址");
//            heads.add("是否同步");
//            heads.add("用户分组");
            String fileName = "Customer_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("password");
            excludes.add("districtCode");
            excludes.add("productList");
            excludes.add("county");
            excludes.add("terminalModel");
            excludes.add("suspendedDate");
            excludes.add("profession");
            excludes.add("hobby");
            excludes.add("deviceCode");
            excludes.add("outterDeviceCode");
            excludes.add("province");
            excludes.add("verificationCode");
            excludes.add("startDate");
            excludes.add("endDate");
            excludes.add("isSync");
            excludes.add("groups");
            excludes.add("loopTime");
            this.export(heads, customers, excludes, Customer.class, fileName, request, response);
        } catch (Exception e) {
            LOGGER.error("Export customer data exception{}", e);
        }
    }

    @RequestMapping(value = "/customer_export_byId.json")
    public void exportCustomerListByIds(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            List<Customer> customers = null;
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                customers = this.customerWebService.getCustomerByIds(idsList);
            }
            List<String> heads = new ArrayList<String>();
            heads.add("用户编号");
            heads.add("外部用户编号");
            heads.add("用户外部编号");
            heads.add("登录名");
            heads.add("真实姓名");
            heads.add("用户昵称");
            heads.add("用户类型");
            heads.add("用户状态");
            heads.add("是否锁定");
            heads.add("客户编号");
            heads.add("所属区域");
            heads.add("所属地市");
            heads.add("创建时间");
            heads.add("激活时间");
            heads.add("服务到期时间");
            heads.add("用户销户时间");
            heads.add("更新时间");
            heads.add("状态变更时间");
            heads.add("证件类型");
            heads.add("证件编号");
            heads.add("年龄");
            heads.add("性别");
            heads.add("用户来源");
            heads.add("用户电话");
            heads.add("宽带速率");
            heads.add("电子邮件");
            heads.add("邮政编码");
            heads.add("常用地址");
//            heads.add("是否同步");
//            heads.add("用户分组");
            String fileName = "Customer_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("password");
            excludes.add("districtCode");
            excludes.add("productList");
            excludes.add("county");
            excludes.add("terminalModel");
            excludes.add("suspendedDate");
            excludes.add("profession");
            excludes.add("hobby");
            excludes.add("deviceCode");
            excludes.add("outterDeviceCode");
            excludes.add("province");
            excludes.add("verificationCode");
            excludes.add("startDate");
            excludes.add("endDate");
            excludes.add("isSync");
            excludes.add("groups");
            excludes.add("loopTime");
            this.export(heads, customers, excludes, Customer.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "导出", "", "导出成功!",
                    request);
        } catch (Exception e) {
        	RenderUtils.renderText("导出异常!", response);
            LOGGER.error("Export customer data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "导出", "", "导出异常!"+e.getMessage(),
                    request);
        }
    }

    @RequestMapping(value = "/customer_export_template.json")
    public void exportDeviceTemplate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String path = request.getSession().getServletContext().getRealPath("template");
            String fileName = path + "/" + TEMPLATE_FILE_NAME;
            this.exportTemplate(fileName, response);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "下载导入模版", "", "下载成功!",
                    request);
        } catch (Exception e) {
        	RenderUtils.renderText("下载异常!", response);
            LOGGER.error("Export device data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "下载导入模版", "", "下载异常!"+e.getMessage(),
                    request);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/customer_import.json")
    public String importCustomerList(@RequestParam(value = "fileField", required = false) MultipartFile fileField,
            HttpServletRequest request, HttpServletResponse response) {
        try {

            String path = request.getSession().getServletContext().getRealPath("upload");
            String fileName = fileField.getOriginalFilename();

            File targetFile = saveFileFromInputStream(fileField.getInputStream(), path, fileName);

            Map<String, Object> map = this.customerWebService.importCustomerFile(targetFile);
            targetFile.delete();
            if ((Boolean) map.get("isSuccess")) {
                String description = "导入成功.导入数据：" + map.get("data").toString();
                this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.IMPORT, "",
                        description, request);
                return "/customer/customer_list";
            } else {
                List<String> errors = (List<String>) map.get("error");
                StringBuilder sb = new StringBuilder();
                for (String error : errors) {
                    sb.append(error + "\n");
                }
                this.loggerWebService.saveOperateLog(Constants.DEVICE_INFO_MAINTAIN, Constants.IMPORT, "",
                        fileField.getName() + "导入失败！", request);
                LOGGER.info(sb.toString());
                request.setAttribute("error", errors);
                return "/error_fileupload";
            }
        } catch (Exception e) {
            LOGGER.error("Import device data exception{}", e);
            request.getSession().setAttribute("error", e.getMessage());
            String description = "导入失败.文件名：" + fileField.getName();
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.IMPORT, "", description,
                    request);
            return "/error_fileupload";
        }
    }

    private File saveFileFromInputStream(InputStream inputStream, String path, String fileName) throws IOException {
        File _file = new File(path);
        if (!_file.exists()) {
            _file.mkdirs();
        }
        File file = new File(path + "/" + fileName);
        // if(!file.exists()){
        // file.createNewFile();
        // }
        FileOutputStream fs = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 1024];
        int byteread = 0;
        while ((byteread = inputStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
            fs.flush();
        }
        fs.close();
        inputStream.close();
        return file;
    }

    @RequestMapping(value = "/add_customer.json")
    public void addCustomerList(Customer customer, @RequestParam("cArea") String cArea,
            @RequestParam("cAge") String cAge, @RequestParam("cServiceStop") String cServiceStop,
            @RequestParam("cRegion") String cRegion, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(cAge)) {
                customer.setAge(Integer.parseInt(cAge));
            }
            if (StringUtils.isNotBlank(cArea)) {
                Area area = new Area();
                area.setId(Long.parseLong(cArea));
                customer.setArea(area);
            }
            if (StringUtils.isNotBlank(cServiceStop)) {
                customer.setServiceStop(sdf.parse(cServiceStop));
            }
            if (StringUtils.isNotBlank(cRegion)) {
                City city = new City();
                city.setId(Long.parseLong(cRegion));
                customer.setRegion(city);
            }
            String message = customerWebService.addCustomer(customer);
            String description = (Constants.SUCCESS.equals(message)) ? "新增用户信息成功" : "新增用户信息失败";
            description += "\n" + "用户外边编号：" + customer.getUserId() + "\n" + "用户密码：" + customer.getPassword() + "\n"
                    + "登录名：" + customer.getLoginName() + "\n" + "用户类型：" + customer.getCustomerType().name() + "\n"
                    + "电话号码为：" + customer.getPhone() + "\n" + "区域：" + customer.getArea() + "\n" + "所属地区为："
                    + customer.getRegion().getName() + "\n" + "绑定的设备为：" + customer.getDeviceCode();
            RenderUtils.renderText(message, response);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.ADD,
                    customer.getId() + "", description, request);
        } catch (Exception e) {
            String description = "新增用户信息失败";
            RenderUtils.renderText("系统异常，请稍后再试！", response);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.ADD, customer.getUserId(),
                    description, request);
        }
    }

    @RequestMapping(value = "/customer_to_update.json")
    public void getCustomer(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            Customer customer = this.customerWebService.getCustomerById(id);
            RenderUtils.renderJson(JsonUtils.toJson(customer), response);
        }
    }

    @RequestMapping(value = "/reset_customer_pwd.json")
    public void resetCustomerPwd(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            Customer customer = this.customerWebService.getCustomerById(id);
            String info = "密码重置成功！     ";
            if (customer != null) {
                info += "用户外部编号为"+customer.getUserId()+"的密码：由"+customer.getPassword()+"-->修改为F147DAC14E10E317234FEC29140C0519";
                customer.setPassword("F147DAC14E10E317234FEC29140C0519");
                this.customerWebService.update(customer);
                
            }
            RenderUtils.renderText(ControllerUtil.returnString(true), response);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.RESET_PWD, id + "",
                    info, request);
        }
    }

    @RequestMapping(value = "/update_customer.json")
    public void updateCustomert(Customer customer, @RequestParam("cArea") String cArea,
            @RequestParam("cAge") String cAge, @RequestParam("cId") String cId,
            @RequestParam("cServiceStop") String cServiceStop, @RequestParam("cRegion") String cRegion,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        if (StringUtils.isNotBlank(cAge))
            customer.setAge(Integer.parseInt(cAge));
        if (StringUtils.isNotBlank(cArea)) {
            Area area = new Area();
            area.setId(Long.parseLong(cArea));
            customer.setArea(area);
        }
        if (StringUtils.isNotBlank(cServiceStop)) {
            customer.setServiceStop(sdf.parse(cServiceStop));
        }
        if (StringUtils.isNotBlank(cRegion)) {
            City city = new City();
            city.setId(Long.parseLong(cRegion));
            customer.setRegion(city);
        }
        customer.setId(Long.parseLong(cId));
        // customer.setCode(this.customerWebService.getCustomerById(Long.parseLong(cId)).getCode());
       String result =  customerWebService.update(customer);

        String description = "\n" + "用户外边编号：" + customer.getUserId() + "\n" + "用户密码：" + customer.getPassword() + "\n"
                + "登录名：" + customer.getLoginName() + "\n" + "用户类型：" + customer.getCustomerType().name() + "\n"
                + "电话号码为：" + customer.getPhone() + "\n" + "区域：" + customer.getArea() + "\n" + "所属地区为："
                + customer.getRegion().getName() + "\n" + "绑定的设备为：" + customer.getDeviceCode();
        RenderUtils.renderText(result.indexOf("成功")>0 ? ControllerUtil.returnString(true) : ControllerUtil.returnString(false), response);
        this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.MODIFY, cId, result
                + description, request);
    }

    @RequestMapping(value = "/delete_customers.json")
    public void deleteCustomers(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            String message = customerWebService.delete(idsList);
            RenderUtils.renderText(message, response);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.DELETE, ids,
                    (Constants.SUCCESS.equals(message)) ? "删除用户信息成功" : "删除用户信息失败", request);
        }
    }

    @RequestMapping(value = "/lock_customers.json")
    public void lockCustomers(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            for (Long id : idsList) {
                Customer customer = customerWebService.getCustomerById(id);
                customer.setIsLock(LockType.LOCK);
                customer.setUpdateTime(new Date());
                customerWebService.update(customer);
                this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.LOCK, customer.getId()
                        + "", "用户外部编号为：" + customer.getUserId() + "加锁成功", request);
            }
            RenderUtils.renderText(ControllerUtil.returnString(true), response);
        }
    }

//    @RequestMapping(value = "/remote_customer_device.json")
//    public void remoteCustomerDevice(@RequestParam("mapIds") String mapIds, HttpServletRequest request,
//            HttpServletResponse response) {
//        if (StringUtils.isNotBlank(mapIds)) {
//            List<Long> customerDeviceIds = StringUtil.splitToLong(mapIds);
//            String message = customerWebService.remoteCustomerDevice(customerDeviceIds);
//            RenderUtils.renderText(message, response);
//            this.loggerWebService.saveOperateLog(Constants.RELATION_CUSTOMER_DEVICE_MAINTAIN, Constants.UNBIND, mapIds,
//                    "解绑成功" + message, request);
//        } else {
//            RenderUtils.renderText("未收到请求参数！", response);
//            this.loggerWebService.saveOperateLog(Constants.RELATION_CUSTOMER_DEVICE_MAINTAIN, Constants.UNBIND, mapIds,
//                    "解绑失败，未收到请求参数！", request);
//        }
//    }

    @RequestMapping(value = "/unlock_customers.json")
    public void unlockCustomers(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            for (Long id : idsList) {
                Customer customer = customerWebService.getCustomerById(id);
                customer.setIsLock(LockType.UNLOCKED);
                customer.setUpdateTime(new Date());
                customerWebService.update(customer);
                this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.UNLOCK,
                        customer.getId() + "", "用户外部编号为：" + customer.getUserId() + "解锁成功", request);

            }
            RenderUtils.renderText(ControllerUtil.returnString(true), response);
        }
    }

    @RequestMapping(value = "/bind_device.json")
    public void bindDevice(@RequestParam("customerId") String customerId, @RequestParam("deviceIds") String deviceIds,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String message = "";
            if (StringUtils.isNotBlank(customerId) && StringUtils.isNotBlank(deviceIds)) {
                message = customerWebService.bindDevice(customerId, StringUtil.splitToLong(deviceIds));
                this.loggerWebService.saveOperateLog(Constants.BIND_CUSTOMER_DEVICE_MAINTAIN, Constants.UNBIND,
                        customerId, (Constants.SUCCESS.equals(message)) ? "绑定设备成功" : "绑定设备失败" + message + ".用户ID："
                                + customerId + ";设备ID:" + deviceIds, request);
            }
            RenderUtils.renderText(message, response);
        } catch (ParseException e) {
            this.loggerWebService.saveOperateLog(Constants.BIND_CUSTOMER_DEVICE_MAINTAIN, Constants.UNBIND, customerId,
                    "绑定设备失败,系统异常，请稍后再试！" + ".用户ID：" + customerId + ";设备ID:" + deviceIds, request);
            RenderUtils.renderText("系统异常，请稍后再试！", response);
        }
    }

    @RequestMapping(value = "/can_bind_customer_list.json")
    public void unBindCustomerList(Customer customer, @RequestParam(value = "cArea", defaultValue = "") String cArea,
            @RequestParam(value = "cRegion", defaultValue = "") String cRegion,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(cArea)) {
            Area area = new Area();
            area.setId(Long.parseLong(cArea));
            customer.setArea(area);
        }
        if (StringUtils.isNotBlank(cRegion)) {
            City city = new City();
            city.setId(Long.parseLong(cRegion));
            customer.setRegion(city);
        }
        Pageable<Customer> pageable = this.customerWebService.findCanBindCustomerList(customer,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/bind_customer_list_export.json")
    public void exportBindCustomerList(Customer customer,
    		@RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "startDateAvtive", defaultValue = "") String startDateAvtive,
            @RequestParam(value = "endDateAvtive", defaultValue = "") String endDateAvtive, 
            @RequestParam(value = "cArea", defaultValue = "") String cArea,
            @RequestParam(value = "cRegion", defaultValue = "") String cRegion, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(cArea)) {
                Area area = new Area();
                area.setId(Long.parseLong(cArea));
                customer.setArea(area);
            }
            if (StringUtils.isNotBlank(cRegion)) {
                City city = new City();
                city.setId(Long.parseLong(cRegion));
                customer.setRegion(city);
            }
            List<Customer> customers = this.customerWebService.findAllCanBindCustomerList(customer);
            List<String> heads = new ArrayList<String>();
            heads.add("用户编号");
            heads.add("用户外部编号");
            heads.add("登录名");
            heads.add("真实姓名");
            heads.add("用户昵称");
            heads.add("用户类型");
            heads.add("用户状态");
            heads.add("是否锁定");
            heads.add("客户编号");
            heads.add("所属区域");
            heads.add("所属地市");
            heads.add("创建时间");
            heads.add("激活时间");
            heads.add("服务到期时间");
            heads.add("用户销户时间");
            heads.add("更新时间");
            heads.add("状态变更时间");
            heads.add("证件类型");
            heads.add("证件编号");
            heads.add("年龄");
            heads.add("性别");
            heads.add("用户来源");
            heads.add("用户电话");
            heads.add("宽带速率");
            heads.add("电子邮件");
            heads.add("邮政编码");
            heads.add("常用地址");
            heads.add("是否同步");
            heads.add("用户分组");
            String fileName = "Bind_Customer_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("id");
            excludes.add("password");
            excludes.add("productList");
            excludes.add("county");
            excludes.add("terminalModel");
            excludes.add("suspendedDate");
            excludes.add("profession");
            excludes.add("hobby");
            excludes.add("deviceCode");
            excludes.add("outterDeviceCode");
            excludes.add("province");
            excludes.add("verificationCode");
            excludes.add("startDate");
            excludes.add("endDate");
            this.export(heads, customers, excludes, Customer.class, fileName, request, response);
        } catch (Exception e) {
            LOGGER.error("Export customer device history data exception{}", e);
        }
    }

    @RequestMapping(value = "/relation_customer_list.json")
    public void findCustomerList(CustomerRelationDeviceVo customerRelationDeviceVo,
    		@RequestParam(value = "bid", defaultValue = "") String bid,
    		@RequestParam(value = "areaId", defaultValue = "") Long areaId,
            @RequestParam(value = "cityId", defaultValue = "") Long cityId,
            @RequestParam(value = Constants.DEVICE_VENDOR_ID, defaultValue = "") Long deviceVendorId,
            @RequestParam(value = Constants.DEVICE_TYPE_ID, defaultValue = "") Long deviceTypeId,
            @RequestParam(value = "dState", defaultValue = "") String dState,
            @RequestParam(value = "cState", defaultValue = "") String cState,
    		@RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "startDateAvtive", defaultValue = "") String startDateAvtive,
            @RequestParam(value = "endDateAvtive", defaultValue = "") String endDateAvtive, 
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
    	try{
    		Map<String, Object> params = new HashMap<String, Object>();
    		Long deviceVendor = deviceVendorId != null && deviceVendorId == 0 ? null : deviceVendorId;
            Long deviceType = deviceTypeId != null && deviceTypeId == 0 ? null : deviceTypeId;
            Long area = areaId != null && areaId == 0 ? null : areaId;
            Long city = cityId != null && cityId == 0 ? null : cityId;
            Device.State s = null;
            if (dState != null && !dState.isEmpty() && !"0".equals(dState)) {
                s = Device.State.valueOf(dState);
            }
            Customer.State cs = null;
            if (cState != null && !cState.isEmpty() && !"0".equals(cState)) {
                cs = Customer.State.valueOf(cState);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("endDate", StringUtils.isNotBlank(endDate) ? sdf.parse(endDate + " 23:59:59") : null);
            params.put("startDate", StringUtils.isNotBlank(startDate) ? sdf.parse(startDate + " 00:00:00") : null);
            params.put("startDateAvtive",
                    StringUtils.isNotBlank(startDateAvtive) ? sdf.parse(startDateAvtive + " 00:00:00") : null);
            params.put("endDateAvtive", StringUtils.isNotBlank(endDateAvtive) ? sdf.parse(endDateAvtive + " 23:59:59")
                    : null);
            params.put("pageNo", Integer.valueOf(pageNo));
            params.put("pageSize", Integer.parseInt(pageSize));
            params.put("ystenId", "");
            params.put("deviceCode", "");
            params.put("deviceSno", ""); 
            params.put("ystenIds", "");
            params.put("deviceCodes", "");
            params.put("deviceSnos", ""); 
            params.put("deviceVendor", deviceVendor);
            params.put("deviceType", deviceType);
            params.put("areaId", area);
            params.put("cityId", city);
            if(bid.equals("0")){
                params.put("ystenId", customerRelationDeviceVo.getYstenId() != null ? FilterSpaceUtils.filterStartEndSpace(customerRelationDeviceVo.getYstenId()) : "");
                params.put("deviceCode", customerRelationDeviceVo.getDeviceCode() != null ? FilterSpaceUtils.filterStartEndSpace(customerRelationDeviceVo.getDeviceCode()) : "");
                params.put("deviceSno", customerRelationDeviceVo.getDeviceSno() != null ? FilterSpaceUtils.filterStartEndSpace(customerRelationDeviceVo.getDeviceSno()) : "");
            }
            if(bid.equals("1")){
                params.put("ystenIds", customerRelationDeviceVo.getYstenId() != null ? this.getListByStringSplitDot(customerRelationDeviceVo.getYstenId()) : "");
                params.put("deviceCodes", customerRelationDeviceVo.getDeviceCode() != null ? this.getListByStringSplitDot(customerRelationDeviceVo.getDeviceCode()) : "");
                params.put("deviceSnos", customerRelationDeviceVo.getDeviceSno() != null ? this.getListByStringSplitDot(customerRelationDeviceVo.getDeviceSno()) : "");
            }
            params.put("customerCode", customerRelationDeviceVo.getCustomerCode() != null ? FilterSpaceUtils.filterStartEndSpace(customerRelationDeviceVo.getCustomerCode()) : "");
            params.put("customerUserId", customerRelationDeviceVo.getCustomerUserId() != null ? FilterSpaceUtils.filterStartEndSpace(customerRelationDeviceVo.getCustomerUserId()) : "");
            params.put("customerPhone", customerRelationDeviceVo.getCustomerPhone() != null ? FilterSpaceUtils.filterStartEndSpace(customerRelationDeviceVo.getCustomerPhone()) : "");
            
            if (!"0".equals(dState)) {
            	params.put("dState", s);
            }
            if (!"0".equals(cState)) {
            	params.put("cState", cs);
            }
            params.put("mac", customerRelationDeviceVo.getDeviceMac() != null ? FilterSpaceUtils.filterStartEndSpace(customerRelationDeviceVo.getDeviceMac()) : "");
            params.put("customerNickName", customerRelationDeviceVo.getCustomerNickName() != null ? FilterSpaceUtils.filterStartEndSpace(customerRelationDeviceVo.getCustomerNickName()) : "");
            params.put("customerLoginName", customerRelationDeviceVo.getCustomerLoginName() != null ? FilterSpaceUtils.filterStartEndSpace(customerRelationDeviceVo.getCustomerLoginName()) : "");
            Pageable<CustomerRelationDeviceVo> pageable = this.customerWebService.findCustomerRelationByCondition(params);
            RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    	}catch (Exception e) {
            LOGGER.error("Height | Query CustomerRelationDevice data exception{}", e);
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
    @RequestMapping(value = "/relation_customer_list_by_snos.json")
    public void findRelationCustomerList(@RequestParam(value = "snos", defaultValue = "") String snos,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
    	Pageable<CustomerRelationDeviceVo> pageable = this.customerWebService.findCustomerRelationByDeviceSno(snos,Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }
    
    @RequestMapping(value = "/relation_device_export_byId.json")
    public void exportRelationListByDeviceIds(@RequestParam(Constants.IDS) String ids,
                                     @RequestParam(value = Constants.DEVICE_CODE, defaultValue = "") String deviceCode,
                                     HttpServletRequest request, HttpServletResponse response) {
        try {
        	if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                List<CustomerRelationDeviceVo> crdvs = this.customerWebService.findRelationCustomerByDeviceIds(idsList);
                if(crdvs.size()==0){
                	RenderUtils.renderText("没有要导出的数据!", response);
                	this.loggerWebService.saveOperateLog(Constants.RELATION_CUSTOMER_DEVICE_MAINTAIN, "导出",
                            "导出关系的设备ID【"+ids+"】" ,"导出失败!没有要导出的记录!", request);
                    return;
                }
                List<String> heads = new ArrayList<String>();
                heads.add("设备编号");
                heads.add("易视腾编号");
                heads.add("设备序列号");
                heads.add("MAC地址");
                heads.add("设备厂商");
                heads.add("设备型号");
                heads.add("设备状态");
                heads.add("设备所属区域");
                heads.add("设备所属地市");
                heads.add("设备创建时间");
                heads.add("设备激活时间");
                heads.add("设备到期时间");
                heads.add("用户编号");
                heads.add("用户外部编号");
                heads.add("登录名");
                heads.add("真实姓名");
                heads.add("用户所属区域");
                heads.add("用户所属地市");
                heads.add("昵称");
                heads.add("用户状态");
                heads.add("用户类型");
                heads.add("手机号");
                String fileName = "Customer_Relation_Device_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                Vector<String> excludes = new Vector<String>();
                excludes.add("relationCreateDate");
                excludes.add("customerId");
                excludes.add("customerIdentityType");
                excludes.add("customerIdentityCode");
                excludes.add("customerSex");
                excludes.add("customerAddress");
                excludes.add("customerZipCode");
                excludes.add("deviceId");
                excludes.add("deviceIsLock");
                excludes.add("mapId");
                this.export(heads, crdvs, excludes, CustomerRelationDeviceVo.class, fileName, request, response);
                this.loggerWebService.saveOperateLog(Constants.RELATION_CUSTOMER_DEVICE_MAINTAIN, "导出",
                        "导出关系的设备ID【"+ids+"】" ,"导出成功!", request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText("导出数据异常!", response);
            LOGGER.error("Export relation device customer data exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.RELATION_CUSTOMER_DEVICE_MAINTAIN, "导出",
                    "导出关系的设备ID【"+ids+"】" ,"导出异常!"+e.getMessage(), request);
        }
    }
    
    @RequestMapping(value = "/relation_device_export_by_conditions.json")
    public void exportRelationListByConditions(
    		@RequestParam(value = "ystenValue", defaultValue = "") String ystenValue,
    	    @RequestParam(value = "deviceCodeValue", defaultValue = "") String deviceCodeValue,
    	    @RequestParam(value = "snoValue", defaultValue = "") String snoValue,
    	    @RequestParam(value = "customerCodeValue", defaultValue = "") String customerCodeValue,
    	    @RequestParam(value = "userIdValue", defaultValue = "") String userIdValue,
    	    @RequestParam(value = "phoneValue", defaultValue = "") String phoneValue,
    	    @RequestParam(value = "snoHvalue", defaultValue = "") String snoHvalue,
    	    @RequestParam(value = "deviceCodeHvalue", defaultValue = "") String deviceCodeHvalue,
    	    @RequestParam(value = "ystenIdHvalue", defaultValue = "") String ystenIdHvalue,
    	    @RequestParam(value = "deviceVendorValue", defaultValue = "") Long deviceVendorValue,
    	    @RequestParam(value = "deviceTypeValue", defaultValue = "") Long deviceTypeValue,
    	    @RequestParam(value = "areaHvalue", defaultValue = "") Long areaHvalue,
    	    @RequestParam(value = "cityValue", defaultValue = "") Long cityValue,
    	    @RequestParam(value = "macValue", defaultValue = "") String macValue,
    	    @RequestParam(value = "deviceStateValue", defaultValue = "") String deviceStateValue,
    	    @RequestParam(value = "userIdHvalue", defaultValue = "") String userIdHvalue,
    	    @RequestParam(value = "customerCodeHvalue", defaultValue = "") String customerCodeHvalue,
    	    @RequestParam(value = "phoneHvalue", defaultValue = "") String phoneHvalue, 
    	    @RequestParam(value = "nickNameValue", defaultValue = "") String nickNameValue,
    	    @RequestParam(value = "loginNameValue", defaultValue = "") String loginNameValue,   
    	    @RequestParam(value = "customerStateValue", defaultValue = "") String customerStateValue, 
    	    @RequestParam(value = "startDateValue", defaultValue = "") String startDateValue,
    	    @RequestParam(value = "endDateValue", defaultValue = "") String endDateValue,
    	    @RequestParam(value = "startDateAvtiveValue", defaultValue = "") String startDateAvtiveValue,
    	    @RequestParam(value = "endDateAvtiveValue", defaultValue = "") String endDateAvtiveValue,
    	    HttpServletRequest request,HttpServletResponse response){
    	try{
    		Map<String, Object> params = new HashMap<String, Object>();
    		Long deviceVendor = deviceVendorValue != null && deviceVendorValue == 0 ? null : deviceVendorValue;
            Long deviceType = deviceTypeValue != null && deviceTypeValue == 0 ? null : deviceTypeValue;
            Long area = areaHvalue != null && areaHvalue == 0 ? null : areaHvalue;
            Long city = cityValue != null && cityValue == 0 ? null : cityValue;
            Device.State dState = null;
            if (deviceStateValue != null && !deviceStateValue.isEmpty() && !"0".equals(deviceStateValue)) {
            	dState = Device.State.valueOf(deviceStateValue);
            }
            Customer.State cState = null;
            if (customerStateValue != null && !customerStateValue.isEmpty() && !"0".equals(customerStateValue)) {
            	cState = Customer.State.valueOf(customerStateValue);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("endDate", StringUtils.isNotBlank(endDateValue) ? sdf.parse(endDateValue + " 23:59:59") : null);
            params.put("startDate", StringUtils.isNotBlank(startDateValue) ? sdf.parse(startDateValue + " 00:00:00") : null);
            params.put("startDateAvtive",
                    StringUtils.isNotBlank(startDateAvtiveValue) ? sdf.parse(startDateAvtiveValue + " 00:00:00") : null);
            params.put("endDateAvtive", StringUtils.isNotBlank(endDateAvtiveValue) ? sdf.parse(endDateAvtiveValue + " 23:59:59")
                    : null);
            params.put("deviceVendor", deviceVendor);
            params.put("deviceType", deviceType);
            params.put("areaId", area);
            params.put("cityId", city);
            params.put("ystenId", ystenValue != null ? FilterSpaceUtils.filterStartEndSpace(ystenValue) : "");
            params.put("deviceCode", deviceCodeValue != null ? FilterSpaceUtils.filterStartEndSpace(deviceCodeValue) : "");
            params.put("deviceSno", snoValue != null ? FilterSpaceUtils.filterStartEndSpace(snoValue) : "");
            params.put("ystenIds", ystenIdHvalue != null ? this.getListByStringSplitDot(ystenIdHvalue) : "");
            params.put("deviceCodes", deviceCodeHvalue != null ? this.getListByStringSplitDot(deviceCodeHvalue) : "");
            params.put("deviceSnos", snoHvalue != null ? this.getListByStringSplitDot(snoHvalue) : "");
            params.put("customerCode", customerCodeValue != null ? FilterSpaceUtils.filterStartEndSpace(customerCodeValue) : "");
            params.put("customerUserId", userIdValue != null ? FilterSpaceUtils.filterStartEndSpace(userIdValue) : "");
            params.put("customerPhone", phoneValue != null ? FilterSpaceUtils.filterStartEndSpace(phoneValue) : "");
            params.put("customerHCode", customerCodeHvalue != null ? FilterSpaceUtils.filterStartEndSpace(customerCodeHvalue) : "");
            params.put("customerHUserId", userIdHvalue != null ? FilterSpaceUtils.filterStartEndSpace(userIdHvalue) : "");
            params.put("customerHPhone", phoneHvalue != null ? FilterSpaceUtils.filterStartEndSpace(phoneHvalue) : "");
            if (!"0".equals(deviceStateValue)) {
            	params.put("dState", dState);
            }
            if (!"0".equals(customerStateValue)) {
            	params.put("cState", cState);
            }
            params.put("mac", macValue != null ? FilterSpaceUtils.filterStartEndSpace(macValue) : "");
            params.put("customerNickName", nickNameValue != null ? FilterSpaceUtils.filterStartEndSpace(nickNameValue) : "");
            params.put("customerLoginName", loginNameValue != null ? FilterSpaceUtils.filterStartEndSpace(loginNameValue) : "");
            List<CustomerRelationDeviceVo> rList = this.customerWebService.exportRelationListByConditions(params);
            if(rList.size() == 0){
            	RenderUtils.renderText("没有要导出的数据!", response);
            	this.loggerWebService.saveOperateLog(Constants.RELATION_CUSTOMER_DEVICE_MAINTAIN, "导出",
                        "" ,"导出失败!没有要导出的记录!", request);
                return;
            }
            if(rList.size() > 20000){
            	 RenderUtils.renderText("导出记录不能超过2万条!", response);
            	 this.loggerWebService.saveOperateLog(Constants.RELATION_CUSTOMER_DEVICE_MAINTAIN, "导出",
                         "" ,"导出失败!导出记录不能超过2万条!", request);
                 return;
            }
            List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("设备序列号");
            heads.add("MAC地址");
            heads.add("设备厂商");
            heads.add("设备型号");
            heads.add("设备状态");
            heads.add("设备所属区域");
            heads.add("设备所属地市");
            heads.add("设备创建时间");
            heads.add("设备激活时间");
            heads.add("设备到期时间");
            heads.add("用户编号");
            heads.add("用户外部编号");
            heads.add("登录名");
            heads.add("真实姓名");
            heads.add("用户所属区域");
            heads.add("用户所属地市");
            heads.add("昵称");
            heads.add("用户状态");
            heads.add("用户类型");
            heads.add("手机号");
            String fileName = "Customer_Relation_Device_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("relationCreateDate");
            excludes.add("customerId");
            excludes.add("customerIdentityType");
            excludes.add("customerIdentityCode");
            excludes.add("customerSex");
            excludes.add("customerAddress");
            excludes.add("customerZipCode");
            excludes.add("deviceId");
            excludes.add("deviceIsLock");
            excludes.add("mapId");
            this.export(heads, rList, excludes, CustomerRelationDeviceVo.class, fileName, request, response);
            this.loggerWebService.saveOperateLog(Constants.RELATION_CUSTOMER_DEVICE_MAINTAIN, "导出",
                    "" ,"导出成功!", request);
    	} catch (Exception e) {
    		RenderUtils.renderText("导出异常!", response);
            LOGGER.error("Export  elations  Device  and  Customer Data By Conditions exception{}", e);
            this.loggerWebService.saveOperateLog(Constants.RELATION_CUSTOMER_DEVICE_MAINTAIN, "导出",
                    "" ,"导出异常!", request);
        }
    }

    @RequestMapping(value = "/relation_customer_list_export.json")
    public void findCustomerList(CustomerRelationDeviceVo customerRelationDeviceVo, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            List<CustomerRelationDeviceVo> crdvs = this.customerWebService
                    .findAllCustomerRelation(customerRelationDeviceVo);
            List<String> heads = new ArrayList<String>();
            heads.add("设备编号");
            heads.add("易视腾编号");
            heads.add("设备序列号");
            heads.add("MAC地址");
            heads.add("设备类型");
            heads.add("设备区域");
            heads.add("设备地市");
            heads.add("设备创建时间");
            heads.add("设备激活时间");
            heads.add("设备到期时间");
            heads.add("用户编号");
            heads.add("用户外部编号");
            heads.add("登录名");
            heads.add("真实姓名");
            String fileName = "Customer_Relation_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Vector<String> excludes = new Vector<String>();
            excludes.add("relationCreateDate");
            excludes.add("customerId");
            excludes.add("customerArea");
            excludes.add("customerCity");
            excludes.add("customerNickName");
            excludes.add("customerIdentityType");
            excludes.add("customerIdentityCode");
            excludes.add("customerPhone");
            excludes.add("customerSex");
            excludes.add("customerAddress");
            excludes.add("customerZipCode");
            excludes.add("deviceId");
            excludes.add("deviceIsLock");
            excludes.add("mapId");
            this.export(heads, crdvs, excludes, CustomerRelationDeviceVo.class, fileName, request, response);
        } catch (Exception e) {
            LOGGER.error("Export customer device history data exception{}", e);
        }
    }

    @RequestMapping(value = "/customer_device_list.json")
    public void findCustomerCanBindDeviceList(Device device,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Pageable<Device> pageable = this.customerWebService.findCustomerCanBindDeviceList(device,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }

    @RequestMapping(value = "/customer_lookpassword.json")
    public void getCustomerPassword(@RequestParam(value = "id", defaultValue = "") String id,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String message = this.customerWebService.getCustomerPassword(id);
            RenderUtils.renderJson(JsonUtils.toJson(message), response);
        } catch (Exception e) {
            RenderUtils.renderJson("查看密码失败！", response);
        }
    }

    @RequestMapping(value = "/userId_exists.json")
    public void checkUserIdExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("userId") String userId, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (userId != null && "".equals(id)) {
            Customer customer = this.customerWebService.getByUserId(userId.trim());
            if (customer != null && !customer.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        // System.out.println(str);
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/get_user_groups.json")
    public void getUserGroupsByUserId(@RequestParam(value="code",defaultValue = "") String code,
                                      HttpServletRequest request,
                                      HttpServletResponse response){
        Map<String, Object>  result = customerWebService.findUserGroupsByUserCode(code);
//        if (StringUtils.isNotBlank(result)) {
//            RenderUtils.renderText(json, response);
//        }
        RenderUtils.renderJson(EnumDisplayUtil.toJson(result), response);
    }

    @RequestMapping(value = "customers_bind_userGroup.json")
    public void bindUserGroup(@RequestParam(Constants.IDS) String ids,
            @RequestParam(value = "userGroupId4", defaultValue = "") String userGroupId4,
            @RequestParam(value = "userGroupId5", defaultValue = "") String userGroupId5,
            @RequestParam(value = "userGroupId6", defaultValue = "") String userGroupId6,
            @RequestParam(value = "userGroupId7", defaultValue = "") String userGroupId7,
            @RequestParam(value = "userGroupId8", defaultValue = "") String userGroupId8,
            @RequestParam(value = "userGroupId9", defaultValue = "") String userGroupId9, HttpServletRequest request,
            HttpServletResponse response) {
        // String message = Constants.FAILED;
        String message = Constants.SUCCESS;
        if(StringUtils.isBlank(userGroupId4) && StringUtils.isBlank(userGroupId5) && StringUtils.isBlank(userGroupId6) && StringUtils.isBlank(userGroupId7) && StringUtils.isBlank(userGroupId8)&& StringUtils.isBlank(userGroupId9)){
        	message = "<br/>至少选择一种类型的用户分组做绑定，请确认！";
        }else{
        	  List<Long> idsList = StringUtil.splitToLong(ids);
              this.customerWebService.deleteUserGroupMapByUserId(idsList);
              if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(userGroupId4)) {
                message = this.bindByGroupType(ids, userGroupId4);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(userGroupId5)) {
                message = this.bindByGroupType(ids, userGroupId5);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(userGroupId6)) {
                message = this.bindByGroupType(ids, userGroupId6);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(userGroupId7)) {
                message = this.bindByGroupType(ids, userGroupId7);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(userGroupId8)) {
                message = this.bindByGroupType(ids, userGroupId8);
            }
            if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(userGroupId9)) {
                message = this.bindByGroupType(ids, userGroupId9);
            }
        }
          
        RenderUtils.renderText(message, response);
        String info = "用户ID：" + ids + "绑定了用户分组ID：" + userGroupId4 + "," + userGroupId5 + "," + userGroupId6 + ","
                + userGroupId7 + "," + userGroupId8 + "," + userGroupId9;
        String result = (message.equals(Constants.SUCCESS)) ? "绑定分组成功！" + info : "绑定分组失败！" + message;
        this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.CUSTOMER_BIND_USERGROUP, ids,
                result, request);
    }

    @RequestMapping(value = "/customer_bind_business.json")
    public void bindBusinesses(@RequestParam(Constants.IDS) String ids,
                               @RequestParam("animation") String animationId,
                               @RequestParam("panel") String panelId,
                               @RequestParam("notice") String noticeIds,
                               @RequestParam("background") String backgroundIds,
                               HttpServletRequest request,HttpServletResponse response) {
        String message = "";
        if ( StringUtils.isBlank(backgroundIds) && StringUtils.isBlank(animationId)
                && StringUtils.isBlank(panelId) && StringUtils.isBlank(noticeIds) ) {
            message = "至少选择一种类型的业务做绑定，请确认！";
        } else {
            message = this.customerWebService.saveCustomerBusinessBind(ids, animationId, panelId, noticeIds, backgroundIds);
        }
        RenderUtils.renderText(message, response);
        String info = "用户ID：" + ids + "绑定了业务ID：" +  "," + animationId + ","
                + panelId + "," + noticeIds + "," + backgroundIds ;
        String result = message +info;
        this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "用户绑定业务", ids, result, request);
    }

    @RequestMapping(value = "/user_userGroup_map_delete")
    public void deleteDeviceGroupMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.customerWebService.deleteUserGroupMapByUserId(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "解绑分组", ids,
                        (bool == true) ? "解绑用户分组成功!" : "解绑用户分组失败!", request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText(ControllerUtil.returnString(false), response);
            LOGGER.error("Delete Unbind Group User Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "解绑分组", ids,
                   "解绑用户分组成功异常!", request);
        }
    }

    @RequestMapping(value = "/user_business_map_delete")
    public void deleteUserGroupBusinessMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.customerWebService.deleteUserBusiness(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "解绑业务", ids,
                        (bool == true) ? "解绑用户与业务关系成功!" : "解绑用户与业务关系失败!", request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText(ControllerUtil.returnString(false), response);
            LOGGER.error("Delete Unbind User Business Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, "解绑业务", ids,
                     "解绑用户与业务关系异常!"+e.getMessage(), request);
        }
    }

    public String bindByGroupType(String ids, String userGroupId) {
        String message = Constants.SUCCESS;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            message = customerWebService.bindUserGroup(idsList, userGroupId);
        }
        return message;
    }

    @RequestMapping(value = "user_sync")
    public void syncCustomer(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean bool = this.customerWebService.syncCustomer();
            int faild = this.customerWebService.getCustomerCountByIsSync();
            String description = (bool == true) ? "用户同步成功" : "用户同步失败，有" + faild + "条数据同步失败！";
            RenderUtils.renderText(description, response);
            List<Customer> customers = this.customerWebService.findAllCustomerByIsSync(null);
            StringBuilder sb = new StringBuilder("");
            if(!CollectionUtils.isEmpty(customers)){
                for(Customer customer:customers){
                    sb.append(customer.getId()).append(",");
                }
            }
            this.loggerWebService.saveOperateLog(Constants.CUSTOMER_INFO_MAINTAIN, Constants.CUSTOMER_SYNC, sb.substring(0, sb.length() - 1).toString(),
                    description, request);
        } catch (Exception e) {
            LOGGER.error("Sync customer exception{}", e);
            RenderUtils.renderText(ControllerUtil.returnString(false), response);
        }
    }
    
}
