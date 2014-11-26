-------add column auto_play for the table bss_panel_item----
alter table bss_panel_item add column auto_play int(1);
insert into system_config (configkey, configvalue, zhname, depiction) values ('maxBindDeviceCount', '2', '江苏移动SP模式终端限绑数', '【江苏移动-业务办理】江苏移动SP模式终端限绑数');
insert into system_config (configkey, configvalue, zhname, depiction) values ('secondExtPrdCode', '2000007065', '江苏移动SP模式套餐二编码', '【江苏移动-业务办理】江苏移动SP模式套餐二编码');
insert into system_config (configkey, configvalue, zhname, depiction) values ('servicePlatform', 'JSYD_Hdc', '江苏移动平台标识码', '【江苏移动-业务办理】江苏移动平台标识码,可取值JSYD_Hdc/JSYD_Huawei/JSYD_Zhongxing');
insert into system_config (configkey, configvalue, zhname, depiction) values ('changeBindingAuto', 'true', '江苏移动平台自动换机开关', '【江苏移动-业务办理】自动换机开关，true表示自动换机');


