# 유저 뱃지 정보 테이블
DROP TABLE IF EXISTS user_badge;
CREATE TABLE user_badge
(
    badge_id        varchar(36) not null,
    user_id         varchar(20) not null,
    badge_name      varchar(256) comment '뱃지 명',
    badge_image_url varchar(1024) comment '뱃지 이미지 링크',
    badge_dark_image_url varchar(1024) comment '뱃지 이미지 링크(어두운 테마)',
    created_at      datetime comment '뱃지 등록 시각',
    public_yn       varchar(1) comment '공개 여부'
);
ALTER TABLE user_badge
    ADD CONSTRAINT `USER_BADGE_UNIQUE_INDEX` UNIQUE (user_id, badge_name);