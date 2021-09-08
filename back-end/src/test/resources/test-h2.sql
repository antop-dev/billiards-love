delete
from tbl_mtc;
delete
from tbl_plyr;
delete
from tbl_cnts;
delete
from tbl_mmbr;
delete
from tbl_kko_lgn;
alter table tbl_mmbr
    alter column mmbr_id bigint auto_increment (6);
alter table tbl_cnts
    alter column cnts_id bigint auto_increment (6);
alter table tbl_plyr
    alter column plyr_id bigint auto_increment (6);
alter table tbl_mtc
    alter column cnts_id bigint auto_increment (11);
/* 카카오 로그인 목록 */
insert into tbl_kko_lgn (lgn_id, lst_cnct_dt, nck_nm, prfl_img_url, prfl_thmb_img_url)
values (1, now(), '안정용', 'https://picsum.photos/640', 'https://picsum.photos/110');
insert into tbl_kko_lgn (lgn_id, lst_cnct_dt, nck_nm, prfl_img_url, prfl_thmb_img_url)
values (2, now(), '김형익', 'https://picsum.photos/640', 'https://picsum.photos/110');
insert into tbl_kko_lgn (lgn_id, lst_cnct_dt, nck_nm, prfl_img_url, prfl_thmb_img_url)
values (3, now(), '김정민', 'https://picsum.photos/640', 'https://picsum.photos/110');
insert into tbl_kko_lgn (lgn_id, lst_cnct_dt, nck_nm, prfl_img_url, prfl_thmb_img_url)
values (4, now(), '홍길동', 'https://picsum.photos/640', 'https://picsum.photos/110');
insert into tbl_kko_lgn (lgn_id, lst_cnct_dt, nck_nm, prfl_img_url, prfl_thmb_img_url)
values (5, now(), '성춘향', 'https://picsum.photos/640', 'https://picsum.photos/110');
/* 회원 목록 */
insert into tbl_mmbr (mmbr_id, mmbr_nck_nm, mmbr_hndc, rgst_dt, kko_lgn_id, mgnr_yn)
values (1, '안탑', 22, now(), 1, 'Y');
insert into tbl_mmbr (mmbr_id, mmbr_nck_nm, mmbr_hndc, rgst_dt, mdfy_dt, kko_lgn_id)
VALUES (2, '띠용', 20, now(), now(), 2);
insert into tbl_mmbr (mmbr_id, mmbr_nck_nm, mmbr_hndc, rgst_dt, kko_lgn_id)
values (3, '잼미니', 25, NOW(), 3);
insert into tbl_mmbr (mmbr_id, mmbr_nck_nm, mmbr_hndc, rgst_dt, kko_lgn_id)
values (4, '인디', 30, NOW(), 4);
insert into tbl_mmbr (mmbr_id, mmbr_nck_nm, mmbr_hndc, rgst_dt, kko_lgn_id)
values (5, '춘향이', 15, NOW(), 5);
insert into tbl_mmbr (mmbr_id, mmbr_nck_nm, mmbr_hndc, rgst_dt, kko_lgn_id)
values (6, '짝대기', 40, NOW(), 5);
/** 진행중인 대회 */
insert into tbl_cnts (cnts_id, cnts_nm, cnts_dscr, strt_date, strt_time, end_date, end_time, prgr_stt, max_prtc_prsn,
                      rgst_dt)
values (1, '2021 리그전', '2021.01.01~', parsedatetime('20210101', 'yyyyMMdd'), PARSEDATETIME('000000', 'HHmmss'),
        parsedatetime('20211230', 'yyyyMMdd'), PARSEDATETIME('235959', 'HHmmss'), '0', 32,
        parsedatetime('20191112151145', 'yyyyMMddHHmmss'));
