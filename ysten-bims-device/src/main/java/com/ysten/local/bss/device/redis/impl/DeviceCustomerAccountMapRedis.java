package com.ysten.local.bss.device.redis.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.redis.IDeviceCustomerAccountMapRedis;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.device.utils.SerializeUtil;

/**
 * @author cwang
 * @version 2014-3-21 上午10:59:23
 * 
 */
@Repository
public class DeviceCustomerAccountMapRedis implements IDeviceCustomerAccountMapRedis {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static final Logger logger = LoggerFactory.getLogger(DeviceCustomerAccountMapRedis.class);

	@Override
	public boolean save(final DeviceCustomerAccountMap bean) {
		if (bean != null) {
			try {
				return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
					@Override
					public Object doInRedis(RedisConnection connection)
							throws DataAccessException {
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP  + ":" + bean.getId()), SerializeUtil.serialize(bean));
//						connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP + bean.getId() + ":" + bean.getCustomerCode()), SerializeUtil.serialize(bean));
//						connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP + bean.getId() + ":" + bean.getCustomerId()), SerializeUtil.serialize(bean));
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP  + ":" + bean.getDeviceId()), SerializeUtil.serialize(bean));
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP + ":" + bean.getDeviceCode()), SerializeUtil.serialize(bean));
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP  + ":" + bean.getDeviceSno()), SerializeUtil.serialize(bean));
						return true;
					}
				});
			} catch (Exception e) {
				logger.error("DeviceCustomerAccountMapRedis save exception:", e);
				return false;
			}
		}
		return false;
	}

	@Override
	public List<DeviceCustomerAccountMap> readByType(final String type) {
		try {
			return redisTemplate.execute(new RedisCallback<List<DeviceCustomerAccountMap>>() {
				List<DeviceCustomerAccountMap> list = new ArrayList<DeviceCustomerAccountMap>();

				@Override
				public List<DeviceCustomerAccountMap> doInRedis(RedisConnection connection)
						throws DataAccessException {
					Set<byte[]> set = connection.keys((Constant.DEVICE_CUSTOMER_ACCOUNT_MAP + ":" + type).getBytes());
					for (Iterator<byte[]> iterator = set.iterator(); iterator.hasNext();) {
						byte[] keyByte = iterator.next();
						if (connection.exists(keyByte)) {
							byte[] value = connection.get(keyByte);
							DeviceCustomerAccountMap map = (DeviceCustomerAccountMap) SerializeUtil.unserialize(value);
							list.add(map);
						}
					}
					// list = removeRepeated(list);
					return list;
				}
			});

		} catch (Exception e) {
			logger.error("DeviceCustomerAccountMapRedis readByType exception:", e);
			return null;
		}
	}

	public static ArrayList<DeviceCustomerAccountMap> removeRepeated(List<DeviceCustomerAccountMap> list) {
		ArrayList<DeviceCustomerAccountMap> tmpArr = new ArrayList<DeviceCustomerAccountMap>();
		for (int i = 0; i < list.size(); i++) {
			if (!tmpArr.contains(list.get(i))) {
				tmpArr.add(list.get(i));
			}
		}
		return tmpArr;
	}

	@Override
	public boolean delete(final DeviceCustomerAccountMap bean) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) {
					Long ii = connection.del(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP  + ":" + bean.getId()));
//					Long jj = connection.del(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP + bean.getId() + ":" + bean.getCustomerCode()));
//					Long kk = connection.del(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP + bean.getId() + ":" + bean.getCustomerId()));
					Long ll = connection.del(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP  + ":" + bean.getDeviceId()));
					Long mm = connection.del(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP  + ":" + bean.getDeviceCode()));
					Long nn = connection.del(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP + ":" + bean.getDeviceSno()));
					if ((ii != null && ii != 0)  && (ll != null && ll != 0) && (mm != null && mm != 0) && (nn != null && nn != 0)) {
						return true;
					}
					return false;
				}
			});

		} catch (Exception e) {
			logger.error("DeviceCustomerAccountMapRedis delete exception:", e);
			return false;
		}
	}

	@Override
	public boolean update(final DeviceCustomerAccountMap newBean) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP +  ":"+newBean.getId()), SerializeUtil.serialize(newBean));
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP +  ":"+newBean.getDeviceId()), SerializeUtil.serialize(newBean));
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP +  ":"+newBean.getDeviceCode()), SerializeUtil.serialize(newBean));
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP +  ":"+newBean.getDeviceSno()), SerializeUtil.serialize(newBean));
					return true;
				}
			});
		} catch (Exception e) {
			logger.error("DeviceCustomerAccountMapRedis update exception:", e);
			return false;
		}
	}

	@Override
	public boolean saveAsSet(final DeviceCustomerAccountMap bean) {
		try {
			boolean flag = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					return connection.sAdd(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP), SerializeUtil.serialize(bean));
				}
			});
			return flag;

		} catch (Exception e) {
			logger.error("DeviceCustomerAccountMapRedis saveAsSet exception:", e);
			return false;
		}
	}

	@Override
	public List<DeviceCustomerAccountMap> readAll() {
		try {
			return redisTemplate.execute(new RedisCallback<List<DeviceCustomerAccountMap>>() {
				@Override
				public List<DeviceCustomerAccountMap> doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] key = redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP);
					List<DeviceCustomerAccountMap> list = new ArrayList<DeviceCustomerAccountMap>();
					if (connection.exists(key)) {
						Set<byte[]> value = connection.sMembers(key);
						for (Iterator<byte[]> iterator = value.iterator(); iterator.hasNext();) {
							byte[] bs = iterator.next();
							DeviceCustomerAccountMap dca = (DeviceCustomerAccountMap) SerializeUtil.unserialize(bs);
							list.add(dca);
						}
						return list;
					}
					return null;
				}
			});
		} catch (Exception e) {
			logger.error("DeviceCustomerAccountMapRedis readAll exception:", e);
			return null;
		}
	}

	@Override
	public boolean deleteFromSet(final DeviceCustomerAccountMap bean) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) {
					connection.sRem(redisTemplate.getStringSerializer().serialize(Constant.DEVICE_CUSTOMER_ACCOUNT_MAP), SerializeUtil.serialize(bean));
					return true;
				}
			});
		} catch (Exception e) {
			logger.error("DeviceCustomerAccountMapRedis deleteFromSet exception:", e);
			return false;
		}
	}
}
