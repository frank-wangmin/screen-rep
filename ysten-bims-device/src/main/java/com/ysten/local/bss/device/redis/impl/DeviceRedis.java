package com.ysten.local.bss.device.redis.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.redis.IDeviceRedis;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.device.utils.SerializeUtil;

/**
 * @author cwang
 * @version 2014-3-20 上午11:22:52
 * 
 */
@Repository
public class DeviceRedis implements IDeviceRedis {
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	private static final Logger logger = LoggerFactory.getLogger(DeviceRedis.class);

	@Override
	public boolean save(final Device bean) {
		if (bean != null) {
			try {
				return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
					@Override
					public Object doInRedis(RedisConnection connection)
							throws DataAccessException {
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + bean.getId()), SerializeUtil.serialize(bean));
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + bean.getCode()), SerializeUtil.serialize(bean));
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + bean.getSno()), SerializeUtil.serialize(bean));
						return true;
					}
				});
			} catch (Exception e) {
				logger.error("DeviceRedis save exception:", e);
				return false;
			}
		}
		return false;
	}

	@Override
	public Device getDeviceByCodeIdSno(final String codeIdSno) {
		try {
			return redisTemplate.execute(new RedisCallback<Device>() {
				@Override
				public Device doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] key = redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + codeIdSno);
					if (connection.exists(key)) {
						byte[] value = connection.get(key);
						Device device = (Device) SerializeUtil.unserialize(value);
						return device;
					}
					return null;
				}
			});
		} catch (Exception e) {
			logger.error("DeviceRedis getDeviceByCodeIdSno exception:", e);
			return null;
		}

	}

	@Override
	public boolean delete(final Device bean) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) {
					Long ii = connection.del(redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + bean.getId()));
					Long jj = connection.del(redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + bean.getCode()));
					Long kk = connection.del(redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + bean.getSno()));
					if ((ii != null && ii != 0) && (jj != null && jj != 0) && (kk != null && kk != 0)) {
						return true;
					}
					return false;
				}
			});
		} catch (Exception e) {
			logger.error("DeviceRedis delete exception:", e);
			return false;
		}
	}

	@Override
	public boolean update(final Device newBean) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {

					connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + newBean.getId()), SerializeUtil.serialize(newBean));
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + newBean.getCode()), SerializeUtil.serialize(newBean));
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_DEVICE_KEY + newBean.getSno()), SerializeUtil.serialize(newBean));
					return true;
				
				}
			});
		} catch (Exception e) {
			logger.error("DeviceRedis update exception:", e);
			return false;
		}
	}
}
