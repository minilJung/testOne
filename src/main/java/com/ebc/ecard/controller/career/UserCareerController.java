package com.ebc.ecard.controller.career;

import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.dto.ListBodyDto;
import com.ebc.ecard.application.career.dto.CareerUpdateDto;
import com.ebc.ecard.application.career.dto.UserCareerUpdateDto;
import com.ebc.ecard.application.career.service.UserCareerService;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/api/users/{userId}")
public class UserCareerController implements CorsDisabledController {

    @Resource
    ObjectMapper objectMapper;

    @Resource
    XeCommon common;

    @Resource
    UserCareerService service;

    /**
     * @title    - NHIS 자격증 조회
     * @desc    - 간편인증 정보를 이용해 건강보험자격득실 내역을 조회하고 경력을 추가합니다.
     * @author    - Jgpark
     * @date    - 2022.07.28
     */
    @PostMapping("/nhis/callback")
    public ReturnMessage getUserCareersFromQNet(
        @PathVariable("userId") String userId,
        @RequestBody CareerUpdateDto payload
    ) {
        try {
            return new ReturnMessage(service.addCareersFromResponseText(payload, userId));
        } catch (Exception e) {
            log.info("API, Failure alert coocon nhis callback: {} {}", e.getMessage(), e);
            return new ReturnMessage("9999", "공개 자격증 조회 API 에러", e);
        }
    }

    /**
     * @title    - 경력 목록 조회
     * @desc    - 유저의 경력을 조회합니다.
     * @author    - Jgpark
     * @date    - 2022.07.25
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/careers")
    public ReturnMessage getUserCareers(@PathVariable("userId") String userId) {
        try {
            return new ReturnMessage(service.findCareerByUserId(userId, null));
        } catch (Exception e) {
            return new ReturnMessage("9999", "경력 조회 API 에러", e);
        }
    }

    /**
     * @title    - 경력 공개여부 수정
     * @desc    - 유저의 경력 공개 여부를 수정합니다.
     * @author    - Jgpark
     * @date    - 2022.07.25
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/careers/{careerId}")
    public ReturnMessage updateUserCareer(
        @PathVariable("userId") String userId,
        @PathVariable("careerId") String careerId,
        @RequestBody UserCareerUpdateDto updateDto
    ) {
        try {
            updateDto.setCareerId(careerId);
            return new ReturnMessage(service.updateCareer(updateDto));
        } catch (Exception e) {
            return new ReturnMessage("9999", "경력 수정 API 에러", e);
        }
    }

    /**
     * @title    - 경력 공개여부 수정
     * @desc    - 유저의 경력 공개 여부를 수정합니다.
     * @author    - Jgpark
     * @date    - 2022.07.25
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/careers")
    public ReturnMessage updateUserCareers(
        @PathVariable("userId") String userId,
        @RequestBody ListBodyDto<UserCareerUpdateDto> updateDto
    ) {
        try {
            return new ReturnMessage(service.updateCareer(updateDto.getData()));
        } catch (Exception e) {
            return new ReturnMessage("9999", "경력 수정 API 에러", e);
        }
    }

    @GetMapping("/nhis/scraping")
    public ReturnMessage getNhisScrapingStatus(
        @PathVariable("userId") String userId,
        @RequestParam("scrapingId") String scrapingId
    ) {
        try {
            return new ReturnMessage(service.getNhisScrapingStatus(scrapingId));
        } catch (Exception e) {
            return new ReturnMessage("9999", "경력 스크래핑 내역 조회 API 에러", e);
        }
    }
}
