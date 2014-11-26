package com.ysten.bims.gearman;

public class PanelSyncParam implements ISyncParam {
	protected String panel_package_id;
	protected Long panel_id;
	protected Long panel_item_id;
	// bss_panel_package_panel_map, nav_id是一串以逗号分隔的字符串,目前不做匹配
	protected Long nav_id;

	protected String type;
	protected String table;

	public String getPanel_package_id() {
		return panel_package_id;
	}

	public void setPanel_package_id(String panel_package_id) {
		this.panel_package_id = panel_package_id;
	}

	public Long getPanel_id() {
		return panel_id;
	}

	public void setPanel_id(Long panel_id) {
		this.panel_id = panel_id;
	}

	public Long getPanel_item_id() {
		return panel_item_id;
	}

	public void setPanel_item_id(Long panel_item_id) {
		this.panel_item_id = panel_item_id;
	}

	public Long getNav_id() {
		return nav_id;
	}

	public void setNav_id(Long nav_id) {
		this.nav_id = nav_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getQueryString() {
		StringBuffer query = new StringBuffer();
		SyncType syncType = getSyncType();
		if ("bss_panel_package".equals(table)) { // data, style, customer
			query.append("panel_package_id:").append(panel_package_id).append(",*");
		} else if ("bss_panel".equals(table)) { // data, style, customer
			query.append("panel_package_id:*").append(",panel_id:*;").append(panel_id).append(";*");
		} else if ("bss_panel_item".equals(table)) { // data
			query.append('*').append(",panel_item_id:*;").append(panel_item_id).append(";*,type:data");
		} else if ("bss_panel_nav_define".equals(table)) { // data or all
			query.append('*').append(",nav_id:*;").append(nav_id).append(";*");
			if (syncType == SyncType.PANEL_DATA) { 
				query.append(",type:data");
			}
		} else if ("bss_panel_panel_item_map".equals(table)) { // data
			query.append('*').append(",panel_id:*;").append(panel_id).append(";*");
			query.append("panel_item_id:*;").append(panel_item_id).append(";*,type:data");
		} else if ("bss_panel_package_panel_map".equals(table)) { // all
			query.append("panel_package_id:").append(panel_package_id);
			query.append(",panel_id:*;").append(panel_id).append(";*");
			// query.append(",nav_id:*;").append(nav_id).append(";*");
		} else if ("bss_preview_item_data".equals(table)) { // data, style
			query.append('*').append(",panel_id:*;").append(panel_id).append(";*");
			query.append("panel_item_id:*;").append(panel_item_id).append(";*");
		}
		return query.toString();
	}

	@Override
	public SyncType getSyncType() {
		return SyncType.valueOf(type);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("type: ").append(type).append(" \ntable: ").append(table)
				.append(" \npanel_package_id: ").append(panel_package_id)
				.append(" \npanel_id: ").append(panel_id)
				.append(" \npanel_item_id: ").append(panel_item_id)
				.append(" \nnav_id: ").append(nav_id);
		sb.append(" \nquery string: " + getQueryString());
		return sb.toString();
	}
}
