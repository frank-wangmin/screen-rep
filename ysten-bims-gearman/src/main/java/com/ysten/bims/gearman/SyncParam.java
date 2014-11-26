package com.ysten.bims.gearman;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SyncParam implements ISyncParam {

	// private static final String[] TABLE_NAMES = {"bss_boot_animation", ""};
	protected String type;
	protected String customer_code;
	protected Long user_group_id;
	protected Long device_group_id;
	protected String ysten_id;
	protected String table;

	public SyncParam() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustomer_code() {
		return customer_code;
	}

	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}

	public Long getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(Long user_group_id) {
		this.user_group_id = user_group_id;
	}

	public Long getDevice_group_id() {
		return device_group_id;
	}

	public void setDevice_group_id(Long device_group_id) {
		this.device_group_id = device_group_id;
	}

	public String getYsten_id() {
		return ysten_id;
	}

	public void setYsten_id(String ysten_id) {
		this.ysten_id = ysten_id;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	// animation_ysten_id:ystenid|boot_animation_id:boot_animation_id|customer_code:code|user_group_id:id|device_group_id:id
	public String getQueryString() {
		StringBuffer query = new StringBuffer();
		SyncType syncType = SyncType.valueOf(type);
		switch (syncType) {
		case ALL:
			if (StringUtils.isNotEmpty(customer_code)) {
				query.append("*customer_code:").append(customer_code)
						.append(",");
				query.append("user_group_id:")
						.append(user_group_id != null ? user_group_id : "")
						.append(",*");
			} else if (StringUtils.isNotEmpty(ysten_id)) {
				query.append("*ysten_id:").append(ysten_id).append(",*");
				query.append("device_group_id:")
						.append(device_group_id != null ? device_group_id : "")
						.append(",*");
			}
			break;
		}
		return query.toString();
	}

	@Override
	public String toString() {
		StringBuffer sb = getDebugInfo();
		sb.append(" \nquery string: " + getQueryString());
		return sb.toString();
	}

	public StringBuffer getDebugInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\ntype: ").append(type).append(" \ntable: ").append(table)
				.append(" \nysten_id: ").append(ysten_id)
				.append(" \ncustomer_code: ").append(customer_code)
				.append(" \nuser_group_id: ").append(user_group_id)
				.append(" \ndevice_group_id: ").append(device_group_id);
		return sb;
	}

	public static ISyncParam parseSyncParam(String data) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(data);
		JsonObject object = element.getAsJsonObject();
		SyncType type = SyncType.valueOf(object.get("type").getAsString());
		Gson gson = new Gson();
		switch (type) {
		case ALL:
			return gson.fromJson(element, SyncParam.class);
		case ANIMATION:
			return gson.fromJson(element, AnimationSyncParam.class);
		case BACKGROUND:
			return gson.fromJson(element, BackgroundSyncParam.class);
		case BOOTSTRAP:
			return gson.fromJson(element, BootstrapSyncParam.class);
		case PANEL_ALL:
		case PANEL_DATA:
		case PANEL_STYLE_DATA:
			return gson.fromJson(element, PanelSyncParam.class);
		}

		return null;
	}

	@Override
	public SyncType getSyncType() {
		return SyncType.valueOf(type);
	}

}
