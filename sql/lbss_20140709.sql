-- ftp配置方式修改
delete FROM `system_config` where id in(31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,53,54,55,56,126,127,128,129);
INSERT INTO `system_config` VALUES ('28', 'hotServer', 'ftp://mediaftp:21ysten123@112.25.7.82:21', '热点ftp服务器', '【内容注入】热点ftp服务器');
INSERT INTO `system_config` VALUES ('31', 'coldServer', 'ftp://mediaftp:21ysten123@112.25.7.61:21', '冷点ftp服务器', '【内容注入】冷点ftp服务器');
INSERT INTO `system_config` VALUES ('32', 'xmlServer', 'ftp://xmlftp:21ysten123@112.25.7.61:21', 'XML文件上传FTP服务器', '【内容注入】XML文件上传FTP服务器');
INSERT INTO `system_config` VALUES ('33', 'posterServer', 'ftp://xxx:xxx@xxx:xx', '海报ftp服务器', '【内容注入】海报ftp服务器');
INSERT INTO `system_config` VALUES ('34', 'urlServer', 'ftp://xxx:xxx@xxx:xx', '媒体Url文件下载FTP服务器', '【内容注入】媒体Url文件下载FTP服务器');
INSERT INTO `system_config` VALUES ('35', 'uploadSnoFtpServer', 'ftp://ysten:cache2013ysten@223.87.2.173:21', '定时上传5M号的ftp服务器', '【业务办理】定时上传5M号的ftp服务器');
INSERT INTO `system_config` VALUES ('36', 'ttFtpServer', 'ftp://xxx:xxx@xxx:xx', '上海视频基地FTP', '上海视频基地FTP');

--device_code更改为ysten_id
alter table bss_panel_package_device_map change device_code  ysten_id varchar(32);

--标识系统为中心|省级[configvalue=true,表示是中心管理系统]
INSERT INTO `system_config`(configkey,configvalue,zhname,depiction) VALUES ('isCenter', 'true', '是否是中心系统', '是否是中心系统');
