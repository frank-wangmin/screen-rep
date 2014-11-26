/*
Navicat MySQL Data Transfer

Source Server         : 58.214.17.75
Source Server Version : 50604
Source Host           : 58.214.17.75:3306
Source Database       : bims_panel_test

Target Server Type    : MYSQL
Target Server Version : 50604
File Encoding         : 65001

Date: 2014-09-25 13:33:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bss_authority`
-- ----------------------------
DROP TABLE IF EXISTS `bss_authority`;
CREATE TABLE `bss_authority` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(5000) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '权限显示的名称',
  `description` varchar(5000) DEFAULT NULL,
  `parent_id` bigint(19) DEFAULT NULL,
  `sort_order` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=351 DEFAULT CHARSET=utf8 COMMENT='特权，记录操作信息';

-- ----------------------------
-- Records of bss_authority
-- ----------------------------
INSERT INTO `bss_authority` VALUES ('8', 'system_manager', '系统管理', '系统管理', '1', '1');
INSERT INTO `bss_authority` VALUES ('9', 'logger_manager', '日志管理', '日志管理', '1', '4');
INSERT INTO `bss_authority` VALUES ('23', 'operator_manager', '操作员管理', '操作员管理', '8', '0');
INSERT INTO `bss_authority` VALUES ('24', 'role_manager', '角色管理', '角色管理', '8', '0');
INSERT INTO `bss_authority` VALUES ('25', 'authority_manager', '权限信息', '权限信息', '8', '0');
INSERT INTO `bss_authority` VALUES ('28', 'interface_log', '接口日志查询', '接口日志查询', '9', '0');
INSERT INTO `bss_authority` VALUES ('44', 'system_conifg', '系统参数配置', '系统参数配置', '187', '0');
INSERT INTO `bss_authority` VALUES ('61', 'operate_manager', '操作日志查询', '操作日志查询', '9', '0');
INSERT INTO `bss_authority` VALUES ('141', 'add_operator', '新增操作员', '新增操作员', '23', '0');
INSERT INTO `bss_authority` VALUES ('142', 'update_operator', '修改操作员', '修改操作员', '23', '0');
INSERT INTO `bss_authority` VALUES ('187', 'config_info', '配置管理', '配置管理', '1', '2');
INSERT INTO `bss_authority` VALUES ('203', 'panel_manager', '面板管理', '面板管理', '1', '10');
INSERT INTO `bss_authority` VALUES ('204', 'panel_info', '面板信息维护', '面板信息维护', '203', '0');
INSERT INTO `bss_authority` VALUES ('213', 'preview_template_info', '预览面板信息维护', '预览面板信息维护', '203', '4');
INSERT INTO `bss_authority` VALUES ('231', 'panel_item_info', '面板项信息维护', '面板项信息维护', '203', '3');
INSERT INTO `bss_authority` VALUES ('337', 'assgin_role_authority', '角色权限分配', '角色权限分配', '24', '0');
INSERT INTO `bss_authority` VALUES ('338', 'add_role', '新增', '新增角色信息', '24', '0');
INSERT INTO `bss_authority` VALUES ('339', 'update_role', '修改', '修改角色信息', '24', '0');
INSERT INTO `bss_authority` VALUES ('340', 'update_sysconfig', '修改', '修改系统配置', '44', '0');
INSERT INTO `bss_authority` VALUES ('341', 'add_panel_item', '新增', '新增面板项', '231', '1');
INSERT INTO `bss_authority` VALUES ('342', 'update_panel_item', '修改', '修改面板项', '231', '2');
INSERT INTO `bss_authority` VALUES ('343', 'delete_panel_item', '删除', '删除面板项', '231', '3');
INSERT INTO `bss_authority` VALUES ('344', 'add_preview_template', '新增', '新增预览面板信息', '213', '0');
INSERT INTO `bss_authority` VALUES ('345', 'update_preview_template', '修改', '修改预览面板信息', '213', '0');
INSERT INTO `bss_authority` VALUES ('346', 'delete_preview_template', '删除', '删除预览面板信息', '213', '0');
INSERT INTO `bss_authority` VALUES ('347', 'panel_template_config', '面板模板配置', '面板模板配置', '213', '0');
INSERT INTO `bss_authority` VALUES ('348', 'add_panel', '新增', '新增', '204', '0');
INSERT INTO `bss_authority` VALUES ('349', 'update_panel', '修改', '修改', '204', '0');
INSERT INTO `bss_authority` VALUES ('350', 'panel_config', '面板配置', '面板配置', '204', '0');

-- ----------------------------
-- Table structure for `bss_interface_log`
-- ----------------------------
DROP TABLE IF EXISTS `bss_interface_log`;
CREATE TABLE `bss_interface_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `interface_name` varchar(32) DEFAULT NULL,
  `caller` varchar(100) DEFAULT NULL,
  `call_time` datetime DEFAULT NULL,
  `input` text,
  `output` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bss_interface_log
-- ----------------------------

-- ----------------------------
-- Table structure for `bss_interface_url`
-- ----------------------------
DROP TABLE IF EXISTS `bss_interface_url`;
CREATE TABLE `bss_interface_url` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `interface_name` varchar(32) DEFAULT NULL,
  `area_id` bigint(16) DEFAULT NULL,
  `interface_url` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省接口Url表';

-- ----------------------------
-- Records of bss_interface_url
-- ----------------------------

-- ----------------------------
-- Table structure for `bss_operate_log`
-- ----------------------------
DROP TABLE IF EXISTS `bss_operate_log`;
CREATE TABLE `bss_operate_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(30) DEFAULT NULL,
  `operation_type` varchar(32) DEFAULT NULL,
  `primary_key_value` text,
  `description` text,
  `operator` varchar(50) DEFAULT NULL,
  `operation_ip` varchar(32) DEFAULT NULL,
  `operation_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bss_operate_log
-- ----------------------------
INSERT INTO `bss_operate_log` VALUES ('1', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-24 14:51:25登陆系统成功', 'admin', '192.168.2.74', '2014-09-24 14:51:25');
INSERT INTO `bss_operate_log` VALUES ('2', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-24 15:10:24登陆系统成功', 'admin', '192.168.2.74', '2014-09-24 15:10:24');
INSERT INTO `bss_operate_log` VALUES ('3', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-24 15:16:17登陆系统成功', 'admin', '192.168.2.74', '2014-09-24 15:16:17');
INSERT INTO `bss_operate_log` VALUES ('4', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-24 15:23:27登陆系统成功', 'admin', '192.168.2.74', '2014-09-24 15:23:27');
INSERT INTO `bss_operate_log` VALUES ('5', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 08:59:43登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 08:59:43');
INSERT INTO `bss_operate_log` VALUES ('6', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 09:04:50登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 09:04:50');
INSERT INTO `bss_operate_log` VALUES ('7', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 09:59:49登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 09:59:49');
INSERT INTO `bss_operate_log` VALUES ('8', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 10:35:40登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 10:35:40');
INSERT INTO `bss_operate_log` VALUES ('9', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 10:41:18登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 10:41:18');
INSERT INTO `bss_operate_log` VALUES ('10', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 10:56:06登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 10:56:06');
INSERT INTO `bss_operate_log` VALUES ('11', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 11:03:22登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 11:03:22');
INSERT INTO `bss_operate_log` VALUES ('12', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 11:11:38登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 11:11:38');
INSERT INTO `bss_operate_log` VALUES ('13', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 11:12:44登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 11:12:44');
INSERT INTO `bss_operate_log` VALUES ('14', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 11:18:28登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 11:18:28');
INSERT INTO `bss_operate_log` VALUES ('15', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 11:21:23登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 11:21:23');
INSERT INTO `bss_operate_log` VALUES ('16', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 11:40:31登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 11:40:31');
INSERT INTO `bss_operate_log` VALUES ('17', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 11:46:53登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 11:46:54');
INSERT INTO `bss_operate_log` VALUES ('18', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 11:50:40登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 11:50:40');
INSERT INTO `bss_operate_log` VALUES ('19', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 11:53:03登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 11:53:03');
INSERT INTO `bss_operate_log` VALUES ('20', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 12:40:54登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 12:40:54');
INSERT INTO `bss_operate_log` VALUES ('21', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 12:48:06登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 12:48:06');
INSERT INTO `bss_operate_log` VALUES ('22', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 13:14:50登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 13:14:50');
INSERT INTO `bss_operate_log` VALUES ('23', '预览模板信息维护', '新增', '1', '新增预览模板成功!', 'admin', '192.168.2.74', '2014-09-25 13:18:23');
INSERT INTO `bss_operate_log` VALUES ('24', '预览模板信息维护', '删除', '', '新增模板成功', 'admin', '192.168.2.74', '2014-09-25 13:18:55');
INSERT INTO `bss_operate_log` VALUES ('25', '预览模板信息维护', '删除', '', '新增模板成功', 'admin', '192.168.2.74', '2014-09-25 13:19:58');
INSERT INTO `bss_operate_log` VALUES ('26', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 13:23:01登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 13:23:01');
INSERT INTO `bss_operate_log` VALUES ('27', '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-09-25 13:25:24登陆系统成功', 'admin', '192.168.2.74', '2014-09-25 13:25:24');
INSERT INTO `bss_operate_log` VALUES ('28', '面板信息维护', '新增', '1', '新增面板成功!', 'admin', '192.168.2.74', '2014-09-25 13:26:22');
INSERT INTO `bss_operate_log` VALUES ('29', '模板项信息维护', '新增', '1', '绑定面板项成功!', 'admin', '192.168.2.74', '2014-09-25 13:26:50');

-- ----------------------------
-- Table structure for `bss_operator`
-- ----------------------------
DROP TABLE IF EXISTS `bss_operator`;
CREATE TABLE `bss_operator` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `email` varchar(32) DEFAULT NULL COMMENT '用户邮箱地址',
  `login_name` varchar(32) DEFAULT NULL COMMENT '登陆的用户名',
  `display_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `state` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户表，记录用户的相关信息';

-- ----------------------------
-- Records of bss_operator
-- ----------------------------
INSERT INTO `bss_operator` VALUES ('1', 'admin@ysten.com', 'admin', '系统管理员', 'NORMAL', '21232f297a57a5a743894a0e4a801fc3');

-- ----------------------------
-- Table structure for `bss_operator_role_map`
-- ----------------------------
DROP TABLE IF EXISTS `bss_operator_role_map`;
CREATE TABLE `bss_operator_role_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `operator_id` bigint(19) DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(19) DEFAULT NULL COMMENT '角色d',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户角色表，定义用户所拥有的角色';

-- ----------------------------
-- Records of bss_operator_role_map
-- ----------------------------
INSERT INTO `bss_operator_role_map` VALUES ('3', '2', '1');
INSERT INTO `bss_operator_role_map` VALUES ('4', '1', '1');

-- ----------------------------
-- Table structure for `bss_panel`
-- ----------------------------
DROP TABLE IF EXISTS `bss_panel`;
CREATE TABLE `bss_panel` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `panel_id` bigint(19) DEFAULT NULL,
  `panel_mark` varchar(32) DEFAULT NULL,
  `template_id` bigint(19) DEFAULT NULL,
  `panel_name` varchar(60) DEFAULT NULL,
  `panel_title` varchar(60) DEFAULT NULL,
  `panel_style` varchar(60) DEFAULT NULL,
  `panel_icon` varchar(128) DEFAULT NULL,
  `link_url` varchar(128) DEFAULT NULL,
  `img_url` varchar(128) DEFAULT NULL,
  `online_status` int(2) DEFAULT NULL,
  `online_status_time` datetime DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `opr_userid` bigint(19) DEFAULT NULL,
  `epg_1_data` text,
  `epg_1_style` text,
  `epg_2_data` text,
  `epg_2_style` text,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `epg_panel_id` bigint(19) DEFAULT NULL,
  `epg_template_id` bigint(19) DEFAULT NULL,
  `big_img` varchar(256) DEFAULT NULL,
  `small_img` varchar(256) DEFAULT NULL,
  `is_custom` int(2) DEFAULT NULL,
  `ref_panel_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bss_panel
-- ----------------------------
INSERT INTO `bss_panel` VALUES ('1', null, null, '1', 'aa', 'aa', 'aa', '', '', '', '0', null, null, '1', null, null, null, null, '2014-09-25 13:26:21', '2014-09-25 13:26:21', null, null, 'a', 'a', '1', null);

-- ----------------------------
-- Table structure for `bss_panel_item`
-- ----------------------------
DROP TABLE IF EXISTS `bss_panel_item`;
CREATE TABLE `bss_panel_item` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `panel_item_id` bigint(19) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `title` varchar(60) DEFAULT NULL,
  `title_comment` varchar(1024) DEFAULT NULL,
  `action_type` int(3) DEFAULT NULL,
  `action_url` varchar(512) DEFAULT NULL,
  `image_url` varchar(512) DEFAULT NULL,
  `image_disturl` varchar(255) DEFAULT NULL,
  `video_url` varchar(255) DEFAULT NULL,
  `content_id` bigint(19) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `content_type` varchar(64) DEFAULT NULL,
  `ref_item_id` bigint(19) DEFAULT NULL,
  `panelitem_parentid` bigint(19) DEFAULT NULL,
  `auto_run` int(1) DEFAULT NULL,
  `focus_run` int(1) DEFAULT NULL,
  `show_title` int(1) DEFAULT NULL,
  `animation_run` int(1) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `opr_userid` bigint(19) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `epg_panelitem_id` bigint(19) DEFAULT NULL,
  `epg_content_id` bigint(19) DEFAULT NULL,
  `epg_ref_item_id` bigint(19) DEFAULT NULL,
  `epg_panelitem_parentid` bigint(19) DEFAULT NULL,
  `has_sub_item` int(1) DEFAULT NULL,
  `auto_play` int(1) DEFAULT NULL,
  `install_url` varchar(255) DEFAULT NULL,
  `online_status` int(1) DEFAULT '99',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bss_panel_item
-- ----------------------------
INSERT INTO `bss_panel_item` VALUES ('1', null, 'aa', 'aa', '', '1', '', '', null, '', null, '', 'icon', null, null, '0', '1', '0', '0', null, '1', '2014-09-25 13:26:50', '2014-09-25 13:26:50', null, null, null, null, '0', '0', '', '99');

-- ----------------------------
-- Table structure for `bss_panel_panel_item_map`
-- ----------------------------
DROP TABLE IF EXISTS `bss_panel_panel_item_map`;
CREATE TABLE `bss_panel_panel_item_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `panel_id` bigint(19) DEFAULT NULL,
  `panel_item_id` bigint(19) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `rel_type` int(3) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `opr_userid` bigint(19) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `epg_panel_id` bigint(19) DEFAULT NULL,
  `epg_panelItem_id` bigint(19) DEFAULT NULL,
  `epg_rel_id` int(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bss_panel_panel_item_map
-- ----------------------------
INSERT INTO `bss_panel_panel_item_map` VALUES ('2', '1', '1', null, null, null, null, '2014-09-25 13:26:59', '2014-09-25 13:26:59', null, null, null);

-- ----------------------------
-- Table structure for `bss_preview_item`
-- ----------------------------
DROP TABLE IF EXISTS `bss_preview_item`;
CREATE TABLE `bss_preview_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) DEFAULT NULL,
  `left` int(11) DEFAULT NULL,
  `top` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `epg_ioid` bigint(20) DEFAULT NULL,
  `epg_template_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bss_preview_item
-- ----------------------------
INSERT INTO `bss_preview_item` VALUES ('3', '1', '0', '0', '1', '1', null, '1', null, null);
INSERT INTO `bss_preview_item` VALUES ('4', '1', '0', '0', '2', '1', null, '2', null, null);
INSERT INTO `bss_preview_item` VALUES ('5', '1', '2', '0', '1', '1', null, null, null, null);
INSERT INTO `bss_preview_item` VALUES ('6', '1', '3', '0', '2', '1', null, null, null, null);
INSERT INTO `bss_preview_item` VALUES ('7', '1', '0', '1', '5', '2', null, null, null, null);

-- ----------------------------
-- Table structure for `bss_preview_item_data`
-- ----------------------------
DROP TABLE IF EXISTS `bss_preview_item_data`;
CREATE TABLE `bss_preview_item_data` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(19) DEFAULT NULL,
  `left` int(2) DEFAULT NULL,
  `top` int(2) DEFAULT NULL,
  `width` int(2) DEFAULT NULL,
  `height` int(2) DEFAULT NULL,
  `type` int(1) DEFAULT NULL COMMENT '1.image\\r\\n            2.icon\\r\\n            3.',
  `sort` int(11) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL COMMENT '1.专题\r\n            2.推荐',
  `content_id` bigint(19) DEFAULT NULL,
  `content_item_id` bigint(19) DEFAULT NULL,
  `epg_ioid` bigint(19) DEFAULT NULL,
  `epg_template_id` bigint(19) DEFAULT NULL,
  `epg_content_id` bigint(19) DEFAULT NULL,
  `epg_content_item_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bss_preview_item_data
-- ----------------------------
INSERT INTO `bss_preview_item_data` VALUES ('1', '1', '2', '0', '1', '1', null, null, null, '1', null, null, null, null, null);
INSERT INTO `bss_preview_item_data` VALUES ('2', '1', '3', '0', '2', '1', null, null, null, '1', null, null, null, null, null);
INSERT INTO `bss_preview_item_data` VALUES ('3', '1', '0', '1', '5', '2', null, null, null, '1', null, null, null, null, null);
INSERT INTO `bss_preview_item_data` VALUES ('4', '1', '0', '0', '1', '1', null, '1', null, '1', null, null, null, null, null);
INSERT INTO `bss_preview_item_data` VALUES ('5', '1', '0', '0', '2', '1', null, '2', null, '1', '1', null, null, null, null);

-- ----------------------------
-- Table structure for `bss_preview_template`
-- ----------------------------
DROP TABLE IF EXISTS `bss_preview_template`;
CREATE TABLE `bss_preview_template` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(19) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `platform_id` bigint(19) DEFAULT NULL,
  `contain_ps` int(1) DEFAULT NULL,
  `epg_template_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bss_preview_template
-- ----------------------------
INSERT INTO `bss_preview_template` VALUES ('1', null, 'test', '', null, null, '1', null);

-- ----------------------------
-- Table structure for `bss_role`
-- ----------------------------
DROP TABLE IF EXISTS `bss_role`;
CREATE TABLE `bss_role` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(5000) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表，记录系统中的角色信息';

-- ----------------------------
-- Records of bss_role
-- ----------------------------
INSERT INTO `bss_role` VALUES ('1', '系统管理员', '系统管理员', '2014-04-21 13:35:54');

-- ----------------------------
-- Table structure for `bss_role_authority_map`
-- ----------------------------
DROP TABLE IF EXISTS `bss_role_authority_map`;
CREATE TABLE `bss_role_authority_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(19) DEFAULT NULL COMMENT '角色id',
  `authority_id` bigint(19) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1285543 DEFAULT CHARSET=utf8 COMMENT='记录角色所拥有的权限';

-- ----------------------------
-- Records of bss_role_authority_map
-- ----------------------------
INSERT INTO `bss_role_authority_map` VALUES ('1285445', '1', '203');
INSERT INTO `bss_role_authority_map` VALUES ('1285450', '1', '213');
INSERT INTO `bss_role_authority_map` VALUES ('1285467', '1', '231');
INSERT INTO `bss_role_authority_map` VALUES ('1285471', '1', '204');
INSERT INTO `bss_role_authority_map` VALUES ('1285496', '1', '9');
INSERT INTO `bss_role_authority_map` VALUES ('1285497', '1', '28');
INSERT INTO `bss_role_authority_map` VALUES ('1285498', '1', '61');
INSERT INTO `bss_role_authority_map` VALUES ('1285500', '1', '187');
INSERT INTO `bss_role_authority_map` VALUES ('1285504', '1', '44');
INSERT INTO `bss_role_authority_map` VALUES ('1285516', '1', '8');
INSERT INTO `bss_role_authority_map` VALUES ('1285517', '1', '23');
INSERT INTO `bss_role_authority_map` VALUES ('1285520', '1', '24');
INSERT INTO `bss_role_authority_map` VALUES ('1285524', '1', '25');
INSERT INTO `bss_role_authority_map` VALUES ('1285527', '1', '141');
INSERT INTO `bss_role_authority_map` VALUES ('1285528', '1', '142');
INSERT INTO `bss_role_authority_map` VALUES ('1285529', '1', '337');
INSERT INTO `bss_role_authority_map` VALUES ('1285530', '1', '338');
INSERT INTO `bss_role_authority_map` VALUES ('1285531', '1', '339');
INSERT INTO `bss_role_authority_map` VALUES ('1285532', '1', '340');
INSERT INTO `bss_role_authority_map` VALUES ('1285533', '1', '341');
INSERT INTO `bss_role_authority_map` VALUES ('1285534', '1', '342');
INSERT INTO `bss_role_authority_map` VALUES ('1285535', '1', '343');
INSERT INTO `bss_role_authority_map` VALUES ('1285536', '1', '344');
INSERT INTO `bss_role_authority_map` VALUES ('1285537', '1', '345');
INSERT INTO `bss_role_authority_map` VALUES ('1285538', '1', '346');
INSERT INTO `bss_role_authority_map` VALUES ('1285539', '1', '347');
INSERT INTO `bss_role_authority_map` VALUES ('1285540', '1', '348');
INSERT INTO `bss_role_authority_map` VALUES ('1285541', '1', '349');
INSERT INTO `bss_role_authority_map` VALUES ('1285542', '1', '350');

-- ----------------------------
-- Table structure for `bss_sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_menu`;
CREATE TABLE `bss_sys_menu` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `authority_id` bigint(19) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `order_sq` bigint(20) DEFAULT NULL COMMENT '导航排序',
  `parent_id` bigint(19) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bss_sys_menu
-- ----------------------------
INSERT INTO `bss_sys_menu` VALUES ('8', '8', '系统管理', '#', '8', '0', '系统管理');
INSERT INTO `bss_sys_menu` VALUES ('9', '9', '日志管理', '#', '7', '0', '日志管理');
INSERT INTO `bss_sys_menu` VALUES ('23', '23', '操作员信息维护', 'to_operator_list.shtml', '1', '8', '操作员管理');
INSERT INTO `bss_sys_menu` VALUES ('24', '24', '角色信息维护', 'to_role_list.shtml', '2', '8', '角色管理');
INSERT INTO `bss_sys_menu` VALUES ('25', '25', '权限信息维护', 'to_authority_list.shtml', '3', '8', '权限信息');
INSERT INTO `bss_sys_menu` VALUES ('28', '28', '接口日志查询', 'to_interface_log_list.shtml', '8', '9', '接口日志查询');
INSERT INTO `bss_sys_menu` VALUES ('44', '44', '系统参数配置', 'to_system_config.shtml', '7', '68', '系统参数配置');
INSERT INTO `bss_sys_menu` VALUES ('46', '61', '操作日志查询', 'to_operate_log_list.shtml', '8', '9', '操作日志查询');
INSERT INTO `bss_sys_menu` VALUES ('68', '187', '配置管理', '#', '7', '0', '配置管理');
INSERT INTO `bss_sys_menu` VALUES ('73', '203', '面板管理', '#', '2', '0', '面板管理');
INSERT INTO `bss_sys_menu` VALUES ('74', '204', '面板信息维护', 'to_panel_layout.shtml', '2', '73', '面板信息维护');
INSERT INTO `bss_sys_menu` VALUES ('80', '213', '预览面板信息维护', 'to_preview_template_list.shtml', '4', '73', '预览面板信息维护');
INSERT INTO `bss_sys_menu` VALUES ('82', '231', '面板项信息维护', 'to_panel_item_list.shtml', '4', '73', '面板项信息维护');

-- ----------------------------
-- Table structure for `system_config`
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `configkey` varchar(64) DEFAULT NULL COMMENT '键',
  `configvalue` varchar(1024) DEFAULT NULL COMMENT '值',
  `zhname` varchar(2048) DEFAULT NULL COMMENT '名称',
  `depiction` varchar(5000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_index` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES ('1', 'systemTitle', '省级屏幕运营', '系统标题', '【管理系统】系统标题');
