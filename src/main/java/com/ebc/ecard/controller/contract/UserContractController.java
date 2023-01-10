package com.ebc.ecard.controller.contract;

import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.ecard.service.ECardService;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.contract.service.UserContractService;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users/{userId}/contract-info")
public class UserContractController implements CorsDisabledController {

    @Resource
    ECardService eCardService;

    @Resource
    UserContractService contractService;

    /**
     * @title - 사용자 계약 현황 정보 조회
     * @desc - 사용자 계약 현황 정보를 조회 한다.
     * @author - Jgpark
     * @date - 2022.06.30
     */
    @GetMapping("")
    public ReturnMessage findUserContracts(
        @PathVariable("userId") String userId,
        EmployeeCvInfoRequestDto requestDto
    ) {
        try {

            return contractService.getUserContractInfoByUserId(userId);
        } catch (Exception e) {
            return new ReturnMessage("9999", "사용자 계약 현황 조회 API 에러", e);
        }
    }

}
