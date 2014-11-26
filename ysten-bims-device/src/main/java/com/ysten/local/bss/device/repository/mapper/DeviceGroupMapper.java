package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.DeviceGroupMap;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.DeviceGroupType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DeviceGroupMapper接口
 *
 * @fileName DeviceGroupMapper.java
 */
public interface DeviceGroupMapper {

    /**
     * 按照主键ID获取设备分组信息
     *
     * @param id
     *            主键ID
     * @return 设备分组信息
     */
    DeviceGroup getById(Long id);

    /**
     * 插入记录
     * @param deviceGroup
     * @return
     */
    int insert(DeviceGroup deviceGroup);

    /**
     * 根据主键查询记录
     * @param deviceGroupId
     * @return
     */
    DeviceGroup selectByPrimaryKey(Long deviceGroupId);

    /**
     * 根据主键修改记录
     * @param deviceGroup
     * @return
     */
    int updateByPrimaryKey(DeviceGroup deviceGroup);

    /**
     * 根据主键删除记录
     * @param deviceGroupId
     * @return
     */
    int deleteByPrimaryKey(Long deviceGroupId);

    List<DeviceGroup>  findDeviceGroupList(@Param("pDeviceGroupId") Long pDeviceGroupId);

    /**
     * 通过主键集 查询记录
     * @param deviceGroupIds
     * @return
     */
    List<DeviceGroup> findListByDeviceGroupIdList(@Param("deviceGroupIds") String deviceGroupIds );

    List<DeviceGroup> findListByDeviceGroupMapList(@Param("list") List<DeviceGroupMap> list, @Param("type") EnumConstantsInterface.DeviceGroupType type);

    /**
     * find device group by device code and group type
     * @param ystenId
     * @param type
     * @return
     */
    List<DeviceGroup> findGroupByDeviceCodeType(@Param("ystenId") String ystenId,@Param("type") EnumConstantsInterface.DeviceGroupType type);

    /**
     * 根据type获取分页分组信息
     * @param type
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<DeviceGroup> findDeviceGroupByType(@Param("name")String name,@Param("type")String type, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize);

    /**
     * 根据type和Name获取分页分组信息
     * @param type
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<DeviceGroup> findDeviceGroupByTypeName(@Param("name")String name,@Param("type")String type, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize);

    List<DeviceGroup> findDeviceGroupByTypeNameDist(@Param("name")String name,@Param("type")String type,@Param("areaId")Long areaId, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize);

    /**
     * 获取分组信息总数
     * @param type
     * @return
     */
    int finDeviceGroupCountByType(@Param("name")String name,@Param("type")String type);

    int finDeviceGroupCountByTypeNameDist(@Param("name")String name,@Param("type")String type,@Param("areaId")Long areaId);

    /**
     * 根据设备分组名称及分组类型唯一确定一条记录
     * @param name
     * @param type
     * @return
     */
    DeviceGroup findByNameAndType(@Param("name")String name, @Param("type")DeviceGroupType type);

    /**
     * find device group by and group type
     * @param type
     * @return
     */
    List<DeviceGroup> findDeviceGroupByDistrictCode(@Param("type") EnumConstantsInterface.DeviceGroupType type,@Param("tableName")String tableName,@Param("character")String character,@Param("areaId")List<String> areaId,@Param("id")Long id);
    /**
     * find device group by and group type
     * @param isDynamic
     * @param type
     * @return
     */
    List<DeviceGroup> findDeviceGroupListByType(@Param("type") EnumConstantsInterface.DeviceGroupType type,@Param("isDynamic") String isDynamic);

    /**
     * find bootstrap device groups which are not bound with service collect
     * @return list
     */
    List<DeviceGroup> finBootDeviceGroupNotBoundServiceCollect();

    List<DeviceGroup> finDeviceGroupBoundServiceCollect();

    /**
     * find device groups which are not bound with service collect
     * @return
     */
    List<DeviceGroup> finDeviceGroupNotBoundServiceCollect(@Param("name")String name, @Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);
    /**
     * find device groups count which are not bound with service collect
     * @return
     */
    Integer getDeviceGroupNotBoundServiceCollectCount(@Param("name")String name);

    /**
     * get deviceGroups bound with service collect by id
     * @param id
     * @return list
     */
    List<DeviceGroup> getDeviceGroupsById(Long id);

    DeviceGroup getDeviceGroupByGroupId(Long id);

    /**
     * get the deviceGroups by upgradeId
     * @param type
     * @return list
     */
    List<DeviceGroup> findDeviceGroupByAppIdAndType(@Param("type")String type);


    /**
     * get the deviceGroup by ystenId
     * @param ystenId
     * @return list
     */
    List<DeviceGroup> findDeviceGroupByDeviceCode(@Param("ystenId")String ystenId);
    
    List<DeviceGroup> findDeviceGroupMapByYstenIdAndType(@Param("ystenIds")String[] ystenIds, @Param("type")String type);
    
    List<DeviceGroup> findDeviceGroupByYstenIdAndArea(@Param("ystenId")String ystenId,@Param("areaId")Long areaId);

    /**
     * 查询已绑定业务的动态分组
     * @param tableName 业务与终端分组的映射表
     * @param type 分组的类型
     * @return
     */
    List<DeviceGroup> findDynamicGroupList(@Param("tableName") String tableName,@Param("type") String type);

//    List<DeviceGroup> findDeviceGroupById(@Param("Id")Long Id,@Param("character") String character,@Param("tableName") String tableName);
    
    List<Long> findDeviceGroupById(@Param("Id")Long Id,@Param("character") String character,@Param("tableName") String tableName);

}
