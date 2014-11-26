package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.device.domain.SpDefine;
import com.ysten.local.bss.device.repository.IConfigRepository;
import com.ysten.local.bss.web.service.IConfigWebService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigWebServiceImpl implements IConfigWebService {

    @Autowired
    private IConfigRepository configRepository;

    @Override
    public boolean saveSpDefine(SpDefine spDefine) {
        return this.configRepository.saveSpDefine(spDefine);
    }

    @Override
    public SpDefine getSpDefineById(Integer id) {
        return this.configRepository.getSpDefineById(id);
    }

    @Override
    public boolean updateSpDefine(SpDefine spDefine) {
        return this.configRepository.updateSpDefine(spDefine);
    }

    @Override
    public List<SpDefine> findAllSp() {
        return this.configRepository.findAllSp();
    }

    @Override
    public SpDefine getSpDefineByName(String spName) {
        return this.configRepository.getSpDefineByName(spName);
    }

    @Override
    public Pageable<SpDefine> findSpDefine(String name, String code, int start, int limit) {
        return this.configRepository.findSpDefine(name, code, start, limit);
    }
}
