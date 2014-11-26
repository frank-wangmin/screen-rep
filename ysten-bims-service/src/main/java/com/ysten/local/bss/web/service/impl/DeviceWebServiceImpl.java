package com.ysten.local.bss.web.service.impl;

import com.ysten.area.domain.Area;
import com.ysten.area.repository.IAreaRepository;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.bean.EShAAAStatus.EPrepareOpen;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.domain.Customer.CustomerType;
import com.ysten.local.bss.device.domain.Customer.IdentityType;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.domain.Device.State;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.device.repository.*;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.DeviceGroupType;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.local.bss.interfaceUrl.repository.IInterfaceUrlRepository;
import com.ysten.local.bss.notice.domain.DeviceNoticeMap;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.notice.repository.ISysNoticeRepository;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.repository.IPanelPackageRepository;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.HttpUtils.HttpResponseWrapper;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.YstenIdUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.web.service.IDeviceWebService;
import com.ysten.local.bss.web.service.ISysNoticeWebService;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DeviceWebServiceImpl implements IDeviceWebService {
	private Logger LOGGER = LoggerFactory.getLogger(DeviceWebServiceImpl.class);

	@Autowired
	private IDeviceRepository deviceRepository;
	@Autowired
	private IConfigRepository configRepository;
	@Autowired
	private IAreaRepository areaRepository;
	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private IInterfaceUrlRepository interfaceUrlRepository;
	@Autowired
	private IDeviceGroupRepository deviceGroupRepository;
	@Autowired
	private IDeviceGroupMapRepository deviceGroupMapRepository;
	@Autowired
	private IServiceCollectDeviceGroupMapRepository serviceCollectDeviceGroupMapRepository;
	@Autowired
	private IBackgroundImageRepository backgroundImageRepository;
	@Autowired
	private IBootAnimationRepository bootAnimationRepository;
	@Autowired
	private IAppUpgradeMapRepository appUpgradeMapRepository;
	@Autowired
	private IUpgradeTaskRepository upgradeTaskRepository;
    @Autowired
    private IApkUpgradeTaskRepository apkUpgradeTaskRepository;
    @Autowired
    private IApkUpgradeMapRepository apkUpgradeMapRepository;
	@Autowired
	private ISysNoticeRepository sysNoticeRepository;
	@Autowired
	private IPanelPackageDeviceMapRepository panelPackageDeviceMapRepository;
	@Autowired
	private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
	@Autowired
	private IDeviceHistoryRepository DeviceHistoryRepository;
	@Autowired
	private ICityRepository cityRepository;
	@Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IServiceCollectRepository serviceCollectRepository;
    @Autowired
    private IPanelPackageRepository panelPackageRepository;
    @Autowired
    private ISysNoticeWebService sysNoticeWebService;

	boolean isFinished = false;
	private static final String UTF8 = "utf-8";

	@Override
	public Pageable<Device> findDevicesByState(String bindType, String isLock,
			String ystenId, String code, String mac, String sno,
			Long deviceVendorId, Long deviceTypeId, Long areaId, State state,
			BindType bindState, Long spDefineId, String productNo,
			Device.DistributeState distributeState, int pageNo, int pageSize) {
		return this.deviceRepository.findDevicesByState(bindType, isLock,
				FilterSpaceUtils.filterStartEndSpace(ystenId),
				FilterSpaceUtils.filterStartEndSpace(code),
				FilterSpaceUtils.filterStartEndSpace(mac),
				FilterSpaceUtils.filterStartEndSpace(sno), deviceVendorId,
				deviceTypeId, areaId, state, bindState, spDefineId, productNo,
				distributeState, pageNo, pageSize);
	}

	@Override
	public Pageable<Device> findDevicesByGroupId(String tableName,String character,String deviceGroupId,
			String ystenId, String code, String mac, String sno, String softCode, String versionSeq,int pageNo,
			int pageSize) {
		if (StringUtils.isNotBlank(deviceGroupId)) {
			return this.deviceRepository.findDevicesByGroupId(tableName,character,
					Long.parseLong(deviceGroupId), ystenId, code, mac, sno, softCode, versionSeq,
					pageNo, pageSize);

		}
		return null;
	}

	@Override
	public Pageable<DeviceVendor> findAllDeviceVendors(String deviceVendorName,
			String deviceVendorCode, Integer pageNo, Integer pageSize) {
		return this.deviceRepository.findAllDeviceVendors(
				FilterSpaceUtils.filterStartEndSpace(deviceVendorName),
				FilterSpaceUtils.filterStartEndSpace(deviceVendorCode), pageNo,
				pageSize);
	}

	@Override
	public Device getDeviceById(long deviceId) {
		return this.deviceRepository.getDeviceById(deviceId);
	}

	@Override
	public List<DeviceVendor> findDeviceVendors(DeviceVendor.State state) {
		return this.deviceRepository.findDeviceVendors(state);
	}

	@Override
	public List<DeviceType> findDeviceTypes() {
		return this.deviceRepository.findDeviceTypes();
	}

	@Override
	public Device getDeviceById(Long id) {
		return this.deviceRepository.getDeviceById(id);
	}

	@Override
	public DeviceType getDeviceTypeById(Long id) {
		return this.deviceRepository.getDeviceTypeById(id);
	}

	@Override
	public DeviceVendor getDeviceVendorById(Long id) {
		return this.deviceRepository.getDeviceVendorById(id);
	}

	@Override
	public List<DeviceType> findDeviceTypesByVendorId(String vendorId,
			DeviceType.State state) {
		return this.deviceRepository.findDeviceTypesByVendorId(vendorId, state);
	}

	@Override
	public boolean saveDeviceVendor(DeviceVendor deviceVendor) {
		return this.deviceRepository.saveDeviceVendor(deviceVendor);
	}

	@Override
	public boolean updateDeviceVendor(DeviceVendor deviceVendor) {
		return this.deviceRepository.updateDeviceVendor(deviceVendor);
	}

	@Override
	public Pageable<DeviceType> findAllDeviceTypes(String deviceTypeName,
			String deviceTypeCode, String deviceVendorId, Integer pageNo,
			Integer pageSize) {
		return this.deviceRepository.findAllDeviceTypes(
				FilterSpaceUtils.filterStartEndSpace(deviceTypeName),
				FilterSpaceUtils.filterStartEndSpace(deviceTypeCode),
				deviceVendorId, pageNo, pageSize);
	}

	@Override
	public boolean saveDeviceType(DeviceType deviceType) {
		return this.deviceRepository.saveDeviceType(deviceType);
	}

	@Override
	public boolean updateDeviceType(DeviceType deviceType) {
		return this.deviceRepository.updateDeviceType(deviceType);
	}

	@Override
	public DeviceType getDeviceTypeByNameOrCode(String deviceVendor,
			String deviceTypeName, String deviceTypeCode) {
		return this.deviceRepository.getDeviceTypeByNameOrCode(deviceVendor,
				deviceTypeName, deviceTypeCode);
	}

	@Override
	public DeviceVendor getDeviceVendorByNameOrCode(String deviceVendorName,
			String deviceVendorCode) {
		return this.deviceRepository.getDeviceVendorByNameOrCode(
				deviceVendorName, deviceVendorCode);
	}

	@Override
	public boolean updateDevice(Device device) {
		return this.deviceRepository.updateDevice(device);
	}

	@Override
	public Pageable<DeviceIp> findDeviceIpByIpSeg(String ipSeg, int pageNo,
			int pageSize) {
		return this.deviceRepository.findDeviceIpByIpSeg(
				FilterSpaceUtils.filterStartEndSpace(ipSeg), pageNo, pageSize);
	}

	@Override
	public boolean saveDeviceIp(DeviceIp deviceIp) {
		return this.deviceRepository.saveDeviceIp(this.getDeviceIp(deviceIp));
	}

	@Override
	public DeviceIp getDeviceIpById(Long id) {
		return this.deviceRepository.getDeviceIpById(id);
	}

	@Override
	public boolean updateDeviceIp(DeviceIp deviceIp) {
		return this.deviceRepository.updateDeviceIp(this.getDeviceIp(deviceIp));
	}

	private DeviceIp getDeviceIp(DeviceIp deviceIp) {
		String[] str = deviceIp.getIpSeg().split("\\.");
		Long startValue = 0L;
		Long endValue1 = 0L;
		Long endValue2 = 0L;
		for (int i = 0; i < 4; i++) {
			startValue = startValue * 256 + Integer.parseInt(str[i]);
		}
		for (int j = 0; j < 4; j++) {
			endValue1 = endValue1 * 256 + Integer.parseInt(str[j]);
		}
		for (int k = 0; k < 32 - deviceIp.getMaskLength(); k++) {
			endValue2 = (endValue2 << 1) + 1;
		}
		deviceIp.setStartIpValue(startValue + 1);
		deviceIp.setEndIpValue(endValue1 + endValue2 - 1);
		return deviceIp;
	}

	@Override
	public boolean deleteDeviceIp(List<Long> ids) {
		return this.deviceRepository.deleteDeviceIp(ids);
	}

	@Override
	public boolean checkDeviceIpExists(String ipSeg1) {
		String[] str = ipSeg1.split("\\.");
		Long ipValue = 0L;
		for (int i = 0; i < 4; i++) {
			ipValue = ipValue * 256 + Integer.parseInt(str[i]);
		}
		List<DeviceIp> list = this.deviceRepository
				.getDeviceIpByIpValue(ipValue);
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean lockDevice(List<Long> ids, String par) {
		boolean bool = false;
		if (ids != null && ids.size() > 0 && StringUtils.isNotBlank(par)) {
			for (int i = 0; i < ids.size(); i++) {
				Device device = this.deviceRepository.getDeviceById(ids.get(i));
				if (device != null) {
					if ("lock".equals(par)) {
						device.setIsLock(LockType.LOCK);
					} else {
						device.setIsLock(LockType.UNLOCKED);
					}
					bool = this.deviceRepository.updateDevice(device);
				}
			}
		}
		return bool;
	}

	@Override
	public boolean distributeDevice(String productNo) throws Exception {
		int distributeDeviceNum = 0;
		String _distributeDeviceNum = this.systemConfigService
				.getSystemConfigByConfigKey("distributeDeviceNum");
		if (!StringUtils.isBlank(_distributeDeviceNum)) {
			distributeDeviceNum = Integer.parseInt(_distributeDeviceNum);
		} else {
			throw new Exception("配置信息有误，请确认配置文件中distributeDeviceNum是否配置正确");
		}
		List<Device> devices = this.deviceRepository.getByProductNo(productNo,
				Device.DistributeState.UNDISTRIBUTE);
		if (devices == null || devices.isEmpty()) {
			return true;
		}
		List<Device> _devices = null;
		int index = 0;
		boolean isAllSuccess = true;
		Area area = devices.get(0).getArea();
		InterfaceUrl interfaceUrl = this.interfaceUrlRepository
				.getByAreaAndName(area.getId(), InterfaceName.RECEIVEDEVICE);
		if (interfaceUrl == null) {
			throw new Exception("请先维护省份接口Url表!");
		}
		String url = interfaceUrl.getInterfaceUrl();
		for (int i = 0; i < devices.size(); i++) {
			if (index < distributeDeviceNum) {
				if (_devices == null) {
					_devices = new ArrayList<Device>();
				}
				_devices.add(devices.get(i));
				index++;
			}
			// 如果达到发送数量或者已经是最后一个设备了，就下发设备
			if (index == distributeDeviceNum || i == devices.size() - 1) {
				boolean isDistributeSuccess = this.distributeDeviceToProvince(
						_devices, url);
				// 只要有一个发送批次失败，那么就返回失败
				if (!isDistributeSuccess) {
					isAllSuccess = false;
					// 批发下发成功，更新批次下发状态
				} else {
					for (Device _device : _devices) {
						_device.setDistributeState(DistributeState.DISTRIBUTE);
						this.deviceRepository.updateDevice(_device);
					}
				}
				_devices = null;
				index = 0;
			}
		}
		return isAllSuccess;
	}

	@Override
	public boolean rendSoftCode(List<Device> devices) throws Exception {
		boolean isAllSuccess = true;
		if (devices == null || devices.isEmpty()) {
			return true;
		}
		Area area = devices.get(0).getArea();
		InterfaceUrl interfaceUrl = this.interfaceUrlRepository
				.getByAreaAndName(area.getId(), InterfaceName.RECEIVEDEVICE);
		if (interfaceUrl == null) {
			throw new Exception("请先维护省份接口Url表!");
		}
		String url = interfaceUrl.getInterfaceUrl();
		boolean isDistributeSuccess = this.distributeDeviceToProvince(devices,
				url);
		// 只要有一个发送批次失败，那么就返回失败
		if (!isDistributeSuccess) {
			isAllSuccess = false;
			// 批发下发成功，更新批次下发状态
		} else {
			for (Device _device : devices) {
				_device.setDistributeState(DistributeState.DISTRIBUTE);
				this.deviceRepository.updateDevice(_device);
			}
		}
		return isAllSuccess;
	}

	@Override
	public String checkInputSql(String sql) {
		List<Device> deviceList = this.deviceGroupRepository.checkInputSql(sql);
		for (Device device : deviceList) {
			if (device == null) {
				return "请输入正确的查询语句!";
			}
			Device deviceGroupExisted = this.deviceRepository
					.getDeviceById(device.getId());
			if (deviceGroupExisted == null
					|| StringUtils.isBlank(deviceGroupExisted.getCode())) {
				return "请输入正确的表名";
			}
		}
		return Constants.SUCCESS;
	}

	@Override
	public List<DeviceGroupMap> findDeviceGroupMapByGroupId(String deviceGroupId) {
		if (StringUtils.isNotBlank(deviceGroupId)) {
			return this.deviceGroupMapRepository
					.findDeviceGroupMapByGroupId(Long.parseLong(deviceGroupId));
		}
		return null;
	}

	@Override
	public Map<String, Object> findDeviceGroupMapByDeviceCode(String deviceCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(deviceCode)) {
			List<DeviceGroup> mapList = this.deviceGroupRepository
					.findDeviceGroupMapByDeviceCode(deviceCode);
			if (mapList != null && mapList.size() > 0) {
				StringBuilder aniGroupId = new StringBuilder("");
				StringBuilder appGroupId = new StringBuilder("");
				StringBuilder backGroupId = new StringBuilder("");
				StringBuilder bootGroupId = new StringBuilder("");
				// StringBuilder commGroupId = new StringBuilder("");
				StringBuilder panelGroupId = new StringBuilder("");
				StringBuilder noticeGroupId = new StringBuilder("");
				StringBuilder upgradeGroupId = new StringBuilder("");
				for (DeviceGroup deviceGroup : mapList) {
					if (DeviceGroupType.ANIMATION.equals(deviceGroup.getType())) {
						if (StringUtils.isBlank(aniGroupId.toString())) {
							aniGroupId.append(deviceGroup.getId());
						} else {
							aniGroupId.append(",").append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.APPUPGRADE.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(appGroupId.toString())) {
							appGroupId.append(deviceGroup.getId());
						} else {
							appGroupId.append(",").append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.BACKGROUND.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(backGroupId.toString())) {
							backGroupId.append(deviceGroup.getId());
						} else {
							backGroupId.append(",").append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.BOOTSTRAP.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(bootGroupId.toString())) {
							bootGroupId.append(deviceGroup.getId());
						} else {
							bootGroupId.append(",").append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.PANEL.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(panelGroupId.toString())) {
							panelGroupId.append(deviceGroup.getId());
						} else {
							panelGroupId.append(",")
									.append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.NOTICE.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(noticeGroupId.toString())) {
							noticeGroupId.append(deviceGroup.getId());
						} else {
							noticeGroupId.append(",").append(
									deviceGroup.getId());
						}
					}
					/**
					 * 暂时无此类型的需求 else if
					 * (DeviceGroupType.COMMON.equals(deviceGroup.getType())) {
					 * if (StringUtils.isBlank(commGroupId.toString())) {
					 * commGroupId.append(deviceGroup.getId()); } else {
					 * commGroupId.append(",").append(deviceGroup.getId()); } }
					 */
					else {
						if (StringUtils.isBlank(upgradeGroupId.toString())) {
							upgradeGroupId.append(deviceGroup.getId());
						} else {
							upgradeGroupId.append(",").append(
									deviceGroup.getId());
						}
					}
				}
				map.put(DeviceGroupType.ANIMATION.toString(), aniGroupId);
				map.put(DeviceGroupType.APPUPGRADE.toString(), appGroupId);
				map.put(DeviceGroupType.BACKGROUND.toString(), backGroupId);
				map.put(DeviceGroupType.BOOTSTRAP.toString(), bootGroupId);
				// map.put(DeviceGroupType.COMMON.toString(), commGroupId);
				map.put(DeviceGroupType.NOTICE.toString(), noticeGroupId);
				map.put(DeviceGroupType.PANEL.toString(), panelGroupId);
				map.put(DeviceGroupType.UPGRADE.toString(), upgradeGroupId);
				// return "{\"" + DeviceGroupType.ANIMATION + ":\"" +
				// aniGroupId.toString() + "\",\"" + DeviceGroupType.NOTICE +
				// ":\"" + noticeGroupId.toString() + "\" }";
				return map;
			}
		}
		return null;
	}

	private boolean distributeDeviceToProvince(List<Device> deviceList,
			String url) {
		String param = JsonUtil.getJsonString4List(deviceList);
		LOGGER.info("Distribute Device Url: " + url);
		LOGGER.info("Distribute Device Url: " + param);
		HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
		int returnCode = wrapper.getHttpStatus();
		if (returnCode == 200) {
			String rsp = wrapper.getResponse();
			JSONObject object = JSONObject.fromObject(rsp);
			String result = object.getString("result");
			if (StringUtils.equalsIgnoreCase(result, "true")) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean pickupDevice(String productNo) throws Exception {
		List<Device> devices = this.deviceRepository.getByProductNo(productNo,
				Device.DistributeState.DISTRIBUTE);
		for (Device device : devices) {
			device.setDistributeState(DistributeState.PICKUP);
			device.setIsSync(SyncType.WAITSYNC);
			this.deviceRepository.updateDevice(device);
		}
		return true;
	}

	@Override
	public String updateDevice(String deviceId, Device.State deviceState,
			String mac, String deviceVendorId, String deviceTypeId,
			String expireDate, String area, String city, String spCode,
			String sno) throws ParseException {
		String str = "fail";
		str = checkMac(mac, deviceId, str);
		if (deviceId != null && !deviceId.isEmpty()) {
			Device device = this.deviceRepository.getDeviceById(Long
					.parseLong(deviceId));
			if (device != null && !str.equals("isExists")) {
				if (StringUtils.isNotBlank(city)) {
					City _city = this.cityRepository.getCityById(Long
							.parseLong(city));
					if (_city != null
							&& _city.getLeaderId().equals(Long.parseLong(area))) {
						device.setCity(this.cityRepository.getCityById(Long
								.parseLong(city)));
					} else {
						return "difference";
					}
				}
				device.setMac(mac != null && !mac.isEmpty()
						&& device.getId().toString().equals(deviceId) ? mac
						.trim() : null);
				if (!deviceState.equals(device.getState())) {
					device.setStateChangeDate(new Date());
				}
				// 根据是否为中心系统 对分发|同步状态字段做更新
				String isCenter = this.systemConfigService
						.getSystemConfigByConfigKey("isCenter");
				if (isCenter.equals("true")) {
					device.setDistributeState(DistributeState.UNDISTRIBUTE);
				} else {
					device.setIsSync(SyncType.WAITSYNC);
				}
				device.setState(deviceState);
				DeviceVendor deviceVendor = new DeviceVendor();
				deviceVendor.setId(Long.parseLong(deviceVendorId));
				device.setDeviceVendor(deviceVendor);
				DeviceType deviceType = new DeviceType();
				deviceType.setId(Long.parseLong(deviceTypeId));
				if (StringUtils.isNotBlank(expireDate)) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					device.setExpireDate(sdf.parse(expireDate));
				}
				device.setDeviceType(deviceType);
				if (StringUtils.isNotBlank(area)) {
					device.setArea(this.areaRepository.getAreaById(Long
							.parseLong(area)));
				}
				if (StringUtils.isNotBlank(spCode)) {
					device.setSpDefine(this.configRepository
							.getSpDefineById(Integer.parseInt(spCode)));
				}
				if (StringUtils.isNotBlank(sno)) {
					device.setSno(sno);
				}
				if (this.deviceRepository.updateDevice(device)) {
					str = "success";
				}
			}
		}
		return str;
	}

	private String checkMac(String mac, String deviceId, String str) {
		if (mac != null && !mac.isEmpty()) {
			Device device = this.deviceRepository.getDeviceByMac(mac);
			if (device != null && !device.getId().toString().equals(deviceId)) {
				str = "isExists";
			}
		}
		return str;
	}

	@Override
	public List<Device> findDevicesByState(Map<String, Object> map) {
		return this.deviceRepository.findDevicesByState(map);
	}

	@Override
	public Map<String, Object> importDeviceFile(File targetFile)
			throws Exception {
		Map<String, Object> map = readDevicesFromFile(targetFile);
		if (!(Boolean) map.get("isSuccess")) {
			return map;
		}
		String[][] data = (String[][]) map.get("data");
		String importFileSize = this.systemConfigService
				.getSystemConfigByConfigKey("importFileSize");
		if (data.length > Integer.parseInt(importFileSize)) {
			this.appendErrorMsg(map, "Excel文档最大导入条数：" + importFileSize);
			return map;
		}
		isFinished = false;
		// 获取cpu各数，也即同时启动线程数
		int threadsNum = Runtime.getRuntime().availableProcessors();
		ExecutorService es = Executors.newFixedThreadPool(threadsNum);
		CyclicBarrier barrier = new CyclicBarrier(threadsNum, new Runnable() {
			@Override
			public void run() {
				isFinished = true;
			}
		});
		int threadSize = data.length / threadsNum;
		if (threadSize == 0) {
			barrier = new CyclicBarrier(1, new Runnable() {
				@Override
				public void run() {
					isFinished = true;
				}
			});
			es.execute(new ImportJob(barrier, data, 0, data.length, map,
					deviceRepository, areaRepository, configRepository,
					cityRepository,customerRepository));
		} else {
			for (int i = 0; i < threadsNum; i++) {
				if (i == threadsNum - 1) {
					es.execute(new ImportJob(barrier, data, i * threadSize,
							data.length, map, deviceRepository, areaRepository,
							configRepository, cityRepository,customerRepository));
				} else {
					es.execute(new ImportJob(barrier, data, i * threadSize,
							(i + 1) * threadSize, map, deviceRepository,
							areaRepository, configRepository, cityRepository,customerRepository));
				}
			}
		}
		while (!isFinished) {
			Thread.sleep(1000);
		}
		return map;
	}

	private Map<String, Object> readDevicesFromFile(File targetFile)
			throws Exception {
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		map.put("isSuccess", true);
		// 创建输入流
		InputStream stream = new FileInputStream(targetFile);
		// 获取Excel文件对象
		Workbook rwb = Workbook.getWorkbook(stream);
		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);
		if (sheet.getRows() <= 1) {
			this.appendErrorMsg(map, "空Excel文档，请确认!");
			return map;
		}
		String[][] data = new String[sheet.getRows() - 1][sheet.getColumns()];
		// 行数(表头的目录不需要，从1开始)
		for (int i = 1; i < sheet.getRows(); i++) {
			boolean isCheckTrue = true;
			for (int j = 0; j < sheet.getColumns(); j++) {
				data[i - 1][j] = sheet.getCell(j, i).getContents();
				if (j < 10 && StringUtils.isBlank(data[i - 1][j])) {
					isCheckTrue = false;
					break;
				}
			}
			if (!isCheckTrue) {
				this.appendErrorMsg(
						map,
						"Row "
								+ i
								+ " error, 设备编号，序列号，MAC，设备型号，设备厂商，区域，地市，运营商，生产批次，软件版本号不能为空！");
			}
		}
		map.put("data", data);
		rwb.close();
		stream.close();
		return map;
	}

	@SuppressWarnings("unchecked")
	private synchronized void appendErrorMsg(Map<String, Object> map,
			String error) {
		List<String> errors = (List<String>) map.get("error");
		if (errors == null) {
			errors = new ArrayList<String>();
			map.put("error", errors);
			map.put("isSuccess", false);
		}
		errors.add(error);
	}

	@Override
	public List<Device> getDeviceByIds(List<Long> ids) {
		return this.deviceRepository.getDeviceByIds(ids);
	}

	@Override
	public List<Device> findDevicesToExport(Map<String, Object> map) {
		return this.deviceRepository.findDevicesToExport(map);
	}

	@Override
	public Pageable<DeviceGroup> findDeviceGroupByType(String name,
			String type, String pageNo, String pageSize) {
		return this.deviceGroupRepository.findDeviceGroupByType(
				FilterSpaceUtils.filterStartEndSpace(name), type,
				Integer.parseInt(pageNo), Integer.parseInt(pageSize));
	}

	@Override
	public Pageable<DeviceGroup> findDeviceGroupByTypeName(String name,
			String type, String pageNo, String pageSize) {
		return this.deviceGroupRepository.findDeviceGroupByTypeName(
				FilterSpaceUtils.filterStartEndSpace(name), type,
				Integer.parseInt(pageNo), Integer.parseInt(pageSize));
	}

	@Override
	public Pageable<DeviceGroup> findDeviceGroupByTypeNameDist(String name,
			String type, Long areaId, String pageNo, String pageSize) {
		return this.deviceGroupRepository.findDeviceGroupByTypeNameDist(
				FilterSpaceUtils.filterStartEndSpace(name), type, areaId,
				Integer.parseInt(pageNo), Integer.parseInt(pageSize));
	}

	@Override
	public boolean saveDeviceGroup(DeviceGroup deviceGroup) {
		DeviceGroup dg = this.deviceGroupRepository.findByNameAndType(
				deviceGroup.getName(), deviceGroup.getType());
		if (dg != null) {
			return false;
		}
		return this.deviceGroupRepository.insertOrUpdate(deviceGroup);
	}

	@Override
	public DeviceGroup getDeviceGroupById(Long groupId) {
		return this.deviceGroupRepository.selectByPrimaryKey(groupId);
	}

	// add the logic check of the deviceGroupName has been modified by joyce on
	// 2014-6-12
	@Override
	public boolean updateDeviceGroup(DeviceGroup deviceGroup) {
		DeviceGroup modifiedRecord = this.deviceGroupRepository
				.selectByPrimaryKey(deviceGroup.getId());
		if (StringUtils.isNotBlank(modifiedRecord.getName())) {
			// check if the name has been modified
			if (modifiedRecord.getName().equals(deviceGroup.getName())) {

			} else {
				DeviceGroup dg = this.deviceGroupRepository.findByNameAndType(
						deviceGroup.getName(), deviceGroup.getType());
				if (dg != null) {
					return false;
				}
			}
		}
		return this.deviceGroupRepository.insertOrUpdate(deviceGroup);
	}

    @Override
    public boolean deleteDeviceByGroupId(Long deviceGroupId,List<String> idsList){
        if(idsList.size()==0){
            this.deviceRepository.deleteDeviceGroupMapByGroupId(deviceGroupId);
        }else{
            this.deviceGroupMapRepository.deleteMapByYstenIdsAndGroups((String[])idsList.toArray(),StringUtil.splitToLong(String.valueOf(deviceGroupId)));
        }
        return true;
    }

	@Override
	public boolean deleteDeviceGroupMap(List<Long> idsList) {
		for (Long id : idsList) {
			this.deviceRepository.deleteDeviceGroupMapByGroupId(id);
		}
		return true;
	}

	@Override
	public boolean deleteDeviceGroupMapByCode(List<Long> idsList) {
		for (Long id : idsList) {
			Device device = this.deviceRepository.getDeviceById(id);
			this.deviceRepository.deleteDeviceGroupMapByCode(device
					.getYstenId());
		}
		return true;
	}

	@Override
	public boolean deleteDeviceGroupBusiness(List<Long> idsList) {
		for (Long id : idsList) {
			DeviceGroup deviceGroup = this.deviceGroupRepository
					.selectByPrimaryKey(id);
			if (deviceGroup.getType().equals(DeviceGroupType.BOOTSTRAP)) {
				this.serviceCollectDeviceGroupMapRepository
						.deleteServiceCollectGroupmapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.BACKGROUND)) {
				this.backgroundImageRepository
						.deleteBackGroundImageMapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.ANIMATION)) {
				this.bootAnimationRepository
						.deleteAnimationDeviceMapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.APPUPGRADE)) {
				this.appUpgradeMapRepository.deleteAppUpgradeMapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.UPGRADE)) {
				this.upgradeTaskRepository.deleteUpgradeTaskMapByGroupId(id);
			}
            if (deviceGroup.getType().equals(DeviceGroupType.APKUPGRADE)) {
                this.apkUpgradeMapRepository.deleteApkUpgradeMapByGroupId(id);
            }
			if (deviceGroup.getType().equals(DeviceGroupType.NOTICE)) {
				this.sysNoticeRepository.deleteSysNoticeMapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.PANEL)) {
				this.panelPackageDeviceMapRepository
						.deletePanelDeviceMapByDeviceGroupId(id);
			}
		}
		return true;
	}

	@Override
	public boolean deleteDeviceGroup(List<Long> idsList) {
		for (Long id : idsList) {
			DeviceGroup deviceGroup = this.deviceGroupRepository
					.selectByPrimaryKey(id);
			this.deviceGroupRepository.deleteByPrimaryKey(id);
			this.deviceRepository.deleteDeviceGroupMapByGroupId(id);
			if (deviceGroup.getType().equals(DeviceGroupType.BOOTSTRAP)) {
				this.serviceCollectDeviceGroupMapRepository
						.deleteServiceCollectGroupmapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.BACKGROUND)) {
				this.backgroundImageRepository
						.deleteBackGroundImageMapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.ANIMATION)) {
				this.bootAnimationRepository
						.deleteAnimationDeviceMapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.APPUPGRADE)) {
				this.appUpgradeMapRepository.deleteAppUpgradeMapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.UPGRADE)) {
				this.upgradeTaskRepository.deleteUpgradeTaskMapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.NOTICE)) {
				this.sysNoticeRepository.deleteSysNoticeMapByGroupId(id);
			}
			if (deviceGroup.getType().equals(DeviceGroupType.PANEL)) {
				this.panelPackageDeviceMapRepository
						.deletePanelDeviceMapByDeviceGroupId(id);
			}
		}
		return true;
	}

	@Override
	public String deleteDeviceGroupByCondition(List<Long> idsList) {
		for (Long id : idsList) {
			DeviceGroup deviceGroup = this.deviceGroupRepository
					.selectByPrimaryKey(id);
			List<DeviceGroupMap> deviceGroupMap = this.deviceGroupMapRepository
					.getDeviceGroupMapByGroupId(id);
			if (deviceGroupMap != null && deviceGroupMap.size() != 0) {
				return "设备分组:" + deviceGroup.getName() + "绑定了设备，不可删除，请先解绑！";
			}
			// this.deviceRepository.deleteDeviceGroupMapByGroupId(id);
			if (deviceGroup.getType().equals(DeviceGroupType.BOOTSTRAP)) {
				// this.serviceCollectDeviceGroupMapRepository.deleteServiceCollectGroupmapByGroupId(id);
				List<ServiceCollectDeviceGroupMap> serviceMap = this.serviceCollectDeviceGroupMapRepository
						.getServiceCollectGroupmapByGroupId(id);
				if (serviceMap != null && serviceMap.size() != 0) {
					return "设备分组:" + deviceGroup.getName()
							+ "绑定了服务集，不可删除，请先解绑！";
				}
			}
			if (deviceGroup.getType().equals(DeviceGroupType.BACKGROUND)) {
				// this.backgroundImageRepository.deleteBackGroundImageMapByGroupId(id);
				List<BackgroundImageDeviceMap> backgroundMap = this.backgroundImageRepository
						.findMapByDeviceGroupId(id);
				if (backgroundMap != null && backgroundMap.size() != 0) {
					return "设备分组:" + deviceGroup.getName()
							+ "绑定了背景轮换，不可删除，请先解绑！";
				}
			}
			if (deviceGroup.getType().equals(DeviceGroupType.ANIMATION)) {
				// this.bootAnimationRepository.deleteAnimationDeviceMapByGroupId(id);
				AnimationDeviceMap animationMap = this.bootAnimationRepository
						.findMapByDeviceGroupId(id, "GROUP");
				if (animationMap != null) {
					return "设备分组:" + deviceGroup.getName()
							+ "绑定了开机动画，不可删除，请先解绑！";
				}
			}
			if (deviceGroup.getType().equals(DeviceGroupType.APPUPGRADE)) {
				// this.appUpgradeMapRepository.deleteAppUpgradeMapByGroupId(id);
				List<AppUpgradeMap> appUpgtade = this.appUpgradeMapRepository
						.findpUpgradeMapByGroupIdAndType(id, "GROUP");
				if (appUpgtade != null) {
					return "设备分组:" + deviceGroup.getName()
							+ "绑定了应用升级任务，不可删除，请先解绑！";
				}
			}
			if (deviceGroup.getType().equals(DeviceGroupType.UPGRADE)) {
				// this.upgradeTaskRepository.deleteUpgradeTaskMapByGroupId(id);
				DeviceUpgradeMap upgradeMap = this.upgradeTaskRepository
						.getByGroupId(id);
				if (upgradeMap != null) {
					return "设备分组:" + deviceGroup.getName()
							+ "绑定了设备升级任务，不可删除，请先解绑！";
				}
			}
			if (deviceGroup.getType().equals(DeviceGroupType.NOTICE)) {
				// this.sysNoticeRepository.deleteSysNoticeMapByGroupId(id);
				List<DeviceNoticeMap> noticeMap = this.sysNoticeRepository
						.findSysNoticeMapByDeviceGroupId(id);
				if (noticeMap != null && noticeMap.size() != 0) {
					return "设备分组:" + deviceGroup.getName() + "绑定了通知，不可删除，请先解绑！";
				}
			}
			if (deviceGroup.getType().equals(DeviceGroupType.PANEL)) {
				// this.panelDeviceRepository.deletePanelDeviceMapByDeviceGroupId(id);
				PanelPackageDeviceMap panelMap = this.panelPackageDeviceMapRepository
						.getPanelDeviceMapByGroupId(id);
				if (panelMap != null) {
					return "设备分组:" + deviceGroup.getName()
							+ "绑定了PANEL面板组，不可删除，请先解绑！";
				}
			}
			this.deviceGroupRepository.deleteByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public List<String> getAllProductNoByArea(String area, DistributeState state) {
		return this.deviceRepository.getAllProductNoByArea(area, state);
	}

	@Override
	public List<DeviceGroup> findDeviceGroupListByType(DeviceGroupType type,
			String isDynamic) {
		return this.deviceGroupRepository.findDeviceGroupListByType(type,
				isDynamic);
	}

	@Override
	public List<DeviceGroup> findDeviceGroupByDistrictCode(
			DeviceGroupType type, String tableName, String character,
			String districtCode, String id) {
		List<String> districtCodes = new ArrayList<String>();
		if (districtCode.indexOf(",") > 0) {
			districtCodes = StringUtil.split(districtCode);
		} else {
			districtCodes.add(districtCode);
		}
		return this.deviceGroupRepository.findDeviceGroupByDistrictCode(type,
				tableName, character, districtCodes, Long.parseLong(id));
	}

	@Override
	public List<Long> findDeviceGroupsById(String id, String character,
			String tableName) {
		if (StringUtils.isNotBlank(id)) {
			return this.deviceGroupRepository.findDeviceGroupById(
					Long.parseLong(id), character, tableName);
		}
		return null;
	}

	@Override
	public boolean syncDevice() throws Exception {
		boolean isAllSuccess = true;
		String num_space = this.systemConfigService
				.getSystemConfigByConfigKey("getSpaceNum");
		if (StringUtils.isBlank(num_space)) {
			throw new Exception("配置信息有误，请确认配置文件中getSpaceNum是否配置正确");
		}
		int distributeDeviceNum = 0;
		String _distributeDeviceNum = this.systemConfigService
				.getSystemConfigByConfigKey("distributeDeviceNum");
		if (!StringUtils.isBlank(_distributeDeviceNum)) {
			distributeDeviceNum = Integer.parseInt(_distributeDeviceNum);
		} else {
			throw new Exception("配置信息有误，请确认配置文件中distributeDeviceNum是否配置正确");
		}

		int counts = this.deviceRepository.getCountByIsSync();// 获取总共要同步量
		// 根据配置参数每次抓取数据同步
		int count = 0;
		if (counts % Integer.parseInt(num_space) == 0) {
			count = counts / Integer.parseInt(num_space);
		} else {
			count = counts / Integer.parseInt(num_space) + 1;
		}
		for (int j = 0; j < count; j++) {
			List<Device> devices = this.deviceRepository
					.getAllDeviceByStateChange(Integer.parseInt(num_space));
			if (devices == null || devices.isEmpty()) {
				return true;
			}
			Area area = devices.get(0).getArea();
			List<Device> _devices = null;
			int index = 0;
			InterfaceUrl interfaceUrl = this.interfaceUrlRepository
					.getByAreaAndName(area.getId(), InterfaceName.SYNCDEVICE);
			if (interfaceUrl == null) {
				throw new Exception("请先维护省份接口Url表!");
			}
			String url = interfaceUrl.getInterfaceUrl();
			for (int i = 0; i < devices.size(); i++) {
				if (index < distributeDeviceNum) {
					if (_devices == null) {
						_devices = new ArrayList<Device>();
					}
					_devices.add(devices.get(i));
					index++;
				}
				// 如果达到同步数量或者已经是最后一个设备了，就同步设备
				if (index == distributeDeviceNum || i == devices.size() - 1) {
					boolean isSyncSuccess = this.syncDevicesToCenter(_devices,
							url);
					// 只要有一个发送批次同步失败，那么就返回失败
					if (!isSyncSuccess) {
						isAllSuccess = false;
						// for(Device device:devices){
						// this.deviceRepository.updateSyncById(device.getId(),
						// SyncType.FAILED.toString());
						// }
						// 成功，更新状态
					} else {
						for (Device device : devices) {
							this.deviceRepository.updateSyncById(
									device.getId(), SyncType.SYNCED.toString());
						}
					}
					_devices = null;
					index = 0;
				}
			}
		}
		return isAllSuccess;
	}

	private boolean syncDevicesToCenter(List<Device> deviceList, String url) {
		String param = JsonUtil.getJsonString4List(deviceList);
		LOGGER.info("Sync Device To Center Url: " + url);
		LOGGER.info("Sync Device To Center Param: " + param);
		HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
		int returnCode = wrapper.getHttpStatus();
		if (returnCode == 200) {
			String rsp = wrapper.getResponse();
			JSONObject object = JSONObject.fromObject(rsp);
			String result = object.getString("result");
			if (StringUtils.equalsIgnoreCase(result, "true")) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public void synchronizeDevice() throws Exception {
		String syncDeviceNum = this.systemConfigService
				.getSystemConfigByConfigKey("syncDeviceNum");
		if (StringUtils.isBlank(syncDeviceNum)) {
			throw new Exception("请确认配置表中syncDeviceNum已经维护");
		}
		int num = Integer.parseInt(syncDeviceNum);
		int count = this.deviceRepository.getTotalSyncingDevice();
		if (count < num) {
			List<Device> devices = this.deviceRepository
					.getAllDeviceByStateChange(new Integer(num - count));
			if (devices == null || devices.isEmpty()) {
				return;
			}
			Area area = devices.get(0).getArea();
			InterfaceUrl interfaceUrl = this.interfaceUrlRepository
					.getByAreaAndName(area.getId(), InterfaceName.SYNCDEVICE);
			if (interfaceUrl == null) {
				throw new Exception("请先维护省份接口Url表!");
			}
			String url = interfaceUrl.getInterfaceUrl();
			String param = JsonUtil.getJsonString4List(devices);
			HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
			int returnCode = wrapper.getHttpStatus();
			if (returnCode == 200) {
				String rsp = wrapper.getResponse();
				JSONObject object = JSONObject.fromObject(rsp);
				String result = object.getString("result");
				if (StringUtils.equalsIgnoreCase(result, "true")) {
					updateDeviceSyncType(devices, SyncType.SYNCED);
				}
				updateDeviceSyncType(devices, SyncType.FAILED);
			}
		}
	}

	private void updateDeviceSyncType(List<Device> devices, SyncType type) {
		for (Device device : devices) {
			device.setIsSync(type);
			this.updateDevice(device);
		}
	}

	/*
	 * @Override public String bindDeviceGroup(String id, String from, String
	 * to) { String[] ids; if (id.indexOf(",") > 0) { ids = id.split(","); }
	 * else { ids = new String[]{id}; } boolean isFromStartWithZero =
	 * from.startsWith("0"); long _from = Long.parseLong(from); long _to =
	 * Long.parseLong(to); StringBuilder sb = null; for (long i = _from; i <=
	 * _to; i++) { String code = "0" + i; if (!isFromStartWithZero) { code =
	 * String.valueOf(i); } Device device =
	 * this.deviceRepository.getDeviceByCode(code); if (device != null) { for
	 * (String _id : ids) { DeviceGroupMap dgm =
	 * this.deviceRepository.getByCodeAndGroup(code, Long.parseLong(_id)); if
	 * (dgm == null) { dgm = new DeviceGroupMap(); dgm.setDeviceCode(code);
	 * dgm.setDeviceGroupId(Long.parseLong(_id)); dgm.setCreateDate(new Date());
	 * this.deviceRepository.updateDeviceGroupMap(dgm); } } } else { if (sb ==
	 * null) { sb = new StringBuilder(); } sb.append("设备Code：" + code +
	 * "不存在!<br/>"); } } if (sb != null &&
	 * StringUtils.isNotBlank(sb.toString())) { return sb.toString(); } return
	 * null; }
	 */
	@Override
	public String bindDeviceGroupMap(String id, String ystenId) {
		String[] ids;
		String[] ystenIds = new String[0];
		StringBuilder sb = new StringBuilder();
		if (id.indexOf(",") > 0) {
			ids = id.split(",");
		} else {
			ids = new String[] { id };
		}
		if (StringUtils.isNotBlank(ystenId)) {
			ystenIds = ystenId.split(",");
			for (String ystId : ystenIds) {
				if (StringUtils.isNotEmpty(ystId)) {
					Device device = this.deviceRepository
							.getDeviceByYstenId(ystId);
					if (device != null) {
						for (String _id : ids) {
							DeviceGroupMap dgm = this.deviceRepository
									.getByCodeAndGroup(ystId,
											Long.parseLong(_id));
							if (dgm == null) {
								dgm = new DeviceGroupMap();
								dgm.setYstenId(ystId);
								dgm.setDeviceGroupId(Long.parseLong(_id));
								dgm.setCreateDate(new Date());
								this.deviceRepository.updateDeviceGroupMap(dgm);
							}
						}
					}
				}
			}
		}
		sb.append(Constants.SUCCESS);
		return sb.toString();
	}

	@Override
	public int getCountByIsSync() {
		return this.deviceRepository.getCountByIsSync();
	}

	@Override
	public List<Device> findNeedSyncDevices(Integer num) {
		return this.deviceRepository.getAllDeviceByStateChange(num);
	}

	@Override
	public String deleteDevice(List<Long> deviceIds) {
		int i = 0;
		String str = "";
		for (Long deviceId : deviceIds) {
			Device device = this.deviceRepository.getDeviceById(deviceId);
			List<DeviceCustomerAccountMap> map = this.deviceCustomerAccountMapRepository
					.getDeviceCustomerAccountMapByDeviceId(deviceId);
			List<DeviceGroupMap> deviceGroupMap = this.deviceGroupMapRepository
					.getByYstenId(device.getYstenId());
			List<DeviceNoticeMap> noticeMap = this.sysNoticeRepository
					.findSysNoticeMapByYstenId(device.getYstenId());
			AnimationDeviceMap animationMap = this.bootAnimationRepository
					.findMapByYstenId(device.getYstenId(), "DEVICE");
			List<DeviceUpgradeMap> upgradeMap = this.upgradeTaskRepository
					.findMapByYstenId(device.getYstenId());
			List<AppUpgradeMap> appUpgradeMap = this.appUpgradeMapRepository
					.findAppUpgradeMapByDeviceCode(device.getCode());
			List<BackgroundImageDeviceMap> backgroundMap = this.backgroundImageRepository
					.findMapByYstenId(device.getYstenId());
			PanelPackageDeviceMap panelMap = this.panelPackageDeviceMapRepository
					.getPanelDeviceMapByYstenId(device.getYstenId());
			List<ServiceCollectDeviceGroupMap> ServiceCollectMap = this.serviceCollectDeviceGroupMapRepository
					.getServiceCollectGroupmapByYstenId(device.getYstenId());
			if (map != null && map.size() > 0) {
				str += "易视腾编号为:" + device.getYstenId() + "的设备已绑定用户，无法删除！";
			}
			if (deviceGroupMap != null && deviceGroupMap.size() > 0) {
				str += "易视腾编号为:" + device.getYstenId() + "的设备已绑定设备分组，无法删除！";
			}
			if (animationMap != null) {
				str += "易视腾编号为:" + device.getYstenId() + "的设备已绑定动画，无法删除！";
			}
			if (noticeMap != null && noticeMap.size() > 0) {
				str += "易视腾编号为:" + device.getYstenId() + "的设备已绑定已绑定通知，无法删除！";
			}
			if (upgradeMap != null && upgradeMap.size() > 0) {
				str += "易视腾编号为:" + device.getYstenId() + "的设备已绑定终端升级任务，无法删除！";
			}
			if (appUpgradeMap != null && appUpgradeMap.size() > 0) {
				str += "易视腾编号为:" + device.getYstenId() + "的设备已绑定应用升级任务，无法删除！";
			}
			if (backgroundMap != null && backgroundMap.size() > 0) {
				str += "易视腾编号为:" + device.getYstenId() + "的设备已绑定背景轮换，无法删除！";
			}
			if (panelMap != null) {
				str += "易视腾编号为:" + device.getYstenId() + "的设备已绑定PANEL面板组，无法删除！";
			}
			if (ServiceCollectMap != null && ServiceCollectMap.size() > 0) {
				str += "易视腾编号为:" + device.getYstenId() + "的设备已绑定服务集合，无法删除！";
			} else if (map.size() == 0 && appUpgradeMap.size() == 0
					&& backgroundMap.size() == 0 && panelMap == null
					&& deviceGroupMap.size() == 0 && animationMap == null
					&& noticeMap.size() == 0 && upgradeMap.size() == 0
					&& ServiceCollectMap.size() == 0) {
				// this.deviceRepository.deleteDeviceById(device);
				this.deviceRepository.deleteDevice(device);
				DeviceHistory deviceHistory = new DeviceHistory(device);
				deviceHistory.setHistoryCreateDate(new Date());
				deviceHistory.setState(State.NONACTIVATED);
				deviceHistory.setBindType(BindType.UNBIND);
				deviceHistory.setIsLock(LockType.UNLOCKED);
				this.DeviceHistoryRepository.save(deviceHistory);
				i++;
			}
		}
		if (i == deviceIds.size()) {
			return Constants.SUCCESS;
		} else {
			return str;
		}
	}


    @Override
    public void deleteDeviceByBusiness(String ystenIds,Long bussinessId, String character,String tableName,String type,String device){
        List<String> ystenList= new ArrayList<String>();
        if(StringUtils.isNotEmpty(ystenIds)){
           ystenList = StringUtil.split(ystenIds);
        }else{
            LOGGER.debug("########### ystenList ################"+ystenList);
            ystenList = null;
        }
        if(Constant.BSS_DEVICE_SERVICE_COLLECT_MAP.equals(tableName)){
            if(ystenList==null){
                this.serviceCollectDeviceGroupMapRepository.deleteServiceCollectGroupBoundDCByServiceCollectId(bussinessId);
            }else{
            this.serviceCollectDeviceGroupMapRepository.deleteServiceCollectGroupmapByGroupIdsAndYstenIds(null, ystenList);
            }
        }else{
         this.deviceRepository.deleteDeviceByBusiness(ystenList,bussinessId,character,tableName,type,device);
        }
    }

	@Override
	public boolean deleteDeviceBusiness(List<Long> idsList) {
		for (Long id : idsList) {
			Device device = this.deviceRepository.getDeviceById(id);
			this.sysNoticeRepository.deleteSysNoticeMapByYstenId(device
					.getYstenId());
			this.bootAnimationRepository.deleteAnimationDeviceMapByCode(device
					.getYstenId());
			this.upgradeTaskRepository.deleteUpgradeTaskMapByCode(device
					.getYstenId());
			this.appUpgradeMapRepository.deleteAppUpgradeMapByCode(device
					.getCode());
            this.apkUpgradeMapRepository.deleteApkUpgradeMapByYstenId(device.getYstenId());

			this.backgroundImageRepository
					.deleteBackGroundImageMapByDeviceCode(device.getYstenId());
			this.panelPackageDeviceMapRepository
					.deletePanelDeviceMapByCode(device.getYstenId());
            this.serviceCollectDeviceGroupMapRepository.deleteServiceCollectGroupmapByDeviceCode(device.getYstenId());
		}
		return true;
	}

	@Override
	public String validatorSno(String snos) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(snos)) {
			String[] _snos = snos.split(",");
			for (int i = 0; i < _snos.length; i++) {
				Device device = this.deviceRepository
						.getDeviceBySno(FilterSpaceUtils.replaceBlank(_snos[i]));
				if (device == null) {
					if (sb.indexOf("序列号为: ") < 0) {
						sb.append("序列号为: ");
					}
					sb.append("[" + _snos[i] + "] ");
				}
				if (i == _snos.length - 1 && sb.indexOf("序列号为: ") == 0) {
					sb.append("的设备不存在，请确认!");
					return sb.toString();
				}
			}
		}
		sb.append(Constants.SUCCESS);
		return sb.toString();
	}

	@Override
	public Pageable<Device> findDevicesBySnos(String snos, int pageNo,
			int pageSize) {
		String sno = "";
		if (StringUtils.isNotBlank(snos)) {
			String[] _snos = snos.split(",");
			for (int i = 0; i < _snos.length; i++) {
				if (i == _snos.length - 1) {
					sno += "'" + FilterSpaceUtils.replaceBlank(_snos[i]) + "'";
				} else {
					sno += "'" + FilterSpaceUtils.replaceBlank(_snos[i]) + "',";
				}
			}
		}
		return this.deviceRepository.findDevicesBySnos(sno, pageNo, pageSize);
	}

	@Override
	public Map<String, Object> findDeviceGroupByYstenIdAndArea(String ystenId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Device device = this.deviceRepository.getDeviceByYstenId(ystenId);
		if (StringUtils.isNotBlank(ystenId)) {
			Long areaId = (device.getArea() == null ? null : device.getArea()
					.getId());
			List<DeviceGroup> mapList = this.deviceGroupRepository
					.findDeviceGroupByYstenIdAndArea(ystenId, areaId);
			if (mapList != null && mapList.size() > 0) {
				StringBuilder aniGroupId = new StringBuilder("");
				StringBuilder appGroupId = new StringBuilder("");
				StringBuilder backGroupId = new StringBuilder("");
				StringBuilder bootGroupId = new StringBuilder("");
				// StringBuilder commGroupId = new StringBuilder("");
				StringBuilder panelGroupId = new StringBuilder("");
				StringBuilder noticeGroupId = new StringBuilder("");
				StringBuilder upgradeGroupId = new StringBuilder("");
				for (DeviceGroup deviceGroup : mapList) {
					if (DeviceGroupType.ANIMATION.equals(deviceGroup.getType())) {
						if (StringUtils.isBlank(aniGroupId.toString())) {
							aniGroupId.append(deviceGroup.getId());
						} else {
							aniGroupId.append(",").append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.APPUPGRADE.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(appGroupId.toString())) {
							appGroupId.append(deviceGroup.getId());
						} else {
							appGroupId.append(",").append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.BACKGROUND.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(backGroupId.toString())) {
							backGroupId.append(deviceGroup.getId());
						} else {
							backGroupId.append(",").append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.BOOTSTRAP.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(bootGroupId.toString())) {
							bootGroupId.append(deviceGroup.getId());
						} else {
							bootGroupId.append(",").append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.PANEL.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(panelGroupId.toString())) {
							panelGroupId.append(deviceGroup.getId());
						} else {
							panelGroupId.append(",")
									.append(deviceGroup.getId());
						}
					} else if (DeviceGroupType.NOTICE.equals(deviceGroup
							.getType())) {
						if (StringUtils.isBlank(noticeGroupId.toString())) {
							noticeGroupId.append(deviceGroup.getId());
						} else {
							noticeGroupId.append(",").append(
									deviceGroup.getId());
						}
					}
					/**
					 * 暂时无此类型的需求 else if
					 * (DeviceGroupType.COMMON.equals(deviceGroup.getType())) {
					 * if (StringUtils.isBlank(commGroupId.toString())) {
					 * commGroupId.append(deviceGroup.getId()); } else {
					 * commGroupId.append(",").append(deviceGroup.getId()); } }
					 */
					else {
						if (StringUtils.isBlank(upgradeGroupId.toString())) {
							upgradeGroupId.append(deviceGroup.getId());
						} else {
							upgradeGroupId.append(",").append(
									deviceGroup.getId());
						}
					}
				}
				map.put(DeviceGroupType.ANIMATION.toString(), aniGroupId);
				map.put(DeviceGroupType.APPUPGRADE.toString(), appGroupId);
				map.put(DeviceGroupType.BACKGROUND.toString(), backGroupId);
				map.put(DeviceGroupType.BOOTSTRAP.toString(), bootGroupId);
				// map.put(DeviceGroupType.COMMON.toString(), commGroupId);
				map.put(DeviceGroupType.NOTICE.toString(), noticeGroupId);
				map.put(DeviceGroupType.PANEL.toString(), panelGroupId);
				map.put(DeviceGroupType.UPGRADE.toString(), upgradeGroupId);
				// return "{\"" + DeviceGroupType.ANIMATION + ":\"" +
				// aniGroupId.toString() + "\",\"" + DeviceGroupType.NOTICE +
				// ":\"" + noticeGroupId.toString() + "\" }";
				return map;
			}
		}
		return null;
	}

    @Override
    public Map<String, Object> findDeviceRelateBusinessByYstenIdOrGroupId(String ystenId, Long groupId){
        Map<String, Object> map = new HashMap<String, Object>();
        if ( (StringUtils.isNotBlank(ystenId) && null==groupId)||(null==ystenId && null!=groupId) ) {
            String bootAnimationId = "";
            String appupgradeId = "";
            String serviceCollectId = "";
            String panelPackageId = "";
            StringBuilder noticeId = new StringBuilder("");
            StringBuilder backgroundImageId = new StringBuilder("");
            StringBuilder upgradeId = new StringBuilder("");
            StringBuilder apkUpgradeId = new StringBuilder("");

            List<UpgradeTask> upgradeTaskList = this.upgradeTaskRepository.findUpgradeTaskByYstenIdOrGroupId(ystenId, groupId);
            if(!CollectionUtils.isEmpty(upgradeTaskList)){
                for(UpgradeTask upgradeTask : upgradeTaskList){
                    upgradeId.append(upgradeTask.getId().toString());
                    upgradeId.append(",");
                }
                upgradeId = upgradeId.deleteCharAt(upgradeId.length()-1);
            }

            List<ApkUpgradeTask> apkUpgradeTaskList = this.apkUpgradeTaskRepository.findUpgradeTaskByYstenIdOrGroupId(ystenId, groupId);
            if(!CollectionUtils.isEmpty(apkUpgradeTaskList)){
                for(ApkUpgradeTask apkUpgradeTask : apkUpgradeTaskList){
                    apkUpgradeId.append(apkUpgradeTask.getId().toString());
                    apkUpgradeId.append(",");
                }
                apkUpgradeId = apkUpgradeId.deleteCharAt(apkUpgradeId.length()-1);
            }

            List<SysNotice> sysNoticeList = this.sysNoticeRepository.findSysNoticeByYstenIdOrGroupId(ystenId, groupId);
            if(!CollectionUtils.isEmpty(sysNoticeList)){
                for(SysNotice sysNotice : sysNoticeList){
                    noticeId.append(sysNotice.getId().toString());
                    noticeId.append(",");
                }
                noticeId = noticeId.deleteCharAt(noticeId.length()-1);
            }

            List<BackgroundImage> backgroundImageList = this.backgroundImageRepository.findBackgroundImageByYstenIdOrGroupId(ystenId, groupId);
            if( !CollectionUtils.isEmpty(backgroundImageList) ){
                for(BackgroundImage backgroundImage : backgroundImageList){
                    backgroundImageId.append(backgroundImage.getId().toString());
                    backgroundImageId.append(",");
                }
                backgroundImageId =  backgroundImageId.deleteCharAt(backgroundImageId.length() - 1);
            }

            BootAnimation bootAnimation = this.bootAnimationRepository.getBootAnimationByYstenIdOrGroupId(ystenId, groupId);
            if( null != bootAnimation ){
                bootAnimationId = bootAnimation.getId().toString();
            }

            ServiceCollect serviceCollect = this.serviceCollectRepository.getServiceCollectByYstenIdOrGroupId(ystenId, groupId);
            if( null != serviceCollect ){
                serviceCollectId = serviceCollect.getId().toString();
            }

            PanelPackage panelPackage = this.panelPackageRepository.getPanelPackageByYstenIdOrGroupId(ystenId, groupId);
            if( null != panelPackage ){
                panelPackageId = panelPackage.getId().toString();
            }

            map.put(DeviceGroupType.ANIMATION.toString(), bootAnimationId);
            map.put(DeviceGroupType.APPUPGRADE.toString(), appupgradeId);
            map.put(DeviceGroupType.BACKGROUND.toString(), backgroundImageId);
            map.put(DeviceGroupType.BOOTSTRAP.toString(), serviceCollectId);
            map.put(DeviceGroupType.NOTICE.toString(), noticeId);
            map.put(DeviceGroupType.PANEL.toString(), panelPackageId);
            map.put(DeviceGroupType.UPGRADE.toString(), upgradeId);
            map.put(DeviceGroupType.APKUPGRADE.toString(), apkUpgradeId);

            return map;
        }
        return null;
    }

	@Override
	public Map<String, Object> createDeviceYstenId(List<Device> deviceList)
			throws Exception {
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		map.put("isSuccess", true);
		isFinished = false;
		// 获取cpu各数，也即同时启动线程数
		int threadsNum = Runtime.getRuntime().availableProcessors();
		ExecutorService es = Executors.newFixedThreadPool(threadsNum);
		CyclicBarrier barrier = new CyclicBarrier(threadsNum, new Runnable() {
			@Override
			public void run() {
				isFinished = true;
			}
		});
		int threadSize = deviceList.size() / threadsNum;
		if (threadSize == 0) {
			barrier = new CyclicBarrier(1, new Runnable() {
				@Override
				public void run() {
					isFinished = true;
				}
			});
			es.execute(new CreateYstenIdJob(barrier, deviceList, 0, deviceList
					.size(), map, deviceRepository, cityRepository,
					areaRepository, configRepository));
		} else {
			for (int i = 0; i < threadsNum; i++) {
				if (i == threadsNum - 1) {
					es.execute(new CreateYstenIdJob(barrier, deviceList, 0,
							deviceList.size(), map, deviceRepository,
							cityRepository, areaRepository, configRepository));
				} else {
					es.execute(new CreateYstenIdJob(barrier, deviceList, 0,
							deviceList.size(), map, deviceRepository,
							cityRepository, areaRepository, configRepository));
				}
			}
		}
		while (!isFinished) {
			Thread.sleep(1000);
		}
		return map;
	}

	@Override
	public List<Device> findDevicesOfBlankYsteId() {
		return this.deviceRepository.findDevicesOfBlankYsteId();
	}

	@Override
	public List<Long> findAreaByBusiness(Long Id, String character,
			String tableName) {
		return this.deviceRepository.findAreaByBusiness(Id, character,
				tableName);
	}

	@Override
	public List<Device> findDeviceYstenIdListBySnos(String snos) {
		String sno = "";
		if (StringUtils.isNotBlank(snos)) {
			String[] _snos = snos.split(",");
			for (int i = 0; i < _snos.length; i++) {
				if (i == _snos.length - 1) {
					sno += "'" + FilterSpaceUtils.replaceBlank(_snos[i]) + "'";
				} else {
					sno += "'" + FilterSpaceUtils.replaceBlank(_snos[i]) + "',";
				}
			}
		}
		return this.deviceRepository.findDevicesBySnos(sno);
	}

	@Override
	public String groupBindDeviceMap(String id, String ystenId) {
		String[] _ystenIds = new String[0];
		StringBuilder jy = new StringBuilder();
		StringBuilder ystenIds = new StringBuilder();
		String[] ids;
		if (id.indexOf(",") > 0) {
			ids = id.split(",");
		} else {
			ids = new String[] { id };
		}
          List<String> ystList = new ArrayList<String>();
		if (StringUtils.isNotBlank(ystenId)) {
			_ystenIds = ystenId.split(",");
			for (int i = 0; i < _ystenIds.length; i++) {
				if (StringUtils.isEmpty(_ystenIds[i])) {
					continue;
				}
				_ystenIds[i] = FilterSpaceUtils.replaceBlank(_ystenIds[i]);
                      ystList.add(_ystenIds[i]);
			}
		}
//		double start = System.currentTimeMillis();
		List<Device> deviceList = this.deviceRepository
				.findDeviceListByYstenIds(_ystenIds);
		if (deviceList.size() == 0) {
			jy.append("设备不合法：未能找到你所上传的所有易视腾编号的设备，请确认!");
			String space="";
			return "{\"info\":\"" + jy.toString() + "\",\"ystenIds\":\"" + space + "\"}";
		}
		String[] ystIds = new String[deviceList.size()];
		if (deviceList.size() > 0 && deviceList.size() != ystList.size()) {
			for (int i = 0; i < ystList.size(); i++) {
				boolean bool = false;
				for (Device device : deviceList) {
					if (device.getYstenId().equals(ystList.get(i))) {
						bool = true;
						break;
					}
				}
				if (bool == false) {
					if (jy.length() <= 0) {
						jy.append("未能找到易视腾编号为："+ ystList.get(i));
					}else{
						jy.append(","+ ystList.get(i));
					}

				}
			}
			if (jy.length() > 0){
				jy.append("的设备，请确认!  ");
			}
		}
//		double end = System.currentTimeMillis();
//		System.out.println("检验设备存在：" + (end - start));
//		double start1 = System.currentTimeMillis();
		for (String _id : ids) {
			DeviceGroup group = this.deviceGroupRepository
					.selectByPrimaryKey(Long.parseLong(_id));
			DeviceGroupType type = group.getType();
			String typeName = group.getType().getDisplayName();
			int n = 0;
			StringBuilder qy = new StringBuilder();
			for (Device device : deviceList) {
				if (device.getArea() != null&& !device.getArea().getId().equals(group.getAreaId())) {
					if(qy.length() <= 0){
						qy.append("易视腾编号：" + device.getYstenId());
					}else{
						qy.append("," + device.getYstenId());
					}
				} else {
					ystIds[n] = device.getYstenId();
					n++;
				}
			}
			if(qy.length() > 0){
				jy.append(qy.toString()+" 的所属区域 与要绑定分组的所属区域不符!  ");
			}
//			double end1 = System.currentTimeMillis();
//			System.out.println("检验设备与分组区域：" + (end1 - start1));

			if (ystIds != null) {
				List<String> ysten = new ArrayList<String>();
				List<String> code = new ArrayList<String>();
				for (String yst : ystIds) {
					if(yst!=null){
						ysten.add(yst);
					}
				}
				double start2 = System.currentTimeMillis();
				if (ysten.size() > 0) {
				List<DeviceGroup> deviceGroupList = this.deviceGroupRepository
						.findDeviceGroupMapByYstenIdAndType(ystIds,
								type.toString());
				if (deviceGroupList != null && deviceGroupList.size() > 0) {
					StringBuilder lx = new StringBuilder();
					for (DeviceGroup deviceGroup : deviceGroupList) {
						if (deviceGroup.getId() != group.getId()) {
							if(lx.length() <= 0){
								lx.append("易视腾编号：" + deviceGroup.getYstenId());
							}else{
								lx.append("," +  deviceGroup.getYstenId());
							}
							code.add(deviceGroup.getYstenId());
						}
						ysten.remove(deviceGroup.getYstenId());
					}
					if(lx.length() > 0){
						jy.append(lx.toString()+ " 绑定过类型为" + typeName + "的设备分组!  ");
					}
				  }
				}
//				double end2 = System.currentTimeMillis();
//				System.out.println("检验设备绑的分组类型是否重复：" + (end2 - start2));

				if (code.size() > 0) {
					for (String y : code) {
						ystenIds.append(y).append(",");
					}
				}

				if (ysten.size() > 0) {
					List<DeviceGroupMap> maps = new ArrayList<DeviceGroupMap>();
					int index = 0;
					for (String y : ysten) {
						DeviceGroupMap dgm = new DeviceGroupMap();
						dgm.setYstenId(y);
						dgm.setDeviceGroupId(Long.parseLong(_id));
						dgm.setCreateDate(new Date());
						maps.add(dgm);
						index++;
						if (index % Constants.BULK_NUM == 0) {
							this.deviceRepository.BulkSaveDeviceGroupMap(maps);
							maps.clear();
						}
					}
					if (maps.size() > 0) {
						this.deviceRepository.BulkSaveDeviceGroupMap(maps);
						maps.clear();
					}
				}

//				double end3 = System.currentTimeMillis();
//				System.out.println("保存绑定：" + (end3 - end2));
			}

		}
		String ysts = ystenIds.length()>0 ? ystenIds.substring(0, ystenIds.length() - 1).toString() : "";
		if (StringUtils.isNotBlank(jy.toString())) {
			System.out.println(jy.toString());
		}

		String info = jy.length()>0 ? jy.toString() : "关联的设备合法,且关联成功!";
		return  "{\"info\":\"" + info + "\",\"ystenIds\":\"" + ysts + "\"}";
//		if (StringUtils.isNotBlank(jy.toString())) {
//			return jy.toString();
//		}
//		StringBuilder sb = new StringBuilder();
//		sb.append(Constants.SUCCESS);
//		return sb.toString();
	}

	@Override
	public String validatorDeviceCode(String deviceCodes) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(deviceCodes)) {
			String[] _deviceCodes = deviceCodes.split(",");
			for (int i = 0; i < _deviceCodes.length; i++) {
				// Device device =
				// this.deviceRepository.getDeviceBySno(FilterSpaceUtils.replaceBlank(_deviceCodes[i]));
				Device device = this.deviceRepository
						.getDeviceByCode(FilterSpaceUtils
								.replaceBlank(_deviceCodes[i]));
				if (device == null) {
					if (sb.indexOf("设备编号为: ") < 0) {
						sb.append("设备编号为: ");
					}
					sb.append("[" + _deviceCodes[i] + "] ");
				}
				if (i == _deviceCodes.length - 1 && sb.indexOf("设备编号为: ") == 0) {
					sb.append("的设备不存在，请确认!");
					return sb.toString();
				}
			}
		}
		sb.append(Constants.SUCCESS);
		return sb.toString();
	}

    @Override
    public String saveDeviceBusinessBind(String ids, String bootId, String animationId, String panelId,
                                         String noticeIds, String backgroundIds, String upgradeIds, String apkUpgradeIds) {
        String message = "";
        List<Long> idsList = StringUtil.splitToLong(ids);
        if(!CollectionUtils.isEmpty(idsList)){
            this.deleteDeviceBusiness(idsList);
        }

        Device device = this.deviceRepository.getDeviceById(idsList.get(0));
        if(null != device){
            String ystenId = device.getYstenId();

            if(StringUtils.isNotBlank(noticeIds)){
                List<Long> noticeIdList = StringUtil.splitToLong(noticeIds);
                for (Long noticeId : noticeIdList) {
                    this.saveNoticeDeviceMap(noticeId, "DEVICE", null, ystenId);
                }
            }
            if(StringUtils.isNotBlank(upgradeIds)){
                List<Long> upgradeList = StringUtil.splitToLong(upgradeIds);
                for (Long upgradeId : upgradeList) {
                    this.saveUpgradeDeviceMap(upgradeId, "DEVICE", null, ystenId);
                }
            }
            if(StringUtils.isNotBlank(apkUpgradeIds)){
                List<Long> apkUpgradeList = StringUtil.splitToLong(apkUpgradeIds);
                for (Long apkUpgradeId : apkUpgradeList) {
                    this.saveApkUpgradeDeviceMap(apkUpgradeId, "DEVICE", null, ystenId);
                }
            }
            if(StringUtils.isNotBlank(backgroundIds)){
                List<Long> backgroundList = StringUtil.splitToLong(backgroundIds);
                for(Long backgroundId : backgroundList){
                    this.saveBackGroundDeviceMap(backgroundId, "DEVICE", null, ystenId);
               } 
            }

            if(StringUtils.isNotBlank(bootId)){
                this.saveServiceCollectDeviceMap(Long.parseLong(bootId), null, ystenId);
            }
            if(StringUtils.isNotBlank(animationId)){
                this.saveAnimationDeviceMap(Long.parseLong(animationId), "DEVICE", null, ystenId);
            }
            if(StringUtils.isNotBlank(panelId)){
                this.savePanelPackageDeviceMap(Long.parseLong(panelId), "DEVICE", null, ystenId);
            }
        }

        message = "终端绑定业务成功!";
        return message;
    }

    @Override
    public String saveGroupBusinessBind(String ids, String bootId, String animationId, String panelId,
                                        String noticeIds, String backgroundIds, String upgradeIds, String apkUpgradeIds) {
        String message = "";
        List<Long> idsList = StringUtil.splitToLong(ids);
        if(!CollectionUtils.isEmpty(idsList)){
            this.deleteDeviceGroupBusiness(idsList);
        }

        DeviceGroup deviceGroup = this.deviceGroupRepository.getDeviceGroupByGroupId(idsList.get(0));
        if(null != deviceGroup){
            Long groupId = deviceGroup.getId();
            int type = deviceGroup.getType().ordinal();

            if(type == DeviceGroupType.NOTICE.ordinal()){
                if(StringUtils.isNotBlank(noticeIds)){
                    List<Long> noticeIdList = StringUtil.splitToLong(noticeIds);
                    for (Long noticeId : noticeIdList) {
                        this.saveNoticeDeviceMap(noticeId, "GROUP", groupId, null);
                    }
                }
            }
            if(type == DeviceGroupType.UPGRADE.ordinal()){
                if(StringUtils.isNotBlank(upgradeIds)){
                    List<Long> upgradeList = StringUtil.splitToLong(upgradeIds);
                    for (Long upgradeId : upgradeList) {
                        this.saveUpgradeDeviceMap(upgradeId, "GROUP", groupId, null);
                    }
                }
            }

            if(type == DeviceGroupType.APKUPGRADE.ordinal()){
                if(StringUtils.isNotBlank(apkUpgradeIds)){
                    List<Long> apkUpgradeList = StringUtil.splitToLong(apkUpgradeIds);
                    for (Long apkUpgradeId : apkUpgradeList) {
                        this.saveApkUpgradeDeviceMap(apkUpgradeId, "GROUP", groupId, null);
                    }
                }
            }

            if(type == DeviceGroupType.BACKGROUND.ordinal()){
                if(StringUtils.isNotBlank(backgroundIds)){
                    List<Long> backgroundList = StringUtil.splitToLong(backgroundIds);
                    for (Long backgroundId : backgroundList) {
                        this.saveBackGroundDeviceMap(backgroundId, "GROUP", groupId, null);
                    }
                }
            }
            if(type == DeviceGroupType.BOOTSTRAP.ordinal()){
                if(StringUtils.isNotBlank(bootId)){
                    this.saveServiceCollectDeviceMap(Long.parseLong(bootId), groupId, null);
                }
            }
            if(type == DeviceGroupType.ANIMATION.ordinal()){
                if(StringUtils.isNotBlank(animationId)){
                    this.saveAnimationDeviceMap(Long.parseLong(animationId), "GROUP", groupId, null);
                }
            }
            if(type == DeviceGroupType.PANEL.ordinal()){
                if(StringUtils.isNotBlank(panelId)){
                    this.savePanelPackageDeviceMap(Long.parseLong(panelId), "GROUP", groupId, null);
                }
            }
        }

        message = "分组绑定业务成功!";
        return message;
    }


    private boolean saveUpgradeDeviceMap(Long upgradeId, String type, Long deviceGroupId, String ystenId) {
        DeviceUpgradeMap deviceUpgradeMap = new DeviceUpgradeMap();
        deviceUpgradeMap.setCreateDate(new Date());
        deviceUpgradeMap.setType(type);
        deviceUpgradeMap.setDeviceGroupId(deviceGroupId);
        deviceUpgradeMap.setYstenId(ystenId);
        deviceUpgradeMap.setUpgradeId(upgradeId);

        return this.upgradeTaskRepository.saveDeviceUpgradeMap(deviceUpgradeMap);
    }

    private boolean saveApkUpgradeDeviceMap(Long apkUpgradeId, String type, Long deviceGroupId, String ystenId) {
        ApkUpgradeMap apkUpgradeMap = new ApkUpgradeMap();
        apkUpgradeMap.setCreateDate(new Date());
        apkUpgradeMap.setType(type);
        apkUpgradeMap.setDeviceGroupId(deviceGroupId);
        apkUpgradeMap.setYstenId(ystenId);
        apkUpgradeMap.setUpgradeId(apkUpgradeId);

        return this.apkUpgradeMapRepository.saveApkUpgradeMap(apkUpgradeMap);
    }

    private boolean saveServiceCollectDeviceMap(Long serviceCollectId, Long deviceGroupId, String ystenId){
        ServiceCollectDeviceGroupMap serviceCollectDeviceGroupMap = new ServiceCollectDeviceGroupMap();
        serviceCollectDeviceGroupMap.setServiceCollectId(serviceCollectId);
        serviceCollectDeviceGroupMap.setDeviceGroupId(deviceGroupId);
        serviceCollectDeviceGroupMap.setYstenId(ystenId);
        this.serviceCollectDeviceGroupMapRepository.saveServiceCollectDeviceGroupMap(serviceCollectDeviceGroupMap);
        return true;
    }

    private boolean saveBackGroundDeviceMap(Long backgroundImageId, String type, Long deviceGroupId, String ystenId){
        BackgroundImageDeviceMap backgroundImageDeviceMap = new BackgroundImageDeviceMap();
        backgroundImageDeviceMap.setBackgroundImageId(backgroundImageId);
        backgroundImageDeviceMap.setType(type);
        backgroundImageDeviceMap.setYstenId(ystenId);
        backgroundImageDeviceMap.setDeviceGroupId(deviceGroupId);
        backgroundImageDeviceMap.setCreateDate(new Date());
        return this.backgroundImageRepository.saveBackgroundImageDeviceMap(backgroundImageDeviceMap);
    }

    private boolean savePanelPackageDeviceMap(Long panelPackageId, String type, Long deviceGroupId, String ystenId){
        PanelPackageDeviceMap panelPackageDeviceMap = new PanelPackageDeviceMap();
        panelPackageDeviceMap.setPanelPackageId(panelPackageId);
        panelPackageDeviceMap.setDeviceGroupId(deviceGroupId);
        panelPackageDeviceMap.setCreateDate(new Date());
        panelPackageDeviceMap.setYstenId(ystenId);
        panelPackageDeviceMap.setType(type);
        return this.panelPackageDeviceMapRepository.saveDeviceMap(panelPackageDeviceMap);
    }

    private boolean saveNoticeDeviceMap(Long sysNoticeId, String type, Long deviceGroupId, String ystenId) {
        DeviceNoticeMap deviceNoticeMap = new DeviceNoticeMap();
        deviceNoticeMap.setCreateDate(new Date());
        deviceNoticeMap.setType(type);
        deviceNoticeMap.setDeviceGroupId(deviceGroupId);
        deviceNoticeMap.setYstenId(ystenId);
        deviceNoticeMap.setNoticeId(sysNoticeId);
        return sysNoticeRepository.saveDeviceNoticeMap(deviceNoticeMap);
    }

    private boolean saveAnimationDeviceMap(Long bootAnimationId, String type, Long groupId, String ystenId) {
        AnimationDeviceMap map = new AnimationDeviceMap();
        map.setCreateDate(new Date());
        map.setType(type);
        map.setDeviceGroupId(groupId);
        map.setBootAnimationId(bootAnimationId);
        map.setYstenId(ystenId);
        return this.bootAnimationRepository.saveAnimationDeviceMap(map);
    }

    @Override
	public List<Device> findDeviceYstenIdListByDeviceCodes(String deviceCodes) {
		String deviceCode = "";
		if (StringUtils.isNotBlank(deviceCodes)) {
			String[] _deviceCodes = deviceCodes.split(",");
			for (int i = 0; i < _deviceCodes.length; i++) {
				if (i == _deviceCodes.length - 1) {
					deviceCode += "'"
							+ FilterSpaceUtils.replaceBlank(_deviceCodes[i])
							+ "'";
				} else {
					deviceCode += "'"
							+ FilterSpaceUtils.replaceBlank(_deviceCodes[i])
							+ "',";
				}
			}
		}
		return this.deviceRepository
				.findDeviceYstenIdListByDeviceCodes(deviceCode);
	}

	@Override
	public void BulkSaveDeviceGroupMap(List<DeviceGroupMap> list) {
		this.deviceRepository.BulkSaveDeviceGroupMap(list);
	}

	@Override
	public String addDeviceGroupMap(String id, String ystenIds) {
		DeviceGroup group = this.deviceGroupRepository.selectByPrimaryKey(Long.parseLong(id));
		String[] ystIds = new String[0];
		List<Long> groupIds = new ArrayList<Long>();
		if(StringUtils.isNotBlank(ystenIds)){
			ystIds = ystenIds.split(",");
			for (int i = 0; i < ystIds.length; i++) {
				if (StringUtils.isEmpty(ystIds[i])) {
					break;
				}
				ystIds[i] = FilterSpaceUtils.replaceBlank(ystIds[i]);
			}
		}
		if(group != null && group.getType() != null){
			List<DeviceGroup> deviceGroupList = this.deviceGroupRepository.findDeviceGroupMapByYstenIdAndType(ystIds,group.getType().toString());
			for(DeviceGroup g:deviceGroupList){
				if(!g.getId().equals(id)){
					groupIds.add(g.getId());
				}
			}
			if(groupIds != null && groupIds.size()>0){
				this.deviceGroupMapRepository.deleteMapByYstenIdsAndGroups(ystIds, groupIds);
			}
		}
		if (ystIds.length > 0) {
			List<DeviceGroupMap> maps = new ArrayList<DeviceGroupMap>();
			int index = 0;
			for (String y : ystIds) {
				DeviceGroupMap dgm = new DeviceGroupMap();
				dgm.setYstenId(y);
				dgm.setDeviceGroupId(Long.parseLong(id));
				dgm.setCreateDate(new Date());
				maps.add(dgm);
				index++;
				if (index % Constants.BULK_NUM == 0) {
					this.deviceRepository.BulkSaveDeviceGroupMap(maps);
					maps.clear();
				}
			}
			if (maps.size() > 0) {
				this.deviceRepository.BulkSaveDeviceGroupMap(maps);
				maps.clear();
			}
		}
		return null;
	}

	@Override
	public DeviceType getDeviceTypeByTypeId(Long id) {
		return this.deviceRepository.getDeviceTypeByTypeId(id);
	}

	@Override
	public Pageable<Device> findDeviceListByConditions(Map<String, Object> map) {
		if(StringUtils.isNotBlank(map.get("deviceCodes").toString())){
			map.put("deviceCodes", this.getListByStringSplitDot(map.get("deviceCodes").toString()));
		}
		if(StringUtils.isNotBlank(map.get("ystenIds").toString())){
			map.put("ystenIds", this.getListByStringSplitDot(map.get("ystenIds").toString()));
		}
		if(StringUtils.isNotBlank(map.get("snos").toString())){
			map.put("snos", this.getListByStringSplitDot(map.get("snos").toString()));
		}
		return this.deviceRepository.findDeviceListByConditions(map);
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
	public List<Device> QueryDevicesToExport(Map<String, Object> map) {
		if(StringUtils.isNotBlank(map.get("deviceCodes").toString())){
			map.put("deviceCodes", this.getListByStringSplitDot(map.get("deviceCodes").toString()));
		}
		if(StringUtils.isNotBlank(map.get("ystenIds").toString())){
			map.put("ystenIds", this.getListByStringSplitDot(map.get("ystenIds").toString()));
		}
		if(StringUtils.isNotBlank(map.get("snos").toString())){
			map.put("snos", this.getListByStringSplitDot(map.get("snos").toString()));
		}
		return this.deviceRepository.QueryDevicesToExport(map);
	}

	@Override
	public List<Device> ExportDevicesOfGroupId(String tableName,
			String character, String deviceGroupId, String ystenId, String code,
			String mac, String sno, String softCode, String versionSeq) {
		if (StringUtils.isNotBlank(deviceGroupId)) {
			return this.deviceRepository.ExportDevicesOfGroupId(tableName, character, Long.parseLong(deviceGroupId), ystenId, code, mac, sno, softCode, versionSeq);
		}
		return null;
	}
}

