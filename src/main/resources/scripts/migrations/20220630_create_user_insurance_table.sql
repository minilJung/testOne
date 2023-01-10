# 상담가능 보험사 테이블
DROP TABLE IF EXISTS user_insurance;
CREATE TABLE user_insurance
(
    insurance_id           varchar(36)  not null,
    user_id                varchar(20)  not null,
    insurance_company_code varchar(36)  not null,
    insurance_company_name varchar(64)  not null,
    insurance_company_logo varchar(256) not null,
    insurance_type         varchar(1)   not null,
    public_yn              varchar(256) not null,
    created_at             datetime
);
ALTER TABLE user_insurance
    ADD CONSTRAINT `USER_INSURANCE_UNIQUE_INDEX` UNIQUE (user_id, insurance_company_code);