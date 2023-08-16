-- 古いテーブル情報を削除.
-- drop table T_FAV_SEARCH_DATA;
-- drop table T_FAV_SEARCH_DETAIL;
-- drop table M_FAV_SEARCH_COLUMN_NAME
-- drop table W_FAV_SEARCH_INPUT;


-- ▼お気に入り情報（お気に入り名称）tableの作成

CREATE TABLE CO_T_FAV_SEARCH_DATA
(
	
	USER_ID            VARCHAR(20) NOT NULL,
	SCRN_ID            CHAR(10) NOT NULL,
	FAV_SEARCH_NO             int(11) NOT NULL,
	FAV_SEARCH_NAME           CHAR(32),
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20)   ,
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3), /* 更新タイムスタンプ自動設定 */
    UPD_FUNC_ID char(12) NOT NULL
);
ALTER TABLE CO_T_FAV_SEARCH_DATA ADD PRIMARY KEY(USER_ID, SCRN_ID, FAV_SEARCH_NO);
-- テーブルの削除
-- drop table CO_T_FAV_SEARCH_DATA;

-- ▼お気に入り詳細情報tableの作成

CREATE TABLE CO_T_FAV_SEARCH_DETAIL
(
	USER_ID            VARCHAR(20) NOT NULL,
	SCRN_ID            CHAR(10) NOT NULL,
	FAV_SEARCH_NO             int(11) NOT NULL,
    SEQ                int(11) NOT NULL,
	COLUMN_NO int(11) NOT NULL,
    INPUT_VALUE        CHAR(64),
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20)   ,
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3), /* 更新タイムスタンプ自動設定 */
    UPD_FUNC_ID char(12) NOT NULL
);
ALTER TABLE CO_T_FAV_SEARCH_DETAIL ADD PRIMARY KEY(USER_ID, SCRN_ID, FAV_SEARCH_NO, SEQ);
-- テーブルの削除
-- drop table CO_T_FAV_SEARCH_DETAIL;

-- ▼お気に入り情報画面用検索項目名保持マスタ
CREATE TABLE CO_M_FAV_SEARCH_COLUMN_NAME
(
	SCRN_ID CHAR(10) NOT NULL,
	COLUMN_NO int(11) NOT NULL,
	INPUT_NAME CHAR(32),
	INPUT_DISP_NAME CHAR(32),
	GR_CD VARCHAR(20),
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20)   ,
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3), /* 更新タイムスタンプ自動設定 */
    UPD_FUNC_ID char(12) NOT NULL
);
ALTER TABLE CO_M_FAV_SEARCH_COLUMN_NAME ADD PRIMARY KEY(SCRN_ID, COLUMN_NO);
-- テーブルの削除
-- drop table CO_M_FAV_SEARCH_COLUMN_NAME

-- ▼お気に入り情報一時保存ワークテーブル
CREATE TABLE CO_W_FAV_SEARCH_INPUT
(
	USER_ID            VARCHAR(20) NOT NULL,
	SCRN_ID            CHAR(10) NOT NULL,
	COLUMN_NO int(11) NOT NULL,
	INPUT_VALUE        CHAR(64),
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20)   ,
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3), /* 更新タイムスタンプ自動設定 */
    UPD_FUNC_ID char(12) NOT NULL
);
ALTER TABLE CO_W_FAV_SEARCH_INPUT ADD PRIMARY KEY(USER_ID, SCRN_ID, COLUMN_NO);
-- テーブルの削除
-- drop table CO_W_FAV_SEARCH_INPUT;

-- table１へのデータ登録

insert into CO_T_FAV_SEARCH_DATA (user_id, SCRN_ID, fav_search_no, fav_search_name, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '1', 'お気に入りその１', 'SYSTEM', 'SYSTEM');
insert into CO_T_FAV_SEARCH_DATA (user_id, SCRN_ID, fav_search_no, fav_search_name, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '2', 'お気に入りその２', 'SYSTEM', 'SYSTEM');
insert into CO_T_FAV_SEARCH_DATA (user_id, SCRN_ID, fav_search_no, fav_search_name, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '3', 'お気に入りその３', 'SYSTEM', 'SYSTEM');
insert into CO_T_FAV_SEARCH_DATA (user_id, SCRN_ID, fav_search_no, fav_search_name, ins_user_id, UPD_FUNC_ID)values('user0002', 'kinou01', '1', 'お気に入りその２－１', 'SYSTEM', 'SYSTEM');


