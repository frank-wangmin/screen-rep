package com.ysten.local.bss.web.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ysten.local.bss.device.domain.ApkUpgradeResultLog;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.device.domain.DeviceUpgradeResultLog;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.logger.domain.InterfaceLog;
import com.ysten.local.bss.logger.domain.OperateLog;
import com.ysten.local.bss.logger.repository.IInterfaceLogRepository;
import com.ysten.local.bss.logger.repository.IOperatorLogRepository;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.message.queue.QueueMessageProducer;
import com.ysten.utils.page.Pageable;

@Service
public class LoggerWebServiceImpl implements ILoggerWebService {

    @Autowired
    private IInterfaceLogRepository interfaceLogRepository;
    @Autowired
    private IOperatorLogRepository operatorLogRepository;
    @Autowired
    private QueueMessageProducer logProducer;
    @Autowired
    private IDeviceRepository deviceRepository;

    @Override
    public Pageable<InterfaceLog> findAllInterfaceLog(String interfaceName, String caller, String input, String output,
            int pageNo, int pageSize) {
        return this.interfaceLogRepository.findAllInterfaceLog(interfaceName, caller, input, output, pageNo, pageSize);
    }

    @Override
    public Pageable<OperateLog> findOperatorLogger(String modelName, String operator, String operatorId,
            String beginDate, String endDate, int start, int limit) {
        return this.operatorLogRepository.findOperateLogger(modelName, operator, operatorId, beginDate, endDate, start,
                limit);
    }

    @Override
    public boolean saveOperateLog(String moduleName, String operationType, String primaryKeyValue, String description,
            HttpServletRequest request) {
        String operationIp = this.getIpAddr(request);
        Operator op = ControllerUtil.getLoginOperator(request);
        String operator = "";
        if (op != null) {
            operator = op.getLoginName();
        }
        OperateLog operateLog = new OperateLog();
        operateLog.setDescription(description);
        operateLog.setModuleName(moduleName);
        operateLog.setOperationIp(operationIp);
        operateLog.setOperationTime(new Date());
        operateLog.setOperationType(operationType);
        operateLog.setOperator(operator);
        operateLog.setPrimaryKeyValue(primaryKeyValue);
        request.getSession().setAttribute("ip", operationIp);

        try {
            logProducer.publish(operateLog);
        } catch (Exception e) {
            return false;
        }
        return true;

        // return operatorLogRepository.saveOperateLog(operateLog);
    }

    @Override
    public List<InterfaceLog> findAllInterfaceLog() {
        return this.interfaceLogRepository.findAllInterfaceLog();
    }

    /**
     * 获取IP
     */
    private String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    @Override
    public Pageable<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCodeAndYstId(String deviceCode,String ystenId, Integer pageNo,
            Integer pageSize) {
        return this.deviceRepository.findDeviceUpgradeResultLogByCodeAndYstId(deviceCode,ystenId, pageNo, pageSize);
    }

    @Override
    public Pageable<ApkUpgradeResultLog> findApkUpgradeResultLogByCodeAndYstId(String deviceCode, String ystenId, Integer pageNo, Integer pageSize) {
        return this.deviceRepository.findApkUpgradeResultLogByCodeAndYstId(deviceCode,ystenId, pageNo, pageSize);
    }

    @Override
	public List<String> getModuleNameList() {
		List<String> list = operatorLogRepository.getModuleNameList();
		Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);  
		Collections.sort(list, comparator);
		return list;
	}

	@Override
	public Pageable<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByCondition(
			Map<String, Object> map) {
		if(StringUtils.isNotBlank(map.get("deviceCodes").toString())){
			map.put("deviceCodes", this.getListByStringSplitDot(map.get("deviceCodes").toString()));
		}
		if(StringUtils.isNotBlank(map.get("ystenIds").toString())){
			map.put("ystenIds", this.getListByStringSplitDot(map.get("ystenIds").toString()));
		}
		return this.deviceRepository.findDeviceUpgradeResultLogByCondition(map);
	}
	public List<String> getListByStringSplitDot(String codes){
		String[] _codes = new String[0];;
		if (StringUtils.isNotBlank(codes)) {
			_codes = codes.split(",");
            List<String> codeList = new ArrayList<String>();
            for (int i = 0; i < _codes.length; i++) {
                if (StringUtils.isEmpty(_codes[i])) {
                    continue;
                }
                _codes[i] = FilterSpaceUtils.replaceBlank(_codes[i]);
                codeList.add(_codes[i]);
            }
            if(codeList.size()>0 && codeList != null){
            	return codeList;
            }
		}
		return null;
	}

	@Override
	public List<DeviceUpgradeResultLog> findDeviceUpgradeResultLogByIds(
			List<Long> ids) {
		return this.deviceRepository.findDeviceUpgradeResultLogByIds(ids);
	}

	@Override
	public List<DeviceUpgradeResultLog> findExportDeviceUpgradeResultLog(
			Map<String, Object> map) {
		if(StringUtils.isNotBlank(map.get("deviceCodeHvalue").toString())){
			map.put("deviceCodeHvalue", this.getListByStringSplitDot(map.get("deviceCodeHvalue").toString()));
		}
		if(StringUtils.isNotBlank(map.get("ystenIdHvalue").toString())){
			map.put("ystenIdHvalue", this.getListByStringSplitDot(map.get("ystenIdHvalue").toString()));
		}
		return this.deviceRepository.findExportDeviceUpgradeResultLog(map);
	}

	@Override
	public Pageable<ApkUpgradeResultLog> findApkUpgradeResultLogByCondition(
			Map<String, Object> map) {
		if(StringUtils.isNotBlank(map.get("deviceCodes").toString())){
			map.put("deviceCodes", this.getListByStringSplitDot(map.get("deviceCodes").toString()));
		}
		if(StringUtils.isNotBlank(map.get("ystenIds").toString())){
			map.put("ystenIds", this.getListByStringSplitDot(map.get("ystenIds").toString()));
		}
		return this.deviceRepository.findApkUpgradeResultLogByCondition(map);
	}

	@Override
	public List<ApkUpgradeResultLog> findApkUpgradeResultLogByIds(List<Long> ids) {
		return this.deviceRepository.findApkUpgradeResultLogByIds(ids);
	}

	@Override
	public List<ApkUpgradeResultLog> findExportApkUpgradeResultLog(
			Map<String, Object> map) {
		if(StringUtils.isNotBlank(map.get("deviceCodeHvalue").toString())){
			map.put("deviceCodeHvalue", this.getListByStringSplitDot(map.get("deviceCodeHvalue").toString()));
		}
		if(StringUtils.isNotBlank(map.get("ystenIdHvalue").toString())){
			map.put("ystenIdHvalue", this.getListByStringSplitDot(map.get("ystenIdHvalue").toString()));
		}
		return this.deviceRepository.findExportApkUpgradeResultLog(map);
	}

	@Override
	public List<InterfaceLog> findExportInterfaceLog(String interfaceName,
			String caller, String input, String output) {
		return this.interfaceLogRepository.findExportInterfaceLog(interfaceName, caller, input, output);
	}

	@Override
	public List<InterfaceLog> findInterfaceLogByIds(List<Long> ids) {
		return this.interfaceLogRepository.findInterfaceLogByIds(ids);
	}
}
