package com.ebc.ecard.application.activity.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.application.activity.dto.ActivityUpdateRequestDto;
import com.ebc.ecard.application.activity.dto.UserActivityDto;
import com.ebc.ecard.application.activity.dto.UserActivityInsightDto;
import com.ebc.ecard.application.activity.dto.education.EducationUpdateDto;
import com.ebc.ecard.application.activity.dto.rwps.RwpsUpdateDto;
import com.ebc.ecard.domain.activity.value.ActivityType;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Resource
    UserRwpsService rwpsService;

    @Resource
    UserEducationService educationService;

    @Override
    public ReturnMessage getUserActivityInfo(ActivityFilterDto filterDto) {

        ActivityFilterDto activityFilter = new ActivityFilterDto(filterDto.getUserId(), null);

        List<UserActivityDto> rwpsList = rwpsService.getUserRwpsInfo(activityFilter);
        List<UserActivityDto> educationList = educationService.getUserEducationInfo(activityFilter);

        List<UserActivityDto> mergedList = mergeList(
            rwpsList,
            educationList,
            null,
            OrderType.START_DATE
        );

        return new ReturnMessage(mergedList);
    }

    @Override
    public ReturnMessage getUserActivityInsightByUserId(ActivityFilterDto filter) {
        UserActivityInsightDto rwpsInsight = rwpsService.getUserRwpsInsight(filter);
        UserActivityInsightDto educationInsight = educationService.getUserEducationInsight(filter);

        List<UserActivityDto> result = mergeList(
            rwpsInsight.getActivities(),
            educationInsight.getActivities(),
            filter,
            OrderType.START_DATE
        );


        boolean existData = (rwpsInsight.isExistData() || educationInsight.isExistData());
        boolean existPublic = (rwpsInsight.isExistPublic() || educationInsight.isExistPublic());
        boolean isAllPublic = (rwpsInsight.isAllPublic() && educationInsight.isAllPublic());

        return new ReturnMessage(
            new UserActivityInsightDto(
                existData,
                existPublic,
                isAllPublic,
                result
            )
        );
    }

    @Transactional
    public ReturnMessage updateActivities(List<ActivityUpdateRequestDto> updateDtoList) throws EntityNotFoundException {

        updateDtoList.forEach(updateDto -> {
            if (updateDto.getType() == ActivityType.RWPS) {
                rwpsService.updateRwps(
                    new RwpsUpdateDto(updateDto.getId(), null, updateDto.getPublicYn())
                );
            }
            if (updateDto.getType() == ActivityType.EDUCATION) {
                educationService.updateUserEducation(
                    new EducationUpdateDto(updateDto.getId(), null, updateDto.getPublicYn())
                );
            }
        });

        return new ReturnMessage();
    }

    @Transactional
    public ReturnMessage deleteActivities(List<ActivityDeleteRequestDto> deleteDtoList) throws EntityNotFoundException {

        deleteDtoList.forEach(deleteDto -> {
            if (deleteDto.getType() == ActivityType.RWPS) {
                rwpsService.deleteRwps(deleteDto);
            }
            if (deleteDto.getType() == ActivityType.EDUCATION) {
                educationService.deleteEducation(deleteDto);
            }
        });

        return new ReturnMessage();
    }

    protected List<UserActivityDto> mergeList(
        List<UserActivityDto> list1,
        List<UserActivityDto> list2,
        ActivityFilterDto filter,
        OrderType orderType
    ) {
        List<UserActivityDto> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {

            Instant list1StartDatetime = list1.get(i).getStartDateOrigin();
            Instant list2StartDatetime = list2.get(j).getStartDateOrigin();

            // 활동 일자 기준 오름차순으로 정렬
            if ((list1StartDatetime.isBefore(list2StartDatetime))) {
                addToList(result, list1.get(i++), filter);

                continue;
            }

            addToList(result, list2.get(j++), filter);
        }

        if (i >= list1.size() && j < list2.size()) {
            while (j < list2.size()) {
                addToList(result, list2.get(j++), filter);
            }
        }

        if (i < list1.size() && j >= list2.size()) {
            while (i < list1.size()) {
                addToList(result, list1.get(i++), filter);
            }
        }
        return result;
    }

    protected void addToList(List<UserActivityDto> list, UserActivityDto activity, ActivityFilterDto filter) {
        if (filter == null) {
            list.add(activity);
            return;
        }

        if (filter.getPublicYn() != null && !filter.getPublicYn().equals(activity.getPublicYn())) {
            return;
        }

        list.add(activity);
    }

    enum OrderType {
        CREATED_AT,
        START_DATE
    }
}