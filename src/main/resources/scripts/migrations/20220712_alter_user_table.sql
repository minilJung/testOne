# Delete company user
DROP TABLE company_user;

# USER Migration for after checkplus
ALTER TABLE user
    ADD COLUMN birthdate date after name;
ALTER TABLE user
    ADD COLUMN di varchar(256) after birthdate;

UPDATE ecard
SET ecard.fax_number = (SELECT fax_no
                        FROM user
                        where user.user_id = ecard.user_id)
where fax_number is null;

ALTER TABLE user
    DROP COLUMN fax_no;