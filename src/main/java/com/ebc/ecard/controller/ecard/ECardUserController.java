package com.ebc.ecard.controller.ecard;

import com.ebc.ecard.application.activity.service.UserActivityService;
import com.ebc.ecard.application.badge.service.UserBadgeService;
import com.ebc.ecard.application.career.service.UserCareerService;
import com.ebc.ecard.application.contract.service.UserContractService;
import com.ebc.ecard.application.ecard.service.ECardService;
import com.ebc.ecard.application.insurance.service.UserInsuranceService;
import com.ebc.ecard.application.majority.service.UserMajorityService;
import com.ebc.ecard.application.qualification.service.UserQualificationService;
import com.ebc.ecard.application.qualification.service.UserRegistrationService;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.application.ecard.dto.ECardUserDto;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/cv/{ecardId}/user")
public class ECardUserController implements CorsDisabledController {

	@Resource
    ECardService eCardService;

	@Resource
	UserCareerService careerService;

	@Resource
	UserQualificationService qualificationService;

	@Resource
    UserActivityService activityService;

	@Resource
	UserBadgeService badgeService;

	@Resource
	UserMajorityService majorityService;

	@Resource
    UserInsuranceService userInsuranceService;

	@Resource
	UserContractService userContractService;

	@Resource
	UserRegistrationService userRegistrtionService;

	@GetMapping("/public-careers")
	public ReturnMessage getPublicCareers(
		@PathVariable("ecardId") String ecardId
	) {
		try {
			ECardBean bean = new ECardBean();
			bean.setEcardId(ecardId);
			ECardUserDto ecard = eCardService.findECardUser(bean);
			if (ecard == null) {
				return new ReturnMessage("404", "전자명함 정보를 찾을 수 없습니다.", false);
			}

			EmployeeCvInfoRequestDto params = new EmployeeCvInfoRequestDto();
			params.setFpId(ecard.getFpId());

			return new ReturnMessage(careerService.findCareerInsightByUserId(ecard.getUserId(), "Y"));
		} catch(Exception e) {
			return new ReturnMessage("9999", "사용자 수상/교욱 내역 조회 API 에러", e);
		}
	}

	@GetMapping("/public-qualifications")
	public ReturnMessage getPublicQualifications(
		@PathVariable("ecardId") String ecardId
	) {
		try {
			ECardBean bean = new ECardBean();
			bean.setEcardId(ecardId);
			ECardUserDto ecard = eCardService.findECardUser(bean);
			if (ecard == null) {
				return new ReturnMessage("404", "전자명함 정보를 찾을 수 없습니다.", false);
			}

			EmployeeCvInfoRequestDto params = new EmployeeCvInfoRequestDto();
			params.setFpId(ecard.getFpId());

			return new ReturnMessage(qualificationService.getQualificationInsightByUserId(ecard.getUserId(), "Y"));
		} catch(Exception e) {
			return new ReturnMessage("9999", "사용자 수상/교욱 내역 조회 API 에러", e);
		}
	}

	@GetMapping("/public-activities")
	public ReturnMessage getPublicActivities(
		@PathVariable("ecardId") String ecardId
	) {
		try {
			ECardBean bean = new ECardBean();
			bean.setEcardId(ecardId);
			ECardUserDto ecard = eCardService.findECardUser(bean);
			if (ecard == null) {
				return new ReturnMessage("404", "전자명함 정보를 찾을 수 없습니다.", false);
			}

			EmployeeCvInfoRequestDto params = new EmployeeCvInfoRequestDto();
			params.setFpId(ecard.getFpId());

			return activityService.getUserActivityInsightByUserId(
					new ActivityFilterDto(ecard.getUserId(), "Y")
				);
		} catch(Exception e) {
			return new ReturnMessage("9999", "사용자 수상/교욱 내역 조회 API 에러", e);
		}
	}

	/**
	 * @title - 사용자 뱃지 정보 조회
	 * @desc - 사용자 뱃지 정보를 조회 한다.
	 * @author - Jgpark
	 * @date - 2022.07.18
	 */
	@GetMapping("/public-badges")
	public ReturnMessage getPublicBadges(@PathVariable("ecardId") String ecardId) {
		try {
			ECardBean bean = new ECardBean();
			bean.setEcardId(ecardId);
			ECardUserDto ecard = eCardService.findECardUser(bean);
			if (ecard == null) {
				return new ReturnMessage("404", "전자명함 정보를 찾을 수 없습니다.", false);
			}

			EmployeeCvInfoRequestDto params = new EmployeeCvInfoRequestDto();
			params.setFpId(ecard.getFpId());
			return badgeService.getUserBadgeInfo(ecard.getUserId(), params, "Y");
		} catch (Exception e) {
			return new ReturnMessage("9999", "개인정보 업데이트 API 에러", e);
		}
	}

