package com.ebc.ecard.application.badge.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.badge.dto.UserBadgeUpdateDto;
import com.ebc.ecard.util.ReturnMessage;

import java.util.List;

public interface UserBadgeService {

	ReturnMessage getUserBadgeInfo(String userId, EmployeeCvInfoRequestDto employeeInfoRequestDto, String publicYn) throws Exception;

	ReturnMessage updateUserBadge(UserBadgeUpdateDto bean) throws EntityNotFoundException;
	ReturnMessage updateUserBadge(List<UserBadgeUpdateDto> bean) throws EntityNotFoundException;
}