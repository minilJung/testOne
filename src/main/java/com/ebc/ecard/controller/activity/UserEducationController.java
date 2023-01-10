package com.ebc.ecard.controller.activity;

import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.domain.activity.UserEducationBean;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.dto.ListBodyDto;
import com.ebc.ecard.application.activity.dto.education.EducationUpdateDto;
import com.ebc.ecard.application.activity.service.UserEducationService;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users/{userId}/educations")
public class UserEducationController implements CorsDisabledController {

    @Resource
    UserEducationService service;

    /**
     * @title - 사용자 교육 이수 내역 추가
     * @desc - 사용자 교육 이수 내역 정보를 추가한다
     * @author - Jgpark
     * @date - 2022.06.29
     */
    @PostMapping("")
    public ReturnMessage addUserEducation(
            @PathVariable("userId") String userId,
            @RequestBody UserEducationBean bean) {
        try {
            bean.setUserId(userId);
            return service.createEducation(bean);
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }

    /**
     * @title - 사용자 교육 이수 내역 조회
     * @desc - 사용자 교육 이수 내역 정보를 조회 한다.
     * @author - Jgpark
     * @date - 2022.06.29
     */
    @GetMapping("")
    public ReturnMessage findUserEducation(@PathVariable("userId") String userId, EmployeeCvInfoRequestDto requestDto) {
        try {
            return new ReturnMessage(service.getUserEducationInfo(new ActivityFilterDto(userId, null)));
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }

    /**
     * @title - 사용자 교육 이수 내역 공개/승인 여부 수정
     * @desc - 사용자 교육 이수 내역 공개/승인 여부를 수정한다.
     * @author - Jgpark
     * @date - 2022.07.06
     */
    @PutMapping("/{educationId}")
    public ReturnMessage updateEducation(
            @PathVariable("userId") String userId,
            @PathVariable("educationId") String educationId,
            @RequestBody EducationUpdateDto updateDto
    ) {
        try {
            if (educationId == null) {
                return new ReturnMessage("400", "잘못된 요청입니다.", false);
            }

            updateDto.setEducationId(educationId);
            return service.updateUserEducation(updateDto);
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }
    /**
     * @title - 사용자 교육 이수 내역 공개/승인 여부 수정
     * @desc - 사용자 교육 이수 내역 공개/승인 여부를 수정한다.
     * @author - Jgpark
     * @date - 2022.07.06
     */
    @PutMapping("")
    public ReturnMessage updateEducations(
        @PathVariable("userId") String userId,
        @RequestBody ListBodyDto<EducationUpdateDto> updateDto,
        HttpServletRequest request
    ) {
        try {
            return service.updateUserEducations(updateDto.getData());
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }
}