class CreateYstenIdJob implements Runnable {
	private CyclicBarrier barrier;
	private List<Device> deviceList;
	private int begin;
	private int end;
	private Map<String, Object> map;
	private ICityRepository cityRepository;
	private IDeviceRepository deviceRepository;
	private IAreaRepository areaRepository;
	private IConfigRepository configRepository;

	/**
	 * private static WebApplicationContext applicationContext = null;
	 * SystemConfigService systemConfigService = (SystemConfigService)
	 * this.getBean("systemConfigService");
	 *
	 * private Object getBean(String beanName) { if (applicationContext == null)
	 * { ServletContext servletContext =
	 * ContextLoader.getCurrentWebApplicationContext().getServletContext();
	 * applicationContext =
	 * WebApplicationContextUtils.getWebApplicationContext(servletContext); }
	 * return applicationContext.getBean(beanName); }
	 */
	public CreateYstenIdJob(CyclicBarrier barrier, List<Device> deviceList,
			int begin, int end, Map<String, Object> map,
			IDeviceRepository deviceRepository, ICityRepository cityRepository,
			IAreaRepository areaRepository, IConfigRepository configRepository) {
		this.barrier = barrier;
		this.deviceList = deviceList;
		this.begin = begin;
		this.end = end;
		this.deviceRepository = deviceRepository;
		this.cityRepository = cityRepository;
		this.map = map;
		this.areaRepository = areaRepository;
		this.configRepository = configRepository;
	}

