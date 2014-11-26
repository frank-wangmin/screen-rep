-- 配置面板上传功能的sql
insert into system_config(configkey, configvalue, zhname, depiction) values('videoUrl', 'http://localhost:8080/video/', '视频访问地址','上传的视频访问地址');
insert into system_config(configkey, configvalue, zhname, depiction) values('picUrl', 'http://localhost:8080/pic/', '图片访问地址','上传的图片访问地地址');
insert into system_config(configkey, configvalue, zhname, depiction) values('picFiles', 'D:\apache-tomcat-7.0.54\webapps\pic\', '图片上传地址','图片上传地址');
insert into system_config(configkey, configvalue, zhname, depiction) values('videoFiles', 'D:\apache-tomcat-7.0.54\webapps\video\', '视频上传地址','视频上传地址');
