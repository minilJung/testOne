
# 간편인증 스크래핑 사용성 개선을 위해 ecardId 및 responseMessage 를 저장할 컬럼 추가
use dev_ecard;

ALTER TABLE scraping ADD COLUMN ecard_id varchar(10) after scraping_id;
ALTER TABLE scraping ADD COLUMN response_message varchar(10) after callback_data;
