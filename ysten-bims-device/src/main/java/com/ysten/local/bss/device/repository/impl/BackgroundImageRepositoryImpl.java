package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.cache.annotation.MethodFlushBackgroundImage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;
import com.ysten.local.bss.device.domain.BackgroundImage;
import com.ysten.local.bss.device.domain.BackgroundImageDeviceMap;
import com.ysten.local.bss.device.domain.BackgroundImageUserMap;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.repository.IBackgroundImageRepository;
import com.ysten.local.bss.device.repository.mapper.BackgroundImageDeviceMapMapper;
import com.ysten.local.bss.device.repository.mapper.BackgroundImageMapper;
import com.ysten.local.bss.device.repository.mapper.BackgroundImageUserMapMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapper;
import com.ysten.local.bss.device.repository.mapper.UserGroupMapper;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.page.Pageable;

/**
 * Created by Neil on 2014-05-06.
 */
@Repository
public class BackgroundImageRepositoryImpl implements IBackgroundImageRepository {
	private static final String BACKGROUD_IMAGE_ID = Constant.COMMON_PACKAGE + "background_image:id:";
    @Autowired
    private BackgroundImageDeviceMapMapper backgroundImageDeviceMapMapper;
    @Autowired
    private BackgroundImageMapper backgroundImageMapper;
	@Autowired
	private UserGroupMapper userGroupMapper;
    @Autowired
    private DeviceGroupMapper deviceGroupMapper;
    @Autowired
    private BackgroundImageUserMapMapper  backgroundImageUserMapMapper;

    @Override
    public List<BackgroundImageDeviceMap> findMapByYstenId(String deviceCode) {
        return this.backgroundImageDeviceMapMapper.findMapByYstenId(deviceCode);
    }

    @Override
    public List<BackgroundImageDeviceMap> findMapByDeviceGroupId(Long deviceGroupId) {
        return this.backgroundImageDeviceMapMapper.findMapByDeviceGroupId(deviceGroupId);
    }

    @Override
    public List<BackgroundImage> findBackgroundImageByDeviceCode(String deviceCode) {
        return this.backgroundImageMapper.findBackgroundImageByDeviceCode(deviceCode);
    }

    @Override
    public List<BackgroundImage> findBackgroundImageByDeviceGroupId(Long deviceGroupId) {
        return this.backgroundImageMapper.findBackgroundImageByDeviceGroupId(deviceGroupId);
    }

//    @Override
//    @MethodCache(key = BACKGROUD_IMAGE_ID + "#{id}")
//    public BackgroundImage findBackgroundImageById(@KeyParam("id") Long id) {
//        return this.backgroundImageMapper.findBackgroundImageById(id);
//    }

    @Override
    public List<BackgroundImage> findDefaultBackgroundImage(int isDefault) {
        return this.backgroundImageMapper.findDefaultBackgroundImage(isDefault);
    }


