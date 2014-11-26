package com.ysten.local.bss.notice.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.area.repository.mapper.AreaMapper;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.repository.mapper.CityMapper;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapper;
import com.ysten.local.bss.device.repository.mapper.UserGroupMapper;
import com.ysten.local.bss.notice.domain.DeviceNoticeMap;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.notice.domain.SysNotice.NoticeType;
import com.ysten.local.bss.notice.domain.UserNoticeMap;
import com.ysten.local.bss.notice.repository.ISysNoticeRepository;
import com.ysten.local.bss.notice.repository.mapper.DeviceNoticeMapMapper;
import com.ysten.local.bss.notice.repository.mapper.SysNoticeMapper;
import com.ysten.local.bss.notice.repository.mapper.UserNoticeMapMapper;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;

@Repository
public class SysNoticeRepositoryImpl  implements ISysNoticeRepository {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    @Autowired
    private DeviceNoticeMapMapper deviceNoticeMapMapper;
    @Autowired
    private UserNoticeMapMapper userNoticeMapMapper;

    @Autowired
    private DeviceGroupMapper deviceGroupMapper;
    
    @Autowired
	private UserGroupMapper userGroupMapper;
    
    @Autowired
	private CityMapper cityMapper;

	@Override
	public boolean deleteSysNotice(List<Long> ids) {
		this.deviceNoticeMapMapper.deleteByNoticeId(ids);
		this.userNoticeMapMapper.deleteByNoticeId(ids);
		return 1== this.sysNoticeMapper.delete(ids);
	}

	@Override
	public boolean saveSysNotice(SysNotice sysNotice) {
		return 1 ==  this.sysNoticeMapper.save(sysNotice);
	}

	@Override
	public SysNotice getSysNoticeById(Long id) {
		return this.sysNoticeMapper.getById(id);
	}

	@Override
	public boolean updateSysNotice(SysNotice sysNotice) {
		return 1 == this.sysNoticeMapper.update(sysNotice);
	}

	@Override
	public Pageable<SysNotice> findSysNotice(String title, String content,Integer pageNo, Integer pageSize) {
		List<SysNotice> lists = this.sysNoticeMapper.findSysNotice(title, content, pageNo, pageSize);
		if(lists!=null && lists.size()>0){
			for(SysNotice sn : lists){
				/*List<DeviceNoticeMap> deivceMapList = this.deviceNoticeMapMapper.findDeviceNoticeMapByNoticeIdAndType(sn.getId(), "DEVICE");
				StringBuffer dsb = new StringBuffer();
				for(DeviceNoticeMap dnm : deivceMapList){
					dsb.append(dnm.getYstenId()).append(",");
				}*/
				StringBuffer sdcb = new StringBuffer();
				if(sn.getDistrictCode() != null && !sn.getDistrictCode().equals("")){
                    List<String> distCode = StringUtil.split(sn.getDistrictCode());
                    for(String cityCode:distCode){
                    	sdcb.append(cityCode.equals("100000")?"全国":this.cityMapper.getByDistrictCode(cityCode).getName()).append(",");
                    }
				}
				if(sdcb.length()>0){
					sn.setDistrictCode(sdcb.substring(0,sdcb.length()-1).toString());
				}
				List<DeviceNoticeMap> groupMapList = this.deviceNoticeMapMapper.findDeviceNoticeMapByNoticeIdAndType(sn.getId(), "GROUP");
				StringBuffer msb = new StringBuffer();
				for(DeviceNoticeMap dnm : groupMapList){
					DeviceGroup deviceGroup = this.deviceGroupMapper.getById(dnm.getDeviceGroupId());
					msb.append(deviceGroup.getName()).append(",");
				}
				/*if(dsb.length()>0){
					sn.setDeviceCodes(dsb.substring(0,dsb.length()-1).toString());
				}*/
				if(msb.length()>0){
					sn.setDeviceGroupIds(msb.substring(0,msb.length()-1).toString());
				}
				/*List<UserNoticeMap> userMapList = this.userNoticeMapMapper.findUserNoticeMapByNoticeIdAndType(sn.getId(), "USER");
				StringBuffer usb = new StringBuffer();
				for(UserNoticeMap unm : userMapList){
					usb.append(unm.getCode()).append(",");
				}*/
				List<UserNoticeMap> userGroupMapList = this.userNoticeMapMapper.findUserNoticeMapByNoticeIdAndType(sn.getId(), "GROUP");
				StringBuffer umsb = new StringBuffer();
				for(UserNoticeMap ugnm : userGroupMapList){
					UserGroup userGroup = this.userGroupMapper.getById(ugnm.getUserGroupId());
					umsb.append(userGroup.getName()).append(",");
				}
				/*if(usb.length()>0){
					sn.setUserIds(usb.substring(0,usb.length()-1).toString());
				}*/
				if(umsb.length()>0){
					sn.setUserGroupIds(umsb.substring(0,umsb.length()-1).toString());
				}
			}
		}
		int total = this.sysNoticeMapper.getCountByCondition(title, content);
		return new Pageable<SysNotice>().instanceByPageNo(lists, total, pageNo, pageSize);
	}

