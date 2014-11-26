package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.PanelNavBox;

public interface PanelNavBoxMapper {
    /**
     * 根据标题查询导航
     * @param title
     * @param pageNo
     * @param pageSize
     * @return
     */
	  List<PanelNavBox> findAllByTitle(@Param("title") String title,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

	  int findCountByTitle(@Param("title") String title);
	  
	  PanelNavBox getById(@Param("id") Long id);
	  
	  int save(PanelNavBox panelNavBox);
	  
	  int update(PanelNavBox panelNavBox);
	  
	  int delete(@Param("ids")List<Long> ids);
	  
	  List<PanelNavBox> findAll();
}