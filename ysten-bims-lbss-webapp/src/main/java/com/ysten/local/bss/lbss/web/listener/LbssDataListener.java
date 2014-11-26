package com.ysten.local.bss.lbss.web.listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ysten.local.bss.bean.tb.SyncDataByDevice;
import com.ysten.local.bss.device.bean.BaseResponse;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.SyncDataLog;
import com.ysten.local.bss.device.repository.ISyncDataLogRepository;
import com.ysten.local.bss.device.service.ISyncLbssDataToBimsService;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.message.MessageListener;
import com.ysten.message.redis.Topic;
import org.apache.bcel.ExceptionConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LbssDataListener implements MessageListener {
    private Logger LOGGER = LoggerFactory.getLogger(LbssDataListener.class);
    @Autowired
    private ISyncLbssDataToBimsService syncLbssDataToBimsService;
    @Autowired
    private ISyncDataLogRepository syncDataLogRepository;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static final String UTF8 = "utf-8";

    @Override
    public void onMessage(Topic topic, Object o) {
        Long id = null;
        String backUrl = "";
        String deviceCode ="";
        String customerCode ="";
        String userId ="";
        try{
            if(o instanceof SyncDataByDevice){
                SyncDataByDevice syncData = (SyncDataByDevice) o;

                id = syncData.getId();
                backUrl = syncData.getBackUrl();
                if(null == id){
                    throw new Exception("id is null");
                }
                if(StringUtils.isBlank(backUrl)){
                    throw new Exception("backUrl is null");
                }

                String type = syncData.getType();
                if(StringUtils.isBlank(type)){
                    throw new Exception("type is null");
                }

                Device device = syncData.getDevice();
                if(null != device){
                    deviceCode = device.getCode();
                    if(StringUtils.isBlank(deviceCode)){
                        throw new Exception("device code is null");
                    }
                    if(null == device.getArea()){
                        throw new Exception("device area is null");
                    }
                    if(null == device.getState()){
                        throw new Exception("device state is null");
                    }
                }
                Customer customer = syncData.getCustomer();
                if(null != customer){
                    userId = customer.getUserId();
                    if(StringUtils.isBlank(userId)){
                        throw new Exception("customer userId is null");
                    }
                    customerCode = customer.getCode();
                    if(StringUtils.isBlank(customerCode)){
                        throw new Exception("customer code is null");
                    }
                    if(null == customer.getArea()){
                        throw new Exception("customer area is null");
                    }
                    if(null == customer.getState()){
                        throw new Exception("customer state is null");
                    }
                }

                try{
                    syncLbssDataToBimsService.saveAcceptDataByDevice(syncData);
                }catch(Exception e){
                    throw new Exception("syncLbssDataToBimsService error  "+e.getMessage(), e);
                }

                LOGGER.info("Sync Lbss Data Success: id={}, backUrl={}", new Object[]{id, backUrl});
            }
        }catch(Exception ex){
            LOGGER.error("Sync Lbss Data Failed:id=[" +id + "], backUrl=[" + backUrl + "], errorMessage=["+ ex.getMessage()+"]", ex);
            SyncDataLog syncDataLog = new SyncDataLog();
            syncDataLog.setDataId(id);
            syncDataLog.setBackUrl(backUrl);
            syncDataLog.setDeviceCode(deviceCode);
            syncDataLog.setCustomerCode(customerCode);
            syncDataLog.setUserId(userId);
            syncDataLog.setCreateDate(new Date());
            syncDataLog.setFlag("0");
            syncDataLog.setServiceName("syncLbssDataToBimsService");
            syncDataLog.setMessage(ex.getMessage());
            syncDataLog.setCause(null == ex.getCause() ?  "": ex.getCause().toString());
            this.syncDataLogRepository.saveSyncDataLog(syncDataLog);
            this.sendErrorDataByUrl(backUrl, id);
        }
    }

    private String sendErrorDataByUrl(String url, Long id)  {
        String sendResult = "";
        try{
            LOGGER.debug("back url: url={}", new Object[]{url});
            String rsp = getData(url, id);
            if(StringUtils.isBlank(rsp)){
                throw new Exception("get response failed! url: "+ url);
            }

            BaseResponse baseResponse = gson.fromJson(rsp, BaseResponse.class);
            sendResult = baseResponse.getResult();
            LOGGER.info("Send Lbss Sync Bad Data RESULT: "+ sendResult);
            return sendResult;
        }catch(Exception e){
            LOGGER.error("Send Lbss Sync Bad Data error!:"+ e.getMessage(), e);
            return sendResult;
        }
    }

    private String getData(String url, Long id)throws Exception {
        String param = String.valueOf(id);
        String rsp = "";
        try{
            HttpUtils.HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
            int returnCode = wrapper.getHttpStatus();
            LOGGER.info("back url return code: returnCode={}", new Object[]{returnCode});
            if (returnCode == 200) {
                rsp = wrapper.getResponse();
            }
        }catch(Exception ex){
            LOGGER.error("Send request error! url={}", new Object[]{url});
            throw ex;
        }
        return rsp;
    }

}
