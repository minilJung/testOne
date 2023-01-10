# 자격증 정보
DROP TABLE IF EXISTS user_qualification;
CREATE TABLE user_qualification
(
    qualification_id                varchar(36) not null,
    user_id                         varchar(20) not null,
    qualification_name              varchar(64) not null,
    qualification_organization_name varchar(64) not null,
    final_qualified_date            varchar(64) not null,
    public_yn                       varchar(1) default 'N'
);
ALTER TABLE USER_QUALIFICATION
    ADD CONSTRAINT PRIMARY KEY (qualification_id);
