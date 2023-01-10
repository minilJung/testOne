package com.ebc.ecard.application.badge.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.badge.dto.UserBadgeDto;
import com.ebc.ecard.application.badge.dto.UserBadgeUpdateDto;
import com.ebc.ecard.mapper.UserBadgeMapper;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Service
public class UserBadgeServiceImpl implements UserBadgeService {

    @Resource
    XeCommon common;

    @Resource
    UserBadgeMapper mapper;

    @Override
    public ReturnMessage getUserBadgeInfo(String userId, EmployeeCvInfoRequestDto employeeInfoRequestDto, String publicYn) throws Exception {

        List<UserBadgeDto> result = new ArrayList<>();
        mapper.findByUserId(userId).forEach(badge -> {
            if (publicYn != null && !badge.getPublicYn().equals(publicYn)) {
                return;
            }

            result.add(UserBadgeDto.fromBean(badge));
        });
        return new ReturnMessage(result);
    }

    @Override
    public ReturnMessage updateUserBadge(UserBadgeUpdateDto updateDto) throws EntityNotFoundException {

        int resultCount = mapper.updateUserBadge(updateDto);
        if (resultCount > 0) {
            return new ReturnMessage(true);
        }
        return new ReturnMessage(false);
    }

    @Override
    public ReturnMessage updateUserBadge(List<UserBadgeUpdateDto> updateDto) throws EntityNotFoundException {

        updateDto.forEach(this::updateUserBadge);

        return new ReturnMessage();
    }
}