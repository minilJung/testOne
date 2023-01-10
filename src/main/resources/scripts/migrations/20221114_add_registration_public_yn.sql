
# 보험모집종사자 등록증 public_yn, last_updated_at 추가.. 및 보험모집종사자 등록증 대리키 생성
ALTER TABLE user_registration ADD COLUMN registration_id varchar(36) after user_id;
ALTER TABLE user_registration MODIFY COLUMN user_id varchar(120) after registration_id;
ALTER TABLE user_registration ADD COLUMN public_yn varchar(36) default 'Y' after registration_file_id;
ALTER TABLE user_registration ADD COLUMN created_at datetime after public_yn;
ALTER TABLE user_registration ADD COLUMN last_updated_at datetime after created_at;

UPDATE user_registration
SET
    registration_id = UUID(),
    public_yn = 'Y',
    created_at = now(),
    last_updated_at = now()
WHERE registration_id is null;

ALTER TABLE user_registration DROP PRIMARY KEY;
ALTER TABLE user_registration ADD CONSTRAINT PRIMARY KEY(registration_id);

ALTER TABLE user_registration ADD CONSTRAINT UNIQUE INDEX(user_id);