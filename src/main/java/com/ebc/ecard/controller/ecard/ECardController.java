package com.ebc.ecard.controller.ecard;

import com.ebc.ecard.application.ecard.dto.CustomerAccessLogAddDto;
import com.ebc.ecard.application.ecard.dto.ECardPreviewImageDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileContactUpdateDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileRequestDto;
import com.ebc.ecard.application.ecard.dto.KakaotalkApiKeyDto;
import com.ebc.ecard.application.ecard.service.ECardService;
import com.ebc.ecard.application.ecard.service.KakaotalkService;
import com.ebc.ecard.application.qualification.service.UserRegistrationService;
import com.ebc.ecard.application.user.service.UserService;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.domain.ecard.CustomerAccessLogBean;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.util.ReturnMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/cv")
public class ECardController implements CorsDisabledController {
	@Resource
    ECardService service;

	@Resource
	UserService userService;

	@Resource
	UserRegistrationService userRegistrationService;

	@Resource
	KakaotalkService kakaotalkService;

	/**
	 * @title	- 전자명함 생성
	 * @desc	- 전자명함을 생성합니다.
	 * @author	- Jinyeon
	 * @date	- 2021.12.20
	 * @param	- ECardBean
	 * @return	- 결과 코드
	 */
	@PostMapping("/{companyId}/{userId}")
	public ReturnMessage saveECard(ECardBean bean) {
		try {
			service.saveECard(bean);
			return new ReturnMessage(bean.getEcardId());
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 생성 에러", e);
		}
	}

	/**
	 * @title	- 전자명함 조회
	 * @desc	- 전자명함 아이디로 전자명함을 조회합니다.
	 * @author	- Jinyeon
	 * @date	- 2021.12.20
	 * @param	- ECardBean
	 * @return	- 결과 코드
	 */
	@GetMapping("/{ecardId}/profile")
	public ReturnMessage findECardByECardId(
		@PathVariable String ecardId,
		ECardProfileRequestDto requestDto
	) {
		try {
			requestDto.setEcardId(ecardId);

			return new ReturnMessage(service.findECardProfile(requestDto));
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 조회 API 에러", e);
		}
	}


	/**
	 * @title	- 전자명함 배경 조회
	 * @desc	- 전자명함 배경 목록을 조회합니다.
	 * @author	- Jinyeon
	 * @date	- 2022.07.15
	 */
	@GetMapping("/profile-backgrounds")
	public ReturnMessage getBackgrounds(ECardBean bean) {
		try {
			return new ReturnMessage(service.getBackgrounds());
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 배경 목록 조회 에러", e);
		}
	}

	/**
	 * @title	- 전자명함 수정
	 * @desc	- 전자명함 아이디로 전자명함을 수정합니다.
	 * @author	- Jinyeon
	 * @date	- 2022.07.12
	 * @param	- ECardBean
	 * @return	- 결과 코드
	 */
	@PutMapping("/{ecardId}/information")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	public ReturnMessage updateECardByECardId(
		@PathVariable String ecardId,
		@RequestBody ECardProfileContactUpdateDto updateDto
	) {
		try {
			updateDto.setEcardId(ecardId);
			return new ReturnMessage(service.updateProfileContact(updateDto));
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 수정 에러", e);
		}
	}

	/**
	 * @title	- 전자명함 조회수 증가
	 * @desc	- 전자명함 조회수를 업데이트 합니다.
	 * @author	- Parkjg20
	 * @date	- 2022.09.15
	 * @param	-
	 * @return	- 결과 코드
	 */
	@PutMapping("/{ecardId}/view-count")
	public ReturnMessage visit(@PathVariable("ecardId") String eCardId) {
		try {
			return new ReturnMessage(service.addViewCount(eCardId));
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 수정 에러", e);
		}
	}

