package com.ebc.ecard.controller.user;

import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.agreement.dto.UserAgreementAddDto;
import com.ebc.ecard.application.agreement.service.UserAgreementService;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users/{userId}/agreements")
public class UserAgreementController implements CorsDisabledController {

    @Resource
    UserAgreementService agreementService;

    /**
     * @title - 약관 동의
     * @desc - 유저가 약관 동의하였을 떄 해당 내용을 저장한다.
     * @author - Jgpark
     * @date - 2022.07.04
     */
    @PostMapping("")
    public ReturnMessage addAgreement(
            @PathVariable("userId") String userId,
            @RequestBody UserAgreementAddDto addDto
    ) {
        addDto.setUserId(userId);
        if (addDto.getAgreementNames() == null || addDto.getAgreementNames().length == 0) {
            return new ReturnMessage("400", "agreementNames is required", addDto);
        }

        try {
            return agreementService.addAgreement(addDto);
        } catch (Exception e) {
            return new ReturnMessage("9999", "약관 동의 내역 저장 API 에러", e);
        }
    }

    /**
     * @title - 동의 내역 조회
     * @desc - 사용자의 약관 동의 내역을 조회한다.
     * @author - Jgpark
     * @date - 2022.07.04
     */
    @GetMapping("")
    public ReturnMessage findAgreementByUserId(@PathVariable("userId") String userId) {
        try {
            return new ReturnMessage(agreementService.findAgreementByUserId(userId));
        } catch (Exception e) {
            return new ReturnMessage("9999", "약관 동의여부 조회 API 에러", e);
        }
    }

}
