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

import com.ysten.local.bss.device.domain.Account;
import com.ysten.local.bss.device.redis.IAccountRedis;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.device.utils.SerializeUtil;

/**
 * @author cwang
 * @version 2014-3-21 上午11:14:48
 * 
 */
@Repository
public class AccountRedis implements IAccountRedis {
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	private static final Logger logger = LoggerFactory.getLogger(AccountRedis.class);

	@Override
	public boolean save(final Account bean) {
		if (bean != null) {
			try {
				return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
					@Override
					public Object doInRedis(RedisConnection connection)
							throws DataAccessException {
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_ACCOUNT_KEY + bean.getId()), SerializeUtil.serialize(bean));
						connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_ACCOUNT_KEY + bean.getCode()), SerializeUtil.serialize(bean));
						return true;
					}
				});
			} catch (Exception e) {
				logger.error("AccountRedis save exception:", e);
				return false;
			}
		}
		return false;
	}

	@Override
	public Account readByIdOrCode(final String idOrCode) {
		try {
			return redisTemplate.execute(new RedisCallback<Account>() {
				@Override
				public Account doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] key = redisTemplate.getStringSerializer().serialize(Constant.REDIS_ACCOUNT_KEY + idOrCode);
					if (connection.exists(key)) {
						byte[] value = connection.get(key);
						Account account = (Account) SerializeUtil.unserialize(value);
						return account;
					}
					return null;
				}
			});
		} catch (Exception e) {
			logger.error("AccountRedis readByIdOrCode exception:", e);
			return null;
		}
	}

	@Override
	public boolean delete(final Account bean) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) {
					Long ii = connection.del(redisTemplate.getStringSerializer().serialize(Constant.REDIS_ACCOUNT_KEY + bean.getId()));
					Long jj = connection.del(redisTemplate.getStringSerializer().serialize(Constant.REDIS_ACCOUNT_KEY + bean.getCode()));
					if ((ii != null && ii != 0) && (jj != null && jj != 0)) {
						return true;
					}
					return false;
				}
			});
		} catch (Exception e) {
			logger.error("AccountRedis delete exception:", e);
			return false;
		}
	}

	@Override
	public boolean update(final Account newBean) {
		try {
			return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_ACCOUNT_KEY + newBean.getId()), SerializeUtil.serialize(newBean));
					connection.set(redisTemplate.getStringSerializer().serialize(Constant.REDIS_ACCOUNT_KEY + newBean.getCode()), SerializeUtil.serialize(newBean));
					return true;
				}
			});
		} catch (Exception e) {
			logger.error("AccountRedis update exception:", e);
			return false;
		}
	}
}
