package com.ebc.ecard.controller.user;

import com.ebc.ecard.application.activity.service.UserActivityService;
import com.ebc.ecard.application.badge.service.UserBadgeService;
import com.ebc.ecard.application.career.service.UserCareerService;
import com.ebc.ecard.application.majority.service.UserMajorityService;
import com.ebc.ecard.application.qualification.service.UserQualificationService;
import com.ebc.ecard.application.qualification.service.UserRegistrationService;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users/{userId}")
public class PublicUserController implements CorsDisabledController {

    @Resource
    UserCareerService careerService;

    @Resource
    UserQualificationService qualificationService;

    @Resource
    UserActivityService activityService;

    @Resource
    UserBadgeService badgeService;

    @Resource
    UserMajorityService majorityService;

    @Resource
    UserRegistrationService registrationService;

    /**
     * @title    - 유저 공개 경력 조회
     * @desc    - 유저의 공개된 경력을 조회합니다.
     * @author    - Jgpark
     * @date    - 2022.07.25
     */
    @GetMapping("/public-careers")
    public ReturnMessage getUserPublicCareers(@PathVariable("userId") String userId) {
        try {
            return new ReturnMessage(careerService.findCareerInsightByUserId(userId, "Y"));
        } catch (Exception e) {
            return new ReturnMessage("9999", "공개 경력 조회 API 에러", e);
        }
    }

    /**
     * @title    - 유저 공개 자격증 조회
     * @desc    - 유저의 공개된 자격증을 조회합니다.
     * @author    - Jgpark
     * @date    - 2022.07.26
     */
    @GetMapping("/public-qualifications")
    public ReturnMessage getUserPublicQualifications(@PathVariable("userId") String userId) {
        try {
            return new ReturnMessage(qualificationService.getQualificationInsightByUserId(userId, "Y"));
        } catch (Exception e) {
            return new ReturnMessage("9999", "공개 자격증 조회 API 에러", e);
        }
    }

    @GetMapping("/public-activities")
    public ReturnMessage getPublicActivities(
        @PathVariable("userId") String userId,
        EmployeeCvInfoRequestDto params
    ) {
        try {
            return activityService.getUserActivityInsightByUserId(new ActivityFilterDto(userId, "Y"));
        } catch(Exception e) {
            return new ReturnMessage("9999", "사용자 수상/교욱 내역 조회 API 에러", e);
        }
    }

    /**
     * @title - 사용자 뱃지 정보 조회
     * @desc - 사용자 뱃지 정보를 조회 한다.
     * @author - Jgpark
     * @date - 2022.07.18
     */
    @GetMapping("/public-badges")
    public ReturnMessage getPublicBadges(
        @PathVariable("userId") String userId,
        EmployeeCvInfoRequestDto requestDto
    ) {
        try {
            return badgeService.getUserBadgeInfo(userId, requestDto, "Y");
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }

    /**
     * @title - 공개 전문분야 조회
     * @desc - 사용자가 공개한 전문분야 목록을 조회한다.
     * @author - Jgpark
     * @date - 2022.07.18
     */
    @GetMapping("/public-majorities")
    public ReturnMessage findPublicMajoritiesByUserId(@PathVariable("userId") String userId) {
        try {
            EmployeeCvInfoRequestDto requestDto = new EmployeeCvInfoRequestDto();

            return new ReturnMessage(majorityService.getMajorityInsightByUserId(userId, requestDto, "Y"));
        } catch (Exception e) {
            return new ReturnMessage("9999", "전문분야 조회 API 에러", e);
        }
    }

    /**
     * @title - 보험모집종사자 등록증 조회
     * @desc - 사용자의 보험모집종사자 등록증을 조회한다.
     * @author - wnguds
     * @date - 2022.10.07
     */
    @GetMapping("/public-registrations")
    public ReturnMessage findPublicRegistrationByUserId(@PathVariable("userId") String userId) {
        try {
            return new ReturnMessage(registrationService.findPublicRegistrationByUserId(userId, ""));
        } catch (Exception e) {
            return new ReturnMessage("9999", "보험모집종사자 등록증 조회 API 에러", e);
        }
    }
}
