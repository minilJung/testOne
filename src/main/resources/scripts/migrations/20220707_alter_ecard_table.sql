# ECard info migrations
ALTER TABLE ecard ADD COLUMN employee_no varchar(36) comment '사원 번호' after user_id;

UPDATE ecard
SET employee_no = (
    SELECT employee_no
    FROM company_user cu
    WHERE cu.company_id = ecard.company_id
      AND cu.user_id = ecard.user_id
    LIMIT 1
);

ALTER TABLE ecard DROP COLUMN position;
ALTER TABLE ecard DROP COLUMN phone_no;

ALTER TABLE ecard ADD COLUMN fp_id varchar(36) comment 'fp 아이디' after department;
ALTER TABLE ecard ADD COLUMN email varchar(64) comment '사내 이메일' after fp_id;
ALTER TABLE ecard ADD COLUMN branch_number varchar(16) comment '지점 번호' after email;
ALTER TABLE ecard ADD COLUMN fax_number varchar(36) comment '팩스 번호' after branch_number;
ALTER TABLE ecard ADD COLUMN on_duty varchar(2) comment '재직여부(Y/N)' after last_updated_at;

# update default values
UPDATE ecard
SET
    email = (SELECT email FROM user WHERE user_id = ecard.user_id),
    fax_number = (SELECT fax_no FROM user WHERE user_id = ecard.user_id),
    on_duty = 'Y'
;