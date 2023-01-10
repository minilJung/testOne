package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.badge.UserBadgeBean;
import com.ebc.ecard.application.badge.dto.UserBadgeUpdateDto;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserBadgeMapper {
    List<UserBadgeBean> findByUserId(String userId);

    Integer createUserBadge(UserBadgeBean badge);

    Integer updateUserBadge(UserBadgeUpdateDto updateDto);
}