	/**
	 * @title	- 전자명함 조회수 증가
	 * @desc	- 전자명함 조회수를 업데이트 합니다.
	 * @author	- Parkjg20
	 * @date	- 2022.09.15
	 * @param	-
	 * @return	- 결과 코드
	 */
	@PutMapping("/{ecardId}/share-count")
	public ReturnMessage share(@PathVariable("ecardId") String eCardId) {
		try {
			return new ReturnMessage(service.addSharedCount(eCardId));
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 수정 에러", e);
		}
	}

	/**
	 * @title	- 전자명함 완성도 조회
	 * @desc	- 전자명함 완성도 정보를 조회합니다.
	 * @author	- Jgpark
	 * @date	- 2022.06.30
	 * @param	-
	 * @return	- 결과 코드
	 */
	@GetMapping("/{ecardId}/complete-rate")
	public ReturnMessage getCompleteRate(@PathVariable("ecardId") String ecardId) {
		try {
			return service.getECardCompleteRate(ecardId);
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 완성도 조회 API 에러", e);
		}
	}

	@GetMapping("/{ecardId}/vcard-file")
	public InputStreamResource getECardAsVCardFile(
		HttpServletResponse response,
		@PathVariable("ecardId") String ecardId
	) throws Exception {
		File vCardFile = service.getECardAsVCard(ecardId);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ vCardFile.getName());
		return new InputStreamResource(Files.newInputStream(vCardFile.toPath()));
	}

	@GetMapping(
		value = "/{ecardId}/profile-image",
		produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE }
	)
	public @ResponseBody byte[] getECardProfileImgUrl(
		@PathVariable("ecardId") String ecardId
	) {

		log.info("--------------------------------------------------");
		log.info("GET profile-image start");
		log.info("--------------------------------------------------");
		try {
			return service.getECardProfileImg(ecardId);
		} catch (Exception e) {

			log.info("--------------------------------------------------");
			log.info("GET profile-image failed", e);
			log.info("--------------------------------------------------");
		}

		return null;
	}

	@PutMapping(value = "/{ecardId}/preview-image")
	public ReturnMessage updateEcardPreviewImage(
		@PathVariable("ecardId") String ecardId,
		@RequestBody ECardPreviewImageDto updateDto
	) {

		log.info("--------------------------------------------------");
		log.info("PUT preview image update start");
		log.info("--------------------------------------------------");
		try {
			updateDto.setEcardId(ecardId);

			return new ReturnMessage(service.updateECardPreviewImage(updateDto));
		} catch (Exception e) {

			log.info("--------------------------------------------------");
			log.info("GET preview image update failed", e);
			log.info("--------------------------------------------------");
			return null;
		}
	}

	/**
	 * 이미지 저장 기능
	 */
	@GetMapping(
		value = "/{ecardId}/downloadable-image",
		produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE }
	)
	public @ResponseBody byte[] downloadECardImage(
			@PathVariable("ecardId") String ecardId
	) {

		log.info("--------------------------------------------------");
		log.info("GET preview image start");
		log.info("--------------------------------------------------");
		try {
			return service.getECardPreviewImage(ecardId);
		} catch (Exception e) {

			log.info("--------------------------------------------------");
			log.info("GET preview image failed", e);
			log.info("--------------------------------------------------");
			return null;
		}
	}

	@GetMapping(value = "/{ecardId}/profile-metadata")
	public ReturnMessage getProfileMetadata(
			@PathVariable("ecardId") String ecardId
		) {

		log.info("--------------------------------------------------");
		log.info("GET profile metadata start");
		log.info("--------------------------------------------------");
		try {
			return new ReturnMessage(service.getECardMetadata(ecardId));
		} catch (Exception e) {

		log.info("--------------------------------------------------");
			log.info("GET profile metadata failed", e);
		log.info("--------------------------------------------------");
		return null;
		}
	}
	@GetMapping("/{ecardId}/nft-profile")
	public ReturnMessage updateEcardInfo(
		HttpServletRequest request,
		HttpServletResponse response,
		@PathVariable("ecardId") String ecardId) {
		try {
			String serverName = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/api"));

			return new ReturnMessage(service.updateEcardInfo(serverName, ecardId));
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 업데이트 API 에러", e);
		}
	}

	@GetMapping("/nft-profile/callback")
	public ReturnMessage updateEcardInfoCallback() {
		return new ReturnMessage("성공");
	}

	@PostMapping("/customer-link/{ecardId}/{custId}/access-log")
	public ReturnMessage addCustomerLinkAccessLog(
		@PathVariable("ecardId") String ecardId,
		@PathVariable("custId") String custId,
		@RequestBody CustomerAccessLogAddDto body
	) {
		if (StringUtils.isEmpty(ecardId) || StringUtils.isEmpty(custId)) {
			return new ReturnMessage("400", "잘못된 요청입니다", new RuntimeException());
		}

		service.addCustomerAccessLog(CustomerAccessLogBean.access(ecardId, custId, body.getCustomerLink()));

		return new ReturnMessage();
	}

	@GetMapping("/kakaotalk-keys")
	public ReturnMessage getKakaoApiKeys() {

		KakaotalkApiKeyDto apiKey = kakaotalkService.getKakaotalkApiKey();
		if (apiKey == null) {
			return new ReturnMessage("1000", "카카오톡 API 키를 찾을 수 없습니다.", null);
		}

		return new ReturnMessage(apiKey);
	}

	/**
	 * @title    - 사용자 가입 여부 확인
	 * @desc    - 사용자 가입 여부를 확인한다.
	 * @author    - Jgpark
	 * @date    - 2022.11.16
	 */
	@GetMapping("/{ecardId}/existence")
	public ReturnMessage getUserExistenceByUserId(
		@PathVariable("ecardId") String ecardId
	) {
		if (StringUtils.isEmpty(ecardId)) {
			return new ReturnMessage("400", "잘못된 요청입니다.", new RuntimeException("ecardId 가 없습니다."));
		}
		try {
			return new ReturnMessage(service.getECardExistenceByECardId(ecardId));
		} catch (Exception e) {
			return new ReturnMessage("9999", "전자명함 가입 여부 조회 API 에러", e);
		}
	}

	/**
	 * @title    - 사용자 가입 여부 확인
	 * @desc    - 사용자 가입 여부를 확인한다.
	 * @author    - Jgpark
	 * @date    - 2022.11.16
	 */
	@PostMapping("/{ecardId}/account-id")
	public ReturnMessage revisionAccountId(
		@PathVariable("ecardId") String ecardId,
		@RequestBody Object body
	) {
		if (StringUtils.isEmpty(ecardId)) {
			return new ReturnMessage("400", "잘못된 요청입니다.", new RuntimeException("ecardId 가 없습니다."));
		}

		try {
			return new ReturnMessage(service.revisionAccountId(ecardId));
		} catch (Exception e) {
			return new ReturnMessage("9999", "전자명함 IAM 계정 업데이트 API 에러", e);
		}
	}

	/**
	 * @title    - FP의 등록증을 조회
	 * @desc    - FP의 등록증을 조회한다.
	 * @author    - wnguds
	 * @date    - 2022.11.24
	 */
	@GetMapping("/{ecardId}/registration-image")
	public ReturnMessage getEcardRegistrationsImage(
			@PathVariable("ecardId") String ecardId
	) {
		if (StringUtils.isEmpty(ecardId)) {
			return new ReturnMessage("400", "잘못된 요청입니다.", new RuntimeException("ecardId 가 없습니다."));
		}
		try {
			return new ReturnMessage(userRegistrationService.getUserRegistrationImageByEcardId(ecardId));
		} catch (Exception e) {
			return new ReturnMessage("9999", "전자명함 등록증 조회 API 에러", e);
		}
	}

}