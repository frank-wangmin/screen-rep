package com.ysten.local.bss.customer.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.CustomerCust;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.web.service.ICustomerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomerCustController {
    private static final String START = "start";
    private static final String LIMIT = "limit";
    @Autowired
    private ICustomerWebService customerWebService;

    @RequestMapping(value = "/message_list")
    public String toCustomerCustList(ModelMap model) {
        return "/customer/customer_cust_list";
    }

    @RequestMapping(value = "/customer_cust_list.json")
    public void findCustomerCustList(@RequestParam(value = "custId", defaultValue = "") String custId,
            @RequestParam(value = "custName", defaultValue = "") String custName,
            @RequestParam(value = "linkTel", defaultValue = "") String linkTel,
            @RequestParam(value = "groupIp", defaultValue = "") String groupIp,
            @RequestParam(value = "groupId", defaultValue = "") String groupId,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        map.put("custId", FilterSpaceUtils.filterStartEndSpace(custId));
        map.put("custName", FilterSpaceUtils.filterStartEndSpace(custName));
        map.put("linkTel", FilterSpaceUtils.filterStartEndSpace(linkTel));
        map.put("groupIp", FilterSpaceUtils.filterStartEndSpace(groupIp));
        map.put("groupId", FilterSpaceUtils.filterStartEndSpace(groupId));
        Pageable<CustomerCust> pageable = this.customerWebService.findCustomerCusts(map);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
    }
}
