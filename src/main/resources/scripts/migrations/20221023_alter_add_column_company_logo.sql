# profile_template TABLE company_logo, company_m_logo column 추가
ALTER TABLE profile_template ADD company_logo varchar(128) AFTER logo_image_type;
ALTER TABLE profile_template ADD company_m_logo varchar(128) AFTER company_logo;

UPDATE profile_template SET company_logo = '/img/company/company_logo.svg' WHERE template_no = 1;
UPDATE profile_template SET company_logo = '/img/company/company_logo_w.svg' WHERE template_no = 2;
UPDATE profile_template SET company_logo = '/img/company/company_logo.svg' WHERE template_no = 3;

UPDATE profile_template SET company_m_logo = '/img/company/company_logo_m.svg' WHERE template_no = 1;
UPDATE profile_template SET company_m_logo = '/img/company/company_logo_m_w.svg' WHERE template_no = 2;
UPDATE profile_template SET company_m_logo = '/img/company/company_logo_m.svg' WHERE template_no = 3;
