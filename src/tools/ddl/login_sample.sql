/*
CREATE USER 'spring'@'localhost' IDENTIFIED BY 'spring';
CREATE USER 'spring'@'%' IDENTIFIED BY 'spring';

GRANT ALL PRIVILEGES ON *.* TO 'spring'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'spring'@'%' WITH GRANT OPTION;
*/

CREATE DATABASE IF NOT EXISTS SPRING_TEST CHARACTER SET utf8;

use SPRING_TEST;

DROP TABLE M_AUTH_USER;
DROP TABLE M_AUTH_DETAIL;
CREATE TABLE IF NOT EXISTS M_AUTH_USER
(
    USER_ID            CHAR(12)      NOT NULL PRIMARY KEY,
    PASSWORD           CHAR(12)      NOT NULL,
    AUTH_CD            CHAR(12)      NOT NULL,
    UPD_FUNC_ID        CHAR(12)      NOT NULL,
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20),
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) /* 更新タイムスタンプ自動設定 */
) ENGINE=InnoDB;

INSERT INTO M_AUTH_USER(USER_ID, PASSWORD, AUTH_CD, UPD_FUNC_ID, INS_USER_ID) VALUES ('user0001', 'pass', '01001', 'SYSTEM', 'SYSTEM');


CREATE TABLE IF NOT EXISTS M_AUTH_DETAIL
(
    SEQ                INT           NOT NULL,
    AUTH_CD            CHAR(12)      NOT NULL,
    APPROVAL           CHAR(128)     NOT NULL,
    AUTH_NAME          CHAR(32),
    UPD_FUNC_ID        CHAR(12)      NOT NULL,
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20),
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) /* 更新タイムスタンプ自動設定 */
) ENGINE=InnoDB;
ALTER TABLE M_AUTH_DETAIL ADD PRIMARY KEY(SEQ, AUTH_CD);
-- このサンプルのAPPROVALは、画面コンテキストのURIを想定
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES ( 1, '01001', 'kinou01', NULL, 'SYSTEM', 'SYSTEM');
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES ( 2, '01001', 'kinou01/m01', NULL, 'SYSTEM', 'SYSTEM');
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES ( 3, '01001', 'kinou01/m02', NULL, 'SYSTEM', 'SYSTEM');
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES ( 4, '01001', 'kinou02', NULL, 'SYSTEM', 'SYSTEM');
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES ( 5, '01001', 'kinou05', NULL, 'SYSTEM', 'SYSTEM');
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES ( 6, '01001', 'kinou05/m01', NULL, 'SYSTEM', 'SYSTEM');
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES ( 7, '01001', 'kinou05/m02', NULL, 'SYSTEM', 'SYSTEM');
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES ( 8, '01001', 'kinou06', NULL, 'SYSTEM', 'SYSTEM');
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES ( 9, '01001', 'kinou08', NULL, 'SYSTEM', 'SYSTEM');
INSERT INTO M_AUTH_DETAIL(SEQ, AUTH_CD, APPROVAL, AUTH_NAME, UPD_FUNC_ID, INS_USER_ID) VALUES (10, '01001', 'kinou09', NULL, 'SYSTEM', 'SYSTEM');
