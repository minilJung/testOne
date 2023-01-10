package com.ebc.ecard.application.contract.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.util.ReturnMessage;

public interface UserContractService {

	ReturnMessage getUserContractInfo(String ecardId) throws EntityNotFoundException;

	ReturnMessage getUserContractInfoByUserId(String userId) throws EntityNotFoundException;
}