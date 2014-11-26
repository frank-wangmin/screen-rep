--bss_customer_device_history 增加 user_id 字段
alter table bss_customer_device_history add column user_id  varchar(32)  DEFAULT NULL  AFTER  customer_code;

-- 同步失败数据记录表
CREATE TABLE `bss_sync_data_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `data_id` bigint(19) DEFAULT NULL,
  `device_code` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `back_url` varchar(256) DEFAULT NULL,
  `flag` char(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `service_name` varchar(32) DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8