	@Override
	public Pageable<BackgroundImage> findBackGroundList(String name, Integer pageNo, Integer pageSize) {
		List<BackgroundImage> backgroundImages = this.backgroundImageMapper.findBackGroundList(name, pageNo, pageSize);
		for(BackgroundImage backgroundImage : backgroundImages){
			List<BackgroundImageDeviceMap> deviceMaps = this.backgroundImageDeviceMapMapper.findBGDeviceMapByBGId(backgroundImage.getId());			
			StringBuilder codes = new StringBuilder();
			Integer deviceLoopTime = null;
			String type = null;
			for(BackgroundImageDeviceMap bidm : deviceMaps){
				if(StringUtils.isNotBlank(bidm.getYstenId())){
					codes.append(bidm.getYstenId()).append(",");
					deviceLoopTime = bidm.getLoopTime();
					type = bidm.getType();
				}	
			}
			if(StringUtils.isNotBlank(codes.toString())){
				backgroundImage.setDeviceCodes(codes.substring(0, codes.length()-1));
			}
			if(deviceLoopTime != null){
				backgroundImage.setDeviceLoopTime(deviceLoopTime);
			}
			List<BackgroundImageUserMap> userMaps = this.backgroundImageUserMapMapper.getBGImageMapByBGIdAndType(backgroundImage.getId(), "USER");
			StringBuilder users = new StringBuilder();
			Integer userLoopTime = null;
			for(BackgroundImageUserMap bium : userMaps){
				    users.append(bium.getCode()).append(",");
					userLoopTime = bium.getLoopTime();
					type = bium.getType();
			}
			if(StringUtils.isNotBlank(users.toString())){
				backgroundImage.setUserIds(users.substring(0, users.length()-1));
			}
			if(userLoopTime != null){
				backgroundImage.setUserLoopTime(userLoopTime);
			}
			List<BackgroundImageUserMap> userGroupMaps = this.backgroundImageUserMapMapper.getBGImageMapByBGIdAndType(backgroundImage.getId(), "GROUP");
			StringBuilder userGroups = new StringBuilder();
			Integer userGroupLoopTime = null;
			for(BackgroundImageUserMap bium : userGroupMaps){
				    UserGroup userGroup = this.userGroupMapper.getById(bium.getUserGroupId());
				    userGroups.append(userGroup.getName()).append(",");
					userGroupLoopTime = bium.getLoopTime();
					type = bium.getType();
			}
			if(StringUtils.isNotBlank(userGroups.toString())){
				backgroundImage.setUserGroupIds(userGroups.substring(0, userGroups.length()-1));
			}
			if(userGroupLoopTime != null){
				backgroundImage.setUserGroupLoopTime(userGroupLoopTime);
			}
			List<BackgroundImageDeviceMap> groupMaps = this.backgroundImageDeviceMapMapper.findBGDeviceMapByBGId(backgroundImage.getId());
			StringBuilder groups = new StringBuilder();
			Integer groupLoopTime = null;
			for(BackgroundImageDeviceMap bidm : groupMaps){
				if(bidm.getDeviceGroupId() != null){
					DeviceGroup deviceGroup = this.deviceGroupMapper.getById(bidm.getDeviceGroupId());
					groups.append(deviceGroup.getName()).append(",");
					groupLoopTime = bidm.getLoopTime();
					type = bidm.getType();
				}
			}
			if(StringUtils.isNotBlank(groups.toString())){
				backgroundImage.setDeviceGroupIds(groups.substring(0, groups.length()-1));
			}
			if(groupLoopTime != null){
				backgroundImage.setGroupLoopTime(groupLoopTime);
			}
			backgroundImage.setType(type);
		}
		int total = this.backgroundImageMapper.getCountByCondition(name);
		return new Pageable<BackgroundImage>().instanceByPageNo(backgroundImages, total, pageNo, pageSize);
	}

	@Override
	public boolean saveBackGroundImage(BackgroundImage backgroundImage) {
		return 1==this.backgroundImageMapper.save(backgroundImage);
	}

	@Override
    @MethodFlushBackgroundImage
	@MethodFlush(keys = {BACKGROUD_IMAGE_ID + "#{backgroundImage.id}"})
	public boolean updateBackGroundImage(@KeyParam("backgroundImage")BackgroundImage backgroundImage) {
		return 1==this.backgroundImageMapper.update(backgroundImage);
	}

	@Override
	@MethodCache(key = BACKGROUD_IMAGE_ID + "#{id}")
	public BackgroundImage getById(@KeyParam("id") Long id) {
		return this.backgroundImageMapper.findBackgroundImageById(id);
	}

//	@Override
//	@MethodFlush(keys = {BACKGROUD_IMAGE_ID + "#{backgroundImage.id}"})
//	public boolean deleteByIds(List<Long> idsList) {
//		return 1==this.backgroundImageMapper.deleteByIds(idsList);
//	}

	@Override
	public BackgroundImageDeviceMap findMapByBGIdAndCode(Long id, String deviceCode) {
		return this.backgroundImageDeviceMapMapper.findMapByBGIdAndCode(id, deviceCode);
	}

	@Override
	public BackgroundImageDeviceMap findMapByBGIdAndGroupId(Long id, Long deviceGroupId) {
		return this.backgroundImageDeviceMapMapper.findMapByBGIdAndGroupId(id, deviceGroupId);
	}

	@Override
    @MethodFlushBackgroundImage
	public boolean saveBackgroundImageDeviceMap(BackgroundImageDeviceMap map) {
		return 1==this.backgroundImageDeviceMapMapper.save(map);
	}

	@Override
	public List<BackgroundImageDeviceMap> getBGImageMapByBGIdAndType(Long bgId, String type) {
		return this.backgroundImageDeviceMapMapper.getBGImageMapByBGIdAndType(bgId, type);
	}

	@Override
    @MethodFlushBackgroundImage
	public boolean deleteBackGroundImageMapByBGId(Long id) {
		//TODO:如何确保正确删除？
	return 1<=	backgroundImageDeviceMapMapper.deleteByBGId(id);
	}

	@Override
    @MethodFlushBackgroundImage
	public void deleteBackGroundImageMapByGroupId(Long id) {
		this.backgroundImageDeviceMapMapper.deleteBackGroundImageMapByGroupId(id);
	}

	@Override
	public List<BackgroundImageUserMap> getBGUserImageMapByBGIdAndType(
			Long bgId, String type) {
		return this.backgroundImageUserMapMapper.getBGImageMapByBGIdAndType(bgId, type);
	}

