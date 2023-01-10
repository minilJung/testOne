package com.ebc.ecard.controller.activity;

import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.dto.ListBodyDto;
import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityUpdateRequestDto;
import com.ebc.ecard.application.activity.service.UserActivityService;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users/{userId}/activities")
public class UserActivityController implements CorsDisabledController {

    @Resource
    UserActivityService service;

    @GetMapping("")
    public ReturnMessage getUserActivityInfo(
        @PathVariable("userId") String userId
    ) {
        try {
            return service.getUserActivityInfo(new ActivityFilterDto(userId, null));
        } catch(Exception e) {
            return new ReturnMessage("9999", "사용자 수상/교욱 내역 조회 API 에러", e);
        }
    }

    /**
     * @return - 결과 코드 (9999: 에러)
     * @title - 교육/수상 정보 수정
     * @desc - 사용자의 교육/수상 정보를 통합 수정합니다.
     * @author - Parkjg20
     * @date - 2022.08.17
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("")
    public ReturnMessage updateActivities(
        @RequestBody ListBodyDto<ActivityUpdateRequestDto> updateDto
    ) {
        try {
            return service.updateActivities(updateDto.getData());
        } catch (Exception e) {
            return new ReturnMessage("9999", "학력 정보 공개 여부 수정 API 에러", e);
        }
    }

    /**
     * @return - 결과 코드 (9999: 에러)
     * @title - 교육/수상 정보 삭제
     * @desc - 사용자의 교육/수상 정보를 통합 삭제합니다.
     * @author - Parkjg20
     * @date - 2022.08.17
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("")
    public ReturnMessage deleteActivities(
        @RequestBody ListBodyDto<ActivityDeleteRequestDto> deleteDto
    ) {
        try {
            return service.deleteActivities(deleteDto.getData());
        } catch (Exception e) {
            return new ReturnMessage("9999", "학력 정보 공개 여부 삭제 API 에러", e);
        }
    }
}
