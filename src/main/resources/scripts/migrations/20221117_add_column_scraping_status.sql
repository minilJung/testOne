# 경력 스크래핑 결과 여부를 체크할 컬럼 추가
ALTER TABLE user_career ADD scraping_status varchar(10) after public_yn;

# 자격증 스크래핑 결과 여부를 체크할 컬럼 추가
ALTER TABLE user_qualification ADD scraping_status varchar(10) after public_yn;


# 스크래핑의 고유 값을 저장할 테이블 추가
DROP TABLE IF EXISTS scraping;
CREATE TABLe scraping(
    scraping_id   varchar(36) primary key,
    request_data TEXT not null,
    callback_data TEXT,
    status varchar(1),
    created_at datetime,
    updated_at datetime
)