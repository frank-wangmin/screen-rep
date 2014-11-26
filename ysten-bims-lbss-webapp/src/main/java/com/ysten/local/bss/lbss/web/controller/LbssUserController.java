package com.ysten.local.bss.lbss.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ysten.core.AppErrorCodeException;
import com.ysten.local.bss.device.api.domain.response.DefaultResponse;
import com.ysten.local.bss.device.exception.ParamIsInvalidException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonUtils;

/**
 * LBSS User 相关接口。
 * 
 * @author
 */
@Controller
@RequestMapping(value = "/stb/center")
public class LbssUserController {
    private static Logger LOGGER = LoggerFactory.getLogger(LbssUserController.class);
    @Autowired
    private ICustomerService customerService;

    private static final String RESULT_CODE_SUCCESS = "111";
    private static final String SYS_INTERVAL_ERROR = "900";
    private static final String PARAM_INVALID_ERROR = "901";

    @RequestMapping(value = "/bindLBSSUserToUserGroup.json")
    public void BindUserToUserGroup(@RequestParam(value = "productId", defaultValue = "") String productId,
                                    @RequestParam(value = "districtCode", defaultValue = "") String districtCode,
                                    @RequestParam(value = "userId", defaultValue = "") String userId,
                                    @RequestParam(value = "orderType", defaultValue = "") String orderType,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        String orderName = "User Bind UserGroup";
        if(orderType.trim().equals("1")){
            orderName = "User Bind UserGroup";
        }else{
            orderName = "User unBind UserGroup";
        }

        try {
            LOGGER.info(orderName + "：productId={},districtCode={}, userId={}, orderType={}", new Object[] { productId ,districtCode, userId, orderType});

            //参数非空校验
            if(StringUtils.isBlank(productId) || StringUtils.isBlank(districtCode) || StringUtils.isBlank(userId)  || StringUtils.isBlank(orderType)
                                              || districtCode.trim().length() != 6){
                throw new ParamIsInvalidException("There is invalid parameter!");
            }

            this.customerService.bindUserGroupByProductId(userId, productId, districtCode, orderType);

            LOGGER.debug(orderName + " Success!");
            RenderUtils.renderJson(JsonUtils.toJson(new DefaultResponse(RESULT_CODE_SUCCESS, orderName+" Success! ")), response);
        }catch(ParamIsInvalidException piee){
            LOGGER.error(orderName + "error:{}", piee.getMessage(), piee);
            RenderUtils.renderJson(JsonUtils.toJson(new DefaultResponse(PARAM_INVALID_ERROR, orderName+" error! " + piee.getMessage())), response);
        }catch(AppErrorCodeException appe){
            LOGGER.error(orderName +"error:{}",appe.getMessage(), appe);
            RenderUtils.renderJson(JsonUtils.toJson(new DefaultResponse(String.valueOf(appe.getErrorCode()), orderName+" error! " + appe.getMessage())), response);
        }catch (Exception e) {
            LOGGER.error(orderName +"error:{}", e.getMessage(), e);
            RenderUtils.renderJson(JsonUtils.toJson(new DefaultResponse(SYS_INTERVAL_ERROR, orderName+" error! " + e.getMessage())), response);
        }
    }
}
