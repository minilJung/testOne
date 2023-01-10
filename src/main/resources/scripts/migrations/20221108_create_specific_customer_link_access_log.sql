
# 특정 고객 URL 접근 로그
DROP TABLE IF EXISTS customer_access_log;
CREATE TABLE customer_access_log(
    log_id int primary key auto_increment,
    ecard_id varchar(10) not null,
    fp_id varchar(36) not null,
    cust_id varchar(10) not null,
    uri varchar(256) not null,
    accessed_at datetime not null
);