package com.ebc.ecard.controller.activity;

import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.domain.activity.UserRwpsBean;
import com.ebc.ecard.application.dto.ListBodyDto;
import com.ebc.ecard.application.activity.dto.rwps.RwpsUpdateDto;
import com.ebc.ecard.application.activity.service.UserRwpsService;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users/{userId}/rwps")
public class UserRwpsController implements CorsDisabledController {

    @Resource
    UserRwpsService service;

    /**
     * @title - 사용자 교육 이수 내역 추가
     * @desc - 사용자 교육 이수 내역 정보를 추가한다
     * @author - Jgpark
     * @date - 2022.06.29
     */
    @PostMapping("")
    public ReturnMessage addUserRwps(
            @PathVariable("userId") String userId,
            @RequestBody UserRwpsBean bean
    ) {
        try {
            bean.setUserId(userId);
            return service.createUserRwps(bean);
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }

    /**
     * @param -
     * @return - 결과 코드
     * @title - 사용자 수상 내역 조회
     * @desc - 사용자 수상 내역 정보를 조회 한다.
     * @author - Jgpark
     * @date - 2022.06.28
     */
    @GetMapping("")
    public ReturnMessage findUserRwps(@PathVariable("userId") String userId) {
        try {
            return new ReturnMessage(service.getUserRwpsInfo(new ActivityFilterDto(userId, null)));
        } catch (Exception e) {
            return new ReturnMessage("9999", "사용자 수상 내역 조회 API 에러", e);
        }
    }

    /**
     * @title - 사용자 수상 내역 공개/승인 여부 수정
     * @desc - 사용자 수상 내역 공개/승인 여부를 수정한다.
     * @author - Jgpark
     * @date - 2022.06.28
     */
    @PutMapping("/{rwpsId}")
    public ReturnMessage updateRwps(
            @PathVariable("userId") String userId,
            @PathVariable("rwpsId") String rwpsId,
            @RequestBody RwpsUpdateDto updateDto
    ) {
        try {
            if (rwpsId == null) {
                return new ReturnMessage("400", "잘못된 요청입니다.", false);
            }

            return service.updateRwps(updateDto);
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }

    /**
     * @title - 사용자 수상 내역 공개/승인 여부 수정
     * @desc - 사용자 수상 내역 공개/승인 여부를 수정한다.
     * @author - Jgpark
     * @date - 2022.06.28
     */
    @PutMapping("")
    public ReturnMessage updateRwpsList(
        @PathVariable("userId") String userId,
        @RequestBody ListBodyDto<RwpsUpdateDto> updateDto
    ) {
        try {
            return service.updateRwps(updateDto.getData());
        } catch (Exception e) {
            return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
        }
    }

}
