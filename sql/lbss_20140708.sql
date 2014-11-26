-------增加开机关机认证日志表----
DROP TABLE IF EXISTS `bss_device_operate_log`;
CREATE TABLE `bss_device_operate_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `device_code` varchar(32) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `result` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
-- 媒体分发进度保存任务ID字段
alter table bss_program_bitrate_record add column task_id varchar(50) default null;
-- 邮件发送配置文件
INSERT INTO `system_config` VALUES ('135', 'isStatisticsAllDate', 'true', '是否统计全部数据', '【发送邮件】是否统计全部数据（true：统计全部数据；false：统计当日数据）');
INSERT INTO `system_config` VALUES ('136', 'mailServer', 'smtp.ysten.com:25', '邮箱地址和端口', '【发送邮件】邮箱地址和端口（例如：smtp.163.com:25）');
INSERT INTO `system_config` VALUES ('137', 'sendMailAccount', 'username@ysten.com:password', '发件人账号密码', '【发送邮件】发件人账号密码（例如：lufei@ysten.com:123456）');
INSERT INTO `system_config` VALUES ('138', 'mailReceive', 'user1@ysten.com,user2@ysten.com', '邮件接收者账户', '【发送邮件】邮件接收者账户,多个用英文逗号隔开（例如：a@ysten.com,b@ysten.com）');
INSERT INTO `system_config` VALUES ('139', 'webHttp', 'http://127.0.0.1:8080/ysten-local-bss-push-webapp/', '系统访问地址', '【发送邮件】系统访问地址，例如：http://127.0.0.1:8080/ysten-local-bss-push-webapp/');


-- bss_city 表增加district_code行政区划编码
alter table bss_city add column district_code varchar(6) default null;