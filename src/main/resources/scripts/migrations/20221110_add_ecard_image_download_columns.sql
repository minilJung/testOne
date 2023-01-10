
# 전자명함 이미지 저장 기능 제공을 위한 이미지 컬럼 추가
ALTER TABLE ecard ADD COLUMN downloadable_file_id varchar(36) after profile_file_id;
ALTER TABLE ecard ADD COLUMN ecard_changed_yn varchar(1) default 'Y' after downloadable_file_id;

UPDATE ecard
SET ecard_changed_yn = 'Y'
WHERE ecard.ecard_changed_yn is null;