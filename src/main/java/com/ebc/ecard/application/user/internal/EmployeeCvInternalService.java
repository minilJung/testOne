package com.ebc.ecard.application.user.internal;

import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoResponseDto;
import com.ebc.ecard.domain.user.UserBean;

/**
 * @apiNote 서비스 이외 계층에서 참조 금지.
 * [Controller -> Service -> (InternalService) -> Mapper]
 */
public interface EmployeeCvInternalService {

	EmployeeCvInfoResponseDto getEmployeeCvInfo(EmployeeCvInfoRequestDto requestDto);

	UserBean setUserProfileToUserBean(UserBean bean, EmployeeCvInfoResponseDto cvInfoDto);

}