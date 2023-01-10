# 보험모집종사자 등록증 테이블
DROP TABLE IF EXISTS user_registration;
CREATE TABLE user_registration
(
    user_id varchar(120) primary key comment 'user_id',
    registration_file_id varchar(128) not null comment '등록증 이미지 파일 ID'
)