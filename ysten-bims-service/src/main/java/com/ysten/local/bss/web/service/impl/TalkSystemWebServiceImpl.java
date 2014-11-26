package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.talk.domain.TalkSystem;
import com.ysten.local.bss.talk.repository.ITalkSystemRepository;
import com.ysten.local.bss.web.service.ITalkSystemWebService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TalkSystemWebServiceImpl implements ITalkSystemWebService {

    @Autowired
    private ITalkSystemRepository talkSystemRepository;

    @Override
    public Pageable<TalkSystem> findAllTalkSystem(int pageNo, int pageSize) {
        return this.talkSystemRepository.findAllTalkSystem(pageNo, pageSize);
    }

    @Override
    public boolean saveTalkSystem(String name, String code, String description) {
        TalkSystem talkSystem = new TalkSystem();
        talkSystem.setCode(code);
        talkSystem.setName(name);
        talkSystem.setDescription(description);
        talkSystem.setCreateDate(new Date());
        return this.talkSystemRepository.saveTalkSystem(talkSystem);
    }

    @Override
    public boolean updateTalkSystem(String id, String name, String code, String description) {
        TalkSystem talkSystem = this.talkSystemRepository.getTalkSystemById(Long.parseLong(id));
        if (talkSystem != null) {
            talkSystem.setCode(code);
            talkSystem.setName(name);
            talkSystem.setDescription(description);
            return this.talkSystemRepository.updateTalkSystem(talkSystem);
        }
        return false;
    }

    @Override
    public TalkSystem getTalkSystemById(Long id) {
        return this.talkSystemRepository.getTalkSystemById(id);
    }

    @Override
    public List<TalkSystem> getAllTalkSystem() {
        return this.talkSystemRepository.getAllTalkSystem();
    }
}