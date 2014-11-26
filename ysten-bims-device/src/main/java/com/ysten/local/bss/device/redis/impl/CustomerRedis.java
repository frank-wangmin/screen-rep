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

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.redis.ICustomerRedis;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.device.utils.SerializeUtil;

/**
 * @author cwang
 * @version 2014-3-21 上午10:36:14
 * 
 */
@Repository
public class CustomerRedis implements ICustomerRedis {
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	private static final Logger logger = LoggerFactory.getLogger(CustomerRedis.class);

	@Override
	public boolean save(final Customer bean) {
		if (bean != null) {
			try {
				return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
					@Override
					public Object doInRedis(RedisConnection connection)
							throws DataAccessException {
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_CUSTOMER_KEY + bean.getCode()), SerializeUtil.serialize(bean));
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_CUSTOMER_KEY + bean.getId()), SerializeUtil.serialize(bean));
						return true;
					}
				});
			} catch (Exception e) {
				logger.error("CustomerRedis save exception:", e);
				return false;
			}
		}
		return false;
	}

	@Override
	public Customer getCustomerByCodeOrId(final String codeOrId) {
		try {
			return redisTemplate.execute(new RedisCallback<Customer>() {
				@Override
				public Customer doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] key = redisTemplate.getStringSerializer().serialize(Constant.REDIS_CUSTOMER_KEY + codeOrId);
					if (connection.exists(key)) {
						byte[] value = connection.get(key);
						Customer device = (Customer) SerializeUtil.unserialize(value);
						return device;
					}
					return null;
				}
			});
		} catch (Exception e) {
			logger.error("CustomerRedis getCustomerByCodeOrId exception:", e);
			return null;
		}
	}

	@Override
	public boolean delete(final Customer bean) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) {
					Long ii = connection.del(redisTemplate.getStringSerializer().serialize(Constant.REDIS_CUSTOMER_KEY + bean.getCode()));
					Long jj = connection.del(redisTemplate.getStringSerializer().serialize(Constant.REDIS_CUSTOMER_KEY + bean.getId()));
					if ((ii != null && ii != 0) && (jj != null && jj != 0)) {
						return true;
					}
					return false;
				}
			});
		} catch (Exception e) {
			logger.error("CustomerRedis delete exception:", e);
			return false;
		}
	}

	@Override
	public boolean update(final Customer newBean) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_CUSTOMER_KEY + newBean.getCode()), SerializeUtil.serialize(newBean));
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_CUSTOMER_KEY + newBean.getId()), SerializeUtil.serialize(newBean));
					return true;
				}
			});
		} catch (Exception e) {
			logger.error("CustomerRedis update exception:", e);
			return false;
		}
	}
}
