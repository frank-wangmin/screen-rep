package com.ysten.local.bss.facade.job;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.ysten.cache.Cache;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.notice.service.ISysNoticeService;

public class ScanExpiringDeviceJob {
	private static final String EXPIRE_DEVICE_ID_KEY = "ysten:expiring_device:id:key";

	private static final Logger logger = LoggerFactory
			.getLogger(ScanExpiringDeviceJob.class);

	private Cache<String, Serializable> cache;

	private ISysNoticeService sysNoticeService;

	public void setCache(Cache<String, Serializable> cache) {
		this.cache = cache;
	}

	public void setSysNoticeService(ISysNoticeService sysNoticeService) {
		this.sysNoticeService = sysNoticeService;
	}

	public void scanExpiringDevice() {
		Set<String> keys = cache.findKeysByRegular(EXPIRE_DEVICE_ID_KEY + "*");
		if (!CollectionUtils.isEmpty(keys)) {
			for (String key : keys) {
				cache.remove(key);
			}
		}

		Map<String, ArrayList<SysNotice>> map = sysNoticeService
				.getSysNoticeForExpiringDevices();
		if (map != null) {
			Set<String> ystenIds = map.keySet();
			for (String id : ystenIds) {
				cache.put(EXPIRE_DEVICE_ID_KEY + id, map.get(id), 24L,
						TimeUnit.HOURS);
			}
		}
	}
}
