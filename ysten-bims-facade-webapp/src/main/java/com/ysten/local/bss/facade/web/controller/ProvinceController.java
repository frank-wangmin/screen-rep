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

import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.util.EntityUtils;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.data.DataUtils;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResultBean;
import com.ysten.utils.json.JsonUtils;

/**
 * 终端领用接口，省级用。
 * 
 * @author cwang
 */
@Controller
@RequestMapping(value = "/province/")
public class ProvinceController {
    @Autowired
    private IDeviceService deviceService;
    private static Logger LOGGER = LoggerFactory.getLogger(ProvinceController.class);

    @RequestMapping(value = "/receiveDevice")
    public void receiveDevice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String result = "";
        String requestStr = "";
        try {
            requestStr = DataUtils.asString(request.getInputStream(), Constant.UTF_ENCODE);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("local-bss,bind device param:" + requestStr);
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
                    boolean sync = deviceService.batchReceiveDevice(list);

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
                    "device bind failed, because param is not json"));
            RenderUtils.renderJson(result, response);
        } catch (Exception e) {
            LOGGER.error("accept request.getInputStream exception{}", e);
            result = JsonUtils.toJson(new JsonResultBean(Constant.FALSE, "device receive exception!"));
            RenderUtils.renderJson(result, response);
        }
        RenderUtils.renderJson(result, response);
    }
}
