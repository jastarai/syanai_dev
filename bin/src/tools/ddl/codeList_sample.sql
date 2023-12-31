/*
CREATE USER 'spring'@'localhost' IDENTIFIED BY 'spring';
CREATE USER 'spring'@'%' IDENTIFIED BY 'spring';

GRANT ALL PRIVILEGES ON *.* TO 'spring'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'spring'@'%' WITH GRANT OPTION;
*/

CREATE DATABASE IF NOT EXISTS SPRING_TEST CHARACTER SET utf8;

use SPRING_TEST;

DROP TABLE M_CODE_LIST;

CREATE TABLE IF NOT EXISTS M_CODE_LIST
(
    GROUP_CODE         CHAR(3)       NOT NULL,
    CODE               CHAR(10)      NOT NULL,
    GROUP_NAME         CHAR(20)      NOT NULL,
    CODE_NAME          CHAR(20),
    VIEW_ITEM          CHAR(20),
    VIEW_ORDER         int(4)        NOT NULL,
    REMARKS            CHAR(50)

) ENGINE=InnoDB;
ALTER TABLE M_CODE_LIST ADD PRIMARY KEY(GROUP_CODE, CODE);
