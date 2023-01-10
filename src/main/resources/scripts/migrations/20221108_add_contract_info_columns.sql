# 계약 현황 정보 저장할 컬럼
ALTER TABLE ecard ADD COLUMN customer_counts int after customers_public_yn;
ALTER TABLE ecard ADD COLUMN contract_counts int after contracts_public_yn;
ALTER TABLE ecard ADD COLUMN contract_rate int after contract_rate_public_yn;
ALTER TABLE ecard ADD COLUMN imperfect_counts int after imperfect_rate_public_yn;