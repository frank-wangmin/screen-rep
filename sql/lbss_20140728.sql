-- 产品内容历史表
CREATE TABLE `bss_product_content_history` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `action` varchar(50) DEFAULT NULL,
  `pc_id` bigint(19) DEFAULT NULL,
  `pc_name` varchar(64) DEFAULT NULL,
  `pc_outter_code` bigint(19) DEFAULT NULL,
  `pc_ott_outter_code` varchar(32) DEFAULT NULL,
  `pc_outter_type` varchar(30) DEFAULT NULL,
  `pc_cp_code` varchar(32) DEFAULT NULL,
  `pc_state` varchar(32) DEFAULT NULL,
  `pc_create_date` datetime DEFAULT NULL,
  `pc_publish_date` datetime DEFAULT NULL,
  `pc_create_operator_name` varchar(32) DEFAULT NULL,
  `pc_ppv_id` bigint(19) DEFAULT NULL,
  `pc_create_operator_id` int(10) DEFAULT NULL,
  `pc_description` varchar(5000) DEFAULT NULL,
  `pc_score` float(4,1) DEFAULT NULL,
  `pc_small_poster_addr` varchar(1024) DEFAULT NULL,
  `pc_poster` varchar(1024) DEFAULT NULL,
  `pc_big_poster_addr` varchar(1024) DEFAULT NULL,
  `pc_push_date` datetime DEFAULT NULL,
  `pc_push_state` varchar(32) DEFAULT NULL,
  `pc_call_back_date` datetime DEFAULT NULL,
  `pc_relation_push_date` datetime DEFAULT NULL,
  `pc_relation_push_state` varchar(32) DEFAULT NULL,  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8


/*修改表--产品包与用户组的关系*/

alter table bss_user_group_pp_info_map change  product_type  product_id varchar(32);

-- 产品内容加入关系推送时间和推送状态
alter table bss_product_content add column relation_push_date datetime DEFAULT NULL;
alter table bss_product_content add column relation_push_state varchar(32) DEFAULT NULL;
