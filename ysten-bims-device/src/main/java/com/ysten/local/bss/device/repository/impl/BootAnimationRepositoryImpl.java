package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.cache.annotation.MethodFlushAnimation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.AnimationDeviceMap;
import com.ysten.local.bss.device.domain.AnimationUserMap;
import com.ysten.local.bss.device.domain.BootAnimation;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.repository.IBootAnimationRepository;
import com.ysten.local.bss.device.repository.mapper.AnimationDeviceMapMapper;
import com.ysten.local.bss.device.repository.mapper.AnimationUserMapMapper;
import com.ysten.local.bss.device.repository.mapper.BootAnimationMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapper;
import com.ysten.local.bss.device.repository.mapper.UserGroupMapper;
import com.ysten.utils.page.Pageable;

@Repository
public class BootAnimationRepositoryImpl implements IBootAnimationRepository {

    @Autowired
    private BootAnimationMapper bootAnimationMapper;

    @Autowired
    private AnimationDeviceMapMapper animationDeviceMapMapper;
    
    @Autowired
    private AnimationUserMapMapper animationUserMapMapper;


    @Autowired
    private DeviceGroupMapper deviceGroupMapper;
    
    @Autowired
	private UserGroupMapper userGroupMapper;

    @Override
    public AnimationDeviceMap findMapByYstenId(String ystenId, String type) {
        return animationDeviceMapMapper.findMapByYstenId(ystenId, type);
    }

    @Override
    public AnimationDeviceMap findMapByDeviceGroupId(Long deviceGroupId, String type) {
        return animationDeviceMapMapper.findMapByDeviceGroupId(deviceGroupId, type);
    }

    @Override
    public BootAnimation findBootAnimationByDeviceCode(String ystenId, String type) {
        return bootAnimationMapper.findBootAnimationByDeviceCode(ystenId, type);
    }

    @Override
    public BootAnimation findBootAnimationByDeviceGroupId(Long deviceGroupId, String type) {
        return bootAnimationMapper.findBootAnimationByDeviceGroupId(deviceGroupId, type);
    }

    @Override
    public BootAnimation findBootAnimationById(Long id, String state) {
        return bootAnimationMapper.findBootAnimationById(id, state);
    }

    @Override
    public BootAnimation findDefaultBootAnimation(int isDefault, String state) {
        return bootAnimationMapper.findDefaultBootAnimation(isDefault, state);
    }


	@Override
	public boolean saveBootAnimation(BootAnimation bootAnimation) {
		return 1 == this.bootAnimationMapper.save(bootAnimation);
	}

	@Override
	public BootAnimation getBootAnimationById(Long id) {
		return this.bootAnimationMapper.getById(id);
	}

	@Override
    @MethodFlushAnimation
	public boolean updateBootAnimation(BootAnimation bootAnimation) {
		return 1 == this.bootAnimationMapper.update(bootAnimation);
	}

	@Override
	public Pageable<BootAnimation> findBootAnimation(String name, Integer pageNo,Integer pageSize) {
		List<BootAnimation> lists = this.bootAnimationMapper.findBootAnimation(name, pageNo, pageSize);
		if(lists!=null && lists.size()>0){
			for(BootAnimation bn : lists){
/*				List<AnimationDeviceMap> deivceMapList = this.animationDeviceMapMapper.findAnimationDeviceMapByAnimationIdAndType(bn.getId(), "DEVICE");
				StringBuffer dsb = new StringBuffer();
				for(AnimationDeviceMap dnm : deivceMapList){
					dsb.append(dnm.getYstenId()).append(",");
				}*/
				List<AnimationDeviceMap> groupMapList = this.animationDeviceMapMapper.findAnimationDeviceMapByAnimationIdAndType(bn.getId(), "GROUP");
				StringBuffer msb = new StringBuffer();
				for(AnimationDeviceMap dnm : groupMapList){
					DeviceGroup deviceGroup = this.deviceGroupMapper.getById(dnm.getDeviceGroupId());
					msb.append(deviceGroup.getName()).append(",");
				}
/*				List<AnimationUserMap> userMapList = this.animationUserMapMapper.findAnimationUserMapByAnimationIdAndType(bn.getId(), "USER");
				StringBuffer usb = new StringBuffer();
				for(AnimationUserMap dnm : userMapList){
					usb.append(dnm.getCode()).append(",");
				}*/
				List<AnimationUserMap> userGroupMapList = this.animationUserMapMapper.findAnimationUserMapByAnimationIdAndType(bn.getId(), "GROUP");
				StringBuffer umsb = new StringBuffer();
				for(AnimationUserMap dnm : userGroupMapList){
					UserGroup userGroup = this.userGroupMapper.getById(dnm.getUserGroupId());
                            if(userGroup!=null){
					umsb.append(userGroup.getName()).append(",");
                            }
				}
/*				if(dsb.length()>0){
					bn.setDeviceCodes(dsb.substring(0,dsb.length()-1).toString());
				}*/
				if(msb.length()>0){
					bn.setDeviceGroupIds(msb.substring(0,msb.length()-1).toString());
				}
				/*if(usb.length()>0){
					bn.setUserIds(usb.substring(0,usb.length()-1).toString());
				}*/
				if(umsb.length()>0){
					bn.setUserGroupIds(umsb.substring(0,umsb.length()-1).toString());
				}
			}
		}
		int total = this.bootAnimationMapper.getCountByCondition(name);
		return new Pageable<BootAnimation>().instanceByPageNo(lists, total, pageNo, pageSize);
	}

