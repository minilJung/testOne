package com.ebc.ecard.application.majority.service;

import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.majority.dto.MajorityFilterDto;
import com.ebc.ecard.application.majority.dto.UserMajorityDto;
import com.ebc.ecard.application.majority.dto.UserMajorityInsightDto;
import com.ebc.ecard.application.majority.dto.UserMajorityUpdateDto;
import com.ebc.ecard.util.ReturnMessage;

import java.util.List;

public interface UserMajorityService {

//	ReturnMessage addMajority(UserMajorityBean bean) throws Exception;

	List<UserMajorityDto> findMajorityByUserId(MajorityFilterDto majorityFilterDto);

	UserMajorityInsightDto getMajorityInsightByUserId(String userId, EmployeeCvInfoRequestDto employeeCvInfoRequestDto, String publicYn) throws Exception;

	ReturnMessage updateMajority(UserMajorityUpdateDto updateDto) throws Exception;

	ReturnMessage updateMajorities(List<UserMajorityUpdateDto> updateDto) throws Exception;

}