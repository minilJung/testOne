# Specific info migrations
# DROP useless columns
ALTER TABLE specific_info DROP COLUMN num_customers;
ALTER TABLE specific_info DROP COLUMN num_contracts;
ALTER TABLE specific_info DROP COLUMN total_amount;
ALTER TABLE specific_info DROP COLUMN last_updated_at;
ALTER TABLE specific_info DROP COLUMN delete_yn;

ALTER TABLE specific_info ADD COLUMN ecard_id varchar(6) comment '계약 유지율 공개 여부' after employee_no;
ALTER TABLE specific_info ADD COLUMN contract_rate_public_yn varchar(1) comment '계약 유지율 공개 여부';
ALTER TABLE specific_info ADD COLUMN imperfect_rate_public_yn varchar(1) comment '불완전 판매율 공개 여부' after contract_rate_public_yn;

UPDATE specific_info
SET ecard_id = (
    SELECT ecard_id
    FROM company_user cu
             JOIN ecard ec ON cu.user_id = ec.user_id
    WHERE cu.employee_no = specific_info.employee_no
    LIMIT 1
)
WHERE ecard_id is null;

# remove useless tables
DROP TABLE authcode;
DROP TABLE token;