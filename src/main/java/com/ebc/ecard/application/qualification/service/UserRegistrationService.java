package com.ebc.ecard.application.qualification.service;

import com.ebc.ecard.application.registration.dto.UserRegistrationDto;
import com.ebc.ecard.application.registration.dto.UserRegistrationUpdateDto;
import com.ebc.ecard.util.ReturnMessage;

public interface UserRegistrationService {
    UserRegistrationDto findRegistrationByUserId(String userId);
    UserRegistrationDto findPublicRegistrationByUserId(String userId, String publicYn);
    UserRegistrationDto findRegistrationById(String registrationId);

    ReturnMessage updateRegistration(UserRegistrationUpdateDto updateDto);


    ReturnMessage deleteRegistration(String userId) throws Exception;

    String getUserRegistrationImg(String userId) throws Exception;

    String getUserRegistrationImageByEcardId(String ecardId);
}
