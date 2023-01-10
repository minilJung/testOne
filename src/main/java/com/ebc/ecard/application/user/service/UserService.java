package com.ebc.ecard.application.user.service;

import com.ebc.ecard.domain.user.UserBean;
import com.ebc.ecard.application.nftcv.dto.NFTCvUserDto;
import com.ebc.ecard.application.user.dto.UserAddUsingFPDto;
import com.ebc.ecard.application.user.dto.UserAddUsingMandatoryDto;
import com.ebc.ecard.application.user.dto.UserExistenceDto;
import com.ebc.ecard.application.user.dto.UserExistenceRequestDto;
import com.ebc.ecard.util.ReturnMessage;

import java.util.HashMap;

public interface UserService {
	HashMap<String, Object>	findUserByUserId(String userId);
	ReturnMessage saveUser(String serverName, UserBean bean) throws Exception;

	String saveUserUsingFpId(String serverHost, UserAddUsingFPDto params) throws Exception;
	String saveUserUsingUserMandatoryInfo(UserAddUsingMandatoryDto params) throws Exception;

	UserExistenceDto getUserExistence(UserExistenceRequestDto filter) throws Exception;

	NFTCvUserDto getEcardUserInfo(String ecardId) throws Exception;
	UserBean getUserInfoByFpId(String ecardId) throws Exception;
	ReturnMessage updateUserInfoByFpId(UserBean bean) throws Exception;
}