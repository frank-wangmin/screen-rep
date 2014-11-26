-- 增加设备过期需要通知的天数
insert into system_config(configkey, configvalue, zhname, depiction) values('notice_expire_days', '15', '通知设备即将过期的通知天数','通知设备即将过期的通知天数');