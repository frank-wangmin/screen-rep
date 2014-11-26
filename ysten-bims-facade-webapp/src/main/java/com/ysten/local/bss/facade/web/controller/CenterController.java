package com.ysten.local.bss.facade.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.util.EntityUtils;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.data.DataUtils;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.json.JsonUtils;

/**
 * 终端-用户信息同步接口,中心用。
 * 
 * @author cwang
 */
@Controller
@RequestMapping(value = "/center/")
public class CenterController {
    private static Logger LOGGER = LoggerFactory.getLogger(CenterController.class);

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "/sysDevice")
    public void sysDevice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String result = "";
        String requestStr = "";
        try {
            requestStr = DataUtils.asString(request.getInputStream(), Constant.UTF_ENCODE);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("sync device param:" + requestStr);
            }
            if (StringUtils.isNotBlank(requestStr)) {
                @SuppressWarnings("unchecked")
                List<Device> list = JsonUtil.getList4Json(requestStr, Device.class, null);
                JsonResultBean jsonResultBean = null;
                for (int i = 0; i < list.size(); i++) {
                    Device device = list.get(i);
                    String message = EntityUtils.checkNull(device);
                    if (message != null) {
                        jsonResultBean = new JsonResultBean(Constant.FALSE, message);
                        break;
                    }
                }
                if (jsonResultBean != null) {
                    result = JsonUtils.toJson(jsonResultBean);
                } else {
                    // 入库
                    boolean sync = deviceService.batchUpdateDevice(list);
                    if (sync) {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE, "sync device success"));
                    } else {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync device failed"));
                    }
                }
            } else {
                result = JsonUtils
                        .toJson(new JsonResultBean(Constant.FALSE, "device bind failed param has null!"));
            }
        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE,
                    "device SYS failed, because param is not json"));
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "device receive exception!"));
        }
        RenderUtils.renderJson(result, response);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/sysUser")
    public void sysCustomer(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        String requestStr = "";
        try {
            requestStr = DataUtils.asString(request.getInputStream(), Constant.UTF_ENCODE);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("sync customer param: " + requestStr);
            }
            if (StringUtils.isNotBlank(requestStr)) {
                List<Customer> list = JsonUtil.getList4Json(requestStr, Customer.class, null);
                // 数据校验
                JsonResultBean jsonResultBean = null;
                for (Customer customer : list) {
                    String message = EntityUtils.checkNull(customer);
                    if (message != null) {
                        jsonResultBean = new JsonResultBean(Constant.FALSE, message);
                        break;
                    }
                }

                if (jsonResultBean != null) {
                    result = JsonUtils.toJson(jsonResultBean);
                } else {
                    // 入库
                    boolean sync = customerService.batchSimpleSyncCustomer(list);
                    if (sync) {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.TRUE, "sync customer success"));
                    } else {
                        result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync customer failed"));
                    }
                }
            } else {
                result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE,
                        "sync customer failed, param is null!"));
            }
        } catch (JSONException jsonException) {
            LOGGER.error("accept request.getInputStream exception{}", jsonException);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE,
                    "sync customer failed, because param is not json"));
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "sync customer exception!"));
        }
        RenderUtils.renderJson(result, response);
    }

//    /**
//     * 由于历史原因ServiceStop在数据库中为data，在domain中为varchar 在这里面做适配。
//     * 
//     * @param customer
//     * @throws ParseException
//     */
//    private void parseServiceStop(Customer customer) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date ss = customer.getServiceStop();
//        if (!StringUtils.isEmpty(ss.toString())) {
//            // ss = sdf.format(sdf.parse(ss));
//            customer.setServiceStop(ss);
//        } else {
//            customer.setServiceStop(sdf.parse("2099-12-31 23:59:59"));
//        }
//    }
}