	@Override
	public void deleteByNoticeIdAndType(Long noticeId,String type,String tableName) {
		 this.deviceNoticeMapMapper.deleteByNoticeIdAndType(noticeId, type,tableName);
		 
	}

	@Override
	public boolean saveDeviceNoticeMap(DeviceNoticeMap deviceNoticeMap) {
		return 1 == this.deviceNoticeMapMapper.save(deviceNoticeMap);
	}

	@Override
	public List<DeviceNoticeMap> findDeviceNoticeMapByNoticeIdAndType(Long noticeId,String type) {
		return this.deviceNoticeMapMapper.findDeviceNoticeMapByNoticeIdAndType(noticeId, type);
	}
	
    @Override
    public List<SysNotice> findSysNoticeByDevice(List<Long> groupIds, String codes) {
        return this.sysNoticeMapper.findSysNoticeByDevice(groupIds,codes);
    }

    @Override
    public List<SysNotice> findSysNoticeByUser(List<Long> groupIds, String codes) {
        return this.sysNoticeMapper.findSysNoticeByUser(groupIds,codes);
    }

    /**
     * get notice ids by device code
     * @param deviceCode
     * @return
     */
    @Override
    public List<Long> findNoticeIdsByDeviceCode(String deviceCode){
        return deviceNoticeMapMapper.findNoticeIdsByDeviceCode(deviceCode);
    }

    @Override
    public List<SysNotice> getBelongToAllNotice() {
        return sysNoticeMapper.getBelongToAllNotice();
    }

    @Override
    public List<SysNotice> findSysNoticeByYstenIdOrGroupId(String ystenId, Long groupId) {
        return sysNoticeMapper.findSysNoticeByYstenIdOrGroupId(ystenId, groupId);
    }

    @Override
    public List<SysNotice> findSysNoticeByCustomerCodeOrGroupId(String customerCode, Long groupId) {
        return sysNoticeMapper.findSysNoticeByCustomerCodeOrGroupId(customerCode, groupId);
    }

    @Override
    public List<SysNotice> getNoticeListByIds(List<Long> noticeIds) {
        return sysNoticeMapper.getNoticeListByIds(noticeIds);
    }

    @Override
    public List<Long> findNoticeIdsByDeviceGroupIds(List<Long> deviceGroupIds) {
        return deviceNoticeMapMapper.findNoticeIdsByDeviceGroupIds(deviceGroupIds);
    }

	@Override
	public boolean saveUserNoticeMap(UserNoticeMap userNoticeMap) {
		return 1 == this.userNoticeMapMapper.save(userNoticeMap);
	}

