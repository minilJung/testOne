package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.user.UserBean;
import com.ebc.ecard.application.nftcv.dto.NFTCvUserDto;
import com.ebc.ecard.application.user.dto.UserExistenceRequestDto;

import com.ebc.ecard.application.user.dto.CooconIdentificationPayloadDto;
import com.ebc.ecard.application.user.dto.IAMInfoDto;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface UserMapper {
	Integer findDuplicationUserByUserId(String userId);
	Integer saveUser(UserBean bean);
	UserBean findUserByUserId(String userId);

	HashMap<String, Object> findUserMapByUserId(String userId);

	Map<String, Object> getUserExistence(UserExistenceRequestDto filter);

	Integer updateUserLastLoginAtByUserId(String userId);

	CooconIdentificationPayloadDto selectScrapingPayload(String userId);

	IAMInfoDto findAccountId(String userId);

	NFTCvUserDto findEcardUserInfo(String ecardId);

	UserBean getUserInfoByFpId(String fpId);
	UserBean getUserInfoByECardId(String ecardId);

	Integer updateUserInfoByUserId(UserBean bean);

}
