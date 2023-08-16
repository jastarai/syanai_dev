-- ▼共通コードマスタ
CREATE TABLE CO_M_CODE
(
	GR_CD VARCHAR(20) NOT NULL,
	CD VARCHAR(20) NOT NULL,
	GR_NM VARCHAR(128) NOT NULL,
	CD_NM VARCHAR(128) NOT NULL,
	DISP_VAL VARCHAR(128) NOT NULL,
	DISP_SEQ int NOT NULL,
	BIKO VARCHAR(255) DEFAULT NULL,
    INS_USER_ID        VARCHAR(20)   NOT NULL,
    INS_DATE           TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UPD_USER_ID        VARCHAR(20)   ,
    UPD_DATE           TIMESTAMP(3)  DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3), /* 更新タイムスタンプ自動設定 */
    UPD_FUNC_ID char(12) NOT NULL
);
ALTER TABLE CO_M_CODE ADD PRIMARY KEY(GR_CD, CD);
-- テーブルの削除
-- drop table CO_M_CODE

--マスタテーブルへの登録
insert into CO_M_CODE(gr_cd, cd, gr_nm, cd_nm, disp_val, disp_seq, ins_user_id, UPD_FUNC_ID) values ('001', '1', 'ラジオボタン値', '選択値１', '選択１', 1, 'SYSTEM', 'SYSTEM');
insert into CO_M_CODE(gr_cd, cd, gr_nm, cd_nm, disp_val, disp_seq, ins_user_id, UPD_FUNC_ID) values ('001', '2', 'ラジオボタン値', '選択値２', '選択２', 2, 'SYSTEM', 'SYSTEM');

insert into CO_M_CODE(gr_cd, cd, gr_nm, cd_nm, disp_val, disp_seq, ins_user_id, UPD_FUNC_ID) values ('002', 'select1', 'リスト値', 'リスト選択値１', 'リスト１', 1, 'SYSTEM', 'SYSTEM');
insert into CO_M_CODE(gr_cd, cd, gr_nm, cd_nm, disp_val, disp_seq, ins_user_id, UPD_FUNC_ID) values ('002', 'select2', 'リスト値', 'リスト選択値２', 'リスト２', 2, 'SYSTEM', 'SYSTEM');

insert into CO_M_CODE(gr_cd, cd, gr_nm, cd_nm, disp_val, disp_seq, ins_user_id, UPD_FUNC_ID) values ('003', 'A', 'チェック値', 'チェック選択値１', 'チェックA', 1, 'SYSTEM', 'SYSTEM');
insert into CO_M_CODE(gr_cd, cd, gr_nm, cd_nm, disp_val, disp_seq, ins_user_id, UPD_FUNC_ID) values ('003', 'B', 'チェック値', 'チェック選択値２', 'チェックB', 2, 'SYSTEM', 'SYSTEM');