package com.ysten.boss.systemconfig.service;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ysten.boss.systemconfig.domain.SystemConfig;
import com.ysten.utils.page.Pageable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="SystemConfigServiceTest-context.xml")
@ActiveProfiles("dev")
public class SystemConfigServiceTest {

	@Autowired
    public SystemConfigService systemConfigService;
	
    @Before
    public void setUp() throws Exception {
        BasicConfigurator.configure();
    }
	
	@Test
	public void getSystemConfigValueByKeyWord()
	{
		String configKey = "keyword";
		String value = systemConfigService.getSystemConfigByConfigKey(configKey);
		assertTrue(value.equals("contentB"));
	}
	
	@Test
	public void findAllSystemConfigForPageable()
	{
		Pageable<SystemConfig> result = systemConfigService.findAllSystemConfig();
		assertTrue(result.getTotal() == 7);
	}
	
	@Test
	public void findSystemConfigForPageableByKey()
	{
		String configKey = "keyword";
		Pageable<SystemConfig> result = systemConfigService.findSystemConfigByConfigKey(configKey);
		assertTrue(result.getTotal() == 1);
		assertTrue(result.getData().get(0).getConfigKey().equals(configKey));
	}
	
	@Test
	public void updateSystemConfigByIdAndKeyWord()
	{
		String configKey = "keyword";
		String configValue = "contentB";
		boolean falg = systemConfigService.updateSystemConfig(configKey, configValue);
		assertTrue(falg == true);
		String value = systemConfigService.getSystemConfigByConfigKey(configKey);
		assertTrue(value.equals("contentB"));
	}
	
	@Test
	public void loadSystemConfig()
	{
//		assertTrue(systemConfigService.loadSystemConfig());
	}
}
