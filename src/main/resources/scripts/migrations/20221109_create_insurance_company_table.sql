
# 판매자격 로고 이미지 테이블
DROP TABLE IF EXISTS insurance_company;
CREATE TABLE insurance_company(
    insurance_company_code varchar(10),
    insurance_company_name varchar(36),
    insurance_company_logo varchar(120),
    insurance_type varchar(1)
);

use ecdb;

# 생명보험사
INSERT INTO insurance_company values ('L02', 'ABL생명', '/img/insurance_company/life/abl.svg', 'L');
INSERT INTO insurance_company values ('L78', 'BNP파리바카디프생명', '/img/insurance_company/life/abl.svg', 'L');
INSERT INTO insurance_company values ('L71', 'DB생명', '/img/insurance_company/life/db.svg', 'L');
INSERT INTO insurance_company values ('L31', 'DGB생명', '/img/insurance_company/life/dgb.svg', 'L');
INSERT INTO insurance_company values ('L18', 'KB생명', '/img/insurance_company/life/kb.svg', 'L');
INSERT INTO insurance_company values ('L33', 'KDB생명', '/img/insurance_company/life/kdb.svg', 'L');
INSERT INTO insurance_company values ('L05', '교보생명', '/img/insurance_company/life/kyobo.svg', 'L');
INSERT INTO insurance_company values ('L42', '농협생명', '/img/insurance_company/life/nh.svg', 'L');
INSERT INTO insurance_company values ('L74', '동양생명', '/img/insurance_company/life/tongYang.svg', 'L');
INSERT INTO insurance_company values ('L51', '라이나생명', '/img/insurance_company/life/cigna.svg', 'L');
INSERT INTO insurance_company values ('L72', '메트라이프', '/img/insurance_company/life/.metLife.svg', 'L');
INSERT INTO insurance_company values ('L34', '미래에셋', '/img/insurance_company/life/mirae.svg', 'L');
INSERT INTO insurance_company values ('L03', '삼성생명', '/img/insurance_company/life/samsung.svg', 'L');
INSERT INTO insurance_company values ('L11', '신한라이프', '/img/insurance_company/life/shinhanLife.svg', 'L');
INSERT INTO insurance_company values ('L61', '프루덴셜', '/img/insurance_company/life/prudential.svg', 'L');
INSERT INTO insurance_company values ('L01', '한화생명', '/img/insurance_company/life/hanwha.svg', 'L');
INSERT INTO insurance_company values ('L04', '흥국생명', '/img/insurance_company/life/heungkuk.svg', 'L');

# 손해보험사
INSERT INTO insurance_company values ('N52', 'ACE손해', '/img/insurance_company/general/ace.svg', 'G');
INSERT INTO insurance_company values ('N51', 'AIG', '/img/insurance_company/general/aig.svg', 'G');
INSERT INTO insurance_company values ('N11', 'DB손해', '/img/insurance_company/general/db.svg', 'G');
INSERT INTO insurance_company values ('N10', 'KB손해', '/img/insurance_company/general/kb.svg', 'G');
INSERT INTO insurance_company values ('N04', 'MG손해', '/img/insurance_company/general/mg.svg', 'G');
INSERT INTO insurance_company values ('N71', '농협손해', '/img/insurance_company/general/nh.svg', 'G');
INSERT INTO insurance_company values ('N17', '더케이손해(하나손해)', '/img/insurance_company/general/thek.svg', 'G');
INSERT INTO insurance_company values ('N03', '롯데손해', '/img/insurance_company/general/lotte.svg', 'G');
INSERT INTO insurance_company values ('N01', '메리츠화재', '/img/insurance_company/general/meritz.svg', 'G');
INSERT INTO insurance_company values ('N08', '삼성화재', '/img/insurance_company/general/samsung.svg', 'G');
INSERT INTO insurance_company values ('N02', '한화손해', '/img/insurance_company/general/hanwha.svg', 'G');
INSERT INTO insurance_company values ('N09', '현대해상', '/img/insurance_company/general/hyundai.svg', 'G');
INSERT INTO insurance_company values ('N05', '흥국화재', '/img/insurance_company/general/heungkuk.svg', 'G');


# 한금서에서 넘어오는 보험사명 일치하도록 업데이트
update insurance_company set insurance_company_name = '한화손보' where insurance_company_name = '한화손해';
update insurance_company set insurance_company_name = '롯데손보' where insurance_company_name = '롯데손해';
update insurance_company set insurance_company_name = 'KB손보' where insurance_company_name = 'KB손해';
update insurance_company set insurance_company_name = 'DB손보' where insurance_company_name = 'DB손해';