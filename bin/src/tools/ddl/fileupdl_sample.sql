/*
CREATE USER 'spring'@'localhost' IDENTIFIED BY 'spring';
CREATE USER 'spring'@'%' IDENTIFIED BY 'spring';

GRANT ALL PRIVILEGES ON *.* TO 'spring'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'spring'@'%' WITH GRANT OPTION;
*/

CREATE DATABASE IF NOT EXISTS SPRING_TEST CHARACTER SET utf8;

use SPRING_TEST;

DROP TABLE m_numbering;
DROP TABLE t_file_manage;
DROP TABLE w_file_access_ctrl;
DROP TABLE t_updl_file_test;
DROP TABLE t_updl_file_list_oya;
DROP TABLE t_updl_file_list_ko;

-- 採番管理テーブル
CREATE TABLE IF NOT EXISTS `m_numbering` (
  `SEQ_KEY` char(10) NOT NULL,
  `SEQ` int(10) NOT NULL,
  `SEQ_CYCLE` tinyint(3) NOT NULL,
  `UPD_FUNC_ID` char(12) NOT NULL,
  `INS_USER_ID` varchar(20) NOT NULL,
  `INS_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `UPD_USER_ID` varchar(20) DEFAULT NULL,
  `UPD_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`SEQ_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 採番初期データが必要なため、下記insertで登録
INSERT INTO spring_test.m_numbering(   `SEQ_KEY`  , `SEQ`  , `SEQ_CYCLE`  , `UPD_FUNC_ID`  , `INS_USER_ID`  , `INS_DATE`  , `UPD_USER_ID`  , `UPD_DATE`) VALUES (   'file'  , 1  , 1  , 'SYSTEM'  , 'SYSTEM'  , null  , 'SYSTEM'  , null);

-- ファイル管理テーブル
CREATE TABLE IF NOT EXISTS `t_file_manage` (
  `FILE_SEQ` char(13) NOT NULL,
  `FILE_NAME` varchar(128) NOT NULL,
  `FILE_CONTENT_TYPE` varchar(128) DEFAULT NULL,
  `FILE_STATUS` char(1) NOT NULL,
  `UPD_FUNC_ID` char(12) NOT NULL,
  `INS_USER_ID` varchar(20) NOT NULL,
  `INS_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `UPD_USER_ID` varchar(20) DEFAULT NULL,
  `UPD_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`FILE_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- アクセス制御テーブル
CREATE TABLE `w_file_access_ctrl` (
  `USER_ID` varchar(12) NOT NULL,
  `FILE_SEQ` char(13) NOT NULL,
  `UPD_FUNC_ID` char(12) NOT NULL,
  `INS_USER_ID` varchar(20) NOT NULL,
  `INS_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `UPD_USER_ID` varchar(20) DEFAULT NULL,
  `UPD_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`USER_ID`,`FILE_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

-- ファイルアップロードダウンロードテスト用テーブル（単テーブル）
CREATE TABLE IF NOT EXISTS `t_updl_file_test` (
  `ID` char(10) NOT NULL,
  `BIKO` varchar(128) DEFAULT NULL,
  `FILE_SEQ` char(13) DEFAULT NULL,
  `FILE_NAME` varchar(128) DEFAULT NULL,
  `UPD_FUNC_ID` char(12) NOT NULL,
  `INS_USER_ID` varchar(20) NOT NULL,
  `INS_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `UPD_USER_ID` varchar(20) DEFAULT NULL,
  `UPD_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ファイルアップロードダウンロードテスト用テーブル（親子関係の親）
CREATE TABLE `t_updl_file_list_oya` (
  `ID` char(10) NOT NULL,
  `BIKO` varchar(128) DEFAULT NULL,
  `UPD_FUNC_ID` char(12) NOT NULL,
  `INS_USER_ID` varchar(20) NOT NULL,
  `INS_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `UPD_USER_ID` varchar(20) DEFAULT NULL,
  `UPD_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ファイルアップロードダウンロードテスト用テーブル（親子関係の子）
CREATE TABLE `t_updl_file_list_ko` (
  `ID` char(10) NOT NULL,
  `ROW_SEQ` int(10) NOT NULL,
  `FILE_SEQ` char(13) DEFAULT NULL,
  `FILE_NAME` varchar(128) DEFAULT NULL,
  `UPD_FUNC_ID` char(12) NOT NULL,
  `INS_USER_ID` varchar(20) NOT NULL,
  `INS_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `UPD_USER_ID` varchar(20) DEFAULT NULL,
  `UPD_DATE` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`ID`,`ROW_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- トリガー（t_updl_file_test）
-- 登録時のトリガー

drop trigger trigger_t_updl_file_test_insert;

create trigger trigger_t_updl_file_test_insert after insert on t_updl_file_test for each row
BEGIN

IF (ifnull(NEW.file_seq,'') != '') THEN

update t_file_manage 
set file_status = 'C',
    upd_func_id = 'TRIGGER',
    upd_user_id = 'TRIGGER',
    upd_date = now()
where file_seq = NEW.file_seq;

END IF;

END;
/

-- 削除時のトリガー

drop trigger trigger_t_updl_file_test_delete;

create trigger trigger_t_updl_file_test_delete after delete on t_updl_file_test for each row
BEGIN

IF ifnull(OLD.file_seq,'') != '' THEN

update t_file_manage 
set file_status = 'D',
    upd_func_id = 'TRIGGER',
    upd_user_id = 'TRIGGER',
    upd_date = now()
where file_seq = OLD.file_seq;

END IF;

END;
/

-- 更新時のトリガー

drop trigger trigger_t_updl_file_test_update;

create trigger trigger_t_updl_file_test_update after update on t_updl_file_test for each row
BEGIN

IF (ifnull(OLD.file_seq,'') != ifnull(NEW.file_seq,''))  THEN

IF ifnull(OLD.file_seq,'') != '' THEN 

update t_file_manage 
set file_status = 'D',
    upd_func_id = 'TRIGGER',
    upd_user_id = 'TRIGGER',
    upd_date = now()
where file_seq = OLD.file_seq;

END IF;

IF ifnull(NEW.file_seq,'') != '' THEN 

update t_file_manage 
set file_status = 'C',
    upd_func_id = 'TRIGGER',
    upd_user_id = 'TRIGGER',
    upd_date = now()
where file_seq = NEW.file_seq;

END IF;

END IF;

END;
/

-- トリガー（t_updl_file_list_ko）
-- 登録時のトリガー

drop trigger trigger_t_updl_file_list_ko_insert;

create trigger trigger_t_updl_file_list_ko_insert after insert on t_updl_file_list_ko for each row
BEGIN

IF (ifnull(NEW.file_seq,'') != '') THEN

update t_file_manage 
set file_status = 'C',
    upd_func_id = 'TRIGGER',
    upd_user_id = 'TRIGGER',
    upd_date = now()
where file_seq = NEW.file_seq;

END IF;

END;
/

-- 削除時のトリガー

drop trigger trigger_t_updl_file_list_ko_delete;

create trigger trigger_t_updl_file_list_ko_delete after delete on t_updl_file_list_ko for each row
BEGIN

IF ifnull(OLD.file_seq,'') != '' THEN

update t_file_manage 
set file_status = 'D',
    upd_func_id = 'TRIGGER',
    upd_user_id = 'TRIGGER',
    upd_date = now()
where file_seq = OLD.file_seq;

END IF;

END;
/

-- 更新時のトリガー

drop trigger trigger_t_updl_file_list_ko_update;

create trigger trigger_t_updl_file_list_ko_update after update on t_updl_file_list_ko for each row
BEGIN

IF (ifnull(OLD.file_seq,'') != ifnull(NEW.file_seq,''))  THEN

IF ifnull(OLD.file_seq,'') != '' THEN 

update t_file_manage 
set file_status = 'D',
    upd_func_id = 'TRIGGER',
    upd_user_id = 'TRIGGER',
    upd_date = now()
where file_seq = OLD.file_seq;

END IF;

IF ifnull(NEW.file_seq,'') != '' THEN 

update t_file_manage 
set file_status = 'C',
    upd_func_id = 'TRIGGER',
    upd_user_id = 'TRIGGER',
    upd_date = now()
where file_seq = NEW.file_seq;

END IF;

END IF;

END;
/
























