package com.ebc.ecard.application.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserExistenceRequestDto {

    protected String fpUniqNo;

    protected String fpNmcdDvsnCode; // 고객 조회 타입

    protected String fpNmcdSendHistUuid; // 플러스앱 전자명함 전송 KEY값

    protected String custId;

}