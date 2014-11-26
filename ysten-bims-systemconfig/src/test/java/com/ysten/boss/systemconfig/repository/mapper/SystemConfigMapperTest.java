package com.ysten.boss.systemconfig.repository.mapper;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ysten.boss.systemconfig.domain.SystemConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "SystemConfigMapperTest-context.xml")
@ActiveProfiles("dev")
public class SystemConfigMapperTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	public SystemConfigMapper systemConfigMapper;

	@Test
	public void getSystemConfigValueByKeyWord() {
		String configKey = "keyword";
		SystemConfig value = systemConfigMapper
				.findSystemConfigByConfigKey(configKey);
		assertTrue(value.getConfigValue().equals("contentB"));
	}

	@Test
	public void findAllSystemConfig() {
		List<SystemConfig> result = systemConfigMapper.findAllSystemConfig();
		assertTrue(result.size() == 7);
	}

	@Test
	public void updateSystemConfigById() {
		String configKey = "keyword";
		String configValue = "contentTT";
		assertTrue(systemConfigMapper
				.updateSystemConfig(configKey, configValue) > 0);
		SystemConfig value = systemConfigMapper
				.findSystemConfigByConfigKey(configKey);
		assertTrue(value.getConfigValue().equals("contentTT"));
	}
}