/* 참가자 목록 */
insert into tbl_plyr (plyr_id, cnts_id, mmbr_id, plyr_no, prtc_hndc, plyr_rnkn, plyr_scr)
values (1, 1, 1, 1, 22, 1, 150);
insert into tbl_plyr (plyr_id, cnts_id, mmbr_id, plyr_no, prtc_hndc, plyr_rnkn, plyr_scr)
values (2, 1, 2, 2, 24, 2, 40);
insert into tbl_plyr (plyr_id, cnts_id, mmbr_id, plyr_no, prtc_hndc, plyr_rnkn, plyr_scr)
values (3, 1, 3, 3, 26, 3, 10);
insert into tbl_plyr (plyr_id, cnts_id, mmbr_id, plyr_no, prtc_hndc, plyr_scr)
values (4, 1, 4, 4, 28, 20);
insert into tbl_plyr (plyr_id, cnts_id, mmbr_id, plyr_no, prtc_hndc, plyr_scr)
values (5, 1, 5, 5, 30, 30);
/* 경기 목록 */
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (1, 1, 1, 2, 'WWL', 'LLW', 1, parsedatetime('20210102171202', 'yyyyMMddHHmmss'));
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (2, 1, 1, 3, 'NNN', 'NNN', null, null);
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (3, 1, 1, 4, 'NNN', 'NNN', null, null);
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (4, 1, 1, 5, 'NNN', 'NNN', null, null);
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (5, 1, 2, 3, 'LLL', 'WWW', null, null);
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (6, 1, 2, 4, 'NNN', 'NNN', null, null);
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (7, 1, 2, 5, 'NNN', 'NNN', null, null);
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (8, 1, 3, 4, 'NNN', 'NNN', null, null);
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (9, 1, 3, 5, 'NNN', 'NNN', null, null);
insert into tbl_mtc (mtc_id, cnts_id, plyr1_id, plyr2_id, plyr1_rslt_inpt, plyr2_rslt_inpt, cnfr_mmbr_id, cnfr_dt)
values (10, 1, 4, 5, 'NNN', 'NNN', null, null);
/** 접수중인 대회 */
insert into tbl_cnts (cnts_id, cnts_nm, cnts_dscr, strt_date, strt_time, end_date, end_time, prgr_stt, max_prtc_prsn,
                      rgst_dt)
values (2, '2022 리그전', '2021.05.01~', parsedatetime('20210101', 'yyyyMMdd'), parsedatetime('000000', 'HHmmss'),
        parsedatetime('20221230', 'yyyyMMdd'), parsedatetime('235959', 'HHmmss'), '1', 128,
        parsedatetime('20200411001145', 'yyyyMMddHHmmss'));
insert into tbl_plyr (plyr_id, cnts_id, mmbr_id, plyr_no, prtc_hndc, plyr_scr)
values (6, 2, 4, null, 30, null);
insert into tbl_plyr (plyr_id, cnts_id, mmbr_id, plyr_no, prtc_hndc, plyr_scr)
values (7, 2, 1, null, 23, null);
insert into tbl_plyr (plyr_id, cnts_id, mmbr_id, plyr_no, prtc_hndc, plyr_scr)
values (8, 2, 2, null, 21, null);
insert into tbl_plyr (plyr_id, cnts_id, mmbr_id, plyr_no, prtc_hndc, plyr_scr)
values (9, 2, 3, null, 28, null);
/** 준비중인 대회 (1) */
insert into tbl_cnts (cnts_id, cnts_nm, cnts_dscr, strt_date, strt_time, end_date, end_time, prgr_stt, max_prtc_prsn,
                      rgst_dt, mdfy_dt)
values (3, '준비중인 대회 (1)', null, now(), null, now(), null, '2', null, now(), now());
/** 준비중인 대회 (2) */
insert into tbl_cnts (cnts_id, cnts_nm, cnts_dscr, strt_date, strt_time, end_date, end_time, prgr_stt, max_prtc_prsn,
                      rgst_dt, mdfy_dt)
values (4, '준비중인 대회 (2)', null, parsedatetime('20210801', 'yyyyMMdd'), null, null, null, '2', null, now(), now());
/** 중지된 대회 */
insert into tbl_cnts (cnts_id, cnts_nm, cnts_dscr, strt_date, strt_time, end_date, end_time, prgr_stt, max_prtc_prsn,
                      rgst_dt, mdfy_dt)
values (5, '중지된 대회', null, parsedatetime('20210101', 'yyyyMMdd'), null, null, null, '3', null, now(), now());
/** 종료된 대회 */
insert into tbl_cnts (cnts_id, cnts_nm, cnts_dscr, strt_date, strt_time, end_date, end_time, prgr_stt, max_prtc_prsn,
                      rgst_dt, mdfy_dt)
values (6, '종료된 대회', null, parsedatetime('20200101', 'yyyyMMdd'), null, null, null, '4', null, now(), now());