	/**
	 * @title - 공개 전문분야 조회
	 * @desc - 사용자가 공개한 전문분야 목록을 조회한다.
	 * @author - Jgpark
	 * @date - 2022.07.18
	 */
	@GetMapping("/public-majorities")
	public ReturnMessage findPublicMajorities(@PathVariable("ecardId") String ecardId) {
		try {

			ECardBean bean = new ECardBean();
			bean.setEcardId(ecardId);
			ECardUserDto ecard = eCardService.findECardUser(bean);
			if (ecard == null) {
				return new ReturnMessage("404", "전자명함 정보를 찾을 수 없습니다.", false);
			}

			EmployeeCvInfoRequestDto params = new EmployeeCvInfoRequestDto();
			params.setFpId(ecard.getFpId());

			return new ReturnMessage(majorityService.getMajorityInsightByUserId(ecard.getUserId(), params, "Y"));
		} catch (Exception e) {
			return new ReturnMessage("9999", "전문분야 조회 API 에러", e);
		}
	}

	/**
	 * @title - 공개 판매자격 조회
	 * @desc - 사용자가 공개한 전문분야 목록을 조회한다.
	 * @author - Jgpark
	 * @date - 2022.07.18
	 */
	@GetMapping("/insurances")
	public ReturnMessage findInsurances(@PathVariable("ecardId") String ecardId) {
		try {

			ECardBean bean = new ECardBean();
			bean.setEcardId(ecardId);
			ECardUserDto ecard = eCardService.findECardUser(bean);
			if (ecard == null) {
				return new ReturnMessage("404", "전자명함 정보를 찾을 수 없습니다.", false);
			}

			return userInsuranceService.getUserInsuranceInfo(ecard.getUserId());
		} catch (Exception e) {
			return new ReturnMessage("9999", "전문분야 조회 API 에러", e);
		}
	}

	/**
	 * @title - 공개 계약현황 조회
	 * @desc - 사용자가 공개한 전문분야 목록을 조회한다.
	 * @author - Jgpark
	 * @date - 2022.07.18
	 */
	@GetMapping("/contract-info")
	public ReturnMessage findContractInfo(@PathVariable("ecardId") String ecardId) {
		try {

			ECardBean bean = new ECardBean();
			bean.setEcardId(ecardId);
			ECardUserDto ecard = eCardService.findECardUser(bean);
			if (ecard == null) {
				return new ReturnMessage("404", "전자명함 정보를 찾을 수 없습니다.", false);
			}

			EmployeeCvInfoRequestDto params = new EmployeeCvInfoRequestDto();
			params.setFpId(ecard.getFpId());

			return userContractService.getUserContractInfo(ecardId);
		} catch (Exception e) {
			return new ReturnMessage("9999", "전문분야 조회 API 에러", e);
		}
	}

	/**
	 * @title - 보험모집종사자 등록증 조회
	 * @desc - 사용자의 보험모집종사자 등록증을 조회한다.
	 * @author - wnguds
	 * @date - 2022.10.07
	 */
	@GetMapping("/public-registrations")
	public ReturnMessage findRegistrationInfo(@PathVariable("ecardId") String ecardId) {
		try {

			ECardBean bean = new ECardBean();
			bean.setEcardId(ecardId);
			ECardUserDto ecard = eCardService.findECardUser(bean);
			if (ecard == null) {
				return new ReturnMessage("404", "전자명함 정보를 찾을 수 없습니다.", false);
			}

			EmployeeCvInfoRequestDto params = new EmployeeCvInfoRequestDto();
			params.setFpId(ecard.getFpId());

			return new ReturnMessage(userRegistrtionService.findPublicRegistrationByUserId(ecard.getUserId(), "Y"));
		} catch (Exception e) {
			return new ReturnMessage("9999", "보험모집종사자 조회 API 에러", e);
		}
	}
}