package com.ebc.ecard.application.scraping.builder;

import java.text.SimpleDateFormat;
import java.util.*;

import com.ebc.ecard.application.user.dto.CooconIdentificationPayloadDto;
import lombok.Getter;

@Getter
public class NHISLoginRequestDtoBuilder extends AbstractIdentificationRequestDtoBuilder {

    protected String idNumber;

    public NHISLoginRequestDtoBuilder(String callbackUrl, String redirectUrl) {
        super(callbackUrl, redirectUrl);
    }

    protected void createInputListItems(List<Map<String, Object>> inputList, CooconIdentificationPayloadDto cooconIdentificationPayloadDto, String scrapingId) {
        Date now = new Date();
        String endDate = new SimpleDateFormat("yyyyMM").format(now);

        Calendar year = Calendar.getInstance();
        year.add(Calendar.YEAR, - 1);
        String startDate = new SimpleDateFormat("yyyyMM").format(year.getTime());

        Map<String, Object> inputLoginItem = new HashMap<>(); //로그인 데이터
        Map<String, Object> loginItem = new HashMap<>();

        Map<String, Object> inputQualificationItem = new HashMap<>(); //자격득실확인서 데이터
        Map<String, Object> qualificationItem = new HashMap<>();
        qualificationItem.put("scrapingId", scrapingId);

        Map<String, Object> inputPayItem = new HashMap<>(); //납부확인서 데이터
        Map<String, Object> payItem = new HashMap<>();

        loginItem.put("생년월일", cooconIdentificationPayloadDto.getBirthdate());
        loginItem.put("성명", cooconIdentificationPayloadDto.getName());
        loginItem.put("로그인방식", "CONV_CERT");
        loginItem.put("핸드폰번호", cooconIdentificationPayloadDto.getMobileNo());

        inputLoginItem.put("Module", "NHIS");
        inputLoginItem.put("Job", "로그인");
        inputLoginItem.put("Class", "민원신청조회");
        inputLoginItem.put("Input", loginItem);

        qualificationItem.put("팩스전송여부", "N");
        qualificationItem.put("주민등록번호표시여부", "Y");

        inputQualificationItem.put("Module", "NHIS");
        inputQualificationItem.put("Job", "자격득실확인서");
        inputQualificationItem.put("Class", "민원신청조회");
        inputQualificationItem.put("Input", qualificationItem);

//        payItem.put("팩스전송여부", "");
//        payItem.put("조회종료년월", endDate);
//        payItem.put("조회시작년월", startDate);
//
//        inputPayItem.put("Module", "NHIS");
//        inputPayItem.put("Job", "납부확인서");
//        inputPayItem.put("Class", "민원신청조회");
//        inputPayItem.put("Input", payItem);
//        inputList.add(inputPayItem);

        inputList.add(inputLoginItem);
        inputList.add(inputQualificationItem);
    }
}
