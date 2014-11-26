package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.PanelTextBox;

public interface PanelTextBoxMapper {
	/**
	 * 根据条件查找文本集
	 * @param title
	 * @param progromId
	 * @param isNew
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<PanelTextBox> findAllByCondition(@Param("title") String title,@Param("progromId") Long progromId,@Param("isNew") int isNew,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

	int findCountByCondition(@Param("title") String title,@Param("progromId") Long progromId,@Param("isNew") int isNew);

	PanelTextBox getById(@Param("id") Long id);
	  
	int save(PanelTextBox panelTextBox);
	  
	int update(PanelTextBox panelTextBox);
	  
	int delete(@Param("ids")List<Long> ids);
	
	List<PanelTextBox> findAll();
}