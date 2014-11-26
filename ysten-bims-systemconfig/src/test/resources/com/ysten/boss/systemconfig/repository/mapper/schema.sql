DROP TABLE SYSTEM_CONFIG if exists CASCADE;
CREATE TABLE SYSTEM_CONFIG (
  id BIGINT NOT NULL Identity,
  configKey varchar(128) NOT NULL,
  configValue varchar(256) NOT NULL,
  zhname varchar(128) NOT NULL,
  depiction varchar(256) NOT NULL,
);