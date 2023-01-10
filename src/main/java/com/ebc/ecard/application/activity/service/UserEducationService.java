package com.ebc.ecard.application.activity.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.domain.activity.UserEducationBean;
import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.application.activity.dto.UserActivityDto;
import com.ebc.ecard.application.activity.dto.UserActivityInsightDto;
import com.ebc.ecard.application.activity.dto.education.EducationUpdateDto;
import com.ebc.ecard.util.ReturnMessage;

import java.util.List;

public interface UserEducationService {

    List<UserActivityDto> getUserEducationInfo(ActivityFilterDto filterDto);

    UserActivityInsightDto getUserEducationInsight(ActivityFilterDto filterDto);

    ReturnMessage createEducation(UserEducationBean bean) throws Exception;
    ReturnMessage updateUserEducation(EducationUpdateDto updateDto);
    ReturnMessage updateUserEducations(List<EducationUpdateDto> updateDto);

    Integer deleteEducation(ActivityDeleteRequestDto deleteDto) throws EntityNotFoundException;

}