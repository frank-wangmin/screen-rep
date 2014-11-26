/*新增表--产品包与用户组的关系*/
CREATE TABLE `bss_user_group_pp_info_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `user_group_id` bigint(19) DEFAULT NULL,
  `product_type` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8