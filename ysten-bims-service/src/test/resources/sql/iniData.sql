INSERT INTO `bss_device_vendor` VALUES (55, 'USABLE', 'TCL', 'TCL', 'TCL 王牌', '2012-08-10 13:24:25');
INSERT INTO `bss_device_vendor` VALUES (56, 'USABLE', 'Toshiba', '东芝', '东芝', '2012-08-10 13:24:25');
INSERT INTO `bss_device_vendor` VALUES (57, 'USABLE', 'Changhong', '长虹', '长虹', '2012-08-10 13:24:25');
INSERT INTO `bss_device_vendor` VALUES (58, 'USABLE', 'Lenovo', '联想', '联想', '2012-08-10 13:24:25');
INSERT INTO `bss_device_vendor` VALUES (59, 'USABLE', 'Hisense', '海信', '海信', '2012-08-10 13:24:25');
INSERT INTO `bss_device_vendor` VALUES (60, 'USABLE', 'Haier', '海尔', '海尔', '2012-08-10 13:24:25');
INSERT INTO `bss_device_vendor` VALUES (61, 'USABLE', '7', '九州', '九州', '2012-08-10 13:24:25');


INSERT INTO `bss_device_upgrade` VALUES ('685', '431', '1', '4420', 'FULL', 'http://192.168.4.215:8080/update/A10/update-4420.zip', 'RELEASE', 'MANDATORY', '1bc378d5c362cc50433e8ed522508ed3', '2012-08-11 12:44:19', '1', '431');
INSERT INTO `bss_device_upgrade` VALUES ('686', '431', '2', '4424', 'FULL', 'http://192.168.4.215:8080/update/A10/update-4424.zip', 'RELEASE', 'MANDATORY', 'c38dabbd1d2583fde524c6b555c156d7', '2012-08-11 12:44:19', '1', '431');
INSERT INTO `bss_device_upgrade` VALUES ('687', '431', '3', '4428', 'FULL', 'http://192.168.4.215:8080/update/A10/update-4428.zip', 'RELEASE', 'MANDATORY', '3bbc40c3dfb5dafebc83e772d200c0df', '2012-08-11 12:44:19', '1', '431');


INSERT INTO `bss_platform_version` VALUES ('404', 'USABLE', '2.0', '2.0', '', '2012-08-10 13:25:48');
INSERT INTO `bss_platform_version` VALUES ('405', 'USABLE', 'HE-MTK', '2.0.1', '', '2012-08-10 13:25:48');
INSERT INTO `bss_platform_version` VALUES ('408', 'USABLE', 'HL-CNTV2.0', 'HL-CNTV2.0', '', '2012-08-10 13:25:48');
INSERT INTO `bss_platform_version` VALUES ('409', 'USABLE', 'hlcntv2.0', '华录mtk', '', '2012-08-10 13:25:48');
INSERT INTO `bss_platform_version` VALUES ('410', 'USABLE', 'TFMTKcntv2.0', '同方MTKcntv2.0', '', '2012-08-10 13:25:48');

INSERT INTO `bss_device_rule` VALUES ('40', '1', null, '2', '417', '11', null, null, null, '1', '44', '1', 'USABLE', '2012-08-13 22:33:23', null, '01', '1', null, null, null, '417');

INSERT INTO `bss_platform` VALUES ('417', '64', '85', '58', '405', 'USABLE', '00HE-MTK000000HEMTK-6i900HE-MTK', '海尔一体机', '', '2012-08-10 13:25:29', null);
INSERT INTO `bss_platform` VALUES ('418', '63', '86', '59', '404', 'USABLE', '000YSTen00C2beta000000C2000002.0', 'YSTen-C2-beta', '', '2012-08-10 13:25:29', null);

INSERT INTO `bss_device_type` VALUES ('85', '64', 'USABLE', 'HE', '海尔一体机', '', '2012-08-10 13:24:46', 'TV');
INSERT INTO `bss_device_type` VALUES ('86', '63', 'USABLE', 'C2beta', 'C2beta', '', '2012-08-10 13:24:46', 'TV');

INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1489, 60, 85, 416, NULL, 203, 417, '01102002000003', 's00001k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1490, 60, 85, 416, NULL, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00002k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1491, 60, 85, 416, NULL, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00003k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1492, 60, 85, 416, NULL, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00004k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1493, 60, 85, 416, NULL, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00005k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1494, 60, 85, 416, 129, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00006k', '2011-6-15 15:02:03', NULL, NULL, 'NOTUSE', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1495, 60, 85, 416, 128, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00007k', '2011-6-15 15:02:03', '2011-6-21 11:33:24', '2011-6-21 11:33:24', 'ACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1496, 60, 85, 416, 127, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00008k', '2011-6-15 15:02:03', '2011-6-16 19:56:52', '2011-6-16 19:56:52', 'ACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.2');

