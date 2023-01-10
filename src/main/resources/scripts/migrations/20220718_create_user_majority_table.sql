# USER Majority Table
CREATE TABLE user_majority
(
    majority_id   varchar(36) not null,
    user_id       varchar(20) not null,
    majority_name varchar(64) not null,
    public_yn     varchar(1)  not null
);
ALTER TABLE user_majority
    ADD CONSTRAINT `MAJORITY_UNIQUE_INDEX` UNIQUE ('user_id', 'majority_name');
