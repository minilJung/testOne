# 배지 테이블
CREATE TABLE badge
(
    badge_name        varchar(36),
    badge_image_url varchar(1024),
    badge_dark_image_url varchar(1024),
    badge_m_image_url varchar(1024),
    badge_m_dark_image_url varchar(1024),
    badge_s_image_url varchar(1024),
    badge_s_dark_image_url varchar(1024)
);

INSERT INTO badge values ('ACE_CLUB', '/img/badge/aceclub_logo.svg', '', '/img/badge/aceclub_logo_m.svg', '', '/img/badge/aceclub_logo_s.svg', '')
INSERT INTO badge values ('우수인증설계사', '/img/badge/good_award_logo.svg', '/img/badge/good_award_logo_w.svg', '/img/badge/good_award_logo_m.svg', '/img/badge/good_award_logo_w_m.svg', '/img/badge/good_award_logo_s.svg', '/img/badge/good_award_logo_w_s.svg')