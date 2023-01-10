
# 간편인증 스크래핑 사용성 개선을 위해 ecardId 및 responseMessage 를 저장할 컬럼 추가
use dev_ecard;

ALTER TABLE ecard CHANGE downloadable_file_id preview_file_id varchar(36);
