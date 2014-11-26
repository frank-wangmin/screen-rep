package com.ysten.local.bss.device.repository;

import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.DeviceGroupMap;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.DeviceGroupType;
import com.ysten.utils.page.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface IDeviceGroupRepository {

	public boolean insertOrUpdate(DeviceGroup epgDeviceGroup) ;

	public boolean deleteByPrimaryKey(Long deviceGroupId);

	public DeviceGroup selectByPrimaryKey(Long deviceGroupId) ;
	
	public List<DeviceGroup>  findEpgDeviceGroupList(Long pDeviceGroupId);

      public List<DeviceGroup> finDeviceGroupBoundServiceCollect();
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	Pageable<DeviceGroup> findDeviceGroupByType(String name,String type, int pageNo, int pageSize);

    /**
     *
     * @param type
     * @param name
     * @return
     */
    Pageable<DeviceGroup> findDeviceGroupByTypeName(String name,String type, int pageNo, int pageSize);
    /**
     *
     * @param type
     * @param name
     * @param districtCode
     * @return
     */
    Pageable<DeviceGroup> findDeviceGroupByTypeNameDist(String name,String type, Long areaId, int pageNo, int pageSize);

    /**
	 * 根据分组名称及分组类型唯一确定一条记录
	 * @param name
	 * @param type
	 * @return
	 */
	DeviceGroup findByNameAndType(String name, DeviceGroupType type);
    /**
     * find device group by and group type
     * @param type
     * @param isDynamic
     * @return
     */
    List<DeviceGroup> findDeviceGroupListByType(EnumConstantsInterface.DeviceGroupType type,String isDynamic);

    /**
     * find device group by districtCode,type and bootId
     *
     * @param id
     * @param districtCode
     * @param tableName
     * @param character
     * @param type
     * @return
     */
    List<DeviceGroup> findDeviceGroupByDistrictCode(EnumConstantsInterface.DeviceGroupType type, String tableName,String character,List<String> districtCode, Long id);

    /**
     * find device groups which are not bound with service collect
     * @return
     */
    Pageable<DeviceGroup> finDeviceGroupNotBoundServiceCollect(String name, Integer pageNo,Integer pageSize);
    /**
     * find boot device groups which are not bound with service collect@
     * @return list
     */
    List<DeviceGroup> finBootDeviceGroupNotBoundServiceCollect();
    

    public List<DeviceGroup> findDeviceGroupByMapListAndType(List<DeviceGroupMap> deviceGroupMapList,EnumConstantsInterface.DeviceGroupType deviceGroupType);

    /**
     * get the deviceGroups bound with service collect by id
     * @param id
     * @return list
     */
    List<DeviceGroup> getDeviceGroupsById(Long id);

    DeviceGroup getDeviceGroupByGroupId(Long id);

    List<Device> checkInputSql(String sql);
    
    /**
     * 查询已绑定业务的动态分组
     * @param tableName 业务与终端分组的映射表
     * @param type 分组的类型
     * @return
     */
    List<DeviceGroup> findDynamicGroupList(String tableName,String type);

    List<DeviceGroup> findListByDeviceGroupMapList(List<DeviceGroupMap> list, EnumConstantsInterface.DeviceGroupType type);

//    /**
//     * find the maps by deviceGroupId
//     * @param deviceGroupId
//     * @return list
//     */
//    List<DeviceGroupMap> findDeviceGroupMapByGroupId(Long deviceGroupId);

    /**
     * find the deviceGroups by deviceCode
     * @param deviceCode
     * @return list
     */
    List<DeviceGroup> findDeviceGroupMapByDeviceCode(String deviceCode);
    
    List<DeviceGroup> findDeviceGroupMapByYstenIdAndType(String[] ystenIds,String type);
    
    List<DeviceGroup> findDeviceGroupByYstenIdAndArea(String ystenId,Long areaId);


    List<Long> findDeviceGroupById(Long Id, String character,String tableName);

}
