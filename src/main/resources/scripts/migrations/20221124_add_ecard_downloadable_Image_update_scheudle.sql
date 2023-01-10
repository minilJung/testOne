
# 캡쳐 이미지 업데이트 요청 큐
use dev_ecard;

DROP TABLE IF EXISTS ecard_capture_schedule;
CREATE TABLE ecard_capture_schedule(
    schedule_id varchar(36) not null,
    ecard_id varchar(6) not null,
    status varchar(1) not null default 'P',
    retry_count int default 0,
    created_at datetime,
    processed_at datetime
);