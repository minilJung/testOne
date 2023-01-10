package com.ebc.ecard.controller.qualification;

import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.dto.ListBodyDto;
import com.ebc.ecard.application.qualification.dto.QualificationUpdateDto;
import com.ebc.ecard.application.qualification.dto.UserQualificationUpdateDto;
import com.ebc.ecard.application.qualification.service.UserQualificationService;
import com.ebc.ecard.util.ReturnMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/users/{userId}/qualifications")
public class UserQualificationController implements CorsDisabledController {

    @Resource
    ObjectMapper objectMapper;

    @Resource
    UserQualificationService service;

    /**
     * @title    - Qnet 자격증 조회
     * @desc    - 간편인증 정보를 이용해 Qnet에서 취득자격을 조회하고 자격증 취득 내역을 추가합니다.
     * @author    - Jgpark
     * @date    - 2022.07.28
     */
    @PostMapping("/qnet/callback")
    public ReturnMessage getUserQualificationsFromQNet(
        @PathVariable("userId") String userId,
        @RequestBody QualificationUpdateDto payload
    ) {
        try {
            return new ReturnMessage(service.addQualificationsFromResponseText(payload, userId));
        } catch (Exception e) {
            log.info("API, Failure alert coocon qnet callback: {} {}", e.getMessage(), e);
            return new ReturnMessage("9999", "공개 자격증 조회 API 에러", e);
        }
    }

    /**
     * @title    - 자격증 목록 조회
     * @desc    - 유저의 자격증을 조회합니다.
     * @author    - Jgpark
     * @date    - 2022.07.26
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("")
    public ReturnMessage getUserCareers(@PathVariable("userId") String userId) {
        try {
            return new ReturnMessage(service.findQualificationByUserId(userId));
        } catch (Exception e) {
            return new ReturnMessage("9999", "자격증 조회 API 에러", e);
        }
    }

    /**
     * @title    - 자격증 공개여부 수정
     * @desc    - 유저의 자격증 공개 여부를 수정합니다.
     * @author    - Jgpark
     * @date    - 2022.07.26
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{qualificationId}")
    public ReturnMessage updateUserCareer(
        @PathVariable("userId") String userId,
        @PathVariable("qualificationId") String qualificationId,
        @RequestBody UserQualificationUpdateDto updateDto
    ) {
        try {
            updateDto.setQualificationId(qualificationId);
            return new ReturnMessage(service.updateQualification(updateDto));
        } catch (Exception e) {
            return new ReturnMessage("9999", "자격증 수정 API 에러", e);
        }
    }

    /**
     * @title    - 자격증 공개여부 수정
     * @desc    - 유저의 자격증 공개 여부를 수정합니다.
     * @author    - Jgpark
     * @date    - 2022.07.26
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("")
    public ReturnMessage updateUserCareers(
        @PathVariable("userId") String userId,
        @RequestBody ListBodyDto<UserQualificationUpdateDto> updateDto
    ) {
        try {
            return new ReturnMessage(service.updateQualification(updateDto.getData()));
        } catch (Exception e) {
            return new ReturnMessage("9999", "자격증 수정 API 에러", e);
        }
    }

    @GetMapping("/qnet/scraping")
    public ReturnMessage getUserQualificaitonScrapingStatus(
        @PathVariable("userId") String userId,
        @RequestParam("scrapingId") String scrapingId
    ) {
        try {
            return new ReturnMessage(service.getQnetScrapingStatus(scrapingId));
        } catch (Exception e) {
            return new ReturnMessage("9999", "자격증 스크래핑 내역 조회 API 에러", e);
        }
    }
}
