package com.ysten.local.bss.util.bean;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis 工具类 Created by frank on 14-6-17.
 */
@Component
public class RedisUtils {

	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);


	/**
	 * 缓存数据库中的表，对应的key,与接口中的key 供触发器使用
	 * @param ystenid
	 * @param businessId
	 * @param customerCode
	 * @param userGroupId
	 * @param deviceGroupId
	 * @param panelPackageId
	 * @param value 接口的key
	 * @param type
	 * @return
	 *ysten_id:ystenid,boot_animation_id:boot_animation_id,customer_code:code,user_group_id:id,device_group_id:id
	 *ysten_id:ysten_id,background_image_id:background_image_id;background_image_id;,customer_code:code,user_group_id:user_group_id,device_group_id:device_group_id
	 *ysten_id:,service_collect_id:,customer_code:, customer_id:, user_group_id:, device_group_id:,panel_package_id:
	 */
	public static boolean saveBootImageAnimationCache(final RedisTemplate<Serializable, Serializable> redisTemplate, final String ystenid, final String businessId, final String customerCode,final String userGroupId, final String deviceGroupId, final String panelPackageId, final String value,final DeviceInterfaceType type) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					switch (type) {
					case Animation:
						connection.set(redisTemplate.getStringSerializer().serialize("ysten_id:" + ystenid + ",boot_animation_id:" + businessId + ",customer_code:" + customerCode + ",user_group_id:" + userGroupId + ",device_group_id:" + deviceGroupId), redisTemplate.getStringSerializer().serialize(value));
						break;
					case BackgroundImage:
						connection.set(redisTemplate.getStringSerializer().serialize("ysten_id:" + ystenid + ",background_image_id:" + businessId + ",customer_code:" + customerCode + ",user_group_id:" + userGroupId + ",device_group_id:" + deviceGroupId), redisTemplate.getStringSerializer().serialize(value));
						break;
					case Boot:
						connection.set(redisTemplate.getStringSerializer().serialize("ysten_id:" + ystenid + ",service_collect_id:" + businessId + ",customer_code:" + customerCode+",panel_package_id:"+panelPackageId +",user_group_id:" + userGroupId + ",device_group_id:" + deviceGroupId), redisTemplate.getStringSerializer().serialize(value));
						break;	
					default:
						return false;
					}
					return true;
				}
			});
		} catch (Exception e) {
			logger.error(type + "interface, RedisUtils saveCache exception:", e);
			return false;
		}
	}

	 //data  panel_package_id:1,panel_id:;1;2;,nav_id:;1;2;,panel_item_id:;1;2;
	 //custom, style   panel_package_id:1,panel_id:;1;2;,nav_id:;1;2;,type:custom|style
	public static boolean savePanelCache(final RedisTemplate<Serializable, Serializable> redisTemplate, final String panelPackageId, final String panelIds, final String navIds,final String panelItemIds,  final String value,final DeviceInterfaceType type) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					switch (type) {
					case PanelData:
						connection.set(redisTemplate.getStringSerializer().serialize("panel_package_id:" + panelPackageId + ",panel_id:" + panelIds + ",nav_id:" + navIds + ",panel_item_id:" + panelItemIds+",type:data"), redisTemplate.getStringSerializer().serialize(value));
						break;
					case PanelStyle:
						connection.set(redisTemplate.getStringSerializer().serialize("panel_package_id:" + panelPackageId + ",panel_id:" + panelIds + ",nav_id:" + navIds + ",panel_item_id:" + panelItemIds+",type:style"), redisTemplate.getStringSerializer().serialize(value));
						break;
					case PanelCustom:
						connection.set(redisTemplate.getStringSerializer().serialize("panel_package_id:" + panelPackageId + ",panel_id:" + panelIds + ",nav_id:" + navIds + ",panel_item_id:" + panelItemIds+",type:custom"), redisTemplate.getStringSerializer().serialize(value));
						break;	
					default:
						return false;
					}
					return true;
				}
			});
		} catch (Exception e) {
			logger.error(type + "interface, RedisUtils saveCache exception:", e);
			return false;
		}
	}
	// public boolean save(final String key, final Object value) {
	// if (value != null) {
	// try {
	// return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
	//
	// @Override
	// public Object doInRedis(RedisConnection connection)
	// throws DataAccessException {
	// connection.set(redisTemplate.getStringSerializer().serialize(key),
	// SerializeUtil.serialize(value));
	// return true;
	// }
	// });
	// } catch (Exception e) {
	// logger.error("RedisUtils save object exception:", e);
	// return false;
	// }
	// }
	// return false;
	// }
	//
	// public boolean delete(final String key) {
	// try {
	// return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
	// public Object doInRedis(RedisConnection connection) {
	// Long count =
	// connection.del(redisTemplate.getStringSerializer().serialize(key));
	// if (count != null && count >= 0) {
	// return true;
	// }
	// return false;
	// }
	// });
	// } catch (Exception e) {
	// logger.error("RedisUtils delete exception:", e);
	// return false;
	// }
	// }
	//
	// public boolean update(final String key, final Object value) {
	// try {
	// return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
	// @Override
	// public Object doInRedis(RedisConnection connection)
	// throws DataAccessException {
	//
	// connection.set(redisTemplate.getStringSerializer().serialize(key),
	// SerializeUtil.serialize(value));
	// return true;
	//
	// }
	// });
	// } catch (Exception e) {
	// logger.error("RedisUtils update exception:", e);
	// return false;
	// }
	// }
	//
	// public Object get(final String key) {
	// try {
	// return redisTemplate.execute(new RedisCallback<Object>() {
	// @Override
	// public Object doInRedis(RedisConnection connection)
	// throws DataAccessException {
	// byte[] serializeKey = redisTemplate.getStringSerializer().serialize(key);
	// if (connection.exists(serializeKey)) {
	// byte[] value = connection.get(serializeKey);
	// Object object = SerializeUtil.unserialize(value);
	// return object;
	// }
	// return null;
	// }
	// });
	// } catch (Exception e) {
	// logger.error("RedisUtils get exception:", e);
	// return null;
	// }
	// }

	public enum DeviceInterfaceType {
		Animation, Boot, BackgroundImage,PanelData,PanelStyle,PanelCustom;
	}
}
