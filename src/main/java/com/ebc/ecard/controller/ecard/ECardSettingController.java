package com.ebc.ecard.controller.ecard;

import com.ebc.ecard.application.ecard.dto.ECardContractSettingsDto;
import com.ebc.ecard.application.ecard.dto.ECardTemplateSettingsDto;
import com.ebc.ecard.application.ecard.service.ECardService;
import com.ebc.ecard.application.ecard.service.ECardSettingService;
import com.ebc.ecard.application.ecard.service.KakaotalkService;
import com.ebc.ecard.application.qualification.service.UserRegistrationService;
import com.ebc.ecard.application.user.service.UserService;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/cv/{ecardId}")
public class ECardSettingController implements CorsDisabledController {

	@Resource
	ECardSettingService service;

	/**
	 * @title	- 전자명함 계약 정보 공개 여부 설정 조회
	 * @desc	- 전자명함 아이디로 전자명함의 계약 정보 공개 여부 설정값을 조회합니다.
	 * @author	- Jgpark
	 * @date	- 2022.07.12
	 * @param	- ECardBean
	 * @return	- 결과 코드
	 */
	@GetMapping("/contract-settings")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	public ReturnMessage findECardContractSettings(@PathVariable String ecardId) {
		try {
			return new ReturnMessage(service.findECardContractSettingsByECardId(ecardId));
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 공개여부 설정 조회 에러", e);
		}
	}

	/**
	 * @title	- 전자명함 계약 정보 공개 여부 수정
	 * @desc	- 전자명함 아이디로 전자명함의 계약 정보 공개 여부 설정값을 수정합니다
	 * @author	- Jgpark
	 * @date	- 2022.11.09
	 * @param	- ECardBean
	 * @return	- 결과 코드
	 */
	@PutMapping("/contract-settings")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	public ReturnMessage updateECardContractSettings(
		@PathVariable String ecardId,
		@RequestBody ECardContractSettingsDto updateDto
	) {
		updateDto.setEcardId(ecardId);

		try {
			return new ReturnMessage(service.updateECardContractSettingsByECardId(updateDto));
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 공개여부 설정 조회 에러", e);
		}
	}

	/**
	 * @title	- 전자명함 템플릿 설정 내용 조회
	 * @desc	- 전자명함 아이디로 전자명함의 템플릿 관련 설정 내용을 조회합니다.
	 * @author	- Jgpark
	 * @date	- 2022.07.20
	 */
	@GetMapping("/template-settings")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	public ReturnMessage findECardTemplateSettings(@PathVariable String ecardId) {
		try {
			return new ReturnMessage(service.findECardTemplateSettingsByECardId(ecardId));
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 공개여부 설정 조회 에러", e);
		}
	}

	/**
	 * @title	- 전자명함 템플릿 설정 내용 수정
	 * @desc	- 전자명함 아이디로 전자명함의 템플릿 관련 설정 내용을 수정합니다.
	 * @author	- Jgpark
	 * @date	- 2022.11.10
	 */
	@PutMapping("/template-settings")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	public ReturnMessage updateECardTemplateSettings(
		@PathVariable String ecardId,
		@RequestBody ECardTemplateSettingsDto updateDto
	) {
		updateDto.setEcardId(ecardId);

		try {
			return new ReturnMessage(service.updateECardTemplateSettingsByECardId(updateDto));
		} catch(Exception e) {
			return new ReturnMessage("9999", "전자명함 공개여부 설정 조회 에러", e);
		}
	}

}