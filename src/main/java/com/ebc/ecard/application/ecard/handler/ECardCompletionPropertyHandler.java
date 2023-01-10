package com.ebc.ecard.application.ecard.handler;

import com.ebc.ecard.application.majority.dto.MajorityFilterDto;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.application.ecard.dto.ECardCompletionPropertiesDto;
import com.ebc.ecard.domain.career.UserCareerBean;
import com.ebc.ecard.domain.majority.UserMajorityBean;
import com.ebc.ecard.domain.qualification.UserQualificationBean;
import com.ebc.ecard.domain.qualification.UserRegistrationBean;
import com.ebc.ecard.application.career.dto.CareerFilterDto;
import com.ebc.ecard.application.qualification.dto.QualificationFilterDto;
import com.ebc.ecard.application.registration.dto.UserRegistrationFilterDto;
import com.ebc.ecard.mapper.UserCareerMapper;
import com.ebc.ecard.mapper.UserMajorityMapper;
import com.ebc.ecard.mapper.UserQualificationMapper;
import com.ebc.ecard.mapper.UserRegistrationMapper;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * 전자명함 완성도를 계산하는 로직을 구현한 메소드
 * 한 기능을 위한 의존성이 Service에 너무 많이 추가되는 것을 방지하기 위해 분리하여 작성
 * @author    - Jgpark
 * @date    - 2022.06.30
 */
@Service
public class ECardCompletionPropertyHandler {

    @Resource
    UserCareerMapper userCareerMapper;

    @Resource
    UserQualificationMapper userQualificationMapper;

    @Resource
    UserRegistrationMapper userRegistrationMapper;

    @Resource
    UserMajorityMapper userMajorityMapper;


    public ECardCompletionPropertiesDto getCompleteRate(ECardBean bean) {

        ECardCompletionPropertiesDto completeRateDto = new ECardCompletionPropertiesDto();

        // 프로필 이미지 등록 여부
        if (bean.getProfileFileId() != null && !bean.getProfileFileId().trim().equals("")) {
            completeRateDto.setProfileImageUploaded(true);
        }

        // 전문분야 선택 여부
        MajorityFilterDto majorityFilter = new MajorityFilterDto(bean.getUserId(), "Y");
        List<UserMajorityBean> majorityBeanList = userMajorityMapper.findMajorityBySpecification(majorityFilter);
        if (majorityBeanList.size() > 0) {
            completeRateDto.setMajoritySelected(true);
        }

        //경력 등록 여부
        CareerFilterDto careerFilter = new CareerFilterDto(bean.getUserId(), null);
        List<UserCareerBean> careerBeanList = userCareerMapper.findCareerBySpecification(careerFilter);
        if (careerBeanList.size() > 0) {
            completeRateDto.setCareerAdded(true);
        }

        //보험모집종사자 등록증 등록 여부
        List<UserRegistrationBean> registration = userRegistrationMapper.findUserRegistrations(new UserRegistrationFilterDto(bean.getUserId(), "Y"));
        if (!registration.isEmpty()) {
            completeRateDto.setQualificationImageAdded(true);
        }

        //자격증 등록 여부
        QualificationFilterDto qualificationFilter = new QualificationFilterDto(bean.getUserId(), null);
        List<UserQualificationBean> qualificationBeanList = userQualificationMapper.findUserQualificationBySpecification(qualificationFilter);
        if (qualificationBeanList.size() > 0) {
            completeRateDto.setQualificationAdded(true);
        }

        return completeRateDto;
    }
}
