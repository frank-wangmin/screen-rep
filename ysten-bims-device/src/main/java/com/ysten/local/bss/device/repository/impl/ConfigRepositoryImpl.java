package com.ysten.local.bss.device.repository.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.SpDefine;
import com.ysten.local.bss.device.repository.IConfigRepository;
import com.ysten.local.bss.device.repository.mapper.SpDefineMapper;
import com.ysten.utils.page.Pageable;
@Repository
public class ConfigRepositoryImpl implements IConfigRepository{

    @Autowired
    private SpDefineMapper spDefineMapper;
    
    @Override
    public Pageable<SpDefine> findSpDefine(String name, String code, int start, int limit){
        List<SpDefine> spList = this.spDefineMapper.findSpDefine(name,code,start, limit);
        int total = this.spDefineMapper.getCountByCondition(name,code);
        return new Pageable<SpDefine>().instanceByStartRow(spList, total, start, limit);
    }
    
    @Override
    public boolean saveSpDefine(SpDefine spDefine){
        return 1 == this.spDefineMapper.save(spDefine);
    }
    
    @Override
    public SpDefine getSpDefineById(Integer id){
        return this.spDefineMapper.getById(id);
    }
    
    @Override
    public boolean updateSpDefine(SpDefine spDefine){
        return 1 == this.spDefineMapper.update(spDefine);
    }
    
    @Override
    public List<SpDefine> findAllSp(){
        return this.spDefineMapper.findAllSp();
    }
    
    @Override
    public SpDefine getSpDefineByName(String spName){
        return this.spDefineMapper.getByName(spName);
    }

    @Override
    public SpDefine getSpDefineListById(Integer spId, String state) {
        return this.spDefineMapper.getSpDefineListById(spId, state);
    }
}
