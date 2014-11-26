CREATE TABLE `bss_sys_version` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `version_id` varchar(32) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('sys_version_list','系统版本管理','系统版本管理',8);

INSERT INTO bss_sys_menu(authority_id, name, url, order_sq, parent_id, description)
VALUES( (select id from bss_authority ba where ba.code = 'sys_version_list'), '版本信息维护', 'to_sys_version_list.shtml', 4, 8, '版本信息维护');

INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'sys_version_list'));

alter table bss_sync_data_log add column cause  varchar(1024)   DEFAULT NULL  AFTER message;