
# USERS account_id 컬럼 추가
ALTER TABLE user ADD account_id varchar(120) AFTER name

# user_id 컬럼 dataType 변경
ALTER TABLE ecard MODIFY user_id varchar(120)
ALTER TABLE user MODIFY user_id varchar(120)
ALTER TABLE user_agreement MODIFY user_id varchar(120)
ALTER TABLE user_authentication MODIFY user_id varchar(120)
ALTER TABLE user_badge MODIFY user_id varchar(120)
ALTER TABLE user_career MODIFY user_id varchar(120)
ALTER TABLE user_education MODIFY user_id varchar(120)
ALTER TABLE user_graduation MODIFY user_id varchar(120)
ALTER TABLE user_insurance MODIFY user_id varchar(120)
ALTER TABLE user_majority MODIFY user_id varchar(120)
ALTER TABLE user_qualification MODIFY user_id varchar(120)
ALTER TABLE user_rwps MODIFY user_id varchar(120)