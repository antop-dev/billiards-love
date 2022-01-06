create table if not exists tbl_mmbr
(
    mmbr_id     bigint      not null auto_increment comment '회원 아이디',
    mmbr_nck_nm varchar(50) null comment '회원 별명',
    mmbr_hndc   tinyint     null comment '핸디탭',
    rgst_dt     datetime    not null comment '등록일시',
    mdfy_dt     datetime    null comment '수정일시',
    kko_lgn_id  bigint      not null comment '카카오톡 로그인 아이디',
    mgnr_yn     char(1)     not null default 'N' comment '관리자 여부',
    primary key (mmbr_id)
);

create table if not exists tbl_kko_lgn
(
    lgn_id            bigint       not null comment '카카오톡 로그인 아이디',
    lst_cnct_dt       datetime     not null comment '마지막 접속일시',
    nck_nm            varchar(255) not null comment '카카오톡 별명',
    prfl_img_url      varchar(255) null comment '프로파일 이미지',
    prfl_thmb_img_url varchar(255) null comment '프로파일 썸네일 이미지',
    primary key (lgn_id)
);

create table if not exists tbl_cnts
(
    cnts_id       bigint       not null auto_increment comment '대회 아이디',
    cnts_nm       varchar(100) not null comment '대회명',
    cnts_dscr     text         null comment '대회 설명',
    strt_date     date         null comment '시작 일자',
    strt_time     time         null comment '시작 시간',
    end_date      date         null comment '종료 일자',
    end_time      time         null comment '종료 시간',
    prgr_stt      varchar(10)  not null comment '진행 상태',
    max_prtc_prsn int          null comment '최대 참가인원',
    crnt_prsn     int          not null default 0 comment '현재 참가인원',
    cnts_prgr     double       not null default 0.0 comment '진행률 (%)',
    rgst_dt       datetime     not null default now() comment '등록 일시',
    mdfy_dt       datetime     null comment '수정 일시',
    primary key (cnts_id)
);

create table if not exists tbl_plyr
(
    plyr_id   bigint  not null auto_increment comment '선수 아이디',
    cnts_id   bigint  not null comment '대회 아이디',
    mmbr_id   bigint  not null comment '회원 아이디',
    plyr_no   int     null comment '선수 번호',
    prtc_hndc tinyint null comment '선수 핸디캡',
    plyr_rnkn int     null comment '순위',
    plyr_scr  int     not null comment '점수',
    plyr_vrtn int     not null default 0 comment '순위 변동',
    plyr_prgr double  not null default 0 comment '개인 진행률',
    primary key (plyr_id),
    unique (cnts_id, mmbr_id)
);

create table if not exists tbl_mtc
(
    mtc_id          bigint   not null auto_increment comment '매칭 아이디',
    cnts_id         bigint   not null comment '대회 아이디',
    plyr1_id        bigint   not null comment '왼쪽 선수 아이디',
    plyr2_id        bigint   not null comment '오른쪽 선수 아이디',
    plyr1_rslt_inpt char(3)  not null default 'NNN' comment '왼쪽 선수 결과 입력',
    plyr2_rslt_inpt char(3)  not null default 'NNN' comment '오른쪽 선수 결과 입력',
    cnfr_mmbr_id    bigint   null comment '확정한 맴버 아이디',
    cnfr_dt         datetime null comment '확정 일시',
    primary key (mtc_id)
);
