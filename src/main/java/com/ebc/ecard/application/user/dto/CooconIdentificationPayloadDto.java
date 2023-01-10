package com.ebc.ecard.application.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CooconIdentificationPayloadDto {

    protected String birthdate; // 생년월일
    protected String name; // 이름
    protected String cnrk; // 주민등록번호
    protected String mobileNo; // 핸드폰번호

    protected String ecardId; // 핸드폰번호
}
