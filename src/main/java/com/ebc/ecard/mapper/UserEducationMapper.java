package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.activity.UserEducationBean;
import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.application.activity.dto.ActivityInsight;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserEducationMapper {

    Integer createUserEducation(UserEducationBean badge);

    List<UserEducationBean> findEducationBySpecification(ActivityFilterDto filter);

    ActivityInsight getInsightByUserId(String userId);

    List<UserEducationBean> findPendingEducationsByFpId(String fpId);

    UserEducationBean findUserEducationByEducationId(String educationId);

    Integer updateUserEducation(UserEducationBean bean);
    Integer deleteEducation(ActivityDeleteRequestDto bean);
    Integer deleteEducationMedia(ActivityDeleteRequestDto bean);



}
