package com.ebc.ecard.application.qualification.dto;

import com.ebc.ecard.application.dto.RequestDtoInterface;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * @desc Qnet API 호출 Dto
 * @author jgpark
 * @date 2022.07.06
 */
@Getter
public class QNetQualificationDto implements RequestDtoInterface<String, String> {

    protected final String loginMethod = "KAKAO";
    protected String name;
    protected String mobileNo;
    protected String birthdate;

    public QNetQualificationDto(String name, String mobileNo, String birthdate) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.birthdate = birthdate;
    }

    @Override
    public Map<String, String> convertToMap() {
        Map<String,  String> map = new HashMap<>();
        map.put("로그인방식", loginMethod);
        map.put("성명", name);
        map.put("핸드폰번호", mobileNo);
        map.put("생년월일", birthdate);

        return map;
    }
}
