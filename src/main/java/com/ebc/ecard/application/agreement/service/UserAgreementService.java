package com.ebc.ecard.application.agreement.service;

import com.ebc.ecard.domain.agreement.UserAgreementBean;
import com.ebc.ecard.application.agreement.dto.UserAgreementAddDto;
import com.ebc.ecard.util.ReturnMessage;

import java.util.List;

public interface UserAgreementService {

	ReturnMessage addAgreement(UserAgreementAddDto addDto);

	List<UserAgreementBean> findAgreementByUserId(String userId) throws Exception;
}