package com.ebc.ecard.controller.badge;

import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.dto.ListBodyDto;
import com.ebc.ecard.application.badge.dto.UserBadgeUpdateDto;
import com.ebc.ecard.application.badge.service.UserBadgeService;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users/{userId}/badges")
public class UserBadgeController implements CorsDisabledController {

    @Resource
    UserBadgeService service;

    /**
     * @title - 사용자 뱃지 정보 조회
     * @desc - 사용자 뱃지 정보를 조회 한다.
     * @author - Jgpark
     * @date - 2022.06.28
     */
    @GetMapping("")
    public ReturnMessage findUserBadge(
            @PathVariable("userId") String userId,
            EmployeeCvInfoRequestDto requestDto
    ) {
        try {
            return service.getUserBadgeInfo(userId, requestDto, null);
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }

    /**
     * @title - 사용자 뱃지 공개 여부 수정
     * @desc - 사용자 뱃지 공개 여부를 수정한다.
     * @author - Jgpark
     * @date - 2022.06.28
     */
    @PutMapping("/{badgeId}")
    public ReturnMessage updateBadge(
            @PathVariable("badgeId") String badgeId,
            @RequestBody UserBadgeUpdateDto updateDto
    ) {
        try {
            if (badgeId == null) {
                return new ReturnMessage("400", "잘못된 요청입니다.", false);
            }

            updateDto.setBadgeId(badgeId);
            return service.updateUserBadge(updateDto);
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }

    /**
     * @title - 사용자 뱃지 공개 여부 수정
     * @desc - 사용자 뱃지 공개 여부를 수정한다.
     * @author - Jgpark
     * @date - 2022.06.28
     */
    @PutMapping("")
    public ReturnMessage updateBadges(
            @RequestBody ListBodyDto<UserBadgeUpdateDto> updateDto
    ) {
        try {
            return service.updateUserBadge(updateDto.getData());
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }
}
