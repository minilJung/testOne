package com.ebc.ecard.controller.insurance;

import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.domain.insurance.UserInsuranceBean;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.insurance.service.UserInsuranceService;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users/{userId}/insurances")
public class UserInsuranceController implements CorsDisabledController {

    @Resource
    UserInsuranceService service;

    /**
     * @title - 사용자 상담가능 보험사 정보 조회
     * @desc - 사용자 상담가능 보험사 정보를 조회 한다.
     * @author - Jgpark
     * @date - 2022.06.30
     */
    @GetMapping("")
    public ReturnMessage findUserInsurance(@PathVariable("userId") String userId) {
        try {
            return service.getUserInsuranceInfo(userId);
        } catch (Exception e) {
            return new ReturnMessage("9999", "상담가능 보험사 정보 조회 API 에러", e);
        }
    }

    /**
     * @title - 사용자 상담가능 보험사 공개 여부 수정
     * @desc - 사용자 상담가능 보험사 공개 여부를 수정한다.
     * @author - Jgpark
     * @date - 2022.06.30
     */
    @PutMapping("/{insuranceId}")
    public ReturnMessage updateInsurancePublicYn(
            @PathVariable("insuranceId") String insuranceId,
            @RequestBody UserInsuranceBean bean
    ) {
        try {
            if (insuranceId == null) {
                return new ReturnMessage("400", "잘못된 요청입니다.", false);
            }
            bean.setInsuranceId(insuranceId);
            return service.updateUserInsurancePublicYn(bean);
        } catch (Exception e) {
            return new ReturnMessage("9999", "상담가능 보험사 공개여부 수정 API 에러", e);
        }
    }
}
