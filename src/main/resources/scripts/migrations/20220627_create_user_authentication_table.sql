# 유저 인증 정보 저장 테이블
DROP TABLE IF EXISTS user_authentication;
CREATE TABLE user_authentication
(
    authentication_id        varchar(36)   not null,
    user_id                  varchar(20)   not null,
    access_token             varchar(1024) not null,
    access_token_expires_at  varchar(1024) not null,
    refresh_token            varchar(1024) not null,
    refresh_token_expires_at datetime,
    expired_yn               varchar(1) default 'N',
    created_at               datetime
);