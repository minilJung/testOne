package com.ebc.ecard.domain.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OpenApiAccessLogBean {
    protected String apiName;
    protected String fpUniqNo;
    protected Date accessAt;
    protected boolean existenceYn;

    public OpenApiAccessLogBean(String apiName, String fpUniqNo) {
        this.apiName = apiName;
        this.fpUniqNo = fpUniqNo;
    }

    public OpenApiAccessLogBean(String apiName, boolean existenceYn) {
        this.apiName = apiName;
        this.existenceYn = existenceYn;
    }
}