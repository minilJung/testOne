# User Career table refactoring
# ALTER CAREER AND CREATE career TABLE
DROP TABLE IF EXISTS user_career;
CREATE TABLE user_career
(
    career_id   varchar(36) not null,
    user_id     varchar(20) not null,
    policyholder_code varchar(36),
    career_name varchar(64),
    start_date  date        not null,
    end_date    date,
    on_duty_yn  varchar(1)  not null default 'Y',
    public_yn   varchar(1)           default 'Y'
);


ALTER TABLE user_career ADD COLUMN policyholder_code varchar(36) comment '가입자 구분' after user_id;