INSERT INTO `bss_customer` VALUES ('128', '11', '4', '20110619000001', '111', '', '', 'c9404018816353f30a3961b22ea5b140', 'PERSONAL', 'MEMBER', 'MAN', '', '', '', null, 'IDCARD', '', '2012-12-19 14:25:33', '', '', '', '', '0', null, '2012-12-19 14:15:31', null);
INSERT INTO `bss_customer` VALUES ('129', '451', '4', '20110619000002', '', '', '', '', 'PERSONAL', 'MEMBER', 'MAN', '', '', '', '2012-12-19 14:25:33', 'IDCARD', '', '2012-12-19 14:25:33', '', '', '', '', null, null, '2012-12-19 14:25:33', null);
INSERT INTO `bss_customer` VALUES ('130', '452', '4', '20110619000003', '', '', '', '', 'PERSONAL', 'MEMBER', 'MAN', '', '', '', '2012-12-19 14:25:33', 'IDCARD', '', '2012-12-19 14:25:33', '', '', '', '', null, null, '2012-12-19 14:25:33', null);
INSERT INTO `bss_customer` VALUES ('131', '453', '4', '20110619000004', '', '', '', '', 'PERSONAL', 'MEMBER', 'MAN', '', '', '', '2012-12-19 14:25:33', 'IDCARD', '', '2012-12-19 14:25:33', '', '', '', '', null, null, '2012-12-19 14:25:33', null);
INSERT INTO `bss_customer` VALUES ('132', '454', '4', '20110619000005', '', '', '', '', 'PERSONAL', 'MEMBER', 'MAN', '', '', '', '2012-12-19 14:25:33', 'IDCARD', '', '2012-12-19 14:25:33', '', '', '', '', null, null, '2012-12-19 14:25:33', null);


INSERT INTO `bss_account` VALUES (449, '20110615000001', '20110615000001', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'NORMAL', '2011-6-20 19:13:46', 8300, '2011-6-16 19:56:52', '4900-1-31 00:00:00', '3899-2-1 00:00:00');
INSERT INTO `bss_account` VALUES (450, '20110621000002', '20110621000002', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'NORMAL', '2011-6-21 11:33:24', 8000, '2011-6-21 11:33:24', '4900-1-31 00:00:00', '3899-2-1 00:00:00');
INSERT INTO `bss_account` VALUES (451, '20110627000003', '20110627000003', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'NORMAL', NULL, 0, NULL, '4900-1-31 00:00:00', '3899-2-1 00:00:00');

INSERT INTO `bss_account` VALUES (452, '20110627000004', '20110627000004', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'UNUSABLE', NULL, 0, NULL, '4900-1-31 00:00:00', '3899-2-1 00:00:00');
INSERT INTO `bss_account` VALUES (453, '20110627000005', '20110627000005', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'CANCEL', NULL, 0, NULL, '4900-1-31 00:00:00', '3899-2-1 00:00:00');

INSERT INTO `bss_product` VALUES (24, 4, 'OPERATOR', '2', '卡拉ok(包月)', 'BASIC', 4, 'NATURAL_MONTH', 'ONLINE', '2011-11-29 00:00:00', '2015-11-29 23:59:59', '卡拉ok包月产品','2011-11-29 20:09:17',25,1,'MONTH','images/innerIcon.jpg','images/outerIcon.jpg');
INSERT INTO `bss_product` VALUES (22, 4, 'OPERATOR', '1', '免费影片', 'SINGLE', 3, 'NATURAL_MONTH', 'ONLINE', '2011-11-29 00:00:00', '2015-11-29 23:59:59', '免费影片','2011-11-29 20:09:17',24,1,'MONTH','images/innerIcon.jpg','images/outerIcon.jpg');

INSERT INTO `bss_price_atom` VALUES ('25', '卡拉ok(包月)', '15', '0', null, null, null);
INSERT INTO `bss_price_atom` VALUES ('24', '卡拉ok(包月)', '15', '1', null, null, null);

INSERT INTO `bss_product_content` VALUES ('75571', '完美嫁衣', 'MOVIE', 'SINGLE', '216621', null, null, null, null, '2011-06-19 13:59:33', null, null);

INSERT INTO `bss_product_order` VALUES ('302', '20130117170039112002', 'PEND', '1196', '20130104120406001000', null, '28', 'Test 0.1RMB', 'SINGLE', '527064', '晓光', null, 'ICNTV', null, '1', '1', '1', '2013-01-17 17:00:38', '2013-01-17 17:00:38', null, null, '760632', '9365');
INSERT INTO `bss_product_order` VALUES ('303', '20130117174936222000', 'PEND', '1196', '20130104120406001000', null, '22', '免费影片', 'BASIC', null, null, 'FREE', 'ICNTV', null, '5', '0', '1', '2013-01-17 17:49:36', '2013-01-17 17:49:36', '2013-01-17 17:49:36', null, null, '9365');
