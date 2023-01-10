package com.ebc.ecard.persistence;

import com.ebc.ecard.domain.agreement.UserAgreementBean;
import com.ebc.ecard.domain.badge.UserBadgeBean;
import com.ebc.ecard.domain.career.UserCareerBean;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.domain.insurance.UserInsuranceBean;
import com.ebc.ecard.domain.majority.UserMajorityBean;
import com.ebc.ecard.domain.qualification.UserQualificationBean;
import com.ebc.ecard.domain.qualification.UserRegistrationBean;
import com.ebc.ecard.domain.user.UserBean;
import com.ebc.ecard.mapper.ECardMapper;
import com.ebc.ecard.mapper.UserAgreementMapper;
import com.ebc.ecard.mapper.UserBadgeMapper;
import com.ebc.ecard.mapper.UserCareerMapper;
import com.ebc.ecard.mapper.UserInsuranceMapper;
import com.ebc.ecard.mapper.UserMajorityMapper;
import com.ebc.ecard.mapper.UserMapper;
import com.ebc.ecard.mapper.UserQualificationMapper;
import com.ebc.ecard.mapper.UserRegistrationMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserRepository implements EntityRepository<UserBean, String> {

    @Resource
    private UserMapper mapper;

    @Resource
    private ECardMapper eCardMapper;

    @Resource
    private UserAgreementMapper agreementMapper;

    @Resource
    private UserCareerMapper careerMapper;

    @Resource
    private UserQualificationMapper qualificationMapper;

    @Resource
    private UserInsuranceMapper insuranceMapper;

    @Resource
    private UserMajorityMapper majorityMapper;

    @Resource
    private UserBadgeMapper badgeMapper;

    @Resource
    private UserRegistrationMapper registrationMapper;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public UserBean findById(String userId) {

        UserBean user = mapper.findUserByUserId(userId);

        List<UserAgreementBean> agreementList = agreementMapper.findByUserId(userId);
        List<UserCareerBean> careerList = careerMapper.findByUserId(userId);
        List<UserQualificationBean> qualificationList = qualificationMapper.findByUserId(userId);
        List<UserBadgeBean> badgeList = badgeMapper.findByUserId(userId);
        List<UserInsuranceBean> insuranceList = insuranceMapper.findByUserId(userId);
        List<UserMajorityBean> majorityList = majorityMapper.findByUserId(userId);
        UserRegistrationBean registration = registrationMapper.findByUserId(userId);

        ECardBean ecard = eCardMapper.findECardByUserId(userId);

        user.setAgreementList(agreementList);
        user.setCareerList(careerList);
        user.setQualificationList(qualificationList);
        user.setBadgeList(badgeList);
        user.setInsuranceList(insuranceList);
        user.setMajorityList(majorityList);
        user.setRegistration(registration);
        user.setEcard(ecard);

        return user;
    }

    public UserBean save(UserBean bean) {
        mapper.saveUser(bean);

        saveInsurances(bean.getInsuranceList());
        saveCareers(bean.getCareerList());
        saveQualifications(bean.getQualificationList());
        saveBadges(bean.getBadgeList());

        return bean;
    }

    public UserBean update(UserBean updateBean, boolean updateSubEntity) {
        mapper.updateUserInfoByUserId(updateBean);

        if (updateSubEntity) {
            saveInsurances(updateBean.getInsuranceList());
            updateCareers(updateBean.getUserId(), updateBean.getCareerList());
            saveQualifications(updateBean.getQualificationList());
            saveBadges(updateBean.getBadgeList());
        }

        return updateBean;
    }

    private void saveInsurances(List<UserInsuranceBean> list) {
        list.forEach(insurance -> {
            try {
                insuranceMapper.createUserInsurance(insurance);
            } catch (DuplicateKeyException e) {

            } catch (Exception e) {
                try {
                    log.info("An internal error occurred while trying to insurance:{}, {}", objectMapper.writeValueAsString(insurance), e.getMessage());
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void saveCareers(List<UserCareerBean> list) {
        list.forEach(career -> {
            try {
                careerMapper.addCareer(career);
            } catch (DuplicateKeyException e) {

            } catch (Exception e) {
                try {
                    log.info("An internal error occurred while trying to career:{}, {}", objectMapper.writeValueAsString(career), e.getMessage());
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void updateCareers(String userId, List<UserCareerBean> list) {
        List<UserCareerBean> savedCareers = careerMapper.findByUserId(userId);

        list.forEach(career -> {
            Optional<UserCareerBean> matchedCareer = savedCareers.stream().filter(it -> it.isEqualsTo(career)).findFirst();

            try {
                if (matchedCareer.isPresent()) {
                    matchedCareer.get().setEndDate(career.getEndDate());
                    careerMapper.updateCareer(matchedCareer.get());
                    return;
                }

                careerMapper.addCareer(career);
            } catch (DuplicateKeyException e) {

            } catch (Exception e) {
                try {
                    log.info("An internal error occurred while trying to career:{}, {}", objectMapper.writeValueAsString(career), e.getMessage());
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void saveQualifications(List<UserQualificationBean> list) {
        list.forEach(qualification -> {
            try {
                qualificationMapper.createQualification(qualification);
            } catch (DuplicateKeyException e) {

            } catch (Exception e) {
                try {
                    log.info("An internal error occurred while trying to qualification:{}, {}", objectMapper.writeValueAsString(qualification), e.getMessage());
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void saveBadges(List<UserBadgeBean> list) {
        list.forEach(badge -> {
            try {
                badgeMapper.createUserBadge(badge);
            } catch (DuplicateKeyException e) {

            } catch (Exception e) {
                try {
                    log.info("An internal error occurred while trying to badge:{}, {}", objectMapper.writeValueAsString(badge), e.getMessage());
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
