ALTER TABLE `bss_panel_item`
ADD COLUMN `install_url`  varchar(255) NULL AFTER `auto_play`;

--- 加入bss_ppv_pp_relation历史表，记录关系的修改、删除操作
CREATE TABLE `bss_ppv_pp_relation_history` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `action` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `old_id` bigint(19) NOT NULL,
  `old_ppv_id` bigint(19) DEFAULT NULL,
  `old_product_id` varchar(32) DEFAULT NULL,
  `old_push_date` datetime DEFAULT NULL,
  `old_push_state` varchar(32) DEFAULT NULL,
  `old_call_back_date` datetime DEFAULT NULL,
  `old_pp_pk_id` bigint(19) DEFAULT NULL,
  `old_ppv_pk_id` bigint(19) DEFAULT NULL,
  `old_create_date` datetime DEFAULT NULL,
  `new_id` bigint(19) DEFAULT NULL,
  `new_ppv_id` bigint(19) DEFAULT NULL,
  `new_product_id` varchar(32) DEFAULT NULL,
  `new_push_date` datetime DEFAULT NULL,
  `new_push_state` varchar(32) DEFAULT NULL,
  `new_call_back_date` datetime DEFAULT NULL,
  `new_pp_pk_id` bigint(19) DEFAULT NULL,
  `new_ppv_pk_id` bigint(19) DEFAULT NULL,
  `new_create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8