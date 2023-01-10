package com.ebc.ecard.application.insurance.service;

import com.ebc.ecard.domain.insurance.UserInsuranceBean;
import com.ebc.ecard.mapper.UserInsuranceMapper;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInsuranceServiceImpl implements UserInsuranceService {

    @Resource
    UserInsuranceMapper mapper;

    @Override
    public ReturnMessage getUserInsuranceInfo(String userId) {

        return new ReturnMessage(mapper.findByUserId(userId));
    }

    @Override
    public ReturnMessage updateUserInsurancePublicYn(UserInsuranceBean bean) {

        int resultCount = mapper.updateUserInsurancePublicYn(bean);
        if (resultCount > 0) {
            return new ReturnMessage(true);
        }
        return new ReturnMessage(false);
    }
}