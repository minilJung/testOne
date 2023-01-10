package com.ebc.ecard.application.insurance.service;

import com.ebc.ecard.domain.insurance.UserInsuranceBean;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.util.ReturnMessage;

public interface UserInsuranceService {

    ReturnMessage getUserInsuranceInfo(String userId);

    ReturnMessage updateUserInsurancePublicYn(UserInsuranceBean bean);
}