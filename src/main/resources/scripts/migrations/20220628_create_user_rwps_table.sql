# 수상내역 테이블 DDL
DROP TABLE IF EXISTS user_rwps;
CREATE TABLE user_rwps
(
    rwps_id        varchar(36) primary key comment '교육/수상내역 고유번호',
    user_id        varchar(20)            not null comment '유저 아이디',
    rwps_cntn_name varchar(128)           not null,
    rwps_broc_name varchar(128)           not null,
    public_yn      varchar(1) default 'N' not null,
    status         varchar(1) default 'P' not null,
    rwps_star_date date,
    created_at     datetime
);
ALTER TABLE user_rwps
    ADD CONSTRAINT `RWPS_UNIQUE_INDEX` UNIQUE (user_id, rwps_cntn_name);