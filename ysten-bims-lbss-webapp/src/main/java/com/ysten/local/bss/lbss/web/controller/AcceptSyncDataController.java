package com.ysten.local.bss.lbss.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ysten.core.AppErrorCodeException;
import com.ysten.local.bss.bean.tb.SyncDataByDevice;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.service.ISyncLbssDataToBimsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ysten.local.bss.device.api.domain.response.DefaultResponse;
import com.ysten.local.bss.device.exception.ParamIsEmptyException;
import com.ysten.local.bss.device.exception.ParamIsInvalidException;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.data.DataUtils;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonUtils;

import java.util.List;

@Controller
@RequestMapping(value = "/stb/center")
public class AcceptSyncDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptSyncDataController.class);

    @Autowired
    private ISyncLbssDataToBimsService syncLbssDataToBimsService;

    private static final String RESULT_CODE_SUCCESS = "111";
    private static final String SYS_INTERVAL_ERROR = "900";
    private static final String PARAM_INVALID_ERROR = "901";
    private static final String PARAM_EMPTY_ERROR = "911";

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	/**
	 * 接收上传的要同步的数据
	 * @param
	 * @param response
	 */
	@RequestMapping(value = "/syncLBSSDeviceUserMapToBIMS")
	public void acceptDataToCenter(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonData = DataUtils.asString(request.getInputStream(), Constant.UTF_ENCODE);
			LOGGER.info("Accept Lbss DeviceUser Data: jsonData=" + jsonData);

			if(StringUtils.isBlank(jsonData)){
                throw new ParamIsEmptyException("Accept Json Data is null! jsonData=" + jsonData);
			}

            List<SyncDataByDevice> syncDataList = gson.fromJson(jsonData, new TypeToken<List<SyncDataByDevice>>() {}.getType());
            if (CollectionUtils.isEmpty(syncDataList)){
                throw new ParamIsInvalidException("syncDataList is null! syncDataList=" + syncDataList);
            }

            this.syncLbssDataToBimsService.saveSyncData(syncDataList);

            LOGGER.info("Accept Lbss DeviceUser Data Success!");
			RenderUtils.renderJson(JsonUtils.toJson(new DefaultResponse(RESULT_CODE_SUCCESS, "Accept Lbss DeviceUser Data Success!")), response);

        }catch(ParamIsEmptyException piee){
            LOGGER.error("Accept Lbss DeviceUser Data error:{}", piee.getMessage(), piee);
            RenderUtils.renderJson(JsonUtils.toJson(new DefaultResponse(PARAM_EMPTY_ERROR, "Accept Lbss DeviceUser Data error!" + piee.getMessage())), response);
        }catch(ParamIsInvalidException piie){
            LOGGER.error("Accept Lbss DeviceUser Data error:{}", piie.getMessage(), piie);
            RenderUtils.renderJson(JsonUtils.toJson(new DefaultResponse(PARAM_INVALID_ERROR, "Accept Lbss DeviceUser Data error!" + piie.getMessage())), response);
        }catch(AppErrorCodeException appe){
            LOGGER.error("Accept Lbss DeviceUser Data error{}",appe.getMessage(), appe);
            RenderUtils.renderJson(JsonUtils.toJson(new DefaultResponse(String.valueOf(appe.getErrorCode()), "Accept Lbss DeviceUser Data error!" + appe.getMessage())), response);
        }catch(Exception e) {
			LOGGER.error("Accept Lbss DeviceUser Data error{}",e.getMessage(), e);
			RenderUtils.renderJson(JsonUtils.toJson(new DefaultResponse(SYS_INTERVAL_ERROR, "Accept Lbss DeviceUser Data error!" + e.getMessage())), response);
		}
	}
}
