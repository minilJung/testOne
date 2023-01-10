# 자격증 발급 기관 명, 최종합격일자 NOT NULL 제거
use ecdb;

ALTER TABLE user_qualification MODIFY COLUMN qualification_organization_name varchar(64);
ALTER TABLE user_qualification MODIFY COLUMN final_qualified_date  varchar(64);
