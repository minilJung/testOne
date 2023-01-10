# 약관 동의 내역
DROP TABLE IF EXISTS user_agreement;
CREATE TABLE user_agreement
(
    agreement_id   varchar(36)  not null,
    user_id        varchar(20)  not null,
    agreement_name varchar(128) not null,
    created_at     datetime
);
ALTER TABLE user_agreement ADD CONSTRAINT `AGREEMENT_UNIQUE_INDEX` UNIQUE(user_id, agreement_name);