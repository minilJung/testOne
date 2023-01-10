package com.ebc.ecard.application.activity.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.application.activity.dto.ActivityUpdateRequestDto;
import com.ebc.ecard.util.ReturnMessage;

import java.util.List;

/**
 * 수상(Rwps) + 교육(Education)을 통합 처리하는 서비스
 *
 * @author jgpark
 */
public interface UserActivityService {
    ReturnMessage getUserActivityInfo(ActivityFilterDto filterDto);

    ReturnMessage getUserActivityInsightByUserId(ActivityFilterDto filter);

    ReturnMessage updateActivities(List<ActivityUpdateRequestDto> updateDto) throws EntityNotFoundException;

    ReturnMessage deleteActivities(List<ActivityDeleteRequestDto> deleteDto) throws EntityNotFoundException;
}