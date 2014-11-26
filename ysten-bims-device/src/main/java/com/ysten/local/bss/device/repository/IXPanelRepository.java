package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.PanelImgBox;
import com.ysten.local.bss.device.domain.PanelNavBox;
import com.ysten.local.bss.device.domain.PanelTextBox;
import com.ysten.utils.page.Pageable;

public interface IXPanelRepository {
	
	Pageable<PanelTextBox> findAllTextBoxByCondition(String title,Long progromId,int isNew,Integer pageNo,Integer pageSize);

	Pageable<PanelImgBox> findAllImgBoxByCondition(String title,Long progromId,Integer pageNo, Integer pageSize);

	Pageable<PanelNavBox> findAllNavBoxByTitle(String title,Integer pageNo, Integer pageSize);
	
	PanelTextBox getTextBoxById(Long id);
	  
	boolean saveTextBox(PanelTextBox panelTextBox);
	  
	boolean updateTextBox(PanelTextBox panelTextBox);
	  
	boolean deleteTextBox(List<Long> ids);

	  PanelImgBox getImgBoxById(Long id);
	  
	  boolean saveImgBox(PanelImgBox panelImgBox);
	  
	  boolean updateImgBox(PanelImgBox panelImgBox);
	  
	  boolean deleteImgBox(List<Long> ids);
	  
	  PanelNavBox getNavBoxById(Long id);
	  
	  boolean saveNavBox(PanelNavBox panelNavBox);
	  
	  boolean updateNavBox(PanelNavBox panelNavBox);
	  
	  boolean deleteNavBox(List<Long> ids);
	  
	  List<PanelImgBox> getByImgBoxId(Long imgBoxId);
	  
	  List<PanelTextBox> findAllTextBox();
	  
	  List<PanelNavBox> findAllNavBox();
	  
	  List<PanelImgBox> findAllImgBox();
	
}
