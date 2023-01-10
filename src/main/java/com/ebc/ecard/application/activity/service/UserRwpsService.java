package com.ebc.ecard.application.activity.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.domain.activity.UserRwpsBean;
import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.application.activity.dto.UserActivityDto;
import com.ebc.ecard.application.activity.dto.UserActivityInsightDto;
import com.ebc.ecard.application.activity.dto.rwps.RwpsUpdateDto;
import com.ebc.ecard.util.ReturnMessage;

import java.util.List;

public interface UserRwpsService {
    ReturnMessage createUserRwps(UserRwpsBean bean) throws Exception;

    List<UserActivityDto> getUserRwpsInfo(ActivityFilterDto filterDto);

    UserActivityInsightDto getUserRwpsInsight(ActivityFilterDto filterDto);

    ReturnMessage updateRwps(RwpsUpdateDto updateDto);

    ReturnMessage updateRwps(List<RwpsUpdateDto> updateDto);

    Integer deleteRwps(ActivityDeleteRequestDto deleteDto) throws EntityNotFoundException;

}