package com.ebc.ecard.application.checkplus;

import com.ebc.ecard.application.checkplus.dto.CheckplusDataDto;
import com.ebc.ecard.application.checkplus.dto.CheckplusTokenRequestDataBodyDto;
import com.ebc.ecard.application.checkplus.dto.CheckplusTokenRequestDataHeaderDto;
import com.ebc.ecard.application.checkplus.dto.CheckplusTokenResponseDataBodyDto;
import com.ebc.ecard.application.checkplus.dto.CheckplusTokenResponseDataHeaderDto;
import com.ebc.ecard.application.checkplus.dto.EcardUiCheckplusDto;
import com.ebc.ecard.util.HttpRequestUtil;
import com.ebc.ecard.util.XeCommon;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import javax.annotation.Resource;

import io.jsonwebtoken.impl.Base64Codec;

@Service
public class CheckplusServiceImpl implements CheckplusService {

    @Value("${checkplus.api.server-url}")
    protected String API_SERVER_URL;

    @Value("${checkplus.access-token}")
    protected String ACCESS_TOKEN;

    @Value("${checkplus.client.id}")
    protected String CLIENT_ID;

    @Value("${checkplus.client.secret}")
    protected String CLIENT_SECRET;;

    @Value("${checkplus.product.id}")
    protected String PRODUCT_ID;

    @Resource
    XeCommon common;

    @Resource
    ObjectMapper objectMapper;

    public EcardUiCheckplusDto getToken() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("ProductId", PRODUCT_ID);

        Date now = new Date();
        String bearerToken = Base64Util.encode(ACCESS_TOKEN + ":" + (now.getTime() / 1000) + ":" + CLIENT_ID);
        headers.set("Authorization", "bearer " + bearerToken);

        CheckplusTokenRequestDataBodyDto requestDataBody = new CheckplusTokenRequestDataBodyDto(
            (new SimpleDateFormat("YYYYMMddHHmmss")).format(now),
            common.getRandomString(false, 30)
        );

        HttpRequestUtil<
            CheckplusDataDto<CheckplusTokenRequestDataHeaderDto, CheckplusTokenRequestDataBodyDto>,
            CheckplusDataDto<CheckplusTokenResponseDataHeaderDto, CheckplusTokenResponseDataBodyDto>
            > request = new HttpRequestUtil<>(
            API_SERVER_URL + "/digital/niceid/api/v1.0/common/crypto/token",
            new CheckplusDataDto<CheckplusTokenRequestDataHeaderDto, CheckplusTokenRequestDataBodyDto>(
                new CheckplusTokenRequestDataHeaderDto(),
                requestDataBody
            ),
            HttpMethod.POST,
            headers, Collections.singletonList(MediaType.APPLICATION_JSON)
        );

        ResponseEntity<CheckplusDataDto<
            CheckplusTokenResponseDataHeaderDto,
            CheckplusTokenResponseDataBodyDto
            >> response = request.execute();

        CheckplusDataDto<CheckplusTokenResponseDataHeaderDto, CheckplusTokenResponseDataBodyDto> responseBody =
            objectMapper.convertValue(response.getBody(), CheckplusDataDto.class);

        assert responseBody != null;
        CheckplusTokenResponseDataBodyDto responseDataBody =
            objectMapper.convertValue(responseBody.getDataBody(), CheckplusTokenResponseDataBodyDto.class);

        return generateSymmetricKey(requestDataBody, responseDataBody);
    }

    protected EcardUiCheckplusDto generateSymmetricKey(
        CheckplusTokenRequestDataBodyDto requestDataBody,
        CheckplusTokenResponseDataBodyDto responseDataBody
    ) throws NoSuchAlgorithmException {
        if (responseDataBody.getToken_val() == null) {
            return null;
        }

        String value = requestDataBody.getRequestDatetime().trim() + requestDataBody.getRequestNo().trim() + responseDataBody.getToken_val().trim();
        MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
        md.update(value.getBytes());

        String base64EncodedValue = Base64Codec.BASE64.encode(md.digest());
        String key = new String(Arrays.copyOfRange(base64EncodedValue.getBytes(), 0, 16));
        String iv = new String(Arrays.copyOfRange(
            base64EncodedValue.getBytes(),
            base64EncodedValue.length() - 16,
            base64EncodedValue.length())
        );

        String hmacKey = new String(Arrays.copyOfRange(base64EncodedValue.getBytes(), 0, 32));

        return new EcardUiCheckplusDto(responseDataBody, key, iv, hmacKey);
    }

}
