package com.ebc.ecard.application.contract.service;

import com.ebc.ecard.application.contract.dto.ContractInfoDto;
import com.ebc.ecard.application.contract.dto.UserContractInfoDto;
import com.ebc.ecard.mapper.ECardMapper;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.application.ecard.dto.ECardContractSettingsDto;
import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserContractServiceImpl implements UserContractService {

	@Resource
	protected ECardMapper eCardMapper;

	@Override
	public ReturnMessage getUserContractInfo(String ecardId) throws EntityNotFoundException {

		ECardBean eCard = eCardMapper.findECardByECardId(ecardId);
		if (eCard == null) {
			throw new EntityNotFoundException(ECardBean.class);
		}

		return new ReturnMessage(UserContractInfoDto.of(eCard));
	}

	@Override
	public ReturnMessage getUserContractInfoByUserId(String userId) throws EntityNotFoundException {

		ECardBean bean = eCardMapper.findECardByUserId(userId);
		if (bean == null) {
			throw new EntityNotFoundException(ECardBean.class);
		}

		return new ReturnMessage(UserContractInfoDto.of(bean));
	}

}