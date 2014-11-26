package com.ysten.bims.gearman;

import org.apache.commons.lang.StringUtils;

public class AnimationSyncParam extends SyncParam {
	protected Long boot_animation_id;

	public Long getBoot_animation_id() {
		return boot_animation_id;
	}

	public void setBoot_animation_id(Long boot_animation_id) {
		this.boot_animation_id = boot_animation_id;
	}

	public String getQueryString() {
		SyncType syncType = SyncType.valueOf(type);
		if (syncType == SyncType.ANIMATION) {
			StringBuffer query = new StringBuffer();
			if ("bss_boot_animation".equals(table)) {
				query.append("*boot_animation_id:").append(boot_animation_id).append(',')
						.append('*');
			} else if ("bss_animation_user_map".equals(table)) {
				query.append("*boot_animation_id:").append(boot_animation_id).append(',');
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
			} else if ("bss_animation_device_map".equals(table)) {
				query.append('*');
				if (StringUtils.isNotEmpty(ysten_id)) {
					query.append("ysten_id:").append(ysten_id).append(',');
				}
				if (device_group_id != null && device_group_id > 0) {
					query.append("boot_animation_id:").append(boot_animation_id).append(',');
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
		sb.append(" \nboot_animation_id: " + boot_animation_id);
		sb.append(" \nquery string: " + getQueryString());
		return sb.toString();
	}
}
