# openAPI 접속 기록 테이블
DROP TABLE IF EXISTS open_api_log;
CREATE TABLE open_api_log
(
    no int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    api_name varchar(50) comment 'customId',
    fp_uniq_no varchar(30) not null comment '접근한 fpId',
    access_at datetime comment '접근 시간'
)

# openAPI List
DROP TABLE IF EXISTS open_api;
CREATE TABLE open_api
(
    api_name varchar(30) primary key comment 'openAPI 이름',
    custom_id varchar(80) not null comment 'openAPI CUSTOM_ID'
)

INSERT INTO openAPI VALUES ('orangeapp-stg', d2d23c3d-80dd-473b-900e-46abcd21313e);
INSERT INTO openAPI VALUES ('orangeapp-dev', c5acd2f2-99f7-417a-a2af-a7f563e6520e);
INSERT INTO openAPI VALUES ('pointmall-dev', 1634aa1d-8f0b-4f99-bf65-c8f46c013d2f);
INSERT INTO openAPI VALUES ('pointmall-stg', 2035e68c-650b-4bec-a9db-81515810cfa8);
INSERT INTO openAPI VALUES ('plusapp-stg', 31415599-2cf8-40c0-9378-ccff75e2f777);
INSERT INTO openAPI VALUES ('plusapp-dev', e1033aba-ed43-4e75-8047-280e1d8591be);
INSERT INTO openAPI VALUES ('xpress-stg', 54a76331-ee15-4737-9885-62b17c8726ff);
INSERT INTO openAPI VALUES ('xpress-dev', a5772cc6-b02f-42b5-a50b-96af54c4d783);