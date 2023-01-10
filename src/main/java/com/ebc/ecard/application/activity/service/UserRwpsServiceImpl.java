package com.ebc.ecard.application.activity.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.domain.activity.UserRwpsBean;
import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.application.activity.dto.ActivityInsight;
import com.ebc.ecard.application.activity.dto.UserActivityDto;
import com.ebc.ecard.application.activity.dto.UserActivityInsightDto;
import com.ebc.ecard.application.activity.dto.rwps.RwpsUpdateDto;
import com.ebc.ecard.mapper.UserRwpsMapper;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
public class UserRwpsServiceImpl implements UserRwpsService {

    @Resource
    XeCommon common;

    @Resource
    UserRwpsMapper mapper;

    @Override
    public List<UserActivityDto> getUserRwpsInfo(ActivityFilterDto filterDto) {

        List<UserRwpsBean> rwpsList = mapper.findRwpsBySpecification(filterDto);
        return rwpsList.stream()
            .map(UserActivityDto::fromBean)
            .collect(Collectors.toList());
    }

    @Override
    public UserActivityInsightDto getUserRwpsInsight(ActivityFilterDto filterDto) {
        ActivityInsight insight = mapper.getInsightByUserId(filterDto.getUserId());

        List<UserRwpsBean> rwpsList = mapper.findRwpsBySpecification(filterDto);
        List<UserActivityDto> activityDtoList = rwpsList.stream()
            .map(UserActivityDto::fromBean)
            .collect(Collectors.toList());

        return UserActivityInsightDto.of(
            insight,
            activityDtoList
        );
    }

    @Override
    public ReturnMessage createUserRwps(UserRwpsBean bean) {
        int resultCount = 0;
        try {
            bean.setRwpsId(common.getUuid(true));
            //bean.setStatus(ApprovalStatus.PENDING);
            bean.setPublicYn("Y");
            resultCount = mapper.createUserRwps(bean);

            if (resultCount > 0) {
                return new ReturnMessage(mapper.findUserRwpsByRwpsId(bean.getRwpsId()));
            }

            return new ReturnMessage(false);
        } catch (DuplicateKeyException e) {
            // ignore duplicated
            e.printStackTrace();
            return new ReturnMessage("9009", "같은 내용의 수상내역이 이미 등록되어있습니다.", bean.getRwpsBrocName());
        }
    }

    @Override
    public ReturnMessage updateRwps(RwpsUpdateDto updateDto) {

        UserRwpsBean bean = mapper.findUserRwpsByRwpsId(updateDto.getRwpsId());
        if (bean == null) {
            return new ReturnMessage("9000", "수상 정보를 찾을 수 없습니다.", updateDto.getRwpsId());
        }

        if (updateDto.getPublicYn() != null) {
            bean.setPublicYn(updateDto.getPublicYn());
        }

//        if (updateDto.getStatus() != null) {
//            bean.setStatus(updateDto.getStatus());
//        }

        int resultCount = mapper.updateUserRwps(bean);

        if (resultCount > 0) {
            return new ReturnMessage(true);
        }

        return new ReturnMessage(false);
    }

    @Override
    public ReturnMessage updateRwps(List<RwpsUpdateDto> updateDto) {

        updateDto.forEach(this::updateRwps);

        return new ReturnMessage(false);
    }

    @Override
    public Integer deleteRwps(ActivityDeleteRequestDto deleteDto) throws EntityNotFoundException {

        UserRwpsBean bean = mapper.findUserRwpsByRwpsId(deleteDto.getId());
        if (bean == null) {
            throw new EntityNotFoundException(UserRwpsBean.class);
        }

        return mapper.deleteRwps(deleteDto);
    }

}