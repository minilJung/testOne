package com.ebc.ecard.application.user.internal;

import com.ebc.ecard.application.badge.dto.EmployeeCvBadgeDto;
import com.ebc.ecard.application.career.dto.EmployeeCvCareerInfoResponseDto;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoResponseDto;
import com.ebc.ecard.application.insurance.dto.EmployeeCvInsuranceResponseDto;
import com.ebc.ecard.application.qualification.dto.EmployeeCvQlfcResponseDto;
import com.ebc.ecard.domain.badge.UserBadgeBean;
import com.ebc.ecard.domain.career.UserCareerBean;
import com.ebc.ecard.domain.insurance.UserInsuranceBean;
import com.ebc.ecard.domain.qualification.UserQualificationBean;
import com.ebc.ecard.domain.user.UserBean;
import com.ebc.ecard.mapper.UserBadgeMapper;
import com.ebc.ecard.mapper.UserCareerMapper;
import com.ebc.ecard.mapper.UserInsuranceMapper;
import com.ebc.ecard.mapper.UserQualificationMapper;
import com.ebc.ecard.persistence.UserRepository;
import com.ebc.ecard.util.XeCommon;
import com.ebc.ecard.util.request.ECardHttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeCvInternalServiceImpl implements EmployeeCvInternalService {
    @Value("${ecard.services.fep.url}")
    private String FEP_URL;

    @Resource
    private XeCommon common;

    @Resource
    UserRepository userRepository;

    @Resource
    UserInsuranceMapper insuranceMapper;

    @Resource
    UserCareerMapper careerMapper;

    @Resource
    UserQualificationMapper qualificationMapper;

    @Resource
    UserBadgeMapper badgeMapper;


    @Resource
    private ObjectMapper objectMapper;

    @Override
    public EmployeeCvInfoResponseDto getEmployeeCvInfo(EmployeeCvInfoRequestDto requestDto) {

        log.info("Get employee CV Info to {} {}", "https://" + FEP_URL + "/fep/api/ebc/empCvInfo.api", requestDto.getFpId());

        EmployeeCvInfoResponseDto response = ECardHttpRequest.Builder.build("https://" + FEP_URL)
                .post("/fep/api/ebc/empCvInfo.api")
                .payload(requestDto)
                .contentType(MediaType.APPLICATION_JSON)
                .executeAndGetBodyAs(EmployeeCvInfoResponseDto.class);

        return (objectMapper.convertValue(response, EmployeeCvInfoResponseDto.class));
    }

    public UserBean setUserProfileToUserBean(UserBean bean, EmployeeCvInfoResponseDto cvInfoDto) {

        bean.setInsuranceList(convertCvIsrnToInsuranceBean(bean.getUserId(), cvInfoDto.getCuslPssbIscm()));
        bean.setCareerList(convertCvCarrToCareerBean(bean.getUserId(), cvInfoDto.getCarr()));
        bean.setQualificationList(convertCvQlfcToQualificationBean(bean.getUserId(), cvInfoDto.getFpQlfcInfo()));
        bean.setBadgeList(convertCvBadgeToBadgeBean(bean.getUserId(), cvInfoDto.getBdge()));

        return bean;
    }

    List<UserQualificationBean> convertCvQlfcToQualificationBean(String userId, List<EmployeeCvQlfcResponseDto> list) {

        return (list == null)
            ? Collections.emptyList()
            : list.stream().map(it -> {
                try {
                    return UserQualificationBean.of(
                        common.getUuid(false),
                        userId,
                        it
                    );
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
    }

    List<UserCareerBean> convertCvCarrToCareerBean(String userId, List<EmployeeCvCareerInfoResponseDto> list) {

        return (list == null)
            ? Collections.emptyList()
            : list.stream().map(it -> {
                try {
                    return UserCareerBean.of(
                        common.getUuid(false),
                        userId,
                        it
                    );
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
    }

    List<UserBadgeBean> convertCvBadgeToBadgeBean(String userId, List<EmployeeCvBadgeDto> list) {

        return (list == null)
            ? Collections.emptyList()
            : list.stream().map(it -> {
                try {
                    return UserBadgeBean.of(
                        common.getUuid(false),
                        userId,
                        it
                    );
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
    }

    List<UserInsuranceBean> convertCvIsrnToInsuranceBean(String userId, List<EmployeeCvInsuranceResponseDto> list) {

        return (list == null)
            ? Collections.emptyList()
            : list.stream().map(it -> {
                return UserInsuranceBean.create(
                    common.getUuid(false),
                    userId,
                    it
                );
            }).collect(Collectors.toList());
    }
}