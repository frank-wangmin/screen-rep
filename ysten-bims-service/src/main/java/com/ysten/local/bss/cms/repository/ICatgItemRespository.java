package com.ysten.local.bss.cms.repository;

import com.ysten.local.bss.cms.domain.CatgItem;

public interface ICatgItemRespository {
	
	CatgItem getCatgItemByCatgItemId(Long catgItemId);
}
