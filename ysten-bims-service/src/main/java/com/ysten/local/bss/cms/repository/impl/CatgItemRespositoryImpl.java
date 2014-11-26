package com.ysten.local.bss.cms.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.cms.domain.CatgItem;
import com.ysten.local.bss.cms.repository.ICatgItemRespository;
import com.ysten.local.bss.cms.repository.mapper.CatgItemMapper;

@Repository
public class CatgItemRespositoryImpl implements ICatgItemRespository {

	@Autowired
	private CatgItemMapper catgItemMapper;
	
	@Override
	public CatgItem getCatgItemByCatgItemId(Long catgItemId) {
		return catgItemMapper.getCatgItemByCatgItemId(catgItemId);
	}

}
