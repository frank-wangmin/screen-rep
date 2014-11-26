INSERT INTO `bss_device_vendor` VALUES (55, 'USABLE', 'TCL', 'TCL', 'TCL 王牌');
INSERT INTO `bss_device_vendor` VALUES (56, 'USABLE', 'Toshiba', '东芝', '东芝');
INSERT INTO `bss_device_vendor` VALUES (57, 'USABLE', 'Changhong', '长虹', '长虹');
INSERT INTO `bss_device_vendor` VALUES (58, 'USABLE', 'Lenovo', '联想', '联想');
INSERT INTO `bss_device_vendor` VALUES (59, 'USABLE', 'Hisense', '海信', '海信');
INSERT INTO `bss_device_vendor` VALUES (60, 'USABLE', 'Haier', '海尔', '海尔');
INSERT INTO `bss_device_vendor` VALUES (61, 'USABLE', '7', '九州', '九州');


INSERT INTO `bss_device_upgrade` VALUES (659, 417, 1, 'V1.2', 'INCREMENT', 'http://192.168.5.111/upgrade/mtk_v1.2.zip', 'TEST', 'MANDATORY', 'sssssssssssssssssssss');
INSERT INTO `bss_device_upgrade` VALUES (660, 417, 1, 'V1.2', 'FULL', 'http://192.168.5.111/upgrade/mtk_full_v1.2.zip', 'RELEASE', 'MANDATORY', 'sssssssssssssssssssss');




INSERT INTO `bss_platform_version` VALUES (404, 'USABLE', 'V1.0', 'V1.0', 'V1.0');
INSERT INTO `bss_platform_version` VALUES (405, 'USABLE', 'V1.1.2', 'V1.1.2', 'V1.1.2');


INSERT INTO `bss_device_rule` VALUES (416, 2, NULL, 203, 417, '海尔D100', 's', 5, 'k', 1, 8, '1', 'USABLE', '2011-6-15 14:59:45', NULL,NULL,NULL,NULL);


INSERT INTO `bss_platform` VALUES (417, 60, 85, 58, 405, 'USABLE', '000Haier00DEV10000000MTKV1.1.2', 'MTK2.3', '');


INSERT INTO `bss_device_type` VALUES (85, 60, 'USABLE', 'DEV100', 'DEV100', 'DEV100');


INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1489, 60, 85, 416, NULL, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00001k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1490, 60, 85, 416, NULL, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00002k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1491, 60, 85, 416, NULL, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00003k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1492, 60, 85, 416, NULL, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00004k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1493, 60, 85, 416, NULL, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00005k', '2011-6-15 15:02:03', NULL, NULL, 'NONACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1494, 60, 85, 416, 129, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00006k', '2011-6-15 15:02:03', NULL, NULL, 'NOTUSE', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1495, 60, 85, 416, 128, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00007k', '2011-6-15 15:02:03', '2011-6-21 11:33:24', '2011-6-21 11:33:24', 'ACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.1');
INSERT INTO `bss_device` (`ID`, `VENDOR_ID`, `DEVICE_TYPE_ID`, `RULE_ID`, `DEFAULT_CUSTOMER_ID`, `AREA_ID`, `platform_id`, `platform_code`, `CODE`, `CREATE_DATE`, `ACTIVATE_DATE`, `STATE_CHANGE_DATE`, `STATE`, `BIND_TYPE`, `DESCRIPTION`, `VENDOR_NAME`, `TYPE_NAME`, `version_seq`, `version_name`) VALUES (1496, 60, 85, 416, 127, 203, 417, '000Haier00DEV10000000MTKV1.1.2', 's00008k', '2011-6-15 15:02:03', '2011-6-16 19:56:52', '2011-6-16 19:56:52', 'ACTIVATED', 'BIND', '', '海尔', 'DEV100 ', 1, 'V1.2');

