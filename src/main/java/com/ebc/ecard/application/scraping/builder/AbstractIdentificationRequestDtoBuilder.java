package com.ebc.ecard.application.scraping.builder;

import java.text.SimpleDateFormat;
import java.util.*;

import com.ebc.ecard.application.user.dto.CooconIdentificationPayloadDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
abstract public class AbstractIdentificationRequestDtoBuilder {

    protected String callbackUrl = "";
    protected String redirectUrl = "";

    public AbstractIdentificationRequestDtoBuilder(String callbackUrl, String redirectUrl) {
        this.callbackUrl = callbackUrl;
        this.redirectUrl = redirectUrl;
    }


    abstract protected void createInputListItems(List<Map<String, Object>> inputList, CooconIdentificationPayloadDto cooconIdentificationPayloadDto, String scrapingId);

    public Map<String, Object> build(CooconIdentificationPayloadDto cooconIdentificationPayloadDto, String scrapingId) {
        String datetimeString = getCurrentDatetime();

        // 공통 부분
        Map<String, Object> common = createCommonPayload();

        // 인풋 목록
        List<Map<String, Object>> inputList = new ArrayList<>();
        createInputListItems(inputList, cooconIdentificationPayloadDto, scrapingId);

        Map<String, Object> body = new HashMap<>();
        body.put("Common", common);
        body.put("InputList", inputList);

        return body;
    }

    protected Map<String, Object> createCommonPayload() {
        SimpleDateFormat dateformat1 = new SimpleDateFormat ( "yyyyMMdd");
        SimpleDateFormat dateformat2 = new SimpleDateFormat ( "HHmmss");
        Date date = new Date();

        int iTranNo = (int)(Math.random() * 100000);
        String tranNo = String.format("%07d", iTranNo); //7자리 포맷

        Map<String, Object> common = new HashMap<>();
        common.put("Date", dateformat1.format(date));
        common.put("Time", dateformat2.format(date));
        common.put("TranNo", tranNo);
        common.put("CallbackUrl", getCallbackUrl());
        //common.put("RedirectUrl", getRedirectUrl());

        return common;
    }


    protected String getCurrentDatetime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return format.format(date);
    }

}
