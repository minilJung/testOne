package com.ebc.ecard.application.qualification.service;

import com.ebc.ecard.domain.qualification.UserRegistrationBean;
import com.ebc.ecard.application.registration.dto.UserRegistrationDto;
import com.ebc.ecard.application.registration.dto.UserRegistrationFilterDto;
import com.ebc.ecard.application.registration.dto.UserRegistrationUpdateDto;
import com.ebc.ecard.mapper.UserRegistrationMapper;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Resource
    UserRegistrationMapper mapper;

    @Resource
    XeCommon common;

    @Override
    public UserRegistrationDto findRegistrationByUserId(String userId) {
        List<UserRegistrationBean> list = mapper.findUserRegistrations(new UserRegistrationFilterDto(userId, null));
        if (!list.isEmpty()) {
            return UserRegistrationDto.from(common, list.get(0));
        }

        return null;
    }
    @Override
    public UserRegistrationDto findPublicRegistrationByUserId(String userId, String publicYn) {
        List<UserRegistrationBean> list = mapper.findUserRegistrations(new UserRegistrationFilterDto(userId, publicYn));
        if (!list.isEmpty()) {
            return UserRegistrationDto.from(common, list.get(0));
        }

        return null;
    }

    @Override
    public UserRegistrationDto findRegistrationById(String registrationId) {
        UserRegistrationBean bean = mapper.findUserRegistrationById(registrationId);

        return UserRegistrationDto.from(common, bean);
    }

    @Override
    @Transactional
    public ReturnMessage updateRegistration(UserRegistrationUpdateDto updateDto) {

        List<UserRegistrationBean> list = mapper.findUserRegistrations(new UserRegistrationFilterDto(updateDto.getUserId(), null));

        if (StringUtils.isEmpty(updateDto.getRegistrationFileId())) {
            list.stream().filter(bean -> bean.getRegistrationId().equals(updateDto.getRegistrationId())).forEach(bean -> {
                bean.setPublicYn(updateDto.getPublicYn());
                mapper.updateRegistration(bean);
            });

            return new ReturnMessage("0000", "성공", true);
        }

        mapper.deleteRegistration(updateDto.getUserId());

        return new ReturnMessage(
            mapper.saveRegistration(
                new UserRegistrationBean(
                    common.getUuid(false),
                    updateDto.getUserId(),
                    updateDto.getRegistrationFileId(),
                    "Y",
                    new Date(),
                    new Date()
                )
            )
        );
    }


    @Override
    public ReturnMessage deleteRegistration(String userId) throws Exception {
        return new ReturnMessage(mapper.deleteRegistration(userId));
    }

    @Override
    public String getUserRegistrationImg(String userId) throws Exception {
        List<UserRegistrationBean> list = mapper.findUserRegistrations(new UserRegistrationFilterDto(userId, "Y"));
        if(!list.isEmpty()) {
            UserRegistrationDto dto = UserRegistrationDto.from(common, list.get(0));

            return dto.getFilePath();
        }

        return null;
    }

    @Override
    public String getUserRegistrationImageByEcardId(String ecardId) {
        List<UserRegistrationBean> list = mapper.getUserRegistrationImageByEcardId(new String(Base64Utils.decodeFromString(ecardId)));
        if(!list.isEmpty()) {
            UserRegistrationDto dto = UserRegistrationDto.from(common, list.get(0));

            return dto.getFilePath();
        }

        return null;
    }
}