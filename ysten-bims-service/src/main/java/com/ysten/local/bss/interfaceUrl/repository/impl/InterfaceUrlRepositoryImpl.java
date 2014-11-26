package com.ysten.local.bss.interfaceUrl.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.local.bss.interfaceUrl.repository.IInterfaceUrlRepository;
import com.ysten.local.bss.interfaceUrl.repository.mapper.InterfaceUrlMapper;
import com.ysten.utils.page.Pageable;


@Repository
public class InterfaceUrlRepositoryImpl implements IInterfaceUrlRepository {
	@Autowired
	private InterfaceUrlMapper interfaceUrlMapper;

	@Override
	public InterfaceUrl getByAreaAndName(Long areaId, InterfaceName interfaceName) {
		return interfaceUrlMapper.getByAreaAndName(areaId, interfaceName);
	}

	@Override
	public InterfaceUrl getInterfaceUrlById(Long id) {
		return this.interfaceUrlMapper.getById(id);
	}

	@Override
	public boolean saveInterfaceUrl(InterfaceUrl interfaceUrl) {
		return 1 ==  this.interfaceUrlMapper.save(interfaceUrl);
	}

	@Override
	public boolean updateInterfaceUrl(InterfaceUrl interfaceUrl) {
		return 1 == this.interfaceUrlMapper.update(interfaceUrl);
	}
		
	@Override
	public boolean deleteByIds(List<Long> idsList) {
		 boolean bool = idsList.size()==this.interfaceUrlMapper.deleteByIds(idsList);
		 return bool;
	}

	@Override
	public Pageable<InterfaceUrl> findInterfaceUrlList(String interfaceName,Long areaId,Integer pageNo,Integer pageSize) {
		List<InterfaceUrl> interfaceUrlList = this.interfaceUrlMapper.findAll(interfaceName,areaId,pageNo, pageSize);
		int total = this.interfaceUrlMapper.getCount(interfaceName,areaId);
		return new Pageable<InterfaceUrl>().instanceByPageNo(interfaceUrlList, total, pageNo, pageSize);
	}
	
}
