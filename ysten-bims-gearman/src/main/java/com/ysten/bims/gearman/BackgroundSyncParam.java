package com.ysten.bims.gearman;

import org.apache.commons.lang.StringUtils;

public class BackgroundSyncParam extends SyncParam {
	protected Long background_image_id;
	
	public Long getBackground_image_id() {
		return background_image_id;
	}

	public void setBackground_image_id(Long background_image_id) {
		this.background_image_id = background_image_id;
	}
	
	public String getQueryString() {
		SyncType syncType = SyncType.valueOf(type);
		if (syncType == SyncType.BACKGROUND) {
			StringBuffer query = new StringBuffer();
			if ("bss_background_image".equals(table)) {
				query.append("*background_image_id:*;").append(background_image_id).append(";*");
			} else if ("bss_background_image_user_map".equals(table)) {
				query.append("*background_image_id:*;").append(background_image_id).append(";*");
				if (StringUtils.isNotEmpty(customer_code)) {
					query.append("customer_code:")
							.append(customer_code).append(',');
				} else {
					query.append('*');
				}
				if (user_group_id != null && user_group_id > 0) {
					query.append("user_group_id:").append(user_group_id).append(',');
				}
				query.append("*");
			} else if ("bss_background_image_device_map".equals(table)) {
				query.append('*');
				if (StringUtils.isNotEmpty(ysten_id)) {
					query.append("ysten_id:").append(ysten_id).append(',');
				}
				if (device_group_id != null && device_group_id > 0) {
					query.append("background_image_id:*;").append(background_image_id).append(';');
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
		sb.append(" \nbackground_image_id: " + background_image_id);
		sb.append(" \nquery string: " + getQueryString());
		return sb.toString();
	}
}
