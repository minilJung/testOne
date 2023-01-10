package com.ebc.ecard.application.user.service;

import com.ebc.ecard.application.agreement.dto.UserAgreementAddDto;
import com.ebc.ecard.application.agreement.service.UserAgreementService;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoResponseDto;
import com.ebc.ecard.application.exception.EmployeeCVNullException;
import com.ebc.ecard.application.exception.NotAvailableCustIdException;
import com.ebc.ecard.application.exception.UnexpectedFpNmcdDvsnCode;
import com.ebc.ecard.application.majority.dto.MajorityDto;
import com.ebc.ecard.application.nftcv.service.NftCvInternalService;
import com.ebc.ecard.application.user.dto.IAMRequestDto;
import com.ebc.ecard.application.user.dto.IAMResponseDto;
import com.ebc.ecard.application.user.dto.UserAddUsingFPDto;
import com.ebc.ecard.application.user.dto.UserAddUsingMandatoryDto;
import com.ebc.ecard.application.user.dto.UserExistenceDto;
import com.ebc.ecard.application.user.dto.UserExistenceRequestDto;
import com.ebc.ecard.application.user.exception.IAMIntegrationFailedException;
import com.ebc.ecard.application.user.internal.EmployeeCvInternalService;
import com.ebc.ecard.application.user.internal.IAMInternalService;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.domain.majority.UserMajorityBean;
import com.ebc.ecard.application.nftcv.dto.NFTCvUserDto;
import com.ebc.ecard.domain.user.UserBean;
import com.ebc.ecard.mapper.ECardMapper;
import com.ebc.ecard.mapper.FileMapper;
import com.ebc.ecard.mapper.UserBadgeMapper;
import com.ebc.ecard.mapper.UserCareerMapper;
import com.ebc.ecard.mapper.UserInsuranceMapper;
import com.ebc.ecard.mapper.UserMajorityMapper;
import com.ebc.ecard.mapper.UserMapper;
import com.ebc.ecard.mapper.UserQualificationMapper;
import com.ebc.ecard.persistence.UserRepository;
import com.ebc.ecard.property.ECardUiPropertyBean;
import com.ebc.ecard.util.AES256;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final String[] DEFAULT_AGREEMENTS = {"개인정보수집및이용동의", "제3자정보제공동의", "서비스이용약관"};

	private final String[] DEFAULT_MAJORITIES = {
		"암/질병 보험",
		"어린이/태아 보험",
		"자동차/운전자 보험",
		"종신/연금 보험",
		"화재/배상/책임 보험",
		"치아 보험"
	};

	@Resource
	private ECardUiPropertyBean uiProperty;

	@Resource
	UserAgreementService agreementService;

	@Resource
    UserMapper mapper;

	@Resource
	ECardMapper ecardMapper;

	@Resource
	UserMajorityMapper majorityMapper;

	@Resource
	UserInsuranceMapper insuranceMapper;

	@Resource
	UserCareerMapper careerMapper;

	@Resource
	UserQualificationMapper qualificationMapper;

	@Resource
	UserBadgeMapper badgeMapper;

	@Resource
	FileMapper fileMapper;

	@Resource
	XeCommon common;

	@Resource
	ObjectMapper objectMapper;

	@Resource
	EmployeeCvInternalService employeeCvService;

	@Resource
	IAMInternalService iamService;

	@Resource
	NftCvInternalService nftCvService;

	@Resource
	UserRepository userRepository;

	public HashMap<String, Object> findUserByUserId(String userId) {
		HashMap<String, Object> userMap = mapper.findUserMapByUserId(userId);

		try {
			if(userMap.get("fileName") != null && !userMap.get("fileName").equals(""))
				userMap.put("profileImgUrl", common.getS3FilePath(userMap.get("fileName").toString()));
		} catch(Exception e) {
			// ignore
		}

		return userMap;
	}

	@Transactional
	public ReturnMessage saveUser(String serverName, UserBean bean) throws Exception {

		UserBean userInfo = mapper.getUserInfoByFpId(bean.getFpId());
		if (userInfo == null || !bean.getUserId().equals(userInfo.getUserId())) {
			log.info("Requested user Id: {} DB User Id: {}", bean.getUserId(), userInfo.getUserId());

			return new ReturnMessage("9901", "FP와 본인인증을 시도하는 유저가 일치하지 않습니다.", "실패");
		}

		// 기본 동의 약관
		UserAgreementAddDto userAgreement = new UserAgreementAddDto(bean.getUserId(), DEFAULT_AGREEMENTS);
		agreementService.addAgreement(userAgreement);

		ECardBean ecard = ecardMapper.findECardByUserId(bean.getUserId());
		if ("hanwhalifefs".equals(ecard.getCompanyId())) {
			String encryptedFpUniqNo = AES256.encrypt(bean.getFpId(), AES256.EBC_AES256_KEY);
			EmployeeCvInfoResponseDto cvInfoDto = employeeCvService.getEmployeeCvInfo(new EmployeeCvInfoRequestDto(encryptedFpUniqNo));
			if ("99".equals(cvInfoDto.getRetnCode())) {
				throw new EmployeeCVNullException();
			}
		}

		// IAM Integration
		IAMResponseDto iamResult = null;
		try {
			iamResult = iamService.saveIAM(new IAMRequestDto(bean.getName(), bean.getUserId(), bean.getCi()));
		} catch (HttpClientErrorException e) {
			if (e.getMessage().contains("\"errorCode\":400101")) {
				// 랜덤 유저아이디로 재시도
				iamResult = iamService.saveIAM(
					new IAMRequestDto(
						bean.getName(),
						bean.getName() + "_" + (int)(Math.random() * 1000),
						bean.getCi()
					)
				);
			}
		} catch(Exception e) {
			log.info("Failed to integration IAM {}", e.getMessage(), e);
		}

		if (iamResult == null) {
			throw new IAMIntegrationFailedException("IAM 등록에 실패했습니다. iamResult is null");
		}

		// Iam 연동까지 성공한 경우 eBAP 유저 등록
		String accountId = iamResult.getData().getAccountId();
		try {
			nftCvService.saveEBAPAccount(iamResult.getData().getAccountId());
		} catch(HttpClientErrorException e) {
			if (!e.getStatusCode().equals(HttpStatus.CONFLICT)) {
				// conflict 이외 ebBAP 실패인 경우
				throw e;
			}

			// ignore when conflict
		}

		UserBean userInfoUpdateBean = mapper.getUserInfoByFpId(bean.getFpId());
		userInfoUpdateBean.setAccountId(accountId);
		userInfoUpdateBean.setBirthdate(bean.getBirthdate());
		userInfoUpdateBean.setCi(bean.getCi());

		return new ReturnMessage(mapper.updateUserInfoByUserId(userInfoUpdateBean));
	}

	@Transactional
	public String saveUserUsingFpId(String serverHost, UserAddUsingFPDto params) throws Exception {

		ECardBean bean = ecardMapper.findECardByFpId(params.getFpUniqNo());

		String encryptedFpUniqNo = AES256.encrypt(bean.getFpId(), AES256.EBC_AES256_KEY);
		EmployeeCvInfoResponseDto cvInfoDto = employeeCvService.getEmployeeCvInfo(new EmployeeCvInfoRequestDto(encryptedFpUniqNo));
		if (bean != null) {
			return (params.getName().equals(cvInfoDto.getFpNm())) ? "성공" : "이름 다름";
		}

		saveEcardUsingEmployeeCvInfo(cvInfoDto);

		return "등록실패";
	}

	@Transactional
	public String saveUserUsingUserMandatoryInfo(UserAddUsingMandatoryDto params) throws Exception {

		String mobileNo = params.getMobileNo().replaceAll("-", "");
		String userId = params.getName() + "_" + mobileNo;

		int userCount = mapper.findDuplicationUserByUserId(userId);
		if (userCount > 0) {
			return userId;
		}

		String di = common.getRandomString(false, 16);
		UserBean newUser = new UserBean(
				userId,
				params.getName() + "_" + di,
				null,
				params.getName(),
				common.getRandomString(false, 16),
				null,
				mobileNo,
				params.getBirthdate(),
				params.getEmail(),
				"N"
		);

		if(0 < mapper.saveUser(newUser)) {
			ECardBean ecard = new ECardBean(
				common.getRandomString(true, 6),
				"X",
				newUser.getUserId(),
				params.getEmail(),
				common.getRandomString(false, 7),
				null,
				null
			);

			ecardMapper.addECard(ecard);

			majorityMapper.addMajority(new UserMajorityBean(common.getUuid(false), "암/질병 보험", newUser.getUserId(), "N"));
			majorityMapper.addMajority(new UserMajorityBean(common.getUuid(false), "어린이/태아 보험", newUser.getUserId(), "N"));
			majorityMapper.addMajority(new UserMajorityBean(common.getUuid(false), "자동차/운전자 보험", newUser.getUserId(), "N"));
			majorityMapper.addMajority(new UserMajorityBean(common.getUuid(false), "종신/연금 보험", newUser.getUserId(), "N"));
			majorityMapper.addMajority(new UserMajorityBean(common.getUuid(false), "화재/배상/책임 보험", newUser.getUserId(), "N"));
			majorityMapper.addMajority(new UserMajorityBean(common.getUuid(false), "치아 보험", newUser.getUserId(), "N"));

			// 기본 동의 약관
			UserAgreementAddDto userAgreement = new UserAgreementAddDto(newUser.getUserId(), DEFAULT_AGREEMENTS);
			agreementService.addAgreement(userAgreement);

			return userId;
		}

		return "등록실패";
	}

	public UserExistenceDto getUserExistence(UserExistenceRequestDto requestDto) throws Exception {

		String plainFpUniqNo = AES256.decrypt(requestDto.getFpUniqNo(), AES256.EBC_AES256_KEY);
		requestDto.setFpUniqNo(plainFpUniqNo);
		String encryptedFpId = AES256.encrypt(plainFpUniqNo, AES256.EBC_AES256_KEY);

		Map<String, Object> existenceDto = mapper.getUserExistence(requestDto);
		if ("N".equals(existenceDto.get("existenceYn"))) {

			EmployeeCvInfoResponseDto cvInfoDto = employeeCvService.getEmployeeCvInfo(
					new EmployeeCvInfoRequestDto(
						encryptedFpId
					)
				);
			if ("99".equals(cvInfoDto.getRetnCode())
				|| !(cvInfoDto.getTnofDvsnCode().equals("01") || cvInfoDto.getTnofDvsnCode().equals("11"))) {

				return new UserExistenceDto(
						"N",
						null
				);
			}

			ECardBean ecard = saveEcardUsingEmployeeCvInfo(cvInfoDto);
			if (ecard == null) {
				throw new RuntimeException("전자명함 초기화에 실패했습니다.");
			}
			existenceDto.put("ecardId", ecard.getEcardId());
		}

		String ecardId = (String) existenceDto.get("ecardId");
		String serviceUrl = uiProperty.getUrlWithProtocol();

		String uri;
		switch (requestDto.getFpNmcdDvsnCode()) {
			case "01":
				uri = "main/" + ecardId;
				break;
			case "02":
				uri = "customer-link/" + Base64Util.encode(ecardId);
				break;
			case "03":
				if(StringUtils.isEmpty(requestDto.getCustId())) {
					throw new NotAvailableCustIdException();
				}

				uri = "customer-link/" + Base64Util.encode(ecardId) + "/" + Base64Util.encode(requestDto.getCustId());
				break;
			default:
				throw new UnexpectedFpNmcdDvsnCode();
		}

		String ecardUrl = serviceUrl + "/" + uri;
		log.info("PlusApp Response Data : {}", ecardUrl);

		//return new UserExistenceDto(
		//	existenceYn,
		//	existenceYn.equals("Y") ?  ecardUrl : null
		//);
		return new UserExistenceDto(
			"Y",
			ecardUrl
		);
	}

	@Override
	public NFTCvUserDto getEcardUserInfo(String ecardId) throws Exception {
		return mapper.findEcardUserInfo(ecardId);
	}

	@Override
	public UserBean getUserInfoByFpId(String ecardId) throws Exception {
		UserBean result = mapper.getUserInfoByFpId(ecardId);

		return result;
	}

	@Override
	public ReturnMessage updateUserInfoByFpId(UserBean bean) throws Exception {
		return new ReturnMessage(mapper.updateUserInfoByUserId(bean));
	}


	private ECardBean saveEcardUsingEmployeeCvInfo(EmployeeCvInfoResponseDto cvInfoDto) throws Exception {
		if ("99".equals(cvInfoDto.getRetnCode())) {
			throw new EmployeeCVNullException();
		}

		String fpUniqNo = AES256.decrypt(cvInfoDto.getFpUniqNo(), AES256.EBC_AES256_KEY);
		String mobileNo = AES256.decrypt(cvInfoDto.getHpno(), AES256.EBC_AES256_KEY);
		String email = AES256.decrypt(cvInfoDto.getMailAddr(), AES256.EBC_AES256_KEY);

		String di = common.getRandomString(false, 16);
		UserBean newUser = new UserBean(
				cvInfoDto.getFpNm() + "_" + mobileNo,
				cvInfoDto.getFpNm() + "_" + di,
				null,
				cvInfoDto.getFpNm(),
				common.getRandomString(false, 16),
				null,
				mobileNo,
				null,
				email,
				"N"
		);

		String ecardId = common.getRandomString(true, 6);
		ECardBean ecard = new ECardBean(
			ecardId,
			"hanwhalifefs",
			newUser.getUserId(),
			email,
			fpUniqNo,
			cvInfoDto.getClpsNm(),
			cvInfoDto.getBrocOrgnNm()
		);

		// save Employee CV to Bean
		newUser = employeeCvService.setUserProfileToUserBean(newUser, cvInfoDto);
		userRepository.save(newUser);

		ecardMapper.addECard(ecard);

		// 전문분야 기본 값 추가
		addDefaultMajority(newUser.getUserId());
		addDefaultAgreement(newUser.getUserId());

		return ecard;
	}

	private void addDefaultMajority(String userId) {
		List<String> defaultMajorities = Arrays.asList(DEFAULT_MAJORITIES);
		List<MajorityDto> majorityList = defaultMajorities.stream().map(it -> {
			return new MajorityDto(common.getUuid(false), it);
		}).collect(Collectors.toList());

		majorityList.forEach(majority -> {
			try {
				majorityMapper.addMajority(new UserMajorityBean(majority.getMajorityId(), majority.getMajorityName(), userId, "N"));

			} catch (DuplicateKeyException e) {
				// ignore
			} catch (Exception e) {
				try {
					log.info("An internal error occurred while trying to majority:{}, {}", objectMapper.writeValueAsString(majority), e.getMessage());
				} catch (JsonProcessingException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
	}

	public void addDefaultAgreement(String userId) {

		try {
			// 기본 동의 약관
			UserAgreementAddDto userAgreement = new UserAgreementAddDto(userId, DEFAULT_AGREEMENTS);
			agreementService.addAgreement(userAgreement);
		} catch (DuplicateKeyException e) {
			// ignore
		} catch (Exception e) {
			try {
				log.info("An internal error occurred while trying to career:{}, {}",
					objectMapper.writeValueAsString(DEFAULT_AGREEMENTS),
					e.getMessage()
				);
			} catch (JsonProcessingException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}
