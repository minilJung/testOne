package com.ebc.ecard.application.scraping.builder;

import com.ebc.ecard.application.user.dto.CooconIdentificationPayloadDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QNETLoginRequestDtoBuilder extends AbstractIdentificationRequestDtoBuilder {

    public QNETLoginRequestDtoBuilder(String callbackUrl, String redirectUrl) {
        super(callbackUrl, redirectUrl);
    }

    protected void createInputListItems(List<Map<String, Object>> inputList, CooconIdentificationPayloadDto cooconIdentificationPayloadDto, String scrapingId) {
        Map<String, Object> inputLoginItem = new HashMap<>(); //로그인 데이터
        Map<String, Object> loginItem = new HashMap<>();

        Map<String, Object> inputQualificationItem = new HashMap<>(); //자격증취득조회
        Map<String, Object> qualificationItem = new HashMap<>();
        qualificationItem.put("scrapingId", scrapingId);

        loginItem.put("생년월일", cooconIdentificationPayloadDto.getBirthdate());
        loginItem.put("성명", cooconIdentificationPayloadDto.getName());
        loginItem.put("로그인방식", "CONV_CERT");
        loginItem.put("핸드폰번호", cooconIdentificationPayloadDto.getMobileNo());

        inputLoginItem.put("Module", "Qnet");
        inputLoginItem.put("Job", "로그인");
        inputLoginItem.put("Class", "개인정보조회");
        inputLoginItem.put("Input", loginItem);

        inputQualificationItem.put("Module", "Qnet");
        inputQualificationItem.put("Job", "자격증취득조회");
        inputQualificationItem.put("Class", "개인정보조회");
        inputQualificationItem.put("Input", qualificationItem);

        inputList.add(inputLoginItem);
        inputList.add(inputQualificationItem);
    }
}
