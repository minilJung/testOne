package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.qualification.UserRegistrationBean;
import com.ebc.ecard.application.registration.dto.UserRegistrationFilterDto;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRegistrationMapper {
    UserRegistrationBean findByUserId(String userId);

    List<UserRegistrationBean> findUserRegistrations(UserRegistrationFilterDto filterDto);
    List<UserRegistrationBean> getUserRegistrationImageByEcardId(String ecardId);
    UserRegistrationBean findUserRegistrationById(String registrationId);

    Integer updateRegistration(UserRegistrationBean updateDto);

    int saveRegistration(UserRegistrationBean bean);

    Integer deleteRegistration(String userId);
}
