package com.ysten.local.bss.panel.service;

/**
 * @author cwang
 * @version 2014-7-10 下午1:02:46
 * 
 */
public interface IPanelDataStyleService {
	
	String getCustomerPanel(String templateId) throws Exception;

	String getPanelStyle(String templateId, String panelIds)throws Exception;

	String getPanelData(String templateId, String panelIds)throws Exception;
}
