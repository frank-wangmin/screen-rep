package com.ysten.local.bss.talk.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.talk.domain.TalkSystem;
import com.ysten.local.bss.talk.repository.ITalkSystemRepository;
import com.ysten.local.bss.talk.repository.mapper.TalkSystemMapper;
import com.ysten.utils.page.Pageable;

@Repository
public class TalkSystemRepositoryImpl implements ITalkSystemRepository {
    
    @Autowired
    private TalkSystemMapper talkSystemMapper;
    
    @Override
    public List<TalkSystem> findTalkSystemByCode(String code) {
        return this.talkSystemMapper.findTalkSystemByCode(code);
    }

    @Override
    public Pageable<TalkSystem> findAllTalkSystem(int pageNo, int pageSize) {
        List<TalkSystem> talkSystem = this.talkSystemMapper.findAllTalkSystem(pageNo,pageSize);
        int count = this.talkSystemMapper.getCount();
        return new Pageable<TalkSystem>().instanceByPageNo(talkSystem, count, pageNo, pageSize);
    }

    @Override
    public boolean saveTalkSystem(TalkSystem talkSystem) {
        return 1 == this.talkSystemMapper.save(talkSystem);
    }

    @Override
    public boolean updateTalkSystem(TalkSystem talkSystem) {
        return 1 == this.talkSystemMapper.update(talkSystem);
    }

    @Override
    public TalkSystem getTalkSystemById(Long id) {
        return this.talkSystemMapper.getById(id);
    }

	@Override
	public List<TalkSystem> getAllTalkSystem() {
		return this.talkSystemMapper.getAllTalkSystem();
	}
	
}