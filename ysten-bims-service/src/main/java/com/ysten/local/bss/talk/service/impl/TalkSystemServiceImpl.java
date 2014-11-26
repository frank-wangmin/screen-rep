package com.ysten.local.bss.talk.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.talk.domain.TalkSystem;
import com.ysten.local.bss.talk.repository.ITalkSystemRepository;
import com.ysten.local.bss.talk.service.ITalkSystemService;

@Service
public class TalkSystemServiceImpl implements ITalkSystemService {

    @Autowired
    private ITalkSystemRepository talkSystemRepository;
    
    @Override
    public boolean checkTalkSystem(String systemCode) {
        boolean bool = false;
        if(StringUtils.isNotBlank(systemCode)){
            List<TalkSystem> list = this.talkSystemRepository.findTalkSystemByCode(systemCode);
            if(list!=null && list.size()>0){
                bool = true;
            }
        }
        return bool;
    }

}
