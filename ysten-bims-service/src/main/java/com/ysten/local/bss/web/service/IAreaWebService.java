package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.area.domian.AreaBean;

public interface IAreaWebService {
	List<AreaBean> findAllArea();

    Area getAreaById(Long areaId);
}
