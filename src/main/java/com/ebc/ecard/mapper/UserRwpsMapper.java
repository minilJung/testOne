package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.activity.UserRwpsBean;
import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.application.activity.dto.ActivityInsight;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRwpsMapper {
    List<UserRwpsBean> findRwpsBySpecification(ActivityFilterDto filter);

    ActivityInsight getInsightByUserId(String userId);

    List<UserRwpsBean> findPendingRwpsByFpId(String fpId);

    UserRwpsBean findUserRwpsByRwpsId(String filter);

    Integer createUserRwps(UserRwpsBean badge);

    Integer updateUserRwps(UserRwpsBean bean);
    Integer deleteRwps(ActivityDeleteRequestDto bean);

}
