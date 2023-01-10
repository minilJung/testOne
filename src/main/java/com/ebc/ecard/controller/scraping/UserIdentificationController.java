package com.ebc.ecard.controller.scraping;

import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.scraping.service.UserIdentificationService;
import com.ebc.ecard.domain.scraping.value.ScrapingType;
import com.ebc.ecard.property.ECardUiPropertyBean;
import com.ebc.ecard.security.token.EbcUserAuthenticationToken;
import com.ebc.ecard.util.ReturnMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import jdk.internal.org.jline.utils.Log;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/identification")
public class UserIdentificationController implements CorsDisabledController {

    @Resource
    protected ECardUiPropertyBean uiProperty;

    @Resource
    ObjectMapper objectMapper;

    @Resource
    UserIdentificationService userIdentificationService;

    @GetMapping("/popup/callback")
    public String popupCallback(@RequestBody Object callback) throws JsonProcessingException {
        String object = objectMapper.writeValueAsString(callback);
       // Log.info("this is callback of identification", objectMapper.writeValueAsString(object));

        return object;
    }

    @GetMapping("/qnet/form-data")
    public ReturnMessage getQnetRequestFormData(
        Authentication authentication
    ) {
        Map<String, Object> detail = (Map) authentication.getDetails();
        String userId = (String) detail.get("userId");
        if (userId == null) {
            return new ReturnMessage("403", "올바르지 않은 접근입니다.", false);
        }

        try {
            return new ReturnMessage(
                userIdentificationService.buildScrapingRequestDto(uiProperty.getUrlWithProtocol(), userId, ScrapingType.LICENSE)
            );
        } catch(Exception e) {
            e.printStackTrace();
            return new ReturnMessage("9999", "NHIS Request Form data 생성 실패", e);
        }
    }

    @GetMapping("/nhis/form-data")
    public ReturnMessage getNHISRequestFormData(
        Authentication authentication
    ) {
        Map<String, Object> detail = (Map) authentication.getDetails();
        String userId = (String) detail.get("userId");
        if (userId == null) {
            return new ReturnMessage("403", "올바르지 않은 접근입니다.", false);
        }

        try {
            return new ReturnMessage(
                userIdentificationService.buildScrapingRequestDto(uiProperty.getUrlWithProtocol(), userId, ScrapingType.CAREER)
            );
        } catch(Exception e) {
            e.printStackTrace();
            return new ReturnMessage("9999", "NHIS Request Form data 생성 실패", e);
        }
    }
}
