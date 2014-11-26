drop table if exists bss_member;

/*==============================================================*/
/* Table: bss_member                                            */
/*==============================================================*/
create table bss_member
(
   ID                   bigint(20) not null comment '用户ID，关联用户信息表 ,会员表主键',
   LOGIN_NAME           varchar(32) not null comment '登录名',
   PASSWORD             varchar(32) not null comment '密码',
   PHONE                varchar(128) comment '电话',
   STATE                varchar(32) comment '状态：1为正常，2为停用，3为销户',
   CREATE_DATE          datetime comment '创建时间',
   STATE_CHANGE_DATE    datetime comment '状态更改时间',
   primary key (ID)
);

alter table bss_member comment '会员信息表，用户表的一个子集';
