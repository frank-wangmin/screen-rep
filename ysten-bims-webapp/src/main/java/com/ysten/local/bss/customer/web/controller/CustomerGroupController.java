package com.ysten.local.bss.customer.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.CustomerGroup;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.web.service.ICustomerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonUtils;
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
public class CustomerGroupController {
    private static final String START = "start";
    private static final String LIMIT = "limit";
    @Autowired
    private ICustomerWebService customerWebService;

    @RequestMapping(value = "/sp_to_add")
    public String toCustomerGroupList(ModelMap model) {
        return "/customer/customer_group_list";
    }

    @RequestMapping(value = "/customer_group_list.json")
    public void findCustomerGroupList(@RequestParam(value = "groupId", defaultValue = "") String groupId,
            @RequestParam(value = "groupName", defaultValue = "") String groupName,
            @RequestParam(value = "linkName", defaultValue = "") String linkName,
            @RequestParam(value = "linkTel", defaultValue = "") String linkTel,
            @RequestParam(value = START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        map.put("groupId", FilterSpaceUtils.filterStartEndSpace(groupId));
        map.put("groupName", FilterSpaceUtils.filterStartEndSpace(groupName));
        map.put("linkName", FilterSpaceUtils.filterStartEndSpace(linkName));
        map.put("linkTel", FilterSpaceUtils.filterStartEndSpace(linkTel));
        Pageable<CustomerGroup> pageable = this.customerWebService.findCustomerGroups(map);
        RenderUtils.renderJson(JsonUtils.toJson(pageable), response);
    }
}
