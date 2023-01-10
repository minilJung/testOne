
# 본인인증 연결 후 테스트를 위해 기존 등록된 FP 정보는 임의로 ci, birthdate 생성.

use ecdb;

UPDATE ecard
SET
    company_id = 'hanwhalifefs'
WHERE fp_id is not null
AND company_id = 'X' and department like '%한화생명%';
;

UPDATE user
SET
    birthdate = '1970-01-01',
    ci = uuid()
WHERE birthdate is null and ci is null;