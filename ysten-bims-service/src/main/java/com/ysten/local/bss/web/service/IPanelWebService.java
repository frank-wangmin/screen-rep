package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.local.bss.device.domain.PanelImgBox;
import com.ysten.local.bss.device.domain.PanelNavBox;
import com.ysten.local.bss.device.domain.PanelTextBox;
import com.ysten.utils.page.Pageable;

public interface IPanelWebService {
	
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

	String navBoxsXml(List<PanelNavBox> panelNavBoxList);

	String textBoxsXml(List<PanelTextBox> panelTextBoxList);

	String imgBoxsXml(List<PanelImgBox> panelImgBoxList);
	
	  
	  List<PanelTextBox> findAllTextBox();
	  
	  List<PanelNavBox> findAllNavBox();
	  
	  List<PanelImgBox> findAllImgBox();
}
