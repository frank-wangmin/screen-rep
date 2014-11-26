package com.ysten.bims.gearman;

import org.apache.commons.lang.StringUtils;

public class BootstrapSyncParam extends SyncParam {
	protected Long service_collect_id;
	protected Long panel_package_id;

	public Long getService_collect_id() {
		return service_collect_id;
	}

	public void setService_collect_id(Long service_collect_id) {
		this.service_collect_id = service_collect_id;
	}

	public Long getPanel_package_id() {
		return panel_package_id;
	}

	public void setPanel_package_id(Long panel_package_id) {
		this.panel_package_id = panel_package_id;
	}

	public String getQueryString() {
		SyncType syncType = SyncType.valueOf(type);
		if (syncType == SyncType.BOOTSTRAP) {
			StringBuffer query = new StringBuffer();
			if ("bss_service_info".equals(table)) {
				query.append("*service_collect_id:").append(service_collect_id).append(',')
						.append('*');
			} else if ("bss_panel_package_user_map".equals(table)) {
				query.append("*service_collect_id:").append(service_collect_id).append(',');
				if (StringUtils.isNotEmpty(customer_code)) {
					query.append("customer_code:")
							.append(customer_code).append(',');
				}
				if (user_group_id != null && user_group_id > 0) {
					query.append("*user_group_id:").append(user_group_id).append(',');
				}
				query.append("*");
			} else if ("bss_service_collect_device_group_map".equals(table)) {
				query.append('*');
				if (StringUtils.isNotEmpty(ysten_id)) {
					query.append("ysten_id:").append(ysten_id).append(',');
				}
				if (device_group_id != null && device_group_id > 0) {
					query.append("service_collect_id:").append(service_collect_id).append(',');
					query.append('*').append("device_group_id:")
							.append(device_group_id);
				} else {
					query.append('*').append("device_group_id:");
				}
				query.append('*');
			} else if ("bss_panel_package_device_map".equals(table)) {
				query.append('*');
				if (StringUtils.isNotEmpty(ysten_id)) {
					query.append("ysten_id:").append(ysten_id).append(',');
				}
				
				if (device_group_id != null && device_group_id > 0) {
					query.append("*panel_package_id:").append(panel_package_id).append(",user_group_id:");
					query.append('*').append("device_group_id:")
							.append(device_group_id);
				} else {
					query.append('*').append("device_group_id:");
				}
				query.append('*');
			}
			return query.toString();
		}
		return super.getQueryString();
	}
	
	public String toString() {
		StringBuffer sb = super.getDebugInfo();
		sb.append(" \nservice_collect_id: " + service_collect_id + " \npanel_package_id: " + panel_package_id);
		sb.append(" \nquery string: " + getQueryString());
		return sb.toString();
	}

}
