/*为bss_customer表添加是否需要同步*/
ALTER TABLE `bss_customer`
	ADD COLUMN `is_sync` varchar(32) NULL DEFAULT NULL AFTER `is_lock`;
insert into system_config (configkey, configvalue, zhname, depiction) values ('ottOrderUrl', 'http://223.87.20.71:4337/instantSubscribe', '订购WebServcie服务端', '订购WebServcie服务端');
insert into system_config (configkey, configvalue, zhname, depiction) values ('ottInquiryUrl', 'http://223.87.20.71:4337/ServiceAuthIn', '鉴权WebServcie服务端', '鉴权WebServcie服务端');
insert into system_config (configkey, configvalue, zhname, depiction) values ('ottType', 'ZHONGXING', 'OTT厂商类型', 'OTT厂商类型');
insert into system_config (configkey, configvalue, zhname, depiction) values ('ottInquiry', '1', 'OTT鉴权开关，1：开起，0：关闭', 'OTT厂商类型');
insert into system_config (configkey, configvalue, zhname, depiction) values ('localInquiry', '1', '本地鉴权开关，1：开起，0：关闭', 'OTT厂商类型');