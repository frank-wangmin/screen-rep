--去掉用户解绑终端操作
delete from  bss_role_authority_map where authority_id = ( select id from  bss_authority where code = 'customer_relation_remote_device' );
delete from  bss_authority where code = 'customer_relation_remote_device';

-- 在省级增加同步数据按钮
INSERT into bss_authority(code,name,description,parent_id) values('push_province_panel_data','向中心同步省级数据','向中心同步省级数据',204);

insert into bss_role_authority_map(role_id,authority_id) values(1,337);

-- 省级中配置同步数据到中心的地址
insert INTO system_config(configkey,configvalue,zhname,depiction) values('syncProvincePanelData','http://localhost:8080/center/receivePanelDatas.json','省级同步数据到中心','省级同步数据到中心');

-- 中心，省配置部署区域行政区规划
insert INTO system_config(configkey,configvalue,zhname,depiction) values('deployDistrictCode','320000','部署区域行政区划码','系统部署所在区域的行政区划码');

-- 面板，面板项，面板与面板项的关系，预览模块实例表中增加字段district_code
ALTER TABLE `bss_panel`
ADD COLUMN `district_code`  varchar(6) NULL AFTER `ref_panel_id`;

ALTER TABLE `bss_panel_item`
ADD COLUMN `district_code`  varchar(6) NULL AFTER `online_status`;

ALTER TABLE `bss_panel_panel_item_map`
ADD COLUMN `district_code`  varchar(6) NULL AFTER `epg_rel_id`;

ALTER TABLE `bss_preview_item_data`
ADD COLUMN `district_code`  varchar(6) NULL AFTER `epg_content_item_id`;