package com.ysten.local.bss.customer.web.controller;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerEnumController {

    @RequestMapping(value = "/customer_type.json")
    public void getCustomerType(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        for (Customer.CustomerType state : Customer.CustomerType.values()) {
            tv.add(new TextValue(state.toString(), state.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/customer_sex.json")
    public void getCustomerSex(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        for (Customer.Sex state : Customer.Sex.values()) {
            tv.add(new TextValue(state.toString(), state.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/customer_identity_type.json")
    public void getCustomerIdentityType(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        for (Customer.IdentityType state : Customer.IdentityType.values()) {
            tv.add(new TextValue(state.toString(), state.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
}