-- table2へのデータ登録(画面IDあり）
insert into CO_T_FAV_SEARCH_DETAIL (user_id, SCRN_ID, fav_search_no, seq, column_no, input_value, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '1', '0', '1', '12345', 'SYSTEM', 'SYSTEM');
insert into CO_T_FAV_SEARCH_DETAIL (user_id, SCRN_ID, fav_search_no, seq, column_no, input_value, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '1', '1', '2', '検索条件１－２', 'SYSTEM', 'SYSTEM');
insert into CO_T_FAV_SEARCH_DETAIL (user_id, SCRN_ID, fav_search_no, seq, column_no, input_value, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '1', '2', '3', '検索条件１－４', 'SYSTEM', 'SYSTEM');
insert into CO_T_FAV_SEARCH_DETAIL (user_id, SCRN_ID, fav_search_no, seq, column_no, input_value, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '1', '3', '9', '1', 'SYSTEM', 'SYSTEM');

insert into CO_T_FAV_SEARCH_DETAIL (user_id, SCRN_ID, fav_search_no, seq, column_no, input_value, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '2', '0', '1', '12345', 'SYSTEM', 'SYSTEM');
insert into CO_T_FAV_SEARCH_DETAIL (user_id, SCRN_ID, fav_search_no, seq, column_no, input_value, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '2', '1', '5', '検索条件２－１', 'SYSTEM', 'SYSTEM');
insert into CO_T_FAV_SEARCH_DETAIL (user_id, SCRN_ID, fav_search_no, seq, column_no, input_value, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '2', '2', '9', '2', 'SYSTEM', 'SYSTEM');

insert into CO_T_FAV_SEARCH_DETAIL (user_id, SCRN_ID, fav_search_no, seq, column_no, input_value, ins_user_id, UPD_FUNC_ID)values('user0001', 'kinou01', '3', '0', '1', '検索条件１', 'SYSTEM', 'SYSTEM');

insert into CO_T_FAV_SEARCH_DETAIL (user_id, SCRN_ID, fav_search_no, seq, column_no, input_value, ins_user_id, UPD_FUNC_ID)values('user0002', 'kinou01', '1', '0', '5', '検索条件２', 'SYSTEM', 'SYSTEM');


-- お気に入り情報画面用検索項目名保持マスタへのデータ登録
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID) values ('kinou01', '1', 'favSearchText1_1', '検索条件１－１', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID) values ('kinou01', '2', 'favSearchText1_2', '検索条件１－２', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID) values ('kinou01', '3', 'favSearchText1_3', '検索条件１－３', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID) values ('kinou01', '4', 'favSearchText1_4', '検索条件１－４', 'SYSTEM', 'SYSTEM');

insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID) values ('kinou01', '5', 'favSearchText2_5', '検索条件２－１', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID) values ('kinou01', '6', 'favSearchText2_6', '検索条件２－２', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID) values ('kinou01', '7', 'favSearchText2_7', '検索条件２－３', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID) values ('kinou01', '8', 'favSearchText2_8', '検索条件２－４', 'SYSTEM', 'SYSTEM');

insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID, GR_CD) values ('kinou01', '9', 'favSearchRadio3_9', '検索条件３－１', 'SYSTEM', 'SYSTEM', '001');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID, GR_CD) values ('kinou01', '10', 'favSearchRadio3_10', '検索条件３－２', 'SYSTEM', 'SYSTEM', '002');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID, GR_CD) values ('kinou01', '11', 'favSearchRadio3_11', '検索条件３－３', 'SYSTEM', 'SYSTEM', '004');

insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID) values ('kinou01', '12', 'favSearchText4_12', '検索条件４－１', 'SYSTEM', 'SYSTEM');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID, GR_CD) values ('kinou01', '13', 'favSearchSelect4_13', '検索条件４－２', 'SYSTEM', 'SYSTEM', '002');
insert into CO_M_FAV_SEARCH_COLUMN_NAME (SCRN_ID, COLUMN_NO, INPUT_NAME, INPUT_DISP_NAME, INS_USER_ID, UPD_FUNC_ID, GR_CD) values ('kinou01', '14', 'favSearchCheck4_14', '検索条件４－３	', 'SYSTEM', 'SYSTEM', '003');
