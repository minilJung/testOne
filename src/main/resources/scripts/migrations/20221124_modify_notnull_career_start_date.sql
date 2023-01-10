
# 캡쳐 근무시작일 없는 경력 존재하지 않도록 수정
use dev_ecard;

ALTER TABLE user_career MODIFY start_date date not null;