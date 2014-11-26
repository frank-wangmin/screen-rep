//package com.ysten.local.bss.facade.web.controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;
//import java.util.Properties;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.ysten.local.bss.device.domain.Account;
//import com.ysten.local.bss.device.domain.Account.State;
//import com.ysten.local.bss.device.domain.AccountDetail;
//import com.ysten.local.bss.device.domain.Customer;
//import com.ysten.local.bss.device.service.IAccountService;
//import com.ysten.local.bss.device.service.ICustomerService;
//import com.ysten.utils.code.NumberGenerator;
//import com.ysten.utils.date.DateUtil;
//import com.ysten.utils.http.HttpUtil;
//import com.ysten.utils.http.RenderUtils;
//import com.ysten.utils.httpclient.PropertisUtil;
//import com.ysten.utils.json.JsonUtils;
//@Controller
//@RequestMapping("/sns")
//public class SnsController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SnsController.class);
//
//    private static final String CHANGE_USER_INFO_URL = "change_user_info_url";
//    
//    private static final String NO_PARAMETER = "NO";
//    private static final String USER_ID = "userId";
//    private static final String BALANCE = "balance";
//    private static final String TEXT_HTML = "text/html";
//    private static final String UTF8 = "utf-8";
//
//    private Properties urlProperties = PropertisUtil.loadProperties("url-config.properties");
//
//    @Autowired
//    private IAccountService accountService;
//    @Autowired
//    private ICustomerService customerService;
//
//    @RequestMapping("/createAccount")
//    public void createAccount (HttpServletRequest request, HttpServletResponse response){
//    	 String userId=request.getParameter("userId");
//         Account account = accountService.getAccountByCustomerId(userId);
//         if (account != null) {
//        	 LOGGER.info("创建失败：账户已存在 ID",account.getCode());
//           return;
//         }else{
//        	 account=new Account();
//        	 account.setCode(NumberGenerator.getNextCode());
//        	 account.setCustomerId(userId);
//        	 account.setBalance(0l);
//        	 account.setCreateDate(new Date());
//        	 account.setState(State.NORMAL);
//        	 accountService.createAccount(account);
//        	 LOGGER.info("创建成功：账户ID",account.getCode());
//         }
//    }
//    
//    // 余额查询接口
//    @RequestMapping("/balance/{userId}")
//    public void getBalance(@PathVariable String userId, HttpServletRequest request, HttpServletResponse response) {
//        Long balance = null;
//        try {
//            Customer customer = customerService.getCustomerByCode(userId);
//            Account account = accountService.getAccountByCustomerId(customer.getCode());
//            if (account != null) {
//                balance = account.getBalance();
//            }
//            JSONObject obj = new JSONObject();
//            obj.put(USER_ID, userId);
//            if (balance == null) {
//                obj.put(BALANCE, "");
//            } else {
//                obj.put(BALANCE, balance);
//            }
//
//            RenderUtils.renderJson(JsonUtils.toJson(obj), response);
//        } catch (Exception e) {
//            LOGGER.error("get balance exception: {}.", e);
//        }
//    }
//
//    // 余额查询接口
//    // ajax调用时增加一个随机数作为参数
//    @RequestMapping("/balance/{userId}/{random}")
//    public void getBalance(@PathVariable String userId, String random, HttpServletRequest request,
//            HttpServletResponse response) {
//        getBalance(userId, request, response);
//    }
//    
//    // 充值记录接口
//    @RequestMapping("rechargeRecord/{userId}/{startTime}/{endTime}/{randomNumber}")
//    public void getRechargeLog(@PathVariable String userId, @PathVariable String startTime,
//            @PathVariable String endTime, String randomNumber, HttpServletRequest request, HttpServletResponse response) {
//
//        Date startDate = null;
//        Date endDate = null;
//        try {
//            if (!NO_PARAMETER.equals(startTime)) {
//                startDate = DateUtil.convertStringToDate(DateUtil.getDatePattern(), startTime);
//            }
//            if (!NO_PARAMETER.equals(startTime)) {
//                endDate = DateUtil.convertStringToDate(DateUtil.getDatePattern(), endTime);
//            }
//        } catch (ParseException e) {
//            LOGGER.error("parse date exception: {}", e);
//        }
//        List<AccountDetail> rechargeLogs = this.accountService.findRechargeRecords(userId, startDate, endDate);
//        JSONArray array = new JSONArray();
//        if (rechargeLogs != null && rechargeLogs.size() > 0) {
//            for (AccountDetail log : rechargeLogs) {
//                JSONObject obj = new JSONObject();
//                obj.put("cost", log.getCost());
//                obj.put("paymentType", "");
//                obj.put("accountCode", log.getCustomerId());
//                obj.put("operateDate", DateUtil.convertDateToString(log.getOperateDate()));
//                obj.put(USER_ID, userId);
//                array.add(obj);
//            }
//        }
//        LOGGER.debug("find recharge records, return json: {}.", array.toString());
//        try {
//
//            response.setContentType(TEXT_HTML);
//            response.setCharacterEncoding(UTF8);
//            PrintWriter writer = response.getWriter();
//            writer.print(array.toString());
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            LOGGER.error(e.getMessage());
//        }
//    }
//
//    /**
//     * 修改用户密码
//     * 
//     * @param userId
//     * @param oldPassword
//     * @param newPassword
//     * @param randomNumber
//     * @param request
//     * @param response
//     */
//    @RequestMapping("/changePassword/{userId}/{oldPassword}/{newPassword}/{randomNumber}")
//    public void changeUserPassword(@PathVariable String userId, @PathVariable String oldPassword,
//            @PathVariable String newPassword, @PathVariable String randomNumber, HttpServletRequest request,
//            HttpServletResponse response) {
//        String getChangePassUrl = urlProperties.getProperty(CHANGE_USER_INFO_URL);
//        StringBuffer sb = new StringBuffer();
//        sb.append("changePassword/").append(getChangePassUrl).append("/").append(userId).append("/").append(oldPassword);
//        sb.append("/").append(newPassword).append("/").append(randomNumber);
//        try {
//            String changePasswordResult = HttpUtil.get(sb.toString());
//            RenderUtils.renderJson(JsonUtils.toJson(changePasswordResult), response);
//            LOGGER.info("change customer password, return json: {}.", changePasswordResult);
//        } catch (Exception e) {
//            LOGGER.error("io exception while change customer password: {}.", e);
//        }
//    }
//    
//    // 用户信息同步接口
//    @RequestMapping("changeUserInfo/{userId}/{loginName}/{gender}/{age}/{city}/{profession}/{randomNumber}")
//    public void changeUserInfo(@PathVariable String userId, @PathVariable String loginName,
//            @PathVariable String gender, @PathVariable String age, @PathVariable String city,
//            @PathVariable String profession, @PathVariable String randomNumber, HttpServletRequest request,
//            HttpServletResponse response) {
//        String getChangeUserInfoUrl = urlProperties.getProperty(CHANGE_USER_INFO_URL);
//        StringBuffer sb = new StringBuffer();
//        sb.append(getChangeUserInfoUrl).append("changeUserInfo/").append(userId).append("/").append(loginName);
//        sb.append("/").append(gender).append("/").append(age).append("/").append(city).append("/").append(profession).append("/").append(randomNumber);
//        try {
//            String changeUserInfoResult = HttpUtil.get(sb.toString());
//            RenderUtils.renderJson(JsonUtils.toJson(changeUserInfoResult), response);
//            LOGGER.info("change customer password, return json: {}.", changeUserInfoResult);
//        } catch (Exception e) {
//            LOGGER.error("io exception while change user info: {}.", e);
//        }
//    }
//
//}
