package com.ysten.local.bss.device.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.repository.IDeviceGroupRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.service.IDeviceGroupService;
import com.ysten.utils.json.JsonResult;
import com.ysten.utils.string.StringUtil;

@Service
public class DeviceGroupServiceImpl implements IDeviceGroupService {
	@Autowired
	private IDeviceGroupRepository deviceGroupRepository;
	
	@Autowired
	private IDeviceRepository  deviceRepository;

	@Override
	public List<DeviceGroup> findDeviceGroupTree(Long pDeviceGroupId) {
		return deviceGroupRepository.findEpgDeviceGroupList( pDeviceGroupId);
	}
	
	@Override
	public JsonResult insertOrUpdate(DeviceGroup epgDeviceGroup) {
		
		JsonResult jsonResult = null;
		DeviceGroup data = null;
		if(null == epgDeviceGroup){
			return new JsonResult(false, "数据对象为空！");
		}
		if(null != epgDeviceGroup.getId()){
			data = this.deviceGroupRepository.selectByPrimaryKey(epgDeviceGroup.getId());
			if(data == null || !epgDeviceGroup.getId().equals(data.getId())){
				jsonResult = new JsonResult(false, "刷新后，再进行修改操作！");
				return jsonResult;
			}
			epgDeviceGroup.setCreateDate(data.getCreateDate());
			jsonResult = new JsonResult(true, "修改成功！");
		}else{
			epgDeviceGroup.setCreateDate(new Date());
			jsonResult = new JsonResult(true, "新增成功！");
		}
		this.deviceGroupRepository.insertOrUpdate(epgDeviceGroup);
		
		return jsonResult;
	}

	@Override
	public DeviceGroup getDeviceGroup(Long deviceGroupId) {

		return deviceGroupRepository.selectByPrimaryKey(deviceGroupId);
	}

	@Override
	public JsonResult deleteDeviceGroupByIds(String ids) {
		List<Long> idList = StringUtil.splitToLong(ids);
		if(StringUtils.isBlank(ids) || CollectionUtils.isEmpty(idList)){
			return new JsonResult(false, "参数为空！");
		}
		if(this.isExistEpgDeviceDatasByDeviceGroupIds(idList)){
			return new JsonResult(false, "该设备分组已关联设备，不能删除！");
		}
		this.deleteBatchEpgDeviceGroupByIds(idList);
		return new JsonResult(true, "删除成功！");
	}
	//验证是否存在设备数据
	private boolean isExistEpgDeviceDatasByDeviceGroupIds(List<Long> idList){
		List<Device> list = deviceRepository.findEpgDeviceByDeviceGroupIds(idList);
		return !CollectionUtils.isEmpty(list);
	}
	
	//批量删除厂商记录
	private boolean deleteBatchEpgDeviceGroupByIds(List<Long> idList){
		for(Long deviceGroupId : idList){
			this.deviceGroupRepository.deleteByPrimaryKey(deviceGroupId);
		}
		return true;
	}
	
	@Override
	public List<DeviceGroup> findDynamicGroupList(String ystenId,String type ,String tableName) {
		// 1.查询动态分组.
		List<DeviceGroup> list = deviceGroupRepository.findDynamicGroupList(tableName,type);
		List<DeviceGroup> listResult = new ArrayList<DeviceGroup>();

		// 2.判断终端是否在动态分组中.
		for (int i = 0; i < list.size(); i++) {
			DeviceGroup deviceGroup = list.get(i);
			String sql= deviceGroup.getSqlExpression();
			sql.replaceAll(";", "");
			sql = sql + " and ysten_id=" + ystenId;
			List<Device> deviceList = deviceGroupRepository.checkInputSql(sql);
			if(!CollectionUtils.isEmpty(deviceList)){
				listResult.add(deviceGroup);
			}
		}
		return listResult;
	}
}
