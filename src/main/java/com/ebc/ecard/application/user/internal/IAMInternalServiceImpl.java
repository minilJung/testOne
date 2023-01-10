package com.ebc.ecard.application.user.internal;

import com.ebc.ecard.application.user.dto.IAMRequestDto;
import com.ebc.ecard.application.user.dto.IAMResponseDto;
import com.ebc.ecard.util.request.ECardHttpRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IAMInternalServiceImpl implements IAMInternalService {

    @Value("${ecard.services.iam.url}")
    private String IAM_URL;

    @Value("${ecard.services.iam.x-iam-service-code}")
    private String X_IAM_SERVICE_CODE;

    @Override
    public IAMResponseDto saveIAM(IAMRequestDto params) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-iam-service-code", X_IAM_SERVICE_CODE);

        return ECardHttpRequest.Builder.build("https://" + IAM_URL)
            .post("/api/users/integration")
            .payload(params)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .executeAndGetBodyAs(IAMResponseDto.class);
    }

}