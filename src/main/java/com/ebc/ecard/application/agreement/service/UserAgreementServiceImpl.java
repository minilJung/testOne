package com.ebc.ecard.application.agreement.service;

import com.ebc.ecard.domain.agreement.UserAgreementBean;
import com.ebc.ecard.application.agreement.dto.UserAgreementAddDto;
import com.ebc.ecard.mapper.UserAgreementMapper;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserAgreementServiceImpl implements UserAgreementService {

    @Resource
    XeCommon common;

    @Resource
    UserAgreementMapper mapper;

    @Override
    public ReturnMessage addAgreement(UserAgreementAddDto addDto) {
        int resultCount = 0;
        for (String agreementName : addDto.getAgreementNames()) {
            String trimmedName = agreementName.replaceAll(" ", "");
            if (trimmedName.equals("")) {
                continue;
            }

            try {
                resultCount += mapper.createUserAgreement(
                        new UserAgreementBean(
                                common.getUuid(false),
                                trimmedName,
                                addDto.getUserId(),
                                null
                        )
                );
            } catch (DuplicateKeyException e) {
                // 이미 동의한 약관은 무시
            }
        }

        if (resultCount > 0) {
            return new ReturnMessage(true);
        }

        return new ReturnMessage(false);
    }

    @Override
    public List<UserAgreementBean> findAgreementByUserId(String userId) throws Exception {
        return mapper.findByUserId(userId);
    }
}