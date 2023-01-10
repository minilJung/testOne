package com.ebc.ecard.controller.majority;

import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.dto.ListBodyDto;
import com.ebc.ecard.application.majority.dto.MajorityFilterDto;
import com.ebc.ecard.application.majority.dto.UserMajorityUpdateDto;
import com.ebc.ecard.application.majority.service.UserMajorityService;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users/{userId}/majorities")
public class UserMajorityController implements CorsDisabledController {

    @Resource
    UserMajorityService majorityService;

    /**
     * @title - 전문분야 조회
     * @desc - 사용자의 전문분야 목록을 조회한다.
     * @author - Jgpark
     * @date - 2022.07.18
     */
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("")
    public ReturnMessage findMajoritiesByUserId(
        @PathVariable("userId") String userId
    ) {
                try {
            return new ReturnMessage(majorityService.findMajorityByUserId(new MajorityFilterDto(userId, null))
            );
        } catch (Exception e) {
            return new ReturnMessage("9999", "전문분야 조회 API 에러", e);
        }
    }

    /**
     * @title - 전문분야 수정
     * @desc - 사용자의 전문분야 정보를 수정합니다.
     * @author - Jgpark
     * @date - 2022.07.18
     */
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("")
    public ReturnMessage updateMajorities(
        @PathVariable("userId") String userId,
        @RequestBody ListBodyDto<UserMajorityUpdateDto> body
    ) {
        try {
            return majorityService.updateMajorities(body.getData());
        } catch (Exception e) {
            return new ReturnMessage("9999", "전문분야 조회 API 에러", e);
        }
    }

}
