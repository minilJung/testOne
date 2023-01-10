# profile_bg table migrations
# profile_bg to profile_template
DROP TABLE IF EXISTS profile_bg;
DROP TABLE IF EXISTS profile_template;
CREATE TABLE profile_template
(
    template_no        int primary key not null auto_increment,
    profile_img_url    varchar(256),
    name_color         varchar(32) default '#333333',
    phone_number_color varchar(32) default '#333333',
    regular_text_color varchar(32) default '#333333',
    logo_image_type    varchar(1)  default 'G' comment 'logo 이미지 타입(일반, 다크)',
    badge_image_type   varchar(1)  default 'G' comment '배지 이미지 타입(일반, 다크)'
);

insert into profile_template
values (1, '/img/profile/profilebg_default.png', '', '', '', '', '');
insert into profile_template
values (2, '/img/profile/profilebg_bk.png', 'gold', 'gold', '#BBA250', 'D', 'D');
insert into profile_template
values (3, '/img/profile/profilebg_gd.png', '#333333', '#333333', '#333333', 'G', 'G');

# bg to template
ALTER TABLE ecard CHANGE COLUMN profile_bg_no profile_template_no int after profile_file_id;