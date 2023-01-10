package com.ebc.ecard.controller.user;

import com.ebc.ecard.domain.user.UserBean;
import com.ebc.ecard.application.user.dto.UserAddUsingFPRequestDto;
import com.ebc.ecard.application.user.dto.UserAddUsingMandatoryRequestDto;
import com.ebc.ecard.application.user.dto.UserExistenceDto;
import com.ebc.ecard.application.user.dto.UserExistenceRequestDto;
import com.ebc.ecard.application.user.service.UserService;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.util.ReturnMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController implements CorsDisabledController {

    @Resource
    ObjectMapper objectMapper;

    @Resource
    UserService service;

    /**
     * @title    - 회원가입
     * @desc    - 사용자 가입신청을 한다. 승인코드가 유효하면 자동으로 가입 완료
     * @author    - Jinyeon
     * @date    - 2021.12.20
     * @param    - UserBean
     * @return    - 결과 코드 (9001: 중복 아이디)
     */
    @PostMapping("/join")
    public ReturnMessage saveUser(
            @RequestBody UserBean bean,
            HttpServletRequest request) {
        try {

            log.info("/api/users/join. Set accountId and birthdate. {}", objectMapper.writeValueAsString(bean));
            String serverName = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/api"));

            return service.saveUser(serverName, bean);
        } catch (Exception e) {
            log.info("Error while set accountId and birthdate. {}", e.getMessage(), e);
            return new ReturnMessage("9999", "회원가입 API 에러", e);
        }
    }

    /**
     * @title    - 회원가입
     * @desc    - 사용자 가입신청을 한다. 승인코드가 유효하면 자동으로 가입 완료
     * @author    - Jinyeon
     * @date    - 2021.12.20
     * @param    - UserBean
     * @return    - 결과 코드 (9001: 중복 아이디)
     */
    @PostMapping("/fp")
    public ReturnMessage addUserUsingFpUniqNo(
        HttpServletRequest request,
        @RequestBody UserAddUsingFPRequestDto params
    ) {
        try {
            String serverName = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/api"));

            List<String> success = new ArrayList<>();
            List<String> failed = new ArrayList<>();
            params.getList().forEach(it -> {
                try {
                    String resultText = service.saveUserUsingFpId(serverName, it);
                    success.add(it.getFpUniqNo());
                } catch(Exception e) {
                    log.info("Error occured while user add: {}", e);
                    failed.add(it.getFpUniqNo());
                }
            });

            Map<String, List<String>> resultMap = new HashMap<>();
            resultMap.put("success", success);
            resultMap.put("failed", failed);
            return new ReturnMessage(resultMap);
        } catch (Exception e) {
            return new ReturnMessage("9999", "회원가입 API 에러", e);
        }
    }

    /**
     * @title    - 회원가입
     * @desc    - 사용자 가입신청을 한다. 승인코드가 유효하면 자동으로 가입 완료
     * @author    - Jinyeon
     * @date    - 2021.12.20
     * @param    - UserBean
     * @return    - 결과 코드 (9001: 중복 아이디)
     */
    @PostMapping("/mandatory")
    public ReturnMessage addUserUsingMandatory(
        HttpServletRequest request,
        @RequestBody UserAddUsingMandatoryRequestDto params
    ) {
        try {
            List<String> success = new ArrayList<>();
            List<String> failed = new ArrayList<>();
            params.getList().forEach(it -> {
                try {
                    String resultText = service.saveUserUsingUserMandatoryInfo(it);
                    success.add(resultText);
                } catch(Exception e) {
                    log.info("Error occured while user add", e);
                    failed.add(it.getName() + "_" + it.getMobileNo().replaceAll("-", ""));
                }
            });

            Map<String, List<String>> resultMap = new HashMap<>();
            resultMap.put("success", success);
            resultMap.put("failed", failed);
            return new ReturnMessage(resultMap);
        } catch (Exception e) {
            return new ReturnMessage("9999", "회원가입 API 에러", e);
        }
    }

    /**
     * @title    - 사용자 가입 여부 확인
     * @desc    - 사용자 가입 여부를 확인한다.
     * @author    - Jgpark
     * @date    - 2022.07.12
     * @param    -
     * @return    - 결과 코드
     */
    @GetMapping("/existence")
    public ReturnMessage getUserExistence(
        HttpServletRequest request,
        UserExistenceRequestDto filter
    ) {
        if (StringUtils.isEmpty(filter.getFpUniqNo())) {
            return new ReturnMessage("400", "잘못된 요청입니다.", new RuntimeException("FP 고유 번호가 없습니다."));
        }
        if (StringUtils.isEmpty(filter.getFpNmcdDvsnCode())) {
            return new ReturnMessage("400", "잘못된 요청입니다.", new RuntimeException("FP 명함 조회 구분이 없습니다."));
        }

        try {

            UserExistenceDto existenceDto = service.getUserExistence(filter);

            return new ReturnMessage(existenceDto);
            //if (existenceDto != null && existenceDto.getMembJoinYn().equals("N")) {
            //    return new ReturnMessage("400", "회원가입을 시도해주세요.", new RuntimeException("회원등록이 되지 않은 FP입니다."));
            //} else {
            //
            //}
        } catch (Exception e) {
            return new ReturnMessage("9999", "전자명함 링크 조회 API 에러", e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'USER_ADMIN')")
    @GetMapping("/me")
    public ReturnMessage getMyInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AtomicBoolean isLoggedIn = new AtomicBoolean(false);
        authentication.getAuthorities().forEach((GrantedAuthority authority) -> {
            if (isLoggedIn.get()) {
                return;
            }

            if (authority.getAuthority().equals("ROLE_ADMIN")
                    || authority.getAuthority().equals("ROLE_USER")) {
                isLoggedIn.set(true);
            }
        });

        if (!isLoggedIn.get()) {
            return new ReturnMessage("9000", "로그인이 필요한 접근입니다.", false);
        }

        try {
            Map<String, Object> result = service.findUserByUserId((String) authentication.getPrincipal());
            return new ReturnMessage(result);
        } catch (Exception e) {
            return new ReturnMessage("9999", "전자 명함 링크 조회 API 에러", e);
        }
    }

    /**
     * fpId를 이용해 유저 정보 조회
     */
    @GetMapping("/{fpId}/existence")
    public ReturnMessage getUserInfoByFpId(@PathVariable String fpId) {
        try {
            return new ReturnMessage(service.getUserInfoByFpId(fpId));
        } catch (Exception e) {
            return new ReturnMessage("9999", "accountId 조회 API 에러", e);
        }
    }

    /**
     * ci
     * @return
     */
    @PutMapping("/{fpId}/ci/existence")
    public ReturnMessage updateUserInfoByFpId(@PathVariable String fpId, UserBean bean) {
        try {
            return new ReturnMessage(service.updateUserInfoByFpId(bean));
        } catch (Exception e) {
            return new ReturnMessage("9999", "회원 정보 업데이트 API 에러", e);
        }
    }
}