INSERT INTO `bss_customer`  (`ID`, `DEFAULT_ACCOUNT_ID`, `AREA_ID`, `CODE`, `LOGIN_NAME`, `REAL_NAME`, `NICK_NAME`, `PASSWORD`, `CUSTOMER_TYPE`, `STATE`, `SEX`, `PHONE`, `PROFESSION`, `HOBBY`, `CREATE_DATE`, `IDENTITY_TYPE`, `IDENTITY_CODE`, `STATE_CHANGE_DATE`, `ZIP_CODE`, `ADRESS`) VALUES (127, 449, 203, '20110615000001', '20110615000001', NULL, NULL, 'f379eaf3c831b04de153469d1bec345e', 'PERSONAL', 'NORMAL', 'MAN', '', '', '', '2011-6-16 19:56:52', 'IDCARD', '', '2011-6-21 11:30:15', '100101', '北京市朝阳区北辰东路8号院北辰时代大厦');
INSERT INTO `bss_customer`  (`ID`, `DEFAULT_ACCOUNT_ID`, `AREA_ID`, `CODE`, `LOGIN_NAME`, `REAL_NAME`, `NICK_NAME`, `PASSWORD`, `CUSTOMER_TYPE`, `STATE`, `SEX`, `PHONE`, `PROFESSION`, `HOBBY`, `CREATE_DATE`, `IDENTITY_TYPE`, `IDENTITY_CODE`, `STATE_CHANGE_DATE`, `ZIP_CODE`, `ADRESS`) VALUES (128, 450, 203, '20110621000002', '20110621000002', NULL, NULL, 'f379eaf3c831b04de153469d1bec345e', 'PERSONAL', 'NORMAL', 'MAN', NULL, NULL, NULL, '2011-6-21 11:33:24', 'IDCARD', NULL, '2011-6-24 11:26:58', NULL, NULL);
INSERT INTO `bss_customer`  (`ID`, `DEFAULT_ACCOUNT_ID`, `AREA_ID`, `CODE`, `LOGIN_NAME`, `REAL_NAME`, `NICK_NAME`, `PASSWORD`, `CUSTOMER_TYPE`, `STATE`, `SEX`, `PHONE`, `PROFESSION`, `HOBBY`, `CREATE_DATE`, `IDENTITY_TYPE`, `IDENTITY_CODE`, `STATE_CHANGE_DATE`, `ZIP_CODE`, `ADRESS`) VALUES (129, 451, 203, '20110627000003', '20110627000003', NULL, NULL, 'f379eaf3c831b04de153469d1bec345e', 'PERSONAL', 'NORMAL', 'MAN', NULL, NULL, NULL, NULL, 'IDCARD', NULL, NULL, NULL, NULL);

INSERT INTO `bss_customer`  (`ID`, `DEFAULT_ACCOUNT_ID`, `AREA_ID`, `CODE`, `LOGIN_NAME`, `REAL_NAME`, `NICK_NAME`, `PASSWORD`, `CUSTOMER_TYPE`, `STATE`, `SEX`, `PHONE`, `PROFESSION`, `HOBBY`, `CREATE_DATE`, `IDENTITY_TYPE`, `IDENTITY_CODE`, `STATE_CHANGE_DATE`, `ZIP_CODE`, `ADRESS`) VALUES (130, 452, 203, '20110627000004', '20110627000004', NULL, NULL, 'f379eaf3c831b04de153469d1bec345e', 'PERSONAL', 'UNUSABLE', 'MAN', NULL, NULL, NULL, NULL, 'IDCARD', NULL, NULL, NULL, NULL);
INSERT INTO `bss_customer`  (`ID`, `DEFAULT_ACCOUNT_ID`, `AREA_ID`, `CODE`, `LOGIN_NAME`, `REAL_NAME`, `NICK_NAME`, `PASSWORD`, `CUSTOMER_TYPE`, `STATE`, `SEX`, `PHONE`, `PROFESSION`, `HOBBY`, `CREATE_DATE`, `IDENTITY_TYPE`, `IDENTITY_CODE`, `STATE_CHANGE_DATE`, `ZIP_CODE`, `ADRESS`) VALUES (131, 453, 203, '20110627000005', '20110627000005', NULL, NULL, 'f379eaf3c831b04de153469d1bec345e', 'PERSONAL', 'CANCEL', 'MAN', NULL, NULL, NULL, NULL, 'IDCARD', NULL, NULL, NULL, NULL);



INSERT INTO `bss_account` VALUES (449, '20110615000001', '20110615000001', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'NORMAL', '2011-6-20 19:13:46', 8300, '2011-6-16 19:56:52', '4900-1-31 00:00:00', '3899-2-1 00:00:00');
INSERT INTO `bss_account` VALUES (450, '20110621000002', '20110621000002', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'NORMAL', '2011-6-21 11:33:24', 8000, '2011-6-21 11:33:24', '4900-1-31 00:00:00', '3899-2-1 00:00:00');
INSERT INTO `bss_account` VALUES (451, '20110627000003', '20110627000003', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'NORMAL', NULL, 0, NULL, '4900-1-31 00:00:00', '3899-2-1 00:00:00');

INSERT INTO `bss_account` VALUES (452, '20110627000004', '20110627000004', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'UNUSABLE', NULL, 0, NULL, '4900-1-31 00:00:00', '3899-2-1 00:00:00');
INSERT INTO `bss_account` VALUES (453, '20110627000005', '20110627000005', 'f379eaf3c831b04de153469d1bec345e', 'PERSION', 'NOMAL', 'CANCEL', NULL, 0, NULL, '4900-1-31 00:00:00', '3899-2-1 00:00:00');

