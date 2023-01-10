package com.ebc.ecard.controller.registration;

import com.ebc.ecard.application.registration.dto.UserRegistrationUpdateDto;
import com.ebc.ecard.application.qualification.service.UserRegistrationService;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users/{userId}/registrations")
public class UserRegistrationController {

    @Resource
    UserRegistrationService service;

    /**
     * @title    - 등록증 조회
     * @desc    - 유저의 등록증을 조회합니다.
     * @author    - wnguds
     * @date    - 2022.10.06
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("")
    public ReturnMessage getUserRegistration(@PathVariable("userId") String userId) {
        try {
            return new ReturnMessage(service.findRegistrationByUserId(userId));
        } catch (Exception e) {
            return new ReturnMessage("9999", "등록증 조회 API 에러", e);
        }
    }

    /**
     * @title    - 등록증 수정
     * @desc    - 유저의 등록증을 수정합니다.
     * @author    - wnguds
     * @date    - 2022.10.06
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("")
    public ReturnMessage updateRegistrations(
            @PathVariable("userId") String userId,
            @RequestBody UserRegistrationUpdateDto updateDto
    ) {
        try {
            updateDto.setUserId(userId);
            return new ReturnMessage(service.updateRegistration(updateDto));
        } catch (Exception e) {
            return new ReturnMessage("9999", "등록증 수정 API 에러", e);
        }
    }

    /**
     * @title    - 등록증 삭제
     * @desc    - 유저의 등록증을 삭제합니다.
     * @author    - wnguds
     * @date    - 2022.10.07
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("")
    public ReturnMessage deleteRegistrations(
            @PathVariable("userId") String userId
            ) {
        try {
            return new ReturnMessage(service.deleteRegistration(userId));
        } catch (Exception e) {
            return new ReturnMessage("9999", "등록증 삭제 API 에러", e);
        }
    }

    /**
     * @title    - 등록증 조회
     * @desc    - 유저의 등록증 이미지를 조회합니다.
     * @author    - wnguds
     * @date    - 2022.11.10
     */
    @GetMapping("/registrations-image")
    public String getRegistrationsImage(
            HttpServletResponse response,
            @PathVariable("userId") String userId
    ) {
        try {
            return service.getUserRegistrationImg(userId);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