	@Override
    @MethodFlushAnimation
	public boolean deleteBootAnimation(List<Long> ids) {
		return 1 == this.bootAnimationMapper.delete(ids);
	}
	@Override
    @MethodFlushAnimation
    public boolean deleteBootAnimationById(Long id) {
        return 1 == this.bootAnimationMapper.deleteById(id);
    }
	@Override
    @MethodFlushAnimation
	public void deleteAnimationMapByAnimationIdAndType(Long bootAnimationId, String type,String tableName) {
		this.animationDeviceMapMapper.deleteByAnimationIdAndType(bootAnimationId, type,tableName);
	}

	@Override
    @MethodFlushAnimation
	public boolean saveAnimationDeviceMap(AnimationDeviceMap animationDeviceMap) {
		return 1== this.animationDeviceMapMapper.save(animationDeviceMap);
	}

	@Override
	public List<AnimationDeviceMap> findAnimationDeviceMapByAnimationIdAndType(Long bootAnimationId, String type) {
		return this.animationDeviceMapMapper.findAnimationDeviceMapByAnimationIdAndType(bootAnimationId, type);
	}

	@Override
    @MethodFlushAnimation
	public boolean saveAnimationUserMap(AnimationUserMap animationUserMap) {
		return 1 == this.animationUserMapMapper.save(animationUserMap);
	}

	@Override
    @MethodFlushAnimation
	public void deleteAnimationDeviceMapByGroupId(Long id) {
		this.animationDeviceMapMapper.deleteAnimationDeviceMapByGroupId(id);
	}

	@Override
	public List<AnimationUserMap> findAnimationUserMapByAnimationIdAndType(Long bootAnimationId, String type) {
		return this.animationUserMapMapper.findAnimationUserMapByAnimationIdAndType(bootAnimationId, type);
	}

	/*@Override
    @MethodFlushAnimation
	public boolean deleteAnimationUserMapByAnimationIdAndType(Long bootAnimationId, String type) {
		return 1 == this.animationUserMapMapper.deleteByAnimationIdAndType(bootAnimationId, type);
	}*/

	@Override
    @MethodFlushAnimation
	public void deleteAnimationUserMapByUserGroupId(Long userGroupId) {
		this.animationUserMapMapper.deleteMapByUserGroupId(userGroupId);
	}

	@Override
    @MethodFlushAnimation
	public void deleteUserMapByAnimationId(List<Long> ids) {
		this.animationUserMapMapper.deleteByAnimationId(ids);
	}

	@Override
    @MethodFlushAnimation
	public void deleteDeviceMapByAnimationId(List<Long> ids) {
		this.animationDeviceMapMapper.deleteByAnimationId(ids);
		
	}

	@Override
    @MethodFlushAnimation
	public void deleteAnimationDeviceMapByCode(String deviceCode) {
		this.animationDeviceMapMapper.deleteAnimationDeviceMapByCode(deviceCode);
	}

	@Override
    @MethodFlushAnimation
	public void deleteAnimationUserMapByCode(String code) {
		this.animationUserMapMapper.deleteMapByUserCode(code);
	}

	@Override
	public BootAnimation findBootAnimationByName(String name) {
		return this.bootAnimationMapper.findBootAnimationByName(name);
	}

    @Override
    public BootAnimation getBootAnimationByDefault() {
        return this.bootAnimationMapper.getBootAnimationByDefault();
    }

    @Override
	public AnimationUserMap findMapByUserGroupId(Long userGroupId, String type) {
		return this.animationUserMapMapper.findMapByUserGroupId(userGroupId, type);
	}

	@Override
	public AnimationUserMap findMapByUserCode(String code, String type) {
		return this.animationUserMapMapper.findMapByUserCode(code, type);
	}

    @Override
    public BootAnimation getBootAnimationByYstenIdOrGroupId(String ystenId, Long groupId) {
        return this.bootAnimationMapper.getBootAnimationByYstenIdOrGroupId(ystenId, groupId);
    }

    @Override
    public BootAnimation getBootAnimationByCustomerCodeOrGroupId(String customerCode, Long groupId) {
        return this.bootAnimationMapper.getBootAnimationByCustomerCodeOrGroupId(customerCode, groupId);
    }

    @Override
    public List<BootAnimation> findAllBootAnimation() {
        return this.bootAnimationMapper.findAllBootAnimation();
    }

    @Override
    public BootAnimation getBootAnimationByDeviceGroupIdAndState(Long deviceGroupId, String state) {
        return this.bootAnimationMapper.getBootAnimationByDeviceGroupIdAndState(deviceGroupId, state);
    }



	@Override
	public void deleteAnimationMapByGroupIds(List<Long> groupIds,String tableName,String character) {
		this.animationDeviceMapMapper.deleteAnimationMapByGroupIds(groupIds, tableName,character);
	}

    @Override
    public void bulkSaveUserAnimationMap(List<AnimationUserMap> animationDeviceMap) {
        this.animationUserMapMapper.bulkSaveUserAnimationMap(animationDeviceMap);
    }



	@Override
	public void bulkSaveAnimationMap(List<AnimationDeviceMap> animationDeviceMap) {
		this.animationDeviceMapMapper.bulkSaveAnimationMap(animationDeviceMap);
	}

	@Override
	public void deleteAnimationDeviceMapByYstenIds(List<String> ystenIds,String tableName,String character) {
		this.animationDeviceMapMapper.deleteAnimationDeviceMapByYstenIds(ystenIds,tableName,character);
	}

}
