# 교육 내역 테이블 DDL
DROP TABLE IF EXISTS user_education;
CREATE TABLE user_education
(
    education_id                varchar(36) primary key comment '교육/수상내역 고유번호',
    user_id                     varchar(20)  not null comment '유저 아이디',
    education_name              varchar(64)  not null,
    education_organization_name varchar(128) not null,
    start_date                  date         not null,
    end_date                    date         not null,
    education_file_url          varchar(128),
    public_yn                   varchar(1) default 'N',
    status                      varchar(1) default 'P',
    created_at                  datetime
);
ALTER TABLE user_education
    ADD CONSTRAINT `EDUCATION_UNIQUE_INDEX` UNIQUE (user_id, education_name);