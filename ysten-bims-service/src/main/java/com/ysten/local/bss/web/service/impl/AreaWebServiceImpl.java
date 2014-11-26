package com.ysten.local.bss.web.service.impl;

import com.ysten.area.domain.Area;
import com.ysten.area.repository.IAreaRepository;
import com.ysten.local.bss.area.domian.AreaBean;
import com.ysten.local.bss.area.domian.AreaChildBean;
import com.ysten.local.bss.web.service.IAreaWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaWebServiceImpl implements IAreaWebService {
    @Autowired
    private IAreaRepository areaRepository;

    @Override
    public List<AreaBean> findAllArea() {
        List<AreaBean> result = null;
        List<Area> areas = this.areaRepository.findAllArea();
        if (areas == null || areas.isEmpty())
            return null;
        for (Area area : areas) {
            AreaBean ab = null;
            if (area.getParentId() == -1) {
                ab = new AreaBean(area.getId(), area.getName(), true);
            } else {
                ab = new AreaChildBean(area.getId(), area.getName(), area.getParentId());
            }
            if (result == null) {
                result = new ArrayList<AreaBean>();
            }
            result.add(ab);
        }
        return result;
    }

    @Override
    public Area getAreaById(Long areaId) {
        return this.areaRepository.getAreaById(areaId);
    }


}
