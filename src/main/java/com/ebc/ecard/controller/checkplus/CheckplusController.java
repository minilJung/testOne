package com.ebc.ecard.controller.checkplus;

import com.ebc.ecard.application.checkplus.CheckplusService;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.checkplus.dto.EcardUiCheckplusDto;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/checkplus")
public class CheckplusController implements CorsDisabledController {

    @Resource
    CheckplusService service;

    @GetMapping("/keys-and-sitecode")
    public ReturnMessage getIdentificationRequirement() throws Exception {
        try {
            EcardUiCheckplusDto result = service.getToken();
            if (result != null) {
                return new ReturnMessage(result);
            }

            return new ReturnMessage("9000", "본인인증 요청 데이터 API 실패", null);
        } catch(Exception e) {
            return new ReturnMessage("9999", "본인인증 요청 데이터 API 에러", e);
        }
    }
}
