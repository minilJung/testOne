package com.ebc.ecard.application.activity.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.domain.activity.UserEducationBean;
import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.application.activity.dto.ActivityInsight;
import com.ebc.ecard.application.activity.dto.UserActivityDto;
import com.ebc.ecard.application.activity.dto.UserActivityInsightDto;
import com.ebc.ecard.application.activity.dto.education.EducationUpdateDto;
import com.ebc.ecard.mapper.UserEducationMapper;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
public class UserEducationServiceImpl implements UserEducationService {

    @Resource
    XeCommon common;

    @Resource
    UserEducationMapper mapper;

    @Override
    public List<UserActivityDto> getUserEducationInfo(ActivityFilterDto filter) {
        List<UserEducationBean> educationList = mapper.findEducationBySpecification(filter);

        return educationList.stream()
            .map(UserActivityDto::fromBean)
            .collect(Collectors.toList());
    }

    @Override
    public UserActivityInsightDto getUserEducationInsight(ActivityFilterDto filterDto) {

        ActivityInsight insight = mapper.getInsightByUserId(filterDto.getUserId());
        List<UserEducationBean> educationList = mapper.findEducationBySpecification(filterDto);
        List<UserActivityDto> activityDtoList = educationList.stream()
            .map(UserActivityDto::fromBean)
            .collect(Collectors.toList());

        return UserActivityInsightDto.of(insight, activityDtoList);
    }

    @Override
    public ReturnMessage createEducation(UserEducationBean bean) throws Exception {
        int resultCount = 0;
        try {
            bean.setEducationId(common.getUuid(true));
            // Set default values;
            bean.setPublicYn("Y");
            //bean.setStatus(ApprovalStatus.PENDING);

            resultCount = mapper.createUserEducation(bean);

            if (resultCount > 0) {
                return new ReturnMessage(mapper.findUserEducationByEducationId(bean.getEducationId()));
            }
        } catch (DuplicateKeyException e) {
            // ignore duplicated
        }

        return new ReturnMessage("9009", "같은 내용의 교육 이수내역이 이미 등록되어있습니다.", bean.getEducationName());
    }

    @Override
    public ReturnMessage updateUserEducation(EducationUpdateDto updateDto) {
        UserEducationBean bean = mapper.findUserEducationByEducationId(updateDto.getEducationId());
        if (bean == null) {
            return new ReturnMessage("9000", "교육 이수내역 정보를 찾을 수 없습니다.", updateDto.getEducationId());
        }

        if (updateDto.getPublicYn() != null) {
            bean.setPublicYn(updateDto.getPublicYn());
        }

//        if (updateDto.getStatus() != null) {
//            bean.setStatus(updateDto.getStatus());
//        }

        int resultCount = mapper.updateUserEducation(bean);

        if (resultCount > 0) {
            return new ReturnMessage(true);
        }

        return new ReturnMessage(false);
    }

    @Override
    public ReturnMessage updateUserEducations(List<EducationUpdateDto> updateDto) {
        updateDto.forEach(this::updateUserEducation);

        return new ReturnMessage(false);
    }

    @Override
    public Integer deleteEducation(ActivityDeleteRequestDto deleteDto) throws EntityNotFoundException {

        UserEducationBean bean = mapper.findUserEducationByEducationId(deleteDto.getId());
        if (bean == null) {
            throw new EntityNotFoundException(UserEducationBean.class);
        }

        mapper.deleteEducationMedia(deleteDto);
        return mapper.deleteEducation(deleteDto);
    }
}