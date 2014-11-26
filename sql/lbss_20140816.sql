-- 数据同步到中心，地市数据库system_config表添加同步配置
insert into system_config (configkey,configvalue,zhname,depiction) value ('syncDataToCenterNum', '1', '发往中心同步用户数量','发往中心同步用户数量');
insert into system_config (configkey,configvalue,zhname,depiction) value ('syncDataToCenterUrl', 'http://192.168.1.1:8080/yst-bims-facade/stb/center/acceptDataToCenter', '数据同步到中心的url','数据同步到中心的url');


-- 中心的数据库需要把customer表的userId有个唯一索引去掉

ALTER TABLE bss_animation_user_map DROP FOREIGN KEY FK_Reference_74;

ALTER TABLE bss_user_upgrade_map DROP FOREIGN KEY FK_Reference_71;

ALTER TABLE bss_customer DROP INDEX user_id_index;