	/*@Override
	public boolean deleteUserNoticeMapByNoticeId(Long noticeId, String type) {
	    return this.userNoticeMapMapper.deleteByNoticeIdAndType(noticeId, type)>=0;
		 
	}
*/
	@Override
	public List<UserNoticeMap> findUserNoticeMapByNoticeIdAndType(
			Long noticeId, String type) {
		return this.userNoticeMapMapper.findUserNoticeMapByNoticeIdAndType(noticeId, type);
	}

	@Override
	public void deleteSysNoticeMapByGroupId(Long id) {
		this.deviceNoticeMapMapper.deleteSysNoticeMapByGroupId(id);
	}

	@Override
	public void deleteByUserGroupId(Long userGroupId) {
		this.userNoticeMapMapper.deleteByUserGroupId(userGroupId);
	}

	@Override
	public SysNotice findSysNoticeByTitle(String title) {
		return this.sysNoticeMapper.findSysNoticeByTitle(title);
	}

    @Override
    public List<SysNotice> findAllSysNoticeList() {
        return this.sysNoticeMapper.findAllSysNoticeList();
    }

    @Override
	public List<DeviceNoticeMap> findSysNoticeMapByDeviceGroupId(Long groupId) {
		return this.deviceNoticeMapMapper.findSysNoticeMapByGroupId(groupId);
	}

	@Override
	public List<UserNoticeMap> getByUserGroupId(Long userGroupId) {
		return this.userNoticeMapMapper.getByUserGroupId(userGroupId);
	}

	@Override
	public List<UserNoticeMap> findSysNoticeMapByUserCode(String code) {
		return this.userNoticeMapMapper.findSysNoticeMapByUserCode(code);
	}

	
	@Override
	public void deleteByUserCode(String code) {
		this.userNoticeMapMapper.deleteByUserCode(code);
	}

    @Override
    public List<UserGroup> findUserGroupByNoticeIdAndType(Long noticeId, String type) {
        
        return this.userGroupMapper.findUserGroupByNoticeIdAndType(noticeId,type);
    }

    @Override
    public List<Long> findNoticeIdsByUserCode(String userId) {
        return this.userNoticeMapMapper.findNoticeIdsByUserCode(userId);
    }

    @Override
    public List<Long> findNoticeIdsByUserGroupIds(List<Long> userGroupIds) {
        return this.userNoticeMapMapper.findNoticeIdsByUserGroupIds(userGroupIds);
    }

	@Override
	public List<UserNoticeMap> findSysNoticeMapByUserGroupId(String groupId) {
		return userNoticeMapMapper.findSysNoticeMapByGroupId(groupId);
	}

	@Override
	public List<DeviceNoticeMap> findSysNoticeMapByYstenId(String ystenId) {
		return deviceNoticeMapMapper.findSysNoticeMapByYstenId( ystenId);
	}

    @Override
    public void deleteSysNoticeMapByYstenId(String ystenId) {
        this.deviceNoticeMapMapper.deleteSysNoticeMapByYstenId(ystenId);
    }

	@Override
	public DeviceNoticeMap findMapByNoticeIdAndYstenId(Long noticeId,
			String ystenId) {
		return this.deviceNoticeMapMapper.findMapByNoticeIdAndYstenId(noticeId, ystenId);
	}

	@Override
       public void bulkSaveDeviceNoticeMap(List<DeviceNoticeMap> list) {
        this.deviceNoticeMapMapper.bulkSaveDeviceNoticeMap(list);
    }

    @Override
    public void bulkSaveUserNoticeMap(List<UserNoticeMap> list) {
        this.userNoticeMapMapper.bulkSaveUserNoticeMap(list);
    }



	@Override
	public void deleteMapByYstenIdsAndNoticeId(Long noticeId,
			List<String> ystenIds,String tableName,String character) {
		this.deviceNoticeMapMapper.deleteMapByYstenIdsAndNoticeId(noticeId, ystenIds,tableName,character);
	}
    
	@Override
	public List<SysNotice> findSysNoticeByType(NoticeType type) {
		return this.sysNoticeMapper.findSysNoticeByType(type.name());
	}
}