	@Override
	public void run() {
		try {
			for (int i = begin; i < end; i++) {
				String distCode = "";
				City city = this.cityRepository.getCityById(deviceList.get(i)
						.getCity().getId());
				DeviceType deviceType = this.deviceRepository
						.getDeviceTypeById(deviceList.get(i).getDeviceType()
								.getId());
				SpDefine spCode = this.configRepository
						.getSpDefineById(deviceList.get(i).getSpDefine()
								.getId());
				if (city != null) {
					distCode = city.getDistCode().substring(0, 4);
				} else {
					Area area = this.areaRepository.getAreaById(deviceList
							.get(i).getArea().getId());
					if (area != null) {
						distCode = area.getDistCode().substring(0, 4);
					}
				}
				if (spCode != null && deviceType != null
						&& !distCode.equals("")) {
					deviceList.get(i).setYstenId(
							YstenIdUtils.createYstenId(
									distCode,
									spCode.getCode(),
									deviceType.getCode(),
									deviceList.get(i).getMac()
											.replaceAll(":", "")));
				}
				// deviceList.get(i).setYstenId(YstenIdUtils.createYstenId(city.getDistCode().substring(0,
				// 4), deviceList.get(i).getMac().replaceAll(":", "")));
				try {
					boolean bool = this.deviceRepository
							.updateDevice(deviceList.get(i));
					if (!bool) {
						this.appendErrorMsg(map, "设备编号为 :"
								+ deviceList.get(i).getCode()
								+ "的设备, 获取易视腾编号失败!");
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
					this.appendErrorMsg(map, "设备编号为 :"
							+ deviceList.get(i).getCode() + "的设备, 获取易视腾编号异常!");
				}
			}
			barrier.await();
		} catch (Exception e) {
			e.printStackTrace();
			this.appendErrorMsg(map, "获取易视腾编号异常!");
		}
	}

	@SuppressWarnings("unchecked")
	private synchronized void appendErrorMsg(Map<String, Object> map,
			String error) {
		List<String> errors = (List<String>) map.get("error");
		if (errors == null) {
			errors = new ArrayList<String>();
			map.put("error", errors);
			map.put("isSuccess", false);
		}
		errors.add(error);
	}
}

class ImportJob implements Runnable {
	private CyclicBarrier barrier;
	private String[][] data;
	private int begin;
	private int end;
	private Map<String, Object> map;
	private IDeviceRepository deviceRepository;
	private IAreaRepository areaRepository;
	private IConfigRepository configRepository;
	private ICityRepository cityRepository;
	private ICustomerRepository customerRepository;

