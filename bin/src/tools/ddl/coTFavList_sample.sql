-- 古いテーブルを削除
-- drop table T_FAV_LIST_DATA;
-- drop table T_FAV_LIST_DETAIL;
-- drop table M_FAV_LIST_COLUMN_NAME


-- ▼お気に入り情報（お気に入り名称）tableの作成

CREATE TABLE CO_T_FAV_LIST_DATA
(
	
	USER_ID            VARCHAR(20) NOT NULL,
	SCRN_ID            CHAR(10) NOT NULL,
	FAV_LIST_NO             int(11) NOT NULL,
	FAV_LIST_NAME           CHAR(32),
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20)   ,
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3), /* 更新タイムスタンプ自動設定 */
    UPD_FUNC_ID char(12) NOT NULL
);
ALTER TABLE CO_T_FAV_LIST_DATA ADD PRIMARY KEY(USER_ID, SCRN_ID, FAV_LIST_NO);
-- テーブルの削除
-- drop table CO_T_FAV_LIST_DATA;

-- ▼お気に入り詳細情報tableの作成

CREATE TABLE CO_T_FAV_LIST_DETAIL
(
	USER_ID            VARCHAR(20) NOT NULL,
	SCRN_ID            CHAR(10) NOT NULL,
	FAV_LIST_NO             int(11) NOT NULL,
    SEQ                int(11) NOT NULL,
	COLUMN_NO int(11) NOT NULL,
    DISP_ORDER int(11) ,
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20)   ,
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3), /* 更新タイムスタンプ自動設定 */
    UPD_FUNC_ID char(12) NOT NULL
);
ALTER TABLE CO_T_FAV_LIST_DETAIL ADD PRIMARY KEY(USER_ID, SCRN_ID, FAV_LIST_NO, SEQ);
-- テーブルの削除
-- drop table CO_T_FAV_LIST_DETAIL;

-- ▼お気に入り情報画面用検索項目名保持マスタ
CREATE TABLE CO_M_FAV_LIST_COLUMN_NAME
(
	SCRN_ID CHAR(10) NOT NULL,
	COLUMN_NO int(11) NOT NULL,
	COLUMN_ID CHAR(32),
	COLUMN_NAME CHAR(32),
	COLUMN_ORDER int(11),
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20)   ,
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3), /* 更新タイムスタンプ自動設定 */
    UPD_FUNC_ID char(12) NOT NULL
);
ALTER TABLE CO_M_FAV_LIST_COLUMN_NAME ADD PRIMARY KEY(SCRN_ID, COLUMN_NO);
-- テーブルの削除
-- drop table CO_M_FAV_LIST_COLUMN_NAME


-- table１へのデータ登録

-- insert into CO_T_FAV_LIST_DATA (user_id, SCRN_ID, fav_list_no, fav_list_name, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', 'お気に入りその１', 'SYSTEM', 'SYSTEM');
-- insert into CO_T_FAV_LIST_DATA (user_id, SCRN_ID, fav_list_no, fav_list_name, ins_user_id, UPD_FUNC_ID)values('user0002', 'kinou11', '1', 'お気に入りその２－１', 'SYSTEM', 'SYSTEM');


-- table2へのデータ登録(画面IDあり）
-- insert into CO_T_FAV_LIST_DETAIL (user_id, SCRN_ID, fav_list_no, seq, column_no,  disp_order, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', '0', '1',  '1', 'SYSTEM', 'SYSTEM');
-- insert into CO_T_FAV_LIST_DETAIL (user_id, SCRN_ID, fav_list_no, seq, column_no,  disp_order, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', '1', '2',  '2', 'SYSTEM', 'SYSTEM');
-- insert into CO_T_FAV_LIST_DETAIL (user_id, SCRN_ID, fav_list_no, seq, column_no,  disp_order, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', '2', '3',  '3', 'SYSTEM', 'SYSTEM');
-- insert into CO_T_FAV_LISt_DETAIL (user_id, SCRN_ID, fav_list_no, seq, column_no, disp_order,  ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', '3', '4',  '4', 'SYSTEM', 'SYSTEM');
-- insert into CO_T_FAV_LISt_DETAIL (user_id, SCRN_ID, fav_list_no, seq, column_no, disp_order,  ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', '4', '5',  '5', 'SYSTEM', 'SYSTEM');
-- insert into CO_T_FAV_LISt_DETAIL (user_id, SCRN_ID, fav_list_no, seq, column_no, disp_order,  ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', '5', '6',  '6', 'SYSTEM', 'SYSTEM');
-- insert into CO_T_FAV_LISt_DETAIL (user_id, SCRN_ID, fav_list_no, seq, column_no, disp_order,  ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', '6', '7',  '7', 'SYSTEM', 'SYSTEM');
-- insert into CO_T_FAV_LISt_DETAIL (user_id, SCRN_ID, fav_list_no, seq, column_no, disp_order,  ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', '7', '8',  '8', 'SYSTEM', 'SYSTEM');
-- insert into CO_T_FAV_LISt_DETAIL (user_id, SCRN_ID, fav_list_no, seq, column_no, disp_order,  ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou11', '1', '8', '9',  '9', 'SYSTEM', 'SYSTEM');



-- お気に入り情報画面用検索項目名保持マスタへのデータ登録（必須）
insert into CO_M_FAV_LIST_COLUMN_NAME (SCRN_ID, COLUMN_NO, COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, INS_USER_ID, UPD_FUNC_ID) values ('kinou11', '1', 'userId', 'ユーザID', '1', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_LIST_COLUMN_NAME (SCRN_ID, COLUMN_NO, COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, INS_USER_ID, UPD_FUNC_ID) values ('kinou11', '2', 'seq', 'シーケンス', '2', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_LIST_COLUMN_NAME (SCRN_ID, COLUMN_NO, COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, INS_USER_ID, UPD_FUNC_ID) values ('kinou11', '3', 'strText', '文字列', '3', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_LIST_COLUMN_NAME (SCRN_ID, COLUMN_NO, COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, INS_USER_ID, UPD_FUNC_ID) values ('kinou11', '4', 'numberText', '数字文字列', '4', 'SYSTEM', 'SYSTEM');

insert into CO_M_FAV_LIST_COLUMN_NAME (SCRN_ID, COLUMN_NO, COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, INS_USER_ID, UPD_FUNC_ID) values ('kinou11', '5', 'amount', 'AMOUNT', '5', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_LIST_COLUMN_NAME (SCRN_ID, COLUMN_NO, COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, INS_USER_ID, UPD_FUNC_ID) values ('kinou11', '6', 'insUserId', '登録ユーザID', '6', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_LIST_COLUMN_NAME (SCRN_ID, COLUMN_NO, COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, INS_USER_ID, UPD_FUNC_ID) values ('kinou11', '7', 'insDate', '登録日時', '7', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_LIST_COLUMN_NAME (SCRN_ID, COLUMN_NO, COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, INS_USER_ID, UPD_FUNC_ID) values ('kinou11', '8', 'updUserId', '更新ユーザID', '8', 'SYSTEM', 'SYSTEM');

insert into CO_M_FAV_LIST_COLUMN_NAME (SCRN_ID, COLUMN_NO, COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, INS_USER_ID, UPD_FUNC_ID) values ('kinou11', '9', 'updDate', '更新日時', '9', 'SYSTEM', 'SYSTEM');
