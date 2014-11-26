INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1, 1, 1, 1, null, 1, 1, '00000CTL000000C200000XLT00000002', 'a00000001b', '2011-5-12 14:42:03', '2011-6-3 13:38:52', NULL, 'NONACTIVATED', 'BIND', '规则描述', 'CTL', 'XLT', 1, 'V1.0');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (2, 1, 1, 1, null, 1, 1, '00000CTL000000C200000XLT00000002', 'a00000002b', '2011-5-12 14:42:03', '2011-6-3 13:38:52', NULL, 'NONACTIVATED', 'BIND', '规则描述', 'CTL', 'XLT', 1, 'V1.0');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (3, 1, 1, 1, 1, 1, 1, '00000CTL000000C200000XLT00000002', 'a00000003b', '2011-5-12 14:42:03', '2011-6-3 13:38:52', NULL, 'ACTIVATED', 'BIND', '规则描述', 'CTL', 'XLT', 1, 'V1.0');

INSERT INTO `bss_customer` (`ID`, `DEFAULT_ACCOUNT_ID`, `AREA_ID`, `CODE`, `LOGIN_NAME`, `REAL_NAME`, `NICK_NAME`, `PASSWORD`, `CUSTOMER_TYPE`, `STATE`, `SEX`, `PHONE`, `PROFESSION`, `HOBBY`, `CREATE_DATE`, `IDENTITY_TYPE`, `IDENTITY_CODE`, `STATE_CHANGE_DATE`, `ZIP_CODE`, `ADRESS`) VALUES (1, 1, 1, '20110530000002', '20110530000002', NULL, NULL, 'f379eaf3c831b04de153469d1bec345e', 'ORGANIZATION', 'NORMAL', 'MAN', '010-88888888', '888', '999', '2011-6-1 10:54:44', 'IDCARD', '123456', '2011-6-2 16:47:18', '100101', '北京市朝阳区北辰东路8号院北辰时代大厦1710');

INSERT INTO `bss_account` (`ID`, `CODE`, `LOGIN_NAME`, `PASSWORD`, `ACCOUNT_TYPE`, `CREDIT_RATE`, `STATE`, `STATE_CHANGE_DATE`, `BALANCE`, `CREATE_DATE`, `eff_date`, `exp_date`) VALUES (1, '20110530000002', '20110530000002', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'NORMAL', '2011-6-2 16:44:36', 19290, '2011-6-2 16:44:52', '4900-1-31 00:00:00', '3899-2-1 00:00:00');


INSERT INTO `bss_area` VALUES ('1', 'beijing', '北京市', 'USABLE', '0', '北京市');

INSERT INTO `bss_price_atom` VALUES ('1', '1元包', '100', '1', null, null, null);

INSERT INTO `bss_price_group` VALUES ('1', '1', null, null, null);
INSERT INTO `bss_price_group` VALUES ('2', '1', 'b409e5731d7541b2a947db303e046088', null, null);

INSERT INTO `bss_event` VALUES ('1', 'b409e5731d7541b2a947db303e046088', 'DATE', '2011-07-02', '2011-05-29');

INSERT INTO `bss_price_plan` VALUES ('1', '1元计划', 'USABLE', '1元计划，全场8折', '2011-06-20 19:04:21', '2');

INSERT INTO `bss_plan_group_map` VALUES ('1', '1', '1');
INSERT INTO `bss_plan_group_map` VALUES ('2', '1', '2');

INSERT INTO `bss_product` VALUES ('1', '1', '2', '最新大片先睹为快', 'SINGLE', 'VENDIBLE', '2011-06-15 00:00:00', '2011-12-31 23:59:59', '最新大片先睹为快', '2011-06-15 15:17:45');

INSERT INTO `bss_price_index` VALUES ('1', '1', 'PRODUCT', '1');

INSERT INTO `bss_product_order` VALUES ('1', '1', '20110530000002', 'PAID', '2011-06-08 10:22:09', '2011-06-09 10:22:13', '2211-06-10 10:22:18');

INSERT INTO `bss_order_item` VALUES ('1', '1', 'PRODUCT', '1', '神州行充值卡', '10000', '1', '1');

INSERT INTO `bss_product_content` VALUES ('1', '单元测试', 'MOVIE', 'CATEGORY', '1', '1', null, 'ENABLE', '', '2011-06-15 15:40:00', null);

INSERT INTO `bss_product_item` VALUES ('1', '成龙系列', '2011-06-15 15:03:55', null, '成龙系列');

INSERT INTO `bss_item_content_map` VALUES ('1', '1', '1');

INSERT INTO `bss_product_item_map` VALUES ('1', '1', '1');

INSERT INTO `program_serial` VALUES ('1', '风华国乐', '', '微视频', '《风华国乐》是一个以民族器乐欣赏为主要内容，介绍中华民族传统音乐文化，展示民族音乐艺术家魅力的舞台。', '', null, 'BG201105270930120146', '', '', null, null, '', null, null, null, null, null, null, null, '1', '爱布谷', '1', '0', null, '2011-06-15 16:04:04', '50', null, '21', '电影音乐', '音乐台', null, null);

INSERT INTO `catg_item` VALUES ('1', '-1', null, '1', 'OMS运营管理系统', null, null, null, null, null, null, '0', null, null);

INSERT INTO `catg_program` VALUES ('1', '1', '17249', '1', '2011-05-18 19:00:03', '10');