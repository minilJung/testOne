package com.ebc.ecard.application.majority.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.majority.dto.MajorityFilterDto;
import com.ebc.ecard.application.majority.dto.UserMajorityDto;
import com.ebc.ecard.application.majority.dto.UserMajorityInsightDto;
import com.ebc.ecard.application.majority.dto.UserMajorityUpdateDto;
import com.ebc.ecard.domain.majority.UserMajorityBean;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.mapper.UserMajorityMapper;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
public class UserMajorityServiceImpl implements UserMajorityService {

	@Resource
	UserMajorityMapper majorityMapper;


	@Override
	public List<UserMajorityDto> findMajorityByUserId(MajorityFilterDto majorityFilter) {

		List<UserMajorityBean> results = majorityMapper.findMajorityBySpecification(majorityFilter);
		return results.stream()
			.map(UserMajorityDto::fromBean)
			.collect(Collectors.toList());
	}

	@Override
	public UserMajorityInsightDto getMajorityInsightByUserId(String userId, EmployeeCvInfoRequestDto employeeCvInfoRequestDto, String publicYn) throws Exception {

		List<UserMajorityBean> results = majorityMapper.findMajorityBySpecification(
			new MajorityFilterDto(
				userId,
				null
			)
		);
		List<UserMajorityDto> convertedResults = new ArrayList<>();
		results.forEach(it -> {
			if (publicYn != null && !it.getPublicYn().equals(publicYn)) {
				return;
			}

			convertedResults.add(
				UserMajorityDto.fromBean(it)
			);
		});

		boolean existData = results.size() > 0;
		boolean existPublic = convertedResults.size() > 0;
		boolean allPublic = existData && convertedResults.size() == results.size();

		return new UserMajorityInsightDto(
			existData,
			existPublic,
			allPublic,
			convertedResults
		);
	}

//	public ReturnMessage addMajority(UserMajorityBean bean) throws Exception {
//
//		return new ReturnMessage(majorityMapper.addMajority(bean));
//	}

	public ReturnMessage updateMajority(UserMajorityUpdateDto updateDto) throws EntityNotFoundException {

		UserMajorityBean bean = majorityMapper.getMajorityByMajorityId(updateDto.getMajorityId());
		if (bean == null) {
			throw new EntityNotFoundException(UserMajorityBean.class);
		}

		if (updateDto.getPublicYn() != null) {
			bean.setPublicYn(updateDto.getPublicYn());
		}

		return new ReturnMessage(majorityMapper.updateMajority(bean));
	}

	public ReturnMessage updateMajorities(List<UserMajorityUpdateDto> updateDtoList) {

		updateDtoList.forEach(this::updateMajority);

		return new ReturnMessage();
	}

}