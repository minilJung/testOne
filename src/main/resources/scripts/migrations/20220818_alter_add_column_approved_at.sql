
# ApprovedAt 컬럼 추가
ALTER TABLE user_education ADD COLUMN approved_at datetime AFTER created_at;
ALTER TABLE user_rwps ADD COLUMN approved_at datetime AFTER created_at;
ALTER TABLE user_graduation ADD COLUMN approved_at datetime AFTER created_at;

# drop delete_yn
ALTER TABLE user_graduation DROP COLUMN delete_yn;