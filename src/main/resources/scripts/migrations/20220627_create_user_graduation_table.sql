# 학력 정보 테이블 DDL
DROP TABLE IF EXISTS user_graduation;
CREATE TABLE user_graduation
(
    graduation_id   varchar(36) primary key comment '학력 정보 고유 아이디',
    user_id         varchar(20) not null comment '유저 아이디',
    graduation_type varchar(32) not null comment '학교 타입',
    name            varchar(64) not null comment '학교명',
    major           varchar(64) comment '전공명',
    start_date      date comment '입학날짜',
    end_date        date                 default null comment '졸업날짜',
    attendance_type varchar(1)  not null comment '졸업구분',
    public_yn       varchar(1)  not null default 'Y' comment '공개여부',
    status          varchar(1)           default 'P' comment '승인 상태',
    delete_yn       varchar(1)           default 'N' comment '삭제 여부',
    created_at      datetime comment '학력 정보 생성 시각'
);