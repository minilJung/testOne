# Create Media Tables
ALTER TABLE ecard
    ADD COLUMN profile_file_id varchar(36) after profile_img_url;
ALTER TABLE files
    ADD COLUMN file_id varchar(36) not null after file_seq;

UPDATE files
SET file_id = MID(UUID(), 1, 36)
WHERE file_seq > 0;

UPDATE ecard
SET ecard.profile_file_id = (SELECT file_id
                             FROM files
                             WHERE files.file_seq = ecard.profile_img_url)
WHERE ecard.profile_file_id is null;


DROP TABLE IF EXISTS user_graduation_media;
CREATE TABLE user_graduation_media
(
    media_id      varchar(36) not null,
    graduation_id varchar(36) not null,
    file_id       varchar(36) not null
);
DROP TABLE IF EXISTS user_education_media;
CREATE TABLE user_education_media
(
    media_id     varchar(36) not null,
    education_id varchar(36) not null,
    file_id      varchar(36) not null
);
DROP TABLE IF EXISTS user_rwps_media;
CREATE TABLE user_rwps_media
(
    media_id varchar(36) not null,
    rwps_id  varchar(36) not null,
    file_id  varchar(36) not null
);
