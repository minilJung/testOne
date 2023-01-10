# move specific info to ecard
ALTER TABLE ecard
    ADD COLUMN customers_public_yn      varchar(1)  null comment '고객 수 공개 여부',
    ADD COLUMN contracts_public_yn      varchar(1)  null comment '계약 수 공개 여부',
    ADD COLUMN contract_rate_public_yn  varchar(1)  null comment '계약 유지율 공개 여부',
    ADD COLUMN imperfect_rate_public_yn varchar(1)  null comment '불완전 판매율 공개 여부';

UPDATE ecard
SET
    customers_public_yn = (SELECT customers_public_yn FROM specific_info where specific_info.ecard_id = ecard.ecard_id),
    contracts_public_yn = (SELECT contracts_public_yn FROM specific_info where specific_info.ecard_id = ecard.ecard_id),
    contract_rate_public_yn = (SELECT contract_rate_public_yn FROM specific_info where specific_info.ecard_id = ecard.ecard_id),
    imperfect_rate_public_yn = (SELECT imperfect_rate_public_yn FROM specific_info where specific_info.ecard_id = ecard.ecard_id)
WHERE
    customers_public_yn is null or
    contracts_public_yn is null or
    contract_rate_public_yn is null or
    imperfect_rate_public_yn is null;

DROP TABLE specific_info;

ALTER TABLE ecard
    ADD COLUMN branch_number_public_yn varchar(1) not null default 'N' comment '지점 번호 노출 여부',
    ADD COLUMN fax_number_public_yn varchar(1) not null default 'N' comment '팩스 번호 노출 여부',
    ADD COLUMN badge_public_yn varchar(1) not null default 'N' comment '활동 뱃지 노출 여부'
;