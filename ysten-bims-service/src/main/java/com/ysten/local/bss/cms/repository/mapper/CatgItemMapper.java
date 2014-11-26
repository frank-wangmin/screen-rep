package com.ysten.local.bss.cms.repository.mapper;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.cms.domain.CatgItem;

public interface CatgItemMapper {

	CatgItem getCatgItemByCatgItemId(@Param("catgItemId")Long catgItemId);
	
}
