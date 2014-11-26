//package com.ysten.iptv.bss.facade.web.controller;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.exception.ExceptionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.ysten.boss.auth.bean.TokenException;
//import com.ysten.boss.auth.bean.TokenInfoBean;
//import com.ysten.boss.auth.service.ITokenService;
//import com.ysten.boss.device.domain.Customer;
//import com.ysten.boss.device.exception.DeviceNotFoundException;
//import com.ysten.iptv.bss.billing.exception.ParametersErrorException;
//import com.ysten.iptv.bss.billing.service.IPayOrderService;
//import com.ysten.iptv.bss.billing.util.SpUtil;
//import com.ysten.iptv.bss.billing.util.SumpayUtil;
//import com.ysten.iptv.bss.billing.util.UpgUtil;
//import com.ysten.iptv.bss.facade.web.bean.ResultBean;
//import com.ysten.iptv.bss.inquiry.domain.UserPackageInfo;
//import com.ysten.iptv.bss.inquiry.service.IUserPackageService;
//import com.ysten.iptv.bss.oms.service.IOMSService;
//import com.ysten.iptv.bss.order.domain.ProductOrder;
//import com.ysten.iptv.bss.order.domain.ProductOrder.BillCycleType;
//import com.ysten.iptv.bss.order.domain.ProductOrder.State;
//import com.ysten.iptv.bss.order.domain.ThirdOrderInfo;
//import com.ysten.iptv.bss.order.service.IProductOrderService;
//import com.ysten.iptv.bss.product.domain.Product;
//import com.ysten.iptv.bss.product.domain.ProductDiscountPolicyDefine;
//import com.ysten.iptv.bss.product.domain.ProductDiscountPolicyDefine.CheckType;
//import com.ysten.iptv.bss.product.exception.ProductIsNotValidityException;
//import com.ysten.iptv.bss.product.exception.ProductNotFoundException;
//import com.ysten.iptv.bss.product.exception.ProductOrderNotFoundException;
//import com.ysten.iptv.bss.product.service.IPriceService;
//import com.ysten.iptv.bss.product.service.IProductService;
//import com.ysten.iptv.bss.util.PriceUtil;
//import com.ysten.utils.http.RenderUtils;
//
//@Controller
//public class ProductOrderPayController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderPayController.class);
//
//    private static final String UTF8 = "utf-8";
//    private static final String TEXT_XML = "text/xml";
//    private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
//    private static final String START_SERVICE = "<Service>";
//    private static final String END_SERVICE = "</Service>";
//    private static final String START_PRODUCTS = "<products>";
//    private static final String END_PRODUCTS = "</products>";
//    private static final String START_PRODUCT = "<product>";
//    private static final String END_PRODUCT = "</product>";
//    private static final String START_PRODUCTID = "<productId>";
//    private static final String END_PRODUCTID = "</productId>";
//    private static final String START_PRODUCTNAME = "<productName>";
//    private static final String END_PRODUCTNAME = "</productName>";
//    private static final String START_PRODUCTALIAS = "<productAlias>";
//    private static final String END_PRODUCTALIAS = "</productAlias>";
//    private static final String START_TYPE = "<type>";
//    private static final String END_TYPE = "</type>";
//    private static final String START_DESC = "<desc>";
//    private static final String END_DESC = "</desc>";
//    private static final String START_PRICE = "<price>";
//    private static final String END_PRICE = "</price>";
//    private static final String START_INNER = "<innerIcon>";
//    private static final String END_INNER = "</innerIcon>";
//    private static final String START_OUTER = "<outerIcon>";
//    private static final String END_OUTER = "</outerIcon>";
//    private static final String START_BEGIN_DATE = "<beginDate>";
//    private static final String END_BEGIN_DATE = "</beginDate>";
//    private static final String START_END_DATE = "<endDate>";
//    private static final String END_END_DATE = "</endDate>";
//    private static final String START_PICTURE = "<picture>";
//    private static final String END_PICTURE = "</picture>";
//    private static final String START_PRODUCT_TYPE = "<productType>";
//    private static final String END_PRODUCT_TYPE = "</productType>";
//    private static final String START_PRODUCT_DESC = "<productDesc>";
//    private static final String END_PRODUCT_DESC = "</productDesc>";
//    private static final String START_LIMIT = "<limit>";
//    private static final String END_LIMIT = "</limit>";
//    private static final String START_DISCOUNTPOLOCIES = "<discountPolicies>";
//    private static final String END_DISCOUNTPOLOCIES = "</discountPolicies>";
//    private static final String START_DISCOUNTPOLOCY = "<discountPolicy>";
//    private static final String END_DISCOUNTPOLOCY = "</discountPolicy>";
//    private static final String START_DISCOUNTPOLOCYID = "<discountPolicyId>";
//    private static final String END_DISCOUNTPOLOCYID = "</discountPolicyId>";
//    private static final String START_DISCOUNTPOLOCYNAME = "<discountPolicyName>";
//    private static final String END_DISCOUNTPOLOCYNAME = "</discountPolicyName>";
//    private static final String START_DISCOUNT = "<discount>";
//    private static final String END_DISCOUNT = "</discount>";
//    private static final String START_QUANTITY = "<quantity>";
//    private static final String END_QUANTITY = "</quantity>";
//    private static final String START_PAYPRICE = "<payPrice>";
//    private static final String END_PAYPRICE = "</payPrice>";
//
//    private static final String PRODUCTORDER_STATUS_SUCESS = "0";
//    private static final String PRODUCTORDER_STATUS_SUCESS_MSG = "成功";
//    private static final String UNTOKEN = "3"; // token 无效
//    private static final String UNTOKEN_MSG = "Token无效";
//    private static final String CUSTOMER_NOT_VALID = "1";
//    private static final String CUSTOMER_NOT_VALID_MSG = "用户未登录"; // 用户未登录
//    private static final String EMPTY_STRING = "";
//    private static final String PRODUCTORDER_STATUS_FAIL = "9";
//    private static final String PRODUCTORDER_STATUS_FAIL_MSG = "失败";
//
//    @Autowired
//    private IProductOrderService productOrderService;
//    @Autowired
//    private IPayOrderService payOrderService;
//    @Autowired
//    private IProductService productService;
//    @Autowired
//    private ITokenService tokenService;
//    @Autowired
//    private IUserPackageService userPackageService;
//    @Autowired
//    private IOMSService omsService;
//    @Autowired
//    private IPriceService priceService;
//
//    @RequestMapping("/member/orderList")
//    public void sendProductOrder(@RequestParam(value = "token", defaultValue = "") String token,
//            HttpServletRequest request, HttpServletResponse response) {
//
//        TokenInfoBean tokenInfo = null;
//        try {
//            tokenInfo = tokenService.getTokenInfo(token);
//        } catch (TokenException e) {
//            this.sendCustomerProductOrderXml(new ArrayList<ProductOrder>(), EMPTY_STRING, UNTOKEN, response);
//            LOGGER.info("token无效！");
//        } catch (DeviceNotFoundException e) {
//            this.sendCustomerProductOrderXml(new ArrayList<ProductOrder>(), EMPTY_STRING, PRODUCTORDER_STATUS_FAIL,
//                    response);
//            LOGGER.info("token中的device无效！");
//        }
//        if (tokenInfo == null) {
//            this.sendCustomerProductOrderXml(new ArrayList<ProductOrder>(), EMPTY_STRING, UNTOKEN, response);
//            LOGGER.info("token非法或超时！");
//        } else if (!tokenInfo.isCustomerOn()) {
//            this.sendCustomerProductOrderXml(new ArrayList<ProductOrder>(), EMPTY_STRING, CUSTOMER_NOT_VALID, response);
//            LOGGER.info("用户未登录！");
//        } else {
//            String customerCode = tokenInfo.getCustomerCode();
//            List<ProductOrder> productOrders = productOrderService.findProductOrderByCustomerCode(customerCode);
//            this.sendCustomerProductOrderXml(productOrders, customerCode, PRODUCTORDER_STATUS_SUCESS, response);
//        }
//
//    }
//
//    @RequestMapping("/order/save")
//    public void sendProductOrder(@RequestParam(value = "token", defaultValue = "") String token,
//            @RequestParam(value = "deviceId", defaultValue = "") String deviceId,
//            @RequestParam(value = "productId", defaultValue = "") String productId,
//            @RequestParam(value = "programSerialId", defaultValue = "") String programSerialId,
//            @RequestParam(value = "monthCount", defaultValue = "") String monthCount,
//            @RequestParam(value = "payType", defaultValue = "") String payType,
//            @RequestParam(value = "discountPolicyId", defaultValue = "") String discountPolicyId,
//            HttpServletRequest request, HttpServletResponse response) {
//
//        /** 临时方案 monthCount =1 */
//        ThirdOrderInfo thirdOrderInfo = null;
//        String programSerialName = null;
//        int buyCount = 1;
//        TokenInfoBean tokenInfo = null;
//        try {
//            tokenInfo = tokenService.getTokenInfo(token);
//            if (tokenInfo == null) {
//                thirdOrderInfo = setThirdOrderInfoStaus(thirdOrderInfo, UNTOKEN, UNTOKEN_MSG);
//                sendProductOrderXml(thirdOrderInfo, deviceId, response);
//                LOGGER.info("token非法或超时！");
//            }
//            if (!tokenInfo.isCustomerOn()) {
//                thirdOrderInfo = setThirdOrderInfoStaus(thirdOrderInfo, CUSTOMER_NOT_VALID, CUSTOMER_NOT_VALID_MSG);
//                sendProductOrderXml(thirdOrderInfo, deviceId, response);
//                LOGGER.info("用户未登录！");
//            }
//            String customerCode = tokenInfo.getCustomerCode();
//            deviceId = tokenInfo.getDeviceCode();
//            if (!StringUtils.isEmpty(programSerialId.trim())) {
//                programSerialName = omsService.getProgramSerialById(Long.parseLong(programSerialId));
//            }
//            ProductDiscountPolicyDefine pd = null;
//            if (!StringUtils.isEmpty(discountPolicyId.trim())) {
//                pd = this.priceService.getDiscountPolicyDefineById(Long.parseLong(discountPolicyId));
//                if (pd != null && !StringUtils.isEmpty(pd.getCheckPar1())
//                        && CheckType.QUANTITY.equals(pd.getCheckType())) {
//                    buyCount = Integer.parseInt(pd.getCheckPar1());
//                }
//            }
//
//            Customer customer = omsService.getCustomerByCode(customerCode);
//            String phone = null;
//            if (customer != null) {
//                phone = customer.getPhone();
//            }
//
//            thirdOrderInfo = this.payOrderService.handleProductOrder(pd, customerCode, deviceId,
//                    Long.parseLong(productId), programSerialId, programSerialName, buyCount, payType, phone, token);
//        } catch (ParametersErrorException e) {
//            LOGGER.error(e.getMessage(), e);
//            thirdOrderInfo = setThirdOrderInfoStaus(thirdOrderInfo, PRODUCTORDER_STATUS_FAIL,
//                    PRODUCTORDER_STATUS_FAIL_MSG);
//        } catch (ParseException e) {
//            LOGGER.error(e.getMessage(), e);
//            thirdOrderInfo = setThirdOrderInfoStaus(thirdOrderInfo, PRODUCTORDER_STATUS_FAIL,
//                    PRODUCTORDER_STATUS_FAIL_MSG);
//        } catch (TokenException e) {
//            LOGGER.warn("token非法或超时！");
//            thirdOrderInfo = setThirdOrderInfoStaus(thirdOrderInfo, UNTOKEN, UNTOKEN_MSG);
//        } catch (DeviceNotFoundException e) {
//            LOGGER.warn("token中的device无效！");
//            thirdOrderInfo = setThirdOrderInfoStaus(thirdOrderInfo, PRODUCTORDER_STATUS_FAIL,
//                    PRODUCTORDER_STATUS_FAIL_MSG);
//        }
//        sendProductOrderXml(thirdOrderInfo, deviceId, response);
//    }
//
//    @RequestMapping(value = "/sp/payshow")
//    public String spShow(@RequestParam(value = "code") String orderCode, @RequestParam(value = "token") String token,
//            @RequestParam(value = "json", defaultValue = "") String json, ModelMap model) {
//
//        if ("true".equals(json)) {
//            model.addAttribute("json", "true");
//        }
//
//        String error = null;
//        TokenInfoBean tokenInfo = null;
//        try {
//
//            tokenInfo = tokenService.getTokenInfo(token);
//            if (tokenInfo == null) {
//                error = "登录异常！";
//            }
//            if (!tokenInfo.isCustomerOn()) {
//                error = "用户未登录！";
//            }
//
//            ProductOrder order = this.productOrderService.getProductOrderByCode(orderCode);
//            if (order == null || !order.getCustomerCode().equals(tokenInfo.getCustomerCode())) {
//                error = "订单未找到！";
//            } else {
//                if (State.PAID.equals(order.getState())) {
//                    model.addAttribute("order", order);
//                    model.addAttribute("success", "true");
//                } else if (State.PEND.equals(order.getState())) {
//                    model.addAttribute("order", order);
//                } else {
//                    model.addAttribute("error", "订单已过期。");
//                }
//            }
//        } catch (TokenException e) {
//            error = "登录异常！";
//            LOGGER.warn("token非法或超时！", e);
//        } catch (DeviceNotFoundException e) {
//            error = "登录异常！";
//            LOGGER.warn("token中的device无效！", e);
//        }
//
//        if (error != null) {
//            model.addAttribute("error", error);
//        }
//
//        return "spshow";
//    }
//
//    /**
//     * 
//     * @param merId
//     * @param goodsId
//     * @param orderId
//     * @param merDate
//     * @param payDate
//     * @param amount
//     * @param amtType
//     * @param bankType
//     * @param mobileId
//     * @param transType
//     * @param settleDate
//     * @param merPriv
//     * @param retCode
//     * @param version
//     * @param sign
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "/sp/save")
//    public String spSave(@RequestParam(value = "merId") String merId, @RequestParam(value = "goodsId") String goodsId,
//            @RequestParam(value = "goodsInf") String goodsInf, @RequestParam(value = "mobileId") String mobileId,
//            @RequestParam(value = "amtType") String amtType, @RequestParam(value = "bankType") String bankType,
//            @RequestParam(value = "version") String version, @RequestParam(value = "sign") String sign, ModelMap model) {
//
//        // TODO 校验是不是对方。
//        if (SpUtil.verify(
//                SpUtil.getSpSaveStringForSign(merId, goodsId, goodsInf, mobileId, amtType, bankType, version), sign)) {
//            // TODO goodsInf扩展信息从哪来。
//            ProductOrder order = this.productOrderService.getProductOrderByCode(goodsInf);
//            model.addAttribute("spString", SpUtil.getSpSaveString(order));
//        }
//
//        return "sp";
//    }
//
//    @RequestMapping(value = "/sp/pay")
//    public String spPay(@RequestParam(value = "merId") String merId, @RequestParam(value = "goodsId") String goodsId,
//            @RequestParam(value = "orderId") String orderId, @RequestParam(value = "merDate") String merDate,
//            @RequestParam(value = "payDate") String payDate, @RequestParam(value = "amount") String amount,
//            @RequestParam(value = "amtType") String amtType, @RequestParam(value = "bankType") String bankType,
//            @RequestParam(value = "mobileId") String mobileId, @RequestParam(value = "transType") String transType,
//            @RequestParam(value = "settleDate") String settleDate, @RequestParam(value = "merPriv") String merPriv,
//            @RequestParam(value = "retCode") String retCode, @RequestParam(value = "version") String version,
//            @RequestParam(value = "sign") String sign, ModelMap model) {
//        try {
//            // 组合url准备校验
//            String dataString = SpUtil.getSpPayStringForSign(merId, goodsId, orderId, merDate, payDate, amount,
//                    amtType, bankType, mobileId, transType, settleDate, merPriv, retCode, version);
//
//            if (SpUtil.verify(dataString, sign)) {
//                // 订单成功业务逻辑处理
//                ProductOrder order = this.payOrderService.callbackProcessProductOrder(orderId, "", BillCycleType.SP);
//
//                model.addAttribute("spString", SpUtil.getSpPayString(order));
//            }
//
//        } catch (Exception e) {
//            LOGGER.error("EncryptionUtil occur exception in suma pay {} ", e);
//            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
//        }
//
//        return "sp";
//    }
//
//    /**
//	 * UPG支付成功回调接口
//	 * 
//	 * @param charger 帐务方编码
//	 * @param customerCode 客户编号
//	 * @param orderCode 订单号
//	 * @param payId 流水号
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "/upg/pay")
//    public void upgPay(@RequestParam(value = "orderCode", defaultValue = "") String orderCode,
//    		@RequestParam(value = "payId", defaultValue = "") String payId, HttpServletRequest request, HttpServletResponse response) {
//    	
//		try {
//			//验证参数是否为空
//			if (UpgUtil.verify(payId,orderCode)) { 
//				// 订单成功业务逻辑处理
//	            this.payOrderService.callbackProcessProductOrder(orderCode, payId, BillCycleType.UPG);
//	            RenderUtils.renderHtml("UPG回调接口正常", response);
//			}else {
//				RenderUtils.renderHtml("UPG回调接口参数异常", response);
//			}
//		} catch (Exception e) {
//			LOGGER.error("Exception in upg pay {} ", e);
//            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
//            RenderUtils.renderHtml("UPG回调接口异常"+e, response);
//		}
//    }
//    /**
//     * 数码视讯支付成功回调接口
//     * 
//     * @param requestId
//     *            外部商户系统发起支付请求时的请求流水号
//     * @param payId
//     *            支付系统生产的交易流水号
//     * @param fiscalDate
//     *            支付系统的会计日期，格式为yyyyMMdd
//     * @param description
//     *            辅助信息，支付系统透传给业务系统
//     * @param resultSignature
//     *            支付系统使用商户的密钥对结果数据进行签名，商户可以根据此签名校验结果数据是否合法
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "/sumapay/pay")
//    public void sumaPay(@RequestParam(value = "requestId") String requestId,
//            @RequestParam(value = "payId") String payId, @RequestParam(value = "fiscalDate") String fiscalDate,
//            @RequestParam(value = "description") String description,
//            @RequestParam(value = "resultSignature") String resultSignature, HttpServletRequest request,
//            HttpServletResponse response) {
//        try {
//            String dataString = requestId + payId + fiscalDate + description;
//            // 验证签名是否正确
//            if (SumpayUtil.verify(dataString, resultSignature)) {
//                // 订单成功业务逻辑处理
//                this.payOrderService.callbackProcessProductOrder(requestId, payId, BillCycleType.SUMAPAY);
//            }
//        } catch (Exception e) {
//            LOGGER.error("EncryptionUtil occur exception in suma pay {} ", e);
//            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
//        }
//        RenderUtils.renderHtml("数码视讯支付成功回调接口正常", response);
//    }
//
//    /**
//     * ysten测试支付功能跳转到空页面，停留5秒钟关闭页面，显示支付成功
//     * 
//     * @param request
//     * @param response
//     * @throws ParseException
//     * @throws ProductOrderNotFoundException
//     * @throws ProductIsNotValidityException
//     * @throws ProductNotFoundException
//     */
//    @RequestMapping(value = "/test/testSuccessActionUrl")
//    public void testActionUrl(@RequestParam(value = "orderCode", defaultValue = "") String orderCode,
//            HttpServletRequest request, HttpServletResponse response) throws ParametersErrorException, ParseException {
//        this.payOrderService.callbackYstenTestProcessProductOrder(orderCode);
//    }
//
//    private static ThirdOrderInfo setThirdOrderInfoStaus(ThirdOrderInfo thirdOrderInfo, String status, String msg) {
//        thirdOrderInfo = new ThirdOrderInfo();
//        thirdOrderInfo.setStatus(status);
//        thirdOrderInfo.setMsg(msg);
//        return thirdOrderInfo;
//    }
//
//    public static void sendProductOrderXml(ThirdOrderInfo thirdOrderInfo, String deviceCode,
//            HttpServletResponse response) {
//        StringBuffer returnXML = new StringBuffer();
//        returnXML.append(XML_HEAD);
//        returnXML.append("<Service id=\"sendProductOrder\">");
//        returnXML.append("<state>").append(thirdOrderInfo.getStatus()).append("</state>");
//        returnXML.append("<msg>").append(thirdOrderInfo.getMsg()).append("</msg>");
//        returnXML.append("<device_id>").append(deviceCode).append("</device_id>");
//        returnXML.append("<pay_price>").append(thirdOrderInfo.getBillAmt() == null ? "0" : thirdOrderInfo.getBillAmt())
//                .append("</pay_price>");
//        // returnXML.append("<actionUrl><![CDATA[").append("http://127.0.0.1:8080/yst-epg-facade/test_pay_redirect.jsp?actionUrl=").append(thirdOrderInfo.getActionUrl()).append("]]></actionUrl>");
//        returnXML.append("<actionUrl><![CDATA[")
//                .append(thirdOrderInfo.getActionUrl() == null ? "" : thirdOrderInfo.getActionUrl())
//                .append("]]></actionUrl>");
//        returnXML.append(END_SERVICE);
//        LOGGER.debug("get third pay redirect url: {}.", returnXML.toString());
//        try {
//            byte[] xmlMsg = returnXML.toString().getBytes(UTF8);
//            response.setContentType(TEXT_XML);
//            response.setContentLength(xmlMsg.length);
//            ServletOutputStream os = response.getOutputStream();
//            os.write(xmlMsg);
//            os.flush();
//            os.close();
//        } catch (IOException e) {
//            LOGGER.error("write stb redirect url error: {}.", e);
//        }
//    }
//
//    public void sendCustomerProductOrderXml(List<ProductOrder> productOrders, String customerId, String status,
//            HttpServletResponse response) {
//        StringBuffer returnXML = new StringBuffer();
//        returnXML.append(XML_HEAD);
//        returnXML.append(START_SERVICE);
//        returnXML.append("<state>" + status + "</state>");
//        returnXML.append("<customerId>" + customerId + "</customerId>");
//        returnXML.append("<totalcount>" + productOrders.size() + "</totalcount>");
//        returnXML.append(START_PRODUCTS);
//        for (int i = 0; i < productOrders.size(); i++) {
//            ProductOrder productOrder = productOrders.get(i);
//            returnXML.append(START_PRODUCT);
//            returnXML.append("<orderId>" + productOrder.getId() + "</orderId>");
//            returnXML.append("<productId>" + productOrder.getProductId() + "</productId>");
//            returnXML.append("<productName>" + productOrder.getProductName() + "</productName>");
//            returnXML.append("<createDate>" + getFormatDate(productOrder.getStartDate()) + "</createDate>");
//            returnXML.append("<payType>" + productOrder.getBillCycleType().getDisplayName() + "</payType>");
//            returnXML.append("<price>" + Double.valueOf(productOrder.getPayPrice()) / 100 + "</price>");
//            returnXML.append("<type>" + productOrder.getProductType() + "</type>");
//            String outterCode = productOrder.getOutterCode();
//            String contentName = productOrder.getContentName();
//            if (outterCode == null) {
//                outterCode = "";
//            }
//            if (contentName == null) {
//                contentName = "";
//            }
//            returnXML.append("<programSerialId>" + outterCode + "</programSerialId>");
//            returnXML.append("<programSerialName>" + contentName + "</programSerialName>");
//            returnXML.append("<count>" + productOrder.getQuantity() + "</count>");
//            returnXML.append(END_PRODUCT);
//        }
//        returnXML.append(END_PRODUCTS);
//        returnXML.append(END_SERVICE);
//        try {
//            byte[] xmlMsg = returnXML.toString().getBytes(UTF8);
//            response.setContentType(TEXT_XML);
//            response.setContentLength(xmlMsg.length);
//            ServletOutputStream os = response.getOutputStream();
//            os.write(xmlMsg);
//            os.flush();
//            os.close();
//        } catch (IOException e) {
//            LOGGER.error("write stb billcode error: {}.", e);
//        }
//
//    }
//
//    public static String getFormatDate(Date date) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        if (date == null) {
//            return "";
//        }
//        return df.format(date);
//
//    }
//
//    // 服务有效期接口
//    @RequestMapping("/comsumOrdeRefective")
//    public void sendComsumOrdeReffective(@RequestParam(value = "token", defaultValue = "") String token,
//            HttpServletRequest request, HttpServletResponse response) {
//        ResultBean resultBean = this.checkToken(token);
//        StringBuffer returnXML = new StringBuffer();
//        returnXML.append(XML_HEAD);
//        returnXML.append(START_SERVICE);
//        if (resultBean.isValid()) {
//            String customerCode = resultBean.getCustomerCode();
//            List<UserPackageInfo> userPackageInfos = this.userPackageService.findByCustomerId(customerCode);
//            returnXML.append("<state>" + resultBean.getState() + "</state>");
//            returnXML.append("<customerId>").append(customerCode).append("</customerId>");
//            returnXML.append("<totalcount>").append(userPackageInfos.size()).append("</totalcount>");
//            returnXML.append(START_PRODUCTS);
//            if (userPackageInfos != null && userPackageInfos.size() != 0) {
//                for (int i = 0; i < userPackageInfos.size(); i++) {
//                    setPackage(userPackageInfos.get(i), returnXML);
//                }
//            }
//            returnXML.append(END_PRODUCTS);
//        } else {
//            returnXML.append("<state>" + resultBean.getState() + "</state>");
//            returnXML.append("<customerId>").append("</customerId>");
//            returnXML.append("<totalcount>").append("</totalcount>");
//            returnXML.append(START_PRODUCTS);
//            returnXML.append(END_PRODUCTS);
//        }
//        returnXML.append(END_SERVICE);
//        try {
//            byte[] xmlMsg = returnXML.toString().getBytes(UTF8);
//            response.setContentType(TEXT_XML);
//            response.setContentLength(xmlMsg.length);
//            ServletOutputStream os = response.getOutputStream();
//            os.write(xmlMsg);
//            os.flush();
//            os.close();
//        } catch (IOException e) {
//            LOGGER.error("write stb billcode error: {}.", e);
//        }
//    }
//
//    private void setPackage(UserPackageInfo userPackageInfo, StringBuffer returnXML) {
//        returnXML.append(START_PRODUCT);
//        returnXML.append("<productId>").append(userPackageInfo.getProduct().getId()).append("</productId>");
//        Product product = productService.findProductById(userPackageInfo.getProduct().getId());
//        if (product == null) {
//            returnXML.append("<productName>").append("</productName>");
//            returnXML.append(START_PICTURE).append(END_PICTURE);
//        } else {
//            returnXML.append("<productName>").append(product.getName()).append("</productName>");
//            returnXML.append(START_PICTURE).append(product.getLightPicture()).append(END_PICTURE);
//        }
//        returnXML.append("<startDate>").append(getFormatDate(userPackageInfo.getStartDate())).append("</startDate>");
//        returnXML.append("<endDate>").append(getFormatDate(userPackageInfo.getEndDate())).append("</endDate>");
//        returnXML.append("<type>").append(userPackageInfo.getProductType()).append("</type>");
//        returnXML
//                .append("<programSerialId>")
//                .append(userPackageInfo.getOutterCode() == null || "".equals(userPackageInfo.getOutterCode()) ? ""
//                        : userPackageInfo.getOutterCode()).append("</programSerialId>");
//        returnXML
//                .append("<programSerialName>")
//                .append(userPackageInfo.getContentName() == null || "".equals(userPackageInfo.getContentName()) ? ""
//                        : userPackageInfo.getContentName()).append("</programSerialName>");
//        returnXML.append(END_PRODUCT);
//    }
//
//    private ResultBean checkToken(String token) {
//        ResultBean resultBean = new ResultBean();
//        TokenInfoBean tokenInfo = null;
//        try {
//            tokenInfo = tokenService.getTokenInfo(token);
//        } catch (TokenException e) {
//            resultBean.setState(UNTOKEN);
//            resultBean.setMessage(UNTOKEN_MSG);
//            resultBean.setValid(false);
//            LOGGER.warn("获取customer异常:{}", e);
//        } catch (DeviceNotFoundException e) {
//            resultBean.setState(PRODUCTORDER_STATUS_FAIL);
//            resultBean.setMessage(PRODUCTORDER_STATUS_FAIL_MSG);
//            resultBean.setValid(false);
//            LOGGER.warn("获取customer异常:{}", e);
//        }
//        if (tokenInfo == null) {
//            resultBean.setState(UNTOKEN);
//            resultBean.setMessage(UNTOKEN_MSG);
//            resultBean.setValid(false);
//        } else if (!tokenInfo.isCustomerOn()) {
//            resultBean.setState(CUSTOMER_NOT_VALID);
//            resultBean.setMessage(CUSTOMER_NOT_VALID_MSG);
//            resultBean.setValid(false);
//        } else {
//            resultBean.setCustomerCode(tokenInfo.getCustomerCode());
//            resultBean.setState(PRODUCTORDER_STATUS_SUCESS);
//            resultBean.setMessage(PRODUCTORDER_STATUS_SUCESS_MSG);
//            resultBean.setValid(true);
//        }
//        return resultBean;
//    }
//
//    /**
//     * 获取所有有效产品包
//     */
//    @RequestMapping(value = "/getProducts")
//    public void findAllProduct(HttpServletRequest request, HttpServletResponse response) {
//        List<Product> products = new ArrayList<Product>();
//        products = this.productService.findEnableProduct();
//        RenderUtils.renderXML(this.getProductXml(products), response);
//    }
//
//    private String getProductXml(List<Product> products) {
//        StringBuffer xmlData = new StringBuffer();
//        xmlData.append(XML_HEAD);
//        xmlData.append(START_SERVICE);
//        xmlData.append(START_PRODUCTS);
//        if (products == null || products.size() == 0) {
//            xmlData.append(END_PRODUCTS);
//            return xmlData.toString();
//        }
//        for (int i = 0; i < products.size(); i++) {
//            xmlData.append(START_PRODUCT);
//            xmlData.append(START_PRODUCTID).append(products.get(i).getId()).append(END_PRODUCTID);
//            xmlData.append(START_PRODUCTNAME).append(products.get(i).getName()).append(END_PRODUCTNAME);
//            xmlData.append(START_PRODUCTALIAS).append(products.get(i).getAlias()).append(END_PRODUCTALIAS);
//            xmlData.append(START_TYPE).append(products.get(i).getProductType().getDisplayName()).append(END_TYPE);
//            xmlData.append(START_DESC).append(products.get(i).getDescription()).append(END_DESC);
//            xmlData.append(START_PRICE).append(products.get(i).getBasePrice().getPrice()).append(END_PRICE);
//            xmlData.append(START_PAYPRICE)
//            // .append(PriceUtil.getPayPrice(products.get(i).getBasePrice().getPrice(),
//            // Integer.parseInt(pdpd.get(i).getCheckPar1()), pdpd.get(i))
//                    .append(END_PAYPRICE);
//            xmlData.append(START_INNER).append(products.get(i).getInnerIcon()).append(END_INNER);
//            xmlData.append(START_OUTER).append(products.get(i).getOuterIcon()).append(END_OUTER);
//            xmlData.append(START_BEGIN_DATE).append(products.get(i).getStartDate()).append(END_BEGIN_DATE);
//            xmlData.append(START_END_DATE).append(products.get(i).getEndDate()).append(END_END_DATE);
//            xmlData.append(END_PRODUCT);
//        }
//        xmlData.append(END_PRODUCTS);
//        xmlData.append(END_SERVICE);
//
//        return xmlData.toString();
//    }
//
//    /**
//     * 获取单个产品包详情
//     * 
//     * @param token
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "/getProductInfo")
//    public void getProductInfo(@RequestParam(value = "token", defaultValue = "") String token,
//            @RequestParam(value = "productId", defaultValue = "") String productId, HttpServletRequest request,
//            HttpServletResponse response) {
//        ResultBean resultBean = this.checkToken(token);
//        StringBuffer returnXML = new StringBuffer();
//        if (!resultBean.isValid()) {
//            String resutl = this.infoFailedMessage(returnXML, resultBean.getState(), resultBean.getMessage());
//            RenderUtils.renderXML(resutl, response);
//            return;
//        }
//        if (productId == null || "".equals(productId)) {
//            String resutl = this.infoFailedMessage(returnXML, resultBean.getState(), "NULL_productId");
//            RenderUtils.renderXML(resutl, response);
//            return;
//        }
//        Product product = this.productService.findProductById(Long.parseLong(productId));
//        if (product == null) {
//            String resutl = this.infoFailedMessage(returnXML, resultBean.getState(), "ERROR_productId");
//            RenderUtils.renderXML(resutl, response);
//            return;
//        }
//        List<ProductDiscountPolicyDefine> pdpd = this.priceService.findProductDiscountPolicyDefineByGroupId(product
//                .getDiscountPolicyGroup().getId());
//        String resutl = this.infoSucceedMessage(returnXML, resultBean.getState(), resultBean.getMessage(), product,
//                pdpd);
//        RenderUtils.renderXML(resutl, response);
//    }
//
//    private String infoFailedMessage(StringBuffer returnXML, String state, String message) {
//        returnXML.append(XML_HEAD);
//        returnXML.append(START_SERVICE);
//        returnXML.append("<state>" + state + "</state>");
//        returnXML.append("<message>" + message + "</message>");
//        returnXML.append(START_PRODUCTID).append(END_PRODUCTID);
//        returnXML.append(START_PRODUCTNAME).append(END_PRODUCTNAME);
//        returnXML.append(START_PRODUCTALIAS).append(END_PRODUCTALIAS);
//        returnXML.append(START_PRODUCT_TYPE).append(END_PRODUCT_TYPE);
//        returnXML.append(START_PRODUCT_DESC).append(END_PRODUCT_DESC);
//        returnXML.append(START_DISCOUNTPOLOCIES).append(END_DISCOUNTPOLOCIES);
//        returnXML.append(END_SERVICE);
//        return returnXML.toString();
//    }
//
//    private String infoSucceedMessage(StringBuffer returnXML, String state, String message, Product product,
//            List<ProductDiscountPolicyDefine> pdpd) {
//        returnXML.append(XML_HEAD);
//        returnXML.append(START_SERVICE);
//        returnXML.append("<state>" + state + "</state>");
//        returnXML.append("<message>" + message + "</message>");
//        returnXML.append(START_PRODUCTID + product.getId() + END_PRODUCTID);
//        returnXML.append(START_PRODUCTNAME + product.getName() + END_PRODUCTNAME);
//        returnXML.append(START_PRODUCTALIAS + product.getAlias() + END_PRODUCTALIAS);
//        returnXML.append(START_PRODUCT_TYPE + product.getProductType().getDisplayName() + END_PRODUCT_TYPE);
//        returnXML.append(START_PRODUCT_DESC + product.getDescription() + END_PRODUCT_DESC);
//        returnXML.append(START_LIMIT + product.getLimitNum() + END_LIMIT);
//        returnXML.append(START_DISCOUNTPOLOCIES);
//        for (int i = 0; i < pdpd.size(); i++) {
//            returnXML.append(START_DISCOUNTPOLOCY);
//            returnXML.append(START_DISCOUNTPOLOCYID + pdpd.get(i).getId() + END_DISCOUNTPOLOCYID);
//            returnXML.append(START_DISCOUNTPOLOCYNAME + pdpd.get(i).getName() + END_DISCOUNTPOLOCYNAME);
//            returnXML.append(START_TYPE + pdpd.get(i).getName() + END_TYPE);
//            returnXML.append(START_DISCOUNT + pdpd.get(i).getDiscountPar1() + END_DISCOUNT);
//            returnXML.append(START_QUANTITY + pdpd.get(i).getCheckPar1() + END_QUANTITY);
//            returnXML.append(START_PRICE + Integer.parseInt(pdpd.get(i).getCheckPar1())
//                    * product.getBasePrice().getPrice() + END_PRICE);
//            returnXML.append(START_PAYPRICE
//                    + PriceUtil.getPayPrice(product.getBasePrice().getPrice(),
//                            Integer.parseInt(pdpd.get(i).getCheckPar1()), pdpd.get(i)) + END_PAYPRICE);
//            returnXML.append(START_DESC + pdpd.get(i).getDescription() + END_DESC);
//            returnXML.append(START_INNER + pdpd.get(i).getInnerIcon() + END_INNER);
//            returnXML.append(START_OUTER + pdpd.get(i).getOuterIcon() + END_OUTER);
//            returnXML.append(END_DISCOUNTPOLOCY);
//        }
//        returnXML.append(END_DISCOUNTPOLOCIES);
//        returnXML.append(END_SERVICE);
//        return returnXML.toString();
//    }
//
//}
