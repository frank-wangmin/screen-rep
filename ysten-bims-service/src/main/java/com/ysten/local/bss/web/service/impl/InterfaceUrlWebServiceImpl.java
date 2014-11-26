package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl;
import com.ysten.local.bss.interfaceUrl.domain.InterfaceUrl.InterfaceName;
import com.ysten.local.bss.interfaceUrl.repository.IInterfaceUrlRepository;
import com.ysten.local.bss.web.service.IInterfaceUrlWebService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterfaceUrlWebServiceImpl implements IInterfaceUrlWebService {

    @Autowired
    private IInterfaceUrlRepository interfaceUrlRepository;

    @Override
    public InterfaceUrl getInterfaceUrlById(Long id) {
        return this.interfaceUrlRepository.getInterfaceUrlById(id);
    }

    @Override
    public boolean saveInterfaceUrl(InterfaceUrl interfaceUrl) {
        return this.interfaceUrlRepository.saveInterfaceUrl(interfaceUrl);
    }

    @Override
    public boolean updateInterfaceUrl(Long id, String interfaceName, String interfaceUrl, Long areaId) {
        InterfaceUrl interfaceUrlUpdate = this.interfaceUrlRepository.getInterfaceUrlById(id);
        interfaceUrlUpdate.setInterfaceName(InterfaceName.valueOf(interfaceName));
        interfaceUrlUpdate.setInterfaceUrl(interfaceUrl);
        interfaceUrlUpdate.getArea().setId(areaId);
        interfaceUrlUpdate.setArea(interfaceUrlUpdate.getArea());
        return this.interfaceUrlRepository.updateInterfaceUrl(interfaceUrlUpdate);
    }
    
    @Override
	public boolean deleteInterfaceUrl(List<Long> interfaceUrlIds){
        return this.interfaceUrlRepository.deleteByIds(interfaceUrlIds);      
    }   

    @Override
    public Pageable<InterfaceUrl> findInterfaceUrlList(String interfaceName, Long areaId, Integer pageNo,
            Integer pageSize) {
        return this.interfaceUrlRepository.findInterfaceUrlList(interfaceName, areaId, pageNo, pageSize);
    }

    @Override
    public InterfaceUrl getByAreaAndName(Long areaId, InterfaceName interfaceName) {
        return this.interfaceUrlRepository.getByAreaAndName(areaId,interfaceName);
    }


}