	@Override
	public BackgroundImageUserMap findUserMapByBGIdAndCode(Long id,
			String code) {
		return this.backgroundImageUserMapMapper.findMapByBGIdAndUserCode(id, code);
	}

	@Override
    @MethodFlushBackgroundImage
	public boolean saveBackgroundImageUserMap(BackgroundImageUserMap map) {
		return 1 == this.backgroundImageUserMapMapper.save(map);
	}

	@Override
    @MethodFlushBackgroundImage
	public boolean deleteUserBackGroundImageMapByBGId(Long id) {
		//TODO:如何确保正确删除？
		return 1<=this.backgroundImageUserMapMapper.deleteByBGId(id);
	}

	@Override
    @MethodFlushBackgroundImage
	public void deleteBackGroundImageMapByUserCode(String code) {
		this.backgroundImageUserMapMapper.deleteMapByUserCode(code);
	}

	@Override
    @MethodFlushBackgroundImage
	public void deleteBackGroundImageMapByDeviceCode(String deviceCode) {
		this.backgroundImageDeviceMapMapper.deleteBackGroundImageMapByCode(deviceCode);
	}

	@Override
    @MethodFlushBackgroundImage
	public void deleteUserBackGroundImageMapByGroupId(Long id) {
		this.backgroundImageUserMapMapper.deleteBackGroundImageMapByGroupId(id);
	}

	@Override
	public List<BackgroundImageUserMap> findMapByUserGroupId(Long userGroupId) {
		return this.backgroundImageUserMapMapper.findMapByUserGroupId(userGroupId);
	}

	@Override
	public List<BackgroundImageUserMap> findMapByUserCode(String code) {
		return this.backgroundImageUserMapMapper.findMapByUserCode(code);
	}

    @Override
    public List<BackgroundImageDeviceMap> getBGImageDeviceMapByBGId(Long bgId) {
        return this.backgroundImageDeviceMapMapper.findBGDeviceMapByBGId(bgId);
    }

    @Override
    public List<BackgroundImageUserMap> getBGUserImageMapByBGId(Long bgId) {
        return this.backgroundImageUserMapMapper.findBGUserMapByBGId(bgId);
    }

    @Override
    @MethodFlushBackgroundImage
    @MethodFlush(keys = {BACKGROUD_IMAGE_ID + "#{id}"})
    public boolean deleteById(@KeyParam("id")Long id) {
        return 1==this.backgroundImageMapper.deleteById(id);
    }

	@Override
//	 @MethodCache(key = YSTEN_ID + "#{ystenId}")
	public List<BackgroundImage> findBackgroundImageByYstenId(@KeyParam("ystenId")String ystenId) {
		return backgroundImageMapper.findBackgroundImageByYstenId(ystenId);
	}
    @Override
    public List<BackgroundImage> findAllBackgroundImage(){
        return backgroundImageMapper.findAllBackgroundImage();
    }

    @Override
    public BackgroundImage getBackgroundImageByIdAndUseable(Long id, String state) {
        return this.backgroundImageMapper.getBackgroundImageByIdAndUseable(id, state);
    }
    
    @Override
    public List<BackgroundImage> findBackgroundImageByYstenIdOrGroupId(String ystenId, Long groupId) {
        return this.backgroundImageMapper.findBackgroundImageByYstenIdOrGroupId(ystenId, groupId);
    }

    @Override
    public List<BackgroundImage> findBackgroundImageByCustomerCodeOrGroupId(String customerCode, Long groupId) {
        return this.backgroundImageMapper.findBackgroundImageByCustomerCodeOrGroupId(customerCode, groupId);
    }

    @Override
    public List<BackgroundImage> findBackgroundImageByDeviceGroupIdAndState(Long deviceGroupId, String state) {
        return this.backgroundImageMapper.findBackgroundImageByDeviceGroupIdAndState(deviceGroupId, state);
    }

	@Override
	public void deleteByBGIdAndType(Long groupId, String type,String tableName) {
		this.backgroundImageDeviceMapMapper.deleteByBGIdAndType(groupId, type,tableName);
		
	}

	@Override
	public void bulkSaveBackgroundMap(List<BackgroundImageDeviceMap> maps) {
		this.backgroundImageDeviceMapMapper.bulkSaveBackgroundMap(maps);
	}


    @Override
    public void bulkSaveUserBackgroundMap(List<BackgroundImageUserMap> maps) {
        this.backgroundImageUserMapMapper.bulkSaveUserBackgroundMap(maps);
    }

	@Override
	public void deleteBackGroundMapByYstenIdsAndBgId(Long bgId,
			List<String> ystenIds,String tableName,String character) {
		this.backgroundImageDeviceMapMapper.deleteBackGroundMapByYstenIdsAndBgId(bgId, ystenIds,tableName,character);
	}
}