	private static WebApplicationContext applicationContext = null;
	SystemConfigService systemConfigService = (SystemConfigService) this
			.getBean("systemConfigService");

	private Object getBean(String beanName) {
		if (applicationContext == null) {
			ServletContext servletContext = ContextLoader
					.getCurrentWebApplicationContext().getServletContext();
			applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
		}
		return applicationContext.getBean(beanName);
	}

	public ImportJob(CyclicBarrier barrier, String[][] data, int begin,
			int end, Map<String, Object> map,
			IDeviceRepository deviceRepository, IAreaRepository areaRepository,
			IConfigRepository configRepository, ICityRepository cityRepository,
			ICustomerRepository customerRepository) {
		this.barrier = barrier;
		this.data = data;
		this.begin = begin;
		this.end = end;
		this.map = map;
		this.deviceRepository = deviceRepository;
		this.areaRepository = areaRepository;
		this.configRepository = configRepository;
		this.cityRepository = cityRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public void run() {
		try {
			// String threadName = Thread.currentThread().getName();
			Map<String, Object> dvCache = new HashMap<String, Object>();
			Map<String, Object> dtCache = new HashMap<String, Object>();
			Map<String, Object> areaCache = new HashMap<String, Object>();
			Map<String, Object> cityCache = new HashMap<String, Object>();
			Map<String, Object> sdCache = new HashMap<String, Object>();
			Map<String, Object> softCodeCache = new HashMap<String, Object>();
			// Map<String, Object> versionSeqCache = new HashMap<String,
			// Object>();

			for (int i = begin; i < end; i++) {
				// LOGGER.debug(threadName + "(" + i + ")" + "|" + "Row :" + (i
				// + 1));
				String[] di = data[i];
				Device device = new Device();
				// 第一列：设备编号
				if (StringUtils.isNotEmpty(di[0])) {
					device.setCode(FilterSpaceUtils.replaceBlank(di[0]));
				}
				// 第二列：设备序列号
				if (StringUtils.isNotEmpty(di[1])) {
					device.setSno(FilterSpaceUtils.replaceBlank(di[1]));
				}
				// 第三列：设备MAC
				if (StringUtils.isNotEmpty(di[2])) {
					device.setMac(FilterSpaceUtils.replaceBlank(di[2]));
				}
				// 第四列：设备厂商
				if (StringUtils.isNotEmpty(di[3])) {
					if (dvCache.get(di[3]) == null) {
						DeviceVendor dv = this.deviceRepository
								.getDeviceVendorByNameOrCode(
										FilterSpaceUtils.replaceBlank(di[3]),
										null);
						if (dv == null) {
							this.appendErrorMsg(map,
									"设备编号为 :" + device.getCode()
											+ "的设备, 设备厂商值不正确!");
							continue;
						}
						dvCache.put(FilterSpaceUtils.replaceBlank(di[3]), dv);
					}
					device.setDeviceVendor((DeviceVendor) dvCache
							.get(FilterSpaceUtils.replaceBlank(di[3])));
				}
				// 第五列：设备型号
				if (StringUtils.isNotEmpty(di[4])) {
					if (dtCache.get(di[4]) == null) {
						DeviceType dt = this.deviceRepository
								.getDeviceTypeByNameOrCode(null,
										FilterSpaceUtils.replaceBlank(di[4]),
										null);
						if (dt == null) {
							this.appendErrorMsg(map,
									"设备编号为 :" + device.getCode()
											+ "的设备, 设备型号值不正确!");
							continue;
						}
						dtCache.put(FilterSpaceUtils
								.replaceBlank(FilterSpaceUtils
										.replaceBlank(di[4])), dt);
					}
					device.setDeviceType((DeviceType) dtCache
							.get(FilterSpaceUtils.replaceBlank(di[4])));
				}
				// 第六列：区域
				if (StringUtils.isNotEmpty(di[5])) {
					if (areaCache.get(di[5]) == null) {
						Area area = this.areaRepository.getAreaByNameOrCode(
								FilterSpaceUtils.replaceBlank(di[5]), null);
						if (area == null) {
							this.appendErrorMsg(map,
									"设备编号为 :" + device.getCode()
											+ "的设备, 区域值不正确!");
							continue;
						}
						areaCache.put(FilterSpaceUtils.replaceBlank(di[5]),
								area);
					}
					device.setArea((Area) areaCache.get(FilterSpaceUtils
							.replaceBlank(di[5])));
				}
				// 第七列：
				if (StringUtils.isNotEmpty(di[6])) {
					if (cityCache.get(di[6]) == null) {
						City city = this.cityRepository
								.getCityByName(FilterSpaceUtils
										.replaceBlank(di[6]));
						if (city == null) {
							this.appendErrorMsg(map,
									"设备编号为 :" + device.getCode()
											+ "的设备, 地市值不正确!");
							continue;
						} else {
							if (city.getDistCode().equals("")) {
								this.appendErrorMsg(map, "地市为 :" + di[6]
										+ "的唯一标准编码不正确，请维护!");
								continue;
							}
						}

						cityCache.put(FilterSpaceUtils.replaceBlank(di[6]),
								city);
					}
					device.setCity((City) cityCache.get(FilterSpaceUtils
							.replaceBlank(di[6])));
					device.setDistrictCode(device.getCity().getDistCode());
				}
				// 第八列：运营商
				if (StringUtils.isNotEmpty(di[7])) {
					if (sdCache.get(di[7]) == null) {
						SpDefine sd = this.configRepository
								.getSpDefineByName(FilterSpaceUtils
										.replaceBlank(di[7]));
						if (sd == null) {
							this.appendErrorMsg(map,
									"设备编号为 :" + device.getCode()
											+ "的设备, 运营商值不正确!");
							continue;
						}
						sdCache.put(FilterSpaceUtils.replaceBlank(di[7]), sd);
					}
					device.setSpDefine((SpDefine) sdCache.get(FilterSpaceUtils
							.replaceBlank(di[7])));
				}
				// 第九列：生产批次
				if (StringUtils.isNotEmpty(di[8])) {
					device.setProductNo(FilterSpaceUtils.replaceBlank(di[8]));
				}
				// 第十列：软件号
				if (StringUtils.isNotEmpty(di[9])) {
					if (softCodeCache.get(di[9]) == null) {
						DeviceSoftwareCode dsc = this.deviceRepository
								.findDeviceSoftwareCodeByCode(FilterSpaceUtils
										.replaceBlank(di[9]));
						if (dsc == null) {
							this.appendErrorMsg(map,
									"设备编号为 :" + device.getCode()
											+ "的设备, 软件号不存在于库中，请添加!");
							continue;
						}
						softCodeCache.put(FilterSpaceUtils.replaceBlank(di[9]),
								dsc);
					}
					device.setSoftCode(FilterSpaceUtils.replaceBlank(di[9]));
				}
				// 第十一列：软件版本号
				if (StringUtils.isNotEmpty(di[10])) {
					// 软件版本号的校验暂无需
					/**
					 * if (versionSeqCache.get(di[10]) == null) {
					 * DeviceSoftwareCode dsc = (DeviceSoftwareCode)
					 * softCodeCache.get(di[9]); DeviceSoftwarePackage dsp =
					 * this.deviceRepository
					 * .findDeviceSoftwarePackageByVersionAndSoftCodeId(di[10],
					 * dsc.getId()); if (dsp == null) { this.appendErrorMsg(map,
					 * "设备编号为 :" + device.getCode() + "的设备, 软件版本号不正确!");
					 * continue; } versionSeqCache.put(di[10], di[10]); }
					 */
					device.setVersionSeq(Integer.parseInt(FilterSpaceUtils
							.replaceBlank(di[10])));
				}
				// 第十二列：描述
				if (StringUtils.isNotEmpty(di[11])) {
					device.setDescription(FilterSpaceUtils.replaceBlank(di[11]));
				}
				// 易视腾编号
				/**
				 * String ystenId = device.getArea().getCode(); ystenId +=
				 * device.getCity().getCode();
				 * device.setYstenId(this.createYstenId
				 * (device.getMac(),ystenId));
				 */

				String isCenter = this.systemConfigService
						.getSystemConfigByConfigKey("isCenter");
				// String ystenId = device.getCity().getDistCode().substring(0,
				// 4).trim().equals("")? "9999" :
				// device.getCity().getDistCode().substring(0, 4).trim();
				// ystenId +=
				// com.ysten.local.bss.bean.NumberGenerator.getNextCode();
				// device.setYstenId(YstenIdUtils.createYstenId(device.getCity().getDistCode().substring(0,
				// 4), device.getMac().replaceAll(":", "")));
				device.setYstenId(YstenIdUtils.createYstenId(device.getCity()
						.getDistCode().substring(0, 4), device.getSpDefine()
						.getCode(), device.getDeviceType().getCode(), device
						.getMac().replaceAll(":", "")));
				// device.setYstenId(ystenId);
				device.setCreateDate(new Date());
				device.setIsLock(LockType.UNLOCKED);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				device.setExpireDate(sdf.parse("2099-12-31 23:59:59"));
				device.setState(State.NONACTIVATED);
				device.setBindType(BindType.BIND);
				device.setPrepareOpen(EPrepareOpen.NOT_OPEN);
				// 中心 或 省级 导入后，同步和分发状态的初始化
				device.setIsSync(SyncType.WAITSYNC);
				if (isCenter.equals("true")) {
					device.setDistributeState(DistributeState.UNDISTRIBUTE);
				} else {
					device.setDistributeState(DistributeState.DISTRIBUTE);
				}
				try {
					Customer customer = new Customer();
					String codeNumber = NumberGenerator.getNextCode();
					customer.setUserId(codeNumber);
					customer.setCode(codeNumber);
					customer.setLoginName(codeNumber);
					customer.setNickName(codeNumber);
					customer.setRealName(codeNumber);
					customer.setCustomerType(CustomerType.PERSONAL);
					customer.setState(com.ysten.local.bss.device.domain.Customer.State.NONACTIVATED);
					customer.setArea(device.getArea());
					customer.setRegion(device.getCity());
					customer.setPhone(codeNumber);
					customer.setIdentityType(IdentityType.IDCARD);
					customer.setSource(Constant.CUSTOMER_SOURCE);
					customer.setAge(0);
					customer.setRate(Constant.CUSTOMER_RATE);
			        customer.setCreateDate(new Date());
			        customer.setIsSync(SyncType.WAITSYNC);
			        customer.setIsLock(LockType.UNLOCKED);
			        customer.setDeviceCode(device.getCode());
			        customer.setDistrictCode(device.getDistrictCode());
			        this.customerRepository.saveCustomer(customer);
			        device.setCustomerId(customer.getId());
			        this.deviceRepository.saveDevice(device);
				} catch (Exception e) {
					e.printStackTrace();
					this.appendErrorMsg(map, "设备编号为 :" + device.getCode()
							+ "的设备, 序列号或MAC值 重复!");
				}
			}
			barrier.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取MD5加密后的MAC值 创建返回的ystenID

	/**
	 * private static String createYstenId(String key,String ystenId) throws
	 * NoSuchAlgorithmException { key =
	 * String.valueOf(System.currentTimeMillis()) + key; StringBuffer buf =
	 * null; try { MessageDigest md = MessageDigest.getInstance("MD5");
	 * md.update(key.getBytes()); byte b[] = md.digest(); int i; buf = new
	 * StringBuffer(""); for (int offset = 0; offset < b.length; offset++) { i =
	 * b[offset]; if (i < 0) i += 256; if (i < 16) buf.append("0");
	 * buf.append(Integer.toHexString(i)); } } catch (NoSuchAlgorithmException
	 * e) { e.printStackTrace(); } int length = ystenId.length(); return
	 * ystenId+buf.toString().substring(0, 32-length); // return buf.toString();
	 * // 32位的加密 // return buf.toString().substring(8, 24); // 16位的加密
	 * <p/>
	 * }
	 */
	@SuppressWarnings("unchecked")
	private synchronized void appendErrorMsg(Map<String, Object> map,
			String error) {
		List<String> errors = (List<String>) map.get("error");
		if (errors == null) {
			errors = new ArrayList<String>();
			map.put("error", errors);
			map.put("isSuccess", false);
		}
		errors.add(error);
	}
}
