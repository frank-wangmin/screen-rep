package com.ysten.local.bss.device.repository.impl;

import com.ysten.cache.annotation.MethodFlushAnimation;
import com.ysten.cache.annotation.MethodFlushBackgroundImage;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.DeviceGroupMap;
import com.ysten.local.bss.device.repository.IDeviceGroupRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceMapper;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.DeviceGroupType;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceGroupRepositoryImpl implements IDeviceGroupRepository {
	
	@Autowired
	private DeviceGroupMapper  deviceGroupMapper;
	@Autowired
	private DeviceMapper deviceMapper;
	@Override
	public boolean insertOrUpdate(DeviceGroup epgDeviceGroup) {
		if(null == epgDeviceGroup){
			return false;
		}
		if(null == epgDeviceGroup.getId()){
			deviceGroupMapper.insert(epgDeviceGroup);
			return true;
		}
		deviceGroupMapper.updateByPrimaryKey(epgDeviceGroup);
		return true;
	}

	@Override
    @MethodFlushAnimation
    @MethodFlushBackgroundImage
	public boolean deleteByPrimaryKey(Long deviceGroupId) {
		deviceGroupMapper.deleteByPrimaryKey(deviceGroupId);
		return true;
	}

	@Override
	public DeviceGroup selectByPrimaryKey(Long deviceGroupId) {
		return deviceGroupMapper.selectByPrimaryKey(deviceGroupId);
	}
	
	@Override
	public List<DeviceGroup>  findEpgDeviceGroupList(Long pDeviceGroupId){
		return deviceGroupMapper.findDeviceGroupList(pDeviceGroupId);
	}

    @Override
    public List<DeviceGroup> finDeviceGroupBoundServiceCollect() {
        return deviceGroupMapper.finDeviceGroupBoundServiceCollect();
    }


    @Override
	public Pageable<DeviceGroup> findDeviceGroupByType(String name,String type, int pageNo, int pageSize) {
		List<DeviceGroup> deviceGroups = this.deviceGroupMapper.findDeviceGroupByType(name,type, pageNo, pageSize);
		int total = this.deviceGroupMapper.finDeviceGroupCountByType(name,type);
		return new Pageable<DeviceGroup>().instanceByPageNo(deviceGroups, total, pageNo, pageSize);
	}

    @Override
    public Pageable<DeviceGroup> findDeviceGroupByTypeName(String name,String type, int pageNo, int pageSize) {
        List<DeviceGroup> deviceGroups = this.deviceGroupMapper.findDeviceGroupByTypeName(name,type, pageNo, pageSize);
        int total = this.deviceGroupMapper.finDeviceGroupCountByType(name,type);
        return new Pageable<DeviceGroup>().instanceByPageNo(deviceGroups, total, pageNo, pageSize);
    }

    @Override
    public Pageable<DeviceGroup> findDeviceGroupByTypeNameDist(String name,String type, Long areaId, int pageNo, int pageSize) {
        List<DeviceGroup> deviceGroups = this.deviceGroupMapper.findDeviceGroupByTypeNameDist(name,type, areaId, pageNo, pageSize);
        int total = this.deviceGroupMapper.finDeviceGroupCountByTypeNameDist(name,type, areaId);
        return new Pageable<DeviceGroup>().instanceByPageNo(deviceGroups, total, pageNo, pageSize);
    }

    @Override
    public List<DeviceGroup> findDeviceGroupByDistrictCode(DeviceGroupType type,String tableName,String character,List<String> districtCode,Long id) {
        return this.deviceGroupMapper.findDeviceGroupByDistrictCode(type, tableName, character,districtCode, id);
    }

    @Override
	public DeviceGroup findByNameAndType(String name, DeviceGroupType type) {
		return this.deviceGroupMapper.findByNameAndType(name, type);
	}

	@Override
	public List<DeviceGroup> findDeviceGroupListByType(DeviceGroupType type,String isDynamic) {
		return this.deviceGroupMapper.findDeviceGroupListByType(type,isDynamic);
	}

    @Override
    public Pageable<DeviceGroup> finDeviceGroupNotBoundServiceCollect(String name, Integer pageNo, Integer pageSize) {
        List<DeviceGroup> groupList = deviceGroupMapper.finDeviceGroupNotBoundServiceCollect(name, pageNo, pageSize);
        Integer total = deviceGroupMapper.getDeviceGroupNotBoundServiceCollectCount(name);
        return new Pageable<DeviceGroup>().instanceByPageNo(groupList, total, pageNo, pageSize);
    }

	@Override
	public List<DeviceGroup> finBootDeviceGroupNotBoundServiceCollect() {
		List<DeviceGroup> groupList = deviceGroupMapper.finBootDeviceGroupNotBoundServiceCollect();
		return groupList;
	}

    @Override
    public List<DeviceGroup> findDeviceGroupByMapListAndType(List<DeviceGroupMap> deviceGroupMapList,EnumConstantsInterface.DeviceGroupType deviceGroupType) {
        return this.deviceGroupMapper.findListByDeviceGroupMapList(deviceGroupMapList, deviceGroupType);
    }

	@Override
	public List<DeviceGroup> getDeviceGroupsById(Long id) {
		return deviceGroupMapper.getDeviceGroupsById(id);
	}

    @Override
    public DeviceGroup getDeviceGroupByGroupId(Long id) {
        return deviceGroupMapper.getDeviceGroupByGroupId(id);
    }

    @Override
    public List<Device> checkInputSql(String sql) {
        return this.deviceMapper.checkInputSql(sql);
    }
    
    @Override
    public List<DeviceGroup> findDynamicGroupList(String tableName,String type){
    	return this.deviceGroupMapper.findDynamicGroupList(tableName, type);
    }

    @Override
    public List<DeviceGroup> findDeviceGroupMapByDeviceCode(String deviceCode) {
        return this.deviceGroupMapper.findDeviceGroupByDeviceCode(deviceCode);
    }

    @Override
    public List<Long> findDeviceGroupById(Long Id,String character,String tableName) {
    	return this.deviceGroupMapper.findDeviceGroupById(Id, character,tableName);
    }

    @Override
    public List<DeviceGroup> findListByDeviceGroupMapList(List<DeviceGroupMap> list, DeviceGroupType type) {
        return deviceGroupMapper.findListByDeviceGroupMapList(list, type);
    }

	@Override
	public List<DeviceGroup> findDeviceGroupByYstenIdAndArea(String ystenId,
			Long areaId) {
		return this.deviceGroupMapper.findDeviceGroupByYstenIdAndArea(ystenId,areaId);
	}

	@Override
	public List<DeviceGroup> findDeviceGroupMapByYstenIdAndType(String[] ystenIds, String type) {
		return this.deviceGroupMapper.findDeviceGroupMapByYstenIdAndType(ystenIds,type);
	}


}
