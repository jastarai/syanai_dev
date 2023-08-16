/*
CREATE USER 'spring'@'localhost' IDENTIFIED BY 'spring';
CREATE USER 'spring'@'%' IDENTIFIED BY 'spring';

GRANT ALL PRIVILEGES ON *.* TO 'spring'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'spring'@'%' WITH GRANT OPTION;
*/

CREATE DATABASE IF NOT EXISTS SPRING_TEST CHARACTER SET utf8;

use SPRING_TEST;

CREATE TABLE IF NOT EXISTS M_VIEW_DATA
(
    SEQ                INT AUTO_INCREMENT PRIMARY KEY,
    TEXT               VARCHAR(64)    NOT NULL,
    INS_USER_ID        VARCHAR(20)    NOT NULL,
    INS_DATE           TIMESTAMP(3)   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20),
    UPD_DATE           TIMESTAMP(3)   DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) /* 更新タイムスタンプ自動設定 */
) ENGINE=InnoDB;

-- insert into M_VIEW_DATA (TEXT, UPD_USER_ID) values ('基盤サンプルデータ1', 'sample_user');
-- insert into M_VIEW_DATA (TEXT, UPD_USER_ID) values ('基盤サンプルデータ2', 'sample_user');
-- insert into M_VIEW_DATA (TEXT, UPD_USER_ID) values ('ﾃｽﾄサンプルData3', 'sample');
-- insert into M_VIEW_DATA (TEXT, UPD_USER_ID) values ('123456789', 'sampleA');
-- insert into M_VIEW_DATA (TEXT, UPD_USER_ID) values ('987654321', 'sampleB');
-- insert into M_VIEW_DATA (TEXT, UPD_USER_ID) values ('ABCDEFGHI', 'sampleC');
-- insert into M_VIEW_DATA (TEXT, UPD_USER_ID) values ('abcdefghi', 'sampleD');
CREATE TABLE T_USER_RECORD_OLD AS SELECT * FROM T_USER_RECORD;
DROP TABLE T_USER_RECORD;
CREATE TABLE IF NOT EXISTS T_USER_RECORD
(
    USER_ID            CHAR(12)      NOT NULL,
    SEQ                INT(3)        NOT NULL,
    STR_TEXT           VARCHAR(32),
    NUMBER_TEXT        BIGINT(13),
    AMOUNT             BIGINT(13),
    FILE_SEQ           CHAR(13),
    INS_USER_ID        VARCHAR(20)    NOT NULL,
    INS_DATE           TIMESTAMP(3)   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20),
    UPD_DATE           TIMESTAMP(3)   DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) /* 更新タイムスタンプ自動設定 */
) ENGINE=InnoDB;
ALTER TABLE T_USER_RECORD ADD PRIMARY KEY(USER_ID, SEQ);
INSERT INTO T_USER_RECORD (USER_ID, SEQ, STR_TEXT, NUMBER_TEXT, AMOUNT, INS_USER_ID, INS_DATE, UPD_USER_ID, UPD_DATE)
SELECT * FROM T_USER_RECORD_OLD;

-- トリガー（t_user_record）
-- 登録時のトリガー

drop trigger trigger_t_user_record_insert;
DELIMITER $$
create trigger trigger_t_user_record_insert after insert on t_user_record for each row
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
$$
DELIMITER ;
-- 削除時のトリガー

drop trigger trigger_t_user_record_delete;

DELIMITER $$
create trigger trigger_t_user_record_delete after delete on t_user_record for each row
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
$$
DELIMITER ;
-- 更新時のトリガー

drop trigger trigger_t_user_record_update;
DELIMITER $$

create trigger trigger_t_user_record_update after update on t_user_record for each row
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
$$
DELIMITER ;

CREATE TABLE IF NOT EXISTS T_MIDIUM_TEXT_DATA
(
    SEQ                INT(3)        AUTO_INCREMENT PRIMARY KEY,
    DATA_NAME          VARCHAR(32)   NOT NULL,
    TEXT_DATA          MEDIUMTEXT    NOT NULL,
    INS_USER_ID        VARCHAR(20)    NOT NULL,
    INS_DATE           TIMESTAMP(3)   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20),
    UPD_DATE           TIMESTAMP(3)   DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) /* 更新タイムスタンプ自動設定 */
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS T_BLOB_FILE
(
    SEQ                INT(3)        AUTO_INCREMENT PRIMARY KEY,
    FILE_PATH          VARCHAR(64)   NOT NULL,
    FILE_NAME          VARCHAR(32)   NOT NULL,
    FILE_DATA          LONGBLOB      NOT NULL,
    FILE_TYPE          INT(1)        NOT NULL,
    INS_USER_ID        VARCHAR(20)    NOT NULL,
    INS_DATE           TIMESTAMP(3)   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20),
    UPD_DATE           TIMESTAMP(3)   DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) /* 更新タイムスタンプ自動設定 */
) ENGINE=InnoDB;


-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('testUser002','0','一覧ﾃｽﾄA','999988887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('testUser003','1','一覧ﾃｽﾄB','999988886666000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('testUser004','2','一覧ﾃｽﾄC','999999997777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('testUser005','3','一覧テストD','888888887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('testUser005','4','一覧テストE','111188887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('testUser005','5','一覧テストF','222288887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('testUser005','6','一覧ﾃｽﾄG','333388887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('testUser006','7','一覧テストH','444488887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム0','8','一覧テストE','111188887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム1','9','一覧テストF','222288887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム2','10','一覧ﾃｽﾄG','333388887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム3','11','一覧テストH','444488887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム4','12','一覧テストE','111188887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム5','13','一覧テストF','222288887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム6','14','一覧ﾃｽﾄG','333388887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム7','15','一覧テストH','444488887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム8','16','一覧テストI','444488887777000999','kibanT');
-- insert into t_user_record (user_id,seq,STR_TEXT,NUMBER_TEXT,ins_user_id) values('基盤チーム9','17','一覧テストJ','444488887777000999','kibanT');
